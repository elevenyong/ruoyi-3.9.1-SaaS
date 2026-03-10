package com.ruoyi.hh.business.service.impl;

import java.util.List;

import com.ruoyi.hh.business.domain.TGasremnantStatistics;
import com.ruoyi.hh.business.mapper.TGasremnantStatisticsMapper;
import com.ruoyi.hh.business.service.ITGasremnantStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 残余瓦斯含量统计Service业务层处理
 * 
 * @author eleven
 * @date 2023-02-04
 */
@Service
public class TGasremnantStatisticsServiceImpl implements ITGasremnantStatisticsService
{
    @Autowired
    private TGasremnantStatisticsMapper tGasremnantStatisticsMapper;

    /**
     * 查询残余瓦斯含量统计
     * 
     * @param id 残余瓦斯含量统计主键
     * @return 残余瓦斯含量统计
     */
    @Override
    public TGasremnantStatistics selectTGasremnantStatisticsById(Integer id)
    {
        return tGasremnantStatisticsMapper.selectTGasremnantStatisticsById(id);
    }

    /**
     * 查询残余瓦斯含量统计列表
     * 
     * @param tGasremnantStatistics 残余瓦斯含量统计
     * @return 残余瓦斯含量统计
     */
    @Override
    public List<TGasremnantStatistics> selectTGasremnantStatisticsList(TGasremnantStatistics tGasremnantStatistics)
    {
        return tGasremnantStatisticsMapper.selectTGasremnantStatisticsList(tGasremnantStatistics);
    }

    /**
     * 新增残余瓦斯含量统计
     * 
     * @param tGasremnantStatistics 残余瓦斯含量统计
     * @return 结果
     */
    @Override
    public int insertTGasremnantStatistics(TGasremnantStatistics tGasremnantStatistics)
    {
        return tGasremnantStatisticsMapper.insertTGasremnantStatistics(tGasremnantStatistics);
    }

    /**
     * 修改残余瓦斯含量统计
     * 
     * @param tGasremnantStatistics 残余瓦斯含量统计
     * @return 结果
     */
    @Override
    public int updateTGasremnantStatistics(TGasremnantStatistics tGasremnantStatistics)
    {
        return tGasremnantStatisticsMapper.updateTGasremnantStatistics(tGasremnantStatistics);
    }

    /**
     * 批量删除残余瓦斯含量统计
     * 
     * @param ids 需要删除的残余瓦斯含量统计主键
     * @return 结果
     */
    @Override
    public int deleteTGasremnantStatisticsByIds(Integer[] ids)
    {
        return tGasremnantStatisticsMapper.deleteTGasremnantStatisticsByIds(ids);
    }

    /**
     * 删除残余瓦斯含量统计信息
     * 
     * @param id 残余瓦斯含量统计主键
     * @return 结果
     */
    @Override
    public int deleteTGasremnantStatisticsById(Integer id)
    {
        return tGasremnantStatisticsMapper.deleteTGasremnantStatisticsById(id);
    }

    /**
     * 根据工作面ID查询列表
     * @param workfaceid 工作面ID
     * @return 列表
     */
    public List<TGasremnantStatistics> getByworkfaceid(Integer workfaceid){
        return this.tGasremnantStatisticsMapper.getByworkfaceid(workfaceid);
    }
}
