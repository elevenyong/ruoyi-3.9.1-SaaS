package com.ruoyi.system.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.mqtt.MQTTClient;
import com.ruoyi.system.domain.Handle;
import com.ruoyi.system.domain.Model;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.Future;
import org.fusesource.mqtt.client.FutureConnection;
import org.fusesource.mqtt.client.QoS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/model")
public class ModelDataController {

    @Autowired
    private MQTTClient client;

    @Autowired
    private ObjectMapper mapper;

    private static Map<String, Model> models = new HashMap<>();

    {
        models.put("1", new Model(10, 10, 10, "1"));
        models.put("2", new Model(20, 20, 20, "2"));
        models.put("3", new Model(30, 30, 30, "3"));
    }

    @GetMapping("/initData")
    public AjaxResult initData() throws JsonProcessingException {
        String s = mapper.writeValueAsString(models);
        List<Model> modelList = new ArrayList<>(models.values());
        return AjaxResult.success(modelList);
    }

    @PostMapping("/handle")
    public AjaxResult handle(@RequestBody Handle handle) throws Exception {
        //handle.setTimestamp(System.currentTimeMillis());
        switch (handle.getHandle()) {
            case "add":
                String[] xyz = handle.getTo().split(",");
                models.put(handle.getModelId(), new Model(Double.parseDouble(xyz[0]), Double.parseDouble(xyz[1]), Double.parseDouble(xyz[2]), handle.getModelId()));
                break;
            case "delete":
                if (!models.containsKey(handle.getModelId())) {
                    return AjaxResult.error("模型不存在");
                }
                models.remove(handle.getModelId());
                break;
            case "move":
                if (!models.containsKey(handle.getModelId())) {
                    return AjaxResult.error("模型不存在");
                }
                String[] xyz1 = handle.getTo().split(",");
                models.get(handle.getModelId()).setXYZ(Double.parseDouble(xyz1[0]), Double.parseDouble(xyz1[1]), Double.parseDouble(xyz1[2]));
                break;
            default:
                return AjaxResult.error("未知操作");
        }
        String text = mapper.writeValueAsString(handle);
        System.out.println(text);
        FutureConnection connection = client.getConnection();
        if (connection.isConnected()) {
            connection.publish("handle", text.getBytes(), QoS.AT_LEAST_ONCE, false);
            return AjaxResult.success();
        } else {
            return AjaxResult.error("服务器连接失败");
        }
    }
}
