package com.ruoyi.hh.hhhz.service.impl;

import com.ruoyi.hh.hhhz.domain.param.DesignParameters;
import com.ruoyi.hh.hhhz.mapper.TDesignParameterMapper;
import com.ruoyi.hh.hhhz.service.ITDesignParameterService;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.physical.domain.TDesignedDirectionalBorehole;
import com.ruoyi.hh.physical.mapper.TDesignedBoreholeMapper;
import com.ruoyi.hh.physical.mapper.TDesignedDirectionalBoreholeMapper;
import com.ruoyi.hh.physical.service.ITDesignedDirectionalBoreholeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.hhhz.service.impl
 * @className: TDesignParameterServiceImpl
 * @author: Eleven
 * @description:设计钻孔参数实现类
 * @date: 2023/7/19 13:53
 * @version: 1.0
 */
@Service
public class TDesignParameterServiceImpl implements ITDesignParameterService {

    @Resource
    private TDesignParameterMapper tDesignParameterMapper;
    @Resource
    private TDesignedBoreholeMapper tDesignedBoreholeMapper;
    @Resource
    private TDesignedDirectionalBoreholeMapper tDesignedDirectionalBoreholeMapper;

    @Resource
    private ITDesignedDirectionalBoreholeService directionalBoreholeService;

    @Override
    public DesignParameters selectDesignParametersById(Integer id) {
        return this.tDesignParameterMapper.selectDesignParametersById(id);
    }

    @Override
    public List<DesignParameters> selectTDesignParametersList(DesignParameters designParameters) {
        List<DesignParameters> list = this.tDesignParameterMapper.selectTDesignParametersList(designParameters);
        for (DesignParameters dp : list) {
            TDesignedBorehole tDesignedBorehole = new TDesignedBorehole();
            tDesignedBorehole.setDesParameterId(dp.getId());
            List<TDesignedBorehole> tDesignedBoreholes = tDesignedBoreholeMapper.selectTDesignedBoreholeList(tDesignedBorehole);
            List<TDesignedDirectionalBorehole> tDesignedDirectionalBoreholes = tDesignedDirectionalBoreholeMapper.getByDesignParameterId(dp.getId());
            dp.settDesignedBoreholes(tDesignedBoreholes);
            dp.settDesignedDirectionalBoreholes(tDesignedDirectionalBoreholes);
        }
        return list;
    }

    /**
     * 插入单条设计参数数据
     *
     * @param designParameters 钻孔设计参数记录
     * @return
     */
    @Transactional
    @Override
    public DesignParameters insertDesignParameters(DesignParameters designParameters) {
        int result = this.tDesignParameterMapper.insertDesignParameters(designParameters);
        int id;
        if (result > 0) {
            id = tDesignParameterMapper.selectTDesignParametersList(designParameters).get(0).getId();
            List<TDesignedBorehole> tDesignedBoreholes = designParameters.gettDesignedBoreholes();
            List<TDesignedDirectionalBorehole> tDesignedDirectionalBoreholes = designParameters.gettDesignedDirectionalBoreholes();
            if (tDesignedDirectionalBoreholes != null) {
                for (TDesignedDirectionalBorehole tdb : tDesignedDirectionalBoreholes) {
                    tdb.setSign(0);
                    tdb.setDesParameterId(id);
                    //tDesignedDirectionalBoreholeMapper.insertTDesignedDirectionalBorehole(tdb);
                    directionalBoreholeService.insertTDesignedDirectionalBorehole(tdb);
                }
            }
            if (tDesignedBoreholes != null && !tDesignedBoreholes.isEmpty()) {
                for (TDesignedBorehole tDesignedBorehole : tDesignedBoreholes) {
                    tDesignedBorehole.setSign(0);
                    tDesignedBorehole.setDesParameterId(id);
                    tDesignedBoreholeMapper.insertTDesignedBorehole(tDesignedBorehole);
                }
            }
        }
        return getLastOne();
    }

    @Override
    public int updateDesignParameters(DesignParameters designParameters) {
        return this.tDesignParameterMapper.updateDesignParameters(designParameters);
    }

    @Transactional
    @Override
    public int deleteDesignParametersById(Integer id) {
        this.tDesignedDirectionalBoreholeMapper.delByDesignParameterId(id);
        this.tDesignedBoreholeMapper.delByDesignParameterId(id);
        return this.tDesignParameterMapper.deleteDesignParametersById(id);
    }

    @Transactional
    @Override
    public int deleteDesignParametersByIds(Integer[] ids) {
        for (Integer id : ids) {
            this.tDesignedDirectionalBoreholeMapper.delByDesignParameterId(id);
            this.tDesignedBoreholeMapper.delByDesignParameterId(id);
        }
        return this.tDesignParameterMapper.deleteDesignParametersByIds(ids);
    }

    @Override
    public DesignParameters getLastOne() {
        return this.tDesignParameterMapper.getLastOne();
    }

    @Override
    public void updateSign(Integer sign,List<Integer> ids) {
        this.tDesignParameterMapper.updateSign(sign,ids);
    }
}
