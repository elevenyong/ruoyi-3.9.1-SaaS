package com.ruoyi.framework.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.framework.tenant.impl
 * @Project：RuoYi-Vue-master
 * @name：TenantMapper
 * @Date：2026/2/6 15:51
 * @Filename：TenantMapper
 * @Description
 */
@Mapper
public interface TenantMapper {

    @Select("select tenant_id from sys_tenant where del_flag='0' and status='0' and tenant_code=#{code} limit 1")
    Long selectTenantIdByCode(@Param("code") String code);
}