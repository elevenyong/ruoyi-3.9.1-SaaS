package com.ruoyi.hh.business.service.impl;

import com.ruoyi.hh.business.domain.TDrainagedataBefore;
import com.ruoyi.hh.business.mapper.TDrainagedataBeforeMapper;
import com.ruoyi.hh.business.service.ITDrainagedataBeforeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 抽采系统截止某时间的抽采量Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-07-21
 */
@Service
public class TDrainagedataBeforeServiceImpl implements ITDrainagedataBeforeService
{
    @Resource
    private TDrainagedataBeforeMapper tDrainagedataBeforeMapper;

    /**
     * 查询抽采系统截止某时间的抽采量
     * 
     * @param id 抽采系统截止某时间的抽采量主键
     * @return 抽采系统截止某时间的抽采量
     */
    @Override
    public TDrainagedataBefore selectTDrainagedataBeforeById(Integer id)
    {
        return tDrainagedataBeforeMapper.selectTDrainagedataBeforeById(id);
    }

    /**
     * 查询抽采系统截止某时间的抽采量列表
     * 
     * @param tDrainagedataBefore 抽采系统截止某时间的抽采量
     * @return 抽采系统截止某时间的抽采量
     */
    @Override
    public List<TDrainagedataBefore> selectTDrainagedataBeforeList(TDrainagedataBefore tDrainagedataBefore)
    {
        return tDrainagedataBeforeMapper.selectTDrainagedataBeforeList(tDrainagedataBefore);
    }

    /**
     * 新增抽采系统截止某时间的抽采量
     * 
     * @param tDrainagedataBefore 抽采系统截止某时间的抽采量
     * @return 结果
     */
    @Override
    public int insertTDrainagedataBefore(TDrainagedataBefore tDrainagedataBefore)
    {
        return tDrainagedataBeforeMapper.insertTDrainagedataBefore(tDrainagedataBefore);
    }

    /**
     * 修改抽采系统截止某时间的抽采量
     * 
     * @param tDrainagedataBefore 抽采系统截止某时间的抽采量
     * @return 结果
     */
    @Override
    public int updateTDrainagedataBefore(TDrainagedataBefore tDrainagedataBefore)
    {
        return tDrainagedataBeforeMapper.updateTDrainagedataBefore(tDrainagedataBefore);
    }

    /**
     * 批量删除抽采系统截止某时间的抽采量
     * 
     * @param ids 需要删除的抽采系统截止某时间的抽采量主键
     * @return 结果
     */
    @Override
    public int deleteTDrainagedataBeforeByIds(Integer[] ids)
    {
        return tDrainagedataBeforeMapper.deleteTDrainagedataBeforeByIds(ids);
    }

    /**
     * 删除抽采系统截止某时间的抽采量信息
     * 
     * @param id 抽采系统截止某时间的抽采量主键
     * @return 结果
     */
    @Override
    public int deleteTDrainagedataBeforeById(Integer id)
    {
        return tDrainagedataBeforeMapper.deleteTDrainagedataBeforeById(id);
    }
    /**
     * 根据测点安装位置获取列表
     * @param measurePointLocal
     * @return
     */
    @Override
    public TDrainagedataBefore getByMeasurePointLocal(String measurePointLocal) {
        return this.tDrainagedataBeforeMapper.getByMeasurePointLocal(measurePointLocal);
    }
}
