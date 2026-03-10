package com.ruoyi.hh.util.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.util.domain
 * @className: TDrainagePipeType
 * @author: Eleven
 * @description: 抽采管路类型
 * @date: 2023/8/11 17:43
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TDrainagePipeType {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 抽采管路类型 */
    @Excel(name = "抽采管路类型")
    private String pipeType;
}
