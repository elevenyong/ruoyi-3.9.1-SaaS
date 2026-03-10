package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TDrivingWork;
import com.ruoyi.hh.physical.mapper.TDrivingWorkMapper;
import com.ruoyi.hh.physical.mapper.TWorkingFaceOfDrivingMapper;
import com.ruoyi.hh.physical.service.ITDrivingWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TDrivingWorkServiceImpl implements ITDrivingWorkService {

    @Resource
    private TDrivingWorkMapper tDrivingWorkMapper;

    @Resource
    private TWorkingFaceOfDrivingMapper tWorkingFaceOfDrivingMapper;

    /**
     * 查询掘进工作
     *
     * @param id 掘进工作主键
     * @return 掘进工作
     */
    @Override
    public TDrivingWork selectTDrivingWorkById(Integer id) {
        TDrivingWork face = tDrivingWorkMapper.selectTDrivingWorkById(id);
        return face;
    }

    /**
     * 查询掘进工作列表
     *
     * @param tDrivingWork 掘进工作
     * @return 掘进工作
     */
    @Override
    public List<TDrivingWork> selectTDrivingWorkList(TDrivingWork tDrivingWork) {
        return tDrivingWorkMapper.selectTDrivingWorkList(tDrivingWork);
    }

    /**
     * 新增掘进工作
     *
     * @param tDrivingWork 掘进工作
     * @return 结果
     */
    @Override
    public TDrivingWork insertTDrivingWork(TDrivingWork tDrivingWork) {
        int i = tDrivingWorkMapper.insertTDrivingWork(tDrivingWork);
        if (i > 0) {
            return tDrivingWork;
        } else return null;
    }

    /**
     * 修改掘进工作
     *
     * @param tDrivingWork 掘进工作
     * @return 结果
     */
    @Override
    public TDrivingWork updateTDrivingWork(TDrivingWork tDrivingWork) {
        int i = tDrivingWorkMapper.updateTDrivingWork(tDrivingWork);
        if (i > 0) {
            return tDrivingWork;
        } else return null;
    }

    /**
     * 批量删除掘进工作
     *
     * @param ids 需要删除的掘进工作主键
     * @return 结果
     */
    @Override
    public int deleteTDrivingWorkByIds(Integer[] ids) {
        return tDrivingWorkMapper.deleteTDrivingWorkByIds(ids);
    }

    /**
     * 删除掘进工作信息
     *
     * @param id 掘进工作主键
     * @return 结果
     */
    @Override
    public int deleteTDrivingWorkById(Integer id) {
        return tDrivingWorkMapper.deleteTDrivingWorkById(id);
    }
}
