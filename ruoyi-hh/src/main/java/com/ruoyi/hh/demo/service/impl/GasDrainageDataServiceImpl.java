package com.ruoyi.hh.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ruoyi.hh.demo.domain.GasDrainageData;
import com.ruoyi.hh.demo.mapper.GasDrainageDataDao;
import com.ruoyi.hh.demo.service.GasDrainageDataService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
@Service("gasDrainageDataService")
public class GasDrainageDataServiceImpl extends ServiceImpl<GasDrainageDataDao, GasDrainageData> implements GasDrainageDataService {

	@Resource
	GasDrainageDataDao gasDrainageDataDao;
	
	@Override
	public List<GasDrainageData> getGasDrainageDataList() {
		// TODO Auto-generated method stub
		return gasDrainageDataDao.getGasDrainageDataList();
	}

}
