package com.ruoyi.hh.simulate.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode()
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsBook {
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
    @Excel(name="冲孔时间")
    private Double totaltimeflushing;
    @Excel(name="施工负责人")
    private String chargehand;
    @Excel(name="冲出煤量")
    private Double actualcoalamountflushed;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name="验收时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date receptiontime;
}
