package com.ruoyi.hh.demo.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.*;


/**
 * (Stripeinfo)表实体类
 *
 * @author Eleven
 * @since 2022-06-13 17:07:01
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "stripeinfo")
public class Stripeinfo extends Model<Stripeinfo> {
    //自增    @TableField(value = "id")
    private Integer id;
    //巷道名    @TableField(value = "constructionname")
    private String constructionname;
    //条带编号    @TableField(value = "stripecode")
    private String stripecode;

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

