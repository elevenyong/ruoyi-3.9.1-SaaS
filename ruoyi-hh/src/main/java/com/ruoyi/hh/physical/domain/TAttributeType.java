package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 属性类型
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@ApiModel(value = "DZATTR地质属性类型类型", description = "DZATTR地质属性类型类型")
public class TAttributeType extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("属性数据类型ID")
    private Integer id;
    @ApiModelProperty("属性数据类型名称")
    private String attributeName;
    @ApiModelProperty("属性数据单位")
    private String dataUnit;
}
