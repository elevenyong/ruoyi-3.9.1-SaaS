package com.ruoyi.hh.business.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.business.domain.TMeasurepointCensusLevel;
import com.ruoyi.hh.business.mapper.TMeasurepointCensusLevelMapper;
import com.ruoyi.hh.business.service.ITMeasurepointCensusLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 测点统计级别统计工作面巷道等Service业务层处理
 * 
 * @author eleven
 * @date 2022-10-18
 */
@Service
public class TMeasurepointCensusLevelServiceImpl implements ITMeasurepointCensusLevelService
{
    @Autowired
    private TMeasurepointCensusLevelMapper tMeasurepointCensusLevelMapper;

    /**
     * 查询测点统计级别统计工作面巷道等
     * 
     * @param id 测点统计级别统计工作面巷道等主键
     * @return 测点统计级别统计工作面巷道等
     */
    @Override
    public TMeasurepointCensusLevel selectTMeasurepointCensusLevelById(Integer id)
    {
        return tMeasurepointCensusLevelMapper.selectTMeasurepointCensusLevelById(id);
    }

    /**
     * 查询测点统计级别统计工作面巷道等列表
     * 
     * @param tMeasurepointCensusLevel 测点统计级别统计工作面巷道等
     * @return 测点统计级别统计工作面巷道等
     */
    @Override
    public List<TMeasurepointCensusLevel> selectTMeasurepointCensusLevelList(TMeasurepointCensusLevel tMeasurepointCensusLevel)
    {
        return tMeasurepointCensusLevelMapper.selectTMeasurepointCensusLevelList(tMeasurepointCensusLevel);
    }

    /**
     * 新增测点统计级别统计工作面巷道等
     * 
     * @param tMeasurepointCensusLevel 测点统计级别统计工作面巷道等
     * @return 结果
     */
    @Override
    public int insertTMeasurepointCensusLevel(TMeasurepointCensusLevel tMeasurepointCensusLevel)
    {
        tMeasurepointCensusLevel.setCreateTime(DateUtils.getNowDate());
        return tMeasurepointCensusLevelMapper.insertTMeasurepointCensusLevel(tMeasurepointCensusLevel);
    }

    /**
     * 修改测点统计级别统计工作面巷道等
     * 
     * @param tMeasurepointCensusLevel 测点统计级别统计工作面巷道等
     * @return 结果
     */
    @Override
    public int updateTMeasurepointCensusLevel(TMeasurepointCensusLevel tMeasurepointCensusLevel)
    {
        tMeasurepointCensusLevel.setUpdateTime(DateUtils.getNowDate());
        return tMeasurepointCensusLevelMapper.updateTMeasurepointCensusLevel(tMeasurepointCensusLevel);
    }

    /**
     * 批量删除测点统计级别统计工作面巷道等
     * 
     * @param ids 需要删除的测点统计级别统计工作面巷道等主键
     * @return 结果
     */
    @Override
    public int deleteTMeasurepointCensusLevelByIds(Integer[] ids)
    {
        return tMeasurepointCensusLevelMapper.deleteTMeasurepointCensusLevelByIds(ids);
    }

    /**
     * 删除测点统计级别统计工作面巷道等信息
     * 
     * @param id 测点统计级别统计工作面巷道等主键
     * @return 结果
     */
    @Override
    public int deleteTMeasurepointCensusLevelById(Integer id)
    {
        return tMeasurepointCensusLevelMapper.deleteTMeasurepointCensusLevelById(id);
    }
}
