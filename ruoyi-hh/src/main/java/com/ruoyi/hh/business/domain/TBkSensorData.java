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
 * 八矿传感器数据对象 t_bk_sensor_data
 * 
 * @author eleven
 * @date 2023-06-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBkSensorData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;
    /**
     * 煤矿名称
     */
    private String coalMineName;

    /** 测点编码 */
    @Excel(name = "测点编码")
    private String measurePointCode;

    /** 传感器类型名称 */
    @Excel(name = "传感器类型名称")
    private String sensorTypeName;

    /** 测点安装位置 */
    @Excel(name = "测点安装位置")
    private String measurePointLocal;

    /** 测点值 */
    @Excel(name = "测点值")
    private float measurePointValue;

    /** 测点数值单位 */
    @Excel(name = "测点数值单位")
    private String unit;

    /** 测点状态 */
    @Excel(name = "测点状态")
    private String status;

    /** 数据时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    private Date minTimestamp;
    /**最大值*/
    private float maxValue;
    /**平均值*/
    private float avgValue;
    /**最大值时刻*/
    private Date maxTime;

}
