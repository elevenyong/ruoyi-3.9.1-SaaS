package com.ruoyi.hh.hhhz.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.hhhz.domain.Version;
import com.ruoyi.hh.hhhz.service.IVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * versionController
 *
 * @author hhhz
 * @date 2022-08-22
 */
@RestController
@RequestMapping("/version")
public class VersionController extends BaseController {
    @Autowired
    private IVersionService versionService;

    /**
     * 查询version列表
     */
    //@PreAuthorize("@ss.hasPermi('hhhz:version:list')")
    @GetMapping("/list")
    public TableDataInfo list(Version version) {
        startPage();
        List<Version> list = versionService.selectVersionList(version);
        return getDataTable(list);
    }

    /**
     * 导出version列表
     */
    //@PreAuthorize("@ss.hasPermi('hhhz:version:export')")
    @Log(title = "version", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Version version) {
        List<Version> list = versionService.selectVersionList(version);
        ExcelUtil<Version> util = new ExcelUtil<Version>(Version.class);
        util.exportExcel(response, list, "version数据");
    }

    /**
     * 获取version详细信息
     */
    //@PreAuthorize("@ss.hasPermi('hhhz:version:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(versionService.selectVersionById(id));
    }

    /**
     * 新增version
     */
    //@PreAuthorize("@ss.hasPermi('hhhz:version:add')")
    @Log(title = "version", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Version version) {
        return toAjax(versionService.insertVersion(version));
    }

    /**
     * 修改version
     */
    //@PreAuthorize("@ss.hasPermi('hhhz:version:edit')")
    @Log(title = "version", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Version version) {
        return toAjax(versionService.updateVersion(version));
    }

    /**
     * 删除version
     */
    //@PreAuthorize("@ss.hasPermi('hhhz:version:remove')")
    @Log(title = "version", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(versionService.deleteVersionByIds(ids));
    }

    @PutMapping("/current/{id}")
    public AjaxResult putCurrentVersion(@PathVariable Long id) {
        return toAjax(versionService.putCurrentVersion(id));
    }

    @GetMapping("/current")
    public AjaxResult getCurrentVersion() {
        Version version = new Version();
        version.setCurrentVersion(1);
        List<Version> versions = versionService.selectVersionList(version);
        if (versions.size() == 1) {
            return AjaxResult.success(versions.get(0));
        } else if (versions.size() > 1) {
            return AjaxResult.error("数据错误，请稍后重试");
        } else {
            return AjaxResult.error("未选中当前版本");
        }
    }
}
