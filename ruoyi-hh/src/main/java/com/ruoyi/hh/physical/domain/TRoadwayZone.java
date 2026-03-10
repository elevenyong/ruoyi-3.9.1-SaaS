package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 区段信息对象 t_roadway_zone
 *
 * @author eleven
 * @date 2022-07-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class TRoadwayZone extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 所属巷道ID */
//    @Excel(name = "所属巷道ID")
    private Integer roadwayId;

    @Excel(name="巷道名称")
    private String roadway;

    /** 区段名称 */
    @Excel(name = "区段名称")
    private String zoneName;

    /** X坐标 */
    @Excel(name = "X坐标")
    private Double X;

    /** Y坐标 */
    @Excel(name = "Y坐标")
    private Double Y;

    /** Z坐标 */
    @Excel(name = "Z坐标")
    private Double Z;

    /** 长度 */
    @Excel(name = "长度")
    private Double length;

    /** 终点 */
    @Excel(name = "终点标高")
    private Double zoneFinalElevation;
    /** 方位角 */
    @Excel(name = "方位角")
    private Double azimuthAngle;

    /** 倾角 */
    @Excel(name = "倾角")
    private Double inclinationAngle;

//    @Excel(name = "操作")
//    private String action;

}
