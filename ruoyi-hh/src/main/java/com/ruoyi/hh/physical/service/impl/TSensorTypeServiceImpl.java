package com.ruoyi.hh.physical.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.domain.TSensorType;
import com.ruoyi.hh.physical.mapper.TSensorTypeMapper;
import com.ruoyi.hh.physical.service.ITSensorTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 传感器类型字典Service业务层处理
 * 
 * @author eleven
 * @date 2023-05-31
 */
@Service
public class TSensorTypeServiceImpl implements ITSensorTypeService
{
    @Resource
    private TSensorTypeMapper tSensorTypeMapper;

    /**
     * 查询传感器类型字典
     * 
     * @param id 传感器类型字典主键
     * @return 传感器类型字典
     */
    @Override
    public TSensorType selectTSensorTypeById(Integer id)
    {
        return tSensorTypeMapper.selectTSensorTypeById(id);
    }

    /**
     * 查询传感器类型字典列表
     * 
     * @param tSensorType 传感器类型字典
     * @return 传感器类型字典
     */
    @Override
    public List<TSensorType> selectTSensorTypeList(TSensorType tSensorType)
    {
        return tSensorTypeMapper.selectTSensorTypeList(tSensorType);
    }

    /**
     * 新增传感器类型字典
     * 
     * @param tSensorType 传感器类型字典
     * @return 结果
     */
    @Override
    public int insertTSensorType(TSensorType tSensorType)
    {
        return tSensorTypeMapper.insertTSensorType(tSensorType);
    }

    /**
     * 修改传感器类型字典
     * 
     * @param tSensorType 传感器类型字典
     * @return 结果
     */
    @Override
    public int updateTSensorType(TSensorType tSensorType)
    {
        return tSensorTypeMapper.updateTSensorType(tSensorType);
    }

    /**
     * 批量删除传感器类型字典
     * 
     * @param ids 需要删除的传感器类型字典主键
     * @return 结果
     */
    @Override
    public int deleteTSensorTypeByIds(Integer[] ids)
    {
        return tSensorTypeMapper.deleteTSensorTypeByIds(ids);
    }

    /**
     * 删除传感器类型字典信息
     * 
     * @param id 传感器类型字典主键
     * @return 结果
     */
    @Override
    public int deleteTSensorTypeById(Integer id)
    {
        return tSensorTypeMapper.deleteTSensorTypeById(id);
    }
}
