package com.ruoyi.hh.hhhz.domain.param;

import com.ruoyi.hh.hhhz.domain.NormalDrillingData;

import java.util.ArrayList;

import static com.ruoyi.hh.hhhz.tools.MathUtils.ang2rad;
import static com.ruoyi.hh.hhhz.tools.MathUtils.rad2ang;


/**
 * 顺层钻孔参数
 */
public class BeddingDrillingParam {
    /**
     * 煤层厚度
     */
    public double hc = 0;
    //public double hc = 2.9;

    /**
     * 煤层倾角
     */
    public double alf = 0;
    //public double alf = ang2rad(15);
    /**
     * 顺槽方位角
     */
    public double belt = 0;
    //public double belt = ang2rad(280);
    /**
     * 顺槽宽
     */
    public double wm = 0;
    //public double wm = 4.6;
    /**
     * 顺槽墙高1
     */
    public double hm1 = 0;
    //public double hm1 = 3;
    /**
     * 顺槽墙高2
     */
    public double hm2 = 0;
    //public double hm2 = 3;
    /**
     * 抽采半径
     */
    public double radius = 0;
    //public double radius = 2.5;
    /**
     * 抽采控制范围
     */
    public double lx = 0;
    //public double lx = 70;
    /**
     * 上顺槽|下顺槽
     */
    public boolean upBedding = false;
    /**
     * 顺槽长度
     */
    public double scLen = 0;
    //public double scLen = 20;

    /**
     * 施工数据
     */
    public ArrayList<NormalDrillingData> drillingData = new ArrayList<>();

    public double getHc() {
        return hc;
    }

    public void setHc(double hc) {
        this.hc = hc;
    }

    public double getAlf() {
        return rad2ang(alf);
    }

    public void setAlf(double alf) {
        this.alf = ang2rad(alf);
    }

    public double getBelt() {
        return rad2ang(belt);
    }

    public void setBelt(double belt) {
        this.belt = ang2rad(belt);
    }

    public double getWm() {
        return wm;
    }

    public void setWm(double wm) {
        this.wm = wm;
    }

    public double getHm1() {
        return hm1;
    }

    public void setHm1(double hm1) {
        this.hm1 = hm1;
    }

    public double getHm2() {
        return hm2;
    }

    public void setHm2(double hm2) {
        this.hm2 = hm2;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getLx() {
        return lx;
    }

    public void setLx(double lx) {
        this.lx = lx;
    }

    public boolean isUpBedding() {
        return upBedding;
    }

    public void setUpBedding(boolean upBedding) {
        this.upBedding = upBedding;
    }

    public double getScLen() {
        return scLen;
    }

    public void setScLen(double scLen) {
        this.scLen = scLen;
    }

    public BeddingDrillingParam() {
    }
}
