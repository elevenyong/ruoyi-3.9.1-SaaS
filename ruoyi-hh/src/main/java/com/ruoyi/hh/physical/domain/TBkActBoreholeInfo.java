package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.util.handler.CustomDateDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 钻孔验收单对象 t_bk_act_borehole_info
 *
 * @author eleven
 * @date 2023-05-27
 */
@ApiModel("DZZK实钻普钻钻孔")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBkActBoreholeInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @ApiModelProperty("实钻钻孔系统内ID")
    private Integer id;

    /**
     * 所属巷道ID
     */
    @Excel(name = "所属巷道ID")
    @ApiModelProperty("所属巷道ID")
    private Integer roadwayId;
    @Excel(name = "所属巷道名称", width = 15)
    @ApiModelProperty("所属巷道名称")
    @JsonIgnore
    private String roadway;

    /**
     * 所属区段ID
     */
    @Excel(name = "所属区段ID")
    @Deprecated
    @JsonIgnore
    private Integer roadwayZoneId;
    @Excel(name = "所属区段名称", width = 12)
    @Deprecated
    @JsonIgnore
    private String zoneName;

    /**
     * 组号
     */
    @Excel(name = "组号", width = 8)
    @ApiModelProperty("组号")
    private String groupNo;

    /**
     * 孔号
     */
    @Excel(name = "孔号", width = 5)
    @ApiModelProperty("孔号")
    private String boreholeNo;

    /**
     * 钻孔编号
     */
    @Excel(name = "钻孔编号", width = 10)
    @ApiModelProperty("钻孔编号")
    private String boreholeCode;

    /**
     * 钻孔类型编码
     */
    @ApiModelProperty("钻孔类型ID：1=条带治理穿层孔, 2=回采区穿层孔, 3=高位钻孔, 4=顺层钻孔, 5=煤巷掘进头顺层孔, 6=高位定向钻孔," +
            " 7=短定向钻孔, 8=测斜钻孔, 9=超前探钻孔, 10=自定义起点钻孔, 11=区域效果检验钻孔, 12=区域验证钻孔, 13=区域原始参数钻孔")
    private Integer categoryCode;


    @Excel(name = "钻孔类型", width = 10)
    @ApiModelProperty("钻孔类型名称")
    @JsonIgnore
    private String boreholeCategory;

    @Excel(name = "钻孔用途id")
    @ApiModelProperty("钻孔用途id")
    @JsonIgnore
    private Integer purposeBoreholeId;
    /**
     * 钻孔用途
     */
    @Excel(name = "钻孔用途", width = 10)
    @ApiModelProperty("钻孔用途")
    @JsonIgnore
    private String purposeBorehole;

    /**
     * 钻孔位置
     */
    @Excel(name = "钻孔位置", width = 30)
    @ApiModelProperty("钻孔位置")
    @JsonIgnore
    private String drillingLocation;

    /**
     * 钻孔起点位置
     */
    @Excel(name = "钻孔起点位置X")
    @ApiModelProperty("钻孔起点位置X")
    @JsonIgnore
    private String drillingStartX;

    @Excel(name = "钻孔起点位置Y")
    @ApiModelProperty("钻孔起点位置Y")
    @JsonIgnore
    private String drillingStartY;

    @Excel(name = "钻孔起点位置Z")
    @ApiModelProperty("钻孔起点位置Z")
    @JsonIgnore
    private String drillingStartZ;

    /**
     * 是否用于煤层创建
     */
    @Excel(name = "是否用于煤层创建")
    @ApiModelProperty("是否用于煤层创建,0=否,1=是")
    private Integer isUsedCreateCoal;

    /**
     * 钻孔日期
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "钻孔日期", width = 15, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("钻孔日期")
    @JsonIgnore
    private Date drillingDate;

    /**
     * 班次ID
     */
    @Excel(name = "班次名称", width = 5)
    @ApiModelProperty("班次名称")
    @JsonIgnore
    private String shiftInfo;
    @ApiModelProperty("班次ID")
    @JsonIgnore
    private Integer shiftId;
    /**
     * 钻机信息ID
     */
    @ApiModelProperty("钻机信息ID")
    @JsonIgnore
    private Integer drillinginfoId;
    @ApiModelProperty("钻机名称")
    @JsonIgnore
    private String drillingName;

    /**
     * 导线点ID
     */
    @Excel(name = "导线点ID")
    @ApiModelProperty("导线点ID")
    @JsonIgnore
    private Integer navigationPointId;
    @Excel(name = "导线点", width = 10)
    @ApiModelProperty("导线点名称")
    @JsonIgnore
    private String navigationPointName;

    /**
     * 距导线点距离
     */
    @Excel(name = "距导线点距离(m)", width = 15)
    @ApiModelProperty("距导线点距离")
    private Double distanceToNavigationPoint;

    /**
     * 导线点方向
     */
    @Excel(name = "导线点方向", width = 10)
    @ApiModelProperty("导线点方向")
    @JsonIgnore
    private String directionToNavigation;

    /**
     * 施工孔径
     */
    @Excel(name = "施工孔径(mm)", width = 15)
    @ApiModelProperty("施工孔径(mm)")
    private Double aperture;

    /**
     * 验收孔径
     */
    @Excel(name = "验收孔径(mm)", width = 15)
    @ApiModelProperty("验收孔径(mm)")
    @JsonIgnore
    private Double checkAperture;

    /**
     * 开孔高度
     */
    @Excel(name = "开孔高度", width = 15)
    @ApiModelProperty("开孔高度")
    private Double holeAltitude;

    /**
     * 验收开孔高度
     */
    @Excel(name = "验收开孔高度(m)", width = 15)
    @ApiModelProperty("验收开孔高度")
    @JsonIgnore
    private Double checkHoleAltitude;

    /**
     * 施工方位角
     */
    @Excel(name = "施工方位角(°)", width = 15)
    @ApiModelProperty("施工方位角")
    private String azimuthAngle;

    /**
     * 验收方位角
     */
    @Excel(name = "验收方位角", width = 15)
    @ApiModelProperty("验收方位角")
    @JsonIgnore
    private String checkAzimuthAngle;

    /**
     * 施工倾角
     */
    @Excel(name = "倾角(°)", width = 5)
    @ApiModelProperty("倾角")
    private Double inclinationAngle;

    /**
     * 验收倾角
     */
    @Excel(name = "验收倾角(°)", width = 10)
    @ApiModelProperty("验收倾角")
    @JsonIgnore
    private Double checkInclinationAngle;

    /**
     * 施工夹角
     */
    @Excel(name = "夹角(°)", width = 5)
    @ApiModelProperty("夹角")
    private Double angle;

    /**
     * 验收夹角
     */
    @Excel(name = "验收夹角(°)", width = 10, readConverterExp = "")
    @ApiModelProperty("验收夹角")
    @JsonIgnore
    private Double checkAngle;

    /**
     * 施工列间距
     */
    @Excel(name = "施工列间距(m)", width = 15)
    @ApiModelProperty("施工列间距")
    @JsonIgnore
    private Double lineSpace;

    /**
     * 验收列间距
     */
    @Excel(name = "验收列间距(m)", width = 15)
    @ApiModelProperty("验收列间距")
    @JsonIgnore
    private Double checkLineSpace;

    /**
     * 施工孔间距
     */
    @Excel(name = "施工孔间距(m)", width = 15)
    @ApiModelProperty("施工孔间距")
    @JsonIgnore
    private Double holeSpace;

    /**
     * 验收孔间距
     */
    @Excel(name = "验收孔间距(m)", width = 15)
    @ApiModelProperty("验收孔间距")
    @JsonIgnore
    private Double checkHoleSpace;

    /**
     * 实际孔深
     */
    @Excel(name = "实际孔深(m)", width = 15)
    @ApiModelProperty("实际孔深")
    private Double boreholeLength;

    /**
     * 验收孔深
     */
    @Excel(name = "验收孔深(m)", width = 15)
    @ApiModelProperty("验收孔深")
    @JsonIgnore
    private Double checkBoreholeLength;

    /**
     * 定向钻点位列表
     */
    @ApiModelProperty("定向钻点位列表")
    private List<TActDirectionalDrillingPoint> tActDirectionalDrillingPoints;

    /**
     * 第一岩起
     */
    @Excel(name = "第一岩起", width = 10)
    @ApiModelProperty("弃用：第一岩起")
    @JsonIgnore
    private Double depth1stRockSeamStart;

    /**
     * 第一岩止
     */
    @Excel(name = "第一岩止", width = 10)
    @ApiModelProperty("弃用：第一岩止")
    @JsonIgnore
    private Double depth1stRockSeamEnd;

    /**
     * 第一煤起
     */
    @Excel(name = "第一煤起", width = 10)
    @ApiModelProperty("第一煤起:岩1")
    private Double depth1stCoalSeamStart;

    /**
     * 第一煤止
     */
    @Excel(name = "第一煤止", width = 10)
    @ApiModelProperty("第一煤止:煤1")
    private Double depth1stCoalSeamEnd;

    /**
     * 第二岩起
     */
    @Excel(name = "第二岩起", width = 10)
    @ApiModelProperty("弃用：第二岩起")
    @JsonIgnore
    private Double depth2stRockSeamStart;

    /**
     * 第二岩止
     */
    @Excel(name = "第二岩止", width = 10)
    @ApiModelProperty("弃用：第二岩止")
    @JsonIgnore
    private Double depth2stRockSeamEnd;

    /**
     * 第二煤起
     */
    @Excel(name = "第二煤起", width = 10)
    @ApiModelProperty("第二煤起:岩2")
    private Double depth2stCoalSeamStart;

    /**
     * 第二煤止
     */
    @Excel(name = "第二煤止", width = 10)
    @ApiModelProperty("第二煤止:煤2")
    private Double depth2stCoalSeamEnd;

    /**
     * 第三岩起
     */
    @Excel(name = "第三岩起", width = 10)
    @ApiModelProperty("弃用：第三岩起")
    @JsonIgnore
    private Double depth3stRockSeamStart;

    /**
     * 第三岩止
     */
    @Excel(name = "第三岩止", width = 10)
    @ApiModelProperty("弃用：第三岩止")
    @JsonIgnore
    private Double depth3stRockSeamEnd;

    /** 第三煤起 */
    @Excel(name = "第三煤起")
    @ApiModelProperty("第三煤起:岩3")
    private Double depth3stCoalSeamStart;

    /** 第三煤止 */
    @Excel(name = "第三煤止")
    @ApiModelProperty("第三煤止:煤3")
    private Double depth3stCoalSeamEnd;

    /** 第四岩起 */
    @Excel(name = "第四岩起")
    @ApiModelProperty("弃用：第四岩起")
    @JsonIgnore
    private Double depth4stRockSeamStart;

    /** 第四岩止 */
    @Excel(name = "第四岩止")
    @ApiModelProperty("第四岩止:岩4")
    private Double depth4stRockSeamEnd;

    /**
     * 绑定第一煤段
     */
    @ApiModelProperty("绑定第一段煤层地层ID")
    private Integer bindCoal1st;

    /**
     * 绑定第二煤段
     */
    @ApiModelProperty("绑定第二段煤层地层ID")
    private Integer bindCoal2st;

    /**
     * 绑定第三煤段
     */
    @ApiModelProperty("绑定第三段煤层地层ID")
    private Integer bindCoal3st;

    /**
     * 是否用于煤层创建
     */
    @Excel(name = "第一煤段是否用于煤层创建")
    @ApiModelProperty("第一煤段是否用于煤层创建,0=否,1=是")
    private Integer isUsedCreateCoal1st;

    /**
     * 是否用于煤层创建
     */
    @Excel(name = "第二煤段是否用于煤层创建")
    @ApiModelProperty("第二煤段是否用于煤层创建,0=否,1=是")
    private Integer isUsedCreateCoal2st;

    /**
     * 是否用于煤层创建
     */
    @Excel(name = "第三煤段是否用于煤层创建")
    @ApiModelProperty("第三煤段是否用于煤层创建,0=否,1=是")
    private Integer isUsedCreateCoal3st;

    /**
     * 第一煤段绑定属性数据
     */
    @ApiModelProperty("第一煤段绑定属性数据")
    private List<TAttributeValue> bindAttributesCoal1st;

    /**
     * 第二煤段绑定属性数据
     */
    @ApiModelProperty("第二煤段绑定属性数据[暂留，但未保存数据]")
    private List<TAttributeValue> bindAttributesCoal2st;

    /**
     * 第三煤段绑定属性数据
     */
    @ApiModelProperty("第三煤段绑定属性数据[暂留，但未保存数据]")
    private List<TAttributeValue> bindAttributesCoal3st;

    /**
     * 抽采半径
     */
    @Excel(name = "抽采半径", width = 10)
    @ApiModelProperty("抽采半径")
    private Double extractionRadius;

    /**
     * 距巷道中线距离
     */
    @Excel(name = "距巷道中线距离(m)", width = 18)
    @ApiModelProperty("距巷道中线距离")
    private Double distanceToCentrallineTunnel;

    /**
     * 拔钻日期
     */
    @Excel(name = "拔钻日期", width = 15)
    @ApiModelProperty("拔钻日期")
    @JsonIgnore
    private Date pullDrillDate;

    /**
     * 临近孔号(组号+孔号列表)
     */
    @Excel(name = "临近孔号(组号+孔号列表)")
    @ApiModelProperty("临近孔号(组号+孔号列表)")
    @JsonIgnore
    private String adjacentBoreholeNo;

    /**
     * 与临近孔距离
     */
    @Excel(name = "与临近孔距离", width = 15)
    @ApiModelProperty("与临近孔距离")
    @JsonIgnore
    private Double distanceAdjacentBorehole;

    /**
     * 钻孔喷空情况
     */
    @Excel(name = "钻孔喷孔情况", width = 15)
    @ApiModelProperty("钻孔喷孔情况")
    @JsonIgnore
    private String sprayHoleCondition;

    /**
     * 打钻过程中异常现象
     */
    @Excel(name = "打钻过程中异常现象", width = 15)
    @ApiModelProperty("打钻过程中异常现象")
    @JsonIgnore
    private String exceptionDuringDrilling;

    /**
     * 施工异常情况
     */
    @Excel(name = "施工异常情况", width = 15)
    @ApiModelProperty("施工异常情况")
    private String exceptionConstruction;

    /**
     * 验收情况
     */
    @Excel(name = "验收情况", width = 10)
    @ApiModelProperty("验收情况")
    @JsonIgnore
    private String acceptanceStatus;

    /**
     * 下管时间起
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下管时间起", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("下管时间起")
    @JsonIgnore
    private Date downTubeTimeStart;

    /**
     * 下管时间止
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下管时间止", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("下管时间止")
    @JsonIgnore
    private Date downTubeTimeEnd;

    /**
     * 下管长度
     */
    @Excel(name = "下管长度(m)", width = 10)
    @ApiModelProperty("下管长度")
    @JsonIgnore
    private Double tubeLength;

    /**
     * 下管负责人
     */
    @Excel(name = "下管负责人", width = 10)
    @ApiModelProperty("下管负责人")
    @JsonIgnore
    private String downTubeChargehand;

//    /** 封孔方式ID */
//    @Excel(name = "封孔方式",width = 10)
//    private String holesealType;
//    private Integer holesealTypeId;

    /**
     * 封孔实管长度m
     */
    @Excel(name = "封孔实管长度(m)", width = 15)
    @ApiModelProperty("封孔实管长度")
    @JsonIgnore
    private Double solidPipelineLength;

    /**
     * 封孔花管长度
     */
    @Excel(name = "封孔花管长度(m)", width = 15)
    @ApiModelProperty("封孔花管长度")
    @JsonIgnore
    private Double sievePipelineLength;

    /**
     * 封孔段长度
     */
    @Excel(name = "封孔段长度(m)", width = 15)
    @ApiModelProperty("封孔段长度")
    @JsonIgnore
    private Double holesealLength;

    /**
     * 封孔深度
     */
    @Excel(name = "封孔深度(m)", width = 15)
    @ApiModelProperty("封孔深度")
    @JsonIgnore
    private Double holesealDepth;

    /**
     * 封孔直径
     */
    @Excel(name = "封孔直径(mm)", width = 15)
    @ApiModelProperty("封孔直径")
    @JsonIgnore
    private Double sealingHoleDiameter;

    /**
     * 封孔规格（管直径）
     */
    @Excel(name = "封孔规格", readConverterExp = "管=直径")
    @ApiModelProperty("封孔规格")
    @JsonIgnore
    private Double holeSealingSpecification;

    /**
     * 封孔材料
     */
    @Excel(name = "封孔材料", width = 10)
    @ApiModelProperty("封孔材料")
    @JsonIgnore
    private String sealingMaterial;

    /**
     * 筛管长度
     */
    @Excel(name = "筛管长度", width = 10)
    @ApiModelProperty("筛管长度")
    @JsonIgnore
    private Double sieveTubeLength;

    /**
     * 封孔时间
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "封孔时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("封孔时间")
    @JsonIgnore
    private Date holesealDate;

    /**
     * 封孔负责人
     */
    @Excel(name = "封孔负责人", width = 10)
    @ApiModelProperty("封孔负责人")
    @JsonIgnore
    private String holesealChargehand;

    /**
     * 有效进尺
     */
    @Excel(name = "有效进尺", width = 10)
    @ApiModelProperty("有效进尺")
    @JsonIgnore
    private Double validFootage;

    /**
     * 钻孔施工人员
     */
    @Excel(name = "钻孔施工人员", width = 10)
    @ApiModelProperty("钻孔施工人员")
    @JsonIgnore
    private String personConstruction;

    /**
     * 钻孔负责人
     */
    @Excel(name = "钻孔负责人", width = 10)
    @ApiModelProperty("钻孔负责人")
    @JsonIgnore
    private String chargehand;

    /**
     * 视频验收人员
     */
    @Excel(name = "视频验收人员", width = 15)
    @ApiModelProperty("视频验收人员")
    @JsonIgnore
    private String personCheckingVideo;

    /**
     * 现场验收人员
     */
    @Excel(name = "现场验收人员", width = 15)
    @ApiModelProperty("现场验收人员")
    @JsonIgnore
    private String personCheckingSite;

    /**
     * 验收日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收日期", width = 15, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("验收日期")
    @JsonIgnore
    private Date checkingDate;

    /**
     * 钻孔标识
     */
    @Excel(name = "钻孔标识", width = 10)
    @ApiModelProperty("钻孔标识")
    private String sign;

    /**
     * 开始冲孔时间
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始冲孔时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("开始冲孔时间")
    @JsonIgnore
    private Date timeBeginFlushing;

    /**
     * 冲孔结束时间
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "冲孔结束时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("冲孔结束时间")
    @JsonIgnore
    private Date timeEndFlushing;

    /**
     * 累计冲孔时间
     */
    @Excel(name = "累计冲孔时间", width = 10)
    @ApiModelProperty("累计冲孔时间")
    @JsonIgnore
    private Double totalTimeFlushing;

    /**
     * 冲孔压力
     */
    @Excel(name = "冲孔压力", width = 10)
    @ApiModelProperty("冲孔压力")
    @JsonIgnore
    private Double waterPressureDuringFlushing;

    /**
     * 冲孔段1
     */
    @Excel(name = "冲孔段1", width = 10)
    @ApiModelProperty("冲孔段1")
    @JsonIgnore
    private Double punchingParagraph1;

    /**
     * 冲孔段2
     */
    @Excel(name = "冲孔段2", width = 10)
    @ApiModelProperty("冲孔段2")
    @JsonIgnore
    private Double punchingParagraph2;

    /**
     * 见岩见煤情况
     */
    @Excel(name = "见岩见煤情况", width = 15)
    @ApiModelProperty("见岩见煤情况")
    private String seeRockCoalCase;

    /**
     * 实际冲出煤量
     */
    @Excel(name = "实际冲出煤量", width = 15)
    @ApiModelProperty("实际冲出煤量")
    private Double actualCoalAmountFlushed;

    /**
     * 冲煤完成比例
     */
    @Excel(name = "冲煤完成比例", width = 15)
    @ApiModelProperty("冲煤完成比例")
    @JsonIgnore
    private Double completionRatioFlushedCoal;

    /**
     * 冲孔过程中异常标注
     */
    @Excel(name = "冲孔过程中异常标注", width = 15)
    @ApiModelProperty("冲孔过程中异常标注")
    @JsonIgnore
    private String exceptionPhenomena;

    /**
     * 冲孔施工人员
     */
    @Excel(name = "冲孔施工人员", width = 15)
    @ApiModelProperty("冲孔施工人员")
    @JsonIgnore
    private String personConstructionFlushing;

    /**
     * 跟班人员
     */
    @Excel(name = "跟班人员", width = 10)
    @ApiModelProperty("跟班人员")
    @JsonIgnore
    private String otherPerson;

    /**
     * 冲孔负责人
     */
    @Excel(name = "冲孔负责人", width = 10)
    @ApiModelProperty("冲孔负责人")
    @JsonIgnore
    private String chargehandFlushing;

    /**
     * 终孔日期
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "终孔日期", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("终孔日期")
    private Date terminalHoleDate;

    /**
     * 冲孔备注
     */
    @Excel(name = "冲孔备注", width = 10)
    @ApiModelProperty("冲孔备注")
    @JsonIgnore
    private String remarkFlushing;

    /**
     * 原冲孔验收单文件路径
     */
    @ApiModelProperty("原冲孔验收单文件路径")
    @JsonIgnore
    private String file;

    /**
     * 注浆日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注浆日期", width = 10, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("注浆日期")
    @JsonIgnore
    private Date groutingDate;

    /**
     * 注浆班次
     */
    @Excel(name = "注浆班次", width = 10)
    @JsonIgnore
    private String groutingShift;
    @ApiModelProperty("注浆班次ID")
    @JsonIgnore
    private Integer groutingShiftId;

    /**
     * 注浆开始时间
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注浆开始时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("注浆开始时间")
    @JsonIgnore
    private Date groutingStartTime;

    /**
     * 注浆结束时间
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注浆结束时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("注浆结束时间")
    @JsonIgnore
    private Date groutingEndTime;

    /**
     * 注浆水泥量
     */
    @Excel(name = "注浆水泥量", width = 15)
    @ApiModelProperty("注浆水泥量")
    @JsonIgnore
    private String groutingCements;

    /**
     * 注浆水量
     */
    @Excel(name = "注浆水量", width = 15)
    @ApiModelProperty("注浆水量")
    @JsonIgnore
    private String groutingWaters;

    /**
     * 实际囊袋注浆量
     */
    @Excel(name = "实际囊袋注浆量", width = 15)
    @ApiModelProperty("实际囊袋注浆量")
    @JsonIgnore
    private String actualAmountBagGrouting;

    /**
     * 囊袋注浆压力
     */
    @Excel(name = "囊袋注浆压力", width = 15)
    @ApiModelProperty("囊袋注浆压力")
    @JsonIgnore
    private Double actualBagPressure;

    /**
     * 实际中间段注浆量
     */
    @Excel(name = "实际中间段注浆量", width = 15)
    @ApiModelProperty("实际中间段注浆量")
    @JsonIgnore
    private String actualAmountGapGrouting;

    /**
     * 实际中间段注浆压力
     */
    @Excel(name = "实际中间段注浆压力", width = 15)
    @ApiModelProperty("实际中间段注浆压力")
    @JsonIgnore
    private String interludeGroutingPressure;

    /**
     * 注浆人员
     */
    @Excel(name = "注浆人员", width = 10)
    @ApiModelProperty("注浆人员")
    @JsonIgnore
    private String groutingPerson;

    /**
     * 注浆异常现象标注
     */
    @Excel(name = "注浆异常现象标注", width = 15)
    @ApiModelProperty("注浆异常现象标注")
    @JsonIgnore
    private String groutingExceptionLabel;

    /**
     * 注浆施工人员
     */
    @Excel(name = "注浆施工人员", width = 15)
    @ApiModelProperty("注浆施工人员")
    @JsonIgnore
    private String groutingPersonConstruction;

    /**
     * 注浆负责人
     */
    @Excel(name = "注浆负责人", width = 10)
    @ApiModelProperty("注浆负责人")
    @JsonIgnore
    private String groutingChargehand;

    /**
     * 注浆现场验收人员
     */
    @Excel(name = "注浆现场验收人员", width = 15)
    @ApiModelProperty("注浆现场验收人员")
    @JsonIgnore
    private String personCheckingSiteGrouting;

    /**
     * 注浆视频验收人员
     */
    @Excel(name = "注浆视频验收人员", width = 15)
    @ApiModelProperty("注浆视频验收人员")
    @JsonIgnore
    private String personCheckingVideoGrouting;

    /**
     * 班长
     */
    @Excel(name = "班长", width = 5)
    @ApiModelProperty("班长")
    @JsonIgnore
    private String monitor;

    /**
     * 区长
     */
    @Excel(name = "区长", width = 5)
    @ApiModelProperty("区长")
    @JsonIgnore
    private String warden;

    /**
     * 防突科
     */
    @Excel(name = "防突科", width = 8)
    @ApiModelProperty("防突科")
    @JsonIgnore
    private String eliminate;

    /**
     * 防突副总
     */
    @Excel(name = "防突副总", width = 10)
    @ApiModelProperty("防突副总")
    @JsonIgnore
    private String eliminateDeputyGeneralManager;

    /**
     * 防突矿长
     */
    @Excel(name = "防突矿长", width = 10)
    @ApiModelProperty("防突矿长")
    @JsonIgnore
    private String eliminateBarmaster;

    /**
     * 备注
     */
    @Excel(name = "备注", width = 10)
    @ApiModelProperty("备注")
    @JsonIgnore
    private String remark;

}
