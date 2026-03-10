package com.ruoyi.hh.physical.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 测点基本信息对象 t_station_info
 * 
 * @author eleven
 * @date 2023-05-16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TStationInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    private Integer roadwayId;
    private Integer drainageUnitId;
    private Integer pipeTypeId;
    private Integer serial;

    /** 测点编码 */
    @Excel(name = "测点编码")
    private String measurePointCode;

    /** 系统编码 */
    @Excel(name = "系统编码")
    private String sysCode;

    /** 分站编码 */
    @Excel(name = "分站编码")
    private String substationCode;

    /** 传感器类型名称 */
    @Excel(name = "传感器类型名称")
    private String sensorTypeName;

    /** 测点数据类型 */
    @Excel(name = "测点数据类型")
    private String measurePointValueType;

    /** 测点数值单位 */
    @Excel(name = "测点数值单位")
    private String measurePointValueUnit;

    /** 高量程 */
    @Excel(name = "高量程")
    private double hightRange;

    /** 低量程 */
    @Excel(name = "低量程")
    private double lowRange;

    /** 上限报警门限 */
    @Excel(name = "上限报警门限")
    private double upWarn;

    /** 上限解报门限 */
    @Excel(name = "上限解报门限")
    private double upCancelWarn;

    /** 下限报警门限 */
    @Excel(name = "下限报警门限")
    private double lowWarn;

    /** 下限解报门限 */
    @Excel(name = "下限解报门限")
    private double lowCancelWarn;

    /** 上限断电门限 */
    @Excel(name = "上限断电门限")
    private double upOutage;

    /** 上限复电门限 */
    @Excel(name = "上限复电门限")
    private double upOnpower;

    /** 下限断电门限 */
    @Excel(name = "下限断电门限")
    private double lowOutage;

    /** 下限复电门限 */
    @Excel(name = "下限复电门限")
    private double lowOnpower;

    /** 开描述 */
    @Excel(name = "开描述")
    private String onDescribe;

    /** 停描述 */
    @Excel(name = "停描述")
    private String stopDescribe;

    /** 测点安装位置 */
    @Excel(name = "测点安装位置")
    private String measurePointLocal;

    /** X坐标 */
    @Excel(name = "X坐标")
    private double X;

    /** Y坐标 */
    @Excel(name = "Y坐标")
    private double Y;

    /** Z坐标 */
    @Excel(name = "Z坐标")
    private double Z;

    /** 传感器关联关系 */
    @Excel(name = "传感器关联关系")
    private String sensorRelevance;

    /** 数据时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "数据时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;
    /**
     * 是否是管路测点（抽采单元的总测点）
     */
    private Boolean piping;

    private Boolean farthestEnd;

    public boolean compareValue(TStationInfo newSI){
        if(!this.getMeasurePointCode().equals(newSI.getMeasurePointCode())){
            return false;
        }else if(!this.getSysCode().equals(newSI.getSysCode())){
            return false;
        }else if(!this.getSubstationCode().equals(newSI.getSubstationCode())){
            return false;
        }else if(!this.getSensorTypeName().equals(newSI.getSensorTypeName())){
            return false;
        }else if(!this.getMeasurePointValueType().equals(newSI.getMeasurePointValueType())){
            return false;
        }else if(!this.getMeasurePointLocal().equals(newSI.getMeasurePointLocal())){
            return false;
        }else if(this.getX()!=newSI.getX()){
            return false;
        }else if(this.getY()!=newSI.getY()){
            return false;
        }else if(this.getZ()!=newSI.getZ()){
            return false;
        }else if(!this.getSensorRelevance().equals(newSI.getSensorRelevance())){
            return false;
        }
        if(this.getMeasurePointValueType().equals("MN")){
            if(!this.getMeasurePointValueUnit().equals(newSI.getMeasurePointValueUnit())){
                return false;
            }else if(this.getHightRange()!=newSI.getHightRange()){
                return false;
            }else if(this.getLowRange()!=newSI.getLowRange()){
                return false;
            }else if(this.getUpWarn()!=newSI.getUpWarn()){
                return false;
            }else if(this.getUpCancelWarn()!=newSI.getUpCancelWarn()){
                return false;
            }else if(this.getLowWarn()!=newSI.getLowWarn()){
                return false;
            }else if(this.getLowCancelWarn()!=newSI.getLowCancelWarn()){
                return false;
            }else if(this.getUpOutage()!=newSI.getUpOutage()){
                return false;
            }else if(this.getUpOnpower()!=newSI.getUpOnpower()){
                return false;
            }else if(this.getLowOutage()!=newSI.getLowOutage()){
                return false;
            }else return this.getLowOnpower() == newSI.getLowOnpower();
        }else if(this.getMeasurePointValueType().equals("KG")){
            if(!this.getOnDescribe().equals(newSI.getOnDescribe())){
                return false;
            }else{
                return this.getStopDescribe().equals(newSI.getStopDescribe());
            }
        }
        return true;
    }

}
