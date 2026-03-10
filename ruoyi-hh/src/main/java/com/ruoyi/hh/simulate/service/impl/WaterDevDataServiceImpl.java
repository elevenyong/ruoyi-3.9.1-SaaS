package com.ruoyi.hh.simulate.service.impl;

import java.util.List;

import com.ruoyi.hh.simulate.domain.WaterDevData;
import com.ruoyi.hh.simulate.mapper.WaterDevDataMapper;
import com.ruoyi.hh.simulate.service.IWaterDevDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 水煤分离之清水计量装置Service业务层处理
 * 
 * @author eleven
 * @date 2023-03-21
 */
@Service
public class WaterDevDataServiceImpl implements IWaterDevDataService
{
    @Autowired
    private WaterDevDataMapper waterDevDataMapper;

    /**
     * 查询水煤分离之清水计量装置
     * 
     * @param id 水煤分离之清水计量装置主键
     * @return 水煤分离之清水计量装置
     */
    @Override
    public WaterDevData selectWaterDevDataById(Integer id)
    {
        return waterDevDataMapper.selectWaterDevDataById(id);
    }

    /**
     * 查询水煤分离之清水计量装置列表
     * 
     * @param waterDevData 水煤分离之清水计量装置
     * @return 水煤分离之清水计量装置
     */
    @Override
    public List<WaterDevData> selectWaterDevDataList(WaterDevData waterDevData)
    {
        return waterDevDataMapper.selectWaterDevDataList(waterDevData);
    }

    /**
     * 新增水煤分离之清水计量装置
     * 
     * @param waterDevData 水煤分离之清水计量装置
     * @return 结果
     */
    @Override
    public int insertWaterDevData(WaterDevData waterDevData)
    {
        return waterDevDataMapper.insertWaterDevData(waterDevData);
    }

    /**
     * 修改水煤分离之清水计量装置
     * 
     * @param waterDevData 水煤分离之清水计量装置
     * @return 结果
     */
    @Override
    public int updateWaterDevData(WaterDevData waterDevData)
    {
        return waterDevDataMapper.updateWaterDevData(waterDevData);
    }

    /**
     * 批量删除水煤分离之清水计量装置
     * 
     * @param ids 需要删除的水煤分离之清水计量装置主键
     * @return 结果
     */
    @Override
    public int deleteWaterDevDataByIds(Integer[] ids)
    {
        return waterDevDataMapper.deleteWaterDevDataByIds(ids);
    }

    /**
     * 删除水煤分离之清水计量装置信息
     * 
     * @param id 水煤分离之清水计量装置主键
     * @return 结果
     */
    @Override
    public int deleteWaterDevDataById(Integer id)
    {
        return waterDevDataMapper.deleteWaterDevDataById(id);
    }
}
