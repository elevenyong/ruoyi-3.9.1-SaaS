package com.ruoyi.hh.prediction.ToPredictionServer.service.impl;

import com.ruoyi.hh.prediction.ToPredictionServer.mapper.WorkOfCoalMiningMapper;
import com.ruoyi.hh.prediction.ToPredictionServer.service.IWorkOfCoalMiningService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.service.impl
 * @Project：WSCCManageBackend
 * @name：WorkOfCoalMiningServiceImpl
 * @Date：2026/1/21 14:28
 * @Filename：WorkOfCoalMiningServiceImpl
 * @Description
 */
@Service
public class WorkOfCoalMiningServiceImpl implements IWorkOfCoalMiningService {

    @Resource
    private WorkOfCoalMiningMapper workOfCoalMiningMapper;

    @Override
    public Map<String, Object> getWorkOfCoalMiningById(Integer id) {
        return workOfCoalMiningMapper.selectById(id);
    }

    @Override
    public Map<Integer, Map<String, Object>> getWorkOfCoalMiningByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashMap<>();
        }

        List<Map<String, Object>> workOfCoalMiningList = workOfCoalMiningMapper.selectByIds(ids);

        return workOfCoalMiningList.stream()
                .collect(Collectors.toMap(
                        map -> (Integer) map.get("id"),
                        map -> map
                ));
    }

    @Override
    public Double getTotalAvgAdvanceByCoalMiningId(Integer coalMiningId) {
        Map<String, Object> workOfCoalMining = getWorkOfCoalMiningById(coalMiningId);
        if (workOfCoalMining != null && workOfCoalMining.containsKey("total_avg_advance")) {
            Object totalAvgAdvanceObj = workOfCoalMining.get("total_avg_advance");
            if (totalAvgAdvanceObj instanceof Number) {
                return ((Number) totalAvgAdvanceObj).doubleValue();
            } else if (totalAvgAdvanceObj instanceof String) {
                try {
                    return Double.parseDouble((String) totalAvgAdvanceObj);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public Integer getWorkfaceIdByCoalMiningId(Integer coalMiningId) {
        Map<String, Object> workOfCoalMining = getWorkOfCoalMiningById(coalMiningId);
        if (workOfCoalMining != null && workOfCoalMining.containsKey("workface_id")) {
            Object workfaceIdObj = workOfCoalMining.get("workface_id");
            if (workfaceIdObj instanceof Integer) {
                return (Integer) workfaceIdObj;
            } else if (workfaceIdObj instanceof Number) {
                return ((Number) workfaceIdObj).intValue();
            } else if (workfaceIdObj instanceof String) {
                try {
                    return Integer.parseInt((String) workfaceIdObj);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }
}