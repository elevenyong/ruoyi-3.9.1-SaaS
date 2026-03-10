package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.util.handler.CustomDateDeserializer;
import lombok.*;

import java.util.Date;

/**
 * 实钻实体（根据八矿的台帐信息设计的Excel）
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class TBKActBoreholeSimple extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * $column.columnComment
     */
    private int id;
    /** 所属巷道ID */
    private Integer roadwayId;
    @Excel(name="所属巷道名称",width = 15)
    private String roadway;
    /** 所属区段ID */
    private Integer roadwayZoneId;
    @Excel(name="所属区段名称",width = 15)
    private String zonename;
//    /** 组号 */
//    @Excel(name = "组号",width = 5)
//    private String groupNo;
//    /** 孔号 */
//    @Excel(name = "孔号",width = 5)
//    private String boreholeNo;
    /**钻孔编号*/
    @Excel(name="钻孔编号",width = 10)
    private String boreholeCode;
    /** 实际孔深 */
    @Excel(name = "实际孔深",width = 10)
    private Double actBoreholeLength;
    /** 设计孔深 */
    @Excel(name = "设计孔深",width = 10)
    private Double desBoreholeLength;
    /** 第一岩起 */
    @Excel(name = "岩一起",width = 8)
    private Double depth1stRockSeamStart;
    /** 第一岩止 */
    @Excel(name = "岩一止",width = 8)
    private Double depth1stRockSeamEnd;
    /** 第一煤起 */
    @Excel(name = "煤一起",width = 8)
    private Double depth1stCoalSeamStart;
    /** 第一煤止 */
    @Excel(name = "煤一止",width = 8)
    private Double depth1stCoalSeamEnd;
    /** 第二煤起 */
    @Excel(name = "煤二起",width = 8)
    private Double depth2stCoalSeamStart;
    /** 第二煤止 */
    @Excel(name = "煤二止",width = 8)
    private Double depth2stCoalSeamEnd;
    /** 第二岩起 */
    @Excel(name = "岩二起",width = 8)
    private Double depth2stRockSeamStart;
    /** 第二岩止 */
    @Excel(name = "岩二止",width = 8)
    private Double depth2stRockSeamEnd;
    /** 第三岩起 */
    @Excel(name = "岩三起",width = 8)
    private Double depth3stRockSeamStart;
    /** 第三岩止 */
    @Excel(name = "岩三止",width = 8)
    private Double depth3stRockSeamEnd;
    /** 冲煤量 */
    @Excel(name = "冲煤量(m³)", width = 10)
    private Double actualCoalAmountFlushed;
    /**钻孔喷孔情况*/
    @Excel(name = "钻孔喷孔情况",width = 15)
    private String sprayHoleCondition;
    /**施工异常情况*/
    @Excel(name="施工异常情况",width = 15)
    private String exceptionConstruction;
    /**验收情况*/
    @Excel(name="验收情况",width = 10)
    private String acceptanceStatus;
    /** 孔径 */
    @Excel(name = "孔径(mm)",width = 10)
    private Double actAperture;
    /**水平角*/
    @Excel(name="水平角(°)",width = 10)
    private String actAzimuthAngle;
    /** 角度 */
    @Excel(name = "实际角度(°)",width = 10)
    private Double actAngle;
    /** 钻孔位置 */
    @Excel(name = "钻孔位置")
    private String drillingLocation;
    /** 打钻过程中异常现象 */
    @Excel(name = "打钻过程中异常现象", width = 18)
    private String exceptionDuringDrilling;
    /**终孔日期*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name="终孔日期", width = 18, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date terminalHoleDate;
    /** 钻孔负责人 */
    @Excel(name = "钻孔负责人", width = 15)
    private String chargehand;
    /** 视频验收人员 */
    @Excel(name = "视频验收人员", width = 12)
    private String personCheckingVideo;
    /** 现场验收人员 */
    @Excel(name = "现场验收人员", width = 12)
    private String personCheckingSite;


    /** 累计冲孔时间 */
    @Excel(name = "累计冲孔时间", width = 12)
    private Double totalTimeFlushing;
    /** 见岩见煤情况 */
    @Excel(name = "见岩见煤情况", width = 12)
    private String seeRockCoalCase;
    /** 冲孔过程中异常标注 */
    @Excel(name = "冲孔过程中异常标注", width = 15)
    private String exceptionPhenomena;

    /**封孔规格（废弃）*/
    private Double holeSealingSpecification;
    /** 封孔管直径*/
    @Excel(name = "封孔管直径(mm)", width =15)
    private Double sealingHoleDiameter;
    /** 封孔实管长度m */
    @Excel(name = "封孔实管长度(m)", width =15)
    private Double solidPipelineLength;
    /** 封孔花管长度 */
    @Excel(name = "封孔花管长度(m)", width =15)
    private Double sievePipelineLength;
    /**封孔材料*/
    @Excel(name="封孔材料", width=10)
    private String sealingMaterial;
    /**封孔深度*/
    @Excel(name="封孔管长度(m)", width =15)
    private Double holesealDepth;
    /**封孔时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name="封孔时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date holesealDate;
    /** 封孔负责人 */
    @Excel(name = "封孔负责人", width =15)
    private String holesealChargehand;


    /** 注浆日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注浆日期", width = 10, dateFormat = "yyyy-MM-dd")
    private Date groutingDate;
    /** 注浆开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注浆开始时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date groutingStartTime;
    /** 注浆结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注浆结束时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date groutingEndTime;
    /** 注浆水泥量 */
    @Excel(name = "注浆水泥量", width =10)
    private String groutingCements;
    /** 注浆水量 */
    @Excel(name = "注浆水量", width =10)
    private String groutingWaters;
    /** 注浆负责人 */
    @Excel(name = "注浆负责人", width =10)
    private String groutingChargehand;
    /** 注浆现场验收人员 */
    @Excel(name = "注浆现场验收人员", width =15)
    private String personCheckingSiteGrouting;
    /** 注浆视频验收人员 */
    @Excel(name = "注浆视频验收人员", width =15)
    private String personCheckingVideoGrouting;

    /**备注*/
    @Excel(name="备注")
    private String remark;

}
