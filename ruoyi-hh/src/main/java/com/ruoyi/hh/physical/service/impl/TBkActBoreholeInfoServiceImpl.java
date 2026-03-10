package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.business.domain.TGasDrillingStandingBook;
import com.ruoyi.hh.business.mapper.TDrainageParameterMapper;
import com.ruoyi.hh.business.mapper.TGasContentPressureMapper;
import com.ruoyi.hh.business.mapper.TSealingGroutingMapper;
import com.ruoyi.hh.demo.domain.TActualBorholeException;
import com.ruoyi.hh.physical.domain.*;
import com.ruoyi.hh.physical.mapper.*;
import com.ruoyi.hh.physical.service.ITBkActBoreholeInfoService;
import com.ruoyi.hh.util.Result;
import com.ruoyi.hh.util.mapper.TBoreholeCategoryMapper;
import com.ruoyi.hh.util.mapper.TPurposeBoreholeMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.*;

/**
 * 钻孔验收单Service业务层处理
 *
 * @author eleven
 * @date 2023-05-27
 */
@Service
public class TBkActBoreholeInfoServiceImpl implements ITBkActBoreholeInfoService {
    @Resource
    private TGasContentPressureMapper tGasContentPressureMapper;
    @Resource
    private TSealingGroutingMapper tSealingGroutingMapper;
    @Resource
    private TActualFlushingMapper tActualFlushingMapper;

    @Resource
    private TDrainageParameterMapper tDrainageParameterMapper;
    @Resource
    private TBkActBoreholeInfoMapper tBkActBoreholeInfoMapper;
    @Resource
    private TRoadwayMapper tRoadwayMapper;
    @Resource
    private TRoadwayZoneMapper tRoadwayZoneMapper;
    @Resource
    private TBoreholeCategoryMapper tBoreholeCategoryMapper;
    @Resource
    private TPurposeBoreholeMapper tPurposeBoreholeMapper;
    @Resource
    private TNavigationPointMapper tNavigationPointMapper;
    @Resource
    private TAttributeValueMapper tAttributeValueMapper;

    @Override
    public List<TBkActBoreholeInfo> selectAdvanceExplorationDrillByRoadwayId(Integer roadwayId) {
        return tBkActBoreholeInfoMapper.selectAdvanceExplorationDrillByRoadwayId(roadwayId);
    }

    /**
     * 查询钻孔验收单
     *
     * @param id 钻孔验收单主键
     * @return 钻孔验收单
     */
    @Override
    public TBkActBoreholeInfo selectTBkActBoreholeInfoById(Integer id) {
        return tBkActBoreholeInfoMapper.selectTBkActBoreholeInfoById(id);
    }

    /**
     * 查询钻孔验收单列表
     *
     * @param tBkActBoreholeInfo 钻孔验收单
     * @return 钻孔验收单
     */
    @Override
    public List<TBkActBoreholeInfo> selectTBkActBoreholeInfoList(TBkActBoreholeInfo tBkActBoreholeInfo) {
        List<TBkActBoreholeInfo> boreholeInfos = tBkActBoreholeInfoMapper.selectTBkActBoreholeInfoList(tBkActBoreholeInfo);
        if (!boreholeInfos.isEmpty()) {
            // 通过巷道获取工作面ID
            TRoadway roadway = tRoadwayMapper.selectTRoadwayById(tBkActBoreholeInfo.getRoadwayId());
            TAttributeValue qValue = new TAttributeValue();
            qValue.setWorkfaceId(roadway.getWorkfaceId());
            // 获取工作面内属性参数
            List<TAttributeValue> attributeValues = tAttributeValueMapper.selectTAttributeValueList(qValue);

            // 遍历所有钻孔
            for (TBkActBoreholeInfo boreholeInfo : boreholeInfos) {
                boreholeInfo.setBindAttributesCoal1st(new ArrayList<>());
                boreholeInfo.setBindAttributesCoal2st(new ArrayList<>());
                boreholeInfo.setBindAttributesCoal3st(new ArrayList<>());

                // 赋属性值
                for (TAttributeValue attributeValue : attributeValues) {
                    if (Objects.equals(boreholeInfo.getId(), attributeValue.getBoreholeId())) {
                        boreholeInfo.getBindAttributesCoal1st().add(attributeValue);
                        attributeValue.setHoleInclinationAngle(boreholeInfo.getInclinationAngle());
                        attributeValue.setHoleDistance(boreholeInfo.getDistanceToNavigationPoint());
                        attributeValue.setHoleAzimuthAngle(Double.valueOf(boreholeInfo.getAzimuthAngle()));
                        attributeValue.setHoleRoadway(boreholeInfo.getRoadwayId());
                        /*if (Objects.equals(boreholeInfo.getBindCoal1st(), attributeValue.getRockCoalId())) {
                            boreholeInfo.getBindAttributesCoal1st().add(attributeValue);
                        } else if (Objects.equals(boreholeInfo.getBindCoal2st(), attributeValue.getRockCoalId())) {
                            boreholeInfo.getBindAttributesCoal2st().add(attributeValue);
                        } else if (Objects.equals(boreholeInfo.getBindCoal3st(), attributeValue.getRockCoalId())) {
                            boreholeInfo.getBindAttributesCoal3st().add(attributeValue);
                        }*/
                    }
                }
            }
        }

        return boreholeInfos;
    }

    /**
     * 新增钻孔验收单
     *
     * @param tBkActBoreholeInfo 钻孔验收单
     * @return 结果
     */
    @Override
    public Result insertTBkActBoreholeInfo(TBkActBoreholeInfo tBkActBoreholeInfo) {
        //通过巷道名称获取巷道ID，通过区段名称获取区段ID，通过钻孔用途获取钻孔用途ID，通过钻孔类型获取钻孔类型ID，通过导线点获取导线点ID
        if (tBkActBoreholeInfo.getRoadway() != null) {
            Integer roadwayId = this.tRoadwayMapper.getIdByRoadWayName(tBkActBoreholeInfo.getRoadway().trim()); //通过巷道名称获取巷道ID
            tBkActBoreholeInfo.setRoadwayId(roadwayId);
        }
        if (tBkActBoreholeInfo.getZoneName() != null && tBkActBoreholeInfo.getRoadway() != null) {
            Integer roadwayId = this.tRoadwayMapper.getIdByRoadWayName(tBkActBoreholeInfo.getRoadway().trim());
            Integer roadwayZoneId = this.tRoadwayZoneMapper.getIdByZoneName(tBkActBoreholeInfo.getZoneName().trim(), roadwayId); //通过巷道区段获取巷道区段ID
            tBkActBoreholeInfo.setRoadwayZoneId(roadwayZoneId);
        }
        if (tBkActBoreholeInfo.getPurposeBorehole() != null) {
            Integer purposeBoreholeid = this.tPurposeBoreholeMapper.getIdByPurposeBorehole(tBkActBoreholeInfo.getPurposeBorehole().trim()); //通过钻孔用途获取钻孔用途ID
            tBkActBoreholeInfo.setPurposeBoreholeId(purposeBoreholeid);
        }
        if (tBkActBoreholeInfo.getNavigationPointName() != null && tBkActBoreholeInfo.getRoadway() != null) {
            Integer roadwayId = this.tRoadwayMapper.getIdByRoadWayName(tBkActBoreholeInfo.getRoadway().trim());
            Integer navigationPointid = this.tNavigationPointMapper.getIdByNavigationPointName(tBkActBoreholeInfo.getNavigationPointName().trim(), roadwayId); //通过导线点名称获取导线点ID
            tBkActBoreholeInfo.setNavigationPointId(navigationPointid);
        }
        if (tBkActBoreholeInfo.getBoreholeCategory() != null) {
//            Integer boreholeCategoryid=this.tBoreholeCategoryMapper.getIdByBoreholeCategory(tBkActBoreholeInfo.getBoreholeCategory().trim()); //通过钻孔类型名称获取钻孔类型ID
//            tBkActBoreholeInfo.setBoreholeCategoryid(boreholeCategoryid);
            Integer code = this.tBoreholeCategoryMapper.getCodeByBoreholeCategory(tBkActBoreholeInfo.getBoreholeCategory().trim()); //通过钻孔类型名称获取类型编码
            tBkActBoreholeInfo.setCategoryCode(code);
        }
        List<TBkActBoreholeInfo> list = this.tBkActBoreholeInfoMapper.selectTBkActBoreholeInfoList(tBkActBoreholeInfo);
        int res = 0;
        if (list.size() > 0) {
            tBkActBoreholeInfo.setId(list.get(0).getId());
            res = updateTBkActBoreholeInfo(tBkActBoreholeInfo);
        } else {
            res = this.tBkActBoreholeInfoMapper.insertTBkActBoreholeInfo(tBkActBoreholeInfo);

            // 插入钻孔绑定属性信息
            List<TAttributeValue> bindAttributesCoal1st = tBkActBoreholeInfo.getBindAttributesCoal1st();
            List<TAttributeValue> bindAttributesCoal2st = tBkActBoreholeInfo.getBindAttributesCoal2st();
            List<TAttributeValue> bindAttributesCoal3st = tBkActBoreholeInfo.getBindAttributesCoal3st();
            if (bindAttributesCoal1st != null) {
                for (TAttributeValue attributeValue : bindAttributesCoal1st) {
                    attributeValue.setBoreholeId(tBkActBoreholeInfo.getId());
                    //attributeValue.setRockCoalId(tBkActBoreholeInfo.getBindCoal1st());
                    tAttributeValueMapper.insertTAttributeValue(attributeValue);
                }
            }
            if (bindAttributesCoal2st != null) {
                for (TAttributeValue attributeValue : bindAttributesCoal2st) {
                    attributeValue.setBoreholeId(tBkActBoreholeInfo.getId());
                    //attributeValue.setRockCoalId(tBkActBoreholeInfo.getBindCoal2st());
                    tAttributeValueMapper.insertTAttributeValue(attributeValue);
                }
            }
            if (bindAttributesCoal3st != null) {
                for (TAttributeValue attributeValue : bindAttributesCoal3st) {
                    attributeValue.setBoreholeId(tBkActBoreholeInfo.getId());
                    //attributeValue.setRockCoalId(tBkActBoreholeInfo.getBindCoal3st());
                    tAttributeValueMapper.insertTAttributeValue(attributeValue);
                }
            }

        }
        if (res > 0) {
            return Result.buildResult(Result.Status.OK);
        } else {
            return Result.buildResult(Result.Status.ERROR);
        }
    }

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    @Override
    public AjaxResult insertBatchBkActBorehole(List<TBkActBoreholeInfo> list) {
        // 先进行自身检查比较
        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new HashSet<>();

        for (TBkActBoreholeInfo borehole : list) {
            String checkCode = borehole.getGroupNo() + "-" + borehole.getBoreholeNo() + "-" + borehole.getBoreholeCategory() + "-" + borehole.getRoadwayId();
            if (!seen.add(checkCode)) {
                duplicates.add(borehole.getGroupNo() + "-" + borehole.getBoreholeNo());
            }

            /*int num = 0;
            for (TBkActBoreholeInfo bo : list) {
                if (Objects.equals(bo.getGroupNo(), borehole.getGroupNo()) &&
                        Objects.equals(bo.getBoreholeNo(), borehole.getBoreholeNo()) &&
                        Objects.equals(bo.getBoreholeCategory(), borehole.getBoreholeCategory()) &&
                        bo.getRoadwayId().equals(borehole.getRoadwayId())) {
                    num++;
                    if (num >= 2) {
                        return AjaxResult.error("数据导入失败！数据文件内钻孔数据有重复，重复孔号:" + bo.getGroupNo() + "-" + bo.getBoreholeNo());
                    }
                }
            }*/
        }
        if (!duplicates.isEmpty()) {
            return AjaxResult.error("数据导入失败！数据文件内钻孔数据有重复，重复孔号:" + duplicates);
        }

        // 再和数据库中数据做比较
        seen = new HashSet<>();
        duplicates = new HashSet<>();
        TBkActBoreholeInfo info = new TBkActBoreholeInfo();
        // 取出巷道ID并赋值
        info.setRoadwayId(list.get(0).getRoadwayId());
        List<TBkActBoreholeInfo> compareList = this.tBkActBoreholeInfoMapper.selectTBkActBoreholeInfoList(info);
        /*for (TBkActBoreholeInfo borehole : list) {
            for (TBkActBoreholeInfo bo : compareList) {
                if (Objects.equals(bo.getGroupNo(), borehole.getGroupNo()) &&
                        Objects.equals(bo.getBoreholeNo(), borehole.getBoreholeNo()) &&
                        Objects.equals(bo.getCategoryCode(), borehole.getCategoryCode()) &&
                        Objects.equals(bo.getRoadwayId(), borehole.getRoadwayId())) {
                    return AjaxResult.error("数据导入失败！导入钻孔与系统内中钻孔有重复，重复孔号:" + bo.getGroupNo() + "-" + bo.getBoreholeNo());
                }
            }
        }*/
        for (TBkActBoreholeInfo borehole : compareList) {
            seen.add(borehole.getGroupNo() + "-" + borehole.getBoreholeNo() + "-" + borehole.getBoreholeCategory() + "-" + borehole.getRoadwayId());
        }
        for (TBkActBoreholeInfo bo : list) {
            String checkCode = bo.getGroupNo() + "-" + bo.getBoreholeNo() + "-" + bo.getBoreholeCategory() + "-" + bo.getRoadwayId();
            if (!seen.add(checkCode)) {
                duplicates.add(bo.getGroupNo() + "-" + bo.getBoreholeNo());
            }
        }
        if (!duplicates.isEmpty()) {
            return AjaxResult.error("数据导入失败！导入钻孔与系统内中钻孔有重复，重复孔号:" + duplicates);
        }

        int count = 0;
        for (TBkActBoreholeInfo tBkActBoreholeInfo : list) {
            int i = this.tBkActBoreholeInfoMapper.insertTBkActBoreholeInfo(tBkActBoreholeInfo);
            count += i;

            List<TAttributeValue> bindAttributesCoal1st = tBkActBoreholeInfo.getBindAttributesCoal1st();
            List<TAttributeValue> bindAttributesCoal2st = tBkActBoreholeInfo.getBindAttributesCoal2st();
            List<TAttributeValue> bindAttributesCoal3st = tBkActBoreholeInfo.getBindAttributesCoal3st();
            if (bindAttributesCoal1st != null) {
                for (TAttributeValue attributeValue : bindAttributesCoal1st) {
                    attributeValue.setBoreholeId(tBkActBoreholeInfo.getId());
                    //attributeValue.setRockCoalId(tBkActBoreholeInfo.getBindCoal1st());
                    tAttributeValueMapper.insertTAttributeValue(attributeValue);
                }
            }
            if (bindAttributesCoal2st != null) {
                for (TAttributeValue attributeValue : bindAttributesCoal2st) {
                    attributeValue.setBoreholeId(tBkActBoreholeInfo.getId());
                    //attributeValue.setRockCoalId(tBkActBoreholeInfo.getBindCoal2st());
                    tAttributeValueMapper.insertTAttributeValue(attributeValue);
                }
            }
            if (bindAttributesCoal3st != null) {
                for (TAttributeValue attributeValue : bindAttributesCoal3st) {
                    attributeValue.setBoreholeId(tBkActBoreholeInfo.getId());
                    //attributeValue.setRockCoalId(tBkActBoreholeInfo.getBindCoal3st());
                    tAttributeValueMapper.insertTAttributeValue(attributeValue);
                }
            }

        }
        return AjaxResult.success("导入成功 " + count + " 条数据");
    }


    /**
     * 修改钻孔验收单
     *
     * @param tBkActBoreholeInfo 钻孔验收单
     * @return 结果
     */
    @Override
    public int updateTBkActBoreholeInfo(TBkActBoreholeInfo tBkActBoreholeInfo) {
        return tBkActBoreholeInfoMapper.updateTBkActBoreholeInfo(tBkActBoreholeInfo);
    }

    /**
     * 批量删除钻孔验收单
     *
     * @param ids 需要删除的钻孔验收单主键
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteTBkActBoreholeInfoByIds(Integer[] ids) {
        tAttributeValueMapper.deleteTAttributeValueByBoreholeIds(ids);
        return tBkActBoreholeInfoMapper.deleteTBkActBoreholeInfoByIds(ids);
    }

    /**
     * 删除钻孔验收单信息
     *
     * @param id 钻孔验收单主键
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteTBkActBoreholeInfoById(Integer id) {
        Integer[] ids = new Integer[]{id};
        tAttributeValueMapper.deleteTAttributeValueByBoreholeIds(ids);
        return tBkActBoreholeInfoMapper.deleteTBkActBoreholeInfoById(id);
    }

    /**
     * 根据巷道ID，组号，孔号查询ID
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号
     * @param boreholeId 孔号
     * @return ID
     */
    @Override
    public Integer getId(@RequestParam Integer roadwayId, @RequestParam String groupNo, @RequestParam String boreholeId) {
        return this.tBkActBoreholeInfoMapper.getId(roadwayId, groupNo, boreholeId);
    }

    /**
     * 水力冲孔台帐
     *
     * @param roadwayId                巷道ID
     * @param groupNo                  组号ID
     * @param drillinglocation         钻孔位置列表
     * @param boreholeNo               孔号
     * @param actualcoalamountflushed1 冲孔煤量1
     * @param actualcoalamountflushed2 冲孔煤量2
     * @param terminalholedate1        终孔时间1
     * @param terminalholedate2        终孔时间2
     * @return 列表
     */
    public List<TBKFlushingStandingBook> getTBKFlushingStandingBook(List<Integer> roadwayId,
                                                                    String groupNo,
                                                                    List<String> drillinglocation,
                                                                    List<Integer> boreholeNo,
                                                                    Double actualcoalamountflushed1,
                                                                    Double actualcoalamountflushed2,
                                                                    Date terminalholedate1,
                                                                    Date terminalholedate2
    ) {
        return this.tBkActBoreholeInfoMapper.getTBKFlushingStandingBook(roadwayId, groupNo, drillinglocation, boreholeNo, actualcoalamountflushed1, actualcoalamountflushed2, terminalholedate1, terminalholedate2);
    }

    /**
     * 八矿实钻台帐
     *
     * @return
     */
    public List<TBKActBoreholeGeneralized> getActBoreholeBook() {
        return this.tBkActBoreholeInfoMapper.getActBoreholeBook();
    }

    /**
     * 获取异常钻孔的实钻信息列表
     *
     * @return
     */
    @Override
    public List<TActualBorholeException> selectTActualBoreholeWithException() {
        return this.tBkActBoreholeInfoMapper.selectTActualBoreholeWithException();
    }

    /**
     * 根据指定信息查询钻孔基础信息
     *
     * @param roadwayId
     * @param groupNo
     * @param purposeBoreholeId
     * @param boreholeNo
     * @param startdate
     * @param enddate
     * @return
     * @CreateTime: 2023/7/17 15:02
     */
    @Override
    public List<TGasDrillingStandingBook> getActBoreholeInfo(Integer roadwayId, List<String> groupNo, List<Integer> purposeBoreholeId,
                                                             @Param("boreholeNo") List<Integer> boreholeNo, Date startdate, Date enddate) {
        if (enddate == null) {
            enddate = new Date();
        }
        return this.tBkActBoreholeInfoMapper.getActBoreholeInfo(roadwayId, groupNo, purposeBoreholeId, boreholeNo, startdate, enddate);
    }

    /**
     * 根据巷道名称、组号、孔号查询实钻ID
     *
     * @param roadway    巷道名称
     * @param groupNo    组号
     * @param boreholeNO 孔号
     * @return 实钻ID
     */
    @Override
    public Integer getIdByRoadWayAndGroupIdBoreholeno(String roadway, String groupNo, String boreholeNO) {
        return this.tBkActBoreholeInfoMapper.getIdByRoadWayAndGroupIdBoreholeno(roadway, groupNo, boreholeNO);
    }

    @Override
    public Integer delByRoadwayZoneIds(List<Integer> roadwayZoneIds) {
        List<Integer> boreholeIds = new ArrayList<>();
        for (Integer roadwayZoneId : roadwayZoneIds) {
            boreholeIds.addAll(this.tBkActBoreholeInfoMapper.getIdsRoadWayZoneId(roadwayZoneId));
        }
        if (boreholeIds.size() > 0) {
            delOthersByBoreholeIds(boreholeIds);
        }
        return this.tBkActBoreholeInfoMapper.delByRoadwayZoneIds(roadwayZoneIds);
    }

    @Override
    public AjaxResult insertAdvanceExpDrillList(Integer roadwayId, List<TBkActBoreholeInfo> list) {
        this.tBkActBoreholeInfoMapper.delAdvanceExpDrillByRoadwayId(roadwayId);
        int count = 0;
        for (TBkActBoreholeInfo info : list) {
            count += this.tBkActBoreholeInfoMapper.insertTBkActBoreholeInfo(info);
        }
        return AjaxResult.success("成功");
    }

    @Override
    public List<TBkActBoreholeInfo> getNormalActBoreholeInfoByRoadwayId(Integer roadwayId) {
        return tBkActBoreholeInfoMapper.selectActBoreholeInfoByRoadwayId(roadwayId);
    }

    @Override
    public Integer getNormalBoreholeCount(Integer type, Integer roadwayId) {
        return tBkActBoreholeInfoMapper.getNormalBoreholeCount(type, roadwayId);
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


}
