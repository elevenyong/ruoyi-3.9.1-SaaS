package com.ruoyi.hh.simulate.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 水煤分离之瓦斯计量装置信息对象 gas_dev_data
 * 
 * @author eleven
 * @date 2023-03-21
 */
public class GasDevData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增 */
    private Integer id;

    /** 编号 */
    @Excel(name = "编号")
    private String code;

    /** 速率 */
    @Excel(name = "速率")
    private Double rate;

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
    public void setRate(Double rate) 
    {
        this.rate = rate;
    }

    public Double getRate() 
    {
        return rate;
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
            .append("rate", getRate())
            .append("cumulant", getCumulant())
            .toString();
    }
}
