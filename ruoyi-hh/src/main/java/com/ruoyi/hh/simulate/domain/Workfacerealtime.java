package com.ruoyi.hh.simulate.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 模拟抽采实时数据对象 workfacerealtime
 * 
 * @author eleven
 * @date 2022-10-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workfacerealtime extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 工作面ID */
    @Excel(name = "工作面ID")
    private Integer workfaceId;
    /**工作面名称*/
    private String workfaceName;
    /**开始日期*/
    private Date beginDrainageDate;

    /** 抽采单元ID */
    @Excel(name = "抽采单元ID")
    private Integer drainageUnitId;

    /**测点ID*/
    private Integer measurePointId;

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

    /**最远端支管抽采负压*/
    private Double distaliPressure;

    /**累计混合量*/
    private long totalMixTureFlow;

    /**累计纯量*/
    private long totalPureFlow;

//    public void setId(Integer id)
//    {
//        this.id = id;
//    }
//
//    public Integer getId()
//    {
//        return id;
//    }
//    public void setWorkfaceid(Integer workfaceid)
//    {
//        this.workfaceid = workfaceid;
//    }
//
//    public Integer getWorkfaceid()
//    {
//        return workfaceid;
//    }
//    public void setDrainageunitid(Integer drainageunitid)
//    {
//        this.drainageunitid = drainageunitid;
//    }
//
//    public Integer getDrainageunitid()
//    {
//        return drainageunitid;
//    }
//    public void setMixtureflow(Double mixtureflow)
//    {
//        this.mixtureflow = mixtureflow;
//    }
//
//    public Double getMixtureflow()
//    {
//        return mixtureflow;
//    }
//    public void setPureflow(Double pureflow)
//    {
//        this.pureflow = pureflow;
//    }
//
//    public Double getPureflow()
//    {
//        return pureflow;
//    }
//    public void setTemperature(Double temperature)
//    {
//        this.temperature = temperature;
//    }
//
//    public Double getTemperature()
//    {
//        return temperature;
//    }
//    public void setCh4concentration(Double ch4concentration)
//    {
//        this.ch4concentration = ch4concentration;
//    }
//
//    public Double getCh4concentration()
//    {
//        return ch4concentration;
//    }
//    public void setCoconcentration(Double coconcentration)
//    {
//        this.coconcentration = coconcentration;
//    }
//
//    public Double getCoconcentration()
//    {
//        return coconcentration;
//    }
//    public void setNegativepressure(Double negativepressure)
//    {
//        this.negativepressure = negativepressure;
//    }
//
//    public Double getNegativepressure()
//    {
//        return negativepressure;
//    }
//
//    @Override
//    public String toString() {
//        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
//            .append("id", getId())
//            .append("workfaceid", getWorkfaceid())
//            .append("drainageunitid", getDrainageunitid())
//            .append("mixtureflow", getMixtureflow())
//            .append("pureflow", getPureflow())
//            .append("temperature", getTemperature())
//            .append("ch4concentration", getCh4concentration())
//            .append("coconcentration", getCoconcentration())
//            .append("negativepressure", getNegativepressure())
//            .toString();
//    }
}
