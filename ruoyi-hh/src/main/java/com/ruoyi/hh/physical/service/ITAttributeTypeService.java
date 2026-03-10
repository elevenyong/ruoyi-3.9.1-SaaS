package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TAttributeType;

import java.util.List;

public interface ITAttributeTypeService {
    /**
     * 查询属性类型
     *
     * @param id 属性类型主键
     * @return 属性类型
     */
    public TAttributeType selectTAttributeTypeById(Long id);

    /**
     * 查询属性类型列表
     *
     * @param tAttributeType 属性类型
     * @return 属性类型集合
     */
    public List<TAttributeType> selectTAttributeTypeList(TAttributeType tAttributeType);

    /**
     * 新增属性类型
     *
     * @param tAttributeType 属性类型
     * @return 结果
     */
    public int insertTAttributeType(TAttributeType tAttributeType);

    /**
     * 修改属性类型
     *
     * @param tAttributeType 属性类型
     * @return 结果
     */
    public int updateTAttributeType(TAttributeType tAttributeType);

    /**
     * 批量删除属性类型
     *
     * @param ids 需要删除的属性类型主键集合
     * @return 结果
     */
    public int deleteTAttributeTypeByIds(Long[] ids);

    /**
     * 删除属性类型信息
     *
     * @param id 属性类型主键
     * @return 结果
     */
    public int deleteTAttributeTypeById(Long id);

}
