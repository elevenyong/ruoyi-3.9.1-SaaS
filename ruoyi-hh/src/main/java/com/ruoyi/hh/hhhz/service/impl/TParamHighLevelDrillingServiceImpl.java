package com.ruoyi.hh.hhhz.service.impl;

import com.ruoyi.hh.hhhz.domain.param.TParamHighLevelDrilling;
import com.ruoyi.hh.hhhz.mapper.TParamHighLevelDrillingMapper;
import com.ruoyi.hh.hhhz.service.ITParamHighLevelDrillingService;
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
 * 高位钻孔Service业务层处理
 *
 * @author hhhz
 * @date 2023-04-24
 */
@Service
public class TParamHighLevelDrillingServiceImpl implements ITParamHighLevelDrillingService {
    @Resource
    private TParamHighLevelDrillingMapper tParamHighLevelDrillingMapper;
    @Resource
    private TCoalinfoMapper coalinfoMapper;
    @Resource
    private TRoadwayMapper roadwayMapper;
    @Resource
    private TWorkingFaceMapper workingFaceMapper;

    /**
     * 查询高位钻孔
     *
     * @param id 高位钻孔主键
     * @return 高位钻孔
     */
    @Override
    public TParamHighLevelDrilling selectTParamHighLevelDrillingById(Integer id) {
        TParamHighLevelDrilling drilling = tParamHighLevelDrillingMapper.selectTParamHighLevelDrillingById(id);
        TWorkingFace workingFace = workingFaceMapper.getInfoById(drilling.getWorkingFaceId());
        TCoalinfo coalinfo = coalinfoMapper.selectTCoalinfoById(workingFace.getCoalId());
        TRoadway roadway = roadwayMapper.selectTRoadwayById(drilling.getRoadId());
        drilling.setWorkingFace(workingFace);
        drilling.setCoalinfo(coalinfo);
        drilling.setRoadway(roadway);
        return drilling;
    }

    /**
     * 查询高位钻孔列表
     *
     * @param tParamHighLevelDrilling 高位钻孔
     * @return 高位钻孔
     */
    @Override
    public List<TParamHighLevelDrilling> selectTParamHighLevelDrillingList(TParamHighLevelDrilling tParamHighLevelDrilling) {
        return tParamHighLevelDrillingMapper.selectTParamHighLevelDrillingList(tParamHighLevelDrilling);
    }

    /**
     * 新增高位钻孔
     *
     * @param tParamHighLevelDrilling 高位钻孔
     * @return 结果
     */
    @Override
    public int insertTParamHighLevelDrilling(TParamHighLevelDrilling tParamHighLevelDrilling) {
        return tParamHighLevelDrillingMapper.insertTParamHighLevelDrilling(tParamHighLevelDrilling);
    }

    /**
     * 修改高位钻孔
     *
     * @param tParamHighLevelDrilling 高位钻孔
     * @return 结果
     */
    @Override
    public int updateTParamHighLevelDrilling(TParamHighLevelDrilling tParamHighLevelDrilling) {
        return tParamHighLevelDrillingMapper.updateTParamHighLevelDrilling(tParamHighLevelDrilling);
    }

    /**
     * 批量删除高位钻孔
     *
     * @param ids 需要删除的高位钻孔主键
     * @return 结果
     */
    @Override
    public int deleteTParamHighLevelDrillingByIds(Integer[] ids) {
        return tParamHighLevelDrillingMapper.deleteTParamHighLevelDrillingByIds(ids);
    }

    /**
     * 删除高位钻孔信息
     *
     * @param id 高位钻孔主键
     * @return 结果
     */
    @Override
    public int deleteTParamHighLevelDrillingById(Integer id) {
        return tParamHighLevelDrillingMapper.deleteTParamHighLevelDrillingById(id);
    }
    @Override
    public TParamHighLevelDrilling getLastOne(){
        return this.tParamHighLevelDrillingMapper.getLastOne();
    }

    /**
     * 更改参数的标识
     * @param ids 列表
     */
    @Override
    public void updateSign(List<Integer> ids){
        this.tParamHighLevelDrillingMapper.updateSign(ids);
    }
}
