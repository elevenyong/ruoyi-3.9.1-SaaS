package com.ruoyi.hh.demo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.hh.demo.domain.GasDrainageData;

public interface GasDrainageDataService extends IService<GasDrainageData>{
	public List<GasDrainageData> getGasDrainageDataList();
}
