package com.ruoyi.hh.util.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 测点类型之对象 t_measuring_type
 *
 * @author eleven
 * @date 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TMeasuringType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 测点类型 */
    @Excel(name = "测点类型")
    private String measuringType;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

}
