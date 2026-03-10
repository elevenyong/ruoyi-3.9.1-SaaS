package com.ruoyi.hh.physical.service;


import com.ruoyi.hh.physical.domain.TRocktype;

import java.util.List;

/**
 * 岩层类型Service接口
 *
 * @author hhhz
 * @date 2023-05-05
 */
public interface ITRocktypeService {
    /**
     * 查询岩层类型
     *
     * @param id 岩层类型主键
     * @return 岩层类型
     */
    public TRocktype selectTRocktypeById(Integer id);

    /**
     * 查询岩层类型列表
     *
     * @param tRocktype 岩层类型
     * @return 岩层类型集合
     */
    public List<TRocktype> selectTRocktypeList(TRocktype tRocktype);

    /**
     * 新增岩层类型
     *
     * @param tRocktype 岩层类型
     * @return 结果
     */
    public int insertTRocktype(TRocktype tRocktype);

    /**
     * 修改岩层类型
     *
     * @param tRocktype 岩层类型
     * @return 结果
     */
    public int updateTRocktype(TRocktype tRocktype);

    /**
     * 批量删除岩层类型
     *
     * @param ids 需要删除的岩层类型主键集合
     * @return 结果
     */
    public int deleteTRocktypeByIds(Integer[] ids);

    /**
     * 删除岩层类型信息
     *
     * @param id 岩层类型主键
     * @return 结果
     */
    public int deleteTRocktypeById(Integer id);
}
