package com.ruoyi.hh.simulate.controller;
 
 
 
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.simulate.domain.DataHistory;
import com.ruoyi.hh.simulate.service.IDataHistoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 模拟存储(DataHistory)表控制层
 *
 * @author Eleven
 * @since 2022-12-13 11:06:12
 */
@RestController
@RequestMapping("dataHistory")
public class DataHistoryController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private IDataHistoryService dataHistoryService;
    @Resource
    private RedisCache redisCache;
	
	/**
     * 获取列表
     * @param dataHistory 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('dataHistory:list')")
    public TableDataInfo selectAll(@RequestBody DataHistory dataHistory) {
        List<DataHistory> list=this.dataHistoryService.selectDataHistoryList(dataHistory);
        return getDataTable(list);
    }
	
	 /**
     * 插入单条数据
     * @param dataHistory 单条数据
     */
    @PostMapping("insert")
	//@PreAuthorize("@ss.hasPermi('dataHistory:insert')")
    public void insertDataHistory (@RequestBody DataHistory dataHistory){
        this.dataHistoryService.insertDataHistory(dataHistory);
    }
	
	  /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
   // @PreAuthorize("@ss.hasPermi('dataHistory:insert')")
    public void insert(@RequestBody List<DataHistory> list){
        if(list.size()>0){
            for(DataHistory dataHistory:list){
                this.dataHistoryService.insertDataHistory(dataHistory);
            }
        }
        //this.dataHistoryService.insertDataHistoryList(list);
    }
	
	
	 /**
     * 修改
     * @param dataHistory 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateDataHistory")
	//@PreAuthorize("@ss.hasPermi('dataHistory:updateDataHistory')")
    public AjaxResult updateTRoadway(@RequestBody DataHistory dataHistory){
        return AjaxResult.success(this.dataHistoryService.updateDataHistory(dataHistory));
    }
	
	 /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('dataHistory:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.dataHistoryService.selectDataHistoryById(id));
    }
	
	/**
     * 根据id删除单挑数据
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteDataHistoryById")
	//@PreAuthorize("@ss.hasPermi('dataHistory:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id){
        int result=this.dataHistoryService.deleteDataHistoryById(id);
        return AjaxResult.success(result);
    }
	
	/**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteDataHistoryByIds")
    public AjaxResult delete (@RequestParam Integer[] ids){
        return AjaxResult.success(this.dataHistoryService.deleteDataHistoryByIds(ids));
    }
    

        /**
     * 导出excel
     * @param response res
     * @param list 列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<DataHistory> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<DataHistory> util = new ExcelUtil<DataHistory>(DataHistory.class);
        util.exportExcel(response, list, "工作面数据");
    }

    @PostMapping("getInTime")
    public List<DataHistory> getInTime(String startDate,String endDate){
        return this.dataHistoryService.getInTime(startDate, endDate);
    }

    @GetMapping("getTable")
    public List<String> getTalbe(){
        return redisCache.getCacheList("tables");
    }
	
    
}

