package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TDrillinginfo;
import com.ruoyi.hh.physical.service.ITDrillinginfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (TDrillinginfo)表控制层
 *
 * @author Eleven
 * @since 2022-07-25 17:11:23
 */
@RestController
@RequestMapping("tDrillinginfo")
public class TDrillinginfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDrillinginfoService tDrillinginfoService;

    /**
     * 获取列表
     *
     * @param tDrillinginfo 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tDrillinginfo:list')")
    public TableDataInfo selectAll(@RequestBody TDrillinginfo tDrillinginfo) {
        startPage();
        List<TDrillinginfo> list =this.tDrillinginfoService.selectTDrillinginfoList(tDrillinginfo);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tDrillinginfo 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tDrillinginfo:insert')")
    public void insertTDrillinginfo(@RequestBody TDrillinginfo tDrillinginfo) {
        this.tDrillinginfoService.insertTDrillinginfo(tDrillinginfo);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDrillinginfo:insert')")
    public void insert(@RequestBody List<TDrillinginfo> list) {
        if (list.size() > 0) {
            for (TDrillinginfo tDrillinginfo : list) {
                this.tDrillinginfoService.insertTDrillinginfo(tDrillinginfo);
            }
        }
        //this.tDrillinginfoService.insertTDrillinginfoList(list);
    }


    /**
     * 修改
     *
     * @param tDrillinginfo 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDrillinginfo")
    //@PreAuthorize("@ss.hasPermi('tDrillinginfo:updateTDrillinginfo')")
    public AjaxResult updateTRoadway(@RequestBody TDrillinginfo tDrillinginfo) {
        return AjaxResult.success(this.tDrillinginfoService.updateTDrillinginfo(tDrillinginfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tDrillinginfo:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDrillinginfoService.selectTDrillinginfoById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDrillinginfoById")
    //@PreAuthorize("@ss.hasPermi('tDrillinginfo:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tDrillinginfoService.deleteTDrillinginfoById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDrillinginfoByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tDrillinginfoService.deleteTDrillinginfoByIds(ids));
    }

    /**
     * 导出excel
     *
     * @param response res
     * @param list     列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<TDrillinginfo> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<TDrillinginfo> util = new ExcelUtil<TDrillinginfo>(TDrillinginfo.class);
        util.exportExcel(response, list, "钻机信息");
    }


}

