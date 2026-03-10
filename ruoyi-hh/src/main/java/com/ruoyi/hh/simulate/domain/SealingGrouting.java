package com.ruoyi.hh.simulate.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 封孔注浆信息
 *
 * @author eleven
 * @date 2022-09-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SealingGrouting extends BaseEntity {
    @Excel(name = "巷道名称")
    private String roadway;
    @Excel(name = "组号")
    private String groupNo;
    @Excel(name = "孔号")
    private Integer boreholeNo;
    /** 注浆日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注浆日期", width = 15, dateFormat = "yyyy-MM-dd")
    private Date groutingdate;
    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date starttime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endtime;
    /** 需要囊袋注浆量（L） */
    @Excel(name = "需要囊袋注浆量")
    private Double desamountbaggrouting;

    /** 实际囊袋注浆量（L） */
    @Excel(name = "实际囊袋注浆量")
    private Double actualamountbaggrouting;

    /** 囊袋注浆压力（MPa） */
    @Excel(name = "囊袋注浆压力")
    private Double actualbagpressure;

    /** 所需中间段注浆量（L） */
    @Excel(name = "所需中间段注浆量")
    private Double designedamountgapgrouting;

    /** 实际中间段注浆量（L） */
    @Excel(name = "实际中间段注浆量")
    private Double actualamountgapgrouting;

    /** 中间段注浆压力（MPa） */
    @Excel(name = "中间段注浆压力")
    private Double interludegroutingpressure;

    /** 注浆人员 */
    @Excel(name = "注浆人员")
    private String groutingperson;
    /** 始抽瓦斯浓度 */
//    @Excel(name = "始抽瓦斯浓度")
    private Double startdrainagegasconcentration;
    /** 始抽负压*/
    private Double startdrainagepressure;
    /** 始抽浓度测量日期 */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "始抽浓度测量日期", width = 15, dateFormat = "yyyy-MM-dd")
    private Date startdrainagedate;
//
    /** 始抽浓度测量人员 */
//    @Excel(name = "始抽浓度测量人员")
    private String startdrainageperson;

    /** 异常现象标注 */
    @Excel(name = "异常现象标注")
    private String exceptionlabel;
}
