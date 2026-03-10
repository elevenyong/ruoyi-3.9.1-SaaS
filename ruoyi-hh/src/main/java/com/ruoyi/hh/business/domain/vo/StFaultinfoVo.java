package com.ruoyi.hh.business.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.hh.simulate.domain.StFaultinfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StFaultinfoVo extends BaseEntity {

    /** 工作面ID */
    @Excel(name = "工作面ID")
    private Integer workfaceid;

    /** 断层名称 */
    @Excel(name = "断层名称")
    private String faultname;

    /** 评价单元名称*/
    private String evaluateunitname;

    /** 走向 */
    @Excel(name = "走向")
    private Double direction;

    /** 倾向 */
    @Excel(name = "倾向")
    private Double tendency;

    /** 倾角 */
    @Excel(name = "倾角")
    private Double dip;

    /** 断层性质 */
    @Excel(name = "断层性质")
    private String nature;

    /** 落差（m） */
    @Excel(name = "落差", readConverterExp = "m=")
    private String fail;

    /** 预计对回采面影响程度 */
    @Excel(name = "预计对回采面影响程度")
    private String influencedegree;

    public StFaultinfoVo(StFaultinfo sf, HashMap<String, ArrayList<SysDictData>> dictmap){
        this.faultname=sf.getFaultName();
        this.evaluateunitname=sf.getEvaluateUnitName();
        this.direction=sf.getDirection();
        this.tendency=sf.getTendency();
        this.dip=sf.getDip();
        this.nature=sf.getNature();
        this.fail=sf.getFail();
        this.influencedegree=sf.getInfluenceDegree();
    }
}
