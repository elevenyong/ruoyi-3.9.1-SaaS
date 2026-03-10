package com.ruoyi.hh.hhhz.tools.drilling;


import com.ruoyi.hh.hhhz.domain.NormalDrillingData;
import com.ruoyi.hh.hhhz.domain.Point;
import com.ruoyi.hh.hhhz.domain.Vector3;
import com.ruoyi.hh.hhhz.domain.param.CoalUncoveringDrillingParam;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

import static com.ruoyi.hh.hhhz.tools.MathUtils.*;


/**
 * 揭煤钻孔设计
 */
@Component
public class CoalUncoveringDrilling {
    /**
     * 煤厚
     */
    /*private double d = 3;

     *//**
     * 石门高度
     *//*
    private double H = 3;
    *//**
     * 揭煤工作面与煤层距离
     *//*
    private double N = 7;
    *//**
     * 石门宽度
     *//*
    private double W = 4;
    *//**
     * 石门上/下底宽(梯形)
     *//*
    private double Wp;
    *//**
     * 石门帮高(拱形)
     *//*
    private double Hp = 0.9;
    *//**
     * 煤层倾角
     *//*
    private double beta = ang2rad(30);
    *//**
     * 石门与煤层走向夹角
     *//*
    private double gama = ang2rad(10);
    *//**
     * 石门与煤层夹角
     *//*
    private double fai = Math.atan(Math.tan(beta) * Math.cos(gama));
    */

    /**
     * 抽采半径
     *//*
    private double R = 5;*/
    public CoalUncoveringDrilling() {
    }

    public static void main(String[] args) {
        CoalUncoveringDrilling drilling = new CoalUncoveringDrilling();
        //drilling.init();
    }

    public CoalUncoveringDrillingParam calDrilling(CoalUncoveringDrillingParam param) {

        //1. 确定底板抽采范围投影矩形
        //2. 确定煤层底板及顶板方程
        //3. 确定顶板抽采范围投影矩形
        //4. 按照抽采半径对顶板进行分割,确定出煤点
        //5. 确定石门处钻孔位置
        //6. 连线计算施工参数
        // 石门距离煤层法距
        double Lc = param.N;
        // 控制范围: h1,h2上下;w1,w2左右
        double h1 = param.h1, h2 = param.h2, w1 = param.w1, w2 = param.w2;
        // 煤层底板控制范围矩形
        // 上下
        double Lh = (param.H / Math.sin(param.getFai())) + h1 + h2;
        // 左右
        double Lw = (param.W / Math.cos(param.gama)) + w1 + w2;
        System.out.println("上下控制范围: Lh = " + Lh);
        System.out.println("左右控制范围: Lw = " + Lw);
        // 控制半径
        double Rs = param.R;
        // 每行布置钻孔数量,对应是多少列
        double n1 = ((int) (Lw / (Math.sqrt(2) * Rs))) + 2;
        // 每列布置钻孔数量,对应是多少行
        double n2 = ((int) (Lh / (Math.sqrt(2) * Rs))) + 2;
        // 排间距, 上下距离/行数
        double e1 = Lh / (n2 - 1);
        // 列间距, 左右距离/列数
        double e2 = Lw / (n1 - 1);
        ArrayList<Point> points = new ArrayList<>();
        // 煤层底板四个角点, 0左上,1右上,2左下,3右下
        for (int i = 1; i <= n2; i++) {
            //System.out.println("第" + i + "行");
            for (int j = 1; j <= n1; j++) {
                double x2ij = ((-Lw / 2) + ((j - 1) * e2)) * Math.cos(param.gama);
                double y2ij = (-w1 * Math.tan(param.gama)) + (((Lc) / Math.sin(param.beta)) / Math.cos(param.gama)) + ((j - 1) * e2 * Math.sin(param.gama)) + ((i - 1) * e1 * Math.cos(param.getFai()));
                double z2ij = param.H + (h1 * Math.sin(param.getFai())) - ((i - 1) * e1 * Math.sin(param.getFai()));
                if ((i == 1 && j == 1) || (i == 1 && j == n1) || (i == n2 && j == 1) || (i == n2 && j == n1)) {
                    points.add(new Point(x2ij, y2ij, z2ij));
                    System.out.println("预备平面角点:(" + x2ij + "," + y2ij + "," + z2ij + "),");
                }
            }
        }

        // 计算底面距离石门顶板距离是否为规定值, 如果不是移动至规定值, 对边界点进行重投影
        double[] flatByPoints = getFlatByPoints(points.get(0), points.get(1), points.get(2));
        Point topPoint = new Point(0, 0, param.H);
        double dis = calDisPoint2Flat(topPoint, flatByPoints);
        //double dis = Math.abs(flatByPoints[0] * topPoint.getX() + flatByPoints[1] * topPoint.getY() + flatByPoints[2] * topPoint.getZ() + flatByPoints[3]) / Math.sqrt(flatByPoints[0] * flatByPoints[0] + flatByPoints[1] * flatByPoints[1] + flatByPoints[2] * flatByPoints[2]);
        System.out.println("预备平面距离: " + dis);

        // 煤层底板平面
        double[] flat1;
        // 煤层顶板平面
        double[] flat2;
        // 煤层底板控制范围四个角点
        ArrayList<Point> bottomPoints = new ArrayList<>();
        // 煤层顶板控制范围投影点
        ArrayList<Point> topPoints = new ArrayList<>();
        // 计算煤层底板平面
        if (dis != 7) {
            flat1 = getFlatByDis(flatByPoints, dis - Lc, (dis - Lc > 0) ? false : true);
            System.out.println("底板平面 flat1 = " + Arrays.toString(flat1));
            // 计算煤层底板面上四个点的位置
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                bottomPoints.add(new Point(point.getX(), (((flat1[0] * point.getX()) + (flat1[2] * point.getZ()) + flat1[3]) / (-flat1[1])), point.getZ()));
            }
            for (Point newPoint : bottomPoints) {
                System.out.println("底板平面角点(" + newPoint.getX() + "," + newPoint.getY() + "," + newPoint.getZ() + "),");
            }
        } else {
            flat1 = flatByPoints;
            bottomPoints = points;
        }

        // 计算煤层顶板平面
        flat2 = getFlatByDis(flat1, param.d, true);
        System.out.println("顶板平面 flat2 = " + Arrays.toString(flat2));
        // 计算底板四个点在顶板的投影
        for (Point newPoint : bottomPoints) {
            Point tPoint = getPointByLineToFlat(new Point(0, 0, 0), newPoint, flat2);
            topPoints.add(tPoint);
        }
        for (Point newPoint : topPoints) {
            System.out.println("顶板板平面角点(" + newPoint.getX() + "," + newPoint.getY() + "," + newPoint.getZ() + "),");
        }
        // 按照正方形布孔对这个区域进行覆盖
        // P0->P1一系列孔, 左上->右上, 一行
        ArrayList<Point> tPs01 = new ArrayList<>();
        Vector3 v01 = toVector(topPoints.get(0), topPoints.get(1));
        // 一行钻孔数量
        int n01 = ((int) (calP2PDis(topPoints.get(0), topPoints.get(1)) / (Math.sqrt(2) * Rs))) + 1;
        System.out.println("n01 = " + n01);
        Point temPoint01 = topPoints.get(0);
        tPs01.add(temPoint01);
        for (int i = 1; i <= n01; i++) {
            temPoint01 = calNewPoint(v01, temPoint01, Math.sqrt(2) * Rs);
            tPs01.add(temPoint01);
        }
        for (Point tP : tPs01) {
            System.out.println("01(" + tP.getX() + "," + tP.getY() + "," + tP.getZ() + "),");
        }
        // P2->P3一系列孔, 左下->右下, 一行
        ArrayList<Point> tPs23 = new ArrayList<>();
        Vector3 v23 = toVector(topPoints.get(2), topPoints.get(3));
        int n23 = ((int) (calP2PDis(topPoints.get(2), topPoints.get(3)) / (Math.sqrt(2) * Rs))) + 1;
        System.out.println("n23 = " + n23);
        Point temPoint23 = topPoints.get(2);
        tPs23.add(temPoint23);
        for (int i = 1; i <= n23; i++) {
            temPoint23 = calNewPoint(v23, temPoint23, Math.sqrt(2) * Rs);
            tPs23.add(temPoint23);
        }
        for (Point tP : tPs23) {
            System.out.println("23(" + tP.getX() + "," + tP.getY() + "," + tP.getZ() + "),");
        }


        // 一列钻孔数量计算竖向钻孔数量
        int n02 = ((int) (calP2PDis(topPoints.get(0), topPoints.get(2)) / (Math.sqrt(2) * Rs))) + 1;
        System.out.println("n02 = " + n02);
        // 计算所有孔位置
        ArrayList<Point> allPoints = new ArrayList<>();
        for (int i = 0; i <= n01; i++) {
            Vector3 v = toVector(tPs01.get(i), tPs23.get(i));
            Point temPoint = tPs01.get(i);
            System.out.println(i);
            System.out.println("(" + temPoint.getX() + "," + temPoint.getY() + "," + temPoint.getZ() + "),");
            // 已经加过初始点
            allPoints.add(temPoint);
            // j从1开始
            for (int j = 1; j <= n02; j++) {
                temPoint = calNewPoint(v, temPoint, Math.sqrt(2) * Rs);
                System.out.println("(" + temPoint.getX() + "," + temPoint.getY() + "," + temPoint.getZ() + "),");
                allPoints.add(temPoint);
            }
        }


        System.out.println("石门钻孔设计:");
        // 石门钻孔设计
        // 钻机最小,最大工作高度
        double q1 = param.q1, q2 = param.q2;
        // 迎头钻孔左右边界
        double p1 = param.p1, p2 = param.p2;
        // 石门面钻孔 排间距
        double s1 = (q2 - q1) / (n02);
        // 石门面钻孔 列间距
        double s2 = (param.W - p1 - p2) / (n01);
        // 石门面钻孔坐标
        System.out.println("n01 = " + n01);
        System.out.println("n02 = " + n02);
        ArrayList<Point> shimenPoints = new ArrayList<>();
        for (int j = 1; j <= n01 + 1; j++) {
            System.out.println("第" + j + "列");
            for (int i = 1; i <= n02 + 1; i++) {
                double x1ij = ((j - 1) * s2) - ((param.W - p1 - p2) / 2);
                double y1ij = 0;
                double z1ij = q2 - ((i - 1) * s1);
                //System.out.println(i + "," + j + ":(" + x1ij + "," + y1ij + "," + z1ij + "),");
                System.out.println("(" + x1ij + "," + y1ij + "," + z1ij + "),");
                shimenPoints.add(new Point(x1ij, y1ij, z1ij));
            }
        }

        System.out.println("size: allPoints = " + allPoints.size());
        System.out.println("size: shimenPoints = " + shimenPoints.size());
        ArrayList<NormalDrillingData> drillingData = new ArrayList<>();
        if (param.type == 0) {
            // 巷头揭煤
            // 计算施工参数
            int num = 1;
            for (int i = 0; i < allPoints.size(); i++) {
                double p2pDis = calP2PDis(allPoints.get(i), shimenPoints.get(i));
                Point bp1 = getPointByLineToFlat(allPoints.get(i), shimenPoints.get(i), flat1);
                double p2bpDis = calP2PDis(bp1, shimenPoints.get(i));
                double cmDis = p2pDis - p2bpDis;
                System.out.println("石门=" + shimenPoints.get(i) + ";见煤=" + bp1 + ";出煤=" + allPoints.get(i) + ";总长度=" + p2pDis + ";见煤长度=" + p2bpDis + ";穿煤长度=" + cmDis + ";倾角=" + rad2ang(calTiltAngle(shimenPoints.get(i), allPoints.get(i))) + "方位角;=" + rad2ang(calAzimuth(shimenPoints.get(i), allPoints.get(i))));
                NormalDrillingData data = new NormalDrillingData(0, num, rad2ang(calAzimuth(shimenPoints.get(i), allPoints.get(i))), rad2ang(calTiltAngle(shimenPoints.get(i), allPoints.get(i))), p2bpDis, cmDis, 0.5, p2pDis, shimenPoints.get(i).z, shimenPoints.get(i), bp1, allPoints.get(i));
                num++;
                drillingData.add(data);
            }
            param.drillingData = drillingData;
            return param;
        } else if (param.type == 1) {
            // 辅助巷揭煤
            // 辅助巷揭煤
            ArrayList<Point> aidPoints = new ArrayList<>();
            double lenOfOrigin = 7;
            Point aidSPoint = new Point(lenOfOrigin, 0, param.H / 2);
            // 把煤层平面直接平移到这个点上
            double disAid2Bottom = calDisPoint2Flat(aidSPoint, flat1);
            // 过辅助巷起点的平行平面
            double[] flat3 = getFlatByDis(flat1, disAid2Bottom, false);
            // x=lenOfOrigin
            System.out.println("flat3 = " + Arrays.toString(flat3));
            Point aidEPoint = getPointByFlat2Flat(flat3, new double[]{1, 0, 0, -lenOfOrigin}, "y", (Math.sqrt(2) * Rs));
            System.out.println("aidSPoint = " + aidSPoint);
            System.out.println("aidEPoint = " + aidEPoint);
            for (int i = 0; i <= n02; i++) {
                //Point aidPoint = calNewPoint(toVector(aidSPoint, aidEPoint), aidSPoint, i);
                double xx = aidSPoint.getX() + (i * (aidEPoint.getX() - aidSPoint.getX()));
                double yy = aidSPoint.getY() + (i * (aidEPoint.getY() - aidSPoint.getY()));
                double zz = aidSPoint.getZ() + (i * (aidEPoint.getZ() - aidSPoint.getZ()));
                Point aidPoint = new Point(xx, yy, zz);
                System.out.println(aidPoint + ",");
                aidPoints.add(aidPoint);
            }

            System.out.println("All");
            for (Point allPoint : allPoints) {
                System.out.println("(" + allPoint.getX() + "," + allPoint.getY() + "," + allPoint.getZ() + "),");
            }

            System.out.println("n01 = " + n01);
            System.out.println("n02 = " + n02);
            // 辅助巷起点

            // 钻孔重排序
            ArrayList<Point> reSortPoints = new ArrayList<>();
            for (int i = 0; i <= n02; i++) {
                //System.out.println("第" + i + "行:");
                for (int j = 0; j <= n01; j++) {
                    Point point = allPoints.get(i + (j * (n02 + 1)));
                    //System.out.println("point = " + point);
                    reSortPoints.add(point);
                }
            }
            System.out.println("reSort");
            for (Point reSP : reSortPoints) {
                System.out.println("(" + reSP.getX() + "," + reSP.getY() + "," + reSP.getZ() + "),");
            }
            System.out.println("辅助巷施工参数:");
            int num = 1;
            for (int i = 0; i < aidPoints.size(); i++) {
                System.out.println("第" + i + "号钻孔");
                for (int j = 0; j < (n01 + 1); j++) {
                    int t = j + i * (n01 + 1);
                    double p2pDis = calP2PDis(reSortPoints.get(t), aidPoints.get(i));
                    Point bp1 = getPointByLineToFlat(reSortPoints.get(t), aidPoints.get(i), flat1);
                    double p2bpDis = calP2PDis(bp1, aidPoints.get(i));
                    double cmDis = p2pDis - p2bpDis;
                    System.out.println("辅助巷=" + aidPoints.get(i) + ";见煤=" + bp1 + ";出煤=" + reSortPoints.get(t) + ";总长度=" + p2pDis + ";见煤长度=" + p2bpDis + ";穿煤长度=" + cmDis + ";倾角=" + rad2ang(calTiltAngle(aidPoints.get(i), reSortPoints.get(t))) + "方位角;=" + rad2ang(calAzimuth(aidPoints.get(i), reSortPoints.get(t))));
                    NormalDrillingData data = new NormalDrillingData(0, num, rad2ang(calAzimuth(aidPoints.get(i), reSortPoints.get(t))), rad2ang(calTiltAngle(aidPoints.get(i), reSortPoints.get(t))), p2bpDis, cmDis, 0.5, p2pDis, 0, aidPoints.get(i), bp1, allPoints.get(i));
                    num++;
                    drillingData.add(data);
                }
            }
            param.drillingData = drillingData;
            return param;
        } else return null;

    }


    /**
     * 计算方位角
     *
     * @param p1
     * @param p2
     * @return
     */
    public double calAzimuth(Point p1, Point p2) {
        if (p2.getY() != 0) {
            return Math.atan((p2.getX() - p1.getX()) / p2.getY());
        }
        return 0;
    }

    /**
     * 计算倾角
     *
     * @param p1
     * @param p2
     * @return
     */
    public double calTiltAngle(Point p1, Point p2) {
        if (p2.getY() != 0) {
            return Math.atan((p2.getZ() - p1.getZ()) / p2.getY());
        }
        return 0;
    }

    /**
     * 计算点和平面的距离
     *
     * @param p
     * @param flat
     * @return
     */
    public double calDisPoint2Flat(Point p, double[] flat) {
        return Math.abs(flat[0] * p.getX() + flat[1] * p.getY() + flat[2] * p.getZ() + flat[3]) / Math.sqrt(flat[0] * flat[0] + flat[1] * flat[1] + flat[2] * flat[2]);
    }
}
