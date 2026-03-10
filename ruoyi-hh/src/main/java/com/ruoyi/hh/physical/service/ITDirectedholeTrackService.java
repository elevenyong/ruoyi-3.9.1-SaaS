package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TDirectedholeTrack;

import java.util.List;

/**
 * 设计定向钻轨迹Service接口
 * 
 * @author eleven
 * @date 2023-03-06
 */
public interface ITDirectedholeTrackService 
{
    /**
     * 查询设计定向钻轨迹
     * 
     * @param id 设计定向钻轨迹主键
     * @return 设计定向钻轨迹
     */
    public TDirectedholeTrack selectTDirectedholeTrackById(Integer id);

    /**
     * 查询设计定向钻轨迹列表
     * 
     * @param tDirectedholeTrack 设计定向钻轨迹
     * @return 设计定向钻轨迹集合
     */
    public List<TDirectedholeTrack> selectTDirectedholeTrackList(TDirectedholeTrack tDirectedholeTrack);

    /**
     * 新增设计定向钻轨迹
     * 
     * @param tDirectedholeTrack 设计定向钻轨迹
     * @return 结果
     */
    public int insertTDirectedholeTrack(TDirectedholeTrack tDirectedholeTrack);

    /**
     * 修改设计定向钻轨迹
     * 
     * @param tDirectedholeTrack 设计定向钻轨迹
     * @return 结果
     */
    public int updateTDirectedholeTrack(TDirectedholeTrack tDirectedholeTrack);

    /**
     * 批量删除设计定向钻轨迹
     * 
     * @param ids 需要删除的设计定向钻轨迹主键集合
     * @return 结果
     */
    public int deleteTDirectedholeTrackByIds(Integer[] ids);

    /**
     * 删除设计定向钻轨迹信息
     * 
     * @param id 设计定向钻轨迹主键
     * @return 结果
     */
    public int deleteTDirectedholeTrackById(Integer id);
}
