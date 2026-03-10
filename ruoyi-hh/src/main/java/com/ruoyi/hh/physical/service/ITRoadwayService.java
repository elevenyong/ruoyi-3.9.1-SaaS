package com.ruoyi.hh.physical.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.physical.domain.TRoadway;
import com.ruoyi.hh.util.Result;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 巷道的增删改查Service接口
 *
 * @author eleven
 * @date 2022-07-12
 */
public interface ITRoadwayService {
    /**
     * 查询巷道的增删改查
     *
     * @param id 巷道的增删改查主键
     * @return 巷道的增删改查
     */
    public TRoadway selectTRoadwayById(Integer id);

    /**
     * 查询巷道的增删改查列表
     *
     * @param tRoadway 巷道的增删改查
     * @return 巷道的增删改查集合
     */
    public List<TRoadway> selectTRoadwayList(TRoadway tRoadway);

    /**
     * 查询巷道列表
     *
     * @return 巷道列表
     */
    public List<TRoadway> getPageList();

    /**
     * 新增巷道的增删改查
     *
     * @param tRoadway 巷道的增删改查
     */
    public AjaxResult insertTRoadway(TRoadway tRoadway);

    /**
     * 修改巷道的增删改查
     *
     * @param tRoadway 巷道的增删改查
     * @return 结果
     */
    public Integer updateTRoadway(TRoadway tRoadway);

    /**
     * 根据巷道名称查询巷道ID
     * @param roadway 巷道名称
     * @return id
     */
    public Integer getIdByRoadWayName(@RequestParam String roadway);

    /**
     * 批量删除巷道的增删改查
     *
     * @param ids 需要删除的巷道的增删改查主键集合
     * @return 结果
     */
    public Integer deleteTRoadwayByIds(List<Integer> ids);

    /**
     * 删除巷道的增删改查信息
     *
     * @param id 巷道的增删改查主键
     * @return 结果
     */
    public Integer deleteTRoadwayById(Integer id);

    /**
     * 批量添加巷道
     *
     * @param list
     */
    public Integer insertTRoadwayList(List<TRoadway> list);


    /**
     * 获取所有巷道名称
     * @return
     */
    public List<String> getRoadwayAll();

    public List<TRoadway> selectRoadwayListByWorkFace(Integer workingFaceId);


    /**
     * 通过巷道ID获取所属的工作面ID
     * @param id
     * @return
     */
    public Integer getWorkfaceByRoadwayId(Integer id);
}
