package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TEvaluateUnit;
import com.ruoyi.hh.physical.service.ITEvaluateUnitService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TEvaluateUnit)表控制层
 *
 * @author Eleven
 * @since 2022-08-16 16:48:47
 */
@RestController
@RequestMapping("tEvaluateUnit")
public class TEvaluateUnitController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITEvaluateUnitService tEvaluateUnitService;

    /**
     * 获取列表
     *
     * @param tEvaluateUnit 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tEvaluateUnit:list')")
    public TableDataInfo selectAll(@RequestBody TEvaluateUnit tEvaluateUnit) {
        startPage();
        List<TEvaluateUnit> list = this.tEvaluateUnitService.selectTEvaluateUnitList(tEvaluateUnit);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tEvaluateUnit 单条数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tEvaluateUnit:insert')")
    public void insertTEvaluateUnit(@RequestBody TEvaluateUnit tEvaluateUnit) {
        this.tEvaluateUnitService.insertTEvaluateUnit(tEvaluateUnit);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tEvaluateUnit:insert')")
    public void insert(@RequestBody List<TEvaluateUnit> list) {
        if (list.size() > 0) {
            for (TEvaluateUnit tEvaluateUnit : list) {
                this.tEvaluateUnitService.insertTEvaluateUnit(tEvaluateUnit);
            }
        }
        //this.tEvaluateUnitService.insertTEvaluateUnitList(list);
    }


    /**
     * 修改
     *
     * @param tEvaluateUnit 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTEvaluateUnit")
    //@PreAuthorize("@ss.hasPermi('tEvaluateUnit:updateTEvaluateUnit')")
    public AjaxResult updateTRoadway(@RequestBody TEvaluateUnit tEvaluateUnit) {
        return AjaxResult.success(this.tEvaluateUnitService.updateTEvaluateUnit(tEvaluateUnit));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tEvaluateUnit:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tEvaluateUnitService.selectTEvaluateUnitById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTEvaluateUnitById")
    //@PreAuthorize("@ss.hasPermi('tEvaluateUnit:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tEvaluateUnitService.deleteTEvaluateUnitById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTEvaluateUnitByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tEvaluateUnitService.deleteTEvaluateUnitByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TEvaluateUnit> list = new ArrayList<>();
        list=   this.tEvaluateUnitService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TEvaluateUnit tEvaluateUnit) {
        List<TEvaluateUnit> list=this.tEvaluateUnitService.selectTEvaluateUnitList(tEvaluateUnit);
        ExcelUtil<TEvaluateUnit> util = new ExcelUtil<TEvaluateUnit>(TEvaluateUnit.class);
        util.exportExcel(response, list, "评价单元数据");
    }

    /**
     * 根据工作面ID或者评价单元ID查询评判报告相关信息
     * @param workfaceId 工作面ID
     * @param evaluateUnitId 评价单元ID
     * @return 列表
     */
    @PostMapping("getJudgeReportInfo")
    public AjaxResult getJudgeReportInfo(@RequestParam(value="workfaceId",required =false)Integer workfaceId,
                                                  @RequestParam(value="evaluateUnitId",required =false)Integer evaluateUnitId){
        List<TEvaluateUnit> list= this.tEvaluateUnitService.getJudgeReportInfo(workfaceId, evaluateUnitId);
        return AjaxResult.success(list);
    }


}

