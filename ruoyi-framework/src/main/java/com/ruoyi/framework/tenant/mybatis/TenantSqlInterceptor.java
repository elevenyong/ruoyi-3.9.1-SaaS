package com.ruoyi.framework.tenant.mybatis;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.tenant.TenantContextHolder;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 多租户 SQL 改写拦截器（JSqlParser 4.9 适配版）
 *
 * - 只使用 join.onExpression 作为最终 ON 输出
 * - 强制 join.onExpressions = emptyList（非 null），避免 4.9 Join.toString NPE + 避免重复 ON
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class TenantSqlInterceptor implements Interceptor {


    private static final String TENANT_COLUMN = "tenant_id";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler handler = (StatementHandler) invocation.getTarget();

        Long tenantId = TenantContextHolder.getTenantId();
        // tenantId==0 可作为平台管理员/平台租户（按需使用），这里默认不隔离
        if (tenantId == null || tenantId == 0L) {
            return invocation.proceed();
        }

        String originalSql = handler.getBoundSql().getSql();
        if (originalSql == null) {
            return invocation.proceed();
        }

        try {
            String newSql = rewriteSql(originalSql, tenantId);
            if (newSql != null && !newSql.equals(originalSql)) {
                setField(handler.getBoundSql(), "sql", newSql);
            }
        } catch (Exception ex) {
            // 解析失败：不能永久放过
            // 对“需要隔离”的 SQL 直接 fail-fast，避免跨租户数据泄露
            if (TenantSqlTableResolver.sqlLikelyNeedTenant(originalSql)) {
                throw new ServiceException("多租户 SQL 解析失败（已阻止执行），请修复 SQL： " + ex);
            }
            return invocation.proceed();
        }

        return invocation.proceed();
    }

    private String rewriteSql(String sql, Long tenantId) throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse(sql);
        if (stmt instanceof Select) {
            Select select = (Select) stmt;
            return rewriteSelect(select, tenantId, sql);
        }

        if (stmt instanceof Update) {
            Update update = (Update) stmt;
            String table = (update.getTable() != null) ? update.getTable().getName() : null;
            if (!TenantSqlTableResolver.needTenant(table)) return sql;

            Expression cond = eqTenant(updateAliasOrTable(update) + "." + TENANT_COLUMN, tenantId);
            Expression newWhere = (update.getWhere() == null) ? cond : new AndExpression(update.getWhere(), cond);
            update.setWhere(simplifyAnd(newWhere));
            return fixDuplicateOn(stmt.toString());
        }

        if (stmt instanceof Delete) {
            Delete del = (Delete) stmt;
            String table = (del.getTable() != null) ? del.getTable().getName() : null;
            if (!TenantSqlTableResolver.needTenant(table)) return sql;

            Expression cond = eqTenant(deleteAliasOrTable(del) + "." + TENANT_COLUMN, tenantId);
            Expression newWhere = (del.getWhere() == null) ? cond : new AndExpression(del.getWhere(), cond);
            del.setWhere(simplifyAnd(newWhere));
            return fixDuplicateOn(stmt.toString());
        }

        if (stmt instanceof Insert) {
            Insert ins = (Insert) stmt;
            String table = (ins.getTable() != null) ? ins.getTable().getName() : null;
            if (!TenantSqlTableResolver.needTenant(table)) return sql;

            // insert-select 不在这里处理（避免误改）
            if (ins.getSelect() != null && isRealInsertSelect(ins)) {
                return sql;
            }
            // 已经显式写了 tenant_id，则不处理
            if (insertHasTenantColumn(ins)) {
                return sql;
            }

            // 追加 tenant_id 列（兼容 4.9 ExpressionList<Column> 与旧版 List<Column>）
            if (appendInsertTenantColumn(ins)) {
                // values(...) 的追加由 TenantSqlInsertValueAppender 内部做 4.9 兼容
                TenantSqlInsertValueAppender.appendTenantValue(ins, tenantId);
                return fixDuplicateOn(stmt.toString());
            }

            // 没写列清单：insert into t values(...)，不安全，默认不处理
            return sql;
        }
        return sql;
    }

    /**
     * 判断是否是真正的 Insert-Select（insert into t (...) select ... from ...）
     * 兼容不同版本 JSqlParser（避免 getSelectBody / SelectBody 等 API 不存在导致编译失败）
     */
    private boolean isRealInsertSelect(net.sf.jsqlparser.statement.insert.Insert ins) {
        try {
            Object selectObj = ins.getSelect(); // 已确认不为 null
            if (selectObj == null) {
                return false;
            }

            // 1) 先尝试：Select#getSelectBody()
            Object selectBody = invokeNoArg(selectObj, "getSelectBody");
            if (selectBody != null) {
                // PlainSelect#getFromItem() / getJoins()
                Object fromItem = invokeNoArg(selectBody, "getFromItem");
                if (fromItem != null) {
                    return true;
                }
                Object joins = invokeNoArg(selectBody, "getJoins");
                if (joins instanceof java.util.List && !((java.util.List) joins).isEmpty()) {
                    return true;
                }

                // 有些版本可能是 SetOperationList / WithItem，保守当作 insert-select
                String bodyClass = selectBody.getClass().getName();
                if (bodyClass.contains("SetOperation") || bodyClass.contains("WithItem")) {
                    return true;
                }

                // 没有 FROM/JOIN，认为只是表达式列表（等价 VALUES）
                return false;
            }

            // 2) 老版本兜底：Select 可能直接有 getPlainSelect() 或 getFromItem()
            Object fromItem2 = invokeNoArg(selectObj, "getFromItem");
            if (fromItem2 != null) {
                return true;
            }
            Object joins2 = invokeNoArg(selectObj, "getJoins");
            if (joins2 instanceof java.util.List && !((java.util.List) joins2).isEmpty()) {
                return true;
            }

            // 3) 实在判断不了：保守起见当作 insert-select（不改写）
            return true;

        } catch (Exception e) {
            // 出异常时：保守起见当作 insert-select，避免误改
            return true;
        }
    }


    private String rewriteSelect(Select select, Long tenantId, String originalSql) {
        Object body = getSelectBodyCompat(select);
        if (body instanceof PlainSelect) {
            PlainSelect ps = (PlainSelect) body;
            boolean changed = applyTenantToPlainSelect(ps, tenantId);
            String out = changed ? select.toString() : originalSql;
            return fixDuplicateOn(out);
        }
        return originalSql;
    }

    private boolean applyTenantToPlainSelect(PlainSelect ps, Long tenantId) {
        if (ps == null) return false;

        boolean changed = false;

        FromItem from = ps.getFromItem();
        String mainTable = TenantSqlTableResolver.resolveMainTableName(from);

        // PageHelper count / 派生表：FROM ( ... ) table_count 需要下钻
        PlainSelect derived = extractPlainSelect(from);
        if (mainTable == null && derived != null) {
            return applyTenantToPlainSelect(derived, tenantId);
        }

        // 主表：WHERE 追加 tenant 条件
        if (mainTable != null && TenantSqlTableResolver.needTenant(mainTable)) {
            String qualifier = tableAliasOrTable(ps, mainTable);
            Expression cond = eqTenant(qualifier + "." + TENANT_COLUMN, tenantId);
            Expression newWhere = (ps.getWhere() == null) ? cond : new AndExpression(ps.getWhere(), cond);
            ps.setWhere(simplifyAnd(newWhere));
            changed = true;
        }

        // JOIN：tenant 条件必须放到 ON（保持 LEFT JOIN 语义）
        List<Join> joins = ps.getJoins();
        if (joins != null && !joins.isEmpty()) {
            for (Join join : joins) {
                FromItem right = join.getRightItem();
                if (right == null) continue;

                // JOIN (subselect) 下钻处理
                PlainSelect joinDerived = extractPlainSelect(right);
                if (joinDerived != null) {
                    changed = applyTenantToPlainSelect(joinDerived, tenantId) || changed;
                    continue;
                }

                String joinTable = TenantSqlTableResolver.resolveMainTableName(right);
                if (joinTable == null || !TenantSqlTableResolver.needTenant(joinTable)) {
                    continue;
                }

                String joinQualifier = joinAliasOrTable(join);
                if (joinQualifier == null || joinQualifier.trim().isEmpty()) {
                    continue;
                }

                Expression joinCond = eqTenant(joinQualifier + "." + TENANT_COLUMN, tenantId);

                // ✅ 一次性修复重复 ON：只用 onExpression 输出 + onExpressions 强制 emptyList
                appendTenantToJoinOnCompat(join, joinCond);
                changed = true;
            }
        }

        return changed;
    }

    /**
     * ✅ 兼容 4.9：只保留一个 ON（使用 onExpression）
     * - onExpressions 强制为 emptyList（非 null）：避免 4.9 Join.toString() NPE
     * - onExpression 设置为 merged：确保只有一个 ON 输出
     */
    @SuppressWarnings("unchecked")
    private void appendTenantToJoinOnCompat(Join join, Expression tenantCond) {
        if (join == null || tenantCond == null) return;

        // 1) 取原始 ON：优先 onExpression；否则从 onExpressions 取第一个非空
        Expression baseOn = join.getOnExpression();

        if (baseOn == null) {
            Object onExprsObj = invokeNoArg(join, "getOnExpressions");
            if (onExprsObj instanceof List) {
                List<Expression> list = (List<Expression>) onExprsObj;
                if (list != null) {
                    for (Expression e : list) {
                        if (e != null) {
                            baseOn = e;
                            break;
                        }
                    }
                }
            }
        }

        // 原 SQL 没有 ON：不强行补（避免把 LEFT JOIN 语义改坏）
        if (baseOn == null) {
            // 但要避免 4.9 onExpressions==null 的 NPE：统一置 emptyList
            normalizeJoinOn(join, null);
            return;
        }

        // 已经带 tenant 条件则不重复追加
        if (containsTenant(baseOn)) {
            normalizeJoinOn(join, baseOn); // 顺手归一化，避免后续仍重复 ON
            return;
        }

        // 2) 合并 tenant 条件
        Expression merged = simplifyAnd(new AndExpression(baseOn, tenantCond));

        // 3) ✅ 一次性解决：强制 Join 只输出一个 ON
        normalizeJoinOn(join, merged);
    }

    /**
     * ✅ 强制归一化 Join 的 ON（一次性解决重复 ON）
     *
     * 思路：
     * 1) 先把 Join 对象里所有“可能承载 ON 的字段/集合”全部清空
     *    - Expression 字段名包含 on + expression -> 置 null
     *    - List 字段名包含 on + expression -> 置 emptyList（非 null，避免 4.9 NPE）
     * 2) 最终只设置一个 ON：onExpression = merged
     * 3) 再次兜底把所有 on*expression* 的 List 字段保持 empty，防止 toString 再输出第二个 ON
     */
    private void normalizeJoinOn(Join join, Expression merged) {
        if (join == null) return;

        final List<Expression> empty = Collections.emptyList();

        // 1) 强制清空 Join 内部所有 on*expression* 字段（防止残留 baseOn 导致 "ON ... ON ..."）
        try {
            Class<?> c = join.getClass();
            while (c != null && c != Object.class) {
                for (Field f : c.getDeclaredFields()) {
                    String n = (f.getName() == null) ? "" : f.getName().toLowerCase();
                    if (!(n.contains("on") && n.contains("expression"))) {
                        continue;
                    }
                    f.setAccessible(true);

                    // Expression 类字段清空
                    if (Expression.class.isAssignableFrom(f.getType())) {
                        f.set(join, null);
                        continue;
                    }

                    // List 类字段置 empty（非 null）
                    if (List.class.isAssignableFrom(f.getType())) {
                        f.set(join, empty);
                    }
                }
                c = c.getSuperclass();
            }
        } catch (Exception ignore) {
        }

        // 2) 只用 onExpression 输出最终 ON
        try {
            join.setOnExpression(merged);
        } catch (Exception ignore) {
        }

        // 3) 反射兜底：确保 onExpression 真正被写入
        try {
            Class<?> c = join.getClass();
            while (c != null && c != Object.class) {
                try {
                    Field f = c.getDeclaredField("onExpression");
                    f.setAccessible(true);
                    f.set(join, merged);
                    break;
                } catch (NoSuchFieldException e) {
                    c = c.getSuperclass();
                }
            }
        } catch (Exception ignore) {
        }

        // 4) 再兜底一次：所有 on*expression* 的 List 字段必须保持 emptyList（避免 toString 输出第二个 ON）
        try {
            Class<?> c = join.getClass();
            while (c != null && c != Object.class) {
                for (Field f : c.getDeclaredFields()) {
                    String n = (f.getName() == null) ? "" : f.getName().toLowerCase();
                    if (List.class.isAssignableFrom(f.getType())
                            && n.contains("on") && n.contains("expression")) {
                        f.setAccessible(true);
                        f.set(join, empty);
                    }
                }
                c = c.getSuperclass();
            }
        } catch (Exception ignore) {
        }

        // 5) 最后再用公开 API 调一次 setOnExpressions(emptyList)（有方法就用，没方法忽略）
        try {
            Method m = join.getClass().getMethod("setOnExpressions", List.class);
            m.invoke(join, empty);
        } catch (Exception ignore) {
        }
    }


    private Field findField(Class<?> clazz, String name) {
        Class<?> c = clazz;
        while (c != null && c != Object.class) {
            try {
                return c.getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            }
        }
        return null;
    }

    private boolean containsTenant(Expression exp) {
        if (exp == null) return false;
        String s = exp.toString().toLowerCase();
        return s.contains("." + TENANT_COLUMN) || s.contains(" " + TENANT_COLUMN + " ");
    }

    /**
     * 从 FromItem 中提取子查询内部的 PlainSelect（最常见的括号子查询）。
     *
     * JSqlParser 4.9+：子查询 FromItem 通常为 ParenthesedSelect。
     */
    private PlainSelect extractPlainSelect(FromItem fromItem) {
        if (fromItem == null) return null;

        if (fromItem instanceof ParenthesedSelect) {
            ParenthesedSelect ps = (ParenthesedSelect) fromItem;
            if (ps.getSelect() == null) return null;
            Object body = getSelectBodyCompat(ps.getSelect());
            return (body instanceof PlainSelect) ? (PlainSelect) body : null;
        }

        // 反射兜底
        try {
            Object selectObj = null;
            try {
                selectObj = fromItem.getClass().getMethod("getSelect").invoke(fromItem);
            } catch (NoSuchMethodException ignore) {
                try {
                    selectObj = fromItem.getClass().getMethod("getSubSelect").invoke(fromItem);
                } catch (NoSuchMethodException ignore2) {
                }
            }

            if (selectObj instanceof Select) {
                Object body = getSelectBodyCompat(selectObj);
                return (body instanceof PlainSelect) ? (PlainSelect) body : null;
            }
        } catch (Exception ignore) {
        }

        return null;
    }

    private EqualsTo eqTenant(String qualifiedColumn, Long tenantId) {
        EqualsTo eq = new EqualsTo();
        eq.setLeftExpression(new Column(qualifiedColumn));
        eq.setRightExpression(new LongValue(tenantId));
        return eq;
    }

    private Object getSelectBodyCompat(Object selectObj) {
        if (selectObj == null) return null;
        try {
            return selectObj.getClass().getMethod("getSelectBody").invoke(selectObj);
        } catch (Exception e) {
            return null;
        }
    }

    private Expression simplifyAnd(Expression exp) {
        if (exp instanceof AndExpression) {
            AndExpression and = (AndExpression) exp;
            if (and.getLeftExpression() == null) return and.getRightExpression();
        }
        return exp;
    }

    private String tableAliasOrTable(PlainSelect ps, String tableName) {
        if (ps.getFromItem() != null && ps.getFromItem().getAlias() != null) {
            return ps.getFromItem().getAlias().getName();
        }
        return tableName;
    }

    private String joinAliasOrTable(Join join) {
        if (join == null || join.getRightItem() == null) {
            return null;
        }
        FromItem right = join.getRightItem();
        if (right.getAlias() != null) {
            return right.getAlias().getName();
        }
        if (right instanceof Table) {
            return ((Table) right).getName();
        }
        return null;
    }

    private String updateAliasOrTable(Update update) {
        if (update.getTable() != null && update.getTable().getAlias() != null) {
            return update.getTable().getAlias().getName();
        }
        return update.getTable() != null ? update.getTable().getName() : "";
    }

    private String deleteAliasOrTable(Delete del) {
        if (del.getTable() != null && del.getTable().getAlias() != null) {
            return del.getTable().getAlias().getName();
        }
        return del.getTable() != null ? del.getTable().getName() : "";
    }

    // -------- Insert columns 兼容（4.9: ExpressionList<Column>）--------

    private boolean insertHasTenantColumn(Insert ins) {
        Object colsObj = invokeNoArg(ins, "getColumns");
        if (colsObj == null) return false;

        // 4.9: ExpressionList<Column> -> getExpressions()
        if (isExpressionList(colsObj)) {
            Object exprs = invokeNoArg(colsObj, "getExpressions");
            if (exprs instanceof List) {
                for (Object o : (List<?>) exprs) {
                    if (o instanceof Column) {
                        if (TENANT_COLUMN.equalsIgnoreCase(((Column) o).getColumnName())) return true;
                    }
                }
            }
            return false;
        }

        // 旧版: List<Column>
        if (colsObj instanceof List) {
            for (Object o : (List<?>) colsObj) {
                if (o instanceof Column) {
                    if (TENANT_COLUMN.equalsIgnoreCase(((Column) o).getColumnName())) return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean appendInsertTenantColumn(Insert ins) {
        Object colsObj = invokeNoArg(ins, "getColumns");
        if (colsObj == null) {
            return false;
        }

        // 4.9: ExpressionList<Column>
        if (isExpressionList(colsObj)) {
            Object exprs = invokeNoArg(colsObj, "getExpressions");
            if (exprs instanceof List) {
                List<Column> cols = new ArrayList<>((List<Column>) exprs);
                cols.add(new Column(TENANT_COLUMN));
                invokeSetter(colsObj, "setExpressions", List.class, cols);
                invokeSetter(ins, "setColumns", colsObj.getClass(), colsObj);
                return true;
            }
            return false;
        }

        // 旧版: List<Column>
        if (colsObj instanceof List) {
            List<Column> cols = new ArrayList<>((List<Column>) colsObj);
            cols.add(new Column(TENANT_COLUMN));
            invokeSetter(ins, "setColumns", List.class, cols);
            return true;
        }
        return false;
    }

    private boolean isExpressionList(Object obj) {
        return obj != null && "net.sf.jsqlparser.expression.operators.relational.ExpressionList"
                .equals(obj.getClass().getName());
    }

    // -------- reflection helpers --------

    private static Object invokeNoArg(Object target, String method) {
        try {
            Method m = target.getClass().getMethod(method);
            return m.invoke(target);
        } catch (Exception e) {
            return null;
        }
    }

    private static void invokeSetter(Object target, String method, Class<?> paramType, Object arg) {
        try {
            Method m = target.getClass().getMethod(method, paramType);
            m.invoke(target, arg);
        } catch (Exception ignore) {
        }
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * ✅ 修复 JSqlParser 4.9 Join.toString() 可能输出的重复 ON 语法：
     *
     * 错误形式（你现在看到的）：
     *   ... ON A AND t.tenant_id = 1 ON A ...
     *
     * 修复为：
     *   ... ON A AND t.tenant_id = 1 ...
     *
     * 规则：发现 "AND <alias>.tenant_id = <num> ON <something = something>"，
     * 直接删除后面的 "ON <something = something>"。
     */
    private String fixDuplicateOn(String sql) {
        if (sql == null || sql.isEmpty()) return sql;

        // (?i) 忽略大小写
        // 匹配：AND d.tenant_id = 1 ON u.dept_id = d.dept_id
        // 替换为：AND d.tenant_id = 1
        return sql.replaceAll(
                "(?i)(AND\\s+\\w+\\.tenant_id\\s*=\\s*\\d+)\\s+ON\\s+[\\w\\.]+\\s*=\\s*[\\w\\.]+",
                "$1"
        );
    }
}