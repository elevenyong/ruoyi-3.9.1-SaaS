package com.ruoyi.framework.web.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.ruoyi.common.tenant.TenantConstants;
import com.ruoyi.common.tenant.TenantContextHolder;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.tenant.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.BlackListException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserNotExistsException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.security.context.AuthenticationContextHolder;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private TenantService tenantService;

    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
//        // 验证码校验
//        validateCaptcha(username, code, uuid);
//        // 登录前置校验
//        loginPreCheck(username, password);
//        // 用户验证
//        Authentication authentication = null;
//        try
        // === 多租户：登录前先解析 tenantCode -> tenantId，写入上下文，确保认证阶段按租户查用户 ===
        HttpServletRequest request = ServletUtils.getRequest();
        String tenantCode = request.getHeader(TenantConstants.TENANT_CODE_HEADER);
        if (tenantCode == null || tenantCode.trim().isEmpty())
        {
            tenantCode = TenantConstants.DEFAULT_TENANT_CODE;
        }
        Long tenantId = tenantService.getTenantIdByCode(tenantCode);
        if (tenantId == null)
        {
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//            AuthenticationContextHolder.setContext(authenticationToken);
//            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
//            authentication = authenticationManager.authenticate(authenticationToken);
            throw new ServiceException("租户不存在或已停用: " + tenantCode);
        }

        // 让 MyBatis 多租户拦截器在认证阶段也生效（selectUserByUserName 会被自动拼 tenant_id）
        TenantContextHolder.setTenantId(tenantId);

        try
        {
//            if (e instanceof BadCredentialsException)
            // 验证码校验
            validateCaptcha(username, code, uuid);
            // 登录前置校验
            loginPreCheck(username, password);

            // 用户验证
            Authentication authentication = null;
            try
            {
//                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
//                throw new UserPasswordNotMatchException();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
                AuthenticationContextHolder.setContext(authenticationToken);
                // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
                authentication = authenticationManager.authenticate(authenticationToken);
            }
//            else
            catch (Exception e)
            {
//                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
//                throw new ServiceException(e.getMessage());
                if (e instanceof BadCredentialsException)
                {
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                    throw new UserPasswordNotMatchException();
                }
                else
                {
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                    throw new ServiceException(e.getMessage());
                }
            }
            finally
            {
                AuthenticationContextHolder.clearContext();
            }

            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();

            // 认证通过后，把租户信息固化到 LoginUser，并写入 token claims
            loginUser.setTenantId(tenantId);
            loginUser.setTenantCode(tenantCode);

            recordLoginInfo(loginUser.getUserId());
            // 生成token
            return tokenService.createToken(loginUser);
        }
        finally
        {
//            AuthenticationContextHolder.clearContext();
            // 必须清理 ThreadLocal，避免线程复用串租户
            TenantContextHolder.clear();
        }
//        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
//        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
//
//        // 从 Header 取 tenantCode
//        HttpServletRequest request = ServletUtils.getRequest();
//        String tenantCode = request.getHeader(TenantConstants.TENANT_CODE_HEADER);
//        if (tenantCode == null || tenantCode.trim().isEmpty()) {
//            tenantCode = TenantConstants.DEFAULT_TENANT_CODE;
//        }
//        Long tenantId = tenantService.getTenantIdByCode(tenantCode);
//        if (tenantId == null) {
//            throw new ServiceException("租户不存在或已停用: " + tenantCode);
//        }
//
//        loginUser.setTenantId(tenantId);
//        loginUser.setTenantCode(tenantCode);
//        recordLoginInfo(loginUser.getUserId());
//        // 生成token
//        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     * 
     * @param username 用户名
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid)
    {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled)
        {
            String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
            String captcha = redisCache.getCacheObject(verifyKey);
            if (captcha == null)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
                throw new CaptchaExpireException();
            }
            redisCache.deleteObject(verifyKey);
            if (!code.equalsIgnoreCase(captcha))
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
                throw new CaptchaException();
            }
        }
    }

    /**
     * 登录前置校验
     * @param username 用户名
     * @param password 用户密码
     */
    public void loginPreCheck(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
            throw new UserNotExistsException();
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
            throw new UserPasswordNotMatchException();
        }
        // IP黑名单校验
        String blackStr = configService.selectConfigByKey("sys.login.blackIPList");
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr()))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("login.blocked")));
            throw new BlackListException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        userService.updateLoginInfo(userId, IpUtils.getIpAddr(), DateUtils.getNowDate());
    }
}
