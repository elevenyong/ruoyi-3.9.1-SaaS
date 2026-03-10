package com.ruoyi.hh.util.mapper;

import com.ruoyi.hh.util.domain.TBoreholesite;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 钻场信息Mapper接口
 *
 * @author eleven
 * @date 2022-07-13
 */
public interface TBoreholesiteMapper {
    /**
     * 查询钻场信息
     *
     * @param id 钻场信息主键
     * @return 钻场信息
     */
    public TBoreholesite selectTBoreholesiteById(Integer id);

    /**
     * 查询钻场信息列表
     *
     * @param tBoreholesite 钻场信息
     * @return 钻场信息集合
     */
    public List<TBoreholesite> selectTBoreholesiteList(TBoreholesite tBoreholesite);

    /**
     * 新增钻场信息
     *
     * @param tBoreholesite 钻场信息
     * @return 结果
     */
    public Integer insertTBoreholesite(TBoreholesite tBoreholesite);

    /**
     * 修改钻场信息
     *
     * @param tBoreholesite 钻场信息
     * @return 结果
     */
    public Integer updateTBoreholesite(TBoreholesite tBoreholesite);

    /**
     * 删除钻场信息
     *
     * @param id 钻场信息主键
     * @return 结果
     */
    public Integer deleteTBoreholesiteById(Integer id);

    /**
     * 批量删除钻场信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTBoreholesiteByIds(@RequestParam List<Integer> ids);

    /**
     * 批量添加钻场信息
     *
     * @param list
     */
    public void insertTBoreholesiteList(@RequestBody List<TBoreholesite> list);

    /**
     * 根据巷道ID删除钻场信息
     * @param RoadWayId 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayId(Integer RoadWayId);

    /**
     * 根据巷道ID删除钻场信息
     * @param roadWayIds 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayIds(@RequestParam List<Integer> roadWayIds);

    /**
     * 根据巷道ID获取钻场ID列表
     * @param roadWayId 巷道ID
     * @return 钻场ID列表
     */
    public List<Integer> getIdsByRoadWayId(Integer roadWayId);
}
