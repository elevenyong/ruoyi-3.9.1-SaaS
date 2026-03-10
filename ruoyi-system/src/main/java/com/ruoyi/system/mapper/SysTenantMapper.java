package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysTenant;
import org.apache.ibatis.annotations.Param;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.system.mapper
 * @Project：RuoYi-Vue-master
 * @name：SysTenantMapper
 * @Date：2026/2/10 14:07
 * @Filename：SysTenantMapper
 * @Description 租户表 数据层
 */
public interface SysTenantMapper
{
    /**
     * 查询租户列表
     */
    List<SysTenant> selectTenantList(SysTenant tenant);

    /**
     * 根据 tenantCode 查询（未删除）
     */
    SysTenant selectTenantByCode(String tenantCode);

    /**
     * 新增租户
     */
    int insertTenant(SysTenant tenant);

    SysTenant selectTenantById(Long tenantId);

    int updateTenantStatus(@Param("tenantId")Long tenantId, @Param("status")String status);

    int updateTenant(SysTenant tenant);

    int deleteTenantByIds(Long[] tenantIds);
}