package com.ruoyi.hh.util.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 钻孔质量分析表对象 t_borehole_quality_analy
 *
 * @author eleven
 * @date 2022-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TBoreholeQualityAnaly extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 指标 */
    @Excel(name = "指标")
    private String indicators;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 临界值 */
    @Excel(name = "临界值")
    private Double criticalValue;



}
