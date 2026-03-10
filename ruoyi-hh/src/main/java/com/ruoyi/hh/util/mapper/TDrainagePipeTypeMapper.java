package com.ruoyi.hh.util.mapper;

import com.ruoyi.hh.util.domain.TDrainagePipeType;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 抽采管路类型Mapper接口
 *
 * @author eleven
 * @CreateTime: 2023/8/11 17:48
 */
public interface TDrainagePipeTypeMapper {
    /**
     * 查询抽采管路类型信息
     *
     * @param id 抽采管路类型信息主键
     * @return 抽采管路类型信息
     */
    public TDrainagePipeType selectTDrainagePipeTypeById(Integer id);

    /**
     * 查询抽采管路类型信息列表
     *
     * @param tDrainagePipeType 抽采管路类型信息
     * @return 抽采管路类型信息集合
     */
    public List<TDrainagePipeType> selectTDrainagePipeTypeList(TDrainagePipeType tDrainagePipeType);

    /**
     * 新增抽采管路类型信息
     *
     * @param tDrainagePipeType 抽采管路类型信息
     * @return 结果
     */
    public int insertTDrainagePipeType(TDrainagePipeType tDrainagePipeType);

    /**
     * 修改抽采管路类型信息
     *
     * @param tDrainagePipeType 抽采管路类型信息
     * @return 结果
     */
    public int updateTDrainagePipeType(TDrainagePipeType tDrainagePipeType);

    /**
     * 删除抽采管路类型信息
     *
     * @param id 抽采管路类型信息主键
     * @return 结果
     */
    public int deleteTDrainagePipeTypeById(Integer id);

    /**
     * 批量删除抽采管路类型信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDrainagePipeTypeByIds(@RequestParam("ids") List<Integer> ids);
}
