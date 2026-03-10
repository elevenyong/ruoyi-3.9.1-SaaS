package com.ruoyi.hh.physical.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 分站信息对象 t_substation_info
 * 
 * @author eleven
 * @date 2023-05-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TSubstationInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 分站编码 */
    @Excel(name = "分站编码")
    private String substationCode;

    /** 分站安装位置 */
    @Excel(name = "分站安装位置")
    private String substationLocal;

    /** 分站运行状态 */
    @Excel(name = "分站运行状态")
    private String workStatus;

    /** 分站供电状态 */
    @Excel(name = "分站供电状态")
    private String electricityStatus;

    /** X坐标 */
    @Excel(name = "X坐标")
    private Double x;

    /** Y坐标 */
    @Excel(name = "Y坐标")
    private Double y;

    /** Z坐标 */
    @Excel(name = "Z坐标")
    private Double z;

    /** 数据时间 */
    @JsonFormat(pattern = "yyyy-MM-dd ")
    @Excel(name = "数据时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /**
     * 对比两个实体是否完全相同
     * @param tSubstationInfo 新实体
     * @return 结果
     */
    public boolean compareValue(TSubstationInfo tSubstationInfo){
        if(!this.getSubstationCode().equals(tSubstationInfo.getSubstationCode())){
            return false;
        }else if(!this.getSubstationLocal().equals(tSubstationInfo.getSubstationLocal())){
            return false;
        }else if(this.getX()!=tSubstationInfo.getX()){
            return false;
        }else if(this.getY()!=tSubstationInfo.getY()){
            return false;
        }
        return this.getZ()!=tSubstationInfo.getZ();

    }

}
