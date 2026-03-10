package com.ruoyi.hh.physical.mapper;

import java.util.List;
import com.ruoyi.hh.physical.domain.TWorkingFaceOfCoalMining;

/**
 * 采煤工作面Mapper接口
 *
 * @author hhhz
 * @date 2023-04-28
 */
public interface TWorkingFaceOfCoalMiningMapper
{
    /**
     * 查询采煤工作面
     *
     * @param id 采煤工作面主键
     * @return 采煤工作面
     */
    public TWorkingFaceOfCoalMining selectTWorkingFaceOfCoalMiningById(Integer id);

    /**
     * 查询采煤工作面列表
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 采煤工作面集合
     */
    public List<TWorkingFaceOfCoalMining> selectTWorkingFaceOfCoalMiningList(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining);

    /**
     * 新增采煤工作面
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 结果
     */
    public int insertTWorkingFaceOfCoalMining(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining);

    /**
     * 修改采煤工作面
     *
     * @param tWorkingFaceOfCoalMining 采煤工作面
     * @return 结果
     */
    public int updateTWorkingFaceOfCoalMining(TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining);

    /**
     * 删除采煤工作面
     *
     * @param id 采煤工作面主键
     * @return 结果
     */
    public int deleteTWorkingFaceOfCoalMiningById(Integer id);

    /**
     * 批量删除采煤工作面
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTWorkingFaceOfCoalMiningByIds(Integer[] ids);

    /**
     * 批量获取采煤工作面
     * @return 结果
     */
    public List<TWorkingFaceOfCoalMining> selectAllTWorkingFaceOfCoalMining();
}
