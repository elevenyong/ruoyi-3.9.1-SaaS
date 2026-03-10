package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.hh.util.handler.CustomDateDeserializer;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * 实钻实体（能组成一个实钻信息所需的所有属性的实体）
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.physical.domain
 * @className: TBKActBoreholeBook
 * @author: Eleven
 * @description: 钻孔台帐实体
 * @date: 2023/7/4 9:54
 * @version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class TBKActBoreholeGeneralized {
    private static final long serialVersionUID = 1L;
    /**
     * $column.columnComment
     */
    private int id;
    @Excel(name="所属巷道名称",width = 15)
    private String roadway;
    /**钻孔编号*/
    @Excel(name="钻孔编号",width = 10)
    private String boreholeCode;
    /** 钻孔类型 */
    @Excel(name="钻孔类型",width = 10)
    private String boreholeCategory;
    private Integer categoryCode;
    /** 钻孔用途 */
    @Excel(name = "钻孔用途",width = 10)
    private String purposeBorehole;
    private Integer purposeBoreholeid;
    /** 钻孔位置 */
    @Excel(name = "钻孔位置",width = 15)
    private String drillingLocation;
    /** 导线点ID */
    @Excel(name = "导线点",width = 8)
    private String navigationPointName;
    private Integer navigationPointid;
    /** 距导线点距离 */
    @Excel(name = "距导线点距离(m)",width = 15)
    private Double distanceToNavigationPoint;
    /** 导线点方向 */
    @Excel(name = "导线点方向",width = 15)
    private String directionToNavigation;
    /** 钻孔日期 */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "钻孔日期", width = 10, dateFormat = "yyyy-MM-dd")
    private Date drillingDate;
    /** 施工孔径 */
    @Excel(name = "施工孔径(mm)",width = 12)
    private Double actAperture;
    /** 施工开孔高度 */
    @Excel(name = "施工开孔高度(m)")
    private Double heightArchBaseline;
    /** 方位角（水平角） */
    @Excel(name = "施工方位角(°)",width = 15)
    private String actAzimuthAngle;
    /** 倾角 */
    @Excel(name = "倾角(°)",width = 8)
    private Double actInclinationAngle;
    /** 夹角 */
    @Excel(name = "夹角(°)",width = 8)
    private Double actAngle;
    /** 施工列间距 */
    @Excel(name = "施工列间距(m)",width = 15)
    private Double actLineSpace;
    /** 施工孔间距 */
    @Excel(name = "施工孔间距(m)",width = 15)
    private Double actHoleSpace;
    /** 实际孔深 */
    @Excel(name = "实际孔深(m)",width = 12)
    private Double actBoreholeLength;
    /** 第一岩起 */
    @Excel(name = "一岩起（m）",width = 12)
    private Double depth1stRockSeamStart;
    /** 第一岩止 */
    @Excel(name = "一岩止（m）",width = 12)
    private Double depth1stRockSeamEnd;
    /** 第一煤起 */
    @Excel(name = "一煤起（m）",width = 12)
    private Double depth1stCoalSeamStart;
    /** 第一煤止 */
    @Excel(name = "一煤止（m）",width = 12)
    private Double depth1stCoalSeamEnd;
    /** 第二煤起 */
    @Excel(name = "二煤起（m）",width = 12)
    private Double depth2stCoalSeamStart;
    /** 第二煤止 */
    @Excel(name = "二煤止（m）",width = 12)
    private Double depth2stCoalSeamEnd;
    /** 第二岩起 */
    @Excel(name = "二岩起（m）",width = 12)
    private Double depth2stRockSeamStart;
    /** 第二岩止 */
    @Excel(name = "二岩止（m）",width = 12)
    private Double depth2stRockSeamEnd;
    /** 第三岩起 */
    @Excel(name = "三岩起（m）",width = 12)
    private Double depth3stRockSeamStart;
    /** 第三岩止 */
    @Excel(name = "三岩止（m）",width = 12)
    private Double depth3stRockSeamEnd;
    /**定向钻点位列表*/
    private List<TActDirectionalDrillingPoint> tActDirectionalDrillingPoints;
    /** 抽采半径 */
    @Excel(name = "抽采半径",width = 10)
    private Double extractionRadius;
    /** 距巷道中线距离 */
    @Excel(name = "距巷道中线距离(m)",width = 18)
    private Double distanceToCentrallineTunnel;
    /**钻孔喷孔情况*/
    @Excel(name="钻孔喷孔情况",width = 12)
    private String sprayHoleCondition;
    /** 打钻过程中异常现象 */
    @Excel(name = "打钻过程中异常现象",width = 18)
    private String exceptionDuringDrilling;
    /** 验收情况 */
    @Excel(name = "验收情况",width = 10)
    private String acceptanceStatus;
    /** 下管时间起 */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下管时间起", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date downTubeTimeStart;
    /** 下管时间止 */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下管时间止", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date downTubeTimeEnd;
    /** 下管长度 */
    @Excel(name = "下管长度(m)",width = 15)
    private Double tubeLength;
    /** 下管负责人 */
    @Excel(name = "下管负责人",width = 15)
    private String downTubeChargehand;
    /** 封孔实管长度m */
    @Excel(name = "封孔实管长度(m)",width = 15)
    private Double solidPipelineLength;
    /** 封孔花管长度 */
    @Excel(name = "封孔花管长度(m)",width = 15)
    private Double sievePipelineLength;
    /** 封孔段长度 */
    @Excel(name = "封孔段长度(m)",width = 15)
    private Double holesealLength;
    /** 封孔深度 */
    @Excel(name = "封孔深度(m)",width = 12)
    private Double holesealDepth;
    /**封孔规格（废弃）*/
    private Double holeSealingSpecification;
    /** 封孔直径 */
    @Excel(name = "封孔直径",width = 10)
    private Double sealingHoleDiameter;
    /** 封孔材料 */
    @Excel(name = "封孔材料",width = 10)
    private String sealingMaterial;
    /** 筛管长度 */
    @Excel(name = "筛管长度",width = 10)
    private Double sieveTubeLength;
    /**封孔时间*/
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name="封孔时间",width = 20,dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date holesealDate;
    /** 封孔负责人 */
    @Excel(name = "封孔负责人",width = 10)
    private String holesealChargehand;
    /** 钻孔负责人 */
    @Excel(name = "钻孔负责人",width = 10)
    private String chargehand;
    /** 视频验收人员 */
    @Excel(name = "视频验收人员",width = 12)
    private String personCheckingVideo;
    /** 现场验收人员 */
    @Excel(name = "现场验收人员",width = 12)
    private String personCheckingSite;
    /** 验收日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收日期", width = 10, dateFormat = "yyyy-MM-dd")
    private Date checkingDate;
    /** 开始冲孔时间 */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始冲孔时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date timeBeginFlushing;
    /** 冲孔结束时间 */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "冲孔结束时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date timeEndFlushing;
    /** 累计冲孔时间 */
    @Excel(name = "累计冲孔时间",width = 12)
    private Double totalTimeFlushing;
    /** 见岩见煤情况 */
    @Excel(name = "见岩见煤情况",width = 12)
    private String seeRockCoalCase;
    /** 实际冲出煤量 */
    @Excel(name = "实际冲出煤量",width = 12)
    private Double actualCoalAmountFlushed;
    /** 冲孔过程中异常标注 */
    @Excel(name = "冲孔过程中异常标注",width = 18)
    private String exceptionPhenomena;
    /** 冲孔负责人 */
    @Excel(name = "冲孔负责人",width = 10)
    private String chargehandFlushing;
    /**终孔日期*/
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name="终孔日期", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date terminalHoleDate;
    /** 冲孔备注 */
    @Excel(name = "冲孔备注",width = 10)
    private String remarkFlushing;
    /** 注浆日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注浆日期", width = 10, dateFormat = "yyyy-MM-dd")
    private Date groutingDate;
    /** 注浆开始时间 */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注浆开始时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date groutingStartTime;
    /** 注浆结束时间 */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注浆结束时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date groutingEndTime;
    /** 注浆水泥量 */
    @Excel(name = "注浆水泥量",width = 10)
    private String groutingCements;
    /** 注浆水量 */
    @Excel(name = "注浆水量",width = 10)
    private String groutingWaters;
    /** 注浆异常现象标注 */
    @Excel(name = "注浆异常现象标注",width = 15)
    private String groutingExceptionLabel;
    /** 注浆负责人 */
    @Excel(name = "注浆负责人",width = 10)
    private String groutingChargehand;
    /** 注浆现场验收人员 */
    @Excel(name = "注浆现场验收人员",width = 15)
    private String personCheckingSiteGrouting;
    /** 注浆视频验收人员 */
    @Excel(name = "注浆视频验收人员",width = 15)
    private String personCheckingVideoGrouting;

}
