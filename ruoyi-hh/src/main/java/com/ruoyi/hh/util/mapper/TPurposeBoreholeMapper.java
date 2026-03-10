package com.ruoyi.hh.util.mapper;

import com.ruoyi.hh.util.domain.TPurposeBorehole;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 钻孔用途相关Mapper接口
 *
 * @author eleven
 * @date 2022-07-13
 */
public interface TPurposeBoreholeMapper {
    /**
     * 查询钻孔用途相关
     *
     * @param id 钻孔用途相关主键
     * @return 钻孔用途相关
     */
    public TPurposeBorehole selectTPurposeBoreholeById(Integer id);

    /**
     * 查询钻孔用途相关列表
     *
     * @param tPurposeBorehole 钻孔用途相关
     * @return 钻孔用途相关集合
     */
    public List<TPurposeBorehole> selectTPurposeBoreholeList(TPurposeBorehole tPurposeBorehole);

    /**
     * 新增钻孔用途相关
     *
     * @param tPurposeBorehole 钻孔用途相关
     */
    public void insertTPurposeBorehole(TPurposeBorehole tPurposeBorehole);

    /**
     * 修改钻孔用途相关
     *
     * @param tPurposeBorehole 钻孔用途相关
     * @return 结果
     */
    public Integer updateTPurposeBorehole(TPurposeBorehole tPurposeBorehole);

    /**
     * 删除钻孔用途相关
     *
     * @param id 钻孔用途相关主键
     * @return 结果
     */
    public Integer deleteTPurposeBoreholeById(@RequestParam Integer id);

    /**
     * 批量删除钻孔用途相关
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTPurposeBoreholeByIds(@RequestParam("ids") List<Integer> ids);

    /**
     * 批量添加钻孔用途信息
     *
     * @param list
     */
    public void insertTPurposeBoreholeList(@RequestBody List<TPurposeBorehole> list);

    /**
     * 分页查询
     *
     * @return 列表
     */
    public List<TPurposeBorehole> getPageList();

    /**
     * 根据钻孔用途查询ID
     * @param purposeborehole 钻孔用途
     * @return ID
     */
    public Integer getIdByPurposeBorehole(String purposeborehole);

    /**
     * 获取钻孔用途
     * @return
     */
    @Select("select purpose_borehole from t_purpose_borehole GROUP BY purpose_borehole")
    public List<String> getPurposeBoreholeAll();
}
