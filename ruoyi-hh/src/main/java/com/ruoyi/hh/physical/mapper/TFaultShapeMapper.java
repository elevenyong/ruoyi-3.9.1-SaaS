package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TFaultShape;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 断层信息表Mapper接口
 *
 * @author eleven
 * @date 2022-07-18
 */
public interface TFaultShapeMapper {
    /**
     * 查询断层信息表
     *
     * @param id 断层信息表主键
     * @return 断层信息表
     */
    public TFaultShape selectTFaultShapeById(Integer id);

    /**
     * 查询断层信息表列表
     *
     * @param tFaultShape 断层信息表
     * @return 断层信息表集合
     */
    public List<TFaultShape> selectTFaultShapeList(TFaultShape tFaultShape);

    /**
     * 新增断层信息表
     *
     * @param tFaultShape 断层信息表
     * @return 结果
     */
    public Integer insertTFaultShape(TFaultShape tFaultShape);

    /**
     * 修改断层信息表
     *
     * @param tFaultShape 断层信息表
     * @return 结果
     */
    public Integer updateTFaultShape(TFaultShape tFaultShape);

    /**
     * 删除断层信息表
     *
     * @param id 断层信息表主键
     * @return 结果
     */
    public Integer deleteTFaultShapeById(Integer id);

    /**
     * 批量删除断层信息表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTFaultShapeByIds(@RequestParam List<Integer> ids);

    /**
     * 获取所有信息
     *
     * @return 列表
     */
    public List<TFaultShape> selectTFaultShapeVo();

    /**
     * 根据断层名称查询断层ID
     * @param shape 断层名称
     * @return ID
     */
    public Integer getIdByShape(String shape);
}
