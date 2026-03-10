package com.ruoyi.hh.util.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 钻孔用途相关对象 t_purpose_borehole
 *
 * @author eleven
 * @date 2022-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TPurposeBorehole extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * $column.columnComment
     */
    private int id;

    /**
     * 钻孔用途
     */
    @Excel(name = "钻孔用途")
    private String purposeBorehole;

}
