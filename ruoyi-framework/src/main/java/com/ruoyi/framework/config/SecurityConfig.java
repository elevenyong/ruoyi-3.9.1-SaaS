package com.ruoyi.framework.config;

import com.ruoyi.framework.config.properties.PermitAllUrlProperties;
import com.ruoyi.framework.security.filter.JwtAuthenticationTokenFilter;
import com.ruoyi.framework.security.handle.AuthenticationEntryPointImpl;
import com.ruoyi.framework.security.handle.LogoutSuccessHandlerImpl;
import com.ruoyi.framework.tenant.TenantContextFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * spring security配置（多租户版）
 *
 * 说明：
 * - 保留你原 SecurityConfig.java 中放行的资源路径（/3DVisual/**、/model/**、/getMenus/** 等）
 * - 保留多租户 TenantContextFilter 的链路位置
 */
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class SecurityConfig
{
    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 认证失败处理类
     */
    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;

    /**
     * 允许匿名访问的地址（若依自带 permit-all 配置）
     */
    @Autowired
    private PermitAllUrlProperties permitAllUrl;

    /**
     * 多租户上下文过滤器
     */
    @Autowired
    private TenantContextFilter tenantContextFilter;

    /**
     * 身份验证实现
     */
    @Bean
    public AuthenticationManager authenticationManager()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }
    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
                // CSRF禁用，因为不使用session
                .csrf(csrf -> csrf.disable())
                // 禁用HTTP响应标头/iframe（保持与原版兼容：允许同源 frame）
                .headers(headers -> headers
                        .cacheControl(cache -> cache.disable())
                        .frameOptions(frame -> frame.sameOrigin()))
                // 认证失败处理类
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                // 基于token，所以不需要session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 过滤请求
                .authorizeHttpRequests(requests -> {
                    // 1) 若依配置文件中标记 permitAll 的 url
                    permitAllUrl.getUrls().forEach(url -> requests.antMatchers(url).permitAll());

                    // 2) 原项目 SecurityConfig.java 里放行的接口/资源（补齐到多租户版）
                    // 登录/注册/验证码
                    requests.antMatchers("/login", "/register", "/captchaImage").permitAll();

                    // 业务/资源放行（原项目）
                    requests.antMatchers(
                            "/3DVisual/**",
                            "/model/**",
                            "/getMenus/**",
                            "/U3DRes/**",
                            "/version/current",
                            "/versionLog",
                            "/versionLog/**",
                            "/WebSocketServer/**",
                            "/alertsWS/**",
                            "/func/**"
                    ).permitAll();

                    // 静态资源（原项目）
                    requests.antMatchers("/static/**", "/index/**").permitAll();

                    // GET 静态资源（原项目/若依常规）
                    requests.antMatchers(
                            HttpMethod.GET,
                            "/",
                            "/*.html",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/profile/**"
                    ).permitAll();

                    // swagger / druid（原项目）
                    requests.antMatchers(
                            "/swagger-ui.html",
                            "/swagger-resources/**",
                            "/webjars/**",
                            "/*/api-docs",
                            "/druid/**"
                    ).permitAll();

                    // 除上面外的所有请求全部需要鉴权认证
                    requests.anyRequest().authenticated();
//                    requests.anyRequest().permitAll();
                })
                // 退出
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler))
                // JWT
                .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 多租户上下文（放在 JWT 之后，确保已解析完 token 再落 tenant）
                .addFilterAfter(tenantContextFilter, JwtAuthenticationTokenFilter.class)
                // CORS
                .addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class)
                .addFilterBefore(corsFilter, LogoutFilter.class)
                .build();
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}