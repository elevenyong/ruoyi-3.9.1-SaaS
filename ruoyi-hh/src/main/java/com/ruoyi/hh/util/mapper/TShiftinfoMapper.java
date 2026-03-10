package com.ruoyi.hh.util.mapper;

import com.ruoyi.hh.util.domain.TShiftinfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 班次信息Mapper接口
 *
 * @author eleven
 * @date 2022-07-18
 */
public interface TShiftinfoMapper {
    /**
     * 查询班次信息
     *
     * @param id 班次信息主键
     * @return 班次信息
     */
    public TShiftinfo selectTShiftinfoById(Integer id);

    /**
     * 查询班次信息列表
     *
     * @param tShiftinfo 班次信息
     * @return 班次信息集合
     */
    public List<TShiftinfo> selectTShiftinfoList(TShiftinfo tShiftinfo);

    /**
     * 新增班次信息
     *
     * @param tShiftinfo 班次信息
     * @return 结果
     */
    public Integer insertTShiftinfo(TShiftinfo tShiftinfo);

    /**
     * 修改班次信息
     *
     * @param tShiftinfo 班次信息
     * @return 结果
     */
    public Integer updateTShiftinfo(TShiftinfo tShiftinfo);

    /**
     * 删除班次信息
     *
     * @param id 班次信息主键
     * @return 结果
     */
    public Integer deleteTShiftinfoById(Integer id);

    /**
     * 批量删除班次信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTShiftinfoByIds(@RequestParam List<Integer> ids);

    public List<TShiftinfo> getPageList();

    /**
     * 根据班次名称查询班次ID
     * @param shiftinfo 班次名称
     * @return 班次ID
     */
    public Integer getIdByName(String shiftinfo);

    /**
     * 获取所有班次信息
     * @return
     */
    @Select("select shift_info from t_shiftinfo GROUP BY shift_info")
    public List<String> getShiftInfoAll();
}
