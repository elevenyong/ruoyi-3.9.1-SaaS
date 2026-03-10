package com.ruoyi.hh.hhhz.domain.param;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.physical.domain.TDesignedDirectionalBorehole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.hhhz.domain.param
 * @className: DesignParameters
 * @author: Eleven
 * @description: 设计参数公共类
 * @date: 2023/7/19 10:45
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignParameters extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;
    /** 用户ID */
    @Excel(name="用户ID",width = 15)
    private String Uid;
    @Excel(name="工作面ID",width = 15)
    private Integer workfaceId;
    @Excel(name="巷道ID",width = 15)
    private Integer roadwayId;
    @Excel(name="设计参数类型",width = 15)
    private String parameterType;
    @Excel(name="详细内容",width = 15)
    private String detailedContent;
    @Excel(name="确定方案标识",width = 15)
    private String sign;
    @Excel(name="方案ID",width = 15)
    private String schemeId;
    /**
     * 设计参数中的普钻信息
     */
//    private List<TDesignedBorehole> tDesignedBoreholes;
    private List<TDesignedBorehole> tDesignedBoreholes;

    public List<TDesignedBorehole> gettDesignedBoreholes() {
        return tDesignedBoreholes;
    }

    public void settDesignedBoreholes(List<TDesignedBorehole> tDesignedBoreholes) {
        this.tDesignedBoreholes = tDesignedBoreholes;
    }

    /**
     * 设计参数中的定向钻信息
     */
    private List<TDesignedDirectionalBorehole> tDesignedDirectionalBoreholes;

    public List<TDesignedDirectionalBorehole> gettDesignedDirectionalBoreholes() {
        return tDesignedDirectionalBoreholes;
    }

    public void settDesignedDirectionalBoreholes(List<TDesignedDirectionalBorehole> tDesignedDirectionalBoreholes) {
        this.tDesignedDirectionalBoreholes = tDesignedDirectionalBoreholes;
    }



}
