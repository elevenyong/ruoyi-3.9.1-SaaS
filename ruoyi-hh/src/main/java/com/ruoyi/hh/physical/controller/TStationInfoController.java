package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TStationInfo;
import com.ruoyi.hh.physical.service.ITStationInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 测点基本信息(TStationInfo)表控制层
 *
 * @author makejava
 * @since 2023-06-21 15:46:56
 */
@RestController
@RequestMapping("tStationInfo")
public class TStationInfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITStationInfoService tStationInfoService;

    /**
     * 获取列表
     *
     * @param tStationInfo 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tStationInfo:list')")
    public TableDataInfo selectAll(@RequestBody TStationInfo tStationInfo) {
        startPage();
        List<TStationInfo> list = this.tStationInfoService.selectTStationInfoList(tStationInfo);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tStationInfo 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tStationInfo:insert')")
    public void insertTStationInfo(@RequestBody TStationInfo tStationInfo) {
        this.tStationInfoService.insertTStationInfo(tStationInfo);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tStationInfo:insert')")
    public void insert(@RequestBody List<TStationInfo> list) {
        if (list.size() > 0) {
            for (TStationInfo td : list) {
                this.tStationInfoService.insertTStationInfo(td);
            }
        }
    }


    /**
     * 修改
     *
     * @param tStationInfo 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTStationInfo")
    // @PreAuthorize("@ss.hasPermi('tStationInfo:updateTStationInfo')")
    public AjaxResult updateTStationInfo(@RequestBody TStationInfo tStationInfo) {
        return AjaxResult.success(this.tStationInfoService.updateTStationInfo(tStationInfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tStationInfo:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tStationInfoService.selectTStationInfoById(id));
    }


    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTStationInfoById")
    // @PreAuthorize("@ss.hasPermi('tStationInfo:id')")
    public AjaxResult deleteTStationInfoById(@RequestParam int id) {
        int result = this.tStationInfoService.deleteTStationInfoById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTStationInfoByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tStationInfoService.deleteTStationInfoByIds(ids));
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TStationInfo tStationInfo) {
        List<TStationInfo> list = this.tStationInfoService.selectTStationInfoList(tStationInfo);
        ExcelUtil<TStationInfo> util = new ExcelUtil<TStationInfo>(TStationInfo.class);
        util.exportExcel(response, list, "测点基本信息");
    }

    /**
     * 查询测点信息中瓦斯累计量的测点的安装位置
     *
     * @return 列表
     */
    @PostMapping("getLJMeasurePointLocal")
    public AjaxResult getLJMeasurePointLocal() {
        return AjaxResult.success(this.tStationInfoService.getLJMeasurePointLocal());
    }

    /**
     * 通过测点安装位置修改测点位置顺序
     *
     * @param measurePointLocal
     * @return
     */
    @PutMapping("updateSerialByLocal")
    public AjaxResult updateSerialByLocal(Integer serial, String measurePointLocal) {
        int result = this.tStationInfoService.updateSerialByLocal(serial, measurePointLocal);
        if (result > 0) {
            return AjaxResult.success("修改成功");
        }
        return AjaxResult.error("修改失败");
    }

    /**
     * 1、
     * 获取所有测点安装位置
     *
     * @return
     */
    @PostMapping("getMeasurePointLocal")
    public AjaxResult getMeasurePointLocal() {
        return AjaxResult.success(this.tStationInfoService.getMeasurePointLocal());
    }

    /**
     * 获取所有测点安装位置（去重）
     * @return
     */
    @PostMapping("getGroupByMeasurePointLocal")
    public AjaxResult getGroupByMeasurePointLocal(){
        return AjaxResult.success(this.tStationInfoService.getGroupByMeasurePointLocal());
    }

    /**
     * 2、将测点设置成总管路
     *
     * @param measurePointLocal 测点安装位置
     * @return
     */
    @PostMapping("updatePipingByLocal")
    public AjaxResult updatePipingByLocal(String measurePointLocal) {
        return AjaxResult.success(this.tStationInfoService.updatePipingByLocal(measurePointLocal));
    }

    /**
     * 3、将指定的测点安装位置设置到指定巷道中
     *
     * @param measurePointLocals
     * @return
     */
    @PostMapping("updateRoadwayIdByLocal")
    public AjaxResult updateRoadwayIdByLocal(@RequestParam("roadwayId") Integer roadwayId,
                                             @RequestParam("measurePointLocals") List<String> measurePointLocals) {
        return AjaxResult.success(this.tStationInfoService.updateRoadwayIdByLocal(roadwayId, measurePointLocals));
    }

    /**
     * 4、设置测点信息的抽采单元和序号
     *
     * @param measurePointLocal
     * @param drainageUnitId
     * @param serial
     */
    @PostMapping("setDrainageUnitAndSerial")
    public AjaxResult setDrainageUnitAndSerial(String measurePointLocal,
                                         Integer drainageUnitId,
                                         Integer serial) {
        return AjaxResult.success(this.tStationInfoService.setDrainageUnitAndSerial(measurePointLocal, drainageUnitId, serial));
    }

    /**
     * 设置测点位置逻辑
     * @param roadwayId 所属巷道
     * @param drainageUnitId 对应的抽采单元ID
     * @param pipeTypeId 抽采管道类型
     * @param serial 同一巷道，同一管道类型中的顺序
     * @param piping 抽采单元总管道
     * @param farthestEnd 是否为最远端的测点位置
     * @param measurePointLocal 测点位置
     * @return
     */
    @PostMapping("setStationInfo")
    public AjaxResult setStationInfo(@RequestParam(value = "roadwayId", required = false) Integer roadwayId,
                                     @RequestParam(value = "drainageUnitId", required = false) Integer drainageUnitId,
                                     @RequestParam(value = "pipeTypeId", required = false) Integer pipeTypeId,
                                     @RequestParam(value = "serial", required = false) Integer serial,
                                     @RequestParam(value = "piping", required = false) Boolean piping,
                                     @RequestParam(value = "farthestEnd", required = false) Boolean farthestEnd,
                                     @RequestParam(value = "measurePointLocal") String measurePointLocal){
        return AjaxResult.success(this.tStationInfoService.setStationInfo(roadwayId, drainageUnitId, pipeTypeId, serial, piping, farthestEnd, measurePointLocal));
    }

    /**
     * 获取所有测点类型
     * @return
     */
    @PostMapping("getSensorTypes")
    public AjaxResult getSensorTypes(){
        return AjaxResult.success(this.tStationInfoService.getSensorTypes());
    }


}