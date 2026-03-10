package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.physical.domain
 * @className: TBKGasDrainageQuantity
 * @author: Eleven
 * @description: 瓦斯抽放量实体
 * @date: 2023/7/13 13:49
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBKGasDrainageQuantity extends BaseEntity {
    /**
     * 月累计纯量
     */
    private Float gasScalarMonth;
    /**
     * 累计纯量
     */
    private Float gasScalar;
    /**
     * 整月的累计纯量
     */
    private Float gasMonoBlock;
    /**
     * 抽放量
     */
    private Float drainageQuantity;
    private Integer month; //获取当前数据的月份
    private Float avgConcentration;  //平均浓度
}
