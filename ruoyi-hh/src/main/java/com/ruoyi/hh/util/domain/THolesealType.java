package com.ruoyi.hh.util.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 封孔方式信息对象 t_holeseal_type
 * 
 * @author eleven
 * @date 2022-09-22
 */
public class THolesealType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 封孔方式 */
    @Excel(name = "封孔方式")
    private String holeSealType;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setHoleSealType(String holeSealType) 
    {
        this.holeSealType = holeSealType;
    }

    public String getHoleSealType() 
    {
        return holeSealType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("holeSealType", getHoleSealType())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
