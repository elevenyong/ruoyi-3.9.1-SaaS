package com.ruoyi.hh.business.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.hhhz.domain.param.DesignParameters;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 设计方案信息对象 t_des_scheme_info
 *
 * @author eleven
 * @date 2023-06-30
 */
public class TDesSchemeInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 方案名 */
    @Excel(name = "方案名")
    private String scheme;

    private Integer sign;

    private Integer workfaceId;

    public List<DesignParameters> getDesignParameters() {
        return designParameters;
    }

    public void setDesignParameters(List<DesignParameters> designParameters) {
        this.designParameters = designParameters;
    }

    /**条带设计参数列表*/
    private List<DesignParameters> designParameters;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setScheme(String scheme)
    {
        this.scheme = scheme;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Integer getWorkfaceId() {
        return workfaceId;
    }

    public void setWorkfaceId(Integer workfaceId) {
        this.workfaceId = workfaceId;
    }

    public String getScheme()
    {
        return scheme;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("scheme", getScheme())
            .append("createTime", getCreateTime())
            .toString();
    }
}
