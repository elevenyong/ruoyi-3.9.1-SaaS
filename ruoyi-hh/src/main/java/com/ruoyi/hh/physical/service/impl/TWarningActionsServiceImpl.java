package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TWarningActions;
import com.ruoyi.hh.physical.mapper.TWarningActionsMapper;
import com.ruoyi.hh.physical.service.ITWarningActionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * actionsService业务层处理
 *
 * @author hhhz
 * @date 2022-12-23
 */
@Service
public class TWarningActionsServiceImpl implements ITWarningActionsService {
    @Resource
    private TWarningActionsMapper tWarningActionsMapper;

    /**
     * 查询actions
     *
     * @param id actions主键
     * @return actions
     */
    @Override
    public TWarningActions selectTWarningActionsById(Integer id) {
        return tWarningActionsMapper.selectTWarningActionsById(id);
    }

    /**
     * 查询actions列表
     *
     * @param tWarningActions actions
     * @return actions
     */
    @Override
    public List<TWarningActions> selectTWarningActionsList(TWarningActions tWarningActions) {
        return tWarningActionsMapper.selectTWarningActionsList(tWarningActions);
    }

    /**
     * 新增actions
     *
     * @param tWarningActions actions
     * @return 结果
     */
    @Override
    public int insertTWarningActions(TWarningActions tWarningActions) {
        return tWarningActionsMapper.insertTWarningActions(tWarningActions);
    }

    /**
     * 修改actions
     *
     * @param tWarningActions actions
     * @return 结果
     */
    @Override
    public int updateTWarningActions(TWarningActions tWarningActions) {
        return tWarningActionsMapper.updateTWarningActions(tWarningActions);
    }

    /**
     * 批量删除actions
     *
     * @param ids 需要删除的actions主键
     * @return 结果
     */
    @Override
    public int deleteTWarningActionsByIds(Integer[] ids) {
        return tWarningActionsMapper.deleteTWarningActionsByIds(ids);
    }

    /**
     * 删除actions信息
     *
     * @param id actions主键
     * @return 结果
     */
    @Override
    public int deleteTWarningActionsById(Integer id) {
        return tWarningActionsMapper.deleteTWarningActionsById(id);
    }
}
