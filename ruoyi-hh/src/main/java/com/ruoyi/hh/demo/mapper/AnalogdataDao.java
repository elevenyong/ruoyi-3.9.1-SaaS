package com.ruoyi.hh.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import java.util.Date;
import java.util.List;

import com.ruoyi.hh.demo.domain.AnalogDataAvg;
import com.ruoyi.hh.demo.domain.Analogdata;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (Analogdata)表数据库访问层
 *
 * @author Eleven
 * @since 2022-05-17 14:58:27
 */
@Mapper
public interface AnalogdataDao extends BaseMapper<Analogdata> {
    public Analogdata getRealTimeDataByPointCode(String PointCode);
    public List<Analogdata> getRealTimeData();
    public List<AnalogDataAvg> getAvgEveryHour(String PointCode);
    public void createTodayTable(String tableName);
    public void deleteSubTable(String tableName);
    public void insertAnalogDataList(List<Analogdata> list);
    public List<Analogdata> getDataInTime(@Param("stratTime") Date stratTime,@Param("endTime") Date endTime,@Param("PointCode") String PointCode);
    public Analogdata getOne(Date stratTime,String PointCode);
}

