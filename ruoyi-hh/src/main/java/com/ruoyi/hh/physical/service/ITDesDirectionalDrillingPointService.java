package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TDesDirectionalDrillingPoint;

import java.util.List;

/**
 * 设计定向钻孔点位信息Service接口
 * 
 * @author eleven
 * @date 2023-06-29
 */
public interface ITDesDirectionalDrillingPointService 
{
    /**
     * 查询设计定向钻孔点位信息
     * 
     * @param id 设计定向钻孔点位信息主键
     * @return 设计定向钻孔点位信息
     */
    public TDesDirectionalDrillingPoint selectTDesDirectionalDrillingPointById(Integer id);

    /**
     * 查询设计定向钻孔点位信息列表
     * 
     * @param tDesDirectionalDrillingPoint 设计定向钻孔点位信息
     * @return 设计定向钻孔点位信息集合
     */
    public List<TDesDirectionalDrillingPoint> selectTDesDirectionalDrillingPointList(TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint);

    /**
     * 新增设计定向钻孔点位信息
     * 
     * @param tDesDirectionalDrillingPoint 设计定向钻孔点位信息
     * @return 结果
     */
    public int insertTDesDirectionalDrillingPoint(TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint);

    /**
     * 修改设计定向钻孔点位信息
     * 
     * @param tDesDirectionalDrillingPoint 设计定向钻孔点位信息
     * @return 结果
     */
    public int updateTDesDirectionalDrillingPoint(TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint);

    /**
     * 批量删除设计定向钻孔点位信息
     * 
     * @param ids 需要删除的设计定向钻孔点位信息主键集合
     * @return 结果
     */
    public int deleteTDesDirectionalDrillingPointByIds(Integer[] ids);

    /**
     * 删除设计定向钻孔点位信息信息
     * 
     * @param id 设计定向钻孔点位信息主键
     * @return 结果
     */
    public int deleteTDesDirectionalDrillingPointById(Integer id);

    /**
     * 根据设计定向钻孔ID获取轨迹数据
     * @param desBoreholeId
     * @return
     */
    public List<TDesDirectionalDrillingPoint> getByDesDirectionalBoreholeId(Integer desBoreholeId);

    /**
     * 根据设计定向钻孔ID删除轨迹数据
     * @param desBoreholeId
     */
    public void delByDesDirectionalBoreholeId(Integer desBoreholeId);
}
