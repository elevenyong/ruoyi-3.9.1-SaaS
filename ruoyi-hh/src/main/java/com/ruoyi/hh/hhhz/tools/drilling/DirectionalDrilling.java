package com.ruoyi.hh.hhhz.tools.drilling;

import com.ruoyi.hh.hhhz.domain.DirectionalDrillingData;
import com.ruoyi.hh.hhhz.domain.Point;
import com.ruoyi.hh.hhhz.domain.Vector3;
import com.ruoyi.hh.hhhz.domain.param.DirectionalDrillingParam;
import com.ruoyi.hh.hhhz.tools.MathUtils;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 定向钻孔设计(短定向, 高位定向钻孔)
 */
@Component
public class DirectionalDrilling {


    /**
     * 煤层厚度（hc）
     * 煤层倾角(α)
     * 煤层底板到底抽巷间距(h2)
     * 底抽巷宽（wd）
     * 底抽巷拱高（hg=w*0.5）
     * 底抽巷高（hd）
     * 底抽巷方位角（βd）
     * 煤巷宽 （wm）
     * 煤巷高（hm）
     * 抽采半径(r)
     * 底抽巷与煤巷水平投影间距(L)
     * 抽采控制范围(Lx)
     * 抽采控制范围(Ls)
     */


    /**
     * 煤层厚度（hc）
     */
    /*private double hc = 4.23;
     *//**
     * 煤层倾角(α,[0,90))
     *//*
    //private double alf = Math.PI / (180 * 12);
    private double alf = ang2rad(15);
    *//**
     * 煤层底板到底抽巷间距(h2)
     *//*
    private double h2 = 12;
    *//**
     * 底抽巷宽（wd）
     *//*
    private double wd = 4.6;
    *//**
     * 底抽巷拱高（hg=w*0.5）
     *//*
    private double hg = wd * 0.5;
    *//**
     * 底抽巷高（hd）
     *//*
    private double hd = 3;
    *//**
     * 底抽巷方位角（βd）
     *//*
    private double betd = 0;
    *//**
     * 煤巷宽 （wm）
     *//*
    private double wm = 5;
    *//**
     * 煤巷高（hm）
     *//*
    private double hm = 3;
    *//**
     * 抽采半径(r)
     *//*
    private double radius = 5;
    *//**
     * 底抽巷与煤巷水平投影间距(L)
     *//*
    private double l = 10;
    *//**
     * 抽采最小控制范围(Lmin)
     *//*
    private double Lmin = 62;
    *//**
     * 抽采最大控制范围(Lmax)
     *//*
    private double Lmax = 78;
    *//**
     * 单位距离, 一根钻杆的长度
     *//*
    private double perLen = 3.0;
    */
    /**
     * 起始段要求直线钻杆的数量
     *//*
    private int startDirectNum = 1;
    */
    public int i111 = 0;
    public int i222 = 0;
    public int i333 = 0;
    public int i444 = 0;
    public int i555 = 0;

    /**
     * 选定角度
     *//*
    private double selAng = 0;*/
    //public List<DirectionalDrillingData> drillData = new ArrayList<>();
    public static void main(String[] args) {
        DirectionalDrilling directionalDrilling = new DirectionalDrilling();
        //directionalDrilling.init();
    }

    /**
     * 计算定向钻孔
     *
     * @param param
     * @return
     */
    public DirectionalDrillingParam calDrilling(DirectionalDrillingParam param) {
        i111 = 0;
        i222 = 0;
        i333 = 0;
        i444 = 0;
        /*// 煤层底板
        double dFA = Math.tan(param.alf);
        double dFB = -1;
        double dFC = (param.h2 + param.hg) / Math.cos(param.alf);
        System.out.println(dFA + "x+" + dFB + "y+" + dFC + "=0");
        // 煤层顶板
        double tFA = Math.tan(param.alf);
        double tFB = -1;
        double tFC = (param.hc + param.h2 + param.hg) / Math.cos(param.alf);
        System.out.println(tFA + "x+" + tFB + "y+" + tFC + "=0");*/

        // 定位孔位置
        //Point verifyPoint = new Point();
        Point oD = new Point();
        oD.setX(param.lMin);
        oD.setY((param.lMin * Math.tan(param.alf)) + ((param.h2 + param.hg) / Math.cos(param.alf)));
        oD.setZ(0);
        System.out.println("oD = " + oD);
        Point oT = new Point();
        oT.setX(param.lMax);
        oT.setY((param.lMax * Math.tan(param.alf)) + ((param.h2 + param.hc + param.hg) / Math.cos(param.alf)));
        oT.setZ(0);
        System.out.println("oT = " + oT);
        //System.out.println("煤巷位置：内错");
        //System.out.println("验证孔：verifyPoint = " + verifyPoint);
        // 重算起点
        /*Vector3 vTD = toVector(oT, oD);
        Point oDp = calNewPoint(vTD, oD, 3);
        oD = oDp;*/

        for (int ii = 1; ii < param.maxStartAng; ii++) {
            // 最多可计算三直两曲类型钻孔轨迹
            // 钻孔起始点O
            Point oS = new Point(0, 0, 0);
            // 倾角, 方位角
            //double startAlpha = 19;
            double startAlpha = ii;
            System.out.println("起始倾角:startAlpha = " + startAlpha);
            //double startAlpha = param.selAng;
            //double startTheta = 0;
            double startTheta = param.betd;
            //double startDrillNum = 4;
            double startDrillNum = param.startDrillNum;
            //double drillLen = 3;
            double perLen = param.perLen;
            // 第一直段终点A
            //Point oA = new Point(12, 6, 0);
            Point oA = new Point(perLen * startDrillNum * Math.cos(MathUtils.ang2rad(startAlpha)) * Math.cos(MathUtils.ang2rad(startTheta)), perLen * startDrillNum * Math.sin(MathUtils.ang2rad(startAlpha)), perLen * startDrillNum * Math.cos(MathUtils.ang2rad(startAlpha)) * Math.sin(MathUtils.ang2rad(startTheta)));
            //System.out.println("oA = " + oA);
            // 第一曲段终点B
            //Point oB = new Point();
            // 第二直段终点C
            //Point oC = new Point();
            // 第二曲段终点D
            //Point oD = new Point(175, 170, 0);
            // 第三直段终点A(入靶点)
            //Point oT = new Point(190, 180, 0);
            // 第一造斜段
            double perAngChange1 = param.maxDeltaAng;
            double perDis1 = param.measureSpace;
            // 第二造斜段
            double perAngChange2 = param.maxDeltaAng;
            double perDis2 = param.measureSpace;
            //double perAngChange2 = 1;
            //double perDis2 = 3;

            Vector3 vAD = new Vector3(oA, oD);
            Vector3 vSA = new Vector3(oS, oA);
            Vector3 vDT = new Vector3(oD, oT);
            double Ts = MathUtils.calProjA2B(vAD, vSA).getNorm();
            double Tt = MathUtils.calProjA2B(vAD, vDT).getNorm();
            //double Lm = calP2PDis(oA, oM);
            //double Ln = calP2PDis(oC, oN);
            //double Lh = calP2PDis(oB, oC);
            double Lh = 0;
            double L = MathUtils.calP2PDis(oA, oD);
            // 起末向量夹角
            double theta = MathUtils.calAng(vSA, vDT);
            // 第一造斜圆弧半径
            double R1 = calK2R(perAngChange1, perDis1);
            // 第二造斜圆弧半径
            double R2 = calK2R(perAngChange2, perDis2);
            System.out.println("设计参数:");
            System.out.println("vSA = " + vSA);
            System.out.println("vAD = " + vAD);
            System.out.println("L = " + L);
            System.out.println("theta = " + MathUtils.rad2ang(theta));
            System.out.println("Ts = " + Ts);
            System.out.println("Tt = " + Tt);
            System.out.println("R1 = " + R1);
            System.out.println("R2 = " + R2);
            System.out.println("========================================================");

            double step = 0.001;
            for (int i = 0; i < 1000000; i++) {
                //System.out.println("Lh = " + Lh);
                // 7  Lm = f(R2,L1,Lh,L2)
                double a0m = 4 * ((R2 * R2 * Math.sin(theta) * Math.sin(theta)) - ((Ts + Lh) * (Ts + Lh)));
                double b0m = (8 * R2 * R2 * (Tt * Math.cos(theta) - Ts)) + (4 * (L * L - Lh * Lh) * (Ts + Lh));
                double c0m = (8 * R2 * R2 * Lh * (Tt + Lh)) + (4 * R2 * R2 * ((L * L) - (Lh * Lh) - ((Tt + Lh) * (Tt + Lh)))) - (((L * L) - (Lh * Lh)) * ((L * L) - (Lh * Lh)));
                double delta0m = (b0m * b0m) - (4 * a0m * c0m);
                if (delta0m < 0) {
                    System.out.println("Lh = " + Lh + ",无解");
                    Lh = (i * step);
                    continue;
                }
                double L0m = ((-1 * b0m) + Math.sqrt(delta0m)) / (2 * a0m);
                //System.out.println("L0m = " + L0m);


                // 8  Ln = f(R1,L1,Lh,L2)
                double a0n = 4 * ((R1 * R1 * Math.sin(theta) * Math.sin(theta)) - ((Tt + Lh) * (Tt + Lh)));
                double b0n = (8 * R1 * R1 * (Ts * Math.cos(theta) - Tt)) + (4 * (L * L - Lh * Lh) * (Tt + Lh));
                double c0n = (8 * R1 * R1 * Lh * (Ts + Lh)) + (4 * R1 * R1 * ((L * L) - (Lh * Lh) - ((Ts + Lh) * (Ts + Lh)))) - (((L * L) - (Lh * Lh)) * ((L * L) - (Lh * Lh)));
                double delta0n = b0n * b0n - 4 * a0n * c0n;
                if (delta0n < 0) {
                    System.out.println("Lh = " + Lh + ",无解");
                    Lh = (i * step);
                    continue;
                }
                double L0n = ((-1 * b0n) + Math.sqrt(delta0n)) / (2 * a0n);
                //System.out.println("L0n = " + L0n);

                // 9  Lm = f(R1,L1,Lh,L2)
                double a1m = (((4 * R1 * R1) - (L * L) + (Lh * Lh)) * (1 - Math.cos(theta))) - (2 * (Ts + Lh) * (Tt + Lh));
                double b1m = 4 * R1 * R1 * (Tt - Ts + (Lh * (1 - Math.cos(theta))));
                double c1m = R1 * R1 * (((L * L - Lh * Lh) * (1 + Math.cos(theta))) - (2 * (Ts - Lh) * (Tt + Lh)));
                double delta1m = b1m * b1m - 4 * a1m * c1m;
                if (delta1m < 0) {
                    System.out.println("Lh = " + Lh + ",无解");
                    Lh = (i * step);
                    continue;
                }
                double L1m = (-1 * b1m - Math.sqrt(delta1m)) / (2 * a1m);
                //System.out.println("L1m = " + L1m);

                // 10  Ln = f(R2,L1,Lh,L2)
                double a1n = (((4 * R2 * R2) - (L * L) + (Lh * Lh)) * (1 - Math.cos(theta))) - (2 * (Tt + Lh) * (Ts + Lh));
                double b1n = 4 * R2 * R2 * (Ts - Tt + Lh * (1 - Math.cos(theta)));
                double c1n = R2 * R2 * ((((L * L - Lh * Lh) * (1 + Math.cos(theta))) - (2 * (Tt - Lh) * (Ts + Lh))));
                double delta1n = b1n * b1n - 4 * a1n * c1n;
                if (delta1n < 0) {
                    System.out.println("Lh = " + Lh + ",无解");
                    Lh = (i * step);
                    continue;
                }
                double L1n = (-1 * b1n - Math.sqrt(delta1n)) / (2 * a1n);


            /*System.out.println("L0m = " + L0m);
            System.out.println("L0n = " + L0n);
            System.out.println("L1m = " + L1m);
            System.out.println("L1n = " + L1n);*/
                double v = L0m - L1m;
                //System.out.println("v = " + v);
                double v1 = L0n - L1n;
                //System.out.println("v1 = " + v1);
                //System.out.println("__________________________________________________");
                if (Math.abs(v) <= 0.001 && Math.abs(v1) <= 0.001) {
                    System.out.println("发现最优解: ");
                    System.out.println("i = " + i);
                    System.out.println("Lh = " + Lh);
                    System.out.println("L0m = " + L0m);
                    System.out.println("L0n = " + L0n);
                    System.out.println("L1m = " + L1m);
                    System.out.println("L1n = " + L1n);
                    param.selAng = ii;

                    double Lm = L0m;
                    double Ln = L0n;
                    double cosTheta1 = (Ts - Lm - (Ln * Math.cos(theta))) / (Lm + Lh + Ln);
                    double cosTheta2 = (Tt - Ln - (Lm * Math.cos(theta))) / (Lm + Lh + Ln);
                    double theta1 = Math.acos(cosTheta1);
                    double theta2 = Math.acos(cosTheta2);
                    double chrod1 = 2 * R1 * Math.sin(theta1 / 2);
                    double chrod2 = 2 * R1 * Math.sin(theta2 / 2);
                    System.out.println("1直径 D1 = " + (R1 * 2));
                    System.out.println("1圆心角 theta1 = " + MathUtils.rad2ang(theta1));
                    System.out.println("1弦长 chrod1 = " + chrod1);
                    System.out.println("---");
                    System.out.println("2直径 D2 = " + (R2 * 2));
                    System.out.println("2圆心角 theta2 = " + MathUtils.rad2ang(theta2));
                    System.out.println("2弦长 chrod2 = " + chrod2);

                    //  轨道关键点坐标计算
                    Vector3 vSM = MathUtils.calNewV3(vSA, Lm);
                    Point oM = vSM.getEnd();
                    Vector3 vTN = MathUtils.calNewV3(vDT, -1 * Ln);
                    Point oN = vTN.getStart();
                    System.out.println("oM = " + oM);
                    System.out.println("oN = " + oN);

                    // s->
                    double ls = (oA.getX() - oS.getX()) / MathUtils.calP2PDis(oA, oS);
                    double ms = (oA.getY() - oS.getY()) / MathUtils.calP2PDis(oA, oS);
                    double ns = (oA.getZ() - oS.getZ()) / MathUtils.calP2PDis(oA, oS);
                    // 计算起始段井斜角(倾角), 方位角
                    double as = Math.atan(ns);
                    double thetaS = Math.atan(ms / ls);
                    System.out.println("s倾角 ah = " + MathUtils.rad2ang(as));
                    System.out.println("s方位角 thetaH = " + MathUtils.rad2ang(thetaS));

                    // h->
                    double lh = (oN.getX() - oM.getX()) / MathUtils.calP2PDis(oN, oM);
                    double mh = (oN.getY() - oM.getY()) / MathUtils.calP2PDis(oN, oM);
                    double nh = (oN.getZ() - oM.getZ()) / MathUtils.calP2PDis(oN, oM);
                    // 计算稳斜段井斜角(倾角), 方位角
                    double ah = Math.atan(nh);
                    double thetaH = Math.atan(mh / lh);
                    System.out.println("h倾角 ah = " + MathUtils.rad2ang(ah));
                    System.out.println("h方位角 thetaH = " + MathUtils.rad2ang(thetaH));

                    // t->
                    double lt = (oT.getX() - oD.getX()) / MathUtils.calP2PDis(oT, oD);
                    double mt = (oT.getY() - oD.getY()) / MathUtils.calP2PDis(oT, oD);
                    double nt = (oT.getZ() - oD.getZ()) / MathUtils.calP2PDis(oT, oD);
                    // 计算终末段井斜角(倾角), 方位角
                    double at = Math.atan(nt);
                    double thetaT = Math.atan(mt / lt);
                    System.out.println("t倾角 ah = " + MathUtils.rad2ang(at));
                    System.out.println("t方位角 thetaH = " + MathUtils.rad2ang(thetaT));


                    // 计算B,C点坐标
                    Vector3 vMN = new Vector3(oM, oN);
                    Point oB = calNewPoint(vMN, oM, Lm);
                    System.out.println("oS = " + oS);
                    System.out.println("oA = " + oA);
                    System.out.println("oB = " + oB);
                    Vector3 vNM = new Vector3(oN, oM);
                    Point oC = calNewPoint(vNM, oN, Ln);
                    System.out.println("oC = " + oC);
                    System.out.println("oD = " + oD);
                    System.out.println("oT = " + oT);

                    Point edPoint = new Point(0, 0, 0);
                    System.out.println("=====================================================");
                    int numCount = 1;
                    double Si = 0;
                    // 第一直线段
                    System.out.println("第一段直线段数据");
                    for (int j = 1; j <= startDrillNum; j++, numCount++) {
                        Si = numCount * perLen;
                        System.out.println("钻杆段:" + numCount + "; 钻杆长度:" + Si + "; 方位角:" + MathUtils.rad2ang(as) + ",倾角:" + MathUtils.rad2ang(thetaS));
                        param.directionalDrillingData.add(new DirectionalDrillingData(Si - perLen, as, thetaS, 0));
                        edPoint = calEndPoint(param);
                        //System.out.println("edPoint = " + edPoint);
                        i111++;
                    }

                    // 计算第一段圆弧数据
                    System.out.println("第一段圆弧数据");
                    //System.out.println("edPoint = " + edPoint);
                    // 计算第一段圆弧上单位矢量的方向余弦(s->, h->)
                    double lr = ((1 / Math.sin(theta1)) * lh) - ((1 / Math.tan(theta1)) * ls);
                    double mr = ((1 / Math.sin(theta1)) * mh) - ((1 / Math.tan(theta1)) * ms);
                    double nr = ((1 / Math.sin(theta1)) * nh) - ((1 / Math.tan(theta1)) * ns);

                    double step2 = perLen;
                    double Si1 = 0;
                    /*double endPDis2B = 99999;
                    for (int j = 0; calP2PDis(edPoint, oB) <= endPDis2B; j++, numCount++) {
                        endPDis2B = calP2PDis(edPoint, oB);
                        Si1 = j * step2;
                        Si += perLen;

                        // 切线方向余弦
                        double li = (Math.sin(Si1 / R1) * lr) + (Math.cos(Si1 / R1) * ls);
                        double mi = (Math.sin(Si1 / R1) * mr) + (Math.cos(Si1 / R1) * ms);
                        double ni = (Math.sin(Si1 / R1) * nr) + (Math.cos(Si1 / R1) * ns);

                        // 倾角,方位角
                        double alfi = Math.acos(ni);
                        double thetai = Math.atan(mi / li);
                        System.out.println("钻杆段:" + numCount + "; 钻杆长度:" + Si *//*+ "; 坐标:" + Xi + "," + Yi + "," + Zi + "; 切线方向余弦:" + li + "," + mi + "," + ni*//* + "; 方位角:" + (rad2ang(alfi) - 90) + ",倾角:" + rad2ang(thetai));
                        //drillData.add(new DrillData(Si - 3, alfi, thetai, 0));
                        param.directionalDrillingData.add(new DirectionalDrillingData(Si - perLen, ang2rad(rad2ang(alfi) - 90), thetai, 0));
                        //System.out.println(Xi + "," + Yi);
                        edPoint = calEndPoint(param);
                        i222++;
                    }
                    param.directionalDrillingData.remove(param.directionalDrillingData.size() - 1);
                    i222--;*/
                    for (int j = 0; Si1 < chrod1; j++) {
                        Si1 = j * step2;
                        Si += perLen;
                        numCount++;

                        // 切线方向余弦
                        double li = (Math.sin(Si1 / R1) * lr) + (Math.cos(Si1 / R1) * ls);
                        double mi = (Math.sin(Si1 / R1) * mr) + (Math.cos(Si1 / R1) * ms);
                        double ni = (Math.sin(Si1 / R1) * nr) + (Math.cos(Si1 / R1) * ns);

                        // 倾角,方位角
                        double alfi = Math.acos(ni);
                        double thetai = Math.atan(mi / li);
                        System.out.println("钻杆段:" + numCount + "; 钻杆长度:" + Si + "; 切线方向余弦:" + li + "," + mi + "," + ni + "; 方位角:" + (MathUtils.rad2ang(alfi) - 90) + ",倾角:" + MathUtils.rad2ang(thetai));
                        //drillData.add(new DrillData(Si - 3, alfi, thetai, 0));
                        param.directionalDrillingData.add(new DirectionalDrillingData(Si - perLen, MathUtils.ang2rad(MathUtils.rad2ang(alfi) - 90), thetai, 0));
                        //System.out.println(Xi + "," + Yi);
                        i222++;
                    }


                    // 计算第二段直线段数据
                    System.out.println("第二段直线段数据");
                    double Di = 0;
                    for (int j = 1; Di <= Lh; j++, numCount++) {
                        Di = j * perLen;
                        Si += perLen;
                        System.out.println("钻杆段:" + numCount + "; 钻杆长度:" + Si + "; 方位角:" + MathUtils.rad2ang(-ah) + ",倾角:" + MathUtils.rad2ang(thetaH));
                        //drillData.add(new DrillData(Si - 3, ah, thetaH, 0));
                        param.directionalDrillingData.add(new DirectionalDrillingData(Si - perLen, -ah, thetaH, 0));
                        i333++;
                    }
                    edPoint = calEndPoint(param);
                    // 计算第二段圆弧数据
                    System.out.println("第二段圆弧数据");
                    // 计算第二段圆弧上单位矢量的方向余弦(h->, t->)
                    double lr1 = ((1 / Math.sin(theta2)) * lt) - ((1 / Math.tan(theta2)) * lh);
                    double mr1 = ((1 / Math.sin(theta2)) * mt) - ((1 / Math.tan(theta2)) * mh);
                    double nr1 = ((1 / Math.sin(theta2)) * nt) - ((1 / Math.tan(theta2)) * nh);

                    double Si2 = 0;
                   /* double endPDis2D = 999999;
                    //System.out.println("edPoint = " + edPoint);
                    for (int j = 0; calP2PDis(edPoint, oD) <= endPDis2D; j++, numCount++) {
                        endPDis2D = calP2PDis(edPoint, oD);
                        //System.out.println("endPDis2D = " + endPDis2D);
                        Si2 = j * step2;
                        Si += perLen;


                        // 切线方向余弦
                        double li2 = (Math.sin(Si2 / R1) * lr1) + (Math.cos(Si2 / R1) * lh);
                        double mi2 = (Math.sin(Si2 / R1) * mr1) + (Math.cos(Si2 / R1) * mh);
                        double ni2 = (Math.sin(Si2 / R1) * nr1) + (Math.cos(Si2 / R1) * nh);

                        // 倾角,方位角
                        double alfi2 = Math.acos(ni2);
                        double thetai2 = Math.atan(mi2 / li2);
                        System.out.println("钻杆段:" + numCount + "; 钻杆长度:" + Si + "; 方位角:" + (rad2ang(alfi2) - 90) + ",倾角:" + rad2ang(thetai2));
                        param.directionalDrillingData.add(new DirectionalDrillingData(Si - perLen, ang2rad(rad2ang(alfi2) - 90), thetai2, 0));
                        edPoint = calEndPoint(param);
                        i444++;
                    }
                    param.directionalDrillingData.remove(param.directionalDrillingData.size() - 1);
                    edPoint = calEndPoint(param);
                    i444--;*/
                    double alfi3 = 0;
                    double thetai3 = 0;
                    for (int j = 0; Si2 < chrod2; j++) {
                        Si2 = j * step2;
                        Si += perLen;
                        numCount++;

                        // 切线方向余弦
                        double li2 = (Math.sin(Si2 / R1) * lr1) + (Math.cos(Si2 / R1) * lh);
                        double mi2 = (Math.sin(Si2 / R1) * mr1) + (Math.cos(Si2 / R1) * mh);
                        double ni2 = (Math.sin(Si2 / R1) * nr1) + (Math.cos(Si2 / R1) * nh);

                        // 倾角,方位角
                        double alfi2 = Math.acos(ni2);
                        double thetai2 = Math.atan(mi2 / li2);
                        System.out.println("钻杆段:" + numCount + "; 钻杆长度:" + Si + "; 方位角:" + (MathUtils.rad2ang(alfi2) - 90) + ",倾角:" + MathUtils.rad2ang(thetai2));
                        //drillData.add(new DrillData(Si - 3, alfi2, thetai2, 0));
                        param.directionalDrillingData.add(new DirectionalDrillingData(Si - perLen, MathUtils.ang2rad(MathUtils.rad2ang(alfi2) - 90), thetai2, 0));
                        alfi3 = MathUtils.ang2rad(MathUtils.rad2ang(alfi2) - 90);
                        thetai3 = thetai2;
                        //System.out.println(Xi + "," + Yi);
                        i444++;
                    }

                    // 计算第三段直线段数据
                    System.out.println("第三段直线数据");
                    edPoint = calEndPoint(param);
                    double disDT = MathUtils.calP2PDis(edPoint, oT);
                    double Di3 = 0;
                    for (int j = 1; Di3 <= disDT + perLen; j++, numCount++) {
                        Di3 = j * perLen;
                        Si += perLen;
                        System.out.println("钻杆段:" + numCount + "; 钻杆长度:" + Si + "; 方位角:" + MathUtils.rad2ang(alfi3) + ",倾角:" + MathUtils.rad2ang(thetai3));
                        //drillData.add(new DrillData(Si - 3, ah, thetaH, 0));
                        param.directionalDrillingData.add(new DirectionalDrillingData(Si - perLen, alfi3, thetai3, 0));
                        i555++;
                    }

                    // 计算第三段直线段数据
                    /*System.out.println("第三段直线段数据");
                    // 计算曲直结合点
                    System.out.println("计算曲直结合点");
                    System.out.println("edPoint = " + edPoint);
                    double stepT = 0.001;
                    Point newPP = new Point();
                    for (int tt = 0; tt < 1000000; tt++) {
                        Vector3 vector3 = toVector(oD, oT);
                        newPP = calNewPoint(vector3, oD, tt * stepT);
                        double p2PDis = calP2PDis(newPP, edPoint);
                        //System.out.println("p2PDis = " + p2PDis);
                        if (Math.abs(calP2PDis(newPP, edPoint) - 3) <= 0.001) {
                            System.out.println("曲直结合点 newPP = " + newPP);
                            edPoint = newPP;
                            break;
                        }
                    }
                    double disDT = calP2PDis(edPoint, oT);
                    double Di3 = 0;
                    for (int j = 1; Di3 <= disDT; j++, numCount++) {
                        Di3 = j * perLen;
                        Si += perLen;
                        Vector3 v3 = toVector(newPP, oT);
                        edPoint = calNewPoint(v3, newPP, Di3);
                        System.out.println(edPoint + ",");
                        //System.out.println("钻杆段:" + numCount + "; 钻杆长度:" + Si + "; 方位角:" + rad2ang(-ah) + ",倾角:" + rad2ang(thetaH));
                        //drillData.add(new DrillData(Si - 3, ah, thetaH, 0));
                        //param.directionalDrillingData.add(new DirectionalDrillingData(Si - perLen, -ah, thetaH, 0));
                        //i333++;
                    }
                    edPoint = calEndPoint(param);*/
                    System.out.println("=================");
                    // 计算施工参数
                    calDrillData(param);
                    return param;
                }
                Lh += step;
            }
        }
        System.out.println("未找到最优解");
        return null;
    }

    public Point calEndPoint(DirectionalDrillingParam param) {
        List<DirectionalDrillingData> drillData = param.directionalDrillingData;
        Point po = new Point(0, 0, 0);
        for (int i = 0; i < drillData.size(); i++) {
            if (i == 0) {
                // 起始点
                drillData.get(0).setStartPoint(po);
                drillData.get(0).setDeviation(0);
                drillData.get(0).setDeep(0);
                drillData.get(0).setLeftRightDeviation(0);
                drillData.get(0).setTopBottomDeviation(0);
                drillData.get(0).setEast(0);
                drillData.get(0).setNorth(0);
                drillData.get(0).setProjDepth(0);
                drillData.get(0).setElevation(0);
                // 第一根钻杆末点
                Point pe0 = new Point(po.getX() + param.perLen * Math.cos(drillData.get(0).getTiltAngle()) * Math.cos(drillData.get(0).getAzimuth()), po.getY() + param.perLen * Math.sin(drillData.get(0).getTiltAngle()), po.getZ() - param.perLen * Math.cos(drillData.get(0).getTiltAngle()) * Math.sin((drillData.get(0).getAzimuth())));
                drillData.get(0).setEndPoint(pe0);
            } else {
                // 计算点位
                drillData.get(i).setStartPoint(drillData.get(i - 1).getEndPoint());
                Point ps = drillData.get(i).getStartPoint();
                Point pe = new Point(ps.getX() + (param.perLen * Math.cos((drillData.get(i).getTiltAngle())) * Math.cos((drillData.get(i).getAzimuth()))), ps.getY() + (param.perLen * Math.sin((drillData.get(i).getTiltAngle()))), ps.getZ() - (param.perLen * Math.cos((drillData.get(i).getTiltAngle())) * Math.sin((drillData.get(i).getAzimuth()))));
                drillData.get(i).setEndPoint(pe);
                // 计算施工参数
                DirectionalDrillingData lastDrill = drillData.get(i - 1);
                DirectionalDrillingData drill = drillData.get(i);
                double projDepth = ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.cos(drill.getTiltAngle()) * Math.cos(drill.getDeviation()) + Math.cos(lastDrill.getTiltAngle()) * Math.cos(lastDrill.getDeviation())) + lastDrill.getProjDepth();
                drillData.get(i).setProjDepth(projDepth);
                double leftRightDev = ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.cos(drill.getTiltAngle()) * Math.sin(drill.getDeviation()) + Math.cos(lastDrill.getTiltAngle()) * Math.sin(lastDrill.getDeviation())) + lastDrill.getLeftRightDeviation();
                drillData.get(i).setLeftRightDeviation(leftRightDev);
                double topBottomDev = ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.sin(drill.getTiltAngle()) + Math.sin(lastDrill.getTiltAngle())) + lastDrill.getTopBottomDeviation();
                drillData.get(i).setTopBottomDeviation(topBottomDev);
                double eastDev = -1 * ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.cos(drill.getTiltAngle()) * Math.sin(drill.getAzimuth()) + Math.cos(lastDrill.getTiltAngle()) * Math.sin(lastDrill.getAzimuth())) + lastDrill.getEast();
                drillData.get(i).setEast(eastDev);
                double northDev = ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.cos(drill.getTiltAngle()) * Math.cos(drill.getAzimuth()) + Math.cos(lastDrill.getTiltAngle()) * Math.cos(lastDrill.getAzimuth())) + lastDrill.getNorth();
                drillData.get(i).setNorth(northDev);
                double elevation = ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.sin(drill.getTiltAngle()) + Math.sin(lastDrill.getTiltAngle())) + lastDrill.getElevation();
                drillData.get(i).setElevation(elevation);
            }
        }
        return drillData.get(drillData.size() - 1).getEndPoint();
    }

    /**
     * 计算施工数据
     *
     * @param param
     */
    public void calDrillData(DirectionalDrillingParam param) {
        List<DirectionalDrillingData> drillData = param.directionalDrillingData;
        System.out.println("drillData.size() = " + drillData.size());
        Point po = new Point(0, 0, 0);
        for (int i = 0; i < drillData.size(); i++) {
            if (i == 0) {
                // 起始点
                drillData.get(0).setStartPoint(po);
                drillData.get(0).setDeviation(0);
                drillData.get(0).setDeep(0);
                drillData.get(0).setLeftRightDeviation(0);
                drillData.get(0).setTopBottomDeviation(0);
                drillData.get(0).setEast(0);
                drillData.get(0).setNorth(0);
                drillData.get(0).setProjDepth(0);
                drillData.get(0).setElevation(0);
                // 第一根钻杆末点
                Point pe0 = new Point(po.getX() + param.perLen * Math.cos(drillData.get(0).getTiltAngle()) * Math.cos(drillData.get(0).getAzimuth()), po.getY() + param.perLen * Math.sin(drillData.get(0).getTiltAngle()), po.getZ() - param.perLen * Math.cos(drillData.get(0).getTiltAngle()) * Math.sin((drillData.get(0).getAzimuth())));
                drillData.get(0).setEndPoint(pe0);
            } else {
                // 计算点位
                drillData.get(i).setStartPoint(drillData.get(i - 1).getEndPoint());
                Point ps = drillData.get(i).getStartPoint();
                Point pe = new Point(ps.getX() + (param.perLen * Math.cos((drillData.get(i).getTiltAngle())) * Math.cos((drillData.get(i).getAzimuth()))), ps.getY() + (param.perLen * Math.sin((drillData.get(i).getTiltAngle()))), ps.getZ() - (param.perLen * Math.cos((drillData.get(i).getTiltAngle())) * Math.sin((drillData.get(i).getAzimuth()))));
                drillData.get(i).setEndPoint(pe);

                // 计算施工参数
                DirectionalDrillingData lastDrill = drillData.get(i - 1);
                DirectionalDrillingData drill = drillData.get(i);
                double projDepth = ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.cos(drill.getTiltAngle()) * Math.cos(drill.getDeviation()) + Math.cos(lastDrill.getTiltAngle()) * Math.cos(lastDrill.getDeviation())) + lastDrill.getProjDepth();
                drillData.get(i).setProjDepth(projDepth);
                double leftRightDev = ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.cos(drill.getTiltAngle()) * Math.sin(drill.getDeviation()) + Math.cos(lastDrill.getTiltAngle()) * Math.sin(lastDrill.getDeviation())) + lastDrill.getLeftRightDeviation();
                drillData.get(i).setLeftRightDeviation(leftRightDev);
                double topBottomDev = ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.sin(drill.getTiltAngle()) + Math.sin(lastDrill.getTiltAngle())) + lastDrill.getTopBottomDeviation();
                drillData.get(i).setTopBottomDeviation(topBottomDev);
                double eastDev = -1 * ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.cos(drill.getTiltAngle()) * Math.sin(drill.getAzimuth()) + Math.cos(lastDrill.getTiltAngle()) * Math.sin(lastDrill.getAzimuth())) + lastDrill.getEast();
                drillData.get(i).setEast(eastDev);
                double northDev = ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.cos(drill.getTiltAngle()) * Math.cos(drill.getAzimuth()) + Math.cos(lastDrill.getTiltAngle()) * Math.cos(lastDrill.getAzimuth())) + lastDrill.getNorth();
                drillData.get(i).setNorth(northDev);
                double elevation = ((drill.getDeep() - lastDrill.getDeep()) / 2.0) * (Math.sin(drill.getTiltAngle()) + Math.sin(lastDrill.getTiltAngle())) + lastDrill.getElevation();
                drillData.get(i).setElevation(elevation);
            }
        }
        for (int i = 0; i < drillData.size(); i++) {
            if (i == i111 + 1) {
                System.out.println("第一段:" + i111);
            }
            if (i == (i111 + i222 + 1)) {
                System.out.println("第二段:" + i222);
            }
            if (i == (i111 + i222 + i333 + 1)) {
                System.out.println("第三段:" + i333);
            }
            if (i == (i111 + i222 + i333 + i444)) {
                System.out.println("第四段:" + i444);
            }
            if (i == (i111 + i222 + i333 + i444 + i555 + 1)) {
                System.out.println("第五段:" + i555);
            }
            System.out.println(drillData.get(i).getStartPoint() + ",");
        }
        /*for (DirectionalDrillingData data : drillData) {
            //System.out.println("方位角:" + rad2ang(data.getAzimuth()) + ",倾角:" + rad2ang(data.getTiltAngle()) + "," + data.getStartPoint() + ",");
            System.out.println(data.getStartPoint() + ",");
        }*/
    }

    public Point calNewPoint(Vector3 v3, Point basePoint, double dis) {
        double vx = basePoint.getX() + (v3.getX() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
        double vy = basePoint.getY() + (v3.getY() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
        double vz = basePoint.getZ() + (v3.getZ() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
        return new Point(vx, vy, vz);
    }


    /**
     * 造斜率(°/m)转半径
     *
     * @return
     */
    public double calK2R(double perAngChange, double perDis) {
        if (perAngChange > 0 && perDis > 0) {
            return perDis / Math.sin(MathUtils.ang2rad(perAngChange));
        }
        return 0;
    }


            /*Vector3 vAD = getV3(oA, oD);


        double mOKA = (oA.getX() - oS.getX()) * (oT.getX() - oA.getX()) + (oA.getY() - oS.getY()) * (oT.getY() - oA.getY()) + (oA.getZ() - oS.getZ()) * (oT.getZ() - oA.getZ());

        double cos = mOKA / (disOK * disKA);
        double thetaKAP = Math.acos(cos);
        double Tp = disKA * Math.cos(thetaKAP);
        double theta = thetaKAP;

        double disFA = calP2PDis(oD, oT);
        double mKAF = (oT.getX() - oA.getX()) * (oD.getX() - oT.getX()) + (oT.getY() - oA.getY()) * (oD.getY() - oT.getY()) + (oT.getZ() - oA.getZ()) * (oD.getZ() - oT.getZ());
        double Tt = disKA * Math.cos(mKAF);

        double L = disKA;
        double L1 = disOK;
        double L2 = calP2PDis(oD, oT);
        double Lh = calP2PDis(oB, oC);

        double theta1 = Math.acos((Tp - Lp - Lq * Math.cos(theta)) / (Lp + Lh + Lq));
        double theta2 = Math.acos((Tt - Lq - Lp * Math.cos(theta)) / (Lp + Lh + Lq));
        double Lp = (L * L - Lh * Lh - 2 * Lq * (Tt + Lh)) / (2 * (Ts + Lh + Lq - Lq * Math.cos(theta)));
        double Lq = (L * L - Lh * Lh - 2 * Lp * (Ts + Lh)) / (2 * (Tt + Lh + Lp - Lp * Math.cos(theta)));
        double Lp = R1 * Math.tan(theta1 / 2);
        double Lq = R2 * Math.tan(theta2 / 2);

        double A0P = 4 * (R2 * R2 * Math.sin(theta) * Math.sin(theta) - (Tp + Lh) * (Tp + Lh));
        double B0P = (8 * R2 * R2 * (Tt * Math.cos(theta) - Tp)) + (4 * (L * L - Lh * Lh) * (Tp + Lh));
        double C0P = (8 * R2 * R2 * Lh * (Tt + Lh)) + (4 * R2 * R2 * (L * L - Lh * Lh - (Tt + Lh) * (Tt + Lh))) - ((L * L - Lh * Lh) * (L * L - Lh * Lh));

        double delta0P = B0P * B0P - 4 * A0P * C0P;
        if (delta0P < 0) {
            System.out.println("无解");
            return;
        }
        Lp0 = (-1 * B0P - Math.sqrt(delta0P)) / (2 * A0P);

        double A0Q = 4 * (R1 * R1 * Math.sin(theta) * Math.sin(theta) - (Tt + Lh) * (Tt + Lh));
        double B0Q = (8 * R1 * R1 * (Tp * Math.cos(theta) - Tt)) + (4 * (L * L - Lh * Lh) * (Tt + Lh));
        double C0Q = (8 * R1 * R1 * Lh * (Tp + Lh)) + (4 * R1 * R1 * (L * L - Lh * Lh - (Tp + Lh) * (Tp + Lh))) - ((L * L - Lh * Lh) * (L * L - Lh * Lh));
        double delta0Q = B0Q * B0Q - 4 * A0Q * C0Q;
        if (delta0Q < 0) {
            System.out.println("无解");
            return;
        }
        Lq0 = (-1 * B0Q - Math.sqrt(delta0Q)) / (2 * A0Q);

        double A1P = ((4 * R1 * R1 - L * L + Lh * Lh) * (1 - Math.cos(theta))) - (2 * (Ts + Lh) * (Tl + Lh));
        double B1P = 4 * R1 * R1 * (Tl - Ts + Lh * (1 - Math.cos(theta)));
        double C1P = R1 * R1 * ((((L * L - Lh * Lh) * (1 + Math.cos(theta))) - (2 * (Ts - Lh) * (Tt + Lh))));
        double delta1P = B1P * B1P - 4 * A1P * C1P;
        if (delta1P < 0) {
            System.out.println("无解");
            return;
        }
        Lp1 = (-1 * B1P - Math.sqrt(delta1P)) / (2 * A1P);

        double A1Q = ((4 * R2 * R2 - L * L + Lh * Lh) * (1 - Math.cos(theta))) - (2 * (Ts + Lh) * (Tl + Lh));
        double B1Q = 4 * R2 * R2 * (Tl - Ts + Lh * (1 - Math.cos(theta)));
        double C1Q = R2 * R2 * ((((L * L - Lh * Lh) * (1 + Math.cos(theta))) - (2 * (Ts - Lh) * (Tt + Lh))));
        double delta1Q = B1Q * B1Q - 4 * A1Q * C1Q;
        if (delta1Q < 0) {
            System.out.println("无解");
            return;
        }
        Lq1 = (-1 * B1Q - Math.sqrt(delta1Q)) / (2 * A1Q);
    */
}
