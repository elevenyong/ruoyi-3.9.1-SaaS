package com.ruoyi.hh.physical.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.domain.TCoalUncover;
import com.ruoyi.hh.physical.mapper.TCoalUncoverMapper;
import com.ruoyi.hh.physical.service.ITCoalUncoverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 揭煤抽采信息Service业务层处理
 * 
 * @author eleven
 * @date 2022-12-09
 */
@Service
public class TCoalUncoverServiceImpl implements ITCoalUncoverService
{
    @Resource
    private TCoalUncoverMapper tCoalUncoverMapper;

    /**
     * 查询揭煤抽采信息
     * 
     * @param id 揭煤抽采信息主键
     * @return 揭煤抽采信息
     */
    @Override
    public TCoalUncover selectTCoalUncoverById(Integer id)
    {
        return tCoalUncoverMapper.selectTCoalUncoverById(id);
    }

    /**
     * 查询揭煤抽采信息列表
     * 
     * @param tCoalUncover 揭煤抽采信息
     * @return 揭煤抽采信息
     */
    @Override
    public List<TCoalUncover> selectTCoalUncoverList(TCoalUncover tCoalUncover)
    {
        return tCoalUncoverMapper.selectTCoalUncoverList(tCoalUncover);
    }

    /**
     * 新增揭煤抽采信息
     * 
     * @param tCoalUncover 揭煤抽采信息
     * @return 结果
     */
    @Override
    public int insertTCoalUncover(TCoalUncover tCoalUncover)
    {
        return tCoalUncoverMapper.insertTCoalUncover(tCoalUncover);
    }

    /**
     * 修改揭煤抽采信息
     * 
     * @param tCoalUncover 揭煤抽采信息
     * @return 结果
     */
    @Override
    public int updateTCoalUncover(TCoalUncover tCoalUncover)
    {
        return tCoalUncoverMapper.updateTCoalUncover(tCoalUncover);
    }

    /**
     * 批量删除揭煤抽采信息
     * 
     * @param ids 需要删除的揭煤抽采信息主键
     * @return 结果
     */
    @Override
    public int deleteTCoalUncoverByIds(Integer[] ids)
    {
        return tCoalUncoverMapper.deleteTCoalUncoverByIds(ids);
    }

    /**
     * 删除揭煤抽采信息信息
     * 
     * @param id 揭煤抽采信息主键
     * @return 结果
     */
    @Override
    public int deleteTCoalUncoverById(Integer id)
    {
        return tCoalUncoverMapper.deleteTCoalUncoverById(id);
    }
}
