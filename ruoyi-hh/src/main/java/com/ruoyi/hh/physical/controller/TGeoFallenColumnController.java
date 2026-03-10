package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TGeoFallenColumn;
import com.ruoyi.hh.physical.service.ITCoalPointService;
import com.ruoyi.hh.physical.service.ITGeoFallenColumnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 陷落柱Controller
 *
 * @author hhhz
 * @date 2022-10-28
 */
@Api(value = "DZGEO陷落柱接口", tags = "DZGEO陷落柱接口")
@RestController
@RequestMapping("/geoFallenColumn")
public class TGeoFallenColumnController extends BaseController {
    @Resource
    private ITGeoFallenColumnService tGeoFallenColumnService;


    /**
     * 按条件查询陷落柱列表
     */
    @GetMapping("/list")
    @ApiOperation("按条件查询陷落柱列表")
    public TableDataInfo list(TGeoFallenColumn tGeoFallenColumn) {
        startPage();
        List<TGeoFallenColumn> list = tGeoFallenColumnService.selectTGeoFallenColumnList(tGeoFallenColumn);
        return getDataTable(list);
    }

    /**
     * 导出陷落柱列表
     */
    @Log(title = "陷落柱", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出陷落柱列表")
    public void export(HttpServletResponse response, TGeoFallenColumn tGeoFallenColumn) {
        List<TGeoFallenColumn> list = tGeoFallenColumnService.selectTGeoFallenColumnList(tGeoFallenColumn);
        ExcelUtil<TGeoFallenColumn> util = new ExcelUtil<TGeoFallenColumn>(TGeoFallenColumn.class);
        util.exportExcel(response, list, "陷落柱数据");
    }

    /**
     * 获取陷落柱详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取单个陷落柱详细信息")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tGeoFallenColumnService.selectTGeoFallenColumnById(id));
    }

    /**
     * 新增陷落柱
     */
    @Log(title = "陷落柱", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增陷落柱")
    public AjaxResult add(@RequestBody TGeoFallenColumn tGeoFallenColumn) {
        if (tGeoFallenColumn != null) {
            tGeoFallenColumn.setId(null);
        } else {
            return AjaxResult.error("数据为null，添加失败");
        }
        int i = tGeoFallenColumnService.insertTGeoFallenColumn(tGeoFallenColumn);
        if (i > 0) {
            return AjaxResult.success(tGeoFallenColumn);
        } else {
            return AjaxResult.error("添加失败");
        }
    }

    /**
     * 修改陷落柱
     */
    @Log(title = "陷落柱", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改陷落柱")
    public AjaxResult edit(@RequestBody TGeoFallenColumn tGeoFallenColumn) {
        return toAjax(tGeoFallenColumnService.updateTGeoFallenColumn(tGeoFallenColumn));
    }

    /**
     * 删除陷落柱
     */
    @Log(title = "陷落柱", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("根据钻孔ID删除陷落柱")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tGeoFallenColumnService.deleteTGeoFallenColumnByIds(ids));
    }
}
