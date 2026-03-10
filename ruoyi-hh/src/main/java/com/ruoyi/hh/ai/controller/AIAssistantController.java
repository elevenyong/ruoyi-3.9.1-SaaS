package com.ruoyi.hh.ai.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.framework.web.service.UserDetailsServiceImpl;
import com.ruoyi.hh.ai.SseEmitterUTF8;
import com.ruoyi.hh.ai.domain.DifyChatMessages;
import io.github.lnyocly.ai4j.listener.SseListener;
import io.github.lnyocly.ai4j.platform.openai.chat.entity.ChatCompletion;
import io.github.lnyocly.ai4j.platform.openai.chat.entity.ChatMessage;
import io.github.lnyocly.ai4j.platform.openai.chat.enums.ChatMessageType;
import io.github.lnyocly.ai4j.service.IChatService;
import io.github.lnyocly.ai4j.service.PlatformType;
import io.github.lnyocly.ai4j.service.factor.AiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;


/**
 * 尝试使用AI4J的框架接入接入DeepSeek接口（若使用Dify框架，可废弃）
 */
@Deprecated
@RestController
@RequestMapping("/ai")
public class AIAssistantController {
    private static final Logger log = LoggerFactory.getLogger(AIAssistantController.class);

    @Resource
    private AiService aiService;


    public String[] funcList = {"queryWorkfaceList", "queryRoadwayList", "queryWeather"};

    public HashMap<String, List<ChatMessage>> historyMap = new HashMap<>();

    public String systemMessage = "你是一个专业的煤矿瓦斯地质AI助手，你的使命是为矿井的安全管理人员提供专业帮助。" +
            "用户在进行问题询问时如果是工作面相关的内容需要用户明确具体哪个工作面，如果是钻孔相关的内容需要明确具体哪条巷道。" +
            "如果用户不清楚有哪些工作面，请向用户展示系统内存在的工作面列表，以便用户参考。";

    @GetMapping("/chat")
    public SseEmitter generate(@RequestParam("question") String question, @RequestParam("user") String user) throws Exception {

        List<ChatMessage> history = historyMap.get(user);
        if (history == null) {
            history = new ArrayList<>();
            historyMap.put(user, history);
        }
        SseEmitter emitter = new SseEmitterUTF8(120000L);
        // 获取chat服务实例
        IChatService chatService = aiService.getChatService(PlatformType.DEEPSEEK);
        ChatCompletion chatCompletion;
        if (history.isEmpty()) {
            history.add(new ChatMessage(ChatMessageType.SYSTEM, systemMessage));
            // 构造请求参数
        }
        history.add(new ChatMessage(ChatMessageType.USER, question));
        chatCompletion = ChatCompletion.builder()
                .model("deepseek-chat")
                .messages(history)
                .functions(funcList)
                .build();

        StringBuilder currentAns = new StringBuilder();
        List<ChatMessage> finalHistory = history;
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                SseListener sseListener = new SseListener() {
                    @Override
                    protected void send() {
                        try {
                            emitter.send(this.getCurrStr());
                            System.out.print(this.getCurrStr());  // 打印当前发送的内容
                            currentAns.append(getCurrStr());
                        } catch (IOException e) {
                            emitter.completeWithError(e);
                        }
                    }
                };

                // 发送流式数据
                chatService.chatCompletionStream(chatCompletion, sseListener);

                // 保存历史数据
                emitter.onCompletion(() -> finalHistory.add(new ChatMessage(ChatMessageType.ASSISTANT, currentAns.toString())));

                // 完成后关闭连接
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }


    @GetMapping("/conversations")
    public JSONObject conversations(@RequestParam Integer limit, @RequestParam String sort_by, @RequestParam String user) {
        return JSONObject.parse("{\n" +
                "  \"limit\": 123,\n" +
                "  \"has_more\": true,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "      \"name\": \"<string>\",\n" +
                "      \"inputs\": {},\n" +
                "      \"status\": \"<string>\",\n" +
                "      \"introduction\": \"<string>\",\n" +
                "      \"created_at\": 123,\n" +
                "      \"updated_at\": 123\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }

    @PostMapping("/conversations")
    public SseEmitter createConversations(@RequestParam Integer limit, @RequestParam String sort_by, @RequestParam String user) {
        return null;
    }

    @PostMapping("/chat-messages")
    public SseEmitter chatMessages(@RequestBody DifyChatMessages messages) {
        List<ChatMessage> history = historyMap.get(messages.getUser());
        if (history == null) {
            history = new ArrayList<>();
            historyMap.put(messages.getUser(), history);
        }
        SseEmitter emitter = new SseEmitterUTF8(120000L);
        // 获取chat服务实例
        IChatService chatService = aiService.getChatService(PlatformType.DEEPSEEK);
        ChatCompletion chatCompletion;
        if (history.isEmpty()) {
            history.add(new ChatMessage(ChatMessageType.SYSTEM, systemMessage));
            // 构造请求参数
        }
        history.add(new ChatMessage(ChatMessageType.USER, messages.getQuery()));
        chatCompletion = ChatCompletion.builder()
                .model("deepseek-chat")
                .messages(history)
                .functions(funcList)
                .build();

        StringBuilder currentAns = new StringBuilder();
        List<ChatMessage> finalHistory = history;
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                SseListener sseListener = new SseListener() {
                    @Override
                    protected void send() {
                        try {
                            emitter.send(this.getCurrData());
                            // 打印相应的内容
                            System.out.print(this.getCurrStr());
                            // 记录历史数据
                            currentAns.append(getCurrStr());
                        } catch (IOException e) {
                            emitter.completeWithError(e);
                        }
                    }
                };

                // 发送流式数据
                chatService.chatCompletionStream(chatCompletion, sseListener);

                // 保存历史数据
                emitter.onCompletion(() -> finalHistory.add(new ChatMessage(ChatMessageType.ASSISTANT, currentAns.toString())));

                // 完成后关闭连接
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    @GetMapping("/messages")
    public JSONObject messages(@RequestParam Integer limit, @RequestParam String conversation_id, @RequestParam String user) {
        return JSONObject.parse("{\n" +
                "  \"limit\": 123,\n" +
                "  \"has_more\": true,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "      \"conversation_id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "      \"inputs\": {},\n" +
                "      \"query\": \"<string>\",\n" +
                "      \"answer\": \"<string>\",\n" +
                "      \"message_files\": [\n" +
                "        {\n" +
                "          \"id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "          \"type\": \"<string>\",\n" +
                "          \"url\": \"<string>\",\n" +
                "          \"belongs_to\": \"user\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"feedback\": {\n" +
                "        \"rating\": \"like\"\n" +
                "      },\n" +
                "      \"retriever_resources\": [\n" +
                "        {\n" +
                "          \"position\": 123,\n" +
                "          \"dataset_id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "          \"dataset_name\": \"<string>\",\n" +
                "          \"document_id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "          \"document_name\": \"<string>\",\n" +
                "          \"segment_id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "          \"score\": 123,\n" +
                "          \"content\": \"<string>\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"created_at\": 123\n" +
                "    }\n" +
                "  ]\n" +
                "}");
    }
}
