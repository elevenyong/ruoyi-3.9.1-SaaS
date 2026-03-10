package com.ruoyi.hh.util.service.impl;

import com.ruoyi.hh.util.domain.TBoreholeCategory;
import com.ruoyi.hh.util.mapper.TBoreholeCategoryMapper;
import com.ruoyi.hh.util.service.ITBoreholeCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 钻孔类型相关接口Service业务层处理
 *
 * @author eleven
 * @date 2022-07-13
 */
@Service
public class TBoreholeCategoryServiceImpl implements ITBoreholeCategoryService {
    @Resource
    private TBoreholeCategoryMapper tBoreholeCategoryMapper;

    /**
     * 查询钻孔类型相关接口
     *
     * @param id 钻孔类型相关接口主键
     * @return 钻孔类型相关接口
     */
    @Override
    public TBoreholeCategory selectTBoreholeCategoryById(Integer id) {
        return tBoreholeCategoryMapper.selectTBoreholeCategoryById(id);
    }

    /**
     * 查询钻孔类型相关接口列表
     *
     * @param tBoreholeCategory 钻孔类型相关接口
     * @return 钻孔类型相关接口
     */
    @Override
    public List<TBoreholeCategory> selectTBoreholeCategoryList(TBoreholeCategory tBoreholeCategory) {
        return tBoreholeCategoryMapper.selectTBoreholeCategoryList(tBoreholeCategory);
    }

    /**
     * 新增钻孔类型相关接口
     *
     * @param tBoreholeCategory 钻孔类型相关接口
     */
    @Override
    public void insertTBoreholeCategory(TBoreholeCategory tBoreholeCategory) {
        TBoreholeCategory tc=this.tBoreholeCategoryMapper.getByCode(tBoreholeCategory.getCategoryCode());
        if(tc!=null){
            tBoreholeCategoryMapper.updateByCode(tBoreholeCategory);
        }else{
            tBoreholeCategoryMapper.insertTBoreholeCategory(tBoreholeCategory);
        }
    }

    /**
     * 修改钻孔类型相关接口
     *
     * @param tBoreholeCategory 钻孔类型相关接口
     * @return 结果
     */
    @Override
    public Integer updateTBoreholeCategory(TBoreholeCategory tBoreholeCategory) {
        TBoreholeCategory tc=this.tBoreholeCategoryMapper.getByCode(tBoreholeCategory.getCategoryCode());
        if(tc==null){
            return tBoreholeCategoryMapper.insertTBoreholeCategory(tBoreholeCategory);
        }else{
            return tBoreholeCategoryMapper.updateByCode(tBoreholeCategory);
        }
    }

    /**
     * 批量删除钻孔类型相关接口
     *
     * @param ids 需要删除的钻孔类型相关接口主键
     * @return 结果
     */
    @Override
    public Integer deleteTBoreholeCategoryByIds(List<Integer> ids) {
        return tBoreholeCategoryMapper.deleteTBoreholeCategoryByIds(ids);
    }

    /**
     * 删除钻孔类型相关接口信息
     *
     * @param id 钻孔类型相关接口主键
     * @return 结果
     */
    @Override
    public Integer deleteTBoreholeCategoryById(Integer id) {
        return tBoreholeCategoryMapper.deleteTBoreholeCategoryById(id);
    }

    /**
     * 批量添加
     *
     * @param list
     */
    @Override
    public void insertTBoreholeCategoryList(List<TBoreholeCategory> list) {
        tBoreholeCategoryMapper.insertTBoreholeCategoryList(list);
    }

    /**
     * 查询列表
     *
     * @return 列表
     */
    @Override
    public List<TBoreholeCategory> getPageList() {
        return this.tBoreholeCategoryMapper.getPageList();
    }

    /**
     * 根据钻孔类别查询钻孔类别ID
     * @param boreholecategory 钻孔类别名称
     * @return 钻孔类别Id
     */
    @Override
    public Integer getIdByBoreholeCategory(String boreholecategory) {
        return this.tBoreholeCategoryMapper.getIdByBoreholeCategory(boreholecategory);
    }

    /**
     * 获取钻孔类别
     * @return
     */
    @Override
    public List<String> getBoreholeCategoryAll() {
        return this.tBoreholeCategoryMapper.getBoreholeCategoryAll();
    }
    /**
     * 通过类别编码获取钻孔类型
     * @param identificationCode
     * @return
     */
    @Override
    public TBoreholeCategory getByCode(Integer identificationCode) {
        return this.tBoreholeCategoryMapper.getByCode(identificationCode);
    }
    /**
     * 根据类别编码更改钻孔类型
     * @param tBoreholeCategory
     * @return
     */
    @Override
    public Integer updateByCode(TBoreholeCategory tBoreholeCategory) {
        return this.tBoreholeCategoryMapper.updateTBoreholeCategory(tBoreholeCategory);
    }


}
