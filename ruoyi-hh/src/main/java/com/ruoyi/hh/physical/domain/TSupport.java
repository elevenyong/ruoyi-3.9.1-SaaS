package com.ruoyi.hh.physical.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel("DZ回采工作面支架信息")
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TSupport {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Integer id;
    @ApiModelProperty("架号")
    private Integer supportNumber;
    @ApiModelProperty("采高")
    private Double supportHeight;
    @ApiModelProperty("坡度")
    private Double supportAzimuth;
}
