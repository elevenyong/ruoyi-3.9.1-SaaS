package com.ruoyi.hh.util.service.impl;

import com.ruoyi.hh.util.domain.TCoalPointtype;
import com.ruoyi.hh.util.mapper.TCoalPointtypeMapper;
import com.ruoyi.hh.util.service.ITCoalPointtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-02
 */
@Service
public class TCoalPointtypeServiceImpl implements ITCoalPointtypeService {
    @Autowired
    private TCoalPointtypeMapper tCoalPointtypeMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    @Override
    public TCoalPointtype selectTCoalPointtypeById(Long id) {
        return tCoalPointtypeMapper.selectTCoalPointtypeById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param tCoalPointtype 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<TCoalPointtype> selectTCoalPointtypeList(TCoalPointtype tCoalPointtype) {
        return tCoalPointtypeMapper.selectTCoalPointtypeList(tCoalPointtype);
    }

    /**
     * 新增【请填写功能名称】
     *
     * @param tCoalPointtype 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertTCoalPointtype(TCoalPointtype tCoalPointtype) {
        return tCoalPointtypeMapper.insertTCoalPointtype(tCoalPointtype);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param tCoalPointtype 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateTCoalPointtype(TCoalPointtype tCoalPointtype) {
        return tCoalPointtypeMapper.updateTCoalPointtype(tCoalPointtype);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteTCoalPointtypeByIds(Long[] ids) {
        return tCoalPointtypeMapper.deleteTCoalPointtypeByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    @Override
    public int deleteTCoalPointtypeById(Long id) {
        return tCoalPointtypeMapper.deleteTCoalPointtypeById(id);
    }
}
