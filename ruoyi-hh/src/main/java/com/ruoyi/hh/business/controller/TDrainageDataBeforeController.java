package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TDrainagedataBefore;
import com.ruoyi.hh.business.service.ITDrainagedataBeforeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 抽采系统截止某时间的抽采量(TDrainagedataBefore)表控制层
 *
 * @author makejava
 * @since 2023-07-21 17:30:25
 */
@RestController
@RequestMapping("tDrainagedataBefore")
public class TDrainageDataBeforeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDrainagedataBeforeService tDrainagedataBeforeService;

    /**
     * 获取列表
     *
     * @param tDrainagedataBefore  要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataBefore:list')")
    public TableDataInfo selectAll(@RequestBody TDrainagedataBefore tDrainagedataBefore) {
        startPage();
        List<TDrainagedataBefore> list =this.tDrainagedataBeforeService.selectTDrainagedataBeforeList(tDrainagedataBefore);
        return getDataTable(list);
    }
    
    /**
     * 插入单条数据
     *
     * @param tDrainagedataBefore 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataBefore:insert')")
    public void insertTDrainagedataBefore (@RequestBody TDrainagedataBefore tDrainagedataBefore) {
        this.tDrainagedataBeforeService.insertTDrainagedataBefore(tDrainagedataBefore);
    }
    
     /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataBefore:insert')")
    public void insert(@RequestBody List<TDrainagedataBefore> list){
        if(list.size()>0){
            for(TDrainagedataBefore td:list){
                this.tDrainagedataBeforeService.insertTDrainagedataBefore(td);
            }
        }
    }
    

     /**
     * 修改
     *
     * @param tDrainagedataBefore 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDrainagedataBefore")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataBefore:updateTDrainagedataBefore')")
    public AjaxResult updateTDrainagedataBefore(@RequestBody TDrainagedataBefore tDrainagedataBefore) {
        return AjaxResult.success(this.tDrainagedataBeforeService.updateTDrainagedataBefore(tDrainagedataBefore));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataBefore:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDrainagedataBeforeService.selectTDrainagedataBeforeById(id));
    }
    
    
    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDrainagedataBeforeById")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataBefore:id')")
    public AjaxResult deleteTDrainagedataBeforeById(@RequestParam int id) {
        int result = this.tDrainagedataBeforeService.deleteTDrainagedataBeforeById(id);
        return AjaxResult.success(result);
    }
    
    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDrainagedataBeforeByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tDrainagedataBeforeService.deleteTDrainagedataBeforeByIds(ids));
    }
    
    

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDrainagedataBefore tDrainagedataBefore) {
        List<TDrainagedataBefore> list=this.tDrainagedataBeforeService.selectTDrainagedataBeforeList(tDrainagedataBefore);
        ExcelUtil<TDrainagedataBefore> util = new ExcelUtil<TDrainagedataBefore>(TDrainagedataBefore.class);
        util.exportExcel(response, list, "测点安装位置某日期前的抽采数据");
    }
    
    

    
}
