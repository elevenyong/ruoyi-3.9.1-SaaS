package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TStationInfo;

import java.util.List;

/**
 * 测点基本信息Service接口
 * 
 * @author eleven
 * @date 2023-05-16
 */
public interface ITStationInfoService 
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
     * 批量删除测点基本信息
     * 
     * @param ids 需要删除的测点基本信息主键集合
     * @return 结果
     */
    public int deleteTStationInfoByIds(Integer[] ids);

    /**
     * 删除测点基本信息信息
     * 
     * @param id 测点基本信息主键
     * @return 结果
     */
    public int deleteTStationInfoById(Integer id);

    /**
     *根据测点编码查询测点信息
     * @param measurePointCode 测点编码
     * @return 测点信息
     */
    public TStationInfo getByMeasurePointCode(String measurePointCode,String sensorTypeName);

    /**
     * 根据测点编码修改测点信息
     * @param tStationInfo 测点信息
     */
    public int updateByMeasurePointCode(TStationInfo tStationInfo);

    /**
     * 查询测点信息中瓦斯累计量的测点的安装位置
     * @return 列表
     */
    public List<TStationInfo> getLJMeasurePointLocal();

    /**
     * 通过测点安装位置修改测点位置顺序
     * @param serial
     * @param measurePointLocal
     */
    public int updateSerialByLocal(Integer serial,String measurePointLocal);
    /**
     * 根据抽采单元ID查询测点安装位置（之后根据测点安装位置得到抽采数据）
     * @param drainageUnitId
     * @CreateTime: 2023/7/12 10:34
     * @return
     */
    public String getCodeByDrainageUnitId(Integer drainageUnitId);

    /**
     * 获取所有管路上的累计测点的测点编码
     * @return
     */
    public List<String> getMeasurePointCode();
    /**
     * 查询所有的测点安装位置
     * @return
     */
    public List<String> getMeasurePointLocal();
    public List<TStationInfo> getGroupByMeasurePointLocal();
    /**
     * 2、将测点设置成总管路
     * @param measurePointLocal 测点安装位置
     * @return
     */
    public int updatePipingByLocal(String measurePointLocal);
    /**
     * 3、将指定的测点安装位置设置到指定巷道中
     * @param measurePointLocals
     * @return
     */
    public int updateRoadwayIdByLocal(Integer roadwayId,List<String> measurePointLocals);
    /**
     *
     * 4、设置测点信息的抽采单元和
     * @param measurePointLocal
     * @param drainageUnitId
     * @param serial
     */
    public int setDrainageUnitAndSerial(String measurePointLocal,
                                         Integer drainageUnitId,
                                         Integer serial);
    /**
     * 根据抽采单元ID查询总控抽采测点安装位置
     * @param drainageUnitId
     * @return
     */
    public String getMeasurePointLocalByDUid(Integer drainageUnitId);

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
    public int setStationInfo(Integer roadwayId,
                              Integer drainageUnitId,
                              Integer pipeTypeId,
                              Integer serial,
                              Boolean piping,
                              Boolean farthestEnd,
                              String measurePointLocal
    );

    /**
     * 获取所有测点类型
     * @return
     */
    public List<String> getSensorTypes();

}
