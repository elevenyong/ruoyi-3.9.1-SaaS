package com.ruoyi.hh.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 测点人工对比功能对象 t_measure_point_contrast
 *
 * @author eleven
 * @date 2022-07-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TMeasurePointContrast extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Integer id;

    /**
     * 测点名称
     */
    @Excel(name = "测点名称",width = 25)
    private String measurePointLocal;

    /**
     * 测量时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "测量时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date measureTime;

    /** 光瓦测量值 */
    @Excel(name = "光瓦测量值",width = 12)
    private Double concentration;

    /** 传感器浓度 */
    @Excel(name = "传感器浓度",width = 12)
    private Double concentrationS;

    /** 校正后显示值 */
    @Excel(name = "校正后显示值",width = 12)
    private Double concentrationRevise;

    /** 负压 */
    @Excel(name = "负压",width = 8)
    private Double vacuum;

    /** 传感器负压 */
    @Excel(name = "传感器负压",width = 10)
    private Double vacuumS;

    /** 负压校正后显示 */
    @Excel(name = "负压校正后显示",width = 15)
    private Double vacuumRevise;

    /** 混合流量 */
    @Excel(name = "混合流量",width = 10)
    private Double mixedFlow;

    /** 传感器混合流量 */
    @Excel(name = "传感器混合流量",width = 15)
    private Double mixedFlowS;

    /** 混合流量校正后显示 */
    @Excel(name = "混合流量校正后显示",width = 20)
    private Double mixedFlowRevise;

    /** 纯流量 */
    @Excel(name = "纯流量",width = 8)
    private Double pureFlow;

    /** 传感器纯流量 */
    @Excel(name = "传感器纯流量",width = 12)
    private Double pureFlowS;

    /** 纯流量校正后显示 */
    @Excel(name = "纯流量校正后显示",width = 18)
    private Double pureFlowRevise;

    /** 温度 */
    @Excel(name = "温度",width = 5)
    private Double temperature;

    /** 传感器温度 */
    @Excel(name = "传感器温度",width = 10)
    private Double temperatureS;

    /** 温度校正后显示 */
    @Excel(name = "温度校正后显示",width = 15)
    private Double temperatureRevise;

    /** 一氧化碳便携仪 */
    @Excel(name = "一氧化碳便携仪",width = 15)
    private Double COPortable;

    /** 传感器一氧化碳 */
    @Excel(name = "传感器一氧化碳",width = 15)
    private Double COS;

    /** 一氧化碳校正后显示 */
    @Excel(name = "一氧化碳校正后显示",width = 18)
    private Double CORevise;

    /** 本地气压（气压表） */
    @Excel(name = "本地气压", readConverterExp = "气=压表",width = 15)
    private Double barometerValue;

    /** 传感器气压值 */
    @Excel(name = "传感器气压值",width = 15)
    private Double pressureS;

    /** 校正后气压值 */
    @Excel(name = "校正后气压值",width = 15)
    private Double pressureRevise;

    /** 实测人 */
    @Excel(name = "实测人",width = 15)
    private String actualMeasurementPersion;

    /** 调校人 */
    @Excel(name = "调校人",width = 15)
    private String timingPersion;

}
