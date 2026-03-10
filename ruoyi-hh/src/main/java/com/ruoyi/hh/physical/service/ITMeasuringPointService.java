package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TMeasuringPoint;

import java.util.List;

/**
 * 测点信息之Service接口
 *
 * @author eleven
 * @date 2022-07-16
 */
public interface ITMeasuringPointService {
    /**
     * 查询测点信息之
     *
     * @param id 测点信息之主键
     * @return 测点信息之
     */
    public TMeasuringPoint selectTMeasuringPointById(Integer id);

    /**
     * 查询测点信息之列表
     *
     * @param tMeasuringPoint 测点信息之
     * @return 测点信息之集合
     */
    public List<TMeasuringPoint> selectTMeasuringPointList(TMeasuringPoint tMeasuringPoint);

    /**
     * 新增测点信息之
     *
     * @param tMeasuringPoint 测点信息之
     * @return 结果
     */
    public int insertTMeasuringPoint(TMeasuringPoint tMeasuringPoint);

    /**
     * 修改测点信息之
     *
     * @param tMeasuringPoint 测点信息之
     * @return 结果
     */
    public Integer updateTMeasuringPoint(TMeasuringPoint tMeasuringPoint);

    /**
     * 批量删除测点信息之
     *
     * @param ids 需要删除的测点信息之主键集合
     * @return 结果
     */
    public Integer deleteTMeasuringPointByIds(List<Integer> ids);

    /**
     * 删除测点信息之信息
     *
     * @param id 测点信息之主键
     * @return 结果
     */
    public Integer deleteTMeasuringPointById(Integer id);

    /**
     * 分页查询列表
     *
     * @return 列表
     */
    public List<TMeasuringPoint> getPageList();
    /**
     * 根据巷道ID删除测点信息
     * @param roadwayId 结果
     */
    public Integer delByRoadWayId(Integer roadwayId);
    /**
     * 根据巷道ID删除测点信息
     * @param roadWayIds 结果
     */
    public Integer delByRoadWayIds(List<Integer> roadWayIds);

    /**
     * 根据巷道ID和测点类型查询测点数据
     * @param roadwayId 巷道ID
     * @param measuringtypeid 测点类型ID
     * @return 列表
     */
    public List<TMeasuringPoint> getByRoadWayIdAndType( Integer roadwayId,
                                                       Integer measuringtypeid);

    /**
     * 根据评价单元查询测点列表
     * @param evaluationid 评价单元ID
     * @return 列表
     */
    public List<TMeasuringPoint> getByDrainageUnitId(Integer evaluationid);

    /**
     * 根据评价单元查询测点列表
     * @param evaluationid 评价单元ID
     * @return 列表
     */
    public List<Integer> getMeasureIdByUnitId(Integer evaluationid);

    /**
     * 根据采空区ID查询测点列表
     * @param goafinfoid  采空区ID
     * @return　列表
     */
    public List<TMeasuringPoint> getByGoafinfoid(Integer goafinfoid);

    /**
     * 根据揭煤区域ID查询测点列表
     * @param coaluncoverid  揭煤区域ID
     * @return　列表
     */
    public List<TMeasuringPoint> getByCoalUncoverid(Integer coaluncoverid);

}
