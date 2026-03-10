package com.ruoyi.hh.hhhz.tools.drilling;


import com.ruoyi.hh.hhhz.domain.NormalDrillingData;
import com.ruoyi.hh.hhhz.domain.Point;
import com.ruoyi.hh.hhhz.domain.param.BeddingDrillingParam;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.ruoyi.hh.hhhz.tools.MathUtils.rad2ang;

/**
 * 顺层钻孔设计
 */
@Component
public class BeddingDrilling {
    /**
     * 煤层厚度
     */
    /*private double hc = 2.9;
     *//**
     * 煤层倾角
     *//*
    private double alf = ang2rad(15);
    *//**
     * 顺槽方位角
     *//*
    private double belt = ang2rad(280);
    *//**
     * 顺槽宽
     *//*
    private double wm = 4.6;
    *//**
     * 顺槽墙高1
     *//*
    private double hm1 = 3;
    *//**
     * 顺槽墙高1
     *//*
    private double hm2 = 3;
    *//**
     * 抽采半径
     *//*
    private double radius = 2.5;
    *//**
     * 抽采控制范围
     *//*
    private double lx = 70;
    *//**
     * 上顺槽|下顺槽
     *//*
    private boolean upBedding = false;
    */

    /**
     * 顺槽长度
     *//*
    private double scLen = 20;*/
    public BeddingDrillingParam calDrilling(BeddingDrillingParam param) {
        if (param.hc / param.radius > 2) {
            System.out.println("煤层过厚，建议使用其他治理方案");
            return null;
        }
        // 排间距
        double lw = 0;
        // 计算布孔间距
        double s = 2 * Math.sqrt((param.radius * param.radius) - ((lw / 2) * (lw / 2)));
        // 计算开孔高度
        double hk = 0;
        double ang = rad2ang(param.belt);
        if (param.upBedding) {
            System.out.println("位置：上顺槽");
            hk = param.hc / 2.0;
            ang = (ang + 270) > 360 ? (ang + 270) - 360 : (ang + 270);
        } else {
            System.out.println("位置：下顺槽");
            hk = (Math.tan(param.alf) * param.wm) + (param.hc / 2.0);
            ang = (ang + 90) > 360 ? (ang + 90) - 360 : (ang + 90);
        }
        System.out.println("开孔高度：" + hk);
        System.out.println("穿煤长度：" + param.lx);
        System.out.println("倾角：" + rad2ang(param.alf));
        System.out.println("方位角：" + ang);
        double kzfw = 0;
        int i = 1;
        ArrayList<NormalDrillingData> drillingData = new ArrayList<>();
        List<Point> points = new ArrayList<>();
        int num = 1;
        while (kzfw <= param.scLen) {
            if (param.upBedding) {
                //System.out.println("upBedding");
                Point point = new Point();
                point.setX(param.wm / 2);
                point.setY((s / 2) + ((i - 1) * s));
                point.setZ(hk);
                points.add(point);
                kzfw = s * i;
                i++;
                // 起始点
                //Point pointp = new Point(point.x, point.z, point.y);
                System.out.println(point + ",");
                // 终点
                Point point1 = new Point(((param.wm / 2) + (param.lx * Math.cos(param.alf))), ((s / 2) + ((i - 1) * s)), hk + (param.lx * Math.sin(param.alf)));
                System.out.println(point1 + ",");
                NormalDrillingData data = new NormalDrillingData(0, num, ang, rad2ang(param.alf), 0, param.lx, 0.5, param.lx, hk, point, point, point1);
                num++;
                drillingData.add(data);
            } else {
                //System.out.println("!upBedding");
                Point point = new Point();
                point.setX(-param.wm / 2);
                point.setY((s / 2) + ((i - 1) * s));
                point.setZ(hk);
                points.add(point);
                kzfw = s * i;
                i++;
                // 起始点
                System.out.println(point + ",");
                // 终点
                Point point1 = new Point((-((param.wm / 2) + (param.lx * Math.cos(param.alf)))), ((s / 2) + ((i - 1) * s)), hk - (param.lx * Math.sin(param.alf)));
                System.out.println(point1 + ",");
                NormalDrillingData data = new NormalDrillingData(0, num, ang, rad2ang(param.alf), 0, param.lx, 0.5, param.lx, hk, point, point, point1);
                num++;
                drillingData.add(data);
            }
        }
        param.drillingData = drillingData;
        /*for (Point point : points) {
            System.out.println(point);
        }*/
        return param;
    }

    public static void main(String[] args) {
        BeddingDrilling drilling = new BeddingDrilling();
        //drilling.init();
    }
}
