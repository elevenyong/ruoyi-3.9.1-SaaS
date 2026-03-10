package com.ruoyi.hh.util.service.impl;

import java.util.List;

import com.ruoyi.hh.util.domain.TProminentCritical;
import com.ruoyi.hh.util.mapper.TProminentCriticalMapper;
import com.ruoyi.hh.util.service.ITProminentCriticalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 突出临界值Service业务层处理
 *
 * @author eleven
 * @date 2022-07-18
 */
@Service
public class TProminentCriticalServiceImpl implements ITProminentCriticalService {
    @Resource
    private TProminentCriticalMapper tProminentCriticalMapper;

    /**
     * 查询突出临界值
     *
     * @param id 突出临界值主键
     * @return 突出临界值
     */
    @Override
    public TProminentCritical selectTProminentCriticalById(Integer id) {
        return tProminentCriticalMapper.selectTProminentCriticalById(id);
    }

    /**
     * 查询突出临界值列表
     *
     * @param tProminentCritical 突出临界值
     * @return 突出临界值
     */
    @Override
    public List<TProminentCritical> selectTProminentCriticalList(TProminentCritical tProminentCritical) {
        return tProminentCriticalMapper.selectTProminentCriticalList(tProminentCritical);
    }

    /**
     * 新增突出临界值
     *
     * @param tProminentCritical 突出临界值
     * @return 结果
     */
    @Override
    public Integer insertTProminentCritical(TProminentCritical tProminentCritical) {
        return tProminentCriticalMapper.insertTProminentCritical(tProminentCritical);
    }

    /**
     * 修改突出临界值
     *
     * @param tProminentCritical 突出临界值
     * @return 结果
     */
    @Override
    public Integer updateTProminentCritical(TProminentCritical tProminentCritical) {
        return tProminentCriticalMapper.updateTProminentCritical(tProminentCritical);
    }

    /**
     * 批量删除突出临界值
     *
     * @param ids 需要删除的突出临界值主键
     * @return 结果
     */
    @Override
    public Integer deleteTProminentCriticalByIds(List<Integer> ids) {
        return tProminentCriticalMapper.deleteTProminentCriticalByIds(ids);
    }

    /**
     * 删除突出临界值信息
     *
     * @param id 突出临界值主键
     * @return 结果
     */
    @Override
    public Integer deleteTProminentCriticalById(Integer id) {
        return tProminentCriticalMapper.deleteTProminentCriticalById(id);
    }

    @Override
    public List<TProminentCritical> getPageList() {
        return this.tProminentCriticalMapper.getPageList();
    }
}
