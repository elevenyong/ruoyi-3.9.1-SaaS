package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 设计钻孔Mapper接口
 *
 * @author eleven
 * @date 2022-07-12
 */
public interface TDesignedBoreholeMapper {
    /**
     * 查询设计钻孔
     *
     * @param id 设计钻孔主键
     * @return 设计钻孔
     */
    public TDesignedBorehole selectTDesignedBoreholeById(Integer id);

    /**
     * 查询设计钻孔列表
     *
     * @param tDesignedBorehole 设计钻孔
     * @return 设计钻孔集合
     */
    public List<TDesignedBorehole> selectTDesignedBoreholeList(TDesignedBorehole tDesignedBorehole);

    /**
     * 新增设计钻孔
     *
     * @param tDesignedBorehole 设计钻孔
     */
    public Integer insertTDesignedBorehole(TDesignedBorehole tDesignedBorehole);

    /**
     * 修改设计钻孔
     *
     * @param tDesignedBorehole 设计钻孔
     * @return 结果
     */
    public Integer updateTDesignedBorehole(TDesignedBorehole tDesignedBorehole);

    /**
     * 删除设计钻孔
     *
     * @param id 设计钻孔主键
     * @return 结果
     */
    public Integer deleteTDesignedBoreholeById(Integer id);

    /**
     * 批量删除设计钻孔
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTDesignedBoreholeByIds(List<Integer> ids);

    /**
     * 批量添加巷道
     *
     * @param list 列表
     */
    //public void insertTDesignedBoreholeList(List<TDesignedBorehole> list);

    /**
     * 多表联查设计钻孔信息列表
     *
     * @return 设计钻孔信息列表
     */
    public List<TDesignedBorehole> getPageList();

    /**
     * @param groupNo 组号
     * @return 列表
     */

    public List<TDesignedBorehole> getByGroupId(String groupNo);

    /**
     * 根据巷道ID和类别ID查询
     *
     * @param roadwayId         巷道ID
     * @param purposeBoreholeId 类别ID
     * @return
     */
    public List<TDesignedBorehole> getByRoadWayIdAndpurposeboreId(Integer roadwayId, Integer purposeBoreholeId);


    /**
     * 删除所有数据
     *
     * @return
     */
    public Integer delAll();

    /**
     * 根据巷道ID删除钻孔信息
     * @param roadwayId 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayId(@RequestParam Integer roadwayId);

    /**
     * 根据巷道ID删除钻孔信息
     * @param roadWayIds 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayIds(@RequestParam List<Integer> roadWayIds);

    /**
     * 通过巷道ID获取设计钻孔ID列表
     * @param roadwayId 巷道ID
     * @return 列表
     */
    public List<Integer> getIdsByRoadWayId(@RequestParam Integer roadwayId);

    /**
     * 根据巷道区段ID获取实钻ID列表
     * @param roadwayZoneId 区段ID
     * @return 实钻ID列表
     */
    public List<Integer> getIdsRoadWayZoneId(@RequestParam Integer roadwayZoneId);

    /**
     * 根据导线点ID查询实钻ID列表
     * @param navigationPointId 导线点ID
     * @return 列表
     */
    public List<Integer> getIdsNavigationPointId(@RequestParam Integer navigationPointId);

    /**
     * 根据区段ID删除设计钻孔信息
     * @param roadwayZoneId 区段ID
     * @return 结果
     */
    public Integer delByRoadwayZoneId(@RequestParam Integer roadwayZoneId);
    public Integer delByRoadwayZoneIds (@RequestParam List<Integer> roadwayZoneIds);

    /**
     * 根据导线点ID删除设计钻孔信息
     * @param navigationPointId 导线点ID
     * @return 结果
     */
    public Integer delByNavigationPointId (@RequestParam Integer navigationPointId);
    public Integer delNavigationPointIds  (@RequestParam List<Integer> navigationPointIds);

    /**
     * 通过区段ID获取设计钻孔列表
     * @param roadwayZoneId 区段ID
     * @return 设计钻孔列表
     */
    public List<TDesignedBorehole> getByRoadWayZoneId(@RequestParam Integer roadwayZoneId);

    /**
     * 根据巷道ID查询设计钻孔列表
     * @param roadwayId 巷道ID
     * @return 设计钻孔列表
     */
    public List<TDesignedBorehole> getByRoadwayId(@RequestParam Integer roadwayId);

    /**
     * 更改设计钻孔状态（确定设计钻孔方案）
     * @param ids id列表
     */
    public void makeSureDesProgramme(@Param("sign") Integer sign,@Param("list") List<Integer> ids);

    /**
     * 获取设计钻孔ID
     * @param desParameterId
     * @return
     */
    public List<Integer> getBoreholeIdByParam(@Param("desParameterId") Integer desParameterId
                                              );

    /**
     * 通过巷道ID，组号和孔号确定设计钻孔信息
     * @param roadwayId 巷道ID
     * @param groupNo 组号
     * @param boreholeNo 孔号
     * @return
     */
    @Select("select * from t_designed_borehole where roadway_id =#{roadwayId} and group_no=#{groupNo} and borehole_no =#{boreholeNo}")
    public TDesignedBorehole findDesBoreholeInfo(@Param("roadwayId")Integer roadwayId,@Param("groupNo")String groupNo,@Param("boreholeNo")String boreholeNo);

    /**
     * 根据设计参数ID删除设计钻孔信息
     * @param designParameterId
     */
    @Delete("delete from t_designed_borehole where des_parameter_id =#{designParameterId}")
    public void delByDesignParameterId(Integer designParameterId);

    /**
     * 根据设计参数Id获取设计钻孔ID列表
     * @param desParameterId
     * @return
     */
    @Select("select id from t_designed_borehole where des_parameter_id=#{id}")
    public List<Integer> getIdsByDesignParameterId(Integer desParameterId);

    @Select("select count(*) from t_designed_borehole where category_code = #{categoryCode} and roadway_id = #{roadwayId}")
    Integer getNormalBoreholeCount(@Param("categoryCode") Integer categoryCode, @Param("roadwayId") Integer roadwayId);
}
