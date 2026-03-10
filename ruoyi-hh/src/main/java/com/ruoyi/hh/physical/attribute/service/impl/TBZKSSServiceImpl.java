package com.ruoyi.hh.physical.attribute.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.attribute.domain.TBZKSS;
import com.ruoyi.hh.physical.attribute.mapper.TBZKSSMapper;
import com.ruoyi.hh.physical.attribute.service.ITBZKSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 钻孔应力测点实时信息Service业务层处理
 * 
 * @author eleven
 * @date 2024-11-12
 */
@Service
public class TBZKSSServiceImpl implements ITBZKSSService
{
    @Resource
    private TBZKSSMapper tBZKSSMapper;

    /**
     * 查询钻孔应力测点实时信息
     * 
     * @param id 钻孔应力测点实时信息主键
     * @return 钻孔应力测点实时信息
     */
    @Override
    public TBZKSS selectTBZKSSById(Integer id)
    {
        return tBZKSSMapper.selectTBZKSSById(id);
    }

    /**
     * 查询钻孔应力测点实时信息列表
     * 
     * @param tBZKSS 钻孔应力测点实时信息
     * @return 钻孔应力测点实时信息
     */
    @Override
    public List<TBZKSS> selectTBZKSSList(TBZKSS tBZKSS)
    {
        return tBZKSSMapper.selectTBZKSSList(tBZKSS);
    }

    /**
     * 新增钻孔应力测点实时信息
     * 
     * @param tBZKSS 钻孔应力测点实时信息
     * @return 结果
     */
    @Override
    public int insertTBZKSS(TBZKSS tBZKSS)
    {
        return tBZKSSMapper.insertTBZKSS(tBZKSS);
    }

    /**
     * 修改钻孔应力测点实时信息
     * 
     * @param tBZKSS 钻孔应力测点实时信息
     * @return 结果
     */
    @Override
    public int updateTBZKSS(TBZKSS tBZKSS)
    {
        return tBZKSSMapper.updateTBZKSS(tBZKSS);
    }

    /**
     * 批量删除钻孔应力测点实时信息
     * 
     * @param ids 需要删除的钻孔应力测点实时信息主键
     * @return 结果
     */
    @Override
    public int deleteTBZKSSByIds(Integer[] ids)
    {
        return tBZKSSMapper.deleteTBZKSSByIds(ids);
    }

    /**
     * 删除钻孔应力测点实时信息信息
     * 
     * @param id 钻孔应力测点实时信息主键
     * @return 结果
     */
    @Override
    public int deleteTBZKSSById(Integer id)
    {
        return tBZKSSMapper.deleteTBZKSSById(id);
    }
}
