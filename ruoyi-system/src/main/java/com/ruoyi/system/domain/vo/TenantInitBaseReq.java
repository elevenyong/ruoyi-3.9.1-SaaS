package com.ruoyi.system.domain.vo;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.system.domain.vo
 * @Project：RuoYi-Vue-master
 * @name：TenantInitBaseReq
 * @Date：2026/2/11 10:45
 * @Filename：TenantInitBaseReq
 * @Description
 */

import javax.validation.constraints.NotNull;

/**
 * 租户基础数据初始化请求
 */
public class TenantInitBaseReq
{
    /** 模板租户ID（默认建议使用 tenant_id=1 的 default 租户作为模板） */
    @NotNull(message = "templateTenantId 不能为空")
    private Long templateTenantId;

    public Long getTemplateTenantId()
    {
        return templateTenantId;
    }

    public void setTemplateTenantId(Long templateTenantId)
    {
        this.templateTenantId = templateTenantId;
    }
}