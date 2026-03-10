package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TDesDirectionalDrillingPoint;
import com.ruoyi.hh.physical.mapper.TDesDirectionalDrillingPointMapper;
import com.ruoyi.hh.physical.service.ITDesDirectionalDrillingPointService;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfCoalMiningService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设计定向钻孔点位信息Service业务层处理
 *
 * @author eleven
 * @date 2023-06-29
 */
@Service
public class TDesDirectionalDrillingPointServiceImpl implements ITDesDirectionalDrillingPointService
{
    @Resource
    private TDesDirectionalDrillingPointMapper tDesDirectionalDrillingPointMapper;

    /**
     * 查询设计定向钻孔点位信息
     *
     * @param id 设计定向钻孔点位信息主键
     * @return 设计定向钻孔点位信息
     */
    @Override
    public TDesDirectionalDrillingPoint selectTDesDirectionalDrillingPointById(Integer id)
    {
        return tDesDirectionalDrillingPointMapper.selectTDesDirectionalDrillingPointById(id);
    }

    /**
     * 查询设计定向钻孔点位信息列表
     *
     * @param tDesDirectionalDrillingPoint 设计定向钻孔点位信息
     * @return 设计定向钻孔点位信息
     */
    @Override
    public List<TDesDirectionalDrillingPoint> selectTDesDirectionalDrillingPointList(TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint)
    {
        return tDesDirectionalDrillingPointMapper.selectTDesDirectionalDrillingPointList(tDesDirectionalDrillingPoint);
    }

    /**
     * 新增设计定向钻孔点位信息
     *
     * @param tDesDirectionalDrillingPoint 设计定向钻孔点位信息
     * @return 结果
     */
    @Override
    public int insertTDesDirectionalDrillingPoint(TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint)
    {
        return tDesDirectionalDrillingPointMapper.insertTDesDirectionalDrillingPoint(tDesDirectionalDrillingPoint);
    }

    /**
     * 修改设计定向钻孔点位信息
     *
     * @param tDesDirectionalDrillingPoint 设计定向钻孔点位信息
     * @return 结果
     */
    @Override
    public int updateTDesDirectionalDrillingPoint(TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint)
    {
        return tDesDirectionalDrillingPointMapper.updateTDesDirectionalDrillingPoint(tDesDirectionalDrillingPoint);
    }

    /**
     * 批量删除设计定向钻孔点位信息
     *
     * @param ids 需要删除的设计定向钻孔点位信息主键
     * @return 结果
     */
    @Override
    public int deleteTDesDirectionalDrillingPointByIds(Integer[] ids)
    {
        return tDesDirectionalDrillingPointMapper.deleteTDesDirectionalDrillingPointByIds(ids);
    }

    /**
     * 删除设计定向钻孔点位信息信息
     *
     * @param id 设计定向钻孔点位信息主键
     * @return 结果
     */
    @Override
    public int deleteTDesDirectionalDrillingPointById(Integer id)
    {
        return tDesDirectionalDrillingPointMapper.deleteTDesDirectionalDrillingPointById(id);
    }
    /**
     * 根据设计定向钻孔ID获取轨迹数据
     * @param desBoreholeId
     * @return
     */
    @Override
    public List<TDesDirectionalDrillingPoint> getByDesDirectionalBoreholeId(Integer desBoreholeId) {
        return this.tDesDirectionalDrillingPointMapper.getByDesDirectionalBoreholeId(desBoreholeId);
    }

    /**
     * 根据设计定向钻孔ID删除轨迹数据
     * @param desBoreholeId
     */
    @Override
    public void delByDesDirectionalBoreholeId(Integer desBoreholeId){
        this.tDesDirectionalDrillingPointMapper.delByDesDirectionalBoreholeId(desBoreholeId);
    }
}
