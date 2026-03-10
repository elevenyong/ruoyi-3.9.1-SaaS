package com.ruoyi.hh.physical.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.domain.TDesignedFlushing;
import com.ruoyi.hh.physical.mapper.TDesignedFlushingMapper;
import com.ruoyi.hh.physical.service.ITDesignedFlushingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 设计冲孔信息Service业务层处理
 * 
 * @author eleven
 * @date 2022-08-05
 */
@Service
public class TDesignedFlushingServiceImpl implements ITDesignedFlushingService
{
    @Resource
    private TDesignedFlushingMapper tDesignedFlushingMapper;

    /**
     * 查询设计冲孔信息
     * 
     * @param id 设计冲孔信息主键
     * @return 设计冲孔信息
     */
    @Override
    public TDesignedFlushing selectTDesignedFlushingById(Integer id)
    {
        return tDesignedFlushingMapper.selectTDesignedFlushingById(id);
    }

    /**
     * 查询设计冲孔信息列表
     * 
     * @param tDesignedFlushing 设计冲孔信息
     * @return 设计冲孔信息
     */
    @Override
    public List<TDesignedFlushing> selectTDesignedFlushingList(TDesignedFlushing tDesignedFlushing)
    {
        return tDesignedFlushingMapper.selectTDesignedFlushingList(tDesignedFlushing);
    }

    /**
     * 新增设计冲孔信息
     * 
     * @param tDesignedFlushing 设计冲孔信息
     * @return 结果
     */
    @Override
    public int insertTDesignedFlushing(TDesignedFlushing tDesignedFlushing)
    {
//        tDesignedFlushing.setCreateTime(DateUtils.getNowDate());
        return tDesignedFlushingMapper.insertTDesignedFlushing(tDesignedFlushing);
    }

    /**
     * 修改设计冲孔信息
     * 
     * @param tDesignedFlushing 设计冲孔信息
     * @return 结果
     */
    @Override
    public int updateTDesignedFlushing(TDesignedFlushing tDesignedFlushing)
    {
//        tDesignedFlushing.setUpdateTime(DateUtils.getNowDate());
        return tDesignedFlushingMapper.updateTDesignedFlushing(tDesignedFlushing);
    }

    /**
     * 批量删除设计冲孔信息
     * 
     * @param ids 需要删除的设计冲孔信息主键
     * @return 结果
     */
    @Override
    public int deleteTDesignedFlushingByIds(List<Integer> ids)
    {
        return tDesignedFlushingMapper.deleteTDesignedFlushingByIds(ids);
    }

    /**
     * 删除设计冲孔信息信息
     * 
     * @param id 设计冲孔信息主键
     * @return 结果
     */
    @Override
    public int deleteTDesignedFlushingById(Integer id)
    {
        return tDesignedFlushingMapper.deleteTDesignedFlushingById(id);
    }

    /**
     * 根据设计钻孔ID删除设计冲孔信息
     * @param boreholeIds 钻孔ID
     * @return 结果
     */
    @Override
    public Integer delByBoreholeIds(List<Integer> boreholeIds) {
        return this.tDesignedFlushingMapper.delByBoreholeIds(boreholeIds);
    }

    /**
     * 根据设计钻孔ID删除设计冲孔信息
     * @param boreholeId 钻孔ID
     * @return 结果
     */
    @Override
    public Integer delByBoreholeId(Integer boreholeId) {
        return this.delByBoreholeId(boreholeId);
    }

    @Override
    public Integer delAll() {
        return this.tDesignedFlushingMapper.delAll();
    }

    @Override
    public List<TDesignedFlushing> getPageList() {
        return this.tDesignedFlushingMapper.getPageList();
    }
}
