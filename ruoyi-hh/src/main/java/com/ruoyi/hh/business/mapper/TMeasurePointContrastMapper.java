package com.ruoyi.hh.business.mapper;

import java.util.List;

import com.ruoyi.hh.business.domain.TMeasurePointContrast;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 测点人工对比功能Mapper接口
 *
 * @author eleven
 * @date 2022-07-28
 */
public interface TMeasurePointContrastMapper {
    /**
     * 查询测点人工对比功能
     *
     * @param id 测点人工对比功能主键
     * @return 测点人工对比功能
     */
    public TMeasurePointContrast selectTMeasurePointContrastById(Integer id);

    /**
     * 查询测点人工对比功能列表
     *
     * @param tMeasurePointContrast 测点人工对比功能
     * @return 测点人工对比功能集合
     */
    public List<TMeasurePointContrast> selectTMeasurePointContrastList(TMeasurePointContrast tMeasurePointContrast);

    /**
     * 新增测点人工对比功能
     *
     * @param tMeasurePointContrast 测点人工对比功能
     * @return 结果
     */
    public Integer insertTMeasurePointContrast(TMeasurePointContrast tMeasurePointContrast);

    /**
     * 修改测点人工对比功能
     *
     * @param tMeasurePointContrast 测点人工对比功能
     * @return 结果
     */
    public Integer updateTMeasurePointContrast(TMeasurePointContrast tMeasurePointContrast);

    /**
     * 删除测点人工对比功能
     *
     * @param id 测点人工对比功能主键
     * @return 结果
     */
    public Integer deleteTMeasurePointContrastById(Integer id);

    /**
     * 批量删除测点人工对比功能
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTMeasurePointContrastByIds(@RequestParam List<Integer> ids);

    /**
     * 获取所有数据
     *
     * @return 列表
     */
    public List<TMeasurePointContrast> getPageList();

    /**
     * 根据测点名称查询id
     *
     * @param name 测点名称
     * @return id
     */
    public Integer getIdByName(String name);

    /**
     *根据测点ID删除测点人工对比信息
     * @param measurePointIds 测点ID列表
     * @return 结果
     */
    public Integer delByMeasurePointIds(@Param("measurePointIds") List<Integer> measurePointIds);

    /**
     * 根据测点ID删除测点人工对比信息
     * @param measurePointId 测点ID
     * @return 结果
     */
    public Integer delByMeasurePointId(@Param("measurePointId") Integer measurePointId);

    /**
     * 通过测点ID查询人工测点数据
     * @param measurePointIds 测点列表
     * @return 人工测点对比列表
     */
    public List<TMeasurePointContrast> getByMeasurePointId(@Param("measurePointIds") List<Integer> measurePointIds);
}
