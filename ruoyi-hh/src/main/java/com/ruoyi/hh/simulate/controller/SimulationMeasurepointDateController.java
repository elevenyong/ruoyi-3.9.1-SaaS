package com.ruoyi.hh.simulate.controller;
 
 
 
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.simulate.domain.SimulationMeasurepointData;
import com.ruoyi.hh.simulate.service.ISimulationMeasurepointDateService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 模拟测点的实时数据(SimulationMeasurepointDate)表控制层
 *
 * @author eleven
 * @since 2022-11-28 14:30:13
 */
@RestController
@RequestMapping("simulationMeasurepointDate")
public class SimulationMeasurepointDateController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ISimulationMeasurepointDateService simulationMeasurepointDateService;

	
	/**
     * 获取列表
     * @param simulationMeasurepointDate 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('simulationMeasurepointDate:list')")
    public TableDataInfo selectAll(@RequestBody SimulationMeasurepointData simulationMeasurepointDate) {
        startPage();
        List<SimulationMeasurepointData> list=this.simulationMeasurepointDateService.selectSimulationMeasurepointDateList(simulationMeasurepointDate);
        return getDataTable(list);
    }
	
	 /**
     * 插入单条数据
     * @param simulationMeasurepointDate 单条数据
     */
    @PostMapping("insert")
	//@PreAuthorize("@ss.hasPermi('simulationMeasurepointDate:insert')")
    public void insertSimulationMeasurepointDate (@RequestBody SimulationMeasurepointData simulationMeasurepointDate){
        this.simulationMeasurepointDateService.insertSimulationMeasurepointDate(simulationMeasurepointDate);
    }
	
	  /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
   // @PreAuthorize("@ss.hasPermi('simulationMeasurepointDate:insert')")
    public void insert(@RequestBody List<SimulationMeasurepointData> list){
        if(list.size()>0){
            for(SimulationMeasurepointData simulationMeasurepointDate:list){
                this.simulationMeasurepointDateService.insertSimulationMeasurepointDate(simulationMeasurepointDate);
            }
        }
        //this.simulationMeasurepointDateService.insertSimulationMeasurepointDateList(list);
    }
	
	
	 /**
     * 修改
     * @param simulationMeasurepointDate 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateSimulationMeasurepointDate")
	//@PreAuthorize("@ss.hasPermi('simulationMeasurepointDate:updateSimulationMeasurepointDate')")
    public AjaxResult updateTRoadway(@RequestBody SimulationMeasurepointData simulationMeasurepointDate){
        return AjaxResult.success(this.simulationMeasurepointDateService.updateSimulationMeasurepointDate(simulationMeasurepointDate));
    }
	
	 /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('simulationMeasurepointDate:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.simulationMeasurepointDateService.selectSimulationMeasurepointDateById(id));
    }
	
	/**
     * 根据id删除单挑数据
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteSimulationMeasurepointDateById")
	//@PreAuthorize("@ss.hasPermi('simulationMeasurepointDate:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id){
        int result=this.simulationMeasurepointDateService.deleteSimulationMeasurepointDateById(id);
        return AjaxResult.success(result);
    }
	
	/**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteSimulationMeasurepointDateByIds")
    public AjaxResult delete (@RequestParam Integer[] ids){
        return AjaxResult.success(this.simulationMeasurepointDateService.deleteSimulationMeasurepointDateByIds(ids));
    }
    

        /**
     * 导出excel
     * @param response res
     * @param list 列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<SimulationMeasurepointData> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<SimulationMeasurepointData> util = new ExcelUtil<SimulationMeasurepointData>(SimulationMeasurepointData.class);
        util.exportExcel(response, list, "模拟测点的实时数据");
    }

}

