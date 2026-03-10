package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.core.controller.BaseController;


import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.hh.physical.domain.TMiningArea;
import com.ruoyi.hh.physical.service.ITMiningAreaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采区信息Controller
 *
 * @author eleven
 * @date 2022-10-19
 */
@RestController
@RequestMapping("/area")
public class TMiningAreaController extends BaseController {
    @Autowired
    private ITMiningAreaService tMiningAreaService;

    /**
     * 查询采区信息列表
     */
    //@PreAuthorize("@ss.hasPermi('area:list')")
    @GetMapping("/list")
    public TableDataInfo list(TMiningArea tMiningArea)
    {
        startPage();
        List<TMiningArea> list = tMiningAreaService.selectTMiningAreaList(tMiningArea);
        return getDataTable(list);
    }

    /**
     * 导出采区信息列表
     */
    //@PreAuthorize("@ss.hasPermi('area:export')")
    @Log(title = "采区信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TMiningArea tMiningArea)
    {
        List<TMiningArea> list = tMiningAreaService.selectTMiningAreaList(tMiningArea);
        ExcelUtil<TMiningArea> util = new ExcelUtil<TMiningArea>(TMiningArea.class);
        util.exportExcel(response, list, "采区信息数据");
    }

    /**
     * 获取采区信息详细信息
     */
    //@PreAuthorize("@ss.hasPermi('area:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(tMiningAreaService.selectTMiningAreaById(id));
    }

    /**
     * 新增采区信息
     */
    //@PreAuthorize("@ss.hasPermi('area:add')")
    @Log(title = "采区信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TMiningArea tMiningArea)
    {
        return toAjax(tMiningAreaService.insertTMiningArea(tMiningArea));
    }

    /**
     * 修改采区信息
     */
    //@PreAuthorize("@ss.hasPermi('area:edit')")
    @Log(title = "采区信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TMiningArea tMiningArea)
    {
        return toAjax(tMiningAreaService.updateTMiningArea(tMiningArea));
    }

    /**
     * 删除采区信息
     */
    @Log(title = "采区信息", businessType = BusinessType.DELETE)
    @DeleteMapping("remove")
    public AjaxResult remove(@RequestParam List<Integer> ids)
    {
        return toAjax(tMiningAreaService.deleteTMiningAreaByIds(ids));
    }

    /**
     * 删除采区信息
     */
    @Log(title = "采区信息", businessType = BusinessType.DELETE)
    @DeleteMapping("del")
    public AjaxResult del(Integer id)
    {
        return toAjax(tMiningAreaService.deleteTMiningAreaById(id));
    }
}
