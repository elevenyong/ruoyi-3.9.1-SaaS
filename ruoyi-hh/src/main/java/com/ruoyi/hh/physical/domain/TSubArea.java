package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.hhhz.domain.Point;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DZ工作面子区域")
public class TSubArea extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("区域ID")
    private Integer id;

    /**
     * 区域名称
     */
    @ApiModelProperty("区域名称")
    private String subAreaName;

    /**
     * 对应工作面ID
     */
    @ApiModelProperty("对应工作面ID")
    private Integer workfaceId;

    /**
     * 对应巷道ID
     */
    @ApiModelProperty("对应巷道ID")
    private Integer roadwayId;


    /**
     * 巷道开始距离
     */
    @ApiModelProperty("巷道开始距离")
    private Double startDistance;

    /**
     * 巷道结束距离
     */
    @ApiModelProperty("巷道结束距离")
    private Double endDistance;

    /**
     * 巷道左侧距离
     */
    @ApiModelProperty("巷道左侧距离")
    private Double leftDistance;

    /**
     * 巷道右侧距离
     */
    @ApiModelProperty("巷道右侧距离")
    private Double rightDistance;

    /**
     * 点位
     */
    @ApiModelProperty("区域边界点")
    private List<Point> boundaryPoints;
}
