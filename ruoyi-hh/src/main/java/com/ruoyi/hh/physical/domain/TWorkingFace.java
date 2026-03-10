package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.simulate.domain.DrainageUnitTotal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 工作面信息对象 t_working_face
 *
 * @author eleven
 * @date 2022-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TWorkingFace extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 工作面名 */
    @Excel(name = "工作面名")
    private String workfaceName;

    /** 工作面编号 */
    @Excel(name = "工作面编号")
    private String workfaceSerial;

    /** 所属采区ID */
    @Excel(name = "所属采区ID")
    private Integer miningAreaId;

    /**采区编号*/
    private String miningArea;

    /** 所属煤层ID */
    private Integer coalId;

    /**所属煤层*/
    private String coalSerial;

    /** 工作面煤层储量 */
    @Excel(name = "工作面煤层储量")
    private Double coalReserves;

    /** 工作面走向长度 */
    @Excel(name = "工作面走向长度")
    private Double towardsLength;

    /** 工作面切眼长度 */
    @Excel(name = "工作面切眼长度")
    private Double cutLength;

    /** 原始瓦斯含量 */
    @Excel(name = "原始瓦斯含量")
    private Double gasContentOriginal;

    /** 工作面瓦斯储量 */
    @Excel(name = "工作面瓦斯储量")
    private Double gasTotalreserves;

    /**工作面累计抽采量*/
    @Excel(name="累计抽采量")
    private Double totalDrainageQuantity;

    /**已抽时间*/
    @Excel(name="已抽时间")
    private Integer gooverDays;

    /** 预抽瓦斯达标量 */
    @Excel(name = "预抽瓦斯达标量")
    private Double gasAmountReachStandard;

    /** 达标残存瓦斯量(m³/t) */
    @Excel(name = "达标残存瓦斯量(m³/t)")
    private Double reachStandardGasRemaining;

    /** 残存瓦斯含量(m³/t) */
    @Excel(name = "残存瓦斯含量(m³/t)")
    private Double gasRemaining;

    /** 抽采半径 */
    @Excel(name = "抽采半径")
    private Double extractionRadius;

    /** 开始抽采日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始抽采日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beginDrainageDate;

    /**抽采单元个数*/
    @Excel(name="抽采单元个数")
    private Integer countDrainageUnit;

    /**累计抽采混量*/
    private double accumulatedMix;

    /**累计抽采纯量*/
    private double accumulatedPure;

    /**抽采单元累计流量列表*/
    private List<DrainageUnitTotal> drainageUnitTotalList;


}
