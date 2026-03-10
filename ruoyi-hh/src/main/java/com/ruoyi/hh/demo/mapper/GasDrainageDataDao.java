package com.ruoyi.hh.demo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.hh.demo.domain.GasDrainageData;

public interface GasDrainageDataDao extends BaseMapper<GasDrainageData>{
	public List<GasDrainageData> getGasDrainageDataList();

}
