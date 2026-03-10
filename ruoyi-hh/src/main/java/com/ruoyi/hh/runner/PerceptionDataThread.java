package com.ruoyi.hh.runner;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.business.domain.TBkSensorData;
import com.ruoyi.hh.business.domain.TSensorData;
import com.ruoyi.hh.business.domain.TSensorDataException;
import com.ruoyi.hh.business.service.ITBkSensorDataService;
import com.ruoyi.hh.business.service.ITSensorDataExceptionService;
import com.ruoyi.hh.business.service.ITSensorDataService;
import com.ruoyi.hh.physical.domain.TBKGasDrainageQuantity;
import com.ruoyi.hh.physical.domain.TStationInfo;
import com.ruoyi.hh.physical.domain.TSubstationInfo;
import com.ruoyi.hh.physical.service.ITStationInfoService;
import com.ruoyi.hh.physical.service.ITSubstationInfoService;
import com.ruoyi.hh.util.Enum.MeasurePointWarnEnum;
import com.ruoyi.hh.util.Enum.SysCodeEnum;
import com.ruoyi.hh.util.WebSocketServer;
import com.ruoyi.hh.util.config.PerceptionDataUtil;
import lombok.SneakyThrows;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.*;

@Component
public class PerceptionDataThread implements CommandLineRunner {

    @Value("${file}")
    private String filestr;
    @Value("${coalcode}")
    private String coalcode;
    @Value("${workfacecode}")
    private String workfacecode;
    @Value("${deleteRedisCDDY}")
    private Boolean deleteRedisCDDY;
    /**
     * 是否读取抽采数据
     */
    @Value("${readData}")
    private boolean readData;
    /**
     * 删除Redis中的数据
     */
    @Value("${deleteRedisCDSS}")
    private boolean deleteRedisCDSS;
    @Value("${demonstrate}")
    private boolean demonstrate;
    @Resource
    private ITBkSensorDataService itBkSensorDataService;
    @Resource
    private ITSensorDataService itSensorDataService;
    @Resource
    private ITSensorDataExceptionService itSensorDataExceptionService;
    @Resource
    private ITStationInfoService itStationInfoService;
    @Resource
    private ITSubstationInfoService itSubstationInfoService ;
    @Resource
    private WebSocketServer webSocketServer;
    @Resource
    private RedisCache redisCache;



    @Override
    public void run(String... args) throws Exception {
        TimerTask timerTask = new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                if(readData){
                    List<String> Mid=new ArrayList<>();
                    List<String> fileList=new ArrayList<>();
//                File file = new File(filestr+getFolderName());
                    File file = new File(filestr+"2023_05-13");
                    //判断文件夹存在并且是文件夹
                    boolean boo = file.exists()&&file.isDirectory();
                    if (boo) {
                        String [] files=file.list();
                        if(files!=null){
                            //将文件中的CDSS列出来放到Mid中
                            for(String s:files){
                                if(s.length()==42&&s.contains(coalcode)&&s.contains("CDSS")){
                                    Mid.add(s);
                                }
                            }
                            if(Mid.size()>0){
                                fileList.add(file+"\\"+Mid.get(0));
                                fileList.add(file+"\\"+Mid.get(Mid.size()-1));
                            }
                            for(String str:files){
                                if(str.length()==42&&str.contains(coalcode)){ //判断文件名是否符合
                                    String filename=file+"\\"+str;
                                    readLine(filename,fileList);
                                }
                            }
                        }
                    }else{
                        //System.out.println("找不到指定的文件");
                    }
                }

            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,5000,5000);
    }

    /**
     * 获取时间格式的文件夹
     * @return 结果
     */
    public String getFolderName(){
        return DateUtils.get_Date();
    }

    /**
     * 获取文件夹下的所有文件
     * @param file
     * @return 结果
     */
    public String[] getFileList(File file){
        return file.list();
    }

    /**
     * 根据文件名称切割
     * @param fileName 单行内容
     * @return 结果
     */
    public void readLine(String fileName,List<String> list){

        boolean analysisComplete = false;
        BufferedReader bufferedReader=null;
        try{
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");//避免中文乱码
            //构造一个BufferedReader类来读取文件
            bufferedReader = new BufferedReader(isr);
            String linetxt = null;
            //result用来存储文件内容
            StringBuilder result = new StringBuilder();
            //按使用readLine方法，一次读一行
            int line=0;
            while ((linetxt = bufferedReader.readLine()) != null) {
                result.append(linetxt);
            }
            String [] results=result.toString().split("~");
            for(String str:results){
                if(line==0){
                    line++;
                    continue;
                }
                if(!"||".equals(str)){
                    if(fileName.contains("CDSS")){
                        analysisCDSS(str, list.contains(fileName));
                    }else if(fileName.contains("CDDY")){
                        analysisCDDY(str);
                    }else if(fileName.contains("TJSJ")){
                        analysisTJSJ(str);
                    }else if(fileName.contains("YCBJ")){
                        analysisYCBJ(str);
                    }else if(fileName.contains("FZDY")){
                        analysisFZDY(str);
                    }else if(fileName.contains("FZSS")){
                        analysisFZSS(str);
                    }
                    line++;
                }else{
                    analysisComplete=true;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(bufferedReader!=null){
                    bufferedReader.close();
                }
                if(analysisComplete){
                    Path path=Paths.get(fileName);
                    if(!demonstrate){
                        Files.deleteIfExists(path);
                        System.err.println("删除"+fileName+"成功");
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }

    /**
     *测点基本信息解析,解析完成后查看是否有该测点，没有保存到数据库中，并放入缓存中
     * @param line 单行内容
     */
    public void analysisCDDY(String line) throws ParseException {
        String measurePointCode=null; //测点编码 0
        String sysCode=null; //系统编码 1
        String substationCode=null; //分站编码 2
        String sensorTypeName=null; //传感器类型 3
        String measurePointValueType=null; //测点数值类型 4
        String measurePointValueUnit = null; //测点数值单位 5
        double hightRange=0; //高量程 6
        double lowRange=0; //低量程 7
        double upWarn=0; //上限报警门限 8
        double upCancelWarn=0; //上限解报门限； 9
        double lowWarn=0; //下限报警门限 10
        double lowCancelWarn=0; //下限解报门限 11
        double upOutage=0; //上限断电门限 12
        double upOnpower=0; //上限复电门限 13
        double lowOutage=0; //下限断电门限 14
        double lowOnpower=0; //下限复电门限 15
        int onDescribe=0; //开描述 16
        int stopDescribe=0; //停描述 17
        String measurePointLocal=null;//测点安装位置 18
        double X=0.0; //X坐标 19
        double Y=0.0; //Y坐标 20
        double Z=0.0; //Z坐标 21
        String sensorRelevance; //传感器关联关系                                                                                                    22
        Date time; //数据时间 23
        String[] values=line.split(";");
        measurePointCode= PerceptionDataUtil.analysisMeasuringPointCode(values[0]);
        if(values[5]!=null){
            measurePointValueUnit=values[5];
        }
        if(values[6]!=null){
            hightRange=stringToDouble(values[6]);
        }
        if(values[7]!=null){
            lowRange=stringToDouble(values[7]);
        }
        if(values[8]!=null){
            upWarn=stringToDouble(values[8]);
        }
        if(values[9]!=null){
            upCancelWarn=stringToDouble(values[9]);
        }
        if(values[10]!=null){
            lowWarn=stringToDouble(values[10]);
        }
        if(values[11]!=null){
            lowCancelWarn=stringToDouble(values[11]);
        }
        if(values[12]!=null){
            upOutage=stringToDouble(values[12]);
        }
        if(values[13]!=null){
            upOnpower=stringToDouble(values[13]);
        }
        if(values[14]!=null){
            lowOutage=stringToDouble(values[14]);
        }
        if(values[15]!=null){
            lowOnpower=stringToDouble(values[15]);
        }
        if(values[16]!=null){
            onDescribe=stringToInt(values[16]);
        }
        if(values[17]!=null){
            stopDescribe=stringToInt(values[17]);
        }
        sysCode= SysCodeEnum.getSysCode(values[1]);
        substationCode=PerceptionDataUtil.analysisSubstationCode(values[2]);
        Map<String,String> map=  redisCache.getCacheMap("sensorTypeCode");
        sensorTypeName=map.get(values[3]);
        measurePointValueType=values[4];
        measurePointLocal=values[18];
        X=stringToDouble(values[19]);
        Y=stringToDouble(values[20]);
        Z=stringToDouble(values[21]);
        sensorRelevance=values[22];
        time=stringToDate(values[23]);
        TStationInfo stationInfo=new TStationInfo();
        stationInfo.setMeasurePointCode(measurePointCode);
        stationInfo.setSysCode(sysCode);
        stationInfo.setSubstationCode(substationCode);
        stationInfo.setSensorTypeName(sensorTypeName);
        stationInfo.setMeasurePointValueType(measurePointValueType);
        stationInfo.setMeasurePointValueUnit(measurePointValueUnit);
        stationInfo.setHightRange(hightRange);
        stationInfo.setLowRange(lowRange);
        stationInfo.setUpWarn(upWarn);
        stationInfo.setUpCancelWarn(upCancelWarn);
        stationInfo.setLowWarn(lowWarn);
        stationInfo.setLowCancelWarn(lowCancelWarn);
        stationInfo.setUpOutage(upOutage);
        stationInfo.setUpOnpower(upOnpower);
        stationInfo.setLowOutage(lowOutage);
        stationInfo.setLowOnpower(lowOnpower);
        if(measurePointValueType.equals("KG")){
            stationInfo.setOnDescribe(PerceptionDataUtil.CDDYDescribe(onDescribe));
            stationInfo.setStopDescribe(PerceptionDataUtil.CDDYDescribe(stopDescribe));
        }
        stationInfo.setMeasurePointLocal(measurePointLocal);
        stationInfo.setX(X);
        stationInfo.setY(Y);
        stationInfo.setZ(Z);
        stationInfo.setSensorRelevance(sensorRelevance);
        stationInfo.setTime(time);
        if(deleteRedisCDDY){
            if(measurePointLocal.contains(workfacecode)){
                redisCache.deleteObject(measurePointCode+sensorTypeName+"CDDY");
            }
        }else{
            /**
             * 测点基本信息；Redis中保存所有测点的信息，以测点编码和测点类型+CDDY为key
             * 读取到Redis中没有该数据，将该数据保存到Redis中，并从数据库中读取是否有该条数据，没有的话插入数据；有的话查看数据所有列是否一致，不一致的话更新
             * 读取到Redis中有该条数据，对比，有不同列，做更新操作
             */
            if(measurePointLocal.contains(workfacecode)){  //判断该条数据是否为15080工作面数据
                TStationInfo si=itStationInfoService.getByMeasurePointCode(measurePointCode,sensorTypeName);
                if(redisCache.getCacheObject(measurePointCode+sensorTypeName+"CDDY")==null){ //缓存中没有该条数据
                    redisCache.setCacheObject(measurePointCode+sensorTypeName+"CDDY",stationInfo); //将数据存入缓存中
                    if(si!=null){ //数据库中有该条数据
                        if(!stationInfo.compareValue(si)){ //对比数据库中数据和最新数据是否一致
                            stationInfo.setId(si.getId());
                            this.itStationInfoService.updateByMeasurePointCode(stationInfo); // 不一致的话更新数据
                        }
                    }else{
                        itStationInfoService.insertTStationInfo(stationInfo);  //数据库中插入新数据
                    }
                }else{
                    if(!stationInfo.compareValue(redisCache.getCacheObject(measurePointCode+sensorTypeName+"CDDY"))){
                        //更新数据库中的数据，并更新缓存中的数据
                        if(si!=null){
                            stationInfo.setId(si.getId());
                            this.itStationInfoService.updateByMeasurePointCode(stationInfo);
                        }else{
                            itStationInfoService.insertTStationInfo(stationInfo);
                        }
//                        redisCache.deleteObject(measurePointCode+sensorTypeName+"CDDY");
                        redisCache.setCacheObject(measurePointCode+sensorTypeName+"CDDY",stationInfo);
                    }
                }
            }
        }
    }

    /**
     * 测点统计数据解析   测点统计数据插入到数据库中
     * @param line 单行内容
     */
    public void analysisTJSJ(String line) throws ParseException {
        String measurePointCode; //测点编码 0
        String sensorTypeName;//传感器类型名称 1
        String measurePointLocal;//测点安装位置 2
        String unit; //测点数值单位 3
        Date startTime;//开始时间 4
        Date endTime;//结束时间 5
        double maxValue;//最大值 6
        Date maxTime; //最大值时刻 7
        double minValue; //最小值 8
        Date minTime;  //最小值时刻 9
        double avgValue; //平均值 10
        Date time; //数据时间 11

        String[] values=line.split(";");
        measurePointCode=PerceptionDataUtil.analysisMeasuringPointCode(values[0]);
        Map<String,String> map=  redisCache.getCacheMap("sensorTypeCode");
        sensorTypeName=values[1];
        measurePointLocal=values[2];
        unit=values[3];
        startTime=stringToDate(values[4]);
        endTime=stringToDate(values[5]);
        maxValue=stringToDouble(values[6]);
        maxTime=stringToDate(values[7]);
        minValue=stringToDouble(values[8]);
        minTime=stringToDate(values[9]);
        avgValue=stringToDouble(values[10]);
        time=stringToDate(values[11]);
        TSensorData td=new TSensorData();
        td.setSensorCode(measurePointCode);
        td.setSensorTypeName(sensorTypeName);
        td.setMeasurePointLocal(measurePointLocal);
        td.setUnitName(unit);
        td.setStartTime(startTime);
        td.setEndTime(endTime);
        td.setMaxValue(maxValue);
        td.setMaxTime(maxTime);
        td.setMinValue(minValue);
        td.setMinTime(minTime);
        td.setAvgValue(avgValue);
        td.setTime(time);
        if(measurePointLocal.contains(workfacecode)){
            itSensorDataService.insertTSensorData(td);
        }
    }

    /**
     * 测点实时解析 测点实时解析完成，直接通过websocket发送
     * @param line 单行内容
     */
    public void analysisCDSS(String line,boolean isInsert) throws ParseException, IOException {
        String coalMineName; //煤矿名称
        String measurePointCode; //测点编码 0
        String sensorTypeName;//传感器类型名称 1
        String measurePointLocal;//测点安装位置 2
        float measurePointValue=0.0f; //测点值 3
        String unit; //测点数值单位 4
        String status; //测点状态 5
        Date time; //数据时间 6

        String[] values=line.split(";");
        coalMineName=PerceptionDataUtil.analysisCoalMineName(values[0]);
        measurePointCode=PerceptionDataUtil.analysisMeasuringPointCode(values[0]);
//        Map<String,String> map=  redisCache.getCacheMap("sensorTypeCode");
        sensorTypeName=values[1];
        measurePointLocal=values[2];
        try{
            measurePointValue=Float.parseFloat(values[3]);
        }catch (NumberFormatException e){
            System.err.println(values[3]+"格式错误");
        }
        unit=values[4];
        status=values[5];
        time=stringToDate(values[6]);
        TBkSensorData tBkSensorData=new TBkSensorData();
        tBkSensorData.setCoalMineName(coalMineName);
        tBkSensorData.setMeasurePointCode(measurePointCode);
        tBkSensorData.setSensorTypeName(sensorTypeName);
        tBkSensorData.setMeasurePointLocal(measurePointLocal);
        tBkSensorData.setMeasurePointValue(measurePointValue);
        tBkSensorData.setUnit(unit);
        tBkSensorData.setStatus(PerceptionDataUtil.measurePointStatus(Integer.parseInt(status)));
        tBkSensorData.setTime(time);
        List<String> measurePointCodes=this.itStationInfoService.getMeasurePointCode();  //获取所有管路上的累积量的测点编码
        if(deleteRedisCDSS){
            redisCache.deleteObject(measurePointCode+"CDSS");
        }else{
            if(redisCache.getCacheObject(measurePointCode+"CDSS")==null){//查看redis中是否该测点编码的数据
                TBKGasDrainageQuantity tdq=new TBKGasDrainageQuantity();
                if("瓦斯抽放量".equals(sensorTypeName)){
                    tdq.setMonth(getMonth(time));
                    tdq.setDrainageQuantity(measurePointValue);
                    tdq.setGasMonoBlock(0.0f);
                    tdq.setGasScalarMonth(0.0f);
                    tdq.setGasScalar(0.0f);
                }else if("瓦斯月累计纯量".equals(sensorTypeName)){
                    tdq.setDrainageQuantity(0.0f);
                    tdq.setGasScalarMonth(measurePointValue);
                    tdq.setGasMonoBlock(0.0f);
                    tdq.setGasScalar(0.0f);
                    tdq.setMonth(getMonth(time));
                }
                redisCache.setCacheObject(measurePointCode+"CDSS", tdq);
            }else{ //
                TBKGasDrainageQuantity tdq=redisCache.getCacheObject(measurePointCode+"CDSS");
                float accumulate=0.0f;
                if("瓦斯抽放量".equals(sensorTypeName)){
                    tdq.setDrainageQuantity(measurePointValue);
                }else if("瓦斯月累计纯量".equals(sensorTypeName)){
                    if(getMonth(time)!=tdq.getMonth()){//跨月数据,将Redis中的月累计数据赋值给整月累计量
                        accumulate=tdq.getGasScalar();
                        tdq.setGasScalarMonth(tdq.getGasScalarMonth()); //月累计量
                        tdq.setGasScalar(accumulate+measurePointValue); //累计抽采纯量
                        tdq.setGasMonoBlock(tdq.getGasScalarMonth()+measurePointValue); //整月累计量
                        tdq.setDrainageQuantity(tdq.getDrainageQuantity());  //瓦斯抽放量
                        tdq.setMonth(getMonth(time));  //月份
                    }else{//不跨月数据
                        accumulate=tdq.getGasScalar();
                        tdq.setGasScalarMonth(measurePointValue);
                        tdq.setGasScalar(tdq.getGasMonoBlock()+measurePointValue);
                        tdq.setMonth(getMonth(time));
                    }
                }
                redisCache.setCacheObject(measurePointCode+"CDSS", tdq);
            }
            if(measurePointLocal.contains(workfacecode)){
                if(demonstrate){
                    String tbds = JSONArray.fromObject(tBkSensorData).toString();
                    System.out.println(tBkSensorData);
                    webSocketServer.sendInfo(tbds);
                }else{
                    itBkSensorDataService.insertTBkSensorData(tBkSensorData);
                    String tbds = JSONArray.fromObject(tBkSensorData).toString();
                    System.out.println(tBkSensorData);
                    webSocketServer.sendInfo(tbds);
                }
            }
        }


    }

    /**
     * 异常报警解析
     * @param line 单行内容
     */
    public void analysisYCBJ(String line) throws ParseException, IOException {
        String coalMineName; //煤矿名称
        String sysCode; //系统
        String measurePointCode; //测点编码 0
        String sensorTypeName;//传感器类型名称 1
        String measurePointLocal;//测点安装位置 2
        String unit; //测点数值单位 3
        String exceptionType; //异常类型 4
        Date startTime; //异常开始时间 5
        Date endTime;  //异常结束时间 6
        double maxValue; //异常期间最大值 7
        Date maxTime; //最大值时刻 8
        double minValue; //异常期间最小值 9
        Date minTime; //最小值时刻 10
        double avgValue; //异常期间平均值 11
        String reason; //异常原因 12
        String measure; //处理措施 13
        Date enterTime; //录入时间 14
        String enterPersion; //录入人 15
        Date time; //数据时间  16

        String [] values=line.split(";");
        coalMineName=PerceptionDataUtil.analysisCoalMineName(values[0]);
        sysCode=PerceptionDataUtil.analysissysCode(values[0]);
        measurePointCode=PerceptionDataUtil.analysisMeasuringPointCode(values[0]);
        sensorTypeName=values[1];
        measurePointLocal=values[2];
        unit=values[3];
        exceptionType= MeasurePointWarnEnum.getMeasurePointWarn(values[4]);
        startTime=stringToDate(values[5]);
        endTime=stringToDate(values[6]);
        maxValue = stringToDouble(values[7]);
        maxTime=stringToDate(values[8]);
        minValue=stringToDouble(values[9]);
        minTime=stringToDate(values[10]);
        avgValue=stringToDouble(values[11]);
        reason=values[12];
        measure=values[13];
        enterTime=stringToDate(values[14]);
        enterPersion=values[15];
        time=stringToDate(values[16]);
        TSensorDataException tde=new TSensorDataException();
        tde.setCoalMineName(coalMineName);
        tde.setSysCode(sysCode);
        tde.setMeasurePointCode(measurePointCode);
        tde.setSensorTypeName(sensorTypeName);
        tde.setMeasurePointLocal(measurePointLocal);
        tde.setUnit(unit);
        tde.setExceptionType(exceptionType);
        tde.setStartTime(startTime);
        tde.setEndTime(endTime);
        tde.setMaxValue(maxValue);
        tde.setMaxTime(maxTime);
        tde.setMinValue(minValue);
        tde.setMinTime(minTime);
        tde.setAvgValue(avgValue);
        tde.setReason(reason);
        tde.setMeasure(measure);
        tde.setEnterTime(enterTime);
        tde.setEnterPersion(enterPersion);
        tde.setTime(time);
//        if(measurePointLocal.contains(workfacecode)){
        if(!demonstrate){
            itSensorDataExceptionService.insertTSensorDataException(tde);  //保存异常告警
            //通过websocket发送异常告警
            webSocketServer.sendInfo(JSONArray.fromObject(tde).toString());
        }
//        }
    }


    /**
     * 分站基本信息解析
     * @param line 单行内容
     */
    public void analysisFZDY(String line){
        String substationCode; //分站编码 0
        String substationLocal; //分站安装位置 1
        double X=0.0; //X坐标  2
        double Y=0.0; //Y坐标 3
//        double Z=0.0; //Z坐标 4

        String [] values=line.split(";");
        substationCode=PerceptionDataUtil.analysisSubstationCode(values[0]);
        substationLocal=values[1];
        if(values.length>2){
            if(values[2]!=null){
                X = stringToDouble(values[2]);
            }
            if(values[3]!=null){
                Y = stringToDouble(values[3]);
            }
        }
//        if(values[4]!=null){
//            Z = stringToDouble(values[4]);
//        }
        TSubstationInfo si=new TSubstationInfo();
        si.setSubstationCode(substationCode);
        si.setSubstationLocal(substationLocal);
        si.setX(X);
        si.setY(Y);
//        si.setZ(Z);
        if(redisCache.getCacheObject(substationCode+"FZDY")==null){
            redisCache.setCacheObject(substationCode+"FZDY",si);
            if(this.itSubstationInfoService.getBySubstationCode(substationCode)!=null){
                if(!si.compareValue(itSubstationInfoService.getBySubstationCode(substationCode))){
                    this.itSubstationInfoService.updateBySubstationCode(si);
                }
            }else{
                this.itSubstationInfoService.insertTSubstationInfo(si);
            }
        }else{
            if(!si.compareValue(redisCache.getCacheObject(substationCode+"FZDY"))){
                if(this.itSubstationInfoService.getBySubstationCode(substationCode)!=null){
                    this.itSubstationInfoService.updateBySubstationCode(si);
                }else{
                    this.itSubstationInfoService.insertTSubstationInfo(si);
                }
                redisCache.deleteObject(substationCode+"FZDY");
                redisCache.setCacheObject(substationCode+"FZDY",si);
            }
        }
    }

    /**
     * 分站实时解析 分站实时数据通过websocket发送
     * @param line 单行内容
     */
    public void analysisFZSS(String line) throws ParseException, IOException {
        String substationCode; //分站编码 0
        int workStatus; // 分站运行状态 1
        int electricityStatus; //分站供电状态 2
        Date time; //数据时间 3

        String [] values=line.split(";");
        substationCode=values[0];
        workStatus=stringToInt(values[1]);
        electricityStatus=stringToInt(values[2]);
        time=stringToDate(values[3]);
        TSubstationInfo si=new TSubstationInfo();
        si.setSubstationCode(PerceptionDataUtil.analysisSubstationCode(substationCode));
        si.setWorkStatus(PerceptionDataUtil.subStationWorkStatus(workStatus));
        si.setElectricityStatus(PerceptionDataUtil.subStationElectricityStatus(electricityStatus));
        si.setTime(time);
        if(demonstrate){
            String tsi = JSONArray.fromObject(si).toString();
            System.out.println(si);
            webSocketServer.sendInfo(tsi);
        }else{
            String tsi = JSONArray.fromObject(si).toString();
            System.out.println(si);
            webSocketServer.sendInfo(tsi);
        }
    }

    /**
     * 字符串转时间
     * @param str 字符串
     * @return 结果
     * @throws ParseException
     */
    public Date stringToDate(String str) throws ParseException {
        if(!str.equals("")){
            return DateUtils.strToDate(str);
        }
        return null;
    }

    /**
     * 字符串转double
     * @param str 字符串
     * @return 结果
     */
    public Double stringToDouble(String str){
        if(!str.equals("")){
            return Double.parseDouble(str);
        }
        return 0.0;
    }

    /**
     * 字符串转Integer
     * @param str 字符串
     * @return 结果
     */
    public int stringToInt(String str){
        if(!str.equals("")){
            return Integer.parseInt(str);
        }
        return 0;
    }

    /**
     * 字符串转long
     * @param str 字符串
     * @return 结果
     */
    public long stringToLong(String str){
        if(!str.equals("")){
            return Long.parseLong(str);
        }
        return 0;
    }
    /**
     * 给定时间，获取给定时间所在的月份
     * @param time 给定时间
     * @return 月份
     */
    public int getMonth(Date time){
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int month = cal.get(Calendar.MONTH);
        return month+1;
    }

}
