package com.ruoyi.hh.simulate.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 水煤分离之煤水计量装置对象 coal_water_dev_data
 * 
 * @author elevn
 * @date 2023-03-21
 */
public class CoalWaterDevData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增 */
    private Integer id;

    /** 煤水计量装置编号 */
    @Excel(name = "煤水计量装置编号")
    private String code;

    /** 混量 */
    @Excel(name = "混量")
    private Double mixture;

    /** 煤水比例 */
    @Excel(name = "煤水比例")
    private Double concentration;

    /** 累计量 */
    @Excel(name = "累计量")
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
    public void setMixture(Double mixture) 
    {
        this.mixture = mixture;
    }

    public Double getMixture() 
    {
        return mixture;
    }
    public void setConcentration(Double concentration) 
    {
        this.concentration = concentration;
    }

    public Double getConcentration() 
    {
        return concentration;
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
            .append("mixture", getMixture())
            .append("concentration", getConcentration())
            .append("cumulant", getCumulant())
            .toString();
    }
}
