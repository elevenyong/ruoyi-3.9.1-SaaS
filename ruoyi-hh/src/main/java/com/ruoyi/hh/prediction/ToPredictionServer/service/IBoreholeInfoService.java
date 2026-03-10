package com.ruoyi.hh.prediction.ToPredictionServer.service;

import java.util.List;
import java.util.Map;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.service
 * @Project：WSCCManageBackend
 * @name：IBoreholeInfoService
 * @Date：2026/1/20 16:02
 * @Filename：IBoreholeInfoService
 * @Description
 */
public interface IBoreholeInfoService {
    /**
     * 查询指定类别的钻孔数据
     * @param categoryName 钻孔类别名称
     * @return 钻孔数据列表
     */
    List<Map<String, Object>> selectVerificationBoreholes(String categoryName);

    /**
     * 根据日期和采面ID统计记录数
     */
    int countByDateAndCoalMiningId(String measurementDate, Integer coalMiningId);
}
