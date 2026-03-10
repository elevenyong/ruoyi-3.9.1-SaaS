package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TSubstationInfo;
import org.apache.ibatis.annotations.Select;

/**
 * 分站信息Mapper接口
 * 
 * @author eleven
 * @date 2023-05-16
 */
public interface TSubstationInfoMapper 
{
    /**
     * 查询分站信息
     * 
     * @param id 分站信息主键
     * @return 分站信息
     */
    public TSubstationInfo selectTSubstationInfoById(Integer id);

    /**
     * 查询分站信息列表
     * 
     * @param tSubstationInfo 分站信息
     * @return 分站信息集合
     */
    public List<TSubstationInfo> selectTSubstationInfoList(TSubstationInfo tSubstationInfo);

    /**
     * 新增分站信息
     * 
     * @param tSubstationInfo 分站信息
     * @return 结果
     */
    public int insertTSubstationInfo(TSubstationInfo tSubstationInfo);

    /**
     * 修改分站信息
     * 
     * @param tSubstationInfo 分站信息
     * @return 结果
     */
    public int updateTSubstationInfo(TSubstationInfo tSubstationInfo);

    /**
     * 删除分站信息
     * 
     * @param id 分站信息主键
     * @return 结果
     */
    public int deleteTSubstationInfoById(Integer id);

    /**
     * 批量删除分站信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTSubstationInfoByIds(Integer[] ids);

    /**
     * 根据分站编码查询分站信息
     * @param substationCode 分站编码
     * @return 分站信息
     */
    @Select("select * from t_substation_info where substation_code = #{substationCode}")
    public TSubstationInfo getBySubstationCode(String substationCode);

    /**
     * 根据分站编码修改分站信息
     * @param tSubstationInfo 分站信息
     * @return 结果
     */
    public int updateBySubstationCode(TSubstationInfo tSubstationInfo);
}
