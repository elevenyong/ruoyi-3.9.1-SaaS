package com.ruoyi.hh.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import javax.annotation.Resource;

import com.ruoyi.hh.demo.domain.Sensordata;
import com.ruoyi.hh.demo.mapper.SensordataDao;
import com.ruoyi.hh.demo.service.SensordataService;
import org.springframework.stereotype.Service;

/**
 * (Sensordata)表服务实现类
 *
 * @author Eleven
 * @since 2022-06-09 10:19:56
 */
@Service("sensordataService")
public class SensordataServiceImpl extends ServiceImpl<SensordataDao, Sensordata> implements SensordataService {

	@Resource
	private SensordataDao sensordataDao;
	
	@Override
	public List<Sensordata> getSensorData() {
		// TODO Auto-generated method stub
		return sensordataDao.getSensorData();
	}

	@Override
	public List<Sensordata> getSensorDataByPointName(String PointName) {
		// TODO Auto-generated method stub
		return sensordataDao.getSensorDataByPointName(PointName);
	}

	@Override
	public List<Sensordata> getSensorDataBySensorType(String Kind) {
		// TODO Auto-generated method stub
		return sensordataDao.getSensorDataBySensorType(Kind);
	}

}

