package com.ruoyi.hh.business.mapper;

import java.util.List;

import com.ruoyi.hh.business.domain.TSensorInfo;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 传感器信息Mapper接口
 *
 * @author eleven
 * @date 2022-07-26
 */
public interface TSensorInfoMapper {
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
     * 删除传感器信息
     *
     * @param id 传感器信息主键
     * @return 结果
     */
    public Integer deleteTSensorInfoById(Integer id);

    /**
     * 批量删除传感器信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTSensorInfoByIds(@RequestParam List<Integer> ids);

    /**
     * 获取所有信息
     *
     * @return 列表
     */
    public List<TSensorInfo> getPageList();

    /**
     * 根据测点ID删除传感器信息id
     * @param measurePoIntegerIds 传感器信息ID
     * @return 结果
     */
    public Integer delByMeasurePointIds(@RequestParam List<Integer> measurePoIntegerIds);

    /**
     * 根据测点ID删除传感器信息id
     * @param measurePointId 传感器信息ID
     * @return 结果
     */
    public Integer delByMeasurePointId(@RequestParam Integer measurePointId);

    /**
     * 根据测点ID查询传感器信息ID
     * @param measurePointId 测点ID
     * @return 列表
     */
    public List<Integer> getIdsByMeasurePointId(@RequestParam Integer measurePointId);
}
