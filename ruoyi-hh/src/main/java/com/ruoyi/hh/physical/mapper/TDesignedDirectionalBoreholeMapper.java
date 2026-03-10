package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TDesignedDirectionalBorehole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 设计定向钻孔信息Mapper接口
 *
 * @author eleven
 * @date 2023-07-27
 */
public interface TDesignedDirectionalBoreholeMapper
{
    /**
     * 查询设计定向钻孔信息
     *
     * @param id 设计定向钻孔信息主键
     * @return 设计定向钻孔信息
     */
    public TDesignedDirectionalBorehole selectTDesignedDirectionalBoreholeById(Integer id);

    /**
     * 查询设计定向钻孔信息列表
     *
     * @param tDesignedDirectionalBorehole 设计定向钻孔信息
     * @return 设计定向钻孔信息集合
     */
    public List<TDesignedDirectionalBorehole> selectTDesignedDirectionalBoreholeList(TDesignedDirectionalBorehole tDesignedDirectionalBorehole);

    /**
     * 新增设计定向钻孔信息
     *
     * @param tDesignedDirectionalBorehole 设计定向钻孔信息
     * @return 结果
     */
    public int insertTDesignedDirectionalBorehole(TDesignedDirectionalBorehole tDesignedDirectionalBorehole);

    /**
     * 修改设计定向钻孔信息
     *
     * @param tDesignedDirectionalBorehole 设计定向钻孔信息
     * @return 结果
     */
    public int updateTDesignedDirectionalBorehole(TDesignedDirectionalBorehole tDesignedDirectionalBorehole);

    /**
     * 删除设计定向钻孔信息
     *
     * @param id 设计定向钻孔信息主键
     * @return 结果
     */
    public int deleteTDesignedDirectionalBoreholeById(Integer id);

    /**
     * 批量删除设计定向钻孔信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDesignedDirectionalBoreholeByIds(Integer[] ids);

    /**
     * 根据设计参数Id查询定向钻列表
     * @param designParameterId
     * @return
     */
    @Select("SELECT * FROM `t_des_directional_borehole` where des_parameter_id = #{designParameterId}")
    public List<TDesignedDirectionalBorehole> getByDesignParameterId(Integer designParameterId);

    /**
     * 根据设计参数ID删除设计定向钻孔信息
     * @param designParameterId
     */
    @Delete("delete from t_des_directional_borehole where des_parameter_id =#{designParameterId}")
    public void delByDesignParameterId(Integer designParameterId);

    /**
     * 更改设计钻孔状态（确定设计钻孔方案）
     * @param ids id列表
     */
    public void makeSureDesProgramme(@Param("sign") Integer sign, @Param("list") List<Integer> ids);


    @Select("select count(*) from t_des_directional_borehole where category_code = #{categoryCode} and roadway_id = #{roadwayId}")
    public Integer getDirectBoreholeCount(@Param("categoryCode") Integer categoryCode, @Param("roadwayId") Integer roadwayId);
}
