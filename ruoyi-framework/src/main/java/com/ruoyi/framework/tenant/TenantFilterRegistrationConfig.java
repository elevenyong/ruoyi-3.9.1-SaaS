package com.ruoyi.framework.tenant;
import com.ruoyi.framework.tenant.TenantContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
*@Author：zhangHY
*@Package：com.ruoyi.framework.tenant
*@Project：RuoYi-Vue-master
*@name：TenantFilterRegistrationConfig
*@Date：2026/3/3  17:56
*@Filename：TenantFilterRegistrationConfig
*@Description 
*/
/**
 * 禁用 TenantContextFilter 在 Servlet 容器层的自动注册，避免与 Spring Security FilterChain 重复执行导致 tenantId 丢失
 */
@Configuration
public class TenantFilterRegistrationConfig {

    @Bean
    public FilterRegistrationBean<TenantContextFilter> tenantContextFilterRegistration(TenantContextFilter filter) {
        FilterRegistrationBean<TenantContextFilter> reg = new FilterRegistrationBean<>(filter);
        reg.setEnabled(false); // 关键：禁用 Tomcat 层注册
        return reg;
    }
}