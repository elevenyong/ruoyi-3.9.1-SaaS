package com.ruoyi.hh.util.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.util.domain.TFlushingException;
import com.ruoyi.hh.util.service.ITFlushingExceptionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TFlushingException)表控制层
 *
 * @author Eleven
 * @since 2022-07-18 11:53:31
 */
@RestController
@RequestMapping("tFlushingException")
public class TFlushingExceptionController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITFlushingExceptionService tFlushingExceptionService;

    /**
     * 获取列表
     *
     * @param tFlushingException 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    @PreAuthorize("@ss.hasPermi('tFlushingException:list')")
    public TableDataInfo selectAll(@RequestBody TFlushingException tFlushingException) {
        startPage();
        List<TFlushingException> list = this.tFlushingExceptionService.selectTFlushingExceptionList(tFlushingException);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tFlushingException 单挑数据
     */
    @PostMapping("insert")
    @PreAuthorize("@ss.hasPermi('tFlushingException:insert')")
    public void insertTFlushingException(@RequestBody TFlushingException tFlushingException) {
        this.tFlushingExceptionService.insertTFlushingException(tFlushingException);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    @PreAuthorize("@ss.hasPermi('tFlushingException:insert')")
    public void insert(@RequestBody List<TFlushingException> list) {
        if (list.size() > 0) {
            for (TFlushingException tFlushingException : list) {
                this.tFlushingExceptionService.insertTFlushingException(tFlushingException);
            }
        }

    }


    /**
     * 修改
     *
     * @param tFlushingException 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTFlushingException")
    @PreAuthorize("@ss.hasPermi('tFlushingException:updateTFlushingException')")
    public AjaxResult updateTRoadway(@RequestBody TFlushingException tFlushingException) {
        return AjaxResult.success(this.tFlushingExceptionService.updateTFlushingException(tFlushingException));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    @PreAuthorize("@ss.hasPermi('tFlushingException:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tFlushingExceptionService.selectTFlushingExceptionById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTFlushingExceptionById")
    @PreAuthorize("@ss.hasPermi('tFlushingException:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tFlushingExceptionService.deleteTFlushingExceptionById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTFlushingExceptionByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tFlushingExceptionService.deleteTFlushingExceptionByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TFlushingException> list = new ArrayList<>();
        list=this.tFlushingExceptionService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TFlushingException tFlushingException) {
        List<TFlushingException> list=this.tFlushingExceptionService.selectTFlushingExceptionList(tFlushingException);
        ExcelUtil<TFlushingException> util = new ExcelUtil<TFlushingException>(TFlushingException.class);
        util.exportExcel(response, list, "冲孔异常数据");
    }


}

