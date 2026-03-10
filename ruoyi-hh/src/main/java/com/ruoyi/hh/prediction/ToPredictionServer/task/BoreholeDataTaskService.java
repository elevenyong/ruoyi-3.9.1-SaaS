package com.ruoyi.hh.prediction.ToPredictionServer.task;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.ruoyi.hh.prediction.ToPredictionServer.config.TaskConfig;
import com.ruoyi.hh.prediction.ToPredictionServer.domain.SampleData;
import com.ruoyi.hh.prediction.ToPredictionServer.service.IAttributeValueService;
import com.ruoyi.hh.prediction.ToPredictionServer.service.IBoreholeInfoService;
import com.ruoyi.hh.prediction.ToPredictionServer.service.IWorkOfCoalMiningService;
import com.ruoyi.hh.prediction.ToPredictionServer.service.IWorkingFaceOfCoalMiningService;
import com.ruoyi.hh.prediction.ToPredictionServer.util.DataPositionParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.service.impl
 * @Project：WSCCManageBackend
 * @name：BoreholeDataTaskService
 * @Date：2026/1/20 16:01
 * @Filename：BoreholeDataTaskService
 * @Description
 */
@Service
public class BoreholeDataTaskService {

    private static final Logger log = LoggerFactory.getLogger(BoreholeDataTaskService.class);

    @Resource
    private TaskConfig taskConfig;

    @Resource
    private IBoreholeInfoService boreholeInfoService;

    @Resource
    private IAttributeValueService attributeValueService;

    @Resource
    private IWorkOfCoalMiningService workOfCoalMiningService;

    @Resource
    private IWorkingFaceOfCoalMiningService workingFaceOfCoalMiningService;

    @Resource
    private RestTemplate restTemplate;

    private static final int ATTRIBUTE_ID_GAS_EMISSION_Q = 6;
    private static final int ATTRIBUTE_ID_DRILLING_CUTTINGS_S = 7;
    private static final int ATTRIBUTE_ID_COAL_THICKNESS = 10;

    // 定义日期格式
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private boolean isRunning = false;
    private final Object lock = new Object();

    /**
     * 定时任务执行方法
     */
    @Scheduled(cron = "${borehole.task.schedule-cron:0 0 0 */4 * ?}")
    public void executeBoreholeDataTask() {
        synchronized (lock) {
            if (isRunning) {
                log.info("钻孔数据任务正在执行，跳过本次执行");
                return;
            }

            isRunning = true;
            try {
                log.info("============================== 开始执行钻孔数据定时任务 ==============================");
                log.info("定时器配置: {}", taskConfig.getScheduleCron());
                log.info("钻孔类别: {}", taskConfig.getBoreholeCategoryName());
                log.info("数据天数阈值: {}天", taskConfig.getDataDaysThreshold());
                log.info("每天数据量阈值: {}条", taskConfig.getDailyDataThreshold());

                processBoreholeData();

                log.info("============================== 钻孔数据定时任务执行完成 ==============================");
            } catch (Exception e) {
                log.error("钻孔数据定时任务执行失败", e);
            } finally {
                isRunning = false;
            }
        }
    }

    private void processBoreholeData() {
        log.info("步骤1: 查询{}类型的数据", taskConfig.getBoreholeCategoryName());

        // 1. 查询指定类型的钻孔数据
        List<Map<String, Object>> boreholeList = boreholeInfoService.selectVerificationBoreholes(
                taskConfig.getBoreholeCategoryName());

        log.info("查询结果: 共{}条数据", boreholeList.size());

        if (boreholeList.isEmpty()) {
            log.warn("没有找到'{}'类型的数据，请检查以下内容：", taskConfig.getBoreholeCategoryName());
            log.warn("1. t_borehole_category表中是否有'{}'记录", taskConfig.getBoreholeCategoryName());
            log.warn("2. t_bk_act_borehole_info表的category_code是否正确");
            log.warn("3. 数据是否满足drilling_date条件");
            return;
        }

        // 打印查询到的钻孔信息
        for (int i = 0; i < Math.min(boreholeList.size(), 5); i++) {
            Map<String, Object> borehole = boreholeList.get(i);
            log.info("钻孔{}: id={}, borehole_code={}, drilling_date={}, coal_mining_id={}, category_name={}",
                    i+1, borehole.get("id"), borehole.get("borehole_code"),
                    borehole.get("drilling_date"), borehole.get("coal_mining_id"),
                    borehole.get("category_name"));
        }

        // 2. 按钻孔日期分组
        log.info("步骤2: 按钻孔日期分组");
        Map<String, List<Map<String, Object>>> groupedByDate = groupByDrillingDate(boreholeList);
        log.info("按日期分组结果: {}个日期", groupedByDate.size());

        for (Map.Entry<String, List<Map<String, Object>>> entry : groupedByDate.entrySet()) {
            log.info("日期{}: {}条数据", entry.getKey(), entry.getValue().size());
        }

        // 3. 筛选符合条件的数据
        log.info("步骤3: 筛选符合条件的数据");
        Map<String, List<Map<String, Object>>> filteredData = filterValidData(groupedByDate);

        if (filteredData.isEmpty()) {
            log.info("没有满足条件的数据（需至少{}天，每天≥{}条）",
                    taskConfig.getDataDaysThreshold(), taskConfig.getDailyDataThreshold());
            return;
        }

        log.info("筛选后数据: {}天的数据", filteredData.size());
        for (Map.Entry<String, List<Map<String, Object>>> entry : filteredData.entrySet()) {
            log.info("日期{}: {}条有效数据", entry.getKey(), entry.getValue().size());
        }

        // 4. 解析数据为SampleData实体
        log.info("步骤4: 解析数据为SampleData实体");
        List<SampleData> sampleDataList = parseSampleData(filteredData);

        if (sampleDataList.isEmpty()) {
            log.warn("解析后没有有效数据");
            return;
        }

        // 5. 控制台输出解析后的数据
        log.info("步骤5: 输出解析后的数据");
        logSampleData(sampleDataList);

        // 6. 组织JSON数据并发送请求
        log.info("步骤6: 组织JSON数据并发送请求");
        sendTrainingRequest(sampleDataList);
    }

    private Map<String, List<Map<String, Object>>> groupByDrillingDate(List<Map<String, Object>> boreholeList) {
        Map<String, List<Map<String, Object>>> groupedData = new HashMap<>();

        for (Map<String, Object> borehole : boreholeList) {
            // 优先使用drilling_date_str字段
            Object drillingDateObj = borehole.get("drilling_date_str");
            if (drillingDateObj == null) {
                // 如果没有drilling_date_str字段，尝试使用drilling_date
                drillingDateObj = borehole.get("drilling_date");
            }

            String dateStr = null;

            try {
                if (drillingDateObj instanceof String) {
                    // 如果是字符串，直接处理
                    String dateString = (String) drillingDateObj;

                    if (dateString != null && !dateString.isEmpty()) {
                        // 提取日期部分（yyyy-MM-dd）
                        if (dateString.length() >= 10) {
                            dateStr = dateString.substring(0, 10);
                            // 验证格式是否正确
                            try {
                                DATE_FORMAT.parse(dateStr);
                            } catch (ParseException e) {
                                log.warn("日期格式不正确: {}", dateStr);
                                continue;
                            }
                        }
                    }
                } else if (drillingDateObj instanceof Date) {
                    // 如果已经是Date类型，直接格式化
                    dateStr = DATE_FORMAT.format((Date) drillingDateObj);
                } else if (drillingDateObj != null) {
                    // 其他类型，转换为字符串
                    String dateString = drillingDateObj.toString();
                    if (dateString.length() >= 10 && dateString.contains("-")) {
                        dateStr = dateString.substring(0, 10);
                        try {
                            DATE_FORMAT.parse(dateStr);
                        } catch (ParseException e) {
                            log.warn("日期格式不正确: {}", dateStr);
                            continue;
                        }
                    }
                } else {
                    log.warn("钻孔{}的drilling_date为空", borehole.get("id"));
                    continue;
                }

                if (dateStr != null) {
                    groupedData.computeIfAbsent(dateStr, k -> new ArrayList<>()).add(borehole);
                }

            } catch (Exception e) {
                log.error("处理钻孔{}的日期时出错: {}", borehole.get("id"), drillingDateObj, e);
            }
        }

        return groupedData;
    }

    private Map<String, List<Map<String, Object>>> filterValidData(Map<String, List<Map<String, Object>>> groupedData) {
        Map<String, List<Map<String, Object>>> validData = new HashMap<>();
        LocalDate now = LocalDate.now();

        int validDays = 0;

        for (Map.Entry<String, List<Map<String, Object>>> entry : groupedData.entrySet()) {
            String dateStr = entry.getKey();
            List<Map<String, Object>> dailyData = entry.getValue();

            try {
                // 解析日期字符串
                LocalDate drillingDate = LocalDate.parse(dateStr);

                // 计算天数差
                long daysBetween = ChronoUnit.DAYS.between(drillingDate, now);
                log.debug("日期{}距离今天{}天", dateStr, daysBetween);

                // 检查数据是否是N天前的数据
                if (daysBetween >= taskConfig.getDataDaysThreshold()) {
                    // 检查预测参数表中是否已存在相同数据
                    List<Map<String, Object>> filteredDailyData = filterExistingData(dailyData, dateStr);

                    if (filteredDailyData.isEmpty()) {
                        log.info("日期{}的所有数据已存在于预测参数表中，跳过", dateStr);
                        continue;
                    }

                    // 检查每天数据量阈值
                    if (filteredDailyData.size() >= taskConfig.getDailyDataThreshold()) {
                        validData.put(dateStr, filteredDailyData);
                        validDays++;
                        log.info("✅ 日期{}的数据满足条件：{}条", dateStr, filteredDailyData.size());
                    } else {
                        log.info("❌ 日期{}的数据量不足（{}条 < {}条），已删除",
                                dateStr, filteredDailyData.size(), taskConfig.getDailyDataThreshold());
                    }
                } else {
                    log.info("⏳ 日期{}的数据不足{}天，跳过", dateStr, taskConfig.getDataDaysThreshold());
                }
            } catch (Exception e) {
                log.error("处理日期{}的数据时出错", dateStr, e);
            }
        }

        // 检查是否满足至少N天的条件
        if (validDays < 2) {
            log.info("❌ 有效数据天数不足2天（实际{}天），任务结束", validDays);
            return Collections.emptyMap();
        }

        log.info("✅ 有效数据天数: {}天", validDays);
        return validData;
    }

    private List<Map<String, Object>> filterExistingData(List<Map<String, Object>> dailyData, String dateStr) {
        return dailyData.stream()
                .filter(data -> {
                    Integer coalMiningId = (Integer) data.get("coal_mining_id");
                    if (coalMiningId == null) {
                        log.warn("钻孔数据coal_mining_id为空: {}", data.get("id"));
                        return false;
                    }

                    // 检查预测参数表中是否已存在相同日期和采面ID的数据
                    int count = boreholeInfoService.countByDateAndCoalMiningId(dateStr, coalMiningId);
                    boolean exists = count > 0;

                    if (exists) {
                        log.debug("钻孔{} (coal_mining_id={})的数据已存在于预测参数表中",
                                data.get("id"), coalMiningId);
                    }

                    return count == 0;
                })
                .collect(Collectors.toList());
    }

    private List<SampleData> parseSampleData(Map<String, List<Map<String, Object>>> filteredData) {
        List<SampleData> result = new ArrayList<>();

        // 第一步：收集所有coal_mining_id
        Set<Integer> coalMiningIds = new HashSet<>();
        for (List<Map<String, Object>> dailyData : filteredData.values()) {
            for (Map<String, Object> borehole : dailyData) {
                Integer coalMiningId = (Integer) borehole.get("coal_mining_id");
                if (coalMiningId != null) {
                    coalMiningIds.add(coalMiningId);
                }
            }
        }

        // 第二步：批量查询采面信息（包含workface_id和workface_name）
        Map<Integer, Map<String, Object>> workOfCoalMiningMap = new HashMap<>();
        if (!coalMiningIds.isEmpty()) {
            workOfCoalMiningMap = workOfCoalMiningService.getWorkOfCoalMiningByIds(
                    new ArrayList<>(coalMiningIds));
            log.info("查询到{}个采面的信息", workOfCoalMiningMap.size());
        }

        // 第三步：收集所有workface_id
        Set<Integer> workfaceIds = new HashSet<>();
        for (Map<String, Object> workOfCoalMining : workOfCoalMiningMap.values()) {
            Object workfaceIdObj = workOfCoalMining.get("workface_id");
            if (workfaceIdObj instanceof Integer) {
                workfaceIds.add((Integer) workfaceIdObj);
            } else if (workfaceIdObj != null) {
                try {
                    workfaceIds.add(Integer.parseInt(workfaceIdObj.toString()));
                } catch (NumberFormatException e) {
                    log.warn("workface_id格式错误: {}", workfaceIdObj);
                }
            }
        }

        // 第四步：批量查询工作面信息
        Map<Integer, Map<String, Object>> workingFaceMap = new HashMap<>();
        if (!workfaceIds.isEmpty()) {
            workingFaceMap = workingFaceOfCoalMiningService.getWorkingFaceByIds(
                    new ArrayList<>(workfaceIds));
            log.info("查询到{}个工作面的信息", workingFaceMap.size());
        }

        int totalParsed = 0;
        int totalFailed = 0;

        for (Map.Entry<String, List<Map<String, Object>>> entry : filteredData.entrySet()) {
            String dateStr = entry.getKey();
            List<Map<String, Object>> dailyData = entry.getValue();

            try {
                // 解析日期字符串为Date
                Date drillingDate = DATE_FORMAT.parse(dateStr);

                // 按照id排序
                dailyData.sort(Comparator.comparingInt(d -> (Integer) d.get("id")));

                for (Map<String, Object> borehole : dailyData) {
                    try {
                        SampleData sampleData = parseSingleBorehole(borehole, drillingDate,
                                workOfCoalMiningMap, workingFaceMap);
                        if (sampleData != null && validateSampleData(sampleData)) {
                            result.add(sampleData);
                            totalParsed++;
                        } else {
                            totalFailed++;
                        }
                    } catch (Exception e) {
                        log.error("解析钻孔{}数据失败", borehole.get("id"), e);
                        totalFailed++;
                    }
                }
            } catch (Exception e) {
                log.error("解析日期{}的数据时出错", dateStr, e);
            }
        }

        log.info("数据解析完成: 成功{}条，失败{}条", totalParsed, totalFailed);
        return result;
    }

    private SampleData parseSingleBorehole(Map<String, Object> borehole, Date drillingDate,
                                           Map<Integer, Map<String, Object>> workOfCoalMiningMap,
                                           Map<Integer, Map<String, Object>> workingFaceMap) {
        try {
            SampleData sampleData = new SampleData();

            // 设置基本字段
            Integer boreholeId = (Integer) borehole.get("id");
            sampleData.setBoreholeId(boreholeId.toString());
            sampleData.setMeasurementDate(drillingDate);

            Integer coalMiningId = (Integer) borehole.get("coal_mining_id");
            sampleData.setWorkfaceId(coalMiningId);
            sampleData.setRoadway("mining");
            sampleData.setRoadwayId(borehole.get("roadway_id") != null ?
                    borehole.get("roadway_id").toString() : "11");
            sampleData.setWorkStage("回采");

            // 设置默认值
            sampleData.setRegionalMeasureStrength(16.5);

            // 关键修改1：从采面表中获取total_avg_advance
            Double totalAvgAdvance = getTotalAvgAdvance(coalMiningId, workOfCoalMiningMap);
            sampleData.setDistanceFromEntrance(totalAvgAdvance);
            sampleData.setFaceAdvanceDistance(totalAvgAdvance);

            // 关键修改2：设置workfaceId和workingFace
            setWorkfaceInfo(sampleData, coalMiningId, workOfCoalMiningMap, workingFaceMap);

            log.debug("钻孔{}使用total_avg_advance: {}", boreholeId, totalAvgAdvance);

            // 查询属性值
            setAttributeValues(sampleData, boreholeId);

            // 解析坐标位置
            setCoordinates(sampleData, boreholeId);

            log.debug("成功解析钻孔{}数据: q={}, s={}, 煤厚={}, total_avg_advance={}, workface_name={}",
                    boreholeId, sampleData.getGasEmissionVelocityQ(),
                    sampleData.getDrillingCuttingsS(), sampleData.getCoalThickness(),
                    totalAvgAdvance, sampleData.getWorkingFace());

            return sampleData;

        } catch (Exception e) {
            log.error("解析钻孔数据失败: boreholeId={}, error={}",
                    borehole.get("id"), e.getMessage(), e);
            return null;
        }
    }

    /**
     * 设置工作面信息
     */
    private void setWorkfaceInfo(SampleData sampleData, Integer coalMiningId,
                                 Map<Integer, Map<String, Object>> workOfCoalMiningMap,
                                 Map<Integer, Map<String, Object>> workingFaceMap) {
        if (coalMiningId == null) {
            sampleData.setWorkingFace("未知工作面");
            return;
        }

        try {
            // 从采面信息中获取workface_id
            Integer workfaceId = null;
            if (workOfCoalMiningMap.containsKey(coalMiningId)) {
                Map<String, Object> workOfCoalMining = workOfCoalMiningMap.get(coalMiningId);
                Object workfaceIdObj = workOfCoalMining.get("workface_id");
                if (workfaceIdObj instanceof Integer) {
                    workfaceId = (Integer) workfaceIdObj;
                } else if (workfaceIdObj != null) {
                    try {
                        workfaceId = Integer.parseInt(workfaceIdObj.toString());
                    } catch (NumberFormatException e) {
                        log.warn("workface_id格式错误: {}", workfaceIdObj);
                    }
                }
            }

            // 如果找到了workface_id，从工作面表中获取workface_name
            if (workfaceId != null && workingFaceMap.containsKey(workfaceId)) {
                Map<String, Object> workingFace = workingFaceMap.get(workfaceId);
                Object workfaceNameObj = workingFace.get("workface_name");
                if (workfaceNameObj != null) {
                    sampleData.setWorkingFace(workfaceNameObj.toString());
                    log.debug("设置工作面名称: {}", workfaceNameObj.toString());
                    return;
                }
            }

            // 如果没有找到，尝试从采面信息中获取workface_name（如果Mapper已经关联查询了）
            if (workOfCoalMiningMap.containsKey(coalMiningId)) {
                Map<String, Object> workOfCoalMining = workOfCoalMiningMap.get(coalMiningId);
                Object workfaceNameObj = workOfCoalMining.get("workface_name");
                if (workfaceNameObj != null) {
                    sampleData.setWorkingFace(workfaceNameObj.toString());
                    log.debug("从采面信息中获取工作面名称: {}", workfaceNameObj.toString());
                    return;
                }
            }

            // 如果还是没有，使用默认值
            sampleData.setWorkingFace("工作面" + coalMiningId);
            log.debug("使用默认工作面名称: 工作面{}", coalMiningId);

        } catch (Exception e) {
            log.error("设置工作面信息失败: coalMiningId={}, error={}", coalMiningId, e.getMessage(), e);
            sampleData.setWorkingFace("工作面" + coalMiningId);
        }
    }

    /**
     * 获取总平均进尺
     */
    private Double getTotalAvgAdvance(Integer coalMiningId, Map<Integer, Map<String, Object>> workOfCoalMiningMap) {
        if (coalMiningId == null) {
            log.warn("coal_mining_id为空，使用默认值105.5");
            return 105.5;
        }

        try {
            // 先从缓存中查找
            if (workOfCoalMiningMap.containsKey(coalMiningId)) {
                Map<String, Object> workOfCoalMining = workOfCoalMiningMap.get(coalMiningId);
                if (workOfCoalMining != null && workOfCoalMining.containsKey("total_avg_advance")) {
                    Object totalAvgAdvanceObj = workOfCoalMining.get("total_avg_advance");
                    Double totalAvgAdvance = parseDoubleValue(totalAvgAdvanceObj);
                    if (totalAvgAdvance != null) {
                        log.debug("从缓存中获取采面{}的total_avg_advance: {}", coalMiningId, totalAvgAdvance);
                        return totalAvgAdvance;
                    }
                }
            }

            // 缓存中没有，从数据库查询
            log.debug("缓存中没有采面{}的信息，从数据库查询", coalMiningId);
            Double totalAvgAdvance = workOfCoalMiningService.getTotalAvgAdvanceByCoalMiningId(coalMiningId);

            if (totalAvgAdvance != null) {
                log.debug("从数据库获取采面{}的total_avg_advance: {}", coalMiningId, totalAvgAdvance);
                return totalAvgAdvance;
            }

            // 数据库中没有，使用默认值
            log.warn("采面{}没有找到total_avg_advance字段，使用默认值105.5", coalMiningId);
            return 105.5;

        } catch (Exception e) {
            log.error("获取采面{}的total_avg_advance失败: {}", coalMiningId, e.getMessage(), e);
            return 105.5; // 默认值
        }
    }

    private Double parseDoubleValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (Exception e) {
            log.warn("解析数值失败: {}", value);
            return null;
        }
    }

    private void setAttributeValues(SampleData sampleData, Integer boreholeId) {
        // 查询瓦斯涌出速度Q
        Map<String, Object> gasEmissionData = attributeValueService.getLatestByBoreholeAndType(
                boreholeId, ATTRIBUTE_ID_GAS_EMISSION_Q);
        if (gasEmissionData != null) {
            sampleData.setGasEmissionVelocityQ(getDoubleValue(gasEmissionData.get("attribute_value_1"), 0.35));
            sampleData.setDrillingDepth(getDoubleValue(gasEmissionData.get("hole_measure_depth"), 2.0));
            sampleData.setDistanceToFace(getDoubleValue(gasEmissionData.get("hole_measure_depth"), 2.0));
            log.debug("钻孔{}的q值: {}", boreholeId, sampleData.getGasEmissionVelocityQ());
        } else {
            sampleData.setGasEmissionVelocityQ(0.35);
            sampleData.setDrillingDepth(2.0);
            sampleData.setDistanceToFace(2.0);
            log.debug("钻孔{}未找到q值，使用默认值", boreholeId);
        }

        // 查询钻屑量S
        Map<String, Object> cuttingsData = attributeValueService.getLatestByBoreholeAndType(
                boreholeId, ATTRIBUTE_ID_DRILLING_CUTTINGS_S);
        Double sValue = getDoubleValue(
                cuttingsData != null ? cuttingsData.get("attribute_value_1") : null, 2.0);
        sampleData.setDrillingCuttingsS(sValue);
        log.debug("钻孔{}的s值: {}", boreholeId, sValue);

        // 查询煤厚
        Map<String, Object> thicknessData = attributeValueService.getLatestByBoreholeAndType(
                boreholeId, ATTRIBUTE_ID_COAL_THICKNESS);
        Double thickness = getDoubleValue(
                thicknessData != null ? thicknessData.get("attribute_value_1") : null, 3.1);
        sampleData.setCoalThickness(thickness);
        log.debug("钻孔{}的煤厚: {}", boreholeId, thickness);
    }

    private Double getDoubleValue(Object value, Double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private void setCoordinates(SampleData sampleData, Integer boreholeId) {
        Map<String, Object> coordData = attributeValueService.getLatestByBoreholeId(boreholeId);
        if (coordData != null && coordData.get("data_position") != null) {
            String dataPosition = (String) coordData.get("data_position");
            Double[] coords = DataPositionParser.parseDataPosition(dataPosition);
            sampleData.setXCoord(coords[0]);
            sampleData.setYCoord(coords[1]);
            sampleData.setZCoord(coords[2]);
            log.debug("钻孔{}坐标: x={}, y={}, z={}",
                    boreholeId, coords[0], coords[1], coords[2]);
        } else {
            // 使用默认坐标
            sampleData.setXCoord(36604.0468137606);
            sampleData.setYCoord(49489.3406847466);
            sampleData.setZCoord(-657.66676577215);
            log.debug("钻孔{}使用默认坐标", boreholeId);
        }
    }

    private boolean validateSampleData(SampleData data) {
        if (data == null) {
            log.warn("SampleData为空");
            return false;
        }

        if (data.getBoreholeId() == null || data.getMeasurementDate() == null) {
            log.warn("数据缺少必要字段: boreholeId={}, measurementDate={}",
                    data.getBoreholeId(), data.getMeasurementDate());
            return false;
        }

        if (data.getGasEmissionVelocityQ() == null || data.getGasEmissionVelocityQ() < 0) {
            log.warn("瓦斯涌出速度Q异常: {}", data.getGasEmissionVelocityQ());
            return false;
        }

        if (data.getDrillingCuttingsS() == null || data.getDrillingCuttingsS() < 0) {
            log.warn("钻屑量S异常: {}", data.getDrillingCuttingsS());
            return false;
        }

        if (data.getCoalThickness() == null || data.getCoalThickness() <= 0) {
            log.warn("煤厚异常: {}", data.getCoalThickness());
            return false;
        }

        return true;
    }

    private void logSampleData(List<SampleData> sampleDataList) {
        log.info("============================== 解析后的SampleData列表 ==============================");
        log.info("共解析出{}条有效数据", sampleDataList.size());

        // 打印详细数据
        for (int i = 0; i < Math.min(sampleDataList.size(), 5); i++) {
            SampleData data = sampleDataList.get(i);
            log.info("数据{}:", i + 1);
            log.info("  - borehole_id: {}", data.getBoreholeId());
            log.info("  - measurement_date: {}", data.getMeasurementDate());
            log.info("  - workface_id: {}", data.getWorkfaceId());
            log.info("  - working_face: {}", data.getWorkingFace());
            log.info("  - gas_emission_velocity_q: {}", data.getGasEmissionVelocityQ());
            log.info("  - drilling_cuttings_s: {}", data.getDrillingCuttingsS());
            log.info("  - coal_thickness: {}", data.getCoalThickness());
            log.info("  - drilling_depth: {}", data.getDrillingDepth());
            log.info("  - distance_to_face: {}", data.getDistanceToFace());
            log.info("  - distance_from_entrance: {}", data.getDistanceFromEntrance());
            log.info("  - face_advance_distance: {}", data.getFaceAdvanceDistance());
            log.info("  - x_coord: {}, y_coord: {}, z_coord: {}",
                    data.getXCoord(), data.getYCoord(), data.getZCoord());
        }

        if (sampleDataList.size() > 5) {
            log.info("... 还有{}条数据未显示", sampleDataList.size() - 5);
        }
    }

    private void sendTrainingRequest(List<SampleData> sampleDataList) {
        try {
            // 构建请求JSON
            JSONObject requestJson = new JSONObject();
            requestJson.put("data", sampleDataList);
            requestJson.put("epochs", 3);

            // 将JSON格式化为漂亮格式用于日志输出
            String jsonString = JSON.toJSONString(requestJson,
                    JSONWriter.Feature.WriteMapNullValue,
                    JSONWriter.Feature.PrettyFormat);

            log.info("============================== 准备发送的JSON数据 ==============================");
            log.info("请求数据大小: {}条", sampleDataList.size());

            // 关键：输出完整的JSON字符串
            log.info("完整JSON数据:\n{}", jsonString);
            log.info("JSON数据长度: {} 字符", jsonString.length());

            // 检查训练接口地址
            String apiUrl = taskConfig.getTrainApiUrl();
            log.info("训练接口地址: {}", apiUrl);

            // 如果调试模式开启，不发送实际请求
            if (taskConfig.isDebugMode()) {
                log.info("调试模式已开启，跳过实际HTTP请求");
                log.info("如果要发送实际请求，请将配置 borehole.task.debug-mode 设置为 false");
                return;
            }

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(JSON.toJSONString(requestJson), headers);

            // 发送POST请求
            log.info("开始发送训练请求...");

            ResponseEntity<String> response = restTemplate.postForEntity(
                    apiUrl, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                log.info("✅ 训练请求发送成功");
                log.info("响应状态码: {}", response.getStatusCode());
                log.info("响应内容: {}", response.getBody());
            } else {
                log.warn("⚠️ 训练请求发送失败");
                log.warn("状态码: {}", response.getStatusCode());
                log.warn("响应内容: {}", response.getBody());
            }

        } catch (Exception e) {
            log.error("❌ 发送训练请求失败", e);

            // 在调试模式下，打印更详细的错误信息
            if (taskConfig.isDebugMode()) {
                log.error("错误详情:", e);
                log.error("请求URL: {}", taskConfig.getTrainApiUrl());
                log.error("数据条数: {}", sampleDataList.size());
            }
        }
    }
}