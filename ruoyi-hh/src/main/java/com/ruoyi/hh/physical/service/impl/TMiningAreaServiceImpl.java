package com.ruoyi.hh.physical.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.physical.domain.TMiningArea;
import com.ruoyi.hh.physical.mapper.TMiningAreaMapper;
import com.ruoyi.hh.physical.service.ITMiningAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 采区信息Service业务层处理
 * 
 * @author eleven
 * @date 2022-10-19
 */
@Service
public class TMiningAreaServiceImpl implements ITMiningAreaService
{
    @Autowired
    private TMiningAreaMapper tMiningAreaMapper;

    /**
     * 查询采区信息
     * 
     * @param id 采区信息主键
     * @return 采区信息
     */
    @Override
    public TMiningArea selectTMiningAreaById(Integer id)
    {
        return tMiningAreaMapper.selectTMiningAreaById(id);
    }

    /**
     * 查询采区信息列表
     * 
     * @param tMiningArea 采区信息
     * @return 采区信息
     */
    @Override
    public List<TMiningArea> selectTMiningAreaList(TMiningArea tMiningArea)
    {
        return tMiningAreaMapper.selectTMiningAreaList(tMiningArea);
    }

    /**
     * 新增采区信息
     * 
     * @param tMiningArea 采区信息
     * @return 结果
     */
    @Override
    public int insertTMiningArea(TMiningArea tMiningArea)
    {
        tMiningArea.setCreateTime(DateUtils.getNowDate());
        return tMiningAreaMapper.insertTMiningArea(tMiningArea);
    }

    /**
     * 修改采区信息
     * 
     * @param tMiningArea 采区信息
     * @return 结果
     */
    @Override
    public int updateTMiningArea(TMiningArea tMiningArea)
    {
        tMiningArea.setUpdateTime(DateUtils.getNowDate());
        return tMiningAreaMapper.updateTMiningArea(tMiningArea);
    }

    /**
     * 批量删除采区信息
     * 
     * @param ids 需要删除的采区信息主键
     * @return 结果
     */
    @Override
    public int deleteTMiningAreaByIds(List<Integer> ids)
    {
        return tMiningAreaMapper.deleteTMiningAreaByIds(ids);
    }

    /**
     * 删除采区信息信息
     * 
     * @param id 采区信息主键
     * @return 结果
     */
    @Override
    public int deleteTMiningAreaById(Integer id)
    {
        return tMiningAreaMapper.deleteTMiningAreaById(id);
    }
}
