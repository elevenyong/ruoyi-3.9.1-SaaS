package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TActDirectionalDrillingPoint;

import java.util.List;

/**
 * 实际定向钻孔点位列Service接口
 * 
 * @author eleven
 * @date 2023-06-29
 */
public interface ITActDirectionalDrillingPointService 
{
    /**
     * 查询实际定向钻孔点位列
     * 
     * @param id 实际定向钻孔点位列主键
     * @return 实际定向钻孔点位列
     */
    public TActDirectionalDrillingPoint selectTActDirectionalDrillingPointById(Integer id);

    /**
     * 查询实际定向钻孔点位列列表
     * 
     * @param tActDirectionalDrillingPoint 实际定向钻孔点位列
     * @return 实际定向钻孔点位列集合
     */
    public List<TActDirectionalDrillingPoint> selectTActDirectionalDrillingPointList(TActDirectionalDrillingPoint tActDirectionalDrillingPoint);

    /**
     * 新增实际定向钻孔点位列
     * 
     * @param tActDirectionalDrillingPoint 实际定向钻孔点位列
     * @return 结果
     */
    public int insertTActDirectionalDrillingPoint(TActDirectionalDrillingPoint tActDirectionalDrillingPoint);

    /**
     * 修改实际定向钻孔点位列
     * 
     * @param tActDirectionalDrillingPoint 实际定向钻孔点位列
     * @return 结果
     */
    public int updateTActDirectionalDrillingPoint(TActDirectionalDrillingPoint tActDirectionalDrillingPoint);

    /**
     * 批量删除实际定向钻孔点位列
     * 
     * @param ids 需要删除的实际定向钻孔点位列主键集合
     * @return 结果
     */
    public int deleteTActDirectionalDrillingPointByIds(Integer[] ids);

    /**
     * 删除实际定向钻孔点位列信息
     * 
     * @param id 实际定向钻孔点位列主键
     * @return 结果
     */
    public int deleteTActDirectionalDrillingPointById(Integer id);

    /**
     * 根据实际定向钻孔ID获取轨迹数据
     * @param actBoreholeId
     * @return
     */
    public List<TActDirectionalDrillingPoint> getByActDirectionalBoreholeId(Integer actBoreholeId);

    /**
     * 根据实际定向钻孔ID删除轨迹数据
     * @param actBoreholeId
     */
    public void delByActDirectionalBoreholeId(Integer actBoreholeId);
}
