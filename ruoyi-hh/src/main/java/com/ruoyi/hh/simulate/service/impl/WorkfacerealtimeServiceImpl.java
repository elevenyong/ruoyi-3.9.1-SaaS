package com.ruoyi.hh.simulate.service.impl;

import java.util.List;

import com.ruoyi.hh.simulate.domain.Workfacerealtime;
import com.ruoyi.hh.simulate.mapper.WorkfacerealtimeMapper;
import com.ruoyi.hh.simulate.service.IWorkfacerealtimeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 模拟抽采实时数据Service业务层处理
 * 
 * @author eleven
 * @date 2022-10-29
 */
@Service
public class WorkfacerealtimeServiceImpl implements IWorkfacerealtimeService
{
    @Resource
    private WorkfacerealtimeMapper workfacerealtimeMapper;

    /**
     * 查询模拟抽采实时数据
     * 
     * @param id 模拟抽采实时数据主键
     * @return 模拟抽采实时数据
     */
    @Override
    public Workfacerealtime selectWorkfacerealtimeById(Integer id)
    {
        return workfacerealtimeMapper.selectWorkfacerealtimeById(id);
    }

    /**
     * 查询模拟抽采实时数据列表
     * 
     * @param workfacerealtime 模拟抽采实时数据
     * @return 模拟抽采实时数据
     */
    @Override
    public List<Workfacerealtime> selectWorkfacerealtimeList(Workfacerealtime workfacerealtime)
    {
        return workfacerealtimeMapper.selectWorkfacerealtimeList(workfacerealtime);
    }

    /**
     * 新增模拟抽采实时数据
     * 
     * @param workfacerealtime 模拟抽采实时数据
     * @return 结果
     */
    @Override
    public int insertWorkfacerealtime(Workfacerealtime workfacerealtime)
    {
        return workfacerealtimeMapper.insertWorkfacerealtime(workfacerealtime);
    }

    /**
     * 修改模拟抽采实时数据
     * 
     * @param workfacerealtime 模拟抽采实时数据
     * @return 结果
     */
    @Override
    public int updateWorkfacerealtime(Workfacerealtime workfacerealtime)
    {
        return workfacerealtimeMapper.updateWorkfacerealtime(workfacerealtime);
    }

    /**
     * 批量删除模拟抽采实时数据
     * 
     * @param ids 需要删除的模拟抽采实时数据主键
     * @return 结果
     */
    @Override
    public int deleteWorkfacerealtimeByIds(Integer[] ids)
    {
        return workfacerealtimeMapper.deleteWorkfacerealtimeByIds(ids);
    }

    /**
     * 删除模拟抽采实时数据信息
     * 
     * @param id 模拟抽采实时数据主键
     * @return 结果
     */
    @Override
    public int deleteWorkfacerealtimeById(Integer id)
    {
        return workfacerealtimeMapper.deleteWorkfacerealtimeById(id);
    }

    /**
     * 根据工作面ID查询模拟工作面管路抽采信息
     * @param workfaceid 工作面ID
     * @return 抽采信息
     */
    @Override
    public List<Workfacerealtime> getWorkFaceExtractionInfoById(Integer workfaceid) {
        return this.workfacerealtimeMapper.getWorkFaceExtractionInfoById(workfaceid);
    }
}
