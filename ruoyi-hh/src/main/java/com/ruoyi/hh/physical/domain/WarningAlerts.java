package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常告警信息（基类）
 */
public class WarningAlerts extends BaseEntity {
    /**
     * 异常记录ID
     */
    private Integer id;

    /**
     * 工作面ID
     */
    private Integer workingFaceId;
    private Double x;
    private Double y;
    private Double z;
    /**
     * 异常类型id
     */
    private Integer type;
    /**
     * 异常类型信息
     */
    private String typeMessage;
    /**
     * 异常信息（简要）
     */
    private String alertMessage;

    /**
     * 异常信息状态
     */
    private Integer status;

    /**
     * 报警等级
     */
    private Integer level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public Integer getWorkingFaceId() {
        return workingFaceId;
    }

    public void setWorkingFaceId(Integer workingFaceId) {
        this.workingFaceId = workingFaceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public WarningAlerts(Double x, Double y, Double z, Integer type, String typeMessage, String alertMessage) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.typeMessage = typeMessage;
        this.alertMessage = alertMessage;
    }

    public WarningAlerts(Double x, Double y, Double z, Integer type, Integer workingFaceId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.workingFaceId = workingFaceId;
    }

    public WarningAlerts() {
    }

    @Override
    public String toString() {
        return "WarningAlerts{" +
                "id=" + id +
                ", workingFaceId=" + workingFaceId +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", type=" + type +
                ", typeMessage='" + typeMessage + '\'' +
                ", alertMessage='" + alertMessage + '\'' +
                ", status=" + status +
                ", level=" + level +
                '}';
    }
}
