package com.ruoyi.hh.business.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.business.domain.TDrainageParameter;
import com.ruoyi.hh.business.mapper.TDrainageParameterMapper;
import com.ruoyi.hh.business.service.ITDrainageParameterService;
import com.ruoyi.hh.physical.domain.TActualBorehole;
import com.ruoyi.hh.physical.mapper.TRoadwayMapper;
import com.ruoyi.hh.physical.service.ITBkActBoreholeInfoService;
import com.ruoyi.hh.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 钻孔抽采参数测量Service业务层处理
 * 
 * @author eleven
 * @date 2022-07-30
 */
@Service
public class TDrainageParameterServiceImpl implements ITDrainageParameterService
{
    @Resource
    private TDrainageParameterMapper tDrainageParameterMapper;
    @Resource
    private TRoadwayMapper tRoadwayMapper;
    @Resource
    private ITBkActBoreholeInfoService tTBActualBoreholeService;

    /**
     * 查询钻孔抽采参数测量
     * 
     * @param id 钻孔抽采参数测量主键
     * @return 钻孔抽采参数测量
     */
    @Override
    public TDrainageParameter selectTDrainageParameterById(Integer id)
    {
        return tDrainageParameterMapper.selectTDrainageParameterById(id);
    }

    /**
     * 查询钻孔抽采参数测量列表
     * 
     * @param tDrainageParameter 钻孔抽采参数测量
     * @return 钻孔抽采参数测量
     */
    @Override
    public List<TDrainageParameter> selectTDrainageParameterList(TDrainageParameter tDrainageParameter)
    {
        List<TDrainageParameter> list=tDrainageParameterMapper.selectTDrainageParameterList(tDrainageParameter);
        if(list.size()>0){
            List<TDrainageParameter> resList=new ArrayList<>();
            for(TDrainageParameter tDrainageParameter1:list){
                if(tDrainageParameter1.getBeginningcon()==1){     //始抽值，将浓度和负压赋值到始抽浓度和始抽负压
                    tDrainageParameter1.setBeginConcentration(tDrainageParameter1.getGasConcentration());
                    tDrainageParameter1.setBeginPressure(tDrainageParameter1.getNegativePressure());
                    tDrainageParameter1.setBeginningconDate(tDrainageParameter1.getMeasureDate());
                }else{
                    TDrainageParameter t=new TDrainageParameter();
                    t.setBeginningcon(1);
                    t.setBoreholeNo(tDrainageParameter1.getBoreholeNo());
                    List<TDrainageParameter> listBegin=this.tDrainageParameterMapper.selectTDrainageParameterList(t);
                    tDrainageParameter1.setBeginConcentration(listBegin.get(0).getGasConcentration());
                    tDrainageParameter1.setBeginPressure(listBegin.get(0).getNegativePressure());
                    tDrainageParameter1.setBeginningconDate(listBegin.get(0).getMeasureDate());
                }
                resList.add(tDrainageParameter1);
            }
            return resList;
        }
        return list;
    }

    /**
     * 新增钻孔抽采参数测量
     * 
     * @param tDrainageParameter 钻孔抽采参数测量
     * @return 结果
     */
    @Override
    public Result insertTDrainageParameter(TDrainageParameter tDrainageParameter)
    {
//        if(checkBorehole(tDrainageParameter).getStatus()!=0){
//            return checkBorehole(tDrainageParameter);
//        }
//        Integer boreholeId=(Integer)checkBorehole(tDrainageParameter).getData();
//        tDrainageParameter.setBoreholeid(boreholeId);
//        if(tDrainageParameterMapper.getByBoreholeId(boreholeId)!=null){
//            tDrainageParameter.setId(tDrainageParameterMapper.getByBoreholeId(boreholeId).getId());
//            tDrainageParameterMapper.updateTDrainageParameter(tDrainageParameter);
//            return Result.buildResult(Result.Status.OK);
//        }
        //判断是否为始抽参数
        Integer r=0;
        if(tDrainageParameter.getBeginningcon()!=null){
            if(tDrainageParameter.getBeginningcon()==1){
                tDrainageParameter.setBeginningconDate(tDrainageParameter.getMeasureDate());
                if(this.tDrainageParameterMapper.getIdByBoreholeid(tDrainageParameter.getId())!=null){
                    tDrainageParameter.setId(this.tDrainageParameterMapper.getIdByBoreholeid(tDrainageParameter.getId()));
                    r=tDrainageParameterMapper.updateTDrainageParameter(tDrainageParameter);
                }else{
                    r = tDrainageParameterMapper.insertTDrainageParameter(tDrainageParameter);
                }
            }else{
                tDrainageParameter.setBeginningconDate(this.tDrainageParameterMapper.getBeginningcondate(tDrainageParameter.getId()));
                r = tDrainageParameterMapper.insertTDrainageParameter(tDrainageParameter);
            }
            if(r>0){
                return Result.buildResult(Result.Status.OK);
            }else {
                return Result.buildResult(Result.Status.ERROR);
            }
        }else{
            return Result.buildResult(Result.Status.NULL_ERROR);
        }
    }

    /**
     * 修改钻孔抽采参数测量
     * 
     * @param tDrainageParameter 钻孔抽采参数测量
     * @return 结果
     */
    @Override
    public Integer updateTDrainageParameter(TDrainageParameter tDrainageParameter)
    {
        tDrainageParameter.setUpdateTime(DateUtils.getNowDate());
        return tDrainageParameterMapper.updateTDrainageParameter(tDrainageParameter);
    }

    /**
     * 批量删除钻孔抽采参数测量
     * 
     * @param ids 需要删除的钻孔抽采参数测量主键
     * @return 结果
     */
    @Override
    public Integer deleteTDrainageParameterByIds(List<Integer> ids)
    {
        return tDrainageParameterMapper.deleteTDrainageParameterByIds(ids);
    }

    /**
     * 删除钻孔抽采参数测量信息
     * 
     * @param id 钻孔抽采参数测量主键
     * @return 结果
     */
    @Override
    public Integer deleteTDrainageParameterById(Integer id)
    {
        return tDrainageParameterMapper.deleteTDrainageParameterById(id);
    }

    @Override
    public List<TDrainageParameter> getPageList() {
        return this.tDrainageParameterMapper.getPageList();
    }

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
    public List<TDrainageParameter> getTDrainageParameter(Integer roadwayId,
                                                          List<String> groupNo,
                                                          List<Integer> purposeboreholeid,
                                                          List<Integer> boreholeNo,
                                                          Date startdate,
                                                          Date enddate){
        List<TDrainageParameter> list=this.tDrainageParameterMapper.getTDrainageParameter(roadwayId, groupNo, purposeboreholeid, boreholeNo, startdate, enddate);
        if(list.size()>0){
            List<TDrainageParameter> resList=new ArrayList<>();
            for(TDrainageParameter tDrainageParameter:list){
                if(tDrainageParameter.getBeginningcon()==1){     //始抽值，将浓度和负压赋值到始抽浓度和始抽负压
                    tDrainageParameter.setBeginConcentration(tDrainageParameter.getGasConcentration());
                    tDrainageParameter.setBeginPressure(tDrainageParameter.getNegativePressure());
                    tDrainageParameter.setBeginningconDate(tDrainageParameter.getMeasureDate());
                }else{
                    TDrainageParameter t=new TDrainageParameter();
                    t.setBeginningcon(1);
                    t.setBoreholeId(tDrainageParameter.getBoreholeId());
                    List<TDrainageParameter> listBegin=this.tDrainageParameterMapper.selectTDrainageParameterList(t);
                    tDrainageParameter.setBeginConcentration(listBegin.get(0).getGasConcentration());
                    tDrainageParameter.setBeginPressure(listBegin.get(0).getNegativePressure());
                    tDrainageParameter.setBeginningconDate(listBegin.get(0).getMeasureDate());
                }
                resList.add(tDrainageParameter);
            }
            return resList;
        }
        return list;
    }

    /**
     * 根据钻孔ID删除钻孔抽采参数测量信息
     * @param boreholeIds 钻孔ID
     * @return 结果
     */
    @Override
    public Integer delByBoreholeIds(List<Integer> boreholeIds) {
        return this.tDrainageParameterMapper.delByBoreholeIds(boreholeIds);
    }

    @Override
    public Integer delByBoreholeId(Integer boreholeId) {
        return this.tDrainageParameterMapper.delByBoreholeId(boreholeId);
    }

    @Override
    public TDrainageParameter getByBoreholeId(Integer boreholeId) {
        return this.tDrainageParameterMapper.getByBoreholeId(boreholeId);
    }

    /**
     * 检查数据
     * @param tDrainageParameter 抽采参数测量信息数据
     * @return
     */
    public Result checkBorehole(TDrainageParameter tDrainageParameter){
        if(tDrainageParameter.getRoadway()==null||tDrainageParameter.getGroupNo()==null||tDrainageParameter
        .getBoreholeNo()==null){
            return Result.buildResult(Result.Status.NULL_ERROR);
        }
        Integer roadWayId= tRoadwayMapper.getIdByRoadWayName(tDrainageParameter.getRoadway());
        if(roadWayId==0){
            return Result.buildResult(Result.Status.OTHER_ERROR);
        }
        if(tTBActualBoreholeService.getId(roadWayId,tDrainageParameter.getGroupNo(),tDrainageParameter.getBoreholeNo())!=null){
            return Result.buildResult(Result.Status.OK,tTBActualBoreholeService.getId(roadWayId,tDrainageParameter.getGroupNo(),tDrainageParameter.getBoreholeNo()));
        }else {
            return Result.buildResult(Result.Status.OTHER_ERROR);
        }
    }

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
            List<Integer> roadwayId,
            List<Integer> purposeboreholeid,
            List<String> groupNo,
            List<Integer> boreholeNo,
            Integer low,
            Integer height){
        return this.tDrainageParameterMapper.getTGasExtractionAnalyze(roadwayId, purposeboreholeid, groupNo, boreholeNo, low, height);
    }

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
            List<Integer> roadwayId,
            List<Integer> purposeboreholeid,
            List<String> groupNo,
            List<Integer> boreholeNo,
            Integer low,
            Integer height){
        return this.tDrainageParameterMapper.getGasAnalyzeBegin(roadwayId, purposeboreholeid, groupNo, boreholeNo, low, height);
    }

    /**
     * 查询未录入钻孔抽采参数测量的钻孔
     * @return 列表
     */
    public List<TActualBorehole> getBoreholeInfoNotRecord(){
        return this.tDrainageParameterMapper.getBoreholeInfoNotRecord();
    }


}
