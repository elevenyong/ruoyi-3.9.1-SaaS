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
 * (Analogdata)表实体类
 *
 * @author Eleven
 * @since 2022-05-17 14:58:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "analogdata")
public class Analogdata extends Model<Analogdata> implements Serializable{
	private static final long serialVersionUID=8723951313107281173L;
    @TableField(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField(value = "Equipid")
    private String equipid;
    @TableField(value = "PointCode")
    private String pointcode;
    @TableField(value = "PointId")
    private String pointid;
    @TableField(value = "StartTime")
    private Date starttime;
    @TableField(value = "EndTime")
    private Date endtime;
    @TableField(value = "`MinValue`")
    private Double minvalue;
    @TableField(value = "MinTime")
    private Date mintime;
    @TableField(value = "`MaxV`")
    private Double maxv;
    @TableField(value = "MaxTime")
    private Date maxtime;
    @TableField(value = "AvgValue")
    private String avgvalue;
    @TableField(value = "ValidTime")
    private Integer validtime;
    @TableField(value = "AlarmCount")
    private String alarmcount;
    @TableField(value = "FeedCount")
    private String feedcount;
    @TableField(value = "Period")
    private String period;
    @TableField(value = "EquipState")
    private String equipstate;
    @TableField(value = "Status")
    private Integer status;
    @TableField(value = "MixedFlow")
    private double mixedFlow;
    @TableField(value = "pureFlow")
    private double PureFlow;
    @TableField(value = "MethaneConcentration")
    private double methaneConcentration;
    @TableField(value = "COConcentration")
    private double coConcentration;
    @TableField(value = "Temperature")
    private double temperature;

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

