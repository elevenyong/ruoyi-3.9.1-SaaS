package com.ruoyi.hh.physical.service;

import java.util.List;

import com.ruoyi.hh.physical.domain.TGoafinfo;

/**
 * 采空区信息Service接口
 * 
 * @author eleven
 * @date 2022-12-09
 */
public interface ITGoafinfoService 
{
    /**
     * 查询采空区信息
     * 
     * @param id 采空区信息主键
     * @return 采空区信息
     */
    public TGoafinfo selectTGoafinfoById(Integer id);

    /**
     * 查询采空区信息列表
     * 
     * @param tGoafinfo 采空区信息
     * @return 采空区信息集合
     */
    public List<TGoafinfo> selectTGoafinfoList(TGoafinfo tGoafinfo);

    /**
     * 新增采空区信息
     * 
     * @param tGoafinfo 采空区信息
     * @return 结果
     */
    public int insertTGoafinfo(TGoafinfo tGoafinfo);

    /**
     * 修改采空区信息
     * 
     * @param tGoafinfo 采空区信息
     * @return 结果
     */
    public int updateTGoafinfo(TGoafinfo tGoafinfo);

    /**
     * 批量删除采空区信息
     * 
     * @param ids 需要删除的采空区信息主键集合
     * @return 结果
     */
    public int deleteTGoafinfoByIds(Integer[] ids);

    /**
     * 删除采空区信息信息
     * 
     * @param id 采空区信息主键
     * @return 结果
     */
    public int deleteTGoafinfoById(Integer id);
}
