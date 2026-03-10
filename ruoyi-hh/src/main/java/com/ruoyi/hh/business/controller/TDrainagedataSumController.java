package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TDrainagedataSum;
import com.ruoyi.hh.business.service.ITDrainagedataSumService;
import com.ruoyi.hh.business.service.ITBkSensorDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * (TDrainagedataSum)表控制层
 *
 * @author makejava
 * @since 2023-08-07 16:01:37
 */
@RestController
@RequestMapping("tBkDrainagedataSum")
public class TDrainagedataSumController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDrainagedataSumService tDrainagedataSumService;
    @Resource
    private ITBkSensorDataService tBkSensorDataService;

    /**
     * 获取列表
     *
     * @param tBkDrainagedataSum  要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tBkDrainagedataSum:list')")
    public TableDataInfo selectAll(@RequestBody TDrainagedataSum tBkDrainagedataSum) {
        startPage();
        List<TDrainagedataSum> list =this.tDrainagedataSumService.selectTDrainagedataSumList(tBkDrainagedataSum);
        return getDataTable(list);
    }
    
    /**
     * 插入单条数据
     *
     * @param tBkDrainagedataSum 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tBkDrainagedataSum:insert')")
    public void insertTDrainagedataSum (@RequestBody TDrainagedataSum tBkDrainagedataSum) {
        this.tDrainagedataSumService.insertTDrainagedataSum(tBkDrainagedataSum);
    }
    
     /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tBkDrainagedataSum:insert')")
    public void insert(@RequestBody List<TDrainagedataSum> list){
        if(list.size()>0){
            for(TDrainagedataSum td:list){
                this.tDrainagedataSumService.insertTDrainagedataSum(td);
            }
        }
    }
    

     /**
     * 修改
     *
     * @param tBkDrainagedataSum 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDrainagedataSum")
    // @PreAuthorize("@ss.hasPermi('tBkDrainagedataSum:updateTDrainagedataSum')")
    public AjaxResult updateTDrainagedataSum(@RequestBody TDrainagedataSum tBkDrainagedataSum) {
        return AjaxResult.success(this.tDrainagedataSumService.updateTDrainagedataSum(tBkDrainagedataSum));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tBkDrainagedataSum:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDrainagedataSumService.selectTDrainagedataSumById(id));
    }
    
    
    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDrainagedataSumById")
    // @PreAuthorize("@ss.hasPermi('tBkDrainagedataSum:id')")
    public AjaxResult deleteTDrainagedataSumById(@RequestParam int id) {
        int result = this.tDrainagedataSumService.deleteTDrainagedataSumById(id);
        return AjaxResult.success(result);
    }
    
    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDrainagedataSumByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tDrainagedataSumService.deleteTDrainagedataSumByIds(ids));
    }
    
    

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDrainagedataSum tBkDrainagedataSum) {
        List<TDrainagedataSum> list=this.tDrainagedataSumService.selectTDrainagedataSumList(tBkDrainagedataSum);
        ExcelUtil<TDrainagedataSum> util = new ExcelUtil<TDrainagedataSum>(TDrainagedataSum.class);
        util.exportExcel(response, list, "抽采单元数据");
    }

    @GetMapping("fileData")
    public void fileData() throws ParseException {
        tBkSensorDataService.fileStationDataSum(DateUtils.getDate());
    }

}
