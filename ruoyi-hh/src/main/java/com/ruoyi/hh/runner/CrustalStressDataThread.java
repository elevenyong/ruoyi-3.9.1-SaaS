package com.ruoyi.hh.runner;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.business.domain.TBkSensorData;
import com.ruoyi.hh.physical.attribute.domain.TBZKSS;
import com.ruoyi.hh.physical.attribute.domain.TBZKYL;
import com.ruoyi.hh.physical.attribute.service.ITBZKSSService;
import com.ruoyi.hh.physical.attribute.service.ITBZKYLService;
import com.ruoyi.hh.physical.attribute.service.impl.TBZKSSServiceImpl;
import com.ruoyi.hh.physical.domain.TBKGasDrainageQuantity;
import com.ruoyi.hh.util.WebSocketServer;
import com.ruoyi.hh.util.config.PerceptionDataUtil;
import lombok.SneakyThrows;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import static com.ruoyi.common.utils.DateUtils.strToDateDay;
import static java.lang.System.out;

/**
 *  读取地应力数据文件
 *  @author eleven
 *  2023年5月13日 14:04:06
 */
@Component
public class CrustalStressDataThread implements CommandLineRunner {

    /**
     * 是否读取数据
     */
//    @Value("${readData}")
//    private boolean readData;
    @Value("E:\\华鸿慧中\\智能软件研究室\\其他公司资料\\地质建模软件相关\\煤矿感知数据联网接入规范\\")
    private String filestr;
    @Value("12345678910")
    private String coalcode;
    @Resource
    private ITBZKSSService tBZKSSService;
    @Resource
    private ITBZKYLService tBZKYLService;
    @Autowired
    private RedisCache redisCache;
    @Resource
    private WebSocketServer webSocketServer;
    /**
     * 是否读取抽采数据
     */
    @Value("${readData}")
    private boolean readData;

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
                    File file = new File(filestr+"20241030");
                    //判断文件夹存在并且是文件夹
                    boolean boo = file.exists()&&file.isDirectory();
                    if (boo) {
                        String [] files=file.list();
                        if(files!=null){
                            //将文件中的CDSS列出来放到Mid中
                            for(String s:files){
                                if(s.length()==35&&s.contains(coalcode)&&s.contains("ZKSS")){
                                    Mid.add(s);
                                }
                            }
                            if(Mid.size()>0){
                                fileList.add(file+"\\"+Mid.get(0));
                                fileList.add(file+"\\"+Mid.get(Mid.size()-1));
                            }
                            for(String str:files){
                                if(str.length()==35&&str.contains(coalcode)){ //判断文件名是否符合
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
        timer.schedule(timerTask,10000,10000);
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
                    if(fileName.contains("ZKSS")){
                        analysisZKSS(str, list.contains(fileName));
                    }else if(fileName.contains("ZKYL")){
                        analysisZKYL(str);
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
                    Path path= Paths.get(fileName);
                    if(!true){
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
     * 测点实时解析 测点实时解析完成，直接通过websocket发送
     * @param line 单行内容
     */
    public void analysisZKSS(String line,boolean isInsert) throws ParseException, IOException {
        String measurePointCode; //测点编码 0
        String sensorTypeName;//传感器类型名称 1
        String sensorLocal;//测点安装位置 2
        Double value=0.0; //测点值 3
        String status; //测点状态 4
        String date; //数据时间 5
//
        String[] values=line.split(";");
        measurePointCode= values[0];
        sensorTypeName=PerceptionDataUtil.sensorType(Integer.parseInt(values[1]));
        sensorLocal=values[2];
        value=Double.parseDouble(values[3]);
        status=values[4];
        date=values[5];
        TBZKSS tbzkss=new TBZKSS();
        tbzkss.setMeasurePointCode(measurePointCode);
        tbzkss.setSensorTypeName(sensorTypeName);
        tbzkss.setSensorLocal(sensorLocal);
        tbzkss.setValue(value);
        tbzkss.setStatus(Integer.parseInt(status));
        tbzkss.setDate(date);
        out.println(tbzkss);
        if(isInsert){
            String zkss=JSONArray.fromObject(tbzkss).toString();
            webSocketServer.sendInfo(zkss);
//            tBZKSSService.insertTBZKSS(tbzkss);
        }
    }

    public void analysisZKYL(String line) throws ParseException, IOException {
        //第一步：解析出来钻孔应力测点的基本信息
        String measurePointCode;            //测点编码
        String areaName;                    //监测区名称
        String sensorTypeName;              //传感器类型
        String sensorLocal;                 //传感器位置
        Double stressmeterDepth;            //应力计安装深度
        Date installationTime;         //安装时间
        Double X;                           //X
        Double Y;                           //Y
        Double Z;                           //Z
        String direction;                   //应力计方向
        Double initializationStress;        //初始应力
        String[] values=line.split(";");
        measurePointCode= values[0];
        areaName=values[1];
        sensorTypeName=PerceptionDataUtil.sensorType(Integer.parseInt(values[2]));
        sensorLocal=values[3];
        stressmeterDepth=Double.parseDouble(values[4]);
        installationTime=strToDateDay(values[5]);
        X=Double.parseDouble(values[6]);
        Y=Double.parseDouble(values[7]);
        Z=Double.parseDouble(values[8]);
        direction=values[9];
        initializationStress=Double.parseDouble(values[10]);
        TBZKYL tbzkyl=new TBZKYL();
        tbzkyl.setMeasurePointCode(measurePointCode);
        tbzkyl.setAreaName(areaName);
        tbzkyl.setSensorTypeName(sensorTypeName);
        tbzkyl.setSensorLocal(sensorLocal);
        tbzkyl.setStressmeterDepth(stressmeterDepth);
        tbzkyl.setInstallationTime(installationTime);
        tbzkyl.setX(X);
        tbzkyl.setY(Y);
        tbzkyl.setZ(Z);
        tbzkyl.setDirection(direction);
        tbzkyl.setInitializationStress(initializationStress);
//        out.println(tbzkyl);
        //第二步：从数据库中查询出来对应的测点信息
        TBZKYL TB=tBZKYLService.getByMeasurePointCode(measurePointCode,sensorTypeName);
        //第三步：从内存中获取到对应的测点信息
        if(redisCache.getCacheObject(measurePointCode+sensorTypeName+"ZKYL")==null){//缓存中没有该条数据
            redisCache.setCacheObject(measurePointCode+sensorTypeName+"ZKYL",tbzkyl);//将数据存入缓存中
            if(TB!=null){
                if(!tbzkyl.compareValue(TB)){
                    tbzkyl.setId(TB.getId());
                    tBZKYLService.updateTBZKYL(tbzkyl);
                }
            }else{
                tBZKYLService.insertTBZKYL(tbzkyl);
            }
        }else{
            if(!tbzkyl.compareValue(redisCache.getCacheObject(measurePointCode+sensorTypeName+"ZKYL"))){
                if(TB!=null){
                    tbzkyl.setId(TB.getId());
                    this.tBZKYLService.updateTBZKYL(tbzkyl);
                }else{
                    tBZKYLService.insertTBZKYL(tbzkyl);
                }
                redisCache.setCacheObject(measurePointCode+sensorTypeName+"ZKYL",tbzkyl);
            }else{
                if(TB==null){
                    tBZKYLService.insertTBZKYL(tbzkyl);
                }
            }
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
}
