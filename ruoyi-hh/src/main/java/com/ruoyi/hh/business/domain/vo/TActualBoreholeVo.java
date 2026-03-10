package com.ruoyi.hh.business.domain.vo;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.hh.physical.domain.TActualBorehole;
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
public class TActualBoreholeVo extends BaseEntity {
    /**空号*/
    private String holeNo;
    /**巷道名称*/
    private String roadway;
    /**实际倾角*/
    private Double inclinationAngle;
    /** 实际方位角*/
    private Double azimuthAngle;
    /**煤孔段长度*/
    private Double coalHoleLength;
    /**实际孔深*/
    private Double boreHoleLength;
    /***/
    private String samplingProt; //施工地点
    private double gasContent;//实测瓦斯含量
    private double gasPressure; //实测瓦斯压力
    /***/

    public TActualBoreholeVo(TActualBorehole tb, HashMap<String, ArrayList<SysDictData>> dictmap){
        this.holeNo=tb.getHoleNo();
        this.roadway=tb.getRoadway();
        this.inclinationAngle=tb.getInclinationAngle();
        this.azimuthAngle=tb.getAzimuthAngle();
        this.coalHoleLength=tb.getCoalHoleLength();
        this.boreHoleLength=tb.getBoreholeLength();
        this.samplingProt=tb.getSamplingProt();
        this.gasContent=tb.getGasContent();
        this.gasPressure=tb.getGasPressure();
    }

}
