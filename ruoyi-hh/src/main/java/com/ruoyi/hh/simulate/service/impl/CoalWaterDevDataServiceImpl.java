package com.ruoyi.hh.simulate.service.impl;

import java.util.List;

import com.ruoyi.hh.simulate.domain.CoalWaterDevData;
import com.ruoyi.hh.simulate.mapper.CoalWaterDevDataMapper;
import com.ruoyi.hh.simulate.service.ICoalWaterDevDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 水煤分离之煤水计量装置Service业务层处理
 * 
 * @author elevn
 * @date 2023-03-21
 */
@Service
public class CoalWaterDevDataServiceImpl implements ICoalWaterDevDataService
{
    @Autowired
    private CoalWaterDevDataMapper coalWaterDevDataMapper;

    /**
     * 查询水煤分离之煤水计量装置
     * 
     * @param id 水煤分离之煤水计量装置主键
     * @return 水煤分离之煤水计量装置
     */
    @Override
    public CoalWaterDevData selectCoalWaterDevDataById(Integer id)
    {
        return coalWaterDevDataMapper.selectCoalWaterDevDataById(id);
    }

    /**
     * 查询水煤分离之煤水计量装置列表
     * 
     * @param coalWaterDevData 水煤分离之煤水计量装置
     * @return 水煤分离之煤水计量装置
     */
    @Override
    public List<CoalWaterDevData> selectCoalWaterDevDataList(CoalWaterDevData coalWaterDevData)
    {
        return coalWaterDevDataMapper.selectCoalWaterDevDataList(coalWaterDevData);
    }

    /**
     * 新增水煤分离之煤水计量装置
     * 
     * @param coalWaterDevData 水煤分离之煤水计量装置
     * @return 结果
     */
    @Override
    public int insertCoalWaterDevData(CoalWaterDevData coalWaterDevData)
    {
        return coalWaterDevDataMapper.insertCoalWaterDevData(coalWaterDevData);
    }

    /**
     * 修改水煤分离之煤水计量装置
     * 
     * @param coalWaterDevData 水煤分离之煤水计量装置
     * @return 结果
     */
    @Override
    public int updateCoalWaterDevData(CoalWaterDevData coalWaterDevData)
    {
        return coalWaterDevDataMapper.updateCoalWaterDevData(coalWaterDevData);
    }

    /**
     * 批量删除水煤分离之煤水计量装置
     * 
     * @param ids 需要删除的水煤分离之煤水计量装置主键
     * @return 结果
     */
    @Override
    public int deleteCoalWaterDevDataByIds(Integer[] ids)
    {
        return coalWaterDevDataMapper.deleteCoalWaterDevDataByIds(ids);
    }

    /**
     * 删除水煤分离之煤水计量装置信息
     * 
     * @param id 水煤分离之煤水计量装置主键
     * @return 结果
     */
    @Override
    public int deleteCoalWaterDevDataById(Integer id)
    {
        return coalWaterDevDataMapper.deleteCoalWaterDevDataById(id);
    }
}
