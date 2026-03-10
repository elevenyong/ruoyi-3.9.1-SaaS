package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TDrivingWork;
import com.ruoyi.hh.physical.service.ITDrivingWorkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/drivingWork")
@Api(value = "掘进工作接口", tags = "DZWF掘进*工作*接口")
public class TDrivingWorkController extends BaseController {
    @Resource
    private ITDrivingWorkService tDrivingWorkService;

    /**
     * 查询掘进工作列表
     */
    @GetMapping("/listAll")
    @ApiOperation("查询全部掘进工作列表")
    public TableDataInfo listAll() {
        startPage();
        List<TDrivingWork> list = tDrivingWorkService.selectTDrivingWorkList(new TDrivingWork());
        return getDataTable(list);
    }

    /**
     * 查询掘进工作列表
     */
    @PostMapping("/list")
    @ApiOperation("查询掘进工作列表")
    public TableDataInfo list(@RequestBody TDrivingWork tDrivingWork) {
        startPage();
        List<TDrivingWork> list = tDrivingWorkService.selectTDrivingWorkList(tDrivingWork);
        return getDataTable(list);
    }

    /**
     * 导出掘进工作列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, TDrivingWork tDrivingWork) {
        List<TDrivingWork> list = tDrivingWorkService.selectTDrivingWorkList(tDrivingWork);
        ExcelUtil<TDrivingWork> util = new ExcelUtil<TDrivingWork>(TDrivingWork.class);
        util.exportExcel(response, list, "掘进工作数据");
    }

    /**
     * 获取掘进工作详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("根据ID查询掘进工作")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tDrivingWorkService.selectTDrivingWorkById(id));
    }

    /**
     * 新增掘进工作
     */
    @PostMapping
    @ApiOperation("新增掘进工作")
    public AjaxResult add(@RequestBody TDrivingWork tDrivingWork) {
        TDrivingWork face = tDrivingWorkService.insertTDrivingWork(tDrivingWork);
        if (face != null) {
            return AjaxResult.success(face);
        } else return AjaxResult.error("新增失败！");
    }

    /**
     * 修改掘进工作
     */
    @PutMapping
    @ApiOperation("修改掘进工作")
    public AjaxResult edit(@RequestBody TDrivingWork tDrivingWork) {
        TDrivingWork face = tDrivingWorkService.updateTDrivingWork(tDrivingWork);
        if (face != null) {
            return AjaxResult.success(face);
        } else return AjaxResult.error("修改失败！");
    }

    /**
     * 删除掘进工作
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除掘进工作")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tDrivingWorkService.deleteTDrivingWorkByIds(ids));
    }
}
