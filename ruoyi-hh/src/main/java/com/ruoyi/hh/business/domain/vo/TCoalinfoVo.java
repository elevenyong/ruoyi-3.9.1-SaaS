package com.ruoyi.hh.business.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.hh.physical.domain.TCoalinfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TCoalinfoVo extends BaseEntity {
    /**
     * 煤层编号
     */
    @Excel(name = "煤层编号")
    private String coalserial;

    /**
     * 煤层储量
     */
    @Excel(name = "煤层储量")
    private Double coalreserves;

    /**
     * 煤层最厚（最小~最大/平均）m
     */
    @Excel(name = "煤层最厚", readConverterExp = "最=小~最大/平均")
    private String coalthick;

    /**
     * 煤层倾角（最小~最大/平均）°
     */
    @Excel(name = "煤层倾角", readConverterExp = "最=小~最大/平均")
    private String coaldip;

    /**
     * 坚固性系数
     */
    @Excel(name = "坚固性系数")
    private String coalhardness;

    /**
     * 煤层层理
     */
    @Excel(name = "煤层层理")
    private String coalbedding;

    /**
     * 煤层节理
     */
    @Excel(name = "煤层节理")
    private String coaljoint;

    /**
     * 自然发火期
     */
    @Excel(name = "自然发火期")
    private String selfignite;

    /**
     * 稳定程度
     */
    @Excel(name = "稳定程度")
    private String stabilize;

    /**
     * 煤尘爆炸指数/%
     */
    @Excel(name = "煤尘爆炸指数/%")
    private String explodecoeffcient;

    /**
     * 地温
     */
    @Excel(name = "地温")
    private Double groundtemperature;

    public TCoalinfoVo(TCoalinfo ti, HashMap<String, ArrayList<SysDictData>> dictmap) {
        this.coalserial = ti.getCoalSerial();
        this.coalreserves = ti.getCoalReserves();
        this.coalthick = ti.getCoalThick();
        this.coaldip = ti.getCoalDip();
        this.coalhardness = ti.getCoalHardness();
        this.coalbedding = ti.getCoalBedding();
        this.coaljoint = ti.getCoalJoint();
        this.selfignite = ti.getSelfIgnite();
        this.stabilize = ti.getStabilize();
        this.explodecoeffcient = ti.getExplodeCoeffcient();
        this.groundtemperature = ti.getGroundTemperature();
    }

}
