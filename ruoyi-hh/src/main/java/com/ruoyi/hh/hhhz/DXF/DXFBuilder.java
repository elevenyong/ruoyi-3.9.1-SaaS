package com.ruoyi.hh.hhhz.DXF;

import com.ruoyi.hh.hhhz.DXF.blocks.Blocks;
import com.ruoyi.hh.hhhz.DXF.classes.Classes;
import com.ruoyi.hh.hhhz.DXF.entities.Entities;
import com.ruoyi.hh.hhhz.DXF.header.Header;
import com.ruoyi.hh.hhhz.DXF.objects.DXFObjects;
import com.ruoyi.hh.hhhz.DXF.tables.Tables;


import java.io.*;

public class DXFBuilder {
    private StringBuilder DXFBuilder;
    private Header header;
    private Classes classes;
    private Tables tables;
    private Blocks blocks;
    private Entities entities;
    private DXFObjects objects;
    private final String EOF = "  0\n" + "EOF\n";

    public DXFBuilder() {
        header = new Header();
        classes = new Classes();
        tables = new Tables();
        blocks = new Blocks();
        entities = new Entities();
        objects = new DXFObjects();
    }

    public Entities getEntities() {
        return entities;
    }

    public void toFile(String filePath, String fileName) {
        DXFBuilder = new StringBuilder();
        DXFBuilder.append(header.getHeader());
        DXFBuilder.append(classes.getClasses());
        DXFBuilder.append(tables.getTables());
        DXFBuilder.append(blocks.getBlocks());
        DXFBuilder.append(entities.getEntitiesAsString());
        DXFBuilder.append(objects.getObjects());
        DXFBuilder.append(EOF);

        try (BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath, false), "gbk"));) {
            fileWriter.write(DXFBuilder.toString());
            fileWriter.flush();
            System.out.println("创建DXF文件:" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param toolPath toolPath
     * @param dxfPath dxfPath
     * @param dwgPath dwgPath
     * @param filePath filePath
     * @param convertToDWG convertToDWG
     * @param fileName fileName
     */
    public void toFile(String toolPath,String dxfPath,String dwgPath,String filePath, boolean convertToDWG, String fileName) {
        try {
            toFile(filePath, fileName);
            if (convertToDWG) {
                String[] cmdArgs = new String[]{toolPath+"ODAFileConverter.exe",
                        dxfPath + fileName,
                        dwgPath,
                        //"ACAD2000",
                        "ACAD12",
                        "DWG",
                        "0",
                        "0"
                };
                Runtime.getRuntime().exec(cmdArgs, null, new File(toolPath));
                System.out.println("转换DWG文件:" + dwgPath + fileName + ".dwg");
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*public void toFile(String filePath, boolean convertToDWG, String fileName) {
        try {
            toFile(filePath, fileName);
            if (convertToDWG) {
                String[] cmdArgs = new String[]{"C:\\Program Files\\ODA\\ODAFileConverter\\ODAFileConverter.exe",
                        "C:\\Users\\Z\\Desktop\\CAD\\DXF\\" + fileName,
                        "C:\\Users\\Z\\Desktop\\CAD\\DWG",
                        //"ACAD2000",
                        "ACAD12",
                        "DWG",
                        "0",
                        "0"
                };
                Runtime.getRuntime().exec(cmdArgs, null, new File("C:\\Program Files\\ODA\\ODAFileConverter"));
                System.out.println("转换DWG文件:" + "C:\\Users\\Z\\Desktop\\CAD\\DWG\\" + fileName + ".dwg");
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
