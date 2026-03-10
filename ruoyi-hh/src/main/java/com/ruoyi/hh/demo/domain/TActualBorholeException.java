package com.ruoyi.hh.demo.domain;

import com.ruoyi.common.core.domain.BaseEntity;

public class TActualBorholeException extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * t_actual_borehole.id                        id,
     * t_actual_borehole.roadwayId                 roadwayId,
     * t_roadway.roadway                           roadWayName,
     * t_actual_borehole.groupNo                   groupNo,
     * t_actual_borehole.borehole_no               boreholeNo,
     * t_actual_borehole.navigation_pointid        navPointId,
     * t_navigation_point.navigation_point_name    navPointName,
     * t_navigation_point.X                        x,
     * t_navigation_point.Y                        y,
     * t_navigation_point.Z                        z,
     * t_actual_borehole.exception_during_drilling exceptions
     */

    private Integer id;
    private Integer roadwayId;
    private String roadWayName;
    private String groupNo;
    private Integer boreholeNo;
    private Integer navPointId;
    private String navPointName;
    private Double x;
    private Double y;
    private Double z;
    private String exceptions;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNavPointId() {
        return navPointId;
    }

    public void setNavPointId(Integer navPointId) {
        this.navPointId = navPointId;
    }

    public Integer getRoadWayId() {
        return roadwayId;
    }

    public void setRoadWayId(Integer roadWayId) {
        this.roadwayId = roadWayId;
    }

    public String getRoadWayName() {
        return roadWayName;
    }

    public void setRoadWayName(String roadWayName) {
        this.roadWayName = roadWayName;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupId(String groupNo) {
        this.groupNo = groupNo;
    }

    public Integer getBoreholeNo() {
        return boreholeNo;
    }

    public void setBoreholeNo(Integer boreholeNo) {
        this.boreholeNo = boreholeNo;
    }

    public String getNavPointName() {
        return navPointName;
    }

    public void setNavPointName(String navPointName) {
        this.navPointName = navPointName;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public String getExceptions() {
        return exceptions;
    }

    public void setExceptions(String exceptions) {
        this.exceptions = exceptions;
    }

    public TActualBorholeException() {
    }

    public TActualBorholeException(Integer id, Integer roadwayId, String roadWayName, String groupNo, Integer boreholeNo, Integer navPointId, String navPointName, Double x, Double y, Double z, String exceptions) {
        this.id = id;
        this.roadwayId = roadwayId;
        this.roadWayName = roadWayName;
        this.groupNo = groupNo;
        this.boreholeNo = boreholeNo;
        this.navPointId = navPointId;
        this.navPointName = navPointName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.exceptions = exceptions;
    }
}
