package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TWarningActions;

/**
 * actionsMapper接口
 *
 * @author hhhz
 * @date 2022-12-23
 */
public interface TWarningActionsMapper
{
    /**
     * 查询actions
     *
     * @param id actions主键
     * @return actions
     */
    public TWarningActions selectTWarningActionsById(Integer id);

    /**
     * 查询actions列表
     *
     * @param tWarningActions actions
     * @return actions集合
     */
    public List<TWarningActions> selectTWarningActionsList(TWarningActions tWarningActions);

    /**
     * 新增actions
     *
     * @param tWarningActions actions
     * @return 结果
     */
    public int insertTWarningActions(TWarningActions tWarningActions);

    /**
     * 修改actions
     *
     * @param tWarningActions actions
     * @return 结果
     */
    public int updateTWarningActions(TWarningActions tWarningActions);

    /**
     * 删除actions
     *
     * @param id actions主键
     * @return 结果
     */
    public int deleteTWarningActionsById(Integer id);

    /**
     * 批量删除actions
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTWarningActionsByIds(Integer[] ids);
}
