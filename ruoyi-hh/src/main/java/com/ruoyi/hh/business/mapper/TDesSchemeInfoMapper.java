package com.ruoyi.hh.business.mapper;

import com.ruoyi.hh.business.domain.TDesSchemeInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 设计方案信息Mapper接口
 * 
 * @author eleven
 * @date 2023-06-30
 */
public interface TDesSchemeInfoMapper 
{
    /**
     * 查询设计方案信息
     * 
     * @param id 设计方案信息主键
     * @return 设计方案信息
     */
    public TDesSchemeInfo selectTDesSchemeInfoById(Integer id);

    /**
     * 查询设计方案信息列表
     * 
     * @param tDesSchemeInfo 设计方案信息
     * @return 设计方案信息集合
     */
    public List<TDesSchemeInfo> selectTDesSchemeInfoList(TDesSchemeInfo tDesSchemeInfo);

    /**
     * 新增设计方案信息
     * 
     * @param tDesSchemeInfo 设计方案信息
     * @return 结果
     */
    public int insertTDesSchemeInfo(TDesSchemeInfo tDesSchemeInfo);

    /**
     * 修改设计方案信息
     * 
     * @param tDesSchemeInfo 设计方案信息
     * @return 结果
     */
    public int updateTDesSchemeInfo(TDesSchemeInfo tDesSchemeInfo);

    /**
     * 删除设计方案信息
     * 
     * @param id 设计方案信息主键
     * @return 结果
     */
    public int deleteTDesSchemeInfoById(Integer id);

    /**
     * 批量删除设计方案信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDesSchemeInfoByIds(Integer[] ids);

    /**
     * 根据工作面ID查询设计方案
     * @param workfaceId
     * @return
     */
    @Select("select * from t_des_scheme_info where workface_id =#{workfaceId}")
    public List<TDesSchemeInfo> getByWorkfaceId(Integer workfaceId);
}
