package com.ruoyi.hh.prediction.ToPredictionServer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.service
 * @Project：WSCCManageBackend
 * @name：AttributeValueMapper
 * @Date：2026/1/20 16:37
 * @Filename：AttributeValueMapper
 * @Description
 */
@Mapper
public interface AttributeValueMapper {

    @Select("SELECT * FROM t_attribute_value " +
            "WHERE borehole_id = #{boreholeId} AND attribute_type_id = #{attributeTypeId} " +
            "ORDER BY data_time DESC LIMIT 1")
    Map<String, Object> getLatestByBoreholeAndType(@Param("boreholeId") Integer boreholeId,
                                                   @Param("attributeTypeId") Integer attributeTypeId);

    @Select("SELECT * FROM t_attribute_value " +
            "WHERE borehole_id = #{boreholeId} " +
            "ORDER BY data_time DESC LIMIT 1")
    Map<String, Object> getLatestByBoreholeId(@Param("boreholeId") Integer boreholeId);
}