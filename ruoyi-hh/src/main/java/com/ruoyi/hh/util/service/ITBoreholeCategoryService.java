package com.ruoyi.hh.util.service;

import com.ruoyi.hh.util.domain.TBoreholeCategory;

import java.util.List;

/**
 * 钻孔类型相关接口Service接口
 *
 * @author eleven
 * @date 2022-07-13
 */
public interface ITBoreholeCategoryService {
    /**
     * 查询钻孔类型相关接口
     *
     * @param id 钻孔类型相关接口主键
     * @return 钻孔类型相关接口
     */
    public TBoreholeCategory selectTBoreholeCategoryById(Integer id);

    /**
     * 查询钻孔类型相关接口列表
     *
     * @param tBoreholeCategory 钻孔类型相关接口
     * @return 钻孔类型相关接口集合
     */
    public List<TBoreholeCategory> selectTBoreholeCategoryList(TBoreholeCategory tBoreholeCategory);

    /**
     * 新增钻孔类型相关接口
     *
     * @param tBoreholeCategory 钻孔类型相关接口
     */
    public void insertTBoreholeCategory(TBoreholeCategory tBoreholeCategory);
//    public Result insertTBoreholeCategory(TBoreholeCategory tBoreholeCategory);

    /**
     * 修改钻孔类型相关接口
     *
     * @param tBoreholeCategory 钻孔类型相关接口
     * @return 结果
     */
    public Integer updateTBoreholeCategory(TBoreholeCategory tBoreholeCategory);

    /**
     * 批量删除钻孔类型相关接口
     *
     * @param ids 需要删除的钻孔类型相关接口主键集合
     * @return 结果
     */
    public Integer deleteTBoreholeCategoryByIds(List<Integer> ids);

    /**
     * 删除钻孔类型相关接口信息
     *
     * @param id 钻孔类型相关接口主键
     * @return 结果
     */
    public Integer deleteTBoreholeCategoryById(Integer id);

    /**
     * 批量添加钻孔类型相关接口
     *
     * @param list
     */
    public void insertTBoreholeCategoryList(List<TBoreholeCategory> list);

    /**
     * 查询列表
     */
    public List<TBoreholeCategory> getPageList();

    /**
     * 根据钻孔类别查询钻孔类别ID
     * @param boreholecategory 钻孔类别名称
     * @return 钻孔类别Id
     */
    public Integer getIdByBoreholeCategory(String boreholecategory);

    /**
     * 获取钻孔类别
     * @return
     */
    public List<String> getBoreholeCategoryAll();

    /**
     * 通过类别编码获取钻孔类型
     * @param identificationCode
     * @return
     */
    public TBoreholeCategory getByCode(Integer identificationCode);

    /**
     * 根据类别编码更改钻孔类型
     * @param tBoreholeCategory
     * @return
     */
    public Integer updateByCode(TBoreholeCategory tBoreholeCategory);
}
