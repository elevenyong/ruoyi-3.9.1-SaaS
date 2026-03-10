package com.ruoyi.hh.hhhz.DXF.entities.base;

public class Ellipse extends BaseEntity {

    private Point center;
    private Point endpoint;
    private double scale;
    private final String ENTITY_SUBCLASS = "100\nAcDbEllipse\n";

    public Ellipse() {
    }

    public Ellipse(Point center, Point endpoint, double scale, int color, String layer) {
        this.center = center;
        this.endpoint = endpoint;
        this.scale = scale;
        setColor(color);
        setLayer(layer);
    }

    public Point getCenter() {
        return center;
    }

    public Point getEndpoint() {
        return endpoint;
    }

    public double getScale() {
        return scale;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public void setEndpoint(Point endpoint) {
        this.endpoint = endpoint;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public String getEllipseSubClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_SUBCLASS);
        sb.append("10\n" + getCenter().getX() + "\n");
        sb.append("20\n" + getCenter().getY() + "\n");
        sb.append("30\n" + getCenter().getZ() + "\n");
        sb.append("11\n" + getEndpoint().getX() + "\n");
        sb.append("21\n" + getEndpoint().getY() + "\n");
        sb.append("31\n" + getEndpoint().getZ() + "\n");
        sb.append("40\n" + getScale() + "\n");
        sb.append("41\n0.0\n");//起点参数 （对于闭合椭圆，该值为 0.0）
        sb.append("42\n6.283185307179586\n");//端点参数 （对于闭合椭圆，该值为 2pi）
        return sb.toString();
    }
}
