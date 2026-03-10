package com.ruoyi.hh.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
 *瓦斯抽采钻孔施工台账
 */
public class TGasDrillingStandingBook {
    private static final long serialVersionUID = 1L;

    /**巷道名称*/
    @Excel(name="巷道名称")
    private String roadway;
    /**组号*/
    @Excel(name="组号")
    private String groupNo;
    /**孔号*/
    @Excel(name="孔号")
    private Integer boreholeNo;
    /** 钻孔编号 */
    @Excel(name = "钻孔编号",width = 10)
    private String boreholeCode;
    /**拔钻日期*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拔钻日期", width = 15, dateFormat = "yyyy-MM-dd")
    private Date pullDrillDate;
    /**设计夹角*/
    @Excel(name="设计夹角")
    private Double desAngle;
    /**实际夹角*/
    @Excel(name="实际夹角")
    private Double actAngle;
    /**设计倾角*/
    @Excel(name="设计倾角")
    private Double desInclinationAngle;
    /**实际倾角*/
    @Excel(name="实际倾角")
    private Double actInclinationAngle;
    /**倾角误差*/
    @Excel(name="倾角误差")
    private Double errInclinationAngle;
    /**设计见煤深度*/
    @Excel(name="设计见煤深度")
    private Double desCoalDepth;
    /**实际见煤深度*/
    @Excel(name="实际见煤深度")
    private Double actCoalDepth;
    /**设计孔深*/
    @Excel(name="设计孔深")
    private double desBoreholeLength;
    /**实际孔深*/
    @Excel(name="实际孔深")
    private double actBoreholeLength;
    /**见煤情况*/
    @Excel(name="见岩见煤情况")
    private String seeRockCoalCase;
    /**见岩长度*/
    @Excel(name="见岩长度")
    private double seeRockLength;
    /**临近孔号*/
    @Excel(name="临近孔号")
    private String adjacentBoreholeNo;
    /**临近孔距离*/
    @Excel(name="临近孔距离")
    private Double distanceAdjacentBorehole;
    /**开孔高度*/
    @Excel(name="开孔高度")
    private Double holeAltitude;
    /**导线点*/
    @Excel(name="导线点")
    private String navigationPointName;
    /**距导线点距离*/
    @Excel(name="距导线点距离")
    private Double distanceToNavigationPoint;
    /**距巷道中线距离*/
    @Excel(name="距巷道中线距离")
    private Double distanceToCentrallineTunnel;
    /**施工人员*/
    @Excel(name="施工人员")
    private String personConstruction;
    /**验收人员*/
    @Excel(name="验收人员")
    private String personChecking;
    @Excel(name="始抽浓度")
    private Double gasConcentration;
    @Excel(name="始抽负压")
    private Double negativePressure;
    /**封孔方式*/
    @Excel(name="封孔材料（封孔方式）")
    private String sealingMaterial;
    /**冲孔长度*/
    @Excel(name="冲孔长度")
    private Double lengthFlushing;
    /**冲孔煤量*/
    @Excel(name="冲孔煤量")
    private Double actualCoalAmountFlushed;
//    /**冲孔煤量完成比例*/
//    @Excel(name="冲孔煤量完成比例")
//    private Double completionratioflushedcoal;


}
