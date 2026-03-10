package com.ruoyi.hh.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 传感器信息对象 t_sensor_info
 *
 * @author eleven
 * @date 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TSensorInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 传感器编号 */
    @Excel(name = "传感器编号")
    private String sensorCode;

    /** 传感器名称 */
    @Excel(name = "传感器名称")
    private String sensorName;

    /** 传感器类型ID */
    @Excel(name = "传感器类型ID")
    private Integer sensorTypeId;

    /** 测点信息ID */
    @Excel(name = "测点信息ID")
    private Integer measurePointId;

}
