package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 地层信息对象 t_rockinfo
 *
 * @author hhhz
 * @date 2023-05-05
 */
@ApiModel("DZ地层信息")
public class TRockinfo extends BaseEntity implements Comparable<TRockinfo> {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty("地层ID")
    private Integer id;

    /**
     * 对应的工作面ID
     */
    @Excel(name = "对应的工作面ID")
    @ApiModelProperty("对应的工作面ID")
    private Integer workfaceId;

    /**
     * 岩层类型ID
     */
    @Excel(name = "岩层类型ID")
    @ApiModelProperty("岩层类型ID")
    private Integer rockType;

    /**
     * 厚度
     */
    @Excel(name = "预设厚度")
    @ApiModelProperty("地层预设厚度")
    private Double thickness;

    /**
     * 岩层序号
     */
    @Excel(name = "序号")
    @ApiModelProperty("地层排序：基准煤层为0，顶板向上+1，底板向下-1")
    private Integer serialNumber;

    /**
     * 地层简要描述
     */
    @Excel(name = "地层简述")
    @ApiModelProperty("地层简要描述")
    private String rockDesc;
    /**
     * 此地层岩层类型
     */
    @ApiModelProperty("只读：岩层类型实体")
    private TRocktype rockTypeData;

    public TRocktype getRockTypeData() {
        return rockTypeData;
    }

    public void setRockTypeData(TRocktype rockTypeData) {
        this.rockTypeData = rockTypeData;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getWorkfaceId() {
        return workfaceId;
    }

    public void setWorkfaceId(Integer workfaceId) {
        this.workfaceId = workfaceId;
    }

    public void setRockType(Integer rockType) {
        this.rockType = rockType;
    }

    public Integer getRockType() {
        return rockType;
    }

    public void setThickness(Double thickness) {
        this.thickness = thickness;
    }

    public Double getThickness() {
        return thickness;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public String getRockDesc() {
        return rockDesc;
    }

    public void setRockDesc(String rockDesc) {
        this.rockDesc = rockDesc;
    }

    @Override
    public String toString() {
        return "TRockinfo{" +
                "id=" + id +
                ", workfaceId=" + workfaceId +
                ", rockType=" + rockType +
                ", thickness=" + thickness +
                ", serialNumber=" + serialNumber +
                ", rockTypeData=" + rockTypeData +
                '}';
    }

    @Override
    public int compareTo(TRockinfo o) {
        return o.serialNumber - serialNumber;
    }
}
