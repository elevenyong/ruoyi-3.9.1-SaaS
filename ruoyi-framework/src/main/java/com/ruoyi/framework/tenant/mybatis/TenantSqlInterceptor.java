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
import java.util.List;
import java.util.Properties;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class TenantSqlInterceptor implements Interceptor {

    private static final String TENANT_COLUMN = "tenant_id";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget();

        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId == null || tenantId == 0L) {
            return invocation.proceed();
        }

        String originalSql = handler.getBoundSql().getSql();
        if (originalSql == null || originalSql.trim().isEmpty()) {
            return invocation.proceed();
        }

        try {
            String newSql = rewriteSql(originalSql, tenantId);
            if (newSql != null && !newSql.equals(originalSql)) {
                setField(handler.getBoundSql(), "sql", newSql);
            }
        } catch (Exception ex) {
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
            if (!TenantSqlTableResolver.needTenant(table)) {
                return sql;
            }

            Expression cond = eqTenant(updateAliasOrTable(update) + "." + TENANT_COLUMN, tenantId);
            Expression newWhere = (update.getWhere() == null) ? cond : new AndExpression(update.getWhere(), cond);
            update.setWhere(simplifyAnd(newWhere));
            return stmt.toString();
        }

        if (stmt instanceof Delete) {
            Delete del = (Delete) stmt;
            String table = (del.getTable() != null) ? del.getTable().getName() : null;
            if (!TenantSqlTableResolver.needTenant(table)) {
                return sql;
            }

            Expression cond = eqTenant(deleteAliasOrTable(del) + "." + TENANT_COLUMN, tenantId);
            Expression newWhere = (del.getWhere() == null) ? cond : new AndExpression(del.getWhere(), cond);
            del.setWhere(simplifyAnd(newWhere));
            return stmt.toString();
        }

        if (stmt instanceof Insert) {
            Insert ins = (Insert) stmt;
            String table = (ins.getTable() != null) ? ins.getTable().getName() : null;
            if (!TenantSqlTableResolver.needTenant(table)) {
                return sql;
            }

            if (ins.getSelect() != null && isRealInsertSelect(ins)) {
                return sql;
            }

            if (insertHasTenantColumn(ins)) {
                return sql;
            }

            if (appendInsertTenantColumn(ins)) {
                TenantSqlInsertValueAppender.appendTenantValue(ins, tenantId);
                return stmt.toString();
            }

            return sql;
        }

        return sql;
    }

    private String rewriteSelect(Select select, Long tenantId, String originalSql) {
        Object body = getSelectBodyCompat(select);
        if (body instanceof PlainSelect) {
            PlainSelect ps = (PlainSelect) body;
            boolean changed = applyTenantToPlainSelect(ps, tenantId);
            return changed ? select.toString() : originalSql;
        }
        return originalSql;
    }

    private boolean applyTenantToPlainSelect(PlainSelect ps, Long tenantId) {
        if (ps == null) {
            return false;
        }

        boolean changed = false;
        FromItem from = ps.getFromItem();

        // 先处理派生表：FROM (SELECT ...) table_count
        PlainSelect derived = extractPlainSelect(from);
        if (derived != null) {
            changed = applyTenantToPlainSelect(derived, tenantId) || changed;
            return changed;
        }

        String mainTable = TenantSqlTableResolver.resolveMainTableName(from);

        // 主表条件追加到 WHERE
        if (mainTable != null && TenantSqlTableResolver.needTenant(mainTable)) {
            String qualifier = tableAliasOrTable(ps, mainTable);
            Expression cond = eqTenant(qualifier + "." + TENANT_COLUMN, tenantId);
            Expression newWhere = (ps.getWhere() == null) ? cond : new AndExpression(ps.getWhere(), cond);
            ps.setWhere(simplifyAnd(newWhere));
            changed = true;
        }

        // JOIN 表条件追加到 ON，不能破坏 LEFT JOIN 语义
        List<Join> joins = ps.getJoins();
        if (joins != null && !joins.isEmpty()) {
            for (Join join : joins) {
                FromItem right = join.getRightItem();
                if (right == null) {
                    continue;
                }

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
                appendTenantToJoinOnCompat(join, joinCond);
                changed = true;
            }
        }

        return changed;
    }

    private PlainSelect extractPlainSelect(FromItem fromItem) {
        if (!(fromItem instanceof ParenthesedSelect)) {
            return null;
        }
        ParenthesedSelect ps = (ParenthesedSelect) fromItem;
        if (ps.getSelect() == null) {
            return null;
        }
        Object body = getSelectBodyCompat(ps.getSelect());
        return (body instanceof PlainSelect) ? (PlainSelect) body : null;
    }

    private Object getSelectBodyCompat(Object selectObj) {
        if (selectObj == null) {
            return null;
        }
        try {
            Method m = selectObj.getClass().getMethod("getSelectBody");
            return m.invoke(selectObj);
        } catch (Exception ignore) {
        }
        try {
            Method m = selectObj.getClass().getMethod("getPlainSelect");
            return m.invoke(selectObj);
        } catch (Exception ignore) {
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void appendTenantToJoinOnCompat(Join join, Expression tenantCond) {
        if (join == null || tenantCond == null) {
            return;
        }
        // 1）优先处理当前版本真正用于输出的 onExpressions
        Object onExprsObj = invokeNoArg(join, "getOnExpressions");
        if (onExprsObj instanceof List) {
            List<Expression> list = (List<Expression>) onExprsObj;
            if (list != null && !list.isEmpty()) {
                Expression base = null;
                for (Expression e : list) {
                    if (e != null) {
                        if (containsTenant(e)) {
                            return;
                        }
                        base = (base == null) ? e : simplifyAnd(new AndExpression(base, e));
                    }
                }
                if (base != null) {
                    Expression merged = simplifyAnd(new AndExpression(base, tenantCond));
                    list.clear();
                    list.add(merged);
                    try {
                        Method m = join.getClass().getMethod("setOnExpressions", List.class);
                        m.invoke(join, list);
                    } catch (Exception ignore) {
                    }
                    // 非常关键：不要再保留 onExpression，否则 toString() 可能输出双 ON
                    try {
                        Field f = join.getClass().getDeclaredField("onExpression");
                        f.setAccessible(true);
                        f.set(join, null);
                    } catch (Exception ignore) {
                    }
                    return;
                }
            }
        }
        // 2）兜底：如果没有 onExpressions，再尝试旧式 onExpression
        Expression onExpression = null;
        try {
            onExpression = join.getOnExpression();
        } catch (Exception ignore) {
        }
        if (onExpression != null) {
            if (containsTenant(onExpression)) {
                return;
            }
            Expression merged = simplifyAnd(new AndExpression(onExpression, tenantCond));
            // 尽量把它转换成 onExpressions 统一输出，避免双 ON
            try {
                List<Expression> singleton = new ArrayList<>();
                singleton.add(merged);
                Method m = join.getClass().getMethod("setOnExpressions", List.class);
                m.invoke(join, singleton);
                Field f = join.getClass().getDeclaredField("onExpression");
                f.setAccessible(true);
                f.set(join, null);
                return;
            } catch (Exception ignore) {
            }
            // 最后兜底：实在不行才用 onExpression
            try {
                join.setOnExpression(merged);
            } catch (Exception ignore) {
            }
        }
    }

    private boolean containsTenant(Expression expr) {
        if (expr == null) {
            return false;
        }
        String s = expr.toString().toLowerCase();
        return s.contains("." + TENANT_COLUMN.toLowerCase()) || s.contains(" " + TENANT_COLUMN.toLowerCase());
    }

    private Expression eqTenant(String qualifiedColumn, Long tenantId) {
        EqualsTo eq = new EqualsTo();
        eq.setLeftExpression(new Column(qualifiedColumn));
        eq.setRightExpression(new LongValue(tenantId));
        return eq;
    }

    private String tableAliasOrTable(PlainSelect ps, String tableName) {
        FromItem from = ps.getFromItem();
        if (from instanceof Table) {
            Table t = (Table) from;
            if (t.getAlias() != null && t.getAlias().getName() != null && !t.getAlias().getName().trim().isEmpty()) {
                return t.getAlias().getName();
            }
            return tableName;
        }
        if (from != null) {
            try {
                Method getAlias = from.getClass().getMethod("getAlias");
                Object alias = getAlias.invoke(from);
                if (alias != null) {
                    Method getName = alias.getClass().getMethod("getName");
                    Object name = getName.invoke(alias);
                    if (name != null && !String.valueOf(name).trim().isEmpty()) {
                        return String.valueOf(name);
                    }
                }
            } catch (Exception ignore) {
            }
        }
        return tableName;
    }

    private String joinAliasOrTable(Join join) {
        FromItem right = join.getRightItem();
        if (right instanceof Table) {
            Table t = (Table) right;
            if (t.getAlias() != null && t.getAlias().getName() != null && !t.getAlias().getName().trim().isEmpty()) {
                return t.getAlias().getName();
            }
            return t.getName();
        }
        if (right != null) {
            try {
                Method getAlias = right.getClass().getMethod("getAlias");
                Object alias = getAlias.invoke(right);
                if (alias != null) {
                    Method getName = alias.getClass().getMethod("getName");
                    Object name = getName.invoke(alias);
                    if (name != null && !String.valueOf(name).trim().isEmpty()) {
                        return String.valueOf(name);
                    }
                }
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    private String updateAliasOrTable(Update update) {
        Table t = update.getTable();
        if (t.getAlias() != null && t.getAlias().getName() != null && !t.getAlias().getName().trim().isEmpty()) {
            return t.getAlias().getName();
        }
        return t.getName();
    }

    private String deleteAliasOrTable(Delete delete) {
        Table t = delete.getTable();
        if (t.getAlias() != null && t.getAlias().getName() != null && !t.getAlias().getName().trim().isEmpty()) {
            return t.getAlias().getName();
        }
        return t.getName();
    }

    private Expression simplifyAnd(Expression expr) {
        if (!(expr instanceof AndExpression)) {
            return expr;
        }
        List<Expression> flat = new ArrayList<>();
        flattenAnd(expr, flat);
        Expression ret = flat.get(0);
        for (int i = 1; i < flat.size(); i++) {
            ret = new AndExpression(ret, flat.get(i));
        }
        return ret;
    }

    private void flattenAnd(Expression e, List<Expression> out) {
        if (e instanceof AndExpression) {
            AndExpression a = (AndExpression) e;
            flattenAnd(a.getLeftExpression(), out);
            flattenAnd(a.getRightExpression(), out);
        } else {
            out.add(e);
        }
    }

    private void setField(Object obj, String fieldName, Object value) {
        if (obj == null) {
            return;
        }
        Class<?> c = obj.getClass();
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                f.set(obj, value);
                return;
            } catch (NoSuchFieldException e) {
                c = c.getSuperclass();
            } catch (Exception e) {
                return;
            }
        }
    }

    private boolean insertHasTenantColumn(Insert ins) {
        Object colsObj = ins.getColumns();
        if (colsObj == null) {
            return false;
        }

        if (colsObj instanceof List) {
            List<?> list = (List<?>) colsObj;
            for (Object o : list) {
                if (o instanceof Column) {
                    String name = ((Column) o).getColumnName();
                    if (TENANT_COLUMN.equalsIgnoreCase(name)) {
                        return true;
                    }
                }
            }
            return false;
        }

        try {
            Method getExprs = colsObj.getClass().getMethod("getExpressions");
            Object exprs = getExprs.invoke(colsObj);
            if (exprs instanceof List) {
                for (Object o : (List<?>) exprs) {
                    if (o instanceof Column) {
                        String name = ((Column) o).getColumnName();
                        if (TENANT_COLUMN.equalsIgnoreCase(name)) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception ignore) {
        }
        return false;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private boolean appendInsertTenantColumn(Insert ins) {
        Object colsObj = ins.getColumns();
        if (colsObj == null) {
            return false;
        }

        if (colsObj instanceof List) {
            ((List) colsObj).add(new Column(TENANT_COLUMN));
            return true;
        }

        try {
            Method getExprs = colsObj.getClass().getMethod("getExpressions");
            Object exprs = getExprs.invoke(colsObj);
            if (exprs instanceof List) {
                ((List) exprs).add(new Column(TENANT_COLUMN));
                return true;
            }
        } catch (Exception ignore) {
        }
        return false;
    }

    private boolean isRealInsertSelect(Insert ins) {
        try {
            Object selectObj = ins.getSelect();
            if (selectObj == null) {
                return false;
            }

            Object selectBody = invokeNoArg(selectObj, "getSelectBody");
            if (selectBody != null) {
                Object fromItem = invokeNoArg(selectBody, "getFromItem");
                if (fromItem != null) {
                    return true;
                }
                Object joins = invokeNoArg(selectBody, "getJoins");
                if (joins instanceof List && !((List<?>) joins).isEmpty()) {
                    return true;
                }

                String bodyClass = selectBody.getClass().getName();
                if (bodyClass.contains("SetOperation") || bodyClass.contains("WithItem")) {
                    return true;
                }
                return false;
            }

            Object fromItem2 = invokeNoArg(selectObj, "getFromItem");
            if (fromItem2 != null) {
                return true;
            }
            Object joins2 = invokeNoArg(selectObj, "getJoins");
            if (joins2 instanceof List && !((List<?>) joins2).isEmpty()) {
                return true;
            }

            return true;
        } catch (Exception e) {
            return true;
        }
    }

    private Object invokeNoArg(Object obj, String methodName) {
        if (obj == null) {
            return null;
        }
        try {
            Method m = obj.getClass().getMethod(methodName);
            return m.invoke(obj);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // no-op
    }
}