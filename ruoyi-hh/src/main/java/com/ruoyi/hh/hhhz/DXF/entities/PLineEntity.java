package com.ruoyi.hh.hhhz.DXF.entities;


import com.ruoyi.hh.hhhz.DXF.entities.base.PLine;

public class PLineEntity extends PLine implements Entity {
    private final static String ENTITY_CLASS = "0\nLWPOLYLINE\n";


    public PLineEntity(PLine pLine) throws Exception {
        super(pLine.getPoints(), pLine.getIsClose(), pLine.getColor(), pLine.getLayer());
    }


    /*public PLineEntity() {
        super();
    }

    public PLineEntity(PLine pline, String layer) throws Exception {
        super(pline.getPoints());
        this.layer = layer;
    }

    public PLineEntity(List<Point> points, String layer) throws Exception {
        super(points);
        this.layer = layer;
    }

    public PLineEntity(Point point1, Point point2, String layer) throws Exception {
        this(Arrays.asList(point1, point2), layer);
    }

    public PLineEntity(List<Point> points) throws Exception {
        this(points, "0");
    }

    public PLineEntity(Point point1, Point point2) throws Exception {
        this(Arrays.asList(point1, point2), "0");
    }*/

    public PLineEntity close() {
        setIsClose("1");
        return this;
    }

    @Override
    public String toDXF() {
        StringBuilder sb = new StringBuilder();
        sb.append(getEntityClass());
        sb.append(getPLineSubClass());
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


    /*public static void main(String[] args) throws Exception {
        PLineEntity e = new PLineEntity(Arrays.asList(new Point(20, 20, 0, 0), new Point(5, 0, 0, 0), new Point(0, 50, 0, 0)), "0");
        System.out.println(e.toDXF());
    }*/
}

/**
 * 0
 * LWPOLYLINE
 * 5
 * 26F
 * 330
 * 1F
 * 100
 * AcDbEntity
 * 8
 * 0
 * 100
 * AcDbPolyline
 * 90
 * 4
 * 70
 * 1
 * 43
 * 0.0
 * 10
 * 10.0
 * 20
 * 10.0
 * 10
 * 21.0
 * 20
 * 21.0
 * 42
 * -0.5223894874633081
 * 10
 * 25.04220020536527
 * 20
 * 23.81315103254991
 * 42
 * -0.7231350770814795
 * 10
 * 28.64966568463582
 * 20
 * 22.62317005246933
 * 42
 * -0.5306754601577078
 */
