package com.ruoyi.hh.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 封孔注浆信息对象 t_sealing_grouting
 *
 * @author eleven
 * @date 2022-07-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TSealingGrouting extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 孔号对应ID */
//    @Excel(name = "孔号对应ID")
    private Integer boreholeId;
    @Excel(name = "巷道名称")
    private String roadway;
    @Excel(name = "组号")
    private Integer groupNo;
    @Excel(name = "孔号")
    private Integer boreholeNo;
    /**下管长度*/
//    private Double lengthpipeinsertion;

    /** 班次ID */
//    @Excel(name = "班次ID")
    private Integer shiftId;

    @Excel(name="班次")
    private String shiftInfo;

    /** 注浆日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注浆日期", width = 15, dateFormat = "yyyy-MM-dd")
    private Date groutingDate;

    /** 下管长度 */
    @Excel(name = "下管长度")
    private Double tubeLength;

//    @Excel(name="下管日期", width = 15, dateFormat = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Date datepipeinsertion;
//
//    @Excel(name="下管责任人")
//    private String checkerpipeinsertion;

    /** 封孔方式ID */
//    @Excel(name = "封孔方式ID")
    private Integer holesealTypeId;

    @Excel(name="封孔方式")
    private String holesealType;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 需要囊袋注浆量（L） */
    @Excel(name = "需要囊袋注浆量")
    private Double desAmountBagGrouting;

    /** 实际囊袋注浆量（L） */
    @Excel(name = "实际囊袋注浆量")
    private Double actualAmountBagGrouting;

    /** 囊袋注浆压力（MPa） */
    @Excel(name = "囊袋注浆压力")
    private Double actualBagPressure;

    /** 所需中间段注浆量（L） */
    @Excel(name = "所需中间段注浆量")
    private Double designedAmountGapGrouting;

    /** 实际中间段注浆量（L） */
    @Excel(name = "实际中间段注浆量")
    private Double actualAmountGapGrouting;

    /** 中间段注浆压力（MPa） */
    @Excel(name = "中间段注浆压力")
    private Double interludeGroutingPressure;

    /** 注浆人员 */
    @Excel(name = "注浆人员")
    private String groutingPerson;

    @Excel(name="注浆负责人")
    private String chargehand;

    /** 始抽瓦斯浓度 */
//    @Excel(name = "始抽瓦斯浓度")
    private Double startDrainageGasConcentration;
    /** 始抽负压*/
    private Double startDrainagePressure;
    /** 始抽浓度测量日期 */
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "始抽浓度测量日期", width = 15, dateFormat = "yyyy-MM-dd")
    private Date startDrainageDate;
//
    /** 始抽浓度测量人员 */
//    @Excel(name = "始抽浓度测量人员")
    private String startdrainageperson;

    /** 异常现象标注 */
    @Excel(name = "异常现象标注")
    private String exceptionLabel;

    /** 记录人 */
    @Excel(name = "记录人")
    private String recorder;

}
