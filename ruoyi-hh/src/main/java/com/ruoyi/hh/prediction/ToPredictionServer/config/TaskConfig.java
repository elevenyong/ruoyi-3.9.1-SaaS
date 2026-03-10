package com.ruoyi.hh.prediction.ToPredictionServer.config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.config
 * @Project：WSCCManageBackend
 * @name：TaskConfig
 * @Date：2026/1/20 15:55
 * @Filename：TaskConfig
 * @Description
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "borehole.task")
public class TaskConfig {


    // 新增：钻孔类别名称配置，用于查询
    private String boreholeCategoryName = "采面验证孔";

    // 新增：调试模式开关
    private boolean debugMode = false;
    // 新增：日期格式配置
    private String dateFormat = "yyyy-MM-dd";
    private String datetimeFormat = "yyyy-MM-dd HH:mm:ss";
    /**
     * 定时任务cron表达式
     */
    private String scheduleCron = "0 0 0 */4 * ?";

    /**
     * 数据天数阈值（天）
     */
    private int dataDaysThreshold = 2;

    /**
     * 每天数据量阈值（条）
     */
    private int dailyDataThreshold = 20;

    /**
     * 模型训练接口地址
     */
    private String trainApiUrl = "http://localhost:5000/api/model/train";
}