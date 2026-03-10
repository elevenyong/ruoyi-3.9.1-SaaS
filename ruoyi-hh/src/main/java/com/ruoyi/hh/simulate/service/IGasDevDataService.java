package com.ruoyi.hh.simulate.service;

import com.ruoyi.hh.simulate.domain.GasDevData;

import java.util.List;

/**
 * 水煤分离之瓦斯计量装置信息Service接口
 * 
 * @author eleven
 * @date 2023-03-21
 */
public interface IGasDevDataService 
{
    /**
     * 查询水煤分离之瓦斯计量装置信息
     * 
     * @param id 水煤分离之瓦斯计量装置信息主键
     * @return 水煤分离之瓦斯计量装置信息
     */
    public GasDevData selectGasDevDataById(Integer id);

    /**
     * 查询水煤分离之瓦斯计量装置信息列表
     * 
     * @param gasDevData 水煤分离之瓦斯计量装置信息
     * @return 水煤分离之瓦斯计量装置信息集合
     */
    public List<GasDevData> selectGasDevDataList(GasDevData gasDevData);

    /**
     * 新增水煤分离之瓦斯计量装置信息
     * 
     * @param gasDevData 水煤分离之瓦斯计量装置信息
     * @return 结果
     */
    public int insertGasDevData(GasDevData gasDevData);

    /**
     * 修改水煤分离之瓦斯计量装置信息
     * 
     * @param gasDevData 水煤分离之瓦斯计量装置信息
     * @return 结果
     */
    public int updateGasDevData(GasDevData gasDevData);

    /**
     * 批量删除水煤分离之瓦斯计量装置信息
     * 
     * @param ids 需要删除的水煤分离之瓦斯计量装置信息主键集合
     * @return 结果
     */
    public int deleteGasDevDataByIds(Integer[] ids);

    /**
     * 删除水煤分离之瓦斯计量装置信息信息
     * 
     * @param id 水煤分离之瓦斯计量装置信息主键
     * @return 结果
     */
    public int deleteGasDevDataById(Integer id);
}
