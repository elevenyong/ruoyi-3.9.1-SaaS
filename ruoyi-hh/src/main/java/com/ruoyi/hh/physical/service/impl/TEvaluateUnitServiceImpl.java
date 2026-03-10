package com.ruoyi.hh.physical.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.physical.domain.TEvaluateUnit;
import com.ruoyi.hh.physical.domain.TWorkingFace;
import com.ruoyi.hh.physical.mapper.TEvaluateUnitMapper;
import com.ruoyi.hh.physical.service.ITEvaluateUnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 评价单元信息Service业务层处理
 * 
 * @author eleven
 * @date 2022-08-26
 */
@Service
public class TEvaluateUnitServiceImpl implements ITEvaluateUnitService
{
    @Resource
    private TEvaluateUnitMapper tEvaluateUnitMapper;

    /**
     * 查询评价单元信息
     * 
     * @param id 评价单元信息主键
     * @return 评价单元信息
     */
    @Override
    public TEvaluateUnit selectTEvaluateUnitById(Integer id)
    {
        return tEvaluateUnitMapper.selectTEvaluateUnitById(id);
    }

    /**
     * 查询评价单元信息列表
     * 
     * @param tEvaluateUnit 评价单元信息
     * @return 评价单元信息
     */
    @Override
    public List<TEvaluateUnit> selectTEvaluateUnitList(TEvaluateUnit tEvaluateUnit)
    {
        return tEvaluateUnitMapper.selectTEvaluateUnitList(tEvaluateUnit);
    }

    /**
     * 新增评价单元信息
     * 
     * @param tEvaluateUnit 评价单元信息
     * @return 结果
     */
    @Override
    public int insertTEvaluateUnit(TEvaluateUnit tEvaluateUnit)
    {
        tEvaluateUnit.setCreateTime(DateUtils.getNowDate());
        return tEvaluateUnitMapper.insertTEvaluateUnit(tEvaluateUnit);
    }

    /**
     * 修改评价单元信息
     * 
     * @param tEvaluateUnit 评价单元信息
     * @return 结果
     */
    @Override
    public int updateTEvaluateUnit(TEvaluateUnit tEvaluateUnit)
    {
        tEvaluateUnit.setUpdateTime(DateUtils.getNowDate());
        return tEvaluateUnitMapper.updateTEvaluateUnit(tEvaluateUnit);
    }

    /**
     * 批量删除评价单元信息
     * 
     * @param ids 需要删除的评价单元信息主键
     * @return 结果
     */
    @Override
    public int deleteTEvaluateUnitByIds(List<Integer> ids)
    {
        return tEvaluateUnitMapper.deleteTEvaluateUnitByIds(ids);
    }

    /**
     * 删除评价单元信息信息
     * 
     * @param id 评价单元信息主键
     * @return 结果
     */
    @Override
    public int deleteTEvaluateUnitById(Integer id)
    {
        return tEvaluateUnitMapper.deleteTEvaluateUnitById(id);
    }

    @Override
    public List<TEvaluateUnit> getPageList() {
        return this.tEvaluateUnitMapper.getPageList();
    }

    /**
     * 根据工作面ID查询评价单元ID
     * @param workfeceid 工作面ID
     * @return 列表
     */
    public List<TEvaluateUnit> getByWorkfaceId(Integer workfeceid){
        return this.tEvaluateUnitMapper.getByWorkfaceId(workfeceid);
    }

    /**
     * 根据评价单元ID查询所属工作面信息
     * @param evaluateunitid 评价单元ID
     * @return 工作面信息
     */
    public TWorkingFace getWorkfaceByEvaluateUnitId(Integer evaluateunitid){
        return this.tEvaluateUnitMapper.getWorkfaceByEvaluateUnitId(evaluateunitid);
    }

    /**
     * 根据工作面ID或者评价单元ID查询评判报告相关信息
     * @param workfaceid 工作面ID
     * @param evaluateunitid 评价单元ID
     * @return 列表
     */
    public List<TEvaluateUnit> getJudgeReportInfo(Integer workfaceid,Integer evaluateunitid){
        return this.tEvaluateUnitMapper.getJudgeReportInfo(workfaceid, evaluateunitid);
    }
}
