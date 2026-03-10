package com.ruoyi.hh.business.service.impl;

import com.ruoyi.hh.business.domain.TGasContentPressure;
import com.ruoyi.hh.business.mapper.TGasContentPressureMapper;
import com.ruoyi.hh.business.service.ITGasContentPressureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 瓦斯含量/压力台账Service业务层处理
 *
 * @author eleven
 * @date 2022-07-27
 */
@Service
public class TGasContentPressureServiceImpl implements ITGasContentPressureService {
    @Resource
    private TGasContentPressureMapper tGasContentPressureMapper;

    /**
     * 查询瓦斯含量/压力台账
     *
     * @param id 瓦斯含量/压力台账主键
     * @return 瓦斯含量/压力台账
     */
    @Override
    public TGasContentPressure selectTGasContentPressureById(Integer id) {
        return tGasContentPressureMapper.selectTGasContentPressureById(id);
    }

    /**
     * 查询瓦斯含量/压力台账列表
     *
     * @param tGasContentPressure 瓦斯含量/压力台账
     * @return 瓦斯含量/压力台账
     */
    @Override
    public List<TGasContentPressure> selectTGasContentPressureList(TGasContentPressure tGasContentPressure) {
        return tGasContentPressureMapper.selectTGasContentPressureList(tGasContentPressure);
    }

    /**
     * 新增瓦斯含量/压力台账
     *
     * @param tGasContentPressure 瓦斯含量/压力台账
     * @return 结果
     */
    @Override
    public Integer insertTGasContentPressure(TGasContentPressure tGasContentPressure) {
        return tGasContentPressureMapper.insertTGasContentPressure(tGasContentPressure);
    }

    /**
     * 修改瓦斯含量/压力台账
     *
     * @param tGasContentPressure 瓦斯含量/压力台账
     * @return 结果
     */
    @Override
    public Integer updateTGasContentPressure(TGasContentPressure tGasContentPressure) {
        return tGasContentPressureMapper.updateTGasContentPressure(tGasContentPressure);
    }

    /**
     * 批量删除瓦斯含量/压力台账
     *
     * @param ids 需要删除的瓦斯含量/压力台账主键
     * @return 结果
     */
    @Override
    public Integer deleteTGasContentPressureByIds(List<Integer> ids) {
        return tGasContentPressureMapper.deleteTGasContentPressureByIds(ids);
    }

    /**
     * 删除瓦斯含量/压力台账信息
     *
     * @param id 瓦斯含量/压力台账主键
     * @return 结果
     */
    @Override
    public Integer deleteTGasContentPressureById(Integer id) {
        return tGasContentPressureMapper.deleteTGasContentPressureById(id);
    }

    @Override
    public List<TGasContentPressure> getData(Integer roadwayId,
                                             List<Integer> purposeboreholeid,
                                             Date startdate,
                                             Date enddate) {
        return this.tGasContentPressureMapper.getData(roadwayId,  purposeboreholeid, startdate, enddate);
    }

    /**
     * 根据钻孔ID删除瓦斯含量信息
     * @param boreholeId 钻孔ID
     * @return 结果
     */
    @Override
    public Integer delByBoreholeId(Integer boreholeId){
        return this.tGasContentPressureMapper.delByBoreholeId(boreholeId);
    }
    /**
     * 根据钻孔ID删除瓦斯含量信息
     * @param boreholeIds 钻孔ID
     * @return 结果
     */
    @Override
    public Integer delByBoreholeIds(List<Integer> boreholeIds){
        return this.tGasContentPressureMapper.delByBoreholeIds(boreholeIds);
    }
}
