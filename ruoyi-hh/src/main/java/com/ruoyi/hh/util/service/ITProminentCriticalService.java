package com.ruoyi.hh.util.service;

import java.util.List;

import com.ruoyi.hh.util.domain.TProminentCritical;

/**
 * 突出临界值Service接口
 *
 * @author eleven
 * @date 2022-07-18
 */
public interface ITProminentCriticalService {
    /**
     * 查询突出临界值
     *
     * @param id 突出临界值主键
     * @return 突出临界值
     */
    public TProminentCritical selectTProminentCriticalById(Integer id);

    /**
     * 查询突出临界值列表
     *
     * @param tProminentCritical 突出临界值
     * @return 突出临界值集合
     */
    public List<TProminentCritical> selectTProminentCriticalList(TProminentCritical tProminentCritical);

    /**
     * 新增突出临界值
     *
     * @param tProminentCritical 突出临界值
     * @return 结果
     */
    public Integer insertTProminentCritical(TProminentCritical tProminentCritical);

    /**
     * 修改突出临界值
     *
     * @param tProminentCritical 突出临界值
     * @return 结果
     */
    public Integer updateTProminentCritical(TProminentCritical tProminentCritical);

    /**
     * 批量删除突出临界值
     *
     * @param ids 需要删除的突出临界值主键集合
     * @return 结果
     */
    public Integer deleteTProminentCriticalByIds(List<Integer> ids);

    /**
     * 删除突出临界值信息
     *
     * @param id 突出临界值主键
     * @return 结果
     */
    public Integer deleteTProminentCriticalById(Integer id);

    public List<TProminentCritical> getPageList();
}
