package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hh.physical.domain.TGroundElevation;
import com.ruoyi.hh.physical.mapper.TGroundElevationMapper;
import com.ruoyi.hh.physical.service.ITGroundElevationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地面高程点信息Service业务层处理
 *
 * @author eleven
 * @date 2022-10-20
 */
@Service
public class TGroundElevationServiceImpl implements ITGroundElevationService {
    @Resource
    private TGroundElevationMapper tGroundElevationMapper;

    /**
     * 查询地面高程点信息
     *
     * @param id 地面高程点信息主键
     * @return 地面高程点信息
     */
    @Override
    public TGroundElevation selectTGroundElevationById(Integer id) {
        return tGroundElevationMapper.selectTGroundElevationById(id);
    }

    /**
     * 查询地面高程点信息列表
     *
     * @param TGroundElevation 地面高程点信息
     * @return 地面高程点信息
     */
    @Override
    public List<TGroundElevation> selectTGroundElevationList(TGroundElevation TGroundElevation) {
        return tGroundElevationMapper.selectTGroundElevationList(TGroundElevation);
    }

    /**
     * 新增地面高程点信息
     *
     * @param TGroundElevation 地面高程点信息
     * @return 结果
     */
    @Override
    public int insertTGroundElevation(TGroundElevation TGroundElevation) {
        return tGroundElevationMapper.insertTGroundElevation(TGroundElevation);
    }

    /**
     * 批量插入地面高程点信息
     * @param tGroundElevationList 地面高程点信息列表
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertList(List<TGroundElevation> tGroundElevationList) {
        int suc = 0;
        int err = 0;
        if (tGroundElevationList != null && !tGroundElevationList.isEmpty()) {
            deleteTGroundElevationByWorkfaceId(tGroundElevationList.get(0).getWorkfaceId());
            for (TGroundElevation TGroundElevation : tGroundElevationList) {
                int res = insertTGroundElevation(TGroundElevation);
                if (res > 0) {
                    suc++;
                } else {
                    err++;
                }
            }
            return suc;
        } else {
            return 0;
        }
    }

    /**
     * 修改地面高程点信息
     *
     * @param TGroundElevation 地面高程点信息
     * @return 结果
     */
    @Override
    public int updateTGroundElevation(TGroundElevation TGroundElevation) {
        return tGroundElevationMapper.updateTGroundElevation(TGroundElevation);
    }

    /**
     * 批量删除地面高程点信息
     *
     * @param ids 需要删除的地面高程点信息主键
     * @return 结果
     */
    @Override
    public int deleteTGroundElevationByIds(Integer[] ids) {
        return tGroundElevationMapper.deleteTGroundElevationByIds(ids);
    }

    /**
     * 删除地面高程点信息信息
     *
     * @param id 地面高程点信息主键
     * @return 结果
     */
    @Override
    public int deleteTGroundElevationById(Integer id) {
        return tGroundElevationMapper.deleteTGroundElevationById(id);
    }

    @Override
    public int deleteTGroundElevationByWorkfaceId(Integer workfaceID) {
        return tGroundElevationMapper.deleteTGroundElevationByWorkfaceId(workfaceID);
    }
}
