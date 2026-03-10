package com.ruoyi.hh.demo.domain;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.*;


/**
 * (Fluxrealdata)表实体类
 *
 * @author Eleven
 * @since 2022-05-25 16:48:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "fluxrealdata")
@ApiModel(value="月统计瓦斯抽采量")
public class Fluxrealdata extends Model<Fluxrealdata> {
    @TableField(value = "id")
    private Integer id;
    //混合抽采量    @TableField(value = "mixedtotal")
    @ApiModelProperty(value="月累计混合总量")
    private String mixedtotal;
    //纯抽采总量    @TableField(value = "purefluxtotal")
    @ApiModelProperty(value="月累计抽采纯量")
    private String purefluxtotal;
    //统计日期    @TableField(value = "fluxdate")
    @ApiModelProperty(value="时间")
    private Date fluxdate;
    //更新时间    @TableField(value = "updatetime")
    private Date updatetime;

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

