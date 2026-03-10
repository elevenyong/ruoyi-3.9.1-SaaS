package com.ruoyi.hh.business.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.hh.physical.domain.TWorkingFace;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class TWorkingFaceVo extends BaseEntity {
    /** 工作面名 */
    @Excel(name = "工作面名")
    private String workingfacename;

    /** 工作面编号 */
    @Excel(name = "工作面编号")
    private String workfaceserial;

    /** 所属采区ID */
    @Excel(name = "所属采区ID")
    private Integer miningareaid;

    /**采区编号*/
    private String miningarea;

    /** 所属煤层ID */
    private Integer coalid;

    /**所属煤层*/
    private String coalserial;

    /** 工作面煤层储量 */
    @Excel(name = "工作面煤层储量")
    private Double coalreserves;

    /** 工作面走向长度 */
    @Excel(name = "工作面走向长度")
    private Double towardslength;

    /** 工作面切眼长度 */
    @Excel(name = "工作面切眼长度")
    private Double cutlength;

    /** 原始瓦斯含量 */
    @Excel(name = "原始瓦斯含量")
    private Double gascontentoriginal;

    /** 工作面瓦斯储量 */
    @Excel(name = "工作面瓦斯储量")
    private Double gastotalreserves;

    /**工作面累计抽采量*/
    @Excel(name="累计抽采量")
    private Double totaldrainagequantity;

    /**已抽时间*/
    @Excel(name="已抽时间")
    private Integer gooverdays;

    /** 预抽瓦斯达标量 */
    @Excel(name = "预抽瓦斯达标量")
    private Double gasamountreachstandard;

    /** 达标残存瓦斯量(m³/t) */
    @Excel(name = "达标残存瓦斯量(m³/t)")
    private Double reachstandardgasremaining;

    /** 残存瓦斯含量(m³/t) */
    @Excel(name = "残存瓦斯含量(m³/t)")
    private Double gasremaining;

    /** 抽采半径 */
    @Excel(name = "抽采半径")
    private Double extractionradius;

    /** 开始抽采日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始抽采日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date begindrainagedate;

    /**抽采单元个数*/
    @Excel(name="抽采单元个数")
    private Integer countdrainageunit;

    public TWorkingFaceVo(TWorkingFace wf, HashMap<String, ArrayList<SysDictData>> dictmap){
        this.workingfacename=wf.getWorkfaceName();
        this.miningarea=wf.getMiningArea();
        this.coalserial=wf.getCoalSerial();
        this.coalreserves=wf.getCoalReserves();
        this.towardslength=wf.getTowardsLength();
        this.cutlength=wf.getCutLength();
        this.gascontentoriginal=wf.getGasContentOriginal();
        this.gastotalreserves=wf.getGasTotalreserves();
        this.totaldrainagequantity=wf.getTotalDrainageQuantity();
        this.extractionradius=wf.getExtractionRadius();
        this.begindrainagedate=wf.getBeginDrainageDate();
        this.countdrainageunit=wf.getCountDrainageUnit();
    }
}
