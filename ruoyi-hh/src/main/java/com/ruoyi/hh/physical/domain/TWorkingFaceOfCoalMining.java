package com.ruoyi.hh.physical.domain;

//import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.hh.demo.domain.TGeoFault;
import com.ruoyi.hh.hhhz.domain.Point;
import com.ruoyi.hh.util.handler.JsonIntegerArrayTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 采煤工作面对象 t_working_face_of_coal_mining
 *
 * @author hhhz
 * @date 2023-04-28
 */
@ApiModel("DZWF采煤工作面")
@Getter
@Setter
public class TWorkingFaceOfCoalMining extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 采煤工作面ID
     */
    @ApiModelProperty("采煤工作面ID")
    private Integer id;

    /**
     * 采煤工作面名称
     */
    @Excel(name = "采煤工作面名称")
    @ApiModelProperty("工作面名称")
    private String workfaceName;

    /**
     * 煤层数量
     */
    @Excel(name = "煤层数量")
    @ApiModelProperty("工作面煤层数量")
    private Integer coalNumber;

    /**
     * 工作面初始煤层厚度
     */
    @Excel(name = "工作面初始煤层厚度")
    @ApiModelProperty("工作面初始煤层厚度")
    private Double defaultCoalThickness;

    /**
     * 走向长度
     */
    @Excel(name = "走向长度")
    @ApiModelProperty("走向长度")
    private Double towardsLength;

    /**
     * 切眼长度/宽度
     */
    @Excel(name = "切眼长度")
    @ApiModelProperty("切眼长度")
    private Double cutLength;

    /**
     * 高度/初始煤层厚度
     */
    @Excel(name = "初始煤层厚度")
    @ApiModelProperty("初始煤层厚度")
    private Double height;
    /**
     * 倾角
     */
    @Excel(name = "倾角")
    @ApiModelProperty("倾角")
    private Double inclinationAngle;

    /**
     * 方位角
     */
    @Excel(name = "方位角")
    @ApiModelProperty("方位角")
    private Double azimuthAngle;

    /**
     * 倾向
     */
    @ApiModelProperty("倾向：0=顺时针，1=逆时针")
    private Integer dipDirection;

    /**
     * 工作面支架数量
     */
    @Excel(name = "工作面支架数量")
    @Deprecated
    @ApiModelProperty(hidden = true)
    private Integer numberOfWorkfaceSupports;

    /**
     * 单个支架宽度
     */
    @Excel(name = "单个支架宽度")
    @Deprecated
    @ApiModelProperty(hidden = true)
    private Double supportLength;

    /**
     * 首架位置
     */
    @Excel(name = "首架位置")
    @Deprecated
    @ApiModelProperty(hidden = true)
    private Integer firstSupportPosition;

    /**
     * 首架距离外帮距离
     */
    @Excel(name = "首架距离外帮距离")
    @Deprecated
    @ApiModelProperty(hidden = true)
    private Double outDistanceOfFirstSupport;


    /**
     * 关联底抽巷
     */
    @Excel(name = "关联底抽巷")
    @ApiModelProperty("关联底抽巷ID列表[进风底抽id,回风底抽id,切眼底抽id,中抽id]")
    private String bottomExtractionRoadway;

    /**
     * 关联进风巷
     */
    @Excel(name = "关联进风巷")
    @ApiModelProperty("关联进风巷ID")
    private Integer airIntakeRoadway;
    /**
     * 进风巷长度
     */
    @Excel(name = "进风巷长度")
    private Double airIntakeRoadwayLength;
    /**
     * 进风巷实体
     */
    @ApiModelProperty(value = "只读：进风巷实体", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private TRoadway airIntakeRoadwayData;

    /**
     * 关联回风巷
     */
    @Excel(name = "关联回风巷")
    @ApiModelProperty("关联回风巷ID")
    private Integer airReturnRoadway;

    /**
     * 关联回风巷
     */
    @Excel(name = "关联中间巷")
    @ApiModelProperty("关联中间巷ID")
    private Integer middleRoadway;

    /**
     * 回风巷实体
     */
    @ApiModelProperty(value = "只读：中间巷实体", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private TRoadway middleRoadwayData;

    /**
     * 关联回风巷长度
     */
    @Excel(name = "关联回风巷长度")
    private Double airReturnRoadwayLength;
    /**
     * 回风巷实体
     */
    @ApiModelProperty(value = "只读：回风巷实体", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private TRoadway airReturnRoadwayData;


    /**
     * 关联底抽巷
     */
    @ApiModelProperty(value = "关联进风底抽巷ID")
    private Integer dcAirIntakeRoadway;
    @ApiModelProperty(value = "关联回风底抽巷ID")
    private Integer dcAirReturnRoadway;
    @ApiModelProperty(value = "关联切眼底抽巷ID")
    private Integer dcCutEyeRoadway;
    @ApiModelProperty(value = "关联中间底抽巷ID")
    private Integer dcMiddleRoadway;
    /**
     * 关联底抽巷实体
     */
    @ApiModelProperty(value = "只读：进风底抽巷实体", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private TRoadway dcAirIntakeRoadwayData;
    @ApiModelProperty(value = "只读：回风底抽巷实体", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private TRoadway dcAirReturnRoadwayData;
    @ApiModelProperty(value = "只读：切眼底抽巷实体", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private TRoadway dcCutEyeRoadwayData;
    @ApiModelProperty(value = "只读：中间底抽巷实体", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private TRoadway dcMiddleRoadwayData;


    /**
     * 关联切眼巷
     */
    @Excel(name = "关联切眼巷")
    @ApiModelProperty("关联切眼巷ID")
    private Integer cutEyeRoadway;

    /**
     * 切眼巷实体
     */
    @ApiModelProperty(value = "只读：切眼巷实体", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private TRoadway cutEyeRoadwayData;

    /**
     * 关联采区大巷
     */
    @Excel(name = "关联采区大巷")
    @Deprecated
    private Integer miningAreaRoadway;

    /**
     * 关联其他巷道
     */
    @Excel(name = "关联切眼巷")
    @ApiModelProperty("关联其他巷道ID")
    private Integer[] otherRoadway;

    /*public Integer[] getOtherRoadway() {
        return Arrays.stream(otherRoadway.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
    }*/

    /*public void setOtherRoadway(Integer[] otherRoadway) {
        this.otherRoadway = Arrays.toString(otherRoadway);
    }*/

    @ApiModelProperty(value = "只读：其他巷道数据", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private List<TRoadway> otherRoadwayData;

    /**
     * 采区大巷实体
     */
    @Deprecated
    @ApiModelProperty(value = "只读：采区大巷实体", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private TRoadway miningAreaRoadwayData;
    /**
     * 岩层信息
     */
    @ApiModelProperty(value = "只读：岩层信息", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private List<TRockinfo> rockinfoList;

    /**
     * 煤层点位信息
     */
    @ApiModelProperty(value = "只读：煤层点位信息", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private List<TCoalPoint> coalPoints;

    @ApiModelProperty(value = "只读：断层信息", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private List<TGeoFault> geoFaults;

    @ApiModelProperty(value = "只读：陷落柱信息", accessMode = ApiModelProperty.AccessMode.READ_ONLY, hidden = true)
    private List<TGeoFallenColumn> geoFallenColumns;

    @ApiModelProperty("工作面边界点")
    private List<Point> boundaryPoints;

    @ApiModelProperty("只读：工作面子区域信息")
    private List<TSubArea> subAreas;

    @ApiModelProperty("只读：回采工作信息")
    private List<TWorkOfCoalMining> workOfCoalMining;

    @Override
    public String toString() {
        return "TWorkingFaceOfCoalMining{" +
                "id=" + id +
                ", towardsLength=" + towardsLength +
                ", cutLength=" + cutLength +
                ", height=" + height +
                ", inclinationAngle=" + inclinationAngle +
                ", azimuthAngle=" + azimuthAngle +
                ", numberOfWorkingFaceSupports=" + numberOfWorkfaceSupports +
                ", supportLength=" + supportLength +
                ", firstSupportPosition=" + firstSupportPosition +
                ", outDistanceOfFirstSupport=" + outDistanceOfFirstSupport +
                ", bottomExtractionRoadway='" + bottomExtractionRoadway + '\'' +
                ", airIntakeRoadway='" + airIntakeRoadway + '\'' +
                ", airIntakeRoadwayData=" + airIntakeRoadwayData +
                ", airReturnRoadway='" + airReturnRoadway + '\'' +
                ", airReturnRoadwayData=" + airReturnRoadwayData +
                ", dcAirIntakeRoadwayData=" + dcAirIntakeRoadwayData +
                ", dcAirReturnRoadwayData=" + dcAirReturnRoadwayData +
                ", dcCutEyeRoadwayData=" + dcCutEyeRoadwayData +
                ", cutEyeRoadway='" + cutEyeRoadway + '\'' +
                ", cutEyeRoadwayData=" + cutEyeRoadwayData +
                ", miningAreaRoadway='" + miningAreaRoadway + '\'' +
                ", miningAreaRoadwayData=" + miningAreaRoadwayData +
                '}';
    }
}
