package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TRiskArea;

import java.util.List;

public interface ITRiskAreaService {
    /**
     * 查询区域
     *
     * @param id 区域主键
     * @return 区域
     */
    public TRiskArea selectTRiskAreaById(Long id);

    /**
     * 查询区域列表
     *
     * @param tRiskArea 区域
     * @return 区域集合
     */
    public List<TRiskArea> selectTRiskAreaList(TRiskArea tRiskArea);

    /**
     * 新增区域
     *
     * @param tRiskArea 区域
     * @return 结果
     */
    public int insertTRiskArea(TRiskArea tRiskArea);

    /**
     * 修改区域
     *
     * @param tRiskArea 区域
     * @return 结果
     */
    public int updateTRiskArea(TRiskArea tRiskArea);

    /**
     * 批量删除区域
     *
     * @param ids 需要删除的区域主键集合
     * @return 结果
     */
    public int deleteTRiskAreaByIds(Long[] ids);

    /**
     * 删除区域信息
     *
     * @param id 区域主键
     * @return 结果
     */
    public int deleteTRiskAreaById(Long id);

}
