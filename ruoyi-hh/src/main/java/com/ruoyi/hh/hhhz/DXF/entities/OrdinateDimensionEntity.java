package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.dim.OrdinateDimension;

public class OrdinateDimensionEntity extends OrdinateDimension implements Entity {

    private final static String ENTITY_CLASS = "0\nDIMENSION\n";

    public OrdinateDimensionEntity(OrdinateDimension dimension) {
        super(dimension.getMidPoint(), dimension.getPoint1(), dimension.getPoint2(), dimension.getColor(), dimension.getLayer(), dimension.getFontSize(),dimension.getText());
    }

    /*public OrdinateDimensionEntity(Point midPoint, Point p1, Point p2, String layer) {
        super(midPoint, p1, p2);
        this.layer = layer;
    }

    public OrdinateDimensionEntity(Point midPoint, Point p1, Point p2) {
        super(midPoint, p1, p2);

    }*/

    @Override
    public String toDXF() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntityClass());
        sb.append(getDimensionSubclass());
        sb.append(getOrdinateDimensionSubclass());
        sb.append(getDSTYLE());
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
        Point midPoint = new Point(10, 2, 0);
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(10, 0, 0);
        OrdinateDimensionEntity a = new OrdinateDimensionEntity(midPoint, p1, p2);
        System.out.println(a.toDXF());
    }*/
}
