package com.ruoyi.hh.demo.domain;/*
package com.ruoyi.demo.domain;



import com.ruoyi.hh.physical.domain.TCoalPoint;
import com.ruoyi.hh.physical.domain.WarningAlerts;

import java.util.List;

public class SensorWarningAlerts extends WarningAlerts {

    */
/**
     * 异常类型
     *//*

    private static final String TYPE_MESSAGE = "传感器异常";
    */
/**
     * 异常数据
     *//*

    private TCoalPoint exceptionData;
    */
/**
     * 补充信息
     *//*

    private List<TCoalPoint> extraData;

    public SensorWarningAlerts(Double x, Double y, Double z, Integer type, String typeMessage, String alertMessage, TCoalPoint exceptionData, List<TCoalPoint> extraData) {
        super(x, y, z, type, typeMessage, alertMessage);
        this.exceptionData = exceptionData;
        this.extraData = extraData;
    }
}
*/
