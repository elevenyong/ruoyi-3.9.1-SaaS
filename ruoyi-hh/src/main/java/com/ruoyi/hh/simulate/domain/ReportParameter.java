package com.ruoyi.hh.simulate.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportParameter extends BaseEntity {
    private Integer evaluateunitid; //评价单元ID
    private Integer workfaceid;  //工作面ID
    private boolean survey;  //第一章概况
    private boolean outburstpreventionstep; //第二章 防突措施
    private boolean reachjudge;  //第三章 区域瓦斯抽采达标评判
//    private boolean coalinfo;   //煤层赋存情况表
//    private boolean faultinfo;  //工作面实际揭露断层情况表
//    private boolean  gascontentpresses;  //工作面实测瓦斯含量、压力情况
//    private boolean  boreholeinfo; //区域防突措施钻孔（穿层+顺层）施工记录
//    private boolean coalreservesstatistics; //工作面煤层储量统计表
//    private boolean coalgasreserves;  //工作面煤层瓦斯储量统计表
//    private boolean gasquantitydetail;  //工作面评判区域内瓦斯抽采量明细表
//    private boolean coalreservesremnant;  //工作面残余瓦斯含量情况表
//    private boolean diversityfactor;  //工作面区域预抽差异系数明细表
//    private boolean gasremnantCconPre;  //实测残余瓦斯含量及压力测定统计表
}
