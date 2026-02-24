package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysTenant;
import com.ruoyi.system.domain.vo.TenantCreateReq;
import com.ruoyi.system.domain.vo.TenantInitBaseReq;
import com.ruoyi.system.domain.vo.TenantResetPwdReq;
import com.ruoyi.system.service.ISysTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.web.controller.system
 * @Project：RuoYi-Vue-master
 * @name：SysTenantController
 * @Date：2026/2/10 14:10
 * @Filename：SysTenantController
 * @Description 平台租户管理（方案1：平台创建租户时同步创建该租户管理员账号）
 */
@RestController
@RequestMapping("/system/tenant")
public class SysTenantController extends BaseController
{
    @Autowired
    private ISysTenantService tenantService;

    @PreAuthorize("@ss.hasPermi('system:tenant:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTenant tenant)
    {
        startPage();
        List<SysTenant> list = tenantService.selectTenantList(tenant);
        return getDataTable(list);
    }

    /**
     * 修改租户状态（启用/停用）
     */
    @PreAuthorize("@ss.hasPermi('system:tenant:edit')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysTenant tenant)
    {
        if (tenant.getTenantId() == null || tenant.getStatus() == null)
        {
            return AjaxResult.error("tenantId/status 不能为空");
        }
        return toAjax(tenantService.updateTenantStatus(tenant.getTenantId(), tenant.getStatus()));
    }

    /**
     * 创建租户（并初始化租户管理员账号/角色/部门）
     */
    @PreAuthorize("@ss.hasPermi('system:tenant:add')")
    @Log(title = "租户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TenantCreateReq req)
    {
        Long tenantId = tenantService.createTenantWithAdmin(req);
        return AjaxResult.success("创建成功", tenantId);
    }

    @PreAuthorize("@ss.hasPermi('system:tenant:query')")
    @GetMapping("/{tenantCode}")
    public AjaxResult getInfo(@PathVariable String tenantCode)
    {
        return AjaxResult.success(tenantService.selectTenantByCode(tenantCode));
    }

    /**
     * 初始化（或重新初始化）租户基础数据：字典/参数/租户管理员角色菜单等
     */
    @PreAuthorize("@ss.hasPermi('system:tenant:init')")
    @Log(title = "租户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/{tenantId}/init-base")
    public AjaxResult initBase(@PathVariable Long tenantId, @Validated @RequestBody TenantInitBaseReq req)
    {
        tenantService.initTenantBase(tenantId, req.getTemplateTenantId());
        return AjaxResult.success();
    }

    /**
     * 重置指定租户的管理员密码
     */
    @PreAuthorize("@ss.hasPermi('system:tenant:resetPwd')")
    @Log(title = "租户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/{tenantId}/reset-admin-pwd")
    public AjaxResult resetAdminPwd(@PathVariable Long tenantId, @Validated @RequestBody TenantResetPwdReq req)
    {
        tenantService.resetTenantAdminPassword(tenantId, req.getAdminUserName(), req.getNewPassword());
        return AjaxResult.success();
    }

}