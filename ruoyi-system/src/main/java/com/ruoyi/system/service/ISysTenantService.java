package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysTenant;
import com.ruoyi.system.domain.vo.TenantCreateReq;


/**
 * @Author：zhangHY
 * @Package：com.ruoyi.system.service
 * @Project：RuoYi-Vue-master
 * @name：ISysTenantService
 * @Date：2026/2/10 14:08
 * @Filename：ISysTenantService
 * @Description 租户 业务层
 */
public interface ISysTenantService
{
    List<SysTenant> selectTenantList(SysTenant tenant);

    SysTenant selectTenantByCode(String tenantCode);

    /**
     * 平台创建租户，并初始化该租户管理员（方案1）
     *
     * @return 新租户 tenantId
     */
    Long createTenantWithAdmin(TenantCreateReq req);
    /**
     * 初始化（或重新初始化）指定租户的基础数据。
     * 典型用途：租户创建后补齐字典/参数/租户管理员角色菜单等。
     *
     * @param tenantId         目标租户ID
     * @param templateTenantId 模板租户ID（一般为 default 租户的 tenantId=1）
     */
    void initTenantBase(Long tenantId, Long templateTenantId);
    /**
     * 重置指定租户的管理员密码（建议仅平台租户可用）。
     *
     * @param tenantId       目标租户ID
     * @param adminUserName  租户管理员用户名
     * @param newPassword    新密码（明文）
     */
    void resetTenantAdminPassword(Long tenantId, String adminUserName, String newPassword);

    int updateTenantStatus(Long tenantId, String status);

    SysTenant selectTenantById(Long tenantId);

    int updateTenant(SysTenant tenant);

    int deleteTenantByIds(Long[] tenantIds);
}