package com.ruoyi.hh.hhhz.domain.param;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.physical.domain.TCoalinfo;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.physical.domain.TRoadway;
import com.ruoyi.hh.physical.domain.TWorkingFace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 高位钻孔对象 t_param_high_level_drilling
 *
 * @author hhhz
 * @date 2023-04-24
 */
public class TParamHighLevelDrilling extends BaseEntity {
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
     * 抽采半径
     */
    @Excel(name = "抽采半径")
    private Double radius;

    /**
     * 钻孔终孔距煤层顶板高度
     */
    @Excel(name = "钻孔终孔距煤层顶板高度")
    private Double heightToTop;

    /**
     * 距巷道水平间距
     */
    @Excel(name = "距巷道水平间距")
    private Double distanceFromRoadway;

    /**
     * 钻场间隔
     */
    @Excel(name = "钻场间隔")
    private Double drillSiteSpacing;

    /**
     * 压茬长度
     */
    @Excel(name = "压茬长度")
    private Double overlapLength;

    /**
     * 终孔点间距
     */
    @Excel(name = "终孔点间距")
    private Double finalholePositionSpacing;

    /**
     * 每个钻场钻孔个数
     */
    @Excel(name = "每个钻场钻孔个数")
    private Integer holesNumberPerDrillSite;

    /**
     * 钻孔孔径
     */
    @Excel(name = "钻孔孔径")
    private Double holeSize;

    /**
     * 钻场:钻场宽
     */
    @Excel(name = "钻场:钻场宽")
    private Double drillSiteWidth;

    /**
     * 钻场:钻场长
     */
    @Excel(name = "钻场:钻场长")
    private Double drillSiteLength;

    /**
     * 钻场:钻场高
     */
    @Excel(name = "钻场:钻场高")
    private Double drillSiteHeight;

    /**
     * 钻场:内外错
     */
    @Excel(name = "钻场:内外错")
    private Integer inOrOut;

    /**
     * 钻场:内外错距离
     */
    @Excel(name = "钻场:内外错距离")
    private Double inoutDistance;

    /**
     * 巷道内:开孔高度
     */
    @Excel(name = "巷道内:开孔高度")
    private Double openingHeight;

    /**
     * 巷道内:开孔间距
     */
    @Excel(name = "巷道内:开孔间距")
    private Double openingDistance;

    /**
     * 施钻点(0=钻场,1=巷道内)
     */
    @Excel(name = "施钻点(0=钻场,1=巷道内)")
    private Integer drillSpace;

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getWorkingFaceId() {
        return workingFaceId;
    }

    public void setWorkingFaceId(Integer workingFaceId) {
        this.workingFaceId = workingFaceId;
    }

    public Integer getRoadId() {
        return roadId;
    }

    public void setRoadId(Integer roadId) {
        this.roadId = roadId;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getHeightToTop() {
        return heightToTop;
    }

    public void setHeightToTop(Double heightToTop) {
        this.heightToTop = heightToTop;
    }

    public Double getDistanceFromRoadway() {
        return distanceFromRoadway;
    }

    public void setDistanceFromRoadway(Double distanceFromRoadway) {
        this.distanceFromRoadway = distanceFromRoadway;
    }

    public Double getDrillSiteSpacing() {
        return drillSiteSpacing;
    }

    public void setDrillSiteSpacing(Double drillSiteSpacing) {
        this.drillSiteSpacing = drillSiteSpacing;
    }

    public Double getOverlapLength() {
        return overlapLength;
    }

    public void setOverlapLength(Double overlapLength) {
        this.overlapLength = overlapLength;
    }

    public Double getFinalholePositionSpacing() {
        return finalholePositionSpacing;
    }

    public void setFinalholePositionSpacing(Double finalholePositionSpacing) {
        this.finalholePositionSpacing = finalholePositionSpacing;
    }

    public Integer getHolesNumberPerDrillSite() {
        return holesNumberPerDrillSite;
    }

    public void setHolesNumberPerDrillSite(Integer holesNumberPerDrillSite) {
        this.holesNumberPerDrillSite = holesNumberPerDrillSite;
    }

    public Double getHoleSize() {
        return holeSize;
    }

    public void setHoleSize(Double holeSize) {
        this.holeSize = holeSize;
    }

    public Double getDrillSiteWidth() {
        return drillSiteWidth;
    }

    public void setDrillSiteWidth(Double drillSiteWidth) {
        this.drillSiteWidth = drillSiteWidth;
    }

    public Double getDrillSiteLength() {
        return drillSiteLength;
    }

    public void setDrillSiteLength(Double drillSiteLength) {
        this.drillSiteLength = drillSiteLength;
    }

    public Double getDrillSiteHeight() {
        return drillSiteHeight;
    }

    public void setDrillSiteHeight(Double drillSiteHeight) {
        this.drillSiteHeight = drillSiteHeight;
    }

    public Integer getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(Integer inOrOut) {
        this.inOrOut = inOrOut;
    }

    public Double getInoutDistance() {
        return inoutDistance;
    }

    public void setInoutDistance(Double inoutDistance) {
        this.inoutDistance = inoutDistance;
    }

    public Double getOpeningHeight() {
        return openingHeight;
    }

    public void setOpeningHeight(Double openingHeight) {
        this.openingHeight = openingHeight;
    }

    public Double getOpeningDistance() {
        return openingDistance;
    }

    public void setOpeningDistance(Double openingDistance) {
        this.openingDistance = openingDistance;
    }

    public Integer getDrillSpace() {
        return drillSpace;
    }

    public void setDrillSpace(Integer drillSpace) {
        this.drillSpace = drillSpace;
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
                .append("heightToTop", getHeightToTop())
                .append("distanceFromRoadway", getDistanceFromRoadway())
                .append("drillSiteSpacing", getDrillSiteSpacing())
                .append("overlapLength", getOverlapLength())
                .append("finalholePositionSpacing", getFinalholePositionSpacing())
                .append("holesNumberPerDrillSite", getHolesNumberPerDrillSite())
                .append("holeSize", getHoleSize())
                .append("drillSiteWidth", getDrillSiteWidth())
                .append("drillSiteLength", getDrillSiteLength())
                .append("drillSiteHeight", getDrillSiteHeight())
                .append("inOrOut", getInOrOut())
                .append("inoutDistance", getInoutDistance())
                .append("openingHeight", getOpeningHeight())
                .append("openingDistance", getOpeningDistance())
                .append("drillSpace", getDrillSpace())
                .toString();
    }
}
