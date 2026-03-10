package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TActDirectionalDrillingPoint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 实际定向钻孔点位列Mapper接口
 * 
 * @author eleven
 * @date 2023-06-29
 */
public interface TActDirectionalDrillingPointMapper 
{
    /**
     * 查询实际定向钻孔点位列
     * 
     * @param id 实际定向钻孔点位列主键
     * @return 实际定向钻孔点位列
     */
    public TActDirectionalDrillingPoint selectTActDirectionalDrillingPointById(Integer id);

    /**
     * 查询实际定向钻孔点位列列表
     * 
     * @param tActDirectionalDrillingPoint 实际定向钻孔点位列
     * @return 实际定向钻孔点位列集合
     */
    public List<TActDirectionalDrillingPoint> selectTActDirectionalDrillingPointList(TActDirectionalDrillingPoint tActDirectionalDrillingPoint);

    /**
     * 新增实际定向钻孔点位列
     * 
     * @param tActDirectionalDrillingPoint 实际定向钻孔点位列
     * @return 结果
     */
    public int insertTActDirectionalDrillingPoint(TActDirectionalDrillingPoint tActDirectionalDrillingPoint);

    /**
     * 修改实际定向钻孔点位列
     * 
     * @param tActDirectionalDrillingPoint 实际定向钻孔点位列
     * @return 结果
     */
    public int updateTActDirectionalDrillingPoint(TActDirectionalDrillingPoint tActDirectionalDrillingPoint);

    /**
     * 删除实际定向钻孔点位列
     * 
     * @param id 实际定向钻孔点位列主键
     * @return 结果
     */
    public int deleteTActDirectionalDrillingPointById(Integer id);

    /**
     * 批量删除实际定向钻孔点位列
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTActDirectionalDrillingPointByIds(Integer[] ids);

    /**
     * 根据实际定向钻孔ID获取轨迹数据
     * @param boreholeId
     * @return
     */
    @Select("select * from t_act_directional_drilling_point where borehole_id = #{boreholeId}")
    public List<TActDirectionalDrillingPoint> getByActDirectionalBoreholeId(Integer boreholeId);

    /**
     * 根据实际定向钻孔ID删除轨迹数据
     * @param boreholeId
     */
    @Delete("delete from t_act_directional_drilling_point where borehole_id = #{boreholeId}")
    public void delByActDirectionalBoreholeId(Integer boreholeId);
}
