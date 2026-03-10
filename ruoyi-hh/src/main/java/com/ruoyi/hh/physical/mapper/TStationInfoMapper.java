package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TStationInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 测点基本信息Mapper接口
 * 
 * @author eleven
 * @date 2023-05-16
 */
public interface TStationInfoMapper 
{
    /**
     * 查询测点基本信息
     * 
     * @param id 测点基本信息主键
     * @return 测点基本信息
     */
    public TStationInfo selectTStationInfoById(Integer id);

    /**
     * 查询测点基本信息列表
     * 
     * @param tStationInfo 测点基本信息
     * @return 测点基本信息集合
     */
    public List<TStationInfo> selectTStationInfoList(TStationInfo tStationInfo);

    /**
     * 新增测点基本信息
     * 
     * @param tStationInfo 测点基本信息
     * @return 结果
     */
    public int insertTStationInfo(TStationInfo tStationInfo);

    /**
     * 修改测点基本信息
     * 
     * @param tStationInfo 测点基本信息
     * @return 结果
     */
    public int updateTStationInfo(TStationInfo tStationInfo);

    /**
     * 删除测点基本信息
     * 
     * @param id 测点基本信息主键
     * @return 结果
     */
    public int deleteTStationInfoById(Integer id);

    /**
     * 批量删除测点基本信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTStationInfoByIds(Integer[] ids);

    /**
     * 根据测点编码查询测点信息
     * @param measurePointCode 测点编码
     * @return 测点信息
     */
    @Select("select * from t_station_info where measure_point_code = #{measurePointCode} and sensor_type_name =#{sensorTypeName}")
    public TStationInfo getByMeasurePointCode(@Param("measurePointCode") String measurePointCode,@Param("sensorTypeName")String sensorTypeName);

    /**
     * 根据测点编码修改测点信息
     * @param tStationInfo 测点信息
     */
    public int updateByMeasurePointCode(TStationInfo tStationInfo);

    /**
     * 查询测点信息中瓦斯累计量的测点的安装位置
     * @return 列表
     */
    @Select("select * from t_station_info where measure_point_value_type ='LJ' group by measure_point_local")
    public List<TStationInfo> getLJMeasurePointLocal();

    /**
     * 通过测点安装位置修改测点位置顺序
     * @param serial
     * @param measurePointLocal
     */
    @Update("update t_station_info set serial =#{serial} where measure_point_local=#{measurePointLocal}")
    public int updateSerialByLocal(@Param("serial") Integer serial,@Param("measurePointLocal")String measurePointLocal);

    /**
     * 根据抽采单元ID查询测点编码（之后根据测点安装位置得到抽采数据）
     * @param drainageUnitId
     * @CreateTime: 2023/7/12 10:34
     * @return
     */
    @Select("select measure_point_code from t_station_info where drainage_unit_id =#{drainageUnitId} and measure_point_value_type='LJ' and piping=1 GROUP BY measure_point_code")
    public String getCodeByDrainageUnitId(Integer drainageUnitId);


    /**
     * 获取所有管路上的累计测点的测点编码
     * @return
     */
    @Select("select measure_point_code from t_station_info where piping=1 and measure_point_value_type = 'LJ' GROUP BY measure_point_code")
    public List<String> getMeasurePointCode();

    /**
     * 1、查询所有的测点安装位置
     * @return
     */
    @Select("select measure_point_local from t_station_info group by measure_point_local")
    public List<String> getMeasurePointLocal();
    @Select("select * from t_station_info group by measure_point_local")
    public List<TStationInfo> getGroupByMeasurePointLocal();


    /**
     * 2、将测点设置成总管路
     * @param measurePointLocal 测点安装位置
     * @return
     */
    @Update("update t_station_info set piping =1 where measure_point_local = #{measurePointLocal}")
    public int updatePipingByLocal(@Param("measurePointLocal") String measurePointLocal);

    /**
     * 3、将指定的测点安装位置设置到指定巷道中
     * @param measurePointLocals
     * @return
     */

    public int updateRoadwayIdByLocal(@Param("roadwayId")Integer roadwayId,@Param("measurePointLocals") List<String> measurePointLocals);


    /**
     * 4、设置某一个巷道内的测点安装位置所属的抽采单元和在该巷道的排列顺序 （排序的序号越小，数值包含的测点越多，如：序号1，则为该巷道第一个测点）
     * 设置测点信息的抽采单元和序号
     * @param measurePointLocal 测点安装位置
     * @param drainageUnitId 抽采单元ID
     * @param serial 序号
     */
    @Update("update t_station_info set serial=#{serial}, drainage_unit_id =#{drainageUnitId} where measure_point_local=#{measurePointLocal}")
    public int setDrainageUnitAndSerial(@Param("measurePointLocal") String measurePointLocal,
                                         @Param("drainageUnitId")Integer drainageUnitId,
                                         @Param("serial")Integer serial);

    /**
     * 根据抽采单元ID查询总控抽采测点安装位置
     * @param drainageUnitId
     * @return
     */
    @Select("select measure_point_local from t_station_info where drainage_unit_id=#{drainageUnitId}  and piping =1 GROUP BY measure_point_local")
    public String getMeasurePointLocalByDUid(Integer drainageUnitId);

    /**
     * 根据抽采单元ID获取测点列表
     * @param drainageUnitId
     * @return
     */
    @Select("select * from t_station_info where drainage_unit_id = #{drainageUnitId}")
    public List<TStationInfo> getByDrainageUnitId(Integer drainageUnitId);


    /**
     * 通过抽采单元ID获取总测点的测点基础信息
     * @param drainageUnitId
     * @return
     */
    @Select("select * from t_station_info where drainage_unit_id =#{drainageUnitId} and measure_point_value_type='LJ' and piping=1 GROUP BY drainage_unit_id")
//    @Select("select * from t_station_info where drainage_unit_id =#{drainageUnitId} and  piping=1 GROUP BY drainage_unit_id")
    public TStationInfo getMainByDrainageUnitId(Integer drainageUnitId);


    /**
     * 设置测点位置逻辑
     * @param roadwayId 所属巷道
     * @param drainageUnitId 对应的抽采单元ID
     * @param pipeTypeId 抽采管道类型
     * @param serial 同一巷道，同一管道类型中的顺序
     * @param piping 抽采单元总管道
     * @param farthestEnd 是否为最远端的测点位置
     * @param measurePointLocal 测点位置
     * @return
     */
    public int setStationInfo(@Param("roadwayId")Integer roadwayId,
                              @Param("drainageUnitId")Integer drainageUnitId,
                              @Param("pipeTypeId")Integer pipeTypeId,
                              @Param("serial")Integer serial,
                              @Param("piping")Boolean piping,
                              @Param("farthestEnd")Boolean farthestEnd,
                              @Param("measurePointLocal")String measurePointLocal
                              );

    /**
     * 获取所有测点类型
     * @return
     */
    @Select("select sensor_type_name from t_station_info group by sensor_type_name")
    public List<String> getSensorTypes();



}
