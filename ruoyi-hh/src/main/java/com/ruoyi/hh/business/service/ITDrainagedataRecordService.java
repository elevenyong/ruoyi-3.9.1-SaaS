package com.ruoyi.hh.business.service;

import com.ruoyi.hh.business.domain.TDrainagedataRecord;

import java.util.List;

/**
 * 抽采数据记录Service接口
 * 
 * @author eleven
 * @date 2023-07-31
 */
public interface ITDrainagedataRecordService 
{
    /**
     * 查询抽采数据记录
     * 
     * @param id 抽采数据记录主键
     * @return 抽采数据记录
     */
    public TDrainagedataRecord selectTDrainagedataRecordById(Integer id);

    /**
     * 查询抽采数据记录列表
     * 
     * @param tDrainagedataRecord 抽采数据记录
     * @return 抽采数据记录集合
     */
    public List<TDrainagedataRecord> selectTDrainagedataRecordList(TDrainagedataRecord tDrainagedataRecord);

    /**
     * 新增抽采数据记录
     * 
     * @param tDrainagedataRecord 抽采数据记录
     * @return 结果
     */
    public int insertTDrainagedataRecord(TDrainagedataRecord tDrainagedataRecord);

    /**
     * 修改抽采数据记录
     * 
     * @param tDrainagedataRecord 抽采数据记录
     * @return 结果
     */
    public int updateTDrainagedataRecord(TDrainagedataRecord tDrainagedataRecord);

    /**
     * 批量删除抽采数据记录
     * 
     * @param ids 需要删除的抽采数据记录主键集合
     * @return 结果
     */
    public int deleteTDrainagedataRecordByIds(Integer[] ids);

    /**
     * 删除抽采数据记录信息
     * 
     * @param id 抽采数据记录主键
     * @return 结果
     */
    public int deleteTDrainagedataRecordById(Integer id);

    /**
     * 根据 根据测点安装位置和时间获取日抽采报表
     * @param measurePointLocal
     * @param time
     * @return
     */
    public TDrainagedataRecord getDayDataByLocalAndTime(String measurePointLocal,  String time);
    /**
     * 根据 根据测点安装位置和时间获取瓦斯抽放量采报表
     * @param measurePointLocal
     * @param time
     * @return
     */
    public TDrainagedataRecord getDayDrainageByLocalAndTime(String measurePointLocal,  String time);

    /**
     * 根据 根据测点安装位置和时间获取月抽采报表
     * @param measurePointLocal
     * @param time
     * @return
     */
    public TDrainagedataRecord getMonthDataByLocalAndTime( String measurePointLocal,  String time);

    /**
     * 根据时间模糊查询测点安装位置
     * @param measurePointLocal
     * @param time
     * @return
     */
    public List<String>  getLocalVague(String measurePointLocal,String time);
}
