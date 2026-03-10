package com.ruoyi.hh.physical.attribute.domain;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author: eleven
 * @date: 2024/11/12 10:58
 * @Description: 钻孔应力测点基本信息数据
 */
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBZKYL {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer id;

    /** 测点编码 */
    @Excel(name = "测点编码")
    private String measurePointCode;

    /** 监测区名称 */
    @Excel(name = "监测区名称")
    private String areaName;

    /** 传感器类型 */
    @Excel(name = "传感器类型")
    private String sensorTypeName;

    /** 传感器位置 */
    @Excel(name = "传感器位置")
    private String sensorLocal;

    /** 应力计安装深度 */
    @Excel(name = "应力计安装深度")
    private double stressmeterDepth;

    /** 安装时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "安装时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installationTime;

    /** X */
    @Excel(name = "X")
    private double X;

    /** Y */
    @Excel(name = "Y")
    private double Y;

    /** Z */
    @Excel(name = "Z")
    private double Z;

    /** 应力计方向 */
    @Excel(name = "应力计方向")
    private String direction;

    /** 初始应力 */
    @Excel(name = "初始应力")
    private double initializationStress;

    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public boolean compareValue(TBZKYL newTBZKYL){
        if(!this.getMeasurePointCode().equals(newTBZKYL.getMeasurePointCode())){
            return false;
        }else if(!this.getSensorTypeName().equals(newTBZKYL.getSensorTypeName())){
            return false;
        }else if(!this.getSensorLocal().equals(newTBZKYL.getSensorLocal())){
            return false;
        }else if(this.getStressmeterDepth()!=newTBZKYL.getStressmeterDepth()){
            return false;
        }else if(!this.getInstallationTime().equals(newTBZKYL.getInstallationTime())){
            return false;
        }else if(this.getX()!=newTBZKYL.getX()){
            return false;
        }else if(this.getY()!=newTBZKYL.getY()){
            return false;
        }else if(this.getZ()!=newTBZKYL.getZ()){
            return false;
        }else if(!this.getDirection().equals(newTBZKYL.getDirection())){
            return false;
        }else if(this.getInitializationStress()!=newTBZKYL.getInitializationStress()){
            return false;
        }
        return true;
    }
}
