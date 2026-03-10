package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 设计定向钻孔信息对象 t_designed_directional_borehole
 *
 * @author eleven
 * @date 2023-07-27
 */
@ApiModel(value = "DZZK设计定向钻",description = "DZZK设计定向钻")
public class TDesignedDirectionalBorehole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @ApiModelProperty("设计定向钻ID")
    private Integer id;

    /** 巷道ID */
    @Excel(name = "巷道ID")
    @ApiModelProperty("巷道ID")
    private Integer roadwayId;

    /** 组号 */
    @Excel(name = "组号")
    @ApiModelProperty("组号")
    private String groupNo;

    /** 孔号 */
    @Excel(name = "孔号")
    @ApiModelProperty("孔号")
    private Integer boreholeNo;

    /** 钻孔编号 */
    @Excel(name = "钻孔编号")
    @ApiModelProperty("钻孔编号")
    private String boreholeCode;

    /** 导线点ID */
    @Excel(name = "导线点ID")
    @ApiModelProperty("导线点ID")
    private Integer navigationPointId;

    /**钻孔类型编码*/
    @ApiModelProperty("钻孔类型ID")
    private Integer categoryCode;
    /** 导线点ID */
    @Excel(name = "钻孔类型")
    @ApiModelProperty("只读：钻孔类型")
    private String boreholeCategory;

    /** 距导线点距离 */
    @Excel(name = "距导线点距离")
    @ApiModelProperty("距导线点距离")
    private Double distanceToNavigationPoint;

    /** 导线点方向 */
    @Excel(name = "导线点方向")
    @ApiModelProperty("导线点方向")
    private String directionToNavigation;

    /** 距巷道中线距离 */
    @Excel(name = "距巷道中线距离")
    @ApiModelProperty("距巷道中线距离")
    private Double distanceToCentrallineTunnel;

    /** 定向钻:上帮\下帮(0=上帮,1=下帮) */
    @Excel(name = "定向钻:上帮、下帮(0=上帮,1=下帮)")
    @ApiModelProperty("定向钻:上帮、下帮(0=上帮,1=下帮)")
    private Integer topOrBottom;

    /** 设计孔径 */
    @Excel(name = "设计孔径")
    @ApiModelProperty("设计孔径")
    private Double aperture;

    /** 钻杆长度 */
    @Excel(name = "钻杆长度")
    @ApiModelProperty("钻杆长度")
    private Double drillPipeLength;

    /** 目标孔段倾角 */
    @Excel(name = "目标孔段倾角")
    @ApiModelProperty("目标孔段倾角")
    private Double endAzimuth;

    /** 目标孔段方位角 */
    @Excel(name = "目标孔段方位角")
    @ApiModelProperty("目标孔段方位角")
    private Double endTilt;
    /**
     * 设计参数ID
     */
    @Excel(name="设计参数ID")
    @ApiModelProperty("设计参数ID")
    private Integer desParameterId;
    /**
     * 钻孔标识
     */
    @Excel(name="钻孔标识")
    @ApiModelProperty("钻孔标识(0=系统内部设计未应用，1=系统内部设计应用到方案，2=外部导入)")
    private Integer sign;
    /**开孔高度*/
    @Excel(name="开孔高度")
    @ApiModelProperty("开孔高度")
    private Double holeAltitude;

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Integer getNavigationPointId() {
        return navigationPointId;
    }

    public void setNavigationPointId(Integer navigationPointId) {
        this.navigationPointId = navigationPointId;
    }

    public Integer getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getBoreholeCategory() {
        return boreholeCategory;
    }

    public void setBoreholeCategory(String boreholeCategory) {
        this.boreholeCategory = boreholeCategory;
    }

    public Double getAperture() {
        return aperture;
    }

    public void setAperture(Double aperture) {
        this.aperture = aperture;
    }

    public Double getHoleAltitude() {
        return holeAltitude;
    }

    public void setHoleAltitude(Double holeAltitude) {
        this.holeAltitude = holeAltitude;
    }

    private List<TDesDirectionalDrillingPoint> tDirectionalDrillingPointList;

    public List<TDesDirectionalDrillingPoint> gettDirectionalDrillingPointList() {
        return tDirectionalDrillingPointList;
    }

    public void settDirectionalDrillingPointList(List<TDesDirectionalDrillingPoint> tDesDirectionalDrillingPointList) {
        this.tDirectionalDrillingPointList = tDesDirectionalDrillingPointList;
    }

    public Integer getDesParameterId() {
        return desParameterId;
    }

    public void setDesParameterId(Integer desParameterId) {
        this.desParameterId = desParameterId;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setRoadwayId(Integer roadwayId)
    {
        this.roadwayId = roadwayId;
    }

    public Integer getRoadwayId()
    {
        return roadwayId;
    }
    public void setGroupNo(String groupNo)
    {
        this.groupNo = groupNo;
    }

    public String getGroupNo()
    {
        return groupNo;
    }
    public void setBoreholeNo(Integer boreholeNo)
    {
        this.boreholeNo = boreholeNo;
    }

    public Integer getBoreholeNo()
    {
        return boreholeNo;
    }
    public void setBoreholeCode(String boreholeCode)
    {
        this.boreholeCode = boreholeCode;
    }

    public String getBoreholeCode()
    {
        return boreholeCode;
    }
    public void setDistanceToNavigationPoint(Double distanceToNavigationPoint)
    {
        this.distanceToNavigationPoint = distanceToNavigationPoint;
    }

    public Double getDistanceToNavigationPoint()
    {
        return distanceToNavigationPoint;
    }
    public void setDirectionToNavigation(String directionToNavigation)
    {
        this.directionToNavigation = directionToNavigation;
    }

    public String getDirectionToNavigation()
    {
        return directionToNavigation;
    }
    public void setDistanceToCentrallineTunnel(Double distanceToCentrallineTunnel)
    {
        this.distanceToCentrallineTunnel = distanceToCentrallineTunnel;
    }

    public Double getDistanceToCentrallineTunnel()
    {
        return distanceToCentrallineTunnel;
    }
    public void setTopOrBottom(Integer topOrBottom)
    {
        this.topOrBottom = topOrBottom;
    }

    public Integer getTopOrBottom()
    {
        return topOrBottom;
    }
    public void setDrillPipeLength(Double drillPipeLength)
    {
        this.drillPipeLength = drillPipeLength;
    }

    public Double getDrillPipeLength()
    {
        return drillPipeLength;
    }
    public void setEndAzimuth(Double endAzimuth)
    {
        this.endAzimuth = endAzimuth;
    }

    public Double getEndAzimuth()
    {
        return endAzimuth;
    }
    public void setEndTilt(Double endTilt)
    {
        this.endTilt = endTilt;
    }

    public Double getEndTilt()
    {
        return endTilt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("roadwayId", getRoadwayId())
            .append("groupNo", getGroupNo())
            .append("boreholeNo", getBoreholeNo())
            .append("boreholeCode", getBoreholeCode())
            .append("distanceToNavigationPoint", getDistanceToNavigationPoint())
            .append("directionToNavigation", getDirectionToNavigation())
            .append("distanceToCentrallineTunnel", getDistanceToCentrallineTunnel())
            .append("topOrBottom", getTopOrBottom())
            .append("drillPipeLength", getDrillPipeLength())
            .append("endAzimuth", getEndAzimuth())
            .append("endTilt", getEndTilt())
            .toString();
    }
}
