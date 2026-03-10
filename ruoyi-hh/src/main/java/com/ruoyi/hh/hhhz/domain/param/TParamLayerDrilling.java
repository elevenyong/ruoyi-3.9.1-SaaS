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
 * 顺层钻孔参数对象 t_param_layer_drilling
 *
 * @author hhhz
 * @date 2023-04-24
 */
public class TParamLayerDrilling extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * id
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
     * 类型(0=条带顺层,1=掘进头顺层)
     */
    @Excel(name = "类型(0=条带顺层,1=掘进头顺层)")
    private Integer type;

    /**
     * 抽采半径
     */
    @Excel(name = "抽采半径")
    private Double radius;

    /**
     * 条带顺层:抽采控制距离
     */
    @Excel(name = "条带顺层:抽采控制距离")
    private Double controlLength;

    /**
     * 条带顺层:钻孔孔径
     */
    @Excel(name = "条带顺层:钻孔孔径")
    private Double holeSize;

    /**
     * 距离巷道起点距离
     */
    @Excel(name = "距离巷道起点距离")
    private Double distanceOfRoadStart;

    /**
     * 设计长度
     */
    @Excel(name = "设计长度")
    private Double designLength;

    /**
     * 条带顺层:开孔高度
     */
    @Excel(name = "条带顺层:开孔高度")
    private Double openingHeight;

    /**
     * 掘进头顺层:控制距离内
     */
    @Excel(name = "掘进头顺层:控制距离内")
    private Double controlLengthIn;

    /**
     * 掘进头顺层:控制距离外
     */
    @Excel(name = "掘进头顺层:控制距离外")
    private Double controlLengthOut;

    /**
     * 掘进头顺层:掘进长度
     */
    @Excel(name = "掘进头顺层:掘进长度")
    private Double tuningLength;

    /**
     * 掘进头顺层:距离
     */
    @Excel(name = "掘进头顺层:距离")
    private Double distance;

    /**
     * 煤层信息ID
     */
    private Integer coalinfoId;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getControlLengthIn() {
        return controlLengthIn;
    }

    public void setControlLengthIn(Double controlLengthIn) {
        this.controlLengthIn = controlLengthIn;
    }

    public Double getControlLengthOut() {
        return controlLengthOut;
    }

    public void setControlLengthOut(Double controlLengthOut) {
        this.controlLengthOut = controlLengthOut;
    }

    public Double getTuningLength() {
        return tuningLength;
    }

    public void setTuningLength(Double tuningLength) {
        this.tuningLength = tuningLength;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
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

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getRadius() {
        return radius;
    }

    public void setControlLength(Double controlLength) {
        this.controlLength = controlLength;
    }

    public Double getControlLength() {
        return controlLength;
    }

    public void setHoleSize(Double holeSize) {
        this.holeSize = holeSize;
    }

    public Double getHoleSize() {
        return holeSize;
    }

    public void setDistanceOfRoadStart(Double distanceOfRoadStart) {
        this.distanceOfRoadStart = distanceOfRoadStart;
    }

    public Double getDistanceOfRoadStart() {
        return distanceOfRoadStart;
    }

    public void setDesignLength(Double designLength) {
        this.designLength = designLength;
    }

    public Double getDesignLength() {
        return designLength;
    }

    public void setOpeningHeight(Double openingHeight) {
        this.openingHeight = openingHeight;
    }

    public Double getOpeningHeight() {
        return openingHeight;
    }

    public Integer getCoalinfoId() {
        return coalinfoId;
    }

    public void setCoalinfoId(Integer coalinfoId) {
        this.coalinfoId = coalinfoId;
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
                .append("radius", getRadius())
                .append("controlLength", getControlLength())
                .append("holeSize", getHoleSize())
                .append("distanceOfRoadStart", getDistanceOfRoadStart())
                .append("designLength", getDesignLength())
                .append("openingHeight", getOpeningHeight())
                .append("coalinfoId",getCoalinfoId())
                .toString();
    }
}
