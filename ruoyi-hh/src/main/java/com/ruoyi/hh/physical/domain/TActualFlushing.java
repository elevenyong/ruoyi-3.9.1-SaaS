package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 实际冲孔信息对象 t_actual_flushing
 *
 * @author eleven
 * @date 2022-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TActualFlushing extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    @Excel(name="巷道名称")
    private String roadway;

    /** 孔号对应ID */
//    @Excel(name = "孔号对应ID")
    private Integer boreholeId;
    /**组号*/
    @Excel(name="组号")
    private String groupNo;
    /**孔号*/
    @Excel(name="孔号")
    private Integer boreholeNo;
    /**孔径*/
    @Excel(name="孔径（mm）")
    private Double boreDiameter;
    /**孔深*/
    @Excel(name="孔深（mm）")
    private Double boreholeLength;
    /**夹角*/
    private double azimuthAngle;
    /**倾角*/
    private double inclinationAngle;

    /** 班次ID */
//    @Excel(name = "班次ID")
    private Integer shiftId;

    /**班次*/
    @Excel(name="班次")
    private String shiftinfo;

    /** 冲孔日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "冲孔日期", width = 15, dateFormat = "yyyy-MM-dd")
    private Date dateFlushing;

    /** 开始冲孔时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始冲孔时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date timeBeginFlushing;

    /** 冲孔结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "冲孔结束时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date timeEndFlushing;

    /** 累计冲孔时间 */
    @Excel(name = "累计冲孔时间")
    private Double totalTimeFlushing;

    /** 冲孔压力 */
    @Excel(name = "冲孔压力")
    private Double waterPressureDuringFlushing;

    /** 冲孔前孔口瓦斯浓度 */
    @Excel(name = "冲孔前孔口瓦斯浓度")
    private Double gasCONCBeforeFlushing;

    /** 冲孔后孔口瓦斯浓度 */
    @Excel(name = "冲孔后孔口瓦斯浓度")
    private Double gasCONCAfterFlushing;

    /** 冲孔段1 */
    @Excel(name = "冲孔段1")
    private Double punchingParagraph1;

    /** 冲孔段2 */
    @Excel(name = "冲孔段2")
    private Double punchingParagraph2;

    /**冲孔段*/
    private String punchingParagraph;
    /**冲孔段长度*/
    private double length;
    /**见煤情况*/
    @Excel(name="见煤情况")
    private String seeCoalCase;
    /** 见岩情况 */
    @Excel(name = "见岩情况")
    private String seeRockCase;

    /** 实际冲孔煤量 */
    @Excel(name = "实际冲孔煤量")
    private Double actualCoalAmountFlushed;

    /**煤体密度*/
    @Excel(name="煤体密度")
    private Double coalDensity;

    /** 冲煤完成比例 */
    @Excel(name = "冲煤完成比例")
    private Double completionRatioFlushedCoal;

    /** 施工人员 */
    @Excel(name = "施工人员")
    private String personConstruction;

    @Excel(name="冲孔负责人")
    private String chargehand;

    /** 验收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "验收时间", width = 20, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date receptionTime;
    /**冲孔期间瓦斯变化情况*/
    @Excel(name="冲孔期间瓦斯变化情况")
    private String gasChangeDuring;

    /** 冲孔过程中现场异常标注 */
    @Excel(name = "冲孔过程中现场异常标注")
    private String exceptionPhenomena;

    /** 验收人员 */
    @Excel(name = "验收人员")
    private String personChecking;

    /** 跟班队员 */
    @Excel(name = "跟班队员")
    private String otherPerson;

    /**原冲孔验收单*/
    private String file;

}
