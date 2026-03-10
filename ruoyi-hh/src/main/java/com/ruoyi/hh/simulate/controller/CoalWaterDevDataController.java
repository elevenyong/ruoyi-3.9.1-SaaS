package com.ruoyi.hh.simulate.controller;



import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.simulate.domain.CoalWaterDevData;
import com.ruoyi.hh.simulate.service.ICoalWaterDevDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 水煤分离之煤水计量装置(CoalWaterDevData)表控制层
 *
 * @author makejava
 * @since 2023-03-21 14:41:44
 */
@RestController
@RequestMapping("coalWaterDevData")
public class CoalWaterDevDataController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ICoalWaterDevDataService coalWaterDevDataService;

    /**
     * 获取列表
     *
     * @param coalWaterDevData  要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('coalWaterDevData:list')")
    public TableDataInfo selectAll(@RequestBody CoalWaterDevData coalWaterDevData) {
        startPage();
        List<CoalWaterDevData> list =this.coalWaterDevDataService.selectCoalWaterDevDataList(coalWaterDevData);
        return getDataTable(list);
    }
    
    /**
     * 插入单条数据
     *
     * @param coalWaterDevData 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('coalWaterDevData:insert')")
    public void insertCoalWaterDevData (@RequestBody CoalWaterDevData coalWaterDevData) {
        this.coalWaterDevDataService.insertCoalWaterDevData(coalWaterDevData);
    }
    
     /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('coalWaterDevData:insert')")
    public void insert(@RequestBody List<CoalWaterDevData> list){
        if(list.size()>0){
            for(CoalWaterDevData td:list){
                this.coalWaterDevDataService.insertCoalWaterDevData(td);
            }
        }
    }
    

     /**
     * 修改
     *
     * @param coalWaterDevData 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateCoalWaterDevData")
    // @PreAuthorize("@ss.hasPermi('coalWaterDevData:updateCoalWaterDevData')")
    public AjaxResult updateCoalWaterDevData(@RequestBody CoalWaterDevData coalWaterDevData) {
        return AjaxResult.success(this.coalWaterDevDataService.updateCoalWaterDevData(coalWaterDevData));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('coalWaterDevData:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.coalWaterDevDataService.selectCoalWaterDevDataById(id));
    }
    
    
    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteCoalWaterDevDataById")
    // @PreAuthorize("@ss.hasPermi('coalWaterDevData:id')")
    public AjaxResult deleteCoalWaterDevDataById(@RequestParam int id) {
        int result = this.coalWaterDevDataService.deleteCoalWaterDevDataById(id);
        return AjaxResult.success(result);
    }
    
    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteCoalWaterDevDataByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.coalWaterDevDataService.deleteCoalWaterDevDataByIds(ids));
    }
    
    

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody CoalWaterDevData coalWaterDevData) {
        List<CoalWaterDevData> list=this.coalWaterDevDataService.selectCoalWaterDevDataList(coalWaterDevData);
        ExcelUtil<CoalWaterDevData> util = new ExcelUtil<CoalWaterDevData>(CoalWaterDevData.class);
        util.exportExcel(response, list, "抽采单元数据");
    }
    
    

    
}

