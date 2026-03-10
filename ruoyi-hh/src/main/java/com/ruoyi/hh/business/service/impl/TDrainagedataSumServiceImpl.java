package com.ruoyi.hh.business.service.impl;

import com.ruoyi.hh.business.domain.TDrainagedataSum;
import com.ruoyi.hh.business.mapper.TDrainagedataSumMapper;
import com.ruoyi.hh.business.service.ITDrainagedataSumService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 抽采单元抽采数据统计中间Service业务层处理
 * 
 * @author eleven
 * @date 2023-08-07
 */
@Service
public class TDrainagedataSumServiceImpl implements ITDrainagedataSumService 
{
    @Resource
    private TDrainagedataSumMapper tDrainagedataSumMapper;

    /**
     * 查询抽采单元抽采数据统计中间
     * 
     * @param id 抽采单元抽采数据统计中间主键
     * @return 抽采单元抽采数据统计中间
     */
    @Override
    public TDrainagedataSum selectTDrainagedataSumById(Integer id)
    {
        return tDrainagedataSumMapper.selectTDrainagedataSumById(id);
    }

    /**
     * 查询抽采单元抽采数据统计中间列表
     * 
     * @param tDrainagedataSum 抽采单元抽采数据统计中间
     * @return 抽采单元抽采数据统计中间
     */
    @Override
    public List<TDrainagedataSum> selectTDrainagedataSumList(TDrainagedataSum tDrainagedataSum)
    {
        return tDrainagedataSumMapper.selectTDrainagedataSumList(tDrainagedataSum);
    }

    /**
     * 新增抽采单元抽采数据统计中间
     * 
     * @param tDrainagedataSum 抽采单元抽采数据统计中间
     * @return 结果
     */
    @Override
    public int insertTDrainagedataSum(TDrainagedataSum tDrainagedataSum)
    {
        return tDrainagedataSumMapper.insertTDrainagedataSum(tDrainagedataSum);
    }

    /**
     * 修改抽采单元抽采数据统计中间
     * 
     * @param tDrainagedataSum 抽采单元抽采数据统计中间
     * @return 结果
     */
    @Override
    public int updateTDrainagedataSum(TDrainagedataSum tDrainagedataSum)
    {
        return tDrainagedataSumMapper.updateTDrainagedataSum(tDrainagedataSum);
    }

    /**
     * 批量删除抽采单元抽采数据统计中间
     * 
     * @param ids 需要删除的抽采单元抽采数据统计中间主键
     * @return 结果
     */
    @Override
    public int deleteTDrainagedataSumByIds(Integer[] ids)
    {
        return tDrainagedataSumMapper.deleteTDrainagedataSumByIds(ids);
    }

    /**
     * 删除抽采单元抽采数据统计中间信息
     * 
     * @param id 抽采单元抽采数据统计中间主键
     * @return 结果
     */
    @Override
    public int deleteTDrainagedataSumById(Integer id)
    {
        return tDrainagedataSumMapper.deleteTDrainagedataSumById(id);
    }
}
