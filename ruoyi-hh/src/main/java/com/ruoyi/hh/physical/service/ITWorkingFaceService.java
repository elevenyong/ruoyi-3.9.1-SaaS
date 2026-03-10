package com.ruoyi.hh.physical.service;

import java.util.List;

import com.ruoyi.hh.physical.domain.TWorkingFace;
import com.ruoyi.hh.util.Result;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 工作面信息Service接口
 *
 * @author eleven
 * @date 2022-07-18
 */
public interface ITWorkingFaceService {
    /**
     * 查询工作面信息
     *
     * @param id 工作面信息主键
     * @return 工作面信息
     */
    public TWorkingFace selectTWorkingFaceById(Integer id);

    /**
     * 查询工作面信息列表
     *
     * @param tWorkingFace 工作面信息
     * @return 工作面信息集合
     */
    public List<TWorkingFace> selectTWorkingFaceList(TWorkingFace tWorkingFace);

    /**
     * 新增工作面信息
     *
     * @param tWorkingFace 工作面信息
     * @return 结果
     */
    public Result insertTWorkingFace(TWorkingFace tWorkingFace);

    /**
     * 修改工作面信息
     *
     * @param tWorkingFace 工作面信息
     * @return 结果
     */
    public Result updateTWorkingFace(TWorkingFace tWorkingFace);

    /**
     * 批量删除工作面信息
     *
     * @param ids 需要删除的工作面信息主键集合
     * @return 结果
     */
    public Integer deleteTWorkingFaceByIds(List<Integer> ids);


    public List<TWorkingFace> getPageList();

    /**
     * 根据工作面编号查询工作面ID
     * @param miningareaid 工作面编号
     * @return id
     */
    public Integer getByWorkfaceSerial(@RequestParam Integer miningareaid);

    /**
     * 抽采动态
     * @return 状态
     */
    public List<TWorkingFace> getWorkfaceDrainageOperation();

    /**
     * 根据工作面ID查询工作面信息
     * @param workFaceId
     * @return
     */
    public TWorkingFace getInfoById(Integer workFaceId);
}
