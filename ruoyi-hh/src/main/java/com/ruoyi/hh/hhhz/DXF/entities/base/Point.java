package com.ruoyi.hh.hhhz.DXF.entities.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Point extends BaseEntity {
    @JsonProperty("x")
    private double x;
    @JsonProperty("y")
    private double y;
    @JsonProperty("z")
    private double z;
    @JsonIgnore
    private double bulge;
    private final String ENTITY_SUBCLASS = "100\nAcDbPoint\n";

    public Point() {
    }

    public Point(double x, double y, double z, double bulge) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.bulge = bulge;
    }

    /*public Point(double x, double y, double z, double bulge) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.bulge = bulge;
    }

    public Point(double x, double y, double z) {
        this(x, y, z, 0);
    }

    public Point(double x, double y) {
        this(x, y, 0, 0);
    }*/

    /*public Point() {
        this(0, 0, 0, 0);
    }*/


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

    public double getBulge() {
        return bulge;
    }

    public void setBulge(double bulge) {
        this.bulge = bulge;
    }

    public String getPointSubClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_SUBCLASS);
        sb.append("10\n" + this.getX() + "\n");
        sb.append("20\n" + this.getY() + "\n");
        sb.append("30\n" + this.getZ() + "\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
