package com.ruoyi.hh.hhhz.service;

import com.ruoyi.hh.hhhz.domain.param.TParamLayerDrilling;

import java.util.List;

/**
 * 顺层钻孔参数Service接口
 *
 * @author hhhz
 * @date 2023-04-24
 */
public interface ITParamLayerDrillingService
{
    /**
     * 查询顺层钻孔参数
     *
     * @param id 顺层钻孔参数主键
     * @return 顺层钻孔参数
     */
    public TParamLayerDrilling selectTParamLayerDrillingById(Integer id);

    /**
     * 查询顺层钻孔参数列表
     *
     * @param tParamLayerDrilling 顺层钻孔参数
     * @return 顺层钻孔参数集合
     */
    public List<TParamLayerDrilling> selectTParamLayerDrillingList(TParamLayerDrilling tParamLayerDrilling);

    /**
     * 新增顺层钻孔参数
     *
     * @param tParamLayerDrilling 顺层钻孔参数
     * @return 结果
     */
    public int insertTParamLayerDrilling(TParamLayerDrilling tParamLayerDrilling);

    /**
     * 修改顺层钻孔参数
     *
     * @param tParamLayerDrilling 顺层钻孔参数
     * @return 结果
     */
    public int updateTParamLayerDrilling(TParamLayerDrilling tParamLayerDrilling);

    /**
     * 批量删除顺层钻孔参数
     *
     * @param ids 需要删除的顺层钻孔参数主键集合
     * @return 结果
     */
    public int deleteTParamLayerDrillingByIds(Integer[] ids);

    /**
     * 删除顺层钻孔参数信息
     *
     * @param id 顺层钻孔参数主键
     * @return 结果
     */
    public int deleteTParamLayerDrillingById(Integer id);

    public TParamLayerDrilling getLastOne();

    /**
     * 更改参数的标识
     * @param ids 列表
     */
    public void updateSign(List<Integer> ids);
}
