package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TDirectedholeTrack;

/**
 * 设计定向钻轨迹Mapper接口
 * 
 * @author eleven
 * @date 2023-03-06
 */
public interface TDirectedholeTrackMapper 
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
     * 删除设计定向钻轨迹
     * 
     * @param id 设计定向钻轨迹主键
     * @return 结果
     */
    public int deleteTDirectedholeTrackById(Integer id);

    /**
     * 批量删除设计定向钻轨迹
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDirectedholeTrackByIds(Integer[] ids);
}
