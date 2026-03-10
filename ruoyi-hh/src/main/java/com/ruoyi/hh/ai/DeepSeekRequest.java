package com.ruoyi.hh.ai;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DeepSeekRequest {
    private String model;
    private Boolean stream;
    private ArrayList<DeepSeekQuestion> messages;
}
