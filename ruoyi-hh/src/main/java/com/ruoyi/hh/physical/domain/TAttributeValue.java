package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.hhhz.domain.Point;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * 属性值
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@ApiModel(value = "DZATTR地质属性值类型", description = "DZATTR地质属性值值")
public class TAttributeValue extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("数据ID")
    private Integer id;

    @ApiModelProperty("属性数据类型")
    private Integer attributeTypeId;

    @ApiModelProperty("工作面ID")
    private Integer workfaceId;

    @ApiModelProperty("关联煤岩层ID")
    private Integer rockCoalId;

    @ApiModelProperty("关联钻孔ID")
    private Integer boreholeId;

    @ApiModelProperty("属性数据1")
    private Double attributeValue1;

    @ApiModelProperty("属性数据2")
    private Double attributeValue2;

    @ApiModelProperty("属性数据3")
    private Double attributeValue3;

    @ApiModelProperty("属性数据4")
    private Double attributeValue4;

    @ApiModelProperty("数据位置")
    private Point dataPosition;

    @ApiModelProperty("钻孔取样深度")
    private Double holeMeasureDepth;

    @ApiModelProperty("只读：钻孔所在巷道")
    private Integer holeRoadway;

    @ApiModelProperty("只读：钻孔位置（距离巷口）")
    private Double holeDistance;

    @ApiModelProperty("只读：钻孔倾角")
    private Double holeInclinationAngle;

    @ApiModelProperty("只读：钻孔方位角")
    private Double holeAzimuthAngle;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("数据时间")
    private Date dataTime;
}
