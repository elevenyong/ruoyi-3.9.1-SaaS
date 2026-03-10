package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 实钻信息相关接口之对象 t_actual_borehole
 * 废弃，实钻使用TBkActBoreholeInfo
 * @author eleven
 * @date 2022-07-13
 */
@Deprecated
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TActualBorehole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Integer id;

    /**
     * 组号
     */
    @Excel(name = "组号")
    private String groupNo;

    /**
     * 孔号
     */
    @Excel(name = "孔号")
    private String boreholeNo;

    private String holeNo;

    /**
     * 所属巷道ID
     */
//    @Excel(name = "所属巷道ID")
    private Integer roadwayId;

    private String workfaceName;

    /**
     * 所属巷道名称
     */

    @Excel(name = "所属巷道名称")
    private String roadway;

    /**
     * 区段ID
     */
//    @Excel(name = "区段ID")
    private Integer roadwayZoneId;
    /**
     * 区段名称
     */
    @Excel(name="区段名称")
    private String zonename;

    /** 关联测点ID */
//    @Excel(name = "关联测点ID")
    private Integer measurePointId;

    /**关联测点名称*/
    @Excel(name="关联测点名称")
    private String measurePointName;

    /**
     * 钻场ID
     */
//    @Excel(name = "钻场ID")
    private Integer boreholeSiteId;

    /** 班次ID */
//    @Excel(name = "班次ID")
    private Integer shiftId;

    @Excel(name="班次")
    private String shiftinfo;

    /**
     * 钻孔时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "钻孔时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date drillingDate;

    /**
     * 钻孔类型ID
     */
//    @Excel(name = "钻孔类型ID")
    private Integer boreholeCategoryId;

    /**
     * 钻孔类型名称
     */
    @Excel(name = "钻孔类型名称")
    private String boreholeCategor;


    /**
     * 钻孔用途ID
     */
//    @Excel(name = "钻孔用途ID")
    private Integer purposeBoreholeId;

    /**
     * 钻机ID
     */
//    @Excel(name = "钻机ID")
    private Integer drillingId;
    /**
     * 钻机名称
     */
//    @Excel(name = "钻机名称")
    private String drillingName;

    /**
     * 实际孔径
     */
    @Excel(name = "实际孔径")
    private double aperture;

    /**设计孔径*/
    private double desBoreDiameter;
    /***/
    private double desCoalSeamstart;
    private double desAzimuthAngle;
    private double desInclinationAngle;


    /***/
    private String samplingProt; //施工地点
    private double gasContent;//实测瓦斯含量
    private double gasPressure; //实测瓦斯压力
    /***/

    /**
     * 钻孔用途
     */
    @Excel(name = "钻孔用途")
    private String purposeBorehole;

    /**
     * 实际方位角
     */
    @Excel(name = "实际方位角")
    private Double azimuthAngle;

    /**
     * 实际倾角
     */
    @Excel(name = "实际倾角")
    private Double inclinationAngle;

    /** 夹角 */
    @Excel(name = "夹角")
    private Double angle;

    /** 第一岩起 */
    @Excel(name = "第一岩起")
    private Double depth1stRockSeamStart;

    /** 第一岩止 */
    @Excel(name = "第一岩止")
    private Double depth1stRockSeamEnd;

    /** 第一煤起 */
    @Excel(name = "第一煤起")
    private Double depth1stCoalSeamStart;

    /** 第一煤止 */
    @Excel(name = "第一煤止")
    private Double depth1stCoalSeamend;

    /** 第二煤起 */
    @Excel(name = "第二煤起")
    private Double depth2stCoalSeamStart;

    /** 第二煤止 */
    @Excel(name = "第二煤止")
    private Double depth2stCoalSeamend;

    /** 第二岩起 */
    @Excel(name = "第二岩起")
    private Double depth2stRockSeamStart;

    /** 第二岩止 */
    @Excel(name = "第二岩止")
    private String depth2stRockSeamEnd;

    /** 第三岩起 */
    @Excel(name = "第三岩起")
    private Double depth3stRockSeamStart;

    /** 第三岩止 */
    @Excel(name = "第三岩止")
    private Double depth3stRockSeamEnd;

    /** 抽采半径 */
    @Excel(name = "抽采半径")
    private Double extractionRadius;
    /**煤孔段长度*/
    private Double coalHoleLength;


    /**
     * 实际孔深
     */
    @Excel(name = "实际孔深")
    private Double boreholeLength;

    /**
     * 导线点ID
     */
//    @Excel(name = "导线点ID")
    private Integer navigationPointId;

    /**
     * 导线点名称
     */
    @Excel(name = "导线点名称")
    private String navigationPointName;

    /**导线点方向*/
    private String directionToNavigation;

    /**有效进尺*/
    @Excel(name="有效进尺")
    private double validFootage;

    /**
     * 距导线点距离
     */
    @Excel(name = "距导线点距离")
    private Double distanceToNavigationPoint;


    /**
     * 距巷道中线距离
     */
    @Excel(name = "距巷道中线距离")
    private Double distanceToCentrallineTunnel;

    /**
     * 距拱基线高度
     */
//    @Excel(name = "距拱基线高度")
    private Double heightArchBaseline;


    //** 拔钻日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拔钻日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pullDrillDate;

    /** 拔钻后孔口瓦斯浓度 */
    @Excel(name = "拔钻后孔口瓦斯浓度")
    private String pulledGasConcentration;

    /** 拔钻后孔空CO浓度 */
    @Excel(name = "拔钻后孔空CO浓度")
    private String pulledCOConcentration;

    /** 打钻过程中异常现象 */
    @Excel(name = "打钻过程中异常现象")
    private String exceptionDuringDrilling;
    /** 封孔方式 */
    @Excel(name = "封孔方式")
    private String holesealType;

    private Integer holesealTypeId;
    /** 下管时间起 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下管时间起", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date downTubeTimeStart;

    /** 下管时间止 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下管时间止", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date downTubeTimeEnd;

    /**下管长度*/
    @Excel(name="下管长度")
    private Double tubeLength;
    /** 封孔实管长度（m） */
    @Excel(name = "封孔实管长度", readConverterExp = "m=")
    private Double solidPipelineLength;

    /** 封孔花管长度 */
    @Excel(name = "封孔花管长度")
    private Double sievePipelineLength;

    /** 封孔规格 */
    @Excel(name = "封孔规格")
    private String holeSealingSpecification;

    /**瓦斯检查人员 */
    @Excel(name="瓦斯检查人员")
    private String gasChecker;

    /** 邻近孔号(组号+孔号列表) */
    @Excel(name = "邻近孔号(组号+孔号列表)")
    private String adjacentBoreholeNo;

    /** 与邻近孔距离 */
    @Excel(name = "与邻近孔距离")
    private Double distanceAdjacentBorehole;

    /**钻孔施工人员*/
    @Excel(name="钻孔施工人员")
    private String personConstruction;

    @Excel(name="钻孔负责人")
    private String chargehand;

    /** 验收人员 */
    @Excel(name = "验收人员")
    private String personChecking;

    /** 验收日期 */
    @JsonFormat(pattern = "yyyy-MM-dd ")
    @Excel(name = "验收日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkingDate;

    /** 钻孔标识 */
    @Excel(name = "钻孔标识")
    private String sign;




}
