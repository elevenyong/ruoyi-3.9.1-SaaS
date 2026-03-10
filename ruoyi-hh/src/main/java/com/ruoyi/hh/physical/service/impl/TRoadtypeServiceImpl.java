package com.ruoyi.hh.physical.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.domain.TRoadtype;
import com.ruoyi.hh.physical.mapper.TRoadtypeMapper;
import com.ruoyi.hh.physical.service.ITRoadtypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 巷道类型Service业务层处理
 *
 * @author eleven
 * @date 2022-07-20
 */
@Service
public class TRoadtypeServiceImpl implements ITRoadtypeService {
    @Resource
    private TRoadtypeMapper tRoadtypeMapper;

    /**
     * 查询巷道类型
     *
     * @param id 巷道类型主键
     * @return 巷道类型
     */
    @Override
    public TRoadtype selectTRoadtypeById(Integer id) {
        return tRoadtypeMapper.selectTRoadtypeById(id);
    }

    /**
     * 查询巷道类型列表
     *
     * @param tRoadtype 巷道类型
     * @return 巷道类型
     */
    @Override
    public List<TRoadtype> selectTRoadtypeList(TRoadtype tRoadtype) {
        return tRoadtypeMapper.selectTRoadtypeList(tRoadtype);
    }

    /**
     * 新增巷道类型
     *
     * @param tRoadtype 巷道类型
     * @return 结果
     */
    @Override
    public Integer insertTRoadtype(TRoadtype tRoadtype) {
        return tRoadtypeMapper.insertTRoadtype(tRoadtype);
    }

//    public Result insertTRoadtype(TRoadtype tRoadtype) {
//        if(this.tRoadtypeMapper.getIdByType(tRoadtype.getRoadwaytype())!=null){
//            return Result.buildResult(Result.Status.EXIT);
//        }
//        int res= tRoadtypeMapper.insertTRoadtype(tRoadtype);
//        if(res>0){
//            return Result.buildResult(Result.Status.OK);
//        }else{
//            return  Result.buildResult(Result.Status.ERROR);
//        }
//    }


    /**
     * 修改巷道类型
     *
     * @param tRoadtype 巷道类型
     * @return 结果
     */
    @Override
    public Integer updateTRoadtype(TRoadtype tRoadtype) {
        return tRoadtypeMapper.updateTRoadtype(tRoadtype);
    }

//    /**
//     * 修改巷道类型
//     *
//     * @param tRoadtype 巷道类型
//     * @return 结果
//     */
//    @Override
//    public Result updateTRoadtype(TRoadtype tRoadtype) {
//        if(this.tRoadtypeMapper.getIdByType(tRoadtype.getRoadwaytype())!=null&&this.tRoadtypeMapper.getIdByType(tRoadtype.getRoadwaytype())!=tRoadtype.getId()){
//            return Result.buildResult(Result.Status.EXIT);
//        }
//        int res= tRoadtypeMapper.updateTRoadtype(tRoadtype);
//        if(res>0){
//            return Result.buildResult(Result.Status.OK);
//        }else {
//            return Result.buildResult(Result.Status.ERROR);
//        }
//    }



    /**
     * 批量删除巷道类型
     *
     * @param ids 需要删除的巷道类型主键
     * @return 结果
     */
    @Override
    public Integer deleteTRoadtypeByIds(List<Integer> ids) {
        return tRoadtypeMapper.deleteTRoadtypeByIds(ids);
    }

    /**
     * 删除巷道类型信息
     *
     * @param id 巷道类型主键
     * @return 结果
     */
    @Override
    public Integer deleteTRoadtypeById(Integer id) {

        return tRoadtypeMapper.deleteTRoadtypeById(id);
    }

    /**
     * 分页查询列表
     *
     * @return 列表
     */
    @Override
    public List<TRoadtype> getPageList() {
        return this.tRoadtypeMapper.getPageList();
    }

    /**
     * 根据巷道类型查询ID
     * @param roadwaytype 巷道类型
     * @return 巷道类型ID
     */
    @Override
    public Integer getIdByType(String roadwaytype) {
        return this.tRoadtypeMapper.getIdByType(roadwaytype);
    }
}
