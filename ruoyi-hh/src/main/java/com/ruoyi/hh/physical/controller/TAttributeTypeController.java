package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TAttributeType;
import com.ruoyi.hh.physical.service.ITAttributeTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/tAttributeType")
@Api(value = "工作面属性类型接口", tags = "DZATTR工作面属性类型值接口")
public class TAttributeTypeController extends BaseController {

    @Resource
    private ITAttributeTypeService tAttributeTypeService;


    /**
     * 按条件查询属性类型列表
     */
    @PostMapping("/list")
    @ApiOperation("按条件查询属性类型列表")
    public TableDataInfo list(@RequestBody TAttributeType tAttributeType) {
        startPage();
        List<TAttributeType> list = tAttributeTypeService.selectTAttributeTypeList(tAttributeType);
        return getDataTable(list);
    }

    /**
     * 导出属性类型列表
     */
    @Log(title = "属性类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出属性类型列表")
    public void export(HttpServletResponse response, TAttributeType tAttributeType) {
        List<TAttributeType> list = tAttributeTypeService.selectTAttributeTypeList(tAttributeType);
        ExcelUtil<TAttributeType> util = new ExcelUtil<TAttributeType>(TAttributeType.class);
        util.exportExcel(response, list, "属性类型数据");
    }

    /**
     * 获取属性类型详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取单个属性类型详细信息")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tAttributeTypeService.selectTAttributeTypeById(id));
    }

    /**
     * 新增属性类型
     */
    @Log(title = "属性类型", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增属性类型")
    public AjaxResult add(@RequestBody TAttributeType tAttributeType) {
        if (tAttributeType != null) {
            tAttributeType.setId(null);
        } else {
            return AjaxResult.error("数据为null，添加失败");
        }
        int i = tAttributeTypeService.insertTAttributeType(tAttributeType);
        if (i > 0) {
            return AjaxResult.success(tAttributeType);
        } else {
            return AjaxResult.error("添加失败");
        }
    }

    /**
     * 修改属性类型
     */
    @Log(title = "属性类型", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改属性类型")
    public AjaxResult edit(@RequestBody TAttributeType tAttributeType) {
        if (tAttributeType != null) {
            int i = tAttributeTypeService.updateTAttributeType(tAttributeType);
            if (i > 0) {
                return AjaxResult.success(tAttributeType);
            } else {
                return AjaxResult.error("修改失败");
            }
        } else {
            return AjaxResult.error("数据为null，修改失败");
        }
    }

    /**
     * 删除属性类型
     */
    @Log(title = "属性类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("根据钻孔ID删除属性类型")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tAttributeTypeService.deleteTAttributeTypeByIds(ids));
    }


}
