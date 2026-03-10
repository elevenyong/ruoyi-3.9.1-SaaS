package com.ruoyi.hh.hhhz.controller.drilling;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.controller.TBKSensorDataController;
import com.ruoyi.hh.hhhz.domain.NormalDrillingData;
import com.ruoyi.hh.hhhz.domain.param.*;
import com.ruoyi.hh.hhhz.tools.drilling.*;
import com.ruoyi.hh.physical.service.ITStationInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/drilling")
public class DrillingController {



    @Resource
    private TBKSensorDataController tbkSensorDataController;
    @Resource
    private RedisCache redisCache;
    @Resource
    private ITStationInfoService itStationInfoService;

    @Resource
    SingleDrilling singleDrilling;

    @Resource
    BandGovernanceDrilling bandGovernanceDrilling;

    @Resource
    ReMiningAreaDrilling reMiningAreaDrilling;

    @Resource
    BeddingDrilling beddingDrilling;

    @Resource
    DirectionalDrilling directionalDrilling;

    @Resource
    CoalUncoveringDrilling uncoveringDrilling;

    /**
     * 常规单组穿层钻孔
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/single")
    public AjaxResult genSingleDrilling(@RequestBody BandGovernanceDrillingParam param) {
        System.out.println(param);
        return AjaxResult.success(singleDrilling.calDrilling(param));
    }

    /**
     * 常规条带穿层钻孔
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/band")
    public AjaxResult genBandGovernanceDrilling(@RequestBody BandGovernanceDrillingParam param) {
        System.out.println(param);
        return AjaxResult.success(bandGovernanceDrilling.calDrilling(param));
    }

    /**
     * 回采区条带穿层钻孔
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/reMiningArea")
    public AjaxResult reMiningAreaDrilling(@RequestBody ReMiningAreaDrillingParam param) {
        System.out.println(param);
        return AjaxResult.success(reMiningAreaDrilling.calDrilling(param));
    }


    @PostMapping("/band/export")
    public void export(HttpServletResponse response, List<NormalDrillingData> list) {
        ExcelUtil<NormalDrillingData> util = new ExcelUtil<NormalDrillingData>(NormalDrillingData.class);
        util.exportExcel(response, list, "条带钻孔设计数据");
    }

    /**
     * 顺层钻孔
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/bedding")
    public AjaxResult genBeddingDrilling(@RequestBody BeddingDrillingParam param) {
        System.out.println(param);
        return AjaxResult.success(beddingDrilling.calDrilling(param));
    }

    /**
     * 定向钻孔
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/directional")
    public AjaxResult genDirectionalDrilling(@RequestBody DirectionalDrillingParam param) {
        DirectionalDrillingParam param1 = directionalDrilling.calDrilling(param);
        if (param1 != null) {
            return AjaxResult.success(param1);
        } else return AjaxResult.error("无法满足施工条件");
    }

    /**
     * 揭煤钻孔
     *
     * @param param
     * @return
     */
    @PostMapping(value = "/uncovering")
    public AjaxResult genCoalUncoveringDrilling(@RequestBody CoalUncoveringDrillingParam param) {
        CoalUncoveringDrillingParam param1 = uncoveringDrilling.calDrilling(param);
        if (param1 != null) {
            return AjaxResult.success(param1);
        } else return AjaxResult.error("无法满足施工条件");
    }



}
