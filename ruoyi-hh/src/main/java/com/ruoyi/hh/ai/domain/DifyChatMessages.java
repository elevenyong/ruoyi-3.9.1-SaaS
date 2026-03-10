package com.ruoyi.hh.ai.domain;

import lombok.Data;

@Deprecated
@Data
public class DifyChatMessages {
    private String auto_generate_name;
    private Object inputs;
    private String query;
    private String response_mode;
    private String user;
}
