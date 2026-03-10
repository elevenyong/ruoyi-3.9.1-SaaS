package com.ruoyi.hh.util.service;

import java.util.List;

import com.ruoyi.hh.util.domain.TMeasuringType;

/**
 * 测点类型之Service接口
 *
 * @author eleven
 * @date 2022-07-16
 */
public interface ITMeasuringTypeService {
    /**
     * 查询测点类型之
     *
     * @param id 测点类型之主键
     * @return 测点类型之
     */
    public TMeasuringType selectTMeasuringTypeById(Integer id);

    /**
     * 查询测点类型之列表
     *
     * @param tMeasuringType 测点类型之
     * @return 测点类型之集合
     */
    public List<TMeasuringType> selectTMeasuringTypeList(TMeasuringType tMeasuringType);

    /**
     * 新增测点类型之
     *
     * @param tMeasuringType 测点类型之
     * @return 结果
     */
    public Integer insertTMeasuringType(TMeasuringType tMeasuringType);

    /**
     * 修改测点类型之
     *
     * @param tMeasuringType 测点类型之
     * @return 结果
     */
    public Integer updateTMeasuringType(TMeasuringType tMeasuringType);

    /**
     * 批量删除测点类型之
     *
     * @param ids 需要删除的测点类型之主键集合
     * @return 结果
     */
    public Integer deleteTMeasuringTypeByIds(List<Integer> ids);

    /**
     * 删除测点类型之信息
     *
     * @param id 测点类型之主键
     * @return 结果
     */
    public Integer deleteTMeasuringTypeById(Integer id);

    /**
     * 分页获取列表
     *
     * @return 列表
     */
    public List<TMeasuringType> getPageList();
}
