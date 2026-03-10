package com.ruoyi.hh.hhhz.domain;

/**
 * 常规钻孔
 */
public class NormalDrillingData {

    /**
     * 组号
     */
    private double groupId;
    /**
     * 孔号
     */
    private double drillId;
    /**
     * 方位角
     */
    private double azimuth;
    /**
     * 倾角
     */
    private double tiltAngle;
    /**
     * 见煤长度
     */
    private double distanceSeeCoal;
    /**
     * 穿煤长度
     */
    private double distanceThroughCoal;
    /**
     * 过煤长度
     */
    private double distanceAfterCoal;
    /**
     * 总长度
     */
    private double distanceTotal;
    /**
     * 开孔高度
     */
    private double openingHeight;

    /**
     * 开孔位置
     */
    private Point openingPosition;
    /**
     * 见煤位置
     */
    private Point seeCoalPosition;
    /**
     * 过煤位置
     */
    private Point passedCoalPosition;


    public NormalDrillingData() {
    }

    public NormalDrillingData(double groupId, double drillId, double azimuth, double tiltAngle, double distanceSeeCoal, double distanceThroughCoal, double distanceAfterCoal, double distanceTotal, double openingHeight) {
        this.groupId = groupId;
        this.drillId = drillId;
        this.azimuth = azimuth;
        this.tiltAngle = tiltAngle;
        this.distanceSeeCoal = distanceSeeCoal;
        this.distanceThroughCoal = distanceThroughCoal;
        this.distanceAfterCoal = distanceAfterCoal;
        this.distanceTotal = distanceTotal;
        this.openingHeight = openingHeight;
    }

    public NormalDrillingData(double groupId, double drillId, double azimuth, double tiltAngle, double distanceSeeCoal, double distanceThroughCoal, double distanceAfterCoal, double distanceTotal, double openingHeight, Point openingPosition, Point seeCoalPosition, Point passedCoalPosition) {
        this(groupId, drillId, azimuth, tiltAngle, distanceSeeCoal, distanceThroughCoal, distanceAfterCoal, distanceTotal, openingHeight);
        this.openingPosition = openingPosition;
        this.seeCoalPosition = seeCoalPosition;
        this.passedCoalPosition = passedCoalPosition;
    }

    @Override
    public String toString() {
        return "NormalDrillingData{" + "组号=" + groupId + ", 孔号=" + drillId + ", 方位角=" + azimuth + ", 倾角=" + tiltAngle + ", 开孔高度=" + openingHeight + ", 见煤深度=" + distanceSeeCoal + ", 穿煤深度=" + distanceThroughCoal + ", 过煤深度=" + distanceAfterCoal + ", 钻孔深度=" + distanceTotal + ", 开孔位置=" + openingPosition + ", 见煤位置=" + seeCoalPosition + ", 过煤位置=" + passedCoalPosition + '}';
    }

    public double getOpeningHeight() {
        return openingHeight;
    }

    public void setOpeningHeight(double openingHeight) {
        this.openingHeight = openingHeight;
    }

    public Point getOpeningPosition() {
        return openingPosition;
    }

    public void setOpeningPosition(Point openingPosition) {
        this.openingPosition = openingPosition;
    }

    public Point getSeeCoalPosition() {
        return seeCoalPosition;
    }

    public void setSeeCoalPosition(Point seeCoalPosition) {
        this.seeCoalPosition = seeCoalPosition;
    }

    public Point getPassedCoalPosition() {
        return passedCoalPosition;
    }

    public void setPassedCoalPosition(Point passedCoalPosition) {
        this.passedCoalPosition = passedCoalPosition;
    }

    public double getDistanceAfterCoal() {
        return distanceAfterCoal;
    }

    public void setDistanceAfterCoal(double distanceAfterCoal) {
        this.distanceAfterCoal = distanceAfterCoal;
    }

    public double getGroupId() {
        return groupId;
    }

    public void setGroupId(double groupId) {
        this.groupId = groupId;
    }

    public double getDrillId() {
        return drillId;
    }

    public void setDrillId(double drillId) {
        this.drillId = drillId;
    }

    public double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(double azimuth) {
        this.azimuth = azimuth;
    }

    public double getTiltAngle() {
        return tiltAngle;
    }

    public void setTiltAngle(double tiltAngle) {
        this.tiltAngle = tiltAngle;
    }

    public double getDistanceSeeCoal() {
        return distanceSeeCoal;
    }

    public void setDistanceSeeCoal(double distanceSeeCoal) {
        this.distanceSeeCoal = distanceSeeCoal;
    }

    public double getDistanceThroughCoal() {
        return distanceThroughCoal;
    }

    public void setDistanceThroughCoal(double distanceThroughCoal) {
        this.distanceThroughCoal = distanceThroughCoal;
    }

    public double getDistanceTotal() {
        return distanceTotal;
    }

    public void setDistanceTotal(double distanceTotal) {
        this.distanceTotal = distanceTotal;
    }
}
