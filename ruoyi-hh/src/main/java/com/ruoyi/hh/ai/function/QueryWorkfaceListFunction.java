package com.ruoyi.hh.ai.function;

import com.ruoyi.hh.ai.SpringContextHolder;
import com.ruoyi.hh.physical.domain.TWorkingFaceOfCoalMining;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfCoalMiningService;
import io.github.lnyocly.ai4j.annotation.FunctionCall;
import io.github.lnyocly.ai4j.annotation.FunctionRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


/**
 * FunctionCall方法，For AI4J框架。（若使用Dify，可废弃）
 */
@Deprecated
@Component
@FunctionCall(name = "queryWorkfaceList", description = "查询系统内存在的工作面")
public class QueryWorkfaceListFunction implements Function<QueryWorkfaceListFunction.Request, String> {

    @Data
    @FunctionRequest
    public static class Request {
    }

    /*public enum Type {
        daily,
        hourly,
        now
    }*/

    @Override
    public String apply(Request request) {
        ITWorkingFaceOfCoalMiningService service = SpringContextHolder.getBean(ITWorkingFaceOfCoalMiningService.class);

        List<TWorkingFaceOfCoalMining> list = service.selectTWorkingFaceOfCoalMiningList(new TWorkingFaceOfCoalMining());
        ArrayList<String> nameList = new ArrayList<>();
        list.forEach(t -> {
            nameList.add("id:" + t.getId() + ";workfaceName:" + t.getWorkfaceName());
        });
        return nameList.toString();

        /*final String key = "SjGm3R8-_aFVRUTFZ";

        String url = String.format("https://api.seniverse.com/v3/weather/%s.json?key=%s&location=%s&days=%d",
                request.type.name(),
                key,
                request.location,
                request.days);


        OkHttpClient client = new OkHttpClient();

        okhttp3.Request http = new okhttp3.Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(http).execute()) {
            if (response.isSuccessful()) {
                // 解析响应体
                return response.body() != null ? response.body().string() : "";
            } else {
                return "获取天气失败 当前天气未知";
            }
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
            return "获取天气失败 当前天气未知";
        }*/
    }

}
