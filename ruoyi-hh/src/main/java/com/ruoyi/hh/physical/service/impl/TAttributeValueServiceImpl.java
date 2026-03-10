package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TBkActBoreholeInfo;
import com.ruoyi.hh.physical.domain.TCoalPoint;
import com.ruoyi.hh.physical.domain.TAttributeValue;
import com.ruoyi.hh.physical.domain.TAttributeValue;
import com.ruoyi.hh.physical.mapper.TBkActBoreholeInfoMapper;
import com.ruoyi.hh.physical.mapper.TCoalPointMapper;
import com.ruoyi.hh.physical.mapper.TAttributeValueMapper;
import com.ruoyi.hh.physical.service.ITAttributeValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TAttributeValueServiceImpl implements ITAttributeValueService {
    @Resource
    private TAttributeValueMapper tAttributeValueMapper;

    @Resource
    private TBkActBoreholeInfoMapper tBkActBoreholeInfoMapper;

    /**
     * 查询属性值
     *
     * @param id 属性值主键
     * @return 属性值
     */
    @Override
    public TAttributeValue selectTAttributeValueById(Integer id) {
        return tAttributeValueMapper.selectTAttributeValueById(id);
    }

    /**
     * 查询属性值列表
     *
     * @param tAttributeValue 属性值
     * @return 属性值
     */
    @Override
    public List<TAttributeValue> selectTAttributeValueList(TAttributeValue tAttributeValue) {
        List<TAttributeValue> queryAttributeValues = tAttributeValueMapper.selectTAttributeValueList(tAttributeValue);
        ArrayList<TAttributeValue> attributeValues = new ArrayList<>();
        for (TAttributeValue value : queryAttributeValues) {
            if (value.getAttributeValue1() != 0) {
                attributeValues.add(value);
            }
        }

        // 赋值钻孔相关属性
        attributeValues.forEach(attributeValue -> {
            Integer boreholeId = attributeValue.getBoreholeId();
            if (boreholeId != null && boreholeId != 0) {
                TBkActBoreholeInfo boreholeInfo = tBkActBoreholeInfoMapper.selectTBkActBoreholeInfoById(boreholeId);
                attributeValue.setHoleRoadway(boreholeInfo.getRoadwayId());
                attributeValue.setHoleDistance(boreholeInfo.getDistanceToNavigationPoint());
                attributeValue.setHoleAzimuthAngle(Double.valueOf(boreholeInfo.getAzimuthAngle()));
                attributeValue.setHoleInclinationAngle(boreholeInfo.getInclinationAngle());
            }
        });

        return attributeValues;
    }

    /**
     * 新增属性值
     *
     * @param tAttributeValue 属性值
     * @return 结果
     */
    @Override
    public int insertTAttributeValue(TAttributeValue tAttributeValue) {
        return tAttributeValueMapper.insertTAttributeValue(tAttributeValue);
    }

    /**
     * 修改属性值
     *
     * @param tAttributeValue 属性值
     * @return 结果
     */
    @Override
    public int updateTAttributeValue(TAttributeValue tAttributeValue) {
        return tAttributeValueMapper.updateTAttributeValue(tAttributeValue);
    }

    /**
     * 批量删除属性值
     *
     * @param ids 需要删除的属性值主键
     * @return 结果
     */
    @Override
    public int deleteTAttributeValueByIds(Integer[] ids) {
        return tAttributeValueMapper.deleteTAttributeValueByIds(ids);
    }

    /**
     * 删除属性值信息
     *
     * @param id 属性值主键
     * @return 结果
     */
    @Override
    public int deleteTAttributeValueById(Integer id) {
        return tAttributeValueMapper.deleteTAttributeValueById(id);
    }

}
