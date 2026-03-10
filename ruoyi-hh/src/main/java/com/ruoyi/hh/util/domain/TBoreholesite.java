package com.ruoyi.hh.util.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 钻场信息对象 t_boreholesite
 *
 * @author eleven
 * @date 2022-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TBoreholesite extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 工作面ID */
    @Excel(name = "工作面ID")
    private Integer workfaceId;

    /** 底板坐标X */
    @Excel(name = "底板坐标X")
    private String floorcoordinatex;

    /** 地板坐标Y */
    @Excel(name = "地板坐标Y")
    private String floorcoordinatey;

    /** 底板坐标Z */
    @Excel(name = "底板坐标Z")
    private String floorcoordinatez;

    /** 煤层厚度（m） */
    @Excel(name = "煤层厚度", readConverterExp = "m=")
    private String coalThick;

    /** 数据类型 */
    @Excel(name = "数据类型")
    private Integer dataType;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private Boolean inuse;

}
