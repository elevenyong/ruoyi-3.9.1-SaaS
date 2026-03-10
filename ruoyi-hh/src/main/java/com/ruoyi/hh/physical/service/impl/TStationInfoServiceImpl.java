package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TStationInfo;
import com.ruoyi.hh.physical.mapper.TStationInfoMapper;
import com.ruoyi.hh.physical.service.ITStationInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测点基本信息Service业务层处理
 * 
 * @author eleven
 * @date 2023-05-16
 */
@Service
public class TStationInfoServiceImpl implements ITStationInfoService
{
    @Resource
    private TStationInfoMapper tStationInfoMapper;

    /**
     * 查询测点基本信息
     * 
     * @param id 测点基本信息主键
     * @return 测点基本信息
     */
    @Override
    public TStationInfo selectTStationInfoById(Integer id)
    {
        return tStationInfoMapper.selectTStationInfoById(id);
    }

    /**
     * 查询测点基本信息列表
     * 
     * @param tStationInfo 测点基本信息
     * @return 测点基本信息
     */
    @Override
    public List<TStationInfo> selectTStationInfoList(TStationInfo tStationInfo)
    {
        return tStationInfoMapper.selectTStationInfoList(tStationInfo);
    }

    /**
     * 新增测点基本信息
     * 
     * @param tStationInfo 测点基本信息
     * @return 结果
     */
    @Override
    public int insertTStationInfo(TStationInfo tStationInfo)
    {
        return tStationInfoMapper.insertTStationInfo(tStationInfo);
    }

    /**
     * 修改测点基本信息
     * 
     * @param tStationInfo 测点基本信息
     * @return 结果
     */
    @Override
    public int updateTStationInfo(TStationInfo tStationInfo)
    {
        return tStationInfoMapper.updateTStationInfo(tStationInfo);
    }

    /**
     * 批量删除测点基本信息
     * 
     * @param ids 需要删除的测点基本信息主键
     * @return 结果
     */
    @Override
    public int deleteTStationInfoByIds(Integer[] ids)
    {
        return tStationInfoMapper.deleteTStationInfoByIds(ids);
    }

    /**
     * 删除测点基本信息信息
     * 
     * @param id 测点基本信息主键
     * @return 结果
     */
    @Override
    public int deleteTStationInfoById(Integer id)
    {
        return tStationInfoMapper.deleteTStationInfoById(id);
    }

    @Override
    public TStationInfo getByMeasurePointCode(String measurePointCode,String sensorTypeName) {
        return this.tStationInfoMapper.getByMeasurePointCode(measurePointCode,sensorTypeName);
    }
    /**
     * 根据测点编码修改测点信息
     * @param tStationInfo 测点信息
     */
    @Override
    public int updateByMeasurePointCode(TStationInfo tStationInfo) {
        return this.tStationInfoMapper.updateByMeasurePointCode(tStationInfo);
    }
    /**
     * 查询测点信息中瓦斯累计量的测点的安装位置
     * @return 列表
     */
    @Override
    public List<TStationInfo> getLJMeasurePointLocal() {
        return this.tStationInfoMapper.getLJMeasurePointLocal();
    }

    /**
     * 通过测点安装位置修改测点位置顺序
     * @param serial
     * @param measurePointLocal
     */
    @Override
    public int updateSerialByLocal(Integer serial,String measurePointLocal){
          return this.tStationInfoMapper.updateSerialByLocal(serial,measurePointLocal);
    }
    /**
     * 根据抽采单元ID查询测点安装位置（之后根据测点安装位置得到抽采数据）
     * @param drainageUnitId
     * @CreateTime: 2023/7/12 10:34
     * @return
     */
    @Override
    public String getCodeByDrainageUnitId(Integer drainageUnitId) {
        return this.tStationInfoMapper.getCodeByDrainageUnitId(drainageUnitId);
    }
    /**
     * 获取所有管路上的累计测点的测点编码
     * @return
     */
    @Override
    public List<String> getMeasurePointCode() {
        return this.tStationInfoMapper.getMeasurePointCode();
    }
    /**
     * 1、查询所有的测点安装位置
     * @return
     */
    @Override
    public List<String> getMeasurePointLocal() {
        return this.tStationInfoMapper.getMeasurePointLocal();
    }
    public List<TStationInfo> getGroupByMeasurePointLocal(){
        return this.tStationInfoMapper.getGroupByMeasurePointLocal();
    }
    /**
     * 2、将测点设置成总管路
     * @param measurePointLocal 测点安装位置
     * @return
     */
    public int updatePipingByLocal(String measurePointLocal){
        return this.tStationInfoMapper.updatePipingByLocal(measurePointLocal);
    }
    /**
     * 3、将指定的测点安装位置设置到指定巷道中
     * @param measurePointLocals
     * @return
     */
    public int updateRoadwayIdByLocal(Integer roadwayId,List<String> measurePointLocals){
        return this.tStationInfoMapper.updateRoadwayIdByLocal(roadwayId,measurePointLocals );
    }

    /**
     * 4、设置测点信息的抽采单元和
     * @param measurePointLocal
     * @param drainageUnitId
     * @param serial
     */
    @Override
    public int setDrainageUnitAndSerial(String measurePointLocal,
                                         Integer drainageUnitId,
                                         Integer serial){
        return this.tStationInfoMapper.setDrainageUnitAndSerial(measurePointLocal,drainageUnitId,serial);
    }
    /**
     * 根据抽采单元ID查询总控抽采测点安装位置
     * @param drainageUnitId
     * @return
     */
    @Override
    public String getMeasurePointLocalByDUid(Integer drainageUnitId) {
        return this.tStationInfoMapper.getMeasurePointLocalByDUid(drainageUnitId);
    }

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
    ){
        return this.tStationInfoMapper.setStationInfo(roadwayId, drainageUnitId, pipeTypeId, serial, piping, farthestEnd, measurePointLocal);
    }

    /**
     * 获取所有测点类型
     * @return
     */
    public List<String> getSensorTypes(){
        return this.tStationInfoMapper.getSensorTypes();
    }
}
