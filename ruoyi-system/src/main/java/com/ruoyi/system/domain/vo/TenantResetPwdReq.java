package com.ruoyi.system.domain.vo;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.system.domain.vo
 * @Project：RuoYi-Vue-master
 * @name：TenantResetPwdReq
 * @Date：2026/2/11 10:46
 * @Filename：TenantResetPwdReq
 * @Description
 */

import javax.validation.constraints.NotBlank;

/**
 * 重置租户管理员密码请求
 */
public class TenantResetPwdReq
{
    @NotBlank(message = "adminUserName 不能为空")
    private String adminUserName;

    @NotBlank(message = "newPassword 不能为空")
    private String newPassword;

    public String getAdminUserName()
    {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName)
    {
        this.adminUserName = adminUserName;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }
}