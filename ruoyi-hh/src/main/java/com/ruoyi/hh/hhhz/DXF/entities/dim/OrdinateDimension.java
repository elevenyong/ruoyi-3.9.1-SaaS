package com.ruoyi.hh.hhhz.DXF.entities.dim;


import com.ruoyi.hh.hhhz.DXF.entities.base.Point;

public class OrdinateDimension extends CommDimension {
    private Point point1;
    private Point point2;
    private final String ENTITY_SUBCLASS = "100\nAcDbOrdinateDimension\n";


    /*public OrdinateDimension(Point midPoint, Point point1, Point point2, int color, String layer) {
        super(new Point(0, 0, 0, 0), midPoint, color, layer);
        this.point1 = point1;
        this.point2 = point2;
    }*/

    public OrdinateDimension(Point midPoint, Point point1, Point point2, int color, String layer, double fontSize,String text) {
        super(new Point(0, 0, 0, 0), midPoint, color, layer, fontSize,text);
        this.point1 = point1;
        this.point2 = point2;
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
        sb.append("70\n38\n");
        sb.append("71\n5\n");
        sb.append(super.getUserInputText());
        sb.append("3\n" + "Standard" + "\n");
        return sb.toString();
    }

    public String getOrdinateDimensionSubclass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_SUBCLASS);
        sb.append("13\n" + this.point1.getX() + "\n");
        sb.append("23\n" + this.point1.getY() + "\n");
        sb.append("33\n" + this.point1.getZ() + "\n");
        sb.append("14\n" + this.point2.getX() + "\n");
        sb.append("24\n" + this.point2.getY() + "\n");
        sb.append("34\n" + this.point2.getZ() + "\n");
        return sb.toString();
    }
}
