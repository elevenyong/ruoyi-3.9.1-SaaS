package com.ruoyi.hh.business.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.hh.business.domain.TGasremnantStatistics;
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
public class TGasremnantStatisticsVo extends BaseEntity {
    /** 工作面ID */
    @Excel(name = "工作面ID")
    private Integer workfaceId;
    /** 孔号 */
    @Excel(name = "孔号")
    private String boreholeCode;
    /** 取样地点 */
    @Excel(name = "取样地点")
    private String local;
    /** 取样深度（m） */
    @Excel(name = "取样深度", readConverterExp = "m=")
    private Double depth;
    /** 倾角（°） */
    @Excel(name = "倾角", readConverterExp = "°=")
    private Double dip;
    /** 方位角（°） */
    @Excel(name = "方位角", readConverterExp = "°=")
    private Double azimuth;
    /** 残余瓦斯含量（m³/t） */
    @Excel(name = "残余瓦斯含量", readConverterExp = "m=³/t")
    private Double gasContentRemnant;
    /** 残余瓦斯压力（MPa） */
    @Excel(name = "残余瓦斯压力", readConverterExp = "M=Pa")
    private Double gasPressureRemnant;
    /** 记录人 */
    @Excel(name = "记录人")
    private String recorder;

    public TGasremnantStatisticsVo (TGasremnantStatistics ts, HashMap<String, ArrayList<SysDictData>> dictmap){
        this.workfaceId=ts.getWorkfaceId();
        this.boreholeCode=ts.getBoreholeCode();
        this.local=ts.getLocal();
        this.depth=ts.getDepth();
        this.dip=ts.getDip();
        this.azimuth=ts.getAzimuth();
        this.gasContentRemnant=ts.getGasContentRemnant();
        this.gasPressureRemnant=ts.getGasPressureRemnant();
        this.recorder=ts.getRecorder();
    }
}
