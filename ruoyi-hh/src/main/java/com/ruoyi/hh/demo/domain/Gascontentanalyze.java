package com.ruoyi.hh.demo.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.*;


/**
 * (Gascontentanalyze)表实体类
 *
 * @author Eleven
 * @since 2022-06-10 16:36:23
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "gascontentanalyze")
public class Gascontentanalyze extends Model<Gascontentanalyze> {
    @TableField(value = "id")
    private Integer id;
    //区域抽采瓦斯    @TableField(value = "areagas")
    private Double areagas;
    //风排瓦斯    @TableField(value = "windgetgas")
    private Double windgetgas;
    //局部抽采    @TableField(value = "partgetgas")
    private Double partgetgas;
    //残余瓦斯    @TableField(value = "surplusgas")
    private Double surplusgas;
    //点位信息ID    @TableField(value = "pointid")
    private Integer pointid;
    //点位信息
    private String pointName;

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

