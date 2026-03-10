package com.ruoyi.hh.prediction.ToPredictionServer.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.util
 * @Project：WSCCManageBackend
 * @name：DataPositionParser
 * @Date：2026/1/20 16:00
 * @Filename：DataPositionParser
 * @Description 解析data_position字段的工具类
 */
public class DataPositionParser {

    private static final Logger log = LoggerFactory.getLogger(DataPositionParser.class);

    public static Double[] parseDataPosition(String dataPosition) {
        try {
            if (dataPosition == null || dataPosition.trim().isEmpty()) {
                return new Double[]{0.0, 0.0, 0.0};
            }

            JSONObject json = JSON.parseObject(dataPosition);
            Double x = json.getDouble("x");
            Double y = json.getDouble("y");
            Double z = json.getDouble("z");

            return new Double[]{
                    x != null ? x : 0.0,
                    y != null ? y : 0.0,
                    z != null ? z : 0.0
            };
        } catch (Exception e) {
            log.error("解析data_position失败: {}", dataPosition, e);
            return new Double[]{0.0, 0.0, 0.0};
        }
    }
}