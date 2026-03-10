package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.base.Line;

public class LineEntity extends Line implements Entity {
    private final static String ENTITY_CLASS = "0\nLINE\n";
    //private String layer = "0";
    //private String handle = "0000";

    /*@Override
    public String getHandle() {
        return this.handle;
    }*/

    /*@Override
    public String setHandle(String handle) {
        this.handle = handle;
        return handle;
    }*/

    /*public LineEntity(Line line, String layer) {
        super(line.getStart(), line.getEnd(), line.getColor(), layer);
        //this.layer = layer;
    }*/

    public LineEntity(Line line) {
        super(line.getStart(), line.getEnd(), line.getLineType(), line.getColor(), line.getLayer());
    }

    /*public LineEntity(Point start, Point end, String layer) {
        super(start, end);
        //this.layer = layer;
        setLayer(layer);
    }

    public LineEntity(Point start, Point end) {
        super(start, end);
    }*/

    public LineEntity() {
        super();
    }


    /*public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }*/

    @Override
    public String getEntityClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_CLASS);
        //sb.append("5\n" + handle + "\n");
        sb.append("5\n" + getHandle() + "\n");
        sb.append(SOFT_POINTER);
        sb.append(SUBCLASS);
        //sb.append("8\n" + layer + "\n");
        //sb.append("62\n" + color + "\n");
        sb.append("8\n" + getLayer() + "\n");
        sb.append("62\n" + getColor() + "\n");
        if (getLineType() != null) {
            sb.append("6\n" + getLineType() + "\n");
        }
        return sb.toString();
    }

    @Override
    public String toDXF() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntityClass());
        sb.append(getLineSubClass());
        return sb.toString();
    }

    /*public static void main(String[] args) {
        LineEntity lineEntity = new LineEntity(new Point(0, 0, 0), new Point(1, 1, 1), "0");
        lineEntity.addPoint(new Point(2, 2, 2));
        lineEntity.addPoint(new Point(3, 3, 3));
        System.out.println(lineEntity.toDXF());
    }*/
}
/**
 * 0
 * LINE
 * 5
 * 2AB
 * 330
 * 1F
 * 100
 * AcDbEntity
 * 8
 * 0
 * 100
 * AcDbLine
 * 10
 * 0.0
 * 20
 * 0.0
 * 30
 * 0.0
 * 11
 * 1.0
 * 21
 * 1.0
 * 31
 * 0.0
 * 0
 */
