package com.ruoyi.hh.simulate.service.impl;

import java.util.List;

import com.ruoyi.hh.simulate.domain.StFaultinfo;
import com.ruoyi.hh.simulate.mapper.StFaultinfoMapper;
import com.ruoyi.hh.simulate.service.IStFaultinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 达标评判断层模拟Service业务层处理
 * 
 * @author eleven
 * @date 2023-01-14
 */
@Service
public class StFaultinfoServiceImpl implements IStFaultinfoService
{
    @Resource
    private StFaultinfoMapper stFaultinfoMapper;

    /**
     * 查询达标评判断层模拟
     * 
     * @param id 达标评判断层模拟主键
     * @return 达标评判断层模拟
     */
    @Override
    public StFaultinfo selectStFaultinfoById(Integer id)
    {
        return stFaultinfoMapper.selectStFaultinfoById(id);
    }

    /**
     * 查询达标评判断层模拟列表
     * 
     * @param stFaultinfo 达标评判断层模拟
     * @return 达标评判断层模拟
     */
    @Override
    public List<StFaultinfo> selectStFaultinfoList(StFaultinfo stFaultinfo)
    {
        return stFaultinfoMapper.selectStFaultinfoList(stFaultinfo);
    }

    /**
     * 新增达标评判断层模拟
     * 
     * @param stFaultinfo 达标评判断层模拟
     * @return 结果
     */
    @Override
    public int insertStFaultinfo(StFaultinfo stFaultinfo)
    {
        return stFaultinfoMapper.insertStFaultinfo(stFaultinfo);
    }

    /**
     * 修改达标评判断层模拟
     * 
     * @param stFaultinfo 达标评判断层模拟
     * @return 结果
     */
    @Override
    public int updateStFaultinfo(StFaultinfo stFaultinfo)
    {
        return stFaultinfoMapper.updateStFaultinfo(stFaultinfo);
    }

    /**
     * 批量删除达标评判断层模拟
     * 
     * @param ids 需要删除的达标评判断层模拟主键
     * @return 结果
     */
    @Override
    public int deleteStFaultinfoByIds(Integer[] ids)
    {
        return stFaultinfoMapper.deleteStFaultinfoByIds(ids);
    }

    /**
     * 删除达标评判断层模拟信息
     * 
     * @param id 达标评判断层模拟主键
     * @return 结果
     */
    @Override
    public int deleteStFaultinfoById(Integer id)
    {
        return stFaultinfoMapper.deleteStFaultinfoById(id);
    }

    /**
     * 根据工作面ID查询模拟断层信息列表
     * @param workfaceid 工作面ID
     * @return 断层信息列表
     */
    public List<StFaultinfo> getByworkfaceid(Integer workfaceid){
        return this.stFaultinfoMapper.getByworkfaceid(workfaceid);
    }
}
