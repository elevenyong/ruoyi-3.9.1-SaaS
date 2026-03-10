package com.ruoyi.hh.util.mapper;

import java.util.List;

import com.ruoyi.hh.util.domain.TFlushingException;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 冲孔异常信息Mapper接口
 *
 * @author eleven
 * @date 2022-07-18
 */
public interface TFlushingExceptionMapper {
    /**
     * 查询冲孔异常信息
     *
     * @param id 冲孔异常信息主键
     * @return 冲孔异常信息
     */
    public TFlushingException selectTFlushingExceptionById(Integer id);

    /**
     * 查询冲孔异常信息列表
     *
     * @param tFlushingException 冲孔异常信息
     * @return 冲孔异常信息集合
     */
    public List<TFlushingException> selectTFlushingExceptionList(TFlushingException tFlushingException);

    /**
     * 新增冲孔异常信息
     *
     * @param tFlushingException 冲孔异常信息
     * @return 结果
     */
    public Integer insertTFlushingException(TFlushingException tFlushingException);

    /**
     * 修改冲孔异常信息
     *
     * @param tFlushingException 冲孔异常信息
     * @return 结果
     */
    public Integer updateTFlushingException(TFlushingException tFlushingException);

    /**
     * 删除冲孔异常信息
     *
     * @param id 冲孔异常信息主键
     * @return 结果
     */
    public Integer deleteTFlushingExceptionById(Integer id);

    /**
     * 批量删除冲孔异常信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTFlushingExceptionByIds(@RequestParam List<Integer> ids);


    public List<TFlushingException> selectTFlushingExceptionVo();
}
