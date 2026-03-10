package com.ruoyi.hh.physical.service;


import com.ruoyi.hh.physical.domain.TRockinfo;

import java.util.List;

/**
 * 岩层信息Service接口
 *
 * @author hhhz
 * @date 2023-05-05
 */
public interface ITRockinfoService
{
    /**
     * 查询岩层信息
     *
     * @param id 岩层信息主键
     * @return 岩层信息
     */
    public TRockinfo selectTRockinfoById(Integer id);

    /**
     * 查询岩层信息列表
     *
     * @param tRockinfo 岩层信息
     * @return 岩层信息集合
     */
    public List<TRockinfo> selectTRockinfoList(TRockinfo tRockinfo);

    /**
     * 新增岩层信息
     *
     * @param tRockinfo 岩层信息
     * @return 结果
     */
    public int insertTRockinfo(TRockinfo tRockinfo);

    /**
     * 修改岩层信息
     *
     * @param tRockinfo 岩层信息
     * @return 结果
     */
    public int updateTRockinfo(TRockinfo tRockinfo);

    /**
     * 批量删除岩层信息
     *
     * @param ids 需要删除的岩层信息主键集合
     * @return 结果
     */
    public int deleteTRockinfoByIds(Integer[] ids);

    /**
     * 删除岩层信息信息
     *
     * @param id 岩层信息主键
     * @return 结果
     */
    public int deleteTRockinfoById(Integer id);
}
