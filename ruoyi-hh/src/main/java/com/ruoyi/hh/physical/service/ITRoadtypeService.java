package com.ruoyi.hh.physical.service;

import java.util.List;

import com.ruoyi.hh.physical.domain.TRoadtype;

/**
 * 巷道类型Service接口
 *
 * @author eleven
 * @date 2022-07-20
 */
public interface ITRoadtypeService {
    /**
     * 查询巷道类型
     *
     * @param id 巷道类型主键
     * @return 巷道类型
     */
    public TRoadtype selectTRoadtypeById(Integer id);

    /**
     * 查询巷道类型列表
     *
     * @param tRoadtype 巷道类型
     * @return 巷道类型集合
     */
    public List<TRoadtype> selectTRoadtypeList(TRoadtype tRoadtype);

    /**
     * 新增巷道类型
     *
     * @param tRoadtype 巷道类型
     * @return 结果
     */
    public Integer insertTRoadtype(TRoadtype tRoadtype);
//    public Result insertTRoadtype(TRoadtype tRoadtype);

    /**
     * 修改巷道类型
     *
     * @param tRoadtype 巷道类型
     * @return 结果
     */
    public Integer updateTRoadtype(TRoadtype tRoadtype);


//    /**
//     * 修改巷道类型
//     *
//     * @param tRoadtype 巷道类型
//     * @return 结果
//     */
//    public Result updateTRoadtype(TRoadtype tRoadtype);

    /**
     * 批量删除巷道类型
     *
     * @param ids 需要删除的巷道类型主键集合
     * @return 结果
     */
    public Integer deleteTRoadtypeByIds(List<Integer> ids);

    /**
     * 删除巷道类型信息
     *
     * @param id 巷道类型主键
     * @return 结果
     */
    public Integer deleteTRoadtypeById(Integer id);

    /**
     * 分页查询
     *
     * @return 列表
     */
    public List<TRoadtype> getPageList();
    /**
     * 根据巷道类型查询ID
     * @param roadwaytype 巷道类型
     * @return 巷道类型ID
     */
    public Integer getIdByType(String roadwaytype);
}
