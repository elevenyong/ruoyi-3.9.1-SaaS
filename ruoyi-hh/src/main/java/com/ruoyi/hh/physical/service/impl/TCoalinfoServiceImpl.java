package com.ruoyi.hh.physical.service.impl;

import java.util.List;

import com.ruoyi.hh.physical.domain.TCoalinfo;
import com.ruoyi.hh.physical.mapper.TCoalPointMapper;
import com.ruoyi.hh.physical.mapper.TCoalinfoMapper;
import com.ruoyi.hh.physical.service.ITCoalinfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 煤层信息表Service业务层处理
 *
 * @author eleven
 * @date 2022-07-18
 */
@Service
public class TCoalinfoServiceImpl implements ITCoalinfoService {
    @Resource
    private TCoalinfoMapper tCoalinfoMapper;
    @Resource
    private TCoalPointMapper TCoalPointMapper;

    /**
     * 查询煤层信息表
     *
     * @param id 煤层信息表主键
     * @return 煤层信息表
     */
    @Override
    public TCoalinfo selectTCoalinfoById(Integer id) {
        return tCoalinfoMapper.selectTCoalinfoById(id);
    }

    /**
     * 查询煤层信息表列表
     *
     * @param tCoalinfo 煤层信息表
     * @return 煤层信息表
     */
    @Override
    public List<TCoalinfo> selectTCoalinfoList(TCoalinfo tCoalinfo) {

        List<TCoalinfo> list = tCoalinfoMapper.selectTCoalinfoList(tCoalinfo);
        if (list.size() > 0) {
            for (TCoalinfo tcl : list) {
                tcl.setCoalPointinfoList(this.TCoalPointMapper.getByCoalInfoId(Math.toIntExact(tcl.getId())));
            }
        }
        return list;
    }

    /**
     * 新增煤层信息表
     *
     * @param tCoalinfo 煤层信息表
     * @return 结果
     */
    @Override
    public Integer insertTCoalinfo(TCoalinfo tCoalinfo) {
        return tCoalinfoMapper.insertTCoalinfo(tCoalinfo);
    }

    /**
     * 修改煤层信息表
     *
     * @param tCoalinfo 煤层信息表
     * @return 结果
     */
    @Override
    public Integer updateTCoalinfo(TCoalinfo tCoalinfo) {
        return tCoalinfoMapper.updateTCoalinfo(tCoalinfo);
    }

    /**
     * 批量删除煤层信息表
     *
     * @param ids 需要删除的煤层信息表主键
     * @return 结果
     */
    @Override
    public Integer deleteTCoalinfoByIds(List<Integer> ids) {
        return tCoalinfoMapper.deleteTCoalinfoByIds(ids);
    }

    /**
     * 删除煤层信息表信息
     *
     * @param id 煤层信息表主键
     * @return 结果
     */
    @Override
    public Integer deleteTCoalinfoById(Integer id) {
        return tCoalinfoMapper.deleteTCoalinfoById(id);
    }

    @Override
    public List<TCoalinfo> getPageList() {
        return this.tCoalinfoMapper.selectTCoalinfoVo();
    }

    /**
     * 根据工作面ID删除煤层信息
     *
     * @param workingFaceIds 工作面ID
     * @return 结果
     */
    @Override
    public Integer delByWorkingFaceIds(List<Integer> workingFaceIds) {
        return this.tCoalinfoMapper.delByWorkingFaceIds(workingFaceIds);
    }
//    /**
//    * 根据工作面ID删除煤层信息
//     * @param workingFaceId 工作面ID
//     * @return 结果
//     */
//    @Override
//    public Integer delByWorkingFaceId(Integer workingFaceId) {
//        return this.tCoalinfoMapper.delByWorkingFaceId(workingFaceId);
//    }

    /**
     * 根据工作面ID和评价单元ID查询所属的煤层信息
     * @param workfaceid 工作面ID
     * @return 列表
     */
    public TCoalinfo getCoalInfo(@Param("workfaceid")Integer workfaceid){
        return this.tCoalinfoMapper.getCoalInfo(workfaceid);
    }
}
