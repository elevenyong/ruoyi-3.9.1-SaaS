package com.ruoyi.hh.simulate.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.simulate.domain.StFaultinfo;
import com.ruoyi.hh.simulate.service.IStFaultinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 达标评判断层模拟(StFaultinfo)表控制层
 *
 * @author Eleven
 * @since 2023-01-14 14:55:05
 */
@RestController
@RequestMapping("stFaultinfo")
public class StFaultinfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private IStFaultinfoService stFaultinfoService;

    /**
     * 获取列表
     *
     * @param stFaultinfo 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('stFaultinfo:list')")
    public TableDataInfo selectAll(@RequestBody StFaultinfo stFaultinfo) {
        startPage();
        List <StFaultinfo> list=this.stFaultinfoService.selectStFaultinfoList(stFaultinfo);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param stFaultinfo 单条数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('stFaultinfo:insert')")
    public void insertStFaultinfo(@RequestBody StFaultinfo stFaultinfo) {
        this.stFaultinfoService.insertStFaultinfo(stFaultinfo);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('stFaultinfo:insert')")
    public void insert(@RequestBody List<StFaultinfo> list) {
        if (list.size() > 0) {
            for (StFaultinfo stFaultinfo : list) {
                this.stFaultinfoService.insertStFaultinfo(stFaultinfo);
            }
        }
        //this.stFaultinfoService.insertStFaultinfoList(list);
    }


    /**
     * 修改
     *
     * @param stFaultinfo 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateStFaultinfo")
    //@PreAuthorize("@ss.hasPermi('stFaultinfo:updateStFaultinfo')")
    public AjaxResult updateTRoadway(@RequestBody StFaultinfo stFaultinfo) {
        return AjaxResult.success(this.stFaultinfoService.updateStFaultinfo(stFaultinfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('stFaultinfo:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.stFaultinfoService.selectStFaultinfoById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteStFaultinfoById")
    //@PreAuthorize("@ss.hasPermi('stFaultinfo:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.stFaultinfoService.deleteStFaultinfoById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteStFaultinfoByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.stFaultinfoService.deleteStFaultinfoByIds(ids));
    }


    /**
     * 导出excel
     *
     * @param response res
     * @param list     列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<StFaultinfo> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<StFaultinfo> util = new ExcelUtil<StFaultinfo>(StFaultinfo.class);
        util.exportExcel(response, list, "达标评判断层信息");
    }

    @PostMapping
    public List<StFaultinfo> getByWorkFaceId(Integer workfaceid){
        return this.stFaultinfoService.getByworkfaceid(workfaceid);
    }


}

