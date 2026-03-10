package com.ruoyi.hh.ai;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class DeepSeekQuestion {

    private String role;
    private String content;

    public DeepSeekQuestion(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
