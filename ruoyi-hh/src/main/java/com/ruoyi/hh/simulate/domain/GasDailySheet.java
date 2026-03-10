package com.ruoyi.hh.simulate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GasDailySheet {
    private String roadway;
    private String measuringpointname;
    private String kind;
    private String unitname;
    private String sensorcode;
    private String sensorname;
    private double substationid;
    private double monitoringvalue;
    private double maxvalue;
    private Date maxtime;
    private double minvalue;
    private Date mintime;
    private double sumvalue;
    private double avgvalue;
    private Date starttime;
    private Date endtime;
}
