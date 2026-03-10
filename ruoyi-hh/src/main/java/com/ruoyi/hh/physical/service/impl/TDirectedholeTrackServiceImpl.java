package com.ruoyi.hh.physical.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.domain.TDirectedholeTrack;
import com.ruoyi.hh.physical.mapper.TDirectedholeTrackMapper;
import com.ruoyi.hh.physical.service.ITDirectedholeTrackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 设计定向钻轨迹Service业务层处理
 * 
 * @author eleven
 * @date 2023-03-06
 */
@Service
public class TDirectedholeTrackServiceImpl implements ITDirectedholeTrackService
{
    @Resource
    private TDirectedholeTrackMapper tDirectedholeTrackMapper;

    /**
     * 查询设计定向钻轨迹
     * 
     * @param id 设计定向钻轨迹主键
     * @return 设计定向钻轨迹
     */
    @Override
    public TDirectedholeTrack selectTDirectedholeTrackById(Integer id)
    {
        return tDirectedholeTrackMapper.selectTDirectedholeTrackById(id);
    }

    /**
     * 查询设计定向钻轨迹列表
     * 
     * @param tDirectedholeTrack 设计定向钻轨迹
     * @return 设计定向钻轨迹
     */
    @Override
    public List<TDirectedholeTrack> selectTDirectedholeTrackList(TDirectedholeTrack tDirectedholeTrack)
    {
        return tDirectedholeTrackMapper.selectTDirectedholeTrackList(tDirectedholeTrack);
    }

    /**
     * 新增设计定向钻轨迹
     * 
     * @param tDirectedholeTrack 设计定向钻轨迹
     * @return 结果
     */
    @Override
    public int insertTDirectedholeTrack(TDirectedholeTrack tDirectedholeTrack)
    {
        return tDirectedholeTrackMapper.insertTDirectedholeTrack(tDirectedholeTrack);
    }

    /**
     * 修改设计定向钻轨迹
     * 
     * @param tDirectedholeTrack 设计定向钻轨迹
     * @return 结果
     */
    @Override
    public int updateTDirectedholeTrack(TDirectedholeTrack tDirectedholeTrack)
    {
        return tDirectedholeTrackMapper.updateTDirectedholeTrack(tDirectedholeTrack);
    }

    /**
     * 批量删除设计定向钻轨迹
     * 
     * @param ids 需要删除的设计定向钻轨迹主键
     * @return 结果
     */
    @Override
    public int deleteTDirectedholeTrackByIds(Integer[] ids)
    {
        return tDirectedholeTrackMapper.deleteTDirectedholeTrackByIds(ids);
    }

    /**
     * 删除设计定向钻轨迹信息
     * 
     * @param id 设计定向钻轨迹主键
     * @return 结果
     */
    @Override
    public int deleteTDirectedholeTrackById(Integer id)
    {
        return tDirectedholeTrackMapper.deleteTDirectedholeTrackById(id);
    }
}
