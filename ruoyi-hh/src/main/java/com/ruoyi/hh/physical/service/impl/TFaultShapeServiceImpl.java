package com.ruoyi.hh.physical.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.domain.TFaultShape;
import com.ruoyi.hh.physical.mapper.TFaultShapeMapper;
import com.ruoyi.hh.physical.service.ITFaultShapeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 断层信息表Service业务层处理
 *
 * @author eleven
 * @date 2022-07-18
 */
@Service
public class TFaultShapeServiceImpl implements ITFaultShapeService {
    @Resource
    private TFaultShapeMapper tFaultShapeMapper;

    /**
     * 查询断层信息表
     *
     * @param id 断层信息表主键
     * @return 断层信息表
     */
    @Override
    public TFaultShape selectTFaultShapeById(Integer id) {
        return tFaultShapeMapper.selectTFaultShapeById(id);
    }

    /**
     * 查询断层信息表列表
     *
     * @param tFaultShape 断层信息表
     * @return 断层信息表
     */
    @Override
    public List<TFaultShape> selectTFaultShapeList(TFaultShape tFaultShape) {
        return tFaultShapeMapper.selectTFaultShapeList(tFaultShape);
    }

    /**
     * 新增断层信息表
     *
     * @param tFaultShape 断层信息表
     * @return 结果
     */
    @Override
    public Integer insertTFaultShape(TFaultShape tFaultShape) {
        return tFaultShapeMapper.insertTFaultShape(tFaultShape);
    }

    /**
     * 修改断层信息表
     *
     * @param tFaultShape 断层信息表
     * @return 结果
     */
    @Override
    public Integer updateTFaultShape(TFaultShape tFaultShape) {
        return tFaultShapeMapper.updateTFaultShape(tFaultShape);
    }

    /**
     * 批量删除断层信息表
     *
     * @param ids 需要删除的断层信息表主键
     * @return 结果
     */
    @Override
    public Integer deleteTFaultShapeByIds(List<Integer> ids) {
        return tFaultShapeMapper.deleteTFaultShapeByIds(ids);
    }

    /**
     * 删除断层信息表信息
     *
     * @param id 断层信息表主键
     * @return 结果
     */
    @Override
    public Integer deleteTFaultShapeById(Integer id) {
        return tFaultShapeMapper.deleteTFaultShapeById(id);
    }

    /**
     * 获取列表
     *
     * @return 列表
     */
    @Override
    public List<TFaultShape> getPageList() {
        return this.tFaultShapeMapper.selectTFaultShapeVo();
    }

    /**
     * 根据断层名称查询断层ID
     * @param shape 断层名称
     * @return ID
     */
    @Override
    public Integer getIdByShape(String shape) {
        return this.tFaultShapeMapper.getIdByShape(shape);
    }
}
