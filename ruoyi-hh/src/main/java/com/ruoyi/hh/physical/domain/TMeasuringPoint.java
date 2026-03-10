package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 测点信息之对象 t_measuring_point
 *
 * @author eleven
 * @date 2022-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TMeasuringPoint extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 测点名称 */
    @Excel(name = "测点名称")
    private String measuringPointName;

    /** 上级测点ID */
    @Excel(name = "上级测点ID")
    private Integer parentId;

    /** 对应的测点统计级别ID */
    @Excel(name = "对应的测点统计级别ID")
    private Integer measuringPointLevelId;

    /** 采空区ID */
    @Excel(name = "采空区ID")
    private Integer goafInfoId;

    /** 揭煤抽采信息ID */
    @Excel(name = "揭煤抽采信息ID")
    private Integer coalUncoverId;

    /** 所属巷道ID */
    @Excel(name = "所属巷道ID")
    private Integer roadwayId;

    /**抽采混合量*/
    private double mixTureFlow;
    /**抽采纯量*/
    private double pureFlow;
    /**累计抽采混量*/
    private long totalMixTureFlow;
    /**累计抽采纯量*/
    private long totalPureFlow;

    /** 关联抽采单元ID */
    @Excel(name = "关联抽采单元ID")
    private Integer drainageUnitId;

    /** 是否为工作面管路（总）测点 */
    @Excel(name = "是否为工作面管路", readConverterExp = "总=")
    private Boolean channel;

    /** 是否为远端测点（最远端） */
    @Excel(name = "是否为远端测点", readConverterExp = "最=远端")
    private Boolean distalis;

}
