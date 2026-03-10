package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 煤层信息表对象 t_coalinfo
 *
 * @author eleven
 * @date 2022-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TCoalinfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Integer id;

//    /**
//     * 煤层编号
//     */
//    @Excel(name = "煤层编号",width = 20)
//    private String coalSerial;
//
//    /**
//     * 煤层储量
//     */
//    @Excel(name = "煤层储量")
//    private Double coalReserves;
//
//    /**
//     * 煤层最厚（最小~最大/平均）m
//     */
//    @Excel(name = "煤层最厚", readConverterExp = "最=小~最大/平均")
//    private String coalThick;
//
//    /**
//     * 煤层倾角（最小~最大/平均）°
//     */
//    @Excel(name = "煤层倾角", readConverterExp = "最=小~最大/平均")
//    private String coalDip;
//
//    /**
//     * 坚固性系数
//     */
//    @Excel(name = "坚固性系数")
//    private String coalHardness;
//
//    /**
//     * 煤层层理
//     */
//    @Excel(name = "煤层层理")
//    private String coalBedding;
//
//    /**
//     * 煤层节理
//     */
//    @Excel(name = "煤层节理")
//    private String coalJoint;
//
//    /**
//     * 自然发火期
//     */
//    @Excel(name = "自然发火期")
//    private String selfIgnite;
//
//    /**
//     * 稳定程度
//     */
//    @Excel(name = "稳定程度")
//    private String stabilize;
//
//    /**
//     * 煤尘爆炸指数/%
//     */
//    @Excel(name = "煤尘爆炸指数/%")
//    private String explodeCoeffcient;
//
//    /**
//     * 地温
//     */
//    @Excel(name = "地温")
//    private Double groundTemperature;
    /**
     * 煤层编号
     */
    @Excel(name = "煤层编号",width = 20,needMerge=true)
    private String coalSerial;

    /**
     * 煤层储量
     */
    @Excel(name = "煤层储量",needMerge=true)
    private Double coalReserves;

    /**
     * 煤层最厚（最小~最大/平均）m
     */
    @Excel(name = "煤层最厚", readConverterExp = "最=小~最大/平均",needMerge=true)
    private String coalThick;

    /**
     * 煤层倾角（最小~最大/平均）°
     */
    @Excel(name = "煤层倾角", readConverterExp = "最=小~最大/平均",needMerge=true)
    private String coalDip;

    /**
     * 坚固性系数
     */
    @Excel(name = "坚固性系数",needMerge=true)
    private String coalHardness;

    /**
     * 煤层层理
     */
    @Excel(name = "煤层层理",needMerge=true)
    private String coalBedding;

    /**
     * 煤层节理
     */
    @Excel(name = "煤层节理",needMerge=true)
    private String coalJoint;

    /**
     * 自然发火期
     */
    @Excel(name = "自然发火期",needMerge=true)
    private String selfIgnite;

    /**
     * 稳定程度
     */
    @Excel(name = "稳定程度",needMerge=true)
    private String stabilize;

    /**
     * 煤尘爆炸指数/%
     */
    @Excel(name = "煤尘爆炸指数/%",needMerge=true)
    private String explodeCoeffcient;

    /**
     * 地温
     */
    @Excel(name = "地温",needMerge=true)
    private Double groundTemperature;
    /**
     * 煤层坐标信息
     */
    @Excel(name="煤层坐标")
    private List<TCoalPoint> coalPointinfoList;


}
