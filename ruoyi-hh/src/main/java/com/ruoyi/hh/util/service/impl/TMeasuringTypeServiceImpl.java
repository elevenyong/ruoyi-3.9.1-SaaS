package com.ruoyi.hh.util.service.impl;

import java.util.List;

import com.ruoyi.hh.util.domain.TMeasuringType;
import com.ruoyi.hh.util.mapper.TMeasuringTypeMapper;
import com.ruoyi.hh.util.service.ITMeasuringTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 测点类型之Service业务层处理
 *
 * @author eleven
 * @date 2022-07-16
 */
@Service
public class TMeasuringTypeServiceImpl implements ITMeasuringTypeService {
    @Resource
    private TMeasuringTypeMapper tMeasuringTypeMapper;

    /**
     * 查询测点类型之
     *
     * @param id 测点类型之主键
     * @return 测点类型之
     */
    @Override
    public TMeasuringType selectTMeasuringTypeById(Integer id) {
        return tMeasuringTypeMapper.selectTMeasuringTypeById(id);
    }

    /**
     * 查询测点类型之列表
     *
     * @param tMeasuringType 测点类型之
     * @return 测点类型之
     */
    @Override
    public List<TMeasuringType> selectTMeasuringTypeList(TMeasuringType tMeasuringType) {
        return tMeasuringTypeMapper.selectTMeasuringTypeList(tMeasuringType);
    }

    /**
     * 新增测点类型之
     *
     * @param tMeasuringType 测点类型之
     * @return 结果
     */
    @Override
    public Integer insertTMeasuringType(TMeasuringType tMeasuringType) {
        return tMeasuringTypeMapper.insertTMeasuringType(tMeasuringType);
    }

    /**
     * 修改测点类型之
     *
     * @param tMeasuringType 测点类型之
     * @return 结果
     */
    @Override
    public Integer updateTMeasuringType(TMeasuringType tMeasuringType) {
        return tMeasuringTypeMapper.updateTMeasuringType(tMeasuringType);
    }

    /**
     * 批量删除测点类型之
     *
     * @param ids 需要删除的测点类型之主键
     * @return 结果
     */
    @Override
    public Integer deleteTMeasuringTypeByIds(List<Integer> ids) {
        return tMeasuringTypeMapper.deleteTMeasuringTypeByIds(ids);
    }

    /**
     * 删除测点类型之信息
     *
     * @param id 测点类型之主键
     * @return 结果
     */
    @Override
    public Integer deleteTMeasuringTypeById(Integer id) {
        return tMeasuringTypeMapper.deleteTMeasuringTypeById(id);
    }

    /**
     * 分页获取列表
     *
     * @return 列表
     */
    @Override
    public List<TMeasuringType> getPageList() {
        return this.tMeasuringTypeMapper.getPageList();
    }
}
