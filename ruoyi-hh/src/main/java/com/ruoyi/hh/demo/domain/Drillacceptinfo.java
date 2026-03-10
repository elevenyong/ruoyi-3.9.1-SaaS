package com.ruoyi.hh.demo.domain;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.*;


/**
 * (Drillacceptinfo)表实体类
 *
 * @author Eleven
 * @since 2022-04-21 10:07:52
 */
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "drillacceptinfo")
public class Drillacceptinfo extends Model<Drillacceptinfo> {
    @TableField(value = "drillid")
    private Integer drillid;
    @TableField(value = "drillsiteid")
    private Integer drillsiteid;
    @TableField(value = "aperture")
    private Double aperture;
    @TableField(value = "angle")
    private Double angle;
    @TableField(value = "inclination")
    private Double inclination;
    @TableField(value = "azimuth")
    private Double azimuth;
    @TableField(value = "seecoaldepth")
    private Double seecoaldepth;
    @TableField(value = "holedepth")
    private Double holedepth;
    @TableField(value = "centerlinedistance")
    private Double centerlinedistance;
    @TableField(value = "floorheight")
    private Double floorheight;
    @TableField(value = "baselineheight")
    private Double baselineheight;
    @TableField(value = "wirepointid")
    private Integer wirepointid;
    @TableField(value = "wirepointdirection")
    private Integer wirepointdirection;
    @TableField(value = "wirepointdistance")
    private Double wirepointdistance;
    @TableField(value = "neardrillid")
    private Integer neardrillid;
    @TableField(value = "neardrilldistance")
    private Double neardrilldistance;
    @TableField(value = "ch4value")
    private Double ch4value;
    @TableField(value = "covalue")
    private Double covalue;
    @TableField(value = "rockstart1")
    private Double rockstart1;
    @TableField(value = "rockend1")
    private Double rockend1;
    @TableField(value = "coalstart1")
    private Double coalstart1;
    @TableField(value = "coalend1")
    private Double coalend1;
    @TableField(value = "rockstart2")
    private Double rockstart2;
    @TableField(value = "rockend2")
    private Double rockend2;
    @TableField(value = "coalstart2")
    private Double coalstart2;
    @TableField(value = "coalend2")
    private Double coalend2;
    @TableField(value = "rockstart3")
    private Double rockstart3;
    @TableField(value = "rockend3")
    private Double rockend3;
    @TableField(value = "coalstart3")
    private Double coalstart3;
    @TableField(value = "coalend3")
    private Double coalend3;
    @TableField(value = "issprayhole")
    private Integer issprayhole;
    @TableField(value = "istopdrill")
    private Integer istopdrill;
    @TableField(value = "isclampdrill")
    private Integer isclampdrill;
    @TableField(value = "othererror")
    private String othererror;
    @TableField(value = "starttime")
    private Date starttime;
    @TableField(value = "endtime")
    private Date endtime;
    @TableField(value = "sealingmethod")
    private String sealingmethod;
    @TableField(value = "sealstarttime")
    private Date sealstarttime;
    @TableField(value = "sealendtime")
    private Date sealendtime;
    @TableField(value = "seallength")
    private String seallength;
    @TableField(value = "solidtubelength")
    private Double solidtubelength;
    @TableField(value = "floraltubelength")
    private Double floraltubelength;
    @TableField(value = "sealstandard")
    private String sealstandard;
    @TableField(value = "sealotherinfo")
    private String sealotherinfo;
    @TableField(value = "result")
    private Integer result;
    @TableField(value = "footage")
    private Double footage;
    @TableField(value = "worker1")
    private String worker1;
    @TableField(value = "worker2")
    private String worker2;
    @TableField(value = "acceptor")
    private String acceptor;
    @TableField(value = "iscoalseam")
    private Integer iscoalseam;
    @TableField(value = "classid")
    private Integer classid;
    @TableField(value = "config")
    private String config;
    @TableField(value = "status")
    private Integer status;
    @TableField(value = "notes")
    private String notes;
    @TableField(value = "rman")
    private String rman;
    @TableField(value = "rdate")
    private Date rdate;
    @TableField(value = "lman")
    private String lman;
    @TableField(value = "ldate")
    private Date ldate;
    @TableField(value = "extractionradius")
    private Double extractionradius;
    @TableField(value = "flowcompleted")
    private Integer flowcompleted;

}

