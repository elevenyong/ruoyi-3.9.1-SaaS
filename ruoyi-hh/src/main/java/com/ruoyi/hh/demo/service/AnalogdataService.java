package com.ruoyi.hh.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.hh.demo.domain.AnalogDataAvg;
import com.ruoyi.hh.demo.domain.Analogdata;

import java.util.Date;
import java.util.List;

/**
 * (Analogdata)表服务接口
 *
 * @author Eleven
 * @since 2022-05-17 14:58:28
 */
public interface AnalogdataService extends IService<Analogdata> {
    public Analogdata getRealTimeDataByPointCode(String PointCode);
    public List<Analogdata> getRealTimeData();
    public List<AnalogDataAvg> getAvgEveryHour(String PointCode);
    public void insertAnalogDataList(List<Analogdata> list);
    public List<Analogdata> getDataInTime(Date stratTime,Date endTime,String PointCode);
    public Analogdata getOne(Date stratTime,String PointCode);
    
}

