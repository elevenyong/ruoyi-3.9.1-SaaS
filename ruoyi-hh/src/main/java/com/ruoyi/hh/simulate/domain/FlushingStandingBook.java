package com.ruoyi.hh.simulate.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlushingStandingBook extends BaseEntity {
    @Excel(name="班次")
    private String shiftinfo;
    @Excel(name="巷道名称")
    private String roadway;
    @Excel(name="组号")
    private String groupNo;
    @Excel(name="孔号")
    private Integer boreholeNo;
    @Excel(name="孔深")
    private Double boreholelength;
    @Excel(name="冲孔段")
    private String punchingparagraph;
    @Excel(name="冲孔段长度")
    private Double punchingparagraphlength;
    @Excel(name="冲出煤量")
    private Double actualcoalamountflushed;
    @Excel(name="冲孔时间")
    private Double totaltimeflushing;
    @Excel(name="冲孔压力")
    private Double waterpressureduringflushing;
}
