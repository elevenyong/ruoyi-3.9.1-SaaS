package com.ruoyi.hh.demo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.hh.demo.domain.Sensordata;

/**
 * (Sensordata)表数据库访问层
 *
 * @author Eleven
 * @since 2022-06-09 10:19:56
 */
public interface SensordataDao extends BaseMapper<Sensordata> {
	public List<Sensordata> getSensorData();
	public List<Sensordata> getSensorDataByPointName(String PointName);
	public List<Sensordata> getSensorDataBySensorType(String Kind);

}

