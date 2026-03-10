package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.business.mapper.TStripedesignparameterMapper;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.physical.domain.TRoadwayZone;
import com.ruoyi.hh.physical.mapper.TBkActBoreholeInfoMapper;
import com.ruoyi.hh.physical.mapper.TRoadwayMapper;
import com.ruoyi.hh.physical.mapper.TRoadwayZoneMapper;
import com.ruoyi.hh.physical.service.ITActualBoreholeService;
import com.ruoyi.hh.physical.service.ITBkActBoreholeInfoService;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import com.ruoyi.hh.physical.service.ITRoadwayZoneService;
import com.ruoyi.hh.util.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 区段信息Service业务层处理
 *
 * @author eleven
 * @date 2022-07-14
 */
@Service
public class TRoadwayZoneServiceImpl implements ITRoadwayZoneService {
    @Resource
    private TRoadwayZoneMapper tRoadwayZoneMapper;
    @Resource
    private TStripedesignparameterMapper tStripedesignparameterMapper;
    @Resource
    private ITDesignedBoreholeService tDesignedBoreholeService;
    @Resource
    private ITActualBoreholeService tActualBoreholeService;
    @Resource
    private ITBkActBoreholeInfoService tBkActBoreholeInfoService;
    @Resource
    private TRoadwayMapper tRoadwayMapper;

    /**
     * 查询区段信息
     *
     * @param id 区段信息主键
     * @return 区段信息
     */
    @Override
    public TRoadwayZone selectTRoadwayZoneById(Integer id) {
        return tRoadwayZoneMapper.selectTRoadwayZoneById(id);
    }

    /**
     * 查询区段信息列表
     *
     * @param tRoadwayZone 区段信息
     * @return 区段信息
     */
    @Override
    public List<TRoadwayZone> selectTRoadwayZoneList(TRoadwayZone tRoadwayZone) {
        return tRoadwayZoneMapper.selectTRoadwayZoneList(tRoadwayZone);
    }

    /**
     * 新增区段信息
     *
     * @param tRoadwayZone 区段信息
     * @return 结果
     */
    @Override
    public Result insertTRoadwayZone(TRoadwayZone tRoadwayZone) {
        if (tRoadwayZone.getRoadwayId() == null && tRoadwayZone.getRoadway() == null) {
            return Result.buildResult(Result.Status.NULL_ERROR);
        } else if (tRoadwayZone.getRoadwayId() == null && tRoadwayZone.getRoadway() != null) {
            tRoadwayZone.setRoadwayId(tRoadwayMapper.getIdByRoadWayName(tRoadwayZone.getRoadway()));
        }
        /*if (this.tRoadwayZoneMapper.getIdByZoneName(tRoadwayZone.getZoneName(), tRoadwayZone.getRoadwayId()) != null) {
            return Result.buildResult(Result.Status.EXIT);
        }*/
        tRoadwayZoneMapper.insertTRoadwayZone(tRoadwayZone);
        return Result.buildResult(Result.Status.OK);
    }

    /**
     * 修改区段信息
     * 修改区段的信息中方位角修改时，修改该
     * 区段下的所有设计钻孔的方位角
     *
     * @param tRoadwayZone 区段信息
     * @return 结果
     */
    @Override
    public Result updateTRoadwayZone(TRoadwayZone tRoadwayZone) {
        if (tRoadwayZone.getRoadwayId() == null) {
            return Result.buildResult(Result.Status.NULL_ERROR);
        }/* else {
            if (tRoadwayZoneMapper.getIdByZoneName(tRoadwayZone.getZoneName(), tRoadwayZone.getRoadwayId()) != null
                    && !tRoadwayZoneMapper.getIdByZoneName(tRoadwayZone.getZoneName(), tRoadwayZone.getRoadwayId()).equals(tRoadwayZone.getId())
            ) {
                return Result.buildResult(Result.Status.EXIT);
            }
        }*/
        double diff = 0.0;
        TRoadwayZone tz = this.tRoadwayZoneMapper.selectTRoadwayZoneById(tRoadwayZone.getId());
        diff = tRoadwayZone.getAzimuthAngle() - tz.getAzimuthAngle();//得到方位角的差值
        if (diff != 0) {
            //更新该区段下的设计钻孔的方位角
            List<TDesignedBorehole> tDesignedBoreholes = new ArrayList<>();
            tDesignedBoreholes = this.tDesignedBoreholeService.getByRoadWayZoneId(tRoadwayZone.getId());
            if (tDesignedBoreholes.size() > 0) {
                for (TDesignedBorehole t : tDesignedBoreholes) {
                    double az = t.getAzimuthAngle() + diff;
                    if (az > 360) {
                        az = az - 360;
                    } else if (az < 0) {
                        az = az + 360;
                    }
                    t.setAzimuthAngle(az);
                    this.tDesignedBoreholeService.updateTDesignedBorehole(t);
                }
            }
        }
        tRoadwayZoneMapper.updateTRoadwayZone(tRoadwayZone);
        return Result.buildResult(Result.Status.OK);
    }

    /**
     * 批量删除区段信息
     *
     * @param ids 需要删除的区段信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTRoadwayZoneByIds(List<Integer> ids) {
        for (Integer id : ids) {
            this.tActualBoreholeService.delByRoadwayZoneId(id);
            this.tDesignedBoreholeService.delByRoadwayZoneId(id);
        }
        return tRoadwayZoneMapper.deleteTRoadwayZoneByIds(ids);
    }

    /**
     * 删除区段信息信息
     *
     * @param id 区段信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTRoadwayZoneById(Integer id) {
        this.tDesignedBoreholeService.delByRoadwayZoneId(id);
        this.tActualBoreholeService.delByRoadwayZoneId(id);
        return tRoadwayZoneMapper.deleteTRoadwayZoneById(id);
    }

    @Override
    public List<TRoadwayZone> list() {
        return this.tRoadwayZoneMapper.list();
    }

    @Override
    public Integer insertTRoadwayZoneList(List<TRoadwayZone> list) {
        int r = 0;
        if (list.size() > 0) {
            for (TRoadwayZone tz : list) {
                int re = this.tRoadwayZoneMapper.insertTRoadwayZone(tz);
                if (re > 0) {
                    r++;
                }
            }
        }
        return r;
    }

    @Override
    public List<TRoadwayZone> getByRoadwayId(Integer RoadwayId) {
        return this.tRoadwayZoneMapper.getByRoadwayId(RoadwayId);
    }

    @Override
    public Integer delByRoadWayId(Integer RoadWayId) {
        //根据巷道ID查询区段ID列表
        List<Integer> roadwayZoneIds = this.tRoadwayZoneMapper.getIdsByRoadWayId(RoadWayId);
        if (roadwayZoneIds.size() != 0) {
            this.tDesignedBoreholeService.delByRoadwayZoneIds(roadwayZoneIds);
            this.tActualBoreholeService.delByRoadwayZoneIds(roadwayZoneIds);
            this.tBkActBoreholeInfoService.delByRoadwayZoneIds(roadwayZoneIds);
            this.tStripedesignparameterMapper.delByRoadWayZoneIds(roadwayZoneIds);
        }
        return this.tRoadwayZoneMapper.delByRoadWayId(RoadWayId);
    }

    /**
     * 根据巷道ID删除区段信息
     *
     * @param roadWayIds 巷道ID
     * @return 列表
     */
    @Override
    public Integer delByRoadWayIds(List<Integer> roadWayIds) {
        List<Integer> roadwayZoneIds = new ArrayList<>();
        for (Integer roadWayId : roadWayIds) {
            roadwayZoneIds.addAll(this.tRoadwayZoneMapper.getIdsByRoadWayId(roadWayId));
        }
        if (roadwayZoneIds.size() != 0) {
            this.tDesignedBoreholeService.delByRoadwayZoneIds(roadwayZoneIds);
            this.tActualBoreholeService.delByRoadwayZoneIds(roadwayZoneIds);
            this.tBkActBoreholeInfoService.delByRoadwayZoneIds(roadwayZoneIds);
            this.tStripedesignparameterMapper.delByRoadWayZoneIds(roadwayZoneIds);
        }
        return this.tRoadwayZoneMapper.delByRoadWayIds(roadWayIds);
    }

    /**
     * 根据巷道ID查询区段ID列表
     *
     * @param roadWayId 巷道ID
     * @return 区段ID列表
     */
    @Override
    public List<Integer> getIdsByRoadWayId(Integer roadWayId) {
        return this.tRoadwayZoneMapper.getIdsByRoadWayId(roadWayId);
    }

    /**
     * 根据区段名称查询区段ID
     *
     * @param zoneName 区段名称
     * @return ID
     */
    @Override
    public Integer getIdByZoneName(String zoneName, Integer roadWayId) {
        return this.tRoadwayZoneMapper.getIdByZoneName(zoneName, roadWayId);
    }

    /**
     * 获取区段名称
     *
     * @return
     */
    @Override
    public List<String> getZoneNameAll() {
        return this.tRoadwayZoneMapper.getZoneNameAll();
    }


}
