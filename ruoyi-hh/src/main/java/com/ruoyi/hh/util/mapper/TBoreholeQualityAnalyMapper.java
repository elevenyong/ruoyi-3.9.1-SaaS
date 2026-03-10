package com.ruoyi.hh.util.mapper;

import java.util.List;

import com.ruoyi.hh.util.domain.TBoreholeQualityAnaly;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 钻孔质量分析表Mapper接口
 *
 * @author eleven
 * @date 2022-07-18
 */
public interface TBoreholeQualityAnalyMapper {
    /**
     * 查询钻孔质量分析表
     *
     * @param id 钻孔质量分析表主键
     * @return 钻孔质量分析表
     */
    public TBoreholeQualityAnaly selectTBoreholeQualityAnalyById(Integer id);

    /**
     * 查询钻孔质量分析表列表
     *
     * @param tBoreholeQualityAnaly 钻孔质量分析表
     * @return 钻孔质量分析表集合
     */
    public List<TBoreholeQualityAnaly> selectTBoreholeQualityAnalyList(TBoreholeQualityAnaly tBoreholeQualityAnaly);

    /**
     * 新增钻孔质量分析表
     *
     * @param tBoreholeQualityAnaly 钻孔质量分析表
     * @return 结果
     */
    public Integer insertTBoreholeQualityAnaly(TBoreholeQualityAnaly tBoreholeQualityAnaly);

    /**
     * 修改钻孔质量分析表
     *
     * @param tBoreholeQualityAnaly 钻孔质量分析表
     * @return 结果
     */
    public Integer updateTBoreholeQualityAnaly(TBoreholeQualityAnaly tBoreholeQualityAnaly);

    /**
     * 删除钻孔质量分析表
     *
     * @param id 钻孔质量分析表主键
     * @return 结果
     */
    public Integer deleteTBoreholeQualityAnalyById(Integer id);

    /**
     * 批量删除钻孔质量分析表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTBoreholeQualityAnalyByIds(@RequestParam List<Integer> ids);

    public List<TBoreholeQualityAnaly> selectTBoreholeQualityAnalyVo();
}
