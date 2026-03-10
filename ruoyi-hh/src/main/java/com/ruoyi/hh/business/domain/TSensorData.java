package com.ruoyi.hh.business.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 传感器数据信息对象 t_sensor_data
 * 
 * @author eleven
 * @date 2022-08-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TSensorData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String sensorCode;

    /** 传感器类型名称 */
    @Excel(name = "传感器类型名称")
    private String sensorTypeName;

    /** 测点安装位置 */
    @Excel(name = "测点安装位置")
    private String measurePointLocal;

    /** 测点数值单位 */
    @Excel(name = "测点数值单位")
    private String unitName;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 最大值 */
    @Excel(name = "最大值")
    private Double maxValue;

    /** 最大值时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最大值时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date maxTime;

    /** 最小值 */
    @Excel(name = "最小值")
    private Double minValue;

    /** 最小值时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最小值时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date minTime;

    /** 平均值 */
    @Excel(name = "平均值")
    private Double avgValue;

    /** 有效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 时间周期 */
    @Excel(name = "时间周期")
    private Double period;

}
