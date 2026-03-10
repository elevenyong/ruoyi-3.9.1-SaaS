package com.ruoyi.hh.demo.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.*;


/**
 * (Mineinfo)表实体类
 *
 * @author Eleven
 * @since 2022-05-25 16:48:03
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mineinfo")
@ApiModel(value="矿井基本信息实体",description = "矿井基本信息")
public class Mineinfo extends Model<Mineinfo>{
    @TableField(value = "id")
    private Integer id;
    
    //矿井名称    @TableField(value = "minename")
    @ApiModelProperty(value="矿井名称")
    private String minename;
    //矿井位置    @TableField(value = "minelocal")
    @ApiModelProperty(value="矿井位置")
    private String minelocal;
    //矿井等级    @TableField(value = "minegrade")
    @ApiModelProperty(value="矿井等级")
    private String minegrade;
    //鉴定等级    @TableField(value = "appraisalcompany")
    @ApiModelProperty(value="鉴定等级")
    private String appraisalcompany;
    //设计产能（万吨）    @TableField(value = "designcapacity")
    @ApiModelProperty(value="设计产能（万吨）")
    private Double designcapacity;
    //核定产能（万吨）    @TableField(value = "approvedcapacity")
    @ApiModelProperty(value="核定产能（万吨）")
    private Double approvedcapacity;
    //地质储能（亿吨）    @TableField(value = "geologystorage")
    @ApiModelProperty(value="地质储能（亿吨）")
    private Double geologystorage;
    //地质储能（亿吨）    @TableField(value = "geologystorage1")
    @ApiModelProperty(value="地质储能（亿吨）")
    private Double geologystorage1;
    //绝对瓦斯量(m³/min)    @TableField(value = "absolutegasquantity")
    @ApiModelProperty(value="绝对瓦斯量(m³/min)")
    private Double absolutegasquantity;
    //相对瓦斯量(m³/min)    @TableField(value = "relativegasquantity")
    @ApiModelProperty(value="相对瓦斯量(m³/min)")
    private Double relativegasquantity;
    //走倾向长    @TableField(value = "longwalkingtendency")
    @ApiModelProperty(value="走倾向长")
    private String longwalkingtendency;
    //水平标高    @TableField(value = "horizontalelevation")
    @ApiModelProperty(value="水平标高")
    private String horizontalelevation;
    //井田面积    @TableField(value = "minefieldarea")
    @ApiModelProperty(value="井田面积 ")
    private String minefieldarea;
    //水文条件    @TableField(value = "hydrologiccondition")
    @ApiModelProperty(value="水文条件")
    private String hydrologiccondition;
    //开采煤层    @TableField(value = "miningcoalseam")
    @ApiModelProperty(value="开采煤层")
    private String miningcoalseam;
    //通风方式    @TableField(value = "ventilationmode")
    @ApiModelProperty(value="通风方式")
    private String ventilationmode;
    //通风方法    @TableField(value = "ventilationmethod")
    @ApiModelProperty(value="通风方法")
    private String ventilationmethod;
    //排水方式    @TableField(value = "drainagemode")
    @ApiModelProperty(value="排水方式")
    private String drainagemode;
    //开采方法    @TableField(value = "miningmethod")
    @ApiModelProperty(value="开采方法")
    private String miningmethod;
    //开拓方式    @TableField(value = "developmentmode")
    @ApiModelProperty(value="开拓方式")
    private String developmentmode;

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

