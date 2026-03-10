package com.ruoyi.hh.util.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.util.domain.THolesealType;
import com.ruoyi.hh.util.mapper.THolesealTypeMapper;
import com.ruoyi.hh.util.service.ITHolesealTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 封孔方式信息Service业务层处理
 * 
 * @author eleven
 * @date 2022-09-22
 */
@Service
public class THolesealTypeServiceImpl implements ITHolesealTypeService
{
    @Resource
    private THolesealTypeMapper tHolesealTypeMapper;

    /**
     * 查询封孔方式信息
     * 
     * @param id 封孔方式信息主键
     * @return 封孔方式信息
     */
    @Override
    public THolesealType selectTHolesealTypeById(Integer id)
    {
        return tHolesealTypeMapper.selectTHolesealTypeById(id);
    }

    /**
     * 查询封孔方式信息列表
     * 
     * @param tHolesealType 封孔方式信息
     * @return 封孔方式信息
     */
    @Override
    public List<THolesealType> selectTHolesealTypeList(THolesealType tHolesealType)
    {
        return tHolesealTypeMapper.selectTHolesealTypeList(tHolesealType);
    }

    /**
     * 新增封孔方式信息
     * 
     * @param tHolesealType 封孔方式信息
     * @return 结果
     */
    @Override
    public int insertTHolesealType(THolesealType tHolesealType)
    {
        tHolesealType.setCreateTime(DateUtils.getNowDate());
        return tHolesealTypeMapper.insertTHolesealType(tHolesealType);
    }

    /**
     * 修改封孔方式信息
     * 
     * @param tHolesealType 封孔方式信息
     * @return 结果
     */
    @Override
    public int updateTHolesealType(THolesealType tHolesealType)
    {
        tHolesealType.setUpdateTime(DateUtils.getNowDate());
        return tHolesealTypeMapper.updateTHolesealType(tHolesealType);
    }

    /**
     * 批量删除封孔方式信息
     * 
     * @param ids 需要删除的封孔方式信息主键
     * @return 结果
     */
    @Override
    public int deleteTHolesealTypeByIds(List<Integer> ids)
    {
        return tHolesealTypeMapper.deleteTHolesealTypeByIds(ids);
    }

    /**
     * 删除封孔方式信息信息
     * 
     * @param id 封孔方式信息主键
     * @return 结果
     */
    @Override
    public int deleteTHolesealTypeById(Integer id)
    {
        return tHolesealTypeMapper.deleteTHolesealTypeById(id);
    }
}
