package com.ruoyi.hh.simulate.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 达标评判断层模拟对象 st_faultinfo
 * 
 * @author eleven
 * @date 2023-01-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StFaultinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 工作面ID */
    @Excel(name = "工作面ID")
    private Integer workfaceId;

    /** 断层名称 */
    @Excel(name = "断层名称")
    private String faultName;

    /** 评价单元名称*/
    private String evaluateUnitName;

    /** 走向 */
    @Excel(name = "走向")
    private Double direction;

    /** 倾向 */
    @Excel(name = "倾向")
    private Double tendency;

    /** 倾角 */
    @Excel(name = "倾角")
    private Double dip;

    /** 断层性质 */
    @Excel(name = "断层性质")
    private String nature;

    /** 落差（m） */
    @Excel(name = "落差", readConverterExp = "m=")
    private String fail;

    /** 预计对回采面影响程度 */
    @Excel(name = "预计对回采面影响程度")
    private String influenceDegree;


}
