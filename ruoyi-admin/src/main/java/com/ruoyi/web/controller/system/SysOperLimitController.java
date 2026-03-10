package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysOperLimit;
import com.ruoyi.system.service.ISysOperLimitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 接口次数限制Controller
 *
 * @author hh
 * @date 2023-03-01
 */
@RestController
@RequestMapping("/system/limit")
public class SysOperLimitController extends BaseController {
    @Resource
    private ISysOperLimitService sysOperLimitService;

    /**
     * 查询接口次数限制列表
     */
    //@PreAuthorize("@ss.hasPermi('system:limit:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysOperLimit sysOperLimit) {
        startPage();
        List<SysOperLimit> list = sysOperLimitService.selectSysOperLimitList(sysOperLimit);
        return getDataTable(list);
    }

    /**
     * 导出接口次数限制列表
     */
    //@PreAuthorize("@ss.hasPermi('system:limit:export')")
    @Log(title = "接口次数限制", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperLimit sysOperLimit) {
        List<SysOperLimit> list = sysOperLimitService.selectSysOperLimitList(sysOperLimit);
        ExcelUtil<SysOperLimit> util = new ExcelUtil<SysOperLimit>(SysOperLimit.class);
        util.exportExcel(response, list, "接口次数限制数据");
    }

    /**
     * 获取接口次数限制详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:limit:query')")
    @GetMapping(value = "/{userid}")
    public AjaxResult getInfo(@PathVariable("userid") Long userid) {
        return AjaxResult.success(sysOperLimitService.selectSysOperLimitByUserid(userid));
    }

    /**
     * 新增接口次数限制
     */
    //@PreAuthorize("@ss.hasPermi('system:limit:add')")
    @Log(title = "接口次数限制", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysOperLimit sysOperLimit) {
        return toAjax(sysOperLimitService.insertSysOperLimit(sysOperLimit));
    }

    /**
     * 修改接口次数限制
     */
    //@PreAuthorize("@ss.hasPermi('system:limit:edit')")
    @Log(title = "接口次数限制", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysOperLimit sysOperLimit) {
        return toAjax(sysOperLimitService.updateSysOperLimit(sysOperLimit));
    }

    /**
     * 删除接口次数限制
     */
    //@PreAuthorize("@ss.hasPermi('system:limit:remove')")
    @Log(title = "接口次数限制", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userids}")
    public AjaxResult remove(@PathVariable Long[] userids) {
        return toAjax(sysOperLimitService.deleteSysOperLimitByUserids(userids));
    }
}
