package com.ruoyi.hh.prediction.ToPredictionServer.service;


import java.util.Map;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.service
 * @Project：WSCCManageBackend
 * @name：IAttributeValueService
 * @Date：2026/1/20 16:06
 * @Filename：IAttributeValueService
 * @Description
 */
public interface IAttributeValueService {

    /**
     * 根据钻孔ID和属性类型ID获取最新记录
     */
    Map<String, Object> getLatestByBoreholeAndType(Integer boreholeId, Integer attributeTypeId);

    /**
     * 根据钻孔ID获取最新记录
     */
    Map<String, Object> getLatestByBoreholeId(Integer boreholeId);
}