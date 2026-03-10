package com.ruoyi.hh.prediction.ToPredictionServer.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.mapper
 * @Project：WSCCManageBackend
 * @name：WorkOfCoalMiningMapper
 * @Date：2026/1/21 14:30
 * @Filename：WorkOfCoalMiningMapper
 * @Description
 */
public interface WorkOfCoalMiningMapper {

    @Select("SELECT w.*, f.workface_name " +
            "FROM t_work_of_coal_mining w " +
            "LEFT JOIN t_working_face_of_coal_mining f ON w.workface_id = f.id " +
            "WHERE w.id = #{id}")
    Map<String, Object> selectById(@Param("id") Integer id);

    @Select({
            "<script>",
            "SELECT w.*, f.workface_name " +
                    "FROM t_work_of_coal_mining w " +
                    "LEFT JOIN t_working_face_of_coal_mining f ON w.workface_id = f.id " +
                    "WHERE w.id IN ",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Map<String, Object>> selectByIds(@Param("ids") List<Integer> ids);
}