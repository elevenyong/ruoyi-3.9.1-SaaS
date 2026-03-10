package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TDirectedholeData;

import java.util.List;

/**
 * 设计定向钻数据Service接口
 * 
 * @author eleven
 * @date 2023-03-06
 */
public interface ITDirectedholeDataService 
{
    /**
     * 查询设计定向钻数据
     * 
     * @param id 设计定向钻数据主键
     * @return 设计定向钻数据
     */
    public TDirectedholeData selectTDirectedholeDataById(Integer id);

    /**
     * 查询设计定向钻数据列表
     * 
     * @param tDirectedholeData 设计定向钻数据
     * @return 设计定向钻数据集合
     */
    public List<TDirectedholeData> selectTDirectedholeDataList(TDirectedholeData tDirectedholeData);

    /**
     * 新增设计定向钻数据
     * 
     * @param tDirectedholeData 设计定向钻数据
     * @return 结果
     */
    public int insertTDirectedholeData(TDirectedholeData tDirectedholeData);

    /**
     * 修改设计定向钻数据
     * 
     * @param tDirectedholeData 设计定向钻数据
     * @return 结果
     */
    public int updateTDirectedholeData(TDirectedholeData tDirectedholeData);

    /**
     * 批量删除设计定向钻数据
     * 
     * @param ids 需要删除的设计定向钻数据主键集合
     * @return 结果
     */
    public int deleteTDirectedholeDataByIds(Integer[] ids);

    /**
     * 删除设计定向钻数据信息
     * 
     * @param id 设计定向钻数据主键
     * @return 结果
     */
    public int deleteTDirectedholeDataById(Integer id);
}
