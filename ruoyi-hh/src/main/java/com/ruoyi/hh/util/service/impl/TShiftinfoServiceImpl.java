package com.ruoyi.hh.util.service.impl;

import com.ruoyi.hh.util.domain.TShiftinfo;
import com.ruoyi.hh.util.mapper.TShiftinfoMapper;
import com.ruoyi.hh.util.service.ITShiftinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 班次信息Service业务层处理
 *
 * @author eleven
 * @date 2022-07-18
 */
@Service
public class TShiftinfoServiceImpl implements ITShiftinfoService {
    @Resource
    private TShiftinfoMapper tShiftinfoMapper;

    /**
     * 查询班次信息
     *
     * @param id 班次信息主键
     * @return 班次信息
     */
    @Override
    public TShiftinfo selectTShiftinfoById(Integer id) {
        return tShiftinfoMapper.selectTShiftinfoById(id);
    }

    /**
     * 查询班次信息列表
     *
     * @param tShiftinfo 班次信息
     * @return 班次信息
     */
    @Override
    public List<TShiftinfo> selectTShiftinfoList(TShiftinfo tShiftinfo) {
        return tShiftinfoMapper.selectTShiftinfoList(tShiftinfo);
    }

    /**
     * 新增班次信息
     *
     * @param tShiftinfo 班次信息
     * @return 结果
     */
    @Override
    public Integer insertTShiftinfo(TShiftinfo tShiftinfo) {
        return tShiftinfoMapper.insertTShiftinfo(tShiftinfo);
    }

    /**
     * 修改班次信息
     *
     * @param tShiftinfo 班次信息
     * @return 结果
     */
    @Override
    public Integer updateTShiftinfo(TShiftinfo tShiftinfo) {
        return tShiftinfoMapper.updateTShiftinfo(tShiftinfo);
    }

    /**
     * 批量删除班次信息
     *
     * @param ids 需要删除的班次信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTShiftinfoByIds(List<Integer> ids) {
        return tShiftinfoMapper.deleteTShiftinfoByIds(ids);
    }

    /**
     * 删除班次信息信息
     *
     * @param id 班次信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTShiftinfoById(Integer id) {
        return tShiftinfoMapper.deleteTShiftinfoById(id);
    }

    @Override
    public List<TShiftinfo> getPageList() {
        return this.tShiftinfoMapper.getPageList();
    }
    /**
     * 获取所有班次信息
     * @return
     */
    @Override
    public List<String> getShiftInfoAll() {
        return this.tShiftinfoMapper.getShiftInfoAll();
    }
}
