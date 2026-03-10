package com.ruoyi.hh.physical.mapper;

import com.ruoyi.hh.physical.domain.TWorkingFace;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 工作面信息Mapper接口
 *
 * @author eleven
 * @date 2022-07-18
 */
public interface TWorkingFaceMapper {
    /**
     * 查询工作面信息
     *
     * @param id 工作面信息主键
     * @return 工作面信息
     */
    public TWorkingFace selectTWorkingFaceById(Integer id);

    /**
     * 查询工作面信息列表
     *
     * @param tWorkingFace 工作面信息
     * @return 工作面信息集合
     */
    public List<TWorkingFace> selectTWorkingFaceList(TWorkingFace tWorkingFace);

    /**
     * 新增工作面信息
     *
     * @param tWorkingFace 工作面信息
     * @return 结果
     */
    public Integer insertTWorkingFace(TWorkingFace tWorkingFace);

    /**
     * 修改工作面信息
     *
     * @param tWorkingFace 工作面信息
     * @return 结果
     */
    public Integer updateTWorkingFace(TWorkingFace tWorkingFace);

    /**
     * 删除工作面信息
     *
     * @param id 工作面信息主键
     * @return 结果
     */
    public Integer deleteTWorkingFaceById(@RequestParam Integer id);

    /**
     * 批量删除工作面信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public Integer deleteTWorkingFaceByIds(@RequestParam List<Integer> ids);

    public List<TWorkingFace> getPageList();

    /**
     * 根据巷道ID删除工作面信息
     * @param roadwayId 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayId(@RequestParam Integer roadwayId);

    /**
     * 根据工作面编号查询工作面ID
     * @param miningAreaId 工作面编号
     * @return id
     */
    public Integer getByWorkfaceSerial(@RequestParam Integer miningAreaId);

    /**
     * 抽采动态
     * @return 状态
     */
    public List<TWorkingFace> getWorkfaceDrainageOperation();

    /**
     * 根据id查询工作面名称
     * @param id ID
     * @return 工作面名称
     */
    public String getWorefaceNameById(Integer id);

    /**
     * 获取所有工作面ID
     * @return ID列表
     */
    public List<Integer> getWorkFaceIds();

    /**
     * 根据工作面名称和采区ID查询
     * @param workfaceName
     * @param miningAreaId
     * @return
     */
    public TWorkingFace getByMinAreaIdAndWorkFaceName(@Param("workfaceName") String workfaceName,@Param("miningAreaId") Integer miningAreaId);

    /**
     * 根据工作面ID查询工作面信息
     * @param workfaceId
     * @return
     */
    public TWorkingFace getInfoById(@Param("workfaceId") Integer workfaceId);

}
