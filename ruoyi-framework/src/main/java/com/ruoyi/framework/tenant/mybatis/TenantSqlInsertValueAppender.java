package com.ruoyi.framework.tenant.mybatis;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.insert.Insert;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * INSERT 语句补 tenant_id 的 values 值（兼容 JSqlParser 多版本 + Java8）
 *
 * 说明：
 * - 某些 JSqlParser 版本会把 VALUES(...)（含 NOW() 等函数）解析到 insert.getSelect() 里，
 *   但这并不意味着是真正的 "insert ... select ... from ..."。
 * - 因此：只跳过“真 insert-select（存在 FROM/JOIN 等来源）”，其它情况继续补 values。
 */
public class TenantSqlInsertValueAppender {

    private TenantSqlInsertValueAppender() {}

    public static void appendTenantValue(Insert insert, Long tenantId) {
        if (insert == null || tenantId == null) return;

        // ✅ 只跳过真正的 insert-select（insert into ... select ... from ...）
        // 注意：某些版本会把 VALUES(...)（含 NOW()）解析到 insert.getSelect() 里
        if (insert.getSelect() != null && isRealInsertSelect(insert)) {
            return;
        }

        // 1) JSqlParser 4.9：优先处理 insert.getValues()
        Object values = invokeNoArg(insert, "getValues");
        if (values != null) {
            if (appendToValues(values, tenantId)) {
                return;
            }
        }

        // 2) 旧版本：反射尝试 getItemsList()
        Object items = invokeNoArg(insert, "getItemsList");
        if (items == null) return;

        // 2.1) 单行：values(...)
        if (items instanceof ExpressionList) {
            ExpressionList el = (ExpressionList) items;
            el.setExpressions(append(el.getExpressions(), tenantId));
            return;
        }

        // 2.2) 批量：values(...),(...)
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

        // 2.3) 某些版本 ItemsList 包装层不同：反射兜底（不抛异常，避免影响系统）
        try {
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
    }

    /**
     * 兼容 4.9 的 values(...) / values(...),(...) 结构。
     * 通过反射拿到 expressions / expressionLists 等字段。
     */
    private static boolean appendToValues(Object values, Long tenantId) {
        // 4.9 常见：values.getExpressions() -> List<Expression> （单行）
        Object exprsObj = invokeNoArg(values, "getExpressions");
        if (exprsObj instanceof List) {
            @SuppressWarnings("unchecked")
            List<Object> list = (List<Object>) exprsObj;
            if (list.isEmpty() || list.get(0) instanceof Expression) {
                @SuppressWarnings("unchecked")
                List<Expression> exprs = (List<Expression>) exprsObj;
                // ✅ 追加常量 tenantId（不改变 MyBatis 参数数量，最稳）
                exprs.add(new LongValue(tenantId));
                return true;
            }
        }

        // 某些实现：values.getExpressionsList() / getExpressionLists() -> List<ExpressionList>
        Object listsObj = invokeNoArg(values, "getExpressionsList");
        if (!(listsObj instanceof List)) {
            listsObj = invokeNoArg(values, "getExpressionLists");
        }
        if (listsObj instanceof List) {
            @SuppressWarnings("unchecked")
            List<Object> lists = (List<Object>) listsObj;
            boolean touched = false;
            for (Object o : lists) {
                if (o instanceof ExpressionList) {
                    ExpressionList el = (ExpressionList) o;
                    el.setExpressions(append(el.getExpressions(), tenantId));
                    touched = true;
                } else if (o instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Expression> exprs = (List<Expression>) o;
                    exprs.add(new LongValue(tenantId));
                    touched = true;
                }
            }
            return touched;
        }

        return false;
    }

    private static List<Expression> append(List<Expression> expressions, Long tenantId) {
        List<Expression> copy = (expressions == null) ? new ArrayList<Expression>() : new ArrayList<Expression>(expressions);
        copy.add(new LongValue(tenantId));
        return copy;
    }

    private static Object invokeNoArg(Object target, String method) {
        try {
            return target.getClass().getMethod(method).invoke(target);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断是否是真正的 Insert-Select（insert into t (...) select ... from ...）
     * 兼容不同版本 JSqlParser：通过反射探测 selectBody/fromItem/joins
     */
    private static boolean isRealInsertSelect(Insert ins) {
        try {
            Object selectObj = ins.getSelect();
            if (selectObj == null) return false;

            Object selectBody = invokeNoArg(selectObj, "getSelectBody");
            if (selectBody != null) {
                Object fromItem = invokeNoArg(selectBody, "getFromItem");
                if (fromItem != null) return true;

                Object joins = invokeNoArg(selectBody, "getJoins");
                if (joins instanceof List && !((List<?>) joins).isEmpty()) return true;

                String bodyClass = selectBody.getClass().getName();
                if (bodyClass.contains("SetOperation") || bodyClass.contains("WithItem")) return true;

                // 没有 FROM/JOIN：多数是表达式列表（等价 VALUES）
                return false;
            }

            Object fromItem2 = invokeNoArg(selectObj, "getFromItem");
            if (fromItem2 != null) return true;

            Object joins2 = invokeNoArg(selectObj, "getJoins");
            if (joins2 instanceof List && !((List<?>) joins2).isEmpty()) return true;

            // 判断不了：保守当 insert-select
            return true;
        } catch (Exception e) {
            return true;
        }
    }
}