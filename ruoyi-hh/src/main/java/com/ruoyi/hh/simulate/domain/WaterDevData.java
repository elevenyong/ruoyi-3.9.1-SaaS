package com.ruoyi.hh.simulate.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 水煤分离之清水计量装置对象 water_dev_data
 * 
 * @author eleven
 * @date 2023-03-21
 */
public class WaterDevData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /**  装置编号 */
    @Excel(name = " 装置编号")
    private String code;

    /** 流速（m³/min） */
    @Excel(name = "流速", readConverterExp = "m=³/min")
    private Double flowRate;

    /** 累计流量 */
    @Excel(name = "累计流量")
    private Double cumulant;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setFlowRate(Double flowRate) 
    {
        this.flowRate = flowRate;
    }

    public Double getFlowRate() 
    {
        return flowRate;
    }
    public void setCumulant(Double cumulant) 
    {
        this.cumulant = cumulant;
    }

    public Double getCumulant() 
    {
        return cumulant;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("flowRate", getFlowRate())
            .append("cumulant", getCumulant())
            .toString();
    }
}
