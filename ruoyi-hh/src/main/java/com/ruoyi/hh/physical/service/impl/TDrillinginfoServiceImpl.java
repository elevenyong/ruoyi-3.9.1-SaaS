package com.ruoyi.hh.physical.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.physical.domain.TDrillinginfo;
import com.ruoyi.hh.physical.mapper.TDrillinginfoMapper;
import com.ruoyi.hh.physical.service.ITDrillinginfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 钻机信息Service业务层处理
 * 
 * @author eleven
 * @date 2023-03-21
 */
@Service
public class TDrillinginfoServiceImpl implements ITDrillinginfoService
{
    @Resource
    private TDrillinginfoMapper tDrillinginfoMapper;

    /**
     * 查询钻机信息
     * 
     * @param id 钻机信息主键
     * @return 钻机信息
     */
    @Override
    public TDrillinginfo selectTDrillinginfoById(Integer id)
    {
        return tDrillinginfoMapper.selectTDrillinginfoById(id);
    }

    /**
     * 查询钻机信息列表
     * 
     * @param tDrillinginfo 钻机信息
     * @return 钻机信息
     */
    @Override
    public List<TDrillinginfo> selectTDrillinginfoList(TDrillinginfo tDrillinginfo)
    {
        return tDrillinginfoMapper.selectTDrillinginfoList(tDrillinginfo);
    }

    /**
     * 新增钻机信息
     * 
     * @param tDrillinginfo 钻机信息
     * @return 结果
     */
    @Override
    public int insertTDrillinginfo(TDrillinginfo tDrillinginfo)
    {
        tDrillinginfo.setCreateTime(DateUtils.getNowDate());
        return tDrillinginfoMapper.insertTDrillinginfo(tDrillinginfo);
    }

    /**
     * 修改钻机信息
     * 
     * @param tDrillinginfo 钻机信息
     * @return 结果
     */
    @Override
    public int updateTDrillinginfo(TDrillinginfo tDrillinginfo)
    {
        tDrillinginfo.setUpdateTime(DateUtils.getNowDate());
        return tDrillinginfoMapper.updateTDrillinginfo(tDrillinginfo);
    }

    /**
     * 批量删除钻机信息
     * 
     * @param ids 需要删除的钻机信息主键
     * @return 结果
     */
    @Override
    public int deleteTDrillinginfoByIds(Integer[] ids)
    {
        return tDrillinginfoMapper.deleteTDrillinginfoByIds(ids);
    }

    /**
     * 删除钻机信息信息
     * 
     * @param id 钻机信息主键
     * @return 结果
     */
    @Override
    public int deleteTDrillinginfoById(Integer id)
    {
        return tDrillinginfoMapper.deleteTDrillinginfoById(id);
    }
}
