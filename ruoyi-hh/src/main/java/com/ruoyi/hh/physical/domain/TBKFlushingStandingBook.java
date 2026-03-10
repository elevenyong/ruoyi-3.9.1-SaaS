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
 * 水力冲孔台帐导出实体
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBKFlushingStandingBook extends BaseEntity {
    /** 所属巷道ID */
    private Integer roadwayId;
    @Excel(name="所属巷道名称")
    private String roadway;
    /** 所属区段ID */
    private Integer roadwayZoneId;
    @Excel(name="所属区段名称")
    private String zoneName;
    /** 钻孔编号 */
    @Excel(name = "钻孔编号",width = 10)
    private String boreholeCode;
    /** 钻孔位置 */
    @Excel(name = "钻孔位置",width = 30)
    private String drillingLocation;
    /** 孔深 */
    @Excel(name = "孔深(m)",width = 15)
    private Double actBoreholeLength;
    /** 开始冲孔时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始冲孔时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date timeBeginFlushing;

    /** 冲孔结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "冲孔结束时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date timeEndFlushing;

    /** 累计冲孔时间 */
    @Excel(name = "累计冲孔时间",width = 10)
    private Double totalTimeFlushing;

    /** 冲孔压力 */
    @Excel(name = "冲孔压力",width = 10)
    private Double waterPressureDuringFlushing;

    /** 冲孔段1 */
    @Excel(name = "冲孔段1",width = 10)
    private Double punchingParagraph1;

    /** 冲孔段2 */
    @Excel(name = "冲孔段2",width = 10)
    private Double punchingParagraph2;

    /** 实际冲出煤量 */
    @Excel(name = "实际冲出煤量",width = 15)
    private Double actualCoalAmountFlushed;

    /** 冲孔过程中异常标注 */
    @Excel(name = "冲孔过程中异常标注",width = 15)
    private String exceptionPhenomena;

    /** 冲孔施工人员 */
    @Excel(name = "冲孔施工人员",width = 15)
    private String personConstructionFlushing;

    /** 跟班人员 */
    @Excel(name = "跟班人员",width = 10)
    private String otherPerson;
    /** 冲孔负责人 */
    @Excel(name = "冲孔负责人",width = 10)
    private String chargehandFlushing;
    /** 冲孔备注 */
    @Excel(name = "冲孔备注",width = 10)
    private String remarkFlushing;
}
