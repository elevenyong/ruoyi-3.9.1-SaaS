package com.ruoyi.hh.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.ruoyi.hh.demo.domain.Fluxrealdata;
import com.ruoyi.hh.demo.domain.GasDrainageData;
import com.ruoyi.hh.demo.service.FluxrealdataService;
import com.ruoyi.hh.demo.service.GasDrainageDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("gasDrainageData")
@Api(value="瓦斯抽采数据Controller",tags={"瓦斯抽采数据相关接口"})
public class GasDrainageDataController  {

	/**
     * 服务对象
     */
	@Resource
	private GasDrainageDataService gasDrainageDataService;
	@Resource
    private FluxrealdataService fluxrealdataService;
	
	@GetMapping("getGasDrainageDataList")
	@ApiOperation("获取瓦斯抽采数据")
	public List<GasDrainageData> getGasDrainageDataList(){
		List<GasDrainageData> list=this.gasDrainageDataService.getGasDrainageDataList();
		List<GasDrainageData> gasDrainageDatas=new ArrayList<GasDrainageData>();
		for(GasDrainageData gasDrainageData:list) {
			QueryWrapper<Fluxrealdata> queryWrapper=new QueryWrapper<>();
			queryWrapper.eq("LocationId", gasDrainageData.getLocationid());
			List<Fluxrealdata> fluxrealdataList= fluxrealdataService.list(queryWrapper);
			gasDrainageData.setFluxrealdatas(fluxrealdataList);
			gasDrainageDatas.add(gasDrainageData);
		}
		return gasDrainageDatas;
		
	}
}
