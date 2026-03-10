package com.ruoyi.hh.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Punchingledger extends BaseEntity {
    @Excel(name="钻孔编号")
    private String boreholecode;
    @Excel(name="钻孔位置")
    private String constructionsite;
    @Excel(name="孔径")
    private String borediameter;
    @Excel(name="设计角度")
    private String desangle;
    @Excel(name="实际角度")
    private String actangle;
    @Excel(name="设计孔深（mm）")
    private String desboreholelength;
    @Excel(name="实际孔深（mm）")
    private String actboreholelength;
    @Excel(name="岩1起")
    private String rock1start;
    @Excel(name="岩1止")
    private String rock1end;
    @Excel(name="煤1起")
    private String coal1start;
    @Excel(name="煤1止")
    private String coal1end;
    @Excel(name="煤2起")
    private String coal2start;
    @Excel(name="煤2止")
    private String coal2end;
    @Excel(name="岩2起")
    private String rock2start;
    @Excel(name="岩2止")
    private String rock2end;
    @Excel(name="岩3起")
    private String rock3start;
    @Excel(name="岩3止")
    private String rock3end;
    @Excel(name="冲煤量")
    private String coalamountflushed;
    @Excel(name="打钻日期",dateFormat = "yyyy-MM-dd" )
    private Date drillingdate;
    @Excel(name="打钻时间",dateFormat = "HH:mm:ss")
    private Date drillingtime;
    @Excel(name="完孔时间",dateFormat = "HH:mm:ss")
    private Date endtime;
    @Excel(name="钻孔施工人员")
    private String personconstruction;
    @Excel(name="钻孔负责人")
    private String chargehand;
    @Excel(name="视频验收人员")
    private String personcheckingvideo;
    @Excel(name="现场验收人员")
    private String personcheckingsite;
    @Excel(name="封孔规格")
    private String holesealingspecification;
    @Excel(name="封孔实管长度")
    private String solidpipelinelength;
    @Excel(name="封孔花管长度")
    private String sievepipelinelength;
    @Excel(name="封孔类型")
    private String holesealtype;
    @Excel(name="注浆日期",dateFormat = "yyyy-MM-dd")
    private Date groutingdate;
    @Excel(name="注浆量")
    private String actualamountbaggrouting;

}
