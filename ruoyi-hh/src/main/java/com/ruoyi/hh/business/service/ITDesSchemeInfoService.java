package com.ruoyi.hh.business.service;

import com.ruoyi.hh.business.domain.TDesSchemeInfo;
import com.ruoyi.hh.hhhz.domain.param.DesignParameters;

import java.util.List;

/**
 * 设计方案信息Service接口
 *
 * @author eleven
 * @date 2023-06-30
 */
public interface ITDesSchemeInfoService
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
     * 批量删除设计方案信息
     *
     * @param ids 需要删除的设计方案信息主键集合
     * @return 结果
     */
    public int deleteTDesSchemeInfoByIds(Integer[] ids);

    /**
     * 删除设计方案信息信息
     *
     * @param id 设计方案信息主键
     * @return 结果
     */
    public int deleteTDesSchemeInfoById(Integer id);

    /**
     * 根据方案ID查询设计参数列表
     * @param id 方案ID
     * @return
     */
    public List<DesignParameters> getByDesSchemeId(Integer id);
    /**
     * 确定方案
     * @param id 方案ID
     */
    public void masureDesScheme(Integer id);
    /**
     * 设计参数追加方案ID
     * @param designParameters
     * @param schemeId
     * @return
     */
    public DesignParameters appendSchemeId(DesignParameters designParameters,Integer schemeId);

    Integer checkExistChooseScheme(Integer roadwayId);
}
