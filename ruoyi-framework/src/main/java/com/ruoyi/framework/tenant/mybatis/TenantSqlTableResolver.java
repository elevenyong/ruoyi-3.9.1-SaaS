package com.ruoyi.framework.tenant.mybatis;


import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.schema.Table;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.framework.tenant.mybatis
 * @Project：RuoYi-Vue-master
 * @name：TenantSqlTableResolver
 * @Date：2026/2/6 16:05
 * @Filename：TenantSqlTableResolver
 * @Description
 */
public class TenantSqlTableResolver {


    /**
     * sys_ 表中必须隔离（租户私有：RBAC + 日志等）
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
     * sys_ 表中不隔离（平台级共享：字典/参数等）
     */
    private static final Set<String> SYS_IGNORE_TABLES = new HashSet<>(Arrays.asList(
            "sys_config",
            "sys_dict_type",
            "sys_menu",
            "sys_dict_data"
    ));
    public static String resolveMainTableName(FromItem fromItem) {
        if (fromItem == null) return null;
        if (fromItem instanceof Table) {
            return ((Table) fromItem).getName();
        }
        // 处理分页插件生成的子查询：FROM ( <origin-select> ) alias
        // JSqlParser 4.9+ 用 ParenthesedSelect 表示子查询 FromItem（不再是 SubSelect）。
        if (fromItem instanceof ParenthesedSelect) {
            ParenthesedSelect sub = (ParenthesedSelect) fromItem;
            if (sub.getSelect() != null && sub.getSelect().getSelectBody() instanceof PlainSelect) {
                PlainSelect ps = (PlainSelect) sub.getSelect().getSelectBody();
                if (ps.getFromItem() instanceof Table) {
                    return ((Table) ps.getFromItem()).getName();
                }
            }
        }
        if (fromItem instanceof TableFunction) {
            return null;
        }
        // 子查询等复杂情况，默认不处理
        return null;
    }


    /**
     * 规则：
     *  - t_%：必隔离
     *  - sys_ 白名单：隔离
     *  - sys_ 黑名单：不隔离
     *  - gen_% / qrtz_%：不隔离
     *  - 其它：默认不隔离（保守）
     */
    public static boolean needTenant(String tableName) {
        if (tableName == null) return false;

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
            // sys_ 未明确列入租户隔离白名单的，默认不隔离（保守，避免误伤系统表）
            return false;
        }

        return false;
    }

    /**
     * 在 SQL 解析失败时，用于“粗判”是否属于必须隔离的 SQL：
     *  - 命中 t_ 表
     *  - 或命中 sys 白名单中的租户表
     *
     * 说明：这是保守策略——宁可 fail-fast，也不要跨租户放过。
     */
    public static boolean sqlLikelyNeedTenant(String sql) {
        if (sql == null) return false;
        String s = sql.toLowerCase(Locale.ROOT);

        // t_ 表粗匹配（常见：from t_xxx / join t_xxx / update t_xxx / into t_xxx）
        if (s.contains(" from t_") || s.contains("\nfrom t_")
                || s.contains(" join t_") || s.contains("\njoin t_")
                || s.contains(" update t_") || s.contains("\nupdate t_")
                || s.contains(" into t_") || s.contains("\ninto t_")) {
            return true;
        }

        // sys 白名单表粗匹配
        for (String tbl : SYS_TENANT_TABLES) {
            if (s.contains(" " + tbl + " ") || s.contains(" " + tbl + "\n")
                    || s.contains(" from " + tbl) || s.contains(" join " + tbl)
                    || s.contains(" update " + tbl) || s.contains(" into " + tbl)) {
                return true;
            }
        }
        return false;
    }
}