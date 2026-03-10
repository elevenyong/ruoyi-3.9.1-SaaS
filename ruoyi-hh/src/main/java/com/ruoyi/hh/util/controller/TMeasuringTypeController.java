package com.ruoyi.hh.util.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.util.domain.TMeasuringType;
import com.ruoyi.hh.util.service.ITMeasuringTypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (TMeasuringType)表控制层
 *
 * @author Eleven
 * @since 2022-07-16 18:12:17
 */
@RestController
@RequestMapping("tMeasuringType")
public class TMeasuringTypeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITMeasuringTypeService tMeasuringTypeService;

    /**
     * 获取列表
     *
     * @param tMeasuringType 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tMeasuringType:list')")
    public TableDataInfo selectAll(@RequestBody TMeasuringType tMeasuringType) {

        startPage();
        List<TMeasuringType> list = this.tMeasuringTypeService.selectTMeasuringTypeList(tMeasuringType);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tMeasuringType 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tMeasuringType:insert')")
    public void insertTMeasuringType(@RequestBody TMeasuringType tMeasuringType) {
        this.tMeasuringTypeService.insertTMeasuringType(tMeasuringType);
    }

	  /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    //@PreAuthorize("@ss.hasPermi('tMeasuringType:insert')")
    public void insert(@RequestBody List<TMeasuringType> list){
        if(list.size()>0){
            for(TMeasuringType tMeasuringType:list){
                this.tMeasuringTypeService.insertTMeasuringType(tMeasuringType);
            }
        }
    }


    /**
     * 修改
     *
     * @param tMeasuringType 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTMeasuringType")
    //@PreAuthorize("@ss.hasPermi('tMeasuringType:updateTMeasuringType')")
    public AjaxResult updateTRoadway(@RequestBody TMeasuringType tMeasuringType) {
        return AjaxResult.success(this.tMeasuringTypeService.updateTMeasuringType(tMeasuringType));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tMeasuringType:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tMeasuringTypeService.selectTMeasuringTypeById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTMeasuringTypeById")
    //@PreAuthorize("@ss.hasPermi('tMeasuringType:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tMeasuringTypeService.deleteTMeasuringTypeById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTMeasuringTypeByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tMeasuringTypeService.deleteTMeasuringTypeByIds(ids));
    }

    /**
     * 分页获取测点类型
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TMeasuringType> list =this.tMeasuringTypeService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TMeasuringType tMeasuringType) {
        List<TMeasuringType> list=this.tMeasuringTypeService.selectTMeasuringTypeList(tMeasuringType);
        ExcelUtil<TMeasuringType> util = new ExcelUtil<TMeasuringType>(TMeasuringType.class);
        util.exportExcel(response, list, "测点类型数据");
    }


}

