package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.business.service.ITDesSchemeInfoService;
import com.ruoyi.hh.physical.domain.TDesDirectionalDrillingPoint;
import com.ruoyi.hh.physical.domain.TDesignedDirectionalBorehole;
import com.ruoyi.hh.physical.mapper.TDesDirectionalDrillingPointMapper;
import com.ruoyi.hh.physical.mapper.TDesignedDirectionalBoreholeMapper;
import com.ruoyi.hh.physical.service.ITDesignedDirectionalBoreholeService;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfCoalMiningService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 设计定向钻孔信息Service业务层处理
 *
 * @author eleven
 * @date 2023-07-27
 */
@Service
public class TDesignedDirectionalBoreholeServiceImpl implements ITDesignedDirectionalBoreholeService {
    @Resource
    private TDesignedDirectionalBoreholeMapper tDesignedDirectionalBoreholeMapper;
    @Resource
    private TDesDirectionalDrillingPointMapper tDesDirectionalDrillingPointMapper;
    @Resource
    private ITWorkingFaceOfCoalMiningService coalMiningWorkFaceService;
    @Resource
    private ITDesSchemeInfoService itDesSchemeInfoService;

    /**
     * 查询设计定向钻孔信息
     *
     * @param id 设计定向钻孔信息主键
     * @return 设计定向钻孔信息
     */
    @Override
    public TDesignedDirectionalBorehole selectTDesignedDirectionalBoreholeById(Integer id) {
        List<TDesDirectionalDrillingPoint> list = this.tDesDirectionalDrillingPointMapper.getByDesDirectionalBoreholeId(id);
        TDesignedDirectionalBorehole tdb = tDesignedDirectionalBoreholeMapper.selectTDesignedDirectionalBoreholeById(id);
        tdb.settDirectionalDrillingPointList(list);
        return tdb;
    }

    /**
     * 查询设计定向钻孔信息列表
     *
     * @param tDesignedDirectionalBorehole 设计定向钻孔信息
     * @return 设计定向钻孔信息
     */
    @Override
    public List<TDesignedDirectionalBorehole> selectTDesignedDirectionalBoreholeList(TDesignedDirectionalBorehole tDesignedDirectionalBorehole) {
        List<Integer> workFaceIds = coalMiningWorkFaceService.getWorkFaceByRoadwayId(tDesignedDirectionalBorehole.getRoadwayId());
        int choose = -1;
        for (Integer workFaceId : workFaceIds) {
            Integer i = itDesSchemeInfoService.checkExistChooseScheme(workFaceId);
            if (i != -1) {
                choose = i;
                break;
            }
        }
        if (choose > -1) {
            tDesignedDirectionalBorehole.setSign(1);
        } else tDesignedDirectionalBorehole.setSign(0);
        List<TDesignedDirectionalBorehole> list = tDesignedDirectionalBoreholeMapper.selectTDesignedDirectionalBoreholeList(tDesignedDirectionalBorehole);
        if (list.size() > 0) {
            for (TDesignedDirectionalBorehole tdb : list) {
                List<TDesDirectionalDrillingPoint> tdp = this.tDesDirectionalDrillingPointMapper.getByDesDirectionalBoreholeId(tdb.getId());
                tdb.settDirectionalDrillingPointList(tdp);
            }
        }
        return list;
    }

    /**
     * 新增设计定向钻孔信息
     *
     * @param tDesignedDirectionalBorehole 设计定向钻孔信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTDesignedDirectionalBorehole(TDesignedDirectionalBorehole tDesignedDirectionalBorehole) {
        int result = tDesignedDirectionalBoreholeMapper.insertTDesignedDirectionalBorehole(tDesignedDirectionalBorehole);
        if (result > 0) {
            TDesignedDirectionalBorehole tddb = this.tDesignedDirectionalBoreholeMapper.selectTDesignedDirectionalBoreholeList(tDesignedDirectionalBorehole).get(0);
            List<TDesDirectionalDrillingPoint> list = tDesignedDirectionalBorehole.gettDirectionalDrillingPointList();
            if (list != null && !list.isEmpty()) {
                for (TDesDirectionalDrillingPoint tdp : list) {
                    tdp.setDesBoreholeId(tddb.getId());
                    tDesDirectionalDrillingPointMapper.insertTDesDirectionalDrillingPoint(tdp);
                }
            }
        }
        return result;
    }

    /**
     * 修改设计定向钻孔信息
     *
     * @param tDesignedDirectionalBorehole 设计定向钻孔信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateTDesignedDirectionalBorehole(TDesignedDirectionalBorehole tDesignedDirectionalBorehole) {
        List<TDesDirectionalDrillingPoint> list = tDesignedDirectionalBorehole.gettDirectionalDrillingPointList();
        this.tDesDirectionalDrillingPointMapper.delByDesDirectionalBoreholeId(tDesignedDirectionalBorehole.getId());
        if (list.size() > 0) {
            for (TDesDirectionalDrillingPoint tdp : list) {
                tdp.setDesBoreholeId(tDesignedDirectionalBorehole.getId());
                tDesDirectionalDrillingPointMapper.insertTDesDirectionalDrillingPoint(tdp);
            }
        }
        return tDesignedDirectionalBoreholeMapper.updateTDesignedDirectionalBorehole(tDesignedDirectionalBorehole);
    }

    /**
     * 批量删除设计定向钻孔信息
     *
     * @param ids 需要删除的设计定向钻孔信息主键
     * @return 结果
     */
    @Override
    public int deleteTDesignedDirectionalBoreholeByIds(Integer[] ids) {
        return tDesignedDirectionalBoreholeMapper.deleteTDesignedDirectionalBoreholeByIds(ids);
    }

    /**
     * 删除设计定向钻孔信息信息
     *
     * @param id 设计定向钻孔信息主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTDesignedDirectionalBoreholeById(Integer id) {
        this.tDesDirectionalDrillingPointMapper.delByDesDirectionalBoreholeId(id);
        return tDesignedDirectionalBoreholeMapper.deleteTDesignedDirectionalBoreholeById(id);
    }

    /**
     * 根据设计参数Id查询定向钻列表
     *
     * @param designParameterId
     * @return
     */
    @Override
    public List<TDesignedDirectionalBorehole> getByDesignParameterId(Integer designParameterId) {
        return this.tDesignedDirectionalBoreholeMapper.getByDesignParameterId(designParameterId);
    }

    /**
     * 根据设计参数ID删除设计定向钻孔信息
     *
     * @param designParameterId
     */
    @Override
    public void delByDesignParameterId(Integer designParameterId) {
        this.tDesignedDirectionalBoreholeMapper.delByDesignParameterId(designParameterId);
    }

    /**
     * 更改设计钻孔状态（确定设计钻孔方案）
     *
     * @param ids 列表
     */
    @Override
    public void makeSureDesProgramme(Integer sign, List<Integer> ids) {
        if (ids.size() > 0) {
            // 常规钻孔已经执行过一次，定向钻不用再执行了
            //this.tDesignParameterMapper.updateSign(sign,ids);
            for (Integer i : ids) {
                List<TDesignedDirectionalBorehole> list = this.tDesignedDirectionalBoreholeMapper.getByDesignParameterId(i);
                List<Integer> idList = new ArrayList<>();
                for (TDesignedDirectionalBorehole borehole : list) {
                    idList.add(borehole.getId());
                }
                if (!idList.isEmpty()) {
                    this.tDesignedDirectionalBoreholeMapper.makeSureDesProgramme(sign, idList);
                }
            }
        }
    }

    @Override
    public Integer getDirectBoreholeCount(Integer type, Integer roadwayId) {
        return tDesignedDirectionalBoreholeMapper.getDirectBoreholeCount(type,roadwayId);
    }
}
