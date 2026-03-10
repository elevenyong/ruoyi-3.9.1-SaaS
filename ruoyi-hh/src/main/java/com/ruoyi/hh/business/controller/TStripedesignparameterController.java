package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TStripedesignparameter;
import com.ruoyi.hh.business.service.ITStripedesignparameterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TStripedesignparameter)表控制层
 *
 * @author Eleven
 * @since 2022-07-16 11:18:00
 */
@RestController
@RequestMapping("tStripedesignparameter")
public class TStripedesignparameterController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITStripedesignparameterService tStripedesignparameterService;

    /**
     * 获取列表
     *
     * @param tStripedesignparameter 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
//    @PreAuthorize("@ss.hasPermi('tStripedesignparameter:list')")
    public TableDataInfo selectAll(@RequestBody TStripedesignparameter tStripedesignparameter) {
        startPage();
        List<TStripedesignparameter> list =this.tStripedesignparameterService.selectTStripedesignparameterList(tStripedesignparameter);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tStripedesignparameter 单挑数据
     */
    @PostMapping("insert")
//	@PreAuthorize("@ss.hasPermi('tStripedesignparameter:insert')")
    public void insertTStripedesignparameter(@RequestBody TStripedesignparameter tStripedesignparameter) {
        this.tStripedesignparameterService.insertTStripedesignparameter(tStripedesignparameter);
    }

	  /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    @PreAuthorize("@ss.hasPermi('tStripedesignparameter:insert')")
    public void insert(@RequestBody List<TStripedesignparameter> list){
        if(list!=null){
            for(TStripedesignparameter tStripedesignparameter:list){
                this.tStripedesignparameterService.insertTStripedesignparameter(tStripedesignparameter);
            }
        }
    }


    /**
     * 修改
     *
     * @param tStripedesignparameter 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTStripedesignparameter")
//	@PreAuthorize("@ss.hasPermi('tStripedesignparameter:updateTStripedesignparameter')")
    public AjaxResult updateTRoadway(@RequestBody TStripedesignparameter tStripedesignparameter) {
        return AjaxResult.success(this.tStripedesignparameterService.updateTStripedesignparameter(tStripedesignparameter));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
//    @PreAuthorize("@ss.hasPermi('tStripedesignparameter:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tStripedesignparameterService.selectTStripedesignparameterById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTStripedesignparameterById")
//	@PreAuthorize("@ss.hasPermi('tStripedesignparameter:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tStripedesignparameterService.deleteTStripedesignparameterById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTStripedesignparameterByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tStripedesignparameterService.deleteTStripedesignparameterByIds(ids));
    }

    /**
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TStripedesignparameter> list = new ArrayList();
              list=  this.tStripedesignparameterService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TStripedesignparameter tStripedesignparameter) {
        List<TStripedesignparameter> list=this.tStripedesignparameterService.selectTStripedesignparameterList(tStripedesignparameter);
        ExcelUtil<TStripedesignparameter> util = new ExcelUtil<TStripedesignparameter>(TStripedesignparameter.class);
        util.exportExcel(response, list, "条带治理钻孔设计参数数据");
    }


}

