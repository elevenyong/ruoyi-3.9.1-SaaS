package com.ruoyi.hh.simulate.service;

import java.util.List;

import com.ruoyi.hh.simulate.domain.DataHistory;
import org.apache.ibatis.annotations.Param;

/**
 * 模拟存储Service接口
 * 
 * @author eleven
 * @date 2022-12-10
 */
public interface IDataHistoryService
{
    /**
     * 查询模拟存储
     * 
     * @param id 模拟存储主键
     * @return 模拟存储
     */
    public DataHistory selectDataHistoryById(Integer id);

    /**
     * 查询模拟存储列表
     * 
     * @param dataHistory 模拟存储
     * @return 模拟存储集合
     */
    public List<DataHistory> selectDataHistoryList(DataHistory dataHistory);

    /**
     * 新增模拟存储
     * 
     * @param dataHistory 模拟存储
     * @return 结果
     */
    public int insertDataHistory(DataHistory dataHistory);

    /**
     * 修改模拟存储
     * 
     * @param dataHistory 模拟存储
     * @return 结果
     */
    public int updateDataHistory(DataHistory dataHistory);

    /**
     * 批量删除模拟存储
     * 
     * @param ids 需要删除的模拟存储主键集合
     * @return 结果
     */
    public int deleteDataHistoryByIds(Integer[] ids);

    /**
     * 删除模拟存储信息
     * 
     * @param id 模拟存储主键
     * @return 结果
     */
    public int deleteDataHistoryById(Integer id);

    public List<DataHistory> getInTime(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
