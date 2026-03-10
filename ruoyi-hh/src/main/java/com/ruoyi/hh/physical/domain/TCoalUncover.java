package com.ruoyi.hh.physical.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 揭煤抽采信息对象 t_coal_uncover
 * 
 * @author eleven
 * @date 2022-12-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TCoalUncover extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 揭煤名称 */
    @Excel(name = "揭煤名称")
    private String name;

    /** 抽采区域状态（0完成1进行中） */
    @Excel(name = "抽采区域状态", readConverterExp = "0=完成1进行中")
    private String status;

    /** 钻孔数量 */
    @Excel(name = "钻孔数量")
    private Double holesize;

    /** 治理范围 */
    @Excel(name = "治理范围")
    private String governRange;

    /** 揭煤区煤储量 */
    @Excel(name = "揭煤区煤储量")
    private Double coalReserves;

    /** 原始瓦斯含量 */
    @Excel(name = "原始瓦斯含量")
    private String gasContentOriginal;

    /** 目标抽采总量 */
    @Excel(name = "目标抽采总量")
    private Double targetTotal;

    /** 累计抽采总量 */
    @Excel(name = "累计抽采总量")
    private Double quantityTotal;

    /** 预期抽采达标时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预期抽采达标时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date predictfinishDate;

    /**所含的测点列表*/
    private List<TMeasuringPoint> tMeasuringPointList;

}
