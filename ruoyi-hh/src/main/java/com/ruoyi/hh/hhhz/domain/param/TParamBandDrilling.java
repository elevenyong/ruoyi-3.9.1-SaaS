package com.ruoyi.hh.hhhz.domain.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.physical.domain.TCoalinfo;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.physical.domain.TRoadway;
import com.ruoyi.hh.physical.domain.TWorkingFace;

import java.util.List;

/**
 * 钻孔设计历史记录对象 t_param_band_drilling
 *
 * @author hhhz
 * @date 2023-04-24
 */
public class TParamBandDrilling extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    @JsonProperty("id")
    private Integer id;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    @JsonProperty("uid")
    private Integer uid;

    /**
     * 钻孔设计类型(0=单孔,1=条带,2=回采区)
     */
    @Excel(name = "钻孔设计类型(0=单孔,1=条带,2=回采区)")
    @JsonProperty("type")
    private Integer type;

    /**
     * 工作面ID
     */
    @Excel(name = "工作面ID")
    @JsonProperty("workingFaceId")
    private Integer workingFaceId;

    /**煤层ID*/
    @JsonProperty("coalinfoId")
    private Integer coalinfoId;
    /**
     * 巷道ID
     */
    @Excel(name = "巷道ID")
    @JsonProperty("roadId")
    private Integer roadId;
    /**
     * 抽采半径
     */
    @Excel(name = "抽采半径")
    @JsonProperty("radius")
    private Double radius;

    /**
     * 左侧控制范围
     */
    @Excel(name = "左侧控制范围")
    @JsonProperty("ls")
    private Double ls;

    /**
     * 右侧控制范围
     */
    @Excel(name = "右侧控制范围")
    @JsonProperty("lx")
    private Double lx;

    /**
     * 布孔方式
     */
    @Excel(name = "布孔方式")
    @JsonProperty("drillingType")
    private Integer drillingType;

    /**
     * 开孔中心上下偏差
     */
    @Excel(name = "开孔中心上下偏差")
    @JsonProperty("hOffset")
    private Double hOffset;

    /**
     * 开孔中心左右偏差
     */
    @Excel(name = "开孔中心左右偏差")
    @JsonProperty("wOffset")
    private Double wOffset;

    /**
     * 设计起点距离巷道起点距离
     */
    @Excel(name = "设计起点距离巷道起点距离")
    @JsonProperty("originPositionDis")
    private Double originPositionDis;

    /**
     * 条带设计长度
     */
    @Excel(name = "条带设计长度")
    @JsonProperty("designLength")
    private Double designLength;
    /**
     * 钻孔最大长度
     */
    @Excel(name="钻孔最大长度")
    @JsonProperty("maxLength")
    private Double maxLength;
    /**
     * 钻孔最小倾角
     */
    @Excel(name="钻孔最小倾角")
    @JsonProperty("minDipAngle")
    private Double minDipAngle;
    /**
     * 回采设计
     */
    @Excel(name="回采设计")
    @JsonProperty("miningDesign")
    private String miningDesign;

    private List<TDesignedBorehole> tDesignedBoreholes;
    /**
     * 工作面信息
     */
    @JsonProperty("workingFace")
    private TWorkingFace workingFace;
    /**
     * 巷道信息
     */
    @JsonProperty("roadway")
    private TRoadway roadway;
    /**
     * 煤层信息
     */
    @JsonProperty("coalinfo")
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

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setWorkingFaceId(Integer workingFaceId) {
        this.workingFaceId = workingFaceId;
    }

    public Integer getWorkingFaceId() {
        return workingFaceId;
    }

    public Integer getCoalinfoId() {
        return coalinfoId;
    }

    public void setCoalinfoId(Integer coalinfoId) {
        this.coalinfoId = coalinfoId;
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

    public void setLs(Double ls) {
        this.ls = ls;
    }

    public Double getLs() {
        return ls;
    }

    public void setLx(Double lx) {
        this.lx = lx;
    }

    public Double getLx() {
        return lx;
    }

    public void setDrillingType(Integer drillingType) {
        this.drillingType = drillingType;
    }

    public Integer getDrillingType() {
        return drillingType;
    }

    public void sethOffset(Double hOffset) {
        this.hOffset = hOffset;
    }

    public Double gethOffset() {
        return hOffset;
    }

    public void setwOffset(Double wOffset) {
        this.wOffset = wOffset;
    }

    public Double getwOffset() {
        return wOffset;
    }

    public void setOriginPositionDis(Double originPositionDis) {
        this.originPositionDis = originPositionDis;
    }

    public Double getOriginPositionDis() {
        return originPositionDis;
    }

    public void setDesignLength(Double designLength) {
        this.designLength = designLength;
    }

    public Double getDesignLength() {
        return designLength;
    }

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

    public Double getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Double maxLength) {
        this.maxLength = maxLength;
    }

    public Double getMinDipAngle() {
        return minDipAngle;
    }

    public void setMinDipAngle(Double minDipAngle) {
        this.minDipAngle = minDipAngle;
    }

    public String getMiningDesign() {
        return miningDesign;
    }

    public void setMiningDesign(String miningDesign) {
        this.miningDesign = miningDesign;
    }

    public List<TDesignedBorehole> gettDesignedBoreholes() {
        return tDesignedBoreholes;
    }

    public void settDesignedBoreholes(List<TDesignedBorehole> tDesignedBoreholes) {
        this.tDesignedBoreholes = tDesignedBoreholes;
    }

    @Override
    public String toString() {
        return "TParamBandDrilling{" +
                "id=" + id +
                ", uid=" + uid +
                ", type=" + type +
                ", workingFaceId=" + workingFaceId +
                ", coalinfoId" + coalinfoId +
                ", roadId=" + roadId +
                ", radius=" + radius +
                ", ls=" + ls +
                ", lx=" + lx +
                ", drillingType=" + drillingType +
                ", hOffset=" + hOffset +
                ", wOffset=" + wOffset +
                ", originPositionDis=" + originPositionDis +
                ", designLength=" + designLength +
                ", maxLength=" + maxLength +
                ", minDipAngle=" + minDipAngle +
                ", miningDesign='" + miningDesign + '\'' +
                ", workingFace=" + workingFace +
                ", roadway=" + roadway +
                ", coalinfo=" + coalinfo +
                '}';
    }
}
