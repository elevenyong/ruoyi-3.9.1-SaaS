package com.ruoyi.hh.business.service.impl;

import java.util.List;

import com.ruoyi.hh.business.domain.TStripedesignparameter;
import com.ruoyi.hh.business.mapper.TStripedesignparameterMapper;
import com.ruoyi.hh.business.service.ITStripedesignparameterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 条带治理钻孔设计参数接口之Service业务层处理
 *
 * @author eleven
 * @date 2022-07-16
 */
@Service
public class TStripedesignparameterServiceImpl implements ITStripedesignparameterService {
    @Resource
    private TStripedesignparameterMapper tStripedesignparameterMapper;

    /**
     * 查询条带治理钻孔设计参数接口之
     *
     * @param id 条带治理钻孔设计参数接口之主键
     * @return 条带治理钻孔设计参数接口之
     */
    @Override
    public TStripedesignparameter selectTStripedesignparameterById(Integer id) {
        return tStripedesignparameterMapper.selectTStripedesignparameterById(id);
    }

    /**
     * 查询条带治理钻孔设计参数接口之列表
     *
     * @param tStripedesignparameter 条带治理钻孔设计参数接口之
     * @return 条带治理钻孔设计参数接口之
     */
    @Override
    public List<TStripedesignparameter> selectTStripedesignparameterList(TStripedesignparameter tStripedesignparameter) {
        return tStripedesignparameterMapper.selectTStripedesignparameterList(tStripedesignparameter);
    }

    /**
     * 新增条带治理钻孔设计参数接口之
     *
     * @param tStripedesignparameter 条带治理钻孔设计参数接口之
     * @return 结果
     */
    @Override
    public Integer insertTStripedesignparameter(TStripedesignparameter tStripedesignparameter) {
        return tStripedesignparameterMapper.insertTStripedesignparameter(tStripedesignparameter);
    }

    /**
     * 修改条带治理钻孔设计参数接口之
     *
     * @param tStripedesignparameter 条带治理钻孔设计参数接口之
     * @return 结果
     */
    @Override
    public Integer updateTStripedesignparameter(TStripedesignparameter tStripedesignparameter) {
        return tStripedesignparameterMapper.updateTStripedesignparameter(tStripedesignparameter);
    }

    /**
     * 批量删除条带治理钻孔设计参数接口之
     *
     * @param ids 需要删除的条带治理钻孔设计参数接口之主键
     * @return 结果
     */
    @Override
    public Integer deleteTStripedesignparameterByIds(List<Integer> ids) {
        return tStripedesignparameterMapper.deleteTStripedesignparameterByIds(ids);
    }

    /**
     * 删除条带治理钻孔设计参数接口之信息
     *
     * @param id 条带治理钻孔设计参数接口之主键
     * @return 结果
     */
    @Override
    public Integer deleteTStripedesignparameterById(Integer id) {
        return tStripedesignparameterMapper.deleteTStripedesignparameterById(id);
    }

    @Override
    public List<TStripedesignparameter> getPageList() {
        return this.tStripedesignparameterMapper.getPageList();
    }

    /**
     * 根据区段ID删除条带直接设计信息
     * @param roadWayZoneIds 区段ID
     * @return 结果
     */
    @Override
    public Integer delByRoadWayZoneIds(List<Integer> roadWayZoneIds) {
        return this.tStripedesignparameterMapper.delByRoadWayZoneIds(roadWayZoneIds);
    }

    @Override
    public Integer delByRoadWayZoneId(Integer roadWayZoneId) {
        return this.tStripedesignparameterMapper.delByRoadWayZoneId(roadWayZoneId);
    }
}
