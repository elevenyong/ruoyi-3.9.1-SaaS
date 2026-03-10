package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TWarningActions;
import com.ruoyi.hh.physical.service.ITWarningActionsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 处理预案
 *
 * @author hhhz
 * @date 2022-12-23
 */
@RestController
@RequestMapping("/actions")
public class TWarningActionsController extends BaseController {
    @Resource
    private ITWarningActionsService tWarningActionsService;

    /**
     * 查询处理预案列表
     */
    //@PreAuthorize("@ss.hasPermi('system:actions:list')")
    @GetMapping("/list")
    public TableDataInfo list(TWarningActions tWarningActions) {
        startPage();
        List<TWarningActions> list = tWarningActionsService.selectTWarningActionsList(tWarningActions);
        return getDataTable(list);
    }

    /**
     * 导出处理预案列表
     */
    //@PreAuthorize("@ss.hasPermi('system:actions:export')")
    @Log(title = "处理预案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWarningActions tWarningActions) {
        List<TWarningActions> list = tWarningActionsService.selectTWarningActionsList(tWarningActions);
        ExcelUtil<TWarningActions> util = new ExcelUtil<TWarningActions>(TWarningActions.class);
        util.exportExcel(response, list, "actions数据");
    }

    /**
     * 获取处理预案详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:actions:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tWarningActionsService.selectTWarningActionsById(id));
    }

    /**
     * 新增处理预案
     */
    //@PreAuthorize("@ss.hasPermi('system:actions:add')")
    @Log(title = "处理预案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWarningActions tWarningActions) {
        return toAjax(tWarningActionsService.insertTWarningActions(tWarningActions));
    }

    /**
     * 修改处理预案
     */
    //@PreAuthorize("@ss.hasPermi('system:actions:edit')")
    @Log(title = "处理预案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWarningActions tWarningActions) {
        return toAjax(tWarningActionsService.updateTWarningActions(tWarningActions));
    }

    /**
     * 删除处理预案
     */
    //@PreAuthorize("@ss.hasPermi('system:actions:remove')")
    @Log(title = "处理预案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tWarningActionsService.deleteTWarningActionsByIds(ids));
    }
}
