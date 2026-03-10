package com.ruoyi.hh.hhhz.service;

import com.ruoyi.hh.hhhz.domain.param.TParamBandDrilling;

import java.util.List;

/**
 * 钻孔设计历史记录Service接口
 *
 * @author hhhz
 * @date 2023-04-24
 */
public interface ITParamBandDrillingService
{
    /**
     * 查询钻孔设计历史记录
     *
     * @param id 钻孔设计历史记录主键
     * @return 钻孔设计历史记录
     */
    public TParamBandDrilling selectTParamBandDrillingById(Integer id);

    /**
     * 查询钻孔设计历史记录列表
     *
     * @param tParamBandDrilling 钻孔设计历史记录
     * @return 钻孔设计历史记录集合
     */
    public List<TParamBandDrilling> selectTParamBandDrillingList(TParamBandDrilling tParamBandDrilling);

    /**
     * 新增钻孔设计历史记录
     *
     * @param tParamBandDrilling 钻孔设计历史记录
     * @return 结果
     */
    public int insertTParamBandDrilling(TParamBandDrilling tParamBandDrilling);

    /**
     * 修改钻孔设计历史记录
     *
     * @param tParamBandDrilling 钻孔设计历史记录
     * @return 结果
     */
    public int updateTParamBandDrilling(TParamBandDrilling tParamBandDrilling);

    /**
     * 批量删除钻孔设计历史记录
     *
     * @param ids 需要删除的钻孔设计历史记录主键集合
     * @return 结果
     */
    public int deleteTParamBandDrillingByIds(Integer[] ids);

    /**
     * 删除钻孔设计历史记录信息
     *
     * @param id 钻孔设计历史记录主键
     * @return 结果
     */
    public int deleteTParamBandDrillingById(Integer id);

    /**
     * 获取最新一条数据
     * @return
     */
    public TParamBandDrilling getLastOne();

    /**
     * 更改参数的标识
     * @param ids 列表
     */
    public void updateSign(List<Integer> ids);
}
