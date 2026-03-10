package com.ruoyi.hh.prediction.ToPredictionServer.service;

import java.util.List;
import java.util.Map;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.service
 * @Project：WSCCManageBackend
 * @name：IWorkingFaceOfCoalMiningService
 * @Date：2026/1/21 15:02
 * @Filename：IWorkingFaceOfCoalMiningService
 * @Description
 */
public interface IWorkingFaceOfCoalMiningService {

    /**
     * 根据工作面ID查询工作面信息
     */
    Map<String, Object> getWorkingFaceById(Integer id);

    /**
     * 根据工作面ID列表批量查询工作面信息
     */
    Map<Integer, Map<String, Object>> getWorkingFaceByIds(List<Integer> ids);

    /**
     * 根据工作面ID获取工作面名称
     */
    String getWorkfaceNameById(Integer workfaceId);
}