package com.ruoyi.hh.simulate.mapper;

import java.util.List;

import com.ruoyi.hh.simulate.domain.DataHistory;
import org.apache.ibatis.annotations.Param;

/**
 * 模拟存储Mapper接口
 * 
 * @author eleven
 * @date 2022-12-10
 */
public interface DataHistoryMapper
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
     * 删除模拟存储
     * 
     * @param id 模拟存储主键
     * @return 结果
     */
    public int deleteDataHistoryById(Integer id);

    /**
     * 批量删除模拟存储
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDataHistoryByIds(Integer[] ids);

    public List<DataHistory> getInTime(@Param("startDate") String startDate,@Param("endDate") String endDate);
}
