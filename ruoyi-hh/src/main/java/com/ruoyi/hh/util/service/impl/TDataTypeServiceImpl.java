package com.ruoyi.hh.util.service.impl;

import java.util.List;

import com.ruoyi.hh.util.domain.TDataType;
import com.ruoyi.hh.util.mapper.TDataTypeMapper;
import com.ruoyi.hh.util.service.ITDataTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 煤层数据类型表Service业务层处理
 *
 * @author eleven
 * @date 2022-07-14
 */
@Service
public class TDataTypeServiceImpl implements ITDataTypeService {
    @Resource
    private TDataTypeMapper tDataTypeMapper;

    /**
     * 查询煤层数据类型表
     *
     * @param id 煤层数据类型表主键
     * @return 煤层数据类型表
     */
    @Override
    public TDataType selectTDataTypeById(Integer id) {
        return tDataTypeMapper.selectTDataTypeById(id);
    }

    /**
     * 查询煤层数据类型表列表
     *
     * @param tDataType 煤层数据类型表
     * @return 煤层数据类型表
     */
    @Override
    public List<TDataType> selectTDataTypeList(TDataType tDataType) {
        return tDataTypeMapper.selectTDataTypeList(tDataType);
    }

    /**
     * 新增煤层数据类型表
     *
     * @param tDataType 煤层数据类型表
     * @return 结果
     */
    @Override
    public Integer insertTDataType(TDataType tDataType) {
        return tDataTypeMapper.insertTDataType(tDataType);
    }

    /**
     * 修改煤层数据类型表
     *
     * @param tDataType 煤层数据类型表
     * @return 结果
     */
    @Override
    public Integer updateTDataType(TDataType tDataType) {
        return tDataTypeMapper.updateTDataType(tDataType);
    }

    /**
     * 批量删除煤层数据类型表
     *
     * @param ids 需要删除的煤层数据类型表主键
     * @return 结果
     */
    @Override
    public Integer deleteTDataTypeByIds(List<Integer> ids) {
        return tDataTypeMapper.deleteTDataTypeByIds(ids);
    }

    /**
     * 删除煤层数据类型表信息
     *
     * @param id 煤层数据类型表主键
     * @return 结果
     */
    @Override
    public Integer deleteTDataTypeById(Integer id) {
        return tDataTypeMapper.deleteTDataTypeById(id);
    }

    @Override
    public List<TDataType> getPageList() {
        return this.tDataTypeMapper.getPageList();
    }

    /**
     * 批量插入列表
     *
     * @param list 列表
     * @return 影响行数
     */
    @Override
    public Integer insertTDataTypeList(List<TDataType> list) {
        return this.tDataTypeMapper.insertTDataTypeList(list);
    }
}
