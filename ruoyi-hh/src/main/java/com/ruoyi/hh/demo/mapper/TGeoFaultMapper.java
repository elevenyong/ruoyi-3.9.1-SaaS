package com.ruoyi.hh.demo.mapper;

import java.util.List;
import com.ruoyi.hh.demo.domain.TGeoFault;

/**
 * 断层Mapper接口
 *
 * @author hhhz
 * @date 2022-10-28
 */
public interface TGeoFaultMapper
{
    /**
     * 查询断层
     *
     * @param id 断层主键
     * @return 断层
     */
    public TGeoFault selectTGeoFaultById(Integer id);

    /**
     * 查询断层列表
     *
     * @param TGeoFault 断层
     * @return 断层集合
     */
    public List<TGeoFault> selectTGeoFaultList(TGeoFault TGeoFault);

    /**
     * 新增断层
     *
     * @param TGeoFault 断层
     * @return 结果
     */
    public int insertTGeoFault(TGeoFault TGeoFault);

    /**
     * 修改断层
     *
     * @param TGeoFault 断层
     * @return 结果
     */
    public int updateTGeoFault(TGeoFault TGeoFault);

    /**
     * 删除断层
     *
     * @param id 断层主键
     * @return 结果
     */
    public int deleteTGeoFaultById(Integer id);

    /**
     * 批量删除断层
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTGeoFaultByIds(Integer[] ids);
}
