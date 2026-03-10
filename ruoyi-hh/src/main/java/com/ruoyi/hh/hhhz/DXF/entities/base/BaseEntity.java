package com.ruoyi.hh.hhhz.DXF.entities.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseEntity {
    @JsonProperty(value = "color")
    private int color = 0;
    @JsonProperty(value = "layer")
    private String layer = "0";
    @JsonIgnore
    private String handle = "0000";


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        if (layer != null && layer.length() >= 1) {
            this.layer = layer;
        }
    }

    public String getHandle() {
        return handle;
    }

    public String setHandle(String handle) {
        this.handle = handle;
        return handle;
    }

    public String toDXF() {
        return "";
    }
}
