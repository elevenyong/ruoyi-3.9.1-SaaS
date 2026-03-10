package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.base.PLineBase;

@Deprecated
public class PLineBaseEntity extends PLineBase implements Entity {
    /*@Override
    public String toDXF() {
        return null;
    }
*/
    @Override
    public String getEntityClass() {
        return null;
    }



    /*private final static String ENTITY_CLASS = "0\nLINE\n";
    private String layer="0";
    private String handle = "0000";

    public PLineBaseEntity(PLineBase line, String layer) throws Exception {
        super(line.getPoints());
        this.layer = layer;
    }

    public PLineBaseEntity(PLineBase line) throws Exception {
        super(line.getPoints());

    }

    public PLineBaseEntity(List<Point> points, String layer) throws Exception {
        super(points);
        this.layer = layer;
    }

    public PLineBaseEntity(Point start, Point end, String layer) {
        super(start, end);
        this.layer = layer;
    }

    public PLineBaseEntity() {
        super();

    }


    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    @Override
    public String getEntityClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_CLASS);
        sb.append("5\n" + handle + "\n");
        sb.append(SOFT_POINTER);
        sb.append(SUBCLASS);
        sb.append("8\n" + layer + "\n");
        return sb.toString();
    }

    @Override
    public String toDXF() {
        Point[] points = super.getPoints().toArray(new Point[0]);
        if (points.length < 2) {
            throw new IllegalArgumentException("Line must have at least 2 points");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < points.length - 1; i++) {
            sb.append(getEntityClass());
            sb.append(getLineSubClass(i));
        }
        return sb.toString();
    }
*/
    /*public static void main(String[] args) {
        LineEntity lineEntity = new LineEntity(new Point(0, 0, 0), new Point(1, 1, 1), "0");
        lineEntity.addPoint(new Point(2, 2, 2));
        lineEntity.addPoint(new Point(3, 3, 3));
        System.out.println(lineEntity.toDXF());
    }*/
}



/*public class PLineBaseEntity extends Line implements Entity {
    private final static String ENTITY_CLASS = "0\nLINE\n";
    private String layer;


    public PLineBaseEntity(Line line, String layer) throws Exception {
        super(line.getPoints());
        this.layer = layer;
    }

    public PLineBaseEntity(Line line) throws Exception {
        super(line.getPoints());
        this.layer = "0";
    }

    public PLineBaseEntity(List<Point> points, String layer) throws Exception {
        super(points);
        this.layer = layer;
    }

    public PLineBaseEntity(Point start, Point end, String layer) {
        super(start, end);
        this.layer = layer;
    }

    public PLineBaseEntity() {
        super();
        this.layer = "0";
    }


    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    @Override
    public String getEntityClass(String handle) {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_CLASS);
        sb.append("5\n" + handle + "\n");
        sb.append(SOFT_POINTER);
        sb.append(SUBCLASS);
        sb.append("8\n" + layer + "\n");
        return sb.toString();
    }

    @Override
    public String toDXF(String handle) {
        Point[] points = super.getPoints().toArray(new Point[0]);
        if (points.length < 2) {
            throw new IllegalArgumentException("Line must have at least 2 points");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < points.length - 1; i++) {
            sb.append(getEntityClass(handle));
            sb.append(getLineSubClass(i));
        }
        return sb.toString();
    }

    *//*public static void main(String[] args) {
        LineEntity lineEntity = new LineEntity(new Point(0, 0, 0), new Point(1, 1, 1), "0");
        lineEntity.addPoint(new Point(2, 2, 2));
        lineEntity.addPoint(new Point(3, 3, 3));
        System.out.println(lineEntity.toDXF());
    }*//*
}*/
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
