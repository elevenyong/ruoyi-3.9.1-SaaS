package com.ruoyi.hh.simulate.mapper;

import com.ruoyi.hh.simulate.domain.WaterDevData;

import java.util.List;

/**
 * 水煤分离之清水计量装置Mapper接口
 * 
 * @author eleven
 * @date 2023-03-21
 */
public interface WaterDevDataMapper 
{
    /**
     * 查询水煤分离之清水计量装置
     * 
     * @param id 水煤分离之清水计量装置主键
     * @return 水煤分离之清水计量装置
     */
    public WaterDevData selectWaterDevDataById(Integer id);

    /**
     * 查询水煤分离之清水计量装置列表
     * 
     * @param waterDevData 水煤分离之清水计量装置
     * @return 水煤分离之清水计量装置集合
     */
    public List<WaterDevData> selectWaterDevDataList(WaterDevData waterDevData);

    /**
     * 新增水煤分离之清水计量装置
     * 
     * @param waterDevData 水煤分离之清水计量装置
     * @return 结果
     */
    public int insertWaterDevData(WaterDevData waterDevData);

    /**
     * 修改水煤分离之清水计量装置
     * 
     * @param waterDevData 水煤分离之清水计量装置
     * @return 结果
     */
    public int updateWaterDevData(WaterDevData waterDevData);

    /**
     * 删除水煤分离之清水计量装置
     * 
     * @param id 水煤分离之清水计量装置主键
     * @return 结果
     */
    public int deleteWaterDevDataById(Integer id);

    /**
     * 批量删除水煤分离之清水计量装置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWaterDevDataByIds(Integer[] ids);
}
