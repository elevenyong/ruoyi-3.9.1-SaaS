package com.ruoyi.hh.business.service;

import java.util.List;

import com.ruoyi.hh.business.domain.TSensorInfo;

/**
 * 传感器信息Service接口
 *
 * @author eleven
 * @date 2022-07-26
 */
public interface ITSensorInfoService {
    /**
     * 查询传感器信息
     *
     * @param id 传感器信息主键
     * @return 传感器信息
     */
    public TSensorInfo selectTSensorInfoById(Integer id);

    /**
     * 查询传感器信息列表
     *
     * @param tSensorInfo 传感器信息
     * @return 传感器信息集合
     */
    public List<TSensorInfo> selectTSensorInfoList(TSensorInfo tSensorInfo);

    /**
     * 新增传感器信息
     *
     * @param tSensorInfo 传感器信息
     * @return 结果
     */
    public Integer insertTSensorInfo(TSensorInfo tSensorInfo);

    /**
     * 修改传感器信息
     *
     * @param tSensorInfo 传感器信息
     * @return 结果
     */
    public Integer updateTSensorInfo(TSensorInfo tSensorInfo);

    /**
     * 批量删除传感器信息
     *
     * @param ids 需要删除的传感器信息主键集合
     * @return 结果
     */
    public Integer deleteTSensorInfoByIds(List<Integer> ids);

    /**
     * 删除传感器信息信息
     *
     * @param id 传感器信息主键
     * @return 结果
     */
    public Integer deleteTSensorInfoById(Integer id);

    /**
     * 获取所有信息
     *
     * @return 列表
     */
    public List<TSensorInfo> getPageList();

    /**
     * 根据测点ID删除传感器信息id
     * @param measurePointIds 传感器信息ID
     * @return 结果
     */
    public Integer delByMeasurePointIds(List<Integer> measurePointIds);
    /**
     * 根据测点ID删除传感器信息id
     * @param measurePointId 传感器信息ID
     * @return 结果
     */
    public Integer delByMeasurePointId(Integer measurePointId);

    /**
     * 根据测点ID查询传感器信息ID
     * @param measurePointId 测点ID
     * @return 列表
     */
    public List<Integer> getIdsByMeasurePointId(Integer measurePointId);
}
