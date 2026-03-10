package com.ruoyi.hh.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 抽采单元抽采数据统计中间对象 t_drainagedata_sum
 * 
 * @author eleven
 * @date 2023-08-07
 */
public class TDrainagedataSum extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 抽采单元名称 */
    @Excel(name = "抽采单元名称")
    private String drainageUnit;

    /** 抽采单元名称ID */
    @Excel(name = "抽采单元名称ID")
    private Integer drainageUnitId;

    /** 当月的累计纯量 */
    @Excel(name = "当月的累计纯量")
    private Float gasScalarMonth;
    /** 累计纯量 */
    @Excel(name = "累计纯量")
    private Float gasScalar;

    /** 瓦斯累计抽放量 */
    @Excel(name = "瓦斯累计抽放量")
    private Float drainageQuantity;

    /** 残余瓦斯含量（m³/t） */
    @Excel(name = "残余瓦斯含量", readConverterExp = "m=³/t")
    private Float gasContentRemnant;

    /** 残余瓦斯储量（t） */
    @Excel(name = "残余瓦斯储量", readConverterExp = "t=")
    private Long gasReservesRemnant;

    /** 抽采完成比例 */
    @Excel(name = "抽采完成比例")
    private Double gasCompleteRatio;

    /** 数据日期 */
    @Excel(name = "数据日期")
    private String dataDate;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setDrainageUnit(String drainageUnit) 
    {
        this.drainageUnit = drainageUnit;
    }

    public String getDrainageUnit() 
    {
        return drainageUnit;
    }

    public Integer getDrainageUnitId() {
        return drainageUnitId;
    }

    public void setDrainageUnitId(Integer drainageUnitId) {
        this.drainageUnitId = drainageUnitId;
    }

    public Float getGasScalarMonth() {
        return gasScalarMonth;
    }

    public void setGasScalarMonth(Float gasScalarMonth) {
        this.gasScalarMonth = gasScalarMonth;
    }

    public void setGasScalar(Float gasScalar)
    {
        this.gasScalar = gasScalar;
    }

    public Float getGasScalar()
    {
        return gasScalar;
    }
    public void setDrainageQuantity(Float drainageQuantity)
    {
        this.drainageQuantity = drainageQuantity;
    }

    public Float getDrainageQuantity()
    {
        return drainageQuantity;
    }
    public void setGasContentRemnant(Float gasContentRemnant)
    {
        this.gasContentRemnant = gasContentRemnant;
    }

    public Float getGasContentRemnant()
    {
        return gasContentRemnant;
    }
    public void setGasReservesRemnant(Long gasReservesRemnant)
    {
        this.gasReservesRemnant = gasReservesRemnant;
    }

    public Long getGasReservesRemnant()
    {
        return gasReservesRemnant;
    }
    public void setGasCompleteRatio(Double gasCompleteRatio) 
    {
        this.gasCompleteRatio = gasCompleteRatio;
    }

    public Double getGasCompleteRatio() 
    {
        return gasCompleteRatio;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate;
    }

    @Override
    public String toString() {
        return "TDrainagedataSum{" +
                "id=" + id +
                ", drainageUnit='" + drainageUnit + '\'' +
                ", drainageUnitId=" + drainageUnitId +
                ", gasScalarMonth=" + gasScalarMonth +
                ", gasScalar=" + gasScalar +
                ", drainageQuantity=" + drainageQuantity +
                ", gasContentRemnant=" + gasContentRemnant +
                ", gasReservesRemnant=" + gasReservesRemnant +
                ", gasCompleteRatio=" + gasCompleteRatio +
                ", dataDate=" + dataDate +
                '}';
    }
}
