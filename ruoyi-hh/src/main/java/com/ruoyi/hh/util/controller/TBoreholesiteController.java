package com.ruoyi.hh.util.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.util.domain.TBoreholesite;
import com.ruoyi.hh.util.service.ITBoreholesiteService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (TBoreholesite)表控制层
 *
 * @author Eleven
 * @since 2022-07-13 14:44:56
 */
@RestController
@RequestMapping("tBoreholesite")
public class TBoreholesiteController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITBoreholesiteService tBoreholesiteService;

    /**
     * 获取列表
     *
     * @param tBoreholesite 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //  @PreAuthorize("@ss.hasPermi('tBoreholesite:list')")
    public TableDataInfo selectAll(@RequestBody TBoreholesite tBoreholesite) {
        startPage();
        List<TBoreholesite> list =this.tBoreholesiteService.selectTBoreholesiteList(tBoreholesite);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tBoreholesite 单挑数据
     */
    @PostMapping("insert")
    //  @PreAuthorize("@ss.hasPermi('tBoreholesite:insert')")
    public void insertTBoreholesite(@RequestBody TBoreholesite tBoreholesite) {
        this.tBoreholesiteService.insertTBoreholesite(tBoreholesite);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    //  @PreAuthorize("@ss.hasPermi('tBoreholesite:insert')")
    public void insert(@RequestBody List<TBoreholesite> list) {
        this.tBoreholesiteService.insertTBoreholesiteList(list);
    }


    /**
     * 修改
     *
     * @param tBoreholesite 要修改的实体信息
     * @return 结果
     */
    @PostMapping("updateTBoreholesite")
    //  @PreAuthorize("@ss.hasPermi('tBoreholesite:updateTBoreholesite')")
    public AjaxResult updateTRoadway(@RequestBody TBoreholesite tBoreholesite) {
        return AjaxResult.success(this.tBoreholesiteService.updateTBoreholesite(tBoreholesite));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
//    //  @PreAuthorize("@ss.hasPermi('tBoreholesite:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tBoreholesiteService.selectTBoreholesiteById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @PostMapping("deleteTBoreholesiteById")
    //  @PreAuthorize("@ss.hasPermi('tBoreholesite:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tBoreholesiteService.deleteTBoreholesiteById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @PostMapping("deleteTBoreholesiteByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tBoreholesiteService.deleteTBoreholesiteByIds(ids));
    }

    /**
     * 导出excel
     *
     * @param response res
     * @param list     列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<TBoreholesite> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<TBoreholesite> util = new ExcelUtil<TBoreholesite>(TBoreholesite.class);
        util.exportExcel(response, list, "钻场数据");
    }


}

