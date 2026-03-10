package com.ruoyi.hh.business.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.business.domain.TSensorData;
import com.ruoyi.hh.business.mapper.TSensorDataMapper;
import com.ruoyi.hh.business.service.ITSensorDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 传感器数据Service业务层处理
 *
 * @author eleven
 * @date 2022-07-26
 */
@Service
public class TSensorDataServiceImpl implements ITSensorDataService {
//    @Resource
//    private TSensorDataMapper tSensorDataMapper;
//    @Value("${sensorinfo.mixture}")
//    private Integer mixture;
//    @Value("${sensorinfo.pure}")
//    private Integer pure;
//    @Value("${sensorinfo.underpressure}")
//    private Integer underpressure;
//    @Value("${sensorinfo.concentration}")
//    private Integer concentration;
//    @Value("${sensorinfo.temperature}")
//    private Integer temperature;
//    /**取数值的频率（几分钟保存一包数据）*/
//    @Value("${sensorinfo.frequency}")
//    private Integer frequency;
//    @Value("${sensorinfo.workfacelevelid}")
//    private Integer workfacelevelid;
//    @Resource
//    private TDrainageUnitMapper tDrainageUnitMapper;
//    @Resource
//    private TMeasuringPointMapper tMeasuringPointMapper;
//    @Resource
//    private TWorkingFaceMapper tWorkingFaceMapper;
//
//
//    /**
//     * 查询传感器数据
//     *
//     * @param id 传感器数据主键
//     * @return 传感器数据
//     */
//    @Override
//    public TSensorData selectTSensorDataById(Integer id) {
//        return tSensorDataMapper.selectTSensorDataById(id);
//    }
//
//    /**
//     * 查询传感器数据列表
//     *
//     * @param tSensorData 传感器数据
//     * @return 传感器数据
//     */
//    @Override
//    public List<TSensorData> selectTSensorDataList(TSensorData tSensorData) {
//        return tSensorDataMapper.selectTSensorDataList(tSensorData);
//    }
//
//    /**
//     * 新增传感器数据
//     *
//     * @param tSensorData 传感器数据
//     * @return 结果
//     */
//    @Override
//    public Integer insertTSensorData(TSensorData tSensorData) {
//        return tSensorDataMapper.insertTSensorData(tSensorData);
//    }
//
//    /**
//     * 修改传感器数据
//     *
//     * @param tSensorData 传感器数据
//     * @return 结果
//     */
//    @Override
//    public Integer updateTSensorData(TSensorData tSensorData) {
//        return tSensorDataMapper.updateTSensorData(tSensorData);
//    }
//
//    /**
//     * 批量删除传感器数据
//     *
//     * @param ids 需要删除的传感器数据主键
//     * @return 结果
//     */
//    @Override
//    public Integer deleteTSensorDataByIds(List<Integer> ids) {
//        return tSensorDataMapper.deleteTSensorDataByIds(ids);
//    }
//
//    /**
//     * 删除传感器数据信息
//     *
//     * @param id 传感器数据主键
//     * @return 结果
//     */
//    @Override
//    public Integer deleteTSensorDataById(Integer id) {
//        return tSensorDataMapper.deleteTSensorDataById(id);
//    }
//
//    /**
//     * 获取所有信息
//     *
//     * @return 列表
//     */
//    @Override
//    public List<TSensorData> getPageList() {
//        return this.tSensorDataMapper.getPageList();
//    }
//
//    /**
//     * 根据传感器信息ID删除信息
//     * @param sensorInfoIds 传感器信息ID
//     * @return 结果
//     */
//    @Override
//    public Integer delBySensorInfoIds(List<Integer> sensorInfoIds) {
//        return this.tSensorDataMapper.delBySensorInfoIds(sensorInfoIds);
//    }
//
//    /**
//     * 根据时间和测点ID查询瓦斯抽采日报表
//     * @param date 时间
//     * @param measurePointId 测点ID
//     * @return 列表
//     */
//    @Override
//    public ExtractionStatistic getDailySheetByMeasurePointId(Date date
//            , Integer measurePointId
//    ) throws ParseException {
//        //某测点的某时间段内的传感器信息
//        List<TSensorData> list=this.tSensorDataMapper.getDailySheetByMeasurePointId(date, measurePointId);
//        return getExtractionBySensorList(list);
//    }
//
//    /**
//     * 根据测点ID查询时间段内的瓦斯抽采报表
//     * @param startDate 开始时间
//     * @param endDate 结束时间
//     * @param measurePointId 测点ID
//     * @return 列表
//     */
//    public ExtractionStatistic getGasSheetDays(Date startDate,
//                                               Date endDate,
//                                               Integer measurePointId) throws ParseException {
//        List<TSensorData> list= this.tSensorDataMapper.getGasSheetDays(startDate,
//                endDate,
//                measurePointId
//                );
//
//        return getExtractionBySensorList(list);
//    }
//
//    /**
//     * 根据测点ID查询月瓦斯抽采报表
//     * @param month 月
//     * @param measurePointId 测点ID列表
//     * @return 结果
//     */
//    public ExtractionStatistic getGasSheetMonth(Date month,
//                                                Integer measurePointId){
//        List<TSensorData> list= this.tSensorDataMapper.getGasSheetMonth(month, measurePointId);
//        return getExtractionBySensorList(list);
//    }
//
//    /**
//     * 根据测点ID查询月瓦斯抽采报表
//     * @param year 年
//     * @param measurePointId 测点ID列表
//     * @return 结果
//     */
//    public ExtractionStatistic getGasSheetYear(@Param("year") Date year,
//                                               @Param("measurePointId") Integer measurePointId){
//        List<TSensorData> list= this.tSensorDataMapper.getGasSheetYear(year, measurePointId);
//        return getExtractionBySensorList(list);
//    }
//
//    /**
//     * 根据测点ID和传感器类型ID查询传感器数据
//     * @param measurepointids 测点ID
//     * @param sensortypeids 传感器类型ID
//     * @param startdate 开始时间
//     * @param enddate 结束时间
//     * @return 数据列表
//     */
//    public List<TSensorData> getDataByMeasurePointAndSensorType(
//            List<Integer> measurepointids,
//            List<Integer> sensortypeids,
//            Date startdate,
//            Date enddate
//    ){
//        return this.tSensorDataMapper.getDataByMeasurePointAndSensorType(measurepointids, sensortypeids,startdate,enddate);
//    }
//
//
//    /**
//     * 根据叶节点统计累计抽采量
//     * @param workfaceid 工作面ID
//     * @param startdate 开始时间
//     * @param enddate 结束时间
//     * @return
//     */
//    public DrainageUnitTotal getTotalFlowByLeafNode(Integer workfaceid,Date startdate,Date enddate){
//        //工作面名称
//        String workfance=this.tWorkingFaceMapper.getWorefaceNameById(workfaceid);
//        //测点列表
//        List<Integer> measuringPointIds=this.tMeasuringPointMapper.getLeafNodeByWorkFaceId(workfaceid);
//        //类型为流量的传感器类型
//        List<Integer> types=new ArrayList<>();
//        types.add(mixture);
//        types.add(pure);
//        long mixtureTotal=0;
//        long pureflow=0;
//        DrainageUnitTotal drainageUnitTotal=new DrainageUnitTotal();
//        if(measuringPointIds.size()>0){
//            List<TSensorData> tSensorDataMixList=getDataByMeasurePointAndSensorType(measuringPointIds,types,startdate,enddate);
//            drainageUnitTotal=getMixPureFlow(tSensorDataMixList);
//        }
//        drainageUnitTotal.setWorkfaceid(workfaceid);
//        drainageUnitTotal.setWorkfacename(workfance);
//        if(enddate==null){
//            drainageUnitTotal.setUpToDate(new Date());
//        }else{
//            drainageUnitTotal.setUpToDate(enddate);
//        }
//        drainageUnitTotal.setTotalmixtureflow(mixtureTotal);
//        drainageUnitTotal.setTotalpureflow(pureflow);
//        return drainageUnitTotal;
//    }
//
//
//
//    /**
//     *根据工作面ID和测点级别ID及时间查询要统计的累计流量
//     * @param measurePointLevelId
//     * @param workingFaceId
//     * @param startdate
//     * @param enddate
//     * @return
//     */
//    /*传感器关联测点，测点关联巷道，巷道关联工作面*/
//    public DrainageUnitTotal getTotalByMeasureLevelId(Integer measurePointLevelId,
//                                                      Integer workingFaceId,
//                                                      Date startdate,
//                                                      Date enddate){
//        String workfance=this.tWorkingFaceMapper.getWorefaceNameById(workingFaceId);
//        List<Integer> types=new ArrayList<>();
//        //只取混合流量和纯流量
//        types.add(mixture);
//        types.add(pure);
//        List<Integer> measuringPointIds=this.tMeasuringPointMapper.getByWorkFaceIdAndMeasureLevel(measurePointLevelId, workingFaceId);
//        DrainageUnitTotal drainageUnitTotal=new DrainageUnitTotal();
//        if(measuringPointIds.size()>0) {
//            List<TSensorData> tSensorDataFlowList = getDataByMeasurePointAndSensorType(measuringPointIds, types, startdate, enddate);
//            drainageUnitTotal=getMixPureFlow(tSensorDataFlowList);
//        }
//        drainageUnitTotal.setWorkfaceid(workingFaceId);
//        drainageUnitTotal.setWorkfacename(workfance);
//        if(enddate==null){
//            drainageUnitTotal.setUpToDate(new Date());
//        }else{
//            drainageUnitTotal.setUpToDate(enddate);
//        }
//        return drainageUnitTotal;
//    }
//
//    /**
//     * 根据工作面ID查询时间段内工作面所有抽采单元的流量
//     * @param workingFaceId
//     * @param startdate
//     * @param enddate
//     * @return
//     */
//    public List<DrainageUnitTotal> getDUFlowsInWorkFace(Integer workingFaceId,
//                                                           Date startdate,
//                                                           Date enddate){
//        //得到工作面名称
//        String workfance=this.tWorkingFaceMapper.getWorefaceNameById(workingFaceId);
//        // 得到该工作面的所有抽采单元
//        List<TDrainageUnit> list=this.tDrainageUnitMapper.getByWorkfaceId(workingFaceId);
//        List<DrainageUnitTotal> drainageUnitTotals=new ArrayList<>();
//        List<Integer> types=new ArrayList<>();
//        //只取混合流量和纯流量
//        types.add(mixture);
//        types.add(pure);
//        if(list!=null) {
//            for (TDrainageUnit tDrainageUnit : list) {
//                DrainageUnitTotal drainageUnitTotal=new DrainageUnitTotal();
//                //得到抽采单元中的各个测点
//                List<Integer> tMeasuringPointIds=this.tMeasuringPointMapper.getMeasureIdByUnitId(tDrainageUnit.getId());
//                if(tMeasuringPointIds.size()>0){
//                    List<TSensorData> tSensorDataMixList=getDataByMeasurePointAndSensorType(tMeasuringPointIds,types,startdate,enddate);
//                    drainageUnitTotal=getMixPureFlow(tSensorDataMixList);
//                }
//                drainageUnitTotal.setWorkfaceid(tDrainageUnit.getWorkfaceid());
//                drainageUnitTotal.setWorkfacename(workfance);
//                drainageUnitTotal.setDrainageunitname(tDrainageUnit.getUnitname());
//                drainageUnitTotal.setDrainageunitid(tDrainageUnit.getId());
//                if(enddate==null){
//                    drainageUnitTotal.setUpToDate(new Date());
//                }else {
//                    drainageUnitTotal.setUpToDate(enddate);
//                }
//                drainageUnitTotals.add(drainageUnitTotal);
//            }
//        }
//        return drainageUnitTotals;
//    }
//
//    /**
//     * 根据工作面ID和抽采单元ID查询时间段内的流量信息
//     * @param workingFaceId 工作面id
//     * @param drainageUnitId 抽采单元ID
//     * @param startdate 开始时间
//     * @param enddate 结束时间
//     * @return
//     */
//    public DrainageUnitTotal getFlowByWorkFaceIdDUnitId(@Param("workingFaceId")Integer workingFaceId,
//                                                        @Param("drainageUnitId")Integer drainageUnitId,
//                                                        @Param("startdate")Date startdate,
//                                                        @Param("enddate" )Date enddate
//    ){
//        List<Integer> types=new ArrayList<>();
//        //只取混合流量和纯流量
//        types.add(mixture);
//        types.add(pure);
//        //得到工作面名称
//        String workfance=this.tWorkingFaceMapper.getWorefaceNameById(workingFaceId);
//        DrainageUnitTotal drainageUnitTotal=new DrainageUnitTotal();
//        List<TSensorData> tSensorDataMixList=this.tSensorDataMapper.getFlowByWorkFaceIdDUnitId(workingFaceId, drainageUnitId,types, startdate, enddate);
//        drainageUnitTotal=getMixPureFlow(tSensorDataMixList);
//        drainageUnitTotal.setWorkfaceid(workingFaceId);
//        drainageUnitTotal.setWorkfacename(workfance);
//        drainageUnitTotal.setDrainageunitid(drainageUnitId);
//        if(enddate==null){
//            drainageUnitTotal.setUpToDate(new Date());
//        }else {
//            drainageUnitTotal.setUpToDate(enddate);
//        }
//        return drainageUnitTotal;
//    }
//    /**
//     * 根据测点ID查询单个测点的抽采量
//     * @param measurePointId 测点ID
//     * @param startdate 开始时间
//     * @param enddate 结束时间
//     * @return
//     */
//    @Override
//    public DrainageUnitTotal getExtractionDataByMeasurePoint(Integer measurePointId, Date startdate, Date enddate) {
//        List<Integer> types=new ArrayList<>();
//        //只取混合流量和纯流量
//        types.add(mixture);
//        types.add(pure);
//        //查询到时间段内符合传感器类型的所有数据
//        List<TSensorData> sensorDataList=this.tSensorDataMapper.getExtractionDataByMeasurePoint(measurePointId,types,startdate,enddate);
//        DrainageUnitTotal drainageUnitTotal=new DrainageUnitTotal();
//        if(sensorDataList.size()>0){
//            //根据数据统计累计混合流量和累计纯流量
//            drainageUnitTotal=getMixPureFlow(sensorDataList);
//        }
//        if(enddate==null){
//            drainageUnitTotal.setUpToDate(new Date());
//        }else {
//            drainageUnitTotal.setUpToDate(enddate);
//        }
//        return drainageUnitTotal;
//    }
//
//    @Override
//    public List<TSensorData> getByInTime(Date startTime, Date endTime) {
//        return this.tSensorDataMapper.getByInTime(startTime,endTime);
//    }
//
//    /**
//     * 给定数量的传感器数据，统计混合流量和纯流量
//     * @param tSensorDatas 传感器数据列表
//     * @return
//     */
//    public DrainageUnitTotal getMixPureFlow(List<TSensorData> tSensorDatas){
//        DrainageUnitTotal drainageUnitTotal=new DrainageUnitTotal();
//        long mixtureTotal=0; //混合流量
//        long pureflow=0; //纯流量
//        if(tSensorDatas.size()>0){
//            for(TSensorData tSensorData:tSensorDatas){
//                if(tSensorData.getSensortypeid().equals(mixture)){
//                    mixtureTotal+=tSensorData.getAvgvalue()*frequency;
//                }else{
//                    pureflow+=tSensorData.getAvgvalue()*frequency;
//                }
//            }
//        }
//        drainageUnitTotal.setTotalmixtureflow(mixtureTotal);
//        drainageUnitTotal.setTotalpureflow(pureflow);
//        return drainageUnitTotal;
//    }
//
//    /**
//     * 根据传感器数据计算各类计量器数据
//     * @param list
//     * @return
//     */
//    public ExtractionStatistic getExtractionBySensorList(List<TSensorData> list){
//        ExtractionStatistic extractionStatistic=new ExtractionStatistic();
//        double mixtureValue=0.0;//混合流量
//        double pureValue=0.0;//纯流量
//        double underpressureValue=0.0;//负压
//        double concentrationValue=0.0;//浓度
//        double temperatureValue=0.0; //温度
//        int m=0;int p=0;int u=0;int c=0;int t=0;
//        if(list.size()>0){
//            for(TSensorData tSensorData:list){
//                if(mixture==tSensorData.getSensortypeid()){
//                    mixtureValue+=tSensorData.getAvgvalue()*frequency;
//                    m++;
//                }else if(pure==tSensorData.getSensortypeid()){
//                    pureValue+=tSensorData.getAvgvalue()*frequency;
//                    p++;
//                }else if(underpressure==tSensorData.getSensortypeid()){
//                    underpressureValue+=tSensorData.getAvgvalue();
//                    u++;
//                }else if(concentration==tSensorData.getSensortypeid()){
//                    concentrationValue+=tSensorData.getAvgvalue();
//                    c++;
//                }else if(temperature==tSensorData.getSensortypeid()){
//                    temperatureValue+=tSensorData.getAvgvalue();
//                    t++;
//                }
//                if(extractionStatistic.getMeasuringpointname()==null){
//                    extractionStatistic.setWorkingfacename(tSensorData.getWorkingfacename());
//                    extractionStatistic.setDrainageunitname(tSensorData.getUnitname());
//                    extractionStatistic.setMeasuringpointname(tSensorData.getMeasuringpointname());
//                    extractionStatistic.setSensortypeid(tSensorData.getSensortypeid());
//                    extractionStatistic.setRoadway(tSensorData.getRoadway());
//                }
//            }
//            extractionStatistic.setMixtureflow(mixtureValue);
//            extractionStatistic.setPureflow(pureValue);
//            extractionStatistic.setKpa(underpressureValue/u);
//            extractionStatistic.setConcentration(concentrationValue/c);
//            extractionStatistic.setTemperature(temperatureValue/t);
//        }
//        return extractionStatistic;
//    }

    @Resource
    private TSensorDataMapper tSensorDataMapper;

    /**
     * 查询传感器数据信息
     *
     * @param id 传感器数据信息主键
     * @return 传感器数据信息
     */
    @Override
    public TSensorData selectTSensorDataById(Integer id)
    {
        return tSensorDataMapper.selectTSensorDataById(id);
    }

    /**
     * 查询传感器数据信息列表
     *
     * @param tSensorData 传感器数据信息
     * @return 传感器数据信息
     */
    @Override
    public List<TSensorData> selectTSensorDataList(TSensorData tSensorData)
    {
        return tSensorDataMapper.selectTSensorDataList(tSensorData);
    }

    /**
     * 新增传感器数据信息
     *
     * @param tSensorData 传感器数据信息
     * @return 结果
     */
    @Override
    public int insertTSensorData(TSensorData tSensorData)
    {
        tSensorData.setCreateTime(DateUtils.getNowDate());
        return tSensorDataMapper.insertTSensorData(tSensorData);
    }

    /**
     * 修改传感器数据信息
     *
     * @param tSensorData 传感器数据信息
     * @return 结果
     */
    @Override
    public int updateTSensorData(TSensorData tSensorData)
    {
        tSensorData.setUpdateTime(DateUtils.getNowDate());
        return tSensorDataMapper.updateTSensorData(tSensorData);
    }

    /**
     * 批量删除传感器数据信息
     *
     * @param ids 需要删除的传感器数据信息主键
     * @return 结果
     */
    @Override
    public int deleteTSensorDataByIds(Integer[] ids)
    {
        return tSensorDataMapper.deleteTSensorDataByIds(ids);
    }

    /**
     * 删除传感器数据信息信息
     *
     * @param id 传感器数据信息主键
     * @return 结果
     */
    @Override
    public int deleteTSensorDataById(Integer id)
    {
        return tSensorDataMapper.deleteTSensorDataById(id);
    }



}
