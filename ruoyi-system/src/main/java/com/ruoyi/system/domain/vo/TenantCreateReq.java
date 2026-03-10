package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.system.domain.vo
 * @Project：RuoYi-Vue-master
 * @name：TenantCreateReq
 * @Date：2026/2/10 14:06
 * @Filename：TenantCreateReq
 * @Description 平台创建租户 + 同步创建该租户管理员账号 请求体
 */
public class TenantCreateReq {
    /**
     * 租户编码（唯一）
     */
    @NotBlank(message = "tenantCode 不能为空")
    @Size(max = 64, message = "tenantCode 长度不能超过64")
    private String tenantCode;

    /**
     * 租户名称
     */
    @NotBlank(message = "tenantName 不能为空")
    @Size(max = 128, message = "tenantName 长度不能超过128")
    private String tenantName;

    /**
     * 租户管理员账号（租户内唯一）
     */
    @NotBlank(message = "adminUserName 不能为空")
    @Size(max = 30, message = "adminUserName 长度不能超过30")
    private String adminUserName;

    /**
     * 租户管理员密码（明文，后台会加密存储）
     */
    @NotBlank(message = "adminPassword 不能为空")
    @Size(min = 6, max = 50, message = "adminPassword 长度应在6~50")
    private String adminPassword;

    /**
     * 租户管理员昵称
     */
    @NotBlank(message = "adminNickName 不能为空")
    @Size(max = 30, message = "adminNickName 长度不能超过30")
    private String adminNickName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date adminExpireTime;

    public Date getAdminExpireTime() {
        return adminExpireTime;
    }

    public void setAdminExpireTime(Date adminExpireTime) {
        this.adminExpireTime = adminExpireTime;
    }

    private String adminPhone;
    private String adminEmail;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminNickName() {
        return adminNickName;
    }

    public void setAdminNickName(String adminNickName) {
        this.adminNickName = adminNickName;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }
}
