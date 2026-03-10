package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.hh.physical.domain.TDrainageUnit;
import com.ruoyi.hh.physical.domain.TStationInfo;
import com.ruoyi.hh.physical.mapper.TDrainageUnitMapper;
import com.ruoyi.hh.physical.mapper.TMeasuringPointMapper;
import com.ruoyi.hh.physical.mapper.TStationInfoMapper;
import com.ruoyi.hh.physical.service.ITDrainageUnitService;
import com.ruoyi.hh.simulate.domain.DrainageUnitTool;
import com.ruoyi.hh.simulate.domain.DrainageUnitTotal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 评价单元信息Service业务层处理
 *
 * @author eleven
 * @date 2022-07-16
 */
@Service
public class TDrainageUnitServiceImpl implements ITDrainageUnitService {
    @Resource
    private TDrainageUnitMapper tDrainageUnitMapper;
    @Resource
    private TMeasuringPointMapper tMeasuringPointMapper;
    @Resource
    private TStationInfoMapper tStationInfoMapper;

    @Resource
    private RedisCache redisCache;

    /**
     * 查询评价单元信息
     *
     * @param id 评价单元信息主键
     * @return 评价单元信息
     */
    @Override
    public TDrainageUnit selectTDrainageUnitById(Integer id) {
        TDrainageUnit tDrainageUnit=tDrainageUnitMapper.selectTDrainageUnitById(id);
        List<TStationInfo> list=new ArrayList<>();
        list=  tStationInfoMapper.getByDrainageUnitId(id);
        tDrainageUnit.setTStationInfoList(list);
        return tDrainageUnit;
    }


    /**
     * 查询评价单元信息列表
     *
     * @param tDrainageUnit 评价单元信息
     * @return 评价单元信息
     */
    @Override
    public List<TDrainageUnit> selectTDrainageUnitList(TDrainageUnit tDrainageUnit) {
        List<TDrainageUnit> list = new ArrayList<>();
        list = tDrainageUnitMapper.selectTDrainageUnitList(tDrainageUnit);
        /**
         * 废弃原模拟数据
         */
//        if (list != null) {
//            for (TDrainageUnit tD : list) {
//                long totalmixtureflow = 0; //累计混合流量
//                long totalpureflow = 0; //累计纯流量
//                List<TMeasuringPoint> tMeasuringPoints = new ArrayList<>();
//                tMeasuringPoints = tMeasuringPointMapper.getByDrainageUnitId(tD.getId());
//                for (TMeasuringPoint t : tMeasuringPoints) {
//                    if (redisCache.getCacheObject(t.getMeasuringpointname()) != null) {
//                        SimulationMeasurepointData s = (SimulationMeasurepointData) redisCache.getCacheObject(t.getMeasuringpointname());
//                        t.setTotalmixtureflow(s.getTotalmixtureflow());
//                        totalmixtureflow+=s.getTotalmixtureflow();
//                        t.setTotalpureflow(s.getTotalpureflow());
//                        totalpureflow+=s.getTotalpureflow();
//                    }
//                    tD.setTMeasuringPointList(tMeasuringPoints);
//                    tD.setTotalmixtureflow(totalmixtureflow);
//                    tD.setTotalpureflow(totalpureflow);
//                    tD.setGastotalreservesresidue(tD.getGastotalreserves()*10000-totalpureflow);
//                }
//            }
            return list;
//        }
//        return null;
    }

    /**
     * 新增评价单元信息
     *
     * @param tDrainageUnit 评价单元信息
     * @return 结果
     */
    @Override
    public Integer insertTDrainageUnit(TDrainageUnit tDrainageUnit) {
        return tDrainageUnitMapper.insertTDrainageUnit(tDrainageUnit);
    }

    /**
     * 修改评价单元信息
     *
     * @param tDrainageUnit 评价单元信息
     * @return 结果
     */
    @Override
    public Integer updateTDrainageUnit(TDrainageUnit tDrainageUnit) {
        return tDrainageUnitMapper.updateTDrainageUnit(tDrainageUnit);
    }

    /**
     * 批量删除评价单元信息
     *
     * @param ids 需要删除的评价单元信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTDrainageUnitByIds(List<Integer> ids) {
        return tDrainageUnitMapper.deleteTDrainageUnitByIds(ids);
    }

    /**
     * 删除评价单元信息信息
     * 删除评价单元，并解除匹配的测点列表
     * @param id 评价单元信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTDrainageUnitById(Integer id) {
//        //根据评价单元Id查询关联的测点列表
//        List<TMeasuringPoint> measuringPoints=new ArrayList<>();
//        measuringPoints=this.tMeasuringPointService.getByEvaluationUnitId(id);
//        //删除评价前接触关联的测点联系
//        for(TMeasuringPoint measuringPoint:measuringPoints){
//            measuringPoint.setEvaluationid(null);
//            this.tMeasuringPointService.updateTMeasuringPoint(measuringPoint);
//        }
        return tDrainageUnitMapper.deleteTDrainageUnitById(id);
    }

//    /**
//     * 分页获取评价单元列表
//     *
//     * @return 列表
//     */
//    public List<TDrainageUnit> getPageList() {
//
//        List<TDrainageUnit> list=new ArrayList<>();
//        list= this.tDrainageUnitMapper.getPageList();
//        if(list.size()>0){
//            for(TDrainageUnit tDrainageUnit:list){
//                List<TMeasuringPoint> tMeasuringPoints=new ArrayList<>();
//                tMeasuringPoints.addAll(this.tMeasuringPointMapper.getByDrainageUnitId(tDrainageUnit.getId()));
//                tDrainageUnit.setTMeasuringPointList(tMeasuringPoints);
//            }
//        }
//        return list;
//    }

    /**
     * 根据工作面ID查询抽采单元列表
     * @param workfaceId 工作面ID
     * @return 抽采单元列表
     */
    @Override
    public List<TDrainageUnit> getByWorkfaceId(Integer workfaceId) {
        return this.tDrainageUnitMapper.getByWorkfaceId(workfaceId);
    }

    /**
     * 获取抽采单元抽采累计流量
     */
    public List <DrainageUnitTotal> getDrainageUnitTotal(Integer workfaceId){
        List<DrainageUnitTool> list=this.tDrainageUnitMapper.getDrainageUnitTotal(workfaceId);
        List <DrainageUnitTotal> drainageUnitTotals=new ArrayList<>();
        if(list!=null){
            for(DrainageUnitTool drainageUnitTool:list){
                DrainageUnitTotal drainageUnitTotal=new DrainageUnitTotal();
                drainageUnitTotal.setWorkfacename(drainageUnitTool.getWorkfacename());
                drainageUnitTotal.setDrainageunitname(drainageUnitTool.getUnitname());

            }
        }
        return null;
    }

    @Override
    public List<TDrainageUnit> getByEvaluateUnitId(Integer evaluationUnitId) {
        return this.tDrainageUnitMapper.getByEvaluateUnitId(evaluationUnitId);
    }


}
