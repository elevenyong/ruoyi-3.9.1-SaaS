package com.ruoyi.hh.simulate.mapper;

import com.ruoyi.hh.simulate.domain.CoalWaterDevData;

import java.util.List;

/**
 * 水煤分离之煤水计量装置Mapper接口
 * 
 * @author elevn
 * @date 2023-03-21
 */
public interface CoalWaterDevDataMapper 
{
    /**
     * 查询水煤分离之煤水计量装置
     * 
     * @param id 水煤分离之煤水计量装置主键
     * @return 水煤分离之煤水计量装置
     */
    public CoalWaterDevData selectCoalWaterDevDataById(Integer id);

    /**
     * 查询水煤分离之煤水计量装置列表
     * 
     * @param coalWaterDevData 水煤分离之煤水计量装置
     * @return 水煤分离之煤水计量装置集合
     */
    public List<CoalWaterDevData> selectCoalWaterDevDataList(CoalWaterDevData coalWaterDevData);

    /**
     * 新增水煤分离之煤水计量装置
     * 
     * @param coalWaterDevData 水煤分离之煤水计量装置
     * @return 结果
     */
    public int insertCoalWaterDevData(CoalWaterDevData coalWaterDevData);

    /**
     * 修改水煤分离之煤水计量装置
     * 
     * @param coalWaterDevData 水煤分离之煤水计量装置
     * @return 结果
     */
    public int updateCoalWaterDevData(CoalWaterDevData coalWaterDevData);

    /**
     * 删除水煤分离之煤水计量装置
     * 
     * @param id 水煤分离之煤水计量装置主键
     * @return 结果
     */
    public int deleteCoalWaterDevDataById(Integer id);

    /**
     * 批量删除水煤分离之煤水计量装置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCoalWaterDevDataByIds(Integer[] ids);
}
