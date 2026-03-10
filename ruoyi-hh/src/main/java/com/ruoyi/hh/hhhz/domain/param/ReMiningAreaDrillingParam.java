package com.ruoyi.hh.hhhz.domain.param;

import com.ruoyi.hh.hhhz.domain.NormalDrillingData;
import com.ruoyi.hh.hhhz.domain.Point;

import java.util.ArrayList;

import static com.ruoyi.hh.hhhz.tools.MathUtils.ang2rad;
import static com.ruoyi.hh.hhhz.tools.MathUtils.rad2ang;

public class ReMiningAreaDrillingParam {

    /**
     * 煤层厚度（hc）
     */
    public double hc = 0;
    //public double hc = 4.3;
    /**
     * 煤层倾角(α,[0,90))
     */
    public double alf = 0;
    //public double alf = ang2rad(15);
    /**
     * 煤层底板到底抽巷间距(h2)
     */
    public double h2 = 0;
    //public double h2 = 15;
    /**
     * 底抽巷宽（wd）
     */
    public double wd = 4.6;
    //public double wd = 4.6;
    /**
     * 底抽巷拱高（hg=w*0.5）
     */
    public double hg = 0;
    //public double hg = wd * 0.5;
    /**
     * 底抽巷高（hd）
     */
    public double hd = 0;
    //public double hd = 3;
    /**
     * 底抽巷方位角（βd）
     */
    public double betd = 0;
    //public double betd = 0;
    /**
     * 煤巷宽 （wm）
     */
    public double wm = 0;
    //public double wm = 5;
    /**
     * 煤巷高（hm）
     */
    public double hm = 0;
    //public double hm = 3;
    /**
     * 抽采半径(r)
     */
    public double radius = 0;
    //public double radius = 2.5;
    /**
     * 控制范围起点(L)
     */
    public double lStart = 0;
    /**
     * 控制范围终点(L)
     */
    public double lEnd = 0;
    //public double l = 10;
    /**
     * 抽采控制范围(Lx)
     */
    public double lx = 0;
    //public double lx = 20;
    /**
     * 抽采控制范围(Ls)
     */
    public double ls = 0;
    //public double ls = 20;
    /**
     * 内错|外错
     */
    public boolean neicuo = true;

    /**
     * 条带设计长度
     */
    public double desLength = 5;

    /**
     * 原点
     */
    public Point oPoint = new Point(0, 0, 0);

    /**
     * 布孔类型: 3=三花布孔,4=方形布孔
     */
    public int type = 4;

    /**
     * 施工数据
     */
    public ArrayList<NormalDrillingData> drillingData = new ArrayList<>();

    /**
     * 验证孔位置
     */
    public Point verifyPoint = new Point(0, 0, 0);

    // 待计算数据
    public double lL;
    public double lR;
    public double numL4;
    public double numR4;
    public double numL3;
    public double numR3;
    public double s4;
    /**
     * 方形布置：排间距（lw4）
     */
    public double lw4;
    /**
     * 三角布置：止煤点间距（s3）
     */
    public double s3;
    /**
     * 三角布置：排间距（lw3）
     */
    public double lw3;

    public double getlStart() {
        return lStart;
    }

    public void setlStart(double lStart) {
        this.lStart = lStart;
    }

    public double getlEnd() {
        return lEnd;
    }

    public void setlEnd(double lEnd) {
        this.lEnd = lEnd;
    }

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
        this.hg = wd * 0.5;
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
        this.betd = betd;
    }

    public double getWm() {
        return wm;
    }

    public void setWm(double wm) {
        this.wm = wm;
    }

    public double getHm() {
        return hm;
    }

    public void setHm(double hm) {
        this.hm = hm;
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

    public double getLs() {
        return ls;
    }

    public void setLs(double ls) {
        this.ls = ls;
    }

    public boolean isNeicuo() {
        return neicuo;
    }

    public void setNeicuo(boolean neicuo) {
        this.neicuo = neicuo;
    }

    public Point getoPoint() {
        return oPoint;
    }

    public void setoPoint(Point oPoint) {
        this.oPoint = oPoint;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<NormalDrillingData> getDrillingData() {
        return drillingData;
    }

    public void setDrillingData(ArrayList<NormalDrillingData> drillingData) {
        this.drillingData = drillingData;
    }
}
