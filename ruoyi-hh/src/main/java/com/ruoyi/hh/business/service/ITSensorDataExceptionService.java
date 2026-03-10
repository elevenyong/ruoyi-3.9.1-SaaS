package com.ruoyi.hh.business.service;

import com.ruoyi.hh.business.domain.TSensorDataException;

import java.util.List;

/**
 * 异常报警Service接口
 * 
 * @author eleven
 * @date 2023-05-15
 */
public interface ITSensorDataExceptionService 
{
    /**
     * 查询异常报警
     * 
     * @param id 异常报警主键
     * @return 异常报警
     */
    public TSensorDataException selectTSensorDataExceptionById(Integer id);

    /**
     * 查询异常报警列表
     * 
     * @param tSensorDataException 异常报警
     * @return 异常报警集合
     */
    public List<TSensorDataException> selectTSensorDataExceptionList(TSensorDataException tSensorDataException);

    /**
     * 新增异常报警
     * 
     * @param tSensorDataException 异常报警
     * @return 结果
     */
    public int insertTSensorDataException(TSensorDataException tSensorDataException);

    /**
     * 修改异常报警
     * 
     * @param tSensorDataException 异常报警
     * @return 结果
     */
    public int updateTSensorDataException(TSensorDataException tSensorDataException);

    /**
     * 批量删除异常报警
     * 
     * @param ids 需要删除的异常报警主键集合
     * @return 结果
     */
    public int deleteTSensorDataExceptionByIds(Integer[] ids);

    /**
     * 删除异常报警信息
     * 
     * @param id 异常报警主键
     * @return 结果
     */
    public int deleteTSensorDataExceptionById(Integer id);

    /**
     * 指定参数获取报警统计
     * @param time 时间
     * @param second 持续时长
     * @param measurePointLocal 测点名称
     * @param sensorTypeName 测点类型
     * @param exceptionType 异常类型
     * @param warnValue 最大值
     * @return
     */
    public List<TSensorDataException> getWarnDayRecord(String time, String second,
                                                       String measurePointLocal,
                                                       String sensorTypeName,
                                                       String exceptionType,
                                                       String warnValue);
    public List<TSensorDataException> getWarnDayRecordGroup(String time,String second,
                                                            String measurePointLocal,
                                                            String sensorTypeName,
                                                            String exceptionType,
                                                            String warnValue
    );
    /**
     * 指定参数获取报警统计
     * @param time 时间
     * @param second 持续时长
     * @param measurePointLocal 测点名称
     * @param sensorTypeName 测点类型
     * @param exceptionType 异常类型
     * @param warnValue 最大值
     * @return
     */
    public List<TSensorDataException> getWarnMonthRecord(String time, String second,
                                                       String measurePointLocal,
                                                       String sensorTypeName,
                                                       String exceptionType,
                                                       String warnValue);
    public List<TSensorDataException> getWarnMonthRecordGroup(String time, String second,
                                                            String measurePointLocal,
                                                            String sensorTypeName,
                                                            String exceptionType,
                                                            String warnValue);
}
