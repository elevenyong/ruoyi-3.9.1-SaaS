package com.ruoyi.hh.util.data;

import com.ruoyi.hh.hhhz.domain.param.TParamBandDrilling;
import com.ruoyi.hh.hhhz.domain.param.TParamDirectionalDrilling;
import com.ruoyi.hh.hhhz.domain.param.TParamHighLevelDrilling;
import com.ruoyi.hh.hhhz.domain.param.TParamLayerDrilling;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class RequestData {
    private List<TParamHighLevelDrilling> highLevelDrillings;
    private List<TParamLayerDrilling> layerDrilling;
    private List<TParamBandDrilling> band;
    private List<TParamDirectionalDrilling> directional;


    public List<TParamHighLevelDrilling> getHighLevelDrillings() {
        return highLevelDrillings;
    }

    public void setHighLevelDrillings(List<TParamHighLevelDrilling> highLevelDrillings) {
        this.highLevelDrillings = highLevelDrillings;
    }

    public List<TParamLayerDrilling> getLayerDrilling() {
        return layerDrilling;
    }

    public void setLayerDrilling(List<TParamLayerDrilling> layerDrilling) {
        this.layerDrilling = layerDrilling;
    }

    public List<TParamBandDrilling> getBand() {
        return band;
    }

    public void setBand(List<TParamBandDrilling> band) {
        this.band = band;
    }

    public List<TParamDirectionalDrilling> getDirectional() {
        return directional;
    }

    public void setDirectional(List<TParamDirectionalDrilling> directional) {
        this.directional = directional;
    }
}
