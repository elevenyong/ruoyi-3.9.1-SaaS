package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 采空区信息对象 t_goafinfo
 * 
 * @author eleven
 * @date 2022-12-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TGoafinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 采空区名称 */
    @Excel(name = "采空区名称")
    private String name;

    /** 采区ID */
    @Excel(name = "采区ID")
    private Integer miningAreaId;

    /**采区名称*/
    private String miningArea;
    /** 形成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "形成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date formedDate;

    /** 开始抽采时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始抽采时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginDrainageDate;

    /** 预计抽采时间(天数) */
    @Excel(name = "预计抽采时间(天数)")
    private Integer predictFinishDays;

    /** 主管直径 */
    @Excel(name = "主管直径")
    private Double mainlineDia;

    /** 支管直径 */
    @Excel(name = "支管直径")
    private Double branchlineDia;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Double pipeLength;

    /**抽采取中测点列表*/
    private List<TMeasuringPoint> measuringPointList;

}
