package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TRoadwayZone;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 区段信息Mapper接口
 *
 * @author eleven
 * @date 2022-07-14
 */
public interface TRoadwayZoneMapper {
    /**
     * 查询区段信息
     *
     * @param id 区段信息主键
     * @return 区段信息
     */
    public TRoadwayZone selectTRoadwayZoneById(@RequestParam Integer id);

    /**
     * 查询区段信息列表
     *
     * @param tRoadwayZone 区段信息
     * @return 区段信息集合
     */
    public List<TRoadwayZone> selectTRoadwayZoneList(TRoadwayZone tRoadwayZone);

    /**
     * 新增区段信息
     *
     * @param tRoadwayZone 区段信息
     * @return 结果
     */
    public Integer insertTRoadwayZone(TRoadwayZone tRoadwayZone);

    /**
     * 修改区段信息
     *
     * @param tRoadwayZone 区段信息
     * @return 结果
     */
    public Integer updateTRoadwayZone(TRoadwayZone tRoadwayZone);

    /**
     * 删除区段信息
     *
     * @param id 区段信息主键
     * @return 结果
     */
    public Integer deleteTRoadwayZoneById(Integer id);

    /**
     * 批量删除区段信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTRoadwayZoneByIds(@RequestParam List<Integer> ids);

    /**
     * 查询区段列表
     *
     * @return
     */
    public List<TRoadwayZone> list();

    /**
     * 批量添加区段
     *
     * @param list 列表
     */
    public Integer insertTRoadwayZoneList(@RequestBody List<TRoadwayZone> list);

    /**
     * 根据巷道ID查询
     *
     * @param roadwayId 巷道ID
     * @return 列表
     */
    public List<TRoadwayZone> getByRoadwayId(Integer roadwayId);

    public List<TRoadwayZone> gettest(@Param("inclinationAngle") Integer inclinationAngle, @Param("roadwayId") Integer roadwayId);

    /**
     * 根据巷道ID删除区段信息
     * @param roadwayId 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayId(@RequestParam Integer roadwayId);

    /**
     * 根据巷道ID删除区段信息
     * @param roadwayIds 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayIds(@RequestParam List<Integer> roadwayIds);

    /**
     * 根据巷道ID查询区段ID列表
     * @param roadwayId 巷道ID
     * @return 区段ID列表
     */
    public List<Integer> getIdsByRoadWayId(@RequestParam Integer roadwayId);

    /**
     * 根据区段名称查询区段ID
     * @param zoneName 区段名称
     * @return ID
     */
    public Integer getIdByZoneName(@Param("zoneName") String zoneName,
                                   @Param("roadwayId") Integer roadwayId);

    /**
     * 获取区段名称
     * @return
     */
    @Select("select zone_name from t_roadway_zone GROUP BY zone_name")
    public List<String> getZoneNameAll();
}
