package com.ruoyi.hh.demo.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.TGeoFault;

/**
 * 断层Service接口
 *
 * @author hhhz
 * @date 2022-10-28
 */
public interface ITGeoFaultService
{
    /**
     * 查询断层
     *
     * @param id 断层主键
     * @return 断层
     */
    public TGeoFault selectTGeoFaultById(Integer id);

    /**
     * 查询断层列表
     *
     * @param TGeoFault 断层
     * @return 断层集合
     */
    public List<TGeoFault> selectTGeoFaultList(TGeoFault TGeoFault);

    /**
     * 新增断层
     *
     * @param TGeoFault 断层
     * @return 结果
     */
    public AjaxResult insertTGeoFault(TGeoFault TGeoFault);

    /**
     * 修改断层
     *
     * @param TGeoFault 断层
     * @return 结果
     */
    public AjaxResult updateTGeoFault(TGeoFault TGeoFault);

    /**
     * 批量删除断层
     *
     * @param ids 需要删除的断层主键集合
     * @return 结果
     */
    public int deleteTGeoFaultByIds(Integer[] ids);

    /**
     * 删除断层信息
     *
     * @param id 断层主键
     * @return 结果
     */
    public int deleteTGeoFaultById(Integer id);
}
