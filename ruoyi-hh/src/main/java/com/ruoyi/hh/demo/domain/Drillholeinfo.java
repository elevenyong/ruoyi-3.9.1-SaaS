package com.ruoyi.hh.demo.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.*;


/**
 * (Drillholeinfo)表实体类
 *
 * @author Eleven
 * @since 2022-05-20 16:10:38
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "drillholeinfo")
public class Drillholeinfo extends Model<Drillholeinfo> {
        @TableField(value = "id")
    private Integer id;
    //钻孔地址    @TableField(value = "drilllocale")
    private String drilllocale;
    //钻场编号    @TableField(value = "drillsiteno")
    private Integer drillsiteno;
    //导线点    @TableField(value = "traversepoint")
    private String traversepoint;
    //对导线点方向    @TableField(value = "conductordirection")
    private String conductordirection;
    //距导线点距离单位m    @TableField(value = "distancetotpoint")
    private Double distancetotpoint;
    //方位角    @TableField(value = "azimuth")
    private String azimuth;
    //倾角    @TableField(value = "dipangle")
    private String dipangle;
    //见煤孔深    @TableField(value = "distanceseecoal")
    private Double distanceseecoal;
    //见岩孔深    @TableField(value = "distanceseerock")
    private Double distanceseerock;
    //实际孔深    @TableField(value = "actualdeep")
    private Double actualdeep;
    //孔号    @TableField(value = "holeno")
    private String holeno;
    //钻孔孔径单位mm    @TableField(value = "drilldiameter")
    private Integer drilldiameter;
    //钻孔类型    @TableField(value = "drilltype")
    private String drilltype;
    //钻孔类别    @TableField(value = "drillcategory")
    private String drillcategory;

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

