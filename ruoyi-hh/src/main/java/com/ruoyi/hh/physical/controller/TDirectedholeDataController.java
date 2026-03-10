package com.ruoyi.hh.physical.controller;



import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TDirectedholeData;
import com.ruoyi.hh.physical.service.ITDirectedholeDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设计定向钻数据(TDirectedholeData)表控制层
 *
 * @author makejava
 * @since 2023-03-06 17:51:54
 */
@RestController
@RequestMapping("tDirectedholeData")
public class TDirectedholeDataController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDirectedholeDataService tDirectedholeDataService;

    /**
     * 获取列表
     *
     * @param tDirectedholeData  要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeData:list')")
    public TableDataInfo selectAll(@RequestBody TDirectedholeData tDirectedholeData) {
        startPage();
        List<TDirectedholeData> list =this.tDirectedholeDataService.selectTDirectedholeDataList(tDirectedholeData);
        return getDataTable(list);
    }
    
    /**
     * 插入单条数据
     *
     * @param tDirectedholeData 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeData:insert')")
    public void insertTDirectedholeData (@RequestBody TDirectedholeData tDirectedholeData) {
        this.tDirectedholeDataService.insertTDirectedholeData(tDirectedholeData);
    }
    
     /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeData:insert')")
    public void insert(@RequestBody List<TDirectedholeData> list){
        if(list.size()>0){
            for(TDirectedholeData td:list){
                this.tDirectedholeDataService.insertTDirectedholeData(td);
            }
        }
    }
    

     /**
     * 修改
     *
     * @param tDirectedholeData 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDirectedholeData")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeData:updateTDirectedholeData')")
    public AjaxResult updateTDirectedholeData(@RequestBody TDirectedholeData tDirectedholeData) {
        return AjaxResult.success(this.tDirectedholeDataService.updateTDirectedholeData(tDirectedholeData));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeData:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDirectedholeDataService.selectTDirectedholeDataById(id));
    }
    
    
    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDirectedholeDataById")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeData:id')")
    public AjaxResult deleteTDirectedholeDataById(@RequestParam int id) {
        int result = this.tDirectedholeDataService.deleteTDirectedholeDataById(id);
        return AjaxResult.success(result);
    }
    
    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDirectedholeDataByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tDirectedholeDataService.deleteTDirectedholeDataByIds(ids));
    }
    
    

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDirectedholeData tDirectedholeData) {
        List<TDirectedholeData> list=this.tDirectedholeDataService.selectTDirectedholeDataList(tDirectedholeData);
        ExcelUtil<TDirectedholeData> util = new ExcelUtil<TDirectedholeData>(TDirectedholeData.class);
        util.exportExcel(response, list, "定向钻孔数据");
    }
    
    

    
}

