package com.ruoyi.hh.physical.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.physical.domain.TCoalPoint;
import com.ruoyi.hh.physical.mapper.TCoalPointMapper;
import com.ruoyi.hh.physical.service.ITCoalPointService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 煤层对应的坐标点信息Service业务层处理
 *
 * @author eleven
 * @date 2022-10-20
 */
@Service
public class TCoalPointServiceImpl implements ITCoalPointService {
    @Resource
    private TCoalPointMapper tCoalPointMapper;

    /**
     * 查询煤层对应的坐标点信息
     *
     * @param id 煤层对应的坐标点信息主键
     * @return 煤层对应的坐标点信息
     */
    @Override
    public TCoalPoint selectTCoalPointById(Integer id)
    {
        return tCoalPointMapper.selectTCoalPointById(id);
    }

    /**
     * 查询煤层对应的坐标点信息列表
     *
     * @param TCoalPoint 煤层对应的坐标点信息
     * @return 煤层对应的坐标点信息
     */
    @Override
    public List<TCoalPoint> selectTCoalPointList(TCoalPoint TCoalPoint)
    {
        return tCoalPointMapper.selectTCoalPointList(TCoalPoint);
    }

    /**
     * 新增煤层对应的坐标点信息
     *
     * @param TCoalPoint 煤层对应的坐标点信息
     * @return 结果
     */
    @Override
    public int insertTCoalPoint(TCoalPoint TCoalPoint)
    {
        TCoalPoint.setCreateTime(DateUtils.getNowDate());
        return tCoalPointMapper.insertTCoalPoint(TCoalPoint);
    }

    /**
     * 修改煤层对应的坐标点信息
     *
     * @param TCoalPoint 煤层对应的坐标点信息
     * @return 结果
     */
    @Override
    public int updateTCoalPoint(TCoalPoint TCoalPoint)
    {
        TCoalPoint.setUpdateTime(DateUtils.getNowDate());
        return tCoalPointMapper.updateTCoalPoint(TCoalPoint);
    }

    /**
     * 批量删除煤层对应的坐标点信息
     *
     * @param ids 需要删除的煤层对应的坐标点信息主键
     * @return 结果
     */
    @Override
    public int deleteTCoalPointByIds(Integer[] ids)
    {
        return tCoalPointMapper.deleteTCoalPointByIds(ids);
    }

    /**
     * 删除煤层对应的坐标点信息信息
     *
     * @param id 煤层对应的坐标点信息主键
     * @return 结果
     */
    @Override
    public int deleteTCoalPointById(Integer id)
    {
        return tCoalPointMapper.deleteTCoalPointById(id);
    }

    @Override
    public int deleteTCoalPointByCoalinfoId(Integer coalInfoId) {
        return tCoalPointMapper.deleteTCoalPointByCoalInfoId(coalInfoId);
    }
}
