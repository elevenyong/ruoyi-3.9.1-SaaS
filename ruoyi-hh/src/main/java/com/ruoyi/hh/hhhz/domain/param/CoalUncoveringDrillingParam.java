package com.ruoyi.hh.hhhz.domain.param;

import com.ruoyi.hh.hhhz.domain.NormalDrillingData;

import java.util.ArrayList;

import static com.ruoyi.hh.hhhz.tools.MathUtils.ang2rad;

public class CoalUncoveringDrillingParam {

    /**
     * 煤厚
     */
    public double d = 3;

    /**
     * 石门高度
     */
    public double H = 3;
    /**
     * 揭煤工作面与煤层距离
     */
    public double N = 7;
    /**
     * 石门宽度
     */
    public double W = 4;
    /**
     * 石门上/下底宽(梯形)
     */
    public double Wp;
    /**
     * 石门帮高(拱形)
     */
    public double Hp = 0.9;
    /**
     * 煤层倾角
     */
    public double beta = ang2rad(30);
    /**
     * 石门与煤层走向夹角
     */
    public double gama = ang2rad(10);
    /**
     * 石门与煤层夹角
     */
    private double fai = 0;
    /**
     * 抽采半径
     */
    public double R = 5;

    /**
     * 上|下控制范围
     */
    public double h1 = 0;
    public double h2 = 0;
    /**
     * 左|右控制范围
     */
    public double w1 = 0;
    public double w2 = 0;

    /**
     * 钻机最小工作高度
     */
    public double q1 = 0;
    /**
     * 钻机最大工作高度
     */
    public double q2 = 0;

    /**
     * 最左侧钻孔距离石门左边界的距离
     */
    public double p1 = 0;
    /**
     * 最右侧钻孔距离石门右边界的距离
     */
    public double p2 = 0;

    /**
     * 0=巷头揭煤||1=辅助巷揭煤
     */
    public int type = 0;

    /**
     * 钻孔数据
     */
    public ArrayList<NormalDrillingData> drillingData = new ArrayList<>();

    public CoalUncoveringDrillingParam() {
    }

    public double getH1() {
        return h1;
    }

    public void setH1(double h1) {
        this.h1 = h1;
    }

    public double getH2() {
        return h2;
    }

    public void setH2(double h2) {
        this.h2 = h2;
    }

    public double getW1() {
        return w1;
    }

    public void setW1(double w1) {
        this.w1 = w1;
    }

    public double getW2() {
        return w2;
    }

    public void setW2(double w2) {
        this.w2 = w2;
    }

    public double getD() {
        return d;
    }

    public void setD(double d) {
        this.d = d;
    }

    public double getH() {
        return H;
    }

    public void setH(double h) {
        H = h;
    }

    public double getN() {
        return N;
    }

    public void setN(double n) {
        N = n;
    }

    public double getW() {
        return W;
    }

    public void setW(double w) {
        W = w;
    }

    public double getWp() {
        return Wp;
    }

    public void setWp(double wp) {
        Wp = wp;
    }

    public double getHp() {
        return Hp;
    }

    public void setHp(double hp) {
        Hp = hp;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = ang2rad(beta);
    }

    public double getGama() {
        return gama;
    }

    public void setGama(double gama) {
        this.gama = ang2rad(gama);
    }

    public double getFai() {
        this.setFai(Math.atan(Math.tan(beta) * Math.cos(gama)));
        return fai;
    }

    public void setFai(double fai) {
        this.fai = fai;
    }

    public double getR() {
        return R;
    }

    public void setR(double r) {
        R = r;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getQ1() {
        return q1;
    }

    public void setQ1(double q1) {
        this.q1 = q1;
    }

    public double getQ2() {
        return q2;
    }

    public void setQ2(double q2) {
        this.q2 = q2;
    }

    public double getP1() {
        return p1;
    }

    public void setP1(double p1) {
        this.p1 = p1;
    }

    public double getP2() {
        return p2;
    }

    public void setP2(double p2) {
        this.p2 = p2;
    }

    public ArrayList<NormalDrillingData> getDrillingData() {
        return drillingData;
    }

    public void setDrillingData(ArrayList<NormalDrillingData> drillingData) {
        this.drillingData = drillingData;
    }
}
