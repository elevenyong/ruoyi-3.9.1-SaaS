package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.business.domain.TGasDrillingStandingBook;
import com.ruoyi.hh.demo.domain.TActualBorholeException;
import com.ruoyi.hh.physical.domain.TBKActBoreholeGeneralized;
import com.ruoyi.hh.physical.domain.TBKFlushingStandingBook;
import com.ruoyi.hh.physical.domain.TBkActBoreholeInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * 钻孔验收单Mapper接口
 *
 * @author eleven
 * @date 2023-05-27
 */
public interface TBkActBoreholeInfoMapper {
    /**
     * 查询钻孔验收单
     *
     * @param id 钻孔验收单主键
     * @return 钻孔验收单
     */
    public TBkActBoreholeInfo selectTBkActBoreholeInfoById(Integer id);

    /**
     * 查询钻孔验收单列表
     *
     * @param tBkActBoreholeInfo 钻孔验收单
     * @return 钻孔验收单集合
     */
    public List<TBkActBoreholeInfo> selectTBkActBoreholeInfoList(TBkActBoreholeInfo tBkActBoreholeInfo);

    /**
     * 新增钻孔验收单
     *
     * @param tBkActBoreholeInfo 钻孔验收单
     * @return 结果
     */
    public int insertTBkActBoreholeInfo(TBkActBoreholeInfo tBkActBoreholeInfo);

    /**
     * 修改钻孔验收单
     *
     * @param tBkActBoreholeInfo 钻孔验收单
     * @return 结果
     */
    public int updateTBkActBoreholeInfo(TBkActBoreholeInfo tBkActBoreholeInfo);

    /**
     * 删除钻孔验收单
     *
     * @param id 钻孔验收单主键
     * @return 结果
     */
    public int deleteTBkActBoreholeInfoById(Integer id);

    /**
     * 批量删除钻孔验收单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTBkActBoreholeInfoByIds(Integer[] ids);

    /**
     * 根据巷道ID，组号，孔号查询ID
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号
     * @param boreholeNO 孔号
     * @return ID
     */
    public Integer getId(@Param("roadwayId") Integer roadwayId,
                         @Param("groupNo") String groupNo,
                         @Param("boreholeNO") String boreholeNO);

    /**
     * 水力冲孔台帐
     *
     * @param roadwayId                巷道ID
     * @param groupNo                  组号ID
     * @param boreholeNo               孔号
     * @param actualcoalamountflushed1 冲孔煤量1
     * @param actualcoalamountflushed2 冲孔煤量2
     * @param terminalholedate1        终孔时间1
     * @param terminalholedate2        终孔时间2
     * @return 列表
     */
    public List<TBKFlushingStandingBook> getTBKFlushingStandingBook(@Param("roadwayId") List<Integer> roadwayId,
                                                                    @Param("groupNo") String groupNo,
                                                                    @Param("drillinglocation") List<String> drillinglocation,
                                                                    @Param("boreholeNo") List<Integer> boreholeNo,
                                                                    @Param("actualcoalamountflushed1") Double actualcoalamountflushed1,
                                                                    @Param("actualcoalamountflushed2") Double actualcoalamountflushed2,
                                                                    @Param("terminalholedate1") Date terminalholedate1,
                                                                    @Param("terminalholedate2") Date terminalholedate2
    );

    /**
     * 八矿实钻台帐
     *
     * @return
     */
    public List<TBKActBoreholeGeneralized> getActBoreholeBook();

    /**
     * 获取钻孔异常列表
     *
     * @return result
     */
    public List<TActualBorholeException> selectTActualBoreholeWithException();

    /**
     * 获取钻孔基础信息
     *
     * @param roadwayId
     * @param groupNo
     * @param purposeBoreholeId
     * @param boreholeNo
     * @param startDate
     * @param endDate
     * @return
     * @CreateTime: 2023/7/17 14:55
     */
    public List<TGasDrillingStandingBook> getActBoreholeInfo(@Param("roadwayId") Integer roadwayId,
                                                             @Param("groupNo") List<String> groupNo,
                                                             @Param("purposeBoreholeId") List<Integer> purposeBoreholeId,
                                                             @Param("boreholeNo") List<Integer> boreholeNo,
                                                             @Param("startdate") Date startDate,
                                                             @Param("enddate") Date endDate);

    /**
     * 根据巷道名称、组号、孔号查询实钻ID
     *
     * @param roadway    巷道名称
     * @param groupNo    组号
     * @param boreholeNo 孔号
     * @return 实钻ID
     */
    public Integer getIdByRoadWayAndGroupIdBoreholeno(@Param("roadway") String roadway,
                                                      @Param("groupNo") String groupNo,
                                                      @Param("boreholeNo") String boreholeNo);

    public Integer delByRoadwayZoneIds(List<Integer> roadwayZoneIds);

    /**
     * 根据巷道区段ID获取实钻ID列表
     *
     * @param roadwayZoneId 区段ID
     * @return 实钻ID列表
     */
    public List<Integer> getIdsRoadWayZoneId(@RequestParam Integer roadwayZoneId);

    /**
     * 获取超前探钻孔
     *
     * @param roadwayId
     * @return
     */
    public List<TBkActBoreholeInfo> selectAdvanceExplorationDrillByRoadwayId(@Param("roadwayId") Integer roadwayId);

    public List<TBkActBoreholeInfo> getByRoadwayId(@Param("roadwayId") Integer roadwayId);

    @Delete("delete from t_bk_act_borehole_info where roadway_id = #{roadwayId} and category_code = 9")
    void delAdvanceExpDrillByRoadwayId(Integer roadwayId);

    @Select("select * from t_bk_act_borehole_info where roadway_id = #{roadwayId}")
    List<TBkActBoreholeInfo> selectActBoreholeInfoByRoadwayId(Integer roadwayId);


    @Select("select count(*) from t_bk_act_borehole_info where category_code = #{categoryCode} and roadway_id = #{roadwayId}")
    Integer getNormalBoreholeCount(@Param("categoryCode") Integer categoryCode, @Param("roadwayId") Integer roadwayId);
}
