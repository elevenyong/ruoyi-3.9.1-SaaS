package com.ruoyi.hh.business.service.impl;

import java.util.List;

import com.ruoyi.hh.business.domain.TSensorDataException;
import com.ruoyi.hh.business.mapper.TSensorDataExceptionMapper;
import com.ruoyi.hh.business.service.ITSensorDataExceptionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 异常报警Service业务层处理
 * 
 * @author eleven
 * @date 2023-05-15
 */
@Service
public class TSensorDataExceptionServiceImpl implements ITSensorDataExceptionService
{
    @Resource
    private TSensorDataExceptionMapper tSensorDataExceptionMapper;

    /**
     * 查询异常报警
     * 
     * @param id 异常报警主键
     * @return 异常报警
     */
    @Override
    public TSensorDataException selectTSensorDataExceptionById(Integer id)
    {
        return tSensorDataExceptionMapper.selectTSensorDataExceptionById(id);
    }

    /**
     * 查询异常报警列表
     * 
     * @param tSensorDataException 异常报警
     * @return 异常报警
     */
    @Override
    public List<TSensorDataException> selectTSensorDataExceptionList(TSensorDataException tSensorDataException)
    {
        return tSensorDataExceptionMapper.selectTSensorDataExceptionList(tSensorDataException);
    }

    /**
     * 新增异常报警
     * 
     * @param tSensorDataException 异常报警
     * @return 结果
     */
    @Override
    public int insertTSensorDataException(TSensorDataException tSensorDataException)
    {
        return tSensorDataExceptionMapper.insertTSensorDataException(tSensorDataException);
    }

    /**
     * 修改异常报警
     * 
     * @param tSensorDataException 异常报警
     * @return 结果
     */
    @Override
    public int updateTSensorDataException(TSensorDataException tSensorDataException)
    {
        return tSensorDataExceptionMapper.updateTSensorDataException(tSensorDataException);
    }

    /**
     * 批量删除异常报警
     * 
     * @param ids 需要删除的异常报警主键
     * @return 结果
     */
    @Override
    public int deleteTSensorDataExceptionByIds(Integer[] ids)
    {
        return tSensorDataExceptionMapper.deleteTSensorDataExceptionByIds(ids);
    }

    /**
     * 删除异常报警信息
     * 
     * @param id 异常报警主键
     * @return 结果
     */
    @Override
    public int deleteTSensorDataExceptionById(Integer id)
    {
        return tSensorDataExceptionMapper.deleteTSensorDataExceptionById(id);
    }

    /**
     *
     * @param time 时间
     * @param second 持续时长
     * @param measurePointLocal 测点名称
     * @param sensorTypeName 测点类型
     * @param exceptionType 异常类型
     * @param warnValue 最大值
     * @return
     */
    @Override
    public List<TSensorDataException> getWarnDayRecord(String time, String second,
                                                       String measurePointLocal,
                                                       String sensorTypeName,
                                                       String exceptionType,
                                                       String warnValue) {
        return this.tSensorDataExceptionMapper.getWarnDayRecord(time, second, measurePointLocal, sensorTypeName, exceptionType,warnValue);
    }
    public List<TSensorDataException> getWarnDayRecordGroup(String time, String second,
                                                            String measurePointLocal,
                                                            String sensorTypeName,
                                                            String exceptionType,
                                                            String warnValue){
        return this.tSensorDataExceptionMapper.getWarnDayRecordGroup(time, second, measurePointLocal, sensorTypeName, exceptionType, warnValue);
    }

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
                                                         String warnValue){
        return this.tSensorDataExceptionMapper.getWarnMonthRecord(time, second, measurePointLocal, sensorTypeName, exceptionType, warnValue);
    }
    public List<TSensorDataException> getWarnMonthRecordGroup(String time, String second,
                                                            String measurePointLocal,
                                                            String sensorTypeName,
                                                            String exceptionType,
                                                            String warnValue){
        return this.tSensorDataExceptionMapper.getWarnMonthRecordGroup(time, second, measurePointLocal, sensorTypeName, exceptionType, warnValue);
    }
}
