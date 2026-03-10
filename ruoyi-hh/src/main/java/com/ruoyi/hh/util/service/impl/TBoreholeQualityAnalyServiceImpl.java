package com.ruoyi.hh.util.service.impl;

import java.util.List;

import com.ruoyi.hh.util.domain.TBoreholeQualityAnaly;
import com.ruoyi.hh.util.mapper.TBoreholeQualityAnalyMapper;
import com.ruoyi.hh.util.service.ITBoreholeQualityAnalyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 钻孔质量分析表Service业务层处理
 *
 * @author eleven
 * @date 2022-07-18
 */
@Service
public class TBoreholeQualityAnalyServiceImpl implements ITBoreholeQualityAnalyService {
    @Resource
    private TBoreholeQualityAnalyMapper tBoreholeQualityAnalyMapper;

    /**
     * 查询钻孔质量分析表
     *
     * @param id 钻孔质量分析表主键
     * @return 钻孔质量分析表
     */
    @Override
    public TBoreholeQualityAnaly selectTBoreholeQualityAnalyById(Integer id) {
        return tBoreholeQualityAnalyMapper.selectTBoreholeQualityAnalyById(id);
    }

    /**
     * 查询钻孔质量分析表列表
     *
     * @param tBoreholeQualityAnaly 钻孔质量分析表
     * @return 钻孔质量分析表
     */
    @Override
    public List<TBoreholeQualityAnaly> selectTBoreholeQualityAnalyList(TBoreholeQualityAnaly tBoreholeQualityAnaly) {
        return tBoreholeQualityAnalyMapper.selectTBoreholeQualityAnalyList(tBoreholeQualityAnaly);
    }

    /**
     * 新增钻孔质量分析表
     *
     * @param tBoreholeQualityAnaly 钻孔质量分析表
     * @return 结果
     */
    @Override
    public Integer insertTBoreholeQualityAnaly(TBoreholeQualityAnaly tBoreholeQualityAnaly) {
        return tBoreholeQualityAnalyMapper.insertTBoreholeQualityAnaly(tBoreholeQualityAnaly);
    }

    /**
     * 修改钻孔质量分析表
     *
     * @param tBoreholeQualityAnaly 钻孔质量分析表
     * @return 结果
     */
    @Override
    public Integer updateTBoreholeQualityAnaly(TBoreholeQualityAnaly tBoreholeQualityAnaly) {
        return tBoreholeQualityAnalyMapper.updateTBoreholeQualityAnaly(tBoreholeQualityAnaly);
    }

    /**
     * 批量删除钻孔质量分析表
     *
     * @param ids 需要删除的钻孔质量分析表主键
     * @return 结果
     */
    @Override
    public Integer deleteTBoreholeQualityAnalyByIds(List<Integer> ids) {
        return tBoreholeQualityAnalyMapper.deleteTBoreholeQualityAnalyByIds(ids);
    }

    /**
     * 删除钻孔质量分析表信息
     *
     * @param id 钻孔质量分析表主键
     * @return 结果
     */
    @Override
    public Integer deleteTBoreholeQualityAnalyById(Integer id) {
        return tBoreholeQualityAnalyMapper.deleteTBoreholeQualityAnalyById(id);
    }

    @Override
    public List<TBoreholeQualityAnaly> getPageList() {
        return this.tBoreholeQualityAnalyMapper.selectTBoreholeQualityAnalyVo();
    }
}
