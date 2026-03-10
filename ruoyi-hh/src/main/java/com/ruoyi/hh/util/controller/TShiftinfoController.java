package com.ruoyi.hh.util.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.hh.util.domain.TShiftinfo;
import com.ruoyi.hh.util.service.ITShiftinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (TShiftinfo)表控制层
 *
 * @author Eleven
 * @since 2022-07-18 17:41:59
 */
@RestController
@RequestMapping("tShiftinfo")
public class TShiftinfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITShiftinfoService tShiftinfoService;

    /**
     * 获取列表
     *
     * @param tShiftinfo 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tShiftinfo:list')")
    public TableDataInfo selectAll(@RequestBody TShiftinfo tShiftinfo) {
        startPage();
        List<TShiftinfo> list =this.tShiftinfoService.selectTShiftinfoList(tShiftinfo);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tShiftinfo 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tShiftinfo:insert')")
    public void insertTShiftinfo(@RequestBody TShiftinfo tShiftinfo) {
        this.tShiftinfoService.insertTShiftinfo(tShiftinfo);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tShiftinfo:insert')")
    public void insert(@RequestBody List<TShiftinfo> list) {
        if (list.size() > 0) {
            for (TShiftinfo tShiftinfo : list) {
                this.tShiftinfoService.insertTShiftinfo(tShiftinfo);
            }
        }
        //this.tShiftinfoService.insertTShiftinfoList(list);
    }


    /**
     * 修改
     *
     * @param tShiftinfo 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTShiftinfo")
    //@PreAuthorize("@ss.hasPermi('tShiftinfo:updateTShiftinfo')")
    public AjaxResult updateTRoadway(@RequestBody TShiftinfo tShiftinfo) {
        return AjaxResult.success(this.tShiftinfoService.updateTShiftinfo(tShiftinfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tShiftinfo:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tShiftinfoService.selectTShiftinfoById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTShiftinfoById")
    //@PreAuthorize("@ss.hasPermi('tShiftinfo:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tShiftinfoService.deleteTShiftinfoById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTShiftinfoByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tShiftinfoService.deleteTShiftinfoByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TShiftinfo> list = new ArrayList<>();
                list=this.tShiftinfoService.getPageList();
        return getDataTable(list);
    }


}

