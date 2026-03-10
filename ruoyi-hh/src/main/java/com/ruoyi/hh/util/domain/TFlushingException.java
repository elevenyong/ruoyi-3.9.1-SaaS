package com.ruoyi.hh.util.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 冲孔异常信息对象 t_flushing_exception
 *
 * @author eleven
 * @date 2022-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TFlushingException extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private int id;

    /**
     * 冲孔过程异常现象
     */
    @Excel(name = "冲孔过程异常现象")
    private String exceptionPhenomena;

}
