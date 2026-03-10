package com.ruoyi.hh.physical.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.service.ITSubstationInfoService;
import org.springframework.stereotype.Service;
import com.ruoyi.hh.physical.mapper.TSubstationInfoMapper;
import com.ruoyi.hh.physical.domain.TSubstationInfo;

import javax.annotation.Resource;

/**
 * 分站信息Service业务层处理
 * 
 * @author eleven
 * @date 2023-05-16
 */
@Service
public class TSubstationInfoServiceImpl implements ITSubstationInfoService
{
    @Resource
    private TSubstationInfoMapper tSubstationInfoMapper;

    /**
     * 查询分站信息
     * 
     * @param id 分站信息主键
     * @return 分站信息
     */
    @Override
    public TSubstationInfo selectTSubstationInfoById(Integer id)
    {
        return tSubstationInfoMapper.selectTSubstationInfoById(id);
    }

    /**
     * 查询分站信息列表
     * 
     * @param tSubstationInfo 分站信息
     * @return 分站信息
     */
    @Override
    public List<TSubstationInfo> selectTSubstationInfoList(TSubstationInfo tSubstationInfo)
    {
        return tSubstationInfoMapper.selectTSubstationInfoList(tSubstationInfo);
    }

    /**
     * 新增分站信息
     * 
     * @param tSubstationInfo 分站信息
     * @return 结果
     */
    @Override
    public int insertTSubstationInfo(TSubstationInfo tSubstationInfo)
    {
        return tSubstationInfoMapper.insertTSubstationInfo(tSubstationInfo);
    }

    /**
     * 修改分站信息
     * 
     * @param tSubstationInfo 分站信息
     * @return 结果
     */
    @Override
    public int updateTSubstationInfo(TSubstationInfo tSubstationInfo)
    {
        return tSubstationInfoMapper.updateTSubstationInfo(tSubstationInfo);
    }

    /**
     * 批量删除分站信息
     * 
     * @param ids 需要删除的分站信息主键
     * @return 结果
     */
    @Override
    public int deleteTSubstationInfoByIds(Integer[] ids)
    {
        return tSubstationInfoMapper.deleteTSubstationInfoByIds(ids);
    }

    /**
     * 删除分站信息信息
     * 
     * @param id 分站信息主键
     * @return 结果
     */
    @Override
    public int deleteTSubstationInfoById(Integer id)
    {
        return tSubstationInfoMapper.deleteTSubstationInfoById(id);
    }

    /**
     * 根据分站编码查询分站信息
     * @param substationCode 分站编码
     * @return 分站信息
     */
    @Override
    public TSubstationInfo getBySubstationCode(String substationCode) {
        return this.tSubstationInfoMapper.getBySubstationCode(substationCode);
    }
    /**
     * 根据分站编码修改分站信息
     * @param tSubstationInfo 分站信息
     * @return 结果
     */
    @Override
    public int updateBySubstationCode(TSubstationInfo tSubstationInfo) {
        return this.tSubstationInfoMapper.updateBySubstationCode(tSubstationInfo);
    }
}
