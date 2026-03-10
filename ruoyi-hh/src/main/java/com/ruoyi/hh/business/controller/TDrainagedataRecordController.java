package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TDrainagedataRecord;
import com.ruoyi.hh.business.service.ITDrainagedataRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 抽采数据记录(TDrainagedataRecord)表控制层
 *
 * @author makejava
 * @since 2023-07-31 14:28:40
 */
@RestController
@RequestMapping("tDrainagedataRecord")
public class TDrainagedataRecordController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDrainagedataRecordService tDrainagedataRecordService;

    /**
     * 获取列表
     *
     * @param tDrainagedataRecord  要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataRecord:list')")
    public TableDataInfo selectAll(@RequestBody TDrainagedataRecord tDrainagedataRecord) {
        startPage();
        List<TDrainagedataRecord> list =this.tDrainagedataRecordService.selectTDrainagedataRecordList(tDrainagedataRecord);
        return getDataTable(list);
    }
    
    /**
     * 插入单条数据
     *
     * @param tDrainagedataRecord 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataRecord:insert')")
    public void insertTDrainagedataRecord (@RequestBody TDrainagedataRecord tDrainagedataRecord) {
        this.tDrainagedataRecordService.insertTDrainagedataRecord(tDrainagedataRecord);
    }
    
     /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataRecord:insert')")
    public void insert(@RequestBody List<TDrainagedataRecord> list){
        if(list.size()>0){
            for(TDrainagedataRecord td:list){
                this.tDrainagedataRecordService.insertTDrainagedataRecord(td);
            }
        }
    }
    

     /**
     * 修改
     *
     * @param tDrainagedataRecord 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDrainagedataRecord")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataRecord:updateTDrainagedataRecord')")
    public AjaxResult updateTDrainagedataRecord(@RequestBody TDrainagedataRecord tDrainagedataRecord) {
        return AjaxResult.success(this.tDrainagedataRecordService.updateTDrainagedataRecord(tDrainagedataRecord));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataRecord:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDrainagedataRecordService.selectTDrainagedataRecordById(id));
    }
    
    
    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDrainagedataRecordById")
    // @PreAuthorize("@ss.hasPermi('tDrainagedataRecord:id')")
    public AjaxResult deleteTDrainagedataRecordById(@RequestParam int id) {
        int result = this.tDrainagedataRecordService.deleteTDrainagedataRecordById(id);
        return AjaxResult.success(result);
    }
    
    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDrainagedataRecordByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tDrainagedataRecordService.deleteTDrainagedataRecordByIds(ids));
    }
    
    

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDrainagedataRecord tDrainagedataRecord) {
        List<TDrainagedataRecord> list=this.tDrainagedataRecordService.selectTDrainagedataRecordList(tDrainagedataRecord);
        ExcelUtil<TDrainagedataRecord> util = new ExcelUtil<TDrainagedataRecord>(TDrainagedataRecord.class);
        util.exportExcel(response, list, "抽采记录数据");
    }

    @PostMapping("getLocalVague")
    public List<String> getLocalVague(String measurePointLocal, String time) {
        return this.tDrainagedataRecordService.getLocalVague(measurePointLocal, time);
    }

}
