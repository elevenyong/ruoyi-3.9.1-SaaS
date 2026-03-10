package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TDrivingWork;
import com.ruoyi.hh.physical.domain.TWorkingFaceOfDriving;
import com.ruoyi.hh.physical.mapper.TDrivingWorkMapper;
import com.ruoyi.hh.physical.mapper.TWorkingFaceOfDrivingMapper;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfDrivingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TWorkingFaceOfDrivingServiceImpl implements ITWorkingFaceOfDrivingService {

    @Resource
    private TWorkingFaceOfDrivingMapper tWorkingFaceOfDrivingMapper;

    @Resource
    private TDrivingWorkMapper tDrivingWorkMapper;

    /**
     * 查询掘进工作面
     *
     * @param id 掘进工作面主键
     * @return 掘进工作面
     */
    @Override
    public TWorkingFaceOfDriving selectTWorkingFaceOfDrivingById(Integer id) {
        TWorkingFaceOfDriving face = tWorkingFaceOfDrivingMapper.selectTWorkingFaceOfDrivingById(id);
        TDrivingWork queryWork = new TDrivingWork();
        queryWork.setWorkfaceId(id);
        List<TDrivingWork> drivingWorks = tDrivingWorkMapper.selectTDrivingWorkList(queryWork);
        face.setWorkingFaceList(drivingWorks);
        Double currentPoi = face.getOriginPosition();
        for (TDrivingWork work : drivingWorks) {
            if (face.getDirection()>0){
                currentPoi += work.getDrivingRate();
            }else {
                currentPoi -= work.getDrivingRate();
            }
        }
        face.setCurrentPosition(currentPoi);
        return face;
    }

    /**
     * 查询掘进工作面列表
     *
     * @param tWorkingFaceOfDriving 掘进工作面
     * @return 掘进工作面
     */
    @Override
    public List<TWorkingFaceOfDriving> selectTWorkingFaceOfDrivingList(TWorkingFaceOfDriving tWorkingFaceOfDriving) {
        List<TWorkingFaceOfDriving> workingFaceOfDrivings = tWorkingFaceOfDrivingMapper.selectTWorkingFaceOfDrivingList(tWorkingFaceOfDriving);
        for (TWorkingFaceOfDriving face : workingFaceOfDrivings) {
            TDrivingWork queryWork = new TDrivingWork();
            queryWork.setWorkfaceId(face.getId());
            List<TDrivingWork> drivingWorks = tDrivingWorkMapper.selectTDrivingWorkList(queryWork);
            face.setWorkingFaceList(drivingWorks);
            Double currentPoi = face.getOriginPosition();
            for (TDrivingWork work : drivingWorks) {
                if (face.getDirection()>0){
                    currentPoi += work.getDrivingRate();
                }else {
                    currentPoi -= work.getDrivingRate();
                }
            }
            face.setCurrentPosition(currentPoi);
        }
        return workingFaceOfDrivings;
    }

    /**
     * 新增掘进工作面
     *
     * @param tWorkingFaceOfDriving 掘进工作面
     * @return 结果
     */
    @Override
    public TWorkingFaceOfDriving insertTWorkingFaceOfDriving(TWorkingFaceOfDriving tWorkingFaceOfDriving) {
        tWorkingFaceOfDriving.setCurrentPosition(0.0);
        int i = tWorkingFaceOfDrivingMapper.insertTWorkingFaceOfDriving(tWorkingFaceOfDriving);
        if (i > 0) {
            return tWorkingFaceOfDriving;
        } else {
            return null;
        }
    }

    /**
     * 修改掘进工作面
     *
     * @param tWorkingFaceOfDriving 掘进工作面
     * @return 结果
     */
    @Override
    public TWorkingFaceOfDriving updateTWorkingFaceOfDriving(TWorkingFaceOfDriving tWorkingFaceOfDriving) {
        tWorkingFaceOfDriving.setCurrentPosition(null);
        int i = tWorkingFaceOfDrivingMapper.updateTWorkingFaceOfDriving(tWorkingFaceOfDriving);
        if (i > 0) {
            return tWorkingFaceOfDriving;
        } else {
            return null;
        }
    }

    /**
     * 批量删除掘进工作面
     *
     * @param ids 需要删除的掘进工作面主键
     * @return 结果
     */
    @Override
    public int deleteTWorkingFaceOfDrivingByIds(Integer[] ids) {
        return tWorkingFaceOfDrivingMapper.deleteTWorkingFaceOfDrivingByIds(ids);
    }

    /**
     * 删除掘进工作面信息
     *
     * @param id 掘进工作面主键
     * @return 结果
     */
    @Override
    public int deleteTWorkingFaceOfDrivingById(Integer id) {
        return tWorkingFaceOfDrivingMapper.deleteTWorkingFaceOfDrivingById(id);
    }
}
