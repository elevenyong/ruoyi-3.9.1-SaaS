package com.ruoyi.hh.util.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.hh.util.domain.THolesealType;
import com.ruoyi.hh.util.service.ITHolesealTypeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 封孔方式信息Controller
 *
 * @author eleven
 * @date 2022-09-22
 */
@RestController
@RequestMapping("tHolesealType")
public class THolesealTypeController extends BaseController
{
    @Autowired
    private ITHolesealTypeService tHolesealTypeService;

    /**
     * 查询封孔方式信息列表
     */
    @PreAuthorize("@ss.hasPermi('tHolesealType:list')")
    @GetMapping("/list")
    public TableDataInfo list(THolesealType tHolesealType)
    {
        startPage();
        List<THolesealType> list = tHolesealTypeService.selectTHolesealTypeList(tHolesealType);
        return getDataTable(list);
    }

    /**
     * 导出封孔方式信息列表
     */
    @PreAuthorize("@ss.hasPermi('tHolesealType:export')")
    @Log(title = "封孔方式信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, THolesealType tHolesealType)
    {
        List<THolesealType> list = tHolesealTypeService.selectTHolesealTypeList(tHolesealType);
        ExcelUtil<THolesealType> util = new ExcelUtil<THolesealType>(THolesealType.class);
        util.exportExcel(response, list, "封孔方式信息数据");
    }

    /**
     * 获取封孔方式信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('tHolesealType:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(tHolesealTypeService.selectTHolesealTypeById(id));
    }

    /**
     * 新增封孔方式信息
     */
    @PreAuthorize("@ss.hasPermi('tHolesealType:add')")
    @Log(title = "封孔方式信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody THolesealType tHolesealType)
    {
        return toAjax(tHolesealTypeService.insertTHolesealType(tHolesealType));
    }

    /**
     * 修改封孔方式信息
     */
    @PreAuthorize("@ss.hasPermi('tHolesealType:edit')")
    @Log(title = "封孔方式信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody THolesealType tHolesealType)
    {
        return toAjax(tHolesealTypeService.updateTHolesealType(tHolesealType));
    }

    /**
     * 删除封孔方式信息
     */
    @PreAuthorize("@ss.hasPermi('tHolesealType:remove')")
    @Log(title = "封孔方式信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@RequestParam("ids") List<Integer>  ids)
    {
        return toAjax(tHolesealTypeService.deleteTHolesealTypeByIds(ids));
    }



}