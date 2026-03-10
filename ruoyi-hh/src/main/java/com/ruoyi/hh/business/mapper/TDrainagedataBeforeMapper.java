package com.ruoyi.hh.business.mapper;

import com.ruoyi.hh.business.domain.TDrainagedataBefore;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 抽采系统截止某时间的抽采量Mapper接口
 * 
 * @author ruoyi
 * @date 2023-07-21
 */
public interface TDrainagedataBeforeMapper
{
    /**
     * 查询抽采系统截止某时间的抽采量
     * 
     * @param id 抽采系统截止某时间的抽采量主键
     * @return 抽采系统截止某时间的抽采量
     */
    public TDrainagedataBefore selectTDrainagedataBeforeById(Integer id);

    /**
     * 查询抽采系统截止某时间的抽采量列表
     * 
     * @param tDrainagedataBefore 抽采系统截止某时间的抽采量
     * @return 抽采系统截止某时间的抽采量集合
     */
    public List<TDrainagedataBefore> selectTDrainagedataBeforeList(TDrainagedataBefore tDrainagedataBefore);

    /**
     * 新增抽采系统截止某时间的抽采量
     * 
     * @param tDrainagedataBefore 抽采系统截止某时间的抽采量
     * @return 结果
     */
    public int insertTDrainagedataBefore(TDrainagedataBefore tDrainagedataBefore);

    /**
     * 修改抽采系统截止某时间的抽采量
     * 
     * @param tDrainagedataBefore 抽采系统截止某时间的抽采量
     * @return 结果
     */
    public int updateTDrainagedataBefore(TDrainagedataBefore tDrainagedataBefore);

    /**
     * 删除抽采系统截止某时间的抽采量
     * 
     * @param id 抽采系统截止某时间的抽采量主键
     * @return 结果
     */
    public int deleteTDrainagedataBeforeById(Integer id);

    /**
     * 批量删除抽采系统截止某时间的抽采量
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTDrainagedataBeforeByIds(Integer[] ids);

    /**
     * 根据测点安装位置获取列表
     * @param measurePointLocal
     * @return
     */
    @Select("select * from t_drainagedata_before where measure_point_local=#{measurePointLocal}")
    public TDrainagedataBefore getByMeasurePointLocal(String measurePointLocal);

    /**
     * 通过测点编码获取系统安装前的累计值
     * @param measurePointCode
     * @return
     */
    @Select("select * from t_drainagedata_before where measure_point_code=#{measurePointCode}")
    public TDrainagedataBefore getByMeasurePointCode(String measurePointCode);

}
