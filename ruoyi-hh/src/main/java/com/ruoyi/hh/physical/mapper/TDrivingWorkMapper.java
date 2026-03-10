package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TDrivingWork;

import java.util.List;

public interface TDrivingWorkMapper {
    /**
     * 查询掘进工作
     *
     * @param id 掘进工作主键
     * @return 掘进工作
     */
    public TDrivingWork selectTDrivingWorkById(Integer id);

    /**
     * 查询掘进工作列表
     *
     * @param tDrivingWork 掘进工作
     * @return 掘进工作集合
     */
    public List<TDrivingWork> selectTDrivingWorkList(TDrivingWork tDrivingWork);

    /**
     * 新增掘进工作
     *
     * @param tDrivingWork 掘进工作
     * @return 结果
     */
    public int insertTDrivingWork(TDrivingWork tDrivingWork);

    /**
     * 修改掘进工作
     *
     * @param tDrivingWork 掘进工作
     * @return 结果
     */
    public int updateTDrivingWork(TDrivingWork tDrivingWork);

    /**
     * 删除掘进工作
     *
     * @param id 掘进工作主键
     * @return 结果
     */
    public int deleteTDrivingWorkById(Integer id);

    /**
     * 批量删除掘进工作
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDrivingWorkByIds(Integer[] ids);

}
