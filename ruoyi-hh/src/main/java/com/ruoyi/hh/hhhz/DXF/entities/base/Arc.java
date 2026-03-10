package com.ruoyi.hh.hhhz.DXF.entities.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Arc extends Circle {
    @JsonProperty("startAngle")
    private double startAngle;
    @JsonProperty("endAngle")
    private double endAngle;
    private final String ENTITY_SUBCLASS = "100\nAcDbArc\n";

    public Arc() {
    }

    // @JsonCreator
    //public Arc(@JsonProperty("center") Point center, @JsonProperty("radius") double radius, @JsonProperty("startAngle") double startAngle, @JsonProperty("endAngle") double endAngle) {
   public Arc( Point center, double radius, double startAngle, double endAngle,int color,String layer) {
        super(center, radius,color,layer);
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    /*public Arc(double x, double y, double z, double radius, double startAngle, double endAngle) {
        this(new Point(x, y, z), radius, startAngle, endAngle);
    }

    public Arc(double x, double y, double radius, double startAngle, double endAngle) {
        this(new Point(x, y), radius, startAngle, endAngle);
    }*/


    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public double getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(double endAngle) {
        this.endAngle = endAngle;
    }

    public String getArcSubclass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_SUBCLASS);
        sb.append("50\n" + this.getStartAngle() + "\n");
        sb.append("51\n" + this.getEndAngle() + "\n");
        return sb.toString();
    }
}