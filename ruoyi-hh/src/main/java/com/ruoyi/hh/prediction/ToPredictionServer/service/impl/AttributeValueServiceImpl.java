package com.ruoyi.hh.prediction.ToPredictionServer.service.impl;

import com.ruoyi.hh.prediction.ToPredictionServer.mapper.AttributeValueMapper;
import com.ruoyi.hh.prediction.ToPredictionServer.service.IAttributeValueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.service.impl
 * @Project：WSCCManageBackend
 * @name：AttributeValueServiceImpl
 * @Date：2026/1/20 16:38
 * @Filename：AttributeValueServiceImpl
 * @Description
 */
@Service
public class AttributeValueServiceImpl implements IAttributeValueService {

    @Resource
    private AttributeValueMapper attributeValueMapper;

    @Override
    public Map<String, Object> getLatestByBoreholeAndType(Integer boreholeId, Integer attributeTypeId) {
        return attributeValueMapper.getLatestByBoreholeAndType(boreholeId, attributeTypeId);
    }

    @Override
    public Map<String, Object> getLatestByBoreholeId(Integer boreholeId) {
        return attributeValueMapper.getLatestByBoreholeId(boreholeId);
    }
}