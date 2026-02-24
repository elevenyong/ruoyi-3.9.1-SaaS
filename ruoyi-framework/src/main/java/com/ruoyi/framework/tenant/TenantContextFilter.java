package com.ruoyi.framework.tenant;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.tenant.TenantConstants;
import com.ruoyi.common.tenant.TenantContextHolder;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author：zhangHY
 * @Package：com.ruoyi.framework.tenant
 * @Project：RuoYi-Vue-master
 * @name：TenantContextFilter
 * @Date：2026/2/6 15:59
 * @Filename：TenantContextFilter
 * @Description
 */
@Component
public class TenantContextFilter extends OncePerRequestFilter {

    @Autowired
    private TenantService tenantService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 1) 优先从 Header 解析 tenantCode（未登录 / 登录中 / 允许匿名的接口也能拿到租户上下文）
            String tenantCode = request.getHeader(TenantConstants.TENANT_CODE_HEADER);
            if (StringUtils.isNotBlank(tenantCode)) {
                Long tenantId = tenantService.getTenantIdByCode(tenantCode);
                if (tenantId != null) {
                    TenantContextHolder.setTenantId(tenantId);
                }
            }

            // 2) 已登录：以 Token 解析出来的 LoginUser.tenantId 为准（覆盖 Header 解析结果，避免被伪造 header 干扰）
            LoginUser loginUser = null;
            try {
                loginUser = SecurityUtils.getLoginUser();
            } catch (Exception ignore) {
                // 未登录/无token时可能抛异常，直接忽略
            }
            if (loginUser != null && loginUser.getTenantId() != null) {
                TenantContextHolder.setTenantId(loginUser.getTenantId());
            }
            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }
}