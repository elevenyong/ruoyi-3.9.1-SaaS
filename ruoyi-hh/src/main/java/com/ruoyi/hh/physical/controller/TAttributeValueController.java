package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TAttributeValue;
import com.ruoyi.hh.physical.domain.TCoalPoint;
import com.ruoyi.hh.physical.service.ITAttributeValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/tAttributeValue")
@Api(value = "工作面属性值接口", tags = "DZATTR工作面属性值接口")
public class TAttributeValueController extends BaseController {

    @Resource
    private ITAttributeValueService tAttributeValueService;


    /**
     * 按条件查询属性值列表
     */
    @PostMapping("/list")
    @ApiOperation("按条件查询属性值列表")
    public TableDataInfo list(@RequestBody TAttributeValue tAttributeValue) {
        startPage();
        List<TAttributeValue> list = tAttributeValueService.selectTAttributeValueList(tAttributeValue);
        return getDataTable(list);
    }

    /**
     * 导出属性值列表
     */
    @Log(title = "属性值", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出属性值列表")
    public void export(HttpServletResponse response, TAttributeValue tAttributeValue) {
        List<TAttributeValue> list = tAttributeValueService.selectTAttributeValueList(tAttributeValue);
        ExcelUtil<TAttributeValue> util = new ExcelUtil<TAttributeValue>(TAttributeValue.class);
        util.exportExcel(response, list, "属性值数据");
    }

    /**
     * 获取属性值详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取单个属性值详细信息")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tAttributeValueService.selectTAttributeValueById(id));
    }

    /**
     * 新增属性值
     */
    @Log(title = "属性值", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增属性值")
    public AjaxResult add(@RequestBody TAttributeValue tAttributeValue) {
        if (tAttributeValue != null) {
            tAttributeValue.setId(null);
        } else {
            return AjaxResult.error("数据为null，添加失败");
        }
        int i = tAttributeValueService.insertTAttributeValue(tAttributeValue);
        if (i > 0) {
            return AjaxResult.success(tAttributeValue);
        } else {
            return AjaxResult.error("添加失败");
        }
    }

    /**
     * 批量导入属性值
     */
    @PostMapping("insertList")
    @ApiOperation("批量导入属性值")
    public AjaxResult addList(@RequestBody List<TAttributeValue> list) {
        int suc = 0;
        int err = 0;
        if (list != null && !list.isEmpty()) {
            for (TAttributeValue attributeValue : list) {
                int res = tAttributeValueService.insertTAttributeValue(attributeValue);
                if (res > 0) {
                    suc++;
                } else {
                    err++;
                }
            }
        }
        if (suc > 0) {
            return AjaxResult.success("成功添加" + suc + "条,失败" + err + "条");
        } else {
            return AjaxResult.error("添加失败");
        }
    }

    /**
     * 修改属性值
     */
    @Log(title = "属性值", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改属性值")
    public AjaxResult edit(@RequestBody TAttributeValue tAttributeValue) {
        if (tAttributeValue != null) {
            int i = tAttributeValueService.updateTAttributeValue(tAttributeValue);
            if (i > 0) {
                return AjaxResult.success(tAttributeValue);
            } else {
                return AjaxResult.error("修改失败");
            }
        } else {
            return AjaxResult.error("数据为null，修改失败");
        }
    }

    /**
     * 删除属性值
     */
    @Log(title = "属性值", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("根据ID删除属性值")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tAttributeValueService.deleteTAttributeValueByIds(ids));
    }


}
