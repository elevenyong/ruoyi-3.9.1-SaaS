package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TWorkingFaceOfDriving;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfDrivingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/drivingWorkingFace")
@Api(value = "掘进工作面接口", tags = "DZWF掘进工作面接口")
public class TWorkingFaceOfDrivingController extends BaseController {
    @Resource
    private ITWorkingFaceOfDrivingService tWorkingFaceOfDrivingService;

    /**
     * 查询掘进工作面列表
     */
    @GetMapping("/listAll")
    @ApiOperation("查询全部掘进工作面列表")
    public TableDataInfo listAll() {
        startPage();
        List<TWorkingFaceOfDriving> list = tWorkingFaceOfDrivingService.selectTWorkingFaceOfDrivingList(new TWorkingFaceOfDriving());
        return getDataTable(list);
    }

    /**
     * 查询掘进工作面列表
     */
    @PostMapping("/list")
    @ApiOperation("查询掘进工作面列表")
    public TableDataInfo list(@RequestBody TWorkingFaceOfDriving tWorkingFaceOfDriving) {
        startPage();
        List<TWorkingFaceOfDriving> list = tWorkingFaceOfDrivingService.selectTWorkingFaceOfDrivingList(tWorkingFaceOfDriving);
        return getDataTable(list);
    }

    /**
     * 导出掘进工作面列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWorkingFaceOfDriving tWorkingFaceOfDriving) {
        List<TWorkingFaceOfDriving> list = tWorkingFaceOfDrivingService.selectTWorkingFaceOfDrivingList(tWorkingFaceOfDriving);
        ExcelUtil<TWorkingFaceOfDriving> util = new ExcelUtil<TWorkingFaceOfDriving>(TWorkingFaceOfDriving.class);
        util.exportExcel(response, list, "掘进工作面数据");
    }

    /**
     * 获取掘进工作面详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("根据ID查询掘进工作面")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        TWorkingFaceOfDriving face = tWorkingFaceOfDrivingService.selectTWorkingFaceOfDrivingById(id);
        return AjaxResult.success(face);
    }

    /**
     * 新增掘进工作面
     */
    @PostMapping
    @ApiOperation("新增掘进工作面")
    public AjaxResult add(@RequestBody TWorkingFaceOfDriving tWorkingFaceOfDriving) {
        TWorkingFaceOfDriving face = tWorkingFaceOfDrivingService.insertTWorkingFaceOfDriving(tWorkingFaceOfDriving);
        if (face != null) {
            return AjaxResult.success(face);
        } else return AjaxResult.error();
    }

    /**
     * 修改掘进工作面
     */
    @PutMapping
    @ApiOperation("修改掘进工作面")
    public AjaxResult edit(@RequestBody TWorkingFaceOfDriving tWorkingFaceOfDriving) {
        TWorkingFaceOfDriving face = tWorkingFaceOfDrivingService.updateTWorkingFaceOfDriving(tWorkingFaceOfDriving);
        if (face != null) {
            return AjaxResult.success(face);
        } else return AjaxResult.error();
    }

    /**
     * 删除掘进工作面
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除掘进工作面")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tWorkingFaceOfDrivingService.deleteTWorkingFaceOfDrivingByIds(ids));
    }
}
