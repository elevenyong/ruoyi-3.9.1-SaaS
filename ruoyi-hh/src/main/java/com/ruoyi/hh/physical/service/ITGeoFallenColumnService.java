package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TGeoFallenColumn;

import java.util.List;

/**
 * 断层Service接口
 *
 * @author hhhz
 * @date 2022-10-28
 */
public interface ITGeoFallenColumnService
{
    /**
     * 查询断层
     *
     * @param id 断层主键
     * @return 断层
     */
    public TGeoFallenColumn selectTGeoFallenColumnById(Integer id);

    /**
     * 查询断层列表
     *
     * @param tGeoFallenColumn 断层
     * @return 断层集合
     */
    public List<TGeoFallenColumn> selectTGeoFallenColumnList(TGeoFallenColumn tGeoFallenColumn);

    /**
     * 新增断层
     *
     * @param tGeoFallenColumn 断层
     * @return 结果
     */
    public int insertTGeoFallenColumn(TGeoFallenColumn tGeoFallenColumn);

    /**
     * 修改断层
     *
     * @param tGeoFallenColumn 断层
     * @return 结果
     */
    public int updateTGeoFallenColumn(TGeoFallenColumn tGeoFallenColumn);

    /**
     * 批量删除断层
     *
     * @param ids 需要删除的断层主键集合
     * @return 结果
     */
    public int deleteTGeoFallenColumnByIds(Integer[] ids);

    /**
     * 删除断层信息
     *
     * @param id 断层主键
     * @return 结果
     */
    public int deleteTGeoFallenColumnById(Integer id);
}
