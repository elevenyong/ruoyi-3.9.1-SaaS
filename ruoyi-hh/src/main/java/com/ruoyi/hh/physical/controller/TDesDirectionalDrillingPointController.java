package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TDesDirectionalDrillingPoint;
import com.ruoyi.hh.physical.service.ITDesDirectionalDrillingPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设计定向钻孔点位信息(TDesDirectionalDrillingPoint)表控制层
 *
 * @author makejava
 * @since 2023-06-29 17:54:00
 */
@Api(value = "DZZK设计定向钻点位接口",tags = "DZZK设计定向钻点位接口")
@RestController
@RequestMapping("tDesDirectionalDrillingPoint")
public class TDesDirectionalDrillingPointController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDesDirectionalDrillingPointService tDesDirectionalDrillingPointService;

    /**
     * 获取列表
     *
     * @param tDesDirectionalDrillingPoint 要查询的信息
     * @return 返回结果
     */
    @ApiOperation("按条件查询设计定向钻点位列表")
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tDesDirectionalDrillingPoint:list')")
    public TableDataInfo selectAll(@RequestBody TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint) {
        startPage();
        List<TDesDirectionalDrillingPoint> list = this.tDesDirectionalDrillingPointService.selectTDesDirectionalDrillingPointList(tDesDirectionalDrillingPoint);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tDesDirectionalDrillingPoint 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tDesDirectionalDrillingPoint:insert')")
    public void insertTDesDirectionalDrillingPoint(@RequestBody TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint) {
        this.tDesDirectionalDrillingPointService.insertTDesDirectionalDrillingPoint(tDesDirectionalDrillingPoint);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @ApiOperation("批量插入设计定向钻点位数据")
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDesDirectionalDrillingPoint:insert')")
    public void insert(@RequestBody List<TDesDirectionalDrillingPoint> list) {
        if (list.size() > 0) {
            for (TDesDirectionalDrillingPoint td : list) {
                this.tDesDirectionalDrillingPointService.insertTDesDirectionalDrillingPoint(td);
            }
        }
    }


    /**
     * 修改
     *
     * @param tDesDirectionalDrillingPoint 要修改的实体信息
     * @return 结果
     */
    @ApiOperation("修改设计定向钻点位数据")
    @PutMapping("updateTDesDirectionalDrillingPoint")
    // @PreAuthorize("@ss.hasPermi('tDesDirectionalDrillingPoint:updateTDesDirectionalDrillingPoint')")
    public AjaxResult updateTDesDirectionalDrillingPoint(@RequestBody TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint) {
        return AjaxResult.success(this.tDesDirectionalDrillingPointService.updateTDesDirectionalDrillingPoint(tDesDirectionalDrillingPoint));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tDesDirectionalDrillingPoint:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDesDirectionalDrillingPointService.selectTDesDirectionalDrillingPointById(id));
    }


    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDesDirectionalDrillingPointById")
    // @PreAuthorize("@ss.hasPermi('tDesDirectionalDrillingPoint:id')")
    public AjaxResult deleteTDesDirectionalDrillingPointById(@RequestParam int id) {
        int result = this.tDesDirectionalDrillingPointService.deleteTDesDirectionalDrillingPointById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @ApiOperation("批量删除设计定向钻点位数据")
    @DeleteMapping("deleteTDesDirectionalDrillingPointByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tDesDirectionalDrillingPointService.deleteTDesDirectionalDrillingPointByIds(ids));
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @ApiOperation("导出设计定向钻点位数据")
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint) {
        List<TDesDirectionalDrillingPoint> list = this.tDesDirectionalDrillingPointService.selectTDesDirectionalDrillingPointList(tDesDirectionalDrillingPoint);
        ExcelUtil<TDesDirectionalDrillingPoint> util = new ExcelUtil<TDesDirectionalDrillingPoint>(TDesDirectionalDrillingPoint.class);
        util.exportExcel(response, list, "设计定向钻孔点位信息");
    }


}

