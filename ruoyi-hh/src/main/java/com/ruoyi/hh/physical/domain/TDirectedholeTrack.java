package com.ruoyi.hh.physical.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设计定向钻轨迹对象 t_directedhole_track
 * 
 * @author eleven
 * @date 2023-03-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TDirectedholeTrack extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 定向钻孔ID */
    @Excel(name = "定向钻孔ID")
    private Integer directedholeId;

    /** 孔深 */
    @Excel(name = "孔深")
    private Double holedepth;

    /** 方位角 */
    @Excel(name = "方位角")
    private Double azimuthangle;

    /** 倾角 */
    @Excel(name = "倾角")
    private Double inclinationangle;

    /** 偏差 */
    @Excel(name = "偏差")
    private Double deviation;

    /** 投影深度 */
    @Excel(name = "投影深度")
    private Double projectiondepth;

    /** 左右偏差 */
    @Excel(name = "左右偏差")
    private Double leftrightdeviation;

    /** 上下偏差 */
    @Excel(name = "上下偏差")
    private Double updowndeviation;

    /** 东偏差 */
    @Excel(name = "东偏差")
    private Double eastdeviation;

    /** 西偏差 */
    @Excel(name = "西偏差")
    private Double westdeviation;

    /** 高程 */
    @Excel(name = "高程")
    private Double elevation;

}
