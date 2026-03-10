package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TSubArea;
import com.ruoyi.hh.physical.service.ITSubAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/tSubArea")
@Api(value = "工作面子区域接口", tags = "DZ工作面子区域接口")
public class TSubAreaController extends BaseController {

    @Resource
    private ITSubAreaService tSubAreaService;


    /**
     * 按条件查询子区域列表
     */
    @PostMapping("/list")
    @ApiOperation("按条件查询子区域列表")
    public TableDataInfo list(@RequestBody TSubArea tSubArea) {
        startPage();
        List<TSubArea> list = tSubAreaService.selectTSubAreaList(tSubArea);
        return getDataTable(list);
    }

    /**
     * 导出子区域列表
     */
    @Log(title = "子区域", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出子区域列表")
    public void export(HttpServletResponse response, TSubArea tSubArea) {
        List<TSubArea> list = tSubAreaService.selectTSubAreaList(tSubArea);
        ExcelUtil<TSubArea> util = new ExcelUtil<TSubArea>(TSubArea.class);
        util.exportExcel(response, list, "子区域数据");
    }

    /**
     * 获取子区域详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取单个子区域详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tSubAreaService.selectTSubAreaById(id));
    }

    /**
     * 新增子区域
     */
    @Log(title = "子区域", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增子区域")
    public AjaxResult add(@RequestBody TSubArea tSubArea) {
        if (tSubArea != null) {
            tSubArea.setId(null);
        } else {
            return AjaxResult.error("数据为null，添加失败");
        }
        int i = tSubAreaService.insertTSubArea(tSubArea);
        if (i > 0) {
            return AjaxResult.success(tSubArea);
        } else {
            return AjaxResult.error("添加失败");
        }
    }

    /**
     * 修改子区域
     */
    @Log(title = "子区域", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改子区域")
    public AjaxResult edit(@RequestBody TSubArea tSubArea) {
        if (tSubArea != null) {
            int i = tSubAreaService.updateTSubArea(tSubArea);
            if (i > 0) {
                return AjaxResult.success(tSubArea);
            } else {
                return AjaxResult.error("修改失败");
            }
        } else {
            return AjaxResult.error("数据为null，修改失败");
        }
    }

    /**
     * 删除子区域
     */
    @Log(title = "子区域", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("根据子区域ID删除子区域")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tSubAreaService.deleteTSubAreaByIds(ids));
    }


}
