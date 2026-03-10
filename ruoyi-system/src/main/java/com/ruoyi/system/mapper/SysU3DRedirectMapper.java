package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysU3DRedirect;

import java.util.List;

/**
 * 菜单指向Mapper接口
 *
 * @author hh
 * @date 2022-09-22
 */
public interface SysU3DRedirectMapper {
    /**
     * 查询菜单指向
     *
     * @param id 菜单指向主键
     * @return 菜单指向
     */
    public SysU3DRedirect selectSysU3DRedirectById(Long id);

    /**
     * 查询菜单指向列表
     *
     * @param sysU3DRedirect 菜单指向
     * @return 菜单指向集合
     */
    public List<SysU3DRedirect> selectSysU3DRedirectList(SysU3DRedirect sysU3DRedirect);

    /**
     * 新增菜单指向
     *
     * @param sysU3DRedirect 菜单指向
     * @return 结果
     */
    public int insertSysU3DRedirect(SysU3DRedirect sysU3DRedirect);

    /**
     * 修改菜单指向
     *
     * @param sysU3DRedirect 菜单指向
     * @return 结果
     */
    public int updateSysU3DRedirect(SysU3DRedirect sysU3DRedirect);

    /**
     * 删除菜单指向
     *
     * @param id 菜单指向主键
     * @return 结果
     */
    public int deleteSysU3DRedirectById(Long id);

    /**
     * 批量删除菜单指向
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysU3DRedirectByIds(Long[] ids);
}
