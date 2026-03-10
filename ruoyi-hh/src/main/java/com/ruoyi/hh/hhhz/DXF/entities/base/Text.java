package com.ruoyi.hh.hhhz.DXF.entities.base;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Text extends BaseEntity {
    // 位置
    @JsonProperty("location")
    private Point location;

    //文本
    @JsonProperty("text")
    private String text;
    // 文本高度
    @JsonProperty("height")
    private double height = 1;
    @JsonProperty("hAlign")
    private int hAlign = 0;
    @JsonProperty("vAlign")
    private int vAlign = 0;

    private final String ENTITY_SUBCLASS = "100\nAcDbText\n";


    public Text() {
    }

    public Text(Point location, String text, double height, int hAlign, int vAlign, int color, String layer) {
        this.location = location;
        this.text = text;
        this.height = height;
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        setColor(color);
        setLayer(layer);
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int gethAlign() {
        return hAlign;
    }

    public void sethAlign(int hAlign) {
        this.hAlign = hAlign;
    }

    public int getvAlign() {
        return vAlign;
    }

    public void setvAlign(int vAlign) {
        this.vAlign = vAlign;
    }

    public String getTextSubClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_SUBCLASS);
        sb.append("10\n" + this.location.getX() + "\n");
        sb.append("20\n" + this.location.getY() + "\n");
        sb.append("30\n" + this.location.getZ() + "\n");
        sb.append("40\n" + this.height + "\n");
        sb.append("1\n" + this.text + "\n");
        sb.append("72\n" + this.hAlign + "\n");
        if (hAlign != 0 || vAlign != 0) {
            sb.append("11\n" + this.location.getX() + "\n");
            sb.append("21\n" + this.location.getY() + "\n");
            sb.append("31\n" + this.location.getZ() + "\n");
        }
        sb.append(ENTITY_SUBCLASS);
        sb.append("73\n" + this.vAlign + "\n");
        return sb.toString();
    }

}


/**
 * 0
 * TEXT
 * 5
 * 3EF
 * 330
 * 1F
 * 100
 * AcDbEntity
 * 8
 * 0
 * -----------------
 * 100
 * AcDbText
 * 10
 * -27.1366014104782
 * 20
 * 18.70956709559217
 * 30
 * 0.0
 * 40
 * 3.577190873485733
 * 1
 * hello
 * 100
 * AcDbText
 */
