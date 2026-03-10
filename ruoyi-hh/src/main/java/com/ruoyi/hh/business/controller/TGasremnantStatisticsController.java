package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TGasremnantStatistics;
import com.ruoyi.hh.business.service.ITGasremnantStatisticsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 残余瓦斯含量统计表(TGasremnantStatistics)表控制层
 *
 * @author Eleven
 * @since 2023-02-04 09:51:05
 */
@RestController
@RequestMapping("tGasremnantStatistics")
public class TGasremnantStatisticsController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITGasremnantStatisticsService tGasremnantStatisticsService;

    /**
     * 获取列表
     *
     * @param tGasremnantStatistics 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tGasremnantStatistics:list')")
    public TableDataInfo selectAll(@RequestBody TGasremnantStatistics tGasremnantStatistics) {
        startPage();
        List<TGasremnantStatistics> list=this.tGasremnantStatisticsService.selectTGasremnantStatisticsList(tGasremnantStatistics);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tGasremnantStatistics 单条数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tGasremnantStatistics:insert')")
    public void insertTGasremnantStatistics(@RequestBody TGasremnantStatistics tGasremnantStatistics) {
        this.tGasremnantStatisticsService.insertTGasremnantStatistics(tGasremnantStatistics);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tGasremnantStatistics:insert')")
    public void insert(@RequestBody List<TGasremnantStatistics> list) {
        if (list.size() > 0) {
            for (TGasremnantStatistics tGasremnantStatistics : list) {
                this.tGasremnantStatisticsService.insertTGasremnantStatistics(tGasremnantStatistics);
            }
        }
        //this.tGasremnantStatisticsService.insertTGasremnantStatisticsList(list);
    }


    /**
     * 修改
     *
     * @param tGasremnantStatistics 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTGasremnantStatistics")
    //@PreAuthorize("@ss.hasPermi('tGasremnantStatistics:updateTGasremnantStatistics')")
    public AjaxResult updateTRoadway(@RequestBody TGasremnantStatistics tGasremnantStatistics) {
        return AjaxResult.success(this.tGasremnantStatisticsService.updateTGasremnantStatistics(tGasremnantStatistics));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tGasremnantStatistics:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tGasremnantStatisticsService.selectTGasremnantStatisticsById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTGasremnantStatisticsById")
    //@PreAuthorize("@ss.hasPermi('tGasremnantStatistics:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tGasremnantStatisticsService.deleteTGasremnantStatisticsById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTGasremnantStatisticsByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tGasremnantStatisticsService.deleteTGasremnantStatisticsByIds(ids));
    }


    /**
     * 导出excel
     *
     * @param response res
     * @param list     列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<TGasremnantStatistics> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<TGasremnantStatistics> util = new ExcelUtil<TGasremnantStatistics>(TGasremnantStatistics.class);
        util.exportExcel(response, list, "残余瓦斯含量统计");
    }


}

