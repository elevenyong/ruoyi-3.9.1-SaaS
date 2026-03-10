package com.ruoyi.hh.util.data;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.hh.util.LimitQueue;
import com.ruoyi.hh.util.Statistics;
import com.ruoyi.hh.util.WebSocketServer;
import com.ruoyi.hh.physical.service.ITDrillinginfoService;
import com.ruoyi.hh.simulate.domain.DataHistory;
import com.ruoyi.hh.simulate.service.IDataHistoryService;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import com.ruoyi.hh.simulate.domain.SimulationMeasurepointData;
import com.ruoyi.hh.simulate.service.ISimulationMeasurepointDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Component
public class simulationRealTimeData implements CommandLineRunner {

    @Resource
    private WebSocketServer webSocketServer;
    @Resource
    private ISimulationMeasurepointDateService simulationMeasurepointDateService;
    @Resource
    private ITDrillinginfoService drillinginfoService;
    @Autowired
    private ISysConfigService configService;
    @Value("${printLog}")
    private boolean printlog;
    @Value("${sensorinfo.sendfrequency}")
    private Integer sendfrequency;
    @Resource
    private RedisCache redisCache;
    private long startTime; //系统启动时间
    @Resource
    private IDataHistoryService iDataHistoryService;
    @Resource
    private RundomData rundomData;
    private Map<String, LimitQueue<SimulationMeasurepointData>> map=new HashMap<>();

    public LimitQueue<SimulationMeasurepointData> getExceptionQueue() {
        return exceptionQueue;
    }

    public void setExceptionQueue(LimitQueue<SimulationMeasurepointData> exceptionQueue) {
        this.exceptionQueue = exceptionQueue;
    }


    /**存放异常值的队列*/
    private LimitQueue<SimulationMeasurepointData> exceptionQueue=new LimitQueue<>(10);
    private int num = 1;


    @Override
    public void run(String... args) throws Exception {

//        startTime=System.currentTimeMillis();
//        TimerTask timerTask = new TimerTask() {
//
//            @SneakyThrows
//            @Override
//            public void run() {
//                List<SimulationMeasurepointData> list = simulationMeasurepointDateService.selectSimulationMeasurepointDateList(new SimulationMeasurepointData());
//                if(list.size()>0){
//                    for(SimulationMeasurepointData smd:list){
//                        LimitQueue<SimulationMeasurepointData> lq=new LimitQueue<>(10);
//                        List<String> exceptionlist=new ArrayList<>();
//                        if(smd.getMeasuringpointname()!=null) {
//                            //使数据库中的值浮动起来
//                            smd=rundomData.Rundom(smd,num);
//                            setRedisTotal(smd);
//                            //是否有异常数据
//                            if (smd.getErrfield().size() > 0) {
//                                exceptionQueue.offer(smd);
//                            }
//                            if(map.get(smd.getMeasuringpointname())==null){
//                                lq.offer(smd);
//                                map.put(smd.getMeasuringpointname(),lq);
//                            }else{
//                                map.get(smd.getMeasuringpointname()).offer(smd);
//                            }
//                        }
//                        DataHistory dh=setPrice(smd);
////                        iDataHistoryService.insertDataHistory(dh);
//                        num++;
//                        if(num>9999999){
//                            num=0;
//                        }
//                    }
//                }
//                for(Map.Entry<String,LimitQueue<SimulationMeasurepointData>> entry:map.entrySet()){
//                    if(checkisFull(entry.getValue())){
//                        if(checkDataException(entry.getValue())&&printlog){
//                            log.error(entry.getKey()+"有异常数据");
//                        }
//                    }
//                }
//                List<TDrillinginfo> tdis=drillinginfoService.selectTDrillinginfoList(null);
//                List<TDrillinginfo> dis=new ArrayList<>();
//                WaterCoalSep wcs=new WaterCoalSep();
//                double belttotal=0.0;
//                if(tdis.size()>0){
//                    for(TDrillinginfo tdi:tdis){
//                        tdi=rundomData.RoundCoalWaterData(tdi,num,startTime);
//                        dis.add(tdi);
//                        belttotal+=tdi.getCoalcumulant();
//                    }
//                }
//                wcs.setBelttotal(belttotal);
//                wcs.setDrillinginfos(dis);
//                String json = JSONArray.fromObject(list).toString();
//                String coalwaterdata = JSONArray.fromObject(dis).toString();
////                webSocketServer.sendInfo(json);
////                webSocketServer.sendInfo(coalwaterdata);
//
//            }
//        };
//        Timer timer = new Timer();
//        timer.schedule(timerTask,sendfrequency*1000,sendfrequency*1000);


    }

//    /**
//     * 取随机值
//     * @param data
//     * @return
//     */
//    public double rundomData(double data){
//        if(data==0.0){
//            return 0.1;
//        }
//        double re=Math.random()*Math.random();
//        double re1=re-Double.parseDouble(String.format("%.2f",re));
////        log.info(re1+"");
//        return Double.parseDouble(String.format("%.2f",data*(1+re1)));
//    }

    /**
     * 取两位小数
     * @param data
     * @return
     */
    public double formateData(double data){
        return Double.parseDouble(String.format("%.2f",data));
    }

    /**
     * 判断Queue是否已满
     * @param queue 队列
     * @return 结果
     */
    public boolean checkisFull(LimitQueue<SimulationMeasurepointData> queue){
        return queue.size()==queue.getLimit();
    }

    /**
     * 判断队列中的数据是否异常
     * @param queue 队列
     * @return 结果
     */
    public boolean checkDataException(LimitQueue<SimulationMeasurepointData> queue){
        boolean res=false;
        LimitQueue<Double> ch4con=new LimitQueue<>(10);
        LimitQueue<Double> coconc=new LimitQueue<>(10);
        LimitQueue<Double> mixtureflow=new LimitQueue<>(10);
        LimitQueue<Double> pureflow=new LimitQueue<>(10);
        LimitQueue<Double> negativepressure=new LimitQueue<>(10);
        LimitQueue<Double> temperature=new LimitQueue<>(10);
        for(SimulationMeasurepointData smd:queue){
            ch4con.add(smd.getCh4Concentration());
            coconc.add(smd.getCoConcentration());
            mixtureflow.add(smd.getMixTureFlow());
            pureflow.add(smd.getPureFlow());
            negativepressure.add(smd.getNegativePressure());
            temperature.add(smd.getTemperature());
        }
        Statistics statistics=new Statistics(ch4con);
        Statistics statistics1=new Statistics(coconc);
        Statistics statistics2=new Statistics(mixtureflow);
        Statistics statistics3=new Statistics(pureflow);
        Statistics statistics4=new Statistics(negativepressure);
        Statistics statistics5=new Statistics(temperature);
        List<Integer> CH4result=statistics.checkData(2.5);
        List<Integer> COresult=statistics1.checkData(2.5);
        List<Integer> mixresult=statistics2.checkData(3);
        List<Integer> pureresult=statistics3.checkData(3);
        List<Integer> negresult=statistics4.checkData(2.5);
        List<Integer> temresult=statistics5.checkData(2.5);
//        List<Double> CH4result=statistics.checkData(2.8);
//        List<Double> COresult=statistics1.checkData(1);
//        List<Double> mixresult=statistics2.checkData(2.5);
//        List<Double> pureresult=statistics3.checkData(2.5);
//        List<Double> negresult=statistics4.checkData(2.2);
//        List<Double> temresult=statistics5.checkData(2.2);
        List<ErrEntity> list=new ArrayList<>();
        if(CH4result.size()>0){
            for(int d:CH4result){
                ErrEntity ee=new ErrEntity();
                ee.setSign("CH4");
                ee.setMeasurepointid(queue.getindex(d).getMeasurePointId());
                ee.setMeasuringpointname(queue.getindex(d).getMeasurePointName());
                ee.setTime(queue.getindex(d).getDate());
                ee.setValue(queue.getindex(d).getCh4Concentration());
                list.add(ee);
            }
            res=true;
        }
        if(COresult.size()>0){
            for(int d:COresult){
                ErrEntity ee=new ErrEntity();
                ee.setSign("CO");
                ee.setMeasurepointid(queue.getindex(d).getMeasurePointId());
                ee.setMeasuringpointname(queue.getindex(d).getMeasurePointName());
                ee.setTime(queue.getindex(d).getDate());
                ee.setValue(queue.getindex(d).getCoConcentration());
                list.add(ee);
            }
            res=true;
        }
        if(mixresult.size()>0){
            for(int d:mixresult){
                ErrEntity ee=new ErrEntity();
                ee.setSign("MIX");
                ee.setMeasurepointid(queue.getindex(d).getMeasurePointId());
                ee.setMeasuringpointname(queue.getindex(d).getMeasurePointName());
                ee.setTime(queue.getindex(d).getDate());
                ee.setValue(queue.getindex(d).getMixTureFlow());
                list.add(ee);
            }
            res=true;
        }
        if(pureresult.size()>0){
            for(int d:pureresult){
                ErrEntity ee=new ErrEntity();
                ee.setSign("PURE");
                ee.setMeasurepointid(queue.getindex(d).getMeasurePointId());
                ee.setMeasuringpointname(queue.getindex(d).getMeasurePointName());
                ee.setTime(queue.getindex(d).getDate());
                ee.setValue(queue.getindex(d).getMixTureFlow());
                list.add(ee);
            }
            res=true;
        }
        if(negresult.size()>0){
            for(int d:negresult){
                ErrEntity ee=new ErrEntity();
                ee.setSign("NEG");
                ee.setMeasurepointid(queue.getindex(d).getMeasurePointId());
                ee.setMeasuringpointname(queue.getindex(d).getMeasurePointName());
                ee.setTime(queue.getindex(d).getDate());
                ee.setValue(queue.getindex(d).getNegativePressure());
                list.add(ee);
            }
            res=true;
        }
        if(temresult.size()>0){
            for(int d:temresult){
                ErrEntity ee=new ErrEntity();
                ee.setSign("TEM");
                ee.setMeasurepointid(queue.getindex(d).getMeasurePointId());
                ee.setMeasuringpointname(queue.getindex(d).getMeasurePointName());
                ee.setTime(queue.getindex(d).getDate());
                ee.setValue(queue.getindex(d).getTemperature());
                list.add(ee);
            }
            res=true;
        }
        for(SimulationMeasurepointData smd:queue){
            smd.setExceptionfield(list);
        }
        return res;
    }

    /**
     * 模拟存储历史数据
     * @param smd 实时数据
     * @return 历史数据
     */
    public DataHistory setPrice(SimulationMeasurepointData smd){
         DataHistory dh=new DataHistory();
         dh.setMeasurepointid(smd.getMeasurePointId());
         dh.setMeasuringpointname(smd.getMeasurePointName());
         dh.setUnitname(smd.getUnitName());
         dh.setWorkingfacename(smd.getWorkfaceName());
         dh.setCh4concentration(smd.getCh4Concentration());
         dh.setCo2concentration(smd.getCh4Concentration());
         dh.setCoconcentration(smd.getCoConcentration());
         dh.setDrainageunitid(smd.getDrainageUnitId());
         dh.setEthene(smd.getEthene());
         dh.setEthyne(smd.getEthyne());
         dh.setNegativepressure(smd.getNegativePressure());
         dh.setOconcentration(smd.getOConcentration());
         dh.setPureflow(smd.getPureFlow());
         dh.setTemperature(smd.getTemperature());
         dh.setMixtureflow(smd.getMixTureFlow());
         dh.setDate(smd.getDate());
         return dh;
    }

    public void  setRedisTotal(SimulationMeasurepointData smd){
        if(redisCache.getCacheObject(smd.getMeasurePointName())!=null){
            redisCache.deleteObject(smd.getMeasurePointName());
            redisCache.setCacheObject(smd.getMeasurePointName(),smd);
        }else{
            redisCache.setCacheObject(smd.getMeasurePointName(),smd);
        }
    }

}
