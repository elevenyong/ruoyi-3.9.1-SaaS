package com.ruoyi.hh.ai;

import groovy.util.logging.Slf4j;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Nullable;

import java.io.IOException;

import static com.ruoyi.framework.datasource.DynamicDataSourceContextHolder.log;

@Slf4j
public class SSEListener extends EventSourceListener {

    private SseEmitter sseEmitter;

    public SSEListener(SseEmitter sseEmitter) {
        this.sseEmitter = sseEmitter;
    }

    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        sseEmitter.complete();
    }

    @Override
    public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        log.info("sse send data ={}", data);
        try {
            sseEmitter.send(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        log.info("sse failure");
        sseEmitter.complete();
    }

    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        log.info("sse open");
    }
}
