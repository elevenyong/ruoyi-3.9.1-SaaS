package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.physical.domain
 * @className: TBKCDSS
 * @author: Eleven
 * @description: 测点抽放统计类
 * @date: 2023/6/6 09:12
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBKCDSS extends BaseEntity {
//    private String measurePointCode; //测点编码

    private String measurePointLocal; //测点安装位置
    private float gasScalarDay;  //日累计抽采纯量
//    private float gasScalarWeek; //周累计抽采纯量
    private float gasScalarMonth; //月抽采纯量
    private float drainageQuantity; //瓦斯抽放量
    private double avgConcentration;  //平均浓度
    private double avgExtractionVolume;//平均混合流量(min/m³)
    private double avgScalar; //平均抽采纯量(min/m³)
    private Date time;  //发生时间
}
