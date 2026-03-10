package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TNavigationPoint;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 导线点相关Mapper接口
 *
 * @author eleven
 * @date 2022-07-13
 */
public interface TNavigationPointMapper {
    /**
     * 查询导线点相关
     *
     * @param id 导线点相关主键
     * @return 导线点相关
     */
    public TNavigationPoint selectTNavigationPointById(Integer id);

    /**
     * 查询导线点相关列表
     *
     * @param tNavigationPoint 导线点相关
     * @return 导线点相关集合
     */
    public List<TNavigationPoint> selectTNavigationPointList(TNavigationPoint tNavigationPoint);

    /**
     * 新增导线点相关
     *
     * @param tNavigationPoint 导线点相关
     * @return 结果
     */
    public Integer insertTNavigationPoint(TNavigationPoint tNavigationPoint);

    /**
     * 修改导线点相关
     *
     * @param tNavigationPoint 导线点相关
     * @return 结果
     */
    public Integer updateTNavigationPoint(TNavigationPoint tNavigationPoint);

    /**
     * 删除导线点相关
     *
     * @param id 导线点相关主键
     * @return 结果
     */
    public Integer deleteTNavigationPointById(Integer id);

    /**
     * 批量删除导线点相关
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTNavigationPointByIds(@RequestParam List<Integer> ids);

    /**
     * 批量添加导线点信息
     *
     * @param list
     */
    public Integer insertTNavigationPointList(@RequestBody List<TNavigationPoint> list);

    /**
     * 查询导线点列表
     *
     * @return 导线点列表
     */
    public List<TNavigationPoint> list();

    /**
     * 根据巷道ID查询导线点信息
     *
     * @param roadwayId 巷道ID
     * @return 导线点列表
     */
    public List<TNavigationPoint> getByRoadwayId(Integer roadwayId);


    /**
     * 分页查询列表
     *
     * @return 列表
     */
    public List<TNavigationPoint> getPageList();

    /**
     * 根据巷道ID删除导线点信息
     * @param roadwayId 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayId(@RequestParam Integer roadwayId);

    /**
     * 根据巷道ID删除导线点信息
     * @param roadwayIds 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayIds(@RequestParam List<Integer> roadwayIds);

    /**
     * 根据巷道ID查询导线点ID列表
     * @param roadwayId 巷道ID
     * @return 导线点ID列表
     */
    public List<Integer> getIdsByRoadWayId(@RequestParam Integer roadwayId);

    /**
     * 根据导线点名称查询导线点ID
     * @param navigationPointName 导线点名称
     * @return ID
     */
    public Integer getIdByNavigationPointName(@Param("navigationPointName") String navigationPointName,
                                              @Param("roadwayId") Integer roadwayId);

    /**
     * 获取导线点名称
     * @return
     */
    @Select("select navigation_point_name from t_navigation_point GROUP BY navigation_point_name")
    public List<String> getNavigationPointAll();

}
