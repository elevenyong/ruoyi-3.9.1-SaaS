package com.ruoyi.hh.util;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.business.controller.TBKSensorDataController;
import com.ruoyi.hh.business.domain.TBkSensorData;
import com.ruoyi.hh.business.domain.TDrainagedataRecord;
import com.ruoyi.hh.business.service.ITBkSensorDataService;
import com.ruoyi.hh.business.service.ITDrainagedataRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@EnableScheduling
public class ScriptRunnerExecSql implements ApplicationListener {

    @Resource
    private ITBkSensorDataService itbkSensorDataService;
    @Resource
    private TBKSensorDataController tbkSensorDataController;
    @Resource
    private ITDrainagedataRecordService itDrainagedataRecordService;
//    @Value("${spring.shardingsphere.datasource.master.driverClassName}")
//    @Value("${spring.datasource.master.driverClassName}")
//    private String driver;
//
////    @Value("${spring.shardingsphere.datasource.master.url}")
//    @Value("${spring.datasource.master.url}")
//    private String jdbcUrl;
//
////    @Value("${spring.shardingsphere.datasource.master.username}")
//    @Value("${spring.datasource.master.username}")
//    private String userName;
//
////    @Value("${spring.shardingsphere.datasource.master.password}")
//    @Value("${spring.datasource.master.password}")
//    private String password;

    public Connection getMySqlConnection()  {
//        Class.forName(driver);
//        return DriverManager.getConnection(jdbcUrl, userName, password);
        return null;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
//        if (event instanceof ApplicationStartedEvent) {
//            try {
//                Connection conn = getMySqlConnection();
//                ScriptRunner runner = new ScriptRunner(conn);
//                Resources.setCharset(Charset.forName("UTF-8")); // 设置字符集,不然中文乱码插入错误
//                runner.setLogWriter(null);// 设置是否输出日志
//                // 从class目录下直接读取
//                Reader read = Resources.getResourceAsReader("schema.sql");
//                runner.runScript(read);
//                runner.closeConnection();
//                conn.close();
//                log.info("sql脚本执行完毕");
//            } catch (Exception e) {
//                log.info("sql脚本执行发生异常");
//            }
//        }
    }

    @Scheduled(cron = "0 50 23 */1 * ?") // 每晚11点50分执行(系统启动也会执行一次)
    // @Scheduled(cron = "0 * * * * ?")
    public void createOrDropTable() {
        try {
            Connection conn = getMySqlConnection();
            ScriptRunner runner = new ScriptRunner(conn);
            Resources.setCharset(Charset.forName("UTF-8")); // 设置字符集,不然中文乱码插入错误
            runner.setLogWriter(null);// 设置是否输出日志
            // 从class目录下直接读取
            Reader read = Resources.getResourceAsReader("schema.sql");
            runner.runScript(read);
            runner.closeConnection();
            conn.close();
            log.info("sql脚本执行完毕");
        } catch (Exception e) {
            log.info("sql脚本执行发生异常");
        }
    }
    @Scheduled(cron = "2 00 05 */1 * ?")
    public void loadCDSSRedis() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        // 设置日期为当前时间
        calendar.setTime(new Date());
        // 将日期减去一天
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        // 获取前一天的日期
        Date previousDay = calendar.getTime();
        String time=DateUtils.dateTime(previousDay);
        //获取该天抽采数据中的所有日累计测点安装位置
        List<String> locals=this.itbkSensorDataService.getDayLocal(DateUtils.strToDate(time+" 00:00:00"), DateUtils.strToDate(time+" 23:59:59"));
        if(locals!=null&&locals.size()>0){
            for(String s:locals){
                TBkSensorData tBkSensorData=tbkSensorDataController.getLastDataSomeDay(time,s,"瓦斯日累计纯量").get(0);
                TDrainagedataRecord tr=new TDrainagedataRecord();
                tr.setMeasurePointLocal(s);
                tr.setMeasurePointCode(tBkSensorData.getMeasurePointCode());
                tr.setDataTime(time);
                tr.setDataType(tBkSensorData.getSensorTypeName());
                tr.setValue(tBkSensorData.getMeasurePointValue());
                List<TDrainagedataRecord> list=itDrainagedataRecordService.selectTDrainagedataRecordList(tr);
                if(list.size()==0) {
                    itDrainagedataRecordService.insertTDrainagedataRecord(tr);
                }
            }
        }
        List<String> locals1=this.itbkSensorDataService.getMonthLocal(DateUtils.strToDate(time+" 00:00:00"), DateUtils.strToDate(time+" 23:59:59"));
        if(locals1!=null&&locals1.size()>0){
            for(String s:locals1){
                TBkSensorData tBkSensorData=tbkSensorDataController.getLastDataSomeDay(time,s,"瓦斯月累计纯量").get(0);
                TDrainagedataRecord tr=new TDrainagedataRecord();
                tr.setMeasurePointLocal(s);
                tr.setMeasurePointCode(tBkSensorData.getMeasurePointCode());
                tr.setDataTime(time);
                tr.setDataType(tBkSensorData.getSensorTypeName());
                tr.setValue(tBkSensorData.getMeasurePointValue());
                List<TDrainagedataRecord> list=itDrainagedataRecordService.selectTDrainagedataRecordList(tr);
                if(list.size()==0) {
                    itDrainagedataRecordService.insertTDrainagedataRecord(tr);
                }
            }
        }

        List<String> locals2=this.itbkSensorDataService.getDrainageLocal(DateUtils.strToDate(time+" 00:00:00"), DateUtils.strToDate(time+" 23:59:59"));
        if(locals2!=null&&locals2.size()>0){
            for(String s:locals2){
                TBkSensorData tBkSensorData=tbkSensorDataController.getLastDataSomeDay(time,s,"瓦斯抽放量").get(0);
                TDrainagedataRecord tr=new TDrainagedataRecord();
                tr.setMeasurePointLocal(s);
                tr.setMeasurePointCode(tBkSensorData.getMeasurePointCode());
                tr.setDataTime(time);
                tr.setDataType(tBkSensorData.getSensorTypeName());
                tr.setValue(tBkSensorData.getMeasurePointValue());
                List<TDrainagedataRecord> list=itDrainagedataRecordService.selectTDrainagedataRecordList(tr);
                if(list.size()==0) {
                    itDrainagedataRecordService.insertTDrainagedataRecord(tr);
                }
            }
        }
        this.itbkSensorDataService.fileStationDataSum(DateUtils.getDate());
    }

//    /**
//     * 获取该月的最后一天
//     * @param time
//     * @return
//     * @throws ParseException
//     */
//    public String getLastDay(Date time){
//        // 将 Date 转换为 LocalDate
//        LocalDate localDate = time.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        // 获取 YearMonth 对象
//        YearMonth yearMonth = YearMonth.from(localDate);
//        // 获取该月份的最后一天
//        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
//        // 格式化为 yyyy-MM-dd
//        return lastDayOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//    }

}
