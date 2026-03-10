package com.ruoyi.hh.util.service;

import com.ruoyi.hh.util.domain.TCoalPointtype;

import java.util.List;


/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2022-12-02
 */
public interface ITCoalPointtypeService
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public TCoalPointtype selectTCoalPointtypeById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param tCoalPointtype 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<TCoalPointtype> selectTCoalPointtypeList(TCoalPointtype tCoalPointtype);

    /**
     * 新增【请填写功能名称】
     *
     * @param tCoalPointtype 【请填写功能名称】
     * @return 结果
     */
    public int insertTCoalPointtype(TCoalPointtype tCoalPointtype);

    /**
     * 修改【请填写功能名称】
     *
     * @param tCoalPointtype 【请填写功能名称】
     * @return 结果
     */
    public int updateTCoalPointtype(TCoalPointtype tCoalPointtype);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteTCoalPointtypeByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteTCoalPointtypeById(Long id);
}
