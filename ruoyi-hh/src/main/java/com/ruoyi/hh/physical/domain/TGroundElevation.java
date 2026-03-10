package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DZ地面高程")
public class TGroundElevation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("高程点ID")
    private Integer id;

    @ApiModelProperty("关联工作面ID")
    private Integer workfaceId;

    @ApiModelProperty("X")
    private Double x;

    @ApiModelProperty("Y")
    private Double y;

    @ApiModelProperty("Z")
    private Double z;

    @ApiModelProperty("备注")
    private String description;
}
