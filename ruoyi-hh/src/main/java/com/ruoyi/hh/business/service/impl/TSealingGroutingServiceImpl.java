package com.ruoyi.hh.business.service.impl;

import com.ruoyi.hh.business.domain.TDrainageParameter;
import com.ruoyi.hh.business.domain.TSealingGrouting;
import com.ruoyi.hh.business.mapper.TDrainageParameterMapper;
import com.ruoyi.hh.business.mapper.TSealingGroutingMapper;
import com.ruoyi.hh.business.service.ITSealingGroutingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 封孔注浆信息Service业务层处理
 *
 * @author eleven
 * @date 2022-07-21
 */
@Service
public class TSealingGroutingServiceImpl implements ITSealingGroutingService {
    @Resource
    private TSealingGroutingMapper tSealingGroutingMapper;
    @Resource
    private TDrainageParameterMapper tDrainageParameterMapper;

    /**
     * 查询封孔注浆信息
     *
     * @param id 封孔注浆信息主键
     * @return 封孔注浆信息
     */
    @Override
    public TSealingGrouting selectTSealingGroutingById(Integer id) {
        return tSealingGroutingMapper.selectTSealingGroutingById(id);
    }

    /**
     * 查询封孔注浆信息列表
     *
     * @param tSealingGrouting 封孔注浆信息
     * @return 封孔注浆信息
     */
    @Override
    public List<TSealingGrouting> selectTSealingGroutingList(TSealingGrouting tSealingGrouting) {
        return tSealingGroutingMapper.selectTSealingGroutingList(tSealingGrouting);
    }

    /**
     * 新增封孔注浆信息
     *
     * @param tSealingGrouting 封孔注浆信息
     * @return 结果
     */
    @Override
    @Transactional
    public Integer insertTSealingGrouting(TSealingGrouting tSealingGrouting) {
        /*TSealingGrouting tGrouting=new TSealingGrouting();
        tGrouting.setBoreholeid(tSealingGrouting.getBoreholeid());
        if(this.tSealingGroutingMapper.selectTSealingGroutingList(tGrouting).size()>0){
            return -2;
        }     注浆记录表中不允许出现重复数据*/
        TDrainageParameter tDrainageParameter=new TDrainageParameter();
//        tDrainageParameter.setBoreholeid(tSealingGrouting.getBoreholeid());  //设置孔号ID
        tDrainageParameter.setBeginningcon(1);   //设置始抽值
        List<TDrainageParameter> list=this.tDrainageParameterMapper.selectTDrainageParameterList(tDrainageParameter);
        tDrainageParameter.setGasConcentration(tSealingGrouting.getStartDrainageGasConcentration());  //始抽浓度
        tDrainageParameter.setNegativePressure(tSealingGrouting.getStartDrainagePressure()); //始抽负压
        tDrainageParameter.setMeasurer(tSealingGrouting.getStartdrainageperson());  //始抽浓度检测人
        tDrainageParameter.setMeasureDate(tSealingGrouting.getStartDrainageDate()); //检测日期
        tDrainageParameter.setBeginningconDate(tSealingGrouting.getStartDrainageDate());  //始抽日期
        if(!list.isEmpty()){
            tDrainageParameter.setId(list.get(0).getId());
            this.tDrainageParameterMapper.updateTDrainageParameter(tDrainageParameter);

        }else{
            this.tDrainageParameterMapper.insertTDrainageParameter(tDrainageParameter);
        }
        return tSealingGroutingMapper.insertTSealingGrouting(tSealingGrouting);
    }

    /**
     * 修改封孔注浆信息
     *
     * @param tSealingGrouting 封孔注浆信息
     * @return 结果
     */
    @Override
    public Integer updateTSealingGrouting(TSealingGrouting tSealingGrouting) {
        TSealingGrouting tGrouting=new TSealingGrouting();
        tGrouting.setBoreholeId(tSealingGrouting.getBoreholeId());
        List<TSealingGrouting> list=new ArrayList<>();
        list=this.tSealingGroutingMapper.selectTSealingGroutingList(tGrouting);
        if(list.size()>1){
            return 2;
        }else if(list.size()==1&&list.get(0).getId()!=tSealingGrouting.getId()){
            return -2;
        }else{
            return tSealingGroutingMapper.updateTSealingGrouting(tSealingGrouting);
        }
    }

    /**
     * 批量删除封孔注浆信息
     *
     * @param ids 需要删除的封孔注浆信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTSealingGroutingByIds(List<Integer> ids) {
        return tSealingGroutingMapper.deleteTSealingGroutingByIds(ids);
    }

    /**
     * 删除封孔注浆信息信息
     *
     * @param id 封孔注浆信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTSealingGroutingById(Integer id) {
        return tSealingGroutingMapper.deleteTSealingGroutingById(id);
    }

    /**
     * 获取所有信息
     *
     * @return 列表
     */
    @Override
    public List<TSealingGrouting> getPageList() {
        return tSealingGroutingMapper.getPageList();
    }

    /**
     * 封孔注浆记录
     *
     * @param groutingdate   注浆日期
     * @param groutingperson 注浆人
     * @param roadwayId      巷道ID
     * @param shiftid        班次ID
     * @return 列表
     */
    @Override
    public List<TSealingGrouting> getSealingGroutingRecord(
             Date groutingdate,
             List<Integer> roadwayId,
             List<String> groupNo,
             List<Integer> boreholeNo,
             List<String> groutingperson,
             Integer shiftid){
        return this.tSealingGroutingMapper.getSealingGroutingRecord(groutingdate, roadwayId, groupNo, boreholeNo, groutingperson, shiftid);
    }

    /**
     * 根据钻孔ID删除注浆信息
     * @param boreholeId 钻孔ID
     * @return 结果
     */
    @Override
    public Integer delByBoreholeId(Integer boreholeId) {
        return this.tSealingGroutingMapper.delByBoreholeId(boreholeId);
    }

    @Override
    public Integer delByBoreholeIds(List<Integer> boreholeIds) {
        return this.tSealingGroutingMapper.delByBoreholeIds(boreholeIds);
    }
}
