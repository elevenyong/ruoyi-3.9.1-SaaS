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
 * 瓦斯含量/压力台账对象 t_gas_content_pressure
 *
 * @author eleven
 * @date 2022-07-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TGasContentPressure extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /**巷道名称*/
    @Excel(name="所属巷道名称",width = 20)
    private String roadway;
    private Integer roadwayId;
    private Integer boreholeId;
    private String boreholeCode;

    /** 取样点 */
    @Excel(name = "取样点",width = 45)
    private String samplingProt;

    /**倾角*/
    @Excel(name="倾角",width = 5)
    private double inclinationAngle;

    /**夹角*/
    @Excel(name="夹角",width = 5)
    private double angle;

    /**用途*/
    @Excel(name="用途",width = 10)
    private String purposeBorehole;
    /**钻孔用途ID*/
    private Integer purposeBoreholeId;


    /** 取样深度 */
    @Excel(name = "取样深度",width = 10)
    private Double samplingDepth;

    /** 瓦斯含量 */
    @Excel(name = "瓦斯含量",width = 10)
    private Double gasContent;

    /** 瓦斯压力 */
    @Excel(name = "瓦斯压力",width = 10)
    private Double gasPressure;

    /** 测量日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "测量日期", width = 15, dateFormat = "yyyy-MM-dd")
    private Date measureDate;

    /**测量人*/
    @Excel(name="测量人")
    private String measurer;
}
