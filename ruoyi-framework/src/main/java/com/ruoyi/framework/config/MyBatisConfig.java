package com.ruoyi.framework.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.ruoyi.framework.tenant.mybatis.TenantSqlInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * MyBatis / MyBatis-Plus 配置
 *
 * 方案 2：通过 SqlSessionFactoryBean#setPlugins 显式注册 TenantSqlInterceptor，确保拦截器一定生效。
 */
@Configuration
public class MyBatisConfig {

    /**
     * 租户 SQL 改写拦截器 Bean
     */
    @Bean
    public TenantSqlInterceptor tenantSqlInterceptor() {
        return new TenantSqlInterceptor();
    }

    /**
     * 自定义 SqlSessionFactory：
     * - 复用 application.yml 中 mybatis-plus.* 的配置
     * - 显式 setPlugins，把 Spring 容器里所有 MyBatis Interceptor 都挂上（含 PageHelper 等）
     * - 尽量把 TenantSqlInterceptor 排在最后（避免影响其它插件行为）
     */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource,
                                               MybatisPlusProperties properties,
                                               ApplicationContext applicationContext) throws Exception {

        MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);

        // 复用 mybatis-plus 配置
        MybatisConfiguration configuration = new MybatisConfiguration();
        factory.setConfiguration(configuration);
        factory.setGlobalConfig(properties.getGlobalConfig());
        factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());

        // mapper-locations
        String[] mapperLocations = properties.getMapperLocations();
        if (mapperLocations != null && mapperLocations.length > 0) {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            List<Resource> resources = new ArrayList<>();
            for (String location : mapperLocations) {
                for (Resource r : resolver.getResources(location)) {
                    resources.add(r);
                }
            }
            factory.setMapperLocations(resources.toArray(new Resource[0]));
        }

        // ⭐关键：显式挂载所有 Interceptor（TenantSqlInterceptor 必须在这里被 setPlugins）
        Map<String, Interceptor> beans = applicationContext.getBeansOfType(Interceptor.class);
        List<Interceptor> plugins = new ArrayList<>(beans.values());

        // 尽量让 TenantSqlInterceptor 排最后
        plugins.sort(Comparator.comparing(i -> (i instanceof TenantSqlInterceptor) ? "z" : "a" + i.getClass().getName()));
        factory.setPlugins(plugins.toArray(new Interceptor[0]));

        return factory.getObject();
    }
}