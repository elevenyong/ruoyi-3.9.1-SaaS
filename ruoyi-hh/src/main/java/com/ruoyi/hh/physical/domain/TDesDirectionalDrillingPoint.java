package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 设计定向钻孔点位信息对象 t_des_directional_drilling_point
 *
 * @author eleven
 * @date 2023-06-29
 */
@ApiModel(value = "DZZK设计定向钻点位", description = "DZZK设计定向钻点位")
public class TDesDirectionalDrillingPoint extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 点位ID
     */
    @ApiModelProperty("点位ID")
    private Integer id;

    /**
     * 设计钻孔ID
     */
    @Excel(name = "设计钻孔ID")
    @ApiModelProperty("设计钻孔ID")
    private Integer desBoreholeId;

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
    private Double offset;

    /**
     * 投影深度(m)
     */
    @Excel(name = "投影深度(m)")
    @ApiModelProperty("投影深度(m)")
    private Double projectionDepth;

    /**
     * 左右偏差(m)
     */
    @Excel(name = "左右偏差(m)")
    @ApiModelProperty("左右偏差(m)")
    private Double offsetLR;

    /**
     * 上下偏差(m)
     */
    @Excel(name = "上下偏差(m)")
    @ApiModelProperty("上下偏差(m)")
    private Double offsetTD;

    /**
     * 东偏(m)
     */
    @Excel(name = "东偏(m)")
    @ApiModelProperty("东偏(m)")
    private Double offsetEast;

    /**
     * 北偏(m)
     */
    @Excel(name = "北偏(m)")
    @ApiModelProperty("北偏(m)")
    private Double offsetNorth;

    /**
     * 高程(m)
     */
    @Excel(name = "高程(m)")
    @ApiModelProperty("高程(m)")
    private Double altitude;

    /**
     * 煤岩情况
     */
    @Excel(name = "煤岩情况")
    @ApiModelProperty("煤岩情况")
    private String coalRockSituation;
    @ApiModelProperty("点位序号")
    private Integer index;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setDesBoreholeId(Integer desBoreholeId) {
        this.desBoreholeId = desBoreholeId;
    }

    public Integer getDesBoreholeId() {
        return desBoreholeId;
    }

    public Double getBoreholeLength() {
        return boreholeLength;
    }

    public void setBoreholeLength(Double boreholeLength) {
        this.boreholeLength = boreholeLength;
    }

    public Double getAzimuthAngle() {
        return azimuthAngle;
    }

    public void setAzimuthAngle(Double azimuthAngle) {
        this.azimuthAngle = azimuthAngle;
    }

    public Double getInclinationAngle() {
        return inclinationAngle;
    }

    public void setInclinationAngle(Double inclinationAngle) {
        this.inclinationAngle = inclinationAngle;
    }

    public Double getOffset() {
        return offset;
    }

    public void setOffset(Double offset) {
        this.offset = offset;
    }

    public Double getProjectionDepth() {
        return projectionDepth;
    }

    public void setProjectionDepth(Double projectionDepth) {
        this.projectionDepth = projectionDepth;
    }

    public Double getOffsetLR() {
        return offsetLR;
    }

    public void setOffsetLR(Double offsetLR) {
        this.offsetLR = offsetLR;
    }

    public Double getOffsetTD() {
        return offsetTD;
    }

    public void setOffsetTD(Double offsetTD) {
        this.offsetTD = offsetTD;
    }

    public Double getOffsetEast() {
        return offsetEast;
    }

    public void setOffsetEast(Double offsetEast) {
        this.offsetEast = offsetEast;
    }

    public Double getOffsetNorth() {
        return offsetNorth;
    }

    public void setOffsetNorth(Double offsetNorth) {
        this.offsetNorth = offsetNorth;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public String getCoalRockSituation() {
        return coalRockSituation;
    }

    public void setCoalRockSituation(String coalRockSituation) {
        this.coalRockSituation = coalRockSituation;
    }

    @Override
    public String toString() {
        return "TDesDirectionalDrillingPoint{" +
                "id=" + id +
                ", desBoreholeId=" + desBoreholeId +
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
