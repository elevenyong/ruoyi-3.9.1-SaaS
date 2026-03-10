package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TSubArea;

import java.util.List;

public interface ITSubAreaService {
    /**
     * 查询区域
     *
     * @param id 区域主键
     * @return 区域
     */
    public TSubArea selectTSubAreaById(Long id);

    /**
     * 查询区域列表
     *
     * @param tSubArea 区域
     * @return 区域集合
     */
    public List<TSubArea> selectTSubAreaList(TSubArea tSubArea);

    /**
     * 新增区域
     *
     * @param tSubArea 区域
     * @return 结果
     */
    public int insertTSubArea(TSubArea tSubArea);

    /**
     * 修改区域
     *
     * @param tSubArea 区域
     * @return 结果
     */
    public int updateTSubArea(TSubArea tSubArea);

    /**
     * 批量删除区域
     *
     * @param ids 需要删除的区域主键集合
     * @return 结果
     */
    public int deleteTSubAreaByIds(Long[] ids);

    /**
     * 删除区域信息
     *
     * @param id 区域主键
     * @return 结果
     */
    public int deleteTSubAreaById(Long id);

}
