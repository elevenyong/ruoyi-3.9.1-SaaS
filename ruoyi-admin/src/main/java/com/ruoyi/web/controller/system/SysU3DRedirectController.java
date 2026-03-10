package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysU3DRedirect;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysU3DRedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 菜单指向Controller
 *
 * @author hh
 * @date 2022-09-22
 */
@RestController
@RequestMapping("/system/u3dredirect")
public class SysU3DRedirectController extends BaseController {
    @Autowired
    private ISysU3DRedirectService sysU3DRedirectService;

    /**
     * 查询菜单指向列表
     */
    //@PreAuthorize("@ss.hasPermi('system:u3dredirect:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysU3DRedirect sysU3DRedirect) {
        startPage();
        List<SysU3DRedirect> list = sysU3DRedirectService.selectSysU3DRedirectList(sysU3DRedirect);
        return getDataTable(list);
    }

    /**
     * 导出菜单指向列表
     */
    //@PreAuthorize("@ss.hasPermi('system:u3dredirect:export')")
    @Log(title = "菜单指向", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysU3DRedirect sysU3DRedirect) {
        List<SysU3DRedirect> list = sysU3DRedirectService.selectSysU3DRedirectList(sysU3DRedirect);
        ExcelUtil<SysU3DRedirect> util = new ExcelUtil<SysU3DRedirect>(SysU3DRedirect.class);
        util.exportExcel(response, list, "菜单指向数据");
    }

    /**
     * 获取菜单指向详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:u3dredirect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysU3DRedirectService.selectSysU3DRedirectById(id));
    }

    /**
     * 新增菜单指向
     */
    //@PreAuthorize("@ss.hasPermi('system:u3dredirect:add')")
    @Log(title = "菜单指向", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysU3DRedirect sysU3DRedirect) {
        return toAjax(sysU3DRedirectService.insertSysU3DRedirect(sysU3DRedirect));
    }

    /**
     * 修改菜单指向
     */
    //@PreAuthorize("@ss.hasPermi('system:u3dredirect:edit')")
    @Log(title = "菜单指向", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysU3DRedirect sysU3DRedirect) {
        return toAjax(sysU3DRedirectService.updateSysU3DRedirect(sysU3DRedirect));
    }

    /**
     * 删除菜单指向
     */
    //@PreAuthorize("@ss.hasPermi('system:u3dredirect:remove')")
    @Log(title = "菜单指向", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysU3DRedirectService.deleteSysU3DRedirectByIds(ids));
    }
}
