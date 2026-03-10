package com.ruoyi.hh.demo.domain;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import com.ruoyi.common.annotation.Excel;
import lombok.*;


/**
 * (Drillsiteinfo)表实体类
 *
 * @author Eleven
 * @since 2022-04-21 11:18:52
 */
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "drillsiteinfo")
public class Drillsiteinfo extends Model<Drillsiteinfo> {
    @TableField(value = "drillsiteid")
    @Excel(name="钻孔位置id")
    private Integer drillsiteid;
    @TableField(value = "areaid")
    @Excel(name="区域ID")
    private Integer areaid;
    @TableField(value = "drillsitecode")
    @Excel(name="钻孔位置编号")
    private String drillsitecode;
    @TableField(value = "drillsitename")
    @Excel(name="钻孔位置名称")
    private String drillsitename;
    @TableField(value = "drillsitetype")
    @Excel(name="钻孔位置类型")
    private String drillsitetype;
    @TableField(value = "config")
    @Excel(name="配置")
    private String config;
    @TableField(value = "status")
    @Excel(name="状态")
    private Integer status;
    @TableField(value = "notes")
    @Excel(name="备注")
    private String notes;
    @TableField(value = "rman")
    private String rman;
    @Excel(name="开始时间")
//    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @TableField(value = "rdate")
    private Date rdate;
    @TableField(value = "lman")
    private String lman;
    @Excel(name="结束时间")
//    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @TableField(value = "ldate")
    private Date ldate;

}

