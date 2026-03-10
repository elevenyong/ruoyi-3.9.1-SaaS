package com.ruoyi.hh.util.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.util.domain.TCoalPointtype;
import com.ruoyi.hh.util.service.ITCoalPointtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 煤层坐标点类型Controller
 *
 * @author ruoyi
 * @date 2022-12-02
 */
@RestController
@RequestMapping("/pointtype")
public class TCoalPointtypeController extends BaseController {
    @Autowired
    private ITCoalPointtypeService tCoalPointtypeService;

    /**
     * 查询煤层坐标点类型列表
     */
    //@PreAuthorize("@ss.hasPermi('system:pointtype:list')")
    @GetMapping("/list")
    @Log(title = "煤层坐标点类型", businessType = BusinessType.QUERY)
    public TableDataInfo list(TCoalPointtype tCoalPointtype) {
        startPage();
        List<TCoalPointtype> list = tCoalPointtypeService.selectTCoalPointtypeList(tCoalPointtype);
        return getDataTable(list);
    }

    /**
     * 导出煤层坐标点类型列表
     */
    //@PreAuthorize("@ss.hasPermi('system:pointtype:export')")
    @Log(title = "煤层坐标点类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TCoalPointtype tCoalPointtype) {
        List<TCoalPointtype> list = tCoalPointtypeService.selectTCoalPointtypeList(tCoalPointtype);
        ExcelUtil<TCoalPointtype> util = new ExcelUtil<TCoalPointtype>(TCoalPointtype.class);
        util.exportExcel(response, list, "煤层坐标点类型数据");
    }

    /**
     * 获取煤层坐标点类型详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:pointtype:query')")
    @GetMapping(value = "/{id}")
    @Log(title = "煤层坐标点类型", businessType = BusinessType.QUERY)
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tCoalPointtypeService.selectTCoalPointtypeById(id));
    }

    /**
     * 新增煤层坐标点类型
     */
    //@PreAuthorize("@ss.hasPermi('system:pointtype:add')")
    @Log(title = "煤层坐标点类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TCoalPointtype tCoalPointtype) {
        return toAjax(tCoalPointtypeService.insertTCoalPointtype(tCoalPointtype));
    }

    /**
     * 修改煤层坐标点类型
     */
    //@PreAuthorize("@ss.hasPermi('system:pointtype:edit')")
    @Log(title = "煤层坐标点类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TCoalPointtype tCoalPointtype) {
        return toAjax(tCoalPointtypeService.updateTCoalPointtype(tCoalPointtype));
    }

    /**
     * 删除煤层坐标点类型
     */
    //@PreAuthorize("@ss.hasPermi('system:pointtype:remove')")
    @Log(title = "煤层坐标点类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tCoalPointtypeService.deleteTCoalPointtypeByIds(ids));
    }
}
