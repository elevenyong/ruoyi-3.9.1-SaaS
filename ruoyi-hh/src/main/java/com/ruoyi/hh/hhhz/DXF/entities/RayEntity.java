package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.base.Ray;

public class RayEntity extends Ray implements Entity {

    private final static String ENTITY_CLASS = "0\nRAY\n";

    public RayEntity(Ray ray) {
        super(ray.getOrigin(), ray.getDirection(),ray.getColor(),ray.getLayer());
    }
    /*public RayEntity(Point origin, Point direction, String layer) {
        super(origin, direction);
        this.layer = layer;
    }

    public RayEntity(Point origin, Point direction) {
        this(origin, direction, "0");
    }*/

    @Override
    public String toDXF() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntityClass());
        sb.append(getRaySubClass());
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
        RayEntity e = new RayEntity(new Point(20, 20), new Point(5, 0), "0");
        System.out.println(e.toDXF());
    }*/
}
