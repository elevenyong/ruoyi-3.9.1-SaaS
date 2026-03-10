package com.ruoyi.hh.util.service.impl;

import java.util.List;

import com.ruoyi.hh.util.domain.TBoreholesite;
import com.ruoyi.hh.util.mapper.TBoreholesiteMapper;
import com.ruoyi.hh.util.service.ITBoreholesiteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 钻场信息Service业务层处理
 *
 * @author eleven
 * @date 2022-07-13
 */
@Service
public class TBoreholesiteServiceImpl implements ITBoreholesiteService {
    @Resource
    private TBoreholesiteMapper tBoreholesiteMapper;

    /**
     * 查询钻场信息
     *
     * @param id 钻场信息主键
     * @return 钻场信息
     */
    @Override
    public TBoreholesite selectTBoreholesiteById(Integer id) {
        return tBoreholesiteMapper.selectTBoreholesiteById(id);
    }

    /**
     * 查询钻场信息列表
     *
     * @param tBoreholesite 钻场信息
     * @return 钻场信息
     */
    @Override
    public List<TBoreholesite> selectTBoreholesiteList(TBoreholesite tBoreholesite) {
        return tBoreholesiteMapper.selectTBoreholesiteList(tBoreholesite);
    }

    /**
     * 新增钻场信息
     *
     * @param tBoreholesite 钻场信息
     * @return 结果
     */
    @Override
    public void insertTBoreholesite(TBoreholesite tBoreholesite) {
        tBoreholesiteMapper.insertTBoreholesite(tBoreholesite);
    }

    /**
     * 修改钻场信息
     *
     * @param tBoreholesite 钻场信息
     * @return 结果
     */
    @Override
    public Integer updateTBoreholesite(TBoreholesite tBoreholesite) {
        return tBoreholesiteMapper.updateTBoreholesite(tBoreholesite);
    }

    /**
     * 批量删除钻场信息
     *
     * @param ids 需要删除的钻场信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTBoreholesiteByIds(List<Integer> ids) {
        return tBoreholesiteMapper.deleteTBoreholesiteByIds(ids);
    }

    /**
     * 删除钻场信息信息
     *
     * @param id 钻场信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTBoreholesiteById(Integer id) {
        return tBoreholesiteMapper.deleteTBoreholesiteById(id);
    }

    /**
     * 批量添加钻场信息
     *
     * @param list
     */
    @Override
    public void insertTBoreholesiteList(List<TBoreholesite> list) {
        tBoreholesiteMapper.insertTBoreholesiteList(list);
    }

    /**
     * 根据巷道ID删除钻场信息
     * @param RoadWayId 巷道ID
     * @return 列表
     */
    @Override
    public Integer delByRoadWayId(Integer RoadWayId) {
        return this.tBoreholesiteMapper.delByRoadWayId(RoadWayId);
    }

    /**
     * 根据巷道ID删除钻场信息
     * @param roadWayIds 巷道ID
     * @return 列表
     */
    @Override
    public Integer delByRoadWayIds(List<Integer> roadWayIds) {
        return this.tBoreholesiteMapper.delByRoadWayIds(roadWayIds);
    }

    /**
     * 根据巷道ID获取钻场ID列表
     * @param roadWayId 巷道ID
     * @return 钻场ID列表
     */
    @Override
    public List<Integer> getIdsByRoadWayId(Integer roadWayId) {
        return this.tBoreholesiteMapper.getIdsByRoadWayId(roadWayId);
    }
}
