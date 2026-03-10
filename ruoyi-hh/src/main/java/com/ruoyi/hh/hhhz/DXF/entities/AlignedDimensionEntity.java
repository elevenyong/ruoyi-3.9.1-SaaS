package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.dim.AlignedDimension;

public class AlignedDimensionEntity extends AlignedDimension implements Entity {

    private final static String ENTITY_CLASS = "0\nDIMENSION\n";

    public AlignedDimensionEntity() {
    }

    public AlignedDimensionEntity(AlignedDimension dimension) {
        super(dimension.getDefPoint(), dimension.getMidPoint(), dimension.getPoint1(), dimension.getPoint2(), dimension.getColor(), dimension.getLayer(), dimension.getFontSize(), dimension.getText());
    }

    /*public AlignedDimensionEntity(Point defPoint, Point midPoint, Point p1, Point p2, String layer) {
        super(defPoint, midPoint, p1, p2);
        this.layer = layer;
    }

    public AlignedDimensionEntity(Point defPoint, Point midPoint, Point p1, Point p2) {
        this(defPoint, midPoint, p1, p2, "0");
    }*/


    @Override
    public String toDXF() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntityClass());
        sb.append(getDimensionSubclass());
        sb.append(getAlignedDimensionSubclass());
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
        Point defPoint = new Point(10, 2, 0);
        Point midPoint = new Point(5, 2, 0);
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(10, 0, 0);
        AlignedDimensionEntity a = new AlignedDimensionEntity(defPoint, midPoint, p1, p2);
        System.out.println(a.toDXF());
    }*/
}
