package com.ruoyi.hh.simulate.service.impl;

import java.util.List;

import com.ruoyi.hh.simulate.domain.GasDevData;
import com.ruoyi.hh.simulate.mapper.GasDevDataMapper;
import com.ruoyi.hh.simulate.service.IGasDevDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 水煤分离之瓦斯计量装置信息Service业务层处理
 * 
 * @author eleven
 * @date 2023-03-21
 */
@Service
public class GasDevDataServiceImpl implements IGasDevDataService
{
    @Autowired
    private GasDevDataMapper gasDevDataMapper;

    /**
     * 查询水煤分离之瓦斯计量装置信息
     * 
     * @param id 水煤分离之瓦斯计量装置信息主键
     * @return 水煤分离之瓦斯计量装置信息
     */
    @Override
    public GasDevData selectGasDevDataById(Integer id)
    {
        return gasDevDataMapper.selectGasDevDataById(id);
    }

    /**
     * 查询水煤分离之瓦斯计量装置信息列表
     * 
     * @param gasDevData 水煤分离之瓦斯计量装置信息
     * @return 水煤分离之瓦斯计量装置信息
     */
    @Override
    public List<GasDevData> selectGasDevDataList(GasDevData gasDevData)
    {
        return gasDevDataMapper.selectGasDevDataList(gasDevData);
    }

    /**
     * 新增水煤分离之瓦斯计量装置信息
     * 
     * @param gasDevData 水煤分离之瓦斯计量装置信息
     * @return 结果
     */
    @Override
    public int insertGasDevData(GasDevData gasDevData)
    {
        return gasDevDataMapper.insertGasDevData(gasDevData);
    }

    /**
     * 修改水煤分离之瓦斯计量装置信息
     * 
     * @param gasDevData 水煤分离之瓦斯计量装置信息
     * @return 结果
     */
    @Override
    public int updateGasDevData(GasDevData gasDevData)
    {
        return gasDevDataMapper.updateGasDevData(gasDevData);
    }

    /**
     * 批量删除水煤分离之瓦斯计量装置信息
     * 
     * @param ids 需要删除的水煤分离之瓦斯计量装置信息主键
     * @return 结果
     */
    @Override
    public int deleteGasDevDataByIds(Integer[] ids)
    {
        return gasDevDataMapper.deleteGasDevDataByIds(ids);
    }

    /**
     * 删除水煤分离之瓦斯计量装置信息信息
     * 
     * @param id 水煤分离之瓦斯计量装置信息主键
     * @return 结果
     */
    @Override
    public int deleteGasDevDataById(Integer id)
    {
        return gasDevDataMapper.deleteGasDevDataById(id);
    }
}
