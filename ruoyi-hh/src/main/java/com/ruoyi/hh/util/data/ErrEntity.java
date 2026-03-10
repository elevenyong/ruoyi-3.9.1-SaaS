package com.ruoyi.hh.util.data;

import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrEntity {
    /** 测点信息ID */
    @Excel(name = "测点信息ID")
    private Integer measurepointid;
    /**测点名称*/
    private String measuringpointname;

    private double value;
    private String sign;
    private Date time;
}
