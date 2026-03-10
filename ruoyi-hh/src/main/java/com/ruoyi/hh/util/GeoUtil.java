package com.ruoyi.hh.util;

/**
 * 地理相关计算类
 */
public class GeoUtil {

    // 三维空间，两点间距离
    public static double getDis3D(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2));
    }
}
