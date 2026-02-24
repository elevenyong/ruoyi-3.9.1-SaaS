package com.ruoyi.framework.tenant.mybatis;

import com.ruoyi.common.tenant.TenantContextHolder;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.framework.tenant.mybatis
 * @Project：RuoYi-Vue-master
 * @name：TenantSqlInterceptor
 * @Date：2026/2/6 16:05
 * @Filename：TenantSqlInterceptor
 * @Description
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})
})
public class TenantSqlInterceptor implements Interceptor {

    private static final String TENANT_COLUMN = "tenant_id";

    /** 不做租户隔离的表（全局表/日志表/菜单表/定时任务/代码生成等） */
    private static final Set<String> IGNORE_TABLES = new HashSet<>(Arrays.asList(
            "sys_tenant",
            "sys_menu",
            "sys_oper_log",
            "sys_logininfor",
            // 代码生成（通常为全局配置，不做租户隔离）
            "gen_table",
            "gen_table_column",
            // 定时任务（通常为全局配置，不做租户隔离）
            "sys_job",
            "sys_job_log"
    ));

    /** 简单缓存：sql->改写结果（避免每次都 parse；注意：不同 tenantId 仍需重新写 where 值，所以这里只缓存结构不太适合；这里干脆不做复杂缓存） */
    private static final Map<String, Boolean> PARSE_FAIL_CACHE = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler handler = (StatementHandler) invocation.getTarget();

        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId == null ) {
            return invocation.proceed();
        }

        String originalSql = handler.getBoundSql().getSql();
        if (originalSql == null) {
            return invocation.proceed();
        }

        // 解析失败的 SQL 直接放过（避免持续报错）
        if (Boolean.TRUE.equals(PARSE_FAIL_CACHE.get(originalSql))) {
            return invocation.proceed();
        }

        try {
            String newSql = rewriteSql(originalSql, tenantId);
            if (newSql != null && !newSql.equals(originalSql)) {
                setField(handler.getBoundSql(), "sql", newSql);
            }
        } catch (Exception ex) {
            PARSE_FAIL_CACHE.put(originalSql, true);
            // 为了不影响系统运行，这里选择放过；你也可以改成抛异常强制修复 SQL
            return invocation.proceed();
        }

        return invocation.proceed();
    }

    private String rewriteSql(String sql, Long tenantId) throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse(sql);

        if (stmt instanceof Select) {
            Select select = (Select) stmt;
            String rewritten = rewriteSelect(select, tenantId, sql);
            return rewritten != null ? rewritten : sql;
//            if (select.getSelectBody() instanceof PlainSelect) {
//                PlainSelect ps = (PlainSelect) select.getSelectBody();
//                String table = TenantSqlTableResolver.resolveMainTableName(ps.getFromItem());
//                if (ignoreTable(table)) return sql;
//
//                Expression tenantExp = new AndExpression(
//                        ps.getWhere(),
//                        new EqualsTo(new Column(tableAliasOrTable(ps, table) + "." + TENANT_COLUMN), new LongValue(tenantId))
//                );
//                ps.setWhere(simplifyAnd(tenantExp));
//                return stmt.toString();
//            }
//            return sql;
        }

        if (stmt instanceof Update) {
            Update update = (Update) stmt;
            String table = update.getTable() != null ? update.getTable().getName() : null;
            if (ignoreTable(table)) return sql;

            Expression tenantExp = new AndExpression(
                    update.getWhere(),
                    new net.sf.jsqlparser.expression.operators.relational.EqualsTo(
                            new Column(TENANT_COLUMN),
                            new LongValue(tenantId)
                    )
            );
            update.setWhere(simplifyAnd(tenantExp));
            return stmt.toString();
        }

        if (stmt instanceof Delete) {
            Delete del = (Delete) stmt;
            String table = del.getTable() != null ? del.getTable().getName() : null;
            if (ignoreTable(table)) return sql;

            Expression tenantExp = new AndExpression(
                    del.getWhere(),
                    new net.sf.jsqlparser.expression.operators.relational.EqualsTo(
                            new Column(TENANT_COLUMN),
                            new LongValue(tenantId)
                    )
            );
            del.setWhere(simplifyAnd(tenantExp));
            return stmt.toString();
        }

        if (stmt instanceof Insert) {
            Insert ins = (Insert) stmt;
            String table = ins.getTable() != null ? ins.getTable().getName() : null;
            if (ignoreTable(table)) return sql;

            // 如果 insert 已经显式写了 tenant_id，则不处理
            if (ins.getColumns() != null) {
                for (Column c : ins.getColumns()) {
                    if (TENANT_COLUMN.equalsIgnoreCase(c.getColumnName())) {
                        return sql;
                    }
                }
            }

            // 补 tenant_id 列和值（只处理最常见 insert into t(a,b) values(?,?) 形式）
            if (ins.getColumns() != null && ins.getItemsList() != null) {
                List<Column> cols = new ArrayList<>(ins.getColumns());
                cols.add(new Column(TENANT_COLUMN));
                ins.setColumns(cols);

                TenantSqlInsertValueAppender.appendTenantValue(ins, tenantId);
                return stmt.toString();
            }
            return sql;
        }

        return sql;
    }

    /**
     * 处理 SELECT：
     * 1) 普通查询：在主表(where)追加 tenant_id
     * 2) PageHelper 自动 count：形如 SELECT count(0) FROM ( <origin-select> ) table_count
     *    外层 fromItem 为子查询(SubSelect)，此时不能在 table_count 上拼 tenant_id，
     *    必须下钻到子查询内部的主表去拼 tenant_id。
     */
    private String rewriteSelect(Select select, Long tenantId, String originalSql) {
        if (select.getSelectBody() instanceof PlainSelect) {
            PlainSelect ps = (PlainSelect) select.getSelectBody();
            boolean changed = applyTenantToPlainSelect(ps, tenantId);
            return changed ? select.toString() : originalSql;
        }
        return originalSql;
    }

    private boolean applyTenantToPlainSelect(PlainSelect ps, Long tenantId) {
        if (ps == null) return false;

        FromItem from = ps.getFromItem();
        String table = TenantSqlTableResolver.resolveMainTableName(from);

        // PageHelper count / 派生表：FROM ( ... ) table_count
        if (table == null && from instanceof SubSelect) {
            SubSelect sub = (SubSelect) from;
            if (sub.getSelectBody() instanceof PlainSelect) {
                return applyTenantToPlainSelect((PlainSelect) sub.getSelectBody(), tenantId);
            }
            return false;
        }

        // 复杂 SQL（例如 from 不是 table / subselect），暂不改写
        if (table == null) {
            return false;
        }

        if (ignoreTable(table)) return false;

        String qualifier = tableAliasOrTable(ps, table);
        Expression cond = new EqualsTo(new Column(qualifier + "." + TENANT_COLUMN), new LongValue(tenantId));
        Expression newWhere = ps.getWhere() == null ? cond : new AndExpression(ps.getWhere(), cond);
        ps.setWhere(simplifyAnd(newWhere));
        return true;
    }


    private boolean ignoreTable(String tableName) {
        if (tableName == null) return false;
        return IGNORE_TABLES.contains(tableName.toLowerCase(Locale.ROOT));
    }

    private Expression simplifyAnd(Expression exp) {
        // AndExpression(null, X) 这种情况处理一下
        if (exp instanceof AndExpression) {
            AndExpression and = (AndExpression) exp;
            if (and.getLeftExpression() == null) return and.getRightExpression();
        }
        return exp;
    }

    private String tableAliasOrTable(PlainSelect ps, String tableName) {
        // 如果 from 有别名，用别名；否则用表名
        if (ps.getFromItem() != null && ps.getFromItem().getAlias() != null) {
            return ps.getFromItem().getAlias().getName();
        }
        return tableName;
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
}