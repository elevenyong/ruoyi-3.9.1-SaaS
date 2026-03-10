package com.ruoyi.hh.simulate.mapper;

import java.util.List;

import com.ruoyi.hh.simulate.domain.Workfacerealtime;

/**
 * 模拟抽采实时数据Mapper接口
 * 
 * @author eleven
 * @date 2022-10-29
 */
public interface WorkfacerealtimeMapper 
{
    /**
     * 查询模拟抽采实时数据
     * 
     * @param id 模拟抽采实时数据主键
     * @return 模拟抽采实时数据
     */
    public Workfacerealtime selectWorkfacerealtimeById(Integer id);

    /**
     * 查询模拟抽采实时数据列表
     * 
     * @param workfacerealtime 模拟抽采实时数据
     * @return 模拟抽采实时数据集合
     */
    public List<Workfacerealtime> selectWorkfacerealtimeList(Workfacerealtime workfacerealtime);

    /**
     * 新增模拟抽采实时数据
     * 
     * @param workfacerealtime 模拟抽采实时数据
     * @return 结果
     */
    public int insertWorkfacerealtime(Workfacerealtime workfacerealtime);

    /**
     * 修改模拟抽采实时数据
     * 
     * @param workfacerealtime 模拟抽采实时数据
     * @return 结果
     */
    public int updateWorkfacerealtime(Workfacerealtime workfacerealtime);

    /**
     * 删除模拟抽采实时数据
     * 
     * @param id 模拟抽采实时数据主键
     * @return 结果
     */
    public int deleteWorkfacerealtimeById(Integer id);

    /**
     * 批量删除模拟抽采实时数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkfacerealtimeByIds(Integer[] ids);

    /**
     * 根据工作面ID查询模拟工作面管路抽采信息
     * @param workfaceid 工作面ID
     * @return 抽采信息
     */
    public List<Workfacerealtime> getWorkFaceExtractionInfoById(Integer workfaceid);
}
