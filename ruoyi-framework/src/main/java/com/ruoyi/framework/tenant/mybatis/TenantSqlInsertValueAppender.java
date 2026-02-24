package com.ruoyi.framework.tenant.mybatis;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.insert.Insert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.framework.tenant.mybatis
 * @Project：RuoYi-Vue-master
 * @name：TenantSqlInsertValueAppender
 * @Date：2026/2/6 16:06
 * @Filename：TenantSqlInsertValueAppender
 * @Description
 */
/**
 * INSERT 语句补 tenant_id 的 values 值
 *
 * 仅处理最常见的：
 *   insert into t(a,b) values (1,2)
 *   insert into t(a,b) values (?,?)
 *
 * 不处理：
 *   insert into t(a,b) values (..),(..)
 *   insert into t(a,b) select ...
 *   以及各种复杂 ItemsList
 *
 * 对于不处理的 SQL，建议你在 XML/注解里显式写 tenant_id。
 */
public class TenantSqlInsertValueAppender {

    private TenantSqlInsertValueAppender() {}

    public static void appendTenantValue(Insert insert, Long tenantId) {
        if (insert == null || tenantId == null) return;

        Object items = insert.getItemsList();
        if (items == null) return;

        // 1) 单行：values(...)
        if (items instanceof ExpressionList) {
            ExpressionList el = (ExpressionList) items;
            el.setExpressions(append(el.getExpressions(), tenantId));
            return;
        }

        // 2) 批量：values(...),(...)
        // JSqlParser 常见类型：net.sf.jsqlparser.expression.operators.relational.MultiExpressionList
        try {
            Class<?> multiClz = Class.forName("net.sf.jsqlparser.expression.operators.relational.MultiExpressionList");
            if (multiClz.isInstance(items)) {
                Method getExprLists = multiClz.getMethod("getExprList");
                @SuppressWarnings("unchecked")
                List<ExpressionList> lists = (List<ExpressionList>) getExprLists.invoke(items);
                if (lists != null) {
                    for (ExpressionList el : lists) {
                        if (el != null) {
                            el.setExpressions(append(el.getExpressions(), tenantId));
                        }
                    }
                }
                return;
            }
        } catch (Exception ignore) {
            // continue fallback
        }

        // 3) 某些版本 ItemsList 包装层不同：做反射兜底（不抛异常，避免影响系统）
        try {
            // 例如：items.getMultiExpressionList()
            Method m = items.getClass().getMethod("getMultiExpressionList");
            Object multi = m.invoke(items);
            if (multi != null) {
                Class<?> multiClz = Class.forName("net.sf.jsqlparser.expression.operators.relational.MultiExpressionList");
                if (multiClz.isInstance(multi)) {
                    Method getExprLists = multiClz.getMethod("getExprList");
                    @SuppressWarnings("unchecked")
                    List<ExpressionList> lists = (List<ExpressionList>) getExprLists.invoke(multi);
                    if (lists != null) {
                        for (ExpressionList el : lists) {
                            if (el != null) {
                                el.setExpressions(append(el.getExpressions(), tenantId));
                            }
                        }
                    }
                }
            }
        } catch (Exception ignore) {
            // ignore
        }

        // 4) insert ... select ... 不处理（避免误改 SQL）
        if (insert.getSelect() != null) return;
    }
    private static List<Expression> append(List<Expression> expressions, Long tenantId) {
        List<Expression> copy = (expressions == null) ? new ArrayList<>() : new ArrayList<>(expressions);
        copy.add(new LongValue(tenantId));
        return copy;
    }

}