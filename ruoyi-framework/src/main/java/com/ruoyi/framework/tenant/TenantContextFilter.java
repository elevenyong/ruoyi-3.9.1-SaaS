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
 * 租户上下文过滤器：为每个请求建立 TenantContextHolder.tenantId
 */
@Component
public class TenantContextFilter extends OncePerRequestFilter {

    @Autowired
    private TenantService tenantService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1) 先尝试从 Header 取 tenantCode（适合未登录/登录接口/匿名接口）
            String tenantCode = request.getHeader(TenantConstants.TENANT_CODE_HEADER);
            if (StringUtils.isNotBlank(tenantCode)) {
                Long tenantId = tenantService.getTenantIdByCode(tenantCode);
                if (tenantId != null) {
                    TenantContextHolder.setTenantId(tenantId);
                }
            }

            // 2) 再尝试从已登录用户取 tenantId（优先级更高，避免伪造 header）
            LoginUser loginUser = null;
            try {
                loginUser = SecurityUtils.getLoginUser();
            } catch (Exception ignore) {
                // 未登录/无token时可能抛异常，忽略即可
            }
            if (loginUser != null && loginUser.getTenantId() != null) {
                TenantContextHolder.setTenantId(loginUser.getTenantId());
            }

            filterChain.doFilter(request, response);
        } finally {
            // 每个请求结束必须清理 ThreadLocal，防止串租户
            TenantContextHolder.clear();
        }
    }
}