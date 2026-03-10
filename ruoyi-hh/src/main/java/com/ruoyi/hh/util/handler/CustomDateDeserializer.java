package com.ruoyi.hh.util.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String raw = p.getText().trim();

        if (raw.length() > 4) {
            // 尝试标准格式（完整）
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(raw);
            } catch (Exception ignored) {
            }

            try {
                // 通用拆分，支持多种分隔符：. / -
                String[] parts = raw.split("[./\\-]");

                if (parts.length < 3 || parts.length > 4) {
                    throw new RuntimeException("非法日期格式，必须是3或4段：年、月、日、[小时]");
                }

                String year = parts[0].length() == 2 ? "20" + parts[0] : parts[0];
                String month = String.format("%02d", Integer.parseInt(parts[1]));
                String day = String.format("%02d", Integer.parseInt(parts[2]));
                String hourCode = (parts.length == 4) ? parts[3] : "0";  // 缺小时默认 0

                // 时间映射表
                // 自定义 hour 映射
                String timeStr = "00:00:00";
                if (hourCode.equals("0")) {
                    timeStr = "00:00:00";
                } else if (hourCode.equals("4")) {
                    timeStr = "16:00:00";
                } else if (hourCode.equals("8")) {
                    timeStr = "08:00:00";
                }

                String finalStr = String.format("%s-%s-%s %s", year, month, day, timeStr);
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(finalStr);

            } catch (Exception e) {
                throw new RuntimeException("无法解析日期格式: " + raw);
            }
        } else {
            // 空字符串，或小于等于4个字符
            return null;
        }
    }
}
