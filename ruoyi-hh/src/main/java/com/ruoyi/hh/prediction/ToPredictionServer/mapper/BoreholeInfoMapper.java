package com.ruoyi.hh.prediction.ToPredictionServer.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.service
 * @Project：WSCCManageBackend
 * @name：BoreholeInfoMapper
 * @Date：2026/1/20 16:03
 * @Filename：BoreholeInfoMapper
 * @Description
 */
public interface BoreholeInfoMapper {

    @Select("SELECT b.*, c.borehole_category as category_name, " +
            "strftime('%Y-%m-%d %H:%M:%S', b.drilling_date) as drilling_date_str " +
            "FROM t_bk_act_borehole_info b " +
            "LEFT JOIN t_borehole_category c ON b.category_code = c.category_code " +
            "WHERE c.borehole_category = #{categoryName} " +
            "AND b.coal_mining_id IS NOT NULL " +  // 确保有coal_mining_id
            "ORDER BY b.drilling_date DESC, b.id")
    List<Map<String, Object>> selectVerificationBoreholes(@Param("categoryName") String categoryName);

    @Select("SELECT COUNT(1) FROM t_prediction_parameters " +
            "WHERE measurement_date = #{measurementDate} " +
            "AND coal_mining_id = #{coalMiningId}")
    int countByDateAndCoalMiningId(@Param("measurementDate") String measurementDate,
                                   @Param("coalMiningId") Integer coalMiningId);
}