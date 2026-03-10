package com.ruoyi.hh.business.mapper;

import com.ruoyi.hh.business.domain.TBkSensorData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * 八矿传感器数据Mapper接口
 * 
 * @author eleven
 * @date 2023-06-02
 */
public interface TBkSensorDataMapper 
{
    /**
     * 查询八矿传感器数据
     * 
     * @param id 八矿传感器数据主键
     * @return 八矿传感器数据
     */
    public TBkSensorData selectTBkSensorDataById(Integer id);

    /**
     * 查询八矿传感器数据列表
     * 
     * @param tBkSensorData 八矿传感器数据
     * @return 八矿传感器数据集合
     */
    public List<TBkSensorData> selectTBkSensorDataList(TBkSensorData tBkSensorData);

    /**
     * 新增八矿传感器数据
     * 
     * @param tBkSensorData 八矿传感器数据
     * @return 结果
     */
    public int insertTBkSensorData(TBkSensorData tBkSensorData);

    /**
     * 修改八矿传感器数据
     * 
     * @param tBkSensorData 八矿传感器数据
     * @return 结果
     */
    public int updateTBkSensorData(TBkSensorData tBkSensorData);

    /**
     * 删除八矿传感器数据
     * 
     * @param id 八矿传感器数据主键
     * @return 结果
     */
    public int deleteTBkSensorDataById(Integer id);

    /**
     * 批量删除八矿传感器数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTBkSensorDataByIds(Integer[] ids);

//    /**
//     * 查询某一天某位置处的测点的最后一包数据
//     * @param time 某天
//     * @param measurePointLocal 某一测点位置
//     * @return 列表
//     */
//    public List<TBkSensorData> getLastDataSomeDay(@Param("time") String time,@Param("measurePointLocal")String measurePointLocal);
//
//    /**
//     * 查询某一天某位置处的测点的第一包数据
//     * @param time 某天
//     * @param measurePointLocal 某一测点位置
//     * @return 列表
//     */
//    public List<TBkSensorData> getFirstDataSomeDay(@Param("time") String time,@Param("measurePointLocal")String measurePointLocal);

    /**
     *获取某一天的第一包数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<TBkSensorData> getFirstDataSomeDay_Time(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("measurePointLocal")String measurePointLocal);

    /**
     * 获取某一天的最后一包数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<TBkSensorData> getLastDataSomeDay_Time(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("measurePointLocal")String measurePointLocal);

    /**
     * 获取某一天的所有数据
     * @param startTime
     * @param endTime
     * @param measurePointLocal
     * @return
     */
    public List<TBkSensorData> getDataSomeDay_All(@Param("startTime")Date startTime,@Param("endTime")Date endTime,
            @Param("measurePointLocal")String measurePointLocal);

    /**
     * 根据测点位置列表查询某一天中的数据
     * @param startTime
     * @param endTime
     * @param measurePointLocal
     * @return
     */
    public List<TBkSensorData> getDataByLocal_All(@Param("startTime")Date startTime,@Param("endTime")Date endTime
            ,@RequestParam("measurePointLocal")List<String> measurePointLocal);

    /**
     * 根据测点类型和测点编码查询历史曲线
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param measurePointCode 测点编码
     * @param sensorTypeName 测点类型名称
     * @return 结果
     */
    public List<TBkSensorData> getHistoricalCurve(@Param("startTime")Date startTime,@Param("endTime")Date endTime,
                                                  @Param("measurePointCode")String measurePointCode, @Param("sensorTypeName")String sensorTypeName);

    /**
     * 根据日期查询当天数据中的日累计统计测点位置
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> getDayLocal(@Param("startTime")Date startTime,@Param("endTime")Date endTime);

    /**
     * 根据日期查询当天数据中的月累计统计测点位置
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> getMonthLocal(@Param("startTime")Date startTime,@Param("endTime")Date endTime);
    /**
     * 根据日期查询当天数据中的瓦斯抽放量测点位置
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> getDrainageLocal(@Param("startTime")Date startTime,@Param("endTime")Date endTime);

    /**
     * 通过测点编码查询抽采历史记录中的瓦斯抽采月累计量
     * @param startTime 当前日期凌晨
     * @param endTime 当前时间
     * @param measurePointCode 测点编码
     * @return
     */
    @Select("select * from t_bk_sensor_data where measure_point_code= #{measurePointCode} and sensor_type_name = '瓦斯月累计纯量' and " +
            "time between #{startTime} and #{endTime} order by time desc limit 0,1")
    public TBkSensorData getGasScalarByMeasurePointCode(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("measurePointCode")String measurePointCode);

    /**
     * 通过测点编码查询历史记录中的瓦斯抽放量
     * @param startTime
     * @param endTime
     * @param measurePointCode
     * @return
     */
    @Select("select * from t_bk_sensor_data where measure_point_code= #{measurePointCode} and sensor_type_name = '瓦斯抽放量' and " +
            "time between #{startTime} and #{endTime} order by time desc limit 0,1")
    public TBkSensorData getdrainageQuantityByMeasurePointCode(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("measurePointCode")String measurePointCode);


}
