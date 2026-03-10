package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TCoalPoint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 煤层对应的坐标点信息Mapper接口
 *
 * @author eleven
 * @date 2022-10-20
 */
public interface TCoalPointMapper {
    /**
     * 查询煤层对应的坐标点信息
     *
     * @param id 煤层对应的坐标点信息主键
     * @return 煤层对应的坐标点信息
     */
    public TCoalPoint selectTCoalPointById(Integer id);

    /**
     * 查询煤层对应的坐标点信息列表
     *
     * @param TCoalPoint 煤层对应的坐标点信息
     * @return 煤层对应的坐标点信息集合
     */
    public List<TCoalPoint> selectTCoalPointList(TCoalPoint TCoalPoint);

    /**
     * 新增煤层对应的坐标点信息
     *
     * @param TCoalPoint 煤层对应的坐标点信息
     * @return 结果
     */
    public int insertTCoalPoint(TCoalPoint TCoalPoint);

    /**
     * 修改煤层对应的坐标点信息
     *
     * @param TCoalPoint 煤层对应的坐标点信息
     * @return 结果
     */
    public int updateTCoalPoint(TCoalPoint TCoalPoint);

    /**
     * 删除煤层对应的坐标点信息
     *
     * @param id 煤层对应的坐标点信息主键
     * @return 结果
     */
    public int deleteTCoalPointById(Integer id);

    /**
     * 批量删除煤层对应的坐标点信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTCoalPointByIds(Integer[] ids);

    /**
     * 根据煤层信息ID查询煤层坐标列表
     *
     * @param coalInfoId 煤层信息id
     * @return 煤层坐标列表
     */
    public List<TCoalPoint> getByCoalInfoId(Integer coalInfoId);

    /**
     * 根据断层ID获取断面点数据
     *
     * @param geoFaultId 断层ID
     * @return geoFaultPoints
     */
    public List<TCoalPoint> getPointsByFaultId(Integer geoFaultId);

    /**
     * 根据陷落柱ID获取陷落柱数据
     * @param geoFallenId 陷落柱ID
     * @return fallenColumnPoints
     */
    public List<TCoalPoint> getPointsByFallenColumnId(Integer geoFallenId);

    int deleteTCoalPointByCoalInfoId(Integer coalInfoId);

    @Delete("delete from t_coal_point where data_type = 2 and geofault_id = #{faultId}")
    int deleteTCoalPointByFaultId(@Param("faultId") Integer faultId);

    @Delete("delete from t_coal_point where data_type = 3 and geofallen_id = #{geofallenId}")
    int deleteTCoalPointByFallenColumnId(@Param("geofallenId") Integer fallenColumnId);
}
