package com.ruoyi.hh.hhhz.tools.drilling;


import com.ruoyi.hh.hhhz.domain.NormalDrillingData;
import com.ruoyi.hh.hhhz.domain.Point;
import com.ruoyi.hh.hhhz.domain.Vector3;
import com.ruoyi.hh.hhhz.domain.param.BandGovernanceDrillingParam;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ruoyi.hh.hhhz.tools.MathUtils.*;


/**
 * 条带治理钻孔设计(常规穿层钻孔设计)
 */
@Component
public class SingleDrilling {

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

    /*
     */
/**
 * 煤层厚度（hc）
 *//*

    private double hc = 4.3;
    */
/**
 * 煤层倾角(α,[0,90))
 *//*

    //private double alf = Math.PI / (180 * 12);
    private double alf = ang2rad(15);
    */
/**
 * 煤层底板到底抽巷间距(h2)
 *//*

    private double h2 = 15;
    */
/**
 * 底抽巷宽（wd）
 *//*

    private double wd = 4.6;
    */
/**
 * 底抽巷拱高（hg=w*0.5）
 *//*

    private double hg = wd * 0.5;
    */
/**
 * 底抽巷高（hd）
 *//*

    private double hd = 3;
    */
/**
 * 底抽巷方位角（βd）
 *//*

    private double betd = 0;
    */
/**
 * 煤巷宽 （wm）
 *//*

    private double wm = 5;
    */
/**
 * 煤巷高（hm）
 *//*

    private double hm = 3;
    */
/**
 * 抽采半径(r)
 *//*

    private double radius = 2.5;
    */
/**
 * 底抽巷与煤巷水平投影间距(L)
 *//*

    private double l = 10;
    */
/**
 * 抽采控制范围(Lx)
 *//*

    private double lx = 20;
    */
/**
 * 抽采控制范围(Ls)
 *//*

    private double ls = 20;
    */
/**
 * 方形布置：止煤点间距（s4）
 *//*

    private double s4;
    */
/**
 * 方形布置：排间距（lw4）
 *//*

    private double lw4;
    */
/**
 * 三角布置：止煤点间距（s3）
 *//*

    private double s3;
    */
/**
 * 三角布置：排间距（lw3）
 *//*

    private double lw3;
    */
/**
 * 内错|外错
 *//*

    private boolean neicuo = true;
    private double numL4;
    private double numR4;
    private double numL3;
    private double numR3;

    private double x1;
    private double y;
    private double z1;

    private double lL;
    private double lR;
    private Point oPoint;

    */
    /**
     * 布孔类型: 3=三花布孔,4=方形布孔
     *//*

    private int type = 4;
*/
    private static final double gen2 = Math.sqrt(2);
    private static final double gen3 = Math.sqrt(3);

    /**
     * 计算条带穿层钻孔
     *
     * @return 施工数据
     */
    public BandGovernanceDrillingParam calDrilling(BandGovernanceDrillingParam param) {
        /*double lL,lR;
        double s3, lw3, s4, lw4;*/
        // 预计算
        param = preCal(param);

        // 按布孔方式不同计算
        if (param.type == 4) {
            calDrilling4(param);
        } else if (param.type == 3) {
            calDrilling3(param);
        } else if (param.type == 41) {
            calDrilling41(param);
            //calDrilling4(param);
        } else if (param.type == 31) {
            calDrilling31(param);
            //calDrilling4(param);
        } else if (param.type == 32) {
            calDrilling32(param);
        }
        return param;
    }

    /**
     * 预计算, 计算各种参数
     *
     * @return 验证孔位置
     */
    public BandGovernanceDrillingParam preCal(BandGovernanceDrillingParam param) {
        if (rad2ang(param.alf) >= 90) {
            System.out.println("煤层倾角应小于90°");
            return null;
        }
        // 设置止煤点间距和排间距
        // 方形布孔
        param.s4 = gen2 * param.radius;
        param.lw4 = gen2 * param.radius;
        //System.out.println("s4 = " + s4 + " lw4 = " + lw4);
        // 三花布孔
        param.s3 = Math.sqrt(3) * param.radius;
        param.lw3 = 1.5 * param.radius;
        //System.out.println("s3 = " + s3 + " lw3 = " + lw3);

        // 钻机原点位置
        //oPoint = new Point(0, 0, 0);
        Point oPoint = param.oPoint;
        System.out.println("原点：oPoint = " + oPoint);
        if (((oPoint.getX() * oPoint.getX()) + (oPoint.getY() * oPoint.getY())) > (param.radius * param.radius)) {
            System.out.println("钻机位置设置错误，超出巷道外");
        }

        // 煤层底板
        double dFA = Math.tan(Math.PI - param.alf);
        double dFB = -1;
        double dFC = (param.h2 + param.hg) / Math.cos(param.alf);
        param.dFA = dFA;
        param.dFB = dFB;
        param.dFC = dFC;
        // 煤层顶板
        double tFA = Math.tan(Math.PI - param.alf);
        double tFB = -1;
        double tFC = (param.hc + param.h2 + param.hg) / Math.cos(param.alf);
        param.tFA = tFA;
        param.tFB = tFB;
        param.tFC = tFC;

        // 定位孔位置
        //Point verifyPoint = new Point();
        Point verifyPoint = param.verifyPoint;
        if (param.neicuo) {
            verifyPoint.setX(-param.l);
            verifyPoint.setY(((-param.l) * Math.tan(Math.PI - param.alf)) + ((param.h2 + param.hc + param.hg) / Math.cos(param.alf)));
            verifyPoint.setZ(0);
            System.out.println("煤巷位置：内错");
            System.out.println("验证孔：verifyPoint = " + verifyPoint);
        } else {
            verifyPoint.setX(param.l);
            verifyPoint.setY((param.l * Math.tan(Math.PI - param.alf)) + ((param.h2 + param.hc + param.hg) / Math.cos(param.alf)));
            verifyPoint.setZ(0);
            System.out.println("煤巷位置：外错");
            System.out.println("验证孔：verifyPoint = " + verifyPoint);
        }

        // 煤巷左下点位置
        Point coalRoadLP = new Point();
        coalRoadLP.setX(verifyPoint.getX() - (param.wm / 2));
        coalRoadLP.setY(verifyPoint.getY() - param.hm - ((param.wm / 2) * Math.tan(param.alf)));
        System.out.println("煤巷左下点：coalRoadLP = " + coalRoadLP);
        // 煤巷右下角位置
        Point coalRoadRP = new Point();
        coalRoadRP.setX(verifyPoint.getX() + (param.wm / 2));
        coalRoadRP.setY(verifyPoint.getY() - param.hm - ((param.wm / 2) * Math.tan(param.alf)));
        System.out.println("煤巷右下点：coalRoadRP = " + coalRoadRP);
        // 左下控制点
        Point ctlLP = new Point();
        // 煤层底板垂线投影点当控制点
        //ctlLP.setX((dFB * dFB * coalRoadLP.getX() - dFA * dFB * coalRoadLP.getY() - dFA * dFC) / ((dFA * dFA) + (dFB * dFB)));
        //ctlLP.setY((dFA * dFA * coalRoadLP.getY() - dFA * dFB * coalRoadLP.getX() - dFB * dFC) / ((dFA * dFA) + (dFB * dFB)));
        // 沿Y轴投影到煤层底板点当控制点
        ctlLP.setX(coalRoadLP.getX());
        ctlLP.setY(((dFA * coalRoadLP.getX()) + dFC) / (-dFB));
        System.out.println("新左下控制点：" + ctlLP);
        // 右下控制点
        Point ctlRP = new Point();
        // 煤层底板垂线投影点当控制点
        //ctlRP.setX((dFB * dFB * coalRoadRP.getX() - dFA * dFB * coalRoadRP.getY() - dFA * dFC) / ((dFA * dFA) + (dFB * dFB)));
        //ctlRP.setY((dFA * dFA * coalRoadRP.getY() - dFA * dFB * coalRoadRP.getX() - dFB * dFC) / ((dFA * dFA) + (dFB * dFB)));
        ctlRP.setX(coalRoadRP.getX());
        ctlRP.setY(((dFA * coalRoadRP.getX()) + dFC) / (-dFB));
        System.out.println("新右下控制点：" + ctlRP);

        // 左边界点
        Point borderLP = new Point();
        borderLP.setX(ctlLP.getX() - (param.ls * Math.cos(param.alf)));
        borderLP.setY(ctlLP.getY() + (param.ls * Math.sin(param.alf)));
        System.out.println("左下边界点：" + borderLP);
        Point borderRP = new Point();
        borderRP.setX(ctlRP.getX() + (param.lx * Math.cos(param.alf)));
        borderRP.setY(ctlRP.getY() - (param.lx * Math.sin(param.alf)));
        System.out.println("右下边界点：" + borderRP);

        // 左边界线  y=kx
        double lk = borderLP.getY() / borderLP.getX();
        //System.out.println("lk:" + lk);
        double borderLRad = Math.atan(lk);
        //System.out.println("borderLRad：" + borderLRad);
        double borderLAng = rad2ang(borderLRad);
        System.out.println("borderLAng：" + borderLAng);

        // 右边界线  y=kx
        double rk = borderRP.getY() / borderRP.getX();
        //System.out.println("rk:" + rk);
        double borderRRad = Math.atan(rk);
        //System.out.println("borderRRad：" + borderRRad);
        double borderRAng = rad2ang(borderRRad);
        System.out.println("borderRAng：" + borderRAng);

        // 计算边界线角度
        double roL = Math.abs(borderLRad) - param.alf;
        double roR = Math.abs(borderRRad) + param.alf;

        System.out.println("与煤层夹角roL：" + rad2ang(roL));
        System.out.println("与煤层夹角roR：" + rad2ang(roR));

        // 计算控制范围
        // 特别注意,一侧减(param.hc * Math.tan(param.alf)),一侧是加
        param.lL = param.ls + (param.wm / (2 * Math.cos(param.alf))) - (param.hc * Math.tan(param.alf)) + (param.hc / Math.tan(roL));
        param.lR = param.lx + (param.wm / (2 * Math.cos(param.alf))) + (param.hc * Math.tan(param.alf)) + (param.hc / Math.tan(roR));

        Point borderTopLeftPoint = new Point();
        borderTopLeftPoint.setX(verifyPoint.x - param.lL * Math.cos(param.alf));
        borderTopLeftPoint.setY(verifyPoint.y + param.lL * Math.sin(param.alf));
        param.topLeft = borderTopLeftPoint;
        System.out.println("borderTopLeftPoint:" + borderTopLeftPoint);
        Point borderTopRightPoint = new Point();
        borderTopRightPoint.setX(verifyPoint.x + param.lR * Math.cos(param.alf));
        borderTopRightPoint.setY(verifyPoint.y - param.lR * Math.sin(param.alf));
        param.topRight = borderTopRightPoint;
        System.out.println("borderTopRightPoint = " + borderTopRightPoint);

        System.out.println("控制范围：");
        System.out.println("lL:" + param.lL);
        System.out.println("lR:" + param.lR);

        return param;
    }

    /**
     * 计算正方形布孔
     */
    private BandGovernanceDrillingParam calDrilling4(BandGovernanceDrillingParam param/*, double lL, double lR, double s4, double alf*/) {
        // 正方形布置
        param.numL4 = (int) (Math.ceil(param.lL / param.s4));
        param.numR4 = (int) (Math.ceil(param.lR / param.s4));
        System.out.println("布孔个数：L4:" + param.numL4 + ",R4:" + param.numR4);
        // 计算出煤点
        ArrayList<Point> points4 = new ArrayList<>();
        for (int i = 1; i <= param.numL4; i++) {
            double xx = param.verifyPoint.getX() - (Math.cos(param.alf) * (i * param.s4));
            double yy = param.verifyPoint.getY() + (Math.sin(param.alf) * (i * param.s4));
            double zz = param.verifyPoint.getZ();
            points4.add(new Point(xx, yy, zz));
        }
        for (int i = 1; i <= param.numR4; i++) {
            double xx = param.verifyPoint.getX() + (Math.cos(param.alf) * (i * param.s4));
            double yy = param.verifyPoint.getY() - (Math.sin(param.alf) * (i * param.s4));
            double zz = param.verifyPoint.getZ();
            points4.add(new Point(xx, yy, zz));
        }
        // 添加验证孔
        points4.add(param.verifyPoint);
        Collections.sort(points4);
        System.out.println("正方形布孔:" + points4.size());
        Collections.sort(points4);
        ArrayList<NormalDrillingData> drillingData = new ArrayList<>();
        int num = 1;
        for (Point point : points4) {
            NormalDrillingData data = this.calData(param, point);
            data.setDrillId(num);
            num++;
            drillingData.add(data);
        }
        param.drillingData = drillingData;
        return param;
    }

    /**
     * 计算正方形考虑角度
     */
    private BandGovernanceDrillingParam calDrilling41(BandGovernanceDrillingParam param) {
        ArrayList<Point> points41 = new ArrayList<>();
        Point vp = param.verifyPoint;
        Point desDrill = vp;
        System.out.println("向左侧推孔位置...");
        System.out.println("左侧距离:" + calP2PDis(param.topLeft, param.verifyPoint));
        while (calP2PDis(desDrill, param.verifyPoint) < calP2PDis(param.topLeft, param.verifyPoint)) {
            double vpAngle = calAng(new Vector3(new Point(vp.x, vp.y), new Point(0, 0)), new Vector3(new Point(vp.x, vp.y), param.topLeft));
            if (rad2ang(vpAngle) < 90) {
                double l2lAng = vpAngle;
                System.out.println("与煤层左向角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = gen2 * param.radius / (2 * Math.sin(l2lAng)) + (gen2 * param.radius) * 0.465f;
                Point point = calNewPoint(new Vector3(vp, param.topLeft), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points41.add(desDrill);
            } else {
                //System.out.println("验证孔位置 = " + vp + ",验证孔角度:" + rad2ang(Math.atan2(vp.y, vp.x)));
                double rradius = gen2 * param.radius / 2;
                double nvx = vp.x - (rradius * Math.cos(param.alf));
                double nvy = vp.y + (rradius * Math.sin(param.alf));
                //System.out.println("0圆心位置:(" + nvx + "," + nvy + ")");
                double dis = Math.sqrt((nvx * nvx) + (nvy * nvy));
                //System.out.println("0斜边 = " + dis);
                double ang1 = Math.atan2(nvy, nvx);
                //System.out.println("1大-斜率角度 = " + rad2ang(ang1));
                double ang2 = Math.asin(rradius / dis);
                //System.out.println("1小-三角角度 = " + rad2ang(ang2));
                double angAng = ang1 + ang2;
                //System.out.println("钻孔角度 = " + rad2ang(angAng));
                // y = tan(angAng)x
                // x=(B1C2-C1B2)/(A1B2-A2B1)
                // y=(A1C2-A2C1)/(A2B1-A1B2)
                double xd = ((-1 * param.dFC) - (0 * param.dFB)) / ((Math.tan(angAng) * param.dFB) - (param.dFA * (-1)));
                double yd = ((Math.tan(angAng) * param.dFC) - (param.dFA * 0)) / ((param.dFA * (-1)) - (Math.tan(angAng) * param.dFB));
                //System.out.println("与底板交点:(" + xd + "," + yd + ")");
                double xt = ((-1 * param.tFC) - (0 * param.tFB)) / ((Math.tan(angAng) * param.tFB) - (param.tFA * (-1)));
                double yt = ((Math.tan(angAng) * param.tFC) - (param.tFA * 0)) / ((param.tFA * (-1)) - (Math.tan(angAng) * param.tFB));
                //System.out.println("与顶板交点:(" + xt + "," + yt + ")");
                double l2lAng = calAng(new Vector3(new Point(xt, yt), new Point(nvx, nvy)), new Vector3(new Point(xt, yt), new Point(xd, yd)));
                if (rad2ang(l2lAng) > 90) {
                    l2lAng = Math.PI - l2lAng;
                }
                System.out.println("与煤层角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = gen2 * param.radius / (2 * Math.sin(l2lAng)) + (gen2 * param.radius) * 0.465f;
                Point point = calNewPoint(new Vector3(vp, new Point(xt, yt)), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points41.add(desDrill);
            }
        }

        System.out.println("向右侧推孔位置...");
        vp = param.verifyPoint;
        desDrill = vp;
        System.out.println("右侧距离:" + calP2PDis(param.topRight, param.verifyPoint));
        while (calP2PDis(desDrill, param.verifyPoint) < calP2PDis(param.topRight, param.verifyPoint)) {
            double vpAngle = calAng(new Vector3(new Point(vp.x, vp.y), new Point(0, 0)), new Vector3(new Point(vp.x, vp.y), param.topRight));
            if (rad2ang(vpAngle) < 90) {
                double l2lAng = vpAngle;
                System.out.println("与煤层右向角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = gen2 * param.radius / (2 * Math.sin(l2lAng)) + (gen2 * param.radius) * 0.465f;
                Point point = calNewPoint(new Vector3(vp, param.topRight), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points41.add(desDrill);
            } else {
                //System.out.println("验证孔位置 = " + vp + ",验证孔角度:" + rad2ang(Math.atan2(vp.y, vp.x)));
                double rradius = gen2 * param.radius / 2;
                double nvx = vp.x + (rradius * Math.cos(param.alf));
                double nvy = vp.y - (rradius * Math.sin(param.alf));
                //System.out.println("0圆心位置:(" + nvx + "," + nvy + ")");
                double dis = Math.sqrt((nvx * nvx) + (nvy * nvy));
                //System.out.println("0斜边 = " + dis);
                double ang1 = Math.atan2(nvy, nvx);
                //System.out.println("1大-斜率角度 = " + rad2ang(ang1));
                double ang2 = Math.asin(rradius / dis);
                //System.out.println("1小-三角角度 = " + rad2ang(ang2));
                double angAng = ang1 + ang2;
                //System.out.println("钻孔角度 = " + rad2ang(angAng));
                // y = tan(angAng)x
                // x=(B1C2-C1B2)/(A1B2-A2B1)
                // y=(A1C2-A2C1)/(A2B1-A1B2)
                double xd = ((-1 * param.dFC) - (0 * param.dFB)) / ((Math.tan(angAng) * param.dFB) - (param.dFA * (-1)));
                double yd = ((Math.tan(angAng) * param.dFC) - (param.dFA * 0)) / ((param.dFA * (-1)) - (Math.tan(angAng) * param.dFB));
                //System.out.println("与底板交点:(" + xd + "," + yd + ")");
                double xt = ((-1 * param.tFC) - (0 * param.tFB)) / ((Math.tan(angAng) * param.tFB) - (param.tFA * (-1)));
                double yt = ((Math.tan(angAng) * param.tFC) - (param.tFA * 0)) / ((param.tFA * (-1)) - (Math.tan(angAng) * param.tFB));
                //System.out.println("与顶板交点:(" + xt + "," + yt + ")");
                double l2lAng = calAng(new Vector3(new Point(xt, yt), new Point(nvx, nvy)), new Vector3(new Point(xt, yt), new Point(xd, yd)));
                if (rad2ang(l2lAng) > 90) {
                    l2lAng = Math.PI - l2lAng;
                }
                System.out.println("与煤层角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = gen2 * param.radius / (2 * Math.sin(l2lAng)) + (gen2 * param.radius) * 0.465f;
                Point point = calNewPoint(new Vector3(vp, param.topRight), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points41.add(desDrill);
            }
        }
        points41.add(param.verifyPoint);
        Collections.sort(points41);
        ArrayList<NormalDrillingData> drillingData = new ArrayList<>();
        /*for (Point point : allPoint) {
            drillingData.add(this.calData(param, point));
        }*/
        int num = 1;
        for (Point point : points41) {
            NormalDrillingData data = this.calData(param, point);
            data.setDrillId(num);
            num++;
            drillingData.add(data);
        }
        param.drillingData = drillingData;
        return param;
    }


    /**
     * 计算三花布孔考虑角度, 横向
     */
    private BandGovernanceDrillingParam calDrilling31(BandGovernanceDrillingParam param) {
        ArrayList<Point> points31 = new ArrayList<>();
        Point vp = param.verifyPoint;
        Point desDrill = vp;
        System.out.println("向左侧推孔位置...");
        System.out.println("左侧距离:" + calP2PDis(param.topLeft, param.verifyPoint));
        while (calP2PDis(desDrill, param.verifyPoint) < calP2PDis(param.topLeft, param.verifyPoint)) {
            double vpAngle = calAng(new Vector3(new Point(vp.x, vp.y), new Point(0, 0)), new Vector3(new Point(vp.x, vp.y), param.topLeft));
            if (rad2ang(vpAngle) < 90) {
                double l2lAng = vpAngle;
                System.out.println("与煤层左向角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = (0.6 * ((param.radius / Math.sin(l2lAng)) - param.radius)) + (gen3 * param.radius);
                Point point = calNewPoint(new Vector3(vp, param.topLeft), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points31.add(desDrill);
            } else {
                //System.out.println("验证孔位置 = " + vp + ",验证孔角度:" + rad2ang(Math.atan2(vp.y, vp.x)));
                double rradius = gen3 * param.radius / 2;
                double nvx = vp.x - (rradius * Math.cos(param.alf));
                double nvy = vp.y + (rradius * Math.sin(param.alf));
                //System.out.println("0圆心位置:(" + nvx + "," + nvy + ")");
                double dis = Math.sqrt((nvx * nvx) + (nvy * nvy));
                //System.out.println("0斜边 = " + dis);
                double ang1 = Math.atan2(nvy, nvx);
                //System.out.println("1大-斜率角度 = " + rad2ang(ang1));
                double ang2 = Math.asin(rradius / dis);
                //System.out.println("1小-三角角度 = " + rad2ang(ang2));
                double angAng = ang1 + ang2;
                //System.out.println("钻孔角度 = " + rad2ang(angAng));
                // y = tan(angAng)x
                // x=(B1C2-C1B2)/(A1B2-A2B1)
                // y=(A1C2-A2C1)/(A2B1-A1B2)
                double xd = ((-1 * param.dFC) - (0 * param.dFB)) / ((Math.tan(angAng) * param.dFB) - (param.dFA * (-1)));
                double yd = ((Math.tan(angAng) * param.dFC) - (param.dFA * 0)) / ((param.dFA * (-1)) - (Math.tan(angAng) * param.dFB));
                //System.out.println("与底板交点:(" + xd + "," + yd + ")");
                double xt = ((-1 * param.tFC) - (0 * param.tFB)) / ((Math.tan(angAng) * param.tFB) - (param.tFA * (-1)));
                double yt = ((Math.tan(angAng) * param.tFC) - (param.tFA * 0)) / ((param.tFA * (-1)) - (Math.tan(angAng) * param.tFB));
                //System.out.println("与顶板交点:(" + xt + "," + yt + ")");
                double l2lAng = calAng(new Vector3(new Point(xt, yt), new Point(nvx, nvy)), new Vector3(new Point(xt, yt), new Point(xd, yd)));
                if (rad2ang(l2lAng) > 90) {
                    l2lAng = Math.PI - l2lAng;
                }
                System.out.println("与煤层角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = (0.6 * (param.radius / Math.sin(l2lAng) - param.radius)) + (gen3 * param.radius);
                Point point = calNewPoint(new Vector3(vp, new Point(xt, yt)), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points31.add(desDrill);
            }
        }

        System.out.println("向右侧推孔位置...");
        vp = param.verifyPoint;
        desDrill = vp;
        System.out.println("右侧距离:" + calP2PDis(param.topRight, param.verifyPoint));
        while (calP2PDis(desDrill, param.verifyPoint) < calP2PDis(param.topRight, param.verifyPoint)) {
            double vpAngle = calAng(new Vector3(new Point(vp.x, vp.y), new Point(0, 0)), new Vector3(new Point(vp.x, vp.y), param.topRight));
            if (rad2ang(vpAngle) < 90) {
                double l2lAng = vpAngle;
                System.out.println("与煤层右向角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = (0.6 * (param.radius / Math.sin(l2lAng) - param.radius)) + (gen3 * param.radius);
                Point point = calNewPoint(new Vector3(vp, param.topRight), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points31.add(desDrill);
            } else {
                //System.out.println("验证孔位置 = " + vp + ",验证孔角度:" + rad2ang(Math.atan2(vp.y, vp.x)));
                double rradius = gen2 * param.radius / 2;
                double nvx = vp.x + (rradius * Math.cos(param.alf));
                double nvy = vp.y - (rradius * Math.sin(param.alf));
                //System.out.println("0圆心位置:(" + nvx + "," + nvy + ")");
                double dis = Math.sqrt((nvx * nvx) + (nvy * nvy));
                //System.out.println("0斜边 = " + dis);
                double ang1 = Math.atan2(nvy, nvx);
                //System.out.println("1大-斜率角度 = " + rad2ang(ang1));
                double ang2 = Math.asin(rradius / dis);
                //System.out.println("1小-三角角度 = " + rad2ang(ang2));
                double angAng = ang1 + ang2;
                //System.out.println("钻孔角度 = " + rad2ang(angAng));
                // y = tan(angAng)x
                // x=(B1C2-C1B2)/(A1B2-A2B1)
                // y=(A1C2-A2C1)/(A2B1-A1B2)
                double xd = ((-1 * param.dFC) - (0 * param.dFB)) / ((Math.tan(angAng) * param.dFB) - (param.dFA * (-1)));
                double yd = ((Math.tan(angAng) * param.dFC) - (param.dFA * 0)) / ((param.dFA * (-1)) - (Math.tan(angAng) * param.dFB));
                //System.out.println("与底板交点:(" + xd + "," + yd + ")");
                double xt = ((-1 * param.tFC) - (0 * param.tFB)) / ((Math.tan(angAng) * param.tFB) - (param.tFA * (-1)));
                double yt = ((Math.tan(angAng) * param.tFC) - (param.tFA * 0)) / ((param.tFA * (-1)) - (Math.tan(angAng) * param.tFB));
                //System.out.println("与顶板交点:(" + xt + "," + yt + ")");
                double l2lAng = calAng(new Vector3(new Point(xt, yt), new Point(nvx, nvy)), new Vector3(new Point(xt, yt), new Point(xd, yd)));
                if (rad2ang(l2lAng) > 90) {
                    l2lAng = Math.PI - l2lAng;
                }
                System.out.println("与煤层角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = (0.6 * (param.radius / Math.sin(l2lAng) - param.radius)) + (gen3 * param.radius);
                Point point = calNewPoint(new Vector3(vp, param.topRight), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points31.add(desDrill);
            }
        }
        // 添加验证孔
        points31.add(param.verifyPoint);
        Collections.sort(points31);
        ArrayList<Point> allPoints = new ArrayList<>();
        // 计算第二排孔位置
        for (int i = 0; i < points31.size(); i++) {
            allPoints.add(points31.get(i));
            if (i != points31.size() - 1) {
                Point point = calNewPoint(new Vector3(param.topRight, param.topLeft), points31.get(i), gen3 * param.radius * 0.5);
                allPoints.add(point);
            }
        }
        ArrayList<NormalDrillingData> drillingData = new ArrayList<>();
        /*for (Point point : allPoint) {
            drillingData.add(this.calData(param, point));
        }*/
        int num = 1;
        for (Point point : allPoints) {
            NormalDrillingData data = this.calData(param, point);
            data.setDrillId(num);
            num++;
            drillingData.add(data);
        }
        param.drillingData = drillingData;
        return param;
    }


    /**
     * 计算三花布孔考虑角度, 纵向
     */
    private BandGovernanceDrillingParam calDrilling32(BandGovernanceDrillingParam param) {
        ArrayList<Point> points32 = new ArrayList<>();
        Point vp = param.verifyPoint;
        Point desDrill = vp;
        System.out.println("向左侧推孔位置...");
        System.out.println("左侧距离:" + calP2PDis(param.topLeft, param.verifyPoint));
        while (calP2PDis(desDrill, param.verifyPoint) < calP2PDis(param.topLeft, param.verifyPoint)) {
            double vpAngle = calAng(new Vector3(new Point(vp.x, vp.y), new Point(0, 0)), new Vector3(new Point(vp.x, vp.y), param.topLeft));
            if (rad2ang(vpAngle) < 90) {
                double l2lAng = vpAngle;
                System.out.println("与煤层左向角度:" + rad2ang(l2lAng));
                // 计算孔间距
                // Ratio * (radius / Mathf.Sin(angle * Mathf.Deg2Rad) - radius) + 1.5f * radius;
                double space = (0.465 * ((param.radius / Math.sin(l2lAng)) - param.radius)) + (1.5 * param.radius);
                Point point = calNewPoint(new Vector3(vp, param.topLeft), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points32.add(desDrill);
            } else {
                //System.out.println("验证孔位置 = " + vp + ",验证孔角度:" + rad2ang(Math.atan2(vp.y, vp.x)));
                double rradius = param.radius / 2.;
                double nvx = vp.x - (rradius * Math.cos(param.alf));
                double nvy = vp.y + (rradius * Math.sin(param.alf));
                //System.out.println("0圆心位置:(" + nvx + "," + nvy + ")");
                double dis = Math.sqrt((nvx * nvx) + (nvy * nvy));
                //System.out.println("0斜边 = " + dis);
                double ang1 = Math.atan2(nvy, nvx);
                //System.out.println("1大-斜率角度 = " + rad2ang(ang1));
                double ang2 = Math.asin(rradius / dis);
                //System.out.println("1小-三角角度 = " + rad2ang(ang2));
                double angAng = ang1 + ang2;
                //System.out.println("钻孔角度 = " + rad2ang(angAng));
                // y = tan(angAng)x
                // x=(B1C2-C1B2)/(A1B2-A2B1)
                // y=(A1C2-A2C1)/(A2B1-A1B2)
                double xd = ((-1 * param.dFC) - (0 * param.dFB)) / ((Math.tan(angAng) * param.dFB) - (param.dFA * (-1)));
                double yd = ((Math.tan(angAng) * param.dFC) - (param.dFA * 0)) / ((param.dFA * (-1)) - (Math.tan(angAng) * param.dFB));
                //System.out.println("与底板交点:(" + xd + "," + yd + ")");
                double xt = ((-1 * param.tFC) - (0 * param.tFB)) / ((Math.tan(angAng) * param.tFB) - (param.tFA * (-1)));
                double yt = ((Math.tan(angAng) * param.tFC) - (param.tFA * 0)) / ((param.tFA * (-1)) - (Math.tan(angAng) * param.tFB));
                //System.out.println("与顶板交点:(" + xt + "," + yt + ")");
                double l2lAng = calAng(new Vector3(new Point(xt, yt), new Point(nvx, nvy)), new Vector3(new Point(xt, yt), new Point(xd, yd)));
                if (rad2ang(l2lAng) > 90) {
                    l2lAng = Math.PI - l2lAng;
                }
                System.out.println("与煤层角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = ((0.465 * ((param.radius / Math.sin(l2lAng))) - param.radius)) + (1.5 * param.radius);
                Point point = calNewPoint(new Vector3(vp, new Point(xt, yt)), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points32.add(desDrill);
            }
        }

        System.out.println("向右侧推孔位置...");
        vp = param.verifyPoint;
        desDrill = vp;
        System.out.println("右侧距离:" + calP2PDis(param.topRight, param.verifyPoint));
        while (calP2PDis(desDrill, param.verifyPoint) < calP2PDis(param.topRight, param.verifyPoint)) {
            double vpAngle = calAng(new Vector3(new Point(vp.x, vp.y), new Point(0, 0)), new Vector3(new Point(vp.x, vp.y), param.topRight));
            if (rad2ang(vpAngle) < 90) {
                double l2lAng = vpAngle;
                System.out.println("与煤层右向角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = (0.465 * ((param.radius / Math.sin(l2lAng)) - param.radius)) + (1.5 * param.radius);
                Point point = calNewPoint(new Vector3(vp, param.topRight), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points32.add(desDrill);
            } else {
                //System.out.println("验证孔位置 = " + vp + ",验证孔角度:" + rad2ang(Math.atan2(vp.y, vp.x)));
                double rradius = param.radius / 2.;
                double nvx = vp.x + (rradius * Math.cos(param.alf));
                double nvy = vp.y - (rradius * Math.sin(param.alf));
                //System.out.println("0圆心位置:(" + nvx + "," + nvy + ")");
                double dis = Math.sqrt((nvx * nvx) + (nvy * nvy));
                //System.out.println("0斜边 = " + dis);
                double ang1 = Math.atan2(nvy, nvx);
                //System.out.println("1大-斜率角度 = " + rad2ang(ang1));
                double ang2 = Math.asin(rradius / dis);
                //System.out.println("1小-三角角度 = " + rad2ang(ang2));
                double angAng = ang1 + ang2;
                //System.out.println("钻孔角度 = " + rad2ang(angAng));
                // y = tan(angAng)x
                // x=(B1C2-C1B2)/(A1B2-A2B1)
                // y=(A1C2-A2C1)/(A2B1-A1B2)
                double xd = ((-1 * param.dFC) - (0 * param.dFB)) / ((Math.tan(angAng) * param.dFB) - (param.dFA * (-1)));
                double yd = ((Math.tan(angAng) * param.dFC) - (param.dFA * 0)) / ((param.dFA * (-1)) - (Math.tan(angAng) * param.dFB));
                //System.out.println("与底板交点:(" + xd + "," + yd + ")");
                double xt = ((-1 * param.tFC) - (0 * param.tFB)) / ((Math.tan(angAng) * param.tFB) - (param.tFA * (-1)));
                double yt = ((Math.tan(angAng) * param.tFC) - (param.tFA * 0)) / ((param.tFA * (-1)) - (Math.tan(angAng) * param.tFB));
                //System.out.println("与顶板交点:(" + xt + "," + yt + ")");
                double l2lAng = calAng(new Vector3(new Point(xt, yt), new Point(nvx, nvy)), new Vector3(new Point(xt, yt), new Point(xd, yd)));
                if (rad2ang(l2lAng) > 90) {
                    l2lAng = Math.PI - l2lAng;
                }
                System.out.println("与煤层角度:" + rad2ang(l2lAng));
                // 计算孔间距
                double space = (0.465 * ((param.radius / Math.sin(l2lAng)) - param.radius)) + (1.5 * param.radius);
                Point point = calNewPoint(new Vector3(vp, param.topRight), vp, space);
                System.out.println("设计钻孔坐标 = " + point);
                vp = point;
                desDrill = point;
                System.out.println("当前钻孔距离:" + calP2PDis(desDrill, param.verifyPoint));
                points32.add(desDrill);
            }
        }
        // 添加验证孔
        points32.add(param.verifyPoint);
        Collections.sort(points32);
        ArrayList<Point> allPoints = new ArrayList<>();
        // 计算第二排孔位置
        for (int i = 0; i < points32.size(); i++) {
            allPoints.add(points32.get(i));
            /*if (i != points32.size() - 1) {
                Point point = calNewPoint(new Vector3(param.topRight, param.topLeft), points32.get(i), gen3 * param.radius * 0.5);
                allPoints.add(point);
            }*/
        }
        ArrayList<NormalDrillingData> drillingData = new ArrayList<>();
        /*for (Point point : allPoint) {
            drillingData.add(this.calData(param, point));
        }*/
        int num = 1;
        for (Point point : allPoints) {
            NormalDrillingData data = this.calData(param, point);
            data.setDrillId(num);
            num++;
            drillingData.add(data);
        }
        param.drillingData = drillingData;
        return param;
    }


    /**
     * 计算三角布局，排间距1.5R，止煤点间距√3R
     */
    private BandGovernanceDrillingParam calDrilling3(BandGovernanceDrillingParam param) {
        // 三角布置
        param.numL3 = Math.ceil(((param.lL - (param.s3 / 2)) / param.s3)) + 1;
        param.numR3 = Math.ceil(((param.lR - (param.s3 / 2)) / param.s3)) + 1;
        System.out.println("布孔个数：L3:" + param.numL3 + ",R3:" + param.numR3);

        // 计算出煤点
        System.out.println("三花布孔");
        List<Point> allPoint = new ArrayList<>();

        for (int lnum = 1; lnum <= 2; lnum++) {
            System.out.println("排数：" + lnum);
            if (lnum % 2 == 0) {
                Point verifyPoint3o = new Point();
                verifyPoint3o.setX(param.verifyPoint.getX() + (Math.cos(param.alf) * (((Math.sqrt(3)) / 2) * param.radius)));
                verifyPoint3o.setY(param.verifyPoint.getY() - (Math.sin(param.alf) * (((Math.sqrt(3)) / 2) * param.radius)));
                verifyPoint3o.setZ(param.verifyPoint.getZ() + (lnum - 1) * 1.5 * param.radius);
                //System.out.println("verifyPoint = " + verifyPoint);
                //System.out.println("verifyPoint3o = " + verifyPoint3o);
                //System.out.println("dis v4->v3:" + Math.sqrt((verifyPoint.getX() - verifyPoint3o.getX()) * (verifyPoint.getX() - verifyPoint3o.getX()) + (verifyPoint.getY() - verifyPoint3o.getY()) * (verifyPoint.getY() - verifyPoint3o.getY())));
                ArrayList<Point> points3 = new ArrayList<>();
                if (param.neicuo) {
                    // 偶数排
                    for (int i = 0; i <= param.numL3; i++) {
                        double xx = verifyPoint3o.getX() - (Math.cos(param.alf) * (i * param.s3));
                        double yy = verifyPoint3o.getY() + (Math.sin(param.alf) * (i * param.s3));
                        double zz = verifyPoint3o.getZ();
                        points3.add(new Point(xx, yy, zz));
                    }
                    for (int i = 1; i <= param.numR3 - 1; i++) {
                        double xx = verifyPoint3o.getX() + (Math.cos(param.alf) * (i * param.s3));
                        double yy = verifyPoint3o.getY() - (Math.sin(param.alf) * (i * param.s3));
                        double zz = verifyPoint3o.getZ();
                        points3.add(new Point(xx, yy, zz));
                    }
                } else {
                    // 偶数排
                    for (int i = 0; i <= param.numL3; i++) {
                        double xx = verifyPoint3o.getX() - (Math.cos(param.alf) * (i * param.s3));
                        double yy = verifyPoint3o.getY() + (Math.sin(param.alf) * (i * param.s3));
                        double zz = verifyPoint3o.getZ();
                        points3.add(new Point(xx, yy, zz));
                    }
                    for (int i = 1; i <= param.numR3 - 1; i++) {
                        double xx = verifyPoint3o.getX() + (Math.cos(param.alf) * (i * param.s3));
                        double yy = verifyPoint3o.getY() - (Math.sin(param.alf) * (i * param.s3));
                        double zz = verifyPoint3o.getZ();
                        points3.add(new Point(xx, yy, zz));
                    }
                }
                //points3.add(verifyPoint);
                Collections.sort(points3);
                for (Point point : points3) {
                    System.out.println(point);
                    allPoint.add(point);
                    //this.calData(point);
                }
            } else {
                Point verifyPoint3j = new Point();
                verifyPoint3j.setX(param.verifyPoint.getX());
                verifyPoint3j.setY(param.verifyPoint.getY());
                verifyPoint3j.setZ(param.verifyPoint.getZ() + (lnum - 1) * 1.5 * param.radius);
                ArrayList<Point> points3 = new ArrayList<>();
                if (param.neicuo) {
                    // 奇数排
                    for (int i = 0; i <= param.numL3 - 1; i++) {
                        double xx = verifyPoint3j.getX() - (Math.cos(param.alf) * (i * param.s3));
                        double yy = verifyPoint3j.getY() + (Math.sin(param.alf) * (i * param.s3));
                        double zz = verifyPoint3j.getZ();
                        points3.add(new Point(xx, yy, zz));
                    }
                    for (int i = 1; i <= param.numR3; i++) {
                        double xx = verifyPoint3j.getX() + (Math.cos(param.alf) * (i * param.s3));
                        double yy = verifyPoint3j.getY() - (Math.sin(param.alf) * (i * param.s3));
                        double zz = verifyPoint3j.getZ();
                        points3.add(new Point(xx, yy, zz));
                    }
                } else {
                    // 奇数排
                    for (int i = 0; i <= param.numL3; i++) {
                        double xx = verifyPoint3j.getX() - (Math.cos(param.alf) * (i * param.s3));
                        double yy = verifyPoint3j.getY() + (Math.sin(param.alf) * (i * param.s3));
                        double zz = verifyPoint3j.getZ();
                        points3.add(new Point(xx, yy, zz));
                    }
                    for (int i = 1; i <= param.numR3 - 1; i++) {
                        double xx = verifyPoint3j.getX() + (Math.cos(param.alf) * (i * param.s3));
                        double yy = verifyPoint3j.getY() - (Math.sin(param.alf) * (i * param.s3));
                        double zz = verifyPoint3j.getZ();
                        points3.add(new Point(xx, yy, zz));
                    }
                }
                //points3.add(verifyPoint);
                Collections.sort(points3);
                for (Point point : points3) {
                    System.out.println(point);
                    allPoint.add(point);
                    //this.calData(point);
                }
            }
        }
        Collections.sort(allPoint);
        ArrayList<NormalDrillingData> drillingData = new ArrayList<>();
        /*for (Point point : allPoint) {
            drillingData.add(this.calData(param, point));
        }*/
        int num = 1;
        for (Point point : allPoint) {
            NormalDrillingData data = this.calData(param, point);
            data.setDrillId(num);
            num++;
            drillingData.add(data);
        }
        param.drillingData = drillingData;
        return param;
    }

    /**
     * 计算施工数据
     *
     * @param point 出煤点
     */
    public NormalDrillingData calData(BandGovernanceDrillingParam param, Point point) {
        // 钻孔长度
        double zuankongchangdu;
        // len = sqrt(x*x+y*y)-hg
        zuankongchangdu = Math.sqrt((point.getX() * point.getX()) + (point.getY() * point.getY())) - param.hg + 0.5;
        // 倾角
        double ang = rad2ang(Math.atan2(point.getY(), point.getX()));
        double qingjiao;
        double fangweijiao;
        double chuanmeichangdu = 0;
        if (ang >= 90) {
            qingjiao = 180 - ang;
            fangweijiao = (param.betd + 90) > 360 ? (param.betd + 90) - 360 : (param.betd + 90);
            chuanmeichangdu = param.hc / Math.sin(ang2rad(qingjiao - rad2ang(param.alf)));
        } else {
            qingjiao = ang;
            fangweijiao = (param.betd + 270) > 360 ? (param.betd + 270) - 360 : (param.betd + 270);
            chuanmeichangdu = param.hc / Math.sin(ang2rad(qingjiao + rad2ang(param.alf)));
        }
        double guomeichangdu = 0.5;
        // 见煤深度
        double jianmeishendu = zuankongchangdu - chuanmeichangdu - guomeichangdu;

        // 开孔高度
        // x2+y2 = r2
        // y=tan(ang)x
        // 联立求解
        double xk = Math.sqrt((param.hg * param.hg) / (1 + (Math.tan(ang2rad(ang)) * Math.tan(ang2rad(ang)))));
        double yk = Math.sqrt((param.hg * param.hg) - (xk * xk));
        double kaikonggaodu = yk;
        System.out.println("倾角：" + qingjiao + ",方位角：" + fangweijiao + ",钻孔长度：" + zuankongchangdu + ",见煤深度：" + jianmeishendu + ",穿煤长度：" + chuanmeichangdu + ",过煤长度：" + guomeichangdu + ",开孔高度：" + kaikonggaodu);
        // 起点
        //System.out.println(param.oPoint + ",");
        // 见煤点
        Point currentOPoint = new Point(param.oPoint.getX(), param.oPoint.getY(), point.getZ());
        Point point1 = calNewPoint(toVector(point, currentOPoint), point, chuanmeichangdu);
        Point point1p = new Point(point1.x, point1.z, point1.y);
        System.out.println(point1p + ",");
        // 出煤点
        Point pointp = new Point(point.x, point.z, point.y);
        System.out.println(pointp + ",");
        return new NormalDrillingData(0, 0, fangweijiao, qingjiao, jianmeishendu, chuanmeichangdu, guomeichangdu, zuankongchangdu, kaikonggaodu, param.oPoint, point1p, pointp);
    }

    public static void main(String[] args) {
        //System.out.println(rad2ang(Math.atan(1)));
        BandGovernanceDrilling drilling = new BandGovernanceDrilling();
        //ArrayList<NormalDrillingData> normalDrillingData = drilling.calDrilling();
    }



    /*// 正方形布置
        numL4 = Math.floor(lL / s4) + 1;
        numR4 = Math.floor(lR / s4) + 1;
        System.out.println("布孔个数：L4:" + numL4 + ",R4:" + numR4);
        // 计算出煤点
        ArrayList<Point> points4 = new ArrayList<>();
        for (int i = 1; i <= numL4; i++) {
            double xx = verifyPoint.getX() - (Math.cos(alf) * (i * s4));
            double yy = verifyPoint.getY() + (Math.sin(alf) * (i * s4));
            double zz = verifyPoint.getZ();
            points4.add(new Point(xx, yy, zz));
        }
        for (int i = 1; i <= numR4; i++) {
            double xx = verifyPoint.getX() + (Math.cos(alf) * (i * s4));
            double yy = verifyPoint.getY() - (Math.sin(alf) * (i * s4));
            double zz = verifyPoint.getZ();
            points4.add(new Point(xx, yy, zz));
        }
        // 添加验证孔
        points4.add(verifyPoint);
        Collections.sort(points4);
        System.out.println("正方形布孔:" + points4.size());
        for (Point point : points4) {
            System.out.println(point);
            // 钻孔长度
            double zuankongchangdu;
            // len = sqrt(x*x+y*y)-hg
            zuankongchangdu = Math.sqrt((point.getX() * point.getX()) + (point.getY() * point.getY())) - hg + 0.5;
            // 倾角
            double ang = rad2ang(Math.atan2(point.getY(), point.getX()));
            double qingjiao;
            double fangweijiao;
            double chuanmeichangdu = 0;
            if (ang >= 90) {
                qingjiao = 180 - ang;
                fangweijiao = (betd + 90) > 360 ? (betd + 90) - 360 : (betd + 90);
                chuanmeichangdu = hc / Math.sin(ang2rad(qingjiao - rad2ang(alf)));
            } else {
                qingjiao = ang;
                fangweijiao = (betd + 270) > 360 ? (betd + 270) - 360 : (betd + 270);
                chuanmeichangdu = hc / Math.sin(ang2rad(qingjiao + rad2ang(alf)));
            }
            double guomeichangdu = 0.5;
            // 见煤深度
            double jianmeishendu = zuankongchangdu - chuanmeichangdu - guomeichangdu;

            // 开孔高度
            // x2+y2 = r2
            // y=tan(ang)x
            // 联立求解
            double xk = Math.sqrt((hg * hg) / (1 + (Math.tan(ang2rad(ang)) * Math.tan(ang2rad(ang)))));
            double yk = Math.sqrt((hg * hg) - (xk * xk));
            double kaikonggaodu = yk;
            System.out.println("倾角：" + qingjiao + ",方位角：" + fangweijiao + ",钻孔长度：" + zuankongchangdu + ",见煤深度：" + jianmeishendu + ",穿煤长度：" + chuanmeichangdu + ",过煤长度：" + guomeichangdu + ",开孔高度：" + kaikonggaodu);
        }*/


        /*// 三角布置
        numL3 = Math.floor(((lL - (s3 / 2)) / s3) + 2);
        numR3 = Math.floor(((lR - (s3 / 2)) / s3) + 2);
        System.out.println("布孔个数：L3:" + numL3 + ",R3:" + numR3);

        // 计算出煤点
        System.out.println("三花布孔");
        List<Point> allPoint = new ArrayList<>();
        for (int lnum = 1; lnum <= 2; lnum++) {
            System.out.println("排数：" + lnum);
            if (lnum % 2 == 0) {
                Point verifyPoint3o = new Point();
                verifyPoint3o.setX(verifyPoint.getX() + (Math.cos(alf) * (((Math.sqrt(3)) / 2) * radius)));
                verifyPoint3o.setY(verifyPoint.getY() - (Math.sin(alf) * (((Math.sqrt(3)) / 2) * radius)));
                verifyPoint3o.setZ(verifyPoint.getZ() + (lnum - 1) * 1.5 * radius);
                //System.out.println("verifyPoint = " + verifyPoint);
                //System.out.println("verifyPoint3o = " + verifyPoint3o);
                //System.out.println("dis v4->v3:" + Math.sqrt((verifyPoint.getX() - verifyPoint3o.getX()) * (verifyPoint.getX() - verifyPoint3o.getX()) + (verifyPoint.getY() - verifyPoint3o.getY()) * (verifyPoint.getY() - verifyPoint3o.getY())));
                ArrayList<Point> points3 = new ArrayList<>();
                // 偶数排
                for (int i = 0; i <= numL3; i++) {
                    double xx = verifyPoint3o.getX() - (Math.cos(alf) * (i * s3));
                    double yy = verifyPoint3o.getY() + (Math.sin(alf) * (i * s3));
                    double zz = verifyPoint3o.getZ();
                    points3.add(new Point(xx, yy, zz));
                }
                for (int i = 1; i <= numR3; i++) {
                    double xx = verifyPoint3o.getX() + (Math.cos(alf) * (i * s3));
                    double yy = verifyPoint3o.getY() - (Math.sin(alf) * (i * s3));
                    double zz = verifyPoint3o.getZ();
                    points3.add(new Point(xx, yy, zz));
                }
                //points3.add(verifyPoint);
                Collections.sort(points3);
                for (Point point : points3) {
                    System.out.println(point);
                    allPoint.add(point);
                    //this.calData(point);
                }
            } else {
                Point verifyPoint3j = new Point();
                verifyPoint3j.setX(verifyPoint.getX());
                verifyPoint3j.setY(verifyPoint.getY());
                verifyPoint3j.setZ(verifyPoint.getZ() + (lnum - 1) * 1.5 * radius);
                ArrayList<Point> points3 = new ArrayList<>();
                // 奇数排
                for (int i = 0; i <= numL3; i++) {
                    double xx = verifyPoint3j.getX() - (Math.cos(alf) * (i * s3));
                    double yy = verifyPoint3j.getY() + (Math.sin(alf) * (i * s3));
                    double zz = verifyPoint3j.getZ();
                    points3.add(new Point(xx, yy, zz));
                }
                for (int i = 1; i <= numR3; i++) {
                    double xx = verifyPoint3j.getX() + (Math.cos(alf) * (i * s3));
                    double yy = verifyPoint3j.getY() - (Math.sin(alf) * (i * s3));
                    double zz = verifyPoint3j.getZ();
                    points3.add(new Point(xx, yy, zz));
                }
                //points3.add(verifyPoint);
                Collections.sort(points3);
                for (Point point : points3) {
                    System.out.println(point);
                    allPoint.add(point);
                    //this.calData(point);
                }
            }
        }
        Collections.sort(allPoint);
        for (Point point : allPoint) {
            this.calData(point);
        }*/
        /*for (int i = 1; i <= numL3; i++) {
            double xx = verifyPoint.getX() - (Math.cos(alf) * (i * s3));
            double yy = verifyPoint.getY() + (Math.sin(alf) * (i * s3));
            double zz = verifyPoint.getZ();
            points3.add(new Point(xx, yy, zz));
        }
        for (int i = 1; i <= numR3; i++) {
            double xx = verifyPoint.getX() + (Math.cos(alf) * (i * s3));
            double yy = verifyPoint.getY() - (Math.sin(alf) * (i * s3));
            double zz = verifyPoint.getZ();
            points3.add(new Point(xx, yy, zz));
        }
        points3.add(verifyPoint);
        Collections.sort(points3);
        System.out.println("三花布孔");
        for (Point point : points3) {
            System.out.println(point);
        }*/



        /*  计算施工数据
        for (Point point : points4) {
            //System.out.println(point);
            // 钻孔长度
            double zuankongchangdu;
            // len = sqrt(x*x+y*y)-hg
            zuankongchangdu = Math.sqrt((point.getX() * point.getX()) + (point.getY() * point.getY())) - hg + 0.5;
            // 倾角
            double ang = rad2ang(Math.atan2(point.getY(), point.getX()));
            double qingjiao;
            double fangweijiao;
            double chuanmeichangdu = 0;
            if (ang >= 90) {
                qingjiao = 180 - ang;
                fangweijiao = (betd + 90) > 360 ? (betd + 90) - 360 : (betd + 90);
                chuanmeichangdu = hc / Math.sin(ang2rad(qingjiao - rad2ang(alf)));
            } else {
                qingjiao = ang;
                fangweijiao = (betd + 270) > 360 ? (betd + 270) - 360 : (betd + 270);
                chuanmeichangdu = hc / Math.sin(ang2rad(qingjiao + rad2ang(alf)));
            }
            double guomeichangdu = 0.5;
            // 见煤深度
            double jianmeishendu = zuankongchangdu - chuanmeichangdu - guomeichangdu;

            // 开孔高度
            // x2+y2 = r2
            // y=tan(ang)x
            // 联立求解
            double xk = Math.sqrt((hg * hg) / (1 + (Math.tan(ang2rad(ang)) * Math.tan(ang2rad(ang)))));
            double yk = Math.sqrt((hg * hg) - (xk * xk));
            double kaikonggaodu = yk;
            System.out.println("倾角：" + qingjiao + ",方位角：" + fangweijiao + ",钻孔长度：" + zuankongchangdu + ",见煤深度：" + jianmeishendu + ",穿煤长度：" + chuanmeichangdu + ",过煤长度：" + guomeichangdu + ",开孔高度：" + kaikonggaodu);
            drillingDataList.add(new NormalDrillingData(0, 0, fangweijiao, qingjiao, jianmeishendu, chuanmeichangdu, guomeichangdu, zuankongchangdu, kaikonggaodu));
        }*/
}
