package com.ruoyi.hh.hhhz.DXF.entities.dim;


import com.ruoyi.hh.hhhz.DXF.entities.base.Point;

public class RotatedDimension extends CommDimension {
    private Point point1;
    private Point point2;
    private int angle = 0;
    private final String ENTITY_SUBCLASS = "100\nAcDbAlignedDimension\n";

    public RotatedDimension() {
    }

    /*public RotatedDimension(Point defPoint, Point midPoint, Point point1, Point point2, int angle, int color, String layer) {
        super(defPoint, midPoint, color, layer);
        this.point1 = point1;
        this.point2 = point2;
        this.angle = angle;
    }*/

    public RotatedDimension(Point defPoint, Point midPoint, Point point1, Point point2, int angle, int color, String layer, Double fontSize, String text) {
        super(defPoint, midPoint, color, layer, fontSize, text);
        this.point1 = point1;
        this.point2 = point2;
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    @Override
    public String getDimensionSubclass() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getDimensionSubclass());
        sb.append("70\n32\n");
        sb.append("71\n5\n");
        sb.append(super.getUserInputText());
        sb.append("3\n" + "Standard" + "\n");
        return sb.toString();
    }

    public String getAlignedDimensionSubclass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_SUBCLASS);
        sb.append("13\n" + this.point1.getX() + "\n");
        sb.append("23\n" + this.point1.getY() + "\n");
        sb.append("33\n" + this.point1.getZ() + "\n");
        sb.append("14\n" + this.point2.getX() + "\n");
        sb.append("24\n" + this.point2.getY() + "\n");
        sb.append("34\n" + this.point2.getZ() + "\n");
        sb.append("50\n" + this.getAngle() + "\n");
        sb.append("100\nAcDbRotatedDimension\n");
        return sb.toString();
    }
}
