package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TMeasuringPoint;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 测点信息之Mapper接口
 *
 * @author eleven
 * @date 2022-07-16
 */
public interface TMeasuringPointMapper {
    /**
     * 查询测点信息之
     *
     * @param id 测点信息之主键
     * @return 测点信息之
     */
    public TMeasuringPoint selectTMeasuringPointById(int id);

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
    public int updateTMeasuringPoint(TMeasuringPoint tMeasuringPoint);

    /**
     * 删除测点信息之
     *
     * @param id 测点信息之主键
     * @return 结果
     */
    public int deleteTMeasuringPointById(@RequestParam Integer id);

    /**
     * 批量删除测点信息之
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTMeasuringPointByIds(@RequestParam List<Integer> ids);

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
    public List<TMeasuringPoint> getByRoadWayIdAndType(@Param("roadwayId") Integer roadwayId,
                                                       @Param("measuringtypeid") Integer measuringtypeid);

    /**
     * 根据抽采单元查询测点列表
     * @param drainageunitid 抽采单元ID
     * @return 列表
     */
    public List<TMeasuringPoint> getByDrainageUnitId(Integer drainageunitid);

    /**
     * 根据抽采单元查询测点ID列表
     * @param drainageunitid 抽采单元ID
     * @return ID列表
     */
    public List<Integer> getMeasureIdByUnitId(Integer drainageunitid);

    /**
     * 根据工作面ID分组查询测点上级测点ID
     * @param workFaceId 工作面ID
     * @return 列表
     */
    public List<Integer> getLeafNodeByWorkFaceId(Integer workFaceId);

    /**
     * 根据工作面ID和测点级别Id查询测点列表
     * @param measurePointLevelId　测点级别ID
     * @param workFaceId　工作面ID
     * @return　结果
     */
    public List<Integer> getByWorkFaceIdAndMeasureLevel(@Param("measurePointLevelId")Integer measurePointLevelId,
                                                                @Param("workFaceId")Integer workFaceId);

    /**
     * 根据采空区ID查询测点列表
     * @param goafinfoid  采空区ID
     * @return　列表
     */
    public List<TMeasuringPoint> getByGoafinfoid(@Param("goafinfoid")Integer goafinfoid);

    /**
     * 根据揭煤区域ID查询测点列表
     * @param coaluncoverid  揭煤区域ID
     * @return　列表
     */
    public List<TMeasuringPoint> getByCoalUncoverid(@Param("coaluncoverid")Integer coaluncoverid);



}
