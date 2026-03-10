package com.ruoyi.hh.util.service.impl;

import com.ruoyi.hh.util.domain.TDrainagePipeType;
import com.ruoyi.hh.util.mapper.TDrainagePipeTypeMapper;
import com.ruoyi.hh.util.service.ITDrainagePipeTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.util.service.impl
 * @className: TDrainagePipeTypeServiceImpl
 * @author: Eleven
 * @description: 抽采管路信息实现类
 * @date: 2023/8/11 17:50
 * @version: 1.0
 */
@Service
public class TDrainagePipeTypeServiceImpl implements ITDrainagePipeTypeService {
    @Resource
    private TDrainagePipeTypeMapper tDrainagePipeTypeMapper;

    /**
     * 查询抽采管路类型
     *
     * @param id 抽采管路类型主键
     * @return 抽采管路类型
     */
    @Override
    public TDrainagePipeType selectTDrainagePipeTypeById(Integer id)
    {
        return tDrainagePipeTypeMapper.selectTDrainagePipeTypeById(id);
    }

    /**
     * 查询抽采管路类型列表
     *
     * @param tDrainagePipeType 抽采管路类型
     * @return 抽采管路类型
     */
    @Override
    public List<TDrainagePipeType> selectTDrainagePipeTypeList(TDrainagePipeType tDrainagePipeType)
    {
        return tDrainagePipeTypeMapper.selectTDrainagePipeTypeList(tDrainagePipeType);
    }

    /**
     * 新增抽采管路类型
     *
     * @param tDrainagePipeType 抽采管路类型
     * @return 结果
     */
    @Override
    public int insertTDrainagePipeType(TDrainagePipeType tDrainagePipeType)
    {
        return tDrainagePipeTypeMapper.insertTDrainagePipeType(tDrainagePipeType);
    }

    /**
     * 修改抽采管路类型
     *
     * @param tDrainagePipeType 抽采管路类型
     * @return 结果
     */
    @Override
    public int updateTDrainagePipeType(TDrainagePipeType tDrainagePipeType)
    {
        return tDrainagePipeTypeMapper.updateTDrainagePipeType(tDrainagePipeType);
    }

    /**
     * 批量删除抽采管路类型
     *
     * @param ids 需要删除的抽采管路类型主键
     * @return 结果
     */
    @Override
    public int deleteTDrainagePipeTypeByIds(List<Integer> ids)
    {
        return tDrainagePipeTypeMapper.deleteTDrainagePipeTypeByIds(ids);
    }

    /**
     * 删除抽采管路类型信息
     *
     * @param id 抽采管路类型主键
     * @return 结果
     */
    @Override
    public int deleteTDrainagePipeTypeById(Integer id)
    {
        return tDrainagePipeTypeMapper.deleteTDrainagePipeTypeById(id);
    }
}
