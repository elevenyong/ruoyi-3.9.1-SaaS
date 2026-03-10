package com.ruoyi.hh.prediction.ToPredictionServer.service;

import java.util.List;
import java.util.Map;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.service
 * @Project：WSCCManageBackend
 * @name：IWorkOfCoalMiningService
 * @Date：2026/1/21 14:28
 * @Filename：IWorkOfCoalMiningService
 * @Description
 */
public interface IWorkOfCoalMiningService {

    /**
     * 根据采面ID查询采面信息
     */
    Map<String, Object> getWorkOfCoalMiningById(Integer id);

    /**
     * 根据采面ID列表批量查询采面信息
     */
    Map<Integer, Map<String, Object>> getWorkOfCoalMiningByIds(List<Integer> ids);

    /**
     * 根据采面ID获取总平均进尺
     */
    Double getTotalAvgAdvanceByCoalMiningId(Integer coalMiningId);
    /**
     * 根据采面ID获取工作面ID
     */
    Integer getWorkfaceIdByCoalMiningId(Integer coalMiningId);

}