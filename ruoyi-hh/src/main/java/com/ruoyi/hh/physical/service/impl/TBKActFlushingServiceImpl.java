package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TBKActFlushing;
import com.ruoyi.hh.physical.mapper.TBActFlushingMapper;
import com.ruoyi.hh.physical.service.TBKActFlushingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TBKActFlushingServiceImpl implements TBKActFlushingService {
    @Resource
    private TBActFlushingMapper tBActFlushingMapper;
    @Override
    public List<TBKActFlushing> selectTBKActFlushingList(TBKActFlushing tbkActFlushing) {
        return tBActFlushingMapper.selectTBKActFlushingList(tbkActFlushing);
    }
}
