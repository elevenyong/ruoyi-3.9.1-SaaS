package com.ruoyi.hh.hhhz.domain.param;

import com.ruoyi.hh.hhhz.domain.DirectionalDrillingData;

import java.util.ArrayList;

import static com.ruoyi.hh.hhhz.tools.MathUtils.ang2rad;

/**
 * 定向钻孔参数
 */
public class DirectionalDrillingParam {

    /**
     * 煤层厚度（hc）
     */
    public double hc = 0;
    /**
     * 煤层倾角(α,[0,90))
     */
    //public double alf = Math.PI / (180 * 12);
    public double alf = 0;
    //public double alf = ang2rad(15);
    /**
     * 煤层底板到底抽巷间距(h2)
     */
    public double h2 = 0;
    //public double h2 = 12;
    /**
     * 底抽巷宽（wd）
     */
    public double wd = 0;
    //public double wd = 4.6;
    /**
     * 底抽巷拱高（hg=w*0.5）
     */
    public double hg = wd * 0.5;
    /**
     * 底抽巷高（hd）
     */
    public double hd = 0;
    /**
     * 底抽巷方位角（βd）
     */
    public double betd = 0;

    /**
     * 抽采半径(r)
     */
    public double radius = 0;
    //public double radius = 5;
    /**
     * 底抽巷与煤巷水平投影间距(L)
     */
    public double l = 0;
    //public double l = 10;
    /**
     * 抽采最小控制范围(Lmin)
     */
    public double lMin = 0;
    //public double Lmin = 62;
    /**
     * 抽采最大控制范围(Lmax)
     */
    public double lMax = 0;
    //public double Lmax = 78;
    /**
     * 单位距离, 一根钻杆的长度
     */
    public double perLen = 0;
    /**
     * 直钻杆数量
     */
    public int startDrillNum = 0;
    /**
     * 钻孔孔径
     */
    public double rz = 0;
    /**
     * 测量间距
     */
    public double measureSpace = 0;
    /**
     * 最大开孔角度
     */
    public double maxStartAng = 0;

    /**
     *
     */
    public double maxDeltaAng = 0;
    //public double perLen = 3.0;
    /**
     * 起始段要求直线钻杆的数量
     */
    public int startDirectNum = 0;
    //public int startDirectNum = 1;
    /**
     * 选定角度
     */
    public double selAng = 0;

    /**
     * 施工参数表
     */
    public ArrayList<DirectionalDrillingData> directionalDrillingData = new ArrayList<>();

    public double getHc() {
        return hc;
    }

    public void setHc(double hc) {
        this.hc = hc;
    }

    public double getAlf() {
        return alf;
    }

    public void setAlf(double alf) {
        this.alf = ang2rad(alf);
    }

    public double getH2() {
        return h2;
    }

    public void setH2(double h2) {
        this.h2 = h2;
    }

    public double getWd() {
        return wd;
    }

    public void setWd(double wd) {
        this.wd = wd;
    }

    public double getHg() {
        return hg;
    }

    public void setHg(double hg) {
        this.hg = hg;
    }

    public double getHd() {
        return hd;
    }

    public void setHd(double hd) {
        this.hd = hd;
    }

    public double getBetd() {
        return betd;
    }

    public void setBetd(double betd) {
        this.betd = ang2rad(betd);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }


    public double getlMin() {
        return lMin;
    }

    public void setlMin(double lMin) {
        this.lMin = lMin;
    }

    public double getlMax() {
        return lMax;
    }

    public void setlMax(double lMax) {
        this.lMax = lMax;
    }

    public double getRz() {
        return rz;
    }

    public void setRz(double rz) {
        this.rz = rz;
    }

    public double getPerLen() {
        return perLen;
    }

    public void setPerLen(double perLen) {
        this.perLen = perLen;
    }

    public int getStartDirectNum() {
        return startDirectNum;
    }

    public void setStartDirectNum(int startDirectNum) {
        this.startDirectNum = startDirectNum;
    }

    public double getSelAng() {
        return selAng;
    }

    public void setSelAng(double selAng) {
        this.selAng = selAng;
    }


    public double getMeasureSpace() {
        return measureSpace;
    }

    public void setMeasureSpace(double measureSpace) {
        this.measureSpace = measureSpace;
    }

    public double getMaxStartAng() {
        return maxStartAng;
    }

    public void setMaxStartAng(double maxStartAng) {
        this.maxStartAng = maxStartAng;
    }

    public int getStartDrillNum() {
        return startDrillNum;
    }

    public void setStartDrillNum(int startDrillNum) {
        this.startDrillNum = startDrillNum;
    }

    public double getMaxDeltaAng() {
        return maxDeltaAng;
    }

    public void setMaxDeltaAng(double maxDeltaAng) {
        this.maxDeltaAng = maxDeltaAng;
    }

    public ArrayList<DirectionalDrillingData> getDirectionalDrillingData() {
        return directionalDrillingData;
    }

    public void setDirectionalDrillingData(ArrayList<DirectionalDrillingData> directionalDrillingData) {
        this.directionalDrillingData = directionalDrillingData;
    }
}
