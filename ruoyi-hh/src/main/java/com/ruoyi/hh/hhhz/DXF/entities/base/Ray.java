package com.ruoyi.hh.hhhz.DXF.entities.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ray extends BaseEntity {
    @JsonProperty("origin")
    private Point origin;
    @JsonProperty("direction")
    private Point direction;
    private final String ENTITY_SUBCLASS = "100\nAcDbRay\n";

    public Ray() {
    }

    //@JsonCreator
    //public Ray(Point origin, Point direction) {
    public Ray(Point origin, Point direction, int color, String layer) {
        this.origin = origin;
        this.direction = direction;
        setColor(color);
        setLayer(layer);
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public Point getDirection() {
        return direction;
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }

    public String getRaySubClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_SUBCLASS);
        sb.append("10\n" + this.getOrigin().getX() + "\n");
        sb.append("20\n" + this.getOrigin().getY() + "\n");
        sb.append("30\n" + this.getOrigin().getZ() + "\n");
        sb.append("11\n" + this.getDirection().getX() + "\n");
        sb.append("21\n" + this.getDirection().getY() + "\n");
        sb.append("31\n" + this.getDirection().getZ() + "\n");
        return sb.toString();
    }
}
