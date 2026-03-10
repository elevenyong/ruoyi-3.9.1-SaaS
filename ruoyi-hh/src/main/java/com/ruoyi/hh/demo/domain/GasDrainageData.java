package com.ruoyi.hh.demo.domain;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="瓦斯抽采数据",description = "瓦斯抽采数据基本信息")
public class GasDrainageData extends Model<GasDrainageData> implements  Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Locationid;
	@ApiModelProperty(value ="哪个井",name="哪个井")
	private String locationName;
	@ApiModelProperty(value ="年累计混合总量")
	private int mixedTotalYear;
	@ApiModelProperty(value ="年累计纯抽采量")
	private int pureFluxtotalYear;
	@ApiModelProperty(value ="混合流量")
	private double mixedFlow;
	@ApiModelProperty(value ="瓦斯纯流量")
	private double pureGasFlow;
	@ApiModelProperty(value ="负压")
	private double negativePressure;
	@ApiModelProperty(value ="温度")
	private double temperature;
	@ApiModelProperty(value ="氧气浓度")
	private double oxygenConcentration;
	@ApiModelProperty(value ="瓦斯浓度")
	private double gasConcentration;
	@ApiModelProperty(value ="二氧化碳浓度")
	private double carbonDioxideConcentration;
	@ApiModelProperty(value ="月统计瓦斯抽采量")
	private List<Fluxrealdata> fluxrealdatas;
}
