package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 岩层类型对象 t_rocktype
 *
 * @author hhhz
 * @date 2023-05-05
 */
@ApiModel("DZ岩层类型")
public class TRocktype extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("岩层类型ID")
    private Integer id;

    /**
     * 岩层类型名称
     */
    @Excel(name = "岩层类型名称")
    @ApiModelProperty("岩层类型名称")
    private String rockType;

    /**
     * 岩层描述
     */
    @Excel(name = "岩层描述")
    @ApiModelProperty("岩层描述")
    private String rockDesc;

    /**
     * 颜色
     */
    @Excel(name = "颜色")
    @ApiModelProperty("岩层材质/颜色")
    private String color;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setRockType(String rockType) {
        this.rockType = rockType;
    }

    public String getRockType() {
        return rockType;
    }

    public void setRockDesc(String rockDesc) {
        this.rockDesc = rockDesc;
    }

    public String getRockDesc() {
        return rockDesc;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("rockType", getRockType())
                .append("rockDesc", getRockDesc())
                .append("color", getColor())
                .toString();
    }
}
