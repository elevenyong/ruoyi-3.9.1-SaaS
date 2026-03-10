package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 钻机信息对象 t_drillinginfo
 * 
 * @author eleven
 * @date 2023-03-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TDrillinginfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;

    /** 钻机编号 */
    @Excel(name = "钻机编号")
    private String drillingNo;

    /** 钻机名称 */
    @Excel(name = "钻机名称")
    private String drillingName;

    /** 施工单位 */
    @Excel(name = "施工单位")
    private String constructionCompany;

    /** 关联清水计量装置ID */
    @Excel(name = "关联清水计量装置ID")
    private Integer waterDevId;

    /** 关联煤水计量装置ID */
    @Excel(name = "关联煤水计量装置ID")
    private Integer coalWaterDevId;

    /** 关联瓦斯排放装置ID */
    @Excel(name = "关联瓦斯排放装置ID")
    private Integer gasDevId;

    /** 清水计量装置编号 */
    @Excel(name = "清水计量装置编号")
    private String waterDevCode;

    /** 清水设备显示流速（模拟） */
    @Excel(name = "清水设备显示流速", readConverterExp = "模=拟")
    private Double flowRate;

    /** 清水累计量（模拟） */
    @Excel(name = "清水累计量", readConverterExp = "模=拟")
    private Double waterCumulant;

    /** 煤水计量装置编号 */
    @Excel(name = "煤水计量装置编号")
    private String coalWaterDevCode;

    /** 煤水设备混量（模拟） */
    @Excel(name = "煤水设备混量", readConverterExp = "模=拟")
    private Double mixture;

    /** 煤水设备显示的煤占比（模拟） */
    @Excel(name = "煤水设备显示的煤占比", readConverterExp = "模=拟")
    private Double concentration;

    /** 煤水累计量（模拟） */
    @Excel(name = "煤水累计量", readConverterExp = "模=拟")
    private Double coalCumulant;

    /** 瓦斯排放装置编号 */
    @Excel(name = "瓦斯排放装置编号")
    private String gasDevCode;

    /** 瓦斯实时流量（模拟） */
    @Excel(name = "瓦斯实时流量", readConverterExp = "模=拟")
    private Double gasMixture;

    /** 瓦斯实时浓度（模拟） */
    @Excel(name = "瓦斯实时浓度", readConverterExp = "模=拟")
    private Double gasConcentration;

    /** 瓦斯排放累计（模拟） */
    @Excel(name = "瓦斯排放累计", readConverterExp = "模=拟")
    private Double gasCumulant;

    /** 累计时间（分） */
    @Excel(name = "累计时间", readConverterExp = "分=")
    private Double cumulantTime;


}
