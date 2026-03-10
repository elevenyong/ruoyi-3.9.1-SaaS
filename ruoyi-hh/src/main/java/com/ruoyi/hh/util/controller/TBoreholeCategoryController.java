package com.ruoyi.hh.util.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.util.domain.TBoreholeCategory;
import com.ruoyi.hh.util.service.ITBoreholeCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (TBoreholeCategory)表控制层
 *
 * @author Eleven
 * @since 2022-07-13 14:44:57
 */
@RestController
@RequestMapping("tBoreholeCategory")
@Api(value = "DZZK钻孔类型信息接口", tags = "DZZK钻孔类型信息接口")
public class TBoreholeCategoryController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITBoreholeCategoryService tBoreholeCategoryService;

    /**
     * 获取列表
     *
     * @param tBoreholeCategory 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    @ApiOperation("获取钻孔类别列表接口")
    //  @PreAuthorize("@ss.hasPermi('tBoreholeCategory:list')")
    public TableDataInfo selectAll(@RequestBody TBoreholeCategory tBoreholeCategory) {
        startPage();
        List<TBoreholeCategory> list = this.tBoreholeCategoryService.selectTBoreholeCategoryList(tBoreholeCategory);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tBoreholeCategory 单挑数据
     */
    @PostMapping("insert")
    @ApiOperation(value = "插入钻孔类别信息", hidden = true)
    //  @PreAuthorize("@ss.hasPermi('tBoreholeCategory:insert')")
    public void insertTBoreholeCategory(@RequestBody TBoreholeCategory tBoreholeCategory) {
        this.tBoreholeCategoryService.insertTBoreholeCategory(tBoreholeCategory);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    @ApiOperation(value = "批量插入钻孔类别列表接口", hidden = true)
//      @PreAuthorize("@ss.hasPermi('tBoreholeCategory:insert')")
    public void insert(@RequestBody List<TBoreholeCategory> list) {
        this.tBoreholeCategoryService.insertTBoreholeCategoryList(list);
    }


    /**
     * 修改
     *
     * @param tBoreholeCategory 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTBoreholeCategory")
    //  @PreAuthorize("@ss.hasPermi('tBoreholeCategory:updateTBoreholeCategory')")
    public AjaxResult updateTRoadway(@RequestBody TBoreholeCategory tBoreholeCategory) {
        return AjaxResult.success(this.tBoreholeCategoryService.updateTBoreholeCategory(tBoreholeCategory));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //  @PreAuthorize("@ss.hasPermi('tBoreholeCategory:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tBoreholeCategoryService.selectTBoreholeCategoryById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTBoreholeCategoryById")
    //  @PreAuthorize("@ss.hasPermi('tBoreholeCategory:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tBoreholeCategoryService.deleteTBoreholeCategoryById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTBoreholeCategoryByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tBoreholeCategoryService.deleteTBoreholeCategoryByIds(ids));
    }

    /**
     * 分页查询列表
     *
     * @return 列表
     */
    @PostMapping("getPageList")

    public TableDataInfo getPageList() {
        startPage();
        List<TBoreholeCategory> list = this.tBoreholeCategoryService.getPageList();
        return getDataTable(list);
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TBoreholeCategory tBoreholeCategory) {
        List<TBoreholeCategory> list = this.tBoreholeCategoryService.selectTBoreholeCategoryList(tBoreholeCategory);
        ExcelUtil<TBoreholeCategory> util = new ExcelUtil<TBoreholeCategory>(TBoreholeCategory.class);
        util.exportExcel(response, list, "钻孔类别数据");
    }


}

