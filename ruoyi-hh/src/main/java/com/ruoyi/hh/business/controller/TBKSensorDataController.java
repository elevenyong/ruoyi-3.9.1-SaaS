package com.ruoyi.hh.business.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.business.domain.MeasurePointData;
import com.ruoyi.hh.business.domain.TBkSensorData;
import com.ruoyi.hh.business.domain.TDrainagedataBefore;
import com.ruoyi.hh.business.domain.TDrainagedataRecord;
import com.ruoyi.hh.business.service.ITBkSensorDataService;
import com.ruoyi.hh.business.service.ITDrainagedataBeforeService;
import com.ruoyi.hh.business.service.ITDrainagedataRecordService;
import com.ruoyi.hh.physical.domain.TBKCDSS;
import com.ruoyi.hh.physical.domain.TBKGasDrainageQuantity;
import com.ruoyi.hh.physical.domain.TStationInfo;
import com.ruoyi.hh.physical.service.ITDrainageUnitService;
import com.ruoyi.hh.physical.service.ITStationInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * (TBKSensorData)表控制层
 * 八矿抽采动态相关Controller
 * @author Eleven
 * @since 2022-07-27 10:41:19
 */
@RestController
@RequestMapping("tBKSensorData")
public class TBKSensorDataController extends BaseController {

    @Resource
    private ITBkSensorDataService itbkSensorDataService;
    @Resource
    private ITStationInfoService itStationInfoService;
    @Resource
    private ITDrainagedataBeforeService itSensordataBeforeService;
    @Resource
    private RedisCache redisCache;
    @Resource
    private ITDrainagedataRecordService itDrainagedataRecordService;
    @Resource
    private ITDrainageUnitService itDrainageUnitService;


    /**
     * 从中间表中获取某天的日抽采报表
     * @param measurePointLocal
     * @return
     * @throws ParseException
     */
    @PostMapping("getDrainageDayReportSimple")
    public TBKCDSS getDrainageDayReportSimple(String date, String measurePointLocal) throws ParseException {
        TDrainagedataRecord dayData=this.itDrainagedataRecordService.getDayDataByLocalAndTime(measurePointLocal,date);  //数据有可能错误
        TDrainagedataRecord monthData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePointLocal,date);
        TDrainagedataRecord yesTodayMonthData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePointLocal,DateUtils.getYesToday(date));
        TDrainagedataRecord dayDrainage=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePointLocal,date);
        TDrainagedataRecord yesTodayDrainage=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePointLocal,DateUtils.getYesToday(date));
        TBKCDSS tbkcdss=new TBKCDSS();
        if(monthData!=null&&yesTodayMonthData!=null){
            tbkcdss.setGasScalarDay(monthData.getValue()-yesTodayMonthData.getValue());
            tbkcdss.setAvgScalar(twoPoint(tbkcdss.getGasScalarDay()/24/60));
        }
        if(monthData!=null){
            tbkcdss.setGasScalarMonth(monthData.getValue());
        }
        tbkcdss.setMeasurePointLocal(measurePointLocal);
        if(dayDrainage!=null&&yesTodayDrainage!=null){
            tbkcdss.setDrainageQuantity(dayDrainage.getValue()-yesTodayDrainage.getValue());
            tbkcdss.setAvgConcentration(twoPoint((tbkcdss.getGasScalarDay()/tbkcdss.getDrainageQuantity())*100));
            tbkcdss.setAvgExtractionVolume(twoPoint(tbkcdss.getDrainageQuantity()/24/60));
        }
        tbkcdss.setTime(DateUtils.strToDate(date+" 00:00:00"));
        return tbkcdss;
    }

    /**
     * 根据测点安装位置模糊查询抽采日报表
     * @param date
     * @param measurePointLocal
     * @return
     * @throws ParseException
     */
    @PostMapping("getDrainageDayReportSimpleByLocalVague")
    public AjaxResult getLocalVague(String date, String measurePointLocal) throws ParseException {
        List<TBKCDSS> tbkcdsses=new ArrayList<>();
        List<String> list=this.itDrainagedataRecordService.getLocalVague(measurePointLocal,date);
        for(String s:list){
            tbkcdsses.add(getDrainageDayReportSimple(date,s));
        }
        return AjaxResult.success(tbkcdsses);
    }


    /**
     * 测点安装位置模糊查询指定时间段内的抽采数据
     * 情况分析：
     * 1、时间开始日期为月初一号，抽采纯量为结束时间的瓦斯月累计纯量
     * 2、时间段跨月份，如5.7--6.7日：5.7日的月抽采量X，5.31日的月抽采量Y，6.7日的月抽采量Z，则计算为Y-X+Z
     * @param startDate 开始时间，格式：yyyy-MM-dd
     * @param endDate 结束时间，格式：yyyy-MM-dd
     * @param measurePointLocal 测点
     * @return
     */
    @PostMapping("getDrainageDaysReportSimpleByLocalVague")
    public AjaxResult getDrainageDaysReportSimpleByLocalVague(String startDate,String endDate,String measurePointLocal) throws ParseException {
        List<String> endPointLocals=this.itDrainagedataRecordService.getLocalVague(measurePointLocal,endDate); //获取符合条件的所有测点安装位置
        List<String> startPointLocals=this.itDrainagedataRecordService.getLocalVague(measurePointLocal,startDate); //获取符合条件的所有测点安装位置
        String formateStartTime=startDate+" 00:00:00";
        String formateEndTime=endDate+" 00:00:00";
        Date start=DateUtils.strToDate(formateStartTime);
        Date end=DateUtils.strToDate(formateEndTime);
        List<TBKCDSS> lists=new ArrayList<>();
        if(getMonth(start)!=getMonth(end)) { //时间跨月
            if(endPointLocals!=null||startPointLocals!=null){
                if(endPointLocals.size()>startPointLocals.size()){
                    for(String measurePoint:endPointLocals){
                        //获取开始时间所在月的最后一天的抽采数据 last
//                    String lastDay=getLastDay(start); //获取开始时间所在的月份的最后一天
//                    TDrainagedataRecord lastData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint,lastDay);
//                    //获取开始时间头一天的抽采数据 start
//                    TDrainagedataRecord startData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint, DateUtils.getYesToday(startDate));
//                    //获取结束时间的抽采数据 end
//                    TDrainagedataRecord endData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint,endDate);
//                    TDrainagedataRecord ssd=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint,DateUtils.getYesToday(startDate));
//                    TDrainagedataRecord endd=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint,endDate);
                        //获取开始时间所在月的最后一天的抽采数据 last
                        String lastDay=getLastDay(start); //获取开始时间所在的月份的最后一天
                        TDrainagedataRecord lastData=getMonthDataEndDate(measurePoint,lastDay);
                        //获取开始时间头一天的抽采数据 start
                        TDrainagedataRecord startData=getMonthDataStartDate(measurePoint, DateUtils.getYesToday(startDate));
                        //获取结束时间的抽采数据 end
                        TDrainagedataRecord endData=getMonthDataEndDate(measurePoint,endDate);
                        TDrainagedataRecord ssd=getDayDrainageStartDate(measurePoint,DateUtils.getYesToday(startDate));
                        TDrainagedataRecord endd=getDayDrainageEndDate(measurePoint,endDate);
                        //last-start+end
                        TBKCDSS tbkcdss=new TBKCDSS();
                        if(lastData!=null&startData!=null&endData!=null){
                            tbkcdss.setGasScalarMonth(lastData.getValue()-startData.getValue()+endData.getValue());
                            tbkcdss.setMeasurePointLocal(measurePoint);
                            tbkcdss.setDrainageQuantity(endd.getValue()-ssd.getValue());
                            tbkcdss.setAvgConcentration(twoPoint(tbkcdss.getGasScalarMonth()/tbkcdss.getDrainageQuantity()*100));
                            tbkcdss.setAvgExtractionVolume(twoPoint(tbkcdss.getDrainageQuantity()/24/60/getDays(startDate,endDate)));
                            tbkcdss.setAvgScalar(twoPoint(tbkcdss.getGasScalarMonth()/24/60/getDays(startDate,endDate)));
                            lists.add(tbkcdss);
                        }else{
                            return AjaxResult.error("数据不完整");
                        }
                    }
                }else{
                    for(String measurePoint:startPointLocals){
                        //获取开始时间所在月的最后一天的抽采数据 last
//                    String lastDay=getLastDay(start); //获取开始时间所在的月份的最后一天
//                    TDrainagedataRecord lastData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint,lastDay);
//                    //获取开始时间头一天的抽采数据 start
//                    TDrainagedataRecord startData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint, DateUtils.getYesToday(startDate));
//                    //获取结束时间的抽采数据 end
//                    TDrainagedataRecord endData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint,endDate);
//                    TDrainagedataRecord ssd=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint,DateUtils.getYesToday(startDate));
//                    TDrainagedataRecord endd=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint,endDate);
                        //获取开始时间所在月的最后一天的抽采数据 last
                        String lastDay=getLastDay(start); //获取开始时间所在的月份的最后一天
                        TDrainagedataRecord lastData=getMonthDataEndDate(measurePoint,lastDay);
                        //获取开始时间头一天的抽采数据 start
                        TDrainagedataRecord startData=getMonthDataStartDate(measurePoint, DateUtils.getYesToday(startDate));
                        //获取结束时间的抽采数据 end
                        TDrainagedataRecord endData=getMonthDataEndDate(measurePoint,endDate);
                        TDrainagedataRecord ssd=getDayDrainageStartDate(measurePoint,DateUtils.getYesToday(startDate));
                        TDrainagedataRecord endd=getDayDrainageEndDate(measurePoint,endDate);
                        //last-start+end
                        TBKCDSS tbkcdss=new TBKCDSS();
                        if(lastData!=null&startData!=null&endData!=null){
                            tbkcdss.setGasScalarMonth(lastData.getValue()-startData.getValue()+endData.getValue());
                            tbkcdss.setMeasurePointLocal(measurePoint);
                            tbkcdss.setDrainageQuantity(endd.getValue()-ssd.getValue());
                            tbkcdss.setAvgConcentration(twoPoint(tbkcdss.getGasScalarMonth()/tbkcdss.getDrainageQuantity()*100));
                            tbkcdss.setAvgExtractionVolume(twoPoint(tbkcdss.getDrainageQuantity()/24/60/getDays(startDate,endDate)));
                            tbkcdss.setAvgScalar(twoPoint(tbkcdss.getGasScalarMonth()/24/60/getDays(startDate,endDate)));
                            lists.add(tbkcdss);
                        }else{
                            return AjaxResult.error("数据不完整");
                        }
                    }
                }
                return AjaxResult.success(lists);
            }
        }else{//时间不跨月
            if(endPointLocals!=null||startPointLocals!=null) {
                if(endPointLocals.size()>startPointLocals.size()){
                    for (String measurePoint : endPointLocals) {
                        //获取开始时间的头一天的抽采数据 start
                        TBKCDSS tbkcdss=new TBKCDSS();
                        if(!isMonthStart(startDate)){
//                        TDrainagedataRecord startData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint, DateUtils.getYesToday(startDate));
//                        //获取结束时间的抽采数据 end
//                        TDrainagedataRecord endData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint,endDate);
//                        TDrainagedataRecord ssd=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint,DateUtils.getYesToday(startDate));
//                        TDrainagedataRecord endd=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint,endDate);
                            TDrainagedataRecord startData=getMonthDataStartDate(measurePoint, DateUtils.getYesToday(startDate));
                            //获取结束时间的抽采数据 end
                            TDrainagedataRecord endData=getMonthDataEndDate(measurePoint,endDate);
                            TDrainagedataRecord ssd=getDayDrainageStartDate(measurePoint,DateUtils.getYesToday(startDate));
                            TDrainagedataRecord endd=getDayDrainageEndDate(measurePoint,endDate);
                            //结束时间的抽采数据-开始时间的抽采数据
                            if(startData!=null&endData!=null){
                                tbkcdss.setGasScalarMonth(endData.getValue()-startData.getValue());
                                tbkcdss.setMeasurePointLocal(measurePoint);
                                tbkcdss.setDrainageQuantity(endd.getValue()-ssd.getValue());
                                tbkcdss.setAvgConcentration(twoPoint(tbkcdss.getGasScalarMonth()/tbkcdss.getDrainageQuantity()*100));
                                tbkcdss.setAvgExtractionVolume(twoPoint(tbkcdss.getDrainageQuantity()/24/60/getDays(startDate,endDate)));
                                tbkcdss.setAvgScalar(twoPoint(tbkcdss.getGasScalarMonth()/24/60/getDays(startDate,endDate)));
                                lists.add(tbkcdss);
                            }else{
                                return AjaxResult.error("数据不完整");
                            }
                        }else{
                            //开始时间为月初当天
//                        TDrainagedataRecord endData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint,endDate);
//                        TDrainagedataRecord endd=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint,endDate);
                            TDrainagedataRecord endData=getMonthDataEndDate(measurePoint,endDate);
                            TDrainagedataRecord endd=getDayDrainageEndDate(measurePoint,endDate);
                            tbkcdss.setGasScalarMonth(endData.getValue());
                            tbkcdss.setDrainageQuantity(endd.getValue());
                            tbkcdss.setMeasurePointLocal(measurePoint);
                            tbkcdss.setAvgConcentration(twoPoint(tbkcdss.getGasScalarMonth()/tbkcdss.getDrainageQuantity()*100));
                            tbkcdss.setAvgExtractionVolume(twoPoint(tbkcdss.getDrainageQuantity()/24/60/getDays(startDate,endDate)));
                            tbkcdss.setAvgScalar(twoPoint(tbkcdss.getGasScalarMonth()/24/60/getDays(startDate,endDate)));
                            lists.add(tbkcdss);
                        }
                    }
                }else{
                    for (String measurePoint : startPointLocals) {
                        //获取开始时间的头一天的抽采数据 start
                        TBKCDSS tbkcdss=new TBKCDSS();
                        if(!isMonthStart(startDate)){
//                        TDrainagedataRecord startData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint, DateUtils.getYesToday(startDate));
//                        //获取结束时间的抽采数据 end
//                        TDrainagedataRecord endData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint,endDate);
//                        TDrainagedataRecord ssd=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint,DateUtils.getYesToday(startDate));
//                        TDrainagedataRecord endd=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint,endDate);
                            TDrainagedataRecord startData=getMonthDataStartDate(measurePoint, DateUtils.getYesToday(startDate));
                            //获取结束时间的抽采数据 end
                            TDrainagedataRecord endData=getMonthDataEndDate(measurePoint,endDate);
                            TDrainagedataRecord ssd=getDayDrainageStartDate(measurePoint,DateUtils.getYesToday(startDate));
                            TDrainagedataRecord endd=getDayDrainageEndDate(measurePoint,endDate);
                            //结束时间的抽采数据-开始时间的抽采数据
                            if(startData!=null&endData!=null){
                                tbkcdss.setGasScalarMonth(endData.getValue()-startData.getValue());
                                tbkcdss.setMeasurePointLocal(measurePoint);
                                tbkcdss.setDrainageQuantity(endd.getValue()-ssd.getValue());
                                tbkcdss.setAvgConcentration(twoPoint(tbkcdss.getGasScalarMonth()/tbkcdss.getDrainageQuantity()*100));
                                tbkcdss.setAvgExtractionVolume(twoPoint(tbkcdss.getDrainageQuantity()/24/60/getDays(startDate,endDate)));
                                tbkcdss.setAvgScalar(twoPoint(tbkcdss.getGasScalarMonth()/24/60/getDays(startDate,endDate)));
                                lists.add(tbkcdss);
                            }else{
                                return AjaxResult.error("数据不完整");
                            }
                        }else{
                            //开始时间为月初当天
//                        TDrainagedataRecord endData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint,endDate);
//                        TDrainagedataRecord endd=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint,endDate);
                            TDrainagedataRecord endData=getMonthDataEndDate(measurePoint,endDate);
                            TDrainagedataRecord endd=getDayDrainageEndDate(measurePoint,endDate);
                            tbkcdss.setGasScalarMonth(endData.getValue());
                            tbkcdss.setDrainageQuantity(endd.getValue());
                            tbkcdss.setMeasurePointLocal(measurePoint);
                            tbkcdss.setAvgConcentration(twoPoint(tbkcdss.getGasScalarMonth()/tbkcdss.getDrainageQuantity()*100));
                            tbkcdss.setAvgExtractionVolume(twoPoint(tbkcdss.getDrainageQuantity()/24/60/getDays(startDate,endDate)));
                            tbkcdss.setAvgScalar(twoPoint(tbkcdss.getGasScalarMonth()/24/60/getDays(startDate,endDate)));
                            lists.add(tbkcdss);
                        }
                    }
                }
                return AjaxResult.success(lists);
            }
        }
        return AjaxResult.error();
    }


    /**
     * 根据测点类型和测点编码查询历史曲线
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param measurePointCode 测点编码
     * @param sensorTypeName 测点类型名称
     * @return 结果
     */
    @PostMapping("getHistoricalCurve")
    public TableDataInfo getHistoricalCurve(@RequestParam(value = "startDate", required = true) String startDate,
                                            @RequestParam(value = "endDate", required = true)String endDate,
                                            @RequestParam(value = "measurePointCode", required = true)String measurePointCode,
                                            @RequestParam(value = "sensorTypeName", required = true)String sensorTypeName) throws ParseException {
        startPage();
        return getDataTable(this.itbkSensorDataService.getHistoricalCurve(DateUtils.strToDate(startDate),DateUtils.strToDate(endDate),measurePointCode,sensorTypeName));
    }

    /**
     * 指定时间段内的数据对比
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param measurePointCode 测点编号
     * @param sensorTypeName 测点类型名称
     * @return 结果
     * @throws ParseException
     */
    @PostMapping("getMaxAvgValue")
    public TBkSensorData getMaxAvgValue(String startDate,String endDate,String measurePointCode,String sensorTypeName) throws ParseException {
        List<TBkSensorData> list=this.itbkSensorDataService.getHistoricalCurve(DateUtils.strToDate(startDate),DateUtils.strToDate(endDate),measurePointCode,sensorTypeName);
        TBkSensorData tBkSensorData =new TBkSensorData();
        if(sensorTypeName.equals("管道压力")){ // 管道压力取最小值
            tBkSensorData = list.stream()
                    .min(Comparator.comparingDouble(TBkSensorData::getMeasurePointValue))
                    .orElse(null);
            assert tBkSensorData != null;
            tBkSensorData.setMaxValue(tBkSensorData.getMeasurePointValue());
        }else{  //其他的取最大值
            tBkSensorData = list.stream()
                    .max(Comparator.comparingDouble(TBkSensorData::getMeasurePointValue))
                    .orElse(null);
            assert tBkSensorData != null;
            tBkSensorData.setMaxValue(tBkSensorData.getMeasurePointValue());
        }
        float sum=0.0f;
        int size=0;
        for(TBkSensorData tbsd:list){
            if(tbsd.getStatus().equals("正常")){
                sum+=tbsd.getMeasurePointValue();
                size++;
            }
        }
//        tBkSensorData.setAvgValue(sum/list.size());
        tBkSensorData.setAvgValue(sum/size);
        tBkSensorData.setMaxTime(tBkSensorData.getTime());
        return tBkSensorData;
    }

    /**
     *
     * @param tBkSensorData
     */
    @PostMapping("insert")
    public void insert(@RequestBody TBkSensorData tBkSensorData){
        this.itbkSensorDataService.insertTBkSensorData(tBkSensorData);
    }

    /**
     * 获取设备的瓦斯抽放量
     * @param firstList 一天中的第一包数据
     * @param lastList 一天中的最后一包数据
     * @return 结果
     */
    public List<TBkSensorData> getDrainageQuantity(List<TBkSensorData> firstList,List<TBkSensorData> lastList){
        List<TBkSensorData> drainageQuantityList=new ArrayList<>();
        for(TBkSensorData tBkSensorData:lastList){
            if(tBkSensorData.getSensorTypeName().equals("瓦斯抽放量")){
                for(TBkSensorData t:firstList){
                    if(tBkSensorData.getMeasurePointLocal().equals(t.getMeasurePointLocal())&&t.getSensorTypeName().equals("瓦斯抽放量")){
                        TBkSensorData tsd=new TBkSensorData();
                        tsd.setMeasurePointLocal(t.getMeasurePointLocal());
                        tsd.setSensorTypeName("瓦斯抽放量");
                        tsd.setMeasurePointValue(tBkSensorData.getMeasurePointValue()-t.getMeasurePointValue());
                        tsd.setTime(tBkSensorData.getTime());
                        drainageQuantityList.add(tsd);
                    }
                }
            }
        }
        return drainageQuantityList;
    }

    /**
     * 保存两位小数
     * @param data 原始数据
     * @return 结果
     */
    public double twoPoint(double data){
        return Double.parseDouble(String.format("%.2f",data));
    }

    /**
     * 给定时间，获取给定时间所在的月份
     * @param date 给定时间
     * @return 月份
     */
    public int getMonth(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month+1;
    }
    /**
     * 获取给定时间的月份的最后一天
     * @param date 给定时间
     * @return 本月的最后一天
     * @throws ParseException 异常
     */
    public String getLastDay(Date date)throws ParseException{
        // 将 Date 转换为 LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // 获取 YearMonth 对象
        YearMonth yearMonth = YearMonth.from(localDate);
        // 获取该月份的最后一天
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        // 格式化为 yyyy-MM-dd
        return lastDayOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


    /**
     * 计算两个时间相差几天（头尾都计算在内）
     * @param start 开始时间
     * @param end 结束时间
     * @return 相差天数
     * @throws ParseException 异常
     */
    public int getDays(String start,String end) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date1=sdf.parse(start);
        Date date2=sdf.parse(end);
        // 将 Date 转换为 LocalDate
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        // 计算两个日期之间的天数差
        return (int) ChronoUnit.DAYS.between(localDate1, localDate2)+1;
    }

    /**
     * 判断时间是不是某个月的第一天
     * @param time 时间
     * @return 结果
     */
    public boolean isMonthStart(String time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将字符串转换为 LocalDate 对象
        LocalDate date = LocalDate.parse(time, formatter);
        return date.getDayOfMonth()==1;
    }

    /**
     *
     * 获取某测点位置某传感器指定日期的第一包数据
     * @param time 日期
     * @param measurePointLocal 测点安装位置
     * @param sensorTypeName 传感器类型名称
     * @return 列表
     * @throws ParseException
     */
    @PostMapping("getFirstDataSomeDay")
    public List<TBkSensorData> getFirstDataSomeDay(String time,String measurePointLocal,String sensorTypeName) throws ParseException {
        List<TBkSensorData> tBkSensorDataTime=this.itbkSensorDataService.getFirstDataSomeDay_Time(DateUtils.strToDate(time+" 00:00:00"),DateUtils.strToDate(time+" 23:59:59"),measurePointLocal);
        List<TBkSensorData> tBkSensorDataAll=this.itbkSensorDataService.getDataSomeDay_All(DateUtils.strToDate(time+" 00:00:00"),DateUtils.strToDate(time+" 23:59:59"),measurePointLocal);
        List<TBkSensorData> list=new ArrayList<>();
        for(TBkSensorData tbsd:tBkSensorDataAll){
            for(TBkSensorData tBkSensorData:tBkSensorDataTime){
                if(tBkSensorData.getMinTimestamp().equals(tbsd.getTime())&&tBkSensorData.getMeasurePointCode().equals(tbsd.getMeasurePointCode())){
                    if(tbsd.getSensorTypeName().equals(sensorTypeName)){
                        list.add(tbsd);
                    }
                }
            }
            if(tBkSensorDataTime.size()==list.size()){
                break;
            }
        }
        return list;
    }

    /**
     * 获取某测点位置某传感器指定日期的最后一包数据
     * @param time 日期
     * @param measurePointLocal 测点安装位置
     * @param sensorTypeName 传感器类型名称
     * @return 列表
     * @throws ParseException
     */
    @PostMapping("getLastDataSomeDay")
    public List<TBkSensorData> getLastDataSomeDay(String time,String measurePointLocal,String sensorTypeName) throws ParseException {
        List<TBkSensorData> tBkSensorDataTime=this.itbkSensorDataService.getLastDataSomeDay_Time(DateUtils.strToDate(time+" 00:00:00"),DateUtils.strToDate(time+" 23:59:59"),measurePointLocal);
        List<TBkSensorData> tBkSensorDataAll=this.itbkSensorDataService.getDataSomeDay_All(DateUtils.strToDate(time+" 00:00:00"),DateUtils.strToDate(time+" 23:59:59"),measurePointLocal);
        List<TBkSensorData> list=new ArrayList<>();
        for(TBkSensorData tbsd:tBkSensorDataAll){
            for(TBkSensorData tBkSensorData:tBkSensorDataTime){
                if(tBkSensorData.getMinTimestamp().equals(tbsd.getTime())&&tBkSensorData.getMeasurePointCode().equals(tbsd.getMeasurePointCode())){
                    if(tbsd.getSensorTypeName().equals(sensorTypeName)){
                        list.add(tbsd);
                    }
                }
            }
            if(tBkSensorDataTime.size()==list.size()){
                break;
            }
        }
        return list;
    }

    @PostMapping("getDrainageUnitData")
    public AjaxResult getDrainageUnitData() throws ParseException {
        List<MeasurePointData> list=this.itbkSensorDataService.getMeasurePointData();
        List<MeasurePointData> drainageUnitDatas=new ArrayList<>();
        for(MeasurePointData measurePointData:list){
            MeasurePointData m=new MeasurePointData();
            m.setWorkfaceId(measurePointData.getWorkfaceId());
            m.setWorkfaceName(measurePointData.getWorkfaceName());
            m.setRoadwayId(measurePointData.getRoadwayId());
            m.setDrainageUnitId(measurePointData.getDrainageUnitId());
            m.setDrainageUnit(measurePointData.getDrainageUnit());
            m.setDrainagePipeTypeId(measurePointData.getDrainagePipeTypeId());
            m.setDrainagePipeType(measurePointData.getDrainagePipeType());
            m.setMeasurePointCode(measurePointData.getMeasurePointCode());
            m.setMeasurePointLocal(measurePointData.getMeasurePointLocal());
            m.setSerial(measurePointData.getSerial());
            m.setFarthestEnd(measurePointData.getFarthestEnd());
            m.setDataTime(measurePointData.getDataTime());
            //取正常的，同一巷道，同一管道类型的测点
            for(MeasurePointData mpd:list){
                if(measurePointData.getRoadwayId()==mpd.getRoadwayId()){  //判断属于同一个巷道的
                    if(measurePointData.getDrainagePipeTypeId()==mpd.getDrainagePipeTypeId()){  //判断是在同一个管路上
                        if(measurePointData.getSerial()!=null&&mpd.getSerial()!=null){
                            if(measurePointData.getSerial()+1==mpd.getSerial()){  //判断在同一管路上同一巷道里面的序号在下一个位置
                                if(measurePointData.getGasScalar()!=null&&mpd.getGasScalar()!=null){
                                    m.setGasScalar(measurePointData.getGasScalar()-mpd.getGasScalar());
                                    double gasReservesRemant=itDrainageUnitService.selectTDrainageUnitById(measurePointData.getDrainageUnitId()).getGasTotalReserves()*10000-m.getGasScalar();
                                    m.setGasReservesRemnant((long)gasReservesRemant);
                                    m.setGasContentRemnant(twoPoint(m.getGasReservesRemnant()/(itDrainageUnitService.selectTDrainageUnitById(measurePointData.getDrainageUnitId()).getCoalReserves()*10000)));
                                    m.setGasCompleteRatio(twoPoint(m.getGasScalar()/(itDrainageUnitService.selectTDrainageUnitById(measurePointData.getDrainageUnitId()).getCoalReserves()*10000)*100));
                                }
                                if(measurePointData.getDrainageQuantity()!=null&&mpd.getDrainageQuantity()!=null){
                                    m.setDrainageQuantity(measurePointData.getDrainageQuantity()-mpd.getDrainageQuantity());
                                }
                            }
                        }
                    }
                }
            }
            //正常的可以计算测点数据的测点
            if(m.getGasScalar()!=null&&m.getDrainageQuantity()!=null){
                drainageUnitDatas.add(m);
            }
            //最远端的测点数据
            if(measurePointData.getFarthestEnd()!=null&&measurePointData.getFarthestEnd()){
                double gasReservesRemant=itDrainageUnitService.selectTDrainageUnitById(measurePointData.getDrainageUnitId()).getGasTotalReserves()*10000-measurePointData.getGasScalar();
                measurePointData.setGasReservesRemnant((long)gasReservesRemant);
                measurePointData.setGasContentRemnant(twoPoint(measurePointData.getGasReservesRemnant()/(itDrainageUnitService.selectTDrainageUnitById(measurePointData.getDrainageUnitId()).getCoalReserves()*10000)));
                measurePointData.setGasCompleteRatio(twoPoint(measurePointData.getGasScalar()/(itDrainageUnitService.selectTDrainageUnitById(measurePointData.getDrainageUnitId()).getCoalReserves()*10000)*100));
                drainageUnitDatas.add(measurePointData);
            }
            if(measurePointData.getMeasurePointCode()==null&&measurePointData.getSerial()==null){
                drainageUnitDatas.add(measurePointData);
            }
        }
        return AjaxResult.success(drainageUnitDatas);
    }


    @PostMapping("getMeasurePointData")
    public List<MeasurePointData> getMeasurePointData() throws ParseException {
        return this.itbkSensorDataService.getMeasurePointData();
    }

    /**
     * 根据抽采单元ID查询抽采数据（查询抽采单元对应测点信息的序号，用上方测点的数据减去该测点数据，及为该抽采单元的抽采数据）
     * @CreateTime: 2023/7/12 10:49
     * @return
     */
    @PostMapping("getDataByDrainageUnitId")
    public AjaxResult getDataByDrainageUnitId(Integer drainageUnitId){
        String currentMeasurePointCode=this.itStationInfoService.getCodeByDrainageUnitId(drainageUnitId); //通过抽采单元ID得到测点编码
        String measurePointLocal=this.itStationInfoService.getMeasurePointLocalByDUid(drainageUnitId); // 根据抽采单元ID获取抽采测点安装位置
        TDrainagedataBefore tsb=this.itSensordataBeforeService.getByMeasurePointLocal(measurePointLocal);
        if(currentMeasurePointCode!=null){
            String nextMeasurePointCode;
            TStationInfo tsi=new TStationInfo();
            try {
                tsi.setDrainageUnitId(drainageUnitId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            tsi.setMeasurePointValueType("LJ");
            tsi.setPiping(true);
            TStationInfo currentStationInfo=this.itStationInfoService.selectTStationInfoList(tsi).get(0); //获取该抽采单元对应的测点信息
            TStationInfo nextStationInfo=new TStationInfo();
            nextStationInfo.setRoadwayId(currentStationInfo.getRoadwayId());
            nextStationInfo.setSerial(currentStationInfo.getSerial()+1);
            List<TStationInfo> tStationInfos=this.itStationInfoService.selectTStationInfoList(nextStationInfo);//获取同一巷道，序号+1的测点信息
            if(tStationInfos.size()==0){//说明该抽采单元对应的测点为最后一个测点，则该测点的数据及为该抽采单元的数据
                TBKGasDrainageQuantity tdq=(TBKGasDrainageQuantity)redisCache.getCacheObject(currentMeasurePointCode+"CDSS");
                if(tsb!=null){
                    tdq.setDrainageQuantity(tdq.getDrainageQuantity());  //将抽采量和系统安装之前的抽采量相加
                    tdq.setGasScalar(tdq.getGasScalar()+tsb.getAccumulateGasScalar());  //抽采纯量
                }
                return AjaxResult.success(tdq);
            }else{
                TStationInfo nexttsi=this.itStationInfoService.selectTStationInfoList(nextStationInfo).get(0);
                if(nexttsi.getDrainageUnitId()!=null){ //正常的可以找到下一个抽采单元ID
                    nextMeasurePointCode=this.itStationInfoService.getCodeByDrainageUnitId(nexttsi.getDrainageUnitId());
                    TBKGasDrainageQuantity current=redisCache.getCacheObject(currentMeasurePointCode+"CDSS");
                    TBKGasDrainageQuantity next=redisCache.getCacheObject(nextMeasurePointCode+"CDSS");
                    TBKGasDrainageQuantity tdq=Calculatedifference(current,next);
                    if(tsb!=null&&tdq!=null){
                        tdq.setDrainageQuantity(tdq.getDrainageQuantity());  //将抽采量和系统安装之前的抽采量相加
                        tdq.setGasScalar(tdq.getGasScalar()+tsb.getAccumulateGasScalar());  //抽采纯量
                    }else if(tsb!=null&&tdq==null){
                        tdq.setDrainageQuantity(0.0f);
                        tdq.setGasScalar(tsb.getAccumulateGasScalar());
                    }else if(tsb==null&&tdq!=null){
                        tdq.setDrainageQuantity(tdq.getDrainageQuantity());
                        tdq.setGasScalar(tdq.getGasScalar());
                    }
                    return AjaxResult.success(tdq);
                }
            }
            return AjaxResult.error("无法计算该抽采单元的抽采数据");
        }else{
            return AjaxResult.error("无对应的抽采单元或该单元没有统计计量设备");
        }
    }



    /**
     * 计算两个抽采量的差值
     * @param current
     * @param next
     * @return
     */
    public TBKGasDrainageQuantity Calculatedifference(TBKGasDrainageQuantity current,TBKGasDrainageQuantity next){
        TBKGasDrainageQuantity tgdq=new TBKGasDrainageQuantity();
        if(current==null||next==null){
            return null;
        }else{
            tgdq.setGasScalar(current.getGasScalar()-next.getGasScalar());
            tgdq.setGasScalarMonth(current.getGasScalarMonth()- next.getGasScalarMonth());
            tgdq.setDrainageQuantity(current.getDrainageQuantity()- next.getDrainageQuantity());
            tgdq.setMonth(current.getMonth());
            return tgdq;
        }
    }

    /**
     * 给定一个时间，将该天的抽采数据保存到中间数据库中
     * @param time 格式YYYY-MM-dd
     * @throws ParseException
     */
    @PostMapping("insertData")
    public void insertData(String time) throws ParseException {
        //获取该天抽采数据中的所有日累计测点安装位置
        List<String> locals=this.itbkSensorDataService.getDayLocal(DateUtils.strToDate(time+" 00:00:00"), DateUtils.strToDate(time+" 23:59:59"));
        if(locals!=null&&locals.size()>0){
            for(String s:locals){
                TBkSensorData tBkSensorData=getLastDataSomeDay(time,s,"瓦斯日累计纯量").get(0);
                TDrainagedataRecord tr=new TDrainagedataRecord();
                tr.setMeasurePointLocal(s);
                tr.setMeasurePointCode(tBkSensorData.getMeasurePointCode());
                tr.setDataTime(time);
                tr.setDataType(tBkSensorData.getSensorTypeName());
                tr.setValue(tBkSensorData.getMeasurePointValue());
                List<TDrainagedataRecord> list=itDrainagedataRecordService.selectTDrainagedataRecordList(tr);
                if(list.size()==0){
                    itDrainagedataRecordService.insertTDrainagedataRecord(tr);
                }else{
                    tr.setId(list.get(0).getId());
                    itDrainagedataRecordService.updateTDrainagedataRecord(tr);
                }
            }
        }
//        if(getLastDay(previousDay).equals(time)){
        List<String> locals1=this.itbkSensorDataService.getMonthLocal(DateUtils.strToDate(time+" 00:00:00"), DateUtils.strToDate(time+" 23:59:59"));
        if(locals1!=null&&locals1.size()>0){
            for(String s:locals1){
                TBkSensorData tBkSensorData=getLastDataSomeDay(time,s,"瓦斯月累计纯量").get(0);
                TDrainagedataRecord tr=new TDrainagedataRecord();
                tr.setMeasurePointLocal(s);
                tr.setMeasurePointCode(tBkSensorData.getMeasurePointCode());
                tr.setDataTime(time);
                tr.setDataType(tBkSensorData.getSensorTypeName());
                tr.setValue(tBkSensorData.getMeasurePointValue());
                List<TDrainagedataRecord> list=itDrainagedataRecordService.selectTDrainagedataRecordList(tr);
                if(list.size()==0) {
                    itDrainagedataRecordService.insertTDrainagedataRecord(tr);
                }else{
                    tr.setId(list.get(0).getId());
                    itDrainagedataRecordService.updateTDrainagedataRecord(tr);
                }
            }
        }

        List<String> locals2=this.itbkSensorDataService.getDrainageLocal(DateUtils.strToDate(time+" 00:00:00"), DateUtils.strToDate(time+" 23:59:59"));
        if(locals2!=null&&locals2.size()>0){
            for(String s:locals2){
                TBkSensorData tBkSensorData=getLastDataSomeDay(time,s,"瓦斯抽放量").get(0);
                TDrainagedataRecord tr=new TDrainagedataRecord();
                tr.setMeasurePointLocal(s);
                tr.setMeasurePointCode(tBkSensorData.getMeasurePointCode());
                tr.setDataTime(time);
                tr.setDataType(tBkSensorData.getSensorTypeName());
                tr.setValue(tBkSensorData.getMeasurePointValue());
                List<TDrainagedataRecord> list=itDrainagedataRecordService.selectTDrainagedataRecordList(tr);
                if(list.size()==0) {
                    itDrainagedataRecordService.insertTDrainagedataRecord(tr);
                }else{
                    tr.setId(list.get(0).getId());
                    itDrainagedataRecordService.updateTDrainagedataRecord(tr);
                }
            }
        }
    }


    /**
     * 手动将抽采历史数据添加到中间表中，并统计数据，更新到抽采累计量中
     * @param startDate 格式：YYYY-MM-dd
     * @param endDate 格式：YYYY-MM-dd
     */
    @PostMapping("handMovementInsertData")
    public void insertData(String startDate,String endDate) throws ParseException {
        while(DateUtils.contrastDate(startDate,endDate)){
            insertData(startDate);
            this.itbkSensorDataService.fileStationDataSum(startDate);
            startDate = DateUtils.getTomorrow(startDate);
        }
    }

    /**
     * 获取结束时间的月抽采数据（没有数据的话获取头一天的……直到获取到数据为止）
     * @param measurePoint
     * @param date
     * @return
     * @throws ParseException
     */
    public TDrainagedataRecord getMonthDataEndDate(String measurePoint,String date) throws ParseException {
        TDrainagedataRecord endData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint, DateUtils.getYesToday(date));
        while(endData==null){
            date = DateUtils.getYesToday(date);
            endData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint, DateUtils.getYesToday(date));
        }
        return endData;
    }

    /**
     * 获取开始日期的月抽采数据（没有数据的话获取第二天的数据……知道获取到数据为止）
     * @param measurePoint
     * @param date
     * @return
     * @throws ParseException
     */
    public TDrainagedataRecord getMonthDataStartDate(String measurePoint,String date) throws ParseException {
        TDrainagedataRecord startData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint, DateUtils.getTomorrow(date));
        while(startData==null){
            date = DateUtils.getTomorrow(date);
            startData=this.itDrainagedataRecordService.getMonthDataByLocalAndTime(measurePoint, DateUtils.getTomorrow(date));
        }
        return startData;
    }
    /**
     * 获取结束时间的日抽采数据（没有数据的话获取头一天的……直到获取到数据为止）
     * @param measurePoint
     * @param date
     * @return
     * @throws ParseException
     */
    public TDrainagedataRecord getDayDrainageEndDate(String measurePoint,String date) throws ParseException {
        TDrainagedataRecord endData=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint, DateUtils.getYesToday(date));
        while(endData==null){
            date = DateUtils.getYesToday(date);
            endData=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint, DateUtils.getYesToday(date));
        }
        return endData;
    }

    /**
     * 获取开始日期的日抽采数据（没有数据的话获取第二天的数据……知道获取到数据为止）
     * @param measurePoint
     * @param date
     * @return
     * @throws ParseException
     */
    public TDrainagedataRecord getDayDrainageStartDate(String measurePoint,String date) throws ParseException {
        TDrainagedataRecord startData=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint, DateUtils.getTomorrow(date));
        while(startData==null){
            date = DateUtils.getTomorrow(date);
            startData=this.itDrainagedataRecordService.getDayDrainageByLocalAndTime(measurePoint, DateUtils.getTomorrow(date));
        }
        return startData;
    }


}