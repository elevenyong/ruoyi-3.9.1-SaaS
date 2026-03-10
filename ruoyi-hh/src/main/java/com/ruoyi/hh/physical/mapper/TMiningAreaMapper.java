package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TMiningArea;

/**
 * 采区信息Mapper接口
 * 
 * @author eleven
 * @date 2022-10-19
 */
public interface TMiningAreaMapper 
{
    /**
     * 查询采区信息
     * 
     * @param id 采区信息主键
     * @return 采区信息
     */
    public TMiningArea selectTMiningAreaById(Integer id);

    /**
     * 查询采区信息列表
     * 
     * @param tMiningArea 采区信息
     * @return 采区信息集合
     */
    public List<TMiningArea> selectTMiningAreaList(TMiningArea tMiningArea);

    /**
     * 新增采区信息
     * 
     * @param tMiningArea 采区信息
     * @return 结果
     */
    public int insertTMiningArea(TMiningArea tMiningArea);

    /**
     * 修改采区信息
     * 
     * @param tMiningArea 采区信息
     * @return 结果
     */
    public int updateTMiningArea(TMiningArea tMiningArea);

    /**
     * 删除采区信息
     * 
     * @param id 采区信息主键
     * @return 结果
     */
    public int deleteTMiningAreaById(Integer id);

    /**
     * 批量删除采区信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTMiningAreaByIds(List<Integer> ids);
}
