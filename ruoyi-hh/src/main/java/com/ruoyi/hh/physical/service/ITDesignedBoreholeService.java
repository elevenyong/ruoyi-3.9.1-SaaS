package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.util.Result;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 设计钻孔Service接口
 *
 * @author eleven
 * @date 2022-07-12
 */
public interface ITDesignedBoreholeService {
    /**
     * 查询设计钻孔
     *
     * @param id 设计钻孔主键
     * @return 设计钻孔
     */
    public TDesignedBorehole selectTDesignedBoreholeById(Integer id);

    /**
     * 查询设计钻孔列表
     *
     * @param tDesignedBorehole 设计钻孔
     * @return 设计钻孔集合
     */
    public List<TDesignedBorehole> selectTDesignedBoreholeList(TDesignedBorehole tDesignedBorehole);

    /**
     * 新增设计钻孔
     *
     * @param tDesignedBorehole 设计钻孔
     */
    public Result insertTDesignedBorehole(TDesignedBorehole tDesignedBorehole);

    /**
     * 修改设计钻孔
     *
     * @param tDesignedBorehole 设计钻孔
     * @return 结果
     */
    public Result updateTDesignedBorehole(TDesignedBorehole tDesignedBorehole);

    /**
     * 批量删除设计钻孔
     *
     * @param ids 需要删除的设计钻孔主键集合
     * @return 结果
     */
    public Integer deleteTDesignedBoreholeByIds(List<Integer> ids);

    /**
     * 删除设计钻孔信息
     *
     * @param id 设计钻孔主键
     * @return 结果
     */
    public Integer deleteTDesignedBoreholeById(Integer id);

    /**
     * 批量添加巷道
     *
     * @param list
     */
    //public void insertTDesignedBoreholeList(List<TDesignedBorehole> list);

    /**
     * 多表联查设计钻孔信息列表
     *
     * @return 设计钻孔信息列表
     */
    public List<TDesignedBorehole> getPageList();

    /**
     * @param groupNo 组号
     * @return 列表
     */

    public List<TDesignedBorehole> getByGroupId(String groupNo);

    /**
     * 根据巷道ID和类别ID查询
     *
     * @param roadwayId         巷道ID
     * @param PurposeBoreholeId 类别ID
     * @return
     */
    public List<TDesignedBorehole> getByRoadWayIdAndpurposeboreId(Integer roadwayId, Integer PurposeBoreholeId);


    /**
     * 删除所有数据
     *
     * @return
     */
    public Integer delAll();

    /**
     * 根据巷道ID删除钻孔信息
     * @param roadwayId 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayId(Integer roadwayId);

    /**
     * 根据巷道ID删除钻孔信息
     * @param roadWayIds 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayIds(List<Integer> roadWayIds);

    /**
     * 通过巷道ID获取设计钻孔ID列表
     * @param roadwayId 巷道ID
     * @return 列表
     */
    public List<Integer> getIdsByRoadWayId(Integer roadwayId);

    /**
     * 根据区段ID删除设计钻孔信息
     * @param roadwayZoneId 区段ID
     * @return 结果
     */
    public Integer delByRoadwayZoneId(Integer roadwayZoneId);
    public Integer delByRoadwayZoneIds (List<Integer> roadwayZoneIds);

    /**
     * 根据导线点ID删除设计钻孔信息
     * @param navigationPointId 导线点ID
     * @return 结果
     */
    public Integer delByNavigationPointId (Integer navigationPointId);
    public Integer delNavigationPointIds  (List<Integer> navigationPointIds);

    /**
     * 通过区段ID获取设计钻孔列表
     * @param roadwayZoneId 区段ID
     * @return 设计钻孔列表
     */
    public List<TDesignedBorehole> getByRoadWayZoneId(@RequestParam Integer roadwayZoneId);

    /**
     * 根据巷道ID查询设计钻孔列表
     * @param roadwayId 巷道ID
     * @return 设计钻孔列表
     */
    public List<TDesignedBorehole> getByRoadwayId(@RequestParam Integer roadwayId);

    /**
     * 更改设计钻孔状态（确定设计钻孔方案）
     * @param ids 设计参数id列表
     */
    public void makeSureDesProgramme(Integer sign,List<Integer> ids);

    /**
     * 获取设计钻孔ID
     * @param desParameterId
     * @return
     */
    public List<Integer> getBoreholeIdByParam( Integer desParameterId);

    /**
     * 通过巷道ID，组号和孔号确定设计钻孔信息
     * @param roadWayId 巷道ID
     * @param groupNo 组号
     * @param boreholeNo 孔号
     * @return
     */
    public TDesignedBorehole findDesBoreholeInfo(Integer roadWayId,String groupNo,String boreholeNo);

    /**
     * 根据设计参数ID删除设计钻孔信息
     * @param designParameterId
     */
    public void delByDesignParameterId(Integer designParameterId);

    Integer getNormalBoreholeCount(Integer type, Integer roadwayId);
}
