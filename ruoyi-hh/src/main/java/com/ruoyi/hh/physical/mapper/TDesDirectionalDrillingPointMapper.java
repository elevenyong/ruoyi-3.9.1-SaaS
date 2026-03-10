package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TDesDirectionalDrillingPoint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 设计定向钻孔点位信息Mapper接口
 * 
 * @author eleven
 * @date 2023-06-29
 */
public interface TDesDirectionalDrillingPointMapper 
{
    /**
     * 查询设计定向钻孔点位信息
     * 
     * @param id 设计定向钻孔点位信息主键
     * @return 设计定向钻孔点位信息
     */
    public TDesDirectionalDrillingPoint selectTDesDirectionalDrillingPointById(Integer id);

    /**
     * 查询设计定向钻孔点位信息列表
     * 
     * @param tDesDirectionalDrillingPoint 设计定向钻孔点位信息
     * @return 设计定向钻孔点位信息集合
     */
    public List<TDesDirectionalDrillingPoint> selectTDesDirectionalDrillingPointList(TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint);

    /**
     * 新增设计定向钻孔点位信息
     * 
     * @param tDesDirectionalDrillingPoint 设计定向钻孔点位信息
     * @return 结果
     */
    public int insertTDesDirectionalDrillingPoint(TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint);

    /**
     * 修改设计定向钻孔点位信息
     * 
     * @param tDesDirectionalDrillingPoint 设计定向钻孔点位信息
     * @return 结果
     */
    public int updateTDesDirectionalDrillingPoint(TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint);

    /**
     * 删除设计定向钻孔点位信息
     * 
     * @param id 设计定向钻孔点位信息主键
     * @return 结果
     */
    public int deleteTDesDirectionalDrillingPointById(Integer id);

    /**
     * 批量删除设计定向钻孔点位信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDesDirectionalDrillingPointByIds(Integer[] ids);

    /**
     * 根据设计定向钻孔ID获取轨迹数据
     * @param boreholeId
     * @return
     */
    @Select("select * from t_des_directional_drilling_point where borehole_id = #{boreholeId}")
    public List<TDesDirectionalDrillingPoint> getByDesDirectionalBoreholeId(Integer boreholeId);

    /**
     * 根据设计定向钻孔ID删除轨迹数据
     * @param boreholeId
     */
    @Delete("delete from t_des_directional_drilling_point where borehole_id = #{boreholeId}")
    public void delByDesDirectionalBoreholeId(Integer boreholeId);
}
