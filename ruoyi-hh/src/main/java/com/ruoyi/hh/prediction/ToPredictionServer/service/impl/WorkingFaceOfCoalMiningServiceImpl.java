package com.ruoyi.hh.prediction.ToPredictionServer.service.impl;

import com.ruoyi.hh.prediction.ToPredictionServer.mapper.WorkingFaceOfCoalMiningMapper;
import com.ruoyi.hh.prediction.ToPredictionServer.service.IWorkingFaceOfCoalMiningService;
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
 * @name：WorkingFaceOfCoalMiningServiceImpl
 * @Date：2026/1/21 15:03
 * @Filename：WorkingFaceOfCoalMiningServiceImpl
 * @Description
 */
@Service
public class WorkingFaceOfCoalMiningServiceImpl implements IWorkingFaceOfCoalMiningService {

    @Resource
    private WorkingFaceOfCoalMiningMapper workingFaceOfCoalMiningMapper;

    @Override
    public Map<String, Object> getWorkingFaceById(Integer id) {
        return workingFaceOfCoalMiningMapper.selectById(id);
    }

    @Override
    public Map<Integer, Map<String, Object>> getWorkingFaceByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashMap<>();
        }

        List<Map<String, Object>> workingFaceList = workingFaceOfCoalMiningMapper.selectByIds(ids);

        return workingFaceList.stream()
                .collect(Collectors.toMap(
                        map -> (Integer) map.get("id"),
                        map -> map
                ));
    }

    @Override
    public String getWorkfaceNameById(Integer workfaceId) {
        if (workfaceId == null) {
            return null;
        }

        Map<String, Object> workingFace = getWorkingFaceById(workfaceId);
        if (workingFace != null && workingFace.containsKey("workface_name")) {
            Object workfaceNameObj = workingFace.get("workface_name");
            return workfaceNameObj != null ? workfaceNameObj.toString() : null;
        }
        return null;
    }
}