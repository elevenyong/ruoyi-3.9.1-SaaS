package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TActDirectionalDrillingPoint;
import com.ruoyi.hh.physical.mapper.TActDirectionalDrillingPointMapper;
import com.ruoyi.hh.physical.service.ITActDirectionalDrillingPointService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 实际定向钻孔点位列Service业务层处理
 * 
 * @author eleven
 * @date 2023-06-29
 */
@Service
public class TActDirectionalDrillingPointServiceImpl implements ITActDirectionalDrillingPointService 
{
    @Resource
    private TActDirectionalDrillingPointMapper tActDirectionalDrillingPointMapper;

    /**
     * 查询实际定向钻孔点位列
     * 
     * @param id 实际定向钻孔点位列主键
     * @return 实际定向钻孔点位列
     */
    @Override
    public TActDirectionalDrillingPoint selectTActDirectionalDrillingPointById(Integer id)
    {
        return tActDirectionalDrillingPointMapper.selectTActDirectionalDrillingPointById(id);
    }

    /**
     * 查询实际定向钻孔点位列列表
     * 
     * @param tActDirectionalDrillingPoint 实际定向钻孔点位列
     * @return 实际定向钻孔点位列
     */
    @Override
    public List<TActDirectionalDrillingPoint> selectTActDirectionalDrillingPointList(TActDirectionalDrillingPoint tActDirectionalDrillingPoint)
    {
        return tActDirectionalDrillingPointMapper.selectTActDirectionalDrillingPointList(tActDirectionalDrillingPoint);
    }

    /**
     * 新增实际定向钻孔点位列
     * 
     * @param tActDirectionalDrillingPoint 实际定向钻孔点位列
     * @return 结果
     */
    @Override
    public int insertTActDirectionalDrillingPoint(TActDirectionalDrillingPoint tActDirectionalDrillingPoint)
    {
        return tActDirectionalDrillingPointMapper.insertTActDirectionalDrillingPoint(tActDirectionalDrillingPoint);
    }

    /**
     * 修改实际定向钻孔点位列
     * 
     * @param tActDirectionalDrillingPoint 实际定向钻孔点位列
     * @return 结果
     */
    @Override
    public int updateTActDirectionalDrillingPoint(TActDirectionalDrillingPoint tActDirectionalDrillingPoint)
    {
        return tActDirectionalDrillingPointMapper.updateTActDirectionalDrillingPoint(tActDirectionalDrillingPoint);
    }

    /**
     * 批量删除实际定向钻孔点位列
     * 
     * @param ids 需要删除的实际定向钻孔点位列主键
     * @return 结果
     */
    @Override
    public int deleteTActDirectionalDrillingPointByIds(Integer[] ids)
    {
        return tActDirectionalDrillingPointMapper.deleteTActDirectionalDrillingPointByIds(ids);
    }

    /**
     * 删除实际定向钻孔点位列信息
     * 
     * @param id 实际定向钻孔点位列主键
     * @return 结果
     */
    @Override
    public int deleteTActDirectionalDrillingPointById(Integer id)
    {
        return tActDirectionalDrillingPointMapper.deleteTActDirectionalDrillingPointById(id);
    }
    /**
     * 根据实际定向钻孔ID获取轨迹数据
     * @param boreholeId
     * @return
     */
    @Override
    public List<TActDirectionalDrillingPoint> getByActDirectionalBoreholeId(Integer boreholeId) {
        return this.tActDirectionalDrillingPointMapper.getByActDirectionalBoreholeId(boreholeId);
    }
    /**
     * 根据实际定向钻孔ID删除轨迹数据
     * @param boreholeId
     */
    @Override
    public void delByActDirectionalBoreholeId(Integer boreholeId) {
        this.tActDirectionalDrillingPointMapper.delByActDirectionalBoreholeId(boreholeId);
    }
}
