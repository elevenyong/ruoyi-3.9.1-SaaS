package com.ruoyi.hh.hhhz.DXF.entities.dim;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.hh.hhhz.DXF.entities.base.BaseEntity;
import com.ruoyi.hh.hhhz.DXF.entities.base.Point;

public class CommDimension extends BaseEntity {
    @JsonProperty("defPoint")
    private Point defPoint;
    @JsonProperty("midPoint")
    private Point midPoint;
    @JsonProperty("fontSize")
    private Double fontSize = 1.0;
    @JsonProperty("text")
    private String text = "";

    public CommDimension() {
    }

    private final String ENTITY_SUBCLASS = "100\nAcDbDimension\n";

    /*public CommDimension(Point defPoint, Point midPoint, int color, String layer) {
        this.defPoint = defPoint;
        this.midPoint = midPoint;
        setColor(color);
        setLayer(layer);
    }*/

    public CommDimension(Point defPoint, Point midPoint, int color, String layer, Double fontSize, String text) {
        this.defPoint = defPoint;
        this.midPoint = midPoint;
        if (fontSize != null && !fontSize.isNaN()) {
            this.fontSize = fontSize;
        }
        if (text != null && text.length() > 0) {
            this.text = text;
        }
        setColor(color);
        setLayer(layer);
    }

    public String getUserInputText() {
        if (text != null && text.length() > 0) {
            return "1\n" + text + "\n";
        }
        return "";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Point getDefPoint() {
        return defPoint;
    }

    public void setDefPoint(Point defPoint) {
        this.defPoint = defPoint;
    }

    public Point getMidPoint() {
        return midPoint;
    }

    public void setMidPoint(Point midPoint) {
        this.midPoint = midPoint;
    }

    public Double getFontSize() {
        return fontSize;
    }

    public void setFontSize(Double fontSize) {
        this.fontSize = fontSize;
    }

    public String getDimensionSubclass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_SUBCLASS);
        sb.append("10\n" + this.defPoint.getX() + "\n");
        sb.append("20\n" + this.defPoint.getY() + "\n");
        sb.append("30\n" + this.defPoint.getZ() + "\n");
        sb.append("11\n" + this.midPoint.getX() + "\n");
        sb.append("21\n" + this.midPoint.getY() + "\n");
        sb.append("31\n" + this.midPoint.getZ() + "\n");
        //sb.append("42\n0\n");
        return sb.toString();
    }

    public String getDSTYLE() {
        StringBuilder sb = new StringBuilder();
        sb.append("1001\nACAD\n");
        sb.append("1000\nDSTYLE\n");
        sb.append("1002\n{\n");
        sb.append("1070\n140\n");
        sb.append("1040\n" + this.fontSize + "\n");
        sb.append("1002\n}\n");
        //sb.append("42\n0\n");
        return sb.toString();
    }
}
