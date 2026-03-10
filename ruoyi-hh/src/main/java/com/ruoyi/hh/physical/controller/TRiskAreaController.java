package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TRiskArea;
import com.ruoyi.hh.physical.service.ITRiskAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/tRiskArea")
@Api(value = "工作面风险区域接口", tags = "DZ工作面风险区域接口")
public class TRiskAreaController extends BaseController {

    @Resource
    private ITRiskAreaService tRiskAreaService;


    /**
     * 按条件查询风险区域列表
     */
    @PostMapping("/list")
    @ApiOperation("按条件查询风险区域列表")
    public TableDataInfo list(@RequestBody TRiskArea TRiskArea) {
        startPage();
        List<TRiskArea> list = tRiskAreaService.selectTRiskAreaList(TRiskArea);
        return getDataTable(list);
    }

    /**
     * 导出风险区域列表
     */
    @Log(title = "风险区域", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出风险区域列表")
    public void export(HttpServletResponse response, TRiskArea TRiskArea) {
        List<TRiskArea> list = tRiskAreaService.selectTRiskAreaList(TRiskArea);
        ExcelUtil<TRiskArea> util = new ExcelUtil<TRiskArea>(TRiskArea.class);
        util.exportExcel(response, list, "风险区域数据");
    }

    /**
     * 获取风险区域详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取单个风险区域详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tRiskAreaService.selectTRiskAreaById(id));
    }

    /**
     * 新增风险区域
     */
    @Log(title = "风险区域", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增风险区域")
    public AjaxResult add(@RequestBody TRiskArea TRiskArea) {
        if (TRiskArea != null) {
            TRiskArea.setId(null);
        } else {
            return AjaxResult.error("数据为null，添加失败");
        }
        int i = tRiskAreaService.insertTRiskArea(TRiskArea);
        if (i > 0) {
            return AjaxResult.success(TRiskArea);
        } else {
            return AjaxResult.error("添加失败");
        }
    }

    /**
     * 修改风险区域
     */
    @Log(title = "风险区域", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改风险区域")
    public AjaxResult edit(@RequestBody TRiskArea TRiskArea) {
        if (TRiskArea != null) {
            int i = tRiskAreaService.updateTRiskArea(TRiskArea);
            if (i > 0) {
                return AjaxResult.success(TRiskArea);
            } else {
                return AjaxResult.error("修改失败");
            }
        } else {
            return AjaxResult.error("数据为null，修改失败");
        }
    }

    /**
     * 删除风险区域
     */
    @Log(title = "风险区域", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("根据区域ID删除风险区域")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tRiskAreaService.deleteTRiskAreaByIds(ids));
    }


}
