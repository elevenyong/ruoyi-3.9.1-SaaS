package com.ruoyi.hh.util.mapper;

import java.util.List;

import com.ruoyi.hh.util.domain.TDataType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 煤层数据类型表Mapper接口
 *
 * @author eleven
 * @date 2022-07-14
 */
public interface TDataTypeMapper {
    /**
     * 查询煤层数据类型表
     *
     * @param id 煤层数据类型表主键
     * @return 煤层数据类型表
     */
    public TDataType selectTDataTypeById(Integer id);

    /**
     * 查询煤层数据类型表列表
     *
     * @param tDataType 煤层数据类型表
     * @return 煤层数据类型表集合
     */
    public List<TDataType> selectTDataTypeList(TDataType tDataType);

    /**
     * 新增煤层数据类型表
     *
     * @param tDataType 煤层数据类型表
     * @return 结果
     */
    public Integer insertTDataType(TDataType tDataType);

    /**
     * 修改煤层数据类型表
     *
     * @param tDataType 煤层数据类型表
     * @return 结果
     */
    public Integer updateTDataType(TDataType tDataType);

    /**
     * 删除煤层数据类型表
     *
     * @param id 煤层数据类型表主键
     * @return 结果
     */
    public Integer deleteTDataTypeById(Integer id);

    /**
     * 批量删除煤层数据类型表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTDataTypeByIds(@RequestParam List<Integer> ids);

    /**
     * 分页查询
     *
     * @return 列表
     */
    public List<TDataType> getPageList();

    /**
     * 批量添加数据
     *
     * @param list 列表
     * @return 影响行数
     */
    public Integer insertTDataTypeList(@RequestBody List<TDataType> list);
}
