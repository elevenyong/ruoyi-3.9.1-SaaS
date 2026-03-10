package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.util.handler.CustomDateDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@ApiModel("DZ回采工作信息")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TWorkOfCoalMining extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("关联采煤工作面ID")
    private Integer workfaceId;

    @ApiModelProperty("回采速度（进尺，按每日统计）")
    private Double miningRate;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("回采班次:支持格式25.(或/)(或-)08.01.0(或4)(或8)，三个规则：四部分组成;(./-)分割;最后一位是0或4或8")
    private Date miningShift;

    @ApiModelProperty("当前采面支架数量")
    private Integer numberOfWorkfaceSupports;

    @ApiModelProperty("单个支架宽度")
    private Double supportLength;

    @ApiModelProperty("施工单位")
    private String constructionUnit;

    @ApiModelProperty("采面前方风巷测点ID")
    private Integer returnAirRoadNavPointId;

    @ApiModelProperty("风巷采面距导线点距离")
    private Double disToRetAirNavPoint;

    @ApiModelProperty("采面前方机巷测点ID")
    private Integer intakeAirRoadNavPointId;

    @ApiModelProperty("机巷采面距导线点距离")
    private Double disToInAirNavPoint;

    @ApiModelProperty("支架信息")
    private List<TSupport> supports;
}
