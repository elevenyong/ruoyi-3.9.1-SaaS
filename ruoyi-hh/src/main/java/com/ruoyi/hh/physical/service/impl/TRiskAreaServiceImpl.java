package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.physical.domain.TRiskArea;
import com.ruoyi.hh.physical.mapper.TRiskAreaMapper;
import com.ruoyi.hh.physical.service.ITRiskAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TRiskAreaServiceImpl implements ITRiskAreaService {
    @Resource
    private TRiskAreaMapper tRiskAreaMapper;

    /**
     * 查询风险区域
     *
     * @param id 风险区域主键
     * @return 风险区域
     */
    @Override
    public TRiskArea selectTRiskAreaById(Long id) {
        return tRiskAreaMapper.selectTRiskAreaById(id);
    }

    /**
     * 查询风险区域列表
     *
     * @param tRiskArea 风险区域
     * @return 风险区域
     */
    @Override
    public List<TRiskArea> selectTRiskAreaList(TRiskArea tRiskArea) {
        return tRiskAreaMapper.selectTRiskAreaList(tRiskArea);
    }

    /**
     * 新增风险区域
     *
     * @param tRiskArea 风险区域
     * @return 结果
     */
    @Override
    public int insertTRiskArea(TRiskArea tRiskArea) {
        return tRiskAreaMapper.insertTRiskArea(tRiskArea);
    }

    /**
     * 修改风险区域
     *
     * @param tRiskArea 风险区域
     * @return 结果
     */
    @Override
    public int updateTRiskArea(TRiskArea tRiskArea) {
        return tRiskAreaMapper.updateTRiskArea(tRiskArea);
    }

    /**
     * 批量删除风险区域
     *
     * @param ids 需要删除的风险区域主键
     * @return 结果
     */
    @Override
    public int deleteTRiskAreaByIds(Long[] ids) {
        return tRiskAreaMapper.deleteTRiskAreaByIds(ids);
    }

    /**
     * 删除风险区域信息
     *
     * @param id 风险区域主键
     * @return 结果
     */
    @Override
    public int deleteTRiskAreaById(Long id) {
        return tRiskAreaMapper.deleteTRiskAreaById(id);
    }

}
