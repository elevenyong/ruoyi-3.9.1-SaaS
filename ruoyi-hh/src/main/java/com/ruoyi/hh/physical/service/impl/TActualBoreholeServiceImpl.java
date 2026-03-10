package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.business.domain.TGasDrillingStandingBook;
import com.ruoyi.hh.business.domain.TSealingGrouting;
import com.ruoyi.hh.business.mapper.TDrainageParameterMapper;
import com.ruoyi.hh.business.mapper.TGasContentPressureMapper;
import com.ruoyi.hh.business.mapper.TSealingGroutingMapper;
import com.ruoyi.hh.demo.domain.TActualBorholeException;
import com.ruoyi.hh.physical.domain.TActualBorehole;
import com.ruoyi.hh.physical.mapper.*;
import com.ruoyi.hh.physical.service.ITActualBoreholeService;
import com.ruoyi.hh.physical.service.ITWorkingFaceService;
import com.ruoyi.hh.util.Result;
import com.ruoyi.hh.util.mapper.TBoreholeCategoryMapper;
import com.ruoyi.hh.util.mapper.TPurposeBoreholeMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 实钻信息相关接口之Service业务层处理
 *
 * @author eleven
 * @date 2022-07-13
 */
@Deprecated
@Service
public class TActualBoreholeServiceImpl implements ITActualBoreholeService {
    @Resource
    private TActualBoreholeMapper tActualBoreholeMapper;
    @Resource
    private TDesignedBoreholeMapper tDesignedBoreholeMapper;
    @Resource
    private TActualFlushingMapper tActualFlushingMapper;
    @Resource
    private TDrainageParameterMapper tDrainageParameterMapper;
    @Resource
    private TGasContentPressureMapper tGasContentPressureMapper;
    @Resource
    private TSealingGroutingMapper tSealingGroutingMapper;
    //钻孔类型
    @Resource
    private TBoreholeCategoryMapper tBoreholeCategoryMapper;
    //钻孔用途
    @Resource
    private TPurposeBoreholeMapper tPurposeBoreholeMapper;
    //导线点
    @Resource
    private TNavigationPointMapper tNavigationPointMapper;
    //区段
    @Resource
    private TRoadwayZoneMapper tRoadwayZoneMapper;
    //巷道
    @Resource
    private TRoadwayMapper tRoadwayMapper;
    @Resource
    private ITWorkingFaceService tWorkingFaceService;

    /**
     * 查询实钻信息相关接口之
     *
     * @param id 实钻信息相关接口之主键
     * @return 实钻信息相关接口之
     */
    @Override
    public TActualBorehole selectTActualBoreholeById(Integer id) {
        //删除实钻信息之前删除实际冲孔和实际注浆信息
        return tActualBoreholeMapper.selectTActualBoreholeById(id);
    }

    /**
     * 查询实钻信息相关接口之列表
     *
     * @param tActualBorehole 实钻信息相关接口之
     * @return 实钻信息相关接口之
     */
    @Override
    public List<TActualBorehole> selectTActualBoreholeList(TActualBorehole tActualBorehole) {
        return tActualBoreholeMapper.selectTActualBoreholeList(tActualBorehole);
    }

    /**
     * 新增实钻信息相关接口之
     *
     * @param tActualBorehole 实钻信息相关接口之
     */
    @Override
    public Result insertTActualBorehole(TActualBorehole tActualBorehole) {
//        Result r= checkDBoreholeInfo(tActualBorehole);
//        if(r.getStatus()!=0){
//            return r;
//        }
        TActualBorehole res = checkholeNo(tActualBorehole.getId(), tActualBorehole.getRoadwayId(), tActualBorehole.getGroupNo(), tActualBorehole.getBoreholeNo());
        if (res != null) {
            return Result.buildResult(Result.Status.EXIT);
        }
//        List<TActualBorehole> list = this.tActualBoreholeMapper.getPageList();
//        for (TActualBorehole tA1 : list) {
//            TDesignedBorehole tDesignedBorehole = new TDesignedBorehole();
//            tDesignedBorehole.setGroupid(tA1.getGroupid());
//            tDesignedBorehole.setBoreholeno(tA1.getBoreholeno());
//            List<TDesignedBorehole> dlist = this.tDesignedBoreholeMapper.selectTDesignedBoreholeList(tDesignedBorehole);
//            TDesignedBorehole tD;
//            if (dlist.size() > 0) {
//                tD = dlist.get(0);
//                tA1.setAdjacentboreholeNo(tD.getAdjacentboreholeNo());
//            }
//            this.tActualBoreholeMapper.updateTActualBorehole(tA1);
//        }
        if (this.tRoadwayMapper.getIdByRoadWayName(tActualBorehole.getRoadway()) != null) {
            tActualBorehole.setRoadwayId(this.tRoadwayMapper.getIdByRoadWayName(tActualBorehole.getRoadway()));
        }
        if (this.tRoadwayZoneMapper.getIdByZoneName(tActualBorehole.getZonename(), tActualBorehole.getRoadwayId()) != null) {
            tActualBorehole.setRoadwayZoneId(this.tRoadwayZoneMapper.getIdByZoneName(tActualBorehole.getZonename(), tActualBorehole.getRoadwayId()));
        }
        if (this.tNavigationPointMapper.getIdByNavigationPointName(tActualBorehole.getNavigationPointName(), tActualBorehole.getRoadwayId()) != null) {
            tActualBorehole.setNavigationPointId(this.tNavigationPointMapper.getIdByNavigationPointName(tActualBorehole.getNavigationPointName(), tActualBorehole.getRoadwayId()));
        }
        if (this.tBoreholeCategoryMapper.getIdByBoreholeCategory(tActualBorehole.getBoreholeCategor()) != null) {
            tActualBorehole.setBoreholeCategoryId(this.tBoreholeCategoryMapper.getIdByBoreholeCategory(tActualBorehole.getBoreholeCategor()));
        }
        if (this.tPurposeBoreholeMapper.getIdByPurposeBorehole(tActualBorehole.getPurposeBorehole()) != null) {
            tActualBorehole.setPurposeBoreholeId(this.tPurposeBoreholeMapper.getIdByPurposeBorehole(tActualBorehole.getPurposeBorehole()));
        }
        int re = tActualBoreholeMapper.insertTActualBorehole(tActualBorehole);
        if (re > 0) {
            return Result.buildResult(Result.Status.OK);
        } else {
            return Result.buildResult(Result.Status.ERROR);
        }
    }

    /**
     * 修改实钻信息相关接口之
     *
     * @param tActualBorehole 实钻信息相关接口之
     * @return 结果
     */
    @Override
    public Integer updateTActualBorehole(TActualBorehole tActualBorehole) {
        return tActualBoreholeMapper.updateTActualBorehole(tActualBorehole);
    }

    /**
     * 批量删除实钻信息相关接口之
     *
     * @param ids 需要删除的实钻信息相关接口之主键
     * @return 结果
     */
    @Override
    public Integer deleteTActualBoreholeByIds(List<Integer> ids) {
        delOthersByBoreholeIds(ids);
        return tActualBoreholeMapper.deleteTActualBoreholeByIds(ids);
    }

    /**
     * 删除实钻信息相关接口之信息
     *
     * @param id 实钻信息相关接口之主键
     * @return 结果
     */
    @Override
    public Integer deleteTActualBoreholeById(Integer id) {
        delOthersByBoreholeId(id);
        return tActualBoreholeMapper.deleteTActualBoreholeById(id);
    }

    /**
     * 批量添加实钻信息
     *
     * @param list 结果
     */
    @Override
    public Integer insertTActualBoreholeList(List<TActualBorehole> list) {
        return this.tActualBoreholeMapper.insertTActualBoreholeList(list);
    }

    /**
     * 分页查询接口
     *
     * @return 列表
     */
    @Override
    public List<TActualBorehole> getPageList() {
        return this.tActualBoreholeMapper.getPageList();
    }

    /**
     * 根据组号查询列表
     *
     * @param groupNo 组号ID
     * @return 列表
     */
    @Override
    public List<TActualBorehole> getBygroupNo(String groupNo) {
        return this.tActualBoreholeMapper.getByGroupNo(groupNo);
    }

    /**
     * 查询钻孔原始验收单
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号
     * @param boreholeNo 孔号
     * @param startdate  开始时间
     * @param enddate    结束时间
     * @return 列表
     */
    @Override
    public List<TActualBorehole> getAcceptanceCertificate(Integer roadwayId, String groupNo, List<Integer> boreholeNo, Date startdate, Date enddate) throws ParseException {
        return this.tActualBoreholeMapper.getAcceptanceCertificate(roadwayId, groupNo, boreholeNo, startdate, enddate);
    }

    /**
     * 根据巷道ID删除实钻信息
     *
     * @param RoadWayId 巷道ID
     * @return 结果
     */
    @Override
    public Integer delByRoadWayId(Integer RoadWayId) {
        //根据巷道ID查询实钻ID
        List<Integer> actualIds = new ArrayList<>();
        actualIds = this.tActualBoreholeMapper.getIdsByRoadWayId(RoadWayId);
        delOthersByBoreholeIds(actualIds);
        return this.tActualBoreholeMapper.delByRoadWayId(RoadWayId);

    }

    /**
     * 根据巷道ID删除实钻信息
     *
     * @param roadWayIds 巷道ID
     * @return 结果
     */
    @Override
    public Integer delByRoadWayIds(List<Integer> roadWayIds) {
        List<Integer> actualIds = new ArrayList<>();
        for (Integer roadwayId : roadWayIds) {
            actualIds.addAll(this.tActualBoreholeMapper.getIdsByRoadWayId(roadwayId));
        }
        delOthersByBoreholeIds(actualIds);
        return this.tActualBoreholeMapper.delByRoadWayIds(roadWayIds);
    }

    /**
     * 通过巷道ID获取实钻ID列表
     *
     * @param roadWayId 巷道ID
     * @return 列表
     */
    @Override
    public List<Integer> getIdsByRoadWayId(Integer roadWayId) {
        return this.tActualBoreholeMapper.getIdsByRoadWayId(roadWayId);
    }

    @Override
    public Integer delByRoadwayZoneId(Integer roadwayZoneId) {
        List<Integer> boreholeIds = new ArrayList<>();
        boreholeIds = this.tActualBoreholeMapper.getIdsRoadWayZoneId(roadwayZoneId);
        if (boreholeIds.size() > 0) {
            delOthersByBoreholeIds(boreholeIds);
        }
        return this.tActualBoreholeMapper.delByRoadwayZoneId(roadwayZoneId);
    }

    @Override
    public Integer delByRoadwayZoneIds(List<Integer> roadwayZoneIds) {
        List<Integer> boreholeIds = new ArrayList<>();
        for (Integer roadwayZoneId : roadwayZoneIds) {
            boreholeIds.addAll(this.tActualBoreholeMapper.getIdsRoadWayZoneId(roadwayZoneId));
        }
        if (boreholeIds.size() > 0) {
            delOthersByBoreholeIds(boreholeIds);
        }
        return this.tActualBoreholeMapper.delByRoadwayZoneIds(roadwayZoneIds);
    }

    @Override
    public Integer delByNavigationPointId(Integer navigationPointId) {
        List<Integer> actBoreholeids = this.tActualBoreholeMapper.getIdsNavigationPointId(navigationPointId);
        delOthersByBoreholeIds(actBoreholeids);
        return this.tActualBoreholeMapper.delByNavigationPointId(navigationPointId);
    }

    @Override
    public Integer delNavigationPointIds(List<Integer> navigationPointIds) {
        List<Integer> actBoreholeids = new ArrayList<>();
        for (Integer navigationPointId : navigationPointIds) {
            actBoreholeids.addAll(this.tActualBoreholeMapper.getIdsNavigationPointId(navigationPointId));
        }
        delOthersByBoreholeIds(actBoreholeids);
        return this.tActualBoreholeMapper.delNavigationPointIds(navigationPointIds);
    }

    @Override
    public List<Integer> getIdsRoadWayZoneId(Integer roadWayZoneId) {
        return this.tActualBoreholeMapper.getIdsRoadWayZoneId(roadWayZoneId);
    }

    /**
     * 检查组号和孔号是否相同
     *
     * @param groupNo    组号
     * @param boreholeNo 孔号
     * @return 结果
     */
    public TActualBorehole checkholeNo(Integer id, Integer roadWayId, String groupNo, String boreholeNo) {
        List<TActualBorehole> list = this.tActualBoreholeMapper.getByRoadWayId(roadWayId);
        if (list.size() > 0) {
            for (TActualBorehole tActualBorehole : list) {
                if (tActualBorehole.getGroupNo().equals(groupNo)) {
                    if (tActualBorehole.getBoreholeNo().equals(boreholeNo) && !tActualBorehole.getId().equals(id)) {
                        return tActualBorehole;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据组号绑定钻孔和测点的关系
     *
     * @param measurePointId 测点ID
     * @param groupNo        组号
     */
    public void bundleByMeasurePointId(@RequestParam Integer measurePointId, @RequestParam String groupNo) {
        this.tActualBoreholeMapper.bundleByMeasurePointId(measurePointId, groupNo);
    }

    /**
     * 根据组号解绑钻孔和测点的关系
     *
     * @param groupNo 组号
     */
    public void unbundleByMeasurePointId(@RequestParam String groupNo) {
        this.tActualBoreholeMapper.unbundleByMeasurePointId(groupNo);
    }

    @Override
    public List<TGasDrillingStandingBook> getBoreholeInfo(Integer roadWayId, List<String> groupNo, List<Integer> purposeBoreholeId, @Param("boreholeNo") List<Integer> boreholeNo, Date startdate, Date enddate) {
        if (enddate == null) {
            enddate = new Date();
        }
        return this.tActualBoreholeMapper.getBoreholeInfo(roadWayId, groupNo, purposeBoreholeId, boreholeNo, startdate, enddate);
    }

    @Override
    public Integer getId(@RequestParam Integer roadwayId, @RequestParam String groupNo, @RequestParam Integer boreholeId) {
        return this.tActualBoreholeMapper.getId(roadwayId, groupNo, boreholeId);
    }

    /**
     * 根据实钻ID删除其他信息
     *
     * @param boreholeIds 实钻信息
     */
    public void delOthersByBoreholeIds(List<Integer> boreholeIds) {
        if (boreholeIds.size() > 0) {
            //删除实钻冲孔信息
            this.tActualFlushingMapper.delByBoreholeIds(boreholeIds);
            //删除实际注浆信息
            this.tSealingGroutingMapper.delByBoreholeIds(boreholeIds);
            //删除钻孔抽采参数测量
            this.tDrainageParameterMapper.delByBoreholeIds(boreholeIds);
            //删除瓦斯含量/压力台帐
            this.tGasContentPressureMapper.delByBoreholeIds(boreholeIds);
        }
    }

    /**
     * 根据实钻ID列表删除其他信息
     *
     * @param boreholeId 实钻信息
     */
    public void delOthersByBoreholeId(Integer boreholeId) {
        //删除实钻冲孔信息
        this.tActualFlushingMapper.delByBoreholeId(boreholeId);
        //删除实际注浆信息
        this.tSealingGroutingMapper.delByBoreholeId(boreholeId);
        //删除钻孔抽采参数测量
        this.tDrainageParameterMapper.delByBoreholeId(boreholeId);
        //删除瓦斯含量/压力台帐
        this.tGasContentPressureMapper.delByBoreholeId(boreholeId);
    }


    /**
     * 检查钻孔信息是否有误
     *
     * @param tActualBorehole 钻孔信息
     * @return 结果
     */
    public Result checkDBoreholeInfo(TActualBorehole tActualBorehole) {
        if (tActualBorehole.getRoadwayId() > 0 && this.tRoadwayMapper.selectTRoadwayById(tActualBorehole.getRoadwayId()) == null) {
            return Result.buildResult(Result.Status.OTHER_ERROR, "巷道信息有误");
        }
        if (tActualBorehole.getRoadwayZoneId() > 0 && this.tRoadwayZoneMapper.selectTRoadwayZoneById(tActualBorehole.getRoadwayZoneId()) == null) {
            return Result.buildResult(Result.Status.OTHER_ERROR, "区段信息有误");
        }
        if (tActualBorehole.getNavigationPointId() > 0 && this.tNavigationPointMapper.selectTNavigationPointById(tActualBorehole.getNavigationPointId()) == null) {
            return Result.buildResult(Result.Status.OTHER_ERROR, "导线点信息有误");
        }
        if (tActualBorehole.getBoreholeCategoryId() > 0 && this.tBoreholeCategoryMapper.selectTBoreholeCategoryById(tActualBorehole.getBoreholeCategoryId()) == null) {
            return Result.buildResult(Result.Status.OTHER_ERROR, "钻孔类型信息有误");
        }
        if (tActualBorehole.getPurposeBoreholeId() > 0 && this.tPurposeBoreholeMapper.selectTPurposeBoreholeById(tActualBorehole.getPurposeBoreholeId()) == null) {
            return Result.buildResult(Result.Status.OTHER_ERROR, "钻孔用途信息有误");
        }
        return Result.buildResult(Result.Status.OK);
    }

    /**
     * 根据巷道Id查询组号列表
     *
     * @param roadwayId 巷道ID
     * @return 列表
     */
    public List<String> getGroupNosByRoadwayId(@Param("roadwayId") Integer roadwayId) {
        return this.tActualBoreholeMapper.getGroupNosByRoadwayId(roadwayId);
    }

    /**
     * 根据巷道ID和组号查询孔号
     *
     * @param roadwayId 巷道ID
     * @param groupNo   组号
     * @return 孔号列表
     */
    public List<Integer> getBoreholenoByRoadwayIdAndGroupNo(Integer roadwayId, String groupNo) {
        return this.tActualBoreholeMapper.getBoreholenoByRoadwayIdAndGroupNo(roadwayId, groupNo);
    }

    @Override
    public List<TSealingGrouting> getIdAndBoreholeNo(Integer roadwayId, String groupNo) {
        return this.tActualBoreholeMapper.getIdAndBoreholeNo(roadwayId, groupNo);
    }

    /**
     * 根据巷道名称、组号、孔号查询实钻ID
     *
     * @param roadway    巷道名称
     * @param groupNo    组号
     * @param boreholeNo 孔号
     * @return 实钻ID
     */
    public Integer getIdByRoadWayAndGroupNoBoreholeno(String roadway, String groupNo, Integer boreholeNo) {
        return this.tActualBoreholeMapper.getIdByRoadWayAndGroupNoBoreholeno(roadway, groupNo, boreholeNo);
    }

    /**
     * 获取钻孔异常列表
     *
     * @return
     */
    public List<TActualBorholeException> selectTActualBoreholeWithException() {
        return this.tActualBoreholeMapper.selectTActualBoreholeWithException();
    }

    /**
     * 根据工作面ID查询实钻信息列表
     * @param workfaceid 工作面ID
     * @return 实钻列表
     */
    public List<TActualBorehole> getByWorkfaceId(Integer workfaceid){
        return this.tActualBoreholeMapper.getByWorkfaceId(workfaceid);
    }

    /**
     * 达标评判之实测瓦斯含量压力情况
     * @param workfaceid 工作面ID
     * @return 列表
     */
    public List<TActualBorehole> getGasContentPressureByWorkfaceid(Integer workfaceid){
        return this.tActualBoreholeMapper.getGasContentPressureByWorkfaceid(workfaceid);
    }
}
