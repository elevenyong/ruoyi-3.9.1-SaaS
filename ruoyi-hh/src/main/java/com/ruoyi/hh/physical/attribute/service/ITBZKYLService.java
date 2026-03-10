package com.ruoyi.hh.physical.attribute.service;

import com.ruoyi.hh.physical.attribute.domain.TBZKYL;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 钻孔应力测点基本信息Service接口
 * 
 * @author eleven
 * @date 2024-11-12
 */
public interface ITBZKYLService 
{
    /**
     * 查询钻孔应力测点基本信息
     * 
     * @param id 钻孔应力测点基本信息主键
     * @return 钻孔应力测点基本信息
     */
    public TBZKYL selectTBZKYLById(Integer id);

    /**
     * 查询钻孔应力测点基本信息列表
     * 
     * @param tBZKYL 钻孔应力测点基本信息
     * @return 钻孔应力测点基本信息集合
     */
    public List<TBZKYL> selectTBZKYLList(TBZKYL tBZKYL);

    /**
     * 新增钻孔应力测点基本信息
     * 
     * @param tBZKYL 钻孔应力测点基本信息
     * @return 结果
     */
    public int insertTBZKYL(TBZKYL tBZKYL);

    /**
     * 修改钻孔应力测点基本信息
     * 
     * @param tBZKYL 钻孔应力测点基本信息
     * @return 结果
     */
    public int updateTBZKYL(TBZKYL tBZKYL);

    /**
     * 批量删除钻孔应力测点基本信息
     * 
     * @param ids 需要删除的钻孔应力测点基本信息主键集合
     * @return 结果
     */
    public int deleteTBZKYLByIds(Integer[] ids);

    /**
     * 删除钻孔应力测点基本信息信息
     * 
     * @param id 钻孔应力测点基本信息主键
     * @return 结果
     */
    public int deleteTBZKYLById(Integer id);
    /**
     * 根据测点编码和传感器类型查询测点信息
     * @param measurePointCode 测点编码
     * @param sensorTypeName 传感器类型名称
     * @return
     */
    public TBZKYL getByMeasurePointCode(String measurePointCode,String sensorTypeName);
}
