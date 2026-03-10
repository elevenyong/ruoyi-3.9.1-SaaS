package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TCoalinfo;
import com.ruoyi.hh.physical.service.ITCoalinfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TCoalinfo)表控制层
 *
 * @author Eleven
 * @since 2022-07-18 11:53:31
 */
@RestController
@RequestMapping("tCoalinfo")
public class TCoalinfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITCoalinfoService tCoalinfoService;

    /**
     * 获取列表
     *
     * @param tCoalinfo 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    @PreAuthorize("@ss.hasPermi('tCoalinfo:list')")
    @Log(title = "煤层信息", businessType = BusinessType.QUERY)
    public TableDataInfo selectAll(@RequestBody TCoalinfo tCoalinfo) {
        startPage();
        List<TCoalinfo> list = this.tCoalinfoService.selectTCoalinfoList(tCoalinfo);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tCoalinfo 单挑数据
     */
    @PostMapping("insert")
    @PreAuthorize("@ss.hasPermi('tCoalinfo:insert')")
    @Log(title = "煤层信息", businessType = BusinessType.INSERT)
    public void insertTCoalinfo(@RequestBody TCoalinfo tCoalinfo) {
        this.tCoalinfoService.insertTCoalinfo(tCoalinfo);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    @PreAuthorize("@ss.hasPermi('tCoalinfo:insert')")
    @Log(title = "煤层信息", businessType = BusinessType.INSERT)
    public void insert(@RequestBody List<TCoalinfo> list) {
        if (list.size() > 0) {
            for (TCoalinfo tCoalinfo : list) {
                this.tCoalinfoService.insertTCoalinfo(tCoalinfo);
            }
        }
//        this.tCoalinfoService.insertTCoalinfoList(list);
    }


    /**
     * 修改
     *
     * @param tCoalinfo 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTCoalinfo")
    @Log(title = "煤层信息", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('tCoalinfo:updateTCoalinfo')")
    public AjaxResult updateTRoadway(@RequestBody TCoalinfo tCoalinfo) {
        return AjaxResult.success(this.tCoalinfoService.updateTCoalinfo(tCoalinfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    @PreAuthorize("@ss.hasPermi('tCoalinfo:id')")
    @Log(title = "煤层信息", businessType = BusinessType.QUERY)
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tCoalinfoService.selectTCoalinfoById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTCoalinfoById")
    @PreAuthorize("@ss.hasPermi('tCoalinfo:id')")
    @Log(title = "煤层信息", businessType = BusinessType.DELETE)
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tCoalinfoService.deleteTCoalinfoById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTCoalinfoByIds")
    @Log(title = "煤层信息", businessType = BusinessType.DELETE)
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tCoalinfoService.deleteTCoalinfoByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    @Log(title = "煤层信息", businessType = BusinessType.QUERY)
    public TableDataInfo getPageList() {
        startPage();
        List<TCoalinfo> list = new ArrayList<>();
        list = this.tCoalinfoService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    @Log(title = "煤层信息", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, @RequestBody TCoalinfo tCoalinfo) {
        List<TCoalinfo> list = this.tCoalinfoService.selectTCoalinfoList(tCoalinfo);
        ExcelUtil<TCoalinfo> util = new ExcelUtil<TCoalinfo>(TCoalinfo.class);
        util.exportExcel(response, list, "煤层信息数据","施工地点：     钻孔类型：");
    }


}

