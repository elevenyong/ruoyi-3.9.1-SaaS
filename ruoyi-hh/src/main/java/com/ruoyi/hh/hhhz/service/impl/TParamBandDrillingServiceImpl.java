package com.ruoyi.hh.hhhz.service.impl;

import com.ruoyi.hh.hhhz.domain.param.TParamBandDrilling;
import com.ruoyi.hh.hhhz.mapper.TParamBandDrillingMapper;
import com.ruoyi.hh.hhhz.service.ITParamBandDrillingService;
import com.ruoyi.hh.physical.domain.TCoalinfo;
import com.ruoyi.hh.physical.domain.TRoadway;
import com.ruoyi.hh.physical.domain.TWorkingFace;
import com.ruoyi.hh.physical.mapper.TCoalinfoMapper;
import com.ruoyi.hh.physical.mapper.TRoadwayMapper;
import com.ruoyi.hh.physical.mapper.TWorkingFaceMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 钻孔设计历史记录Service业务层处理
 *
 * @author hhhz
 * @date 2023-04-24
 */
@Service
public class TParamBandDrillingServiceImpl implements ITParamBandDrillingService {
    @Resource
    private TParamBandDrillingMapper tParamBandDrillingMapper;

    @Resource
    private TCoalinfoMapper coalinfoMapper;
    @Resource
    private TRoadwayMapper roadwayMapper;
    @Resource
    private TWorkingFaceMapper workingFaceMapper;

    /**
     * 查询钻孔设计历史记录
     *
     * @param id 钻孔设计历史记录主键
     * @return 钻孔设计历史记录
     */
    @Override
    public TParamBandDrilling selectTParamBandDrillingById(Integer id) {
        TParamBandDrilling drilling = tParamBandDrillingMapper.selectTParamBandDrillingById(id);
        TWorkingFace workingFace = workingFaceMapper.getInfoById(drilling.getWorkingFaceId());
        TCoalinfo coalinfo = coalinfoMapper.selectTCoalinfoById(workingFace.getCoalId());
        TRoadway roadway = roadwayMapper.selectTRoadwayById(drilling.getRoadId());
        drilling.setWorkingFace(workingFace);
        drilling.setCoalinfo(coalinfo);
        drilling.setRoadway(roadway);

        return drilling;
    }

    /**
     * 查询钻孔设计历史记录列表
     *
     * @param tParamBandDrilling 钻孔设计历史记录
     * @return 钻孔设计历史记录
     */
    @Override
    public List<TParamBandDrilling> selectTParamBandDrillingList(@RequestBody TParamBandDrilling tParamBandDrilling) {
        return tParamBandDrillingMapper.selectTParamBandDrillingList(tParamBandDrilling);
    }

    /**
     * 新增钻孔设计历史记录
     *
     * @param tParamBandDrilling 钻孔设计历史记录
     * @return 结果
     */
    @Override
    public int insertTParamBandDrilling(TParamBandDrilling tParamBandDrilling) {
        return tParamBandDrillingMapper.insertTParamBandDrilling(tParamBandDrilling);
    }

    /**
     * 修改钻孔设计历史记录
     *
     * @param tParamBandDrilling 钻孔设计历史记录
     * @return 结果
     */
    @Override
    public int updateTParamBandDrilling(TParamBandDrilling tParamBandDrilling) {
        return tParamBandDrillingMapper.updateTParamBandDrilling(tParamBandDrilling);
    }

    /**
     * 批量删除钻孔设计历史记录
     *
     * @param ids 需要删除的钻孔设计历史记录主键
     * @return 结果
     */
    @Override
    public int deleteTParamBandDrillingByIds(Integer[] ids) {
        return tParamBandDrillingMapper.deleteTParamBandDrillingByIds(ids);
    }

    /**
     * 删除钻孔设计历史记录信息
     *
     * @param id 钻孔设计历史记录主键
     * @return 结果
     */
    @Override
    public int deleteTParamBandDrillingById(Integer id) {
        return tParamBandDrillingMapper.deleteTParamBandDrillingById(id);
    }

    /**
     * 获取最新一条数据
     * @return 结果
     */
    public TParamBandDrilling getLastOne(){
        return this.tParamBandDrillingMapper.getLastOne();
    }

    /**
     * 更改参数的标识
     * @param ids 列表
     */
    @Override
    public void updateSign(List<Integer> ids){
        this.tParamBandDrillingMapper.updateSign(ids);
    }

}
