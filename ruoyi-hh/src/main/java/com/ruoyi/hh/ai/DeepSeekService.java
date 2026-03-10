package com.ruoyi.hh.ai;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class DeepSeekService {
    private String url = "https://api.deepseek.com/chat/completions";
    private String token = "sk-07504d3a44434c4b8e0a995c9d4de741";

    public JSONObject chat(String sysAsk, String userAsk) {
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(120, TimeUnit.SECONDS).
                readTimeout(120, TimeUnit.SECONDS).
                build();
        MediaType mediaType = MediaType.parse("application/json");
        DeepSeekRequest deepSeekRequest = createRequest(sysAsk, userAsk, false, "deepseek-chat");
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(deepSeekRequest));
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String respBody = response.body().string();
            return JSON.parseObject(respBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public SseEmitter sseChat(String sysAsk, String userAsk) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
        EventSource.Factory factory = EventSources.createFactory(client);

        SseEmitter sseEmitter = new SseEmitter(30000L);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        DeepSeekRequest deepSeekRequest = createRequest(sysAsk, userAsk, true, "deepseek-chat");
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(deepSeekRequest));

        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        // 自定义监听器
        EventSourceListener eventSourceListener = new EventSourceListener() {
            @Override
            public void onOpen(EventSource eventSource, Response response) {
                super.onOpen(eventSource, response);
            }

            @Override
            public void onEvent(EventSource eventSource, @Nullable String id, @Nullable String type, String data) {
                //   接受消息 data
                super.onEvent(eventSource, id, type, data);
                System.out.println("onEvent: " + data);
            }

            @Override
            public void onClosed(EventSource eventSource) {
                super.onClosed(eventSource);
            }

            @Override
            public void onFailure(EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                super.onFailure(eventSource, t, response);
            }
        };


        //factory.newEventSource(request, new SSEListener(sseEmitter));
        factory.newEventSource(request, eventSourceListener);
        return null;
    }

    public static DeepSeekRequest createRequest(String sysQuestion, String userQuestion, Boolean stream, String model) {
        DeepSeekRequest deepSeekRequest = new DeepSeekRequest();
        ArrayList<DeepSeekQuestion> messages = new ArrayList<>();
        messages.add(new DeepSeekQuestion("system", sysQuestion));
        messages.add(new DeepSeekQuestion("user", userQuestion));
        deepSeekRequest.setMessages(messages);
        deepSeekRequest.setStream(stream);
        deepSeekRequest.setModel(model);
        return deepSeekRequest;
    }

}
