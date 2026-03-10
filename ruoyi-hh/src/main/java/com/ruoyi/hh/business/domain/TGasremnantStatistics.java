package com.ruoyi.hh.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 残余瓦斯含量统计对象 t_gasremnant_statistics
 * 
 * @author eleven
 * @date 2023-02-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TGasremnantStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 工作面ID */
    @Excel(name = "工作面ID")
    private Integer workfaceId;

    /** 孔号 */
    @Excel(name = "孔号")
    private String boreholeCode;

    /** 取样地点 */
    @Excel(name = "取样地点")
    private String local;

    /** 取样深度（m） */
    @Excel(name = "取样深度", readConverterExp = "m=")
    private Double depth;

    /** 倾角（°） */
    @Excel(name = "倾角", readConverterExp = "°=")
    private Double dip;

    /** 方位角（°） */
    @Excel(name = "方位角", readConverterExp = "°=")
    private Double azimuth;

    /** 残余瓦斯含量（m³/t） */
    @Excel(name = "残余瓦斯含量", readConverterExp = "m=³/t")
    private Double gasContentRemnant;

    /** 残余瓦斯压力（MPa） */
    @Excel(name = "残余瓦斯压力", readConverterExp = "M=Pa")
    private Double gasPressureRemnant;

    /** 记录人 */
    @Excel(name = "记录人")
    private String recorder;


}
