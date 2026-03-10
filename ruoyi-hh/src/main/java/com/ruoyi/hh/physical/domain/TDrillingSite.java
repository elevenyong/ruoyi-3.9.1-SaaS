package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("DZ钻场信息")
public class TDrillingSite extends BaseEntity {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty("钻场ID")
    private Integer id;
    @ApiModelProperty("所属巷道ID")
    private Integer roadwayId;
    @ApiModelProperty("钻场名称")
    private String name;
    @ApiModelProperty("钻场宽度")
    private Double length;
    @ApiModelProperty("钻场深度")
    private Double depth;
    @ApiModelProperty("钻场高度")
    private Double height;
    @ApiModelProperty("钻场位置：距离巷道起点距离")
    private Double position;

}
