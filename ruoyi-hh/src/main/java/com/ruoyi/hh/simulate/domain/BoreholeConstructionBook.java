package com.ruoyi.hh.simulate.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
/*
 * 钻孔施工台账
 */
public class BoreholeConstructionBook {
    /**巷道名称*/
    @Excel(name="巷道名称")
    private String roadway;
    /**组号*/
    @Excel(name="组号")
    private String groupNo;
    /**孔号*/
    @Excel(name="孔号")
    private Integer boreholeNo;
    /**拔钻日期*/
    @Excel(name="拔钻日期", width = 15, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date pulldrilldate;
    /**班次*/
    @Excel(name="班次")
    private String shiftinfo;
    /**设计夹角*/
    @Excel(name="设计夹角")
    private double desangle;
    /**实际夹角*/
    @Excel(name="实际夹角")
    private double actangle;
    /***/
//    @Excel(name="设计方位角")
    private double desazimuthangle;
//    @Excel(name="实际方位角")
    private double actAzimuthangle;
    /**设计倾角*/
    @Excel(name="设计倾角")
    private double desInclinationangle;
    /**实际倾角*/
    @Excel(name="实际倾角")
    private double actInclinationangle;
    /**倾角误差*/
    @Excel(name="倾角误差")
    private double inclinationangleErr;
    /**孔径*/
    @Excel(name="孔径")
    private double borediameter;
    /**设计孔深*/
    @Excel(name="设计孔深")
    private double desboreholelength;
    /**实际孔深*/
    @Excel(name="实际孔深")
    private double actboreholelength;
    /**设计见煤深度*/
    @Excel(name="设计见煤深度")
    private double desdepth1stcoalseamstart;
    /**实际见煤深度*/
    @Excel(name="实际见煤深度")
    private double actdepth1stcoalseamstart;
    /**实际见煤误差*/
    @Excel(name="实际见煤误差")
    private double seecoalerr;
    /**实际见煤误差比*/
    @Excel(name="实际见煤误差比")
    private double seecoalerrratio;
    /**设计止煤深度*/
    @Excel(name="设计止煤深度")
    private double desdepthstopcoalseamstart;
    /**实际止煤深度*/
    @Excel(name="实际止煤深度")
    private double actdepthstopcoalseamstart;
    /**实际止煤误差*/
    @Excel(name="实际止煤误差")
    private double stopcoalerr;
    /**实际止煤误差比*/
    @Excel(name="实际止煤误差比")
    private double stopcoalerrratio;
    /**导线点名称*/
    @Excel(name="导线点名称")
    private String navigationpointname;
    /**距导线点距离*/
    @Excel(name="距导线点距离")
    private double distancetonavigationpoint;
    /**钻孔用途*/
    @Excel(name="钻孔用途")
    private String purposeborehole;
    /**封孔方式*/
    @Excel(name="封孔方式")
    private String holesealtype;
    /**拔钻浓度*/
    @Excel(name="拔钻浓度")
    private double pulledgasconcentration;
    /**施工负责人*/
    @Excel(name="施工负责人")
    private String drillingchargehand;
    /**瓦斯检查员*/
    @Excel(name="瓦斯检查员")
    private String gaschecker;
    /**下管责任人*/
    @Excel(name="下管责任人")
    private String downtubechargehand;
    /**下管长度*/
    @Excel(name="下管长度")
    private double tubelength;
    /**下管日期*/
    @Excel(name="下管日期", width = 15, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date downtubedate;
    /**注浆责任人*/
    @Excel(name="注浆责任人")
    private String sgchargehand;
    /**注浆日期*/
    @Excel(name="注浆日期", width = 15, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date groutingdate;
    /**注浆量*/
    @Excel(name="注浆量")
    private double groutamount;
    /**注浆异常情况*/
    @Excel(name="注浆异常情况")
    private String exceptionlabel;
    /**冲孔日期*/
    @Excel(name="冲孔日期", width = 15, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateflushing;
    /**冲孔段*/
    @Excel(name="冲孔段")
    private String punchingparagraph;
    /**冲孔长度*/
    @Excel(name="冲孔长度")
    private double punchinglength;
    /**冲孔时间*/
    @Excel(name="冲孔时间")
    private double totaltimeflushing;
    /**冲孔压力*/
    @Excel(name="冲孔压力")
    private double waterpressureduringflushing;
    /**冲孔责任人*/
    @Excel(name="冲孔责任人")
    private String afchargehand;
    /**冲孔煤量*/
    @Excel(name="冲孔煤量")
    private double actualcoalamountflushed;
    /**验煤时间*/
    @Excel(name="验煤时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receptiontime;

}
