package com.ruoyi.hh.util.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.util.domain.TPurposeBorehole;
import com.ruoyi.hh.util.service.ITPurposeBoreholeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TPurposeBorehole)表控制层
 *
 * @author Eleven
 * @since 2022-07-13 14:44:57
 */
@RestController
@RequestMapping("tPurposeBorehole")
public class TPurposeBoreholeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITPurposeBoreholeService tPurposeBoreholeService;

    /**
     * 获取列表
     *
     * @param tPurposeBorehole 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //  @PreAuthorize("@ss.hasPermi('tPurposeBorehole:list')")
    public TableDataInfo selectAll(@RequestBody TPurposeBorehole tPurposeBorehole) {
        startPage();
        List<TPurposeBorehole> list=new ArrayList();
        list=this.tPurposeBoreholeService.selectTPurposeBoreholeList(tPurposeBorehole);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tPurposeBorehole 单挑数据
     */
    @PostMapping("insert")
    //  @PreAuthorize("@ss.hasPermi('tPurposeBorehole:insert')")
    public void insertTPurposeBorehole(@RequestBody TPurposeBorehole tPurposeBorehole) {
        this.tPurposeBoreholeService.insertTPurposeBorehole(tPurposeBorehole);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    //  @PreAuthorize("@ss.hasPermi('tPurposeBorehole:insert')")
    public void insert(@RequestBody List<TPurposeBorehole> list) {
        this.tPurposeBoreholeService.insertTPurposeBoreholeList(list);
    }


    /**
     * 修改
     *
     * @param tPurposeBorehole 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTPurposeBorehole")
    //  @PreAuthorize("@ss.hasPermi('tPurposeBorehole:updateTPurposeBorehole')")
    public AjaxResult updateTRoadway(@RequestBody TPurposeBorehole tPurposeBorehole) {
        return AjaxResult.success(this.tPurposeBoreholeService.updateTPurposeBorehole(tPurposeBorehole));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //  @PreAuthorize("@ss.hasPermi('tPurposeBorehole:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tPurposeBoreholeService.selectTPurposeBoreholeById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTPurposeBoreholeById")
    //  @PreAuthorize("@ss.hasPermi('tPurposeBorehole:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tPurposeBoreholeService.deleteTPurposeBoreholeById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTPurposeBoreholeByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tPurposeBoreholeService.deleteTPurposeBoreholeByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TPurposeBorehole> list = new ArrayList<>();
        list=this.tPurposeBoreholeService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TPurposeBorehole tPurposeBorehole) {
        List<TPurposeBorehole> list=this.tPurposeBoreholeService.selectTPurposeBoreholeList(tPurposeBorehole);
        ExcelUtil<TPurposeBorehole> util = new ExcelUtil<TPurposeBorehole>(TPurposeBorehole.class);
        util.exportExcel(response, list, "钻孔用图表数据");
    }


}

