package com.ruoyi.system.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ruoyi.common.core.domain.entity.*;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.tenant.TenantContextHolder;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.domain.SysTenant;
import com.ruoyi.system.domain.vo.TenantCreateReq;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysTenantService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.system.service.impl
 * @Project：RuoYi-Vue-master
 * @name：SysTenantServiceImpl
 * @Date：2026/2/10 14:08
 * @Filename：SysTenantServiceImpl
 * @Description 租户 业务层处理（方案1：平台创建租户时同步创建租户管理员）
 */
@Service
public class SysTenantServiceImpl implements ISysTenantService
{
    @Autowired
    private SysTenantMapper tenantMapper;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private SysConfigMapper configMapper;

    @Override
    public List<SysTenant> selectTenantList(SysTenant tenant)
    {
        return tenantMapper.selectTenantList(tenant);
    }

    @Override
    public SysTenant selectTenantByCode(String tenantCode)
    {
        if (StringUtils.isEmpty(tenantCode))
        {
            return null;
        }
        return tenantMapper.selectTenantByCode(tenantCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTenantWithAdmin(TenantCreateReq req)
    {
        // 0) 校验 tenantCode 是否已存在（sys_tenant 是全局表）
        SysTenant exist = tenantMapper.selectTenantByCode(req.getTenantCode());
        if (exist != null)
        {
            throw new ServiceException("租户编码已存在：" + req.getTenantCode());
        }

        // 1) 创建租户记录（全局表：不会被租户拦截器加 tenant_id）
        SysTenant tenant = new SysTenant();
        tenant.setTenantCode(req.getTenantCode());
        tenant.setTenantName(req.getTenantName());
        tenant.setStatus("0");
        tenant.setDelFlag("0");
        String oper = SecurityUtils.getUsername();
        tenant.setCreateBy(oper);
        tenant.setUpdateBy(oper);

        int rows = tenantMapper.insertTenant(tenant);
        if (rows <= 0 || tenant.getTenantId() == null)
        {
            throw new ServiceException("创建租户失败");
        }

        Long tenantId = tenant.getTenantId();

        // 2) 切换到新租户上下文：下面所有 insert/select 会自动带 tenant_id
        TenantContextHolder.setTenantId(tenantId);
        try
        {
            // 2.1 初始化默认部门（作为租户根部门）
            SysDept dept = new SysDept();
            // 显式写入 tenantId，避免 insert 未带 tenant_id 导致落库为 0/null
            dept.setTenantId(tenantId);
            dept.setParentId(0L);
            dept.setDeptName(tenant.getTenantName());
            dept.setOrderNum(0);
            dept.setStatus("0");
            dept.setDelFlag("0");
            dept.setCreateBy(oper);
            dept.setUpdateBy(oper);

            int deptRows = deptService.insertDept(dept);
            if (deptRows <= 0 || dept.getDeptId() == null)
            {
                throw new ServiceException("初始化租户默认部门失败");
            }

            // 2.2 初始化租户管理员角色（默认拥有全部菜单权限）
            List<SysMenu> menus = menuMapper.selectMenuList(new SysMenu());
            Long[] menuIds = menus.stream().map(SysMenu::getMenuId).toArray(Long[]::new);

            SysRole adminRole = new SysRole();
            adminRole.setTenantId(tenantId);
            adminRole.setRoleName("租户管理员");
            adminRole.setRoleKey("tenant_admin");
            adminRole.setRoleSort(1);
            adminRole.setDataScope("1"); // 全部数据权限
            adminRole.setStatus("0");
            adminRole.setDelFlag("0");
            adminRole.setMenuIds(menuIds);
            adminRole.setCreateBy(oper);
            adminRole.setUpdateBy(oper);

            int roleRows = roleService.insertRole(adminRole);
            if (roleRows <= 0 || adminRole.getRoleId() == null)
            {
                throw new ServiceException("初始化租户管理员角色失败");
            }

            // 2.3 创建该租户管理员账号
            SysUser admin = new SysUser();
            admin.setTenantId(tenantId);
            admin.setDeptId(dept.getDeptId());
            admin.setUserName(req.getAdminUserName());
            admin.setNickName(req.getAdminNickName());
            admin.setPhonenumber(req.getAdminPhone());
            admin.setEmail(req.getAdminEmail());
            admin.setSex("0");
            admin.setStatus("0");
            admin.setDelFlag("0");
            admin.setPassword(SecurityUtils.encryptPassword(req.getAdminPassword()));
            admin.setCreateBy(oper);
            admin.setUpdateBy(oper);
            admin.setRoleIds(new Long[] { adminRole.getRoleId() });

            // 租户内用户唯一性校验（此时 tenantId 已切换为新租户）
            if (!userService.checkUserNameUnique(admin))
            {
                throw new ServiceException("租户管理员账号已存在：" + req.getAdminUserName());
            }

            int userRows = userService.insertUser(admin);
            if (userRows <= 0 || admin.getUserId() == null)
            {
                throw new ServiceException("创建租户管理员账号失败");
            }

            return tenantId;
        }
        finally
        {
            TenantContextHolder.clear();
        }
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initTenantBase(Long tenantId, Long templateTenantId)
    {
        if (tenantId == null)
        {
            throw new ServiceException("tenantId 不能为空");
        }
        if (templateTenantId == null)
        {
            throw new ServiceException("templateTenantId 不能为空");
        }

        SysTenant tenant = tenantMapper.selectTenantById(tenantId);
        if (tenant == null)
        {
            throw new ServiceException("租户不存在: " + tenantId);
        }
        if (!"0".equals(tenant.getStatus()))
        {
            throw new ServiceException("租户已停用，无法初始化: " + tenant.getTenantCode());
        }

        String oper = SecurityUtils.getUsername();

        // 1) 读取模板租户数据
        List<SysDictType> tplTypes;
        List<SysDictData> tplDatas;
        List<SysConfig> tplConfigs;
        TenantContextHolder.setTenantId(templateTenantId);
        try
        {
            tplTypes = dictTypeMapper.selectDictTypeAll();
            tplDatas = dictDataMapper.selectDictDataList(new SysDictData());
            tplConfigs = configMapper.selectConfigList(new SysConfig());
        }
        finally
        {
            TenantContextHolder.clear();
        }

        // 2) 切到目标租户，补齐数据
        TenantContextHolder.setTenantId(tenantId);
        try
        {
            // 2.1 复制字典类型
            Set<String> existType = new HashSet<>();
            for (SysDictType t : dictTypeMapper.selectDictTypeAll())
            {
                if (t != null && StringUtils.isNotEmpty(t.getDictType()))
                {
                    existType.add(t.getDictType());
                }
            }
            for (SysDictType t : tplTypes)
            {
                if (t == null || StringUtils.isEmpty(t.getDictType()))
                {
                    continue;
                }
                if (existType.contains(t.getDictType()))
                {
                    continue;
                }
                SysDictType nt = new SysDictType();
                nt.setDictName(t.getDictName());
                nt.setDictType(t.getDictType());
                nt.setStatus(t.getStatus());
                nt.setRemark(t.getRemark());
                nt.setCreateBy(oper);
                dictTypeMapper.insertDictType(nt);
                existType.add(t.getDictType());
            }

            // 2.2 复制字典数据
            Set<String> existData = new HashSet<>();
            for (SysDictData d : dictDataMapper.selectDictDataList(new SysDictData()))
            {
                if (d != null && StringUtils.isNotEmpty(d.getDictType()) && StringUtils.isNotEmpty(d.getDictValue()))
                {
                    existData.add(d.getDictType() + "||" + d.getDictValue());
                }
            }
            for (SysDictData d : tplDatas)
            {
                if (d == null || StringUtils.isEmpty(d.getDictType()) || StringUtils.isEmpty(d.getDictValue()))
                {
                    continue;
                }
                String key = d.getDictType() + "||" + d.getDictValue();
                if (existData.contains(key))
                {
                    continue;
                }
                SysDictData nd = new SysDictData();
                nd.setDictSort(d.getDictSort());
                nd.setDictLabel(d.getDictLabel());
                nd.setDictValue(d.getDictValue());
                nd.setDictType(d.getDictType());
                nd.setCssClass(d.getCssClass());
                nd.setListClass(d.getListClass());
                nd.setIsDefault(d.getIsDefault());
                nd.setStatus(d.getStatus());
                nd.setRemark(d.getRemark());
                nd.setCreateBy(oper);
                dictDataMapper.insertDictData(nd);
                existData.add(key);
            }

            // 2.3 复制系统参数
            Set<String> existCfg = new HashSet<>();
            for (SysConfig c : configMapper.selectConfigList(new SysConfig()))
            {
                if (c != null && StringUtils.isNotEmpty(c.getConfigKey()))
                {
                    existCfg.add(c.getConfigKey());
                }
            }
            for (SysConfig c : tplConfigs)
            {
                if (c == null || StringUtils.isEmpty(c.getConfigKey()))
                {
                    continue;
                }
                if (existCfg.contains(c.getConfigKey()))
                {
                    continue;
                }
                SysConfig nc = new SysConfig();
                nc.setConfigName(c.getConfigName());
                nc.setConfigKey(c.getConfigKey());
                nc.setConfigValue(c.getConfigValue());
                nc.setConfigType(c.getConfigType());
                nc.setRemark(c.getRemark());
                nc.setCreateBy(oper);
                configMapper.insertConfig(nc);
                existCfg.add(c.getConfigKey());
            }

            // 2.4 确保“租户管理员”角色存在并绑定全量菜单
            List<SysMenu> menus = menuMapper.selectMenuList(new SysMenu());
            Long[] menuIds = menus.stream().map(SysMenu::getMenuId).toArray(Long[]::new);

            SysRole q = new SysRole();
            q.setRoleKey("tenant_admin");
            List<SysRole> roles = roleService.selectRoleList(q);

            if (roles == null || roles.isEmpty())
            {
                SysRole adminRole = new SysRole();
                adminRole.setTenantId(tenantId);
                adminRole.setRoleName("租户管理员");
                adminRole.setRoleKey("tenant_admin");
                adminRole.setRoleSort(1);
                adminRole.setDataScope("1");
                adminRole.setStatus("0");
                adminRole.setDelFlag("0");
                adminRole.setMenuIds(menuIds);
                adminRole.setCreateBy(oper);
                adminRole.setUpdateBy(oper);
                roleService.insertRole(adminRole);
            }
            else
            {
                SysRole adminRole = roles.get(0);
                adminRole.setTenantId(tenantId);
                adminRole.setMenuIds(menuIds);
                adminRole.setUpdateBy(oper);
                roleService.updateRole(adminRole);
            }
        }
        finally
        {
            TenantContextHolder.clear();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetTenantAdminPassword(Long tenantId, String adminUserName, String newPassword)
    {
        if (tenantId == null)
        {
            throw new ServiceException("tenantId 不能为空");
        }
        if (StringUtils.isEmpty(adminUserName))
        {
            throw new ServiceException("adminUserName 不能为空");
        }
        if (StringUtils.isEmpty(newPassword))
        {
            throw new ServiceException("newPassword 不能为空");
        }

        SysTenant tenant = tenantMapper.selectTenantById(tenantId);
        if (tenant == null)
        {
            throw new ServiceException("租户不存在: " + tenantId);
        }

        TenantContextHolder.setTenantId(tenantId);
        try
        {
            SysUser u = userService.selectUserByUserName(adminUserName);
            if (u == null)
            {
                throw new ServiceException("该租户下管理员账号不存在: " + adminUserName);
            }
            String enc = SecurityUtils.encryptPassword(newPassword);
            userService.resetUserPwd(u.getUserId(), enc);
        }
        finally
        {
            TenantContextHolder.clear();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateTenantStatus(Long tenantId, String status)
    {
        return tenantMapper.updateTenantStatus(tenantId, status);
    }
}