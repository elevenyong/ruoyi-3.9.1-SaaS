package com.ruoyi.hh.simulate.service.impl;

import java.util.List;

import com.ruoyi.hh.simulate.domain.DataHistory;
import com.ruoyi.hh.simulate.mapper.DataHistoryMapper;
import com.ruoyi.hh.simulate.service.IDataHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 模拟存储Service业务层处理
 * 
 * @author eleven
 * @date 2022-12-10
 */
@Service
public class DataHistoryServiceImpl implements IDataHistoryService
{
    @Resource
    private DataHistoryMapper dataHistoryMapper;

    /**
     * 查询模拟存储
     * 
     * @param id 模拟存储主键
     * @return 模拟存储
     */
    @Override
    public DataHistory selectDataHistoryById(Integer id)
    {
        return dataHistoryMapper.selectDataHistoryById(id);
    }

    /**
     * 查询模拟存储列表
     * 
     * @param dataHistory 模拟存储
     * @return 模拟存储
     */
    @Override
    public List<DataHistory> selectDataHistoryList(DataHistory dataHistory)
    {
        return dataHistoryMapper.selectDataHistoryList(dataHistory);
    }

    /**
     * 新增模拟存储
     * 
     * @param dataHistory 模拟存储
     * @return 结果
     */
    @Override
    public int insertDataHistory(DataHistory dataHistory)
    {
        return dataHistoryMapper.insertDataHistory(dataHistory);
    }

    /**
     * 修改模拟存储
     * 
     * @param dataHistory 模拟存储
     * @return 结果
     */
    @Override
    public int updateDataHistory(DataHistory dataHistory)
    {
        return dataHistoryMapper.updateDataHistory(dataHistory);
    }

    /**
     * 批量删除模拟存储
     * 
     * @param ids 需要删除的模拟存储主键
     * @return 结果
     */
    @Override
    public int deleteDataHistoryByIds(Integer[] ids)
    {
        return dataHistoryMapper.deleteDataHistoryByIds(ids);
    }

    /**
     * 删除模拟存储信息
     * 
     * @param id 模拟存储主键
     * @return 结果
     */
    @Override
    public int deleteDataHistoryById(Integer id)
    {
        return dataHistoryMapper.deleteDataHistoryById(id);
    }

    @Override
    public List<DataHistory> getInTime(String startDate, String endDate) {
        return this.dataHistoryMapper.getInTime(startDate, endDate);
    }
}
