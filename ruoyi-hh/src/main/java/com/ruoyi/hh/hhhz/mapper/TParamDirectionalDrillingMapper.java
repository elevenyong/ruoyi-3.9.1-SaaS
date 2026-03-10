package com.ruoyi.hh.hhhz.mapper;

import com.ruoyi.hh.hhhz.domain.param.TParamDirectionalDrilling;

import java.util.List;


/**
 * 定向钻孔Mapper接口
 *
 * @author hhhz
 * @date 2023-04-25
 */
public interface TParamDirectionalDrillingMapper
{
    /**
     * 查询定向钻孔
     *
     * @param id 定向钻孔主键
     * @return 定向钻孔
     */
    public TParamDirectionalDrilling selectTParamDirectionalDrillingById(Integer id);

    /**
     * 查询定向钻孔列表
     *
     * @param tParamDirectionalDrilling 定向钻孔
     * @return 定向钻孔集合
     */
    public List<TParamDirectionalDrilling> selectTParamDirectionalDrillingList(TParamDirectionalDrilling tParamDirectionalDrilling);

    /**
     * 新增定向钻孔
     *
     * @param tParamDirectionalDrilling 定向钻孔
     * @return 结果
     */
    public int insertTParamDirectionalDrilling(TParamDirectionalDrilling tParamDirectionalDrilling);

    /**
     * 修改定向钻孔
     *
     * @param tParamDirectionalDrilling 定向钻孔
     * @return 结果
     */
    public int updateTParamDirectionalDrilling(TParamDirectionalDrilling tParamDirectionalDrilling);

    /**
     * 删除定向钻孔
     *
     * @param id 定向钻孔主键
     * @return 结果
     */
    public int deleteTParamDirectionalDrillingById(Integer id);

    /**
     * 批量删除定向钻孔
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTParamDirectionalDrillingByIds(Integer[] ids);


    public TParamDirectionalDrilling getLastOne();

    /**
     * 更改参数的标识
     * @param ids 列表
     */
    public void updateSign(List<Integer> ids);
}
