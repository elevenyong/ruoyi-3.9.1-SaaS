package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.physical.domain
 * @className: TBKActFlushing
 * @author: Eleven
 * @description: 冲孔实体
 * @date: 2023/7/15 15:04
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBKActFlushing extends BaseEntity {

    /** 所属巷道ID */
    private Integer roadwayId;
    @Excel(name="所属巷道名称",width = 15)
    private String roadway;

    /** 组号 */
    @Excel(name = "组号",width = 8)
    private String groupNo;

    /** 孔号 */
    @Excel(name = "孔号",width = 5)
    private String boreholeNo;

    /** 钻孔编号 */
    @Excel(name = "钻孔编号",width = 10)
    private String boreholeCode;

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

    /** 见岩见煤情况 */
    @Excel(name = "见岩见煤情况",width = 15)
    private String seeRockCoalCase;

    /** 实际冲出煤量 */
    @Excel(name = "实际冲出煤量",width = 15)
    private Double actualCoalAmountFlushed;

    /** 冲煤完成比例 */
    @Excel(name = "冲煤完成比例",width = 15)
    private Double completionRatioFlushedCoal;

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

    /**终孔日期*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name="终孔日期", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date terminalHoleDate;

    /** 冲孔备注 */
    @Excel(name = "冲孔备注",width = 10)
    private String remarkFlushing;

    /** 原冲孔验收单文件路径 */
    private String file;
}
