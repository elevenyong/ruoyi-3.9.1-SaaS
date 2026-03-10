package com.ruoyi.hh.hhhz.domain;

import static com.ruoyi.hh.hhhz.tools.MathUtils.rad2ang;

/**
 * 定向钻孔
 */
public class DirectionalDrillingData {
    /**
     * 孔深
     */
    private double deep;
    /**
     * 方位角
     */
    private double azimuth;
    /**
     * 倾角
     */
    private double tiltAngle;
    /**
     * 投影深度
     */
    private double projDepth;
    /**
     * 偏差
     */
    private double deviation;
    /**
     * 左右偏差
     */
    private double leftRightDeviation;
    /**
     * 上下偏差
     */
    private double topBottomDeviation;
    /**
     * 东偏
     */
    private double east;
    /**
     * 北偏
     */
    private double north;
    /**
     * 高程
     */
    private double elevation;

    /**
     * 钻杆起点
     */
    private Point startPoint;
    /**
     * 钻杆终点
     */
    private Point endPoint;

    @Override
    public String toString() {
        return "DrillData{" + " 孔深=" + deep + ",  方位角=" + rad2ang(azimuth) + ", 倾角=" + rad2ang(tiltAngle) + ", 投影深度=" + projDepth + ", 偏差=" + deviation + ", 左右偏差=" + leftRightDeviation + ", 上下偏差=" + topBottomDeviation + ", 东偏=" + east + ", 北偏=" + north + ", 高程=" + elevation + '}';
    }

    public DirectionalDrillingData() {
    }

    public DirectionalDrillingData(double deep, double azimuth, double tiltAngle, double deviation) {
        this.deep = deep;
        this.azimuth = azimuth;
        this.tiltAngle = tiltAngle;
        this.deviation = deviation;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

    public DirectionalDrillingData(double deep, double azimuth, double tiltAngle, double projDepth, double deviation, double leftRightDeviation, double topBottomDeviation, double east, double north, double elevation) {
        this.deep = deep;
        this.azimuth = azimuth;
        this.tiltAngle = tiltAngle;
        this.projDepth = projDepth;
        this.deviation = deviation;
        this.leftRightDeviation = leftRightDeviation;
        this.topBottomDeviation = topBottomDeviation;
        this.east = east;
        this.north = north;
        this.elevation = elevation;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public double getDeep() {
        return deep;
    }

    public void setDeep(double deep) {
        this.deep = deep;
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

    public double getProjDepth() {
        return projDepth;
    }

    public void setProjDepth(double projDepth) {
        this.projDepth = projDepth;
    }

    public double getLeftRightDeviation() {
        return leftRightDeviation;
    }

    public void setLeftRightDeviation(double leftRightDeviation) {
        this.leftRightDeviation = leftRightDeviation;
    }

    public double getTopBottomDeviation() {
        return topBottomDeviation;
    }

    public void setTopBottomDeviation(double topBottomDeviation) {
        this.topBottomDeviation = topBottomDeviation;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }


}
