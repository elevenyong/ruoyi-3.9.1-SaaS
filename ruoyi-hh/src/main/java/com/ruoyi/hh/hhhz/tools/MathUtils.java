package com.ruoyi.hh.hhhz.tools;

import com.ruoyi.hh.hhhz.domain.Point;
import com.ruoyi.hh.hhhz.domain.Vector3;

public class MathUtils {
    public static double rad2ang(double rad) {
        return rad * (180 / Math.PI);
    }

    public static double ang2rad(double ang) {
        return ang * (Math.PI / 180);
    }

    /**
     * 三点确定平面方程 Ax+By+Cz+D=0
     *
     * @param a
     * @param b
     * @param c
     */
    public static double[] getFlatByPoints(Point a, Point b, Point c) {
        /*System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);*/
        double A = a.getY() * (b.getZ() - c.getZ()) + b.getY() * (c.getZ() - a.getZ()) + c.getY() * (a.getZ() - b.getZ());
        double B = a.getZ() * (b.getX() - c.getX()) + b.getZ() * (c.getX() - a.getX()) + c.getZ() * (a.getX() - b.getX());
        double C = a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX() * (a.getY() - b.getY());
        double D = (-a.getX() * (b.getY() * c.getZ() - c.getY() * b.getZ())) - (b.getX() * (c.getY() * a.getZ() - a.getY() * c.getZ())) - (c.getX() * (a.getY() * b.getZ() - b.getY() * a.getZ()));
        //System.out.println("A = " + A + ";B = " + B + ";C = " + C + ";D = " + D);
        System.out.println((A != 0.0 ? (A + "x+") : "") + (B != 0.0 ? (B + "y+") : "") + (C != 0.0 ? (C + "z+") : "") + (D != 0.0 ? (D + "=0") : "=0"));
        return new double[]{A, B, C, D};
    }

    /**
     * 平面平移
     *
     * @param beforeFlat
     * @param dis
     * @param upper
     * @return
     */
    public static double[] getFlatByDis(double[] beforeFlat, double dis, boolean upper) {
        double[] flat = beforeFlat.clone();
        double v = Math.abs(dis) * Math.sqrt(flat[0] * flat[0] + flat[1] * flat[1] + flat[2] * flat[2]);
        if (upper) {
            flat[3] = flat[3] - v;
        } else {
            flat[3] = flat[3] + v;
        }
        System.out.println((flat[0] != 0.0 ? (flat[0] + "x+") : "") + (flat[1] != 0.0 ? (flat[1] + "y+") : "") + (flat[2] != 0.0 ? (flat[2] + "z+") : "") + (flat[3] != 0.0 ? (flat[3] + "=0") : "=0"));
        return flat;
    }

    /**
     * 空间直线与平面的交点
     *
     * @param p1
     * @param p2
     * @param a
     * @param b
     * @param c
     * @param d
     */
    public static Point getPointByLineToFlat(Point p1, Point p2, double a, double b, double c, double d) {
        double x1 = p1.getX(), y1 = p1.getY(), z1 = p1.getZ();
        double x2 = p2.getX(), y2 = p2.getY(), z2 = p2.getZ();

        double t = (-(d + a * x1 + b * y1 + c * z1)) / (a * (x2 - x1) + b * (y2 - y1) + c * (z2 - z1));
        double x = x1 + t * (x2 - x1);
        double y = y1 + t * (y2 - y1);
        double z = z1 + t * (z2 - z1);

        //System.out.println("交点坐标为：(" + x + ", " + y + ", " + z + ")");
        return new Point(x, y, z);
    }

    /*public static double[] getLineByFlat2Flat(double[] flat1, double[] flat2) {
        double[] directionVector = new double[3];
        directionVector[0] = flat1[1] * flat2[2] - flat2[1] * flat1[2];
        directionVector[1] = flat1[2] * flat2[0] - flat2[2] * flat1[0];
        directionVector[2] = flat1[0] * flat2[1] - flat2[0] * flat1[1];

        // 找到交线上的一点，使 y 坐标为 1
        double x0 = (flat2[3] - flat2[1] - flat2[2]) / flat2[0];
        double y0 = 1;
        double z0 = (flat1[3] - flat1[1] - flat1[0] * x0) / flat1[2];

        // 输出交线的点向式方程
        //System.out.println("(x-" + x0 + ")/" + directionVector[0] + " = (y-" + y0 + ")/" + directionVector[1] + " = (z-" + z0 + ")/" + directionVector[2]);
    }*/

    public static Point getPointByFlat2Flat(double[] flat1, double[] flat2, String xyz, double val) {
        // 平面一般式方程 Ax+By+Cz+D=0
        double A = flat1[0];
        double B = flat1[1];
        double C = flat1[2];
        double D = flat1[3];
        // 平面一般式方程 A1x+B1y+C1z+D1=0
        double A1 = flat2[0];
        double B1 = flat2[1];
        double C1 = flat2[2];
        double D1 = flat2[3];

        switch (xyz) {
            case "x": {
                double y = (A * D1 - A1 * D + (C * A1 - C1 * A) * val) / (A * B1 - A1 * B);
                double z = (-A * val - B * y - D) / C;
                return new Point(val, y, z);
            }
            case "y": {
                double x = (B * D1 - B1 * D + (C * B1 - C1 * B) * val) / (A * B1 - A1 * B);
                double z = (-A * x - B * val - D) / C;
                return new Point(x, val, z);
            }
            case "z": {
                double x = (C * D1 - C1 * D + (A * C1 - A1 * C) * val) / (A * B1 - A1 * B);
                double y = (-A * x - C * val - D) / B;
                return new Point(x, y, val);
            }
            default:
                return new Point(0, 0, 0);
        }
    }

    /**
     * 空间直线与平面的交点
     *
     * @param p1   直线上一点
     * @param p2   直线上一点
     * @param flat 平面方程
     */
    public static Point getPointByLineToFlat(Point p1, Point p2, double[] flat) {
        double x1 = p1.getX(), y1 = p1.getY(), z1 = p1.getZ();
        double x2 = p2.getX(), y2 = p2.getY(), z2 = p2.getZ();

        double t = (-(flat[3] + (flat[0] * x1) + (flat[1] * y1) + (flat[2] * z1))) / (flat[0] * (x2 - x1) + flat[1] * (y2 - y1) + flat[2] * (z2 - z1));
        double x = x1 + t * (x2 - x1);
        double y = y1 + t * (y2 - y1);
        double z = z1 + t * (z2 - z1);

        //System.out.println("交点坐标为：(" + x + ", " + y + ", " + z + ")");
        return new Point(x, y, z);
    }

    /**
     * 计算距离
     *
     * @param p0
     * @param p1
     * @return
     */
    public static double calP2PDis(Point p0, Point p1) {
        return Math.sqrt(((p1.getX() - p0.getX()) * (p1.getX() - p0.getX())) + ((p1.getY() - p0.getY()) * (p1.getY() - p0.getY())) + ((p1.getZ() - p0.getZ()) * (p1.getZ() - p0.getZ())));
    }

    /**
     * 计算沿向量方向增加某个距离得到的新向量
     *
     * @param v3
     * @param dis
     * @return
     */
    public static Vector3 calNewV3(Vector3 v3, double dis) {
        if (dis > 0) {
            double vx = v3.getEnd().getX() + (v3.getX() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
            double vy = v3.getEnd().getY() + (v3.getY() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
            double vz = v3.getEnd().getZ() + (v3.getZ() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
            return new Vector3(v3.getStart(), new Point(vx, vy, vz));
        } else if (dis < 0) {
            double vx = v3.getStart().getX() + (v3.getX() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
            double vy = v3.getStart().getY() + (v3.getY() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
            double vz = v3.getStart().getZ() + (v3.getZ() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
            return new Vector3(new Point(vx, vy, vz), v3.getEnd());
        } else return v3;
    }


    /**
     * 计算向量a在向量b上的投影
     *
     * @param a
     * @param b
     * @return
     */
    public static Vector3 calProjA2B(Vector3 a, Vector3 b) {
        double x = ((a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ()) / (b.getX() * b.getX() + b.getY() * b.getY() + b.getZ() * b.getZ())) * b.getX();
        double y = ((a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ()) / (b.getX() * b.getX() + b.getY() * b.getY() + b.getZ() * b.getZ())) * b.getY();
        double z = ((a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ()) / (b.getX() * b.getX() + b.getY() * b.getY() + b.getZ() * b.getZ())) * b.getZ();
        return new Vector3(x, y, z);
    }

    /**
     * 计算两个向量之间的角度
     *
     * @param a
     * @param b
     * @return
     */
    public static double calAng(Vector3 a, Vector3 b) {
        double cosTheta = (a.getX() * b.getX() + a.getY() * b.getY() + a.getZ() * b.getZ()) / (Math.sqrt(a.getX() * a.getX() + a.getY() * a.getY() + a.getZ() * a.getZ()) * Math.sqrt(b.getX() * b.getX() + b.getY() * b.getY() + b.getZ() * b.getZ()));
        return Math.acos(cosTheta);
    }

    /**
     * 返回一个空间向量
     *
     * @param p0
     * @param p1
     * @return
     */
    public static Vector3 toVector(Point p0, Point p1) {
        return new Vector3(p1.getX() - p0.getX(), p1.getY() - p0.getY(), p1.getZ() - p0.getZ());
    }

    public static Point calNewPoint(Vector3 v3, Point basePoint, double dis) {
        double vx = basePoint.getX() + (v3.getX() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
        double vy = basePoint.getY() + (v3.getY() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
        double vz = basePoint.getZ() + (v3.getZ() * (dis / Math.sqrt((v3.getX() * v3.getX()) + (v3.getY() * v3.getY()) + (v3.getZ() * v3.getZ()))));
        return new Point(vx, vy, vz);
        /*// 点 A 的坐标
        double x = basePoint.getX();
        double y = basePoint.getY();
        double z = basePoint.getZ();
        // 空间向量 t
        double a = v3.getX();
        double b = v3.getY();
        double c = v3.getZ();
        // 距离 m
        double m = dis;

        // 计算新点的坐标
        double newX = x + m * a;
        double newY = y + m * b;
        double newZ = z + m * c;
        return new Point(newX, newY, newZ);*/
    }

}
