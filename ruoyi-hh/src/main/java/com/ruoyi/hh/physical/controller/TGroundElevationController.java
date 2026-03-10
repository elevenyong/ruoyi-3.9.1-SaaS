package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TGroundElevation;
import com.ruoyi.hh.physical.service.ITGroundElevationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 地面高程点信息Controller
 *
 * @author eleven
 * @date 2022-10-20
 */
@RestController
@RequestMapping("/groundElevation")
@Api(value = "地面高程点", tags = "DZ地面高程点接口")
public class TGroundElevationController extends BaseController {
    @Resource
    private ITGroundElevationService tGroundElevationService;

    /**
     * 按条件查询地面高程点列表
     */
    @GetMapping("/list")
    @ApiOperation("按条件查询地面高程点列表")
    public TableDataInfo list(TGroundElevation TGroundElevation) {
        startPage();
        List<TGroundElevation> list = tGroundElevationService.selectTGroundElevationList(TGroundElevation);
        return getDataTable(list);
    }

    /**
     * 导出地面高程点列表
     */
    @Log(title = "地面高程点信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出地面高程点列表")
    public void export(HttpServletResponse response, TGroundElevation TGroundElevation) {
        List<TGroundElevation> list = tGroundElevationService.selectTGroundElevationList(TGroundElevation);
        ExcelUtil<TGroundElevation> util = new ExcelUtil<TGroundElevation>(TGroundElevation.class);
        util.exportExcel(response, list, "地面高程点信息数据");
    }

    /**
     * 查询单条地面高程点
     */
    @GetMapping(value = "query")
    @ApiOperation("查询单条地面高程点")
    public AjaxResult getInfo(Integer id) {
        return AjaxResult.success(tGroundElevationService.selectTGroundElevationById(id));
    }

    /**
     * 新增单条地面高程点
     */
    @Log(title = "地面高程点信息", businessType = BusinessType.INSERT)
    @PostMapping("insert")
    @ApiOperation("新增单条地面高程点")
    public AjaxResult add(@RequestBody TGroundElevation tGroundElevation) {
        int i = tGroundElevationService.insertTGroundElevation(tGroundElevation);
        if (i > 0) {
            return AjaxResult.success(tGroundElevation);
        }else {
            return AjaxResult.error("新增失败，请检查后重试！");
        }
    }

    /**
     * 批量导入地面高程点
     */
    @Log(title = "地面高程点信息", businessType = BusinessType.INSERT)
    @PostMapping("insertList")
    @ApiOperation("批量导入地面高程点")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addList(@RequestBody List<TGroundElevation> list) {
        int insertCount = tGroundElevationService.insertList(list);
        if (insertCount > 0) {
            return AjaxResult.success("成功添加"+insertCount+"条数据");
        }else {
            return AjaxResult.error("添加失败，请检查后重试");
        }
    }

    /**
     * 修改地面高程点信息
     */
    //@PreAuthorize("@ss.hasPermi('pointinfo:edit')")
    @Log(title = "地面高程点信息", businessType = BusinessType.UPDATE)
    @PutMapping("updateGroundElevation")
    @ApiOperation("修改地面高程点")
    public AjaxResult edit(@RequestBody TGroundElevation TGroundElevation) {
        return toAjax(tGroundElevationService.updateTGroundElevation(TGroundElevation));
    }

    /**
     * 删除地面高程点信息
     */
    @Log(title = "地面高程点信息", businessType = BusinessType.DELETE)
    @DeleteMapping("deleteByIds")
    @ApiOperation("删除地面高程点")
    public AjaxResult remove(Integer[] ids) {
        return toAjax(tGroundElevationService.deleteTGroundElevationByIds(ids));
    }
}
