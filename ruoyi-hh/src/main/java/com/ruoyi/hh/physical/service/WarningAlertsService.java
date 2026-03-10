package com.ruoyi.hh.physical.service;


import com.ruoyi.hh.physical.domain.WarningAlerts;

import java.util.ArrayList;
import java.util.List;

public interface WarningAlertsService {

    ArrayList<WarningAlerts> getFaultsWarningAlerts();

    ArrayList<WarningAlerts> getDrillingWarningAlerts();

    ArrayList<WarningAlerts> getCoalbedsWarningAlerts();

    ArrayList<WarningAlerts> getFaultsWarningAlerts(double x, double y, double z);


    /**
     * 查询异常信息
     *
     * @param id 异常信息主键
     * @return 异常信息
     */
    public WarningAlerts selectWarningAlertsById(Integer id);

    /**
     * 查询异常信息列表
     *
     * @param tWarningAlerts 异常信息
     * @return 异常信息集合
     */
    public List<WarningAlerts> selectWarningAlertsList(WarningAlerts tWarningAlerts);

    /**
     * 新增异常信息
     *
     * @param tWarningAlerts 异常信息
     * @return 结果
     */
    public int insertWarningAlerts(WarningAlerts tWarningAlerts);

    /**
     * 修改异常信息
     *
     * @param tWarningAlerts 异常信息
     * @return 结果
     */
    public int updateWarningAlerts(WarningAlerts tWarningAlerts);

    /**
     * 批量删除异常信息
     *
     * @param ids 需要删除的异常信息主键集合
     * @return 结果
     */
    public int deleteWarningAlertsByIds(Long[] ids);

    /**
     * 删除异常信息信息
     *
     * @param id 异常信息主键
     * @return 结果
     */
    public int deleteWarningAlertsById(Long id);

    List<WarningAlerts> getWarningAlertsWithParams(Double x, Double y, Double z, Double dis, String type, Integer i);
}
