package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.physical.domain.TRockinfo;
import com.ruoyi.hh.physical.domain.TRocktype;
import com.ruoyi.hh.physical.mapper.TRockinfoMapper;
import com.ruoyi.hh.physical.service.ITRockinfoService;
import com.ruoyi.hh.physical.mapper.TRocktypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 岩层信息Service业务层处理
 *
 * @author hhhz
 * @date 2023-05-05
 */
@Service
public class TRockinfoServiceImpl implements ITRockinfoService {
    @Resource
    private TRockinfoMapper tRockinfoMapper;

    @Resource
    private TRocktypeMapper rocktypeMapper;

    /**
     * 查询岩层信息
     *
     * @param id 岩层信息主键
     * @return 岩层信息
     */
    @Override
    public TRockinfo selectTRockinfoById(Integer id) {
        return tRockinfoMapper.selectTRockinfoById(id);
    }

    /**
     * 查询岩层信息列表
     *
     * @param tRockinfo 岩层信息
     * @return 岩层信息
     */
    @Override
    public List<TRockinfo> selectTRockinfoList(TRockinfo tRockinfo) {
        List<TRockinfo> tRockinfos = tRockinfoMapper.selectTRockinfoList(tRockinfo);
        for (TRockinfo rockinfo : tRockinfos) {
            TRocktype type = rocktypeMapper.selectTRocktypeById(rockinfo.getRockType());
            rockinfo.setRockTypeData(type);
        }
        Collections.sort(tRockinfos);
        return tRockinfos;
    }

    /**
     * 新增岩层信息
     *
     * @param tRockinfo 岩层信息
     * @return 结果
     */
    @Override
    public int insertTRockinfo(TRockinfo tRockinfo) {
        tRockinfo.setCreateTime(DateUtils.getNowDate());
        return tRockinfoMapper.insertTRockinfo(tRockinfo);
    }

    /**
     * 修改岩层信息
     *
     * @param tRockinfo 岩层信息
     * @return 结果
     */
    @Override
    public int updateTRockinfo(TRockinfo tRockinfo) {
        tRockinfo.setUpdateTime(DateUtils.getNowDate());
        return tRockinfoMapper.updateTRockinfo(tRockinfo);
    }

    /**
     * 批量删除岩层信息
     *
     * @param ids 需要删除的岩层信息主键
     * @return 结果
     */
    @Override
    public int deleteTRockinfoByIds(Integer[] ids) {
        return tRockinfoMapper.deleteTRockinfoByIds(ids);
    }

    /**
     * 删除岩层信息信息
     *
     * @param id 岩层信息主键
     * @return 结果
     */
    @Override
    public int deleteTRockinfoById(Integer id) {
        return tRockinfoMapper.deleteTRockinfoById(id);
    }
}
