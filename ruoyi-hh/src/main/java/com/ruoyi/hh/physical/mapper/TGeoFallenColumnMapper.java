package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TGeoFallenColumn;

import java.util.List;

/**
 * 陷落柱Mapper接口
 *
 * @author hhhz
 * @date 2022-10-28
 */
public interface TGeoFallenColumnMapper
{
    /**
     * 查询陷落柱
     *
     * @param id 陷落柱主键
     * @return 陷落柱
     */
    public TGeoFallenColumn selectTGeoFallenColumnById(Integer id);

    /**
     * 查询陷落柱列表
     *
     * @param tGeoFallenColumn 陷落柱
     * @return 陷落柱集合
     */
    public List<TGeoFallenColumn> selectTGeoFallenColumnList(TGeoFallenColumn tGeoFallenColumn);

    /**
     * 新增陷落柱
     *
     * @param tGeoFallenColumn 陷落柱
     * @return 结果
     */
    public int insertTGeoFallenColumn(TGeoFallenColumn tGeoFallenColumn);

    /**
     * 修改陷落柱
     *
     * @param tGeoFallenColumn 陷落柱
     * @return 结果
     */
    public int updateTGeoFallenColumn(TGeoFallenColumn tGeoFallenColumn);

    /**
     * 删除陷落柱
     *
     * @param id 陷落柱主键
     * @return 结果
     */
    public int deleteTGeoFallenColumnById(Integer id);

    /**
     * 批量删除陷落柱
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTGeoFallenColumnByIds(Integer[] ids);
}
