package com.ruoyi.hh.business.mapper;

import com.ruoyi.hh.business.domain.TDrainagedataRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 抽采数据记录Mapper接口
 * 
 * @author eleven
 * @date 2023-07-31
 */
public interface TDrainagedataRecordMapper 
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
     * 删除抽采数据记录
     * 
     * @param id 抽采数据记录主键
     * @return 结果
     */
    public int deleteTDrainagedataRecordById(Integer id);

    /**
     * 批量删除抽采数据记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDrainagedataRecordByIds(Integer[] ids);

    /**
     * 根据 根据测点安装位置和时间获取日抽采报表
     * @param measurePointLocal
     * @param time
     * @return
     */
    @Select("select * from t_drainagedata_record where measure_point_local =#{measurePointLocal} and data_time =#{time} and data_type ='瓦斯日累计纯量'")
    public TDrainagedataRecord getDayDataByLocalAndTime(@Param("measurePointLocal") String measurePointLocal,@Param("time")  String time);

    /**
     * 根据 根据测点安装位置和时间获取日抽采报表
     * @param measurePointLocal
     * @param time
     * @return
     */
    @Select("select * from t_drainagedata_record where measure_point_local =#{measurePointLocal} and data_time =#{time} and data_type ='瓦斯抽放量'")
    public TDrainagedataRecord getDayDrainageByLocalAndTime(@Param("measurePointLocal") String measurePointLocal,@Param("time")  String time);
    /**
     * 根据 根据测点安装位置和时间获取月抽采报表
     * @param measurePointLocal
     * @param time
     * @return
     */
    @Select("select * from t_drainagedata_record where measure_point_local =#{measurePointLocal} and data_time =#{time} and data_type ='瓦斯月累计纯量'")
    public TDrainagedataRecord getMonthDataByLocalAndTime(@Param("measurePointLocal") String measurePointLocal,@Param("time")  String time);


    /**
     * 根据时间模糊查询测点安装位置
     * @param measurePointLocal
     * @param time
     * @return
     */
    @Select("select measure_point_local from t_drainagedata_record where data_time =#{time} and measure_point_local like '%${measurePointLocal}%' group by measure_point_local")
    public List<String>  getLocalVague(@Param("measurePointLocal") String measurePointLocal,@Param("time")  String time);
}
