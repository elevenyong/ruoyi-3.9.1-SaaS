package com.ruoyi.hh.util.service.impl;

import com.ruoyi.hh.util.domain.TPurposeBorehole;
import com.ruoyi.hh.util.mapper.TPurposeBoreholeMapper;
import com.ruoyi.hh.util.service.ITPurposeBoreholeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 钻孔用途相关Service业务层处理
 *
 * @author eleven
 * @date 2022-07-13
 */
@Service
public class TPurposeBoreholeServiceImpl implements ITPurposeBoreholeService {
    @Resource
    private TPurposeBoreholeMapper tPurposeBoreholeMapper;

    /**
     * 查询钻孔用途相关
     *
     * @param id 钻孔用途相关主键
     * @return 钻孔用途相关
     */
    @Override
    public TPurposeBorehole selectTPurposeBoreholeById(Integer id) {
        return tPurposeBoreholeMapper.selectTPurposeBoreholeById(id);
    }

    /**
     * 查询钻孔用途相关列表
     *
     * @param tPurposeBorehole 钻孔用途相关
     * @return 钻孔用途相关
     */
    @Override
    public List<TPurposeBorehole> selectTPurposeBoreholeList(TPurposeBorehole tPurposeBorehole) {
        return tPurposeBoreholeMapper.selectTPurposeBoreholeList(tPurposeBorehole);
    }

    /**
     * 新增钻孔用途相关
     *
     * @param tPurposeBorehole 钻孔用途相关
     */
    @Override
    public void insertTPurposeBorehole(TPurposeBorehole tPurposeBorehole) {
        tPurposeBoreholeMapper.insertTPurposeBorehole(tPurposeBorehole);
    }

    /**
     * 修改钻孔用途相关
     *
     * @param tPurposeBorehole 钻孔用途相关
     * @return 结果
     */
    @Override
    public Integer updateTPurposeBorehole(TPurposeBorehole tPurposeBorehole) {
        return tPurposeBoreholeMapper.updateTPurposeBorehole(tPurposeBorehole);
    }

    /**
     * 批量删除钻孔用途相关
     *
     * @param ids 需要删除的钻孔用途相关主键
     * @return 结果
     */
    @Override
    public Integer deleteTPurposeBoreholeByIds(List<Integer> ids) {
        return tPurposeBoreholeMapper.deleteTPurposeBoreholeByIds(ids);
    }

    /**
     * 删除钻孔用途相关信息
     *
     * @param id 钻孔用途相关主键
     * @return 结果
     */
    @Override
    public Integer deleteTPurposeBoreholeById(Integer id) {
        return tPurposeBoreholeMapper.deleteTPurposeBoreholeById(id);
    }

    /**
     * 批量添加钻孔用途信息
     *
     * @param list
     */
    @Override
    public void insertTPurposeBoreholeList(List<TPurposeBorehole> list) {
        this.tPurposeBoreholeMapper.insertTPurposeBoreholeList(list);
    }

    @Override
    public List<TPurposeBorehole> getPageList() {
        return this.tPurposeBoreholeMapper.getPageList();
    }

    /**
     * 根据钻孔用途查询ID
     * @param purposeborehole 钻孔用途
     * @return ID
     */
    @Override
    public Integer getIdByPurposeBorehole(String purposeborehole) {
        return this.tPurposeBoreholeMapper.getIdByPurposeBorehole(purposeborehole);
    }
    /**
     * 获取钻孔用途
     * @return
     */
    @Override
    public List<String> getPurposeBoreholeAll() {
        return this.tPurposeBoreholeMapper.getPurposeBoreholeAll();
    }
}
