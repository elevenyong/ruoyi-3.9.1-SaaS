package com.ruoyi.hh.business.service;

import com.ruoyi.hh.business.domain.TDrainagedataSum;

import java.util.List;

/**
 * 抽采单元抽采数据统计中间Service接口
 * 
 * @author eleven
 * @date 2023-08-07
 */
public interface ITDrainagedataSumService 
{
    /**
     * 查询抽采单元抽采数据统计中间
     * 
     * @param id 抽采单元抽采数据统计中间主键
     * @return 抽采单元抽采数据统计中间
     */
    public TDrainagedataSum selectTDrainagedataSumById(Integer id);

    /**
     * 查询抽采单元抽采数据统计中间列表
     * 
     * @param tBkDrainagedataSum 抽采单元抽采数据统计中间
     * @return 抽采单元抽采数据统计中间集合
     */
    public List<TDrainagedataSum> selectTDrainagedataSumList(TDrainagedataSum tBkDrainagedataSum);

    /**
     * 新增抽采单元抽采数据统计中间
     * 
     * @param tBkDrainagedataSum 抽采单元抽采数据统计中间
     * @return 结果
     */
    public int insertTDrainagedataSum(TDrainagedataSum tBkDrainagedataSum);

    /**
     * 修改抽采单元抽采数据统计中间
     * 
     * @param tBkDrainagedataSum 抽采单元抽采数据统计中间
     * @return 结果
     */
    public int updateTDrainagedataSum(TDrainagedataSum tBkDrainagedataSum);

    /**
     * 批量删除抽采单元抽采数据统计中间
     * 
     * @param ids 需要删除的抽采单元抽采数据统计中间主键集合
     * @return 结果
     */
    public int deleteTDrainagedataSumByIds(Integer[] ids);

    /**
     * 删除抽采单元抽采数据统计中间信息
     * 
     * @param id 抽采单元抽采数据统计中间主键
     * @return 结果
     */
    public int deleteTDrainagedataSumById(Integer id);
}
