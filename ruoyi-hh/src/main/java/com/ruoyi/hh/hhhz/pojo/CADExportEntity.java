package com.ruoyi.hh.hhhz.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruoyi.hh.hhhz.DXF.entities.dim.AlignedDimension;
import com.ruoyi.hh.hhhz.DXF.entities.dim.RotatedDimension;
import com.ruoyi.hh.hhhz.DXF.entities.base.*;

public class CADExportEntity {

    @JsonProperty("exportId")
    private String exportId;

    @JsonProperty("Points")
    private Point[] points;

    @JsonProperty("Lines")
    private Line[] lines;

    @JsonProperty("Circles")
    private Circle[] circles;

    @JsonProperty("Arcs")
    private Arc[] arcs;

    @JsonProperty("PLines")
    private PLine[] plines;

    @JsonProperty("Texts")
    private Text[] texts;

    @JsonProperty("RotatedDIMs")
    private RotatedDimension[] rotatedDimensions;

    @JsonProperty("AlignedDIMs")
    private AlignedDimension[] alignedDimensions;

    public CADExportEntity() {
    }

    public Arc[] getArcs() {
        return arcs;
    }

    public void setArcs(Arc[] arcs) {
        this.arcs = arcs;
    }

    public PLine[] getPLines() {
        return plines;
    }

    public void setPLines(PLine[] PLines) {
        this.plines = PLines;
    }

    public RotatedDimension[] getRotatedDimensions() {
        return rotatedDimensions;
    }

    public void setRotatedDimensions(RotatedDimension[] rotatedDimensions) {
        this.rotatedDimensions = rotatedDimensions;
    }

    public AlignedDimension[] getAlignedDimensions() {
        return alignedDimensions;
    }

    public void setAlignedDimensions(AlignedDimension[] alignedDimensions) {
        this.alignedDimensions = alignedDimensions;
    }

    public Text[] getTexts() {
        return texts;
    }

    public void setTexts(Text[] texts) {
        this.texts = texts;
    }

    public String getExportId() {
        return exportId;
    }

    public void setExportId(String exportId) {
        this.exportId = exportId;
    }

    public Point[] getPoints() {
        return points;
    }

    public void setPoints(Point[] points) {
        this.points = points;
    }

    public Line[] getLines() {
        return lines;
    }

    public void setLines(Line[] lines) {
        this.lines = lines;
    }

    public Circle[] getCircles() {
        return circles;
    }

    public void setCircles(Circle[] circles) {
        this.circles = circles;
    }
}
