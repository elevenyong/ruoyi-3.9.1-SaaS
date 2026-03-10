package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TGroundElevation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 地面高程点信息Mapper接口
 *
 * @author eleven
 * @date 2022-10-20
 */
public interface TGroundElevationMapper {
    /**
     * 查询地面高程点信息
     *
     * @param id 地面高程点信息主键
     * @return 地面高程点信息
     */
    public TGroundElevation selectTGroundElevationById(Integer id);

    /**
     * 查询地面高程点信息列表
     *
     * @param tGroundElevation 地面高程点信息
     * @return 地面高程点信息集合
     */
    public List<TGroundElevation> selectTGroundElevationList(TGroundElevation tGroundElevation);

    /**
     * 新增地面高程点信息
     *
     * @param tGroundElevation 地面高程点信息
     * @return 结果
     */
    public int insertTGroundElevation(TGroundElevation tGroundElevation);

    /**
     * 修改地面高程点信息
     *
     * @param tGroundElevation 地面高程点信息
     * @return 结果
     */
    public int updateTGroundElevation(TGroundElevation tGroundElevation);

    /**
     * 删除地面高程点信息
     *
     * @param id 地面高程点信息主键
     * @return 结果
     */
    public int deleteTGroundElevationById(Integer id);

    /**
     * 批量删除地面高程点信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTGroundElevationByIds(Integer[] ids);

    /**
     * 根据工作面ID查询地面高程点列表
     *
     * @param workfaceId 工作面ID
     * @return 地面高程点列表
     */
    public List<TGroundElevation> getByWorkfaceId(Integer workfaceId);


    /**
     * 根据工作面ID删除地面高程点
     * @param workfaceId 工作面ID
     * @return 删除条数
     */
    public int deleteTGroundElevationByWorkfaceId(Integer workfaceId);
}
