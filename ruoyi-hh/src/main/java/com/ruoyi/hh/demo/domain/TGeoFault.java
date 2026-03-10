package com.ruoyi.hh.demo.domain;

import com.ruoyi.hh.physical.domain.TCoalPoint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 断层对象 t_geo_fault
 *
 * @author hhhz
 * @date 2022-10-28
 */
@ApiModel("DZ地质断层")
public class TGeoFault extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("断层ID")
    private Integer id;

    /**
     * 所属工作面ID
     */
    @ApiModelProperty("所属工作面ID")
    private Integer workfaceId;

    /**
     * 断层名称
     */
    @Excel(name = "断层名称")
    @ApiModelProperty("断层名称")
    private String name;

    /**
     * 断层长度
     */
    @Excel(name = "断层长度")
    @ApiModelProperty("断层长度")
    private Double length;

    /**
     * 断层方位角
     */
    @Excel(name = "断层方位角/倾向")
    @ApiModelProperty("断层方位角/（倾向=1：顺时针，0：逆时针）")
    private Double azimuth;

    /**
     * 断层倾角
     */
    @Excel(name = "断层倾角")
    @ApiModelProperty("断层倾角")
    private Double inclination;

    /**
     * 断层落差
     */
    @Excel(name = "断层落差/断距")
    @ApiModelProperty("断层落差/断距")
    private Double faultHeight;

    /**
     * 正逆断层:0=正断层，1=逆断层
     */
    @Excel(name = "正/逆断层")
    @ApiModelProperty("正/逆断层:0=正断层，1=逆断层")
    private Integer faultType;

    /**
     * x坐标
     */
    @Excel(name = "x坐标")
    @ApiModelProperty("断层中心坐标x")
    private Double centerX;

    /**
     * y坐标
     */
    @Excel(name = "y坐标")
    @ApiModelProperty("断层中心坐标y")
    private Double centerY;


    /**
     * z坐标
     */
    @Excel(name = "z坐标")
    @ApiModelProperty("断层中心坐标z")
    private Double centerZ;

    @ApiModelProperty("断层中心线点位")
    private List<TCoalPoint> geoFaultPoints;

    @ApiModelProperty("标注断层的依据")
    private String accordingTo;


    /**
     * 影响参数
     */
    @ApiModelProperty("断层影响范围参数")
    private Double influenceScope;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAzimuth(Double azimuth) {
        this.azimuth = azimuth;
    }

    public Double getAzimuth() {
        return azimuth;
    }

    public void setInclination(Double inclination) {
        this.inclination = inclination;
    }

    public Double getInclination() {
        return inclination;
    }

    public void setCenterX(Double centerX) {
        this.centerX = centerX;
    }

    public Double getCenterX() {
        return centerX;
    }

    public void setCenterY(Double centerY) {
        this.centerY = centerY;
    }

    public Double getCenterY() {
        return centerY;
    }

    public void setCenterZ(Double centerZ) {
        this.centerZ = centerZ;
    }

    public Double getCenterZ() {
        return centerZ;
    }

    public void setFaultHeight(Double faultHeight) {
        this.faultHeight = faultHeight;
    }

    public Double getFaultHeight() {
        return faultHeight;
    }

    public void setFaultType(Integer faultType) {
        this.faultType = faultType;
    }

    public Integer getFaultType() {
        return faultType;
    }

    public List<TCoalPoint> getGeoFaultPoints() {
        return geoFaultPoints;
    }

    public void setGeoFaultPoints(List<TCoalPoint> geoFaultPoints) {
        this.geoFaultPoints = geoFaultPoints;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Integer getWorkfaceId() {
        return workfaceId;
    }

    public void setWorkfaceId(Integer workfaceId) {
        this.workfaceId = workfaceId;
    }

    public Double getInfluenceScope() {
        return influenceScope;
    }

    public void setInfluenceScope(Double influenceScope) {
        this.influenceScope = influenceScope;
    }

    public String getAccordingTo() {
        return accordingTo;
    }

    public void setAccordingTo(String accordingTo) {
        this.accordingTo = accordingTo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("azimuth", getAzimuth())
                .append("inclination", getInclination())
                .append("centerX", getCenterX())
                .append("centerY", getCenterY())
                .append("centerZ", getCenterZ())
                .append("faultHeight", getFaultHeight())
                .append("faultType", getFaultType())
                .append("accordingTo", getAccordingTo())
                .toString();
    }
}
