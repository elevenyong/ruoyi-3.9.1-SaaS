package com.ruoyi.hh.simulate.service;

import java.util.List;

import com.ruoyi.hh.simulate.domain.SimulationMeasurepointData;

/**
 * 模拟测点的实时数据Service接口
 *
 * @author eleven
 * @date 2022-11-28
 */
public interface ISimulationMeasurepointDateService
{
    /**
     * 查询模拟测点的实时数据
     *
     * @param id 模拟测点的实时数据主键
     * @return 模拟测点的实时数据
     */
    public SimulationMeasurepointData selectSimulationMeasurepointDateById(Integer id);

    /**
     * 查询模拟测点的实时数据列表
     *
     * @param simulationMeasurepointDate 模拟测点的实时数据
     * @return 模拟测点的实时数据集合
     */
    public List<SimulationMeasurepointData> selectSimulationMeasurepointDateList(SimulationMeasurepointData simulationMeasurepointDate);

    /**
     * 新增模拟测点的实时数据
     *
     * @param simulationMeasurepointDate 模拟测点的实时数据
     * @return 结果
     */
    public int insertSimulationMeasurepointDate(SimulationMeasurepointData simulationMeasurepointDate);

    /**
     * 修改模拟测点的实时数据
     *
     * @param simulationMeasurepointDate 模拟测点的实时数据
     * @return 结果
     */
    public int updateSimulationMeasurepointDate(SimulationMeasurepointData simulationMeasurepointDate);

    /**
     * 批量删除模拟测点的实时数据
     *
     * @param ids 需要删除的模拟测点的实时数据主键集合
     * @return 结果
     */
    public int deleteSimulationMeasurepointDateByIds(Integer[] ids);

    /**
     * 删除模拟测点的实时数据信息
     *
     * @param id 模拟测点的实时数据主键
     * @return 结果
     */
    public int deleteSimulationMeasurepointDateById(Integer id);

    public List<SimulationMeasurepointData> getList();


    /**
     * 获取测点历史数据
     *
     * @param measurePointId
     * @param limit
     * @return
     */
    public List<SimulationMeasurepointData> selectSimulationHistoryDataById(Integer measurePointId, Integer limit);
}
