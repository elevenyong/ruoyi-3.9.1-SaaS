package com.ruoyi.hh.physical.attribute.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.attribute.domain.TBZKYL;
import com.ruoyi.hh.physical.attribute.mapper.TBZKYLMapper;
import com.ruoyi.hh.physical.attribute.service.ITBZKYLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 钻孔应力测点基本信息Service业务层处理
 * 
 * @author eleven
 * @date 2024-11-12
 */
@Service
public class TBZKYLServiceImpl implements ITBZKYLService
{
    @Resource
    private TBZKYLMapper tBZKYLMapper;

    /**
     * 查询钻孔应力测点基本信息
     * 
     * @param id 钻孔应力测点基本信息主键
     * @return 钻孔应力测点基本信息
     */
    @Override
    public TBZKYL selectTBZKYLById(Integer id)
    {
        return tBZKYLMapper.selectTBZKYLById(id);
    }

    /**
     * 查询钻孔应力测点基本信息列表
     * 
     * @param tBZKYL 钻孔应力测点基本信息
     * @return 钻孔应力测点基本信息
     */
    @Override
    public List<TBZKYL> selectTBZKYLList(TBZKYL tBZKYL)
    {
        return tBZKYLMapper.selectTBZKYLList(tBZKYL);
    }

    /**
     * 新增钻孔应力测点基本信息
     * 
     * @param tBZKYL 钻孔应力测点基本信息
     * @return 结果
     */
    @Override
    public int insertTBZKYL(TBZKYL tBZKYL)
    {
        return tBZKYLMapper.insertTBZKYL(tBZKYL);
    }

    /**
     * 修改钻孔应力测点基本信息
     * 
     * @param tBZKYL 钻孔应力测点基本信息
     * @return 结果
     */
    @Override
    public int updateTBZKYL(TBZKYL tBZKYL)
    {
        return tBZKYLMapper.updateTBZKYL(tBZKYL);
    }

    /**
     * 批量删除钻孔应力测点基本信息
     * 
     * @param ids 需要删除的钻孔应力测点基本信息主键
     * @return 结果
     */
    @Override
    public int deleteTBZKYLByIds(Integer[] ids)
    {
        return tBZKYLMapper.deleteTBZKYLByIds(ids);
    }

    /**
     * 删除钻孔应力测点基本信息信息
     * 
     * @param id 钻孔应力测点基本信息主键
     * @return 结果
     */
    @Override
    public int deleteTBZKYLById(Integer id)
    {
        return tBZKYLMapper.deleteTBZKYLById(id);
    }

    /**
     * 根据测点编号和测点类型名称获取数据
     * @param measurePointCode 测点编码
     * @param sensorTypeName 传感器类型
     * @return
     */
    @Override
    public TBZKYL getByMeasurePointCode(String measurePointCode, String sensorTypeName) {
        return tBZKYLMapper.getByMeasurePointCode(measurePointCode,sensorTypeName);
    }
}
