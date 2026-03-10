package com.ruoyi.hh.hhhz.service.impl;

import com.ruoyi.hh.hhhz.domain.param.TParamDirectionalDrilling;
import com.ruoyi.hh.hhhz.mapper.TParamDirectionalDrillingMapper;
import com.ruoyi.hh.hhhz.service.ITParamDirectionalDrillingService;
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
 * 定向钻孔Service业务层处理
 *
 * @author hhhz
 * @date 2023-04-25
 */
@Service
public class TParamDirectionalDrillingServiceImpl implements ITParamDirectionalDrillingService {
    @Resource
    private TParamDirectionalDrillingMapper tParamDirectionalDrillingMapper;
    @Resource
    private TCoalinfoMapper coalinfoMapper;
    @Resource
    private TRoadwayMapper roadwayMapper;
    @Resource
    private TWorkingFaceMapper workingFaceMapper;
    /**
     * 查询定向钻孔
     *
     * @param id 定向钻孔主键
     * @return 定向钻孔
     */
    @Override
    public TParamDirectionalDrilling selectTParamDirectionalDrillingById(Integer id) {
        TParamDirectionalDrilling drilling = tParamDirectionalDrillingMapper.selectTParamDirectionalDrillingById(id);
        TWorkingFace workingFace = workingFaceMapper.getInfoById(drilling.getWorkingFaceId());
        TCoalinfo coalinfo = coalinfoMapper.selectTCoalinfoById(workingFace.getCoalId());
        TRoadway roadway = roadwayMapper.selectTRoadwayById(drilling.getRoadId());
        drilling.setWorkingFace(workingFace);
        drilling.setCoalinfo(coalinfo);
        drilling.setRoadway(roadway);


        return drilling;
    }

    /**
     * 查询定向钻孔列表
     *
     * @param tParamDirectionalDrilling 定向钻孔
     * @return 定向钻孔
     */
    @Override
    public List<TParamDirectionalDrilling> selectTParamDirectionalDrillingList(TParamDirectionalDrilling tParamDirectionalDrilling) {
        return tParamDirectionalDrillingMapper.selectTParamDirectionalDrillingList(tParamDirectionalDrilling);
    }

    /**
     * 新增定向钻孔
     *
     * @param tParamDirectionalDrilling 定向钻孔
     * @return 结果
     */
    @Override
    public int insertTParamDirectionalDrilling(TParamDirectionalDrilling tParamDirectionalDrilling) {
        return tParamDirectionalDrillingMapper.insertTParamDirectionalDrilling(tParamDirectionalDrilling);
    }

    /**
     * 修改定向钻孔
     *
     * @param tParamDirectionalDrilling 定向钻孔
     * @return 结果
     */
    @Override
    public int updateTParamDirectionalDrilling(TParamDirectionalDrilling tParamDirectionalDrilling) {
        return tParamDirectionalDrillingMapper.updateTParamDirectionalDrilling(tParamDirectionalDrilling);
    }

    /**
     * 批量删除定向钻孔
     *
     * @param ids 需要删除的定向钻孔主键
     * @return 结果
     */
    @Override
    public int deleteTParamDirectionalDrillingByIds(Integer[] ids) {
        return tParamDirectionalDrillingMapper.deleteTParamDirectionalDrillingByIds(ids);
    }

    /**
     * 删除定向钻孔信息
     *
     * @param id 定向钻孔主键
     * @return 结果
     */
    @Override
    public int deleteTParamDirectionalDrillingById(Integer id) {
        return tParamDirectionalDrillingMapper.deleteTParamDirectionalDrillingById(id);
    }

    @Override
    public TParamDirectionalDrilling getLastOne(){
        return this.tParamDirectionalDrillingMapper.getLastOne();
    }

    /**
     * 更改参数的标识
     * @param ids 列表
     */
    @Override
    public void updateSign(List<Integer> ids){
        this.tParamDirectionalDrillingMapper.updateSign(ids);
    }
}
