package com.ruoyi.hh.simulate.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FlushingStatistics extends BaseEntity {

    @Excel(name="巷道名称")
    private String roadway;
    /**组号*/
    @Excel(name="组号")
    private String groupNo;
    /**孔号*/
    @Excel(name="孔号")
    private Integer boreholeNo;
    @Excel(name="冲孔数量")
    private Integer boreholecount;
    @Excel(name="冲孔段长度")
    private Double punchingparagraphlength;
    @Excel(name="应冲出煤量")
    private Double designedcoalamountflushed;
    @Excel(name="实际冲出煤量")
    private Double actualcoalamountflushed;
    @Excel(name="完成比例")
    private Double completionratioflushedcoal;
}
