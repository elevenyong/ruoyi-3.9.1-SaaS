package com.ruoyi.hh.hhhz.domain;

public class Point implements Comparable<Point> {
    public double x;
    public double y;
    public double z;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    /*@Override
    public String toString() {
        return "(" + ((int) (x * 100000)) / 100000. + "," + ((int) (y * 100000)) / 100000. + "," + ((int) (z * 100000)) / 100000. + ")";
    }*/

    @Override
    public int compareTo(Point o) {
        return (int) (o.x - x);
    }
}
