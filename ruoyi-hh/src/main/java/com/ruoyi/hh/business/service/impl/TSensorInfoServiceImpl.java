package com.ruoyi.hh.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.hh.business.domain.TSensorInfo;
import com.ruoyi.hh.business.mapper.TSensorDataMapper;
import com.ruoyi.hh.business.mapper.TSensorInfoMapper;
import com.ruoyi.hh.business.service.ITSensorInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 传感器信息Service业务层处理
 *
 * @author eleven
 * @date 2022-07-26
 */
@Service
public class TSensorInfoServiceImpl implements ITSensorInfoService {
    @Resource
    private TSensorInfoMapper tSensorInfoMapper;
    @Resource
    private TSensorDataMapper tSensorDataMapper;

    /**
     * 查询传感器信息
     *
     * @param id 传感器信息主键
     * @return 传感器信息
     */
    @Override
    public TSensorInfo selectTSensorInfoById(Integer id) {
        return tSensorInfoMapper.selectTSensorInfoById(id);
    }

    /**
     * 查询传感器信息列表
     *
     * @param tSensorInfo 传感器信息
     * @return 传感器信息
     */
    @Override
    public List<TSensorInfo> selectTSensorInfoList(TSensorInfo tSensorInfo) {
        return tSensorInfoMapper.selectTSensorInfoList(tSensorInfo);
    }

    /**
     * 新增传感器信息
     *
     * @param tSensorInfo 传感器信息
     * @return 结果
     */
    @Override
    public Integer insertTSensorInfo(TSensorInfo tSensorInfo) {
        return tSensorInfoMapper.insertTSensorInfo(tSensorInfo);
    }

    /**
     * 修改传感器信息
     *
     * @param tSensorInfo 传感器信息
     * @return 结果
     */
    @Override
    public Integer updateTSensorInfo(TSensorInfo tSensorInfo) {
        return tSensorInfoMapper.updateTSensorInfo(tSensorInfo);
    }

    /**
     * 批量删除传感器信息
     *
     * @param ids 需要删除的传感器信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTSensorInfoByIds(List<Integer> ids)
    {
//        //首先根据传感器信息ID删除传感器数据
//        this.tSensorDataMapper.deleteTSensorDataByIds(ids);
//        //然后再删除传感器信息
//        return tSensorInfoMapper.deleteTSensorInfoByIds(ids);
        return null;
    }

    /**
     * 删除传感器信息信息
     *
     * @param id 传感器信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTSensorInfoById(Integer id) {
        //首先根据传感器信息ID删除传感器数据
        this.tSensorDataMapper.deleteTSensorDataById(id);
        //然后再删除传感器信息
        return tSensorInfoMapper.deleteTSensorInfoById(id);
    }

    /**
     * 获取所有信息
     *
     * @return 列表
     */
    @Override
    public List<TSensorInfo> getPageList() {
        return this.tSensorInfoMapper.getPageList();
    }

    /**
     * 根据测点ID删除传感器信息id
     * @param measurePointIds 传感器信息ID
     * @return 结果
     */
    @Override
    public Integer delByMeasurePointIds(List<Integer> measurePointIds) {
        List<Integer> sensordataids=new ArrayList<>();
        for(Integer i:measurePointIds){
            sensordataids.addAll(this.tSensorInfoMapper.getIdsByMeasurePointId(i));
        }
        if(sensordataids.size()!=0){
//            this.tSensorDataMapper.delBySensorInfoIds(sensordataids);
        }
        return this.tSensorInfoMapper.delByMeasurePointIds(measurePointIds);
    }
    /**
     * 根据测点ID删除传感器信息id
     * @param measurePointId 传感器信息ID
     * @return 结果
     */
    @Override
    public Integer delByMeasurePointId(Integer measurePointId) {
        return this.tSensorInfoMapper.delByMeasurePointId(measurePointId);
    }

    /**
     * 根据测点ID查询传感器信息ID
     * @param measurePointId 测点ID
     * @return 列表
     */
    @Override
    public List<Integer> getIdsByMeasurePointId(Integer measurePointId) {
        return this.tSensorInfoMapper.getIdsByMeasurePointId(measurePointId);
    }
}
