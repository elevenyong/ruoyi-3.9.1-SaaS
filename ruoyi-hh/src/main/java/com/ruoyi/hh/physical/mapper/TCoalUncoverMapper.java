package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TCoalUncover;

/**
 * 揭煤抽采信息Mapper接口
 * 
 * @author eleven
 * @date 2022-12-09
 */
public interface TCoalUncoverMapper 
{
    /**
     * 查询揭煤抽采信息
     * 
     * @param id 揭煤抽采信息主键
     * @return 揭煤抽采信息
     */
    public TCoalUncover selectTCoalUncoverById(Integer id);

    /**
     * 查询揭煤抽采信息列表
     * 
     * @param tCoalUncover 揭煤抽采信息
     * @return 揭煤抽采信息集合
     */
    public List<TCoalUncover> selectTCoalUncoverList(TCoalUncover tCoalUncover);

    /**
     * 新增揭煤抽采信息
     * 
     * @param tCoalUncover 揭煤抽采信息
     * @return 结果
     */
    public int insertTCoalUncover(TCoalUncover tCoalUncover);

    /**
     * 修改揭煤抽采信息
     * 
     * @param tCoalUncover 揭煤抽采信息
     * @return 结果
     */
    public int updateTCoalUncover(TCoalUncover tCoalUncover);

    /**
     * 删除揭煤抽采信息
     * 
     * @param id 揭煤抽采信息主键
     * @return 结果
     */
    public int deleteTCoalUncoverById(Integer id);

    /**
     * 批量删除揭煤抽采信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTCoalUncoverByIds(Integer[] ids);
}
