package com.ruoyi.hh.physical.controller;
 
 
 
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TCoalUncover;
import com.ruoyi.hh.physical.service.ITCoalUncoverService;
import com.ruoyi.hh.physical.service.ITMeasuringPointService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 揭煤抽采信息(TCoalUncover)表控制层
 *
 * @author Eleven
 * @since 2022-12-09 11:59:33
 */
@RestController
@RequestMapping("tCoalUncover")
public class TCoalUncoverController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITCoalUncoverService tCoalUncoverService;
    @Resource
    private ITMeasuringPointService itMeasuringPointService;
	
	/**
     * 获取列表
     * @param tCoalUncover 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tCoalUncover:list')")
    public TableDataInfo selectAll(@RequestBody TCoalUncover tCoalUncover) {
        startPage();
        List<TCoalUncover> list=this.tCoalUncoverService.selectTCoalUncoverList(tCoalUncover);
        for(TCoalUncover tu:list){
            tu.setTMeasuringPointList(this.itMeasuringPointService.getByCoalUncoverid(tu.getId()));
        }
        return getDataTable(list);
    }
	
	 /**
     * 插入单条数据
     * @param tCoalUncover 单条数据
     */
    @PostMapping("insert")
	//@PreAuthorize("@ss.hasPermi('tCoalUncover:insert')")
    public void insertTCoalUncover (@RequestBody TCoalUncover tCoalUncover){
        this.tCoalUncoverService.insertTCoalUncover(tCoalUncover);
    }
	
	  /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
   // @PreAuthorize("@ss.hasPermi('tCoalUncover:insert')")
    public void insert(@RequestBody List<TCoalUncover> list){
        if(list.size()>0){
            for(TCoalUncover tCoalUncover:list){
                this.tCoalUncoverService.insertTCoalUncover(tCoalUncover);
            }
        }
        //this.tCoalUncoverService.insertTCoalUncoverList(list);
    }
	
	
	 /**
     * 修改
     * @param tCoalUncover 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTCoalUncover")
	//@PreAuthorize("@ss.hasPermi('tCoalUncover:updateTCoalUncover')")
    public AjaxResult updateTRoadway(@RequestBody TCoalUncover tCoalUncover){
        return AjaxResult.success(this.tCoalUncoverService.updateTCoalUncover(tCoalUncover));
    }
	
	 /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tCoalUncover:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tCoalUncoverService.selectTCoalUncoverById(id));
    }
	
	/**
     * 根据id删除单挑数据
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTCoalUncoverById")
	//@PreAuthorize("@ss.hasPermi('tCoalUncover:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id){
        int result=this.tCoalUncoverService.deleteTCoalUncoverById(id);
        return AjaxResult.success(result);
    }
	
	/**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTCoalUncoverByIds")
    public AjaxResult delete (@RequestParam Integer[] ids){
        return AjaxResult.success(this.tCoalUncoverService.deleteTCoalUncoverByIds(ids));
    }
    

        /**
     * 导出excel
     * @param response res
     * @param list 列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<TCoalUncover> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<TCoalUncover> util = new ExcelUtil<TCoalUncover>(TCoalUncover.class);
        util.exportExcel(response, list, "揭煤抽采信息数据");
    }
	
    
}

