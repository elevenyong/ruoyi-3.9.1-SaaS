package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TDesDirectionalDrillingPoint;
import com.ruoyi.hh.physical.domain.TDesignedDirectionalBorehole;
import com.ruoyi.hh.physical.service.ITDesDirectionalDrillingPointService;
import com.ruoyi.hh.physical.service.ITDesignedDirectionalBoreholeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设计定向钻(TDesignedDirectionalBorehole)表控制层
 *
 * @author makejava
 * @since 2023-07-27 15:47:00
 */
@Api(value = "DZZK设计定向钻接口", tags = "DZZK设计定向钻接口")
@RestController
@RequestMapping("tDesignedDirectionalBorehole")
public class TDesignedDirectionalBoreholeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDesignedDirectionalBoreholeService tDesignedDirectionalBoreholeService;
    @Resource
    private ITDesDirectionalDrillingPointService itDesDirectionalDrillingPointService;

    /**
     * 获取列表
     *
     * @param tDesignedDirectionalBorehole 要查询的信息
     * @return 返回结果
     */
    @ApiOperation("按条件查询设计定向钻孔列表")
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tDesignedDirectionalBorehole:list')")
    public TableDataInfo selectAll(@RequestBody TDesignedDirectionalBorehole tDesignedDirectionalBorehole) {
        startPage();
        List<TDesignedDirectionalBorehole> list = this.tDesignedDirectionalBoreholeService.selectTDesignedDirectionalBoreholeList(tDesignedDirectionalBorehole);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tDesignedDirectionalBorehole 单条数据
     */
    @ApiOperation("新增单条设计定向钻孔")
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tDesignedDirectionalBorehole:insert')")
    public AjaxResult insertTDesignedDirectionalBorehole(@RequestBody TDesignedDirectionalBorehole tDesignedDirectionalBorehole) {
        tDesignedDirectionalBorehole.setSign(2);
        int result = this.tDesignedDirectionalBoreholeService.insertTDesignedDirectionalBorehole(tDesignedDirectionalBorehole);
        if (result < 0) {
            return AjaxResult.error("添加失败" + result + "条");
        }
        return AjaxResult.success("添加成功" + result + "条");
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @ApiOperation("批量插入设计定向钻孔数据")
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDesignedDirectionalBorehole:insert')")
    public void insert(@RequestBody List<TDesignedDirectionalBorehole> list) {
        if (list.size() > 0) {
            for (TDesignedDirectionalBorehole td : list) {
                td.setSign(2);
                this.tDesignedDirectionalBoreholeService.insertTDesignedDirectionalBorehole(td);
            }
        }
    }


    /**
     * 修改
     *
     * @param tDesignedDirectionalBorehole 要修改的实体信息
     * @return 结果
     */
    @ApiOperation("修改设计定向钻孔数据")
    @PutMapping("updateTDesignedDirectionalBorehole")
    // @PreAuthorize("@ss.hasPermi('tDesignedDirectionalBorehole:updateTDesignedDirectionalBorehole')")
    public AjaxResult updateTDesignedDirectionalBorehole(@RequestBody TDesignedDirectionalBorehole tDesignedDirectionalBorehole) {
        return AjaxResult.success(this.tDesignedDirectionalBoreholeService.updateTDesignedDirectionalBorehole(tDesignedDirectionalBorehole));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过ID查询设计定向钻孔数据")
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tDesignedDirectionalBorehole:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        List<TDesDirectionalDrillingPoint> list = this.itDesDirectionalDrillingPointService.getByDesDirectionalBoreholeId(id);
        return AjaxResult.success(list);
    }


    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @ApiOperation("根据ID删除单条设计定向钻孔数据")
    @DeleteMapping("deleteTDesignedDirectionalBoreholeById")
    // @PreAuthorize("@ss.hasPermi('tDesignedDirectionalBorehole:id')")
    public AjaxResult deleteTDesignedDirectionalBoreholeById(@RequestParam int id) {
        int result = this.tDesignedDirectionalBoreholeService.deleteTDesignedDirectionalBoreholeById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @ApiOperation("根据ID批量删除设计定向钻孔数据")
    @DeleteMapping("deleteTDesignedDirectionalBoreholeByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tDesignedDirectionalBoreholeService.deleteTDesignedDirectionalBoreholeByIds(ids));
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDesignedDirectionalBorehole tDesignedDirectionalBorehole) {
        List<TDesignedDirectionalBorehole> list = this.tDesignedDirectionalBoreholeService.selectTDesignedDirectionalBoreholeList(tDesignedDirectionalBorehole);
        ExcelUtil<TDesignedDirectionalBorehole> util = new ExcelUtil<TDesignedDirectionalBorehole>(TDesignedDirectionalBorehole.class);
        util.exportExcel(response, list, "设计定向钻孔数据");
    }

}
