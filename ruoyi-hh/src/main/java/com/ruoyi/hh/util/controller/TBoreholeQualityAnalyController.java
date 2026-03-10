package com.ruoyi.hh.util.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.util.domain.TBoreholeQualityAnaly;
import com.ruoyi.hh.util.service.ITBoreholeQualityAnalyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TBoreholeQualityAnaly)表控制层
 *
 * @author Eleven
 * @since 2022-07-18 11:53:31
 */
@RestController
@RequestMapping("tBoreholeQualityAnaly")
public class TBoreholeQualityAnalyController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITBoreholeQualityAnalyService tBoreholeQualityAnalyService;

    /**
     * 获取列表
     *
     * @param tBoreholeQualityAnaly 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tBoreholeQualityAnaly:list')")
    public TableDataInfo selectAll(@RequestBody TBoreholeQualityAnaly tBoreholeQualityAnaly) {
        startPage();
        List<TBoreholeQualityAnaly> list =this.tBoreholeQualityAnalyService.selectTBoreholeQualityAnalyList(tBoreholeQualityAnaly);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tBoreholeQualityAnaly 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tBoreholeQualityAnaly:insert')")
    public void insertTBoreholeQualityAnaly(@RequestBody TBoreholeQualityAnaly tBoreholeQualityAnaly) {
        this.tBoreholeQualityAnalyService.insertTBoreholeQualityAnaly(tBoreholeQualityAnaly);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    //@PreAuthorize("@ss.hasPermi('tBoreholeQualityAnaly:insert')")
    public void insert(@RequestBody List<TBoreholeQualityAnaly> list) {
        if (list.size() > 0) {
            for (TBoreholeQualityAnaly tBoreholeQualityAnaly : list) {
                this.tBoreholeQualityAnalyService.insertTBoreholeQualityAnaly(tBoreholeQualityAnaly);
            }

        }
//        this.tBoreholeQualityAnalyService.insertTBoreholeQualityAnalyList(list);
    }


    /**
     * 修改
     *
     * @param tBoreholeQualityAnaly 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTBoreholeQualityAnaly")
    //@PreAuthorize("@ss.hasPermi('tBoreholeQualityAnaly:updateTBoreholeQualityAnaly')")
    public AjaxResult updateTRoadway(@RequestBody TBoreholeQualityAnaly tBoreholeQualityAnaly) {
        return AjaxResult.success(this.tBoreholeQualityAnalyService.updateTBoreholeQualityAnaly(tBoreholeQualityAnaly));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tBoreholeQualityAnaly:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tBoreholeQualityAnalyService.selectTBoreholeQualityAnalyById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTBoreholeQualityAnalyById")
    //@PreAuthorize("@ss.hasPermi('tBoreholeQualityAnaly:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tBoreholeQualityAnalyService.deleteTBoreholeQualityAnalyById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTBoreholeQualityAnalyByIds")
    //@PreAuthorize("@ss.hasPermi('tBoreholeQualityAnaly:deleteTBoreholeQualityAnalyByIds')")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tBoreholeQualityAnalyService.deleteTBoreholeQualityAnalyByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    //@PreAuthorize("@ss.hasPermi('tBoreholeQualityAnaly:getPageList')")
    public TableDataInfo getPageList() {
        startPage();
        List<TBoreholeQualityAnaly> list = new ArrayList<>();
        list=this.tBoreholeQualityAnalyService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TBoreholeQualityAnaly tBoreholeQualityAnaly) {
        List<TBoreholeQualityAnaly> list=this.tBoreholeQualityAnalyService.selectTBoreholeQualityAnalyList(tBoreholeQualityAnaly);
        ExcelUtil<TBoreholeQualityAnaly> util = new ExcelUtil<TBoreholeQualityAnaly>(TBoreholeQualityAnaly.class);
        util.exportExcel(response, list, "钻孔质量分析数据");
    }


}

