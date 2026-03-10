package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TRocktype;
import com.ruoyi.hh.physical.mapper.TRocktypeMapper;
import com.ruoyi.hh.physical.service.ITRocktypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 岩层类型Service业务层处理
 *
 * @author hhhz
 * @date 2023-05-05
 */
@Service
public class TRocktypeServiceImpl implements ITRocktypeService {
    @Resource
    private TRocktypeMapper tRocktypeMapper;

    /**
     * 查询岩层类型
     *
     * @param id 岩层类型主键
     * @return 岩层类型
     */
    @Override
    public TRocktype selectTRocktypeById(Integer id) {
        return tRocktypeMapper.selectTRocktypeById(id);
    }

    /**
     * 查询岩层类型列表
     *
     * @param tRocktype 岩层类型
     * @return 岩层类型
     */
    @Override
    public List<TRocktype> selectTRocktypeList(TRocktype tRocktype) {
        return tRocktypeMapper.selectTRocktypeList(tRocktype);
    }

    /**
     * 新增岩层类型
     *
     * @param tRocktype 岩层类型
     * @return 结果
     */
    @Override
    public int insertTRocktype(TRocktype tRocktype) {
        return tRocktypeMapper.insertTRocktype(tRocktype);
    }

    /**
     * 修改岩层类型
     *
     * @param tRocktype 岩层类型
     * @return 结果
     */
    @Override
    public int updateTRocktype(TRocktype tRocktype) {
        return tRocktypeMapper.updateTRocktype(tRocktype);
    }

    /**
     * 批量删除岩层类型
     *
     * @param ids 需要删除的岩层类型主键
     * @return 结果
     */
    @Override
    public int deleteTRocktypeByIds(Integer[] ids) {
        return tRocktypeMapper.deleteTRocktypeByIds(ids);
    }

    /**
     * 删除岩层类型信息
     *
     * @param id 岩层类型主键
     * @return 结果
     */
    @Override
    public int deleteTRocktypeById(Integer id) {
        return tRocktypeMapper.deleteTRocktypeById(id);
    }
}
