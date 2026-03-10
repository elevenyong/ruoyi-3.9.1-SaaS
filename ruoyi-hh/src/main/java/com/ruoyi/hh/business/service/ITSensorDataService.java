package com.ruoyi.hh.business.service;

import java.util.List;

import com.ruoyi.hh.business.domain.TSensorData;

/**
 * 传感器数据Service接口
 *
 * @author eleven
 * @date 2022-07-26
 */
public interface ITSensorDataService {
//    /**
//     * 查询传感器数据
//     *
//     * @param id 传感器数据主键
//     * @return 传感器数据
//     */
//    public TSensorData selectTSensorDataById(Integer id);
//
//    /**
//     * 查询传感器数据列表
//     *
//     * @param tSensorData 传感器数据
//     * @return 传感器数据集合
//     */
//    public List<TSensorData> selectTSensorDataList(TSensorData tSensorData);
//
//    /**
//     * 新增传感器数据
//     *
//     * @param tSensorData 传感器数据
//     * @return 结果
//     */
//    public Integer insertTSensorData(TSensorData tSensorData);
//
//    /**
//     * 修改传感器数据
//     *
//     * @param tSensorData 传感器数据
//     * @return 结果
//     */
//    public Integer updateTSensorData(TSensorData tSensorData);
//
//    /**
//     * 批量删除传感器数据
//     *
//     * @param ids 需要删除的传感器数据主键集合
//     * @return 结果
//     */
//    public Integer deleteTSensorDataByIds(List<Integer> ids);
//
//    /**
//     * 删除传感器数据信息
//     *
//     * @param id 传感器数据主键
//     * @return 结果
//     */
//    public Integer deleteTSensorDataById(Integer id);
//
//    /**
//     * 获取所有数据
//     *
//     * @return 列表
//     */
//    public List<TSensorData> getPageList();
//
//    /**
//     * 根据传感器信息ID删除信息
//     * @param sensorInfoIds 传感器信息ID
//     * @return 结果
//     */
//    public Integer delBySensorInfoIds(List<Integer> sensorInfoIds);
//
//    /**
//     * 根据时间和测点ID查询瓦斯抽采日报表
//     * @param date 时间
//     * @param measurePointId 测点ID
//     * @return 结果
//     */
//    public ExtractionStatistic getDailySheetByMeasurePointId(@RequestParam Date date
//            , @RequestParam Integer measurePointId
//    ) throws ParseException;
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
//                                               Integer measurePointId) throws ParseException;
//    /**
//     * 根据测点ID查询月瓦斯抽采报表
//     * @param month 月
//     * @param measurePointId 测点ID
//     * @return 结果
//     */
//    public ExtractionStatistic getGasSheetMonth( Date month,
//                                                 Integer measurePointId);
//
//    /**
//     * 根据测点ID查询月瓦斯抽采报表
//     * @param year 年
//     * @param measurePointId 测点ID
//     * @return 结果
//     */
//    public ExtractionStatistic getGasSheetYear(@Param("year") Date year,
//                                               @Param("measurePointId") Integer measurePointId);
//
////    /**
////     * 根据测点ID和传感器类型ID查询传感器数据
////     * @param measurepointids 测点ID
////     * @param sensortypeids 传感器类型ID
////     * @param startdate 开始时间
////     * @param enddate 结束时间
////     * @return 数据列表
////     */
////    public List<TSensorData> getDataByMeasurePointAndSensorType(
////            List<Integer> measurepointids,
////            List<Integer> sensortypeids,
////            Date startdate,
////            Date enddate
////    );
//
////    /**
////     * 获取累计混合流量
////     * @return 混合流量
////     */
////    public List<DrainageUnitTotal> getTotalFlow(Integer workfaceid,Date startdate,Date enddate);
//
//    /**
//     * 根据叶节点统计累计抽采量
//     * @param workfaceid 工作面ID
//     * @return 累计抽采量
//     */
//    public DrainageUnitTotal getTotalFlowByLeafNode(Integer workfaceid,Date startdate,Date enddate);
//
////    /**
////     * 根据测点级别和时间获取指定的传感器数据列表
////     * @param measurePointLevelId 测点级别ID
////     * @param workingFaceId 要查询的工作面ID
////     * @param sensortypeids 传感器类别
////     * @param startdate 开始时间
////     * @param enddate 结束时间
////     * @return 传感器数据列表
////     */
////    public List<TSensorData> getTotalListByMeasureLevel(Integer measurePointLevelId,
////                                                        Integer workingFaceId,
////                                                        List<Integer> sensortypeids,
////                                                        Date startdate,
////                                                        Date enddate);
//    /**
//     * 根据测点级别和时间获取指定的传感器数据列表
//     * @param measurePointLevelId 测点级别ID
//     * @param workingFaceId 工作面ID
//     * @param startdate 开始时间
//     * @param enddate 结束时间
//     * @return 传感器数据列表
//     */
//    /*传感器关联测点，测点关联巷道，巷道关联工作面*/
//    public DrainageUnitTotal getTotalByMeasureLevelId(Integer measurePointLevelId,
//                                                      Integer workingFaceId,
//                                                      Date startdate,
//                                                      Date enddate);
//
//    /**
//     * 查询工作面内所有的抽采单元的流量情况
//     * @param workingFaceId
//     * @param startdate
//     * @param enddate
//     * @return
//     */
//    public List<DrainageUnitTotal> getDUFlowsInWorkFace(Integer workingFaceId,
//                                                                 Date startdate,
//                                                                 Date enddate);
//    /**
//     * 根据工作面ID和抽采单元ID查询时间段内的流量信息
//     * @param workingFaceId 工作面id
//     * @param drainageUnitId 抽采单元ID
//     * @param startdate 开始时间
//     * @param enddate 结束时间
//     * @return
//     */
//    public DrainageUnitTotal getFlowByWorkFaceIdDUnitId(Integer workingFaceId,
//                                                        Integer drainageUnitId,
//                                                        Date startdate,
//                                                        Date enddate
//    );
//
//    /**
//     * 根据测点ID查询单个测点的抽采量
//     * @param measurePointId 测点ID
//     * @param startdate 开始时间
//     * @param enddate 结束时间
//     * @return
//     */
//    public DrainageUnitTotal getExtractionDataByMeasurePoint(Integer measurePointId,
//                                                           Date startdate,
//                                                           Date enddate
//                                                           );
//
//    public List<TSensorData> getByInTime(Date startTime,
//                                         Date endTime);

    /**
     * 查询传感器数据信息
     *
     * @param id 传感器数据信息主键
     * @return 传感器数据信息
     */
    public TSensorData selectTSensorDataById(Integer id);

    /**
     * 查询传感器数据信息列表
     *
     * @param tSensorData 传感器数据信息
     * @return 传感器数据信息集合
     */
    public List<TSensorData> selectTSensorDataList(TSensorData tSensorData);

    /**
     * 新增传感器数据信息
     *
     * @param tSensorData 传感器数据信息
     * @return 结果
     */
    public int insertTSensorData(TSensorData tSensorData);

    /**
     * 修改传感器数据信息
     *
     * @param tSensorData 传感器数据信息
     * @return 结果
     */
    public int updateTSensorData(TSensorData tSensorData);

    /**
     * 批量删除传感器数据信息
     *
     * @param ids 需要删除的传感器数据信息主键集合
     * @return 结果
     */
    public int deleteTSensorDataByIds(Integer[] ids);

    /**
     * 删除传感器数据信息信息
     *
     * @param id 传感器数据信息主键
     * @return 结果
     */
    public int deleteTSensorDataById(Integer id);
}
