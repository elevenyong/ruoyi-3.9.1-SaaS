package com.ruoyi.hh.physical.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.hh.physical.domain.TCoalPoint;
import com.ruoyi.hh.physical.service.ITCoalPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 煤层坐标点信息Controller
 *
 * @author eleven
 * @date 2022-10-20
 */
@RestController
@RequestMapping("/pointinfo")
@Api(value = "煤层坐标点", tags = "DZ煤层坐标点(地测钻孔)接口")
public class TCoalPointController extends BaseController {
    @Resource
    private ITCoalPointService tCoalPointService;

    /**
     * 按条件查询煤层坐标点列表
     */
    //@PreAuthorize("@ss.hasPermi('pointinfo:list')")
    @GetMapping("/list")
    @ApiOperation("按条件查询煤层坐标点列表")
    public TableDataInfo list(TCoalPoint TCoalPoint) {
        startPage();
        List<TCoalPoint> list = tCoalPointService.selectTCoalPointList(TCoalPoint);
        return getDataTable(list);
    }

    /**
     * 导出煤层坐标点列表
     */
    //@PreAuthorize("@ss.hasPermi('pointinfo:export')")
    @Log(title = "煤层坐标点信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出煤层坐标点列表")
    public void export(HttpServletResponse response, TCoalPoint TCoalPoint) {
        List<TCoalPoint> list = tCoalPointService.selectTCoalPointList(TCoalPoint);
        ExcelUtil<TCoalPoint> util = new ExcelUtil<TCoalPoint>(TCoalPoint.class);
        util.exportExcel(response, list, "煤层坐标点信息数据");
    }

    /**
     * 查询单条煤层坐标点
     */
    //@PreAuthorize("@ss.hasPermi('pointinfo:query')")
    @GetMapping(value = "query")
    @ApiOperation("查询单条煤层坐标点")
    public AjaxResult getInfo(Integer id) {
        return AjaxResult.success(tCoalPointService.selectTCoalPointById(id));
    }

    /**
     * 新增单条煤层坐标点
     */
    //@PreAuthorize("@ss.hasPermi('pointinfo:add')")
    @Log(title = "煤层坐标点信息", businessType = BusinessType.INSERT)
    @PostMapping("insert")
    @ApiOperation("新增单条煤层坐标点")
    public AjaxResult add(@RequestBody TCoalPoint TCoalPoint) {
        return toAjax(tCoalPointService.insertTCoalPoint(TCoalPoint));
    }

    /**
     * 批量导入煤层坐标点
     */
    //@PreAuthorize("@ss.hasPermi('pointinfo:add')")
    @Log(title = "煤层坐标点信息", businessType = BusinessType.INSERT)
    @PostMapping("insertList")
    @ApiOperation("批量导入煤层坐标点")
    public AjaxResult addList(@RequestBody List<TCoalPoint> list) {
        int suc = 0;
        int err = 0;
        if (list != null && !list.isEmpty()) {
            // 只删除煤层点，不删除断层及陷落柱点
            int i = tCoalPointService.deleteTCoalPointByCoalinfoId(list.get(0).getCoalinfoId());
            logger.info("清除：{}条煤层点数据。", i);
            for (TCoalPoint TCoalPoint : list) {
                int res = tCoalPointService.insertTCoalPoint(TCoalPoint);
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
     * 修改煤层坐标点信息
     */
    //@PreAuthorize("@ss.hasPermi('pointinfo:edit')")
    @Log(title = "煤层坐标点信息", businessType = BusinessType.UPDATE)
    @PutMapping("updateCoalPointinfo")
    @ApiOperation("修改煤层坐标点")
    public AjaxResult edit(@RequestBody TCoalPoint TCoalPoint) {
        return toAjax(tCoalPointService.updateTCoalPoint(TCoalPoint));
    }

    /**
     * 删除煤层坐标点信息
     */
    //@PreAuthorize("@ss.hasPermi('pointinfo:remove')")
    @Log(title = "煤层坐标点信息", businessType = BusinessType.DELETE)
    @DeleteMapping("del")
    @ApiOperation("删除煤层坐标点")
    public AjaxResult remove(Integer[] ids) {
        return toAjax(tCoalPointService.deleteTCoalPointByIds(ids));
    }
}
