package com.ruoyi.hh.physical.mapper;

import java.util.List;

import com.ruoyi.hh.physical.domain.TCoalinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 煤层信息表Mapper接口
 *
 * @author eleven
 * @date 2022-07-18
 */
public interface TCoalinfoMapper {
    /**
     * 查询煤层信息表
     *
     * @param id 煤层信息表主键
     * @return 煤层信息表
     */
    public TCoalinfo selectTCoalinfoById(Integer id);

    /**
     * 查询煤层信息表列表
     *
     * @param tCoalinfo 煤层信息表
     * @return 煤层信息表集合
     */
    public List<TCoalinfo> selectTCoalinfoList(TCoalinfo tCoalinfo);

    /**
     * 新增煤层信息表
     *
     * @param tCoalinfo 煤层信息表
     * @return 结果
     */
    public Integer insertTCoalinfo(TCoalinfo tCoalinfo);

    /**
     * 修改煤层信息表
     *
     * @param tCoalinfo 煤层信息表
     * @return 结果
     */
    public Integer updateTCoalinfo(TCoalinfo tCoalinfo);

    /**
     * 删除煤层信息表
     *
     * @param id 煤层信息表主键
     * @return 结果
     */
    public Integer deleteTCoalinfoById(Integer id);

    /**
     * 批量删除煤层信息表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTCoalinfoByIds(@RequestParam List<Integer> ids);

    public List<TCoalinfo> selectTCoalinfoVo();

    /**
     * 根据工作面ID删除煤层信息
     * @param workingFaceIds 工作面ID
     * @return 结果
     */
    public Integer delByWorkingFaceIds(@RequestParam  List<Integer>  workingFaceIds);

    /**
     * 根据工作面ID和评价单元ID查询所属的煤层信息
     * @param workfaceid 工作面ID
     * @return 列表
     */
    public TCoalinfo getCoalInfo(@Param("workfaceid")Integer workfaceid);

}
