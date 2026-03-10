package com.ruoyi.hh.physical.controller;

import cn.hutool.core.util.ArrayUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TRocktype;
import com.ruoyi.hh.physical.service.ITRocktypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 岩层类型Controller
 *
 * @author hhhz
 * @date 2023-05-05
 */
@Api(value = "DZGEO岩层类型", tags = "DZGEO岩层类型接口")
@RestController
@RequestMapping("/rocktype")
public class TRocktypeController extends BaseController {
    @Resource
    private ITRocktypeService tRocktypeService;

    /**
     * 查询岩层类型列表
     */
    //@PreAuthorize("@ss.hasPermi('system:rocktype:list')")
    @ApiOperation("按条件查询岩层类型列表")
    @GetMapping("/list")
    public TableDataInfo list(TRocktype tRocktype) {
        startPage();
        List<TRocktype> list = tRocktypeService.selectTRocktypeList(tRocktype);
        return getDataTable(list);
    }

    /**
     * 导出岩层类型列表
     */
    //@PreAuthorize("@ss.hasPermi('system:rocktype:export')")
    @ApiOperation("导出岩层类型列表")
    @Log(title = "岩层类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRocktype tRocktype) {
        List<TRocktype> list = tRocktypeService.selectTRocktypeList(tRocktype);
        ExcelUtil<TRocktype> util = new ExcelUtil<TRocktype>(TRocktype.class);
        util.exportExcel(response, list, "岩层类型数据");
    }

    /**
     * 获取岩层类型详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:rocktype:query')")
    @ApiOperation("根据ID获取岩层类型详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tRocktypeService.selectTRocktypeById(id));
    }

    /**
     * 新增岩层类型
     */
    //@PreAuthorize("@ss.hasPermi('system:rocktype:add')")
    @ApiOperation("新增岩层类型")
    @Log(title = "岩层类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRocktype tRocktype) {
        return toAjax(tRocktypeService.insertTRocktype(tRocktype));
    }

    /**
     * 修改岩层类型
     */
    //@PreAuthorize("@ss.hasPermi('system:rocktype:edit')")
    @ApiOperation("修改岩层类型")
    @Log(title = "岩层类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRocktype tRocktype) {
        if (tRocktype.getId() == 1) {
            return AjaxResult.error("煤层信息不允许修改");
        }
        return toAjax(tRocktypeService.updateTRocktype(tRocktype));
    }

    /**
     * 删除岩层类型
     */
    //@PreAuthorize("@ss.hasPermi('system:rocktype:remove')")
    @ApiOperation("删除岩层类型")
    @Log(title = "岩层类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        if (ArrayUtil.contains(ids, 1)) {
            return AjaxResult.error("煤层信息不允许删除");
        }
        return toAjax(tRocktypeService.deleteTRocktypeByIds(ids));
    }
}
