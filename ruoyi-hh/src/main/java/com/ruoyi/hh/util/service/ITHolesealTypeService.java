package com.ruoyi.hh.util.service;

import java.util.List;

import com.ruoyi.hh.util.domain.THolesealType;

/**
 * 封孔方式信息Service接口
 * 
 * @author eleven
 * @date 2022-09-22
 */
public interface ITHolesealTypeService 
{
    /**
     * 查询封孔方式信息
     * 
     * @param id 封孔方式信息主键
     * @return 封孔方式信息
     */
    public THolesealType selectTHolesealTypeById(Integer id);

    /**
     * 查询封孔方式信息列表
     * 
     * @param tHolesealType 封孔方式信息
     * @return 封孔方式信息集合
     */
    public List<THolesealType> selectTHolesealTypeList(THolesealType tHolesealType);

    /**
     * 新增封孔方式信息
     * 
     * @param tHolesealType 封孔方式信息
     * @return 结果
     */
    public int insertTHolesealType(THolesealType tHolesealType);

    /**
     * 修改封孔方式信息
     * 
     * @param tHolesealType 封孔方式信息
     * @return 结果
     */
    public int updateTHolesealType(THolesealType tHolesealType);

    /**
     * 批量删除封孔方式信息
     * 
     * @param ids 需要删除的封孔方式信息主键集合
     * @return 结果
     */
    public int deleteTHolesealTypeByIds(List<Integer> ids);

    /**
     * 删除封孔方式信息信息
     * 
     * @param id 封孔方式信息主键
     * @return 结果
     */
    public int deleteTHolesealTypeById(Integer id);
}
