package com.ruoyi.hh;

import com.ruoyi.hh.hhhz.DXF.DXFBuilder;
import com.ruoyi.hh.hhhz.DXF.entities.*;
import com.ruoyi.hh.hhhz.DXF.entities.base.Line;
import com.ruoyi.hh.hhhz.DXF.entities.base.Point;
import com.ruoyi.hh.hhhz.DXF.entities.dim.AlignedDimension;
import com.ruoyi.hh.hhhz.DXF.entities.dim.OrdinateDimension;
import com.ruoyi.hh.hhhz.DXF.entities.dim.RotatedDimension;

public class MainTest {

    public static void main(String[] args) {
        DXFBuilder dxfBuilder = new DXFBuilder();
        Entities entities = dxfBuilder.getEntities();
        // 直线
        /*LineEntity lineEntity1 = new LineEntity(
                new Line(
                        new Point(0, 0, 0, 0),
                        new Point(10, 1, 0, 0),
                        0, "0"
                ));

        entities.addEntity(lineEntity1);*/

        // DIM
        Point defPoint = new Point(10, 2, 0, 0);
        Point midPoint = new Point(5, 2, 0, 0);
        Point p1 = new Point(0, 0, 0, 0);
        Point p2 = new Point(10, 2, 0, 0);
        AlignedDimensionEntity a = new AlignedDimensionEntity(
                new AlignedDimension(
                        defPoint, midPoint, p1, p2, 0, "0",1.0,""
                ));
        entities.addEntity(a);


        // 直线
        LineEntity lineEntity2 = new LineEntity(
                new Line(
                        new Point(0, 0, 0, 0),
                        new Point(10, 10, 0, 0),
                        "ACAD_ISO02W100",
                        0, "0"
                ));
        entities.addEntity(lineEntity2);


        // DIM
        Point defPoint1 = new Point(10, 12, 0, 0);
        Point midPoint1 = new Point(5, 6, 0, 0);
        Point p11 = new Point(0, 0, 0, 0);
        Point p21 = new Point(10, 10, 0, 0);
        AlignedDimensionEntity a1 = new AlignedDimensionEntity(new AlignedDimension(defPoint1, midPoint1, p11, p21, 0, "0",1.5,""));
        RotatedDimensionEntity a2 = new RotatedDimensionEntity(new RotatedDimension(defPoint1, midPoint1, p11, p21, 0, 0, "0",2.0,""));
        entities.addEntity(a1);
        entities.addEntity(a2);

        // DIM2
        Point midPoint2 = new Point(5, 8, 0, 0);
        Point p12 = new Point(5.5, 8, 0, 0);
        Point p22 = new Point(10, 10, 0, 0);
        OrdinateDimensionEntity o = new OrdinateDimensionEntity(new OrdinateDimension(midPoint2, p12, p22, 0, "0",2.5,""));
        entities.addEntity(o);

        entities.showEntitiesCountStatus();
        //entities.deleteEntity(ray);
        //entities.showEntitiesStatus();
        dxfBuilder.toFile("C:\\Users\\Z\\Desktop\\CAD\\TestFolder\\aa.dxf", "aa");
    }
}
