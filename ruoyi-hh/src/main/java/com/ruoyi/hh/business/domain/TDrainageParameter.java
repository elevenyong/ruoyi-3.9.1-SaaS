package com.ruoyi.hh.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 钻孔抽采参数测量对象 t_drainage_parameter
 * 
 * @author eleven
 * @date 2022-07-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TDrainageParameter extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private int id;
    /**巷道ID*/
    private Integer roadwayId;
    /**巷道名称*/
    @Excel(name="巷道名称")
    private String roadway;
    /** 组号 */
    @Excel(name = "组号",width = 8)
    private String groupNo;
    private Integer boreholeId;
    @Excel(name = "孔号",width = 5)
    private String boreholeNo;
    /** 钻孔编号 */
//    @Excel(name = "钻孔编号",width = 10)
    private String boreholeCode;
    @Excel(name = "钻孔位置",width = 30)
    private String drillingLocation;
    /** 单孔浓度 */
    @Excel(name = "单孔浓度")
    private Double gasConcentration;
    /** 单孔负压 */
    @Excel(name = "单孔负压")
    private Double negativePressure;
    /**始抽浓度*/
    private Double beginConcentration;
    /**始抽负压*/
    private Double beginPressure;

    /** 预计抽采完成所用的天数 */
//    @Excel(name = "预计抽采完成所用的天数")
    private Double estimatedTimeDrainageDone;

    /** 测量日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "测量日期", width = 15, dateFormat = "yyyy-MM-dd")
    private Date measureDate;

    /** 是否为始抽值 */
    @Excel(name = "是否为始抽值",readConverterExp = "0=否,1=是")
    private Integer beginningcon;

    /**始抽浓度测量日期*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginningconDate;


    /** 测量人 */
    @Excel(name = "测量人")
    private String measurer;

    /** 制表人 */
    @Excel(name = "制表人")
    private String tabulator;

    /**钻孔用途*/
    private String purposeBorehole;
    /**实际倾角*/
    private double azimuthAngle;
    /**实际夹角*/
    private double angle;
    /**实际方位角*/
    private double inclinationAngle;
    /**实际孔深*/
    private double boreholeLength;
    /**见岩情况*/
    private String seeRockCase;
    /**见煤情况*/
    private String seeCoalCase;
    /**导线点*/
    private String navigationPointName;
    /**距导线点距离*/
    private double distanceToNavigationPoint;
    /**封孔方式*/
    private String holesealType;
    /**冲孔煤量*/
    private Double actualCoalAmountFlushed;
}
