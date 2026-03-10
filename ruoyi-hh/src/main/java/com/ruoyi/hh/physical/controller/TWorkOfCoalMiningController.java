package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TWorkOfCoalMining;
import com.ruoyi.hh.physical.service.ITWorkOfCoalMiningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/workOfCoalMining")
@Api(value = "回采工作接口", tags = "DZWF回采*工作*接口")
public class TWorkOfCoalMiningController extends BaseController {
    @Resource
    private ITWorkOfCoalMiningService tWorkOfCoalMiningService;

    /**
     * 查询回采工作列表
     */
    @GetMapping("/listAll")
    @ApiOperation("查询全部回采工作列表")
    public TableDataInfo listAll() {
        startPage();
        List<TWorkOfCoalMining> list = tWorkOfCoalMiningService.selectTWorkOfCoalMiningList(new TWorkOfCoalMining());
        return getDataTable(list);
    }

    /**
     * 查询回采工作列表
     */
    @PostMapping("/list")
    @ApiOperation("查询回采工作列表")
    public TableDataInfo list(@RequestBody TWorkOfCoalMining tWorkOfCoalMining) {
        startPage();
        List<TWorkOfCoalMining> list = tWorkOfCoalMiningService.selectTWorkOfCoalMiningList(tWorkOfCoalMining);
        return getDataTable(list);
    }

    /**
     * 导出回采工作列表
     */
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWorkOfCoalMining tWorkOfCoalMining) {
        List<TWorkOfCoalMining> list = tWorkOfCoalMiningService.selectTWorkOfCoalMiningList(tWorkOfCoalMining);
        ExcelUtil<TWorkOfCoalMining> util = new ExcelUtil<TWorkOfCoalMining>(TWorkOfCoalMining.class);
        util.exportExcel(response, list, "回采工作数据");
    }

    /**
     * 获取回采工作详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("根据ID查询回采工作")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tWorkOfCoalMiningService.selectTWorkOfCoalMiningById(id));
    }

    /**
     * 获取回采工作详细信息
     */
    @GetMapping(value = "/workface/{id}")
    @ApiOperation("根据工作面ID查询回采工作")
    public TableDataInfo getInfoByWorkfaceId(@PathVariable("id") Integer id) {
        startPage();
        TWorkOfCoalMining queryWOCM = new TWorkOfCoalMining();
        queryWOCM.setWorkfaceId(id);
        List<TWorkOfCoalMining> workOfCoalMinings = tWorkOfCoalMiningService.selectTWorkOfCoalMiningList(queryWOCM);
        return getDataTable(workOfCoalMinings);
    }

    /**
     * 新增回采工作
     */
    @PostMapping
    @ApiOperation("新增回采工作")
    public AjaxResult add(@RequestBody TWorkOfCoalMining tWorkOfCoalMining) {
        TWorkOfCoalMining face = tWorkOfCoalMiningService.insertTWorkOfCoalMining(tWorkOfCoalMining);
        if (face != null) {
            return AjaxResult.success(face);
        } else return AjaxResult.error("新增失败！");
    }

    /**
     * 修改回采工作
     */
    @PutMapping
    @ApiOperation("修改回采工作")
    public AjaxResult edit(@RequestBody TWorkOfCoalMining tWorkOfCoalMining) {
        TWorkOfCoalMining face = tWorkOfCoalMiningService.updateTWorkOfCoalMining(tWorkOfCoalMining);
        if (face != null) {
            return AjaxResult.success(face);
        } else return AjaxResult.error("修改失败！");
    }

    /**
     * 删除回采工作
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除回采工作")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tWorkOfCoalMiningService.deleteTWorkOfCoalMiningByIds(ids));
    }
}
