package com.ruoyi.hh.physical.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.domain.TDirectedholeData;
import com.ruoyi.hh.physical.mapper.TDirectedholeDataMapper;
import com.ruoyi.hh.physical.service.ITDirectedholeDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 设计定向钻数据Service业务层处理
 * 
 * @author eleven
 * @date 2023-03-06
 */
@Service
public class TDirectedholeDataServiceImpl implements ITDirectedholeDataService
{
    @Resource
    private TDirectedholeDataMapper tDirectedholeDataMapper;

    /**
     * 查询设计定向钻数据
     * 
     * @param id 设计定向钻数据主键
     * @return 设计定向钻数据
     */
    @Override
    public TDirectedholeData selectTDirectedholeDataById(Integer id)
    {
        return tDirectedholeDataMapper.selectTDirectedholeDataById(id);
    }

    /**
     * 查询设计定向钻数据列表
     * 
     * @param tDirectedholeData 设计定向钻数据
     * @return 设计定向钻数据
     */
    @Override
    public List<TDirectedholeData> selectTDirectedholeDataList(TDirectedholeData tDirectedholeData)
    {
        return tDirectedholeDataMapper.selectTDirectedholeDataList(tDirectedholeData);
    }

    /**
     * 新增设计定向钻数据
     * 
     * @param tDirectedholeData 设计定向钻数据
     * @return 结果
     */
    @Override
    public int insertTDirectedholeData(TDirectedholeData tDirectedholeData)
    {
        return tDirectedholeDataMapper.insertTDirectedholeData(tDirectedholeData);
    }

    /**
     * 修改设计定向钻数据
     * 
     * @param tDirectedholeData 设计定向钻数据
     * @return 结果
     */
    @Override
    public int updateTDirectedholeData(TDirectedholeData tDirectedholeData)
    {
        return tDirectedholeDataMapper.updateTDirectedholeData(tDirectedholeData);
    }

    /**
     * 批量删除设计定向钻数据
     * 
     * @param ids 需要删除的设计定向钻数据主键
     * @return 结果
     */
    @Override
    public int deleteTDirectedholeDataByIds(Integer[] ids)
    {
        return tDirectedholeDataMapper.deleteTDirectedholeDataByIds(ids);
    }

    /**
     * 删除设计定向钻数据信息
     * 
     * @param id 设计定向钻数据主键
     * @return 结果
     */
    @Override
    public int deleteTDirectedholeDataById(Integer id)
    {
        return tDirectedholeDataMapper.deleteTDirectedholeDataById(id);
    }
}
