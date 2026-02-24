package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.system.domain
 * @Project：RuoYi-Vue-master
 * @name：SysTenant
 * @Date：2026/2/10 14:05
 * @Filename：SysTenant
 * @Description 租户对象 sys_tenant（全局表，不做租户隔离）
 */
public class SysTenant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 租户ID */
    private Long tenantId;

    /** 租户编码（唯一） */
    @Excel(name = "租户编码")
    private String tenantCode;

    /** 租户名称 */
    @Excel(name = "租户名称")
    private String tenantName;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public Long getTenantId()
    {
        return tenantId;
    }

    public void setTenantId(Long tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getTenantCode()
    {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode)
    {
        this.tenantCode = tenantCode;
    }

    public String getTenantName()
    {
        return tenantName;
    }

    public void setTenantName(String tenantName)
    {
        this.tenantName = tenantName;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }
}