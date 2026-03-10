package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TRoadway;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 巷道的增删改查Mapper接口
 *
 * @author eleven
 * @date 2022-07-12
 */
public interface TRoadwayMapper {
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
     * @return
     */
    public List<TRoadway> getPageList();

    /**
     * 新增巷道的增删改查
     *
     * @param tRoadway 巷道的增删改查
     * @return 结果
     */
    public Integer insertTRoadway(TRoadway tRoadway);

    /**
     * 修改巷道的增删改查
     *
     * @param tRoadway 巷道的增删改查
     * @return 结果
     */
    public Integer updateTRoadway(TRoadway tRoadway);

    /**
     * 删除巷道的增删改查
     *
     * @param id 巷道的增删改查主键
     * @return 结果
     */
    public Integer deleteTRoadwayById(@RequestParam Integer id);

    /**
     * 根据巷道名称查询巷道ID
     * @param roadway 巷道名称
     * @return id
     */
    public Integer getIdByRoadWayName(@RequestParam String roadway);

    /**
     * 批量删除巷道的增删改查
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTRoadwayByIds(@RequestParam("ids") List<Integer> ids);

    /**
     * 批量添加巷道
     *
     * @param list
     */
    public void insertTRoadwayList(@RequestParam("list")List<TRoadway> list);

    /**
     * 获取所有巷道名称
     * @return
     */
    @Select("select roadway from t_roadway GROUP BY roadway")
    public List<String> getRoadwayAll();

    /**
     * 通过巷道ID获取所属的工作面ID
     * @param id
     * @return
     */
    @Select("select workface_id from t_roadway where id =#{id}")
    public Integer getWorkfaceByRoadwayId(Integer id);

    @Select("select * from t_roadway where workface_id = #{id}")
    public List<TRoadway> selectTRoadwayByWorkingFaceId(Integer id);
}
