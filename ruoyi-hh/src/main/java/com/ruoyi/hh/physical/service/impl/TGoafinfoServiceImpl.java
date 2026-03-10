package com.ruoyi.hh.physical.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.domain.TGoafinfo;
import com.ruoyi.hh.physical.mapper.TGoafinfoMapper;
import com.ruoyi.hh.physical.service.ITGoafinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 采空区信息Service业务层处理
 * 
 * @author eleven
 * @date 2022-12-09
 */
@Service
public class TGoafinfoServiceImpl implements ITGoafinfoService
{
    @Resource
    private TGoafinfoMapper tGoafinfoMapper;

    /**
     * 查询采空区信息
     * 
     * @param id 采空区信息主键
     * @return 采空区信息
     */
    @Override
    public TGoafinfo selectTGoafinfoById(Integer id)
    {
        return tGoafinfoMapper.selectTGoafinfoById(id);
    }

    /**
     * 查询采空区信息列表
     * 
     * @param tGoafinfo 采空区信息
     * @return 采空区信息
     */
    @Override
    public List<TGoafinfo> selectTGoafinfoList(TGoafinfo tGoafinfo)
    {
        return tGoafinfoMapper.selectTGoafinfoList(tGoafinfo);
    }

    /**
     * 新增采空区信息
     * 
     * @param tGoafinfo 采空区信息
     * @return 结果
     */
    @Override
    public int insertTGoafinfo(TGoafinfo tGoafinfo)
    {
        return tGoafinfoMapper.insertTGoafinfo(tGoafinfo);
    }

    /**
     * 修改采空区信息
     * 
     * @param tGoafinfo 采空区信息
     * @return 结果
     */
    @Override
    public int updateTGoafinfo(TGoafinfo tGoafinfo)
    {
        return tGoafinfoMapper.updateTGoafinfo(tGoafinfo);
    }

    /**
     * 批量删除采空区信息
     * 
     * @param ids 需要删除的采空区信息主键
     * @return 结果
     */
    @Override
    public int deleteTGoafinfoByIds(Integer[] ids)
    {
        return tGoafinfoMapper.deleteTGoafinfoByIds(ids);
    }

    /**
     * 删除采空区信息信息
     * 
     * @param id 采空区信息主键
     * @return 结果
     */
    @Override
    public int deleteTGoafinfoById(Integer id)
    {
        return tGoafinfoMapper.deleteTGoafinfoById(id);
    }
}
