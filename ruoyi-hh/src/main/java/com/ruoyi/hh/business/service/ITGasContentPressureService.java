package com.ruoyi.hh.business.service;

import com.ruoyi.hh.business.domain.TGasContentPressure;

import java.util.Date;
import java.util.List;

/**
 * 瓦斯含量/压力台账Service接口
 *
 * @author eleven
 * @date 2022-07-27
 */
public interface ITGasContentPressureService {
    /**
     * 查询瓦斯含量/压力台账
     *
     * @param id 瓦斯含量/压力台账主键
     * @return 瓦斯含量/压力台账
     */
    public TGasContentPressure selectTGasContentPressureById(Integer id);

    /**
     * 查询瓦斯含量/压力台账列表
     *
     * @param tGasContentPressure 瓦斯含量/压力台账
     * @return 瓦斯含量/压力台账集合
     */
    public List<TGasContentPressure> selectTGasContentPressureList(TGasContentPressure tGasContentPressure);

    /**
     * 新增瓦斯含量/压力台账
     *
     * @param tGasContentPressure 瓦斯含量/压力台账
     * @return 结果
     */
    public Integer insertTGasContentPressure(TGasContentPressure tGasContentPressure);

    /**
     * 修改瓦斯含量/压力台账
     *
     * @param tGasContentPressure 瓦斯含量/压力台账
     * @return 结果
     */
    public Integer updateTGasContentPressure(TGasContentPressure tGasContentPressure);

    /**
     * 批量删除瓦斯含量/压力台账
     *
     * @param ids 需要删除的瓦斯含量/压力台账主键集合
     * @return 结果
     */
    public Integer deleteTGasContentPressureByIds(List<Integer> ids);

    /**
     * 删除瓦斯含量/压力台账信息
     *
     * @param id 瓦斯含量/压力台账主键
     * @return 结果
     */
    public Integer deleteTGasContentPressureById(Integer id);

    /**
     * 根据巷道ID，组号，孔号，测定时间获取列表
     *
     * @param roadwayId  巷道ID
     * @param startdate  开始时间
     * @param enddate    结束时间
     * @return 列表
     */
    public List<TGasContentPressure> getData(Integer roadwayId,
                                             List<Integer> purposeboreholeid,
                                             Date startdate,
                                             Date enddate);

    /**
     * 根据钻孔ID删除瓦斯含量信息
     * @param boreholeId 钻孔ID
     * @return 结果
     */
    public Integer delByBoreholeId(Integer boreholeId);
    /**
     * 根据钻孔ID删除瓦斯含量信息
     * @param boreholeIds 钻孔ID
     * @return 结果
     */
    public Integer delByBoreholeIds(List<Integer> boreholeIds);
}
