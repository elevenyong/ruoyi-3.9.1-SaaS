package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.hh.business.domain.TSensorData;
import com.ruoyi.hh.physical.domain.TMeasuringPoint;
import com.ruoyi.hh.physical.domain.TWorkingFace;
import com.ruoyi.hh.simulate.domain.DrainageUnitTotal;
import com.ruoyi.hh.physical.mapper.TWorkingFaceMapper;
import com.ruoyi.hh.physical.service.ITMeasuringPointService;
import com.ruoyi.hh.business.service.ITSensorDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (TSensorData)表控制层
 *
 * @author Eleven
 * @since 2022-07-26 16:40:40
 */
@RestController
@RequestMapping("tSensorData")
public class TSensorDataController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITSensorDataService tSensorDataService;
    @Resource
    private RedisCache redisCache;
    @Resource
    private TWorkingFaceMapper tWorkingFaceMapper;
    @Resource
    private ITMeasuringPointService itMeasuringPointService;


    /**
     * 获取列表
     *
     * @param tSensorData 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tSensorData:list')")
    public TableDataInfo selectAll(@RequestBody TSensorData tSensorData) {
        startPage();
        List<TSensorData> list = this.tSensorDataService.selectTSensorDataList(tSensorData);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tSensorData 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tSensorData:insert')")
    public void insertTSensorData(@RequestBody TSensorData tSensorData) {
        this.tSensorDataService.insertTSensorData(tSensorData);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tSensorData:insert')")
    public void insert(@RequestBody List<TSensorData> list) {
        if (list.size() > 0) {
            for (TSensorData tSensorData : list) {
                this.tSensorDataService.insertTSensorData(tSensorData);
            }
        }
        //this.tSensorDataService.insertTSensorDataList(list);
    }


    /**
     * 修改
     *
     * @param tSensorData 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTSensorData")
    //@PreAuthorize("@ss.hasPermi('tSensorData:updateTSensorData')")
    public AjaxResult updateTRoadway(@RequestBody TSensorData tSensorData) {
        return AjaxResult.success(this.tSensorDataService.updateTSensorData(tSensorData));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tSensorData:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tSensorDataService.selectTSensorDataById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTSensorDataById")
    //@PreAuthorize("@ss.hasPermi('tSensorData:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tSensorDataService.deleteTSensorDataById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTSensorDataByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tSensorDataService.deleteTSensorDataByIds(ids));
    }


//    /**
//     * 根据时间和测点ID查询瓦斯抽采日报表
//     * @param date 时间
//     * @param measurePointId 测点ID
//     * @return 列表
//     */
//    @PostMapping("getDailySheetByMeasurePointId")
//    public AjaxResult getDailySheetByMeasurePointId(@RequestParam Date date,
//                                                             @RequestParam List<Integer> measurePointId
//    ) throws ParseException {
//        List<ExtractionStatistic> list=new ArrayList<>();
//        if(measurePointId.size()>0){
//            for(Integer i:measurePointId){
//                list.add(this.tSensorDataService.getDailySheetByMeasurePointId(date
//                        , i));
//            }
//        }
//        return AjaxResult.success(list);
//    }
//
//    /**
//     * 根据测点ID查询时间段内的瓦斯抽采报表
//     * @param startDate 开始时间
//     * @param endDate 结束时间
//     * @param measurePointId 测点ID
//     * @return 列表
//     */
//    @PostMapping("getGasSheetDays")
//    public AjaxResult getGasSheetDays(@RequestParam Date startDate,
//                                      @RequestParam Date endDate,
//                                      @RequestParam List<Integer> measurePointId) throws ParseException {
//        List<ExtractionStatistic> list = new ArrayList<>();
//        if(measurePointId.size()>0){
//            for(Integer i:measurePointId){
//                ExtractionStatistic extractionStatistic=this.tSensorDataService.getGasSheetDays(startDate,endDate,i);
//                list.add(extractionStatistic);
//            }
//        }
//        return AjaxResult.success(list);
//    }
//
//    /**
//     * 根据测点ID查询月瓦斯抽采报表
//     * @param month 月
//     * @param measurePointId 测点ID列表
//     * @return 结果
//     */
//    @PostMapping("getGasSheetMonth")
//    public AjaxResult getGasSheetMonth(@RequestParam Date month,
//                                                @RequestParam List<Integer> measurePointId){
//        List<ExtractionStatistic> list = new ArrayList<>();
//        if(measurePointId.size()>0){
//            for(Integer i:measurePointId){
//                ExtractionStatistic extractionStatistic=this.tSensorDataService.getGasSheetMonth(month,i);
//                list.add(extractionStatistic);
//            }
//        }
//        return AjaxResult.success(list);
//    }
//
//    /**
//     * 根据测点ID查询月瓦斯抽采报表
//     * @param year 年
//     * @param measurePointId 测点ID列表
//     * @return 结果
//     */
//    @PostMapping("getGasSheetYear")
//    public AjaxResult getGasSheetYear(@RequestParam Date year,
//                                               @RequestParam List<Integer> measurePointId){
//        List<ExtractionStatistic> list = new ArrayList<>();
//        if(measurePointId.size()>0){
//            for(Integer i:measurePointId){
//                ExtractionStatistic extractionStatistic=this.tSensorDataService.getGasSheetYear(year,i);
//                list.add(extractionStatistic);
//            }
//        }
//        return AjaxResult.success(list);
//    }
//
//    /**
//     * 导出excel
//     *
//     * @param response res
//     */
//    @PostMapping("export")
//    public void export(HttpServletResponse response, @RequestBody TSensorData tSensorData) {
//        List<TSensorData> list=this.tSensorDataService.selectTSensorDataList(tSensorData);
//        ExcelUtil<TSensorData> util = new ExcelUtil<TSensorData>(TSensorData.class);
//        util.exportExcel(response, list, "传感器数据");
//    }
//
//
//    /**
//     * 根据叶节点统计累计抽采量
//     * @param workfaceId 工作面ID
//     * @return 累计抽采量
//     */
//    @PostMapping("getTotalFlowByLeafNode")
//    public DrainageUnitTotal getTotalFlowByLeafNode(@RequestParam(value="workfaceId",required = false) Integer workfaceId,
//                                                    @RequestParam(value="startdate",required = false) Date startdate,
//                                                    @RequestParam(value="enddate",required = false) Date enddate){
//        if(startdate!=null&&enddate==null){
//            enddate=new Date();
//        }
//        return this.tSensorDataService.getTotalFlowByLeafNode(workfaceId, startdate, enddate);
//    }


    /**
     * 根据测点级别ID和工作面ID及时间统计指定条件内的流量
     * @param measurePointLevelId 测点级别ID
     * @param workfaceId 工作面ID
     * @return 列表
     */
    @PostMapping("getTotalByMeasureLevelId")
    public DrainageUnitTotal getTotalByMeasureLevelId(@RequestParam(value="measurePointLevelId") Integer measurePointLevelId,
                                                      @RequestParam(value="workfaceId") Integer workfaceId){
        List<DrainageUnitTotal> list=redisCache.getCacheList("sensordata");
        for(DrainageUnitTotal drainageUnitTotal:list){
            if(workfaceId.equals(drainageUnitTotal.getWorkfaceid())){
                return drainageUnitTotal;
            }
        }
        return null;
    }

    /**
     * 根据测点ID查询测点的累计流量
     * @param measuringPointId
     * @return
     */
    @PostMapping("getTotalByMeasurePointName")
    public DrainageUnitTotal getTotalByMeasurePointName(@RequestParam(value="measuringPointId") Integer measuringPointId){
        TMeasuringPoint tMeasuringPoint=this.itMeasuringPointService.selectTMeasuringPointById(measuringPointId);
        if(tMeasuringPoint!=null&&tMeasuringPoint.getMeasuringPointName()!=null){
            return redisCache.getCacheObject(tMeasuringPoint.getMeasuringPointName());
        }
        return null;
    }




    @PostMapping("getData")
    public List<DrainageUnitTotal> getData(){

        return redisCache.getCacheList("sensordata");
    }

    @PostMapping("getworkfacesData")
    public List<DrainageUnitTotal> getworkfacesData(){
        List<TWorkingFace> workfaces=this.tWorkingFaceMapper.getPageList();
        List<DrainageUnitTotal> list=new ArrayList<>();
        for(TWorkingFace t:workfaces){
            list.addAll(redisCache.getCacheList(t.getWorkfaceName()));
        }
        return list;
    }

//    @PostMapping("getByInTime")
//    public List<TSensorData> getByInTime(Date startTime,
//                                        Date endTime){
//        return this.tSensorDataService.getByInTime(startTime, endTime);
//    }



}

