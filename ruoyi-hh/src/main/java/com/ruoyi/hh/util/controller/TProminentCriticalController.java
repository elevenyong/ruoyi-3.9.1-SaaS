package com.ruoyi.hh.util.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.util.domain.TProminentCritical;
import com.ruoyi.hh.util.service.ITProminentCriticalService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TProminentCritical)表控制层
 *
 * @author Eleven
 * @since 2022-07-18 11:53:31
 */
@RestController
@RequestMapping("tProminentCritical")
public class TProminentCriticalController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITProminentCriticalService tProminentCriticalService;

    /**
     * 获取列表
     *
     * @param tProminentCritical 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    @PreAuthorize("@ss.hasPermi('tProminentCritical:list')")
    public TableDataInfo selectAll(@RequestBody TProminentCritical tProminentCritical) {
        startPage();
        List<TProminentCritical> list =this.tProminentCriticalService.selectTProminentCriticalList(tProminentCritical);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tProminentCritical 单挑数据
     */
    @PostMapping("insert")
    @PreAuthorize("@ss.hasPermi('tProminentCritical:insert')")
    public void insertTProminentCritical(@RequestBody TProminentCritical tProminentCritical) {
        this.tProminentCriticalService.insertTProminentCritical(tProminentCritical);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    @PreAuthorize("@ss.hasPermi('tProminentCritical:insert')")
    public void insert(@RequestBody List<TProminentCritical> list) {
        if (list.size() > 0) {
            for (TProminentCritical tProminentCritical : list) {
                this.tProminentCriticalService.insertTProminentCritical(tProminentCritical);
            }
        }
//        this.tProminentCriticalService.insertTProminentCriticalList(list);
    }


    /**
     * 修改
     *
     * @param tProminentCritical 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTProminentCritical")
    @PreAuthorize("@ss.hasPermi('tProminentCritical:updateTProminentCritical')")
    public AjaxResult updateTRoadway(@RequestBody TProminentCritical tProminentCritical) {
        return AjaxResult.success(this.tProminentCriticalService.updateTProminentCritical(tProminentCritical));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    @PreAuthorize("@ss.hasPermi('tProminentCritical:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tProminentCriticalService.selectTProminentCriticalById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTProminentCriticalById")
    @PreAuthorize("@ss.hasPermi('tProminentCritical:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tProminentCriticalService.deleteTProminentCriticalById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTProminentCriticalByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tProminentCriticalService.deleteTProminentCriticalByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TProminentCritical> list = new ArrayList<>();
        list=this.tProminentCriticalService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TProminentCritical tProminentCritical) {
        List<TProminentCritical> list=this.tProminentCriticalService.selectTProminentCriticalList(tProminentCritical);
        ExcelUtil<TProminentCritical> util = new ExcelUtil<TProminentCritical>(TProminentCritical.class);
        util.exportExcel(response, list, "突出临界值数据");
    }


}

