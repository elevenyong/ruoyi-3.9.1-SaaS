package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TWorkingFaceOfUncoverCoal;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfUncoverCoalService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 揭煤工作面Controller
 *
 * @author hhhz
 * @date 2023-05-04
 */
@RestController
@RequestMapping("/uncoverCoal")
public class TWorkingFaceOfUncoverCoalController extends BaseController {
    @Resource
    private ITWorkingFaceOfUncoverCoalService tWorkingFaceOfUncoverCoalService;

    /**
     * 查询揭煤工作面列表
     */
    //@PreAuthorize("@ss.hasPermi('system:uncovercoal:list')")
    @GetMapping("/list")
    public TableDataInfo list(TWorkingFaceOfUncoverCoal tWorkingFaceOfUncoverCoal) {
        startPage();
        List<TWorkingFaceOfUncoverCoal> list = tWorkingFaceOfUncoverCoalService.selectTWorkingFaceOfUncoverCoalList(tWorkingFaceOfUncoverCoal);
        return getDataTable(list);
    }

    /**
     * 导出揭煤工作面列表
     */
    //@PreAuthorize("@ss.hasPermi('system:uncovercoal:export')")
    @Log(title = "揭煤工作面", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWorkingFaceOfUncoverCoal tWorkingFaceOfUncoverCoal) {
        List<TWorkingFaceOfUncoverCoal> list = tWorkingFaceOfUncoverCoalService.selectTWorkingFaceOfUncoverCoalList(tWorkingFaceOfUncoverCoal);
        ExcelUtil<TWorkingFaceOfUncoverCoal> util = new ExcelUtil<TWorkingFaceOfUncoverCoal>(TWorkingFaceOfUncoverCoal.class);
        util.exportExcel(response, list, "揭煤工作面数据");
    }

    /**
     * 获取揭煤工作面详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:uncovercoal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tWorkingFaceOfUncoverCoalService.selectTWorkingFaceOfUncoverCoalById(id));
    }

    /**
     * 新增揭煤工作面
     */
    //@PreAuthorize("@ss.hasPermi('system:uncovercoal:add')")
    @Log(title = "揭煤工作面", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWorkingFaceOfUncoverCoal tWorkingFaceOfUncoverCoal) {
        return toAjax(tWorkingFaceOfUncoverCoalService.insertTWorkingFaceOfUncoverCoal(tWorkingFaceOfUncoverCoal));
    }

    /**
     * 修改揭煤工作面
     */
    //@PreAuthorize("@ss.hasPermi('system:uncovercoal:edit')")
    @Log(title = "揭煤工作面", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWorkingFaceOfUncoverCoal tWorkingFaceOfUncoverCoal) {
        return toAjax(tWorkingFaceOfUncoverCoalService.updateTWorkingFaceOfUncoverCoal(tWorkingFaceOfUncoverCoal));
    }

    /**
     * 删除揭煤工作面
     */
    //@PreAuthorize("@ss.hasPermi('system:uncovercoal:remove')")
    @Log(title = "揭煤工作面", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tWorkingFaceOfUncoverCoalService.deleteTWorkingFaceOfUncoverCoalByIds(ids));
    }
}
