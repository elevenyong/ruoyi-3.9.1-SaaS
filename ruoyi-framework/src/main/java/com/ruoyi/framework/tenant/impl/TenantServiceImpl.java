package com.ruoyi.framework.tenant.impl;

import com.ruoyi.framework.mapper.TenantMapper;
import com.ruoyi.framework.tenant.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.framework.tenant.impl
 * @Project：RuoYi-Vue-master
 * @name：TenantServiceImpl
 * @Date：2026/2/6 15:51
 * @Filename：TenantServiceImpl
 * @Description
 */
@Service
public class TenantServiceImpl implements TenantService {

    @Autowired
    private TenantMapper tenantMapper;

    @Override
    public Long getTenantIdByCode(String tenantCode) {
        return tenantMapper.selectTenantIdByCode(tenantCode);
    }
}