package com.ruoyi.hh.util.service.impl;

import com.ruoyi.hh.util.mapper.TableMapper;
import com.ruoyi.hh.util.service.TableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    @Resource
    private TableMapper tableMapper;

    @Override
    public List<String> getTableNames() {
        return tableMapper.getTableNames();
    }
}
