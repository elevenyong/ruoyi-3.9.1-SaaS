package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TDrainageUnit;
import com.ruoyi.hh.physical.service.ITDrainageUnitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (TDrainageUnit)表控制层
 *
 * @author Eleven
 * @since 2022-07-16 18:45:20
 */
@RestController
@RequestMapping("tDrainageUnit")
public class TDrainageUnitController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDrainageUnitService tDrainageUnitService;

    /**
     * 获取列表
     *
     * @param tDrainageUnit 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tDrainageUnit:list')")
    public TableDataInfo selectAll(@RequestBody TDrainageUnit tDrainageUnit) {
        startPage();
        List<TDrainageUnit> list =this.tDrainageUnitService.selectTDrainageUnitList(tDrainageUnit);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tDrainageUnit 单挑数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tDrainageUnit:insert')")
    public void insertTDrainageUnit(@RequestBody TDrainageUnit tDrainageUnit) {
        this.tDrainageUnitService.insertTDrainageUnit(tDrainageUnit);
    }

	  /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDrainageUnit:insert')")
    public void insert(@RequestBody List<TDrainageUnit> list){
        if(list.size()>0){
            for(TDrainageUnit td:list){
                this.tDrainageUnitService.insertTDrainageUnit(td);
            }
        }
    }


    /**
     * 修改
     *
     * @param tDrainageUnit 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDrainageUnit")
    // @PreAuthorize("@ss.hasPermi('tDrainageUnit:updateTDrainageUnit')")
    public AjaxResult updateTRoadway(@RequestBody TDrainageUnit tDrainageUnit) {
        return AjaxResult.success(this.tDrainageUnitService.updateTDrainageUnit(tDrainageUnit));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tDrainageUnit:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDrainageUnitService.selectTDrainageUnitById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDrainageUnitById")
    // @PreAuthorize("@ss.hasPermi('tDrainageUnit:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tDrainageUnitService.deleteTDrainageUnitById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDrainageUnitByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tDrainageUnitService.deleteTDrainageUnitByIds(ids));
    }

//    /**
//     * 分页查询
//     *
//     * @return 列表
//     */
//    @PostMapping("getPageList")
//    public TableDataInfo getPageList() {
//        startPage();
//        List<TDrainageUnit> list = new ArrayList<>();
//        list=this.tDrainageUnitService.getPageList();
//        return getDataTable(list);
//    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDrainageUnit tDrainageUnit) {
        List<TDrainageUnit> list=this.tDrainageUnitService.selectTDrainageUnitList(tDrainageUnit);
        ExcelUtil<TDrainageUnit> util = new ExcelUtil<TDrainageUnit>(TDrainageUnit.class);
        util.exportExcel(response, list, "抽采单元数据");
    }



}

