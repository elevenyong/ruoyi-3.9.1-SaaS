package com.ruoyi.hh.business.mapper;

import java.util.List;

import com.ruoyi.hh.business.domain.TSensorDataException;
import org.apache.ibatis.annotations.Param;

/**
 * 异常报警Mapper接口
 * 
 * @author eleven
 * @date 2023-05-15
 */
public interface TSensorDataExceptionMapper 
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
     * 删除异常报警
     * 
     * @param id 异常报警主键
     * @return 结果
     */
    public int deleteTSensorDataExceptionById(Integer id);

    /**
     * 批量删除异常报警
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTSensorDataExceptionByIds(Integer[] ids);

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
    public List<TSensorDataException> getWarnDayRecord(@Param("time") String time, @Param("second") String second,
                                                       @Param("measurePointLocal") String measurePointLocal,
                                                       @Param("sensorTypeName") String sensorTypeName,
                                                       @Param("exceptionType") String exceptionType,
                                                       @Param("warnValue") String warnValue
    );
    public List<TSensorDataException> getWarnDayRecordGroup(@Param("time") String time, @Param("second") String second,
                                                       @Param("measurePointLocal") String measurePointLocal,
                                                       @Param("sensorTypeName") String sensorTypeName,
                                                       @Param("exceptionType") String exceptionType,
                                                       @Param("warnValue") String warnValue
    );
    /**
     * 指定参数获取报警统计
     * @param time 月
     * @param second 持续时长
     * @param measurePointLocal 测点名称
     * @param sensorTypeName 测点类型
     * @param exceptionType 异常类型
     * @param warnValue 最大值
     * @return
     */
    public List<TSensorDataException> getWarnMonthRecord(@Param("time") String time, @Param("second") String second,
                                                       @Param("measurePointLocal") String measurePointLocal,
                                                       @Param("sensorTypeName") String sensorTypeName,
                                                       @Param("exceptionType") String exceptionType,
                                                       @Param("warnValue") String warnValue
    );
    public List<TSensorDataException> getWarnMonthRecordGroup(@Param("time") String time, @Param("second") String second,
                                                            @Param("measurePointLocal") String measurePointLocal,
                                                            @Param("sensorTypeName") String sensorTypeName,
                                                            @Param("exceptionType") String exceptionType,
                                                            @Param("warnValue") String warnValue
    );
}
