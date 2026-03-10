package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TWorkingFaceOfDriving;

import java.util.List;

public interface TWorkingFaceOfDrivingMapper {
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
    public int insertTWorkingFaceOfDriving(TWorkingFaceOfDriving tWorkingFaceOfDriving);

    /**
     * 修改掘进工作面
     *
     * @param tWorkingFaceOfDriving 掘进工作面
     * @return 结果
     */
    public int updateTWorkingFaceOfDriving(TWorkingFaceOfDriving tWorkingFaceOfDriving);

    /**
     * 删除掘进工作面
     *
     * @param id 掘进工作面主键
     * @return 结果
     */
    public int deleteTWorkingFaceOfDrivingById(Integer id);

    /**
     * 批量删除掘进工作面
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTWorkingFaceOfDrivingByIds(Integer[] ids);

}
