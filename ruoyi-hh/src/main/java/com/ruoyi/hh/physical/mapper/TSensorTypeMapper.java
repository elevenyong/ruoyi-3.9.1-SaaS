package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TSensorType;

import java.util.List;

/**
 * 传感器类型字典Mapper接口
 * 
 * @author eleven
 * @date 2023-05-31
 */
public interface TSensorTypeMapper 
{
    /**
     * 查询传感器类型字典
     * 
     * @param id 传感器类型字典主键
     * @return 传感器类型字典
     */
    public TSensorType selectTSensorTypeById(Integer id);

    /**
     * 查询传感器类型字典列表
     * 
     * @param tSensorType 传感器类型字典
     * @return 传感器类型字典集合
     */
    public List<TSensorType> selectTSensorTypeList(TSensorType tSensorType);

    /**
     * 新增传感器类型字典
     * 
     * @param tSensorType 传感器类型字典
     * @return 结果
     */
    public int insertTSensorType(TSensorType tSensorType);

    /**
     * 修改传感器类型字典
     * 
     * @param tSensorType 传感器类型字典
     * @return 结果
     */
    public int updateTSensorType(TSensorType tSensorType);

    /**
     * 删除传感器类型字典
     * 
     * @param id 传感器类型字典主键
     * @return 结果
     */
    public int deleteTSensorTypeById(Integer id);

    /**
     * 批量删除传感器类型字典
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTSensorTypeByIds(Integer[] ids);
}
