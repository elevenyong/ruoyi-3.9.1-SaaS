package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.base.Circle;

public class CircleEntity extends Circle implements Entity {

    private final static String ENTITY_CLASS = "0\nCIRCLE\n";

    public CircleEntity(Circle circle) {
        super(circle.getCenter(), circle.getRadius(), circle.getColor(), circle.getLayer());
    }

    /*public CircleEntity(Circle circle, String layer) {
        this(circle.getCenter(), circle.getRadius(), layer);
        this.layer = layer;
    }

    public CircleEntity(Point center, double radius, String layer) {
        super(center, radius);
        this.layer = layer;
    }*/

    /*public CircleEntity(Point center, double radius) {
        this(center, radius, "0");
    }

    public CircleEntity(double x, double y, double z, double radius, String layer) {
        this(new Point(x, y, z), radius, layer);
    }

    public CircleEntity(double x, double y, double z, double radius) {
        this(x, y, z, radius, "0");
    }

    public CircleEntity(double x, double y, double radius, String layer) {
        this(new Point(x, y), radius, layer);
    }

    public CircleEntity(double x, double y, double radius) {
        this(x, y, 0, radius);
    }
*/
    @Override
    public String getEntityClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_CLASS);
        sb.append("5\n" + getHandle() + "\n");
        sb.append(SOFT_POINTER);
        sb.append(SUBCLASS);
        sb.append("8\n" + getLayer() + "\n");
        sb.append("62\n" + getColor() + "\n");
        return sb.toString();
    }

    @Override
    public String toDXF() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntityClass());
        sb.append(getCircleSubClass());
        return sb.toString();
    }

    /*public static void main(String[] args) {
        CircleEntity circle = new CircleEntity(0, 0, 2);
        System.out.println(circle.toDXF());
        CircleEntity circle1 = new CircleEntity(0, 0, 3);
        System.out.println(circle1.toDXF());
    }*/
}

/**
 * 0
 * CIRCLE
 * 5
 * 3B1
 * 330
 * 1F
 * 100
 * AcDbEntity
 * 8
 * 0
 * 100
 * AcDbCircle
 * 10
 * 10.0
 * 20
 * 10.0
 * 30
 * 0.0
 * 40
 * 10.0
 */
