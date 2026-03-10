package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.tenant.TenantContextHolder;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysTenant;
import com.ruoyi.system.domain.vo.TenantCreateReq;
import com.ruoyi.system.domain.vo.TenantInitBaseReq;
import com.ruoyi.system.domain.vo.TenantResetPwdReq;
import com.ruoyi.system.service.ISysTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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


    /**
     * 仅平台租户(tenant_id=1, tenant_code=default)允许访问租户管理能力
     */
    private void checkPlatformTenant()
    {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId == null || tenantId.longValue() != 1L)
        {
            throw new ServiceException("无权限：仅平台超级管理员可访问【租户管理】");
        }
    }

    @PreAuthorize("@ss.hasPermi('system:tenant:query')")
    @GetMapping("/id/{tenantId}")
    public AjaxResult getInfoById(@PathVariable Long tenantId)
    {
        checkPlatformTenant();
        return AjaxResult.success(tenantService.selectTenantById(tenantId));
    }

    /**
     * 查询租户管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:tenant:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysTenant tenant)
    {
        checkPlatformTenant();
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
        checkPlatformTenant();
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
        checkPlatformTenant();
        Long tenantId = tenantService.createTenantWithAdmin(req);
        return AjaxResult.success("创建成功", tenantId);
    }

    @PreAuthorize("@ss.hasPermi('system:tenant:query')")
    @GetMapping("/{tenantCode}")
    public AjaxResult getInfo(@PathVariable String tenantCode)
    {
        checkPlatformTenant();
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
        checkPlatformTenant();
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
        checkPlatformTenant();
        tenantService.resetTenantAdminPassword(tenantId, req.getAdminUserName(), req.getNewPassword());
        return AjaxResult.success();
    }

    /**
     * 导出租户管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:tenant:export')")
    @Log(title = "租户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysTenant tenant)
    {
        checkPlatformTenant();
        List<SysTenant> list = tenantService.selectTenantList(tenant);
        ExcelUtil<SysTenant> util = new ExcelUtil<SysTenant>(SysTenant.class);
        util.exportExcel(response, list, "租户管理数据");
    }

    /**
     * 兼容前端：tenant.js 使用 GET /system/tenant/export
     * 不改前端的情况下，这里必须提供 GET 版本
     */
    @PreAuthorize("@ss.hasPermi('system:tenant:export')")
    @Log(title = "租户管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void exportGet(HttpServletResponse response, SysTenant tenant)
    {
        export(response, tenant);
    }

    /**
     * 修改租户（tenant.js：PUT /system/tenant）
     */
    @PreAuthorize("@ss.hasPermi('system:tenant:edit')")
    @Log(title = "租户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysTenant tenant)
    {
        checkPlatformTenant();
        // 你 service 里需要实现 updateTenant(tenant)
        return toAjax(tenantService.updateTenant(tenant));
    }

    /**
     * 删除租户（tenant.js：DELETE /system/tenant/{tenantIds}）
     * tenantIds 形如 "2,3,4"
     */
    @PreAuthorize("@ss.hasPermi('system:tenant:remove')")
    @Log(title = "租户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tenantIds}")
    public AjaxResult remove(@PathVariable Long[] tenantIds)
    {
        checkPlatformTenant();
        // 你 service 里需要实现 deleteTenantByIds(tenantIds)
        return toAjax(tenantService.deleteTenantByIds(tenantIds));
    }

}