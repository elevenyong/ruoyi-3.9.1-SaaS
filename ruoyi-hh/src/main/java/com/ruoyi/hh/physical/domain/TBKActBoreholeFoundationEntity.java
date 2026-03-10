package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.physical.domain
 * @className: TBKActBoreholeFoundationEntity
 * @author: Eleven
 * @description: 钻孔基础信息实体类
 * @date: 2023/7/18 14:07
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TBKActBoreholeFoundationEntity {
    private Integer id;
    @Excel(name="所属巷道名称",width = 15)
    private String roadway;
    /** 钻孔位置 */
    @Excel(name = "钻孔位置",width = 30)
    private String drillingLocation;
    /** 钻孔编号 */
    @Excel(name = "钻孔编号",width = 10)
    private String boreholeCode;
    /** 夹角 */
    @Excel(name = "设计夹角(°)",width = 5)
    private Double desAngle;
    /** 夹角 */
    @Excel(name = "实际夹角(°)",width = 5)
    private Double actAngle;
    /** 倾角 */
    @Excel(name = "设计倾角(°)",width = 5)
    private Double desInclinationAngle;
    /** 倾角 */
    @Excel(name = "实际倾角(°)",width = 5)
    private Double actInclinationAngle;
    /** 设计孔深 */
    @Excel(name = "设计孔深(m)",width = 15)
    private Double desBoreholeLength;
    /** 实际孔深 */
    @Excel(name = "实际孔深(m)",width = 15)
    private Double actBoreholeLength;
    /** 见岩见煤情况 */
    @Excel(name = "见岩见煤情况",width = 15)
    private String seeRockCoalCase;
    /** 封孔材料 */
    @Excel(name = "封孔材料",width = 10)
    private String sealingMaterial;
    @Excel(name="冲孔长度",width=10)
    private Double flushingLength;
    /** 实际冲出煤量 */
    @Excel(name = "实际冲出煤量",width = 15)
    private Double actualCoalAmountFlushed;

}
