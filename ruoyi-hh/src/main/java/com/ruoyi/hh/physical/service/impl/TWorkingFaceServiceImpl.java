package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TWorkingFace;
import com.ruoyi.hh.physical.mapper.TWorkingFaceMapper;
import com.ruoyi.hh.physical.service.ITCoalinfoService;
import com.ruoyi.hh.physical.service.ITRockinfoService;
import com.ruoyi.hh.physical.service.ITWorkingFaceService;
import com.ruoyi.hh.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工作面信息Service业务层处理
 *
 * @author eleven
 * @date 2022-07-18
 */
@Service
public class TWorkingFaceServiceImpl implements ITWorkingFaceService {
    @Resource
    private TWorkingFaceMapper tWorkingFaceMapper;
    @Resource
    private ITCoalinfoService tCoalinfoService;
    @Resource
    private ITRockinfoService tRockinfoService;

    /**
     * 查询工作面信息
     *
     * @param id 工作面信息主键
     * @return 工作面信息
     */
    @Override
    public TWorkingFace selectTWorkingFaceById(Integer id) {
        return tWorkingFaceMapper.selectTWorkingFaceById(id);
    }

    /**
     * 查询工作面信息列表
     *
     * @param tWorkingFace 工作面信息
     * @return 工作面信息
     */
    @Override
    public List<TWorkingFace> selectTWorkingFaceList(TWorkingFace tWorkingFace) {
        return tWorkingFaceMapper.selectTWorkingFaceList(tWorkingFace);
    }

    /**
     * 新增工作面信息
     *
     * @param tWorkingFace 工作面信息
     * @return 结果
     */
    @Override
    public Result insertTWorkingFace(TWorkingFace tWorkingFace) {
        if(tWorkingFace.getMiningAreaId()==null){
            return Result.buildResult(Result.Status.NULL_ERROR);
        }
        if(this.tWorkingFaceMapper.getByMinAreaIdAndWorkFaceName(tWorkingFace.getWorkfaceName(),tWorkingFace.getMiningAreaId())!=null){
            return Result.buildResult(Result.Status.EXIT);
        }
        int res= tWorkingFaceMapper.insertTWorkingFace(tWorkingFace);
        if(res>0){
            return Result.buildResult(Result.Status.OK);
        }else {
            return Result.buildResult(Result.Status.ERROR);
        }

    }

    /**
     * 修改工作面信息
     *
     * @param tWorkingFace 工作面信息
     * @return 结果
     */
    @Override
    public Result updateTWorkingFace(TWorkingFace tWorkingFace) {
        if(this.tWorkingFaceMapper.getByMinAreaIdAndWorkFaceName(tWorkingFace.getWorkfaceName(),tWorkingFace.getMiningAreaId())!=null
        &&this.tWorkingFaceMapper.getByMinAreaIdAndWorkFaceName(tWorkingFace.getWorkfaceName(),tWorkingFace.getMiningAreaId()).getId().equals(tWorkingFace.getId())
        ){
            return Result.buildResult(Result.Status.EXIT);
        }else{
            int res= tWorkingFaceMapper.updateTWorkingFace(tWorkingFace);
            if(res>0){
                return Result.buildResult(Result.Status.OK);
            }else{
                return Result.buildResult(Result.Status.ERROR);
            }
        }
    }

    /**
     * 批量删除工作面信息
     *
     * @param ids 需要删除的工作面信息主键
     * @return 结果
     */
    @Override
    public Integer deleteTWorkingFaceByIds(List<Integer> ids) {
        if(ids.size()>0){
            //this.tRockinfoService.delByWorkingFaceIds(ids);
            this.tCoalinfoService.delByWorkingFaceIds(ids);
        }
        return tWorkingFaceMapper.deleteTWorkingFaceByIds(ids);
    }


    @Override
    public List<TWorkingFace> getPageList() {
        return this.tWorkingFaceMapper.getPageList();
    }

    /**
     * 根据工作面编号查询工作面ID
     * @param miningareaid 工作面编号
     * @return id
     */
    @Override
    public Integer getByWorkfaceSerial(@RequestParam Integer miningareaid){
        return this.tWorkingFaceMapper.getByWorkfaceSerial(miningareaid);
    }

    /**
     * 抽采动态
     * @return 状态
     */
    public List<TWorkingFace> getWorkfaceDrainageOperation(){
        return this.tWorkingFaceMapper.getWorkfaceDrainageOperation();
    }

    @Override
    public TWorkingFace getInfoById(Integer workFaceId) {
        return this.tWorkingFaceMapper.getInfoById(workFaceId);
    }

}
