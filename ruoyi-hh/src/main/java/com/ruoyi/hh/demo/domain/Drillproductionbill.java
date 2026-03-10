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
 * (Drillproductionbill)表实体类
 *
 * @author Eleven
 * @since 2022-06-08 17:49:26
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "drillproductionbill")
public class Drillproductionbill extends Model<Drillproductionbill> {
    @TableField(value = "id")
    @TableId(type=IdType.AUTO)
    private Integer id;
    
  //钻场地点    @TableField(value = "drillsiteLocal")
    private String drillsiteLocal;
  //钻场编号    @TableField(value = "drillsiteCode")
    private String drillsiteCode;
    
//    private String drillsiteandno;
    //钻孔号    @TableField(value = "holeno")
    private String holeno;
    //拔钻日期    @TableField(value = "pulldrilldate")
    private Date pulldrilldate;
    //班次    @TableField(value = "classes")
    private String classes;
    //设计夹角    @TableField(value = "designangle")
    private Double designangle;
    //实际夹角    @TableField(value = "actualangle")
    private Double actualangle;
    //设计倾角    @TableField(value = "designdipangle")
    private Double designdipangle;
    //误差倾角    @TableField(value = "errordipangle")
    private Double errordipangle;
    //实际倾角    @TableField(value = "actualdipangle")
    private Double actualdipangle;
    //孔径    @TableField(value = "holediameter")
    private Integer holediameter;
    //设计孔深    @TableField(value = "designdeep")
    private Double designdeep;
    //实际孔深    @TableField(value = "actualdeep")
    private Double actualdeep;
    //设计见煤深度    @TableField(value = "designtocoaldeep")
    private Double designtocoaldeep;
    //实际见煤深度    @TableField(value = "actualtocoaldeep")
    private Double actualtocoaldeep;
    //见煤深度误差    @TableField(value = "errortocoaldeep")
    private Double errortocoaldeep;
    //见煤深度误差比    @TableField(value = "tocoaldeepratio")
    private Double tocoaldeepratio;
    //设计止煤深度    @TableField(value = "designendcoaldeep")
    private Double designendcoaldeep;
    //实际止煤深度    @TableField(value = "actualendcoaldeep")
    private Double actualendcoaldeep;
    //止煤深度误差    @TableField(value = "errorendcoaldeep")
    private Double errorendcoaldeep;
    //止煤深度误差比    @TableField(value = "endcoaldeepratio")
    private Double endcoaldeepratio;
    //钻孔定位导向点    @TableField(value = "drillnavigationpoints")
    private String drillnavigationpoints;
    //与导向点距离（m）    @TableField(value = "distancetonavigationpoints")
    private Double distancetonavigationpoints;
    //是否安装吹水装置    @TableField(value = "blowwaterdevice")
    private String blowwaterdevice;
    //钻孔意图    @TableField(value = "drillpurpose")
    private String drillpurpose;
    //封孔方式    @TableField(value = "holesealingmode")
    private String holesealingmode;
    //拔钻浓度（%）    @TableField(value = "pulldrillconcentration")
    private String pulldrillconcentration;
    //施工负责人    @TableField(value = "constructiondirector")
    private String constructiondirector;
    //瓦斯检察员    @TableField(value = "gasprocurator")
    private String gasprocurator;
    //拔钻时间    @TableField(value = "pulldrilldatetime")
    private Date pulldrilldatetime;
    //封孔下管非责任    @TableField(value = "hsdowntubedirector")
    private String hsdowntubedirector;
    //封孔下管长度（m）    @TableField(value = "hsdowntubelength")
    private Double hsdowntubelength;
    //封孔下管日期    @TableField(value = "hsdowntubedate")
    private Date hsdowntubedate;
    //注浆负责人    @TableField(value = "hsslipcastingdirector")
    private String hsslipcastingdirector;
    //注浆日期    @TableField(value = "hsslipcastingdate")
    private Date hsslipcastingdate;
    //注浆量（L）    @TableField(value = "hsslipcastingcapacity")
    private Integer hsslipcastingcapacity;
    //注浆异常情况    @TableField(value = "hsslipcastingexception")
    private String hsslipcastingexception;
    //封孔冲孔日期    @TableField(value = "hspunchingdate")
    private Date hspunchingdate;
    //封孔冲孔段/m    @TableField(value = "hspunchingsegment")
    private String hspunchingsegment;
    //封孔冲孔长度/m    @TableField(value = "hspunchinglength")
    private Double hspunchinglength;
    //封孔冲孔时间    @TableField(value = "hspunchingdatetime")
    private Date hspunchingdatetime;
    //封孔冲孔压力(Mpa)    @TableField(value = "hspunchingpressure")
    private Double hspunchingpressure;
    //封孔冲孔负责人    @TableField(value = "hspunchingdirector")
    private String hspunchingdirector;
    //封孔冲孔煤量(m³)    @TableField(value = "hspunchingcoalquantity")
    private Double hspunchingcoalquantity;
    //验煤时间    @TableField(value = "hspunchingcheckoutcoaltime")
    private Date hspunchingcheckoutcoaltime;

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

