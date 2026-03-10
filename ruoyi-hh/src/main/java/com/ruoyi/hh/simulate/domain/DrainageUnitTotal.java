package com.ruoyi.hh.simulate.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrainageUnitTotal extends BaseEntity {
    /**工作面ID*/
    private Integer workfaceid;
    /**所属工作面名称*/
    private String workfacename;
    /**抽采单元名称*/
    private String drainageunitname;
    /**抽采单元ID*/
    private Integer drainageunitid;
    /**保存截至时间*/
    private Date upToDate;
    /**累计混合流量*/
    private long totalmixtureflow;
    /**累计纯流量*/
    private long totalpureflow;
}
