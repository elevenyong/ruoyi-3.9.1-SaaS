package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TSensorDataException;
import com.ruoyi.hh.business.service.ITSensorDataExceptionService;
import com.ruoyi.hh.util.Enum.MeasurePointWarnEnum;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * 异常报警(TSensorDataException)表控制层
 *
 * @author makejava
 * @since 2023-06-15 10:28:48
 */
@RestController
@RequestMapping("tSensorDataException")
public class TSensorDataExceptionController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITSensorDataExceptionService tSensorDataExceptionService;

    /**
     * 获取列表
     *
     * @param tSensorDataException  要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tSensorDataException:list')")
    public TableDataInfo selectAll(@RequestBody TSensorDataException tSensorDataException) {
        startPage();
        List<TSensorDataException> list =this.tSensorDataExceptionService.selectTSensorDataExceptionList(tSensorDataException);
        for(TSensorDataException tde:list){
            if(tde.getEndTime()!=null&&tde.getStartTime()!=null){
                tde.setDuration((int)(tde.getEndTime().getTime()-tde.getStartTime().getTime())/1000);
            }
        }
        return getDataTable(list);
    }
    
    /**
     * 插入单条数据
     *
     * @param tSensorDataException 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tSensorDataException:insert')")
    public void insertTSensorDataException (@RequestBody TSensorDataException tSensorDataException) {
        this.tSensorDataExceptionService.insertTSensorDataException(tSensorDataException);
    }
    
     /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tSensorDataException:insert')")
    public void insert(@RequestBody List<TSensorDataException> list){
        if(list.size()>0){
            for(TSensorDataException td:list){
                this.tSensorDataExceptionService.insertTSensorDataException(td);
            }
        }
    }
    

     /**
     * 修改
     *
     * @param tSensorDataException 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTSensorDataException")
    // @PreAuthorize("@ss.hasPermi('tSensorDataException:updateTSensorDataException')")
    public AjaxResult updateTSensorDataException(@RequestBody TSensorDataException tSensorDataException) {
        return AjaxResult.success(this.tSensorDataExceptionService.updateTSensorDataException(tSensorDataException));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tSensorDataException:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tSensorDataExceptionService.selectTSensorDataExceptionById(id));
    }
    
    
    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTSensorDataExceptionById")
    // @PreAuthorize("@ss.hasPermi('tSensorDataException:id')")
    public AjaxResult deleteTSensorDataExceptionById(@RequestParam int id) {
        int result = this.tSensorDataExceptionService.deleteTSensorDataExceptionById(id);
        return AjaxResult.success(result);
    }
    
    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTSensorDataExceptionByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tSensorDataExceptionService.deleteTSensorDataExceptionByIds(ids));
    }

    /**
     * 指定参数获取报警记录
     * @param time 某一天 格式：yyyy-MM-dd
     * @param second 持续时长
     * @param measurePointLocal 测点名称
     * @param sensorTypeName 测点类型
     * @param exceptionType 异常类型
     * @param warnValue 最大值
     * @return 结果
     */
    @PostMapping("getWarnDayRecord")
    public TableDataInfo getWarnDayRecord(@RequestParam(value = "time")String time,
                                                           @RequestParam(value = "second", required = false)String second,
                                                           @RequestParam(value = "measurePointLocal", required = false)String measurePointLocal,
                                                           @RequestParam(value = "sensorTypeName", required = false)String sensorTypeName,
                                                           @RequestParam(value = "exceptionType", required = false)String exceptionType,
                                                           @RequestParam(value = "warnValue", required = false)String warnValue) throws ParseException {
        startPage();
         return getDataTable(this.tSensorDataExceptionService.getWarnDayRecord(time, second, measurePointLocal, sensorTypeName, exceptionType, warnValue));

    }

    /**
     * 指定参数获取报警统计
     * @param time 时间
     * @param second 持续时长
     * @param measurePointLocal 测点名称
     * @param sensorTypeName 测点类型
     * @param exceptionType 异常类型
     * @param warnValue 最大值
     * @return 结果
     */
    @PostMapping("getWarnDayStatistics")
    public TableDataInfo getWarnDayStatistics(@RequestParam(value = "time")String time,
                                           @RequestParam(value = "second", required = false)String second,
                                           @RequestParam(value = "measurePointLocal", required = false)String measurePointLocal,
                                           @RequestParam(value = "sensorTypeName", required = false)String sensorTypeName,
                                           @RequestParam(value = "exceptionType", required = false)String exceptionType,
                                           @RequestParam(value = "warnValue", required = false)String warnValue){
        startPage();
        List<TSensorDataException> listMid=this.tSensorDataExceptionService.getWarnDayRecord(time, second, measurePointLocal, sensorTypeName, exceptionType, warnValue);
        List<TSensorDataException> listGroup=this.tSensorDataExceptionService.getWarnDayRecordGroup(time, second, measurePointLocal, sensorTypeName, exceptionType, warnValue);

        List<TSensorDataException> list=new ArrayList<>();
        for(TSensorDataException te:listGroup){
            te.setDuration(0);
            for(TSensorDataException tsde:listMid){
                if(te.getSensorTypeName().equals(tsde.getSensorTypeName())&&te.getMeasurePointLocal().equals(tsde.getMeasurePointLocal())){
                    te.setCount(te.getCount()+1);
                    te.setDuration(te.getDuration()+tsde.getDuration());
                }
            }
            list.add(te);
        }
        return getDataTable(list);
    }

    /**
     * 指定参数获取报警记录
     * @param time 月份
     * @param second 持续时长
     * @param measurePointLocal 测点名称
     * @param sensorTypeName 测点类型
     * @param exceptionType 异常类型
     * @param warnValue 最大值
     * @return 结果
     */
    @PostMapping("getWarnMonthRecord")
    public TableDataInfo getWarnMonthRecord(@RequestParam(value = "time")String time,
                                       @RequestParam(value = "second", required = false)String second,
                                       @RequestParam(value = "measurePointLocal", required = false)String measurePointLocal,
                                       @RequestParam(value = "sensorTypeName", required = false)String sensorTypeName,
                                       @RequestParam(value = "exceptionType", required = false)String exceptionType,
                                       @RequestParam(value = "warnValue", required = false)String warnValue) throws ParseException {
        startPage();
        return getDataTable(this.tSensorDataExceptionService.getWarnMonthRecord(time, second, measurePointLocal, sensorTypeName, exceptionType, warnValue));

    }

    /**
     * 指定参数获取报警统计
     * @param time 月份
     * @param second 持续时长
     * @param measurePointLocal 测点名称
     * @param sensorTypeName 测点类型
     * @param exceptionType 异常类型
     * @param warnValue 最大值
     * @return 结果
     */
    @PostMapping("getWarnMonthStatistics")
    public TableDataInfo getWarnMonthStatistics(@RequestParam(value = "time")String time,
                                           @RequestParam(value = "second", required = false)String second,
                                           @RequestParam(value = "measurePointLocal", required = false)String measurePointLocal,
                                           @RequestParam(value = "sensorTypeName", required = false)String sensorTypeName,
                                           @RequestParam(value = "exceptionType", required = false)String exceptionType,
                                           @RequestParam(value = "warnValue", required = false)String warnValue){
        startPage();
        List<TSensorDataException> listMid=this.tSensorDataExceptionService.getWarnMonthRecord(time, second, measurePointLocal, sensorTypeName, exceptionType, warnValue);
        List<TSensorDataException> listGroup=this.tSensorDataExceptionService.getWarnMonthRecordGroup(time, second, measurePointLocal, sensorTypeName, exceptionType, warnValue);

        List<TSensorDataException> list=new ArrayList<>();
        for(TSensorDataException te:listGroup){
            te.setDuration(0);
            for(TSensorDataException tsde:listMid){
                if(te.getSensorTypeName().equals(tsde.getSensorTypeName())&&te.getMeasurePointLocal().equals(tsde.getMeasurePointLocal())){
                    te.setCount(te.getCount()+1);
                    te.setDuration(te.getDuration()+tsde.getDuration());
                }
            }
            list.add(te);
        }
        return getDataTable(list);
    }
    
    

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TSensorDataException tSensorDataException) {
        List<TSensorDataException> list=this.tSensorDataExceptionService.selectTSensorDataExceptionList(tSensorDataException);
        ExcelUtil<TSensorDataException> util = new ExcelUtil<TSensorDataException>(TSensorDataException.class);
        util.exportExcel(response, list, "报警日记","日常报警数据");
    }

    /**
     * 获取系统设定好的
     * @return
     */
    @PostMapping("getMeasurePointWarnType")
    public AjaxResult getMeasurePointWarnType(){
        List<String> warnTypes=new ArrayList<>();
        EnumSet<MeasurePointWarnEnum> enumSet = EnumSet.allOf(MeasurePointWarnEnum.class);
        for(MeasurePointWarnEnum value: enumSet){
            warnTypes.add(value.getValue());
        }
        return AjaxResult.success(warnTypes);
    }

}

