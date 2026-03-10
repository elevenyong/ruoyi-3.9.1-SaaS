package com.ruoyi.framework.datasource;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.VersionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DatabaseUpdater {


    @Autowired
    private RuoYiConfig ruoYiConfig;

    private final JdbcTemplate jdbcTemplate;


    public DatabaseUpdater(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    @Transactional
    public void checkAndUpdateDatabase() throws Exception {
        System.out.println("检查连接数据库版本...");
        List<Integer> dbVersions = getDbVersions();
        List<Integer> codeVersions = getCodeVersions();
        Map<Integer, String> codeVersionMaps = getCodeVersionMaps();

        if (!dbVersions.isEmpty()) {
            // 取最后一个
            int currentDbVersion = dbVersions.get(dbVersions.size() - 1);
            // 取最后一个
            int targetVersion = codeVersions.get(codeVersions.size() - 1);

            System.out.println("[DB] 当前数据库版本 = " + currentDbVersion);
            System.out.println("[DB] 应用目标数据库版本 = " + targetVersion);

            if (currentDbVersion > targetVersion) {
                throw new IllegalStateException("数据库版本高于程序版本，请更新应用程序！");
            } else if (currentDbVersion < targetVersion) {
                System.out.println("数据库版本低于程序版本，即将升级数据库！");
                for (Integer ver : codeVersions) {
                    if (ver > currentDbVersion) {
                        System.out.println("[DB] 准备升级版本-> " + ver);
                        String executedSql = executeSqlFileForVersion(ver);
                        updateVersion(ver, codeVersionMaps.get(ver), executedSql);
                        System.out.println("[DB] 已升级到版本 " + ver);
                    }
                }
            } else {
                System.out.println("版本检查一致，程序即将启动！");
            }
        } else {
            System.out.println("无版本信息！");
        }
    }

    private Map<Integer, String> getCodeVersionMaps() {
        List<VersionLog> versionLogs = ruoYiConfig.getVersionLogs();
        HashMap<Integer, String> vMaps = new HashMap<>();
        for (VersionLog versionLog : versionLogs) {
            vMaps.put(versionLog.getVersionCode(), versionLog.getVersionName());
        }
        return vMaps;
    }

    private List<Integer> getCodeVersions() {
        List<VersionLog> versionLogs = ruoYiConfig.getVersionLogs();
        ArrayList<Integer> list = new ArrayList<>();
        for (VersionLog versionLog : versionLogs) {
            list.add(versionLog.getVersionCode());
        }
        return list;
    }

    private List<Integer> getDbVersions() {
        try {
            return jdbcTemplate.query(
                    "SELECT version_code FROM sys_version ORDER BY version_code",
                    (rs, rowNum) -> rs.getInt("version_code")
            );
        } catch (Exception e) {
            List<Integer> initList = new ArrayList<>();
            initList.add(25053101);
            return initList;
        }
    }

    @Transactional
    public String executeSqlFileForVersion(int version) {
        try {
            String pattern = String.format("classpath*:db/migrate/V%d.sql", version);
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(pattern);

        /*if (resources.length == 0) {
            throw new RuntimeException("找不到升级脚本：" + pattern);
        }*/
            if (resources.length == 0) {
                System.out.println("无升级文件，未执行SQL。");
                return "无升级文件，未执行SQL。";
            } else {
                System.out.println("执行SQL文件：" + pattern + "。");
                String sql = StreamUtils.copyToString(resources[0].getInputStream(), StandardCharsets.UTF_8);
                System.out.println(sql);

                // 按分号拆分并逐条执行（注意：简单拆分，复杂脚本需更慎重）
                String[] statements = sql.split("(?<=;)");
                for (String stmt : statements) {
                    stmt = stmt.trim();
                    if (!stmt.isEmpty() && !stmt.startsWith("#") && !stmt.startsWith("--")) {
                        jdbcTemplate.execute(stmt);
                    }
                }

                return pattern;
            }
        } catch (Exception e) {
            System.out.println(version + ",SQL执行错误，自动回滚");
            throw new RuntimeException(e);
        }
    }

    private void updateVersion(int version, String versionName, String executedSql) {
        jdbcTemplate.update(
                "INSERT INTO sys_version (version_code,version_name, update_time,executed_sql) VALUES (?,?,?, ?)",
                version, versionName, LocalDateTime.now().toString(), executedSql
        );
    }
}
