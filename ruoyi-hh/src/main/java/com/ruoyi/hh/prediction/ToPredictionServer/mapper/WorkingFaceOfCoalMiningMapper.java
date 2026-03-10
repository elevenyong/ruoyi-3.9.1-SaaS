package com.ruoyi.hh.prediction.ToPredictionServer.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.mapper
 * @Project：WSCCManageBackend
 * @name：WorkingFaceOfCoalMiningMapper
 * @Date：2026/1/21 15:03
 * @Filename：WorkingFaceOfCoalMiningMapper
 * @Description
 */
public interface WorkingFaceOfCoalMiningMapper {

    @Select("SELECT * FROM t_working_face_of_coal_mining WHERE id = #{id}")
    Map<String, Object> selectById(@Param("id") Integer id);

    @Select({
            "<script>",
            "SELECT * FROM t_working_face_of_coal_mining WHERE id IN ",
            "<foreach collection='collection' item='item' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"
    })
    List<Map<String, Object>> selectByIds(@Param("ids") List<Integer> ids);
}