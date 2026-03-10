package com.ruoyi.hh.demo.domain;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.*;


/**
 * (Constructionmanage)表实体类
 *
 * @author Eleven
 * @since 2022-06-07 17:08:33
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "constructionmanage")
public class Constructionmanage extends Model<Constructionmanage> {
    @TableField(value = "id")
    private Integer id;
    //任务名称    @TableField(value = "taskname")
    private String taskname;
    //任务开始日期    @TableField(value = "startdate")
    private Date startdate;
    //任务计划结束日期    @TableField(value = "planenddate")
    private Date planenddate;
    //备注    @TableField(value = "remarks")
    private String remarks;

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

