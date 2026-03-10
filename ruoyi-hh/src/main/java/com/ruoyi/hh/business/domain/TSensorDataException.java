package com.ruoyi.hh.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常报警对象 t_sensor_data_exception
 * 
 * @author eleven
 * @date 2023-05-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TSensorDataException extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;
    /**煤矿名称*/
    private String coalMineName;
    /**系统类别*/
    private String sysCode;

    /** 测点编码 */
    @Excel(name = "测点编码")
    private String measurePointCode;

    /** 传感器类型名称 */
    @Excel(name = "传感器类型名称")
    private String sensorTypeName;

    /** 测点安装位置 */
    @Excel(name = "测点安装位置")
    private String measurePointLocal;

    /** 测点数值单位 */
    @Excel(name = "测点数值单位")
    private String unit;

    /** 异常类型 */
    @Excel(name = "异常类型")
    private String exceptionType;

    /** 异常开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "异常开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 异常结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "异常结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 异常期间最大值 */
    @Excel(name = "异常期间最大值")
    private Double maxValue;

    /** 最大值时刻 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最大值时刻", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date maxTime;

    /** 异常期间最小值 */
    @Excel(name = "异常期间最小值")
    private Double minValue;

    /** 最小值时刻 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最小值时刻", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date minTime;

    /** 异常期间平均值 */
    @Excel(name = "异常期间平均值")
    private Double avgValue;

    /** 异常原因 */
    @Excel(name = "异常原因")
    private String reason;

    @Excel(name="持续时长（秒）")
    private Integer duration;

    /** 处理措施 */
    @Excel(name = "处理措施")
    private String measure;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date enterTime;

    /** 录入人 */
    @Excel(name = "录入人")
    private String enterPersion;

    /** 数据时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "数据时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private int count;

}
