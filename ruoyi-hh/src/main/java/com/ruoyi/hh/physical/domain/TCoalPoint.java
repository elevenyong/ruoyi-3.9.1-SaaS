package com.ruoyi.hh.physical.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 煤层对应的坐标点信息对象 t_coal_point
 *
 * @author eleven
 * @date 2022-10-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("DZ煤层点位信息(地测孔数据)")
public class TCoalPoint extends BaseEntity implements Comparable<TCoalPoint> {
    private static final long serialVersionUID = 1L;

    /**
     * 煤层点ID
     */
    @ApiModelProperty("煤层点ID")
    private Integer id;

    /**
     * 对应煤层信息的ID
     */
    @Excel(name = "对应煤层信息的ID")
    @ApiModelProperty("对应煤层地层ID")
    private Integer coalinfoId;

    /**
     * 关联工作面ID
     */
    @ApiModelProperty("关联工作面ID")
    private Integer workfaceId;


    /**
     * （煤层底板）坐标X
     */
    @Excel(name = "（煤层底板）坐标X")
    @ApiModelProperty("（煤层底板）坐标X")
    private Double floorCoordinatex;

    /**
     * （煤层底板）坐标Y
     */
    @Excel(name = "（煤层底板）坐标Y")
    @ApiModelProperty("（煤层底板）坐标Y")
    private Double floorCoordinatey;

    /**
     * （煤层底板）坐标Z
     */
    @Excel(name = "（煤层底板）坐标Z")
    @ApiModelProperty("（煤层底板）坐标Z")
    private Double floorCoordinatez;

    /**
     * 煤层厚度
     */
    @Excel(name = "煤层厚度")
    @ApiModelProperty("煤层厚度")
    private Double coalThick;

    /**
     * 数据类型(1=地测钻孔点，2=断层面点，3=陷落柱点位)
     */
    @Excel(name = "数据类型")
    @ApiModelProperty("地质点类型(1=地测钻孔点，2=断层面点，3=陷落柱点位)")
    private Integer dataType;

    /**
     * 是否启用（0=停用，1=启用）
     */
    @Excel(name = "是否启用")
    @ApiModelProperty("是否启用（0=停用，1=启用）")
    private Integer isuse;

    /**
     * 关联断层ID（只有当坐标点为断层点类型时，该字段才应该有值）
     */
    @ApiModelProperty("断层点所属关联断层ID")
    private Integer geoFaultId;

    /**
     * 关联陷落柱ID（只有当坐标点为陷落柱类型时，该字段才应该有值）
     */
    @ApiModelProperty("关联陷落柱ID")
    private Integer geoFallenId;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId()).append("coalinfoId", getCoalinfoId()).append("floorCoordinatex", getFloorCoordinatex()).append("floorCoordinatey", getFloorCoordinatey()).append("floorCoordinatez", getFloorCoordinatez()).append("coalThick", getCoalThick()).append("dataType", getDataType()).append("isuse", getIsuse()).append("createBy", getCreateBy()).append("createTime", getCreateTime()).append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
    }

    public TCoalPoint() {
    }

    public TCoalPoint(Integer id, Integer coalinfoId, Double floorCoordinatex, Double floorCoordinatey, Double floorCoordinatez, Integer dataType) {
        this.id = id;
        this.coalinfoId = coalinfoId;
        this.floorCoordinatex = floorCoordinatex;
        this.floorCoordinatey = floorCoordinatey;
        this.floorCoordinatez = floorCoordinatez;
        this.dataType = dataType;
    }

    /**
     * 按照x，y两值相加排序
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(TCoalPoint o) {
        return (int) ((getFloorCoordinatex() + getFloorCoordinatey()) - (o.getFloorCoordinatex() + o.getFloorCoordinatey()));
    }
}
