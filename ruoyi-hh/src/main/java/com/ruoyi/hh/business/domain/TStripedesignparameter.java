package com.ruoyi.hh.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 条带治理钻孔设计参数接口之对象 t_stripedesignparameter
 *
 * @author eleven
 * @date 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TStripedesignparameter extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 巷道ID */
    @Excel(name = "巷道ID")
    private Integer roadwayZoneId;

    /** 煤层厚度 */
    @Excel(name = "煤层厚度")
    private Double coalThick;

    /** 地层倾角 */
    @Excel(name = "地层倾角")
    private Double dipAngle;

    /** 煤层地板到底抽巷间距 */
    @Excel(name = "煤层地板到底抽巷间距")
    private Double diskerveRoadway;

    /** 煤巷宽 */
    @Excel(name = "煤巷宽")
    private Double wide;

    /** 煤巷高 */
    @Excel(name = "煤巷高")
    private Double high;

    /** 抽采半径 */
    @Excel(name = "抽采半径")
    private Double extractionRadius;

    /** 水平投影间距 */
    @Excel(name = "水平投影间距")
    private Double disToProjection;

    /** 抽采控制距离1 */
    @Excel(name = "抽采控制距离1")
    private Double extractionControlDis1;

    /** 抽采控制距离2 */
    @Excel(name = "抽采控制距离2")
    private Double extractionControlDis2;

}
