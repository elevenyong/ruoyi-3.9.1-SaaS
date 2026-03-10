package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 导线点相关对象 t_navigation_point
 *
 * @author eleven
 * @date 2022-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DZ导线点数据")
public class TNavigationPoint extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("导线点ID")
    private Integer id;

    /**
     * 导线点名称
     */
    @Excel(name = "导线点名称")
    @ApiModelProperty("导线点名称")
    private String navigationPointName;

    @Excel(name = "所属巷道名称")
    @ApiModelProperty("只读：所属巷道名称")
    private String roadway;

    /**
     * 所属巷道ID
     */
    @Excel(name = "所属巷道ID")
    @ApiModelProperty("所属巷道ID")
    private Integer roadwayId;

    /**
     * X坐标
     */
    @Excel(name = "X坐标")
    @ApiModelProperty("X坐标")
    private Double x;

    /**
     * Y坐标
     */
    @Excel(name = "Y坐标")
    @ApiModelProperty("Y坐标")
    private Double y;

    /**
     * Z坐标
     */
    @Excel(name = "Z坐标")
    @ApiModelProperty("Z坐标")
    private Double z;

    /**
     * 序号
     */
    @Excel(name = "序号")
    @ApiModelProperty("序号")
    private Integer serialNumber;

    /**
     * 巷道终点标识
     */
    @Excel(name = "巷道终点标识")
    @ApiModelProperty("巷道终点标识（1=终点；0=非终点）")
    private Integer isEndPoint;

    @ApiModelProperty("虚拟/实际导线点（0=虚拟点[巷道未掘]，1=实际测量[巷道已掘]）")
    private Integer isActualPoint;
}
