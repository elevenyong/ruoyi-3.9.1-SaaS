package com.ruoyi.hh.physical.service;

import com.ruoyi.hh.demo.domain.TActualBorholeException;
import com.ruoyi.hh.business.domain.TGasDrillingStandingBook;
import com.ruoyi.hh.business.domain.TSealingGrouting;
import com.ruoyi.hh.physical.domain.TActualBorehole;
import com.ruoyi.hh.util.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 实钻信息相关接口之Service接口
 *
 * @author eleven
 * @date 2022-07-13
 */
@Deprecated
public interface ITActualBoreholeService {
    /**
     * 查询实钻信息相关接口之
     *
     * @param id 实钻信息相关接口之主键
     * @return 实钻信息相关接口之
     */
    public TActualBorehole selectTActualBoreholeById(Integer id);

    /**
     * 查询实钻信息相关接口之列表
     *
     * @param tActualBorehole 实钻信息相关接口之
     * @return 实钻信息相关接口之集合
     */
    public List<TActualBorehole> selectTActualBoreholeList(TActualBorehole tActualBorehole);

    /**
     * 新增实钻信息相关接口之
     *
     * @param tActualBorehole 实钻信息相关接口之
     */
    public Result insertTActualBorehole(TActualBorehole tActualBorehole);

    /**
     * 修改实钻信息相关接口之
     *
     * @param tActualBorehole 实钻信息相关接口之
     * @return 结果
     */
    public Integer updateTActualBorehole(TActualBorehole tActualBorehole);

    /**
     * 批量删除实钻信息相关接口之
     *
     * @param ids 需要删除的实钻信息相关接口之主键集合
     * @return 结果
     */
    public Integer deleteTActualBoreholeByIds(List<Integer> ids);

    /**
     * 删除实钻信息相关接口之信息
     *
     * @param id 实钻信息相关接口之主键
     * @return 结果
     */
    public Integer deleteTActualBoreholeById(Integer id);

    /**
     * 批量添加实钻信息
     *
     * @param list
     */
    public Integer insertTActualBoreholeList(List<TActualBorehole> list);

    /**
     * 分页查询
     *
     * @return 列表
     */
    public List<TActualBorehole> getPageList();

    /**
     * 根据组号查询列表
     *
     * @param groupNo 组号ID
     * @return 列表
     */
    public List<TActualBorehole> getBygroupNo(String groupNo);


    /**
     * 查询钻孔原始验收单
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号
     * @param boreholeNo 孔号
     * @param startdate  开始时间
     * @param enddate    结束时间
     * @return 列表
     */
    public List<TActualBorehole> getAcceptanceCertificate(
            Integer roadwayId, String groupNo,
            List<Integer> boreholeNo, Date startdate, Date enddate) throws ParseException;

    /**
     * 根据巷道ID删除实钻信息
     *
     * @param RoadWayId 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayId(Integer RoadWayId);

    /**
     * 根据巷道ID删除实钻信息
     *
     * @param roadWayIds 巷道ID
     * @return 列表
     */
    public Integer delByRoadWayIds(List<Integer> roadWayIds);

    /**
     * 通过巷道ID获取实钻ID列表
     *
     * @param roadWayId 巷道ID
     * @return 列表
     */
    public List<Integer> getIdsByRoadWayId(Integer roadWayId);

    /**
     * 根据区段ID删除设计钻孔信息
     *
     * @param roadwayZoneId 区段ID
     * @return 结果
     */
    public Integer delByRoadwayZoneId(Integer roadwayZoneId);

    public Integer delByRoadwayZoneIds(List<Integer> roadwayZoneIds);

    /**
     * 根据导线点ID删除设计钻孔信息
     *
     * @param navigationPointId 导线点ID
     * @return 结果
     */
    public Integer delByNavigationPointId(Integer navigationPointId);

    public Integer delNavigationPointIds(List<Integer> navigationPointIds);

    /**
     * 根据巷道区段ID获取实钻ID列表
     *
     * @param roadWayZoneId 区段ID
     * @return 实钻ID列表
     */
    public List<Integer> getIdsRoadWayZoneId(Integer roadWayZoneId);

    /**
     * 根据组号绑定钻孔和测点的关系
     *
     * @param measurePointId 测点ID
     * @param groupNo        组号
     */
    public void bundleByMeasurePointId(@RequestParam Integer measurePointId, @RequestParam String groupNo);

    /**
     * 根据组号解绑钻孔和测点的关系
     *
     * @param groupNo 组号
     */
    public void unbundleByMeasurePointId(@RequestParam String groupNo);

    /**
     * 获取钻孔信息
     *
     * @return 列表
     */
    public List<TGasDrillingStandingBook> getBoreholeInfo(Integer roadWayId,
                                                          List<String> groupNo,
                                                          List<Integer> purposeBoreholeId,
                                                          List<Integer> boreholeNo,
                                                          Date startdate,
                                                          Date enddate);

    /**
     * 根据巷道ID，组号，孔号查询ID
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号
     * @param boreholeId 孔号
     * @return ID
     */
    public Integer getId(Integer roadwayId,
                         String groupNo,
                         Integer boreholeId);

    /**
     * 根据巷道Id查询组号列表
     *
     * @param roadwayId 巷道ID
     * @return 列表
     */
    public List<String> getGroupNosByRoadwayId(@Param("roadwayId") Integer roadwayId);

    /**
     * 根据巷道ID和组号查询孔号
     *
     * @param roadwayId 巷道ID
     * @param groupNo   组号
     * @return 孔号列表
     */
    public List<Integer> getBoreholenoByRoadwayIdAndGroupNo(Integer roadwayId,
                                                            String groupNo);

    /**
     * 根据巷道ID和组号查询孔号和ID
     *
     * @param roadwayId 巷道ID
     * @param groupNo   组号
     * @return 结果
     */
    public List<TSealingGrouting> getIdAndBoreholeNo(Integer roadwayId,
                                                     String groupNo);

    /**
     * 根据巷道名称、组号、孔号查询实钻ID
     *
     * @param roadway    巷道名称
     * @param groupNo    组号
     * @param boreholeNo 孔号
     * @return 实钻ID
     */
    public Integer getIdByRoadWayAndGroupNoBoreholeno(String roadway,
                                                      String groupNo,
                                                      Integer boreholeNo);

    /**
     * 获取钻孔异常信息列表
     *
     * @return
     */
    public List<TActualBorholeException> selectTActualBoreholeWithException();

    /**
     * 根据工作面ID查询实钻信息列表
     * @param workfaceid 工作面ID
     * @return 实钻列表
     */
    public List<TActualBorehole> getByWorkfaceId(Integer workfaceid);

    /**
     * 达标评判之实测瓦斯含量压力情况
     * @param workfaceid 工作面ID
     * @return 列表
     */
    public List<TActualBorehole> getGasContentPressureByWorkfaceid(Integer workfaceid);
}
