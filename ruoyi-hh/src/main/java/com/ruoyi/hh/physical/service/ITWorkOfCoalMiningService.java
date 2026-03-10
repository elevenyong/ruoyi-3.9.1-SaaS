package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TWorkOfCoalMining;

import java.util.List;

public interface ITWorkOfCoalMiningService {
    /**
     * 查询回采工作
     *
     * @param id 回采工作主键
     * @return 回采工作
     */
    public TWorkOfCoalMining selectTWorkOfCoalMiningById(Integer id);

    /**
     * 查询回采工作列表
     *
     * @param tWorkOfCoalMining 回采工作
     * @return 回采工作集合
     */
    public List<TWorkOfCoalMining> selectTWorkOfCoalMiningList(TWorkOfCoalMining tWorkOfCoalMining);

    /**
     * 新增回采工作
     *
     * @param tWorkOfCoalMining 回采工作
     * @return 结果
     */
    public TWorkOfCoalMining insertTWorkOfCoalMining(TWorkOfCoalMining tWorkOfCoalMining);

    /**
     * 修改回采工作
     *
     * @param tWorkOfCoalMining 回采工作
     * @return 结果
     */
    public TWorkOfCoalMining updateTWorkOfCoalMining(TWorkOfCoalMining tWorkOfCoalMining);

    /**
     * 批量删除回采工作
     *
     * @param ids 需要删除的回采工作主键集合
     * @return 结果
     */
    public int deleteTWorkOfCoalMiningByIds(Integer[] ids);

    /**
     * 删除回采工作信息
     *
     * @param id 回采工作主键
     * @return 结果
     */
    public int deleteTWorkOfCoalMiningById(Integer id);
}
