package com.ruoyi.hh.business.service.impl;

import java.util.List;

import com.ruoyi.hh.business.domain.TMeasurePointContrast;
import com.ruoyi.hh.business.mapper.TMeasurePointContrastMapper;
import com.ruoyi.hh.business.service.ITMeasurePointContrastService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 测点人工对比功能Service业务层处理
 *
 * @author eleven
 * @date 2022-07-28
 */
@Service
public class TMeasurePointContrastServiceImpl implements ITMeasurePointContrastService {
    @Resource
    private TMeasurePointContrastMapper tMeasurePointContrastMapper;

    /**
     * 查询测点人工对比功能
     *
     * @param id 测点人工对比功能主键
     * @return 测点人工对比功能
     */
    @Override
    public TMeasurePointContrast selectTMeasurePointContrastById(Integer id) {
        return tMeasurePointContrastMapper.selectTMeasurePointContrastById(id);
    }

    /**
     * 查询测点人工对比功能列表
     *
     * @param tMeasurePointContrast 测点人工对比功能
     * @return 测点人工对比功能
     */
    @Override
    public List<TMeasurePointContrast> selectTMeasurePointContrastList(TMeasurePointContrast tMeasurePointContrast) {
        return tMeasurePointContrastMapper.selectTMeasurePointContrastList(tMeasurePointContrast);
    }

    /**
     * 新增测点人工对比功能
     *
     * @param tMeasurePointContrast 测点人工对比功能
     * @return 结果
     */
    @Override
    public Integer insertTMeasurePointContrast(TMeasurePointContrast tMeasurePointContrast) {
        return tMeasurePointContrastMapper.insertTMeasurePointContrast(tMeasurePointContrast);
    }

    /**
     * 修改测点人工对比功能
     *
     * @param tMeasurePointContrast 测点人工对比功能
     * @return 结果
     */
    @Override
    public Integer updateTMeasurePointContrast(TMeasurePointContrast tMeasurePointContrast) {
        return tMeasurePointContrastMapper.updateTMeasurePointContrast(tMeasurePointContrast);
    }

    /**
     * 批量删除测点人工对比功能
     *
     * @param ids 需要删除的测点人工对比功能主键
     * @return 结果
     */
    @Override
    public Integer deleteTMeasurePointContrastByIds(List<Integer> ids) {
        return tMeasurePointContrastMapper.deleteTMeasurePointContrastByIds(ids);
    }

    /**
     * 删除测点人工对比功能信息
     *
     * @param id 测点人工对比功能主键
     * @return 结果
     */
    @Override
    public Integer deleteTMeasurePointContrastById(Integer id) {

        return tMeasurePointContrastMapper.deleteTMeasurePointContrastById(id);
    }

    @Override
    public List<TMeasurePointContrast> getPageList() {
        return this.tMeasurePointContrastMapper.getPageList();
    }

    /**
     * 根据测点名称查询ID
     *
     * @param name 测点名称
     * @return id
     */
    @Override
    public Integer getIdByName(String name) {
        return this.tMeasurePointContrastMapper.getIdByName(name);
    }
    /**
     *根据测点ID删除测点人工对比信息
     * @param measurePointIds 测点ID列表
     * @return 结果
     */
    @Override
    public Integer delByMeasurePointIds(List<Integer> measurePointIds) {
        return this.tMeasurePointContrastMapper.delByMeasurePointIds(measurePointIds);
    }
    /**
     *根据测点ID删除测点人工对比信息
     * @param measurePointId 测点ID列表
     * @return 结果
     */
    @Override
    public Integer delByMeasurePointId(Integer measurePointId) {
        return this.tMeasurePointContrastMapper.delByMeasurePointId(measurePointId);
    }
    /**
     * 通过测点ID查询人工测点数据
     * @param measurePointIds 测点列表
     * @return 人工测点对比列表
     */
    @Override
    public List<TMeasurePointContrast> getByMeasurePointId(List<Integer> measurePointIds){
        return this.tMeasurePointContrastMapper.getByMeasurePointId(measurePointIds);
    }
}
