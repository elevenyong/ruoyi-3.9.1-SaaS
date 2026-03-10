package com.ruoyi.hh.simulate.mapper;

import com.ruoyi.hh.simulate.domain.SimulationMeasurepointData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 模拟测点的实时数据Mapper接口
 *
 * @author eleven
 * @date 2022-11-28
 */
public interface SimulationMeasurepointDateMapper {
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
     * 删除模拟测点的实时数据
     *
     * @param id 模拟测点的实时数据主键
     * @return 结果
     */
    public int deleteSimulationMeasurepointDateById(Integer id);

    /**
     * 批量删除模拟测点的实时数据
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSimulationMeasurepointDateByIds(Integer[] ids);

    public List<SimulationMeasurepointData> getList();


    /**
     * 获取模拟测点历史数据
     *
     * @param measurePointId
     * @param limit
     * @return
     */
    @Select("SELECT * FROM data_history WHERE measure_pointid = #{measurePointId} ORDER BY DATE DESC LIMIT #{limit}")
    public List<SimulationMeasurepointData> selectSimulationHistoryDataById(@Param("measurePointId") Integer measurePointId, @Param("limit") Integer limit);
}
