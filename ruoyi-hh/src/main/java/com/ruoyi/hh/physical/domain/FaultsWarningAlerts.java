package com.ruoyi.hh.physical.domain;


import com.ruoyi.hh.demo.domain.TGeoFault;

import java.util.List;

/**
 * 断层异常类型,type=0
 */
public class FaultsWarningAlerts extends WarningAlerts {
    private static final String TYPE_MESSAGE = "断层异常";
    private TGeoFault exceptionData;
    private List<TCoalPoint> extraData;

    public TGeoFault getExceptionData() {
        return exceptionData;
    }

    public void setExceptionData(TGeoFault exceptionData) {
        this.exceptionData = exceptionData;
    }

    public List<TCoalPoint> getExtraData() {
        return extraData;
    }

    public void setExtraData(List<TCoalPoint> extraData) {
        this.extraData = extraData;
    }

    public FaultsWarningAlerts(TGeoFault exceptionData, List<TCoalPoint> extraData) {
        super(exceptionData.getCenterX(), exceptionData.getCenterY(), exceptionData.getCenterZ(), 0, TYPE_MESSAGE, "接近 " + exceptionData.getName() + " 断层");
        this.exceptionData = exceptionData;
        this.extraData = extraData;
    }
}
