package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.hhhz.domain.Point;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DZ工作面风险区域")
public class TRiskArea extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("风险区域ID")
    private Integer id;

    /**
     * 区域名称
     */
    @ApiModelProperty("区域名称")
    private String riskAreaName;

    /**
     * 对应工作面ID
     */
    @ApiModelProperty("对应工作面ID")
    private Integer workfaceId;

    /**
     * 对应煤层信息的ID
     */
    @ApiModelProperty("对应煤层地层ID")
    private Integer coalinfoId;

    /**
     * 风险等级
     */
    @ApiModelProperty("风险等级：0=低风险，1=中风险，2=高风险")
    private Integer riskLevel;

    /**
     * 风险类型
     */
    @ApiModelProperty("风险类型：0=其他，1=采空区")
    private Integer riskType;

    /**
     * 影响范围
     */
    @ApiModelProperty("风险因素影响范围（指风险区域边界之外波及到的范围）")
    private Double influenceRange;
    /**
     * 风险信息
     */
    @ApiModelProperty("风险信息")
    private String riskInfo;
    /**
     * 区域点位
     */
    @ApiModelProperty("区域边界点")
    private List<Point> boundaryPoints;
}
