package com.ruoyi.hh.util.data;//package com.ruoyi.quartz.util.data;
//
//import com.ruoyi.common.core.redis.RedisCache;
//import com.ruoyi.quartz.simulate.domain.DrainageUnitTotal;
//import com.ruoyi.hh.physical.mapper.TMeasuringPointMapper;
//import com.ruoyi.hh.physical.mapper.TWorkingFaceMapper;
//import com.ruoyi.quartz.business.service.ITSensorDataService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * 缓存初始化
// */
//@Slf4j
//@Component
//public class SensorData implements CommandLineRunner {
//
//    @Autowired
//    private RedisCache redisCache;
//    @Resource
//    private TWorkingFaceMapper tWorkingFaceMapper;
//    @Resource
//    private ITSensorDataService tSensorDataService;
//    @Resource
//    private TMeasuringPointMapper tMeasuringPointMapper;
//    @Value("${sensorinfo.workfacelevelid}")
//    private Integer workfacelevelid;
//    @Value("${printLog}")
//    private boolean printlog;
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<Integer> workfaceids=this.tWorkingFaceMapper.getWorkFaceIds();
//        List<DrainageUnitTotal> drainageUnitTotals=new ArrayList<>();
//        if(workfaceids.size()!=0){
//            for(Integer i:workfaceids){
//                //计算工作面的总抽采量
////                drainageUnitTotals.add(this.tSensorDataService.getTotalByMeasureLevelId(workfacelevelid,i,null,new Date()));
//                drainageUnitTotals.add(this.tSensorDataService.getTotalByMeasureLevelId(workfacelevelid,i,formateDate("2022-02-25 00:00:10"),formateDate("2022-02-25 11:30:06"))); //测试用
//
//                //计算每个工作面中每个抽采单元的流量统计数值，以工作面名称为key，保存到redis缓存
//                String workfacename=this.tWorkingFaceMapper.getWorefaceNameById(i);//工作面名称
//                //得到该工作面下的所有抽采单元的流量统计值
//                List<DrainageUnitTotal> list=this.tSensorDataService.getDUFlowsInWorkFace(i,null,formateDate("2022-02-25 11:30:06"));  //测试用
////                List<DrainageUnitTotal> list=this.tSensorDataService.getDrainageUnitFlowByWorkFace(i,null,new Date());
//                //将该工作面下的各个抽采单元下的流量值，以工作面名称为key，列表为value，保存到map中
//                if(redisCache.getCacheList(workfacename)!=null){
//                    redisCache.deleteObject(workfacename);
//                }
//                if(list.size()>0){
//                    redisCache.setCacheList(workfacename,list);
//                }
//
//            }
//            if(redisCache.getCacheList("sensordata")!=null){
//                redisCache.deleteObject("sensordata");
//            }
//            redisCache.setCacheList("sensordata",drainageUnitTotals);
//        }
//        //查询所有测点的累计流量保存到缓存中共存①
////        List<TMeasuringPoint> measuringPoints=tMeasuringPointMapper.selectTMeasuringPointList(new TMeasuringPoint());
////        if(measuringPoints.size()>0){
////            for(TMeasuringPoint tp:measuringPoints){
////                if(tp.getMeasuringpointname()!=null&&redisCache.getCacheObject(tp.getMeasuringpointname())!=null){
////                    //移除内存中原有的测点累计量
////                    redisCache.deleteObject(tp.getMeasuringpointname());
////                    //重新计算各测点的累计抽采量
////                    DrainageUnitTotal dut=this.tSensorDataService.getExtractionDataByMeasurePoint(tp.getId(),null,formateDate("2022-02-25 11:30:06"));//测试用
////                    redisCache.setCacheObject(tp.getMeasuringpointname(),dut);
////                }else if(tp.getMeasuringpointname()!=null&&redisCache.getCacheObject(tp.getMeasuringpointname())==null){
//////                    DrainageUnitTotal dut=this.tSensorDataService.getExtractionDataByMeasurePoint(tp.getId(),null,new Date());
////                    DrainageUnitTotal dut=this.tSensorDataService.getExtractionDataByMeasurePoint(tp.getId(),null,formateDate("2022-02-25 11:30:06"));//测试用
////                    redisCache.setCacheObject(tp.getMeasuringpointname(),dut);
////                }
////            }
////        }
//        sendDate();
//        log.info("系统启动，初始化内存中的累计抽采量");
//
//    }
//
//    public void sendDate(){
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                if(printlog){
//                    log.info("定时更新累计抽采量");
//                }
//                //定时器按照叶子节点更新统计工作面抽采总量
//                List<DrainageUnitTotal> drainageUnitTotals=redisCache.getCacheList("sensordata");
//                List<DrainageUnitTotal> list=new ArrayList<>();
//                if(drainageUnitTotals.size()>=0){
//                    for(DrainageUnitTotal drainageUnitTotal:drainageUnitTotals){
////                        DrainageUnitTotal dr=tSensorDataService.getTotalFlowByLeafNode(drainageUnitTotal.getWorkfaceid(),drainageUnitTotal.getUpToDate(),new Date());
//                        DrainageUnitTotal dr=tSensorDataService.getTotalByMeasureLevelId(workfacelevelid,drainageUnitTotal.getWorkfaceid(),drainageUnitTotal.getUpToDate(),addOneDay(drainageUnitTotal.getUpToDate()));
//                        drainageUnitTotal.setTotalpureflow(drainageUnitTotal.getTotalpureflow()+dr.getTotalpureflow());
//                        drainageUnitTotal.setTotalmixtureflow(drainageUnitTotal.getTotalmixtureflow()+dr.getTotalmixtureflow());
////                        drainageUnitTotal.setUpToDate(new Date());
//                        drainageUnitTotal.setUpToDate(addOneDay(drainageUnitTotal.getUpToDate()));//测试，一次循环+1天
//                        list.add(drainageUnitTotal);
//                    }
//                }
//                if(redisCache.getCacheList("sensordata")!=null){
//                    redisCache.deleteObject("sensordata");
//                }
//                redisCache.setCacheList("sensordata",list);
//
//                //定时器更新该工作面下的所有抽采单元的流量情况
//                List<Integer> workfaceids=tWorkingFaceMapper.getWorkFaceIds();
//                if(workfaceids.size()>0){
//                    for(Integer i:workfaceids){
//                        String workfacename=tWorkingFaceMapper.getWorefaceNameById(i);
//                        if(redisCache.getCacheList(workfacename).size()>0){
//                            //获取缓存中的抽采单元的流量
//                            List<DrainageUnitTotal> lis=redisCache.getCacheList(workfacename);
//                            List<DrainageUnitTotal> updateList=new ArrayList<>();
//                            for(DrainageUnitTotal dut:lis){
//                                DrainageUnitTotal updateDUT=new DrainageUnitTotal();
//                                //得到最新未统计的流量
//                                DrainageUnitTotal newDUT=tSensorDataService.getFlowByWorkFaceIdDUnitId(i,dut.getDrainageunitid(),dut.getUpToDate(),addOneDay(dut.getUpToDate()));
//                                //更新到新实体中
//                                updateDUT.setWorkfacename(dut.getWorkfacename());
//                                updateDUT.setWorkfaceid(dut.getWorkfaceid());
//                                updateDUT.setDrainageunitname(dut.getDrainageunitname());
//                                updateDUT.setDrainageunitid(dut.getDrainageunitid());
//                                updateDUT.setTotalmixtureflow(dut.getTotalmixtureflow()+newDUT.getTotalmixtureflow());
//                                updateDUT.setTotalpureflow(dut.getTotalpureflow()+newDUT.getTotalpureflow());
//                                updateDUT.setUpToDate(newDUT.getUpToDate());
//                                updateList.add(updateDUT);
//                            }
//                            redisCache.deleteObject(workfacename);
//                            redisCache.setCacheList(workfacename,updateList);
//                        }
//                    }
//                }
//
//                //①定时器更新各个测点的累计流量,此方法为历史数据累加存放缓存中
////                List<TMeasuringPoint> measuringPoints=tMeasuringPointMapper.selectTMeasuringPointList(new TMeasuringPoint());
////                if(measuringPoints.size()>0) {
////                    for (TMeasuringPoint tp : measuringPoints) {
////                        if(tp.getMeasuringpointname()!=null&&redisCache.getCacheObject(tp.getMeasuringpointname())!=null){
////                            DrainageUnitTotal dut=redisCache.getCacheObject(tp.getMeasuringpointname());
////                            //得到最新未统计的流量
////                            DrainageUnitTotal newDUT=tSensorDataService.getExtractionDataByMeasurePoint(tp.getId(),dut.getUpToDate(),addOneDay(dut.getUpToDate()));
////                            newDUT.setTotalmixtureflow(newDUT.getTotalmixtureflow()+dut.getTotalmixtureflow());
////                            newDUT.setTotalpureflow(newDUT.getTotalpureflow()+dut.getTotalpureflow());
////                            redisCache.deleteObject(tp.getMeasuringpointname());
////                            redisCache.setCacheObject(tp.getMeasuringpointname(),newDUT);
////                        }
////                    }
////                }
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(timerTask, 1000, 30000);
//    }
//
//    public Date formateDate(String time) throws ParseException {
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        return sdf.parse(time);
//    }
//
//
//    public Date addOneDay(Date day){
//        Calendar calendar = new GregorianCalendar();
//        calendar.setTime(day);
//        // 把日期往后增加一天,整数  往后推,负数往前移动
//        calendar.add(Calendar.DATE, 1);
//        // 这个时间就是日期往后推一天的结果
//        day = calendar.getTime();
//        return day;
//    }
//
//
//}
