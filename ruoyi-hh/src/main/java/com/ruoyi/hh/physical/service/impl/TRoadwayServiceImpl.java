package com.ruoyi.hh.physical.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.physical.domain.*;
import com.ruoyi.hh.physical.mapper.TRoadwayMapper;
import com.ruoyi.hh.physical.service.*;
import com.ruoyi.hh.util.Result;
import com.ruoyi.hh.util.service.ITBoreholesiteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 巷道的增删改查Service业务层处理
 *
 * @author eleven
 * @date 2022-07-12
 */
@Service
public class TRoadwayServiceImpl implements ITRoadwayService {
    @Resource
    private TRoadwayMapper tRoadwayMapper;
    @Resource
    private ITNavigationPointService tNavigationPointService;
    @Resource
    private ITRoadwayZoneService tRoadwayZoneService;
    @Resource
    private ITWorkingFaceService tWorkingFaceService;
    @Resource
    private ITRoadtypeService tRoadtypeService;
    @Resource
    private ITFaultShapeService tFaultShapeService;
    @Resource
    private ITMeasuringPointService tMeasuringPointService;
    @Resource
    private ITBoreholesiteService tBoreholesiteService;

    @Resource
    private ITWorkingFaceOfCoalMiningService wfCoalMiningService;
    @Resource
    private ITBkActBoreholeInfoService actBoreholeInfoService;

    @Resource
    private ITActDirectionalBoreholeService actDirectionalBoreholeService;

    /**
     * 查询巷道的增删改查
     *
     * @param id 巷道的增删改查主键
     * @return 巷道的增删改查
     */
    @Override
    public TRoadway selectTRoadwayById(Integer id) {
        TRoadway tRoadway = tRoadwayMapper.selectTRoadwayById(id);
        if (tRoadway == null) {
            return null;
        }
        List<TNavigationPoint> tNavigationPoints = this.tNavigationPointService.getByRoadwayId(tRoadway.getId());
        // 不再使用巷道区段信息
        //List<TRoadwayZone> tRoadwayZones = this.tRoadwayZoneService.getByRoadwayId(tRoadway.getId());

        // 设置超前探数据
        // 超前探数据被覆盖
        /*List<TBkActBoreholeInfo> advanceBoreholeInfos = actBoreholeInfoService.selectAdvanceExplorationDrillByRoadwayId(tRoadway.getId());
        tRoadway.setAdvanceExplorationDrillList(advanceBoreholeInfos);*/

        // 设置参与构建煤层的实钻普钻数据（包含超前探数据）
        TBkActBoreholeInfo tBkActBoreholeInfo = new TBkActBoreholeInfo();
        tBkActBoreholeInfo.setRoadwayId(tRoadway.getId());
        tBkActBoreholeInfo.setIsUsedCreateCoal(1);

        List<TBkActBoreholeInfo> actBoreholeInfos = actBoreholeInfoService.selectTBkActBoreholeInfoList(tBkActBoreholeInfo);
        tRoadway.setAdvanceExplorationDrillList(actBoreholeInfos);

        // 设置参与构建煤层的定向钻数据
        List<TActDirectionalBorehole> actDirectionalBoreholes = actDirectionalBoreholeService.getUsedDirectionalByRoadwayId(tRoadway.getId());
        tRoadway.setUsedDirectDrillList(actDirectionalBoreholes);

        // 设置导线点信息
        tRoadway.setTNavigationPointList(tNavigationPoints);

        // 设置巷道区段信息（不再使用巷道区段）
        //tRoadway.setTroadwayZoneList(tRoadwayZones);


        return tRoadway;
    }

    /**
     * 查询巷道的增删改查列表
     *
     * @param tRoadway 巷道的增删改查
     * @return 巷道的增删改查
     */
    @Override
    public List<TRoadway> selectTRoadwayList(TRoadway tRoadway) {
        List<TRoadway> list = new ArrayList<>();
        list = tRoadwayMapper.selectTRoadwayList(tRoadway);
        if (list.size() > 0) {
            for (TRoadway t : list) {
                List<TRoadwayZone> tRoadwayZoneList = tRoadwayZoneService.getByRoadwayId(t.getId());
                List<TNavigationPoint> tNavigationPointList = tNavigationPointService.getByRoadwayId(t.getId());
                t.setTroadwayZoneList(tRoadwayZoneList);
                t.setTNavigationPointList(tNavigationPointList);
                // 设置超前探数据
                List<TBkActBoreholeInfo> actBoreholeInfos = actBoreholeInfoService.selectAdvanceExplorationDrillByRoadwayId(t.getId());
                t.setAdvanceExplorationDrillList(actBoreholeInfos);
            }
        }
        return list;
    }

    /**
     * 获取巷道列表
     *
     * @return 巷道列表
     */
    @Override
    public List<TRoadway> getPageList() {
        return this.tRoadwayMapper.getPageList();
    }

    /**
     * 新增巷道的增删改查
     *
     * @param tRoadway 巷道的增删改查
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult insertTRoadway(TRoadway tRoadway) {
        /*List<TRoadwayZone> tRoadwayZones = tRoadway.getTroadwayZoneList();
        if (tRoadwayZones != null && tRoadwayZones.size() > 0) {
            for (TRoadwayZone tRoadwayZone : tRoadwayZones) {
                this.tRoadwayZoneService.insertTRoadwayZone(tRoadwayZone);
            }
        }*/
        /*List<TNavigationPoint> tNavigationPoints = tRoadway.getTNavigationPointList();
        if (tNavigationPoints != null && !tNavigationPoints.isEmpty()) {
            for (TNavigationPoint tNavigationPoint : tNavigationPoints) {
                this.tNavigationPointService.insertTNavigationPoint(tNavigationPoint);
            }
        }*/
        if (tRoadway.getRoadwayType() != null && tRoadway.getType() == 0) {
            if (tRoadtypeService.getIdByType(tRoadway.getRoadwayType()) != null) {
                tRoadway.setType(tRoadtypeService.getIdByType(tRoadway.getRoadwayType()));
            }
        }
        if (tRoadway.getShape() != null && tRoadway.getFaultShapId() == 0) {
            if (tFaultShapeService.getIdByShape(tRoadway.getShape()) != null) {
                tRoadway.setFaultShapId(tFaultShapeService.getIdByShape(tRoadway.getShape()));
            }
        }
        /*// 根据巷道名称获取巷道ID
        if (tRoadwayMapper.getIdByRoadWayName(tRoadway.getRoadway()) != null) {
            return Result.buildResult(Result.Status.EXIT);
        }*/
        if (tRoadwayMapper.insertTRoadway(tRoadway) > 0) {
            // 更新工作面数据
            TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining = wfCoalMiningService.selectTWorkingFaceOfCoalMiningById(tRoadway.getWorkfaceId());
            if (tWorkingFaceOfCoalMining != null) {
                switch (tRoadway.getType()) {
                    case 1:
                        tWorkingFaceOfCoalMining.setAirIntakeRoadway(tRoadway.getId());
                        break;
                    case 2:
                        tWorkingFaceOfCoalMining.setAirReturnRoadway(tRoadway.getId());
                        break;
                    case 3:
                        tWorkingFaceOfCoalMining.setCutEyeRoadway(tRoadway.getId());
                        break;
                    case 4:
                        tWorkingFaceOfCoalMining.setDcAirIntakeRoadway(tRoadway.getId());
                        break;
                    case 5:
                        tWorkingFaceOfCoalMining.setDcAirReturnRoadway(tRoadway.getId());
                        break;
                    case 6:
                        tWorkingFaceOfCoalMining.setDcCutEyeRoadway(tRoadway.getId());
                        break;
                    case 7:
                        tWorkingFaceOfCoalMining.setDcMiddleRoadway(tRoadway.getId());
                        break;
                    case 9:
                        tWorkingFaceOfCoalMining.setMiddleRoadway(tRoadway.getId());
                        break;
                    case 8:
                        // 其他巷道
                        Integer[] otherRoadway = tWorkingFaceOfCoalMining.getOtherRoadway();
                        List<Integer> list;
                        if (otherRoadway != null) {
                            list = new ArrayList<>(Arrays.asList(otherRoadway));
                        } else {
                            list = new ArrayList<>();
                        }
                        list.add(tRoadway.getId());
                        Integer[] array = list.toArray(new Integer[0]);
                        tWorkingFaceOfCoalMining.setOtherRoadway(array);
                        break;
                }
                wfCoalMiningService.updateTWorkingFaceOfCoalMining(tWorkingFaceOfCoalMining);
            } else {
                deleteTRoadwayById(tRoadway.getId());
                return AjaxResult.error("添加失败，工作面不存在！");
            }
            // 导线点
            List<TNavigationPoint> tNavigationPoints = new ArrayList<>();
            tNavigationPoints = tRoadway.getTNavigationPointList();
            if (tNavigationPoints != null) {
                List<TNavigationPoint> byRoadwayId = this.tNavigationPointService.getByRoadwayId(tRoadway.getId());
                Set<Integer> existNavPoints = new HashSet<>();
                for (TNavigationPoint tNavPoint : byRoadwayId) {
                    existNavPoints.add(tNavPoint.getId());
                }
                Set<Integer> postNavPointss = new HashSet<>();
                for (TNavigationPoint tNavPoint : tNavigationPoints) {
                    if (tNavPoint.getId() != null && tNavPoint.getId() > 0) {
                        postNavPointss.add(tNavPoint.getId());
                    }
                }
                // 取差集，然后视为删除
                for (Integer existZonesId : existNavPoints) {
                    if (!postNavPointss.contains(existZonesId)) {
                        this.tNavigationPointService.deleteTNavigationPointById(existZonesId);
                    }
                }

                int i = 1;
                List<TNavigationPoint> addedNavPoints = new ArrayList<>();
                for (TNavigationPoint tNavigationPoint : tNavigationPoints) {
                    if (tNavigationPoint.getId() != null && tNavigationPoint.getId() > 0) {
                        tNavigationPoint.setSerialNumber(i);
                        tNavigationPoint.setRoadwayId(tRoadway.getId());
                        this.tNavigationPointService.updateTNavigationPoint(tNavigationPoint);
                        addedNavPoints.add(tNavigationPoint);
                    } else {
                        tNavigationPoint.setSerialNumber(i);
                        tNavigationPoint.setRoadwayId(tRoadway.getId());
                        this.tNavigationPointService.insertTNavigationPoint(tNavigationPoint);
                        addedNavPoints.add(tNavigationPoint);
                    }
                    i++;
                }
                tRoadway.setTNavigationPointList(addedNavPoints);
            }

            return AjaxResult.success(tRoadway);
        } else {
            return AjaxResult.error("添加失败！");
        }
    }

    /**
     * 修改巷道的增删改查
     *
     * @param tRoadway 巷道的增删改查
     * @return 结果
     */
    @Override
    @Transactional
    public Integer updateTRoadway(TRoadway tRoadway) {
        // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓巷道区段，废弃不用，待后续版本删除
        List<TRoadwayZone> tRoadwayZones = new ArrayList<>();
        tRoadwayZones = tRoadway.getTroadwayZoneList();
        if (tRoadwayZones != null) {
            List<TRoadwayZone> byRoadwayId = this.tRoadwayZoneService.getByRoadwayId(tRoadway.getId());
            Set<Integer> existZonesIds = new HashSet<>();
            for (TRoadwayZone tRoadwayZone : byRoadwayId) {
                existZonesIds.add(tRoadwayZone.getId());
            }
            Set<Integer> postZonesIds = new HashSet<>();
            for (TRoadwayZone tRoadwayZone : tRoadwayZones) {
                if (tRoadwayZone.getId() > 0) {
                    postZonesIds.add(tRoadwayZone.getId());
                }
            }
            // 取差集，然后视为删除
            for (Integer existZonesId : existZonesIds) {
                if (!postZonesIds.contains(existZonesId)) {
                    this.tRoadwayZoneService.deleteTRoadwayZoneById(existZonesId);
                }
            }
            for (TRoadwayZone tRoadwayZone : tRoadwayZones) {
                if (tRoadwayZone.getId() > 0) {
                    tRoadwayZone.setRoadwayId(tRoadway.getId());
                    this.tRoadwayZoneService.updateTRoadwayZone(tRoadwayZone);
                } else {
                    tRoadwayZone.setRoadwayId(tRoadway.getId());
                    this.tRoadwayZoneService.insertTRoadwayZone(tRoadwayZone);
                }
            }
        }
        // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑巷道区段，废弃不用，待后续版本删除

        // 导线点
        List<TNavigationPoint> tNavigationPoints = new ArrayList<>();
        tNavigationPoints = tRoadway.getTNavigationPointList();
        if (tNavigationPoints != null) {
            List<TNavigationPoint> byRoadwayId = this.tNavigationPointService.getByRoadwayId(tRoadway.getId());
            Set<Integer> existNavPoints = new HashSet<>();
            for (TNavigationPoint tNavPoint : byRoadwayId) {
                existNavPoints.add(tNavPoint.getId());
            }
            Set<Integer> postNavPointss = new HashSet<>();
            for (TNavigationPoint tNavPoint : tNavigationPoints) {
                if (tNavPoint.getId() != null && tNavPoint.getId() > 0) {
                    postNavPointss.add(tNavPoint.getId());
                }
            }
            // 取差集，然后视为删除
            for (Integer existZonesId : existNavPoints) {
                if (!postNavPointss.contains(existZonesId)) {
                    this.tNavigationPointService.deleteTNavigationPointById(existZonesId);
                }
            }

            int i = 1;
            for (TNavigationPoint tNavigationPoint : tNavigationPoints) {
                if (tNavigationPoint.getId() != null && tNavigationPoint.getId() > 0) {
                    tNavigationPoint.setSerialNumber(i);
                    tNavigationPoint.setRoadwayId(tRoadway.getId());
                    this.tNavigationPointService.updateTNavigationPoint(tNavigationPoint);
                } else {
                    tNavigationPoint.setSerialNumber(i);
                    tNavigationPoint.setRoadwayId(tRoadway.getId());
                    this.tNavigationPointService.insertTNavigationPoint(tNavigationPoint);
                }
                i++;
            }
        }
        return tRoadwayMapper.updateTRoadway(tRoadway);
    }

    /**
     * 根据巷道名称查询巷道ID
     *
     * @param roadway 巷道名称
     * @return id
     */
    @Override
    public Integer getIdByRoadWayName(String roadway) {
        return this.tRoadwayMapper.getIdByRoadWayName(roadway);
    }

    /**
     * 批量删除巷道的增删改查
     *
     * @param ids 需要删除的巷道的增删改查主键
     * @return 结果
     */
    @Override
    public Integer deleteTRoadwayByIds(List<Integer> ids) {
        this.tBoreholesiteService.delByRoadWayIds(ids);
        this.tRoadwayZoneService.delByRoadWayIds(ids);
        this.tNavigationPointService.delByRoadWayIds(ids);
        this.tMeasuringPointService.delByRoadWayIds(ids);
        return tRoadwayMapper.deleteTRoadwayByIds(ids);
    }

    /**
     * 删除巷道的增删改查信息
     *
     * @param id 巷道的增删改查主键
     * @return 结果
     */
    @Override
    public Integer deleteTRoadwayById(Integer id) {

        //删除巷道下的所属信息
        //巷道下的巷道区段
        this.tRoadwayZoneService.delByRoadWayId(id);
        //删除导线点信息
        this.tNavigationPointService.delByRoadWayId(id);
        //删除钻场信息
        this.tBoreholesiteService.delByRoadWayId(id);
        //删除测点信息
        this.tMeasuringPointService.delByRoadWayId(id);
        //向前兼容，待后续删除↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓更新工作面里的绑定关系，巷道删除后移除绑定关系
            /*List<TWorkingFaceOfCoalMining> allTWorkingFaceOfCoalMining = this.wfCoalMiningService.getAllTWorkingFaceOfCoalMining();
            for (TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining : allTWorkingFaceOfCoalMining) {
                if (tWorkingFaceOfCoalMining.getAirIntakeRoadway().equals(id.toString())) {
                    tWorkingFaceOfCoalMining.setAirIntakeRoadway(null);
                }
                if (tWorkingFaceOfCoalMining.getAirReturnRoadway().equals(id.toString())) {
                    tWorkingFaceOfCoalMining.setAirReturnRoadway(null);
                }
                if (tWorkingFaceOfCoalMining.getCutEyeRoadway().equals(id.toString())) {
                    tWorkingFaceOfCoalMining.setCutEyeRoadway(null);
                }
                String extractionRoadwayStrs = tWorkingFaceOfCoalMining.getBottomExtractionRoadway();
                extractionRoadwayStrs = extractionRoadwayStrs.replaceAll("\\[,", "[null,").replaceAll(",]", ",null]").replaceAll(",,", ",null,");
                extractionRoadwayStrs = extractionRoadwayStrs.replaceAll("\\[", "").replaceAll("]", "");
                String[] split = extractionRoadwayStrs.split(",");
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals(id.toString())) {
                        split[i] = null;
                    }
                }
                StringBuilder extractionRoadwayArrayStr = new StringBuilder("[");
                for (int i = 0; i < split.length; i++) {
                    extractionRoadwayArrayStr.append(split[i]);
                    if (i < split.length - 1) {
                        extractionRoadwayArrayStr.append(",");
                    }
                }
                extractionRoadwayArrayStr.append("]");
                tWorkingFaceOfCoalMining.setBottomExtractionRoadway(extractionRoadwayArrayStr.toString());
                this.wfCoalMiningService.updateTWorkingFaceOfCoalMining(tWorkingFaceOfCoalMining);
            }*/
        //向前兼容，待后续删除↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑更新工作面里的绑定关系，巷道删除后移除绑定关系

        // 更新工作面数据
        TRoadway tRoadway = tRoadwayMapper.selectTRoadwayById(id);
        if (tRoadway != null) {
            TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining = wfCoalMiningService.selectTWorkingFaceOfCoalMiningById(tRoadway.getWorkfaceId());
            if (tWorkingFaceOfCoalMining != null) {
                switch (tRoadway.getType()) {
                    case 1:
                        tWorkingFaceOfCoalMining.setAirIntakeRoadway(0);
                        break;
                    case 2:
                        tWorkingFaceOfCoalMining.setAirReturnRoadway(0);
                        break;
                    case 3:
                        tWorkingFaceOfCoalMining.setCutEyeRoadway(0);
                        break;
                    case 4:
                        tWorkingFaceOfCoalMining.setDcAirIntakeRoadway(0);
                        break;
                    case 5:
                        tWorkingFaceOfCoalMining.setDcAirReturnRoadway(0);
                        break;
                    case 6:
                        tWorkingFaceOfCoalMining.setDcCutEyeRoadway(0);
                        break;
                    case 7:
                        tWorkingFaceOfCoalMining.setDcMiddleRoadway(0);
                        break;
                    case 8:
                        // 其他巷道
                        Integer[] otherRoadway = tWorkingFaceOfCoalMining.getOtherRoadway();
                        List<Integer> list;
                        if (otherRoadway != null) {
                            list = new ArrayList<>(Arrays.asList(otherRoadway));
                        } else {
                            list = new ArrayList<>();
                        }
                        list.remove(tRoadway.getId());
                        Integer[] array = list.toArray(new Integer[0]);
                        tWorkingFaceOfCoalMining.setOtherRoadway(array);
                        break;
                }
                wfCoalMiningService.updateTWorkingFaceOfCoalMining(tWorkingFaceOfCoalMining);
            }
        }


        //        //首先删除实钻信息
        //        this.tActualBoreholeService.delByRoadWayId(id);
        //        //删除设计钻孔信息
        //        this.tDesignedBoreholeService.delByRoadWayId(id);
        return tRoadwayMapper.deleteTRoadwayById(id);
    }

    @Override
    public Integer insertTRoadwayList(List<TRoadway> list) {
        int suc = 0;
        if (!list.isEmpty()) {
            for (TRoadway tRoadway : list) {
                if (tRoadway != null) {
                    AjaxResult r = insertTRoadway(tRoadway);
                    if ((int) r.get("code") == 200) {
                        suc++;
                    }
                }
            }
            if (suc > 0) {
                return suc;
            } else {
                return -1;
            }
        }
        return -1;
    }

    /**
     * 获取所有巷道名称
     *
     * @return
     */
    @Override
    public List<String> getRoadwayAll() {
        return this.tRoadwayMapper.getRoadwayAll();
    }

    /**
     * 获取工作面关联巷道
     *
     * @param workingFaceId 工作面ID
     * @return 关联巷道列表
     */
    @Override
    public List<TRoadway> selectRoadwayListByWorkFace(Integer workingFaceId) {
        /*ArrayList<TRoadway> result = new ArrayList<>();
        TWorkingFaceOfCoalMining workingFaceOfCoalMining = wfCoalMiningService.selectTWorkingFaceOfCoalMiningById(workingFaceId);
        if (workingFaceOfCoalMining != null) {
            String bottomRoadways = workingFaceOfCoalMining.getBottomExtractionRoadway();
            String s = bottomRoadways.replaceAll("\\[", "").replaceAll("]", "");
            String[] split = s.split(",");
            Integer airIn = workingFaceOfCoalMining.getAirIntakeRoadway();
            Integer airRet = workingFaceOfCoalMining.getAirReturnRoadway();
            Integer cutEye = workingFaceOfCoalMining.getCutEyeRoadway();
            Integer miningArea = workingFaceOfCoalMining.getMiningAreaRoadway();

            List<Integer> workFaceIdsList = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                workFaceIdsList.add(Integer.parseInt(split[i]));
            }
            //Collections.addAll(workFaceIdsList, split);
            workFaceIdsList.add(airIn);
            workFaceIdsList.add(airRet);
            workFaceIdsList.add(cutEye);
            workFaceIdsList.add(miningArea);
            for (Integer id : workFaceIdsList) {
                if (id != null && !Objects.equals(id, 0)) {
                    int i = id;
                    TRoadway tRoadway = tRoadwayMapper.selectTRoadwayById(i);
                    if (tRoadway != null) {
                        result.add(tRoadway);
                    }
                }
            }
        }*/
        List<TRoadway> result = tRoadwayMapper.selectTRoadwayByWorkingFaceId(workingFaceId);
        for (TRoadway t : result) {
            List<TRoadwayZone> tRoadwayZoneList = tRoadwayZoneService.getByRoadwayId(t.getId());
            List<TNavigationPoint> tNavigationPointList = tNavigationPointService.getByRoadwayId(t.getId());
            t.setTroadwayZoneList(tRoadwayZoneList);
            t.setTNavigationPointList(tNavigationPointList);
        }
        return result;
    }

    /**
     * 通过巷道ID获取所属的工作面ID
     *
     * @param id
     * @return
     */
    public Integer getWorkfaceByRoadwayId(Integer id) {
        return this.tRoadwayMapper.getWorkfaceByRoadwayId(id);
    }

}

