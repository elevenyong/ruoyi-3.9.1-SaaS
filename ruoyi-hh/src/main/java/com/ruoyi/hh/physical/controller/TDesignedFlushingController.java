package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TDesignedFlushing;
import com.ruoyi.hh.physical.service.ITDesignedFlushingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TDesignedFlushing)表控制层
 *
 * @author Eleven
 * @since 2022-08-09 14:55:12
 */
@RestController
@RequestMapping("tDesignedFlushing")
public class TDesignedFlushingController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDesignedFlushingService tDesignedFlushingService;

    /**
     * 获取列表
     *
     * @param tDesignedFlushing 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tDesignedFlushing:list')")
    public TableDataInfo selectAll(@RequestBody TDesignedFlushing tDesignedFlushing) {
        startPage();
        List<TDesignedFlushing> list = this.tDesignedFlushingService.selectTDesignedFlushingList(tDesignedFlushing);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tDesignedFlushing 单条数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tDesignedFlushing:insert')")
    public void insertTDesignedFlushing(@RequestBody TDesignedFlushing tDesignedFlushing) {
        this.tDesignedFlushingService.insertTDesignedFlushing(tDesignedFlushing);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDesignedFlushing:insert')")
    public void insert(@RequestBody List<TDesignedFlushing> list) {
        if (list.size() > 0) {
            for (TDesignedFlushing tDesignedFlushing : list) {
                this.tDesignedFlushingService.insertTDesignedFlushing(tDesignedFlushing);
            }
        }
        //this.tDesignedFlushingService.insertTDesignedFlushingList(list);
    }


    /**
     * 修改
     *
     * @param tDesignedFlushing 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDesignedFlushing")
    //@PreAuthorize("@ss.hasPermi('tDesignedFlushing:updateTDesignedFlushing')")
    public AjaxResult updateTRoadway(@RequestBody TDesignedFlushing tDesignedFlushing) {
        return AjaxResult.success(this.tDesignedFlushingService.updateTDesignedFlushing(tDesignedFlushing));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tDesignedFlushing:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDesignedFlushingService.selectTDesignedFlushingById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDesignedFlushingById")
    //@PreAuthorize("@ss.hasPermi('tDesignedFlushing:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tDesignedFlushingService.deleteTDesignedFlushingById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDesignedFlushingByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tDesignedFlushingService.deleteTDesignedFlushingByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TDesignedFlushing> list = new ArrayList<>();
        list=this.tDesignedFlushingService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDesignedFlushing tDesignedFlushing) {
        List<TDesignedFlushing> list=this.tDesignedFlushingService.selectTDesignedFlushingList(tDesignedFlushing);
        ExcelUtil<TDesignedFlushing> util = new ExcelUtil<TDesignedFlushing>(TDesignedFlushing.class);
        util.exportExcel(response, list, "设计冲孔数据");
    }


}

