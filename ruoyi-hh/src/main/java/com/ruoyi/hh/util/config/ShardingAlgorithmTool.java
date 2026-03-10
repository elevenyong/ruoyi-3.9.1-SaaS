package com.ruoyi.hh.util.config;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class ShardingAlgorithmTool {

    private static final HashSet<String> tableNameCache = new HashSet<>();

    public static List<String> getAllTableNameBySchema() {
        List<String> res = new ArrayList<>();
        Environment env = SpringUtil.getApplicationContext().getEnvironment();
        try (Connection connection = DriverManager.getConnection(env.getProperty("spring.shardingsphere.datasource.master.url"),
                env.getProperty("spring.shardingsphere.datasource.master.username"),
                env.getProperty("spring.shardingsphere.datasource.master.password"));
             Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery(env.getProperty("sqlScript"))) {
                while (rs.next()) {
                    res.add(rs.getString(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 缓存重载方法
     */
    public static void tableNameCacheReload() {
        // 读取数据库中所有表名
        List<String> tableNameList = getAllTableNameBySchema();
        // 删除旧的缓存(如果存在)
        ShardingAlgorithmTool.tableNameCache.clear();
        // 写入新的缓存
        ShardingAlgorithmTool.tableNameCache.addAll(tableNameList);
    }
}
