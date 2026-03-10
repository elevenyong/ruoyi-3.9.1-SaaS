package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TMeasuringPoint;
import com.ruoyi.hh.physical.service.ITMeasuringPointService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TMeasuringPoint)表控制层
 *
 * @author Eleven
 * @since 2022-07-16 18:25:57
 */
@RestController
@RequestMapping("tMeasuringPoint")
public class TMeasuringPointController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITMeasuringPointService tMeasuringPointService;


    /**
     * 获取列表
     *
     * @param tMeasuringPoint 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tMeasuringPoint:list')")
    public TableDataInfo selectAll(@RequestBody TMeasuringPoint tMeasuringPoint) {
        startPage();
        List<TMeasuringPoint> list = this.tMeasuringPointService.selectTMeasuringPointList(tMeasuringPoint);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tMeasuringPoint 单挑数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tMeasuringPoint:insert')")
    public void insertTMeasuringPoint(@RequestBody TMeasuringPoint tMeasuringPoint) {
        this.tMeasuringPointService.insertTMeasuringPoint(tMeasuringPoint);
    }

	  /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tMeasuringPoint:insert')")
    public void insert(@RequestBody List<TMeasuringPoint> list){
        if(list!=null){
            for(TMeasuringPoint tMeasuringPoint:list){
                this.tMeasuringPointService.insertTMeasuringPoint(tMeasuringPoint);
            }
        }
    }


    /**
     * 修改
     *
     * @param tMeasuringPoint 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTMeasuringPoint")
    // @PreAuthorize("@ss.hasPermi('tMeasuringPoint:updateTMeasuringPoint')")
    public AjaxResult updateTRoadway(@RequestBody TMeasuringPoint tMeasuringPoint) {
        return AjaxResult.success(this.tMeasuringPointService.updateTMeasuringPoint(tMeasuringPoint));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tMeasuringPoint:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tMeasuringPointService.selectTMeasuringPointById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTMeasuringPointById")
    // @PreAuthorize("@ss.hasPermi('tMeasuringPoint:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tMeasuringPointService.deleteTMeasuringPointById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTMeasuringPointByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tMeasuringPointService.deleteTMeasuringPointByIds(ids));
    }

    /**
     * 根据巷道ID和测点类型查询列表
     * @param roadwayId 巷道ID
     * @param measureTypeId 测点ID
     * @return 列表
     */
    @PostMapping("getByRoadWayIdAndType")
    public List<TMeasuringPoint> getByRoadWayIdAndType(@Param("roadwayId") Integer roadwayId,
                                                       @Param("measureTypeId") Integer measureTypeId){
        return this.tMeasuringPointService.getByRoadWayIdAndType(roadwayId, measureTypeId);
    }


    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        List<TMeasuringPoint> list = new ArrayList<>();
        startPage();
        list=this.tMeasuringPointService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TMeasuringPoint TMeasuringPoint) {
        List<TMeasuringPoint> list=this.tMeasuringPointService.selectTMeasuringPointList(TMeasuringPoint);
        ExcelUtil<TMeasuringPoint> util = new ExcelUtil<TMeasuringPoint>(TMeasuringPoint.class);
        util.exportExcel(response, list, "测点数据");
    }



}

