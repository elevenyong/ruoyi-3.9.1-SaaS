package com.ruoyi.hh.physical.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.physical.domain.TActDirectionalBorehole;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 实际定向钻孔信息Service接口
 *
 * @author eleven
 * @date 2023-07-27
 */
public interface ITActDirectionalBoreholeService
{
    /**
     * 查询实际定向钻孔信息
     *
     * @param id 实际定向钻孔信息主键
     * @return 实际定向钻孔信息
     */
    public TActDirectionalBorehole selectTActDirectionalBoreholeById(Integer id);

    /**
     * 查询实际定向钻孔信息列表
     *
     * @param tActDirectionalBorehole 实际定向钻孔信息
     * @return 实际定向钻孔信息集合
     */
    public List<TActDirectionalBorehole> selectTActDirectionalBoreholeList(TActDirectionalBorehole tActDirectionalBorehole);

    /**
     * 新增实际定向钻孔信息
     *
     * @param tActDirectionalBorehole 实际定向钻孔信息
     * @return 结果
     */
    public int insertTActDirectionalBorehole(TActDirectionalBorehole tActDirectionalBorehole);

    /**
     * 修改实际定向钻孔信息
     *
     * @param tActDirectionalBorehole 实际定向钻孔信息
     * @return 结果
     */
    public int updateTActDirectionalBorehole(TActDirectionalBorehole tActDirectionalBorehole);

    /**
     * 批量删除实际定向钻孔信息
     *
     * @param ids 需要删除的实际定向钻孔信息主键集合
     * @return 结果
     */
    public int deleteTActDirectionalBoreholeByIds(Integer[] ids);

    /**
     * 删除实际定向钻孔信息信息
     *
     * @param id 实际定向钻孔信息主键
     * @return 结果
     */
    public int deleteTActDirectionalBoreholeById(Integer id);

    //AjaxResult insertBatchTDesignedBorehole(List<TActDirectionalBorehole> list);

    List<TActDirectionalBorehole> getActDirectionalBoreholeInfoByRoadwayId(Integer id);

    List<TActDirectionalBorehole> getUsedDirectionalByRoadwayId(Integer id);


    Integer getDirectBoreholeCount(Integer type, Integer roadwayId);
}
