package com.ruoyi.hh.demo.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.*;


/**
 * (Gasoverview)表实体类
 *
 * @author Eleven
 * @since 2022-05-25 16:48:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "gasoverview")
@ApiModel(value="瓦斯总量信息实体",description = "瓦斯总量信息")
public class Gasoverview extends Model<Gasoverview> {
    @TableField(value = "id")
    private Integer id;
    //矿井瓦斯总储量(单位/万m³)    @TableField(value = "gastotalreserves")
    @ApiModelProperty(value="矿井瓦斯总储量(单位/万m³)")
    private Integer gastotalreserves;
    //矿井累计抽采量(单位/万m³)    @TableField(value = "extractiontotalreserves")
    @ApiModelProperty(value="矿井累计抽采量(单位/万m³)")
    private Integer extractiontotalreserves;
    //矿井抽采率    @TableField(value = "mineextractionrate")
    @ApiModelProperty(value="矿井抽采率")
    private Double mineextractionrate;

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

