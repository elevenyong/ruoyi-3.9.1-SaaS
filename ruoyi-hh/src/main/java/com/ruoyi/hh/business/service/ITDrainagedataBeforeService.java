package com.ruoyi.hh.business.service;

import com.ruoyi.hh.business.domain.TDrainagedataBefore;

import java.util.List;

/**
 * 抽采系统截止某时间的抽采量Service接口
 * 
 * @author ruoyi
 * @date 2023-07-21
 */
public interface ITDrainagedataBeforeService
{
    /**
     * 查询抽采系统截止某时间的抽采量
     * 
     * @param id 抽采系统截止某时间的抽采量主键
     * @return 抽采系统截止某时间的抽采量
     */
    public TDrainagedataBefore selectTDrainagedataBeforeById(Integer id);

    /**
     * 查询抽采系统截止某时间的抽采量列表
     * 
     * @param tDrainagedataBefore 抽采系统截止某时间的抽采量
     * @return 抽采系统截止某时间的抽采量集合
     */
    public List<TDrainagedataBefore> selectTDrainagedataBeforeList(TDrainagedataBefore tDrainagedataBefore);

    /**
     * 新增抽采系统截止某时间的抽采量
     * 
     * @param tDrainagedataBefore 抽采系统截止某时间的抽采量
     * @return 结果
     */
    public int insertTDrainagedataBefore(TDrainagedataBefore tDrainagedataBefore);

    /**
     * 修改抽采系统截止某时间的抽采量
     * 
     * @param tDrainagedataBefore 抽采系统截止某时间的抽采量
     * @return 结果
     */
    public int updateTDrainagedataBefore(TDrainagedataBefore tDrainagedataBefore);

    /**
     * 批量删除抽采系统截止某时间的抽采量
     * 
     * @param ids 需要删除的抽采系统截止某时间的抽采量主键集合
     * @return 结果
     */
    public int deleteTDrainagedataBeforeByIds(Integer[] ids);

    /**
     * 删除抽采系统截止某时间的抽采量信息
     * 
     * @param id 抽采系统截止某时间的抽采量主键
     * @return 结果
     */
    public int deleteTDrainagedataBeforeById(Integer id);

    /**
     * 根据测点安装位置获取列表
     * @param measurePointLocal
     * @return
     */
    public TDrainagedataBefore getByMeasurePointLocal(String measurePointLocal);
}
