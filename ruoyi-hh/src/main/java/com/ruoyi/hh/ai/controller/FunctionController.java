package com.ruoyi.hh.ai.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.physical.controller.TWorkingFaceOfCoalMiningController;
import com.ruoyi.hh.physical.domain.*;
import com.ruoyi.hh.physical.service.*;
import com.ruoyi.hh.util.WebSocketServer;
import com.ruoyi.hh.util.domain.TBoreholeCategory;
import com.ruoyi.hh.util.service.ITBoreholeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;


/**
 * FunctionCall接口，For Dify
 */
@RestController
@RequestMapping("/func")
@Api(value = "ZFUNC模型调用相关接口", tags = "ZFUNC模型调用相关接口")
public class FunctionController {
    private static final Logger log = LoggerFactory.getLogger(FunctionController.class);

    @Resource
    private ITWorkingFaceOfCoalMiningService workfaceService;
    @Resource
    private TWorkingFaceOfCoalMiningController workingFaceController;
    @Resource
    private ITRoadwayService roadwayService;
    @Resource
    private ITBkActBoreholeInfoService boreholeService;
    @Resource
    private ITActDirectionalBoreholeService directionalBoreholeService;
    @Resource
    private ITBoreholeCategoryService categoryService;
    @Resource
    private ITWorkingFaceOfDrivingService workingFaceOfDriving;
    @Resource
    private ITAttributeTypeService attributeTypeService;
    @Resource
    private ITRockinfoService rockInfoService;
    @Resource
    private WebSocketServer webSocketServer;
    @Resource
    private ITWorkOfCoalMiningService workOfCoalMiningService;
    @Resource
    private ITActDirectionalDrillingPointService actDirectionalDrillingPointService;

    @GetMapping("/queryWorkOfCoalMining")
    @ApiOperation("回采工作面位置")
    public AjaxResult queryWorkOfCoalMining(@ApiParam(name = "workfaceId", value = "工作面ID") @RequestParam Integer workfaceId) {
        log.info("/queryWorkOfCoalMining，workfaceId={}", workfaceId);
        TWorkOfCoalMining workOfCoalMining = new TWorkOfCoalMining();
        workOfCoalMining.setWorkfaceId(workfaceId);
        List<TWorkOfCoalMining> workOfCoalMinings = workOfCoalMiningService.selectTWorkOfCoalMiningList(workOfCoalMining);
        return AjaxResult.success(workOfCoalMinings);
    }

    @GetMapping("/viewControl")
    @ApiOperation("视图控制工具")
    public AjaxResult viewControl(@ApiParam(name = "function", value = "function") @RequestParam String function, @ApiParam(name = "param", value = "function参数") @RequestParam String param) throws IOException {
        log.info("/viewControl，function={},param={}", function, param);
        WebSocketServer.sendInfo("{\"viewControl\":{\"function\":\"" + function + "\",\"param\":\"" + param + "\"}}");
        return AjaxResult.success("OK");
    }

    @GetMapping("/queryRockInfos")
    @ApiOperation("根据工作面ID获取该工作面对应的地层信息")
    public AjaxResult queryRockInfosByWorkfaceId(@ApiParam(name = "workfaceId", value = "工作面ID")
                                                 @RequestParam Integer workfaceId) {
        log.info("/queryRockInfos，workfaceId={}", workfaceId);
        TRockinfo queryRockInfo = new TRockinfo();
        queryRockInfo.setWorkfaceId(workfaceId);
        List<TRockinfo> rockInfos = rockInfoService.selectTRockinfoList(queryRockInfo);
        ArrayList<String> list = new ArrayList<>();
        rockInfos.forEach(rockInfo -> {
            list.add("{id:" + rockInfo.getId() + ",rockInfo:" + rockInfo.getRockDesc() + ",rockType:" + rockInfo.getRockTypeData().getRockType() + "}");
        });
        return AjaxResult.success(list);
    }


    @GetMapping("/queryAttributeTypes")
    @ApiOperation("获取系统内瓦斯相关参数的数据类型")
    public AjaxResult queryAttributeTypes() {
        log.info("/queryAttributeTypes");
        List<TAttributeType> attributeTypes = attributeTypeService.selectTAttributeTypeList(new TAttributeType());
        ArrayList<String> list = new ArrayList<>();
        attributeTypes.forEach(attributeType -> {
            list.add("{id:" + attributeType.getId() + ",attributeType:" + attributeType.getAttributeName() + ",unit:" + attributeType.getDataUnit() + "}");
        });
        return AjaxResult.success(list);
    }

    @GetMapping("/queryWorkingFaceOfDrivingByRoadwayId")
    @ApiOperation("根据巷道ID查询该巷道上的掘进工作面情况")
    public AjaxResult queryWorkingFaceOfDrivingByRoadwayId(@ApiParam(name = "roadwayId", value = "巷道ID")
                                                           @RequestParam Integer roadwayId) {
        log.info("/queryWorkingFaceOfDrivingByRoadwayId,roadwayId={}", roadwayId);
        TWorkingFaceOfDriving face = new TWorkingFaceOfDriving();
        face.setRoadwayId(roadwayId);
        List<TWorkingFaceOfDriving> workingFaceOfDrivings = workingFaceOfDriving.selectTWorkingFaceOfDrivingList(face);
        return AjaxResult.success(workingFaceOfDrivings);
    }

    @GetMapping("/queryRoadwayByRoadwayId")
    @ApiOperation("根据巷道ID查询该巷道信息")
    public AjaxResult queryRoadwayByRoadwayId(@ApiParam(name = "roadwayId", value = "巷道ID")
                                              @RequestParam Integer roadwayId) {
        log.info("/queryRoadwayByRoadwayId,roadwayId={}", roadwayId);
        TRoadway roadway = roadwayService.selectTRoadwayById(roadwayId);
        roadway.setUsedDirectDrillList(null);
        roadway.setAdvanceExplorationDrillList(null);
        return AjaxResult.success(roadway);
    }

    @GetMapping("/queryBoreholeCategoryList")
    @ApiOperation("工作面钻孔类型信息")
    public AjaxResult queryBoreholeCategoryList() {
        log.info("/queryBoreholeCategoryList");
        List<TBoreholeCategory> categories = categoryService.selectTBoreholeCategoryList(new TBoreholeCategory());
        ArrayList<String> categoryList = new ArrayList<>();
        categories.forEach(category -> {
            categoryList.add("{id:" + category.getCategoryCode() + ",categoryName:" + category.getBoreholeCategory() + "}");
        });
        return AjaxResult.success(categoryList);
    }

    @GetMapping("/queryBoreholeListByGroupNoAndBoreholeId")
    @ApiOperation("通过巷道ID、钻孔类型ID、钻孔组号、钻孔孔号查询钻孔信息")
    public AjaxResult queryBoreholeListByGroupNoAndBoreholeId(@ApiParam(name = "roadwayId", value = "巷道ID")
                                                              @RequestParam Integer roadwayId,
                                                              @ApiParam(name = "boreholeType", value = "钻孔类型ID")
                                                              @RequestParam Integer boreholeType,
                                                              @ApiParam(name = "groupNo", value = "钻孔组号")
                                                              @RequestParam String groupNo,
                                                              @ApiParam(name = "boreholeNo", value = "钻孔孔号")
                                                              @RequestParam(required = false) String boreholeNo) {
        log.info("/queryBoreholeListByGroupNoAndBoreholeId,roadwayId={},boreholeType={},groupNo={},boreholeNo={}", roadwayId, boreholeType, groupNo, boreholeNo);
        TBkActBoreholeInfo qBoreholeInfo = new TBkActBoreholeInfo();
        if (roadwayId != null) {
            qBoreholeInfo.setRoadwayId(roadwayId);
        } else {
            return AjaxResult.error("请确定巷道ID");
        }
        if (boreholeType != null) {
            qBoreholeInfo.setCategoryCode(boreholeType);
        } else {
            return AjaxResult.error("请确定钻孔类型");
        }
        if (groupNo.isEmpty()) {
            return AjaxResult.error("请确定钻孔组号");
        } else {
            qBoreholeInfo.setGroupNo(groupNo);
        }
        if (boreholeNo == null || boreholeNo.isEmpty()) {
            qBoreholeInfo.setBoreholeNo(null);
        }
        ArrayList<String> returnList = new ArrayList<>();

        if (boreholeType == 6 || boreholeType == 7) {
            TActDirectionalBorehole directionalBorehole = new TActDirectionalBorehole();
            directionalBorehole.setBoreholeNo(boreholeNo);
            directionalBorehole.setGroupNo(groupNo);
            directionalBorehole.setRoadwayId(roadwayId);
            directionalBorehole.setCategoryCode(boreholeType);
            List<TActDirectionalBorehole> directionalBoreholes = directionalBoreholeService.selectTActDirectionalBoreholeList(directionalBorehole);
            for (TActDirectionalBorehole borehole : directionalBoreholes) {

                TActDirectionalDrillingPoint queryPoint = new TActDirectionalDrillingPoint();
                queryPoint.setBoreholeId(borehole.getId());
                borehole.settDirectionalDrillingPointList(actDirectionalDrillingPointService.selectTActDirectionalDrillingPointList(queryPoint));

                StringBuilder sb = new StringBuilder();
                sb.append("{组号:").append(borehole.getGroupNo()).append(",");
                sb.append("孔号:").append(borehole.getBoreholeNo()).append(",");
                sb.append("开孔位置（距巷口）:").append(borehole.getDistanceToNavigationPoint()).append(",");
                sb.append("冲煤量:").append(borehole.getActualCoalAmountFlushed()).append(",");
                sb.append("孔深:").append(borehole.gettDirectionalDrillingPointList().size() * borehole.gettDirectionalDrillingPointList().get(0).getBoreholeLength()).append("}");
                returnList.add(sb.toString());
            }
            return AjaxResult.success("共查询到" + directionalBoreholes.size() + "个钻孔。" + returnList);
        } else {
            List<TBkActBoreholeInfo> boreholeInfos = boreholeService.selectTBkActBoreholeInfoList(qBoreholeInfo);
            if (boreholeInfos.size() > 20) {
                for (int i = 0; i < 20; i++) {
                    TBkActBoreholeInfo b = boreholeInfos.get(i);
                    StringBuilder sb = new StringBuilder();
                    sb.append("{组号:").append(b.getGroupNo()).append(",");
                    sb.append("孔号:").append(b.getBoreholeNo()).append(",");
                    sb.append("方位角:").append(b.getAzimuthAngle()).append(",");
                    sb.append("开孔位置（距巷口）:").append(b.getDistanceToNavigationPoint()).append(",");
                    sb.append("倾角:").append(b.getInclinationAngle()).append(",");/*
                sb.append("岩段1:").append(b.getBoreholeNo()).append(",");
                sb.append("煤段1:").append(b.getBoreholeNo()).append(",");
                sb.append("岩段2:").append(b.getBoreholeNo()).append(",");
                sb.append("煤段2:").append(b.getBoreholeNo()).append(",");
                sb.append("岩段3:").append(b.getBoreholeNo()).append(",");
                sb.append("煤段3:").append(b.getBoreholeNo()).append(",");
                sb.append("岩段4:").append(b.getBoreholeNo()).append(",");*/
                    sb.append("孔深:").append(b.getBoreholeLength()).append(",");
                    sb.append("冲煤量:").append(b.getActualCoalAmountFlushed()).append(",");
                    sb.append("钻孔异常:").append((b.getExceptionConstruction() == null || b.getExceptionConstruction().isEmpty()) ? "无" : b.getExceptionConstruction()).append("}");
                    returnList.add(sb.toString());

                }
                return AjaxResult.success("共查询到" + boreholeInfos.size() + "个钻孔。数量过多，显示前20个钻孔的信息：" + returnList);
            } else {
                boreholeInfos.forEach(b -> {
                    StringBuilder sb = new StringBuilder();
                    sb.append("{组号:").append(b.getGroupNo()).append(",");
                    sb.append("孔号:").append(b.getBoreholeNo()).append(",");
                    sb.append("方位角:").append(b.getAzimuthAngle()).append(",");
                    sb.append("钻孔位置（距巷口）:").append(b.getDistanceToNavigationPoint()).append(",");
                    sb.append("倾角:").append(b.getInclinationAngle()).append(",");/*
                sb.append("岩段1:").append(b.getBoreholeNo()).append(",");
                sb.append("煤段1:").append(b.getBoreholeNo()).append(",");
                sb.append("岩段2:").append(b.getBoreholeNo()).append(",");
                sb.append("煤段2:").append(b.getBoreholeNo()).append(",");
                sb.append("岩段3:").append(b.getBoreholeNo()).append(",");
                sb.append("煤段3:").append(b.getBoreholeNo()).append(",");
                sb.append("岩段4:").append(b.getBoreholeNo()).append(",");*/
                    sb.append("孔深:").append(b.getBoreholeLength()).append(",");
                    sb.append("冲煤量:").append(b.getActualCoalAmountFlushed()).append(",");
                    sb.append("钻孔异常:").append((b.getExceptionConstruction() == null || b.getExceptionConstruction().isEmpty()) ? "无" : b.getExceptionConstruction()).append("}");
                    returnList.add(sb.toString());
                });
                return AjaxResult.success("共查询到" + boreholeInfos.size() + "个钻孔。" + returnList);
            }
        }
    }


    @GetMapping("/queryBoreholeList")
    @ApiOperation("综合查询工作面的钻孔信息")
    public AjaxResult queryBoreholeList(@ApiParam(name = "roadwayIds", value = "巷道ID，支持传入多个巷道ID，用英文逗号分割")
                                        @RequestParam String roadwayIds,
                                        @ApiParam(name = "boreholeTypes", value = "钻孔类型ID，支持传入多个钻孔类型ID，用英文逗号分割")
                                        @RequestParam String boreholeTypes) {
        log.info("/queryBoreholeList,roadwayIds=[{}],boreholeTypes=[{}]", roadwayIds, boreholeTypes);
        String[] rds = roadwayIds.split(",");
        String[] tps = boreholeTypes.split(",");
        ArrayList<Integer> roadways = new ArrayList<>();
        ArrayList<Integer> types = new ArrayList<>();
        try {
            for (String rd : rds) {
                // 去重
                if (!roadways.contains(Integer.valueOf(rd))) {
                    roadways.add(Integer.parseInt(rd));
                }
            }
            for (String tp : tps) {
                // 去重
                if (!types.contains(Integer.valueOf(tp))) {
                    types.add(Integer.parseInt(tp));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("传入参数有误，请检查后重试！");
        }
        HashMap<String, HashMap<String, HashMap<String, List<?>>>> boreholes = workfaceService.getBoreholesDetail(roadways, types);
        boreholes.keySet().forEach(key -> {
            // key 普钻，定向钻
            boreholes.get(key).keySet().forEach(key1 -> {
                // key1 巷道ID
                boreholes.get(key).get(key1).keySet().forEach(key2 -> {

                    // key2 钻孔类型
                    if (key.equals("ordinary")) {
                        List<TBkActBoreholeInfo> holes = (List<TBkActBoreholeInfo>) boreholes.get(key).get(key1).get(key2);
                        AtomicReference<Double> length = new AtomicReference<>(0.0);
                        AtomicInteger count = new AtomicInteger(0);
                        holes.forEach(h -> {
                            length.updateAndGet(v -> new Double(v + h.getBoreholeLength()));
                            count.getAndIncrement();
                        });
                    } else {
                        List<TActDirectionalBorehole> holes = (List<TActDirectionalBorehole>) boreholes.get(key).get(key1).get(key2);
                        AtomicReference<Double> length = new AtomicReference<>(0.0);
                        AtomicInteger count = new AtomicInteger(0);
                        holes.forEach(h -> {
                            TActDirectionalDrillingPoint queryPoint = new TActDirectionalDrillingPoint();
                            queryPoint.setBoreholeId(h.getId());
                            h.settDirectionalDrillingPointList(actDirectionalDrillingPointService.selectTActDirectionalDrillingPointList(queryPoint));
                            length.updateAndGet(v -> new Double(v + h.gettDirectionalDrillingPointList().size() * h.gettDirectionalDrillingPointList().get(0).getBoreholeLength()));
                            count.getAndIncrement();
                        });
                    }

                });
            });
        });

        return AjaxResult.success(boreholes);
    }

    @GetMapping("/queryBoreholeCountList")
    @ApiOperation(value = "统计工作面的钻孔数量信息")
    public AjaxResult queryBoreholeCountList(@ApiParam(name = "roadwayIds", value = "巷道ID，支持传入多个巷道ID，用英文逗号分割")
                                             @RequestParam String roadwayIds,
                                             @ApiParam(name = "boreholeTypes", value = "钻孔类型ID，支持传入多个钻孔类型ID，用英文逗号分割")
                                             @RequestParam String boreholeTypes) {
        log.info("/queryBoreholeCountList,roadwayIds=[{}],boreholeTypes=[{}]", roadwayIds, boreholeTypes);
        String[] rds = roadwayIds.split(",");
        String[] tps = boreholeTypes.split(",");
        ArrayList<Integer> roadways = new ArrayList<>();
        ArrayList<Integer> types = new ArrayList<>();
        try {
            for (String rd : rds) {
                // 去重
                if (!roadways.contains(Integer.valueOf(rd))) {
                    roadways.add(Integer.parseInt(rd));
                }
            }
            for (String tp : tps) {
                // 去重
                if (!types.contains(Integer.valueOf(tp))) {
                    types.add(Integer.parseInt(tp));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("传入参数有误，请检查后重试！");
        }

        ArrayList<String> returnInfos = new ArrayList<>();
        HashMap<String, HashMap<String, HashMap<String, List<?>>>> boreholes = workfaceService.getBoreholesDetail(roadways, types);
        boreholes.keySet().forEach(key -> {
            // key 普钻，定向钻
            boreholes.get(key).keySet().forEach(key1 -> {
                // key1 巷道ID
                boreholes.get(key).get(key1).keySet().forEach(key2 -> {
                    // key2 钻孔类型
                    if (key.equals("ordinary")) {
                        List<TBkActBoreholeInfo> holes = (List<TBkActBoreholeInfo>) boreholes.get(key).get(key1).get(key2);
                        AtomicReference<Double> length = new AtomicReference<>(0.0);
                        AtomicInteger count = new AtomicInteger(0);
                        holes.forEach(h -> {
                            length.updateAndGet(v -> new Double(v + h.getBoreholeLength()));
                            count.getAndIncrement();
                        });
                        returnInfos.add("巷道ID:" + key1 + ",钻孔类型ID:" + key2 + ",钻孔数量:" + count.get() + ",钻孔总长度:" + length.get());
                    } else {
                        List<TActDirectionalBorehole> holes = (List<TActDirectionalBorehole>) boreholes.get(key).get(key1).get(key2);
                        AtomicReference<Double> length = new AtomicReference<>(0.0);
                        AtomicInteger count = new AtomicInteger(0);
                        holes.forEach(h -> {
                            TActDirectionalDrillingPoint queryPoint = new TActDirectionalDrillingPoint();
                            queryPoint.setBoreholeId(h.getId());
                            h.settDirectionalDrillingPointList(actDirectionalDrillingPointService.selectTActDirectionalDrillingPointList(queryPoint));
                            if (h.gettDirectionalDrillingPointList().size() > 0) {
                                length.updateAndGet(v -> new Double(v + h.gettDirectionalDrillingPointList().size() * h.gettDirectionalDrillingPointList().get(0).getBoreholeLength()));
                                count.getAndIncrement();
                            }
                        });
                        returnInfos.add("巷道ID:" + key1 + ",钻孔类型ID:" + key2 + ",钻孔数量:" + count.get() + ",钻孔总长度:" + length.get());
                    }
                });
            });
        });

        return AjaxResult.success(returnInfos);
    }

    @GetMapping("/queryWorkfaceList")
    @ApiOperation("查询系统内所有工作面信息")
    public String queryWorkfaceList() {
        log.info("查询系统内所有工作面信息");
        List<TWorkingFaceOfCoalMining> list = workfaceService.selectTWorkingFaceOfCoalMiningListForAI(new TWorkingFaceOfCoalMining());
        ArrayList<String> nameList = new ArrayList<>();
        list.forEach(t -> {
            nameList.add("{id:" + t.getId() + ",workfaceName:" + t.getWorkfaceName() + "}");
        });
        return nameList.toString();
    }

    @GetMapping("/queryRoadwayList")
    @ApiOperation("查询指定工作面下的所有巷道信息")
    public String queryRoadwayList(@RequestParam Integer workfaceId, @RequestParam String workfaceName) {
        log.info("/queryRoadwayList,workfaceId={},workfaceName={}", workfaceId, workfaceName);
        TRoadway queryRoadway = new TRoadway();
        ArrayList<String> roadwayNameList = new ArrayList<>();
        if (workfaceId == null) {
            List<TWorkingFaceOfCoalMining> wfList = workfaceService.selectTWorkingFaceOfCoalMiningListForAI(new TWorkingFaceOfCoalMining());
            for (TWorkingFaceOfCoalMining face : wfList) {
                if (Objects.equals(face.getWorkfaceName(), workfaceName)) {
                    queryRoadway.setWorkfaceId(face.getId());
                    break;
                }
            }
        } else {
            queryRoadway.setWorkfaceId(workfaceId);
        }
        List<TRoadway> tRoadways = roadwayService.selectTRoadwayList(queryRoadway);
        for (TRoadway tRoadway : tRoadways) {
            roadwayNameList.add("{id:" + tRoadway.getId() + ",roadwayName:" + tRoadway.getRoadway() + "}");
        }
        return roadwayNameList.toString();
    }

}
