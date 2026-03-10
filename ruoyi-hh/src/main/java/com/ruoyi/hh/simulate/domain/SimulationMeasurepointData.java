package com.ruoyi.hh.simulate.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.util.data.ErrEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;

import java.util.Date;
import java.util.List;

/**
 * 模拟测点的实时数据对象 simulation_measurepoint_date
 * 
 * @author eleven
 * @date 2022-11-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimulationMeasurepointData extends BaseEntity
{
    /** $column.columnComment */
    private Integer id;
    /** 测点信息ID */
    @Excel(name = "测点信息ID")
    private Integer measurePointId;
    /**测点名称*/
    private String measurePointName;
    /**抽采单元名称*/
    private String unitName;
    /** 所属抽采单元ID */
    @Excel(name = "所属抽采单元ID")
    private Integer drainageUnitId;

    /**所属工作面名称*/
    private String workfaceName;
    /** 所属工作面ID */
    @Excel(name = "所属工作面ID")
    private Integer workfaceId;

    /** 混合流量 */
    @Excel(name = "混合流量")
    private Double mixTureFlow;

    /** 纯流量 */
    @Excel(name = "纯流量")
    private Double pureFlow;

    /** 温度 */
    @Excel(name = "温度")
    private Double temperature;

    /** 甲烷浓度 */
    @Excel(name = "甲烷浓度")
    private Double ch4Concentration;

    /** 一氧化碳浓度 */
    @Excel(name = "一氧化碳浓度")
    private Double coConcentration;

    /** 抽采负压 */
    @Excel(name = "抽采负压")
    private Double negativePressure;

    /** 二氧化碳浓度 */
    @Excel(name = "二氧化碳浓度")
    private Double co2Concentration;


    /** 累计抽采混合流量 */
    @Excel(name = "累计抽采混合流量")
    private Long totalMixTureFlow;

    /** 累计抽采纯量 */
    @Excel(name = "累计抽采纯量")
    private Long totalPureFlow;

    /** 乙烯浓度 */
    @Excel(name = "乙烯浓度")
    private Double ethene;

    /** 乙炔浓度 */
    @Excel(name = "乙炔浓度")
    private Double ethyne;

    /** 氧气浓度 */
    @Excel(name = "氧气浓度")
    private Double oConcentration;

    /**时间*/
    private Date date;

    /**数据错误（超限字段合集）*/
    private List<String> errfield;

    /**数据异常的字段合集*/
    private List<ErrEntity> exceptionfield;

}
