package com.ruoyi.hh.util.mapper;

import com.ruoyi.hh.util.domain.TBoreholeCategory;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 钻孔类型相关接口Mapper接口
 *
 * @author eleven
 * @date 2022-07-13
 */
public interface TBoreholeCategoryMapper {
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
     * @return 结果
     */
    public Integer insertTBoreholeCategory(TBoreholeCategory tBoreholeCategory);

    /**
     * 修改钻孔类型相关接口
     *
     * @param tBoreholeCategory 钻孔类型相关接口
     * @return 结果
     */
    public Integer updateTBoreholeCategory(TBoreholeCategory tBoreholeCategory);

    /**
     * 删除钻孔类型相关接口
     *
     * @param id 钻孔类型相关接口主键
     * @return 结果
     */
    public Integer deleteTBoreholeCategoryById(Integer id);

    /**
     * 批量删除钻孔类型相关接口
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTBoreholeCategoryByIds(@RequestParam List<Integer> ids);

    /**
     * 批量添加钻孔类型相关接口
     */
    public void insertTBoreholeCategoryList(@RequestBody List<TBoreholeCategory> list);

    /**
     * 查询列表
     */
    public List<TBoreholeCategory> getPageList();

    /**
     * 根据钻孔类别查询钻孔类别ID
     * @param boreholeCategory 钻孔类别名称
     * @return 钻孔类别Id
     */
    public Integer getIdByBoreholeCategory( String boreholeCategory);


    /**
     * 获取钻孔类别
     * @return
     */
    @Select("select borehole_category from t_borehole_category GROUP BY borehole_category")
    public List<String> getBoreholeCategoryAll();

    /**
     * 通过类别编码获取钻孔类型
     * @param categoryCode
     * @return
     */
    @Select("select * from t_borehole_category where category_code=#{categoryCode}")
    public TBoreholeCategory getByCode(Integer categoryCode);

    /**
     * 根据类别编码更改钻孔类型
     * @param tBoreholeCategory
     * @return
     */
    public Integer updateByCode(TBoreholeCategory tBoreholeCategory);

    /**
     * 根据钻孔类型获取钻孔类型编码
     * @param boreholeCategory
     * @return
     */
    @Select("select category_code from t_borehole_category where borehole_category=#{boreholeCategory}")
    public Integer getCodeByBoreholeCategory(String boreholeCategory);

}
