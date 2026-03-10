package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TBKActFlushing;

import java.util.List;

/**
 * @projectName: ruoyi
 * @package: com.ruoyi.hh.physical.mapper
 * @className: TBActFlushingMapper
 * @author: Eleven
 * @description: 水力冲孔mapper
 * @date: 2023/7/15 15:52
 * @version: 1.0
 */
public interface TBActFlushingMapper {
    /**
     * 根据任意冲孔信息查询实钻信息
     * @param tbkActFlushing
     * @return
     */
    public List<TBKActFlushing> selectTBKActFlushingList(TBKActFlushing tbkActFlushing);
}
