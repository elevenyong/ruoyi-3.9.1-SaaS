package com.ruoyi.hh.hhhz.DXF.entities.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Circle extends BaseEntity {
    @JsonProperty("center")
    private Point center;
    @JsonProperty("radius")
    private double radius;
    private final String ENTITY_SUBCLASS = "100\nAcDbCircle\n";


    public Circle() {
    }

    //@JsonCreator
    //public Circle(@JsonProperty("center") Point center, @JsonProperty("radius") double radius,int color,String layer) {
    public Circle(Point center, double radius, int color, String layer) {
        this.center = center;
        this.radius = radius;
        setColor(color);
        setLayer(layer);
    }

    /*public Circle(double x, double y, double z, double radius) {
        this(new Point(x, y, z), radius);
    }

    public Circle(double x, double y, double radius) {
        this(new Point(x, y), radius);
    }*/

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getCircleSubClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_SUBCLASS);
        sb.append("10\n" + getCenter().getX() + "\n");
        sb.append("20\n" + getCenter().getY() + "\n");
        sb.append("30\n" + getCenter().getZ() + "\n");
        sb.append("40\n" + getRadius() + "\n");
        return sb.toString();
    }
}
