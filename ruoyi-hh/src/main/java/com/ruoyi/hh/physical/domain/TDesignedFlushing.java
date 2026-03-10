package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 设计冲孔信息对象 t_designed_flushing
 * 
 * @author eleven
 * @date 2022-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TDesignedFlushing extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;
    @Excel(name="巷道名称")
    private String roadway;
    @Excel(name="组号")
    private String groupNo;
    @Excel(name="孔号")
    private Integer boreholeNo;

    /** 孔号 */
    @Excel(name = "孔号")
    private Integer boreholeId;

    /** 设计冲孔长 */
    @Excel(name = "设计冲孔长")
    private Long designedLengthFlushing;

    /** 冲孔段1 */
    @Excel(name = "冲孔段1")
    private String designedSectionFlushing1;

    /** 冲孔段2 */
    @Excel(name = "冲孔段2")
    private String designedSectionFlushing2;

    /** 设计冲煤量（m³） */
    @Excel(name = "设计冲煤量")
    private Double designedCoalAmountFlush;

}
