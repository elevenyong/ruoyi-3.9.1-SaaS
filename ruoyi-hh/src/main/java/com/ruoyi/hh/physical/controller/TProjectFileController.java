package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TProjectFile;
import com.ruoyi.hh.physical.service.ITProjectFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/tProjectFile")
@Api(value = "DZZDB项目资源文件", tags = "DZZDB项目资源文件")
public class TProjectFileController extends BaseController {

    @Resource
    private ITProjectFileService tProjectFileService;


    /**
     * 按条件查询项目资源文件列表
     */
    @PostMapping("/list")
    @ApiOperation("按条件查询项目资源文件列表")
    public TableDataInfo list(@RequestBody TProjectFile TProjectFile) {
        startPage();
        List<TProjectFile> list =  tProjectFileService.selectTProjectFileList(TProjectFile);
        return getDataTable(list);
    }

    /**
     * 导出项目资源文件列表
     */
    @Log(title = "项目资源文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出项目资源文件列表")
    public void export(HttpServletResponse response, TProjectFile TProjectFile) {
        List<TProjectFile> list =  tProjectFileService.selectTProjectFileList(TProjectFile);
        ExcelUtil<TProjectFile> util = new ExcelUtil<TProjectFile>(TProjectFile.class);
        util.exportExcel(response, list, "项目资源文件数据");
    }

    /**
     * 获取项目资源文件详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取单个项目资源文件详细信息")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success( tProjectFileService.selectTProjectFileById(id));
    }

    /**
     * 新增项目资源文件
     */
    @Log(title = "项目资源文件", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增项目资源文件")
    public AjaxResult add(@RequestBody TProjectFile TProjectFile) {
        if (TProjectFile != null) {
            TProjectFile.setId(null);
        } else {
            return AjaxResult.error("数据为null，添加失败");
        }
        int i =  tProjectFileService.insertTProjectFile(TProjectFile);
        if (i > 0) {
            return AjaxResult.success(TProjectFile);
        } else {
            return AjaxResult.error("添加失败");
        }
    }

    /**
     * 修改项目资源文件
     */
    @Log(title = "项目资源文件", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改项目资源文件")
    public AjaxResult edit(@RequestBody TProjectFile TProjectFile) {
        if (TProjectFile != null) {
            int i =  tProjectFileService.updateTProjectFile(TProjectFile);
            if (i > 0) {
                return AjaxResult.success(TProjectFile);
            } else {
                return AjaxResult.error("修改失败");
            }
        } else {
            return AjaxResult.error("数据为null，修改失败");
        }
    }

    /**
     * 删除项目资源文件
     */
    @Log(title = "项目资源文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("根据资源文件ID删除项目资源文件")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax( tProjectFileService.deleteTProjectFileByIds(ids));
    }

}
