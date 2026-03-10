package com.ruoyi.hh.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.hh.demo.domain.AnalogDataAvg;
import com.ruoyi.hh.demo.domain.Analogdata;
import com.ruoyi.hh.demo.mapper.AnalogdataDao;
import com.ruoyi.hh.demo.service.AnalogdataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

/**
 * (Analogdata)表服务实现类
 *
 * @author Eleven
 * @since 2022-05-17 14:58:28
 */
@Service("analogdataService")

public class AnalogdataServiceImpl extends ServiceImpl<AnalogdataDao, Analogdata> implements AnalogdataService {

    @Resource
    private AnalogdataDao analogdataDao;

    @Override
    public Analogdata getRealTimeDataByPointCode(String PointCode) {
        return analogdataDao.getRealTimeDataByPointCode(PointCode);
    }

    @Override
    public List<Analogdata> getRealTimeData() {
        return analogdataDao.getRealTimeData();
    }

	@Override
	public List<AnalogDataAvg> getAvgEveryHour(String PointCode) {
		// TODO Auto-generated method stub
		return analogdataDao.getAvgEveryHour(PointCode);
	}

	@Override
	public void insertAnalogDataList(List<Analogdata> list) {
		// TODO Auto-generated method stub
		analogdataDao.insertAnalogDataList(list);
	}

	@Override
	public List<Analogdata> getDataInTime(Date stratTime, Date endTime, String PointCode) {
		// TODO Auto-generated method stub
		return analogdataDao.getDataInTime(stratTime, endTime, PointCode);
	}

	@Override
	public Analogdata getOne(Date stratTime, String PointCode) {
		// TODO Auto-generated method stub
		return analogdataDao.getOne(stratTime, PointCode);
	}
}

