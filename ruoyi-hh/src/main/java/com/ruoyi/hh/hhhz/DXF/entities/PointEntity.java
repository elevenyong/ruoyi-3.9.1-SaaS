package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.base.Point;

public class PointEntity extends Point implements Entity {
    private final static String ENTITY_CLASS = "0\nPOINT\n";


    public PointEntity(Point point) {
        super(point.getX(), point.getY(), point.getZ(), point.getBulge());
    }

    public PointEntity(double x, double y, double z, String layer) {
        super(x, y, z, 0);
    }

    public PointEntity(double x, double y, double z) {
        this(x, y, z, "0");
    }

    public PointEntity(double x, double y, String layer) {
        this(x, y, 0.0, layer);
    }

    public PointEntity(double x, double y) {
        this(x, y, 0.0, "0");
    }


    @Override
    public String getEntityClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_CLASS);
        sb.append("5\n" + getHandle() + "\n");
        sb.append(SOFT_POINTER);
        sb.append(SUBCLASS);
        sb.append("8\n" + 0 + "\n");
        //sb.append("8\n" + getLayer() + "\n");
        //sb.append("62\n" + getColor() + "\n");
        return sb.toString();
    }


    @Override
    public String toDXF() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntityClass());
        sb.append(getPointSubClass());
        return sb.toString();
    }

    /*public static void main(String[] args) {
        PointEntity point = new PointEntity(10, 10);
        PointEntity point2 = new PointEntity(20, 20);
        PointEntity point3 = new PointEntity(30, 30);
        System.out.println(point);
        System.out.println(point2);
        System.out.println(point3);
    }*/
}
/**
 * 0
 * POINT
 * 5
 * 2FF
 * 330
 * 1F
 * 100
 * AcDbEntity
 * 8
 * 0
 * 100
 * AcDbPoint
 * 10
 * 2.0
 * 20
 * 2.0
 * 30
 * 0.0
 */


