package com.ruoyi.hh.hhhz.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hh.hhhz.DXF.DXFBuilder;
import com.ruoyi.hh.hhhz.DXF.entities.*;
import com.ruoyi.hh.hhhz.DXF.entities.base.*;
import com.ruoyi.hh.hhhz.DXF.entities.dim.AlignedDimension;
import com.ruoyi.hh.hhhz.DXF.entities.dim.RotatedDimension;
import com.ruoyi.hh.hhhz.pojo.CADExportEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/CAD_Deprecated")
@Deprecated
public class CADExportController {

    @Value("${CADExport.DXFFilePath}")
    private String dxfFilePath;
    @Value("${CADExport.DWGFilePath}")
    private String dwgFilePath;
    @Value("${CADExport.CoverToolPath}")
    private String convertToolPath;

    @Log(title = "生成CAD文件", businessType = BusinessType.EXPORT)
    //@ApiOperation("导出CAD-dwg文件")
    @PostMapping("/export_Deprecated")
    @Deprecated
    public void CADExport(@ApiParam(name = "CADRecEntity", value = "{\n" +
            "    \"exportId\":\"001\",\n" +
            "    \"Points\":[\n" +
            "        {\n" +
            "            \"x\":0,\n" +
            "            \"y\":0\n" +
            "        },\n" +
            "        {\n" +
            "            \"x\":0,\n" +
            "            \"y\":1\n" +
            "        },\n" +
            "        {\n" +
            "            \"x\":0,\n" +
            "            \"y\":2\n" +
            "        }\n" +
            "    ],\n" +
            "    \"Lines\":[\n" +
            "        {\n" +
            "            \"start\":{\n" +
            "                \"x\":0,\n" +
            "                \"y\":0\n" +
            "            },\n" +
            "            \"end\":{\n" +
            "                \"x\":1,\n" +
            "                \"y\":1\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"start\":{\n" +
            "                \"x\":2,\n" +
            "                \"y\":2\n" +
            "            },\n" +
            "            \"end\":{\n" +
            "                \"x\":3,\n" +
            "                \"y\":3\n" +
            "            }\n" +
            "        },\n" +
            "        {\n" +
            "            \"start\":{\n" +
            "                \"x\":4,\n" +
            "                \"y\":4\n" +
            "            },\n" +
            "            \"end\":{\n" +
            "                \"x\":5,\n" +
            "                \"y\":5\n" +
            "            }\n" +
            "        }\n" +
            "    ],\n" +
            "    \"Circles\":[\n" +
            "        {\n" +
            "            \"center\":{\n" +
            "                \"x\":0,\n" +
            "                \"y\":1\n" +
            "            },\n" +
            "            \"radius\":10\n" +
            "        }\n" +
            "    ],\n" +
            "    \"Arcs\":[\n" +
            "        {\n" +
            "            \"center\":{\n" +
            "                \"x\":30,\n" +
            "                \"y\":30\n" +
            "            },\n" +
            "            \"radius\":10,\n" +
            "            \"startAngle\":30,\n" +
            "            \"endAngle\":60\n" +
            "        }\n" +
            "    ],\n" +
            "    \"PLines\":[\n" +
            "        [\n" +
            "            {\n" +
            "                \"x\":10,\n" +
            "                \"y\":10\n" +
            "            },\n" +
            "            {\n" +
            "                \"x\":20,\n" +
            "                \"y\":20\n" +
            "            },\n" +
            "            {\n" +
            "                \"x\":30,\n" +
            "                \"y\":10\n" +
            "            }\n" +
            "        ]\n" +
            "    ],\n" +
            "    \"Texts\":[\n" +
            "        {\n" +
            "            \"location\":{\n" +
            "                \"x\":10,\n" +
            "                \"y\":10\n" +
            "            },\n" +
            "            \"text\":\"Hello world!华鸿慧中\",\n" +
            "            \"height\":1\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\":{\n" +
            "                \"x\":10,\n" +
            "                \"y\":20\n" +
            "            },\n" +
            "            \"text\":\"1234567890\",\n" +
            "            \"height\":1\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\":{\n" +
            "                \"x\":10,\n" +
            "                \"y\":30\n" +
            "            },\n" +
            "            \"text\":\"ABCDEFGH\",\n" +
            "            \"height\":1\n" +
            "        },\n" +
            "        {\n" +
            "            \"location\":{\n" +
            "                \"x\":10,\n" +
            "                \"y\":40\n" +
            "            },\n" +
            "            \"text\":\"一二三四五六七八九十\",\n" +
            "            \"height\":1\n" +
            "        }\n" +
            "    ]\n" +
            "}", required = true) @RequestBody CADExportEntity CADRecEntity, HttpServletResponse response) throws Exception {
        DXFBuilder dxfBuilder = new DXFBuilder();
        Entities entities = dxfBuilder.getEntities();
        Point[] points = CADRecEntity.getPoints();
        if (points != null && points.length > 0) {
            for (Point point : points) {
                entities.addEntity(new PointEntity(point));
            }
        }
        PLine[] PLines = CADRecEntity.getPLines();
        if (PLines != null && PLines.length > 0) {
            for (PLine pLine : PLines) {
                PLineEntity tempPLineEntity = new PLineEntity(pLine);
                entities.addEntity(tempPLineEntity);
                // 区域填充
                if (pLine.isHatch()) {
                    /*if (pLine.getHatchId() == -1) {
                        throw new RuntimeException("区域填充对象hatchId不能为空");
                    }*/
                    List<BaseEntity> baseEntities = entities.getHatchs().get(pLine.getHatchId());
                    if (null != baseEntities) {
                        entities.getHatchs().get(pLine.getHatchId()).add(tempPLineEntity);
                    } else {
                        ArrayList<BaseEntity> hatchEntities = new ArrayList<>();
                        hatchEntities.add(tempPLineEntity);
                        entities.getHatchs().put(pLine.getHatchId(), hatchEntities);
                    }
                    //entities.addEntity(new HatchEntity(tempPLineEntity));
                }
            }
        }
        // 生成Hatch
        Set<Integer> keys = entities.getHatchs().keySet();
        for (Integer key : keys) {
            List<BaseEntity> baseEntities = entities.getHatchs().get(key);
            //entities.addEntity(new HatchEntity(baseEntities));
            // 没有设置HatchID则hatchId为-1，单独填充。所有hatchId为-1的图形单独填充，不与其他图形产生关联填充
            if (key == -1) {
                for (BaseEntity baseEntity : baseEntities) {
                    entities.addEntity(new HatchEntity(Arrays.asList(baseEntity)));
                }
            } else {
                entities.addEntity(new HatchEntity(baseEntities));
            }
        }
        Line[] lines = CADRecEntity.getLines();
        if (lines != null && lines.length > 0) {
            for (Line line : lines) {
                entities.addEntity(new LineEntity(line));
            }
        }
        Circle[] circles = CADRecEntity.getCircles();
        if (circles != null && circles.length > 0) {
            for (Circle circle : circles) {
                entities.addEntity(new CircleEntity(circle));
            }
        }
        Arc[] arcs = CADRecEntity.getArcs();
        if (arcs != null && arcs.length > 0) {
            for (Arc arc : arcs) {
                entities.addEntity(new ArcEntity(arc));
            }
        }


        Text[] texts = CADRecEntity.getTexts();
        if (texts != null && texts.length > 0) {
            for (Text text : texts) {
                entities.addEntity(new TextEntity(text));
            }
        }

        RotatedDimension[] rotaDIMs = CADRecEntity.getRotatedDimensions();
        if (rotaDIMs != null && rotaDIMs.length > 0) {
            for (RotatedDimension rotaDIM : rotaDIMs) {
                entities.addEntity(new RotatedDimensionEntity(rotaDIM));
            }
        }

        AlignedDimension[] alignDIMs = CADRecEntity.getAlignedDimensions();
        if (alignDIMs != null && alignDIMs.length > 0) {
            for (AlignedDimension alignDIM : alignDIMs) {
                entities.addEntity(new AlignedDimensionEntity(alignDIM));
            }
        }

        // 展示构建结果
        entities.showEntitiesCountStatus();
        String fileName = System.currentTimeMillis() + "";
        //boolean mkdir = new File("C:\\Users\\Z\\Desktop\\CAD\\DXF\\" + fileName).mkdir();
        boolean mkdir = new File(dxfFilePath + fileName).mkdir();
        if (mkdir) {
            //dxfBuilder.toFile("C:\\Users\\Z\\Desktop\\CAD\\DXF\\" + fileName + "\\" + fileName + ".dxf", true, fileName);
            dxfBuilder.toFile(convertToolPath, dxfFilePath, dwgFilePath, dxfFilePath + fileName + "\\" + fileName + ".dxf", true, fileName);
        }

        //File file = new File("C:\\Users\\Z\\Desktop\\CAD\\DWG\\" + fileName + ".dwg");
        File file = new File(dwgFilePath + fileName + ".dwg");
        if (file.exists()) {
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + ".dwg", "UTF-8"));
            response.setContentType("application/octet-stream");
            response.addHeader("Content-Length", String.valueOf(file.length()));
            ServletOutputStream respOutputStream = response.getOutputStream();
            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            InputStream inputStream = Files.newInputStream(file.toPath());
            bis = new BufferedInputStream(inputStream);
            int i = bis.read(buff);
            while (i != -1) {
                respOutputStream.write(buff, 0, buff.length);
                respOutputStream.flush();
                i = bis.read(buff);
            }
            bis.close();
            respOutputStream.close();
        } else {
            throw new RuntimeException("文件不存在");
        }
    }


    /*public static void main(String[] args) throws Exception {
        DXFBuilder dxf = new DXFBuilder();
        Entities entities = dxf.getEntities();

        // 点2
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

        // 直线
        LineEntity lineEntity1 = new LineEntity();
        lineEntity1.addPoint(new Point(0, 0));
        lineEntity1.addPoint(new Point(10, 0));
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
        lineEntity1.addPoint(new Point(0, 0));
        lineEntity1.addPoint(new Point(10, 10));
        entities.addEntity(lineEntity1);

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

        entities.showEntitiesStatus();
        //entities.deleteEntity(ray);
        //entities.showEntitiesStatus();
        dxf.toFile("C:\\Users\\Z\\Desktop\\CAD\\main.java.DXF\\aa.dxf","aa");
    }*/


}
