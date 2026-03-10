package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * 掘进工作面
 */
@ApiModel("DZWF掘进工作面")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TWorkingFaceOfDriving extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 掘进工作面ID
     */
    @ApiModelProperty("掘进工作面ID")
    private Integer id;

    /**
     * 掘进工作面名称
     */
    @ApiModelProperty("掘进工作面名称")
    private String workfaceName;

    /**
     * 掘进巷道ID
     */
    @ApiModelProperty("掘进巷道ID")
    private Integer roadwayId;

    /**
     * 迎头位置
     */
    @ApiModelProperty("始掘位置：距巷口")
    private Double originPosition;

    /**
     * 迎头位置
     */
    @ApiModelProperty("迎头位置：距巷口")
    private Double currentPosition;

    /**
     * 掘进方向
     */
    @ApiModelProperty("掘进方向：0=向外（巷口）掘进，1=向内（切眼）掘进")
    private Integer direction;

    /**
     * 掘进工作
     */
    @ApiModelProperty("掘进工作面关联掘进工作")
    private List<TDrivingWork> workingFaceList;
}
