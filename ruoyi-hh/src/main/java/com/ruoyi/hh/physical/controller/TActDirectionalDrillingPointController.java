package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TActDirectionalDrillingPoint;
import com.ruoyi.hh.physical.service.ITActDirectionalDrillingPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 实际定向钻孔点位列表(TActDirectionalDrillingPoint)表控制层
 *
 * @author makejava
 * @since 2023-06-29 17:53:19
 */
@Api(value = "DZZK实钻定向钻点位接口", tags = "DZZK实钻定向钻点位接口")
@RestController
@RequestMapping("tActDirectionalDrillingPoint")
public class TActDirectionalDrillingPointController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITActDirectionalDrillingPointService tActDirectionalDrillingPointService;

    /**
     * 获取列表
     *
     * @param tActDirectionalDrillingPoint 要查询的信息
     * @return 返回结果
     */
    @ApiOperation("按条件获取实钻定向钻点位列表")
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalDrillingPoint:list')")
    public TableDataInfo selectAll(@RequestBody TActDirectionalDrillingPoint tActDirectionalDrillingPoint) {
        startPage();
        List<TActDirectionalDrillingPoint> list = this.tActDirectionalDrillingPointService.selectTActDirectionalDrillingPointList(tActDirectionalDrillingPoint);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tActDirectionalDrillingPoint 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalDrillingPoint:insert')")
    public void insertTActDirectionalDrillingPoint(@RequestBody TActDirectionalDrillingPoint tActDirectionalDrillingPoint) {
        this.tActDirectionalDrillingPointService.insertTActDirectionalDrillingPoint(tActDirectionalDrillingPoint);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @ApiOperation("批量插入实钻定向钻点位")
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalDrillingPoint:insert')")
    public void insert(@RequestBody List<TActDirectionalDrillingPoint> list) {
        if (list.size() > 0) {
            for (TActDirectionalDrillingPoint td : list) {
                this.tActDirectionalDrillingPointService.insertTActDirectionalDrillingPoint(td);
            }
        }
    }


    /**
     * 修改
     *
     * @param tActDirectionalDrillingPoint 要修改的实体信息
     * @return 结果
     */
    @ApiOperation("修改实钻定向钻点位数据")
    @PutMapping("updateTActDirectionalDrillingPoint")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalDrillingPoint:updateTActDirectionalDrillingPoint')")
    public AjaxResult updateTActDirectionalDrillingPoint(@RequestBody TActDirectionalDrillingPoint tActDirectionalDrillingPoint) {
        return AjaxResult.success(this.tActDirectionalDrillingPointService.updateTActDirectionalDrillingPoint(tActDirectionalDrillingPoint));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalDrillingPoint:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tActDirectionalDrillingPointService.selectTActDirectionalDrillingPointById(id));
    }


    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTActDirectionalDrillingPointById")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalDrillingPoint:id')")
    public AjaxResult deleteTActDirectionalDrillingPointById(@RequestParam int id) {
        int result = this.tActDirectionalDrillingPointService.deleteTActDirectionalDrillingPointById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @ApiOperation("批量删除实钻定向钻点位数据")
    @DeleteMapping("deleteTActDirectionalDrillingPointByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tActDirectionalDrillingPointService.deleteTActDirectionalDrillingPointByIds(ids));
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @ApiOperation("导出实钻定向钻点位数据")
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TActDirectionalDrillingPoint tActDirectionalDrillingPoint) {
        List<TActDirectionalDrillingPoint> list = this.tActDirectionalDrillingPointService.selectTActDirectionalDrillingPointList(tActDirectionalDrillingPoint);
        ExcelUtil<TActDirectionalDrillingPoint> util = new ExcelUtil<TActDirectionalDrillingPoint>(TActDirectionalDrillingPoint.class);
        util.exportExcel(response, list, "实际定向钻孔点位列表");
    }


}

