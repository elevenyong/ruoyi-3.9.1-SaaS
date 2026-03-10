package com.ruoyi.hh.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 抽采系统截止某时间的抽采量对象 t_sensordata_before
 * 
 * @author eleven
 * @date 2023-07-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TDrainagedataBefore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 测点位置 */
    @Excel(name = "测点位置")
    private String measurePointLocal;

    /** 测点编码 */
    @Excel(name = "测点编码")
    private String measurePointCode;


    /** 累计瓦斯纯量 */
    @Excel(name = "累计瓦斯纯量")
    private float accumulateGasScalar;

    /** 截止日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截止日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date toDate;


}
