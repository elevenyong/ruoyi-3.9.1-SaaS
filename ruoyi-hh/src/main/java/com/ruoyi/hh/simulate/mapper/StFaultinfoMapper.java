package com.ruoyi.hh.simulate.mapper;

import com.ruoyi.hh.simulate.domain.StFaultinfo;

import java.util.List;

/**
 * 达标评判断层模拟Mapper接口
 * 
 * @author eleven
 * @date 2023-01-14
 */
public interface StFaultinfoMapper 
{
    /**
     * 查询达标评判断层模拟
     * 
     * @param id 达标评判断层模拟主键
     * @return 达标评判断层模拟
     */
    public StFaultinfo selectStFaultinfoById(Integer id);

    /**
     * 查询达标评判断层模拟列表
     * 
     * @param stFaultinfo 达标评判断层模拟
     * @return 达标评判断层模拟集合
     */
    public List<StFaultinfo> selectStFaultinfoList(StFaultinfo stFaultinfo);

    /**
     * 新增达标评判断层模拟
     * 
     * @param stFaultinfo 达标评判断层模拟
     * @return 结果
     */
    public int insertStFaultinfo(StFaultinfo stFaultinfo);

    /**
     * 修改达标评判断层模拟
     * 
     * @param stFaultinfo 达标评判断层模拟
     * @return 结果
     */
    public int updateStFaultinfo(StFaultinfo stFaultinfo);

    /**
     * 删除达标评判断层模拟
     * 
     * @param id 达标评判断层模拟主键
     * @return 结果
     */
    public int deleteStFaultinfoById(Integer id);

    /**
     * 批量删除达标评判断层模拟
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStFaultinfoByIds(Integer[] ids);

    /**
     * 根据工作面ID查询模拟断层信息列表
     * @param workfaceid 工作面ID
     * @return 断层信息列表
     */
    public List<StFaultinfo> getByworkfaceid(Integer workfaceid);
}
