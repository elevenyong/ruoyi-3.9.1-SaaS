package com.ruoyi.hh.business.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.business.domain
 * @className: MeasurePointData
 * @author: Eleven
 * @description: 计算测点的实时抽采量
 * @date: 2023/8/9 16:38
 * @version: 1.0
 */
@Data
@NoArgsConstructor
public class MeasurePointData {
    /**工作面ID*/
    private Integer workfaceId;
    /**工作面名称*/
    private String workfaceName;
    /**所属巷道ID*/
    private Integer roadwayId;
    /**抽采单元ID*/
    private Integer drainageUnitId;
    /**抽采单元名称*/
    private String drainageUnit;
    /**所属管路类型ID*/
    private Integer drainagePipeTypeId;
    /**所属管路类型*/
    private String drainagePipeType;
    /**测点安装位置*/
    private String measurePointLocal;
    /**测点编码*/
    private String measurePointCode;
    /**该测点在该巷道中的位置顺序*/
    private Integer serial;
    /**累计抽采纯量*/
    private Float gasScalar;
    /** 残余瓦斯含量（m³/t） */
    private Double gasContentRemnant;
    /** 残余瓦斯储量（m³） */
    private Long gasReservesRemnant;
    /** 瓦斯预抽率 */
    private Double gasCompleteRatio;
    /**累计抽放量*/
    private Float drainageQuantity;
    /**是否为该巷道最远端的总累积量测点*/
    private Boolean farthestEnd;
    /**数据发生时间*/
    private Date dataTime;
}
