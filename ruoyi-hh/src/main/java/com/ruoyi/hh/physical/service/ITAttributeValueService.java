package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TAttributeValue;

import java.util.List;

public interface ITAttributeValueService {
    /**
     * 查询属性值
     *
     * @param id 属性值主键
     * @return 属性值
     */
    public TAttributeValue selectTAttributeValueById(Integer id);

    /**
     * 查询属性值列表
     *
     * @param tAttributeValue 属性值
     * @return 属性值集合
     */
    public List<TAttributeValue> selectTAttributeValueList(TAttributeValue tAttributeValue);

    /**
     * 新增属性值
     *
     * @param tAttributeValue 属性值
     * @return 结果
     */
    public int insertTAttributeValue(TAttributeValue tAttributeValue);

    /**
     * 修改属性值
     *
     * @param tAttributeValue 属性值
     * @return 结果
     */
    public int updateTAttributeValue(TAttributeValue tAttributeValue);

    /**
     * 批量删除属性值
     *
     * @param ids 需要删除的属性值主键集合
     * @return 结果
     */
    public int deleteTAttributeValueByIds(Integer[] ids);

    /**
     * 删除属性值信息
     *
     * @param id 属性值主键
     * @return 结果
     */
    public int deleteTAttributeValueById(Integer id);

}
