package com.ruoyi.hh.demo.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.demo.domain.TGeoFault;
import com.ruoyi.hh.demo.service.ITGeoFaultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 断层Controller
 *
 * @author hhhz
 * @date 2022-10-28
 */
@Api(value = "DZGEO断层接口", tags = "DZGEO断层接口")
@RestController
@RequestMapping("/geofault")
public class TGeoFaultController extends BaseController {
    @Resource
    private ITGeoFaultService TGeoFaultService;

    /**
     * 按条件查询断层列表
     */
    @GetMapping("/list")
    @ApiOperation("按条件查询断层列表")
    public TableDataInfo list(TGeoFault TGeoFault) {
        startPage();
        List<TGeoFault> list = TGeoFaultService.selectTGeoFaultList(TGeoFault);
        return getDataTable(list);
    }

    /**
     * 导出断层列表
     */
    @Log(title = "断层", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出断层列表")
    public void export(HttpServletResponse response, TGeoFault TGeoFault) {
        List<TGeoFault> list = TGeoFaultService.selectTGeoFaultList(TGeoFault);
        ExcelUtil<TGeoFault> util = new ExcelUtil<TGeoFault>(TGeoFault.class);
        util.exportExcel(response, list, "断层数据");
    }

    /**
     * 获取断层详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("获取单个断层详细信息")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(TGeoFaultService.selectTGeoFaultById(id));
    }

    /**
     * 新增断层
     */
    @Log(title = "断层", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增断层")
    public AjaxResult add(@RequestBody TGeoFault TGeoFault) {
        if (TGeoFault != null) {
            TGeoFault.setId(null);
        } else {
            return AjaxResult.error("断层数据为null，添加失败");
        }
        return TGeoFaultService.insertTGeoFault(TGeoFault);
    }

    /**
     * 修改断层
     */
    @Log(title = "断层", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改断层")
    public AjaxResult edit(@RequestBody TGeoFault TGeoFault) {
        return TGeoFaultService.updateTGeoFault(TGeoFault);
    }

    /**
     * 删除断层
     */
    @Log(title = "断层", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("根据钻孔ID删除断层")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(TGeoFaultService.deleteTGeoFaultByIds(ids));
    }
}
