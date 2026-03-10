package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TFaultShape;
import com.ruoyi.hh.physical.service.ITFaultShapeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TFaultShape)表控制层
 *
 * @author Eleven
 * @since 2022-07-18 11:15:49
 */
@RestController
@RequestMapping("tFaultShape")
public class TFaultShapeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITFaultShapeService tFaultShapeService;

    /**
     * 获取列表
     *
     * @param tFaultShape 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tFaultShape:list')")
    public AjaxResult selectAll(@RequestBody TFaultShape tFaultShape) {
        return AjaxResult.success(this.tFaultShapeService.selectTFaultShapeList(tFaultShape));
    }

    /**
     * 插入单条数据
     *
     * @param tFaultShape 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tFaultShape:insert')")
    public void insertTFaultShape(@RequestBody TFaultShape tFaultShape) {
        this.tFaultShapeService.insertTFaultShape(tFaultShape);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    //@PreAuthorize("@ss.hasPermi('tFaultShape:insert')")
    public void insert(@RequestBody List<TFaultShape> list) {
        if (list.size() > 0) {
            for (TFaultShape tFaultShape : list) {
                this.tFaultShapeService.insertTFaultShape(tFaultShape);
            }
        }

    }


    /**
     * 修改
     *
     * @param tFaultShape 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTFaultShape")
    //@PreAuthorize("@ss.hasPermi('tFaultShape:updateTFaultShape')")
    public AjaxResult updateTRoadway(@RequestBody TFaultShape tFaultShape) {
        return AjaxResult.success(this.tFaultShapeService.updateTFaultShape(tFaultShape));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tFaultShape:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tFaultShapeService.selectTFaultShapeById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTFaultShapeById")
    //@PreAuthorize("@ss.hasPermi('tFaultShape:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tFaultShapeService.deleteTFaultShapeById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTFaultShapeByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tFaultShapeService.deleteTFaultShapeByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TFaultShape> list = new ArrayList<>();
        list=this.tFaultShapeService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TFaultShape tFaultShape) {
        List<TFaultShape> list=this.tFaultShapeService.selectTFaultShapeList(tFaultShape);
        ExcelUtil<TFaultShape> util = new ExcelUtil<TFaultShape>(TFaultShape.class);
        util.exportExcel(response, list, "断面信息数据");
    }


}

