package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.base.Ellipse;

public class EllipseEntity extends Ellipse implements Entity {
    private final static String ENTITY_CLASS = "0\nELLIPSE\n";


    public EllipseEntity(Ellipse ellipse) {
        super(ellipse.getCenter(), ellipse.getEndpoint(), ellipse.getScale(), ellipse.getColor(), ellipse.getLayer());
    }
    /*public EllipseEntity(Point center, Point endpoint, double scale, String layer) {
        super(center, endpoint, scale);
        this.layer = layer;
    }

    public EllipseEntity(Point center, Point endpoint, double scale) {
        this(center, endpoint, scale, "0");
    }*/


    @Override
    public String toDXF() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntityClass());
        sb.append(getEllipseSubClass());
        return sb.toString();
    }

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

    /*public static void main(String[] args) {
        EllipseEntity e = new EllipseEntity(new Point(20, 20), new Point(5, 0), 0.6, "0");
        System.out.println(e.toDXF());
    }*/
}
