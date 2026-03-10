package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TActDirectionalBorehole;
import com.ruoyi.hh.physical.domain.TActDirectionalDrillingPoint;
import com.ruoyi.hh.physical.mapper.TActDirectionalBoreholeMapper;
import com.ruoyi.hh.physical.service.ITActDirectionalBoreholeService;
import com.ruoyi.hh.physical.service.ITActDirectionalDrillingPointService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 实际定向钻孔信息Service业务层处理
 *
 * @author eleven
 * @date 2023-07-27
 */
@Service
public class TActDirectionalBoreholeServiceImpl implements ITActDirectionalBoreholeService {
    @Resource
    private TActDirectionalBoreholeMapper tActDirectionalBoreholeMapper;

    @Resource
    private ITActDirectionalDrillingPointService tActDirectionalDrillingPointService;

    /**
     * 查询实际定向钻孔信息
     *
     * @param id 实际定向钻孔信息主键
     * @return 实际定向钻孔信息
     */
    @Override
    public TActDirectionalBorehole selectTActDirectionalBoreholeById(Integer id) {
        return tActDirectionalBoreholeMapper.selectTActDirectionalBoreholeById(id);
    }

    /**
     * 查询实际定向钻孔信息列表
     *
     * @param tActDirectionalBorehole 实际定向钻孔信息
     * @return 实际定向钻孔信息
     */
    @Override
    public List<TActDirectionalBorehole> selectTActDirectionalBoreholeList(TActDirectionalBorehole tActDirectionalBorehole) {
        return tActDirectionalBoreholeMapper.selectTActDirectionalBoreholeList(tActDirectionalBorehole);
    }

    /**
     * 新增实际定向钻孔信息
     *
     * @param borehole 实际定向钻孔信息
     * @return 结果
     */
    @Override
    public int insertTActDirectionalBorehole(TActDirectionalBorehole borehole) {
        // 判断是否重复
        TActDirectionalBorehole queryInfo = new TActDirectionalBorehole();
        queryInfo.setRoadwayId(borehole.getRoadwayId());
        List<TActDirectionalBorehole> compareList = this.tActDirectionalBoreholeMapper.selectTActDirectionalBoreholeList(queryInfo);
        for (TActDirectionalBorehole bo : compareList) {
            if (Objects.equals(bo.getGroupNo(), borehole.getGroupNo()) &&
                    Objects.equals(bo.getBoreholeNo(), borehole.getBoreholeNo()) &&
                    Objects.equals(bo.getCategoryCode(), borehole.getCategoryCode()) &&
                    Objects.equals(bo.getRoadwayId(), borehole.getRoadwayId())) {
                // 钻孔重复
                return -1;
            }
        }
        // 钻孔信息插入
        int result = tActDirectionalBoreholeMapper.insertTActDirectionalBorehole(borehole);
        if (result > 0) {
            // 钻孔插入成功
            List<TActDirectionalDrillingPoint> list = borehole.gettDirectionalDrillingPointList();
            if (!list.isEmpty()) {
                // 插入钻孔轨迹
                int pi = 0;
                for (TActDirectionalDrillingPoint td : list) {
                    // 导入数据时需要清空ID
                    td.setId(null);
                    td.setBoreholeId(borehole.getId());
                    int i = tActDirectionalDrillingPointService.insertTActDirectionalDrillingPoint(td);
                    if (i > 0) {
                        pi++;
                    }
                }
                if (pi == list.size()) {
                    return 1;
                } else {
                    // 钻孔轨迹部分插入失败
                    tActDirectionalBoreholeMapper.deleteTActDirectionalBoreholeById(borehole.getId());
                    tActDirectionalDrillingPointService.delByActDirectionalBoreholeId(borehole.getId());
                    return -4;
                }
            } else {
                // 钻孔轨迹为空
                // 删除已插入的钻孔信息
                tActDirectionalBoreholeMapper.deleteTActDirectionalBoreholeById(borehole.getId());
                return -3;
            }
        } else {
            // 钻孔信息插入失败
            return -2;
        }
    }

    /**
     * 修改实际定向钻孔信息
     *
     * @param tActDirectionalBorehole 实际定向钻孔信息
     * @return 结果
     */
    @Override
    public int updateTActDirectionalBorehole(TActDirectionalBorehole tActDirectionalBorehole) {
        return tActDirectionalBoreholeMapper.updateTActDirectionalBorehole(tActDirectionalBorehole);
    }

    /**
     * 批量删除实际定向钻孔信息
     *
     * @param ids 需要删除的实际定向钻孔信息主键
     * @return 结果
     */
    @Override
    public int deleteTActDirectionalBoreholeByIds(Integer[] ids) {
        return tActDirectionalBoreholeMapper.deleteTActDirectionalBoreholeByIds(ids);
    }

    /**
     * 删除实际定向钻孔信息信息
     *
     * @param id 实际定向钻孔信息主键
     * @return 结果
     */
    @Override
    public int deleteTActDirectionalBoreholeById(Integer id) {
        return tActDirectionalBoreholeMapper.deleteTActDirectionalBoreholeById(id);
    }

    /*@Override
    public AjaxResult insertBatchTDesignedBorehole(List<TActDirectionalBorehole> list) {
        return null;
    }*/

    /**
     * 根据巷道ID获取所有定向钻孔
     * @param id
     * @return
     */
    @Override
    public List<TActDirectionalBorehole> getActDirectionalBoreholeInfoByRoadwayId(Integer id) {
        List<TActDirectionalBorehole> tActDirectionalBoreholes = tActDirectionalBoreholeMapper.selectTActDirectionalBoreholeByRoadwayId(id);
        for (TActDirectionalBorehole tActDirectionalBorehole : tActDirectionalBoreholes) {
            List<TActDirectionalDrillingPoint> points = tActDirectionalDrillingPointService.getByActDirectionalBoreholeId(tActDirectionalBorehole.getId());
            tActDirectionalBorehole.settDirectionalDrillingPointList(points);
        }
        return tActDirectionalBoreholes;
    }

    /**
     * 根据巷道ID获取巷道内参与煤层构建的定向钻孔
     * @param id
     * @return
     */
    @Override
    public List<TActDirectionalBorehole> getUsedDirectionalByRoadwayId(Integer id) {
        TActDirectionalBorehole queryDirectionInfo = new TActDirectionalBorehole();
        queryDirectionInfo.setRoadwayId(id);
        queryDirectionInfo.setIsUsedCreateCoal(1);
        List<TActDirectionalBorehole> tActDirectionalBoreholes = tActDirectionalBoreholeMapper.selectTActDirectionalBoreholeList(queryDirectionInfo);
        for (TActDirectionalBorehole tActDirectionalBorehole : tActDirectionalBoreholes) {
            List<TActDirectionalDrillingPoint> points = tActDirectionalDrillingPointService.getByActDirectionalBoreholeId(tActDirectionalBorehole.getId());
            tActDirectionalBorehole.settDirectionalDrillingPointList(points);
        }
        return tActDirectionalBoreholes;
    }

    @Override
    public Integer getDirectBoreholeCount(Integer categoryCode, Integer roadwayId) {
        return tActDirectionalBoreholeMapper.getDirectBoreholeCount(categoryCode,roadwayId);
    }
}
