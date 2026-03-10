package com.ruoyi.hh.physical.attribute.service;

import com.ruoyi.hh.physical.attribute.domain.TBZKSS;

import java.util.List;

/**
 * 钻孔应力测点实时信息Service接口
 * 
 * @author eleven
 * @date 2024-11-12
 */
public interface ITBZKSSService 
{
    /**
     * 查询钻孔应力测点实时信息
     * 
     * @param id 钻孔应力测点实时信息主键
     * @return 钻孔应力测点实时信息
     */
    public TBZKSS selectTBZKSSById(Integer id);

    /**
     * 查询钻孔应力测点实时信息列表
     * 
     * @param tBZKSS 钻孔应力测点实时信息
     * @return 钻孔应力测点实时信息集合
     */
    public List<TBZKSS> selectTBZKSSList(TBZKSS tBZKSS);

    /**
     * 新增钻孔应力测点实时信息
     * 
     * @param tBZKSS 钻孔应力测点实时信息
     * @return 结果
     */
    public int insertTBZKSS(TBZKSS tBZKSS);

    /**
     * 修改钻孔应力测点实时信息
     * 
     * @param tBZKSS 钻孔应力测点实时信息
     * @return 结果
     */
    public int updateTBZKSS(TBZKSS tBZKSS);

    /**
     * 批量删除钻孔应力测点实时信息
     * 
     * @param ids 需要删除的钻孔应力测点实时信息主键集合
     * @return 结果
     */
    public int deleteTBZKSSByIds(Integer[] ids);

    /**
     * 删除钻孔应力测点实时信息信息
     * 
     * @param id 钻孔应力测点实时信息主键
     * @return 结果
     */
    public int deleteTBZKSSById(Integer id);
}
