package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.physical.domain.TGroundElevation;

import java.util.List;

/**
 * 地面高程点信息Service接口
 *
 * @author eleven
 * @date 2022-10-20
 */
public interface ITGroundElevationService
{
    /**
     * 查询地面高程点信息
     *
     * @param id 地面高程点信息主键
     * @return 地面高程点信息
     */
    public TGroundElevation selectTGroundElevationById(Integer id);

    /**
     * 查询地面高程点信息列表
     *
     * @param TGroundElevation 地面高程点信息
     * @return 地面高程点信息集合
     */
    public List<TGroundElevation> selectTGroundElevationList(TGroundElevation TGroundElevation);

    /**
     * 新增地面高程点信息
     *
     * @param TGroundElevation 地面高程点信息
     * @return 结果
     */
    public int insertTGroundElevation(TGroundElevation TGroundElevation);

    /**
     * 批量插入地面高程点信息
     *
     * @param tGroundElevationList 地面高程点信息列表
     * @return 结果
     */
    public int insertList(List<TGroundElevation> tGroundElevationList);

    /**
     * 修改地面高程点信息
     *
     * @param TGroundElevation 地面高程点信息
     * @return 结果
     */
    public int updateTGroundElevation(TGroundElevation TGroundElevation);

    /**
     * 批量删除地面高程点信息
     *
     * @param ids 需要删除的地面高程点信息主键集合
     * @return 结果
     */
    public int deleteTGroundElevationByIds(Integer[] ids);

    /**
     * 删除地面高程点信息信息
     *
     * @param id 地面高程点信息主键
     * @return 结果
     */
    public int deleteTGroundElevationById(Integer id);

    int deleteTGroundElevationByWorkfaceId(Integer workfaceId);
}
