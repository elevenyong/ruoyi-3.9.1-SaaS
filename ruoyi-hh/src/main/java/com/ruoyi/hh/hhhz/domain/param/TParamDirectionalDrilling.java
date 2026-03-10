package com.ruoyi.hh.hhhz.domain.param;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.physical.domain.TCoalinfo;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.physical.domain.TRoadway;
import com.ruoyi.hh.physical.domain.TWorkingFace;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 定向钻孔对象 t_param_directional_drilling
 *
 * @author hhhz
 * @date 2023-04-25
 */
public class TParamDirectionalDrilling extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Integer uid;

    /**
     * 工作面ID
     */
    @Excel(name = "工作面ID")
    private Integer workingFaceId;

    /**
     * 巷道ID
     */
    @Excel(name = "巷道ID")
    private Integer roadId;

    /**
     * 设计类型(0=短定向钻,1=高位定向钻)
     */
    @Excel(name = "设计类型(0=短定向钻,1=高位定向钻)")
    private Integer type;

    /**
     * 短定向钻:抽采半径
     */
    @Excel(name = "短定向钻:抽采半径")
    private Double radius;

    /**
     * 短定向钻:抽采控制距离小值
     */
    @Excel(name = "短定向钻:抽采控制距离小值")
    private Double controlLengthMin;

    /**
     * 短定向钻:抽采控制距离大值
     */
    @Excel(name = "短定向钻:抽采控制距离大值")
    private Double controlLengthMax;

    /**
     * 短定向钻:上帮/下帮(0=上帮,1=下帮)
     */
    @Excel(name = "短定向钻:上帮/下帮(0=上帮,1=下帮)")
    private Integer topOrBottom;

    /**
     * 短定向钻:钻场位置距离巷道起点
     */
    @Excel(name = "短定向钻:钻场位置距离巷道起点")
    private Double distanceOfRoadStart;

    /**
     * 高位定向:距煤层顶板高度
     */
    @Excel(name = "高位定向:距煤层顶板高度")
    private Double heightToTop;

    /**
     * 高位定向:与风巷水平投影间距
     */
    @Excel(name = "高位定向:与风巷水平投影间距")
    private Double distanceFromWindRoadway;

    /**
     * 高位定向:与切眼巷距离
     */
    @Excel(name = "高位定向:与切眼巷距离")
    private Double distanceFromCutRoadway;

    /**
     * 高位定向:内外错(0=内错,1=外错)
     */
    @Excel(name = "高位定向:内外错(0=内错,1=外错)")
    private Integer inOrOut;

    /**
     * 高位定向:控制距离
     */
    @Excel(name = "高位定向:控制距离")
    private Double controlLength;

    /**
     * 高位定向:目标孔段方位角
     */
    @Excel(name = "高位定向:目标孔段方位角")
    private Double endAzimuth;

    /**
     * 高位定向:目标孔段倾角
     */
    @Excel(name = "高位定向:目标孔段倾角")
    private Double endTilt;

    /**
     * 钻孔属性:定向钻杆长度
     */
    @Excel(name = "钻孔属性:定向钻杆长度")
    private Double perDrillLength;

    /**
     * 钻孔属性:测量间距
     */
    @Excel(name = "钻孔属性:测量间距")
    private Double measureingDistance;

    /**
     * 钻孔属性:最大开孔角度
     */
    @Excel(name = "钻孔属性:最大开孔角度")
    private Double openingAngleMax;

    /**
     * 钻孔属性:最大曲率
     */
    @Excel(name = "钻孔属性:最大曲率")
    private Double curvatureMax;

    /**
     * 钻孔属性:钻孔孔径
     */
    @Excel(name = "钻孔属性:钻孔孔径")
    private Double drillHoleSize;

    /**
     * 工作面信息
     */
    private TWorkingFace workingFace;
    /**
     * 巷道信息
     */
    private TRoadway roadway;
    /**
     * 煤层信息
     */
    private TCoalinfo coalinfo;

    private String sign;

    private String schemeId;

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    private List<TDesignedBorehole> tDesignedBoreholes;

    public TWorkingFace getWorkingFace() {
        return workingFace;
    }

    public void setWorkingFace(TWorkingFace workingFace) {
        this.workingFace = workingFace;
    }

    public TRoadway getRoadway() {
        return roadway;
    }

    public void setRoadway(TRoadway roadway) {
        this.roadway = roadway;
    }

    public TCoalinfo getCoalinfo() {
        return coalinfo;
    }

    public void setCoalinfo(TCoalinfo coalinfo) {
        this.coalinfo = coalinfo;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setWorkingFaceId(Integer workingFaceId) {
        this.workingFaceId = workingFaceId;
    }

    public Integer getWorkingFaceId() {
        return workingFaceId;
    }

    public void setRoadId(Integer roadId) {
        this.roadId = roadId;
    }

    public Integer getRoadId() {
        return roadId;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getRadius() {
        return radius;
    }

    public void setControlLengthMin(Double controlLengthMin) {
        this.controlLengthMin = controlLengthMin;
    }

    public Double getControlLengthMin() {
        return controlLengthMin;
    }

    public void setControlLengthMax(Double controlLengthMax) {
        this.controlLengthMax = controlLengthMax;
    }

    public Double getControlLengthMax() {
        return controlLengthMax;
    }

    public void setTopOrBottom(Integer topOrBottom) {
        this.topOrBottom = topOrBottom;
    }

    public Integer getTopOrBottom() {
        return topOrBottom;
    }

    public void setDistanceOfRoadStart(Double distanceOfRoadStart) {
        this.distanceOfRoadStart = distanceOfRoadStart;
    }

    public Double getDistanceOfRoadStart() {
        return distanceOfRoadStart;
    }

    public void setHeightToTop(Double heightToTop) {
        this.heightToTop = heightToTop;
    }

    public Double getHeightToTop() {
        return heightToTop;
    }

    public void setDistanceFromWindRoadway(Double distanceFromWindRoadway) {
        this.distanceFromWindRoadway = distanceFromWindRoadway;
    }

    public Double getDistanceFromWindRoadway() {
        return distanceFromWindRoadway;
    }

    public void setDistanceFromCutRoadway(Double distanceFromCutRoadway) {
        this.distanceFromCutRoadway = distanceFromCutRoadway;
    }

    public Double getDistanceFromCutRoadway() {
        return distanceFromCutRoadway;
    }

    public void setInOrOut(Integer inOrOut) {
        this.inOrOut = inOrOut;
    }

    public Integer getInOrOut() {
        return inOrOut;
    }

    public void setControlLength(Double controlLength) {
        this.controlLength = controlLength;
    }

    public Double getControlLength() {
        return controlLength;
    }

    public void setEndAzimuth(Double endAzimuth) {
        this.endAzimuth = endAzimuth;
    }

    public Double getEndAzimuth() {
        return endAzimuth;
    }

    public void setEndTilt(Double endTilt) {
        this.endTilt = endTilt;
    }

    public Double getEndTilt() {
        return endTilt;
    }

    public void setPerDrillLength(Double perDrillLength) {
        this.perDrillLength = perDrillLength;
    }

    public Double getPerDrillLength() {
        return perDrillLength;
    }

    public void setMeasureingDistance(Double measureingDistance) {
        this.measureingDistance = measureingDistance;
    }

    public Double getMeasureingDistance() {
        return measureingDistance;
    }

    public void setOpeningAngleMax(Double openingAngleMax) {
        this.openingAngleMax = openingAngleMax;
    }

    public Double getOpeningAngleMax() {
        return openingAngleMax;
    }

    public void setCurvatureMax(Double curvatureMax) {
        this.curvatureMax = curvatureMax;
    }

    public Double getCurvatureMax() {
        return curvatureMax;
    }

    public void setDrillHoleSize(Double drillHoleSize) {
        this.drillHoleSize = drillHoleSize;
    }

    public Double getDrillHoleSize() {
        return drillHoleSize;
    }

    public List<TDesignedBorehole> gettDesignedBoreholes() {
        return tDesignedBoreholes;
    }

    public void settDesignedBoreholes(List<TDesignedBorehole> tDesignedBoreholes) {
        this.tDesignedBoreholes = tDesignedBoreholes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("uid", getUid())
                .append("workingFaceId", getWorkingFaceId())
                .append("roadId", getRoadId())
                .append("type", getType())
                .append("radius", getRadius())
                .append("controlLengthMin", getControlLengthMin())
                .append("controlLengthMax", getControlLengthMax())
                .append("topOrBottom", getTopOrBottom())
                .append("distanceOfRoadStart", getDistanceOfRoadStart())
                .append("heightToTop", getHeightToTop())
                .append("distanceFromWindRoadway", getDistanceFromWindRoadway())
                .append("distanceFromCutRoadway", getDistanceFromCutRoadway())
                .append("inOrOut", getInOrOut())
                .append("controlLength", getControlLength())
                .append("endAzimuth", getEndAzimuth())
                .append("endTilt", getEndTilt())
                .append("perDrillLength", getPerDrillLength())
                .append("measureingDistance", getMeasureingDistance())
                .append("openingAngleMax", getOpeningAngleMax())
                .append("curvatureMax", getCurvatureMax())
                .append("drillHoleSize", getDrillHoleSize())
                .toString();
    }
}
