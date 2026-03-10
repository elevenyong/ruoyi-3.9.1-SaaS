package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TDrainageUnit;
import com.ruoyi.hh.simulate.domain.DrainageUnitTotal;

import java.util.List;

/**
 * 评价单元信息Service接口
 *
 * @author eleven
 * @date 2022-07-16
 */
public interface ITDrainageUnitService {
    /**
     * 查询评价单元信息
     *
     * @param id 评价单元信息主键
     * @return 评价单元信息
     */
    public TDrainageUnit selectTDrainageUnitById(Integer id);

    /**
     * 查询评价单元信息列表
     *
     * @param tDrainageUnit 评价单元信息
     * @return 评价单元信息集合
     */
    public List<TDrainageUnit> selectTDrainageUnitList(TDrainageUnit tDrainageUnit);

    /**
     * 新增评价单元信息
     *
     * @param tDrainageUnit 评价单元信息
     * @return 结果
     */
    public Integer insertTDrainageUnit(TDrainageUnit tDrainageUnit);

    /**
     * 修改评价单元信息
     *
     * @param tDrainageUnit 评价单元信息
     * @return 结果
     */
    public Integer updateTDrainageUnit(TDrainageUnit tDrainageUnit);

    /**
     * 批量删除评价单元信息
     *
     * @param ids 需要删除的评价单元信息主键集合
     * @return 结果
     */
    public Integer deleteTDrainageUnitByIds(List<Integer> ids);

    /**
     * 删除评价单元信息信息
     *
     * @param id 评价单元信息主键
     * @return 结果
     */
    public Integer deleteTDrainageUnitById(Integer id);

//    /**
//     * 分页获取评价单元列表
//     *
//     * @return 列表
//     */
//    public List<TDrainageUnit> getPageList();

    /**
     * 根据工作面ID查询抽采单元列表
     * @param workfaceid 工作面ID
     * @return 抽采单元列表
     */
    public List<TDrainageUnit> getByWorkfaceId(Integer workfaceid);

    /**
     * 获取抽采单元抽采累计流量
     */
    public List <DrainageUnitTotal> getDrainageUnitTotal(Integer workfaceid);

    /***
     * 根据评价单元ID查询抽采单元列表
     * @param evaluationunitid 评价单元
     * @return
     */
    public List<TDrainageUnit> getByEvaluateUnitId(Integer evaluationunitid);
}
