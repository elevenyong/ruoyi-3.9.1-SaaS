package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TRockinfo;
import com.ruoyi.hh.physical.domain.TWorkingFaceOfCoalMining;
import com.ruoyi.hh.physical.service.ITRockinfoService;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfCoalMiningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 岩层信息Controller
 *
 * @author hhhz
 * @date 2023-05-05
 */
@Api(value = "DZGEO地层信息接口", tags = "DZGEO地层信息接口")
@RestController
@RequestMapping("/rockinfo")
public class TRockinfoController extends BaseController {
    @Resource
    private ITRockinfoService tRockinfoService;

    @Resource
    private ITWorkingFaceOfCoalMiningService workingFaceService;

    /**
     * 查询岩层信息列表
     */
    //@PreAuthorize("@ss.hasPermi('system:rockinfo:list')")
    @ApiOperation("按条件查询地层信息列表")
    @GetMapping("/list")
    public TableDataInfo list(TRockinfo tRockinfo) {
        startPage();
        List<TRockinfo> list = tRockinfoService.selectTRockinfoList(tRockinfo);
        return getDataTable(list);
    }

    /**
     * 导出岩层信息列表
     */
    //@PreAuthorize("@ss.hasPermi('system:rockinfo:export')")
    @ApiOperation("导出地层信息列表")
    @Log(title = "岩层信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRockinfo tRockinfo) {
        List<TRockinfo> list = tRockinfoService.selectTRockinfoList(tRockinfo);
        ExcelUtil<TRockinfo> util = new ExcelUtil<TRockinfo>(TRockinfo.class);
        util.exportExcel(response, list, "岩层信息数据");
    }

    /**
     * 获取岩层信息详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:rockinfo:query')")
    @ApiOperation("根据ID获取地层详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tRockinfoService.selectTRockinfoById(id));
    }

    /**
     * 新增岩层信息
     */
    //@PreAuthorize("@ss.hasPermi('system:rockinfo:add')")
    @ApiOperation("新增地层信息")
    @Log(title = "岩层信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRockinfo tRockinfo) {
        return toAjax(tRockinfoService.insertTRockinfo(tRockinfo));
    }

    /**
     * 修改岩层信息
     */
    //@PreAuthorize("@ss.hasPermi('system:rockinfo:edit')")
    @ApiOperation("修改地层信息")
    @Log(title = "岩层信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRockinfo tRockinfo) {
        if (tRockinfo.getSerialNumber() == 0 && tRockinfo.getRockType() == 1) {
            TWorkingFaceOfCoalMining face = workingFaceService.selectTWorkingFaceOfCoalMiningById(tRockinfo.getWorkfaceId());
            if (face != null) {
                face.setDefaultCoalThickness(tRockinfo.getThickness());
                workingFaceService.updateTWorkingFaceOfCoalMining(face);
            }
        }
        return toAjax(tRockinfoService.updateTRockinfo(tRockinfo));
    }

    /**
     * 删除岩层信息
     */
    //@PreAuthorize("@ss.hasPermi('system:rockinfo:remove')")
    @ApiOperation("删除地层信息")
    @Log(title = "岩层信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tRockinfoService.deleteTRockinfoByIds(ids));
    }
}
