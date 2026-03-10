package com.ruoyi.hh.physical.service;

import java.util.List;

import com.ruoyi.hh.physical.domain.TEvaluateUnit;
import com.ruoyi.hh.physical.domain.TWorkingFace;

/**
 * 评价单元信息Service接口
 * 
 * @author eleven
 * @date 2022-08-26
 */
public interface ITEvaluateUnitService 
{
    /**
     * 查询评价单元信息
     * 
     * @param id 评价单元信息主键
     * @return 评价单元信息
     */
    public TEvaluateUnit selectTEvaluateUnitById(Integer id);

    /**
     * 查询评价单元信息列表
     * 
     * @param tEvaluateUnit 评价单元信息
     * @return 评价单元信息集合
     */
    public List<TEvaluateUnit> selectTEvaluateUnitList(TEvaluateUnit tEvaluateUnit);

    /**
     * 新增评价单元信息
     * 
     * @param tEvaluateUnit 评价单元信息
     * @return 结果
     */
    public int insertTEvaluateUnit(TEvaluateUnit tEvaluateUnit);

    /**
     * 修改评价单元信息
     * 
     * @param tEvaluateUnit 评价单元信息
     * @return 结果
     */
    public int updateTEvaluateUnit(TEvaluateUnit tEvaluateUnit);

    /**
     * 批量删除评价单元信息
     * 
     * @param ids 需要删除的评价单元信息主键集合
     * @return 结果
     */
    public int deleteTEvaluateUnitByIds(List<Integer> ids);

    /**
     * 删除评价单元信息信息
     * 
     * @param id 评价单元信息主键
     * @return 结果
     */
    public int deleteTEvaluateUnitById(Integer id);
    /**
     * 查询所有
     * @return 列表
     */
    public List<TEvaluateUnit> getPageList();

    /**
     * 根据工作面ID查询评价单元ID
     * @param workfeceid 工作面ID
     * @return 列表
     */
    public List<TEvaluateUnit> getByWorkfaceId(Integer workfeceid);

    /**
     * 根据评价单元ID查询所属工作面信息
     * @param evaluateunitid 评价单元ID
     * @return 工作面信息
     */
    public TWorkingFace getWorkfaceByEvaluateUnitId(Integer evaluateunitid);

    /**
     * 根据工作面ID或者评价单元ID查询评判报告相关信息
     * @param workfaceid 工作面ID
     * @param evaluateunitid 评价单元ID
     * @return 列表
     */
    public List<TEvaluateUnit> getJudgeReportInfo(Integer workfaceid,Integer evaluateunitid);
}
