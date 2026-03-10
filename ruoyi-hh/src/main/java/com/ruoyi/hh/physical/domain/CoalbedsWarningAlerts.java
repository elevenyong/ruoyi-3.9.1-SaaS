package com.ruoyi.hh.physical.domain;

import java.util.List;

/**
 * 煤层异常信息
 */
public class CoalbedsWarningAlerts extends WarningAlerts {
    /**
     * 异常类型
     */
    private static final String TYPE_MESSAGE = "煤层异常";
    /**
     * 异常数据
     */
    private TCoalPoint exceptionData;
    /**
     * 补充信息
     */
    private List<TCoalPoint> extraData;

    public TCoalPoint getExceptionData() {
        return exceptionData;
    }

    public void setExceptionData(TCoalPoint exceptionData) {
        this.exceptionData = exceptionData;
    }

    public List<TCoalPoint> getExtraData() {
        return extraData;
    }

    public void setExtraData(List<TCoalPoint> extraData) {
        this.extraData = extraData;
    }

    public CoalbedsWarningAlerts(TCoalPoint exceptionData, String alertMessage, List extraData) {
        super(exceptionData.getFloorCoordinatex(), exceptionData.getFloorCoordinatey(), exceptionData.getFloorCoordinatez(), 2, TYPE_MESSAGE, alertMessage);
        this.exceptionData = exceptionData;
        this.extraData = extraData;
    }
}
