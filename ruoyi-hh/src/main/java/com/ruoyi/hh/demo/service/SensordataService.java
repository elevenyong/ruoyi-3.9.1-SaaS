package com.ruoyi.hh.demo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.hh.demo.domain.Sensordata;

/**
 * (Sensordata)表服务接口
 *
 * @author Eleven
 * @since 2022-06-09 10:19:56
 */
public interface SensordataService extends IService<Sensordata> {
	public List<Sensordata> getSensorData();
	public List<Sensordata> getSensorDataByPointName(String PointName);
	public List<Sensordata> getSensorDataBySensorType(String Kind);
}

