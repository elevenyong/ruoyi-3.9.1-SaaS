package com.ruoyi.hh.physical.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.hh.business.domain.TDesSchemeInfo;
import com.ruoyi.hh.business.service.ITDesSchemeInfoService;
import com.ruoyi.hh.demo.domain.TGeoFault;
import com.ruoyi.hh.demo.service.ITGeoFaultService;
import com.ruoyi.hh.hhhz.domain.param.DesignParameters;
import com.ruoyi.hh.hhhz.service.ITDesignParameterService;
import com.ruoyi.hh.physical.domain.*;
import com.ruoyi.hh.physical.mapper.TWorkingFaceOfCoalMiningMapper;
import com.ruoyi.hh.physical.service.*;
import com.ruoyi.hh.util.domain.TBoreholeCategory;
import com.ruoyi.hh.util.service.ITBoreholeCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.*;

import static com.ruoyi.common.utils.SecurityUtils.getLoginUser;

/**
 * 采煤工作面Service业务层处理
 *
 * @author hhhz
 * @date 2023-04-28
 */
@Slf4j
@Service
public class TWorkingFaceOfCoalMiningServiceImpl implements ITWorkingFaceOfCoalMiningService {

    @Resource
    private PlatformTransactionManager transactionManager;

    @Resource
    private TWorkingFaceOfCoalMiningMapper tWorkingFaceOfCoalMiningMapper;
    //@Resource
    //private TRoadwayMapper roadwayMapper;

    @Resource
    private ITRoadwayService roadwayService;

    @Resource
    private ITRockinfoService rockinfoService;

    @Resource
    private ITCoalPointService coalPointService;

    @Resource
    private ITGeoFaultService geoFaultService;

    @Resource
    private ITGeoFallenColumnService geoFallenColumnService;

    @Resource
    private ITBkActBoreholeInfoService actBoreholeInfoService;

    @Resource
    private ITActDirectionalBoreholeService actDirectionalBoreholeService;

    @Resource
    private ITActDirectionalDrillingPointService actDirectionalDrillingPointService;

    @Resource
    private ITDesignedBoreholeService designedBoreholeService;

    @Resource
    private ITDesignedDirectionalBoreholeService desDirectionalBoreholeService;

    @Resource
    private ITDesDirectionalDrillingPointService desDirectionalDrillingPointService;

    @Resource
    private ITDesignParameterService designParameterService;

    @Resource
    private ITDesSchemeInfoService desSchemeInfoService;

    @Resource
    private ITSubAreaService subAreaService;

    @Resource
    private ITAttributeValueService attributeValueService;

    @Resource
    private ITBoreholeCategoryService categoryService;


    @Override
    public List<TWorkingFaceOfCoalMining> getAllTWorkingFaceOfCoalMining() {
        return tWorkingFaceOfCoalMiningMapper.selectAllTWorkingFaceOfCoalMining();
    }

    /**
     * 查询采煤工作面
     *
     * @param id 采煤工作面主键`
     * @return 采煤工作面
     */
    @Override
    public TWorkingFaceOfCoalMining selectTWorkingFaceOfCoalMiningById(Integer id) {
        TWorkingFaceOfCoalMining face = tWorkingFaceOfCoalMiningMapper.selectTWorkingFaceOfCoalMiningById(id);
        if (face != null) {
            // 匹配关联巷道信息
            if (face.getAirIntakeRoadway() != null && !Objects.equals(face.getAirIntakeRoadway(), 0)) {
                face.setAirIntakeRoadwayData(roadwayService.selectTRoadwayById(face.getAirIntakeRoadway()));
            }
            if (face.getAirReturnRoadway() != null && !Objects.equals(face.getAirReturnRoadway(), 0)) {
                face.setAirReturnRoadwayData(roadwayService.selectTRoadwayById(face.getAirReturnRoadway()));
            }
            if (face.getCutEyeRoadway() != null && !Objects.equals(face.getCutEyeRoadway(), 0)) {
                face.setCutEyeRoadwayData(roadwayService.selectTRoadwayById(face.getCutEyeRoadway()));
            }
            if (face.getDcAirIntakeRoadway() != null && !Objects.equals(face.getDcAirIntakeRoadway(), 0)) {
                face.setDcAirIntakeRoadwayData(roadwayService.selectTRoadwayById(face.getDcAirIntakeRoadway()));
            }
            if (face.getDcAirReturnRoadway() != null && !Objects.equals(face.getDcAirReturnRoadway(), 0)) {
                face.setDcAirReturnRoadwayData(roadwayService.selectTRoadwayById(face.getDcAirReturnRoadway()));
            }
            if (face.getDcCutEyeRoadway() != null && !Objects.equals(face.getDcCutEyeRoadway(), 0)) {
                face.setDcCutEyeRoadwayData(roadwayService.selectTRoadwayById(face.getDcCutEyeRoadway()));
            }
            if (face.getMiddleRoadway() != null && !Objects.equals(face.getMiddleRoadway(), 0)) {
                face.setMiddleRoadwayData(roadwayService.selectTRoadwayById(face.getMiddleRoadway()));
            }
            if (face.getDcMiddleRoadway() != null && !Objects.equals(face.getDcMiddleRoadway(), 0)) {
                face.setDcMiddleRoadwayData(roadwayService.selectTRoadwayById(face.getDcMiddleRoadway()));
            }
            // 匹配其他巷道信息
            if (face.getOtherRoadway() != null && face.getOtherRoadway().length > 0) {
                Integer[] otherRoadwayIds = face.getOtherRoadway();
                ArrayList<TRoadway> otherRoadwayData = new ArrayList<>();
                for (Integer otherRoadwayId : otherRoadwayIds) {
                    otherRoadwayData.add(roadwayService.selectTRoadwayById(otherRoadwayId));
                }
                face.setOtherRoadwayData(otherRoadwayData);
            }

            // 设置岩层
            TRockinfo rockInfo = new TRockinfo();
            rockInfo.setWorkfaceId(face.getId());
            List<TRockinfo> tRockInfos = rockinfoService.selectTRockinfoList(rockInfo);
            face.setRockinfoList(tRockInfos);

            // 设置煤层点位数据
            List<Integer> coalIds = new ArrayList<>();
            TCoalPoint qCoalPoint = new TCoalPoint();
            for (TRockinfo tRockInfo : tRockInfos) {
                if (tRockInfo.getRockType() == 1) {
                    coalIds.add(tRockInfo.getId());
                }
            }
            List<TCoalPoint> coalPoints = new ArrayList<>();
            for (Integer coalId : coalIds) {
                qCoalPoint.setWorkfaceId(face.getId());
                qCoalPoint.setDataType(1);
                qCoalPoint.setCoalinfoId(coalId);
                List<TCoalPoint> tCoalPoints = coalPointService.selectTCoalPointList(qCoalPoint);
                if (!tCoalPoints.isEmpty()) {
                    coalPoints.addAll(tCoalPoints);
                }
            }
            face.setCoalPoints(coalPoints);

            // 设置断层数据
            TGeoFault qGeoFault = new TGeoFault();
            qGeoFault.setWorkfaceId(face.getId());
            List<TGeoFault> geoFaults = geoFaultService.selectTGeoFaultList(qGeoFault);
            face.setGeoFaults(geoFaults);

            // 设置陷落柱数据
            TGeoFallenColumn qGeoFallenColumn = new TGeoFallenColumn();
            qGeoFallenColumn.setWorkfaceId(face.getId());
            List<TGeoFallenColumn> geoFallenColumns = geoFallenColumnService.selectTGeoFallenColumnList(qGeoFallenColumn);
            face.setGeoFallenColumns(geoFallenColumns);

            // 设置子区域数据
            TSubArea qSubArea = new TSubArea();
            qSubArea.setWorkfaceId(face.getId());
            List<TSubArea> tSubAreas = subAreaService.selectTSubAreaList(qSubArea);
            face.setSubAreas(tSubAreas);
        }
        return face;
    }
    /**
     * 查询采煤工作面列表
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 采煤工作面
     */
    @Override
    public List<TWorkingFaceOfCoalMining> selectTWorkingFaceOfCoalMiningListForAI(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining) {
        List<TWorkingFaceOfCoalMining> workingFaceOfCoalMinings = tWorkingFaceOfCoalMiningMapper.selectTWorkingFaceOfCoalMiningList(tWorkingFaceOfCoalMining);
        return workingFaceOfCoalMinings;
    }
    /**
     * 查询采煤工作面列表
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 采煤工作面
     */
    @Override
    public List<TWorkingFaceOfCoalMining> selectTWorkingFaceOfCoalMiningList(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining) {
        String username = "";
        try{
            LoginUser user = getLoginUser();
            username = user.getUsername();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<TWorkingFaceOfCoalMining> workingFaceOfCoalMinings = tWorkingFaceOfCoalMiningMapper.selectTWorkingFaceOfCoalMiningList(tWorkingFaceOfCoalMining);
        List<TWorkingFaceOfCoalMining> result = new ArrayList<>();
        for (TWorkingFaceOfCoalMining workingFaceOfCoalMining : workingFaceOfCoalMinings) {
            if ("sj".equals(username)) {
                if (workingFaceOfCoalMining.getWorkfaceName().contains("八矿")) {
                    result.add(workingFaceOfCoalMining);
                }
            }else if ("yksj".equals(username)){
                if (workingFaceOfCoalMining.getWorkfaceName().contains("一矿")) {
                    result.add(workingFaceOfCoalMining);
                }
            }else {
                result.add(workingFaceOfCoalMining);
            }
        }
        return result;
        /*for (TWorkingFaceOfCoalMining face : workingFaceOfCoalMinings) {
            if (face != null) {
                // 匹配巷道信息
                if (face.getAirIntakeRoadway() != null && !Objects.equals(face.getAirIntakeRoadway(), "null")) {
                    face.setAirIntakeRoadwayData(roadwayService.selectTRoadwayById(Integer.parseInt(face.getAirIntakeRoadway())));
                }
                if (face.getAirReturnRoadway() != null && !Objects.equals(face.getAirReturnRoadway(), "null")) {
                    face.setAirReturnRoadwayData(roadwayService.selectTRoadwayById(Integer.parseInt(face.getAirReturnRoadway())));
                }
                if (face.getCutEyeRoadway() != null && !Objects.equals(face.getCutEyeRoadway(), "null")) {
                    face.setCutEyeRoadwayData(roadwayService.selectTRoadwayById(Integer.parseInt(face.getCutEyeRoadway())));
                }
                String bottoms = face.getBottomExtractionRoadway();
                String s = bottoms.replaceAll("\\[", "").replaceAll("]", "");
                String[] ids = s.split(",");
                if (ids.length >= 1 && !Objects.equals(ids[0], "null") && !Objects.equals(ids[0], "")) {
                    face.setDcAirIntakeRoadwayData(roadwayService.selectTRoadwayById(Integer.parseInt(ids[0])));
                }
                if (ids.length >= 2 && !Objects.equals(ids[1], "null") && !Objects.equals(ids[1], "")) {
                    face.setDcAirReturnRoadwayData(roadwayService.selectTRoadwayById(Integer.parseInt(ids[1])));
                }
                if (ids.length >= 3 && !Objects.equals(ids[2], "null") && !Objects.equals(ids[2], "")) {
                    face.setDcCutEyeRoadwayData(roadwayService.selectTRoadwayById(Integer.parseInt(ids[2])));
                }
                if (ids.length >= 4 && !Objects.equals(ids[3], "null") && !Objects.equals(ids[3], "")) {
                    face.setDcMiddleRoadwayData(roadwayService.selectTRoadwayById(Integer.parseInt(ids[3])));
                }
                // 设置岩层
                TRockinfo rockInfo = new TRockinfo();
                rockInfo.setWorkfaceId(face.getId());
                List<TRockinfo> tRockInfos = rockinfoService.selectTRockinfoList(rockInfo);
                face.setRockinfoList(tRockInfos);
            }
        }*/
        //return workingFaceOfCoalMinings;
    }

    /**
     * 新增采煤工作面
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 结果
     */
    @Override
    public TWorkingFaceOfCoalMining insertTWorkingFaceOfCoalMining(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining) {
        // ------↓兼容低版本客户端，后期可删除↓--------//
        // 进风底抽id,回风底抽id,切眼底抽id,中抽id
        Integer dcAirIntakeRoadway = tWorkingFaceOfCoalMining.getDcAirIntakeRoadway();
        Integer dcAirReturnRoadway = tWorkingFaceOfCoalMining.getDcAirReturnRoadway();
        Integer dcCutEyeRoadway = tWorkingFaceOfCoalMining.getDcCutEyeRoadway();
        Integer dcMiddleRoadway = tWorkingFaceOfCoalMining.getDcMiddleRoadway();
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        if (dcAirIntakeRoadway != null && dcAirIntakeRoadway != 0) {
            builder.append(dcAirIntakeRoadway);
        }
        builder.append(",");
        if (dcAirReturnRoadway != null && dcAirReturnRoadway != 0) {
            builder.append(dcAirReturnRoadway);
        }
        builder.append(",");
        if (dcCutEyeRoadway != null && dcCutEyeRoadway != 0) {
            builder.append(dcCutEyeRoadway);
        }
        builder.append(",");
        if (dcMiddleRoadway != null && dcMiddleRoadway != 0) {
            builder.append(dcMiddleRoadway);
        }
        builder.append("]");
        tWorkingFaceOfCoalMining.setBottomExtractionRoadway(builder.toString());
        // ------↑兼容低版本客户端，后期可删除↑--------//
        int i = tWorkingFaceOfCoalMiningMapper.insertTWorkingFaceOfCoalMining(tWorkingFaceOfCoalMining);
        if (i > 0) {
            return tWorkingFaceOfCoalMining;
        } else return null;
    }

    /**
     * 修改采煤工作面
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 结果
     */
    @Override
    public TWorkingFaceOfCoalMining updateTWorkingFaceOfCoalMining(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining) {
        int i = tWorkingFaceOfCoalMiningMapper.updateTWorkingFaceOfCoalMining(tWorkingFaceOfCoalMining);
        if (i > 0) {
            return tWorkingFaceOfCoalMining;
        } else return null;
    }

    /**
     * 批量删除采煤工作面
     *
     * @param ids 需要删除的采煤工作面主键
     * @return 结果
     */
    @Override
    public int deleteTWorkingFaceOfCoalMiningByIds(Integer[] ids) {
        return tWorkingFaceOfCoalMiningMapper.deleteTWorkingFaceOfCoalMiningByIds(ids);
    }

    /**
     * 删除采煤工作面信息
     *
     * @param id 采煤工作面主键
     * @return 结果
     */
    @Override
    public int deleteTWorkingFaceOfCoalMiningById(Integer id) {
        return tWorkingFaceOfCoalMiningMapper.deleteTWorkingFaceOfCoalMiningById(id);
    }

    /**
     * 根据巷道ID获取工作面IDList
     *
     * @param roadwayId
     * @return
     */
    @Override
    public List<Integer> getWorkFaceByRoadwayId(Integer roadwayId) {
        List<Integer> idsList = new ArrayList<>();
        List<TWorkingFaceOfCoalMining> allTWorkingFaceOfCoalMining = getAllTWorkingFaceOfCoalMining();
        for (TWorkingFaceOfCoalMining face : allTWorkingFaceOfCoalMining) {
            if (face.getAirIntakeRoadway() != null && !Objects.equals(face.getAirIntakeRoadway(), 0)) {
                if (roadwayId.equals(face.getAirIntakeRoadway())) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            if (face.getAirReturnRoadway() != null && !Objects.equals(face.getAirReturnRoadway(), 0)) {
                if (roadwayId.equals(face.getAirReturnRoadway())) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            if (face.getCutEyeRoadway() != null && !Objects.equals(face.getCutEyeRoadway(), 0)) {
                if (roadwayId.equals(face.getCutEyeRoadway())) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            if (face.getDcMiddleRoadway() != null && !Objects.equals(face.getDcMiddleRoadway(), 0)) {
                if (roadwayId.equals(face.getDcMiddleRoadway())) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            if (face.getDcCutEyeRoadway() != null && !Objects.equals(face.getDcCutEyeRoadway(), 0)) {
                if (roadwayId.equals(face.getDcCutEyeRoadway())) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            if (face.getDcAirIntakeRoadway() != null && !Objects.equals(face.getDcAirIntakeRoadway(), 0)) {
                if (roadwayId.equals(face.getDcAirIntakeRoadway())) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            if (face.getDcAirReturnRoadway() != null && !Objects.equals(face.getDcAirReturnRoadway(), 0)) {
                if (roadwayId.equals(face.getDcAirReturnRoadway())) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            if (face.getMiddleRoadway() != null && !Objects.equals(face.getMiddleRoadway(), 0)) {
                if (roadwayId.equals(face.getMiddleRoadway())) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            /*String bottoms = face.getBottomExtractionRoadway();
            String s = bottoms.replaceAll("\\[", "").replaceAll("]", "");
            String[] ids = s.split(",");
            if (ids.length >= 1 && !Objects.equals(ids[0], "null") && !Objects.equals(ids[0], "")) {
                if (roadwayId.equals(Integer.parseInt(ids[0]))) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            if (ids.length >= 2 && !Objects.equals(ids[1], "null") && !Objects.equals(ids[1], "")) {
                if (roadwayId.equals(Integer.parseInt(ids[1]))) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            if (ids.length >= 3 && !Objects.equals(ids[2], "null") && !Objects.equals(ids[2], "")) {
                if (roadwayId.equals(Integer.parseInt(ids[2]))) {
                    idsList.add(face.getId());
                    continue;
                }
            }
            if (ids.length >= 4 && !Objects.equals(ids[3], "null") && !Objects.equals(ids[3], "")) {
                if (roadwayId.equals(Integer.parseInt(ids[3]))) {
                    idsList.add(face.getId());
                }
            }*/
        }
        return idsList;
    }

    /**
     * 导出工作面数据
     * 00 = 导出结果提示
     * s0 = 工作面基本信息数据
     * s1 = 巷道数据
     * s2 = 实钻普钻数据
     * s3 = 实钻定向钻数据
     * s4 = 设计普钻数据
     * s5 = 设计定向钻数据
     * s6 = 设计参数
     * s7 = 地层数据
     * s8 = 煤层点数据
     * s9 = 设计方案数据
     * ss0 = 断层数据
     * ss1 = 陷落柱数据
     * ss2 = 属性数据
     * ss3 = 子区域数据
     *
     * @param id id
     * @return 工作面数据
     * @throws JsonProcessingException
     */
    @Override
    public HashMap<String, String> exportData(Integer id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // 工作面基础信息
        TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining = tWorkingFaceOfCoalMiningMapper.selectTWorkingFaceOfCoalMiningById(id);
        if (tWorkingFaceOfCoalMining != null) {
            String s0 = mapper.writeValueAsString(tWorkingFaceOfCoalMining);

            // 工作面巷道，导线点
            List<TRoadway> tRoadways = roadwayService.selectRoadwayListByWorkFace(id);
            String s1 = mapper.writeValueAsString(tRoadways);
            int i1 = tRoadways.size();
            int i11 = 0;
            for (TRoadway tRoadway : tRoadways) {
                i11 += tRoadway.getTNavigationPointList().size();
            }

            // 普钻，需要更新绑定地层ID
            HashMap<Integer, List<TBkActBoreholeInfo>> actBoreholes = new HashMap<>();
            int i2 = 0;
            for (TRoadway tRoadway : tRoadways) {
                List<TBkActBoreholeInfo> rdActBoreholeInfos = actBoreholeInfoService.getNormalActBoreholeInfoByRoadwayId(tRoadway.getId());
                actBoreholes.put(tRoadway.getId(), rdActBoreholeInfos);
                i2 += rdActBoreholeInfos.size();
            }
            String s2 = mapper.writeValueAsString(actBoreholes);

            // 定向钻，轨迹点
            HashMap<Integer, List<TActDirectionalBorehole>> actDirectionBoreholes = new HashMap<>();
            int i3 = 0;
            for (TRoadway tRoadway : tRoadways) {
                List<TActDirectionalBorehole> rdDirectionActBoreholeInfos = actDirectionalBoreholeService.getActDirectionalBoreholeInfoByRoadwayId(tRoadway.getId());
                actDirectionBoreholes.put(tRoadway.getId(), rdDirectionActBoreholeInfos);
                i3 += rdDirectionActBoreholeInfos.size();
            }
            String s3 = mapper.writeValueAsString(actDirectionBoreholes);

            // 设计普钻
            HashMap<Integer, List<TDesignedBorehole>> desBoreholes = new HashMap<>();
            int i4 = 0;
            for (TRoadway tRoadway : tRoadways) {
                List<TDesignedBorehole> desBoreholesByRoadway = designedBoreholeService.getByRoadwayId(tRoadway.getId());
                desBoreholes.put(tRoadway.getId(), desBoreholesByRoadway);
                i4 += desBoreholesByRoadway.size();
            }
            String s4 = mapper.writeValueAsString(desBoreholes);

            // 设计定向钻
            HashMap<Integer, List<TDesignedDirectionalBorehole>> desDirectionBoreholes = new HashMap<>();
            int i5 = 0;
            for (TRoadway tRoadway : tRoadways) {
                TDesignedDirectionalBorehole queryDesDirBorehole = new TDesignedDirectionalBorehole();
                queryDesDirBorehole.setRoadwayId(tRoadway.getId());
                List<TDesignedDirectionalBorehole> desDirBoreholesByRoadway = desDirectionalBoreholeService.selectTDesignedDirectionalBoreholeList(queryDesDirBorehole);
                desDirectionBoreholes.put(tRoadway.getId(), desDirBoreholesByRoadway);
                i5 += desDirBoreholesByRoadway.size();
            }
            String s5 = mapper.writeValueAsString(desDirectionBoreholes);

            // 设计参数
            DesignParameters queryParam = new DesignParameters();
            queryParam.setWorkfaceId(id);
            List<DesignParameters> designParameters = designParameterService.selectTDesignParametersList(queryParam);
            int i6 = designParameters.size();
            String s6 = mapper.writeValueAsString(designParameters);

            // 地层，层位，厚度。
            TRockinfo queryRock = new TRockinfo();
            queryRock.setWorkfaceId(id);
            List<TRockinfo> tRockinfos = rockinfoService.selectTRockinfoList(queryRock);
            int i7 = tRockinfos.size();
            String s7 = mapper.writeValueAsString(tRockinfos);

            // 地层点。须更新地层ID
            TCoalPoint queryCoalPoint = new TCoalPoint();
            queryCoalPoint.setWorkfaceId(id);
            List<TCoalPoint> tCoalPoints = coalPointService.selectTCoalPointList(queryCoalPoint);
            int i8 = tCoalPoints.size();
            String s8 = mapper.writeValueAsString(tCoalPoints);

            // 设计方案
            TDesSchemeInfo queryScheme = new TDesSchemeInfo();
            queryScheme.setWorkfaceId(id);
            List<TDesSchemeInfo> desSchemeInfos = desSchemeInfoService.selectTDesSchemeInfoList(queryScheme);
            String s9 = mapper.writeValueAsString(desSchemeInfos);
            int i9 = desSchemeInfos.size();

            // 断层数据。须更新煤层点对应断层ID，工作面ID
            TGeoFault queryFault = new TGeoFault();
            queryFault.setWorkfaceId(id);
            List<TGeoFault> geoFaults = geoFaultService.selectTGeoFaultList(queryFault);
            String ss0 = mapper.writeValueAsString(geoFaults);
            int ii0 = geoFaults.size();

            // 陷落柱数据。须更新煤层点对应陷落柱ID，工作面ID
            TGeoFallenColumn queryFallen = new TGeoFallenColumn();
            queryFallen.setWorkfaceId(id);
            List<TGeoFallenColumn> fallenColumns = geoFallenColumnService.selectTGeoFallenColumnList(queryFallen);
            String ss1 = mapper.writeValueAsString(fallenColumns);
            int ii1 = fallenColumns.size();

            // 属性数据。需更新关联钻孔ID，工作面ID
            TAttributeValue queryAttributeValue = new TAttributeValue();
            queryAttributeValue.setWorkfaceId(id);
            List<TAttributeValue> attributeValues = attributeValueService.selectTAttributeValueList(queryAttributeValue);
            String ss2 = mapper.writeValueAsString(attributeValues);
            int ii2 = attributeValues.size();

            // 子区域数据。需更新工作面ID，巷道ID
            TSubArea querySubArea = new TSubArea();
            querySubArea.setWorkfaceId(id);
            List<TSubArea> subAreas = subAreaService.selectTSubAreaList(querySubArea);
            String ss3 = mapper.writeValueAsString(subAreas);
            int ii3 = subAreas.size();


            String resultMsg = String.format("成功导出工作面数据： 导出巷道 %d 条;导线点 %d 个;实钻普钻数据 %d 条;实钻定向钻数据 %d 条;设计普钻数据 %d 条;设计定向钻数据 %d 条;设计参数 %d 组;设计方案 %d 组;地层数据 %d 条;地层点数据 %d 条;断层数据 %d 条;陷落柱数据 %d 条;属性数据 %d 条;子区域数据 %d 条", i1, i11, i2, i3, i4, i5, i6, i9, i7, i8, ii0, ii1, ii2, ii3);

            log.info(resultMsg);

            // 聚合数据
            HashMap<String, String> result = new HashMap<>();
            result.put("name", tWorkingFaceOfCoalMining.getWorkfaceName());
            result.put("00", resultMsg);
            result.put("s0", s0);
            result.put("s1", s1);
            result.put("s2", s2);
            result.put("s3", s3);
            result.put("s4", s4);
            result.put("s5", s5);
            result.put("s6", s6);
            result.put("s7", s7);
            result.put("s8", s8);
            result.put("s9", s9);
            result.put("ss0", ss0);
            result.put("ss1", ss1);
            result.put("ss2", ss2);
            result.put("ss3", ss3);
            return result;
        } else {
            return null;
        }
    }

    /**
     * 导入工作面数据
     * 00 = 导出结果提示
     * s0 = 工作面基本信息数据
     * s1 = 巷道数据
     * s2 = 实钻普钻数据
     * s3 = 实钻定向钻数据
     * s4 = 设计普钻数据
     * s5 = 设计定向钻数据
     * s6 = 设计参数
     * s7 = 地层数据
     * s8 = 煤层点数据
     * s9 = 设计方案数据
     * ss0 = 断层数据
     * ss1 = 陷落柱数据
     * ss2 = 属性数据
     * ss3 = 子区域数据
     *
     * @param data
     * @return
     * @throws JsonProcessingException
     */
    @Override
    public AjaxResult importData(String data) throws JsonProcessingException {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("ManualTx");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);

        log.info("开启事务，开始执行导入...");
        try {
            ObjectMapper mapper = new ObjectMapper();
            // 解析数据
            HashMap<String, String> map1 = mapper.readValue(data, HashMap.class);
            String wfName = map1.get("name");

            // 工作面基础信息
            log.info("导入 {} 工作面信息", wfName);
            String strWFInfo = map1.get("s0");
            Integer oldWFId = -1;
            Integer newWFId = -1;
            if (strWFInfo != null) {
                TWorkingFaceOfCoalMining workingFaceOfCoalMining = mapper.readValue(strWFInfo, TWorkingFaceOfCoalMining.class);
                oldWFId = workingFaceOfCoalMining.getId();
                // 清除所有巷道绑定，在添加巷道时会自动更新上
                workingFaceOfCoalMining.setWorkfaceName("(导入)" + workingFaceOfCoalMining.getWorkfaceName());
                workingFaceOfCoalMining.setId(null);
                workingFaceOfCoalMining.setAirIntakeRoadway(null);
                workingFaceOfCoalMining.setAirReturnRoadway(null);
                workingFaceOfCoalMining.setDcAirIntakeRoadway(null);
                workingFaceOfCoalMining.setDcAirReturnRoadway(null);
                workingFaceOfCoalMining.setCutEyeRoadway(null);
                workingFaceOfCoalMining.setDcCutEyeRoadway(null);
                workingFaceOfCoalMining.setDcMiddleRoadway(null);
                workingFaceOfCoalMining.setMiddleRoadway(null);
                workingFaceOfCoalMining.setOtherRoadway(null);
                TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining = insertTWorkingFaceOfCoalMining(workingFaceOfCoalMining);
                newWFId = tWorkingFaceOfCoalMining.getId();
            } else {
                return AjaxResult.error("导入数据有误，请检查后重试");
            }

            // 巷道
            log.info("导入巷道数据..");
            String strRoadways = map1.get("s1");
            int i1 = 0;
            int i11 = 0;
            HashMap<Integer, Integer> roadwayCompareIds = new HashMap<>();
            if (strRoadways != null) {
                List<TRoadway> roadways = mapper.readValue(strRoadways, new TypeReference<List<TRoadway>>() {
                });
                for (TRoadway roadway : roadways) {
                    Integer oldRWId = roadway.getId();
                    roadway.setId(null);
                    for (TNavigationPoint tNavigationPoint : roadway.getTNavigationPointList()) {
                        tNavigationPoint.setId(null);
                        tNavigationPoint.setRoadwayId(null);
                        i11++;
                    }
                    roadway.setWorkfaceId(newWFId);
                    AjaxResult result = roadwayService.insertTRoadway(roadway);
                    TRoadway newRoadway = (TRoadway) result.get("data");
                    Integer newRWId = newRoadway.getId();
                    roadwayCompareIds.put(oldRWId, newRWId);
                    i1++;
                }
            }


            Set<Integer> oldRWIds = new HashSet<>();


            // 实钻定向钻
            log.info("导入实钻定向钻数据..");
            String strActDirectionBoreholes = map1.get("s3");
            HashMap<Integer, List<TActDirectionalBorehole>> actDirectionalBoreholes = mapper.readValue(strActDirectionBoreholes, new TypeReference<HashMap<Integer, List<TActDirectionalBorehole>>>() {
            });
            int i3 = 0;
            oldRWIds = actDirectionalBoreholes.keySet();
            for (Integer oldRWId : oldRWIds) {
                List<TActDirectionalBorehole> actDirectBoreholeInfoList = actDirectionalBoreholes.get(oldRWId);
                for (TActDirectionalBorehole actDirectBoreholeInfo : actDirectBoreholeInfoList) {
                    actDirectBoreholeInfo.setId(null);
                    actDirectBoreholeInfo.setRoadwayId(roadwayCompareIds.get(oldRWId));
                    int result = actDirectionalBoreholeService.insertTActDirectionalBorehole(actDirectBoreholeInfo);
                /*if (result > 0) {
                    // 插入定向钻孔点
                    List<TActDirectionalDrillingPoint> drillingPointList = actDirectBoreholeInfo.gettDirectionalDrillingPointList();
                    if (!drillingPointList.isEmpty()) {
                        for (TActDirectionalDrillingPoint drillingPoint : drillingPointList) {
                            drillingPoint.setId(null);
                            drillingPoint.setBoreholeId(actDirectBoreholeInfo.getId());
                            actDirectionalDrillingPointService.insertTActDirectionalDrillingPoint(drillingPoint);
                        }
                    }
                }*/
                    if (result > 0) {
                        i3++;
                    }
                }
            }

            // 设计普钻
            log.info("导入设计普钻数据..");
            String strDesBoreholes = map1.get("s4");
            HashMap<Integer, List<TDesignedBorehole>> desBoreholes = mapper.readValue(strDesBoreholes, new TypeReference<HashMap<Integer, List<TDesignedBorehole>>>() {
            });
            int i4 = 0;
            oldRWIds = desBoreholes.keySet();
            for (Integer oldRWId : oldRWIds) {
                List<TDesignedBorehole> desBoreholeInfoList = desBoreholes.get(oldRWId);
                if (desBoreholeInfoList != null && !desBoreholeInfoList.isEmpty()) {
                    for (TDesignedBorehole desBoreholeInfo : desBoreholeInfoList) {
                        desBoreholeInfo.setId(null);
                        desBoreholeInfo.setRoadwayId(roadwayCompareIds.get(oldRWId));
                    }
                    for (TDesignedBorehole tDesignedBorehole : desBoreholeInfoList) {
                        designedBoreholeService.insertTDesignedBorehole(tDesignedBorehole);
                        i4++;
                    }
                }
            }

            // 设计定向钻
            log.info("导入设计定向钻数据..");
            String strDesDirectionBoreholes = map1.get("s5");
            int i5 = 0;
            if (strDesDirectionBoreholes != null) {
                HashMap<Integer, List<TDesignedDirectionalBorehole>> desDirectionalBoreholes = mapper.readValue(strDesDirectionBoreholes, new TypeReference<HashMap<Integer, List<TDesignedDirectionalBorehole>>>() {
                });
                oldRWIds = desDirectionalBoreholes.keySet();
                for (Integer oldRWId : oldRWIds) {
                    List<TDesignedDirectionalBorehole> desDirectBoreholeInfoList = desDirectionalBoreholes.get(oldRWId);
                    for (TDesignedDirectionalBorehole desDirectBoreholeInfo : desDirectBoreholeInfoList) {
                        desDirectBoreholeInfo.setId(null);
                        desDirectBoreholeInfo.setRoadwayId(roadwayCompareIds.get(oldRWId));
                        int result = desDirectionalBoreholeService.insertTDesignedDirectionalBorehole(desDirectBoreholeInfo);
                        if (result > 0) {
                            // 插入定向钻孔点
                            List<TDesDirectionalDrillingPoint> drillingPointList = desDirectBoreholeInfo.gettDirectionalDrillingPointList();
                            if (!drillingPointList.isEmpty()) {
                                for (TDesDirectionalDrillingPoint drillingPoint : drillingPointList) {
                                    drillingPoint.setId(null);
                                    drillingPoint.setDesBoreholeId(desDirectBoreholeInfo.getId());
                                    desDirectionalDrillingPointService.insertTDesDirectionalDrillingPoint(drillingPoint);
                                }
                            }
                        }
                        i5++;
                    }
                }
            }


            // 设计参数
            log.info("导入设计参数数据..");
            String strDesignParameters = map1.get("s6");
            HashMap<Integer, Integer> paramCompareIds = new HashMap<>();
            int i6 = 0;
            if (strDesignParameters != null) {
                List<DesignParameters> designParameters = mapper.readValue(strDesignParameters, new TypeReference<List<DesignParameters>>() {
                });
                for (DesignParameters designParameter : designParameters) {
                    Integer oldRWId = designParameter.getRoadwayId();
                    Integer oldParamId = designParameter.getId();
                    designParameter.setId(null);
                    designParameter.setWorkfaceId(newWFId);
                    designParameter.setRoadwayId(roadwayCompareIds.get(oldRWId));
                    String replacedStr = designParameter.getDetailedContent()
                            .replaceAll("\"RoadwayId\":" + oldRWId, "\"RoadwayId\":" + roadwayCompareIds.get(oldRWId))
                            .replaceAll("\"workingFaceId\":" + oldWFId, "\"workingFaceId\":" + newWFId)
                            .replaceAll("\"roadId\":" + oldRWId, "\"roadId\":" + roadwayCompareIds.get(oldRWId));
                    designParameter.setDetailedContent(replacedStr);
                    designParameter.setSchemeId("0");
                    designParameter = designParameterService.insertDesignParameters(designParameter);
                    Integer newParamId = designParameter.getId();
                    paramCompareIds.put(oldParamId, newParamId);
                    i6++;
                }
            }

            // 地层，层位
            log.info("导入地层，层位数据..");
            String strRockInfos = map1.get("s7");
            HashMap<Integer, Integer> rockInfoCompareIds = new HashMap<>();
            rockInfoCompareIds.put(0, 0);
            int i7 = 0;
            if (strRockInfos != null) {
                List<TRockinfo> rockInfos = mapper.readValue(strRockInfos, new TypeReference<List<TRockinfo>>() {
                });
                for (TRockinfo rockInfo : rockInfos) {
                    Integer oldRockId = rockInfo.getId();
                    rockInfo.setId(null);
                    rockInfo.setWorkfaceId(newWFId);
                    rockinfoService.insertTRockinfo(rockInfo);
                    Integer newRockId = rockInfo.getId();
                    rockInfoCompareIds.put(oldRockId, newRockId);
                    i7++;
                }
            }

            // 实钻普钻
            log.info("导入实钻普钻数据..");
            String strActBoreholes = map1.get("s2");
            int i2 = 0;
            HashMap<Integer, Integer> actHoleCompareIds = new HashMap<>();
            if (strActBoreholes != null) {
                HashMap<Integer, List<TBkActBoreholeInfo>> actBoreholes = mapper.readValue(strActBoreholes, new TypeReference<HashMap<Integer, List<TBkActBoreholeInfo>>>() {
                });
                oldRWIds = actBoreholes.keySet();
                for (Integer oldRWId : oldRWIds) {
                    List<TBkActBoreholeInfo> actBoreholeInfoList = actBoreholes.get(oldRWId);
                    if (actBoreholeInfoList != null && !actBoreholeInfoList.isEmpty()) {
                        for (TBkActBoreholeInfo boreholeInfo : actBoreholeInfoList) {
                            int oldHoleId = boreholeInfo.getId();
                            boreholeInfo.setId(null);
                            // 绑定关联巷道
                            boreholeInfo.setRoadwayId(roadwayCompareIds.get(oldRWId));
                            // 绑定关联地层
                            boreholeInfo.setBindCoal1st(rockInfoCompareIds.get(boreholeInfo.getBindCoal1st()));
                            boreholeInfo.setBindCoal2st(rockInfoCompareIds.get(boreholeInfo.getBindCoal2st()));
                            boreholeInfo.setBindCoal3st(rockInfoCompareIds.get(boreholeInfo.getBindCoal3st()));
                            // 此处导入不会插入属性数据
                            actBoreholeInfoService.insertTBkActBoreholeInfo(boreholeInfo);
                            int newHoleId = boreholeInfo.getId();
                            actHoleCompareIds.put(oldHoleId, newHoleId);
                            i2++;
                        }
                        // 批量导入
                        // actBoreholeInfoService.insertBatchBkActBorehole(actBoreholeInfoList);
                    }
                }
            }

            // 断层
            log.info("导入断层数据..");
            String strGeoFault = map1.get("ss0");
            HashMap<Integer, Integer> geoFaultCompareIds = new HashMap<>();
            int ii0 = 0;
            if (strGeoFault != null) {
                List<TGeoFault> geoFaults = mapper.readValue(strGeoFault, new TypeReference<List<TGeoFault>>() {
                });
                for (TGeoFault geoFault : geoFaults) {
                    Integer oldGeoFaultId = geoFault.getId();
                    geoFault.setId(null);
                    geoFault.setWorkfaceId(newWFId);
                    // 防止重复插入
                    geoFault.setGeoFaultPoints(null);
                    geoFaultService.insertTGeoFault(geoFault);
                    Integer newGeoFaultId = geoFault.getId();
                    geoFaultCompareIds.put(oldGeoFaultId, newGeoFaultId);
                    ii0++;
                }
            }

            // 陷落柱
            log.info("导入陷落柱数据..");
            String strGeoFallen = map1.get("ss1");
            int ii1 = 0;
            HashMap<Integer, Integer> geoFallenCompareIds = new HashMap<>();
            if (strGeoFallen != null) {
                List<TGeoFallenColumn> geoFallenColumns = mapper.readValue(strGeoFallen, new TypeReference<List<TGeoFallenColumn>>() {
                });
                for (TGeoFallenColumn geoFallen : geoFallenColumns) {
                    Integer oldGeoFallenId = geoFallen.getId();
                    geoFallen.setId(null);
                    geoFallen.setWorkfaceId(newWFId);
                    // 防止重复插入
                    geoFallen.setGeoFallenColumnPoints(null);
                    geoFallenColumnService.insertTGeoFallenColumn(geoFallen);
                    Integer newGeoFallenId = geoFallen.getId();
                    geoFallenCompareIds.put(oldGeoFallenId, newGeoFallenId);
                    ii1++;
                }
            }

            // 属性数据
            log.info("导入属性数据数据..");
            String strAttributeValues = map1.get("ss2");
            int ii2 = 0;
            if (strGeoFallen != null) {
                List<TAttributeValue> tAttributeValues = mapper.readValue(strAttributeValues, new TypeReference<List<TAttributeValue>>() {
                });
                for (TAttributeValue attributeValue : tAttributeValues) {
                    attributeValue.setId(null);
                    attributeValue.setWorkfaceId(newWFId);
                    attributeValue.setRockCoalId(rockInfoCompareIds.get(attributeValue.getRockCoalId()));
                    attributeValue.setBoreholeId(actHoleCompareIds.get(attributeValue.getBoreholeId()));
                    attributeValueService.insertTAttributeValue(attributeValue);
                    ii2++;
                }
            }

            // 地层点
            log.info("导入地层点数据..");
            String strCoalPoints = map1.get("s8");
            int i8 = 0;
            if (strCoalPoints != null) {
                List<TCoalPoint> coalPoints = mapper.readValue(strCoalPoints, new TypeReference<List<TCoalPoint>>() {
                });
                for (TCoalPoint coalPoint : coalPoints) {
                    coalPoint.setId(null);
                    coalPoint.setWorkfaceId(newWFId);
                    // 更新地层ID
                    coalPoint.setCoalinfoId(rockInfoCompareIds.get(coalPoint.getCoalinfoId()));
                    // 更新断层ID
                    coalPoint.setGeoFaultId(geoFaultCompareIds.get(coalPoint.getGeoFaultId()));
                    // 更新陷落柱ID
                    coalPoint.setGeoFallenId(geoFallenCompareIds.get(coalPoint.getGeoFallenId()));
                    coalPointService.insertTCoalPoint(coalPoint);
                    i8++;
                }
            }

            // 设计方案
            log.info("导入设计方案数据..");
            String strDesSchemes = map1.get("s9");
            int i9 = 0;
            if (strDesSchemes != null) {
                List<TDesSchemeInfo> desSchemeInfos = mapper.readValue(strDesSchemes, new TypeReference<List<TDesSchemeInfo>>() {
                });
                for (TDesSchemeInfo desSchemeInfo : desSchemeInfos) {
                    desSchemeInfo.setId(null);
                    desSchemeInfo.setWorkfaceId(newWFId);
                    List<DesignParameters> parameters = desSchemeInfo.getDesignParameters();
                    for (DesignParameters parameter : parameters) {
                        parameter.setId(paramCompareIds.get(parameter.getId()));
                    }
                    desSchemeInfoService.insertTDesSchemeInfo(desSchemeInfo);
                    i9++;
                }
            }

            // 子区域
            log.info("导入子区域数据..");
            String strParameters = map1.get("ss3");
            int ii3 = 0;
            if (strParameters != null) {
                List<TSubArea> subAreas = mapper.readValue(strParameters, new TypeReference<List<TSubArea>>() {
                });
                for (TSubArea subArea : subAreas) {
                    subArea.setId(null);
                    subArea.setWorkfaceId(newWFId);
                    subArea.setRoadwayId(roadwayCompareIds.get(subArea.getRoadwayId()));
                    subAreaService.insertTSubArea(subArea);
                    ii3++;
                }
            }

            String resultMsg = String.format("成功导入工作面数据：%s。 导入巷道 %d 条;导线点 %d 个;实钻普钻数据 %d 条;实钻定向钻数据 %d 条;设计普钻数据 %d 条;设计定向钻数据 %d 条;设计参数 %d 组;设计方案 %d 组;地层数据 %d 条;地层点数据 %d 条;断层数据 %d 条;陷落柱数据 %d 条;属性数据 %d 条;子区域数据 %d 条", wfName, i1, i11, i2, i3, i4, i5, i6, i9, i7, i8, ii0, ii1, ii2, ii3);

            log.info(resultMsg);
            transactionManager.commit(status);
            return AjaxResult.success(resultMsg);
        } catch (Exception e) {
            transactionManager.rollback(status);
            return AjaxResult.error(e.getMessage());
        }
    }


    /**
     * 计算工作面内所有实钻数量
     *
     * @param workfaceId
     * @return
     */
    public HashMap<String, HashMap<Integer, ?>> getBoreholesCountDetail(Integer workfaceId) {
        ArrayList<Integer> roadwayIds = new ArrayList<>();
        List<TRoadway> roadways = roadwayService.selectRoadwayListByWorkFace(workfaceId);
        for (TRoadway roadway : roadways) {
            roadwayIds.add(roadway.getId());
        }
        ArrayList<Integer> types = new ArrayList<>();
        List<TBoreholeCategory> categories = categoryService.selectTBoreholeCategoryList(new TBoreholeCategory());
        for (TBoreholeCategory category : categories) {
            types.add(category.getCategoryCode());
        }
        HashMap<String, HashMap<Integer, ?>> finalRetMaps = new HashMap<>();
        HashMap<Integer, HashMap<Integer, Integer>> actRetMaps = new HashMap<>();

        for (Integer type : types) {
            Integer count = 0;
            for (Integer roadwayId : roadwayIds) {
                // 分类获取钻孔数量
                if (type != 6 && type != 7) {
                    count = actBoreholeInfoService.getNormalBoreholeCount(type, roadwayId);
                    //System.out.println("type:" + type + ",roadwayID:" + roadwayId + ",count:" + count);
                } else {
                    count = actDirectionalBoreholeService.getDirectBoreholeCount(type, roadwayId);
                    //System.out.println("type:" + type + ",roadwayID:" + roadwayId + ",count:" + count);
                }
                // 构造返回值
                if (!actRetMaps.containsKey(type)) {
                    HashMap<Integer, Integer> countMap = new HashMap<>();
                    countMap.put(roadwayId, count);
                    actRetMaps.put(type, countMap);
                } else {
                    actRetMaps.get(type).put(roadwayId, count);
                }
            }
        }
        HashMap<Integer, HashMap<Integer, Integer>> desRetMaps = new HashMap<>();
        for (Integer type : types) {
            Integer count = 0;
            for (Integer roadwayId : roadwayIds) {
                // 分类获取钻孔数量
                if (type != 6 && type != 7) {
                    count = designedBoreholeService.getNormalBoreholeCount(type, roadwayId);
                    //System.out.println("type:" + type + ",roadwayID:" + roadwayId + ",count:" + count);
                } else {
                    count = desDirectionalBoreholeService.getDirectBoreholeCount(type, roadwayId);
                    //System.out.println("type:" + type + ",roadwayID:" + roadwayId + ",count:" + count);
                }
                // 构造返回值
                if (!desRetMaps.containsKey(type)) {
                    HashMap<Integer, Integer> countMap = new HashMap<>();
                    countMap.put(roadwayId, count);
                    desRetMaps.put(type, countMap);
                } else {
                    desRetMaps.get(type).put(roadwayId, count);
                }
            }
        }
        finalRetMaps.put("design", desRetMaps);
        finalRetMaps.put("actual", actRetMaps);
        return finalRetMaps;
    }

    @Override
    public HashMap<String, HashMap<String, HashMap<String, List<?>>>> getBoreholesDetail(Integer workfaceId) {
        ArrayList<Integer> roadwayIds = new ArrayList<>();
        List<TRoadway> roadways = roadwayService.selectRoadwayListByWorkFace(workfaceId);
        for (TRoadway roadway : roadways) {
            roadwayIds.add(roadway.getId());
        }
        ArrayList<Integer> types = new ArrayList<>();
        List<TBoreholeCategory> categories = categoryService.selectTBoreholeCategoryList(new TBoreholeCategory());
        for (TBoreholeCategory category : categories) {
            types.add(category.getCategoryCode());
        }
        return getBoreholesDetail(roadwayIds, types);
    }


    /**
     * 获取钻孔信息
     *
     * @param roadways
     * @param types
     * @return
     */
    @Override
    public HashMap<String, HashMap<String, HashMap<String, List<?>>>> getBoreholesDetail(ArrayList<Integer> roadways, ArrayList<Integer> types) {
        HashMap<String, HashMap<String, HashMap<String, List<?>>>> boreholesMap = new HashMap<>();

        // 区分类型
        ArrayList<Integer> ordTypes = new ArrayList<>();
        ArrayList<Integer> dirTypes = new ArrayList<>();
        for (Integer type : types) {
            if (type != 6 && type != 7) {
                ordTypes.add(type);
            } else {
                dirTypes.add(type);
            }
        }

        // 普钻
        HashMap<String, HashMap<String, List<?>>> ordinaryHoles = new HashMap<>();
        for (Integer roadway : roadways) {
            HashMap<String, List<?>> roadwayHoles = new HashMap<>();
            TBkActBoreholeInfo queryInfo = new TBkActBoreholeInfo();
            queryInfo.setRoadwayId(roadway);
            for (Integer type : ordTypes) {
                queryInfo.setCategoryCode(type);
                List<TBkActBoreholeInfo> boreholeInfos = actBoreholeInfoService.selectTBkActBoreholeInfoList(queryInfo);
                roadwayHoles.put(String.valueOf(type), boreholeInfos);
            }
            ordinaryHoles.put(String.valueOf(roadway), roadwayHoles);
        }

        // 定向钻
        HashMap<String, HashMap<String, List<?>>> directionalHoles = new HashMap<>();
        for (Integer roadway : roadways) {
            HashMap<String, List<?>> roadwayHoles = new HashMap<>();
            TActDirectionalBorehole queryInfo = new TActDirectionalBorehole();
            queryInfo.setRoadwayId(roadway);
            for (Integer type : dirTypes) {
                queryInfo.setCategoryCode(type);
                List<TActDirectionalBorehole> boreholeInfos = actDirectionalBoreholeService.selectTActDirectionalBoreholeList(queryInfo);
                for (TActDirectionalBorehole boreholeInfo : boreholeInfos) {
                    TActDirectionalDrillingPoint queryPoint = new TActDirectionalDrillingPoint();
                    queryPoint.setBoreholeId(boreholeInfo.getId());
                    boreholeInfo.settDirectionalDrillingPointList(actDirectionalDrillingPointService.selectTActDirectionalDrillingPointList(queryPoint));
                }
                roadwayHoles.put(String.valueOf(type), boreholeInfos);
            }
            directionalHoles.put(String.valueOf(roadway), roadwayHoles);
        }

        boreholesMap.put("ordinary", ordinaryHoles);
        boreholesMap.put("directional", directionalHoles);

        return boreholesMap;
    }

    @Override
    //public HashMap<String, HashMap<String, HashMap<String, List<?>>>> getBoreholes(ArrayList<Integer> roadways, ArrayList<Integer> types) {
    public HashMap<String, List<?>> getBoreholes(ArrayList<Integer> roadways, ArrayList<Integer> types) {
        //HashMap<String, HashMap<String, HashMap<String, List<?>>>> boreholesMap = new HashMap<>();
        HashMap<String, List<?>> boreholesMap = new HashMap<>();

        // 区分类型
        ArrayList<Integer> ordTypes = new ArrayList<>();
        ArrayList<Integer> dirTypes = new ArrayList<>();
        for (Integer type : types) {
            if (type != 6 && type != 7) {
                ordTypes.add(type);
            } else {
                dirTypes.add(type);
            }
        }

        // 普钻
        //HashMap<String, HashMap<String, List<?>>> ordinaryHoles = new HashMap<>();
        List<TBkActBoreholeInfo> ordinaryHoles = new ArrayList<>();
        for (Integer roadway : roadways) {
            //HashMap<String, List<?>> roadwayHoles = new HashMap<>();
            TBkActBoreholeInfo queryInfo = new TBkActBoreholeInfo();
            queryInfo.setRoadwayId(roadway);
            for (Integer type : ordTypes) {
                queryInfo.setCategoryCode(type);
                List<TBkActBoreholeInfo> boreholeInfos = actBoreholeInfoService.selectTBkActBoreholeInfoList(queryInfo);
                //roadwayHoles.put(String.valueOf(type), boreholeInfos);
                ordinaryHoles.addAll(boreholeInfos);
            }
            //ordinaryHoles.put(String.valueOf(roadway), roadwayHoles);
        }

        // 定向钻
        //HashMap<String, HashMap<String, List<?>>> directionalHoles = new HashMap<>();
        List<TActDirectionalBorehole> directionalHoles = new ArrayList<>();
        for (Integer roadway : roadways) {
            //HashMap<String, List<?>> roadwayHoles = new HashMap<>();
            TActDirectionalBorehole queryInfo = new TActDirectionalBorehole();
            queryInfo.setRoadwayId(roadway);
            for (Integer type : dirTypes) {
                queryInfo.setCategoryCode(type);
                List<TActDirectionalBorehole> boreholeInfos = actDirectionalBoreholeService.selectTActDirectionalBoreholeList(queryInfo);
                for (TActDirectionalBorehole boreholeInfo : boreholeInfos) {
                    TActDirectionalDrillingPoint queryPoint = new TActDirectionalDrillingPoint();
                    queryPoint.setBoreholeId(boreholeInfo.getId());
                    boreholeInfo.settDirectionalDrillingPointList(actDirectionalDrillingPointService.selectTActDirectionalDrillingPointList(queryPoint));
                }
                directionalHoles.addAll(boreholeInfos);
                //roadwayHoles.put(String.valueOf(type), boreholeInfos);
            }
            //directionalHoles.put(String.valueOf(roadway), roadwayHoles);
        }

        boreholesMap.put("ordinary", ordinaryHoles);
        boreholesMap.put("directional", directionalHoles);

        return boreholesMap;
    }
}
