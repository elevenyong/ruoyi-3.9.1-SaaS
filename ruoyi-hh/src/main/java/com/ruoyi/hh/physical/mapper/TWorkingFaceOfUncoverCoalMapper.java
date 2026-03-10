package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TWorkingFaceOfUncoverCoal;

import java.util.List;

/**
 * 揭煤工作面Mapper接口
 *
 * @author hhhz
 * @date 2023-05-04
 */
public interface TWorkingFaceOfUncoverCoalMapper {
    /**
     * 查询揭煤工作面
     *
     * @param id 揭煤工作面主键
     * @return 揭煤工作面
     */
    public TWorkingFaceOfUncoverCoal selectTWorkingFaceOfUncoverCoalById(Integer id);

    /**
     * 查询揭煤工作面列表
     *
     * @param tWorkingFaceOfUncoverCoal 揭煤工作面
     * @return 揭煤工作面集合
     */
    public List<TWorkingFaceOfUncoverCoal> selectTWorkingFaceOfUncoverCoalList(TWorkingFaceOfUncoverCoal tWorkingFaceOfUncoverCoal);

    /**
     * 新增揭煤工作面
     *
     * @param tWorkingFaceOfUncoverCoal 揭煤工作面
     * @return 结果
     */
    public int insertTWorkingFaceOfUncoverCoal(TWorkingFaceOfUncoverCoal tWorkingFaceOfUncoverCoal);

    /**
     * 修改揭煤工作面
     *
     * @param tWorkingFaceOfUncoverCoal 揭煤工作面
     * @return 结果
     */
    public int updateTWorkingFaceOfUncoverCoal(TWorkingFaceOfUncoverCoal tWorkingFaceOfUncoverCoal);

    /**
     * 删除揭煤工作面
     *
     * @param id 揭煤工作面主键
     * @return 结果
     */
    public int deleteTWorkingFaceOfUncoverCoalById(Integer id);

    /**
     * 批量删除揭煤工作面
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTWorkingFaceOfUncoverCoalByIds(Integer[] ids);
}
