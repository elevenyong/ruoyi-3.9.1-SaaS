package com.ruoyi.hh.business.mapper;

import java.util.List;

import com.ruoyi.hh.business.domain.TSensorData;

/**
 * 传感器数据Mapper接口
 *
 * @author eleven
 * @date 2022-07-26
 */
public interface TSensorDataMapper {
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
//     * 删除传感器数据
//     *
//     * @param id 传感器数据主键
//     * @return 结果
//     */
//    public Integer deleteTSensorDataById(Integer id);
//
//    /**
//     * 批量删除传感器数据
//     *
//     * @param ids 需要删除的数据主键集合
//     * @return 结果
//     */
//    public Integer deleteTSensorDataByIds(@Param("ids") List<Integer> ids);
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
//    public Integer delBySensorInfoIds(@Param("sensorInfoIds") List<Integer> sensorInfoIds);
//
//    /**
//     * 根据时间和测点ID查询瓦斯抽采日报表
//     * @param date 时间
//     * @param measurePointId 测点ID
//     * @return 列表
//     */
//    public List<TSensorData> getDailySheetByMeasurePointId(@Param("date") Date date
//            , @Param("measurePointId") Integer measurePointId
//    );
//
//    /**
//     * 根据测点ID查询时间段内的瓦斯抽采报表
//     * @param startDate 开始时间
//     * @param endDate 结束时间
//     * @param measurePointId 测点ID
//     * @return 列表
//     */
//    public List<TSensorData> getGasSheetDays(@Param("startDate") Date startDate,
//                                               @Param("endDate") Date endDate,
//                                               @Param("measurePointId") Integer measurePointId);
//
//    /**
//     * 根据测点ID查询月瓦斯抽采报表
//     * @param month 月
//     * @param measurePointId 测点ID
//     * @return 结果
//     */
//    public List<TSensorData> getGasSheetMonth(@Param("month") Date month,
//                                                @Param("measurePointId") Integer measurePointId);
//
//    /**
//     * 根据测点ID查询月瓦斯抽采报表
//     * @param year 年
//     * @param measurePointId 测点ID
//     * @return 结果
//     */
//    public List<TSensorData> getGasSheetYear(@Param("year") Date year,
//                                                @Param("measurePointId") Integer measurePointId);
//
//    /**
//     * 根据测点ID和传感器类型ID查询传感器数据
//     * @param measurepointids 测点ID
//     * @param sensortypeids 传感器ID
//     * @return 数据列表
//     */
//    public List<TSensorData> getDataByMeasurePointAndSensorType(
//            @Param("measurepointids")List<Integer> measurepointids,
//            @Param("sensortypeids")List<Integer> sensortypeids,
//            @Param("startdate")Date startdate,
//            @Param("enddate" )Date enddate
//    );
//
//    /**
//     * 根据测点统计级别查询传感器数据
//     * @param measurePointLevelId 测点统计级别
//     * @param workingFaceId 工作面ID
//     * @param startdate 开始时间
//     * @param enddate 结束时间
//     * @return
//     */
//    public List<TSensorData> getTotalFlowByMeasureLevel(@Param("measurePointLevelId")Integer measurePointLevelId,
//                                                        @Param("workingFaceId")Integer workingFaceId,
//                                                        @Param("sensortypeids")List<Integer> sensortypeids,
//                                                        @Param("startdate")Date startdate,
//                                                        @Param("enddate" )Date enddate
//                                                        );
//
//    /**
//     * 根据工作面ID和抽采单元ID查询时间段内的流量信息
//     * @param workFaceId 工作面id
//     * @param drainageUnitId 抽采单元ID
//     * @param startdate 开始时间
//     * @param enddate 结束时间
//     * @return
//     */
//    public List<TSensorData> getFlowByWorkFaceIdDUnitId(@Param("workFaceId")Integer workFaceId,
//                                                        @Param("drainageUnitId")Integer drainageUnitId,
//                                                        @Param("sensortypeids")List<Integer> sensortypeids,
//                                                        @Param("startdate")Date startdate,
//                                                        @Param("enddate" )Date enddate
//                                                        );
//
//    /**
//     * 根据测点ID查询该测点某个时间段内的流量信息
//     * @param measurPointId 测点ID
//     * @param sensortypeids 传感器类型ID
//     * @param startdate 开始时间
//     * @param enddate 结束时间
//     * @return
//     */
//    public List<TSensorData> getExtractionDataByMeasurePoint(@Param("measurPointId")  Integer measurPointId,
//                                                             @Param("sensortypeids")List<Integer> sensortypeids,
//                                                             @Param("startdate")Date startdate,
//                                                             @Param("enddate" )Date enddate
//    );
//
//    public List<TSensorData> getByInTime(@Param("startTime")Date startTime,
//                                         @Param("endTime" )Date endTime);

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
     * 删除传感器数据信息
     *
     * @param id 传感器数据信息主键
     * @return 结果
     */
    public int deleteTSensorDataById(Integer id);

    /**
     * 批量删除传感器数据信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTSensorDataByIds(Integer[] ids);

}
