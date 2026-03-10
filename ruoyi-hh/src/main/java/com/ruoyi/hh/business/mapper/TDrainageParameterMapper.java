package com.ruoyi.hh.business.mapper;

import com.ruoyi.hh.business.domain.TDrainageParameter;
import com.ruoyi.hh.physical.domain.TActualBorehole;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * 钻孔抽采参数测量Mapper接口
 * 
 * @author eleven
 * @date 2022-07-30
 */
public interface TDrainageParameterMapper 
{
    /**
     * 查询钻孔抽采参数测量
     * 
     * @param id 钻孔抽采参数测量主键
     * @return 钻孔抽采参数测量
     */
    public TDrainageParameter selectTDrainageParameterById(Integer id);

    /**
     * 查询钻孔抽采参数测量列表
     * 
     * @param tDrainageParameter 钻孔抽采参数测量
     * @return 钻孔抽采参数测量集合
     */
    public List<TDrainageParameter> selectTDrainageParameterList(TDrainageParameter tDrainageParameter);

    /**
     * 新增钻孔抽采参数测量
     * 
     * @param tDrainageParameter 钻孔抽采参数测量
     * @return 结果
     */
    public Integer insertTDrainageParameter(TDrainageParameter tDrainageParameter);

    /**
     * 修改钻孔抽采参数测量
     * 
     * @param tDrainageParameter 钻孔抽采参数测量
     * @return 结果
     */
    public Integer updateTDrainageParameter(TDrainageParameter tDrainageParameter);

    /**
     * 删除钻孔抽采参数测量
     * 
     * @param id 钻孔抽采参数测量主键
     * @return 结果
     */
    public Integer deleteTDrainageParameterById(Integer id);

    /**
     * 批量删除钻孔抽采参数测量
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTDrainageParameterByIds(@RequestParam List<Integer> ids);

    /**
     * 分页查询
     * @return 列表
     */
    public List<TDrainageParameter> getPageList();

    /**
     * 根据巷道ID，测量日期查询钻孔抽采参数
     * @param roadwayId 巷道ID
     * @param groupNo 组号
     * @param purposeboreholeid 钻孔用途ID
     * @param boreholeNo 孔号
     * @param startdate 开始日期
     * @param enddate 结束日期
     * @return 列表
     */
    public List<TDrainageParameter> getTDrainageParameter(@Param("roadwayId") Integer roadwayId,
                                                          @Param("groupNo") List<String> groupNo,
                                                          @Param("purposeboreholeid") List<Integer> purposeboreholeid,
                                                          @Param("boreholeNo") List<Integer> boreholeNo,
                                                          @Param("startdate") Date startdate,
                                                          @Param("enddate") Date enddate);

    /**
     *
     * 根据钻孔ID删除钻孔抽采参数测量信息
     * @param boreholeIds 钻孔ID
     * @return 结果
     */
    public Integer delByBoreholeIds(@RequestParam List<Integer> boreholeIds);

    public Integer delByBoreholeId(@RequestParam Integer boreholeId);

    /**
     * 根据钻孔ID查询钻孔抽采参数测量信息
     * @param boreholeId 孔号ID
     * @return
     */
    public TDrainageParameter getByBoreholeId(Integer boreholeId);

    /***
     * 根据巷道ID，钻孔用途，组号，查询给定阶段的浓度
     * @param roadwayId 巷道ID
     * @param purposeboreholeid 钻孔用途ID
     * @param groupNo 组号ID
     * @param boreholeNo 孔号
     * @param low 小值
     * @param height 大值
     * @return 列表
     */
    public List<TDrainageParameter> getTGasExtractionAnalyze(
            @Param("roadwayId")List<Integer> roadwayId,
            @Param("purposeboreholeid")List<Integer> purposeboreholeid,
            @Param("groupNo")List<String> groupNo,
            @Param("boreholeNo")List<Integer> boreholeNo,
            @Param("low") Integer low,
            @Param("height") Integer height);

    /**
     * 查询钻孔始抽效果分析
     * @param roadwayId 巷道ID
     * @param purposeboreholeid 钻孔用途ID
     * @param groupNo 组号ID
     * @param boreholeNo 孔号
     * @param low 小值
     * @param height 大值
     * @return 列表
     */
    public List<TDrainageParameter> getGasAnalyzeBegin(
            @Param("roadwayId")List<Integer> roadwayId,
            @Param("purposeboreholeid")List<Integer> purposeboreholeid,
            @Param("groupNo")List<String> groupNo,
            @Param("boreholeNo")List<Integer> boreholeNo,
            @Param("low") Integer low,
            @Param("height") Integer height);

    /**
     * 查询未录入钻孔抽采参数测量的钻孔
     * @return
     */
    public List<TActualBorehole> getBoreholeInfoNotRecord();

    /**
     * 根据钻孔ID查询始抽浓度测量日期
     * @param boreholeid 钻孔ID
     * @return
     */
    public Date getBeginningcondate(Integer boreholeid);

    /**
     * 根据钻孔ID查询始抽浓度记录的ID
     * @param boreholeid 钻孔ID
     * @return ID
     */
    public Integer getIdByBoreholeid(Integer boreholeid);

}
