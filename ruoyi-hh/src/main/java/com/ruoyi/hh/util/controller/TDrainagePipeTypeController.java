package com.ruoyi.hh.util.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.util.domain.TDrainagePipeType;
import com.ruoyi.hh.util.service.ITDrainagePipeTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 抽采管路类型(TDrainagePipeType)表控制层
 *
 * @author makejava
 * @since 2023-08-11 17:53:52
 */
@RestController
@RequestMapping("tDrainagePipeType")
public class TDrainagePipeTypeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDrainagePipeTypeService tDrainagePipeTypeService;

    /**
     * 获取列表
     *
     * @param tDrainagePipeType  要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tDrainagePipeType:list')")
    public TableDataInfo selectAll(@RequestBody TDrainagePipeType tDrainagePipeType) {
        startPage();
        List<TDrainagePipeType> list =this.tDrainagePipeTypeService.selectTDrainagePipeTypeList(tDrainagePipeType);
        return getDataTable(list);
    }
    
    /**
     * 插入单条数据
     *
     * @param tDrainagePipeType 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tDrainagePipeType:insert')")
    public void insertTDrainagePipeType (@RequestBody TDrainagePipeType tDrainagePipeType) {
        this.tDrainagePipeTypeService.insertTDrainagePipeType(tDrainagePipeType);
    }
    
     /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDrainagePipeType:insert')")
    public void insert(@RequestBody List<TDrainagePipeType> list){
        if(list.size()>0){
            for(TDrainagePipeType td:list){
                this.tDrainagePipeTypeService.insertTDrainagePipeType(td);
            }
        }
    }
    

     /**
     * 修改
     *
     * @param tDrainagePipeType 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDrainagePipeType")
    // @PreAuthorize("@ss.hasPermi('tDrainagePipeType:updateTDrainagePipeType')")
    public AjaxResult updateTDrainagePipeType(@RequestBody TDrainagePipeType tDrainagePipeType) {
        return AjaxResult.success(this.tDrainagePipeTypeService.updateTDrainagePipeType(tDrainagePipeType));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tDrainagePipeType:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDrainagePipeTypeService.selectTDrainagePipeTypeById(id));
    }
    
    
    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDrainagePipeTypeById")
    // @PreAuthorize("@ss.hasPermi('tDrainagePipeType:id')")
    public AjaxResult deleteTDrainagePipeTypeById(@RequestParam int id) {
        int result = this.tDrainagePipeTypeService.deleteTDrainagePipeTypeById(id);
        return AjaxResult.success(result);
    }
    
    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDrainagePipeTypeByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tDrainagePipeTypeService.deleteTDrainagePipeTypeByIds(ids));
    }
    
    

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDrainagePipeType tDrainagePipeType) {
        List<TDrainagePipeType> list=this.tDrainagePipeTypeService.selectTDrainagePipeTypeList(tDrainagePipeType);
        ExcelUtil<TDrainagePipeType> util = new ExcelUtil<TDrainagePipeType>(TDrainagePipeType.class);
        util.exportExcel(response, list, "抽采管路类型信息");
    }
    
    

    
}
