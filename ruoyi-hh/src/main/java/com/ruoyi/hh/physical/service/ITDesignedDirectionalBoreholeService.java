package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TDesignedDirectionalBorehole;

import java.util.List;

/**
 * 设计定向钻孔信息Service接口
 *
 * @author eleven
 * @date 2023-07-27
 */
public interface ITDesignedDirectionalBoreholeService
{
    /**
     * 查询设计定向钻孔信息
     *
     * @param id 设计定向钻孔信息主键
     * @return 设计定向钻孔信息
     */
    public TDesignedDirectionalBorehole selectTDesignedDirectionalBoreholeById(Integer id);

    /**
     * 查询设计定向钻孔信息列表
     *
     * @param tDesignedDirectionalBorehole 设计定向钻孔信息
     * @return 设计定向钻孔信息集合
     */
    public List<TDesignedDirectionalBorehole> selectTDesignedDirectionalBoreholeList(TDesignedDirectionalBorehole tDesignedDirectionalBorehole);

    /**
     * 新增设计定向钻孔信息
     *
     * @param tDesignedDirectionalBorehole 设计定向钻孔信息
     * @return 结果
     */
    public int insertTDesignedDirectionalBorehole(TDesignedDirectionalBorehole tDesignedDirectionalBorehole);

    /**
     * 修改设计定向钻孔信息
     *
     * @param tDesignedDirectionalBorehole 设计定向钻孔信息
     * @return 结果
     */
    public int updateTDesignedDirectionalBorehole(TDesignedDirectionalBorehole tDesignedDirectionalBorehole);

    /**
     * 批量删除设计定向钻孔信息
     *
     * @param ids 需要删除的设计定向钻孔信息主键集合
     * @return 结果
     */
    public int deleteTDesignedDirectionalBoreholeByIds(Integer[] ids);

    /**
     * 删除设计定向钻孔信息信息
     *
     * @param id 设计定向钻孔信息主键
     * @return 结果
     */
    public int deleteTDesignedDirectionalBoreholeById(Integer id);

    /**
     * 根据设计参数Id查询定向钻列表
     * @param designParameterId
     * @return
     */
    public List<TDesignedDirectionalBorehole> getByDesignParameterId(Integer designParameterId);

    /**
     * 根据设计参数ID删除设计定向钻孔信息
     * @param designParameterId
     */
    public void delByDesignParameterId(Integer designParameterId);

    /**
     * 确认设计方案
     * @param sign
     * @param ids
     */
    void makeSureDesProgramme(Integer sign, List<Integer> ids);

    Integer getDirectBoreholeCount(Integer type, Integer roadwayId);
}

