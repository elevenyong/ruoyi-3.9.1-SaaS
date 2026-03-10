package com.ruoyi.framework.config;

import java.util.Properties;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig{
    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        VendorDatabaseIdProvider provider = new VendorDatabaseIdProvider();
        Properties props = new Properties();
        // 数据库厂商名称 -> 标识（与xml中的contains判断对应）
        props.setProperty("MySQL", "mysql");      // _databaseId会被设为"mysql"
        props.setProperty("SQLite", "sqlite");    // _databaseId会被设为"sqlite"
        provider.setProperties(props);
        return provider;
    }
}

