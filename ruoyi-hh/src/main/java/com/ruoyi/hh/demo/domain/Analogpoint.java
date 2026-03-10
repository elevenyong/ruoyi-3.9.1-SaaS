package com.ruoyi.hh.demo.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.*;


/**
 * (Analogpoint)表实体类
 *
 * @author Eleven
 * @since 2022-05-17 14:58:28
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "analogpoint")
public class Analogpoint extends Model<Analogpoint> {
    @TableField(value = "id")
    private Integer id;
    //点位编号    @TableField(value = "pointcode")
    private String pointcode;
    //点位名称    @TableField(value = "pointname")
    private String pointname;
    //端口编号    @TableField(value = "portno")
    private Integer portno;
    //位置信息    @TableField(value = "location")
    private String location;
    //装备代码    @TableField(value = "equipcode")
    private String equipcode;
    //单位    @TableField(value = "unitname")
    private String unitname;
    //信号系统名称    @TableField(value = "signalsystemname")
    private String signalsystemname;
    //信号类型    @TableField(value = "signtype")
    private String signtype;
    //范围码    @TableField(value = "rangecode")
    private String rangecode;
    //最大值    @TableField(value = "maxvalue")
    @TableField("`maxvalue`")
    private Integer maxvalue;
    //中间值    @TableField(value = "midvalue")
    private Integer midvalue;
    //最小值    @TableField(value = "minvalue")
    private Integer minvalue;
    //上限开关    @TableField(value = "upperlimitswitchoff")
    private String upperlimitswitchoff;
    //备注    @TableField(value = "notes")
    private String notes;

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

