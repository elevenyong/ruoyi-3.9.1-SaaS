package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.hh.business.mapper.TMeasurePointContrastMapper;
import com.ruoyi.hh.business.service.ITSensorInfoService;
import com.ruoyi.hh.physical.domain.TMeasuringPoint;
import com.ruoyi.hh.physical.mapper.TMeasuringPointMapper;
import com.ruoyi.hh.physical.service.ITMeasuringPointService;
import com.ruoyi.hh.simulate.domain.SimulationMeasurepointData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 测点信息之Service业务层处理
 *
 * @author eleven
 * @date 2022-07-16
 */
@Service
public class TMeasuringPointServiceImpl implements ITMeasuringPointService {
    @Resource
    private TMeasuringPointMapper tMeasuringPointMapper;
    @Resource
    private TMeasurePointContrastMapper tMeasurePointContrastMapper;
    @Resource
    private ITSensorInfoService tSensorInfoService;
    @Resource
    private RedisCache redisCache;

    /**
     * 查询测点信息之
     *
     * @param id 测点信息之主键
     * @return 测点信息之
     */
    @Override
    public TMeasuringPoint selectTMeasuringPointById(Integer id) {
        return tMeasuringPointMapper.selectTMeasuringPointById(id);
    }

    /**
     * 查询测点信息之列表
     *
     * @param tMeasuringPoint 测点信息之
     * @return 测点信息之
     */
    @Override
    public List<TMeasuringPoint> selectTMeasuringPointList(TMeasuringPoint tMeasuringPoint) {
        List<TMeasuringPoint> list= tMeasuringPointMapper.selectTMeasuringPointList(tMeasuringPoint);

        for(TMeasuringPoint tMeasuringPoint1:list){
            if(redisCache.getCacheObject(tMeasuringPoint1.getMeasuringPointName())!=null){
                SimulationMeasurepointData s=(SimulationMeasurepointData)redisCache.getCacheObject(tMeasuringPoint1.getMeasuringPointName());
                tMeasuringPoint1.setTotalMixTureFlow(s.getTotalMixTureFlow());
                tMeasuringPoint1.setTotalPureFlow(s.getTotalPureFlow());
                tMeasuringPoint1.setMixTureFlow(s.getMixTureFlow());
                tMeasuringPoint1.setPureFlow(s.getPureFlow());
            }
        }
        return list;
    }

    /**
     * 新增测点信息之
     *
     * @param tMeasuringPoint 测点信息之
     * @return 结果
     */
    @Override
    public int insertTMeasuringPoint(TMeasuringPoint tMeasuringPoint) {
        return tMeasuringPointMapper.insertTMeasuringPoint(tMeasuringPoint);
    }

    /**
     * 修改测点信息之
     *
     * @param tMeasuringPoint 测点信息之
     * @return 结果
     */
    @Override
    public Integer updateTMeasuringPoint(TMeasuringPoint tMeasuringPoint) {
        return tMeasuringPointMapper.updateTMeasuringPoint(tMeasuringPoint);
    }

    /**
     * 批量删除测点信息之
     *
     * @param ids 需要删除的测点信息之主键
     * @return 结果
     */
    @Override
    public Integer deleteTMeasuringPointByIds(List<Integer> ids) {
        return tMeasuringPointMapper.deleteTMeasuringPointByIds(ids);
    }

    /**
     * 删除测点信息之信息
     *
     * @param id 测点信息之主键
     * @return 结果
     */
    @Override
    public Integer deleteTMeasuringPointById(Integer id) {
        //删除测点人工对比
        this.tMeasurePointContrastMapper.deleteTMeasurePointContrastById(id);
        //删除传感器信息
        this.tSensorInfoService.delByMeasurePointId(id);
        return tMeasuringPointMapper.deleteTMeasuringPointById(id);
    }

    /**
     * 分页查询列表
     *
     * @return 列表
     */
    @Override
    public List<TMeasuringPoint> getPageList() {
        return this.tMeasuringPointMapper.getPageList();
    }
    /**
     * 根据巷道ID删除测点信息
     * @param roadwayId 结果
     */
    @Override
    public Integer delByRoadWayId(Integer roadwayId) {
        return this.tMeasuringPointMapper.delByRoadWayId(roadwayId);
    }
    /**
     * 根据巷道ID删除测点信息
     * @param roadWayIds 结果
     */
    @Override
    public Integer delByRoadWayIds(List<Integer> roadWayIds) {
        return this.tMeasuringPointMapper.delByRoadWayIds(roadWayIds);
    }

    @Override
    public List<TMeasuringPoint> getByRoadWayIdAndType(Integer roadwayId, Integer measuringtypeid) {
        return this.tMeasuringPointMapper.getByRoadWayIdAndType(roadwayId, measuringtypeid);
    }

    /**
     * 根据评价单元查询测点列表
     * @param evaluationid 评价单元ID
     * @return 列表
     */
    @Override
    public List<TMeasuringPoint> getByDrainageUnitId(Integer evaluationid) {
        return this.tMeasuringPointMapper.getByDrainageUnitId(evaluationid);
    }

    /**
     * 根据评价单元查询测点列表
     * @param evaluationid 评价单元ID
     * @return 列表
     */
    @Override
    public List<Integer> getMeasureIdByUnitId(Integer evaluationid) {
        return this.tMeasuringPointMapper.getMeasureIdByUnitId(evaluationid);
    }

    @Override
    public List<TMeasuringPoint> getByGoafinfoid(Integer goafinfoid) {
        return this.tMeasuringPointMapper.getByGoafinfoid(goafinfoid);
    }

    @Override
    public List<TMeasuringPoint> getByCoalUncoverid(Integer coaluncoverid) {
        return this.tMeasuringPointMapper.getByCoalUncoverid(coaluncoverid);
    }

}
