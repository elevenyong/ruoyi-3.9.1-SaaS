package com.ruoyi.hh.hhhz.mapper;

import com.ruoyi.hh.hhhz.domain.param.TParamHighLevelDrilling;

import java.util.List;

/**
 * 高位钻孔Mapper接口
 *
 * @author hhhz
 * @date 2023-04-24
 */
public interface TParamHighLevelDrillingMapper {
    /**
     * 查询高位钻孔
     *
     * @param id 高位钻孔主键
     * @return 高位钻孔
     */
    public TParamHighLevelDrilling selectTParamHighLevelDrillingById(Integer id);

    /**
     * 查询高位钻孔列表
     *
     * @param tParamHighLevelDrilling 高位钻孔
     * @return 高位钻孔集合
     */
    public List<TParamHighLevelDrilling> selectTParamHighLevelDrillingList(TParamHighLevelDrilling tParamHighLevelDrilling);

    /**
     * 新增高位钻孔
     *
     * @param tParamHighLevelDrilling 高位钻孔
     * @return 结果
     */
    public int insertTParamHighLevelDrilling(TParamHighLevelDrilling tParamHighLevelDrilling);

    /**
     * 修改高位钻孔
     *
     * @param tParamHighLevelDrilling 高位钻孔
     * @return 结果
     */
    public int updateTParamHighLevelDrilling(TParamHighLevelDrilling tParamHighLevelDrilling);

    /**
     * 删除高位钻孔
     *
     * @param id 高位钻孔主键
     * @return 结果
     */
    public int deleteTParamHighLevelDrillingById(Integer id);

    /**
     * 批量删除高位钻孔
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTParamHighLevelDrillingByIds(Integer[] ids);

    public TParamHighLevelDrilling getLastOne();

    /**
     * 更改参数的标识
     * @param ids 列表
     */
    public void updateSign(List<Integer> ids);


}
