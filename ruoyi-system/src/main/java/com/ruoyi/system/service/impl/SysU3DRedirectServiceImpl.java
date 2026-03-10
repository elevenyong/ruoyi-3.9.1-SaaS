package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysU3DRedirect;
import com.ruoyi.system.mapper.SysU3DRedirectMapper;
import com.ruoyi.system.service.ISysU3DRedirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单指向Service业务层处理
 *
 * @author hh
 * @date 2022-09-22
 */
@Service
public class SysU3DRedirectServiceImpl implements ISysU3DRedirectService {
    @Autowired
    private SysU3DRedirectMapper sysU3DRedirectMapper;

    /**
     * 查询菜单指向
     *
     * @param id 菜单指向主键
     * @return 菜单指向
     */
    @Override
    public SysU3DRedirect selectSysU3DRedirectById(Long id) {
        return sysU3DRedirectMapper.selectSysU3DRedirectById(id);
    }

    /**
     * 查询菜单指向列表
     *
     * @param sysU3DRedirect 菜单指向
     * @return 菜单指向
     */
    @Override
    public List<SysU3DRedirect> selectSysU3DRedirectList(SysU3DRedirect sysU3DRedirect) {
        return sysU3DRedirectMapper.selectSysU3DRedirectList(sysU3DRedirect);
    }

    /**
     * 新增菜单指向
     *
     * @param sysU3DRedirect 菜单指向
     * @return 结果
     */
    @Override
    public int insertSysU3DRedirect(SysU3DRedirect sysU3DRedirect) {
        return sysU3DRedirectMapper.insertSysU3DRedirect(sysU3DRedirect);
    }

    /**
     * 修改菜单指向
     *
     * @param sysU3DRedirect 菜单指向
     * @return 结果
     */
    @Override
    public int updateSysU3DRedirect(SysU3DRedirect sysU3DRedirect) {
        return sysU3DRedirectMapper.updateSysU3DRedirect(sysU3DRedirect);
    }

    /**
     * 批量删除菜单指向
     *
     * @param ids 需要删除的菜单指向主键
     * @return 结果
     */
    @Override
    public int deleteSysU3DRedirectByIds(Long[] ids) {
        return sysU3DRedirectMapper.deleteSysU3DRedirectByIds(ids);
    }

    /**
     * 删除菜单指向信息
     *
     * @param id 菜单指向主键
     * @return 结果
     */
    @Override
    public int deleteSysU3DRedirectById(Long id) {
        return sysU3DRedirectMapper.deleteSysU3DRedirectById(id);
    }
}
