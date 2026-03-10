package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import java.util.List;

@ApiModel("DZ地质陷落柱")
public class TGeoFallenColumn extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("陷落柱ID")
    private Integer id;

    @ApiModelProperty("所属工作面ID")
    private Integer workfaceId;

    @ApiModelProperty("陷落柱名称")
    private String name;

    /**
     * x坐标
     */
    @ApiModelProperty("陷落柱中心坐标x")
    private Double centerX;

    /**
     * y坐标
     */
    @ApiModelProperty("陷落柱中心坐标y")
    private Double centerY;

    /**
     * z坐标
     */
    @ApiModelProperty("陷落柱中心坐标z")
    private Double centerZ;

    @ApiModelProperty("陷落柱描述")
    private String fallenDescribe;

    @ApiModelProperty("陷落柱点位信息")
    private List<TCoalPoint> GeoFallenColumnPoints;

    public List<TCoalPoint> getGeoFallenColumnPoints() {
        return GeoFallenColumnPoints;
    }

    public void setGeoFallenColumnPoints(List<TCoalPoint> geoFallenColumnPoints) {
        GeoFallenColumnPoints = geoFallenColumnPoints;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWorkfaceId() {
        return workfaceId;
    }

    public void setWorkfaceId(Integer workfaceId) {
        this.workfaceId = workfaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCenterX() {
        return centerX;
    }

    public void setCenterX(Double centerX) {
        this.centerX = centerX;
    }

    public Double getCenterY() {
        return centerY;
    }

    public void setCenterY(Double centerY) {
        this.centerY = centerY;
    }

    public Double getCenterZ() {
        return centerZ;
    }

    public void setCenterZ(Double centerZ) {
        this.centerZ = centerZ;
    }

    public String getFallenDescribe() {
        return fallenDescribe;
    }

    public void setFallenDescribe(String fallenDescribe) {
        this.fallenDescribe = fallenDescribe;
    }
}
