package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 巷道信息对象 t_roadway
 *
 * @author eleven
 * @date 2022-07-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DZ巷道信息")
public class TRoadway extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    @ApiModelProperty("巷道ID")
    private Integer id;
    /**
     * 对应工作面ID
     */
    @ApiModelProperty("对应工作面ID")
    private Integer workfaceId;

    /**
     * 巷道名称
     */
    @Excel(name = "巷道名称")
    @ApiModelProperty("巷道名称")
    private String roadway;

    /**
     * 巷道类型
     */
    @Excel(name = "巷道类型ID")
    @ApiModelProperty("巷道类型ID")
    private Integer type;

    /**
     * 巷道类型名字
     */
    @Excel(name = "巷道类型")
    @ApiModelProperty("只读：巷道类型名称")
    private String roadwayType;

    /**
     * 巷道断面形状ID（1=矩形；2=梯形；3=拱形）
     */
    @Excel(name = "巷道断面形状ID")
    @ApiModelProperty("巷道断面形状ID（1=矩形；2=梯形；3=拱形）")
    private Integer faultShapId;

    /**
     * 断层形状名称
     */
    @Excel(name = "断层形状")
    @ApiModelProperty("只读：断层形状名称")
    private String shape;

    /**
     * 巷道方位角
     */
    @Excel(name = "巷道方位角")
    @Deprecated
    private Double azimuthAngle;

    /**
     * 宽
     */
    @Excel(name = "宽")
    @ApiModelProperty("巷道宽")
    private Double wide;

    /**
     * 高
     */
    @Excel(name = "高")
    @ApiModelProperty("巷道高")
    private Double height;

    @Excel(name = "海拔高度")
    @Deprecated
    private Double elevation;

    @ApiModelProperty("左侧煤巷条带控制范围")
    private Double stripCtrlLeft;

    @ApiModelProperty("右侧煤巷条带控制范围")
    private Double stripCtrlRight;

    @Excel(name = "内外错(0=内错,1=外错)")
    @ApiModelProperty("内外错(0=内错,1=外错)")
    private Integer inOrOut;

    @Excel(name = "两巷水平投影间距")
    @ApiModelProperty("两巷水平投影间距")
    private Double inoutDistance;

    @Excel(name = "煤层底板到底抽巷间距")
    @ApiModelProperty("煤层底板到底抽巷间距")
    private Double distanceCoalRoadway;

    /**
     * 巷道起点X
     */
    @ApiModelProperty("巷道起点X")
    private Double roadOriginX;
    /**
     * 巷道起点Y
     */
    @ApiModelProperty("巷道起点Y")
    private Double roadOriginY;
    /**
     * 巷道起点Z
     */
    @ApiModelProperty("巷道起点Z")
    private Double roadOriginZ;

    /**
     * 巷道长度
     */
    @ApiModelProperty("巷道长度")
    private Double length;

    /**
     * 煤巷贴煤层顶/底板（0=底板，1=顶板）
     */
    @ApiModelProperty("煤巷贴煤层顶/底板（0=顶板，1=底板）")
    private Integer nearFloor;
    /**
     * 对应的区段信息
     */
    @ApiModelProperty(value = "停用：区段信息", hidden = true)
    private List<TRoadwayZone> troadwayZoneList;

    /**
     * 对应的导线点信息
     */
    @ApiModelProperty(value = "导线点信息",accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private List<TNavigationPoint> tNavigationPointList;

    /**
     * 参与构建普钻信息(超前探钻孔/普通穿层钻孔）
     */
    @ApiModelProperty(value = "参与构建钻孔信息(超前探钻孔/普通穿层钻孔）", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private List<TBkActBoreholeInfo> advanceExplorationDrillList;

    /**
     * 参与构建定向钻信息
     */
    @ApiModelProperty(value = "参与构建定向钻信息", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private List<TActDirectionalBorehole> usedDirectDrillList;

}
