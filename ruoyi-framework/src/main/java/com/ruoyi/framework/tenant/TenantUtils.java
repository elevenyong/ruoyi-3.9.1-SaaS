package com.ruoyi.framework.tenant;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.framework.tenant
 * @Project：RuoYi-Vue-master
 * @name：TenantUtils
 * @Date：2026/3/3 11:24
 * @Filename：TenantUtils
 * @Description
 */

import com.ruoyi.common.utils.StringUtils;

/**
 * 租户工具类：判断是否平台租户（平台超级管理员租户）
 */
public class TenantUtils {

    /** 平台租户 tenant_id */
    public static final Long PLATFORM_TENANT_ID = 1L;

    /** 平台租户 tenant_code */
    public static final String PLATFORM_TENANT_CODE = "default";

    /**
     * 是否平台租户
     */
    public static boolean isPlatformTenant(Long tenantId, String tenantCode) {
        return tenantId != null
                && PLATFORM_TENANT_ID.equals(tenantId)
                && StringUtils.equals(PLATFORM_TENANT_CODE, tenantCode);
    }

    /**
     * 是否平台租户（只按 tenant_id 判断）
     */
    public static boolean isPlatformTenant(Long tenantId) {
        return tenantId != null && PLATFORM_TENANT_ID.equals(tenantId);
    }
}