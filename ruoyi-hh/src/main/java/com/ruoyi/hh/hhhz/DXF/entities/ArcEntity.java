package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.base.Arc;

public class ArcEntity extends Arc implements Entity {

    private final static String ENTITY_CLASS = "0\nARC\n";


    public ArcEntity(Arc arc) {
        super(arc.getCenter(), arc.getRadius(), arc.getStartAngle(), arc.getEndAngle(), arc.getColor(), arc.getLayer());
    }

    /*public ArcEntity(Point center, double radius, double startAngle, double endAngle, String layer) {
        super(center, radius, startAngle, endAngle);
        this.layer = layer;
    }

    public ArcEntity(double x, double y, double radius, double startAngle, double endAngle, String layer) {
        this(x, y, 0, radius, startAngle, endAngle, layer);
    }

    public ArcEntity(double x, double y, double radius, double startAngle, double endAngle) {
        this(x, y, 0, radius, startAngle, endAngle, "0");
    }


    public ArcEntity(double x, double y, double z, double radius, double startAngle, double endAngle, String layer) {
        this(new Point(x, y, z), radius, startAngle, endAngle, layer);
    }

    public ArcEntity(double x, double y, double z, double radius, double startAngle, double endAngle) {
        this(x, y, z, radius, startAngle, endAngle, "0");
    }


    public ArcEntity(Point center, double radius, double startAngle, double endAngle) {
        this(center, radius, startAngle, endAngle, "0");
    }

    public ArcEntity(Arc arc, String layer) {
        this(arc.getCenter(), arc.getRadius(), arc.getStartAngle(), arc.getEndAngle(), layer);
    }*/

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
        sb.append(getArcSubclass());
        return sb.toString();
    }

    /*public static void main(String[] args) {
        ArcEntity arc = new ArcEntity(0, 0, 2, 0, 90, "0");
        System.out.println(arc.toDXF());
    }*/
}
/**
 * 0
 * ARC
 * 5
 * 221
 * 330
 * 1F
 * 100
 * AcDbEntity
 * 8
 * 0
 * 100
 * AcDbCircle
 * 10
 * 0.0
 * 20
 * 10.0
 * 30
 * 0.0
 * 40
 * 10.0
 * 100
 * AcDbArc
 * 50
 * 359.9999999999998
 * 51
 * 90.0
 */
