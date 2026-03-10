package com.ruoyi.hh.util.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 钻孔类型相关接口对象 t_borehole_category
 *
 * @author eleven
 * @date 2022-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TBoreholeCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 钻孔类别 */
    @Excel(name = "钻孔类别")
    private String boreholeCategory;

    /**钻孔类型识别码*/
    @Excel(name="钻孔类型识别码")
    private Integer categoryCode;

}
