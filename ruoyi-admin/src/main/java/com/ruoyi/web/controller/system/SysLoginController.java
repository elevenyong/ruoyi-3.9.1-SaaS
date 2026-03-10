package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.ruoyi.common.tenant.TenantConstants;
import com.ruoyi.framework.tenant.TenantService;
import com.ruoyi.system.domain.vo.RouterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysMenuService;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private TenantService tenantService;

//    @PostMapping("/login")
//    public AjaxResult login(@RequestBody LoginBody loginBody, HttpServletRequest request)
//    {
//        String tenantCode = request.getHeader(TenantConstants.TENANT_CODE_HEADER);
//        if (tenantCode == null || tenantCode.trim().isEmpty()) {
//            tenantCode = TenantConstants.DEFAULT_TENANT_CODE;
//        }
//        // 先解析 tenantId
//        Long tenantId = tenantService.getTenantIdByCode(tenantCode);
//        if (tenantId == null) {
//            return AjaxResult.error("租户不存在或已停用: " + tenantCode);
//        }
//        // 原有登录逻辑（不改你原来的密码校验/验证码校验等）
//        AjaxResult ajax = AjaxResult.success(loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
//                loginBody.getUuid()));
//        // 把 tenant 信息回传给前端（可选，但建议）
//        ajax.put("tenantId", tenantId);
//        ajax.put("tenantCode", tenantCode);
//        return ajax;
//    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody, HttpServletRequest request)
    {
        String tenantCode = request.getHeader(TenantConstants.TENANT_CODE_HEADER);
        System.err.println("tenantCode"+tenantCode);
        if (tenantCode == null || tenantCode.trim().isEmpty()) {
            tenantCode = TenantConstants.DEFAULT_TENANT_CODE;
        }
        Long tenantId = tenantService.getTenantIdByCode(tenantCode);
        if (tenantId == null) {
            return AjaxResult.error("租户不存在或已停用: " + tenantCode);
        }
        // 生成令牌（保持原有登录逻辑）
        String token = loginService.login(
                loginBody.getUsername(),
                loginBody.getPassword(),
                loginBody.getCode(),
                loginBody.getUuid()
        );
        // ✅ 关键：兼容 RuoYi 3.8.1 / Unity 老客户端——token 必须放在 "token" 字段
        AjaxResult ajax = AjaxResult.success();
        ajax.put(Constants.TOKEN, token);
        // （可选）为了兼容你现有前端如果用了 data，也可以同时放一份
        ajax.put("data", token);
        // 回传租户信息
        ajax.put("tenantId", tenantId);
        ajax.put("tenantCode", tenantCode);
        return ajax;
    }


    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        if (!loginUser.getPermissions().equals(permissions))
        {
            loginUser.setPermissions(permissions);
            tokenService.refreshToken(loginUser);
        }
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        ajax.put("isDefaultModifyPwd", initPasswordIsModify(user.getPwdUpdateDate()));
        ajax.put("isPasswordExpired", passwordIsExpiration(user.getPwdUpdateDate()));
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        if (userId != 1) {
            int arrRoleIndex = -1;
            // 只有管理员能看到角色管理
            //List<RouterVo> routerVos = menuService.buildMenus(menus);
            for (int i = 0; i < menus.size(); i++) {
                SysMenu sysMenu = menus.get(i);
                if (sysMenu.getPath().equals("system")) {
                    List<SysMenu> systemMenus = sysMenu.getChildren();
                    for (int i1 = 0; i1 < systemMenus.size(); i1++) {
                        if (systemMenus.get(i1).getPath().equals("role")) {
                            arrRoleIndex = i1;
                            break;
                        }
                    }
                    if (arrRoleIndex != -1) {
                        systemMenus.remove(arrRoleIndex);
                    }
                }
            }
        }
        return AjaxResult.success(menuService.buildMenus(menus));
    }
    
    // 检查初始密码是否提醒修改
    public boolean initPasswordIsModify(Date pwdUpdateDate)
    {
        Integer initPasswordModify = Convert.toInt(configService.selectConfigByKey("sys.account.initPasswordModify"));
        return initPasswordModify != null && initPasswordModify == 1 && pwdUpdateDate == null;
    }

    // 检查密码是否过期
    public boolean passwordIsExpiration(Date pwdUpdateDate)
    {
        Integer passwordValidateDays = Convert.toInt(configService.selectConfigByKey("sys.account.passwordValidateDays"));
        if (passwordValidateDays != null && passwordValidateDays > 0)
        {
            if (StringUtils.isNull(pwdUpdateDate))
            {
                // 如果从未修改过初始密码，直接提醒过期
                return true;
            }
            Date nowDate = DateUtils.getNowDate();
            return DateUtils.differentDaysByMillisecond(nowDate, pwdUpdateDate) > passwordValidateDays;
        }
        return false;
    }

    /**
     * 获取U3D路由信息
     *
     * @return
     */
    @ApiOperation(value = "获取U3D路由信息",hidden = true)
    @GetMapping("getU3DMenus")
    public AjaxResult getU3DMenus() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        List<RouterVo> routerVos = menuService.buildMenus(menus);
        // 过滤U3D菜单
        List<RouterVo> u3dMenusVo = new ArrayList<>();
        for (RouterVo vo : routerVos) {
            if ("/HHHZ".equals(vo.getPath())) {
                u3dMenusVo.add(vo);
            }
        }
        return AjaxResult.success(u3dMenusVo);
    }
}
