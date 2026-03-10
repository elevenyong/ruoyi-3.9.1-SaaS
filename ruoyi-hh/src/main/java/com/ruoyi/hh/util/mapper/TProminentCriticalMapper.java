package com.ruoyi.hh.util.mapper;

import java.util.List;

import com.ruoyi.hh.util.domain.TProminentCritical;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 突出临界值Mapper接口
 *
 * @author eleven
 * @date 2022-07-18
 */
public interface TProminentCriticalMapper {
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
     * 删除突出临界值
     *
     * @param id 突出临界值主键
     * @return 结果
     */
    public Integer deleteTProminentCriticalById(@RequestParam Integer id);

    /**
     * 批量删除突出临界值
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTProminentCriticalByIds(@RequestParam List<Integer> ids);

    public List<TProminentCritical> getPageList();
}
