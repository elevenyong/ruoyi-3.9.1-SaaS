package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TCoalPoint;
import com.ruoyi.hh.physical.domain.TAttributeType;
import com.ruoyi.hh.physical.domain.TAttributeType;
import com.ruoyi.hh.physical.mapper.TCoalPointMapper;
import com.ruoyi.hh.physical.mapper.TAttributeTypeMapper;
import com.ruoyi.hh.physical.service.ITAttributeTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class TAttributeTypeServiceImpl implements ITAttributeTypeService {
    @Resource
    private TAttributeTypeMapper tAttributeTypeMapper;

    /**
     * 查询属性类型
     *
     * @param id 属性类型主键
     * @return 属性类型
     */
    @Override
    public TAttributeType selectTAttributeTypeById(Long id) {
        return tAttributeTypeMapper.selectTAttributeTypeById(id);
    }

    /**
     * 查询属性类型列表
     *
     * @param tAttributeType 属性类型
     * @return 属性类型
     */
    @Override
    public List<TAttributeType> selectTAttributeTypeList(TAttributeType tAttributeType) {
        return tAttributeTypeMapper.selectTAttributeTypeList(tAttributeType);
    }

    /**
     * 新增属性类型
     *
     * @param tAttributeType 属性类型
     * @return 结果
     */
    @Override
    public int insertTAttributeType(TAttributeType tAttributeType) {
        return tAttributeTypeMapper.insertTAttributeType(tAttributeType);
    }

    /**
     * 修改属性类型
     *
     * @param tAttributeType 属性类型
     * @return 结果
     */
    @Override
    public int updateTAttributeType(TAttributeType tAttributeType) {
        return tAttributeTypeMapper.updateTAttributeType(tAttributeType);
    }

    /**
     * 批量删除属性类型
     *
     * @param ids 需要删除的属性类型主键
     * @return 结果
     */
    @Override
    public int deleteTAttributeTypeByIds(Long[] ids) {
        return tAttributeTypeMapper.deleteTAttributeTypeByIds(ids);
    }

    /**
     * 删除属性类型信息
     *
     * @param id 属性类型主键
     * @return 结果
     */
    @Override
    public int deleteTAttributeTypeById(Long id) {
        return tAttributeTypeMapper.deleteTAttributeTypeById(id);
    }

}
