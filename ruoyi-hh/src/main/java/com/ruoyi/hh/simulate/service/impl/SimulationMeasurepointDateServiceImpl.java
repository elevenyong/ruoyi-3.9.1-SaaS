package com.ruoyi.hh.simulate.service.impl;

import java.util.List;

import com.ruoyi.hh.simulate.domain.SimulationMeasurepointData;
import com.ruoyi.hh.simulate.mapper.SimulationMeasurepointDateMapper;
import com.ruoyi.hh.simulate.service.ISimulationMeasurepointDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模拟测点的实时数据Service业务层处理
 *
 * @author eleven
 * @date 2022-11-28
 */
@Service
public class SimulationMeasurepointDateServiceImpl implements ISimulationMeasurepointDateService
{
    @Autowired
    private SimulationMeasurepointDateMapper simulationMeasurepointDateMapper;

    /**
     * 查询模拟测点的实时数据
     *
     * @param id 模拟测点的实时数据主键
     * @return 模拟测点的实时数据
     */
    @Override
    public SimulationMeasurepointData selectSimulationMeasurepointDateById(Integer id)
    {
        return simulationMeasurepointDateMapper.selectSimulationMeasurepointDateById(id);
    }

    /**
     * 查询模拟测点的实时数据列表
     *
     * @param simulationMeasurepointDate 模拟测点的实时数据
     * @return 模拟测点的实时数据
     */
    @Override
    public List<SimulationMeasurepointData> selectSimulationMeasurepointDateList(SimulationMeasurepointData simulationMeasurepointDate)
    {
        return simulationMeasurepointDateMapper.selectSimulationMeasurepointDateList(simulationMeasurepointDate);
    }

    /**
     * 新增模拟测点的实时数据
     *
     * @param simulationMeasurepointDate 模拟测点的实时数据
     * @return 结果
     */
    @Override
    public int insertSimulationMeasurepointDate(SimulationMeasurepointData simulationMeasurepointDate)
    {
        return simulationMeasurepointDateMapper.insertSimulationMeasurepointDate(simulationMeasurepointDate);
    }

    /**
     * 修改模拟测点的实时数据
     *
     * @param simulationMeasurepointDate 模拟测点的实时数据
     * @return 结果
     */
    @Override
    public int updateSimulationMeasurepointDate(SimulationMeasurepointData simulationMeasurepointDate)
    {
        return simulationMeasurepointDateMapper.updateSimulationMeasurepointDate(simulationMeasurepointDate);
    }

    /**
     * 批量删除模拟测点的实时数据
     *
     * @param ids 需要删除的模拟测点的实时数据主键
     * @return 结果
     */
    @Override
    public int deleteSimulationMeasurepointDateByIds(Integer[] ids)
    {
        return simulationMeasurepointDateMapper.deleteSimulationMeasurepointDateByIds(ids);
    }

    /**
     * 删除模拟测点的实时数据信息
     *
     * @param id 模拟测点的实时数据主键
     * @return 结果
     */
    @Override
    public int deleteSimulationMeasurepointDateById(Integer id)
    {
        return simulationMeasurepointDateMapper.deleteSimulationMeasurepointDateById(id);
    }

    @Override
    public List<SimulationMeasurepointData> getList() {
        return this.simulationMeasurepointDateMapper.getList();
    }

    /**
     * 获取测点历史数据
     *
     * @param measurePointId
     * @param limit
     * @return
     */
    public List<SimulationMeasurepointData> selectSimulationHistoryDataById(Integer measurePointId, Integer limit) {
        return simulationMeasurepointDateMapper.selectSimulationHistoryDataById(measurePointId, limit);
    }
}
