package com.ruoyi.hh.business.service;

import java.util.List;

import com.ruoyi.hh.business.domain.TStripedesignparameter;

/**
 * 条带治理钻孔设计参数接口之Service接口
 *
 * @author eleven
 * @date 2022-07-16
 */
public interface ITStripedesignparameterService {
    /**
     * 查询条带治理钻孔设计参数接口之
     *
     * @param id 条带治理钻孔设计参数接口之主键
     * @return 条带治理钻孔设计参数接口之
     */
    public TStripedesignparameter selectTStripedesignparameterById(Integer id);

    /**
     * 查询条带治理钻孔设计参数接口之列表
     *
     * @param tStripedesignparameter 条带治理钻孔设计参数接口之
     * @return 条带治理钻孔设计参数接口之集合
     */
    public List<TStripedesignparameter> selectTStripedesignparameterList(TStripedesignparameter tStripedesignparameter);

    /**
     * 新增条带治理钻孔设计参数接口之
     *
     * @param tStripedesignparameter 条带治理钻孔设计参数接口之
     * @return 结果
     */
    public Integer insertTStripedesignparameter(TStripedesignparameter tStripedesignparameter);

    /**
     * 修改条带治理钻孔设计参数接口之
     *
     * @param tStripedesignparameter 条带治理钻孔设计参数接口之
     * @return 结果
     */
    public Integer updateTStripedesignparameter(TStripedesignparameter tStripedesignparameter);

    /**
     * 批量删除条带治理钻孔设计参数接口之
     *
     * @param ids 需要删除的条带治理钻孔设计参数接口之主键集合
     * @return 结果
     */
    public Integer deleteTStripedesignparameterByIds(List<Integer> ids);

    /**
     * 删除条带治理钻孔设计参数接口之信息
     *
     * @param id 条带治理钻孔设计参数接口之主键
     * @return 结果
     */
    public Integer deleteTStripedesignparameterById(Integer id);

    /**
     * 分页获取列表
     *
     * @return 列表
     */
    public List<TStripedesignparameter> getPageList();

    /**
     * 根据区段ID删除条带直接设计信息
     * @param roadWayZoneIds 区段ID
     * @return 结果
     */
    public Integer delByRoadWayZoneIds(List<Integer> roadWayZoneIds);
    /**
     * 根据区段ID删除条带直接设计信息
     * @param roadWayZoneId 区段ID
     * @return 结果
     */
    public Integer delByRoadWayZoneId(Integer roadWayZoneId);
}
