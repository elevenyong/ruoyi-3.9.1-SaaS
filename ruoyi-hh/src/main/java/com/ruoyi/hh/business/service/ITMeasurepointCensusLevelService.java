package com.ruoyi.hh.business.service;

import java.util.List;

import com.ruoyi.hh.business.domain.TMeasurepointCensusLevel;

/**
 * 测点统计级别统计工作面巷道等Service接口
 * 
 * @author eleven
 * @date 2022-10-18
 */
public interface ITMeasurepointCensusLevelService 
{
    /**
     * 查询测点统计级别统计工作面巷道等
     * 
     * @param id 测点统计级别统计工作面巷道等主键
     * @return 测点统计级别统计工作面巷道等
     */
    public TMeasurepointCensusLevel selectTMeasurepointCensusLevelById(Integer id);

    /**
     * 查询测点统计级别统计工作面巷道等列表
     * 
     * @param tMeasurepointCensusLevel 测点统计级别统计工作面巷道等
     * @return 测点统计级别统计工作面巷道等集合
     */
    public List<TMeasurepointCensusLevel> selectTMeasurepointCensusLevelList(TMeasurepointCensusLevel tMeasurepointCensusLevel);

    /**
     * 新增测点统计级别统计工作面巷道等
     * 
     * @param tMeasurepointCensusLevel 测点统计级别统计工作面巷道等
     * @return 结果
     */
    public int insertTMeasurepointCensusLevel(TMeasurepointCensusLevel tMeasurepointCensusLevel);

    /**
     * 修改测点统计级别统计工作面巷道等
     * 
     * @param tMeasurepointCensusLevel 测点统计级别统计工作面巷道等
     * @return 结果
     */
    public int updateTMeasurepointCensusLevel(TMeasurepointCensusLevel tMeasurepointCensusLevel);

    /**
     * 批量删除测点统计级别统计工作面巷道等
     * 
     * @param ids 需要删除的测点统计级别统计工作面巷道等主键集合
     * @return 结果
     */
    public int deleteTMeasurepointCensusLevelByIds(Integer[] ids);

    /**
     * 删除测点统计级别统计工作面巷道等信息
     * 
     * @param id 测点统计级别统计工作面巷道等主键
     * @return 结果
     */
    public int deleteTMeasurepointCensusLevelById(Integer id);
}
