package com.ruoyi.framework.tenant;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.framework.tenant
 * @Project：RuoYi-Vue-master
 * @name：TenantService
 * @Date：2026/2/6 15:50
 * @Filename：TenantService
 * @Description
 */
public interface TenantService {
    Long getTenantIdByCode(String tenantCode);
}