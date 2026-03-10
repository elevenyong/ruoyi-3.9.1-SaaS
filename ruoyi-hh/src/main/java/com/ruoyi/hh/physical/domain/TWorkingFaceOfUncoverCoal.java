package com.ruoyi.hh.physical.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 揭煤工作面对象
 *
 * @author hhhz
 * @date 2023-05-04
 */
public class TWorkingFaceOfUncoverCoal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 揭煤工作面名称 */
    @Excel(name = "揭煤工作面名称")
    private String workfaceName;

    /** 走向长度 */
    @Excel(name = "走向长度")
    private Double towardsLength;

    /** 切眼长度 */
    @Excel(name = "切眼长度")
    private Double cutLength;

    /** 高度 */
    @Excel(name = "高度")
    private Double height;

    /**
     * 关联底抽巷
     */
    @Excel(name = "关联底抽巷")
    private String bottomExtractionRoadway;

    /**
     * 关联进风巷
     */
    @Excel(name = "关联进风巷")
    private String airIntakeRoadway;

    /**
     * 进风巷实体
     */
    private TRoadway airIntakeRoadwayData;

    /**
     * 关联回风巷
     */
    @Excel(name = "关联回风巷")
    private String airReturnRoadway;

    /**
     * 回风巷实体
     */
    private TRoadway airReturnRoadwayData;

    /**
     * 关联底抽巷实体
     */
    private TRoadway dcAirIntakeRoadwayData;
    private TRoadway dcAirReturnRoadwayData;
    private TRoadway dcCutEyeRoadwayData;

    /**
     * 关联切眼巷
     */
    @Excel(name = "关联切眼巷")
    private String cutEyeRoadway;

    /**
     * 切眼巷实体
     */
    private TRoadway cutEyeRoadwayData;

    /**
     * 关联采区大巷
     */
    @Excel(name = "关联采区大巷")
    private String miningAreaRoadway;

    /**
     * 采区大巷实体
     */
    private TRoadway miningAreaRoadwayData;

    public TRoadway getAirIntakeRoadwayData() {
        return airIntakeRoadwayData;
    }

    public void setAirIntakeRoadwayData(TRoadway airIntakeRoadwayData) {
        this.airIntakeRoadwayData = airIntakeRoadwayData;
    }

    public TRoadway getAirReturnRoadwayData() {
        return airReturnRoadwayData;
    }

    public void setAirReturnRoadwayData(TRoadway airReturnRoadwayData) {
        this.airReturnRoadwayData = airReturnRoadwayData;
    }

    public TRoadway getDcAirIntakeRoadwayData() {
        return dcAirIntakeRoadwayData;
    }

    public void setDcAirIntakeRoadwayData(TRoadway dcAirIntakeRoadwayData) {
        this.dcAirIntakeRoadwayData = dcAirIntakeRoadwayData;
    }

    public TRoadway getDcAirReturnRoadwayData() {
        return dcAirReturnRoadwayData;
    }

    public void setDcAirReturnRoadwayData(TRoadway dcAirReturnRoadwayData) {
        this.dcAirReturnRoadwayData = dcAirReturnRoadwayData;
    }

    public TRoadway getDcCutEyeRoadwayData() {
        return dcCutEyeRoadwayData;
    }

    public void setDcCutEyeRoadwayData(TRoadway dcCutEyeRoadwayData) {
        this.dcCutEyeRoadwayData = dcCutEyeRoadwayData;
    }

    public TRoadway getCutEyeRoadwayData() {
        return cutEyeRoadwayData;
    }

    public void setCutEyeRoadwayData(TRoadway cutEyeRoadwayData) {
        this.cutEyeRoadwayData = cutEyeRoadwayData;
    }

    public TRoadway getMiningAreaRoadwayData() {
        return miningAreaRoadwayData;
    }

    public  void setMiningAreaRoadwayData(TRoadway miningAreaRoadwayData) {
        this.miningAreaRoadwayData = miningAreaRoadwayData;
    }

    public String getWorkfaceName() {
        return workfaceName;
    }

    public void setWorkfaceName(String workfaceName) {
        this.workfaceName = workfaceName;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setTowardsLength(Double towardsLength)
    {
        this.towardsLength = towardsLength;
    }

    public Double getTowardsLength()
    {
        return towardsLength;
    }
    public void setCutLength(Double cutLength)
    {
        this.cutLength = cutLength;
    }

    public Double getCutLength()
    {
        return cutLength;
    }
    public void setHeight(Double height)
    {
        this.height = height;
    }

    public Double getHeight()
    {
        return height;
    }
    public void setBottomExtractionRoadway(String bottomExtractionRoadway)
    {
        this.bottomExtractionRoadway = bottomExtractionRoadway;
    }

    public String getBottomExtractionRoadway()
    {
        return bottomExtractionRoadway;
    }
    public void setAirIntakeRoadway(String airIntakeRoadway)
    {
        this.airIntakeRoadway = airIntakeRoadway;
    }

    public String getAirIntakeRoadway()
    {
        return airIntakeRoadway;
    }
    public void setAirReturnRoadway(String airReturnRoadway)
    {
        this.airReturnRoadway = airReturnRoadway;
    }

    public String getAirReturnRoadway()
    {
        return airReturnRoadway;
    }
    public void setCutEyeRoadway(String cutEyeRoadway)
    {
        this.cutEyeRoadway = cutEyeRoadway;
    }

    public String getCutEyeRoadway()
    {
        return cutEyeRoadway;
    }
    public void setMiningAreaRoadway(String miningAreaRoadway)
    {
        this.miningAreaRoadway = miningAreaRoadway;
    }

    public String getMiningAreaRoadway()
    {
        return miningAreaRoadway;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("towardsLength", getTowardsLength())
            .append("cutLength", getCutLength())
            .append("height", getHeight())
            .append("bottomExtractionRoadway", getBottomExtractionRoadway())
            .append("airIntakeRoadway", getAirIntakeRoadway())
            .append("airReturnRoadway", getAirReturnRoadway())
            .append("cutEyeRoadway", getCutEyeRoadway())
            .append("miningAreaRoadway", getMiningAreaRoadway())
            .toString();
    }
}
