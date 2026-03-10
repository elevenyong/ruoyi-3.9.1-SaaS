package com.ruoyi.hh.demo.domain;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.*;


/**
 * (Sensordata)表实体类
 *
 * @author Eleven
 * @since 2022-06-09 10:19:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sensordata")
public class Sensordata extends Model<Sensordata> {
    @TableField(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    //点位名称    @TableField(value = "pointname")
    private String pointname;
    //传感器类型    @TableField(value = "sensorid")
    private Integer sensorid;
    //时间    @TableField(value = "date")
    private Date date;
    //数值    @TableField(value="data")
    private Double data;
    private String kind;
    private String unit;

    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    public Serializable pkVal() {
        return this.id;
    }
}

