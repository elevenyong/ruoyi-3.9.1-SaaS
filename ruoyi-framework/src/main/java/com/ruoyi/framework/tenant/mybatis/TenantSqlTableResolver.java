package com.ruoyi.framework.tenant.mybatis;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.TableFunction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class TenantSqlTableResolver {

    /**
     * sys_ 表中必须隔离（租户私有）
     */
    private static final Set<String> SYS_TENANT_TABLES = new HashSet<>(Arrays.asList(
            "sys_user",
            "sys_role",
            "sys_dept",
            "sys_post",
            "sys_user_role",
            "sys_role_menu",
            "sys_user_post",
            "sys_role_dept",
            "sys_oper_log",
            "sys_logininfor",
            "sys_notice"
    ));

    /**
     * sys_ 表中不隔离（平台级共享）
     */
    private static final Set<String> SYS_IGNORE_TABLES = new HashSet<>(Arrays.asList(
            "sys_config",
            "sys_dict_type",
            "sys_menu",
            "sys_dict_data"
    ));

    public static String resolveMainTableName(FromItem fromItem) {
        if (fromItem == null) {
            return null;
        }
        if (fromItem instanceof Table) {
            return ((Table) fromItem).getName();
        }
        // 派生表 / 子查询（如 FROM (SELECT ...) table_count）不在这里解析主表名，
        // 统一交给 TenantSqlInterceptor 下钻处理，避免错误拼出 table_count.tenant_id
        if (fromItem instanceof ParenthesedSelect) {
            return null;
        }
        if (fromItem instanceof TableFunction) {
            return null;
        }
        return null;
    }

    /**
     * 规则：
     *  - t_%：必隔离
     *  - sys_ 白名单：隔离
     *  - sys_ 黑名单：不隔离
     *  - gen_% / qrtz_%：不隔离
     *  - 其它：默认不隔离
     */
    public static boolean needTenant(String tableName) {
        if (tableName == null) {
            return false;
        }

        String t = tableName.toLowerCase(Locale.ROOT);

        if (t.startsWith("t_")) {
            return true;
        }

        if (t.startsWith("gen_")) {
            return false;
        }
        if (t.startsWith("qrtz_")) {
            return false;
        }

        if (t.startsWith("sys_")) {
            if (SYS_TENANT_TABLES.contains(t)) {
                return true;
            }
            if (SYS_IGNORE_TABLES.contains(t)) {
                return false;
            }
            return false;
        }

        return false;
    }

    /**
     * SQL 解析失败时，粗判是否属于必须隔离的 SQL
     */
    public static boolean sqlLikelyNeedTenant(String sql) {
        if (sql == null) {
            return false;
        }
        String s = sql.toLowerCase(Locale.ROOT);

        if (s.contains(" from t_") || s.contains("\nfrom t_")
                || s.contains(" join t_") || s.contains("\njoin t_")
                || s.contains(" update t_") || s.contains("\nupdate t_")
                || s.contains(" into t_") || s.contains("\ninto t_")) {
            return true;
        }

        for (String tbl : SYS_TENANT_TABLES) {
            if (s.contains(" " + tbl + " ")
                    || s.contains(" " + tbl + "\n")
                    || s.contains(" from " + tbl)
                    || s.contains(" join " + tbl)
                    || s.contains(" update " + tbl)
                    || s.contains(" into " + tbl)) {
                return true;
            }
        }
        return false;
    }
}