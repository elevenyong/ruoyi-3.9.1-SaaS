package com.ruoyi.hh.business.mapper;

import java.util.List;

import com.ruoyi.hh.business.domain.TGasremnantStatistics;

/**
 * 残余瓦斯含量统计Mapper接口
 * 
 * @author eleven
 * @date 2023-02-04
 */
public interface TGasremnantStatisticsMapper 
{
    /**
     * 查询残余瓦斯含量统计
     * 
     * @param id 残余瓦斯含量统计主键
     * @return 残余瓦斯含量统计
     */
    public TGasremnantStatistics selectTGasremnantStatisticsById(Integer id);

    /**
     * 查询残余瓦斯含量统计列表
     * 
     * @param tGasremnantStatistics 残余瓦斯含量统计
     * @return 残余瓦斯含量统计集合
     */
    public List<TGasremnantStatistics> selectTGasremnantStatisticsList(TGasremnantStatistics tGasremnantStatistics);

    /**
     * 新增残余瓦斯含量统计
     * 
     * @param tGasremnantStatistics 残余瓦斯含量统计
     * @return 结果
     */
    public int insertTGasremnantStatistics(TGasremnantStatistics tGasremnantStatistics);

    /**
     * 修改残余瓦斯含量统计
     * 
     * @param tGasremnantStatistics 残余瓦斯含量统计
     * @return 结果
     */
    public int updateTGasremnantStatistics(TGasremnantStatistics tGasremnantStatistics);

    /**
     * 删除残余瓦斯含量统计
     * 
     * @param id 残余瓦斯含量统计主键
     * @return 结果
     */
    public int deleteTGasremnantStatisticsById(Integer id);

    /**
     * 批量删除残余瓦斯含量统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTGasremnantStatisticsByIds(Integer[] ids);

    /**
     * 根据工作面ID查询列表
     * @param workfaceid 工作面ID
     * @return 列表
     */
    public List<TGasremnantStatistics> getByworkfaceid(Integer workfaceid);
}
