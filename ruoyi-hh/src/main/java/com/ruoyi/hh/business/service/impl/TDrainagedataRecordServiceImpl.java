package com.ruoyi.hh.business.service.impl;

import com.ruoyi.hh.business.domain.TDrainagedataRecord;
import com.ruoyi.hh.business.mapper.TDrainagedataRecordMapper;
import com.ruoyi.hh.business.service.ITDrainagedataRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 抽采数据记录Service业务层处理
 * 
 * @author eleven
 * @date 2023-07-31
 */
@Service
public class TDrainagedataRecordServiceImpl implements ITDrainagedataRecordService 
{
    @Resource
    private TDrainagedataRecordMapper tDrainagedataRecordMapper;

    /**
     * 查询抽采数据记录
     * 
     * @param id 抽采数据记录主键
     * @return 抽采数据记录
     */
    @Override
    public TDrainagedataRecord selectTDrainagedataRecordById(Integer id)
    {
        return tDrainagedataRecordMapper.selectTDrainagedataRecordById(id);
    }

    /**
     * 查询抽采数据记录列表
     * 
     * @param tDrainagedataRecord 抽采数据记录
     * @return 抽采数据记录
     */
    @Override
    public List<TDrainagedataRecord> selectTDrainagedataRecordList(TDrainagedataRecord tDrainagedataRecord)
    {
        return tDrainagedataRecordMapper.selectTDrainagedataRecordList(tDrainagedataRecord);
    }

    /**
     * 新增抽采数据记录
     * 
     * @param tDrainagedataRecord 抽采数据记录
     * @return 结果
     */
    @Override
    public int insertTDrainagedataRecord(TDrainagedataRecord tDrainagedataRecord)
    {
        return tDrainagedataRecordMapper.insertTDrainagedataRecord(tDrainagedataRecord);
    }

    /**
     * 修改抽采数据记录
     * 
     * @param tDrainagedataRecord 抽采数据记录
     * @return 结果
     */
    @Override
    public int updateTDrainagedataRecord(TDrainagedataRecord tDrainagedataRecord)
    {
        return tDrainagedataRecordMapper.updateTDrainagedataRecord(tDrainagedataRecord);
    }

    /**
     * 批量删除抽采数据记录
     * 
     * @param ids 需要删除的抽采数据记录主键
     * @return 结果
     */
    @Override
    public int deleteTDrainagedataRecordByIds(Integer[] ids)
    {
        return tDrainagedataRecordMapper.deleteTDrainagedataRecordByIds(ids);
    }

    /**
     * 删除抽采数据记录信息
     * 
     * @param id 抽采数据记录主键
     * @return 结果
     */
    @Override
    public int deleteTDrainagedataRecordById(Integer id)
    {
        return tDrainagedataRecordMapper.deleteTDrainagedataRecordById(id);
    }
    /**
     * 根据 根据测点安装位置和时间获取日抽采报表
     * @param measurePointLocal
     * @param time
     * @return
     */
    @Override
    public TDrainagedataRecord getDayDataByLocalAndTime(String measurePointLocal, String time) {
        return this.tDrainagedataRecordMapper.getDayDataByLocalAndTime(measurePointLocal, time);
    }
    /**
     * 根据 根据测点安装位置和时间获取瓦斯抽放量采报表
     * @param measurePointLocal
     * @param time
     * @return
     */
    @Override
    public TDrainagedataRecord getDayDrainageByLocalAndTime(String measurePointLocal, String time) {
        return this.tDrainagedataRecordMapper.getDayDrainageByLocalAndTime(measurePointLocal, time);
    }
    /**
     * 根据 根据测点安装位置和时间获取月抽采报表
     * @param measurePointLocal
     * @param time
     * @return
     */
    @Override
    public TDrainagedataRecord getMonthDataByLocalAndTime(String measurePointLocal, String time) {
        return this.tDrainagedataRecordMapper.getMonthDataByLocalAndTime(measurePointLocal, time);
    }
    /**
     * 根据时间模糊查询测点安装位置
     * @param measurePointLocal
     * @param time
     * @return
     */
    @Override
    public List<String> getLocalVague(String measurePointLocal, String time) {
        return this.tDrainagedataRecordMapper.getLocalVague(measurePointLocal, time);
    }
}
