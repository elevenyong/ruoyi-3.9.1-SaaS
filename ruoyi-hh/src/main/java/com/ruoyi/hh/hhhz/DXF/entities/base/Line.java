package com.ruoyi.hh.hhhz.DXF.entities.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Line 由两点构成，不允许多点构建。如果想使用多点构建请使用PLine，或手动循环创建
 */
public class Line extends BaseEntity {

    @JsonProperty(value = "start")
    private Point start;
    @JsonProperty(value = "end")
    private Point end;
    @JsonProperty(value = "lineType")
    private String lineType="ByLayer";
    private final String ENTITY_SUBCLASS = "100\nAcDbLine\n";

    public Line() {
    }

    public Line(Point start, Point end, String lineType, int color, String layer) {
        this.start = start;
        this.end = end;
        this.lineType = lineType;
        setColor(color);
        setLayer(layer);
    }

    /*public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        setColor(0);
    }*/


    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    /*public int getColor() {
        System.out.println("getgetColor:" + getColor());
        return color;
    }*/

    /*public void setColor(int color) {
        System.out.println("color:" + color);
        this.color = color;
        System.out.println("this.color:" + this.color);
    }*/

    public String getLineSubClass() {
        StringBuilder sb = new StringBuilder();

        sb.append(ENTITY_SUBCLASS);
        sb.append("10\n" + start.getX() + "\n");
        sb.append("20\n" + start.getY() + "\n");
        sb.append("30\n" + start.getZ() + "\n");
        sb.append("11\n" + end.getX() + "\n");
        sb.append("21\n" + end.getY() + "\n");
        sb.append("31\n" + end.getZ() + "\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", end=" + end +
                ", color=" + getColor() +
                ", ENTITY_SUBCLASS='" + ENTITY_SUBCLASS + '\'' +
                '}';
    }
}



/*public class Line {

    @JsonProperty(value = "start")
    private Point start;
    @JsonProperty(value = "end")
    private Point end;
    @JsonProperty(value = "color")
    public int color;

    private final String ENTITY_SUBCLASS = "100\nAcDbLine\n";

    public Line(Point start, Point end, int color) {
        this.start = start;
        this.end = end;
        this.color = color;
    }

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        this.color = 0;
    }

    public Line() {
        this.start = new Point(0, 0);
        this.end = new Point(1, 1);
        this.color = 0;
    }

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public int getColor() {
        System.out.println("getgetColor:" + color);
        return color;
    }

    public void setColor(int color) {
        System.out.println("color:" + color);
        this.color = color;
        System.out.println("this.color:" + this.color);
    }

    public String getLineSubClass() {
        StringBuilder sb = new StringBuilder();

        sb.append(ENTITY_SUBCLASS);
        sb.append("10\n" + start.getX() + "\n");
        sb.append("20\n" + start.getY() + "\n");
        sb.append("30\n" + start.getZ() + "\n");
        sb.append("11\n" + end.getX() + "\n");
        sb.append("21\n" + end.getY() + "\n");
        sb.append("31\n" + end.getZ() + "\n");

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", end=" + end +
                ", color=" + color +
                ", ENTITY_SUBCLASS='" + ENTITY_SUBCLASS + '\'' +
                '}';
    }
}*/
