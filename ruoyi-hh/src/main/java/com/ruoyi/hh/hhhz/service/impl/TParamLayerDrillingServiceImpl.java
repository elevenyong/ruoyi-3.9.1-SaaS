package com.ruoyi.hh.hhhz.service.impl;

import com.ruoyi.hh.hhhz.domain.param.TParamLayerDrilling;
import com.ruoyi.hh.hhhz.mapper.TParamLayerDrillingMapper;
import com.ruoyi.hh.hhhz.service.ITParamLayerDrillingService;
import com.ruoyi.hh.physical.domain.TCoalinfo;
import com.ruoyi.hh.physical.domain.TRoadway;
import com.ruoyi.hh.physical.domain.TWorkingFace;
import com.ruoyi.hh.physical.mapper.TCoalinfoMapper;
import com.ruoyi.hh.physical.mapper.TRoadwayMapper;
import com.ruoyi.hh.physical.mapper.TWorkingFaceMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 顺层钻孔参数Service业务层处理
 *
 * @author hhhz
 * @date 2023-04-24
 */
@Service
public class TParamLayerDrillingServiceImpl implements ITParamLayerDrillingService {
    @Resource
    private TParamLayerDrillingMapper tParamLayerDrillingMapper;

    @Resource
    private TCoalinfoMapper coalinfoMapper;
    @Resource
    private TRoadwayMapper roadwayMapper;
    @Resource
    private TWorkingFaceMapper workingFaceMapper;


    /**
     * 查询顺层钻孔参数
     *
     * @param id 顺层钻孔参数主键
     * @return 顺层钻孔参数
     */
    @Override
    public TParamLayerDrilling selectTParamLayerDrillingById(Integer id) {
        TParamLayerDrilling drilling = tParamLayerDrillingMapper.selectTParamLayerDrillingById(id);
        TWorkingFace workingFace = workingFaceMapper.getInfoById(drilling.getWorkingFaceId());
        TCoalinfo coalinfo = coalinfoMapper.selectTCoalinfoById(workingFace.getCoalId());
        TRoadway roadway = roadwayMapper.selectTRoadwayById(drilling.getRoadId());
        drilling.setWorkingFace(workingFace);
        drilling.setCoalinfo(coalinfo);
        drilling.setRoadway(roadway);

        return drilling;
    }

    /**
     * 查询顺层钻孔参数列表
     *
     * @param tParamLayerDrilling 顺层钻孔参数
     * @return 顺层钻孔参数
     */
    @Override
    public List<TParamLayerDrilling> selectTParamLayerDrillingList(TParamLayerDrilling tParamLayerDrilling) {
        return tParamLayerDrillingMapper.selectTParamLayerDrillingList(tParamLayerDrilling);
    }

    /**
     * 新增顺层钻孔参数
     *
     * @param tParamLayerDrilling 顺层钻孔参数
     * @return 结果
     */
    @Override
    public int insertTParamLayerDrilling(TParamLayerDrilling tParamLayerDrilling) {
        return tParamLayerDrillingMapper.insertTParamLayerDrilling(tParamLayerDrilling);
    }

    /**
     * 修改顺层钻孔参数
     *
     * @param tParamLayerDrilling 顺层钻孔参数
     * @return 结果
     */
    @Override
    public int updateTParamLayerDrilling(TParamLayerDrilling tParamLayerDrilling) {
        return tParamLayerDrillingMapper.updateTParamLayerDrilling(tParamLayerDrilling);
    }

    /**
     * 批量删除顺层钻孔参数
     *
     * @param ids 需要删除的顺层钻孔参数主键
     * @return 结果
     */
    @Override
    public int deleteTParamLayerDrillingByIds(Integer[] ids) {
        return tParamLayerDrillingMapper.deleteTParamLayerDrillingByIds(ids);
    }

    /**
     * 删除顺层钻孔参数信息
     *
     * @param id 顺层钻孔参数主键
     * @return 结果
     */
    @Override
    public int deleteTParamLayerDrillingById(Integer id) {
        return tParamLayerDrillingMapper.deleteTParamLayerDrillingById(id);
    }
    @Override
    public TParamLayerDrilling getLastOne(){
        return this.tParamLayerDrillingMapper.getLastOne();
    }

    /**
     * 更改参数的标识
     * @param ids 列表
     */
    @Override
    public void updateSign(List<Integer> ids){
        this.tParamLayerDrillingMapper.updateSign(ids);
    }
}
