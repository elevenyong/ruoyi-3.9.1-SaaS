package com.ruoyi.hh.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.hh.business.domain.TMeasurepointCensusLevel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hh.business.service.ITMeasurepointCensusLevelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测点统计级别统计工作面巷道等Controller
 *
 * @author eleven
 * @date 2022-10-18
 */
@RestController
@RequestMapping("/quartz/level")
public class TMeasurepointCensusLevelController extends BaseController {
    @Autowired
    private ITMeasurepointCensusLevelService tMeasurepointCensusLevelService;

    /**
     * 查询测点统计级别统计工作面巷道等列表
     */
    @PreAuthorize("@ss.hasPermi('quartz:level:list')")
    @GetMapping("/list")
    public TableDataInfo list(TMeasurepointCensusLevel tMeasurepointCensusLevel)
    {
        startPage();
        List<TMeasurepointCensusLevel> list = tMeasurepointCensusLevelService.selectTMeasurepointCensusLevelList(tMeasurepointCensusLevel);
        return getDataTable(list);
    }

    /**
     * 导出测点统计级别统计工作面巷道等列表
     */
    @PreAuthorize("@ss.hasPermi('quartz:level:export')")
    @Log(title = "测点统计级别统计工作面巷道等", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TMeasurepointCensusLevel tMeasurepointCensusLevel)
    {
        List<TMeasurepointCensusLevel> list = tMeasurepointCensusLevelService.selectTMeasurepointCensusLevelList(tMeasurepointCensusLevel);
        ExcelUtil<TMeasurepointCensusLevel> util = new ExcelUtil<TMeasurepointCensusLevel>(TMeasurepointCensusLevel.class);
        util.exportExcel(response, list, "测点统计级别统计工作面巷道等数据");
    }

    /**
     * 获取测点统计级别统计工作面巷道等详细信息
     */
    @PreAuthorize("@ss.hasPermi('quartz:level:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(tMeasurepointCensusLevelService.selectTMeasurepointCensusLevelById(id));
    }

    /**
     * 新增测点统计级别统计工作面巷道等
     */
    @PreAuthorize("@ss.hasPermi('quartz:level:add')")
    @Log(title = "测点统计级别统计工作面巷道等", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TMeasurepointCensusLevel tMeasurepointCensusLevel)
    {
        return toAjax(tMeasurepointCensusLevelService.insertTMeasurepointCensusLevel(tMeasurepointCensusLevel));
    }

    /**
     * 修改测点统计级别统计工作面巷道等
     */
    @PreAuthorize("@ss.hasPermi('quartz:level:edit')")
    @Log(title = "测点统计级别统计工作面巷道等", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TMeasurepointCensusLevel tMeasurepointCensusLevel)
    {
        return toAjax(tMeasurepointCensusLevelService.updateTMeasurepointCensusLevel(tMeasurepointCensusLevel));
    }

    /**
     * 删除测点统计级别统计工作面巷道等
     */
    @PreAuthorize("@ss.hasPermi('quartz:level:remove')")
    @Log(title = "测点统计级别统计工作面巷道等", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(tMeasurepointCensusLevelService.deleteTMeasurepointCensusLevelByIds(ids));
    }
}
