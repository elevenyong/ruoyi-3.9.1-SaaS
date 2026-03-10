package com.ruoyi.common.core.domain;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class VersionLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer versionCode;
    private String versionName;
    private String versionDesc;
}
