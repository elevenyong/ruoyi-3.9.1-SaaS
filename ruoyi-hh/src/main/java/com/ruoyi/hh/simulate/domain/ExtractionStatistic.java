package com.ruoyi.hh.simulate.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtractionStatistic extends BaseEntity {
    /**工作面名称*/
    private String workingfacename;
    /**巷道名称*/
    private String roadway;
    /**抽采单元名称*/
    private String drainageunitname;
    /**测点名称*/
    private String measuringpointname;
    /**传感器类型ID*/
    private Integer sensortypeid;
    /**负压*/
    private double kpa;
    /**CH4浓度*/
    private double concentration;
    /**温度*/
    private double temperature;
    /**累计混合流量*/
    private double mixtureflow;
    /**累计纯流量*/
    private double pureflow;
}
