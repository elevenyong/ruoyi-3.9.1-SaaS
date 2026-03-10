package com.ruoyi.hh.physical.domain;

import com.ruoyi.hh.demo.domain.TActualBorholeException;

/**
 * 钻孔异常信息,type=1
 */
public class ActualBorholeWarningAlerts extends WarningAlerts {
    private static final String TYPE_MESSAGE = "钻孔异常";
    private TActualBorholeException exceptionData;

    private TActualBorehole extraData;

    public TActualBorehole getExtraData() {
        return extraData;
    }

    public void setExtraData(TActualBorehole extraData) {
        this.extraData = extraData;
    }

    public TActualBorholeException getExceptionData() {
        return exceptionData;
    }

    public void setExceptionData(TActualBorholeException exceptionData) {
        this.exceptionData = exceptionData;
    }

    public ActualBorholeWarningAlerts(TActualBorholeException exceptionData, TActualBorehole holeData) {
        super(exceptionData.getX(), exceptionData.getY(), exceptionData.getZ(), 1, TYPE_MESSAGE, "钻孔：" + exceptionData.getRoadWayName() + "-" + exceptionData.getGroupNo() + "-" + exceptionData.getBoreholeNo() + " 曾出现" + exceptionData.getExceptions() + "异常");
        this.exceptionData = exceptionData;
        this.extraData = holeData;
    }
}
