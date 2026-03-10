package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TNavigationPoint;
import com.ruoyi.hh.util.Result;

import java.util.List;

/**
 * 导线点相关Service接口
 *
 * @author eleven
 * @date 2022-07-13
 */
public interface ITNavigationPointService {
    /**
     * 查询导线点相关
     *
     * @param id 导线点相关主键
     * @return 导线点相关
     */
    public TNavigationPoint selectTNavigationPointById(Integer id);

    /**
     * 查询导线点相关列表
     *
     * @param tNavigationPoint 导线点相关
     * @return 导线点相关集合
     */
    public List<TNavigationPoint> selectTNavigationPointList(TNavigationPoint tNavigationPoint);

    /**
     * 新增导线点相关
     *
     * @param tNavigationPoint 导线点相关
     */
    public Result insertTNavigationPoint(TNavigationPoint tNavigationPoint);

    /**
     * 修改导线点相关
     *
     * @param tNavigationPoint 导线点相关
     * @return 结果
     */
    public Result updateTNavigationPoint(TNavigationPoint tNavigationPoint);

    /**
     * 批量删除导线点相关
     *
     * @param ids 需要删除的导线点相关主键集合
     * @return 结果
     */
    public Integer deleteTNavigationPointByIds(List<Integer> ids);

    /**
     * 删除导线点相关信息
     *
     * @param id 导线点相关主键
     * @return 结果
     */
    public Integer deleteTNavigationPointById(Integer id);

    /**
     * 批量添加导线点信息
     *
     * @param list 导线点列表
     */
    public int insertTNavigationPointList(List<TNavigationPoint> list);

    /**
     * 查询导线点列表
     *
     * @return 导线点列表
     */
    public List<TNavigationPoint> list();

    /**
     * 根据巷道ID查询导线点信息
     *
     * @param roadwayId 巷道ID
     * @return 导线点列表
     */
    public List<TNavigationPoint> getByRoadwayId(Integer roadwayId);

    /**
     * 根据巷道ID删除导线点信息
     * @param RoadWayId 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayId(Integer RoadWayId);

    /**
     * 根据巷道ID删除导线点信息
     * @param roadWayIds 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayIds(List<Integer> roadWayIds);

    /**
     * 根据导线点名称查询导线点ID
     * @param navigationpointname 导线点名称
     * @return ID
     */
    public Integer getIdByNavigationPointName(String navigationpointname,Integer roadwayId);

    /**
     * 获取所有导线点名称
     * @return
     */
    public List<String> getNavigationPointAll();

}
