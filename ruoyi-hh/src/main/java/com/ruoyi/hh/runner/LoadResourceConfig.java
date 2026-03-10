package com.ruoyi.hh.runner;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.hh.physical.domain.TSensorType;
import com.ruoyi.hh.physical.service.ITSensorTypeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 从数据库加载测点类型
 */
@Order(1)
@Component
public class LoadResourceConfig implements CommandLineRunner {

    @Resource
    private RedisCache redisCache;
    @Resource
    private ITSensorTypeService tSensorTypeService;

    @Override
    public void run(String... args) throws Exception {
        //加载传感器编码到内存中
        if(redisCache.getCacheMap("sensorTypeCode")!=null){
            redisCache.deleteObject("sensorTypeCode");
            List<TSensorType> list=this.tSensorTypeService.selectTSensorTypeList(new TSensorType());
            Map<String,String> m=new HashMap<>();
            for(TSensorType tt:list){
                m.put(tt.getCode(),tt.getDescribe());
            }
            redisCache.setCacheMap("sensorTypeCode",m);
        }else{
            List<TSensorType> list=this.tSensorTypeService.selectTSensorTypeList(new TSensorType());
            Map<String,String> m=new HashMap<>();
            for(TSensorType tt:list){
                m.put(tt.getCode(),tt.getDescribe());
            }
            redisCache.setCacheMap("sensorTypeCode",m);
        }
        //检查抽采记录表（t_drainagedata_record）中的数据和历史数据(t_bk_sensor_data)是否一致

    }
}



