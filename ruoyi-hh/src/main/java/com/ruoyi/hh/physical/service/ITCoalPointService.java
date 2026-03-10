package com.ruoyi.hh.physical.service;

import java.util.List;

import com.ruoyi.hh.physical.domain.TCoalPoint;

/**
 * 煤层对应的坐标点信息Service接口
 *
 * @author eleven
 * @date 2022-10-20
 */
public interface ITCoalPointService
{
    /**
     * 查询煤层对应的坐标点信息
     *
     * @param id 煤层对应的坐标点信息主键
     * @return 煤层对应的坐标点信息
     */
    public TCoalPoint selectTCoalPointById(Integer id);

    /**
     * 查询煤层对应的坐标点信息列表
     *
     * @param TCoalPoint 煤层对应的坐标点信息
     * @return 煤层对应的坐标点信息集合
     */
    public List<TCoalPoint> selectTCoalPointList(TCoalPoint TCoalPoint);

    /**
     * 新增煤层对应的坐标点信息
     *
     * @param TCoalPoint 煤层对应的坐标点信息
     * @return 结果
     */
    public int insertTCoalPoint(TCoalPoint TCoalPoint);


    /**
     * 修改煤层对应的坐标点信息
     *
     * @param TCoalPoint 煤层对应的坐标点信息
     * @return 结果
     */
    public int updateTCoalPoint(TCoalPoint TCoalPoint);

    /**
     * 批量删除煤层对应的坐标点信息
     *
     * @param ids 需要删除的煤层对应的坐标点信息主键集合
     * @return 结果
     */
    public int deleteTCoalPointByIds(Integer[] ids);

    /**
     * 删除煤层对应的坐标点信息信息
     *
     * @param id 煤层对应的坐标点信息主键
     * @return 结果
     */
    public int deleteTCoalPointById(Integer id);

    int deleteTCoalPointByCoalinfoId(Integer coalinfoId);
}
