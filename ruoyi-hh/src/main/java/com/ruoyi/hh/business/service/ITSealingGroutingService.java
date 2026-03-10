package com.ruoyi.hh.business.service;

import com.ruoyi.hh.business.domain.TSealingGrouting;

import java.util.Date;
import java.util.List;

/**
 * 封孔注浆信息Service接口
 *
 * @author eleven
 * @date 2022-07-21
 */
public interface ITSealingGroutingService {
    /**
     * 查询封孔注浆信息
     *
     * @param id 封孔注浆信息主键
     * @return 封孔注浆信息
     */
    public TSealingGrouting selectTSealingGroutingById(Integer id);

    /**
     * 查询封孔注浆信息列表
     *
     * @param tSealingGrouting 封孔注浆信息
     * @return 封孔注浆信息集合
     */
    public List<TSealingGrouting> selectTSealingGroutingList(TSealingGrouting tSealingGrouting);

    /**
     * 新增封孔注浆信息
     *
     * @param tSealingGrouting 封孔注浆信息
     * @return 结果
     */
    public Integer insertTSealingGrouting(TSealingGrouting tSealingGrouting);

    /**
     * 修改封孔注浆信息
     *
     * @param tSealingGrouting 封孔注浆信息
     * @return 结果
     */
    public Integer updateTSealingGrouting(TSealingGrouting tSealingGrouting);

    /**
     * 批量删除封孔注浆信息
     *
     * @param ids 需要删除的封孔注浆信息主键集合
     * @return 结果
     */
    public Integer deleteTSealingGroutingByIds(List<Integer> ids);

    /**
     * 删除封孔注浆信息信息
     *
     * @param id 封孔注浆信息主键
     * @return 结果
     */
    public Integer deleteTSealingGroutingById(Integer id);

    /**
     * 获取所有信息
     *
     * @return 列表
     */
    public List<TSealingGrouting> getPageList();

    /**
     * 根据巷道ID、注浆日期、班次、注浆人查询注浆记录
     *
     * @param groutingdate   注浆日期
     * @param groutingperson 注浆人
     * @param roadwayId      巷道ID
     * @param shiftid        班次ID
     * @return 注浆记录列表
     */
    public List<TSealingGrouting> getSealingGroutingRecord(
            Date groutingdate,
            List<Integer> roadwayId,
            List<String> groupNo,
            List<Integer> boreholeNo,
            List<String> groutingperson,
            Integer shiftid);

    /**
     * 根据钻孔ID删除注浆信息
     * @param boreholeId 钻孔ID
     * @return 结果
     */
    public Integer delByBoreholeId(Integer boreholeId);

    /**
     * 根据钻孔ID删除注浆信息
     * @param boreholeIds 钻孔ID
     * @return 结果
     */
    public Integer delByBoreholeIds(List<Integer> boreholeIds);


}
