package com.ruoyi.hh.prediction.ToPredictionServer.service.impl;

import com.ruoyi.hh.prediction.ToPredictionServer.mapper.BoreholeInfoMapper;
import com.ruoyi.hh.prediction.ToPredictionServer.service.IBoreholeInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.hh.prediction.ToPredictionServer.service.impl
 * @Project：WSCCManageBackend
 * @name：BoreholeInfoServiceImpl
 * @Date：2026/1/20 16:03
 * @Filename：BoreholeInfoServiceImpl
 * @Description
 */
@Service
public class BoreholeInfoServiceImpl implements IBoreholeInfoService {

    @Resource
    private BoreholeInfoMapper boreholeInfoMapper;

    @Override
    public List<Map<String, Object>> selectVerificationBoreholes(String categoryName) {
        return boreholeInfoMapper.selectVerificationBoreholes(categoryName);
    }

    @Override
    public int countByDateAndCoalMiningId(String measurementDate, Integer coalMiningId) {
        return boreholeInfoMapper.countByDateAndCoalMiningId(measurementDate, coalMiningId);
    }
}