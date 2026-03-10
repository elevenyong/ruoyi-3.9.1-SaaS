package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TCoalPoint;
import com.ruoyi.hh.physical.domain.TSubArea;
import com.ruoyi.hh.physical.domain.TSubArea;
import com.ruoyi.hh.physical.mapper.TCoalPointMapper;
import com.ruoyi.hh.physical.mapper.TSubAreaMapper;
import com.ruoyi.hh.physical.service.ITSubAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class TSubAreaServiceImpl implements ITSubAreaService {
    @Resource
    private TSubAreaMapper tSubAreaMapper;

    /**
     * 查询子区域
     *
     * @param id 子区域主键
     * @return 子区域
     */
    @Override
    public TSubArea selectTSubAreaById(Long id) {
        return tSubAreaMapper.selectTSubAreaById(id);
    }

    /**
     * 查询子区域列表
     *
     * @param tSubArea 子区域
     * @return 子区域
     */
    @Override
    public List<TSubArea> selectTSubAreaList(TSubArea tSubArea) {
        return tSubAreaMapper.selectTSubAreaList(tSubArea);
    }

    /**
     * 新增子区域
     *
     * @param tSubArea 子区域
     * @return 结果
     */
    @Override
    public int insertTSubArea(TSubArea tSubArea) {
        return tSubAreaMapper.insertTSubArea(tSubArea);
    }

    /**
     * 修改子区域
     *
     * @param tSubArea 子区域
     * @return 结果
     */
    @Override
    public int updateTSubArea(TSubArea tSubArea) {
        return tSubAreaMapper.updateTSubArea(tSubArea);
    }

    /**
     * 批量删除子区域
     *
     * @param ids 需要删除的子区域主键
     * @return 结果
     */
    @Override
    public int deleteTSubAreaByIds(Long[] ids) {
        return tSubAreaMapper.deleteTSubAreaByIds(ids);
    }

    /**
     * 删除子区域信息
     *
     * @param id 子区域主键
     * @return 结果
     */
    @Override
    public int deleteTSubAreaById(Long id) {
        return tSubAreaMapper.deleteTSubAreaById(id);
    }

}
