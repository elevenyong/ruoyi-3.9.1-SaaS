package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TRoadwayZone;
import com.ruoyi.hh.util.Result;

import java.util.List;

/**
 * 区段信息Service接口
 *
 * @author eleven
 * @date 2022-07-14
 */
public interface ITRoadwayZoneService {
    /**
     * 查询区段信息
     *
     * @param id 区段信息主键
     * @return 区段信息
     */
    public TRoadwayZone selectTRoadwayZoneById(Integer id);

    /**
     * 查询区段信息列表
     *
     * @param tRoadwayZone 区段信息
     * @return 区段信息集合
     */
    public List<TRoadwayZone> selectTRoadwayZoneList(TRoadwayZone tRoadwayZone);

    /**
     * 新增区段信息
     *
     * @param tRoadwayZone 区段信息
     * @return 结果
     */
    public Result insertTRoadwayZone(TRoadwayZone tRoadwayZone);

    /**
     * 修改区段信息
     *
     * @param tRoadwayZone 区段信息
     * @return 结果
     */
    public Result updateTRoadwayZone(TRoadwayZone tRoadwayZone);

    /**
     * 批量删除区段信息
     *
     * @param ids 需要删除的区段信息主键集合
     * @return 结果
     */
    public Integer deleteTRoadwayZoneByIds(List<Integer> ids);

    /**
     * 删除区段信息信息
     *
     * @param id 区段信息主键
     * @return 结果
     */
    public Integer deleteTRoadwayZoneById(Integer id);

    /**
     * 查询区段列表
     *
     * @return
     */
    public List<TRoadwayZone> list();

    /**
     * 批量添加区段
     *
     * @param list
     */
    public Integer insertTRoadwayZoneList(List<TRoadwayZone> list);

    /**
     * 根据巷道ID查询
     *
     * @param RoadwayId 巷道ID
     * @return 列表
     */
    public List<TRoadwayZone> getByRoadwayId(Integer RoadwayId);

    /**
     * 根据巷道ID删除区段信息
     * @param RoadWayId 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayId(Integer RoadWayId);

    /**
     * 根据巷道ID删除区段信息
     * @param roadWayIds 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayIds(List<Integer> roadWayIds);

    /**
     * 根据巷道ID查询区段ID列表
     * @param roadWayId 巷道ID
     * @return 区段ID列表
     */
    public List<Integer> getIdsByRoadWayId(Integer roadWayId);

    /**
     * 根据区段名称查询区段ID
     * @param zoneName 区段名称
     * @return ID
     */
    public Integer getIdByZoneName(String zoneName,Integer roadWayId);

    /**
     * 获取区段名称
     * @return
     */
    public List<String> getZoneNameAll();

}
