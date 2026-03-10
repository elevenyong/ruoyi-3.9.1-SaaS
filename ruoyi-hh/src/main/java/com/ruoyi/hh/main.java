package com.ruoyi.hh;

public class main {
    /*public static void main.java.main(String[] args) throws Exception {
        String EOF = "  0\n" + "EOF\n";

        String header = new Header().getHeader();
        String classes = new Classes().getClasses();
        String tables = new Tables().getTables();
        String blocks = new Blocks().getBlocks();

        Entities entities = new Entities().getEntities();

        // 点
        entities.addEntity(new PointEntity(0, 10));
        entities.addEntity(new PointEntity(0, 20));

        // 线
        LineEntity lineEntity = new LineEntity();
        lineEntity.addPoint(new Point(0, 0));
        lineEntity.addPoint(new Point(0, 1));
        lineEntity.addPoint(new Point(1, 1));
        lineEntity.addPoint(new Point(1, 0));
        lineEntity.addPoint(new Point(0, 0));
        entities.addEntity(lineEntity);

        // 圆
        CircleEntity circle1 = new CircleEntity(10, 10, 10);
        CircleEntity circle = new CircleEntity(20, 20, 10);
        entities.addEntity(circle);
        entities.addEntity(circle1);

        // 圆弧
        ArcEntity arc = new ArcEntity(30, 30, 10, 30, 60);
        entities.addEntity(arc);

        // 椭圆
        EllipseEntity e = new EllipseEntity(new Point(10, 30), new Point(5, 0), 0.6);
        entities.addEntity(e);

        // 纯直线多段线
        PLineEntity pl = new PLineEntity(Arrays.asList(new Point(-30, 0, 0, 0), new Point(-40, 0, 0, 0), new Point(-40, 20, 0, 0), new Point(-30, 0, 0, 0)), "0");
        entities.addEntity(pl);

        // 圆弧多段线
        PLineEntity pl2 = new PLineEntity(Arrays.asList(new Point(0, 0, 0, 0), new Point(-10, 0, 0, 1), new Point(-10, 10, 0, -0.5), new Point(-10, 20, 0, 0), new Point(0, 0, 0, 0)), "0");
        entities.addEntity(pl2);

        // 文字
        TextEntity text = new TextEntity(new Point(40, 40), "HHHZ.你好你好！123456", 0.6, "0");
        entities.addEntity(text);

        // 射线
        RayEntity ray = new RayEntity(new Point(0, 0), new Point(10, 10));
        entities.addEntity(ray);

        // 多行线


        // 合并
        String entitiesAsString = entities.getEntitiesAsString();
        String objects = new Objects().getObjects();

        StringBuilder sb = new StringBuilder();
        sb.append(header);
        sb.append(classes);
        sb.append(tables);
        sb.append(blocks);
        sb.append(entitiesAsString);
        sb.append(objects);
        sb.append(EOF);


        //try (FileWriter fileWriter = new FileWriter("C:\\Users\\Z\\Desktop\\CAD\\main.java.DXF\\aa.dxf");) {
        try (BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("C:\\Users\\Z\\Desktop\\CAD\\main.java.DXF\\aa.dxf", false), "gbk"));) {
            fileWriter.write(sb.toString());
            fileWriter.flush();
        }
    }*/

    /*public static void main(String[] args) throws Exception {
        DXFBuilder dxf = new DXFBuilder();
        Entities entities = dxf.getEntities();

        // 点2
        entities.addEntity(new PointEntity(0, 10));
        entities.addEntity(new PointEntity(0, 20));

        // 线
        *//*LineEntity lineEntity = new LineEntity();
        lineEntity.addPoint(new Point(0, 0));
        lineEntity.addPoint(new Point(0, 1));
        lineEntity.addPoint(new Point(1, 1));
        lineEntity.addPoint(new Point(1, 0));
        lineEntity.addPoint(new Point(0, 0));
        entities.addEntity(lineEntity);*//*
        LineEntity lineEntity = new LineEntity(new Point(0, 0), new Point(1, 1));

        // 圆
        CircleEntity circle1 = new CircleEntity(10, 10, 10);
        CircleEntity circle = new CircleEntity(20, 20, 10);
        entities.addEntity(circle);
        entities.addEntity(circle1);

        // 圆弧
        ArcEntity arc = new ArcEntity(30, 30, 10, 30, 60);
        entities.addEntity(arc);

        // 椭圆
        EllipseEntity e = new EllipseEntity(new Point(10, 30), new Point(5, 0), 0.6);
        entities.addEntity(e);

        // 纯直线多段线
        PLineEntity pl = new PLineEntity(Arrays.asList(new Point(-30, 0, 0, 0), new Point(-40, 0, 0, 0), new Point(-40, 20, 0, 0), new Point(-30, 0, 0, 0)), "0");
        entities.addEntity(pl);

        // 圆弧多段线
        PLineEntity pl2 = new PLineEntity(Arrays.asList(new Point(0, 0, 0, 0), new Point(-10, 0, 0, 1), new Point(-10, 10, 0, -0.5), new Point(-10, 20, 0, 0), new Point(0, 0, 0, 0)), "0");
        entities.addEntity(pl2);

        // 文字
        TextEntity text = new TextEntity(new Point(40, 40), "HHHZ.你好你好！123456", 0.6, "0");
        entities.addEntity(text);

        // 射线
        RayEntity ray = new RayEntity(new Point(0, 0), new Point(10, 10));
        entities.addEntity(ray);

        // 直线
        LineEntity lineEntity1 = new LineEntity();
        *//*lineEntity1.addPoint(new Point(0, 0));
        lineEntity1.addPoint(new Point(10, 0));*//*
        lineEntity1.setStart(new Point(0, 0));
        lineEntity1.setEnd(new Point(10, 0));
        entities.addEntity(lineEntity1);

        // DIM
        Point defPoint = new Point(10, 2, 0);
        Point midPoint = new Point(5, 2, 0);
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(10, 0, 0);
        AlignedDimensionEntity a = new AlignedDimensionEntity(defPoint, midPoint, p1, p2);
        entities.addEntity(a);


        // 直线
        LineEntity lineEntity2 = new LineEntity();
        *//*lineEntity1.addPoint(new Point(0, 0));
        lineEntity1.addPoint(new Point(10, 10));*//*
        lineEntity2.setStart(new Point(0, 0));
        lineEntity2.setEnd(new Point(10, 10));
        entities.addEntity(lineEntity2);

        // DIM
        Point defPoint1 = new Point(10, 12, 0);
        Point midPoint1 = new Point(5, 6, 0);
        Point p11 = new Point(0, 0, 0);
        Point p21 = new Point(10, 10, 0);
        AlignedDimensionEntity a1 = new AlignedDimensionEntity(defPoint1, midPoint1, p11, p21);
        RotatedDimensionEntity a2 = new RotatedDimensionEntity(defPoint1, midPoint1, p11, p21);
        entities.addEntity(a1);
        entities.addEntity(a2);

        // DIM2
        Point midPoint2 = new Point(5, 8, 0);
        Point p12 = new Point(5.5, 8, 0);
        Point p22 = new Point(10, 10, 0);
        OrdinateDimensionEntity o = new OrdinateDimensionEntity(midPoint2, p12, p22);
        entities.addEntity(o);

        entities.showEntitiesCountStatus();
        //entities.deleteEntity(ray);
        //entities.showEntitiesStatus();
        dxf.toFile("C:\\Users\\Z\\Desktop\\CAD\\main.java.DXF\\aa.dxf", "aa");
    }*/




}
