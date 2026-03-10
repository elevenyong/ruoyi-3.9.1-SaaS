package com.ruoyi.hh.demo.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.*;


/**
 * (Sensortype)表实体类
 *
 * @author Eleven
 * @since 2022-06-09 10:19:57
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sensortype")
public class Sensortype extends Model<Sensortype> {
    @TableField(value = "id")
    private Integer id;
    //传感器种类    @TableField(value = "kind")
    private String kind;

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

