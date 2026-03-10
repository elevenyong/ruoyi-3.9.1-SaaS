package com.ruoyi.hh.ai.function;

import com.ruoyi.hh.ai.SpringContextHolder;
import com.ruoyi.hh.physical.domain.TRoadway;
import com.ruoyi.hh.physical.domain.TWorkingFaceOfCoalMining;
import com.ruoyi.hh.physical.service.ITRoadwayService;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfCoalMiningService;
import io.github.lnyocly.ai4j.annotation.FunctionCall;
import io.github.lnyocly.ai4j.annotation.FunctionParameter;
import io.github.lnyocly.ai4j.annotation.FunctionRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


/**
 * FunctionCall方法，For AI4J框架。（若使用Dify，可废弃）
 */
@Deprecated
@Component
@FunctionCall(name = "queryRoadwayList", description = "查询工作面内关联的巷道")
public class QueryRoadwayListFunction implements Function<QueryRoadwayListFunction.Request, String> {

    @Data
    @FunctionRequest
    public static class Request {
        @FunctionParameter(description = "需要查询的工作面名称")
        private String workfaceName;
        @FunctionParameter(description = "需要查询的工作面ID")
        private Integer worfaceId;
    }

    /*public enum Type {
        daily,
        hourly,
        now
    }*/

    @Override
    public String apply(Request request) {
        Integer wfId = null;
        TRoadway queryRoadway = new TRoadway();
        ArrayList<String> roadwayNameList = new ArrayList<>();
        if (request.worfaceId==null){
            ITWorkingFaceOfCoalMiningService workfaceService = SpringContextHolder.getBean(ITWorkingFaceOfCoalMiningService.class);
            List<TWorkingFaceOfCoalMining> wfList = workfaceService.selectTWorkingFaceOfCoalMiningList(new TWorkingFaceOfCoalMining());
            for (TWorkingFaceOfCoalMining face : wfList) {
                if (Objects.equals(face.getWorkfaceName(), request.workfaceName)){
                    queryRoadway.setWorkfaceId(face.getId());
                    break;
                }
            }
        }else {
            queryRoadway.setWorkfaceId(request.worfaceId);
        }
        ITRoadwayService roadwayService = SpringContextHolder.getBean(ITRoadwayService.class);
        List<TRoadway> tRoadways = roadwayService.selectTRoadwayList(queryRoadway);
        for (TRoadway tRoadway : tRoadways) {
            roadwayNameList.add(tRoadway.getRoadway());
        }
        return roadwayNameList.toString();

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
