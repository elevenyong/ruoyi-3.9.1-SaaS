package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TActDirectionalBorehole;
import com.ruoyi.hh.physical.domain.TActDirectionalDrillingPoint;
import com.ruoyi.hh.physical.service.ITActDirectionalBoreholeService;
import com.ruoyi.hh.physical.service.ITActDirectionalDrillingPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 实际定向钻(TActDirectionalBorehole)表控制层
 *
 * @author makejava
 * @since 2023-07-27 15:48:43
 */
@Api(value = "DZZK实钻定向钻接口", tags = "DZZK实钻定向钻接口")
@RestController
@RequestMapping("tActDirectionalBorehole")
public class TActDirectionalBoreholeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITActDirectionalBoreholeService tActDirectionalBoreholeService;
    @Resource
    private ITActDirectionalDrillingPointService itActDirectionalDrillingPointService;

    /**
     * 获取列表
     *
     * @param tActDirectionalBorehole 要查询的信息
     * @return 返回结果
     */
    @ApiOperation("按条件查询实钻定向钻列表")
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalBorehole:list')")
    public TableDataInfo selectAll(@RequestBody TActDirectionalBorehole tActDirectionalBorehole) {
        startPage();
        List<TActDirectionalBorehole> list = this.tActDirectionalBoreholeService.selectTActDirectionalBoreholeList(tActDirectionalBorehole);
        if (list.size() > 0) {
            for (TActDirectionalBorehole tdb : list) {
                tdb.settDirectionalDrillingPointList(itActDirectionalDrillingPointService.getByActDirectionalBoreholeId(tdb.getId()));
            }
        }
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tActDirectionalBorehole 单条数据
     */
    @ApiOperation("新增单条实钻定向钻数据")
    @Transactional
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalBorehole:insert')")
    public AjaxResult insertTActDirectionalBorehole(@RequestBody TActDirectionalBorehole tActDirectionalBorehole) {
        TActDirectionalBorehole hole = tActDirectionalBorehole;
        if (hole != null && hole.gettDirectionalDrillingPointList() != null && !hole.gettDirectionalDrillingPointList().isEmpty()) {
            int result = this.tActDirectionalBoreholeService.insertTActDirectionalBorehole(tActDirectionalBorehole);
            if (result <= 0) {
                if (result == -1) {
                    return AjaxResult.error("添加失败，钻孔数据已存在");
                } else if (result == -2) {
                    return AjaxResult.error("添加失败，钻孔信息添加失败");
                } else if (result == -3) {
                    return AjaxResult.error("添加失败，钻孔轨迹数据为空");
                } else if (result == -4) {
                    return AjaxResult.error("添加失败，部分轨迹保存失败");
                } else {
                    return AjaxResult.error("添加失败");
                }
            }else {
                return AjaxResult.success("添加成功");
            }
        }
        return AjaxResult.error("添加失败，钻孔信息为空或钻孔轨迹为空");
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @ApiOperation("批量插入实钻定向钻数据")
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalBorehole:insert')")
    public AjaxResult insert(@RequestBody List<TActDirectionalBorehole> list) {
        int succ = 0;
        int fail = 0;
        if (!list.isEmpty()) {
            HashSet<String> failDrill = new HashSet<>();
            for (TActDirectionalBorehole td : list) {
                int i1 = this.tActDirectionalBoreholeService.insertTActDirectionalBorehole(td);
                if (i1 > 0) {
                    succ++;
                } else {
                    fail++;
                    if (i1 == -1) {
                        failDrill.add(td.getBoreholeCode() + ":钻孔重复");
                    } else if (i1 == -2) {
                        failDrill.add(td.getBoreholeCode() + ":钻孔信息保存失败");
                    } else if (i1 == -3) {
                        failDrill.add(td.getBoreholeCode() + ":钻孔轨迹为空");
                    } else if (i1 == -4) {
                        failDrill.add(td.getBoreholeCode() + ":部分轨迹保存失败");
                    } else {
                        failDrill.add(td.getBoreholeCode() + ":保存失败");
                    }
                }
            }
            if (!failDrill.isEmpty()) {
                return AjaxResult.success("定向钻数据导入成功：" + succ + "条，失败：" + fail + "条，" + "失败孔号:" + Arrays.toString(failDrill.toArray()));
            } else {
                return AjaxResult.success("定向钻数据导入成功：" + succ + "条");
            }
        }
        return AjaxResult.error("数据为空");
    }


    /**
     * 修改
     *
     * @param tActDirectionalBorehole 要修改的实体信息
     * @return 结果
     */
    @ApiOperation("修改实钻定向钻数据")
    @Transactional
    @PutMapping("updateTActDirectionalBorehole")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalBorehole:updateTActDirectionalBorehole')")
    public AjaxResult updateTActDirectionalBorehole(@RequestBody TActDirectionalBorehole tActDirectionalBorehole) {
        List<TActDirectionalDrillingPoint> list = tActDirectionalBorehole.gettDirectionalDrillingPointList();
        this.itActDirectionalDrillingPointService.delByActDirectionalBoreholeId(tActDirectionalBorehole.getId());
        if (list.size() > 0) {
            for (TActDirectionalDrillingPoint tdp : list) {
                tdp.setBoreholeId(tActDirectionalBorehole.getId());
                itActDirectionalDrillingPointService.insertTActDirectionalDrillingPoint(tdp);
            }
        }
        return AjaxResult.success(this.tActDirectionalBoreholeService.updateTActDirectionalBorehole(tActDirectionalBorehole));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过id查询实钻定向钻")
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalBorehole:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        List<TActDirectionalDrillingPoint> list = this.itActDirectionalDrillingPointService.getByActDirectionalBoreholeId(id);
        TActDirectionalBorehole tdb = this.tActDirectionalBoreholeService.selectTActDirectionalBoreholeById(id);
        tdb.settDirectionalDrillingPointList(list);
        return AjaxResult.success(tdb);
    }


    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @ApiOperation("通过id删除实钻定向钻")
    @DeleteMapping("deleteById")
    // @PreAuthorize("@ss.hasPermi('tActDirectionalBorehole:id')")
    public AjaxResult deleteTActDirectionalBoreholeById(@RequestParam int id) {
        this.itActDirectionalDrillingPointService.delByActDirectionalBoreholeId(id);
        int result = this.tActDirectionalBoreholeService.deleteTActDirectionalBoreholeById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @ApiOperation("通过id批量删除实钻定向钻")
    @DeleteMapping("deleteByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tActDirectionalBoreholeService.deleteTActDirectionalBoreholeByIds(ids));
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TActDirectionalBorehole tActDirectionalBorehole) {
        List<TActDirectionalBorehole> list = this.tActDirectionalBoreholeService.selectTActDirectionalBoreholeList(tActDirectionalBorehole);
        ExcelUtil<TActDirectionalBorehole> util = new ExcelUtil<TActDirectionalBorehole>(TActDirectionalBorehole.class);
        util.exportExcel(response, list, "实际定向钻孔数据");
    }


}
