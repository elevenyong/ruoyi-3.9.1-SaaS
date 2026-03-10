package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设计定向钻数据对象 t_directedhole_data
 * 
 * @author eleven
 * @date 2023-03-06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TDirectedholeData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 所属工作面ID */
    @Excel(name = "所属工作面ID")
    private Integer workfaceId;

    /** 开孔位置 */
    @Excel(name = "开孔位置")
    private String holePlace;

    /** 开孔高度 */
    @Excel(name = "开孔高度")
    private Double holeHight;

    /** 开孔倾角 */
    @Excel(name = "开孔倾角")
    private Double trepanningDip;

    /** 终孔倾角 */
    @Excel(name = "终孔倾角")
    private Double finishDip;

    /** 煤孔开始 */
    @Excel(name = "煤孔开始")
    private Double coalholeStart;

    /** 煤孔结束 */
    @Excel(name = "煤孔结束")
    private Double coalholeEnd;

//    /**煤孔范围*/
//    private String coalholerange;

    /** 造斜段最大变化率 */
    @Excel(name = "造斜段最大变化率")
    private Double deflectingMaxGradient;

}
