package com.ruoyi.hh.util.service;

import com.ruoyi.hh.util.domain.TShiftinfo;

import java.util.List;

/**
 * 班次信息Service接口
 *
 * @author eleven
 * @date 2022-07-18
 */
public interface ITShiftinfoService {
    /**
     * 查询班次信息
     *
     * @param id 班次信息主键
     * @return 班次信息
     */
    public TShiftinfo selectTShiftinfoById(Integer id);

    /**
     * 查询班次信息列表
     *
     * @param tShiftinfo 班次信息
     * @return 班次信息集合
     */
    public List<TShiftinfo> selectTShiftinfoList(TShiftinfo tShiftinfo);

    /**
     * 新增班次信息
     *
     * @param tShiftinfo 班次信息
     * @return 结果
     */
    public Integer insertTShiftinfo(TShiftinfo tShiftinfo);

    /**
     * 修改班次信息
     *
     * @param tShiftinfo 班次信息
     * @return 结果
     */
    public Integer updateTShiftinfo(TShiftinfo tShiftinfo);

    /**
     * 批量删除班次信息
     *
     * @param ids 需要删除的班次信息主键集合
     * @return 结果
     */
    public Integer deleteTShiftinfoByIds(List<Integer> ids);

    /**
     * 删除班次信息信息
     *
     * @param id 班次信息主键
     * @return 结果
     */
    public Integer deleteTShiftinfoById(Integer id);

    public List<TShiftinfo> getPageList();

    /**
     * 获取所有班次信息
     * @return
     */
    public List<String> getShiftInfoAll();
}
