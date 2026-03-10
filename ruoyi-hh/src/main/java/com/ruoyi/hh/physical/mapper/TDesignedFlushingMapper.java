package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TDesignedFlushing;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 设计冲孔信息Mapper接口
 * 
 * @author eleven
 * @date 2022-08-05
 */
public interface TDesignedFlushingMapper 
{
    /**
     * 查询设计冲孔信息
     * 
     * @param id 设计冲孔信息主键
     * @return 设计冲孔信息
     */
    public TDesignedFlushing selectTDesignedFlushingById(Integer id);

    /**
     * 查询设计冲孔信息列表
     * 
     * @param tDesignedFlushing 设计冲孔信息
     * @return 设计冲孔信息集合
     */
    public List<TDesignedFlushing> selectTDesignedFlushingList(TDesignedFlushing tDesignedFlushing);

    /**
     * 新增设计冲孔信息
     * 
     * @param tDesignedFlushing 设计冲孔信息
     * @return 结果
     */
    public int insertTDesignedFlushing(TDesignedFlushing tDesignedFlushing);

    /**
     * 修改设计冲孔信息
     * 
     * @param tDesignedFlushing 设计冲孔信息
     * @return 结果
     */
    public int updateTDesignedFlushing(TDesignedFlushing tDesignedFlushing);

    /**
     * 删除设计冲孔信息
     * 
     * @param id 设计冲孔信息主键
     * @return 结果
     */
    public int deleteTDesignedFlushingById(@RequestParam Integer id);

    /**
     * 批量删除设计冲孔信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDesignedFlushingByIds(@RequestParam List<Integer> ids);

    /**
     * 根据设计钻孔ID删除设计冲孔信息
     * @param boreholeIds 钻孔ID
     * @return 结果
     */
    public Integer delByBoreholeIds(@RequestParam List<Integer> boreholeIds);
    /**
     * 根据设计钻孔ID删除设计冲孔信息
     * @param boreholeId 钻孔ID
     * @return 结果
     */
    public Integer delByBoreholeId(@RequestParam Integer boreholeId);

    /**
     * 删除设计冲孔所有数据
     * @return 结果
     */
    public Integer delAll();

    /**
     * 查询所有列表
     * @return 列表
     */
    public List<TDesignedFlushing> getPageList();
}
