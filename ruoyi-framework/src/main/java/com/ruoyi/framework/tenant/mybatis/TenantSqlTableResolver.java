package com.ruoyi.framework.tenant.mybatis;


import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.schema.Table;

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
    public static String resolveMainTableName(FromItem fromItem) {
        if (fromItem == null) return null;
        if (fromItem instanceof Table) {
            return ((Table) fromItem).getName();
        }
        if (fromItem instanceof TableFunction) {
            return null;
        }
        // 子查询等复杂情况，默认不处理
        return null;
    }
}