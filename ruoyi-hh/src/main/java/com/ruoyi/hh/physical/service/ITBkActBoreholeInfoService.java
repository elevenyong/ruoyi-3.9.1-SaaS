package com.ruoyi.hh.physical.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.business.domain.TGasDrillingStandingBook;
import com.ruoyi.hh.demo.domain.TActualBorholeException;
import com.ruoyi.hh.physical.domain.TBKActBoreholeGeneralized;
import com.ruoyi.hh.physical.domain.TBKFlushingStandingBook;
import com.ruoyi.hh.physical.domain.TBkActBoreholeInfo;
import com.ruoyi.hh.util.Result;

import java.util.Date;
import java.util.List;

/**
 * 钻孔验收单Service接口
 *
 * @author eleven
 * @date 2023-05-27
 */
public interface ITBkActBoreholeInfoService {


    List<TBkActBoreholeInfo> selectAdvanceExplorationDrillByRoadwayId(Integer roadwayId);

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
    public Result insertTBkActBoreholeInfo(TBkActBoreholeInfo tBkActBoreholeInfo);

    AjaxResult insertBatchBkActBorehole(List<TBkActBoreholeInfo> list);

    /**
     * 修改钻孔验收单
     *
     * @param tBkActBoreholeInfo 钻孔验收单
     * @return 结果
     */
    public int updateTBkActBoreholeInfo(TBkActBoreholeInfo tBkActBoreholeInfo);

    /**
     * 批量删除钻孔验收单
     *
     * @param ids 需要删除的钻孔验收单主键集合
     * @return 结果
     */
    public int deleteTBkActBoreholeInfoByIds(Integer[] ids);

    /**
     * 删除钻孔验收单信息
     *
     * @param id 钻孔验收单主键
     * @return 结果
     */
    public int deleteTBkActBoreholeInfoById(Integer id);


    /**
     * 根据巷道ID，组号，孔号查询ID
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号
     * @param boreholeId 孔号
     * @return ID
     */
    public Integer getId(Integer roadwayId,
                         String groupNo,
                         String boreholeId);

    /**
     * 水力冲孔台帐
     *
     * @param roadwayId                巷道ID
     * @param groupNo                  组号ID
     * @param drillinglocation         钻孔位置
     * @param boreholeNo               孔号
     * @param actualcoalamountflushed1 冲孔煤量1
     * @param actualcoalamountflushed2 冲孔煤量2
     * @param terminalholedate1        终孔时间1
     * @param terminalholedate2        终孔时间2
     * @return 列表
     */
    public List<TBKFlushingStandingBook> getTBKFlushingStandingBook(List<Integer> roadwayId,
                                                                    String groupNo,
                                                                    List<String> drillinglocation,
                                                                    List<Integer> boreholeNo,
                                                                    Double actualcoalamountflushed1,
                                                                    Double actualcoalamountflushed2,
                                                                    Date terminalholedate1,
                                                                    Date terminalholedate2
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
     * @param startdate
     * @param enddate
     * @return
     * @CreateTime: 2023/7/17 14:58
     */
    public List<TGasDrillingStandingBook> getActBoreholeInfo(Integer roadwayId,
                                                             List<String> groupNo,
                                                             List<Integer> purposeBoreholeId,
                                                             List<Integer> boreholeNo,
                                                             Date startdate,
                                                             Date enddate);

    /**
     * 根据巷道名称、组号、孔号查询实钻ID
     *
     * @param roadway    巷道名称
     * @param groupNo    组号
     * @param boreholeNO 孔号
     * @return 实钻ID
     */
    public Integer getIdByRoadWayAndGroupIdBoreholeno(String roadway,
                                                      String groupNo,
                                                      String boreholeNO);

    public Integer delByRoadwayZoneIds(List<Integer> roadwayZoneIds);

    AjaxResult insertAdvanceExpDrillList(Integer roadwayId, List<TBkActBoreholeInfo> list);

    public List<TBkActBoreholeInfo> getNormalActBoreholeInfoByRoadwayId(Integer roadwayId);

    Integer getNormalBoreholeCount(Integer type, Integer roadwayId);
}
