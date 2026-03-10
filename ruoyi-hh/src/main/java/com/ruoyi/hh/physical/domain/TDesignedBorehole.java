package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 设计钻孔对象 t_borehole
 *
 * @author eleven
 * @date 2022-07-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@ApiModel(value = "DZZK设计普钻信息", description = "DZZK设计普钻信息")
public class TDesignedBorehole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 钻孔ID */
    @ApiModelProperty("设计普钻钻孔ID")
    private Integer id;

    /** 所属巷道ID */
    @Excel(name = "所属巷道ID")
    @ApiModelProperty("所属巷道ID")
    private Integer roadwayId;
    /**
     * 所属巷道名称
     */
    @Excel(name = "巷道名称")
    @ApiModelProperty("只读：所属巷道名称")
    private String roadway;

    /** 组号 */
    @Excel(name = "组号")
    @ApiModelProperty("组号")
    private String groupNo;

    /** 孔号 */
    @Excel(name = "孔号")
    @ApiModelProperty("孔号")
    private String boreholeNo;

    /** 钻孔编号 */
    /*@Excel(name = "钻孔编号")
    @ApiModelProperty("钻孔编号")
    private String boreholeCode;*/



    /** 所属区段ID */
    @Excel(name = "所属区段ID")
    @Deprecated
    private Integer roadwayZoneId;
    @Excel(name="区段名称")
    @Deprecated
    private String zoneName;

    /** 钻场ID */
    @Excel(name = "钻场ID")
    @ApiModelProperty("钻场ID")
    private Integer boreholeSiteId;

    /** 钻孔类别编码 */
    @Excel(name = "钻孔类别编码")
    @ApiModelProperty("钻孔类别编码")
    private Integer categoryCode;
    @Excel(name = "钻孔类型名称")
    @ApiModelProperty("只读：钻孔类别名称")
    private String boreholeCategory;

    /** 钻孔用途ID */
    @Excel(name = "钻孔用途ID")
    @ApiModelProperty("钻孔用途ID")
    private Integer purposeBoreholeId;
    @Excel(name = "钻孔用途")
    @ApiModelProperty("只读：钻孔用途")
    private String purposeBorehole;

    /** 关联测点ID */
    @Excel(name = "关联测点ID")
    @ApiModelProperty("关联测点ID")
    private Integer measurePointId;
    @Excel(name="关联测点名称")
    @ApiModelProperty("只读：关联测点名称")
    private String measurePointName;

    /** 钻孔设计参数ID */
    @Excel(name = "钻孔设计参数ID")
    @ApiModelProperty("钻孔设计参数ID")
    private Integer desParameterId;

    /** 设计方位角 */
    @Excel(name = "设计方位角")
    @ApiModelProperty("设计方位角")
    private Double azimuthAngle;

    /** 设计夹角(水平角) */
    @Excel(name = "设计夹角(水平角)")
    @ApiModelProperty("设计夹角(水平角)")
    private Double angle;

    /** 设计倾角 */
    @Excel(name = "设计倾角")
    @ApiModelProperty("设计倾角")
    private Double inclinationAngle;

    /** 设计孔径 */
    @Excel(name = "设计孔径")
    @ApiModelProperty("设计孔径(mm)")
    private Double aperture;

    /** 第一岩起 */
    @Deprecated
    @Excel(name = "第一岩起")
    @ApiModelProperty("弃用：第一岩起")
    private Double depth1stRockSeamStart;

    /** 第一岩止 */
    @Deprecated
    @Excel(name = "第一岩止")
    @ApiModelProperty("弃用：第一岩止")
    private Double depth1stRockSeamEnd;

    /** 第一煤起 */
    @Excel(name = "第一煤起")
    @ApiModelProperty("第一煤起")
    private Double depth1stCoalSeamStart;

    /** 第一煤止 */
    @Excel(name = "第一煤止")
    @ApiModelProperty("第一煤止")
    private Double depth1stCoalSeamEnd;

    /** 第二岩起 */
    @Deprecated
    @Excel(name = "第二岩起")
    @ApiModelProperty("弃用：第二岩起")
    private Double depth2stRockSeamStart;

    /** 第二岩止 */
    @Deprecated
    @Excel(name = "第二岩止")
    @ApiModelProperty("弃用：第二岩止")
    private Double depth2stRockSeamEnd;

    /** 第二煤起 */
    @Excel(name = "第二煤起")
    @ApiModelProperty("第二煤起")
    private Double depth2stCoalSeamStart;

    /** 第二煤止 */
    @Excel(name = "第二煤止")
    @ApiModelProperty("第二煤止")
    private Double depth2stCoalSeamEnd;

    /** 第三岩起 */
    @Deprecated
    @Excel(name = "第三岩起")
    @ApiModelProperty("弃用：第三岩起")
    private Double depth3stRockSeamStart;

    /** 第三岩起 */
    @Deprecated
    @Excel(name = "第三岩起")
    @ApiModelProperty("弃用：第三岩起")
    private Double depth3stRockSeamEnd;

    /** 第三煤起 */
    @Excel(name = "第三煤起")
    @ApiModelProperty("第三煤起")
    private Double depth3stCoalSeamStart;

    /** 第三煤止 */
    @Excel(name = "第三煤止")
    @ApiModelProperty("第三煤止")
    private Double depth3stCoalSeamEnd;

    /** 第四岩起 */
    @Excel(name = "第四岩起")
    @ApiModelProperty("第四岩起")
    private Double depth4stRockSeamStart;

    /** 第四岩止 */
    @Excel(name = "第四岩止")
    @ApiModelProperty("第四岩止")
    private Double depth4stRockSeamEnd;


    /** 设计孔深 */
    @Excel(name = "设计孔深")
    @ApiModelProperty("设计孔深")
    private Double boreholeLength;

    /** 导线点ID */
    @Excel(name = "导线点ID")
    @ApiModelProperty("导线点ID")
    private Integer navigationPointId;

    /** 距导线点距离 */
    @Excel(name = "距导线点距离")
    @ApiModelProperty("距导线点距离")
    private Double distanceToNavigationPoint;
    /** 导线点方向 */
    @Excel(name = "导线点方向")
    @ApiModelProperty("导线点方向")
    private String directionToNavigation;

    /** 抽采半径 */
    @Excel(name = "抽采半径")
    @ApiModelProperty("抽采半径")
    private Double extractionRadius;

    /** 距巷道中线距离 */
    @Excel(name = "距巷道中线距离")
    @ApiModelProperty("距巷道中线距离")
    private Double distanceToCentrallineTunnel;

    /** 邻近孔号(组号+孔号集合) */
    @Excel(name = "邻近孔号(组号+孔号集合)")
    @ApiModelProperty("邻近孔号(组号+孔号集合)")
    private String adjacentBoreholeNo;

    /** 与邻近孔距离 */
    @Excel(name = "与邻近孔距离")
    @ApiModelProperty("与邻近孔距离")
    private Double distanceAdjacentBorehole;

    /** 钻孔标识 */
    @Excel(name = "钻孔标识")
    @ApiModelProperty("钻孔标识(0=系统内部设计未应用，1=系统内部设计应用到方案，2=外部导入)")
    private Integer sign;

    /** 设计开孔高度 */
    @Excel(name = "设计开孔高度")
    @ApiModelProperty("设计开孔高度")
    private Double holeAltitude;

    /** 设计列间距 */
    @Excel(name = "设计列间距")
    @ApiModelProperty("设计列间距")
    private Double lineSpace;

    /** 设计孔间距 */
    @Excel(name = "设计孔间距")
    @ApiModelProperty("设计孔间距")
    private Double holeSpace;

}
