package com.ruoyi.hh.demo.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.*;


/**
 * (Extractionjudge)表实体类
 *
 * @author Eleven
 * @since 2022-06-13 17:07:02
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "extractionjudge")
public class Extractionjudge extends Model<Extractionjudge> {
    //自增    @TableField(value = "id")
    private Integer id;
    //条带ID    @TableField(value = "stripeid")
    private Integer stripeid;
    //目标抽采总量（万m³）    @TableField(value = "targettotal")
    private String targettotal;
    //累计抽采总量（万m³）    @TableField(value = "accumulativetotal")
    private String accumulativetotal;
    //累计风排瓦斯总量（万m³）    @TableField(value = "accumulativetotalbywind")
    private String accumulativetotalbywind;
    //煤总储量（万t）    @TableField(value = "coalgross")
    private String coalgross;
    //瓦斯总储量（万m³）    @TableField(value = "gasgross")
    private String gasgross;
    //平均瓦斯含量（m³/t）    @TableField(value = "avggascontent")
    private String avggascontent;
    //抽采达标量（m³/t）    @TableField(value = "extractionreachquantity")
    private String extractionreachquantity;
    //昨日累计抽采量（万m³）    @TableField(value = "yesterdayquantity")
    private String yesterdayquantity;
    //昨日风排瓦斯总量（万m³）    @TableField(value = "yesterdaywindexhaustgas")
    private String yesterdaywindexhaustgas;
    
    private String ConstructionName;
    private String StripeCode;
    //当前瓦斯浓度
    private double CurrentConcentration;

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

