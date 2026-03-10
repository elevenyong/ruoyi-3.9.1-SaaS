package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.business.domain.Punchingledger;
import com.ruoyi.hh.physical.domain.TActualFlushing;
import com.ruoyi.hh.physical.mapper.TActualFlushingMapper;
import com.ruoyi.hh.physical.service.ITActualFlushingService;
import com.ruoyi.hh.simulate.domain.FlushingStandingBook;
import com.ruoyi.hh.simulate.domain.FlushingStatistics;
import com.ruoyi.hh.simulate.domain.StatisticsBook;
import com.ruoyi.hh.util.mapper.TShiftinfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 实际冲孔信息Service业务层处理
 *
 * @author eleven
 * @date 2022-07-19
 */
@Service
public class TActualFlushingServiceImpl implements ITActualFlushingService {
    @Resource
    private TActualFlushingMapper tActualFlushingMapper;
    @Resource
    private TShiftinfoMapper tShiftinfoMapper;

    /**
     * 查询实际冲孔信息
     *
     * @param id 实际冲孔信息主键
     * @return 实际冲孔信息
     */
    @Override
    public TActualFlushing selectTActualFlushingById(Integer id) {
        return tActualFlushingMapper.selectTActualFlushingById(id);
    }

    /**
     * 查询实际冲孔信息列表
     *
     * @param tActualFlushing 实际冲孔信息
     * @return 实际冲孔信息
     */
    @Override
    public List<TActualFlushing> selectTActualFlushingList(TActualFlushing tActualFlushing) {
        return tActualFlushingMapper.selectTActualFlushingList(tActualFlushing);
    }

    /**
     * 新增实际冲孔信息
     *
     * @param tActualFlushing 实际冲孔信息
     * @return 结果
     */
    @Override
    public Integer insertTActualFlushing(TActualFlushing tActualFlushing) {
       /*孔号不能重复  TActualFlushing t = new TActualFlushing();
        t.setBoreholeid(tActualFlushing.getBoreholeid());
        List<TActualFlushing> list = this.tActualFlushingMapper.selectTActualFlushingList(t);
        if (list.size() == 0) {
            return tActualFlushingMapper.insertTActualFlushing(tActualFlushing);
        } else {
            return -1;
        }*/
        if(tActualFlushing.getShiftId()==null&&tActualFlushing.getShiftinfo()!=null){
            tActualFlushing.setShiftId(tShiftinfoMapper.getIdByName(tActualFlushing.getShiftinfo()));
        }
        return this.tActualFlushingMapper.insertTActualFlushing(tActualFlushing);

    }

    /**
     * 修改实际冲孔信息
     *
     * @param tActualFlushing 实际冲孔信息
     * @return 结果
     */
    @Override
    public Integer updateTActualFlushing(TActualFlushing tActualFlushing) {
        /*修改孔号不能重复 TActualFlushing t = new TActualFlushing();
        t.setBoreholeid(tActualFlushing.getBoreholeid());
        List<TActualFlushing> list=new ArrayList<>();
        list = this.tActualFlushingMapper.selectTActualFlushingList(t);
        if(list.size()>1){
            return 2;
        }else if(list.size()==1&&list.get(0).getId()!=tActualFlushing.getId()){
            return -2;
        }else{
            return tActualFlushingMapper.updateTActualFlushing(tActualFlushing);
        }*/
        return this.tActualFlushingMapper.updateTActualFlushing(tActualFlushing);
    }

    /**
     * 批量删除实际冲孔信息
     *
     * @param ids 需要删除的实际冲孔信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTActualFlushingByIds(List<Integer> ids) {
        return tActualFlushingMapper.deleteTActualFlushingByIds(ids);
    }

    /**
     * 删除实际冲孔信息信息
     *
     * @param id 实际冲孔信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTActualFlushingById(Integer id) {
        return tActualFlushingMapper.deleteTActualFlushingById(id);
    }

    /**
     * 联查钻孔信息数据
     *
     * @return 列表
     */
    @Override
    public List<TActualFlushing> getAndBroeholeInfo() {
        return this.tActualFlushingMapper.getAndBroeholeInfo();
    }

    @Override
    public List<FlushingStandingBook> getFlushingStandingBook(List<Integer> roadwayId,
                                                              List<String> groupNo,
                                                              List<Integer> boreholeNo,
                                                              Date startdate, Date enddate) {
        return this.tActualFlushingMapper.getFlushingStandingBook(roadwayId,groupNo,boreholeNo, startdate, enddate);
    }

    @Override
    public List<FlushingStatistics> getFlushingStatistics(List<Integer> roadwayId,
                                                          List<String> groupNo,
                                                          List<Integer> boreholeNo,
                                                          Date startdate, Date enddate) {
        return this.tActualFlushingMapper.getFlushingStatistics(roadwayId,groupNo,boreholeNo, startdate, enddate);
    }

    @Override
    public List<TActualFlushing> getFlushingInfoAndOther(List<Integer> roadwayId,
                                                         List<String> groupNo,
                                                         List<Integer> boreholeNo,
                                                         Date startdate, Date enddate) {
        return this.tActualFlushingMapper.getFlushingInfoAndOther(roadwayId,groupNo,boreholeNo, startdate, enddate);
    }

    @Override
    public List<TActualFlushing> getAcceptBills(List<Integer> roadwayId,
                                                List<String> groupNo,
                                                List<Integer> boreholeNo,
                                                Date startdate, Date enddate) {
        return this.tActualFlushingMapper.getAcceptBills(roadwayId, groupNo, boreholeNo, startdate, enddate);
    }

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
            Date enddate){
        return this.tActualFlushingMapper.getStatisticsBook(roadwayId, groupNo, boreholeNo, startdate, enddate);
    }

    /**
     * 根据钻孔ID删除冲孔信息
     * @param boreholeIds 钻孔ID
     * @return 结果
     */
    @Override
    public Integer delByBoreholeIds(List<Integer> boreholeIds) {
//        return this.tActualFlushingMapper.delByBoreholeIds(boreholeIds);
        return 0;
    }

    @Override
    public Integer delByBoreholeId(Integer boreholeId) {
        return this.tActualFlushingMapper.delByBoreholeId(boreholeId);
    }

    @Override
    public List<Punchingledger> PunchingledgerForExcel() {
        return this.tActualFlushingMapper.PunchingledgerForExcel();
    }


}
