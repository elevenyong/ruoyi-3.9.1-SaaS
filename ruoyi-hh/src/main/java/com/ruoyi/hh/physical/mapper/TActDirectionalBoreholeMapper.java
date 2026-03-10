package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TActDirectionalBorehole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 实际定向钻孔信息Mapper接口
 *
 * @author eleven
 * @date 2023-07-27
 */
public interface TActDirectionalBoreholeMapper {
    /**
     * 查询实际定向钻孔信息
     *
     * @param id 实际定向钻孔信息主键
     * @return 实际定向钻孔信息
     */
    public TActDirectionalBorehole selectTActDirectionalBoreholeById(Integer id);

    /**
     * 查询实际定向钻孔信息列表
     *
     * @param tActDirectionalBorehole 实际定向钻孔信息
     * @return 实际定向钻孔信息集合
     */
    public List<TActDirectionalBorehole> selectTActDirectionalBoreholeList(TActDirectionalBorehole tActDirectionalBorehole);

    /**
     * 新增实际定向钻孔信息
     *
     * @param tActDirectionalBorehole 实际定向钻孔信息
     * @return 结果
     */
    public int insertTActDirectionalBorehole(TActDirectionalBorehole tActDirectionalBorehole);

    /**
     * 修改实际定向钻孔信息
     *
     * @param tActDirectionalBorehole 实际定向钻孔信息
     * @return 结果
     */
    public int updateTActDirectionalBorehole(TActDirectionalBorehole tActDirectionalBorehole);

    /**
     * 删除实际定向钻孔信息
     *
     * @param id 实际定向钻孔信息主键
     * @return 结果
     */
    public int deleteTActDirectionalBoreholeById(Integer id);

    /**
     * 批量删除实际定向钻孔信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTActDirectionalBoreholeByIds(Integer[] ids);

    @Select("select * from t_act_directional_borehole where roadway_id = #{roadwayId}")
    List<TActDirectionalBorehole> selectTActDirectionalBoreholeByRoadwayId(Integer id);


    @Select("select count(*) from t_act_directional_borehole where category_code = #{categoryCode} and roadway_id = #{roadwayId}")
    Integer getDirectBoreholeCount(@Param("categoryCode") Integer categoryCode, @Param("roadwayId") Integer roadwayId);
}
