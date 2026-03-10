package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 实际定向钻孔点位列对象 t_act_directional_drilling_point
 *
 * @author eleven
 * @date 2023-06-29
 */
@ApiModel(value = "DZZK实钻定向钻点位")
@Getter
@Setter
public class TActDirectionalDrillingPoint extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 点位ID
     */
    @ApiModelProperty("点位ID")
    private Integer id;

    /**
     * 设计钻孔ID
     */
    @Excel(name = "实钻钻孔ID")
    @ApiModelProperty("实钻钻孔ID")
    private Integer boreholeId;

    /**
     * 孔深（m）
     */
    @Excel(name = "孔深", readConverterExp = "m=")
    @ApiModelProperty("孔深")
    private Double boreholeLength;

    /**
     * 方位角
     */
    @Excel(name = "方位角")
    @ApiModelProperty("方位角")
    private Double azimuthAngle;

    /**
     * 倾角
     */
    @Excel(name = "倾角")
    @ApiModelProperty("倾角")
    private Double inclinationAngle;

    /**
     * 偏差
     */
    @Excel(name = "偏差")
    @ApiModelProperty("偏差")
    @JsonIgnore
    private Double offset;

    /**
     * 投影深度(m)
     */
    @Excel(name = "投影深度(m)")
    @ApiModelProperty("投影深度(m)")
    @JsonIgnore
    private Double projectionDepth;

    /**
     * 左右偏差(m)
     */
    @Excel(name = "左右偏差(m)")
    @ApiModelProperty("左右偏差(m)")
    @JsonIgnore
    private Double offsetLR;

    /**
     * 上下偏差(m)
     */
    @Excel(name = "上下偏差(m)")
    @ApiModelProperty("上下偏差(m)")
    @JsonIgnore
    private Double offsetTD;

    /**
     * 东偏(m)
     */
    @Excel(name = "东偏(m)")
    @ApiModelProperty("东偏(m)")
    @JsonIgnore
    private Double offsetEast;

    /**
     * 北偏(m)
     */
    @Excel(name = "北偏(m)")
    @ApiModelProperty("北偏(m)")
    @JsonIgnore
    private Double offsetNorth;

    /**
     * 高程(m)
     */
    @Excel(name = "高程(m)")
    @ApiModelProperty("高程(m)")
    @JsonIgnore
    private Double altitude;

    /**
     * 煤岩情况
     */
    @Excel(name = "煤岩情况")
    @ApiModelProperty("煤岩情况")
    private String coalRockSituation;
    @ApiModelProperty("孔段序号")
    private Integer index;

    //↓↓↓↓↓钻孔测斜参数↓↓↓↓↓
    /**
     * 加速度
     */
    @ApiModelProperty("加速度")
    @JsonIgnore
    private String addSpeed;
    /**
     * 时间
     */
    @ApiModelProperty("时间")
    @JsonIgnore
    private String timer;
    /**
     * 温度
     */
    @ApiModelProperty("温度")
    @JsonIgnore
    private String temp;
    /**
     * 总磁场
     */
    @ApiModelProperty("总磁场")
    @JsonIgnore
    private String cichang;
    /**
     * 地磁倾角
     */
    @ApiModelProperty("地磁倾角")
    @JsonIgnore
    private String magneticDip;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    //↑↑↑↑↑钻孔测斜参数↑↑↑↑↑
    @Override
    public String toString() {
        return "TActDirectionalDrillingPoint{" +
                "id=" + id +
                ", boreholeId=" + boreholeId +
                ", boreholeLength=" + boreholeLength +
                ", azimuthAngle=" + azimuthAngle +
                ", inclinationAngle=" + inclinationAngle +
                ", offset=" + offset +
                ", projectionDepth=" + projectionDepth +
                ", offsetLR=" + offsetLR +
                ", offsetTD=" + offsetTD +
                ", offsetEast=" + offsetEast +
                ", offsetNorth=" + offsetNorth +
                ", altitude=" + altitude +
                ", coalRockSituation='" + coalRockSituation + '\'' +
                ", index=" + index +
                '}';
    }
}
