package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 抽采单元信息对象 t_evaluation_unit
 *
 * @author eleven
 * @date 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TDrainageUnit extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 抽采单元名称 */
    @Excel(name = "抽采单元名称")
    private String unitName;

    /** 所属评价单元ID */
    @Excel(name = "所属评价单元ID")
    private Integer evaluationUnitId;
    /**评价单元名称*/
    private String evaluateUnitName;

    /**抽采单元类型id*/
    @Excel(name="抽采单元类型ID")
    private Integer drainageUnitTypeId;

    /**抽采单元类型名称*/
    @Excel(name="类型名称")
    private String drainageUnitTypeName;

    /**所属工作面ID*/
    private Integer roadwayId;
    /**所属工作面名称*/
    private String roadwayName;

    /** 工作面ID */
    @Excel(name = "工作面ID")
    private Integer workfaceId;
    private String workfaceName;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 走向长度(m) */
    @Excel(name = "走向长度(m)")
    private Double length;

    /** 宽度(m) */
    @Excel(name = "宽度(m)")
    private Double wide;

    /** 计量单元储煤量 */
    @Excel(name = "计量单元储煤量(wt)")
    private Double coalReserves;

    /** 计量单元原始瓦斯含量 */
    @Excel(name = "计量单元原始瓦斯含量")
    private Double gasContentOriginal;

    /** 计量单元瓦斯储量 */
    @Excel(name = "计量单元瓦斯储量(wm³)")
    private Long gasTotalReserves;
    /**剩余瓦斯量*/
    private Long gasTotalReservesResidue;

    /**抽采混合量*/
    private Double mixTureFlow;
    /**抽采纯量*/
    private Double pureFlow;
    /**累计抽采混量*/
    private Long totalMixTureFlow;
    /**累计抽采纯量*/
    private Long totalPureFlow;

    /** 钻孔施工长度 */
    @Excel(name = "钻孔施工长度")
    private Double boreholeLengthTotal;

    /** 抽采钻孔管径 */
    @Excel(name = "抽采钻孔管径")
    private Double boreDiameter;

    /** 抽采单元汇总管径 */
    @Excel(name = "抽采单元汇总管径")
    private Double boreDiameterCollect;

    /** 坐标1X */
    @Excel(name = "坐标1X")
    private Double X1;

    /** 坐标1Y */
    @Excel(name = "坐标1Y")
    private Double Y1;

    /** 坐标1Z */
    @Excel(name = "坐标1Z")
    private Double Z1;

    /** 坐标2X */
    @Excel(name = "坐标2X")
    private Double X2;

    /** 坐标2Y */
    @Excel(name = "坐标2Y")
    private Double Y2;

    /** 坐标2Z */
    @Excel(name = "坐标2Z")
    private Double Z2;

    /** 坐标3X */
    @Excel(name = "坐标3X")
    private Double X3;

    /** 坐标3Y */
    @Excel(name = "坐标3Y")
    private Double Y3;

    /** 坐标3Z */
    @Excel(name = "坐标3Z")
    private Double Z3;

    /** 坐标4X */
    @Excel(name = "坐标4X")
    private Double X4;

    /** 坐标4Y */
    @Excel(name = "坐标4Y")
    private Double Y4;

    /** 坐标4Z */
    @Excel(name = "坐标4Z")
    private Double Z4;

    /**
     * 对应测点列表
     */
    private List<TStationInfo> tStationInfoList;



}
