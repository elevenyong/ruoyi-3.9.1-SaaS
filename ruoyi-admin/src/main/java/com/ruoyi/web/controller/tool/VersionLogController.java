package com.ruoyi.web.controller.tool;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.VersionLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "服务器版本信息接口", tags = "SYS服务器版本信息")
@RestController
@RequestMapping("/versionLog")
public class VersionLogController {

    @Autowired
    private RuoYiConfig ruoYiConfig;

    @ApiOperation("获取当前版本")
    @GetMapping("/current")
    public AjaxResult current() {
        List<VersionLog> versionLogs = ruoYiConfig.getVersionLogs();
        return AjaxResult.success(versionLogs.get(versionLogs.size() - 1));
    }

    @ApiOperation("历史版本信息")
    @GetMapping()
    public String index() {
        StringBuilder sb = new StringBuilder();
        List<VersionLog> versionLogs = ruoYiConfig.getVersionLogs();
        sb.append("最新版本：").append(versionLogs.get(versionLogs.size() - 1).getVersionName()).append("(").append(versionLogs.get(versionLogs.size() - 1).getVersionCode()).append(")").append("<br/>");
        for (VersionLog versionLog : versionLogs) {
            sb.append("-").append(versionLog.getVersionName()).append("(").append(versionLog.getVersionCode()).append(") ").append(versionLog.getVersionDesc()).append("<br/>");
        }
        return sb.toString();
    }
}
