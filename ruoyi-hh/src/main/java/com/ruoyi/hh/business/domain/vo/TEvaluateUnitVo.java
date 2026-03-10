package com.ruoyi.hh.business.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.hh.physical.domain.TEvaluateUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 评价单元信息对象 t_evaluate_unit
 * 
 * @author eleven
 * @date 2022-08-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TEvaluateUnitVo extends BaseEntity
{

    /** 评价单元名称 */
    @Excel(name = "评价单元名称")
    private String evaluateUnitName;

    /** 宽 */
    @Excel(name = "宽")
    private Double wide;

    /** 长 */
    @Excel(name = "长")
    private Double length;

    /** 煤层厚度 */
    @Excel(name = "煤层厚度")
    private Double coalThickness;

    /** 评价单元储煤量万吨 */
    @Excel(name = "评价单元储煤量万吨")
    private Double coalReserves;

    /** 平面积（m²） */
    @Excel(name = "平面积", readConverterExp = "m=²")
    private Double area;

    /** 容量 */
    @Excel(name = "容量")
    private Double capacity;

    /** 所属工作面ID */
    @Excel(name = "所属工作面ID")
    private Integer workfaceId;

    /** 评价单元原始瓦斯含量立方米每吨 */
    @Excel(name = "评价单元原始瓦斯含量立方米每吨")
    private Double gasContentOriginal;

    /** 评价单元瓦斯储量万立方米 */
    @Excel(name = "评价单元瓦斯储量万立方米")
    private Double gasTotalReserves;
    /**开始施工日期*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startConstruction;
    /**结束施工日期*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endConstruction;

    /**累计抽采量*/
    private Double accumulatedAmount;
    /**残余瓦斯储量*/
    private Double remnantGasReserves;
    /**残余瓦斯含量*/
    private Double remnantGasContentRemnant;
    /**抽采率*/
    private Double completeRatio;
    /** 预抽时间最长的钻孔ID */
    @Excel(name = "预抽时间最长的钻孔ID")
    private Integer longestTimeBoreholeId;

    /** 开始时间/抽采时间最长钻孔 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间/抽采时间最长钻孔", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTimeL;

    /** 结束时间/抽采时间最长钻孔 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间/抽采时间最长钻孔", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTimeL;

    /** 预抽时间最短的钻孔ID */
    @Excel(name = "预抽时间最短的钻孔ID")
    private Integer shortestTimeBoreholeId;

    /** 开始时间/抽采时间最短 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间/抽采时间最短", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTimeS;

    /** 结束时间/抽采时间最短钻孔 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间/抽采时间最短钻孔", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTimeS;

    /** 抽采时间最长的天数 */
    @Excel(name = "抽采时间最长的天数")
    private Double daysL;

    /** 抽采时间最短的天数 */
    @Excel(name = "抽采时间最短的天数")
    private Double daysS;

    /** 计算预抽时间差异系数 */
    @Excel(name = "计算预抽时间差异系数")
    private String diffCoefficient;

    /** 预抽时间差异系数标准 */
    @Excel(name = "预抽时间差异系数标准")
    private String diffCoefficientStandard;

    public TEvaluateUnitVo(TEvaluateUnit eu, HashMap<String, ArrayList<SysDictData>> dictmap){
        this.evaluateUnitName=eu.getEvaluateUnitName();
        this.wide=eu.getWide();
        this.length=eu.getLength();
        this.coalThickness=eu.getCoalThickness();
        this.coalReserves=eu.getCoalReserves();
        this.area=eu.getArea();
        this.capacity=eu.getCapacity();
        this.workfaceId=eu.getWorkfaceId();
        this.gasContentOriginal=eu.getGasContentOriginal();
        this.startConstruction=eu.getStartConstruction();
        this.endConstruction=eu.getEndConstruction();
        this.accumulatedAmount=eu.getAccumulatedAmount();
        this.remnantGasReserves=eu.getRemnantGasReserves();
        this.remnantGasContentRemnant=eu.getRemnantGasContentRemnant();
        this.completeRatio=eu.getCompleteRatio();
        this.gasTotalReserves=eu.getGasTotalReserves();
        this.longestTimeBoreholeId=eu.getLongestTimeBoreholeId();
        this.startTimeL=eu.getStartTimeL();
        this.endTimeL=eu.getEndTimeL();
        this.shortestTimeBoreholeId=eu.getShortestTimeBoreholeId();
        this.startTimeS=eu.getEndTimeS();
        this.daysL=eu.getDaysL();
        this.daysS=eu.getDaysS();
        this.diffCoefficient=eu.getDiffCoefficient();
        this.diffCoefficientStandard=eu.getDiffCoefficientStandard();
    }

}