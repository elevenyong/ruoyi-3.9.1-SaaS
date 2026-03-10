package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.business.domain.Punchingledger;
import com.ruoyi.hh.physical.domain.TActualFlushing;
import com.ruoyi.hh.simulate.domain.FlushingStandingBook;
import com.ruoyi.hh.simulate.domain.FlushingStatistics;
import com.ruoyi.hh.simulate.domain.StatisticsBook;

import java.util.Date;
import java.util.List;

/**
 * 实际冲孔信息Service接口
 *
 * @author eleven
 * @date 2022-07-19
 */
public interface ITActualFlushingService {
    /**
     * 查询实际冲孔信息
     *
     * @param id 实际冲孔信息主键
     * @return 实际冲孔信息
     */
    public TActualFlushing selectTActualFlushingById(Integer id);

    /**
     * 查询实际冲孔信息列表
     *
     * @param tActualFlushing 实际冲孔信息
     * @return 实际冲孔信息集合
     */
    public List<TActualFlushing> selectTActualFlushingList(TActualFlushing tActualFlushing);

    /**
     * 新增实际冲孔信息
     *
     * @param tActualFlushing 实际冲孔信息
     * @return 结果
     */
    public Integer insertTActualFlushing(TActualFlushing tActualFlushing);

    /**
     * 修改实际冲孔信息
     *
     * @param tActualFlushing 实际冲孔信息
     * @return 结果
     */
    public Integer updateTActualFlushing(TActualFlushing tActualFlushing);

    /**
     * 批量删除实际冲孔信息
     *
     * @param ids 需要删除的实际冲孔信息主键集合
     * @return 结果
     */
    public Integer deleteTActualFlushingByIds(List<Integer> ids);

    /**
     * 删除实际冲孔信息信息
     *
     * @param id 实际冲孔信息主键
     * @return 结果
     */
    public Integer deleteTActualFlushingById(Integer id);

    /**
     * 钻孔信息和冲孔信息联查
     *
     * @return 列表
     */
    public List<TActualFlushing> getAndBroeholeInfo();

    /**
     * 按照冲孔时间和巷道信息查询水力冲孔台账信息
     *
     * @param roadwayId 巷道ID
     * @param startdate 开始时间
     * @param enddate   结束时间
     * @return 列表
     */
    public List<FlushingStandingBook> getFlushingStandingBook(List<Integer> roadwayId,
                                                              List<String> groupNo,
                                                              List<Integer> boreholeNo,
                                                              Date startdate, Date enddate);

    /**
     * 根据钻孔时间和巷道信息查询冲煤量完成情况
     *
     * @param roadwayId 巷道ID
     * @param startdate 开始时间
     * @param enddate   结束时间
     * @return 列表
     */
    public List<FlushingStatistics> getFlushingStatistics(List<Integer> roadwayId,
                                                          List<String> groupNo,
                                                          List<Integer> boreholeNo,
                                                          Date startdate, Date enddate);

    /**
     * 根据冲孔时间和巷道查询列表
     *
     * @param roadwayId 巷道列表
     * @param startdate 开始时间
     * @param enddate   结束时间
     * @return 列表
     */
    public List<TActualFlushing> getFlushingInfoAndOther(List<Integer> roadwayId,
                                                         List<String> groupNo,
                                                         List<Integer> boreholeNo,
                                                         Date startdate, Date enddate);

    /**
     * 根据孔号和验收时间查询列表（冲孔验收单）
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号ID
     * @param boreholeNo 孔号
     * @param startdate  开始时间
     * @param enddate    结束时间
     * @return 实体
     */
    public List<TActualFlushing> getAcceptBills( List<Integer> roadwayId,
                                                 List<String> groupNo,
                                                 List<Integer> boreholeNo,
                                                 Date startdate,
                                                 Date enddate);

    /**
     * 水力冲孔统计台账
     *
     * @param roadwayId 巷道ID
     * @param startdate 开始时间
     * @param enddate   结束时间
     * @return 列表
     */
    public List<StatisticsBook> getStatisticsBook(
             List<Integer> roadwayId,
             List<String> groupNo,
             List<Integer> boreholeNo,
             Date startdate,
             Date enddate);

    /**
     * 根据钻孔ID删除冲孔信息
     * @param boreholeIds 钻孔ID
     * @return 结果
     */
    public Integer delByBoreholeIds(List<Integer> boreholeIds);
    public Integer delByBoreholeId(Integer boreholeId);
    public List<Punchingledger> PunchingledgerForExcel();
}
