package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TWorkingFaceOfDriving;

import java.util.List;

public interface ITWorkingFaceOfDrivingService {
    /**
     * 查询掘进工作面
     *
     * @param id 掘进工作面主键
     * @return 掘进工作面
     */
    public TWorkingFaceOfDriving selectTWorkingFaceOfDrivingById(Integer id);

    /**
     * 查询掘进工作面列表
     *
     * @param tWorkingFaceOfDriving 掘进工作面
     * @return 掘进工作面集合
     */
    public List<TWorkingFaceOfDriving> selectTWorkingFaceOfDrivingList(TWorkingFaceOfDriving tWorkingFaceOfDriving);

    /**
     * 新增掘进工作面
     *
     * @param tWorkingFaceOfDriving 掘进工作面
     * @return 结果
     */
    public TWorkingFaceOfDriving insertTWorkingFaceOfDriving(TWorkingFaceOfDriving tWorkingFaceOfDriving);

    /**
     * 修改掘进工作面
     *
     * @param tWorkingFaceOfDriving 掘进工作面
     * @return 结果
     */
    public TWorkingFaceOfDriving updateTWorkingFaceOfDriving(TWorkingFaceOfDriving tWorkingFaceOfDriving);

    /**
     * 批量删除掘进工作面
     *
     * @param ids 需要删除的掘进工作面主键集合
     * @return 结果
     */
    public int deleteTWorkingFaceOfDrivingByIds(Integer[] ids);

    /**
     * 删除掘进工作面信息
     *
     * @param id 掘进工作面主键
     * @return 结果
     */
    public int deleteTWorkingFaceOfDrivingById(Integer id);
}
