package com.ruoyi.hh.physical.mapper;



import com.ruoyi.hh.physical.domain.WarningAlerts;

import java.util.List;

/**
 * 异常信息Mapper接口
 *
 * @author hhhz
 * @date 2022-12-07
 */
public interface WarningAlertsMapper {
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
     * 删除异常信息
     *
     * @param id 异常信息主键
     * @return 结果
     */
    public int deleteWarningAlertsById(Long id);

    /**
     * 批量删除异常信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarningAlertsByIds(Long[] ids);
}
