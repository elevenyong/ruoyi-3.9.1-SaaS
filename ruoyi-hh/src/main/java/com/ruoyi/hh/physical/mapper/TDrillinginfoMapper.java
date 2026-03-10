package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TDrillinginfo;

/**
 * 钻机信息Mapper接口
 * 
 * @author eleven
 * @date 2023-03-21
 */
public interface TDrillinginfoMapper 
{
    /**
     * 查询钻机信息
     * 
     * @param id 钻机信息主键
     * @return 钻机信息
     */
    public TDrillinginfo selectTDrillinginfoById(Integer id);

    /**
     * 查询钻机信息列表
     * 
     * @param tDrillinginfo 钻机信息
     * @return 钻机信息集合
     */
    public List<TDrillinginfo> selectTDrillinginfoList(TDrillinginfo tDrillinginfo);

    /**
     * 新增钻机信息
     * 
     * @param tDrillinginfo 钻机信息
     * @return 结果
     */
    public int insertTDrillinginfo(TDrillinginfo tDrillinginfo);

    /**
     * 修改钻机信息
     * 
     * @param tDrillinginfo 钻机信息
     * @return 结果
     */
    public int updateTDrillinginfo(TDrillinginfo tDrillinginfo);

    /**
     * 删除钻机信息
     * 
     * @param id 钻机信息主键
     * @return 结果
     */
    public int deleteTDrillinginfoById(Integer id);

    /**
     * 批量删除钻机信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDrillinginfoByIds(Integer[] ids);
}
