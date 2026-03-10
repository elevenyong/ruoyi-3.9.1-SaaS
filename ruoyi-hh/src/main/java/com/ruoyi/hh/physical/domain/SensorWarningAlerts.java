package com.ruoyi.hh.physical.domain;


import com.ruoyi.hh.simulate.domain.SimulationMeasurepointData;

import java.util.List;

public class SensorWarningAlerts extends WarningAlerts {

    /**
     * 异常类型
     */
    private static final String TYPE_MESSAGE = "传感器异常";
    /**
     * 异常数据
     */
    private SimulationMeasurepointData exceptionData;
    /**
     * 补充信息
     */
    private List<SimulationMeasurepointData> extraData;

    public SimulationMeasurepointData getExceptionData() {
        return exceptionData;
    }

    public void setExceptionData(SimulationMeasurepointData exceptionData) {
        this.exceptionData = exceptionData;
    }

    public List<SimulationMeasurepointData> getExtraData() {
        return extraData;
    }

    public void setExtraData(List<SimulationMeasurepointData> extraData) {
        this.extraData = extraData;
    }

    public SensorWarningAlerts(Double x, Double y, Double z, Integer type, String typeMessage, String alertMessage, SimulationMeasurepointData exceptionData, List<SimulationMeasurepointData> extraData) {
        super(x, y, z, type, typeMessage, alertMessage);
        this.exceptionData = exceptionData;
        this.extraData = extraData;
    }
}
