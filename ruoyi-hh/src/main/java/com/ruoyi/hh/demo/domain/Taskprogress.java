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
 * (Taskprogress)表实体类
 *
 * @author Eleven
 * @since 2022-06-07 17:08:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "taskprogress")
public class Taskprogress extends Model<Taskprogress> {
    @TableField(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    //任务ID    @TableField(value = "taskid")
    private Integer taskid;
    //工作时间    @TableField(value = "workdate")
    private Date workdate;
    //完成百分比    @TableField(value = "completepercentage")
    private Double completepercentage;

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

