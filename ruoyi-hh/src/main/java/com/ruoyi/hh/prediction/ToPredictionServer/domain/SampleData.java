package com.ruoyi.hh.prediction.ToPredictionServer.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.domain
 * @Project：WSCCManageBackend
 * @name：SampleData
 * @Date：2026/1/20 15:17
 * @Filename：SampleData
 * @Description 训练模型样本数据实体
 */
@Deprecated
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class SampleData extends BaseEntity{

    /**
     * 钻孔ID
     */
    @JsonProperty("borehole_id")
    private String boreholeId;

    /**
     * 煤厚
     */
    @JsonProperty("coal_thickness")
    private Double coalThickness;

    /**
     * 距巷道入口距离
     */
    @JsonProperty("distance_from_entrance")
    private Double distanceFromEntrance;

    /**
     * 距工作面距离
     */
    @JsonProperty("distance_to_face")
    private Double distanceToFace;

    /**
     * 钻屑量S
     */
    @JsonProperty("drilling_cuttings_s")
    private Double drillingCuttingsS;

    /**
     * 钻孔深度
     */
    @JsonProperty("drilling_depth")
    private Double drillingDepth;

    /**
     * 工作面推进距离
     */
    @JsonProperty("face_advance_distance")
    private Double faceAdvanceDistance;

    /**
     * 瓦斯涌出速度Q
     */
    @JsonProperty("gas_emission_velocity_q")
    private Double gasEmissionVelocityQ;

    /**
     * 测量日期
     */
    @JsonProperty("measurement_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date measurementDate;

    /**
     * 区域测量强度
     */
    @JsonProperty("regional_measure_strength")
    private Double regionalMeasureStrength;

    /**
     * 巷道名称
     */
    @JsonProperty("roadway")
    private String roadway;

    /**
     * 巷道ID
     */
    @JsonProperty("roadway_id")
    private String roadwayId;

    /**
     * 工作阶段
     */
    @JsonProperty("work_stage")
    private String workStage;

    /**
     * 工作面ID
     */
    @JsonProperty("workface_id")
    private Integer workfaceId;

    /**
     * 工作面名称
     */
    @JsonProperty("working_face")
    private String workingFace;

    /**
     * X坐标
     */
    @JsonProperty("x_coord")
    private Double xCoord;

    /**
     * Y坐标
     */
    @JsonProperty("y_coord")
    private Double yCoord;

    /**
     * Z坐标
     */
    @JsonProperty("z_coord")
    private Double zCoord;


}