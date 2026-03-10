package com.ruoyi.hh.business.mapper;

import com.ruoyi.hh.business.domain.TSealingGrouting;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * 封孔注浆信息Mapper接口
 *
 * @author eleven
 * @date 2022-07-21
 */
public interface TSealingGroutingMapper {
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
     * 删除封孔注浆信息
     *
     * @param id 封孔注浆信息主键
     * @return 结果
     */
    public Integer deleteTSealingGroutingById(Integer id);

    /**
     * 批量删除封孔注浆信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTSealingGroutingByIds(@RequestParam List<Integer> ids);

    /**
     * 获取所有信息
     *
     * @return 列表
     */
    public List<TSealingGrouting> getPageList();

    /**
     * 根据巷道ID、注浆日期、班次、注浆人查询注浆记录
     *
     * @param groutingDate   注浆日期
     * @param groutingPerson 注浆人
     * @param roadwayId      巷道ID
     * @param shiftId        班次ID
     * @return 注浆记录列表
     */
    public List<TSealingGrouting> getSealingGroutingRecord(
            @Param("groutingDate") Date groutingDate,
            @Param("roadwayId") List<Integer> roadwayId,
            @Param("groupNo") List<String> groupNo,
            @Param("boreholeNo") List<Integer> boreholeNo,
            @Param("groutingPerson") List<String> groutingPerson,
            @Param("shiftId") Integer shiftId);

    /**
     * 根据钻孔ID删除注浆信息
     * @param boreholeId 钻孔ID
     * @return 结果
     */
    public Integer delByBoreholeId(@RequestParam Integer boreholeId);


    /**
     * 根据钻孔ID删除注浆信息
     * @param boreholeIds 钻孔ID
     * @return 结果
     */
    public Integer delByBoreholeIds(@RequestParam List<Integer> boreholeIds);
}
