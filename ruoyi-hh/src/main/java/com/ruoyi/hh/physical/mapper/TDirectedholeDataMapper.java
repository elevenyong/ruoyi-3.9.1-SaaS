package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TDirectedholeData;

/**
 * 设计定向钻数据Mapper接口
 * 
 * @author eleven
 * @date 2023-03-06
 */
public interface TDirectedholeDataMapper 
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
     * 删除设计定向钻数据
     * 
     * @param id 设计定向钻数据主键
     * @return 结果
     */
    public int deleteTDirectedholeDataById(Integer id);

    /**
     * 批量删除设计定向钻数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDirectedholeDataByIds(Integer[] ids);
}
