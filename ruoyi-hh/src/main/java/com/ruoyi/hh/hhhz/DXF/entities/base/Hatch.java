package com.ruoyi.hh.hhhz.DXF.entities.base;

import com.ruoyi.hh.hhhz.DXF.entities.PLineEntity;

import java.util.List;

public class Hatch extends BaseEntity {

    private List<BaseEntity> entities;
    private final String ENTITY_SUBCLASS = "100\nAcDbHatch\n";


    public Hatch() {
    }


    public Hatch(List<BaseEntity> entities) {
        if (entities != null && entities.size() >= 1) {
            this.entities = entities;
            setLayer(this.entities.get(0).getLayer());
            setColor(this.entities.get(0).getColor());
        } else {
            throw new RuntimeException("区域填充传入参数有误：参数不能为空");
        }
    }


    public String getHatchSubClass() {
        StringBuilder sb = new StringBuilder();
        sb.append(ENTITY_SUBCLASS);
        sb.append(" 10\n" +
                "0.0\n" +
                " 20\n" +
                "0.0\n" +
                " 30\n" +
                "0.0\n" +
                "210\n" +
                "0.0\n" +
                "220\n" +
                "0.0\n" +
                "230\n" +
                "1.0\n");
        sb.append("2\n" + "SOLID\n");
        sb.append("70\n" + "1\n");
        sb.append("71\n" + "1\n");
        sb.append("91\n" + entities.size() + "\n");
        int sumX = 0;
        int sumY = 0;
        int i = 0;
        for (BaseEntity entity : entities) {
            sb.append("92\n" + "7\n");
            sb.append("72\n" + "0\n");
            sb.append("73\n" + "1\n");
            sb.append("93\n" + ((PLineEntity) entity).getPoints().size() + "\n");
            for (Point point : ((PLineEntity) entity).getPoints()) {
                sb.append("10\n" + point.getX() + "\n");
                sb.append("20\n" + point.getY() + "\n");
                sumX += point.getX();
                sumY += point.getY();
                i++;
            }
            sb.append("97\n" + "1\n");
            sb.append("330\n" + entity.getHandle() + "\n");
        }
        sb.append("75\n" +
                "1\n" +
                "76\n" +
                "1\n" +
                "47\n" +
                "0.02\n" +
                "98\n" +
                "1\n");
        sb.append("10\n" + sumX / i + "\n");
        sb.append("20\n" + sumY / i + "\n");
        sb.append("1001\n" +
                "GradientColor1ACI\n" +
                "1070\n" +
                "5\n" +
                "1001\n" +
                "GradientColor2ACI\n" +
                "1070\n" +
                "2\n" +
                "1001\n" +
                "ACAD\n" +
                "1010\n" +
                "0.0\n" +
                "1020\n" +
                "0.0\n" +
                "1030\n" +
                "0.0\n");
        return sb.toString();
    }

}
/**
 * 0
 * HATCH
 * 5      handle
 * 230
 * 330
 * 1F
 * 100
 * AcDbEntity
 * 8
 * 0
 * 100
 * AcDbHatch
 * 10          标高点const
 * 0.0          x 0
 * 20
 * 0.0          y 0
 * 30
 * 0.0          z 0
 * 210          拉伸方向const optional
 * 0.0          x 0
 * 220
 * 0.0          y 0
 * 230
 * 1.0          z 1
 * 2            填充图案名
 * ANSI31       ANSI31斜线||SOLID涂满
 * 70          实体填充
 * 0            ANSI31斜线选0||SOLID涂满选1
 * 71          关联性标志（0 = 无关联；1 = 关联）
 * 1
 * 91          边界路径（环）数=1
 * 1
 * 92          边界路径类型标志
 * 7
 * 72          “有凸度”标志（0代表无吧
 * 0
 * 73          “关闭”标志
 * 1            1关闭
 * 93          多段线顶点数
 * 4            4个顶点
 * 10          顶点x
 * 5.0
 * 20          顶点y
 * 5.0
 * 10          顶点x
 * 0.0
 * 20          顶点y
 * 5.0
 * 10          ...
 * 0.0
 * 20          ...
 * 0.0
 * 10          ...
 * 5.0
 * 20          ...
 * 0.0
 * 97          源边界对象数
 * 1    1个多段线
 * 330          边界对应的handle
 * 228          多段线handle
 * 75          图案填充样式  0 = 对“奇数奇偶校验”区域进行图案填充（普通样式）  1 = 仅对最外层区域进行图案填充（“外部”样式）  2 = 对整个区域进行图案填充（“忽略”样式）
 * 1       仅对最外层区域进行图案填充
 * 76          填充图案类型
 * 1       1预定义
 * 52          填充图案角度
 * 0.0          0.0
 * 41          填充图案比例或间距
 * 1.0          1
 * 77          填充图案双向标志（仅限图案填充）
 * 0       非双向标志
 * 78          图案定义直线数
 * 1       1条？
 * 53          图案直线角度
 * 45.0         45度斜的
 * 43          图案直线基点x
 * 0.0
 * 44          图案直线基点y
 * 0.0
 * 45          图案直线偏移x
 * -0.0883883476483185
 * 46          图案直线偏移y
 * 0.0883883476483185
 * 79          虚线长度项目数
 * 0
 * 47          指定像素密度大小
 * 0.0217398549301354       暂定0.2
 * 98          种子点数
 * 1    1
 * 10          种子x
 * 2.860909325389428
 * 20          种子y
 * 4.948600271909839
 * 1001
 * GradientColor1ACI
 * 1070
 * 5
 * 1001
 * GradientColor2ACI
 * 1070
 * 2
 * 1001         const扩展数据的注册应用程序名
 * ACAD
 * 1010         const扩展数据中的点X
 * 0.0
 * 1020         const扩展数据中的点Y
 * 0.0
 * 1030         const扩展数据中的点Z
 * 0.0
 */
