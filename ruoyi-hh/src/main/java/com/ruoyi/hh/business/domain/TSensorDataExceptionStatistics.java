package com.ruoyi.hh.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class TSensorDataExceptionStatistics {

    /** 测点安装位置 */
    @Excel(name = "测点名称")
    private String measurePointLocal;

    /** 传感器类型名称 */
    @Excel(name = "测点类型")
    private String sensorTypeName;

    /** 异常类型 */
    @Excel(name = "报警类型")
    private String exceptionType;

    /** 异常期间最大值 */
    @Excel(name = "最大值")
    private Double maxValue;

    /** 最大值时刻 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最大值时刻", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date maxTime;

    /** 异常期间最小值 */
    @Excel(name = "最小值")
    private Double minValue;
    /** 最小值时刻 */
    @Excel(name = "最小值时刻", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date minTime;

    @Excel(name = "首次开始时间")
    private Date firstStartTime;

    @Excel(name = "末次结束时间")
    private Date lastEndTime;

    @Excel(name = "累计次数")
    private int count;

    @Excel(name = "累计时间")
    private int sumSecond;

}
