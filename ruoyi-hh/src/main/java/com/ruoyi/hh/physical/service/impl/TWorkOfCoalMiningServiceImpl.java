package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TWorkOfCoalMining;
import com.ruoyi.hh.physical.mapper.TWorkOfCoalMiningMapper;
import com.ruoyi.hh.physical.mapper.TWorkingFaceOfDrivingMapper;
import com.ruoyi.hh.physical.service.ITWorkOfCoalMiningService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TWorkOfCoalMiningServiceImpl implements ITWorkOfCoalMiningService {


    @Resource
    private TWorkOfCoalMiningMapper tWorkOfCoalMiningMapper;

    /**
     * 查询掘进工作
     *
     * @param id 掘进工作主键
     * @return 掘进工作
     */
    @Override
    public TWorkOfCoalMining selectTWorkOfCoalMiningById(Integer id) {
        TWorkOfCoalMining face = tWorkOfCoalMiningMapper.selectTWorkOfCoalMiningById(id);
        return face;
    }

    /**
     * 查询掘进工作列表
     *
     * @param tWorkOfCoalMining 掘进工作
     * @return 掘进工作
     */
    @Override
    public List<TWorkOfCoalMining> selectTWorkOfCoalMiningList(TWorkOfCoalMining tWorkOfCoalMining) {
        return tWorkOfCoalMiningMapper.selectTWorkOfCoalMiningList(tWorkOfCoalMining);
    }

    /**
     * 新增掘进工作
     *
     * @param tWorkOfCoalMining 掘进工作
     * @return 结果
     */
    @Override
    public TWorkOfCoalMining insertTWorkOfCoalMining(TWorkOfCoalMining tWorkOfCoalMining) {
        int i = tWorkOfCoalMiningMapper.insertTWorkOfCoalMining(tWorkOfCoalMining);
        if (i > 0) {
            return tWorkOfCoalMining;
        } else return null;
    }

    /**
     * 修改掘进工作
     *
     * @param tWorkOfCoalMining 掘进工作
     * @return 结果
     */
    @Override
    public TWorkOfCoalMining updateTWorkOfCoalMining(TWorkOfCoalMining tWorkOfCoalMining) {
        int i = tWorkOfCoalMiningMapper.updateTWorkOfCoalMining(tWorkOfCoalMining);
        if (i > 0) {
            return tWorkOfCoalMining;
        } else return null;
    }

    /**
     * 批量删除掘进工作
     *
     * @param ids 需要删除的掘进工作主键
     * @return 结果
     */
    @Override
    public int deleteTWorkOfCoalMiningByIds(Integer[] ids) {
        return tWorkOfCoalMiningMapper.deleteTWorkOfCoalMiningByIds(ids);
    }

    /**
     * 删除掘进工作信息
     *
     * @param id 掘进工作主键
     * @return 结果
     */
    @Override
    public int deleteTWorkOfCoalMiningById(Integer id) {
        return tWorkOfCoalMiningMapper.deleteTWorkOfCoalMiningById(id);
    }
}
