package com.ruoyi.hh.business.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.hh.simulate.domain.SimulationMeasurepointData;
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
public class SimulationMeasurepointDataVo extends BaseEntity {
    /** 测点信息ID */
    @Excel(name = "测点信息ID")
    private Integer measurepointid;
    /**测点名称*/
    private String measuringpointname;
    /**抽采单元名称*/
    private String unitname;
    /** 所属抽采单元ID */
    @Excel(name = "所属抽采单元ID")
    private Integer drainageunitid;

    /**所属工作面名称*/
    private String workingfacename;
    /** 所属工作面ID */
    @Excel(name = "所属工作面ID")
    private Integer workfaceid;

    /** 混合流量 */
    @Excel(name = "混合流量")
    private Double mixtureflow;

    /** 纯流量 */
    @Excel(name = "纯流量")
    private Double pureflow;

    /** 温度 */
    @Excel(name = "温度")
    private Double temperature;

    /** 甲烷浓度 */
    @Excel(name = "甲烷浓度")
    private Double ch4concentration;

    /** 一氧化碳浓度 */
    @Excel(name = "一氧化碳浓度")
    private Double coconcentration;

    /** 抽采负压 */
    @Excel(name = "抽采负压")
    private Double negativepressure;

    /** 二氧化碳浓度 */
    @Excel(name = "二氧化碳浓度")
    private Double co2concentration;


    /** 累计抽采混合流量 */
    @Excel(name = "累计抽采混合流量")
    private Long totalmixtureflow;

    /** 累计抽采纯量 */
    @Excel(name = "累计抽采纯量")
    private Long totalpureflow;

    /** 乙烯浓度 */
    @Excel(name = "乙烯浓度")
    private Double ethene;

    /** 乙炔浓度 */
    @Excel(name = "乙炔浓度")
    private Double ethyne;

    /** 氧气浓度 */
    @Excel(name = "氧气浓度")
    private Double oconcentration;


    public SimulationMeasurepointDataVo(SimulationMeasurepointData smd, HashMap<String, ArrayList<SysDictData>> dictmap){
        this.measuringpointname=smd.getMeasurePointName();
        this.unitname=smd.getUnitName();
        this.workingfacename=smd.getWorkfaceName();
        this.mixtureflow=smd.getMixTureFlow();
        this.pureflow=smd.getPureFlow();
        this.temperature=smd.getTemperature();
        this.ch4concentration=smd.getCh4Concentration();
        this.coconcentration=smd.getCoConcentration();
        this.negativepressure=smd.getNegativePressure();
        this.co2concentration=smd.getCo2Concentration();
        this.totalmixtureflow=smd.getTotalMixTureFlow();
        this.totalpureflow=smd.getTotalPureFlow();
        this.ethene=smd.getEthene();
        this.ethyne=smd.getEthyne();
        this.oconcentration=smd.getOConcentration();

    }
}
