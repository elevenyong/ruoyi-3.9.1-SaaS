package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TNavigationPoint;
import com.ruoyi.hh.physical.mapper.TNavigationPointMapper;
import com.ruoyi.hh.physical.mapper.TRoadwayMapper;
import com.ruoyi.hh.physical.service.ITActualBoreholeService;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import com.ruoyi.hh.physical.service.ITNavigationPointService;
import com.ruoyi.hh.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 导线点相关Service业务层处理
 *
 * @author eleven
 * @date 2022-07-13
 */
@Service
public class TNavigationPointServiceImpl implements ITNavigationPointService {
    @Resource
    private TNavigationPointMapper tNavigationPointMapper;
    @Resource
    private ITDesignedBoreholeService tDesignedBoreholeService;
    @Resource
    private ITActualBoreholeService tActualBoreholeService;
    @Resource
    private TRoadwayMapper tRoadwayMapper;

    /**
     * 查询导线点相关
     *
     * @param id 导线点相关主键
     * @return 导线点相关
     */
    @Override
    public TNavigationPoint selectTNavigationPointById(Integer id) {
        return tNavigationPointMapper.selectTNavigationPointById(id);
    }

    /**
     * 查询导线点相关列表
     *
     * @param tNavigationPoint 导线点相关
     * @return 导线点相关
     */
    @Override
    public List<TNavigationPoint> selectTNavigationPointList(TNavigationPoint tNavigationPoint) {
        return tNavigationPointMapper.selectTNavigationPointList(tNavigationPoint);
    }

    /**
     * 新增导线点相关
     *
     * @param tNavigationPoint 导线点相关
     * @return 结果
     */
    @Override
    public Result insertTNavigationPoint(TNavigationPoint tNavigationPoint) {
        if(tNavigationPoint.getRoadwayId()==null&&tNavigationPoint.getRoadway()==null){
            return Result.buildResult(Result.Status.NULL_ERROR);
        }else if(tNavigationPoint.getRoadwayId()==null&&tNavigationPoint.getRoadway()!=null){
            tNavigationPoint.setRoadwayId(this.tRoadwayMapper.getIdByRoadWayName(tNavigationPoint.getRoadway()));
            if(this.tNavigationPointMapper.getIdByNavigationPointName(tNavigationPoint.getNavigationPointName(),
                    tNavigationPoint.getRoadwayId())!=null){
                return Result.buildResult(Result.Status.EXIT);
            }
        }
        tNavigationPointMapper.insertTNavigationPoint(tNavigationPoint);
        return Result.buildResult(Result.Status.OK);
    }

    /**
     * 修改导线点相关
     *
     * @param tNavigationPoint 导线点相关
     * @return 结果
     */
    @Override
    public Result updateTNavigationPoint(TNavigationPoint tNavigationPoint) {
        if(tNavigationPoint.getRoadwayId()==null){
            return Result.buildResult(Result.Status.NULL_ERROR);
        }else if(tNavigationPoint.getRoadwayId()!=null){
            if(tNavigationPointMapper.getIdByNavigationPointName(tNavigationPoint.getNavigationPointName(),tNavigationPoint.getRoadwayId())!=null
            &&!tNavigationPointMapper.getIdByNavigationPointName(tNavigationPoint.getNavigationPointName(),tNavigationPoint.getRoadwayId()).equals(tNavigationPoint.getId())){
                return Result.buildResult(Result.Status.EXIT);
            }
        }
        tNavigationPointMapper.updateTNavigationPoint(tNavigationPoint);
        return Result.buildResult(Result.Status.OK);
    }

    /**
     * 批量删除导线点相关
     *
     * @param ids 需要删除的导线点相关主键
     * @return 结果
     */
    @Override
    public Integer deleteTNavigationPointByIds( List<Integer> ids) {
        for(Integer id:ids){
            this.tActualBoreholeService.delByNavigationPointId(id);
            this.tDesignedBoreholeService.delByNavigationPointId(id);
        }
        return tNavigationPointMapper.deleteTNavigationPointByIds(ids);
    }

    /**
     * 删除导线点相关信息
     *
     * @param id 导线点相关主键
     * @return 结果
     */
    @Override
    public Integer deleteTNavigationPointById(Integer id) {
        this.tActualBoreholeService.delByNavigationPointId(id);
        this.tDesignedBoreholeService.delByNavigationPointId(id);
        return tNavigationPointMapper.deleteTNavigationPointById(id);
    }

    /**
     * 批量添加导线点信息
     *
     * @param list 导线点列表
     */
    @Override
    public int insertTNavigationPointList(List<TNavigationPoint> list) {
        return this.tNavigationPointMapper.insertTNavigationPointList(list);
    }

    @Override
    public List<TNavigationPoint> list() {
        return this.tNavigationPointMapper.list();
    }

    /**
     * 根据巷道ID查询导线点列表
     *
     * @param RoadwayId 巷道ID
     * @return 导线点列表
     */
    @Override
    public List<TNavigationPoint> getByRoadwayId(Integer RoadwayId) {
        return this.tNavigationPointMapper.getByRoadwayId(RoadwayId);
    }

    @Override
    public Integer delByRoadWayId(Integer RoadWayId) {
        List<TNavigationPoint> list=new ArrayList<>();
        list =this.tNavigationPointMapper.getByRoadwayId(RoadWayId);
        //删除导线点前，删除关联的设计钻孔和实际钻孔
        if(list.size()>0){
            for(TNavigationPoint tNavigationPoint:list){
                this.tDesignedBoreholeService.delByNavigationPointId(tNavigationPoint.getId());
                this.tActualBoreholeService.delByNavigationPointId(tNavigationPoint.getId());
            }
        }
        return this.tNavigationPointMapper.delByRoadWayId(RoadWayId);
    }

    /**
     * 根据巷道ID删除导线点信息
     * @param roadWayIds 巷道ID
     * @return 列表
     */
    @Override
    public Integer delByRoadWayIds(List<Integer> roadWayIds) {
        List<TNavigationPoint> list=new ArrayList<>();
        for(Integer roadWayId:roadWayIds){
            list.addAll(this.tNavigationPointMapper.getByRoadwayId(roadWayId));
        }
        if(list.size()>0){
            for(TNavigationPoint tNavigationPoint:list){
                this.tDesignedBoreholeService.delByNavigationPointId(tNavigationPoint.getId());
                this.tActualBoreholeService.delByNavigationPointId(tNavigationPoint.getId());
            }
        }
        return this.tNavigationPointMapper.delByRoadWayIds(roadWayIds);
    }

    /**
     * 根据导线点名称查询导线点ID
     * @param navigationpointname 导线点名称
     * @param roadwayId 巷道ID
     * @return ID
     */
    @Override
    public Integer getIdByNavigationPointName(String navigationpointname,Integer roadwayId) {
        return this.tNavigationPointMapper.getIdByNavigationPointName(navigationpointname,roadwayId);
    }

    /**
     * 获取所有导线点名称
     * @return
     */
    @Override
    public List<String> getNavigationPointAll() {
        return this.tNavigationPointMapper.getNavigationPointAll();
    }
}
