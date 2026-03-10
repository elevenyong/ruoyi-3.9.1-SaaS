package com.ruoyi.hh.util.service;

import com.ruoyi.hh.util.domain.TDrainagePipeType;

import java.util.List;

/**
 * 抽采管路类型信息Service接口
 * @CreateTime: 2023/8/11 17:48
 * @author eleven
 */ 
public interface ITDrainagePipeTypeService {
    /**
     * 查询封抽采管路类型
     *
     * @param id 封抽采管路类型主键
     * @return 封抽采管路类型
     */
    public TDrainagePipeType selectTDrainagePipeTypeById(Integer id);

    /**
     * 查询封抽采管路类型列表
     *
     * @param tDrainagePipeType 封抽采管路类型
     * @return 封抽采管路类型集合
     */
    public List<TDrainagePipeType> selectTDrainagePipeTypeList(TDrainagePipeType tDrainagePipeType);

    /**
     * 新增封抽采管路类型
     *
     * @param tDrainagePipeType 封抽采管路类型
     * @return 结果
     */
    public int insertTDrainagePipeType(TDrainagePipeType tDrainagePipeType);

    /**
     * 修改封抽采管路类型
     *
     * @param tDrainagePipeType 封抽采管路类型
     * @return 结果
     */
    public int updateTDrainagePipeType(TDrainagePipeType tDrainagePipeType);

    /**
     * 批量删除封抽采管路类型
     *
     * @param ids 需要删除的封抽采管路类型主键集合
     * @return 结果
     */
    public int deleteTDrainagePipeTypeByIds(List<Integer> ids);

    /**
     * 删除封抽采管路类型信息
     *
     * @param id 封抽采管路类型主键
     * @return 结果
     */
    public int deleteTDrainagePipeTypeById(Integer id);
}
