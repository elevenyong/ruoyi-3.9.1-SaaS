package com.ruoyi.hh.hhhz.DXF.entities.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 已废弃，多点构成的线请使用PLine
 */
@Deprecated
public class PLineBase {

    private List<Point> points;
    private final String ENTITY_SUBCLASS = "100\nAcDbLine\n";

    public PLineBase() {
        this.points = new ArrayList<>();
    }

    public PLineBase(List<Point> points) throws Exception {
        if (points.size() >= 2) {
            this.points = points;
        } else {
            throw new Exception("PLine must have at least 2 points.");
        }
    }

    public PLineBase(Point point1, Point point2) {
        this.points = new ArrayList<>();
        this.points.add(point1);
        this.points.add(point2);
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public void removePoint(Point point) {
        this.points.remove(point);
    }

    public String getLineSubClass(int index) {
        StringBuilder sb = new StringBuilder();
        Point[] points = getPoints().toArray(new Point[0]);
        if (points.length < 2) {
            throw new IllegalArgumentException("Line must have at least 2 points.");
        }

        sb.append(ENTITY_SUBCLASS);
        sb.append("10\n" + points[index].getX() + "\n");
        sb.append("20\n" + points[index].getY() + "\n");
        sb.append("30\n" + points[index].getZ() + "\n");
        sb.append("11\n" + points[index + 1].getX() + "\n");
        sb.append("21\n" + points[index + 1].getY() + "\n");
        sb.append("31\n" + points[index + 1].getZ() + "\n");

        return sb.toString();
    }

}
