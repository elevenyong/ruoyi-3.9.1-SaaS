package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 巷道类型对象 t_roadtype
 *
 * @author eleven
 * @date 2022-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TRoadtype extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private int id;

    /**
     * 巷道类型
     */
    @Excel(name = "巷道类型")
    private String roadwayType;

}
