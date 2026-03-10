package com.ruoyi.hh.physical.controller;
 
 
 
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TGoafinfo;
import com.ruoyi.hh.physical.service.ITGoafinfoService;
import com.ruoyi.hh.physical.service.ITMeasuringPointService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 采空区信息(TGoafinfo)表控制层
 *
 * @author Eleven
 * @since 2022-12-09 11:59:33
 */
@RestController
@RequestMapping("tGoafinfo")
public class TGoafinfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITGoafinfoService tGoafinfoService;
    @Resource
    private ITMeasuringPointService itMeasuringPointService;
	
	/**
     * 获取列表
     * @param tGoafinfo 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tGoafinfo:list')")
    public TableDataInfo selectAll(@RequestBody TGoafinfo tGoafinfo) {
        List<TGoafinfo> list=this.tGoafinfoService.selectTGoafinfoList(tGoafinfo);
        for(TGoafinfo tf:list){
            tf.setMeasuringPointList(itMeasuringPointService.getByGoafinfoid(tf.getId()));
        }
        return getDataTable(list);
    }
	
	 /**
     * 插入单条数据
     * @param tGoafinfo 单条数据
     */
    @PostMapping("insert")
	//@PreAuthorize("@ss.hasPermi('tGoafinfo:insert')")
    public void insertTGoafinfo (@RequestBody TGoafinfo tGoafinfo){
        this.tGoafinfoService.insertTGoafinfo(tGoafinfo);
    }
	
	  /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
   // @PreAuthorize("@ss.hasPermi('tGoafinfo:insert')")
    public void insert(@RequestBody List<TGoafinfo> list){
        if(list.size()>0){
            for(TGoafinfo tGoafinfo:list){
                this.tGoafinfoService.insertTGoafinfo(tGoafinfo);
            }
        }
        //this.tGoafinfoService.insertTGoafinfoList(list);
    }
	
	
	 /**
     * 修改
     * @param tGoafinfo 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTGoafinfo")
	//@PreAuthorize("@ss.hasPermi('tGoafinfo:updateTGoafinfo')")
    public AjaxResult updateTRoadway(@RequestBody TGoafinfo tGoafinfo){
        return AjaxResult.success(this.tGoafinfoService.updateTGoafinfo(tGoafinfo));
    }
	
	 /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tGoafinfo:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tGoafinfoService.selectTGoafinfoById(id));
    }
	
	/**
     * 根据id删除单挑数据
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTGoafinfoById")
	//@PreAuthorize("@ss.hasPermi('tGoafinfo:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id){
        int result=this.tGoafinfoService.deleteTGoafinfoById(id);
        return AjaxResult.success(result);
    }
	
	/**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTGoafinfoByIds")
    public AjaxResult delete (@RequestParam Integer[] ids){
        return AjaxResult.success(this.tGoafinfoService.deleteTGoafinfoByIds(ids));
    }
    

        /**
     * 导出excel
     * @param response res
     * @param list 列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<TGoafinfo> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<TGoafinfo> util = new ExcelUtil<TGoafinfo>(TGoafinfo.class);
        util.exportExcel(response, list, "采空区数据");
    }
	
    
}

