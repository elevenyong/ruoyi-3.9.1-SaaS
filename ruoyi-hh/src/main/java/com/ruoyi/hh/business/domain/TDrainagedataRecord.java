package com.ruoyi.hh.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 抽采数据记录对象 t_drainagedata_record
 * 
 * @author eleven
 * @date 2023-07-31
 */
public class TDrainagedataRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 测点安装位置 */
    @Excel(name = "测点安装位置")
    private String measurePointLocal;

    /** 测点编码 */
    @Excel(name = "测点编码")
    private String measurePointCode;

    /** 数据时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "数据时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String dataTime;

    /** 数据类型 */
    @Excel(name = "数据类型")
    private String dataType;

    /**数值*/
    @Excel(name="数值")
    private Float value;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setMeasurePointLocal(String measurePointLocal) 
    {
        this.measurePointLocal = measurePointLocal;
    }

    public String getMeasurePointLocal() 
    {
        return measurePointLocal;
    }
    public void setMeasurePointCode(String measurePointCode) 
    {
        this.measurePointCode = measurePointCode;
    }

    public String getMeasurePointCode() 
    {
        return measurePointCode;
    }
    public void setDataTime(String dataTime)
    {
        this.dataTime = dataTime;
    }

    public String getDataTime()
    {
        return dataTime;
    }
    public void setDataType(String dataType) 
    {
        this.dataType = dataType;
    }

    public String getDataType() 
    {
        return dataType;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("measurePointLocal", getMeasurePointLocal())
            .append("measurePointCode", getMeasurePointCode())
            .append("dataTime", getDataTime())
            .append("dataType", getDataType())
            .toString();
    }
}
