package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.business.service.ITDesSchemeInfoService;
import com.ruoyi.hh.hhhz.mapper.TDesignParameterMapper;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.physical.mapper.TDesignedBoreholeMapper;
import com.ruoyi.hh.physical.mapper.TNavigationPointMapper;
import com.ruoyi.hh.physical.mapper.TRoadwayMapper;
import com.ruoyi.hh.physical.mapper.TRoadwayZoneMapper;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import com.ruoyi.hh.physical.service.ITDesignedFlushingService;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfCoalMiningService;
import com.ruoyi.hh.util.Result;
import com.ruoyi.hh.util.mapper.TBoreholeCategoryMapper;
import com.ruoyi.hh.util.mapper.TPurposeBoreholeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 设计钻孔Service业务层处理
 *
 * @author eleven
 * @date 2022-07-12
 */
@Service
public class TDesignedBoreholeServiceImpl implements ITDesignedBoreholeService {
    @Resource
    private TDesignedBoreholeMapper tDesignedBoreholeMapper;
    @Resource
    private ITDesignedFlushingService tDesignedFlushingService;
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
    private TDesignParameterMapper tDesignParameterMapper;

    @Resource
    private ITDesSchemeInfoService itDesSchemeInfoService;

    @Resource
    private ITWorkingFaceOfCoalMiningService coalMiningWorkFaceService;

    /**
     * 查询设计钻孔
     *
     * @param id 设计钻孔主键
     * @return 设计钻孔
     */
    @Override
    public TDesignedBorehole selectTDesignedBoreholeById(Integer id) {
        return tDesignedBoreholeMapper.selectTDesignedBoreholeById(id);
    }

    /**
     * 查询设计钻孔列表
     *
     * @param tDesignedBorehole 设计钻孔
     * @return 设计钻孔
     */
    @Override
    public List<TDesignedBorehole> selectTDesignedBoreholeList(TDesignedBorehole tDesignedBorehole) {
        List<Integer> workFaceIds = coalMiningWorkFaceService.getWorkFaceByRoadwayId(tDesignedBorehole.getRoadwayId());
        int choose = -1;
        for (Integer workFaceId : workFaceIds) {
            Integer i = itDesSchemeInfoService.checkExistChooseScheme(workFaceId);
            if (i != -1) {
                choose = i;
                break;
            }
        }
        if (choose > -1) {
            tDesignedBorehole.setSign(1);
        } else tDesignedBorehole.setSign(0);
        return tDesignedBoreholeMapper.selectTDesignedBoreholeList(tDesignedBorehole);
    }

    /**
     * 新增设计钻孔
     *
     * @param tDesignedBorehole 设计钻孔
     */
    @Override
    public Result insertTDesignedBorehole(TDesignedBorehole tDesignedBorehole) {
        StringBuffer stringBuffer = new StringBuffer();
//        if(checkDBoreholeInfo(tDesignedBorehole).getStatus()==-1) {
//            return checkDBoreholeInfo(tDesignedBorehole);
//        }
//        TDesignedBorehole res = checkholeNo(tDesignedBorehole.getId(),tDesignedBorehole.getRoadwayid(),tDesignedBorehole.getGroupid(), tDesignedBorehole.getBoreholeno());
//        //查询组号和孔号联合是否有重复
//        if (res !=null) {
//            return Result.buildResult(Result.Status.EXIT);
//        }
//        //excel导入通过区段名称查询区段ID
//        if(tDesignedBorehole.getZonename()!=null&&tDesignedBorehole.getRoadwayzoneid()==0){
//            if(this.tRoadwayZoneMapper.getIdByZoneName(tDesignedBorehole.getZonename(),tDesignedBorehole.getRoadwayid())!=null){
//                tDesignedBorehole.setRoadwayzoneid(this.tRoadwayZoneMapper.getIdByZoneName(tDesignedBorehole.getZonename(),tDesignedBorehole.getRoadwayid()));
//            }
//        }
//        //excel导入通过巷道名称查询巷道ID
//        if(tDesignedBorehole.getRoadway()!=null&&tDesignedBorehole.getRoadwayid()==0){
//            if(this.tRoadwayMapper.getIdByRoadWayName(tDesignedBorehole.getRoadway())!=null){
//                tDesignedBorehole.setRoadwayid(this.tRoadwayMapper.getIdByRoadWayName(tDesignedBorehole.getRoadway()));
//            }
//        }
//        //excel导入，通过导线点名称查询导线点ID
//        if(tDesignedBorehole.getNavigationpointname()!=null&&tDesignedBorehole.getNavigationpointid()==0){
//            if(this.tNavigationPointMapper.getIdByNavigationPointName(tDesignedBorehole.getNavigationpointname(),tDesignedBorehole.getRoadwayid())!=null){
//                tDesignedBorehole.setNavigationpointid(this.tNavigationPointMapper.getIdByNavigationPointName(tDesignedBorehole.getNavigationpointname(),tDesignedBorehole.getRoadwayid()));
//            }
//        }
//        //excel导入，通过钻孔类型名称查询钻孔类型ID
//        if(tDesignedBorehole.getBoreholecategory()!=null&&tDesignedBorehole.getBoreholecategoryid()==0){
//            if(this.tBoreholeCategoryMapper.getIdByBoreholeCategory(tDesignedBorehole.getBoreholecategory())!=null){
//                tDesignedBorehole.setBoreholecategoryid(this.tBoreholeCategoryMapper.getIdByBoreholeCategory(tDesignedBorehole.getBoreholecategory()));
//            }
//        }
//        //excel导入，通过钻孔用途查询钻孔用途ID
//        if(tDesignedBorehole.getPurposeborehole()!=null&&tDesignedBorehole.getPurposeboreholeid()==0){
//            if(this.tPurposeBoreholeMapper.getIdByPurposeBorehole(tDesignedBorehole.getPurposeborehole())!=null){
//                tDesignedBorehole.setPurposeboreholeid(this.tPurposeBoreholeMapper.getIdByPurposeBorehole(tDesignedBorehole.getPurposeborehole()));
//            }
//        }
        Integer result = this.tDesignedBoreholeMapper.insertTDesignedBorehole(tDesignedBorehole);
        if (result > 0) {
            return Result.buildResult(Result.Status.OK, stringBuffer);
        } else {
            return Result.buildResult(Result.Status.ERROR, stringBuffer);
        }
    }

    /**
     * 修改设计钻孔
     *
     * @param tDesignedBorehole 设计钻孔
     * @return 结果
     */
    @Override
    public Result updateTDesignedBorehole(TDesignedBorehole tDesignedBorehole) {
        if (checkDBoreholeInfo(tDesignedBorehole).getStatus() == -1) {
            return checkDBoreholeInfo(tDesignedBorehole);
        }
        TDesignedBorehole res = checkholeNo(tDesignedBorehole.getId(), tDesignedBorehole.getRoadwayId(), tDesignedBorehole.getGroupNo(), tDesignedBorehole.getBoreholeNo());
        //查询组号和孔号联合是否有重复
        //@CreateTime: 2023/7/24 16:59 设计钻孔有可能重复，查重废弃
//        if (res !=null &&res.getId()!=tDesignedBorehole.getId()) {
//           return Result.buildResult(Result.Status.EXIT);
//        }
        Integer result = this.tDesignedBoreholeMapper.updateTDesignedBorehole(tDesignedBorehole);
        if (result > 0) {
            return Result.buildResult(Result.Status.OK);
        } else {
            return Result.buildResult(Result.Status.ERROR);
        }
//        return tDesignedBoreholeMapper.updateTDesignedBorehole(tDesignedBorehole);
    }

    /**
     * 批量删除设计钻孔
     *
     * @param ids 需要删除的设计钻孔主键
     * @return 结果
     */
    @Override
    public Integer deleteTDesignedBoreholeByIds(List<Integer> ids) {
        //首先删除相关的设计冲孔
        this.tDesignedFlushingService.delByBoreholeIds(ids);
        return tDesignedBoreholeMapper.deleteTDesignedBoreholeByIds(ids);
    }

    /**
     * 删除设计钻孔信息
     *
     * @param id 设计钻孔主键
     * @return 结果
     */
    @Override
    public Integer deleteTDesignedBoreholeById(Integer id) {
        this.tDesignedFlushingService.delByBoreholeId(id);
        return tDesignedBoreholeMapper.deleteTDesignedBoreholeById(id);
    }

    /*@Override
    public void insertTDesignedBoreholeList(List<TDesignedBorehole> list) {
        tDesignedBoreholeMapper.insertTDesignedBoreholeList(list);
    }*/

    @Override
    public List<TDesignedBorehole> getPageList() {
        return this.tDesignedBoreholeMapper.getPageList();
    }

    @Override
    public List<TDesignedBorehole> getByGroupId(String groupNo) {
        return this.tDesignedBoreholeMapper.getByGroupId(groupNo);
    }

    @Override
    public List<TDesignedBorehole> getByRoadWayIdAndpurposeboreId(Integer roadwayId, Integer PurposeBoreholeId) {
        return this.tDesignedBoreholeMapper.getByRoadWayIdAndpurposeboreId(roadwayId, PurposeBoreholeId);
    }


    @Override
    public Integer delAll() {
        //删除设计钻孔前，删除所有设计冲孔信息
        this.tDesignedFlushingService.delAll();
        return this.tDesignedBoreholeMapper.delAll();
    }

    /**
     * 根据巷道ID删除钻场信息
     *
     * @param roadwayId 巷道ID
     * @return 列表
     */
    @Override
    public Integer delByRoadWayId(Integer roadwayId) {
        //删除设计钻孔的前提是查询出对应的设计冲孔，并删除
        List<Integer> boreholeids = this.tDesignedBoreholeMapper.getIdsByRoadWayId(roadwayId);
        if (boreholeids.size() > 0) {
            this.tDesignedFlushingService.delByBoreholeIds(boreholeids);
        }
        return this.tDesignedBoreholeMapper.delByRoadWayId(roadwayId);
    }

    /**
     * 根据巷道ID删除钻场信息
     *
     * @param roadWayIds 巷道ID
     * @return 列表
     */
    @Override
    public Integer delByRoadWayIds(List<Integer> roadWayIds) {
        //删除设计钻孔的前提是查询出对应的设计冲孔，并删除
        List<Integer> boreholeids = new ArrayList<>();
        for (Integer i : roadWayIds) {
            boreholeids.addAll(this.tDesignedBoreholeMapper.getIdsByRoadWayId(i));
        }
        if (boreholeids.size() > 0) {
            this.tDesignedFlushingService.delByBoreholeIds(boreholeids);
        }
        return this.tDesignedBoreholeMapper.delByRoadWayIds(roadWayIds);
    }

    /**
     * 通过巷道ID获取设计钻孔ID列表
     *
     * @param roadwayId 巷道ID
     * @return 列表
     */
    @Override
    public List<Integer> getIdsByRoadWayId(Integer roadwayId) {
        return this.tDesignedBoreholeMapper.getIdsByRoadWayId(roadwayId);
    }

    @Override
    public Integer delByRoadwayZoneId(Integer roadwayZoneId) {
        List<Integer> boreholeids = this.tDesignedBoreholeMapper.getIdsRoadWayZoneId(roadwayZoneId);
        if (boreholeids.size() > 0) {
            this.tDesignedFlushingService.delByBoreholeIds(boreholeids);
        }
        return this.tDesignedBoreholeMapper.delByRoadwayZoneId(roadwayZoneId);
    }

    @Override
    public Integer delByRoadwayZoneIds(List<Integer> roadwayZoneIds) {
        List<Integer> boreholeids = new ArrayList<>();
        for (Integer i : roadwayZoneIds) {
            boreholeids.addAll(this.tDesignedBoreholeMapper.getIdsRoadWayZoneId(i));
        }
        if (boreholeids.size() > 0) {
            this.tDesignedFlushingService.delByBoreholeIds(boreholeids);
        }
        return this.tDesignedBoreholeMapper.delByRoadwayZoneIds(roadwayZoneIds);
    }

    @Override
    public Integer delByNavigationPointId(Integer navigationPointId) {
        List<Integer> boreholeids = this.tDesignedBoreholeMapper.getIdsNavigationPointId(navigationPointId);
        if (boreholeids.size() > 0) {
            this.tDesignedFlushingService.delByBoreholeIds(boreholeids);
        }
        return this.tDesignedBoreholeMapper.delByNavigationPointId(navigationPointId);
    }

    @Override
    public Integer delNavigationPointIds(List<Integer> navigationPointIds) {
        List<Integer> boreholeids = new ArrayList<>();
        for (Integer i : navigationPointIds) {
            boreholeids.addAll(this.tDesignedBoreholeMapper.getIdsNavigationPointId(i));
        }
        if (boreholeids.size() > 0) {
            this.tDesignedFlushingService.delByBoreholeIds(boreholeids);
        }
        return this.tDesignedBoreholeMapper.delNavigationPointIds(navigationPointIds);
    }

    /**
     * 根据区段ID获取设计钻孔列表
     *
     * @param roadwayZoneId 区段ID
     * @return 设计钻孔列表
     */
    @Override
    public List<TDesignedBorehole> getByRoadWayZoneId(Integer roadwayZoneId) {
        return this.tDesignedBoreholeMapper.getByRoadWayZoneId(roadwayZoneId);
    }

    /**
     * 根据巷道ID查询设计钻孔列表
     *
     * @param roadwayId 巷道ID
     * @return 设计钻孔列表
     */
    @Override
    public List<TDesignedBorehole> getByRoadwayId(Integer roadwayId) {
        return this.tDesignedBoreholeMapper.getByRoadwayId(roadwayId);
    }

    /**
     * 检查组号和孔号是否相同
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号
     * @param boreholeNo 孔号
     * @return 结果
     */
    public TDesignedBorehole checkholeNo(Integer id, Integer roadwayId, String groupNo, String boreholeNo) {
        List<TDesignedBorehole> list = this.tDesignedBoreholeMapper.getByRoadwayId(roadwayId);
        if (list.size() > 0) {
            for (TDesignedBorehole tDesignedBorehole : list) {
                if (tDesignedBorehole.getGroupNo() == groupNo) {
                    if (tDesignedBorehole.getBoreholeNo() == boreholeNo && tDesignedBorehole.getId() != id) {
                        return tDesignedBorehole;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 检查设计钻孔信息是否有误
     *
     * @param tDesignedBorehole 钻孔信息
     * @return 结果
     */
    public Result checkDBoreholeInfo(TDesignedBorehole tDesignedBorehole) {
        if (tDesignedBorehole.getRoadwayId() > 0 && this.tRoadwayMapper.selectTRoadwayById(tDesignedBorehole.getRoadwayId()) == null) {
            return Result.buildResult(Result.Status.ERROR, "巷道信息有误");
        }
        if (tDesignedBorehole.getRoadwayZoneId() > 0 && this.tRoadwayZoneMapper.selectTRoadwayZoneById(tDesignedBorehole.getRoadwayZoneId()) == null) {
            return Result.buildResult(Result.Status.ERROR, "区段信息有误");
        }
        if (tDesignedBorehole.getNavigationPointId() > 0 && this.tNavigationPointMapper.selectTNavigationPointById(tDesignedBorehole.getNavigationPointId()) == null) {
            return Result.buildResult(Result.Status.ERROR, "导线点信息有误");
        }
        if (tDesignedBorehole.getCategoryCode() > 0 && this.tBoreholeCategoryMapper.selectTBoreholeCategoryById(tDesignedBorehole.getCategoryCode()) == null) {
            return Result.buildResult(Result.Status.ERROR, "钻孔类型信息有误");
        }
        if (tDesignedBorehole.getPurposeBoreholeId() > 0 && this.tPurposeBoreholeMapper.selectTPurposeBoreholeById(tDesignedBorehole.getPurposeBoreholeId()) == null) {
            return Result.buildResult(Result.Status.ERROR, "钻孔用途信息有误");
        }
        return Result.buildResult(Result.Status.OK);
    }

    /**
     * 获取设计钻孔ID
     *
     * @param desParameterId
     * @return
     */
    public List<Integer> getBoreholeIdByParam(Integer desParameterId) {
        return this.tDesignedBoreholeMapper.getBoreholeIdByParam(desParameterId);
    }

    /**
     * 通过巷道ID，组号和孔号确定设计钻孔信息
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号
     * @param boreholeNo 孔号
     * @return
     */
    @Override
    public TDesignedBorehole findDesBoreholeInfo(Integer roadwayId, String groupNo, String boreholeNo) {
        return this.tDesignedBoreholeMapper.findDesBoreholeInfo(roadwayId, groupNo, boreholeNo);
    }

    /**
     * 根据设计参数ID删除设计钻孔信息
     *
     * @param designParameterId
     */
    @Override
    public void delByDesignParameterId(Integer designParameterId) {
        this.tDesignedBoreholeMapper.delByDesignParameterId(designParameterId);
    }

    @Override
    public Integer getNormalBoreholeCount(Integer type, Integer roadwayId) {
        return tDesignedBoreholeMapper.getNormalBoreholeCount(type, roadwayId);
    }

    /**
     * 更改设计钻孔状态（确定设计钻孔方案）
     *
     * @param ids 列表
     */
    @Override
    public void makeSureDesProgramme(Integer sign, List<Integer> ids) {
        if (ids.size() > 0) {
            this.tDesignParameterMapper.updateSign(sign, ids);
            for (Integer i : ids) {
                List<Integer> list = this.tDesignedBoreholeMapper.getIdsByDesignParameterId(i);
                if (!list.isEmpty()) {
                    this.tDesignedBoreholeMapper.makeSureDesProgramme(sign, list);
                }
            }
        }
    }
}
