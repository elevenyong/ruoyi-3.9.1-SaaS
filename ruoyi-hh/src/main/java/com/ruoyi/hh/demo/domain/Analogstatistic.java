package com.ruoyi.hh.demo.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName(value = "kj370_analogstatistic")//指定表名
public class Analogstatistic implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("id")
    private int ID;
    @TableField("PointID")
    private String PointID;
    @TableField("PointName")
    private String PointName;
    @TableField("SubStationID")
    private int SubStationID;
    @TableField("PortNO")
    private int PortNO;
    @TableField("Location")
    private String Location;
    @TableField("UnitName")
    private String UnitName;
    @TableField("MonitoringValue")
    private String MonitoringValue;
    @TableField("State")
    private int State;
    @TableField("`MaxValue`")
    private double MaxValue;
    @TableField("MaxValueTime")
    private Timestamp MaxValueTime;
    @TableField("`MinValue`")
    private double MinValue;
    @TableField("MinValueTime")
    private Timestamp MinValueTime;
    @TableField("SumValue")
    private float SumValue;
    @TableField("SumCount")
    private int SumCount;
    @TableField("AvgValue")
    private String AvgValue;
    @TableField("StartTime")
    private Timestamp StartTime;
    @TableField("EndTime")
    private Timestamp EndTime;


}