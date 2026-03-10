package com.ruoyi.hh.util.service;

import com.ruoyi.hh.util.domain.TPurposeBorehole;

import java.util.List;

/**
 * 钻孔用途相关Service接口
 *
 * @author eleven
 * @date 2022-07-13
 */
public interface ITPurposeBoreholeService {
    /**
     * 查询钻孔用途相关
     *
     * @param id 钻孔用途相关主键
     * @return 钻孔用途相关
     */
    public TPurposeBorehole selectTPurposeBoreholeById(Integer id);

    /**
     * 查询钻孔用途相关列表
     *
     * @param tPurposeBorehole 钻孔用途相关
     * @return 钻孔用途相关集合
     */
    public List<TPurposeBorehole> selectTPurposeBoreholeList(TPurposeBorehole tPurposeBorehole);

    /**
     * 新增钻孔用途相关
     *
     * @param tPurposeBorehole 钻孔用途相关
     */
    public void insertTPurposeBorehole(TPurposeBorehole tPurposeBorehole);

    /**
     * 修改钻孔用途相关
     *
     * @param tPurposeBorehole 钻孔用途相关
     * @return 结果
     */
    public Integer updateTPurposeBorehole(TPurposeBorehole tPurposeBorehole);

    /**
     * 批量删除钻孔用途相关
     *
     * @param ids 需要删除的钻孔用途相关主键集合
     * @return 结果
     */
    public Integer deleteTPurposeBoreholeByIds(List<Integer> ids);

    /**
     * 删除钻孔用途相关信息
     *
     * @param id 钻孔用途相关主键
     * @return 结果
     */
    public Integer deleteTPurposeBoreholeById(Integer id);

    /**
     * 批量添加钻孔用途信息
     *
     * @param list
     */
    public void insertTPurposeBoreholeList(List<TPurposeBorehole> list);

    /**
     * 分页查询
     *
     * @return 列表
     */
    public List<TPurposeBorehole> getPageList();
    /**
     * 根据钻孔用途查询ID
     * @param purposeborehole 钻孔用途
     * @return ID
     */
    public Integer getIdByPurposeBorehole(String purposeborehole);

    /**
     * 获取钻孔用途
     * @return
     */
    public List<String> getPurposeBoreholeAll();

}
