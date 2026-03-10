package com.ruoyi.hh.util.service.impl;

import java.util.List;

import com.ruoyi.hh.util.domain.TFlushingException;
import com.ruoyi.hh.util.mapper.TFlushingExceptionMapper;
import com.ruoyi.hh.util.service.ITFlushingExceptionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 冲孔异常信息Service业务层处理
 *
 * @author eleven
 * @date 2022-07-18
 */
@Service
public class TFlushingExceptionServiceImpl implements ITFlushingExceptionService {
    @Resource
    private TFlushingExceptionMapper tFlushingExceptionMapper;

    /**
     * 查询冲孔异常信息
     *
     * @param id 冲孔异常信息主键
     * @return 冲孔异常信息
     */
    @Override
    public TFlushingException selectTFlushingExceptionById(Integer id) {
        return tFlushingExceptionMapper.selectTFlushingExceptionById(id);
    }

    /**
     * 查询冲孔异常信息列表
     *
     * @param tFlushingException 冲孔异常信息
     * @return 冲孔异常信息
     */
    @Override
    public List<TFlushingException> selectTFlushingExceptionList(TFlushingException tFlushingException) {
        return tFlushingExceptionMapper.selectTFlushingExceptionList(tFlushingException);
    }

    /**
     * 新增冲孔异常信息
     *
     * @param tFlushingException 冲孔异常信息
     * @return 结果
     */
    @Override
    public Integer insertTFlushingException(TFlushingException tFlushingException) {
        return tFlushingExceptionMapper.insertTFlushingException(tFlushingException);
    }

    /**
     * 修改冲孔异常信息
     *
     * @param tFlushingException 冲孔异常信息
     * @return 结果
     */
    @Override
    public Integer updateTFlushingException(TFlushingException tFlushingException) {
        return tFlushingExceptionMapper.updateTFlushingException(tFlushingException);
    }

    /**
     * 批量删除冲孔异常信息
     *
     * @param ids 需要删除的冲孔异常信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTFlushingExceptionByIds(List<Integer> ids) {
        return tFlushingExceptionMapper.deleteTFlushingExceptionByIds(ids);
    }

    /**
     * 删除冲孔异常信息信息
     *
     * @param id 冲孔异常信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTFlushingExceptionById(Integer id) {
        return tFlushingExceptionMapper.deleteTFlushingExceptionById(id);
    }

    @Override
    public List<TFlushingException> getPageList() {
        return tFlushingExceptionMapper.selectTFlushingExceptionVo();
    }
}
