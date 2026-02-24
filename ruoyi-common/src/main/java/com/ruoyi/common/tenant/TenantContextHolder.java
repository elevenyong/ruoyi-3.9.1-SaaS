package com.ruoyi.common.tenant;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.common.tenant
 * @Project：RuoYi-Vue-master
 * @name：TenantContextHolder
 * @Date：2026/2/6 15:49
 * @Filename：TenantContextHolder
 * @Description
 */
public class TenantContextHolder {
    private static final ThreadLocal<Long> TENANT_ID = new ThreadLocal<>();

    public static void setTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    public static void clear() {
        TENANT_ID.remove();
    }
}