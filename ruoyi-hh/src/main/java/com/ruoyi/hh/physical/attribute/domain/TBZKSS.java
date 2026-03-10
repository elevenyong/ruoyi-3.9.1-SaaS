package com.ruoyi.hh.physical.attribute.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author: eleven
 * @date: 2024/11/12 10:06
 * @Description: 钻孔应力测点实时信息数据
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBZKSS {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 测点编码 */
    @Excel(name = "测点编码")
    private String measurePointCode;

    /** 传感器类型 */
    @Excel(name = "传感器类型")
    private String sensorTypeName;

    /** 传感器位置 */
    @Excel(name = "传感器位置")
    private String sensorLocal;

    /** 监测值 */
    @Excel(name = "监测值")
    private Double value;

    /** 测点状态 */
    @Excel(name = "测点状态")
    private Integer status;

    /** 数据时间 */
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @Excel(name = "数据时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private String date;
}
