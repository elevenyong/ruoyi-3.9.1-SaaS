package com.ruoyi.hh.business.service;

import com.ruoyi.hh.business.domain.MeasurePointData;
import com.ruoyi.hh.business.domain.TBkSensorData;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 八矿传感器数据Service接口
 * 
 * @author eleven
 * @date 2023-06-02
 */
public interface ITBkSensorDataService
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
     * 批量删除八矿传感器数据
     * 
     * @param ids 需要删除的八矿传感器数据主键集合
     * @return 结果
     */
    public int deleteTBkSensorDataByIds(Integer[] ids);

    /**
     * 删除八矿传感器数据信息
     * 
     * @param id 八矿传感器数据主键
     * @return 结果
     */
    public int deleteTBkSensorDataById(Integer id);

//    /**
//     * 查询某一天某位置处的测点的最后一包数据
//     * @param time 某天
//     * @param measurePointLocal 某一测点位置
//     * @return 列表
//     */
//    public List<TBkSensorData> getLastDataSomeDay(String time,String measurePointLocal);
//
//    /**
//     * 查询某一天某位置处的测点的第一包数据
//     * @param time 某天
//     * @param measurePointLocal 某一测点位置
//     * @return 列表
//     */
//    public List<TBkSensorData> getFirstDataSomeDay(String time,String measurePointLocal);

    /**
     * 获取某一天的第一包数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<TBkSensorData> getFirstDataSomeDay_Time(Date startTime,Date endTime,String measurePointLocal);

    /**
     * 获取某一天的最后一包数据
     * @param startTime
     * @param endTime
     * @return
     */
    public List<TBkSensorData> getLastDataSomeDay_Time(Date startTime,Date endTime,String measurePointLocal);

    /**
     * 获取某天的所有数据
     * @param startTime
     * @param endTime
     * @param measurePointLocal
     * @return
     */
    public List<TBkSensorData> getDataSomeDay_All(Date startTime,Date endTime,String measurePointLocal);
    /**
     * 根据测点位置列表查询某一天中的数据
     * @param startTime
     * @param endTime
     * @param measurePointLocal
     * @return
     */
    public List<TBkSensorData> getDataByLocal_All(Date startTime,Date endTime,
            List<String> measurePointLocal);

    /**
     * 根据测点类型和测点编码查询历史曲线
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param measurePointCode 测点编码
     * @param sensorTypeName 测点类型名称
     * @return 结果
     */
    public List<TBkSensorData> getHistoricalCurve(Date startTime,Date endTime,
                                                  String measurePointCode, String sensorTypeName);

    /**
     * 根据日期查询当天数据中的日累计统计测点位置
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> getDayLocal(Date startTime,Date endTime);

    /**
     * 根据日期查询当天数据中的月累计统计测点位置
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> getMonthLocal(Date startTime,Date endTime);

    /**
     * 根据日期查询当天数据中的瓦斯抽放量测点位置
     * @param startTime
     * @param endTime
     * @return
     */
    public List<String> getDrainageLocal(Date startTime, Date endTime);

    /**
     * 从抽采中间表中计算抽采累计表
     * @param date 指定某一天，格式：YYYY-MM-dd
     */
    public void fileStationDataSum(String date) throws ParseException;

    /**
     * 获取抽采数据累计列表
     * @return
     */
    public List<MeasurePointData> getMeasurePointData() throws ParseException;
}
