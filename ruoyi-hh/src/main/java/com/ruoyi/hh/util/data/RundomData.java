package com.ruoyi.hh.util.data;

import com.ruoyi.hh.physical.domain.TDrillinginfo;
import com.ruoyi.hh.simulate.domain.SimulationMeasurepointData;
import com.ruoyi.system.service.ISysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
/**
 * 模拟数据：累计量累加，实时量浮动
 */
public class RundomData {

    @Value("${sensorinfo.sendfrequency}")
    private Integer sendfrequency;
    @Value("${printLog}")
    private boolean printlog;
    @Resource
    private ISysConfigService configService;

    /**+
     * 模拟实时数据（从数据库中读取数据使其随机浮动）
     * @param smd 数据库中读取的值
     * @param i 下标
     * @return 浮动值
     */
    public SimulationMeasurepointData Rundom(SimulationMeasurepointData smd, int i){
        List<String> errlist=new ArrayList<>();
        double ch4=Double.parseDouble(configService.selectConfigByKey("sys.alarm.CH4Concentration"));
        double CO=Double.parseDouble(configService.selectConfigByKey("sys.alarm.COConcentration"));
        double tem=Double.parseDouble(configService.selectConfigByKey("sys.alarm.Tem"));
        double Neg=Double.parseDouble(configService.selectConfigByKey("sys.alarm.Neg"));
        /*if(smd.getMeasuringpointname()!=null) {累计量从redis中获取
            DrainageUnitTotal dut = redisCache.getCacheObject(smd.getMeasuringpointname());
            if (dut != null) {
                smd.setTotalmixtureflow(formateData(dut.getTotalmixtureflow()));
                smd.setTotalpureflow(formateData(dut.getTotalpureflow()));
                smd.setMixtureflow(rundomData(smd.getMixtureflow()));
                smd.setPureflow(rundomData(smd.getPureflow()));
            }
        }*/
        //累计量从模拟数据中直接获取,测试--------工作面id是12的工作面累计抽采量默认不变
        if(smd.getWorkfaceId()!=null&&smd.getWorkfaceId()==12){
            smd.setTotalMixTureFlow(smd.getTotalMixTureFlow());
            smd.setTotalPureFlow(smd.getTotalPureFlow());
            smd.setMixTureFlow(0.0);
            smd.setPureFlow(rundomData(0.0));
        }else{
            smd.setTotalMixTureFlow(smd.getTotalMixTureFlow()+(long)rundomData(sendfrequency*0.55*i));
            smd.setTotalPureFlow(smd.getTotalPureFlow()+(long)rundomData(sendfrequency*0.55*i));
            smd.setMixTureFlow(rundomData(smd.getMixTureFlow()));
            smd.setPureFlow(rundomData(smd.getPureFlow()));
        }
        if (i % 150 == 1&&i>150) {
            smd.setTemperature(9 + rundomData(smd.getTemperature()));
        } else {
            smd.setTemperature(rundomData(smd.getTemperature()));
        }
        if (i % 150 == 21&&i>150) {
            smd.setCh4Concentration(5 + rundomData(smd.getCh4Concentration()));
        } else {
            smd.setCh4Concentration(rundomData(smd.getCh4Concentration()));
        }
        if (i % 150 == 41&&i>150) {
            smd.setCoConcentration(1 + rundomData(smd.getCoConcentration()));
        } else {
            smd.setCoConcentration(rundomData(smd.getCoConcentration()));
        }
        if (i % 150 == 61&&i>150) {
            smd.setNegativePressure(3 + rundomData(smd.getNegativePressure()));
        } else {
            smd.setNegativePressure(rundomData(smd.getNegativePressure()));
        }
        if(i%150==81&&i>150){
            smd.setCo2Concentration(6+rundomData(smd.getCo2Concentration()));
        }else{
            smd.setCo2Concentration(rundomData(smd.getCo2Concentration()));
        }
        if(i%150==101&&i>150){
            smd.setEthyne(rundomData(6+smd.getEthyne()));
        }else{
            smd.setEthyne(rundomData(smd.getEthyne()));
        }
        if(i%150==121&&i>150){
            smd.setEthene(6+rundomData(6+smd.getEthene()));
        }else{
            smd.setEthene(rundomData(smd.getEthene()));
        }if(i%150==141&&i>150){
            smd.setOConcentration(6+rundomData(smd.getOConcentration()));
        }else{
            smd.setOConcentration(rundomData(smd.getOConcentration()));
        }
        smd.setDate(new Date());
        if (smd.getCh4Concentration() > ch4) {
            errlist.add("CH");
        }
        if (smd.getCh4Concentration() > CO) {
            errlist.add("CO");
        }
        if (smd.getTemperature() > tem) {
            errlist.add("TEM");
        }
        if (smd.getNegativePressure() > Neg) {
            errlist.add("NEG");
        }
        smd.setErrfield(errlist);
        if(printlog){
            log.error(i+"");
        }
        return smd;
    }

    /**
     * 取两位小数
     * @param data
     * @return
     */
    public long formateData(double data){
        return Long.parseLong(String.format("%.2f",data));
    }

    /**
     * 取随机值
     * @param data
     * @return
     */
    public double rundomData(double data){
        if(data==0.0){
            return 0.0;
        }
        double re=Math.random()*Math.random();
        double re1=re-Double.parseDouble(String.format("%.2f",re));
        return Double.parseDouble(String.format("%.2f",data*(1+re1)));
    }

    /**
     * 取随机值
     * @param data
     * @return
     */
    public long rundomDataLong(double data){
        if(data==0.0){
            return 0;
        }
        double re=Math.random()*Math.random();
        double re1=re-Double.parseDouble(String.format("%.2f",re));
        return Long.parseLong(String.format("%.2f",data*(1+re1)));
    }

    public TDrillinginfo RoundCoalWaterData(TDrillinginfo drillinginfo, int i,long startTime){
        drillinginfo.setFlowRate(rundomData(drillinginfo.getFlowRate()));
        drillinginfo.setWaterCumulant(Double.parseDouble(String.format("%.2f",drillinginfo.getWaterCumulant()+rundomData(sendfrequency*0.55*i))));
        drillinginfo.setMixture(Double.parseDouble(String.format("%.2f",drillinginfo.getMixture()+rundomData(sendfrequency*0.55*i))));
        drillinginfo.setConcentration(rundomData(drillinginfo.getConcentration()));
        drillinginfo.setCoalCumulant(Double.parseDouble(String.format("%.2f",drillinginfo.getCoalCumulant()+ rundomData(sendfrequency*0.55*i))));
        drillinginfo.setGasMixture(rundomData(drillinginfo.getGasMixture()));
        drillinginfo.setGasConcentration(rundomData(drillinginfo.getGasConcentration()));
        drillinginfo.setGasCumulant(Double.parseDouble(String.format("%.2f",drillinginfo.getCoalCumulant()+rundomData(sendfrequency*0.55*i))));
        drillinginfo.setCumulantTime(getTime(startTime));
        return drillinginfo;
    }

    public double getTime(long startTime){
        long interval= System.currentTimeMillis()-startTime;
        return (double) Double.parseDouble(String.format("%.2f",(interval/60000.0)));
    }



}
